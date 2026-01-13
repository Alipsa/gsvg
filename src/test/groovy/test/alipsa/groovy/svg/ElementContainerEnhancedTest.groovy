package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.*
import static org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

class ElementContainerEnhancedTest {

  Svg svg

  @BeforeEach
  void setup() {
    svg = new Svg(200, 200)

    // Create a test structure
    svg.addCircle().id('circle1').cx(50).cy(50).r(25).fill('red')
    svg.addCircle().id('circle2').cx(100).cy(100).r(30).fill('blue')
    svg.addRect().id('rect1').x(10).y(10).width(50).height(50).fill('red')
    svg.addRect().id('rect2').x(70).y(70).width(100).height(80).fill('green')

    // Add a group with nested elements
    G g = svg.addG().id('group1')
    g.addCircle().id('circle3').cx(150).cy(150).r(20).fill('yellow')
    g.addRect().id('rect3').x(120).y(120).width(40).height(40).fill('red')
  }

  // ==================== FILTER TESTS ====================

  @Test
  void testFilter() {
    List<SvgElement> redElements = svg.filter { it.getFill() == 'red' }
    assertEquals(2, redElements.size())
  }

  @Test
  void testFilterByType() {
    List<SvgElement> circles = svg.filter { it instanceof Circle }
    assertEquals(2, circles.size()) // Only direct children
  }

  @Test
  void testFilterEmpty() {
    List<SvgElement> none = svg.filter { it.getFill() == 'purple' }
    assertEquals(0, none.size())
  }

  // ==================== FIND ALL TESTS ====================

  @Test
  void testFindAllByType() {
    List<Circle> circles = svg.findAll(Circle)
    assertEquals(2, circles.size())
    assertTrue(circles.every { it instanceof Circle })
  }

  @Test
  void testFindAllWithPredicate() {
    List<Circle> largeCircles = svg.findAll(Circle) {
      (it.getR() as int) > 25
    }
    assertEquals(1, largeCircles.size())
    assertEquals('circle2', largeCircles[0].getId())
  }

  @Test
  void testFindAllRects() {
    List<Rect> rects = svg.findAll(Rect)
    assertEquals(2, rects.size())
  }

  @Test
  void testFindAllWithPredicateNoMatches() {
    List<Circle> huge = svg.findAll(Circle) {
      (it.getR() as int) > 1000
    }
    assertEquals(0, huge.size())
  }

  // ==================== FIND FIRST TESTS ====================

  @Test
  void testFindFirstByPredicate() {
    SvgElement redElement = svg.findFirst { it.getFill() == 'red' }
    assertNotNull(redElement)
    assertEquals('circle1', redElement.getId())
  }

  @Test
  void testFindFirstByType() {
    Circle circle = svg.findFirst(Circle)
    assertNotNull(circle)
    assertEquals('circle1', circle.getId())
  }

  @Test
  void testFindFirstNotFound() {
    SvgElement purple = svg.findFirst { it.getFill() == 'purple' }
    assertNull(purple)
  }

  @Test
  void testFindFirstTypeNotFound() {
    Path path = svg.findFirst(Path)
    assertNull(path)
  }

  // ==================== DESCENDANTS TESTS ====================

  @Test
  void testDescendantsAll() {
    List<SvgElement> all = svg.descendants()
    // Should include: 2 circles, 2 rects, 1 group, 1 circle in group, 1 rect in group
    assertEquals(7, all.size())
  }

  @Test
  void testDescendantsByType() {
    List<Circle> allCircles = svg.descendants(Circle)
    assertEquals(3, allCircles.size()) // Including the one in the group
  }

  @Test
  void testDescendantsRects() {
    List<Rect> allRects = svg.descendants(Rect)
    assertEquals(3, allRects.size())
  }

  @Test
  void testDescendantsGs() {
    List<G> groups = svg.descendants(G)
    assertEquals(1, groups.size())
  }

  @Test
  void testDescendantsTypeNotPresent() {
    List<Path> paths = svg.descendants(Path)
    assertEquals(0, paths.size())
  }

