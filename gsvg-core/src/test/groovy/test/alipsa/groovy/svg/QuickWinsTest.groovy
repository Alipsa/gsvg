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

  @Test
  void testGetAttributeOrDefaultWithExistingAttribute() {
    Svg svg = new Svg(200, 200)
    def rect = svg.addRect(100, 100).x(50).y(75).fill('green')

    assertEquals('50', rect.getAttributeOrDefault('x', '0'))
    assertEquals('75', rect.getAttributeOrDefault('y', '0'))
    assertEquals('green', rect.getAttributeOrDefault('fill', 'black'))
  }

  @Test
  void testGetAttributeOrDefaultWithMissingAttribute() {
    Svg svg = new Svg(200, 200)
    def rect = svg.addRect(100, 100)

    assertEquals('0', rect.getAttributeOrDefault('x', '0'))
    assertEquals('0', rect.getAttributeOrDefault('y', '0'))
    assertEquals('black', rect.getAttributeOrDefault('fill', 'black'))
    assertEquals('none', rect.getAttributeOrDefault('stroke', 'none'))
  }

  @Test
  void testGetAttributeOrDefaultPreventNullPointer() {
    Svg svg = new Svg(200, 200)
    def circle = svg.addCircle()

    // These should not throw NullPointerException
    assertNotNull(circle.getAttributeOrDefault('cx', '50'))
    assertNotNull(circle.getAttributeOrDefault('cy', '50'))
    assertNotNull(circle.getAttributeOrDefault('r', '25'))

    assertEquals('50', circle.getAttributeOrDefault('cx', '50'))
    assertEquals('50', circle.getAttributeOrDefault('cy', '50'))
    assertEquals('25', circle.getAttributeOrDefault('r', '25'))
  }

  @Test
  void testCloneWithMethod() {
    Svg svg = new Svg(200, 200)
    def circle = svg.addCircle().cx(100).cy(100).r(50).fill('red').stroke('blue')

    // Clone with modifications
    def clone = circle.cloneWith(svg, [fill: 'green', r: 75])

    // Original should be unchanged
    assertEquals('red', circle.getFill())
    assertEquals('50', circle.getR())

    // Clone should have modifications
    assertEquals('green', clone.getFill())
    assertEquals('75', clone.getR())
    // Other attributes should be cloned
    assertEquals('100', clone.getCx())
    assertEquals('100', clone.getCy())
    assertEquals('blue', clone.getStroke())
  }
}
