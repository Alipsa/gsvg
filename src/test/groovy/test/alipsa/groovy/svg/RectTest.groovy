package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.SvgWriter

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg

class RectTest {

  @Test
  void testSimpleRect() {
    Svg svg = new Svg(400, 200)
    svg.addRect(200, 100)
    .x(100)
    .y(50)
    .rx(20)
    .ry(20)
    .fill("blue")

    assertIterableEquals(
        ['', '<svg xmlns="http://www.w3.org/2000/svg" width="400" height="200">',
        '  <rect width="200" height="100" x="100" y="50" rx="20" ry="20" fill="blue"/>',
        '</svg>'], SvgWriter.toXmlPretty(svg).readLines())
  }
}
