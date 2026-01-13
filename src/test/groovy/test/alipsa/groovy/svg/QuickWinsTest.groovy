package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*

class QuickWinsTest {

  @Test
  void testHasAttribute() {
    Svg svg = new Svg(200, 200)
    def rect = svg.addRect(100, 100).fill('red')

    assertTrue(rect.hasAttribute('fill'))
    assertFalse(rect.hasAttribute('stroke'))
    assertTrue(rect.hasAttribute('width'))
  }

  @Test
  void testAttrsShorthand() {
    Svg svg = new Svg(200, 200)
    def rect = svg.addRect(100, 100).attrs([
      x: 10,
      y: 20,
      fill: 'blue',
      stroke: 'red'
    ])

    assertEquals('10', rect.getX())
    assertEquals('20', rect.getY())
    assertEquals('blue', rect.getFill())
    assertEquals('red', rect.getStroke())
  }
}
