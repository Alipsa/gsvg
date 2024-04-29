package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.A
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.Text

import static org.junit.jupiter.api.Assertions.*

class ATest {

    @Test
    void testSurroundElements() {
        def svgString = '''<svg viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
          <!-- A link around a shape -->
          <a href="/docs/Web/SVG/Element/circle">
            <circle cx="50" cy="40" r="35" />
          </a>
        
          <!-- A link around a text -->
          <a href="/docs/Web/SVG/Element/text">
            <text x="50" y="90" text-anchor="middle">&lt;circle&gt;</text>
          </a>
        </svg>'''

        Svg svg = SvgReader.parse(svgString)
        A a1 = svg[0] as A
        assertEquals('/docs/Web/SVG/Element/circle', a1.getAttribute('href'))
        Circle c = a1[0] as Circle
        assertEquals("40", c.getAttribute('cy'))

        A a2 = svg[1] as A
        assertEquals('/docs/Web/SVG/Element/text', a2.getAttribute('href'))
        Text t = a2[0] as Text
        assertEquals("90", t.getAttribute('y'))
    }
}
