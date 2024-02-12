package test.alipsa.groovy.svg

import static org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Polygon

class PolygonTest {

    @Test
    void testSimplePolygon() {
        Polygon polygon = new Polygon()
        .points([200,10], [250,190], [150,190])
        .style("fill:lime;stroke:purple;stroke-width:3")

        assertEquals('<polygon points="200,10 250,190 150,190" style="fill:lime;stroke:purple;stroke-width:3" />', polygon.toXml())

        assertEquals(
                '<polygon points="200,10 250,190 150,190"  />',
                new Polygon([200,10], [250,190], [150,190]).toXml()
        )
    }
}
