package test.alipsa.groovy.svg

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg

class RectTest {

  @Test
  void testSimpleRect() {
    Svg svg = new Svg(400, 200)
    svg << new Rect(200, 100)
    .setX(100)
    .setY(50)
    .setRx(20)
    .setRy(20)
    .setFill("blue")
    assertIterableEquals(
        ['<svg width="400" height="200" xmlns="http://www.w3.org/2000/svg">',
        '  <rect width="200" height="100" x="100" y="50" rx="20" ry="20" fill="blue" />',
        '</svg>'], svg.toXml().readLines())
  }
}
