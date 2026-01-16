package se.alipsa.groovy.svg.export

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import se.alipsa.groovy.svg.Ellipse
import se.alipsa.groovy.svg.Svg

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import static org.junit.jupiter.api.Assertions.*

class SvgRendererTest {

    @TempDir
    File tempDir

    @Test
    void testToPngFile() {
        Svg svg = new Svg(200, 200)
        svg.addCircle().cx(100).cy(100).r(50).fill('red')

        File output = new File(tempDir, 'test.png')
        SvgRenderer.toPng(svg, output, [width: 400, height: 400])

        assertTrue(output.exists())
        assertTrue(output.length() > 0)

        // Verify it's a valid PNG
        BufferedImage image = ImageIO.read(output)
        assertNotNull(image)
        assertEquals(400, image.width)
        assertEquals(400, image.height)
    }

    @Test
    void testToPngOutputStream() {
        Svg svg = new Svg(200, 200)
        svg.addRect().x(50).y(50).width(100).height(100).fill('blue')

        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        SvgRenderer.toPng(svg, baos, [scale: 2.0])

        assertTrue(baos.size() > 0)

        // Verify it's a valid PNG
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(baos.toByteArray()))
        assertNotNull(image)
        assertEquals(400, image.width)
        assertEquals(400, image.height)
    }

    @Test
    void testToJpegFile() {
        Svg svg = new Svg(200, 200)
        Ellipse ellipse = svg.addEllipse().cx(100).cy(100).fill('green')
        ellipse.addAttribute('rx', '80')
        ellipse.addAttribute('ry', '50')

        File output = new File(tempDir, 'test.jpg')
        SvgRenderer.toJpeg(svg, output, [
                width: 400,
                height: 400,
                backgroundColor: 'white',
                quality: 0.9
        ])

        assertTrue(output.exists())
        assertTrue(output.length() > 0)

        // Verify it's a valid JPEG
        BufferedImage image = ImageIO.read(output)
        assertNotNull(image)
        assertEquals(400, image.width)
        assertEquals(400, image.height)
    }

    @Test
    void testToBufferedImage() {
        Svg svg = new Svg(100, 100)
        svg.addCircle().cx(50).cy(50).r(40).fill('purple')

        BufferedImage image = SvgRenderer.toBufferedImage(svg, [:])

        assertNotNull(image)
        assertTrue(image.width > 0)
        assertTrue(image.height > 0)
        assertEquals(BufferedImage.TYPE_INT_ARGB, image.type)
    }

    @Test
    void testToBufferedImageWithScale() {
        Svg svg = new Svg(100, 100)
        svg.addRect().x(10).y(10).width(80).height(80).fill('orange')

        BufferedImage image = SvgRenderer.toBufferedImage(svg, [scale: 3.0])

        assertNotNull(image)
        assertEquals(300, image.width)
        assertEquals(300, image.height)
    }

    @Test
    void testToBufferedImageWithDimensions() {
        Svg svg = new Svg(200, 100)
        svg.addCircle().cx(100).cy(50).r(40).fill('cyan')

        // Specify only width, height should be calculated
        BufferedImage image1 = SvgRenderer.toBufferedImage(svg, [width: 400])
        assertNotNull(image1)
        assertEquals(400, image1.width)
        assertEquals(200, image1.height)

        // Specify only height, width should be calculated
        BufferedImage image2 = SvgRenderer.toBufferedImage(svg, [height: 300])
        assertNotNull(image2)
        assertEquals(600, image2.width)
        assertEquals(300, image2.height)

        // Specify both
        BufferedImage image3 = SvgRenderer.toBufferedImage(svg, [width: 500, height: 500])
        assertNotNull(image3)
        assertEquals(500, image3.width)
        assertEquals(500, image3.height)
    }

    @Test
    void testAntialiasing() {
        Svg svg = new Svg(100, 100)
        svg.addCircle().cx(50).cy(50).r(40).fill('red')

        BufferedImage imageWithAA = SvgRenderer.toBufferedImage(svg, [antialiasing: true])
        assertNotNull(imageWithAA)

        BufferedImage imageWithoutAA = SvgRenderer.toBufferedImage(svg, [antialiasing: false])
        assertNotNull(imageWithoutAA)
    }

    @Test
    void testComplexSvg() {
        Svg svg = new Svg(300, 300)

        // Add multiple elements
        svg.addRect().x(10).y(10).width(280).height(280).fill('lightgray').stroke('black').strokeWidth(2)
        svg.addCircle().cx(150).cy(150).r(100).fill('none').stroke('blue').strokeWidth(3)
        svg.addCircle().cx(150).cy(150).r(5).fill('red')

        // Add some lines
        for (int i = 0; i < 12; i++) {
            double angle = (i * 30) * Math.PI / 180
            double x2 = 150 + 100 * Math.cos(angle)
            double y2 = 150 + 100 * Math.sin(angle)
            svg.addLine().x1(150).y1(150).x2(x2).y2(y2).stroke('black').strokeWidth(1)
        }

        File output = new File(tempDir, 'complex.png')
        SvgRenderer.toPng(svg, output, [width: 600, height: 600, antialiasing: true])

        assertTrue(output.exists())
        BufferedImage image = ImageIO.read(output)
        assertNotNull(image)
        assertEquals(600, image.width)
        assertEquals(600, image.height)
    }

    @Test
    void testBackgroundColor() {
        Svg svg = new Svg(200, 200)
        svg.addCircle().cx(100).cy(100).r(50).fill('red')

        // Test white background (default for JPEG)
        File output1 = new File(tempDir, 'bg_white.jpg')
        SvgRenderer.toJpeg(svg, output1, [backgroundColor: 'white'])
        assertTrue(output1.exists())

        // Test blue background
        File output2 = new File(tempDir, 'bg_blue.jpg')
        SvgRenderer.toJpeg(svg, output2, [backgroundColor: '#0000FF'])
        assertTrue(output2.exists())

        // Test named color
        File output3 = new File(tempDir, 'bg_yellow.jpg')
        SvgRenderer.toJpeg(svg, output3, [backgroundColor: 'yellow'])
        assertTrue(output3.exists())
    }

    @Test
    void testCreateParentDirectories() {
        Svg svg = new Svg(100, 100)
        svg.addRect().x(10).y(10).width(80).height(80).fill('green')

        File output = new File(tempDir, 'subdir/nested/test.png')
        assertFalse(output.parentFile.exists())

        SvgRenderer.toPng(svg, output, [:])

        assertTrue(output.exists())
        assertTrue(output.parentFile.exists())
    }
}
