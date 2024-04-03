package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader

import static org.junit.jupiter.api.Assertions.*

class GTest {

    @Test
    void testSimpleGroup() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100">
          <!-- Using g to inherit presentation attributes -->
          <g fill="white" stroke="green" stroke-width="5">
            <circle cx="40" cy="40" r="25" />
            <circle cx="60" cy="60" r="25" />
          </g>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        G g = svg.children[0] as G
        assertEquals(1, svg.children.size())
        assertEquals('white', g.getAttribute('fill'))

        assertEquals(2, g.children.size())
        Circle c1 = g.children[0] as Circle
        assertEquals('40', c1.getAttribute('cx'))
    }
}
