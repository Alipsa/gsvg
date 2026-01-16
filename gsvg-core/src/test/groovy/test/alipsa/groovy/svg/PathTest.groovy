package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Path

class PathTest {

    @Test
    void testSimplePath() {
        Svg svg = new Svg(100,100)
        svg.addPath()
        .d("M150 0 L75 200 L225 200 Z")
        .style("fill:none;stroke:green;stroke-width:3")

        assertEquals('<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">' +
                '<path d="M150 0 L75 200 L225 200 Z" style="fill:none;stroke:green;stroke-width:3"/>' +
            '</svg>',
            svg.toXml()
        )
    }
}
