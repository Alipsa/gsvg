package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotSame
import static org.junit.jupiter.api.Assertions.assertTrue

class SvgCloneTest {

  @Test
  void testCloneCopiesAttributesAndChildren() {
    Svg svg = new Svg(100, 80)
    svg.viewBox(0, 0, 100, 80)
    def rect = svg.addRect().x(10).y(10).width(20).height(20)

    Svg copy = svg.clone()

    assertNotSame(svg, copy)
    assertEquals(svg.getWidth(), copy.getWidth())
    assertEquals(svg.getHeight(), copy.getHeight())
    assertEquals(svg.getViewBox(), copy.getViewBox())
    assertEquals(1, copy.children.size())
    assertNotSame(rect, copy.children[0])
  }

  @Test
  void testCloneOverridesNumericSize() {
    Svg svg = new Svg(100, 80)
    svg.viewBox(0, 0, 100, 80)

    Svg resized = svg.clone(200, 160)

    assertEquals('200', resized.getWidth())
    assertEquals('160', resized.getHeight())
    assertEquals(svg.getViewBox(), resized.getViewBox())
    assertEquals('100', svg.getWidth())
    assertEquals('80', svg.getHeight())
  }

  @Test
  void testCloneOverridesStringSize() {
    Svg svg = new Svg('100px', '80px')
    svg.viewBox(0, 0, 100, 80)

    Svg resized = svg.clone('480px', '320px')

    assertEquals('480px', resized.getWidth())
    assertEquals('320px', resized.getHeight())
    assertEquals(svg.getViewBox(), resized.getViewBox())
    assertTrue(svg.getWidth().endsWith('px'))
  }
}
