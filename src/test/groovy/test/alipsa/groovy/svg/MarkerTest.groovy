package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Defs
import se.alipsa.groovy.svg.Line
import se.alipsa.groovy.svg.Marker
import se.alipsa.groovy.svg.Path
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElement
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.*

class MarkerTest {

  String svgContent = '''
  <svg xmlns="http://www.w3.org/2000/svg" width="350" height="250">
    <defs>
      <marker id="circle" markerWidth="8" markerHeight="8" refX="5" refY="5">
        <circle cx="5" cy="5" r="3" fill="black"/>
      </marker>
      <marker id="arrow" markerWidth="10" markerHeight="10" refX="5" refY="5" orient="auto">
        <path d="M 0 0 L 10 5 L 0 10 z" fill="black"/>
      </marker>
    </defs>
    <line x1="10" y1="10" x2="300" y2="200" stroke="red" stroke-width="3" marker-start="url(#circle)" marker-end="url(#arrow)"/>
  </svg>
  '''.stripIndent()

  @Test
  void testBuildMarkerFromCode() {
    Svg svg = new Svg(350, 250)
    Defs defs = svg.addDefs()
    Marker marker = defs.addMarker('circle')
      .markerWidth(8)
      .markerHeight(8)
      .refX(5)
      .refY(5)
    marker.addCircle()
      .cx(5)
      .cy(5)
      .r(3)
      .fill('black')
    defs.addMarker('arrow')
      .markerWidth(10)
      .markerHeight(10)
      .refX(5)
      .refY(5)
      .addAttribute('orient', 'auto')
    .addPath().d('M 0 0 L 10 5 L 0 10 z').fill('black')

    svg.addLine(10, 10, 300, 200)
      .stroke('red')
      .strokeWidth(3)
      .markerStart('url(#circle)')
    .markerEnd('url(#arrow)')

    checkAttributes(svg)

    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testParseMarker() {
    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkAttributes(svg)
  }

  void checkAttributes(Svg svg) {
    Defs defs = svg[Defs.NAME][0]
    assertNotNull(defs)
    List<SvgElement> markers = defs.children
    assertEquals(2, markers.size())
    Marker circleMarker = defs[Marker][0]
    assertNotNull(circleMarker)
    assertEquals('circle', circleMarker.getAttribute('id'))
    assertEquals('8', circleMarker.markerWidth)
    assertEquals('8', circleMarker.markerHeight)
    assertEquals('5', circleMarker.refX)
    assertEquals('5', circleMarker.refY)
    Circle c = circleMarker[0]
    assertEquals('5', c.cx)
    assertEquals('5', c.cy)
    assertEquals('3', c.r)
    assertEquals('black', c.fill)

    Marker arrowMarker = defs[1]
    assertEquals('arrow', arrowMarker.id)
    assertEquals('10', arrowMarker.markerWidth)
    assertEquals('10', arrowMarker.markerHeight)
    assertEquals('5', arrowMarker.refX)
    assertEquals('5', arrowMarker.refY)
    assertEquals('auto', arrowMarker.getAttribute('orient'))
    Path path = arrowMarker[0]
    assertEquals('M 0 0 L 10 5 L 0 10 z', path.d)
    assertEquals('black', path.fill)

    Line line = svg[Line][0]
    assertEquals('10', line.x1)
    assertEquals('10', line.y1)
    assertEquals('300', line.x2)
    assertEquals('200', line.y2)
    assertEquals('red', line.stroke)
    assertEquals('3', line.strokeWidth)
    assertEquals('url(#circle)', line.markerStart)
    assertEquals('url(#arrow)', line.markerEnd)
  }
}
