package se.alipsa.groovy.svg.export

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class RendererOptionsBuilderTest {

    @Test
    void testSizeClearsScale() {
        Map options = RendererOptionsBuilder.create()
                .scale(2)
                .size(640, 480)
                .build()

        assertEquals(640, options.width)
        assertEquals(480, options.height)
        assertFalse(options.containsKey('scale'))
    }

    @Test
    void testScaleClearsDimensions() {
        Map options = RendererOptionsBuilder.builder()
                .width(100)
                .height(200)
                .scale(3)
                .build()

        assertEquals(3, options.scale)
        assertFalse(options.containsKey('width'))
        assertFalse(options.containsKey('height'))
    }

    @Test
    void testOtherOptions() {
        Map options = RendererOptionsBuilder.create()
                .backgroundColor('white')
                .quality(0.9)
                .antialiasing(false)
                .build()

        assertEquals('white', options.backgroundColor)
        assertEquals(0.9, options.quality)
        assertEquals(false, options.antialiasing)
    }

    @Test
    void testBuildReturnsCopy() {
        RendererOptionsBuilder builder = RendererOptionsBuilder.create()
                .width(100)

        Map options1 = builder.build()
        options1.width = 200
        Map options2 = builder.build()

        assertEquals(100, options2.width)
    }
}
