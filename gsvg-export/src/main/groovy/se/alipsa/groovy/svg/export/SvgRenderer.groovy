package se.alipsa.groovy.svg.export

import com.github.weisj.jsvg.SVGDocument
import com.github.weisj.jsvg.parser.LoaderContext
import com.github.weisj.jsvg.parser.SVGLoader
import se.alipsa.groovy.svg.Svg

import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.nio.charset.StandardCharsets

/**
 * Provides SVG rendering capabilities to raster formats (PNG, JPEG).
 * Uses jsvg library for high-quality SVG rendering.
 *
 * @since 1.0.0
 */
class SvgRenderer {

    /**
     * Saves the SVG content to a file.
     *
     * @param svg The SVG object to save
     * @param output The output file
     */
    static void toSvgFile(Svg svg, File output) {
        output.parentFile?.mkdirs()
        output.text = svg.toXml()
    }

    /**
     * Renders an SVG to PNG format and saves to a file.
     *
     * @param svg The SVG object to render
     * @param output The output file
     * @param options Rendering options (width, height, scale, antialiasing)
     */
    static void toPng(Svg svg, File output, Map options = [:]) {
        BufferedImage image = toBufferedImage(svg, options)
        output.parentFile?.mkdirs()
        ImageIO.write(image, 'PNG', output)
    }

    /**
     * Renders an SVG to PNG format and writes to an output stream.
     *
     * @param svg The SVG object to render
     * @param outputStream The output stream
     * @param options Rendering options (width, height, scale, antialiasing)
     */
    static void toPng(Svg svg, OutputStream outputStream, Map options = [:]) {
        BufferedImage image = toBufferedImage(svg, options)
        ImageIO.write(image, 'PNG', outputStream)
    }

    /**
     * Renders an SVG to JPEG format and saves to a file.
     *
     * @param svg The SVG object to render
     * @param output The output file
     * @param options Rendering options (width, height, scale, backgroundColor, quality, antialiasing)
     */
    static void toJpeg(Svg svg, File output, Map options = [:]) {
        BufferedImage image = toBufferedImage(svg, options, true)
        output.parentFile?.mkdirs()

        // Set JPEG quality if specified
        if (options.quality != null) {
            def writer = ImageIO.getImageWritersByFormatName('JPEG').next()
            def writeParam = writer.defaultWriteParam
            writeParam.compressionMode = ImageWriteParam.MODE_EXPLICIT
            writeParam.compressionQuality = options.quality as float

            output.withOutputStream { stream ->
                writer.output = ImageIO.createImageOutputStream(stream)
                writer.write(null, new IIOImage(image, null, null), writeParam)
                writer.dispose()
            }
        } else {
            ImageIO.write(image, 'JPEG', output)
        }
    }

    /**
     * Renders an SVG to a BufferedImage.
     *
     * @param svg The SVG object to render
     * @param options Rendering options:
     *   - width: Output width in pixels (if not specified, uses SVG width or viewBox)
     *   - height: Output height in pixels (if not specified, uses SVG height or viewBox)
     *   - scale: Scale factor (alternative to width/height)
     *   - backgroundColor: Background color for JPEG (default: white)
     *   - antialiasing: Enable antialiasing (default: true)
     * @param forJpeg Whether this is for JPEG output (adds background)
     * @return The rendered BufferedImage
     */
    static BufferedImage toBufferedImage(Svg svg, Map options = [:], boolean forJpeg = false) {
        // Parse SVG using jsvg library
        SVGLoader loader = new SVGLoader()
        String svgString = svg.toXml()
        ByteArrayInputStream svgStream = new ByteArrayInputStream(svgString.getBytes(StandardCharsets.UTF_8))
        SVGDocument document = loader.load(svgStream, null, LoaderContext.createDefault())

        if (document == null) {
            throw new IllegalArgumentException("Failed to parse SVG document")
        }

        // Determine output dimensions
        def (int width, int height) = calculateDimensions(svg, document, options)

        // Create BufferedImage
        // TYPE_INT_ARGB is crucial for transparency support
        int imageType = forJpeg ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB
        BufferedImage image = new BufferedImage(width, height, imageType)

        // Get graphics context
        Graphics2D g2d = image.createGraphics()

        try {
            // Apply background color for JPEG
            if (forJpeg) {
                String bgColor = options.backgroundColor ?: 'white'
                g2d.color = parseColor(bgColor)
                g2d.fillRect(0, 0, width, height)
            }

            // Apply rendering hints
            boolean antialiasing = options.antialiasing != null ? options.antialiasing : true
            if (antialiasing) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC)
            }

            // Scale the graphics context to fit the desired dimensions
            def size = document.size()
            double scaleX = width / size.width
            double scaleY = height / size.height
            g2d.scale(scaleX, scaleY)

            // Render the SVG
            document.render(null, g2d)

        } finally {
            g2d.dispose()
        }

        return image
    }

    /**
     * Calculates output dimensions based on SVG and options.
     */
    private static List<Integer> calculateDimensions(Svg svg, SVGDocument document, Map options) {
        int width
        int height

        if (options.scale) {
            // Use scale factor
            float scale = options.scale as float
            def size = document.size()
            width = (size.width * scale) as int
            height = (size.height * scale) as int
        } else if (options.width || options.height) {
            // Use specified dimensions
            def size = document.size()
            float aspectRatio = size.width / size.height

            if (options.width && options.height) {
                width = options.width as int
                height = options.height as int
            } else if (options.width) {
                width = options.width as int
                height = (width / aspectRatio) as int
            } else {
                height = options.height as int
                width = (height * aspectRatio) as int
            }
        } else {
            // Use SVG's natural size
            def size = document.size()
            width = size.width as int
            height = size.height as int
        }

        return [width, height]
    }

    /**
     * Parses a color string to AWT Color.
     */
    private static Color parseColor(String colorString) {
        if (!colorString) {
            return Color.WHITE
        }

        colorString = colorString.trim().toLowerCase()

        // Handle named colors
        switch (colorString) {
            case 'white': return Color.WHITE
            case 'black': return Color.BLACK
            case 'red': return Color.RED
            case 'green': return Color.GREEN
            case 'blue': return Color.BLUE
            case 'yellow': return Color.YELLOW
            case 'cyan': return Color.CYAN
            case 'magenta': return Color.MAGENTA
            case 'gray':
            case 'grey': return Color.GRAY
            case 'darkgray':
            case 'darkgrey': return Color.DARK_GRAY
            case 'lightgray':
            case 'lightgrey': return Color.LIGHT_GRAY
            case 'orange': return Color.ORANGE
            case 'pink': return Color.PINK
        }

        // Handle hex colors
        if (colorString.startsWith('#')) {
            colorString = colorString.substring(1)
            if (colorString.length() == 3) {
                // Short form #RGB -> #RRGGBB
                colorString = colorString.collect { it + it }.join('')
            }
            if (colorString.length() == 6) {
                try {
                    int rgb = Integer.parseInt(colorString, 16)
                    return new Color(rgb)
                } catch (NumberFormatException e) {
                    // Invalid hex color, fall through to default
                }
            }
        }

        // Default to white
        return Color.WHITE
    }
}
