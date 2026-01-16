package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*

class TransformTest {

  @Test
  void testRotate() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100).x(10).y(10).rotate(45)

    assertEquals('rotate(45)', rect.getTransform())
  }

  @Test
  void testRotateWithCenter() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100).x(10).y(10).rotate(45, 50, 50)

    assertEquals('rotate(45 50 50)', rect.getTransform())
  }

  @Test
  void testTranslate() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100).translate(10, 20)

    assertEquals('translate(10 20)', rect.getTransform())
  }

  @Test
  void testTranslateSingleAxis() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100).translate(10)

    assertEquals('translate(10)', rect.getTransform())
  }

  @Test
  void testScale() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100).scale(2, 3)

    assertEquals('scale(2 3)', rect.getTransform())
  }

  @Test
  void testScaleUniform() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100).scale(2)

    assertEquals('scale(2)', rect.getTransform())
  }

  @Test
  void testSkewX() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100).skewX(15)

    assertEquals('skewX(15)', rect.getTransform())
  }

  @Test
  void testSkewY() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100).skewY(10)

    assertEquals('skewY(10)', rect.getTransform())
  }

  @Test
  void testMatrix() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100).matrix(1, 0, 0, 1, 10, 20)

    assertEquals('matrix(1 0 0 1 10 20)', rect.getTransform())
  }

  @Test
  void testTransformChaining() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100).rotate(45).translate(10, 20).scale(2)

    assertEquals('rotate(45) translate(10 20) scale(2)', rect.getTransform())
  }

  @Test
  void testTransformStringApi() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100).transform('rotate(45) translate(10, 20)')

    assertEquals('rotate(45) translate(10, 20)', rect.getTransform())
  }

  @Test
  void testTransformChainingAfterStringApi() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100)
      .transform('rotate(45)')
      .translate(10, 20)

    assertEquals('rotate(45) translate(10 20)', rect.getTransform())
  }
}
