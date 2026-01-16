package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.A
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
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

    @Test
    void testAAttributes() {
        Svg svg = new Svg()
        def a = svg.addA()
          .href('https://example.com')
          .target('_blank')
          .type('text/html')
          .download('file.svg')
          .rel('noopener')

        assertEquals('https://example.com', a.href)
        assertEquals('_blank', a.target)
        assertEquals('text/html', a.type)
        assertEquals('file.svg', a.download)
        assertEquals('noopener', a.rel)
    }

    @Test
    void testAWithCircle() {
        Svg svg = new Svg(100, 100)
        def a = svg.addA().href('#section1')
        a.addCircle().cx(50).cy(50).r(30).fill('red')

        assertTrue(a.toXml().contains('href="#section1"'))
        assertTrue(a.toXml().contains('<circle'))
        assertTrue(a.toXml().contains('cx="50"'))
    }

    @Test
    void testAWithText() {
        Svg svg = new Svg()
        def a = svg.addA().href('/page')
        a.addText('Click me').x(10).y(20).fill('blue')

        assertTrue(a.toXml().contains('href="/page"'))
        assertTrue(a.toXml().contains('Click me'))
    }

    @Test
    void testAWithMapAttributes() {
        Svg svg = new Svg()
        def a = svg.addA([href: 'http://test.com', target: '_self'])

        assertEquals('http://test.com', a.href)
        assertEquals('_self', a.target)
    }

    @Test
    void testAWithMultipleShapes() {
        Svg svg = new Svg()
        def a = svg.addA().href('/gallery')

        a.addRect().x(10).y(10).width(50).height(50)
        a.addCircle().cx(100).cy(100).r(25)
        a.addText('Gallery').x(10).y(75)

        def xml = a.toXml()
        assertTrue(xml.contains('<rect'))
        assertTrue(xml.contains('<circle'))
        assertTrue(xml.contains('<text'))
    }

    @Test
    void testALinkAttributeGetters() {
        Svg svg = new Svg()
        def a = svg.addA()
          .href('https://example.org/file.svg')
          .download('my-file.svg')
          .type('image/svg+xml')
          .rel('noopener noreferrer')

        // Test explicit getters
        assertEquals('my-file.svg', a.getDownload())
        assertEquals('image/svg+xml', a.getType())
        assertEquals('noopener noreferrer', a.getRel())

        // Test property access
        assertEquals('my-file.svg', a.download)
        assertEquals('image/svg+xml', a.type)
        assertEquals('noopener noreferrer', a.rel)

        // Verify in XML
        def xml = a.toXml()
        assertTrue(xml.contains('download="my-file.svg"'))
        assertTrue(xml.contains('type="image/svg+xml"'))
        assertTrue(xml.contains('rel="noopener noreferrer"'))
    }
}
