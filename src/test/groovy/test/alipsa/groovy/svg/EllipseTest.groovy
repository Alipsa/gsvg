package test.alipsa.groovy.svg

import static org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Ellipse

class EllipseTest {

    @Test
    void testSimpleEllipse() {
        def ellipse = new Ellipse(100, 50)
        .cx(200)
        .cy(80)
        .style("fill:yellow;stroke:green;stroke-width:3")

        assertEquals('<ellipse rx="100" ry="50" cx="200" cy="80" style="fill:yellow;stroke:green;stroke-width:3" />',
        ellipse.toXml())
    }
}
