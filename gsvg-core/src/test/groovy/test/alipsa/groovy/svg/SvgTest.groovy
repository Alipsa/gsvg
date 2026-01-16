package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals

class SvgTest {

  @Test
  void testSimpleOutput() {
    Svg svg = new Svg(100,100)
    svg.addCircle()
        .cx(50)
        .cy(50)
        .r(40)
        .stroke('green')
        .strokeWidth(4)
        .fill('yellow')

    assertEquals('<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">' +
        '<circle cx="50" cy="50" r="40" stroke="green" stroke-width="4" fill="yellow"/>' +
        '</svg>',
        SvgWriter.toXml(svg)
    )
  }
}