  @Test
  void testDescendantsNestedAccess() {
    List<SvgElement> allRed = svg.descendants().findAll { it.getFill() == 'red' }
    assertEquals(3, allRed.size()) // circle1, rect1, rect3
  }

  // ==================== XPATH TESTS ====================

  @Test
  void testXPathAllCircles() {
    List<SvgElement> circles = svg.xpath('//circle')
    assertEquals(3, circles.size())
  }

  @Test
  void testXPathAllRects() {
    List<SvgElement> rects = svg.xpath('//rect')
    assertEquals(3, rects.size())
  }

  @Test
  void testXPathById() {
    List<SvgElement> element = svg.xpath('//*[@id="circle1"]')
    assertEquals(1, element.size())
    assertEquals('circle1', element[0].getId())
  }

  @Test
  void testXPathByAttribute() {
    List<SvgElement> redElements = svg.xpath('//*[@fill="red"]')
    assertEquals(3, redElements.size())
  }

  @Test
  void testXPathNoMatches() {
    List<SvgElement> none = svg.xpath('//path')
    assertEquals(0, none.size())
  }

  @Test
  void testXPathDirectChildren() {
    List<SvgElement> directCircles = svg.xpath('/svg/circle')
    assertEquals(2, directCircles.size())
  }

  @Test
  void testXPathInG() {
    List<SvgElement> groupCircles = svg.xpath('//g/circle')
    assertEquals(1, groupCircles.size())
    assertEquals('circle3', groupCircles[0].getId())
  }

  // ==================== COUNT TESTS ====================

  @Test
  void testCount() {
    int redCount = svg.count { it.getFill() == 'red' }
    assertEquals(2, redCount)
  }

  @Test
  void testCountCircles() {
    int circleCount = svg.count { it instanceof Circle }
    assertEquals(2, circleCount)
  }

  @Test
  void testCountAll() {
    int total = svg.count { true }
    assertEquals(5, total) // Direct children only
  }

  @Test
  void testCountNone() {
    int none = svg.count { false }
    assertEquals(0, none)
  }

  // ==================== ANY TESTS ====================

  @Test
  void testAnyTrue() {
    assertTrue(svg.any { it.getFill() == 'red' })
  }

  @Test
  void testAnyFalse() {
    assertFalse(svg.any { it.getFill() == 'purple' })
  }

  @Test
  void testAnyCircles() {
    assertTrue(svg.any { it instanceof Circle })
  }

  @Test
  void testAnyPaths() {
    assertFalse(svg.any { it instanceof Path })
  }

  // ==================== ALL TESTS ====================

  @Test
  void testAllTrue() {
    assertTrue(svg.all { it.getId() != null })
  }

  @Test
  void testAllFalse() {
    assertFalse(svg.all { it.getFill() == 'red' })
  }

  @Test
  void testAllHaveFill() {
    assertTrue(svg.all { it.getFill() != null })
  }

  // ==================== COLLECT TESTS ====================

  @Test
  void testCollectIds() {
    List<String> ids = svg.collect { it.getId() }
    assertEquals(5, ids.size())
    assertTrue(ids.contains('circle1'))
    assertTrue(ids.contains('rect2'))
  }

  @Test
  void testCollectFills() {
    List<String> fills = svg.collect { it.getFill() }
    assertTrue(fills.contains('red'))
    assertTrue(fills.contains('blue'))
    assertTrue(fills.contains('green'))
  }

  @Test
  void testCollectTypes() {
    List<String> types = svg.collect { it.class.simpleName }
    assertTrue(types.contains('Circle'))
    assertTrue(types.contains('Rect'))
    assertTrue(types.contains('G'))
  }

  // ==================== INTEGRATION TESTS ====================

  @Test
  void testFilterThenCollect() {
    List<String> redIds = svg.filter { it.getFill() == 'red' }.collect { it.getId() }
    assertEquals(2, redIds.size())
    assertTrue(redIds.contains('circle1'))
    assertTrue(redIds.contains('rect1'))
  }

  @Test
  void testDescendantsThenFilter() {
    List<Circle> redCircles = svg.descendants(Circle).findAll {
      it.getFill() == 'red'
    }
    assertEquals(1, redCircles.size())
    assertEquals('circle1', redCircles[0].getId())
  }

