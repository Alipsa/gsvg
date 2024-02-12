package test.alipsa.groovy.svg

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Path

class PathTest {

    @Test
    void testSimplePath() {
        Path path = new Path()
        .d("M150 0 L75 200 L225 200 Z")
        .style("fill:none;stroke:green;stroke-width:3")

        assertEquals(
                '<path  d="M150 0 L75 200 L225 200 Z" style="fill:none;stroke:green;stroke-width:3" />',
            path.toXml()
        )
    }
}
