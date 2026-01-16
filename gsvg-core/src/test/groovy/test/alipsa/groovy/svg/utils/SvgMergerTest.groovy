package test.alipsa.groovy.svg.utils

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.utils.SvgMerger

import static org.junit.jupiter.api.Assertions.*

class SvgMergerTest {

  @Test
  void testMergeHorizontallyEmpty() {
    Svg result = SvgMerger.mergeHorizontally()
    assertNotNull(result)
    assertEquals(0, result.children.size())
  }

  @Test
  void testMergeHorizontallySingle() {
    Svg svg = new Svg(100, 100)
    svg.addCircle()

    Svg result = SvgMerger.mergeHorizontally(svg)
    assertSame(svg, result)
  }

  @Test
  void testMergeHorizontallyTwoSvgs() {
    // Create first SVG
    Svg svg1 = new Svg(100, 50)
    svg1.addRect().width(80).height(40).x(10).y(5)

    // Create second SVG
    Svg svg2 = new Svg(150, 60)
    svg2.addCircle().cx(75).cy(30).r(25)

    // Merge horizontally
    Svg result = SvgMerger.mergeHorizontally(svg1, svg2)

    assertNotNull(result)
    assertEquals('250', result.getWidth())  // 100 + 150
    assertEquals('60', result.getHeight())   // max(50, 60)

    // Check that we have 2 groups
    assertEquals(2, result.children.size())
    assertTrue(result.children[0] instanceof G)
    assertTrue(result.children[1] instanceof G)

    // First group should have no transform
    G group1 = result.children[0] as G
    assertNull(group1.getTransform())

    // Second group should have translate(100, 0)
    G group2 = result.children[1] as G
    assertEquals('translate(100, 0)', group2.getTransform())

    // Verify children were copied
    assertEquals(1, group1.children.size())
    assertEquals(1, group2.children.size())
  }

  @Test
  void testMergeVerticallyEmpty() {
    Svg result = SvgMerger.mergeVertically()
    assertNotNull(result)
    assertEquals(0, result.children.size())
  }

  @Test
  void testMergeVerticallySingle() {
    Svg svg = new Svg(100, 100)
    svg.addCircle()

    Svg result = SvgMerger.mergeVertically(svg)
    assertSame(svg, result)
  }

  @Test
  void testMergeVerticallyTwoSvgs() {
    // Create first SVG
    Svg svg1 = new Svg(100, 50)
    svg1.addRect().width(80).height(40).x(10).y(5)

    // Create second SVG
    Svg svg2 = new Svg(150, 60)
    svg2.addCircle().cx(75).cy(30).r(25)

    // Merge vertically
    Svg result = SvgMerger.mergeVertically(svg1, svg2)

    assertNotNull(result)
    assertEquals('150', result.getWidth())   // max(100, 150)
    assertEquals('110', result.getHeight())  // 50 + 60

    // Check that we have 2 groups
    assertEquals(2, result.children.size())
    assertTrue(result.children[0] instanceof G)
    assertTrue(result.children[1] instanceof G)

    // First group should have no transform
    G group1 = result.children[0] as G
    assertNull(group1.getTransform())

    // Second group should have translate(0, 50)
    G group2 = result.children[1] as G
    assertEquals('translate(0, 50)', group2.getTransform())

    // Verify children were copied
    assertEquals(1, group1.children.size())
    assertEquals(1, group2.children.size())
  }

  @Test
  void testMergeHorizontallyWithViewBox() {
    // Create SVG with viewBox but no width/height
    String svgStr1 = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 100"><circle cx="50" cy="50" r="40"/></svg>'
    Svg svg1 = SvgReader.parse(svgStr1)

    String svgStr2 = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 300 150"><rect x="10" y="10" width="100" height="80"/></svg>'
    Svg svg2 = SvgReader.parse(svgStr2)

    // Merge horizontally
    Svg result = SvgMerger.mergeHorizontally(svg1, svg2)

    assertNotNull(result)
    assertEquals('500', result.getWidth())  // 200 + 300
    assertEquals('150', result.getHeight()) // max(100, 150)
  }

  @Test
  void testMergeVerticallyWithViewBox() {
    // Create SVG with viewBox but no width/height
    String svgStr1 = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 100"><circle cx="50" cy="50" r="40"/></svg>'
    Svg svg1 = SvgReader.parse(svgStr1)

    String svgStr2 = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 300 150"><rect x="10" y="10" width="100" height="80"/></svg>'
    Svg svg2 = SvgReader.parse(svgStr2)

    // Merge vertically
    Svg result = SvgMerger.mergeVertically(svg1, svg2)

    assertNotNull(result)
    assertEquals('300', result.getWidth())  // max(200, 300)
    assertEquals('250', result.getHeight()) // 100 + 150
  }

  @Test
  void testMergeHorizontallyWithUnits() {
    Svg svg1 = new Svg('100px', '50px')
    svg1.addCircle()

    Svg svg2 = new Svg('150pt', '60pt')
    svg2.addRect()

    Svg result = SvgMerger.mergeHorizontally(svg1, svg2)

    assertNotNull(result)
    assertEquals('250', result.getWidth())  // Units stripped: 100 + 150
    assertEquals('60', result.getHeight())
  }