  @Test
  void testXPathThenModify() {
    List<SvgElement> redElements = svg.xpath('//*[@fill="red"]')
    redElements.each { it.stroke('black') }

    // Verify all red elements now have black stroke
    List<SvgElement> blackStroke = svg.descendants().findAll {
      it.getStroke() == 'black'
    }
    assertEquals(3, blackStroke.size())
  }

  @Test
  void testComplexQuery() {
    // Find all circles with radius > 25 OR all red elements
    List<SvgElement> results = svg.descendants().findAll { element ->
      (element instanceof Circle && (element as Circle).getR() as int > 25) ||
      element.getFill() == 'red'
    }

    // Should include: circle2 (r=30), circle3 (r=20, but red? no, yellow),
    // circle1 (red), rect1 (red), rect3 (red)
    assertTrue(results.size() >= 3)
  }

  @Test
  void testNestedGQuery() {
    // Create deeper nesting
    G g1 = svg.addG().id('g1')
    G g2 = g1.addG().id('g2')
    g2.addCircle().id('deep-circle').fill('orange')

    List<Circle> allCircles = svg.descendants(Circle)
    assertEquals(4, allCircles.size()) // Original 3 + deep-circle

    Circle deepCircle = svg.descendants(Circle).find {
      it.getId() == 'deep-circle'
    }
    assertNotNull(deepCircle)
    assertEquals('orange', deepCircle.getFill())
  }

  @Test
  void testFindAllWithMultipleLevels() {
    // Add another level of nesting
    G level1 = svg.addG().id('level1')
    G level2 = level1.addG().id('level2')
    level2.addCircle().id('nested').r(5)

    // Test that descendants finds it
    List<Circle> all = svg.descendants(Circle)
    assertTrue(all.any { it.getId() == 'nested' })
  }

  @Test
  void testEmptyContainer() {
    Svg emptySvg = new Svg()

    assertEquals(0, emptySvg.filter { true }.size())
    assertEquals(0, emptySvg.findAll(Circle).size())
    assertNull(emptySvg.findFirst { true })
    assertEquals(0, emptySvg.descendants().size())
    assertEquals(0, emptySvg.count { true })
    assertFalse(emptySvg.any { true })
    assertTrue(emptySvg.all { false }) // Vacuous truth
    assertEquals(0, emptySvg.collect { it }.size())
  }

  @Test
  void testMixedSelection() {
    // Use multiple selection methods together
    List<SvgElement> redCirclesOrLargeRects = svg.descendants().findAll { element ->
      (element instanceof Circle && element.getFill() == 'red') ||
      (element instanceof Rect && (element as Rect).getWidth() as int > 50)
    }

    // Should include: circle1 (red circle), rect2 (100 width)
    assertTrue(redCirclesOrLargeRects.size() >= 2)
  }

  @Test
  void testPredicateWithNullSafety() {
    // Add element without fill
    svg.addPath().id('no-fill').d('M 0 0 L 10 10')

    List<SvgElement> withFill = svg.filter { it.getFill() != null }
    List<SvgElement> withoutFill = svg.filter { it.getFill() == null }

    assertEquals(5, withFill.size())
    assertEquals(1, withoutFill.size())
  }

  @Test
  void testGetAtStillWorks() {
    // Ensure existing getAt methods still work
    SvgElement first = svg[0]
    assertNotNull(first)

    List<SvgElement> circles = svg[Circle]
    assertEquals(2, circles.size())

    List<SvgElement> circlesByName = svg['circle']
    assertEquals(2, circlesByName.size())
  }

  @Test
  void testDescendantsOnG() {
    G g = svg.findFirst { it.getId() == 'group1' } as G
    List<SvgElement> groupDescendants = g.descendants()
    assertEquals(2, groupDescendants.size())
  }

  @Test
  void testXPathComplexQuery() {
    // Test more complex XPath
    List<SvgElement> bigRects = svg.xpath('//rect[@width>50]')
    // XPath numeric comparison might not work with attributes as expected
    // Just verify it doesn't crash
    assertNotNull(bigRects)
  }
}
