package se.alipsa.groovy.svg.export

/**
 * Fluent API for building SvgRenderer options maps.
 *
 * <p>Example usage:</p>
 * <pre>
 * def options = RendererOptionsBuilder.create()
 *     .size(600, 400)
 *     .antialiasing(true)
 *     .build()
 *
 * SvgRenderer.toPng(svg, outputFile, options)
 * </pre>
 *
 * @since 1.0.0
 */
class RendererOptionsBuilder {

    private final Map<String, Object> options = [:]

    private RendererOptionsBuilder() {}

    /**
     * Create a new {@link RendererOptionsBuilder} instance.
     * Alias for the builder() method
     */
    static RendererOptionsBuilder create() {
        builder()
    }

    /**
     * Create a new {@link RendererOptionsBuilder} instance.
     */
    static RendererOptionsBuilder builder() {
        new RendererOptionsBuilder()
    }

    RendererOptionsBuilder width(int width) {
        options.width = width
        options.remove('scale')
        this
    }

    RendererOptionsBuilder height(int height) {
        options.height = height
        options.remove('scale')
        this
    }

    RendererOptionsBuilder size(int width, int height) {
        options.width = width
        options.height = height
        options.remove('scale')
        this
    }

    RendererOptionsBuilder scale(Number scale) {
        options.scale = scale
        options.remove('width')
        options.remove('height')
        this
    }

    RendererOptionsBuilder backgroundColor(String color) {
        options.backgroundColor = color
        this
    }

    RendererOptionsBuilder quality(Number quality) {
        options.quality = quality
        this
    }

    RendererOptionsBuilder antialiasing(boolean enabled) {
        options.antialiasing = enabled
        this
    }

    Map<String, Object> build() {
        new LinkedHashMap<>(options)
    }

    /**
     * Alias for build() method
     */
    Map<String, Object> toMap() {
        build()
    }
}
