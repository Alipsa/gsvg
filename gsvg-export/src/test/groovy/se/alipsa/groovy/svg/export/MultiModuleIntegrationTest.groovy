package se.alipsa.groovy.svg.export

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg

import java.awt.image.BufferedImage

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull

class MultiModuleIntegrationTest {

    @Test
    void testOptimizeThenRender() {
        System.setProperty('java.awt.headless', 'true')

        Svg svg = new Svg(200, 120)
        svg.addRect().x(10).y(10).width(180).height(100).fill('#1976d2')
        svg.addCircle().cx(60).cy(60).r(30).fill('white')

        Svg optimized = SvgOptimizer.optimize(svg, [precision: 2, removeMetadata: false])
        BufferedImage image = SvgRenderer.toBufferedImage(optimized, [width: 400])

        assertNotNull(image)
        assertEquals(400, image.width)
    }
}