  @Test
  void testMergeThreeSvgsHorizontally() {
    Svg svg1 = new Svg(100, 100)
    svg1.addCircle()

    Svg svg2 = new Svg(100, 100)
    svg2.addRect()

    Svg svg3 = new Svg(100, 100)
    svg3.addCircle()

    Svg result = SvgMerger.mergeHorizontally(svg1, svg2, svg3)

    assertNotNull(result)
    assertEquals('300', result.getWidth())
    assertEquals('100', result.getHeight())
    assertEquals(3, result.children.size())

    // Check transforms
    G g1 = result.children[0] as G
    G g2 = result.children[1] as G
    G g3 = result.children[2] as G

    assertNull(g1.getTransform())
    assertEquals('translate(100, 0)', g2.getTransform())
    assertEquals('translate(200, 0)', g3.getTransform())
  }

  @Test
  void testMergeThreeSvgsVertically() {
    Svg svg1 = new Svg(100, 100)
    svg1.addCircle()

    Svg svg2 = new Svg(100, 100)
    svg2.addRect()

    Svg svg3 = new Svg(100, 100)
    svg3.addCircle()

    Svg result = SvgMerger.mergeVertically(svg1, svg2, svg3)

    assertNotNull(result)
    assertEquals('100', result.getWidth())
    assertEquals('300', result.getHeight())
    assertEquals(3, result.children.size())

    // Check transforms
    G g1 = result.children[0] as G
    G g2 = result.children[1] as G
    G g3 = result.children[2] as G

    assertNull(g1.getTransform())
    assertEquals('translate(0, 100)', g2.getTransform())
    assertEquals('translate(0, 200)', g3.getTransform())
  }

  @Test
  void testMergeOnTopEmpty() {
    Svg result = SvgMerger.mergeOnTop()
    assertNotNull(result)
    assertEquals(0, result.children.size())
  }

  @Test
  void testMergeOnTopSingle() {
    Svg svg = new Svg(100, 100)
    svg.addCircle()

    Svg result = SvgMerger.mergeOnTop(svg)
    assertSame(svg, result)
  }

  @Test
  void testMergeOnTopTwoSvgs() {
    // Create first SVG - smaller
    Svg svg1 = new Svg(80, 60)
    svg1.addRect().width(70).height(50).x(5).y(5).fill('blue')

    // Create second SVG - larger
    Svg svg2 = new Svg(100, 100)
    svg2.addCircle().cx(50).cy(50).r(30).fill('red')

    // Merge on top
    Svg result = SvgMerger.mergeOnTop(svg1, svg2)

    assertNotNull(result)
    // Container should be max of both dimensions
    assertEquals('100', result.getWidth())   // max(80, 100)
    assertEquals('100', result.getHeight())  // max(60, 100)

    // Check that we have 2 groups
    assertEquals(2, result.children.size())
    assertTrue(result.children[0] instanceof G)
    assertTrue(result.children[1] instanceof G)

    // Neither group should have transforms (layered on top)
    G group1 = result.children[0] as G
    G group2 = result.children[1] as G
    assertNull(group1.getTransform())
    assertNull(group2.getTransform())

    // Verify children were copied
    assertEquals(1, group1.children.size())
    assertEquals(1, group2.children.size())
  }

  @Test
  void testMergeOnTopThreeSvgs() {
    Svg svg1 = new Svg(100, 100)
    svg1.addRect().fill('blue')

    Svg svg2 = new Svg(80, 120)
    svg2.addCircle().fill('green')

    Svg svg3 = new Svg(120, 80)
    svg3.addCircle().fill('red')

    Svg result = SvgMerger.mergeOnTop(svg1, svg2, svg3)

    assertNotNull(result)
    // Container should be max of all dimensions
    assertEquals('120', result.getWidth())   // max(100, 80, 120)
    assertEquals('120', result.getHeight())  // max(100, 120, 80)
    assertEquals(3, result.children.size())

    // Check that none have transforms
    G g1 = result.children[0] as G
    G g2 = result.children[1] as G
    G g3 = result.children[2] as G

    assertNull(g1.getTransform())
    assertNull(g2.getTransform())
    assertNull(g3.getTransform())
  }

  @Test
  void testMergeOnTopWithViewBox() {
    // Create SVG with viewBox but no width/height
    String svgStr1 = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 100"><circle cx="50" cy="50" r="40"/></svg>'
    Svg svg1 = SvgReader.parse(svgStr1)

    String svgStr2 = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 150 200"><rect x="10" y="10" width="100" height="80"/></svg>'
    Svg svg2 = SvgReader.parse(svgStr2)

    // Merge on top
    Svg result = SvgMerger.mergeOnTop(svg1, svg2)

    assertNotNull(result)
    assertEquals('200', result.getWidth())  // max(200, 150)
    assertEquals('200', result.getHeight()) // max(100, 200)
  }

  @Test
  void testMergeOnTopWithDifferentSizes() {
    Svg svg1 = new Svg('50px', '50px')
    svg1.addCircle()

    Svg svg2 = new Svg('100pt', '75pt')
    svg2.addRect()

    Svg svg3 = new Svg('80', '120')
    svg3.addCircle()

    Svg result = SvgMerger.mergeOnTop(svg1, svg2, svg3)

    assertNotNull(result)
    // Units stripped: max(50, 100, 80) = 100, max(50, 75, 120) = 120
    assertEquals('100', result.getWidth())
    assertEquals('120', result.getHeight())
  }
}
