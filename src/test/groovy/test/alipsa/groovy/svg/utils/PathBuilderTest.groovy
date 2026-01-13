package test.alipsa.groovy.svg.utils

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.Path
import se.alipsa.groovy.svg.utils.PathBuilder
import static org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PathBuilderTest {

  @Test
  void testBasicMoveTo() {
    PathBuilder path = PathBuilder.moveTo(10, 20)
    assertEquals('M 10 20', path.toString())
  }

  @Test
  void testMoveToAndLineTo() {
    PathBuilder path = PathBuilder.moveTo(10, 10)
        .lineTo(20, 20)
        .lineTo(30, 10)
    assertEquals('M 10 10 L 20 20 L 30 10', path.toString())
  }

  @Test
  void testClosePath() {
    PathBuilder path = PathBuilder.moveTo(10, 10)
        .lineTo(20, 20)
        .lineTo(30, 10)
        .closePath()
    assertEquals('M 10 10 L 20 20 L 30 10 Z', path.toString())
  }

  @Test
  void testTriangle() {
    PathBuilder path = PathBuilder.moveTo(150, 0)
        .lineTo(75, 200)
        .lineTo(225, 200)
        .Z()
    assertEquals('M 150 0 L 75 200 L 225 200 Z', path.toString())
  }

  @Test
  void testRelativeCommands() {
    PathBuilder path = PathBuilder.moveTo(10, 10)
        .lineToRel(10, 10)  // relative to 10,10 -> 20,20
        .lineToRel(10, -10) // relative to 20,20 -> 30,10
    assertEquals('M 10 10 l 10 10 l 10 -10', path.toString())
  }

  @Test
  void testHorizontalAndVerticalLines() {
    PathBuilder path = PathBuilder.moveTo(10, 10)
        .horizontalTo(50)
        .verticalTo(50)
        .horizontalToRel(-20)
        .verticalToRel(-20)
    assertEquals('M 10 10 H 50 V 50 h -20 v -20', path.toString())
  }

  @Test
  void testCubicBezier() {
    PathBuilder path = PathBuilder.moveTo(10, 10)
        .curveTo(20, 20, 40, 20, 50, 10)
    assertEquals('M 10 10 C 20 20, 40 20, 50 10', path.toString())
  }

  @Test
  void testSmoothCubicBezier() {
    PathBuilder path = PathBuilder.moveTo(10, 80)
        .curveTo(40, 10, 65, 10, 95, 80)
        .smoothCurveTo(150, 150, 180, 80)
    assertEquals('M 10 80 C 40 10, 65 10, 95 80 S 150 150, 180 80', path.toString())
  }

  @Test
  void testQuadraticBezier() {
    PathBuilder path = PathBuilder.moveTo(10, 80)
        .quadTo(50, 10, 90, 80)
    assertEquals('M 10 80 Q 50 10, 90 80', path.toString())
  }

  @Test
  void testSmoothQuadraticBezier() {
    PathBuilder path = PathBuilder.moveTo(10, 80)
        .quadTo(50, 10, 90, 80)
        .smoothQuadTo(130, 80)
    assertEquals('M 10 80 Q 50 10, 90 80 T 130 80', path.toString())
  }

  @Test
  void testEllipticalArc() {
    PathBuilder path = PathBuilder.moveTo(10, 10)
        .arc(30, 30, 0, 0, 1, 40, 40)
    assertEquals('M 10 10 A 30 30 0 0 1 40 40', path.toString())
  }

  @Test
  void testRelativeArc() {
    PathBuilder path = PathBuilder.moveTo(10, 10)
        .arcRel(30, 30, 0, 0, 1, 30, 30)
    assertEquals('M 10 10 a 30 30 0 0 1 30 30', path.toString())
  }

  @Test
  void testSingleLetterCommands() {
    PathBuilder path = PathBuilder.moveTo(10, 10)
        .L(20, 20)
        .H(30)
        .V(30)
        .C(40, 40, 50, 50, 60, 60)
        .S(70, 70, 80, 80)
        .Q(90, 90, 100, 100)
        .T(110, 110)
        .A(10, 10, 0, 0, 1, 120, 120)
        .z()

    assertTrue(path.toString().contains('M 10 10'))
    assertTrue(path.toString().contains('L 20 20'))
    assertTrue(path.toString().contains('Z'))
  }

  @Test
  void testRelativeMoveTo() {
    PathBuilder path = PathBuilder.moveToRel(10, 10)
        .lineToRel(20, 20)
    assertEquals('m 10 10 l 20 20', path.toString())
  }

  @Test
  void testParse() {
    PathBuilder path = PathBuilder.parse('M 10 10 L 20 20')
    assertEquals('M 10 10 L 20 20', path.toString())
  }

  @Test
  void testParseAndExtend() {
    PathBuilder path = PathBuilder.parse('M 10 10 L 20 20')
        .lineTo(30, 30)
        .closePath()
    assertEquals('M 10 10 L 20 20 L 30 30 Z', path.toString())
  }

  @Test
  void testCreateEmpty() {
    PathBuilder path = PathBuilder.create()
    assertTrue(path.isEmpty())

    path.M(10, 10).L(20, 20)
    assertFalse(path.isEmpty())
  }

  @Test
  void testGetCommands() {
    PathBuilder path = PathBuilder.moveTo(10, 10)
        .lineTo(20, 20)
        .closePath()

    List<String> commands = path.getCommands()
    assertEquals(3, commands.size())
    assertEquals('M 10 10', commands[0])
    assertEquals('L 20 20', commands[1])
    assertEquals('Z', commands[2])
  }

  @Test
  void testCopy() {
    PathBuilder original = PathBuilder.moveTo(10, 10).lineTo(20, 20)
    PathBuilder copy = original.copy()

    assertEquals(original.toString(), copy.toString())
    assertNotSame(original, copy)

    // Modify copy and verify original unchanged
    copy.lineTo(30, 30)
    assertNotEquals(original.toString(), copy.toString())
  }

  @Test
  void testEquals() {
    PathBuilder path1 = PathBuilder.moveTo(10, 10).lineTo(20, 20)
    PathBuilder path2 = PathBuilder.moveTo(10, 10).lineTo(20, 20)
    PathBuilder path3 = PathBuilder.moveTo(10, 10).lineTo(30, 30)

    assertEquals(path1, path2)
    assertNotEquals(path1, path3)
  }

  @Test
  void testHashCode() {
    PathBuilder path1 = PathBuilder.moveTo(10, 10).lineTo(20, 20)
    PathBuilder path2 = PathBuilder.moveTo(10, 10).lineTo(20, 20)

    assertEquals(path1.hashCode(), path2.hashCode())
  }

  @Test
  void testIntegrationWithPath() {
    Svg svg = new Svg(200, 200)
    Path path = svg.addPath()
        .d(PathBuilder.moveTo(10, 10)
            .lineTo(20, 20)
            .lineTo(30, 10)
            .closePath())
        .fill('red')
        .stroke('blue')

    assertEquals('M 10 10 L 20 20 L 30 10 Z', path.getD())
    assertTrue(svg.toXml().contains('d="M 10 10 L 20 20 L 30 10 Z"'))
  }

  @Test
  void testBuilderMethod() {
    Svg svg = new Svg(200, 200)
    Path path = svg.addPath()
        .builder(PathBuilder.moveTo(50, 50)
            .arc(25, 25, 0, 0, 1, 100, 50)
            .closePath())

    assertEquals('M 50 50 A 25 25 0 0 1 100 50 Z', path.getD())
  }

  @Test
  void testComplexPath() {
    // Draw a heart shape (simplified)
    PathBuilder heart = PathBuilder.moveTo(75, 40)
        .curveTo(75, 37, 70, 25, 50, 25)
        .curveTo(20, 25, 20, 62.5, 20, 62.5)
        .curveTo(20, 80, 40, 102, 75, 120)
        .curveTo(110, 102, 130, 80, 130, 62.5)
        .curveTo(130, 62.5, 130, 25, 100, 25)
        .curveTo(85, 25, 75, 37, 75, 40)
        .closePath()

    String pathData = heart.toString()
    assertTrue(pathData.startsWith('M 75 40'))
    assertTrue(pathData.contains('C'))
    assertTrue(pathData.endsWith('Z'))
  }

  @Test
  void testAddCommand() {
    PathBuilder path = PathBuilder.create()
        .addCommand('M 10 10')
        .addCommand('L 20 20')
        .addCommand('Z')

    assertEquals('M 10 10 L 20 20 Z', path.toString())
  }

  @Test
  void testParseEmptyString() {
    PathBuilder path = PathBuilder.parse('')
    assertTrue(path.isEmpty())
  }

  @Test
  void testParseWithWhitespace() {
    PathBuilder path = PathBuilder.parse('  M 10 10 L 20 20  ')
    assertEquals('M 10 10 L 20 20', path.toString())
  }

  @Test
  void testNumberTypes() {
    // Test with different number types (int, double, float, long)
    PathBuilder path = PathBuilder.moveTo(10, 20.5)
        .lineTo(30.7, 40)
        .lineTo(50L, 60.0f)

    String result = path.toString()
    assertTrue(result.contains('M 10 20.5'))
    assertTrue(result.contains('L 30.7 40'))
    assertTrue(result.contains('L 50 60'))
  }

  @Test
  void testChaining() {
    // Verify all methods return this for chaining
    PathBuilder path = PathBuilder.moveTo(0, 0)
        .lineTo(10, 10)
        .lineToRel(5, 5)
        .horizontalTo(20)
        .horizontalToRel(5)
        .verticalTo(30)
        .verticalToRel(5)
        .curveTo(40, 40, 50, 50, 60, 60)
        .curveToRel(10, 10, 20, 20, 30, 30)
        .smoothCurveTo(70, 70, 80, 80)
        .smoothCurveToRel(10, 10, 20, 20)
        .quadTo(90, 90, 100, 100)
        .quadToRel(10, 10, 20, 20)
        .smoothQuadTo(110, 110)
        .smoothQuadToRel(10, 10)
        .arc(5, 5, 0, 0, 1, 120, 120)
        .arcRel(5, 5, 0, 0, 1, 10, 10)
        .closePath()

    assertNotNull(path)
    assertFalse(path.isEmpty())
  }
}
