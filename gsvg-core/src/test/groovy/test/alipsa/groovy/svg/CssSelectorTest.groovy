package test.alipsa.groovy.svg

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.*

import static org.junit.jupiter.api.Assertions.*

class CssSelectorTest {

    Svg svg

    @BeforeEach
    void setup() {
        svg = new Svg(400, 400)

        // Create test structure
        svg.addCircle().id('logo').addClass('icon').cx(50).cy(50).r(25).fill('red')
        svg.addCircle().id('avatar').addClass('icon').cx(100).cy(50).r(30).fill('blue')
        svg.addRect().id('background').addClass('layer').x(0).y(0).width(400).height(400).fill('white')
        svg.addRect().id('panel').addClass('layer highlighted').x(10).y(10).width(200).height(200).fill('gray')

        G group1 = svg.addG().id('group1').addClass('container')
        group1.addCircle().id('c1').addClass('highlight').cx(150).cy(150).r(20).fill('red')
        group1.addCircle().id('c2').cx(180).cy(150).r(20).fill('green')
        group1.addRect().id('r1').x(200).y(200).width(50).height(50).fill('red')

        G group2 = svg.addG().id('group2')
        group2.addRect().id('r2').x(300).y(300).width(40).height(40).fill('blue')
        G nestedGroup = group2.addG().id('nested')
        nestedGroup.addCircle().id('nested-circle').cx(320).cy(320).r(10).fill('yellow')
    }

    // ==================== TYPE SELECTORS ====================

    @Test
    void testTypeSelector_Circle() {
        List<SvgElement> circles = svg.css('circle')
        assertEquals(5, circles.size())
    }

    @Test
    void testTypeSelector_Rect() {
        List<SvgElement> rects = svg.css('rect')
        assertEquals(4, rects.size())
    }

    @Test
    void testTypeSelector_G() {
        List<SvgElement> groups = svg.css('g')
        assertEquals(3, groups.size())
    }

    @Test
    void testTypeSelector_Path() {
        List<SvgElement> paths = svg.css('path')
        assertEquals(0, paths.size())
    }

    // ==================== CLASS SELECTORS ====================

    @Test
    void testClassSelector_Icon() {
        List<SvgElement> icons = svg.css('.icon')
        assertEquals(2, icons.size())
        assertTrue(icons.every { it.hasClass('icon') })
    }

    @Test
    void testClassSelector_Layer() {
        List<SvgElement> layers = svg.css('.layer')
        assertEquals(2, layers.size())
        assertTrue(layers.every { it.hasClass('layer') })
    }

    @Test
    void testClassSelector_Highlight() {
        List<SvgElement> highlighted = svg.css('.highlight')
        assertEquals(1, highlighted.size())
        assertEquals('c1', highlighted[0].getId())
    }

    @Test
    void testClassSelector_Highlighted() {
        List<SvgElement> highlighted = svg.css('.highlighted')
        assertEquals(1, highlighted.size())
        assertEquals('panel', highlighted[0].getId())
    }

    @Test
    void testClassSelector_NonExistent() {
        List<SvgElement> none = svg.css('.nonexistent')
        assertEquals(0, none.size())
    }

    // ==================== ID SELECTORS ====================

    @Test
    void testIdSelector_Logo() {
        List<SvgElement> logo = svg.css('#logo')
        assertEquals(1, logo.size())
        assertEquals('logo', logo[0].getId())
    }

    @Test
    void testIdSelector_Background() {
        List<SvgElement> bg = svg.css('#background')
        assertEquals(1, bg.size())
        assertEquals('background', bg[0].getId())
    }

    @Test
    void testIdSelector_NestedCircle() {
        List<SvgElement> nested = svg.css('#nested-circle')
        assertEquals(1, nested.size())
        assertEquals('nested-circle', nested[0].getId())
    }

    @Test
    void testIdSelector_NonExistent() {
        List<SvgElement> none = svg.css('#nonexistent')
        assertEquals(0, none.size())
    }

    // ==================== ATTRIBUTE SELECTORS ====================

    @Test
    void testAttributeSelector_HasAttribute() {
        List<SvgElement> withFill = svg.css('[fill]')
        assertTrue(withFill.size() >= 8) // All elements have fill
    }

    @Test
    void testAttributeSelector_ExactMatch() {
        List<SvgElement> redElements = svg.css('[fill="red"]')
        assertEquals(3, redElements.size())
        assertTrue(redElements.every { it.getAttribute('fill') == 'red' })
    }

    @Test
    void testAttributeSelector_ExactMatch_Blue() {
        List<SvgElement> blueElements = svg.css('[fill="blue"]')
        assertEquals(2, blueElements.size())
        assertTrue(blueElements.every { it.getAttribute('fill') == 'blue' })
    }

    @Test
    void testAttributeSelector_NoQuotes() {
        List<SvgElement> redElements = svg.css('[fill=red]')
        assertEquals(3, redElements.size())
    }

    // ==================== DESCENDANT COMBINATOR ====================

    @Test
    void testDescendantCombinator_GCircle() {
        List<SvgElement> groupCircles = svg.css('g circle')
        // Finds all groups (group1, group2, nested), then finds circle descendants of each
        // group1 descendants: c1, c2
        // group2 descendants: nested-circle
        // nested descendants: nested-circle (duplicate, removed by deduplication)
        // Result: c1, c2, nested-circle (3 unique circles)
        assertEquals(3, groupCircles.size())
        assertTrue(groupCircles.any { it.getId() == 'c1' })
        assertTrue(groupCircles.any { it.getId() == 'c2' })
        assertTrue(groupCircles.any { it.getId() == 'nested-circle' })
    }

    @Test
    void testDescendantCombinator_GRect() {
        List<SvgElement> groupRects = svg.css('g rect')
        assertEquals(2, groupRects.size()) // r1, r2
    }

    @Test
    void testDescendantCombinator_NestedG() {
        List<SvgElement> nestedGroups = svg.css('g g')
        assertEquals(1, nestedGroups.size())
        assertEquals('nested', nestedGroups[0].getId())
    }

    @Test
    void testDescendantCombinator_WithClass() {
        List<SvgElement> containerCircles = svg.css('.container circle')
        assertEquals(2, containerCircles.size()) // c1, c2
    }

    // ==================== CHILD COMBINATOR ====================

    @Test
    void testChildCombinator_DirectChildren() {
        List<SvgElement> directCircles = svg.css('svg > circle')
        assertEquals(2, directCircles.size()) // logo, avatar (direct children)
    }

    @Test
    void testChildCombinator_GCircle() {
        List<SvgElement> groupCircles = svg.css('g > circle')
        assertEquals(3, groupCircles.size()) // c1, c2, nested-circle (all direct children of g)
    }

    @Test
    void testChildCombinator_GG() {
        List<SvgElement> directGroups = svg.css('g > g')
        assertEquals(1, directGroups.size())
        assertEquals('nested', directGroups[0].getId())
    }

    // ==================== PSEUDO-CLASSES ====================

    @Test
    void testPseudoClass_FirstChild() {
        // :first-child matches any element that is the first child of its parent
        // This includes: logo (first of svg), c1 (first of group1), r2 (first of group2), nested-circle (first of nested)
        List<SvgElement> firstChildren = svg.css(':first-child')
        assertTrue(firstChildren.size() >= 1)
        assertTrue(firstChildren.any { it.getId() == 'logo' })
    }

    @Test
    void testPseudoClass_LastChild() {
        // :last-child matches any element that is the last child of its parent
        List<SvgElement> lastChildren = svg.css(':last-child')
        assertTrue(lastChildren.size() >= 1)
        assertTrue(lastChildren.any { it.getId() == 'group2' })
    }

    @Test
    void testPseudoClass_NthChild() {
        // :nth-child(2) matches any element that is the 2nd child of its parent
        List<SvgElement> secondChildren = svg.css(':nth-child(2)')
        assertTrue(secondChildren.size() >= 1)
        assertTrue(secondChildren.any { it.getId() == 'avatar' })
    }

    @Test
    void testPseudoClass_TypeAndFirstChild() {
        // circle:first-child matches circles that are first children
        // This includes: logo (first child of svg and is a circle)
        // and any other circles that are first children of their parents
        List<SvgElement> firstCircles = svg.css('circle:first-child')
        assertTrue(firstCircles.size() >= 1)
        assertTrue(firstCircles.any { it.getId() == 'logo' })
    }

    // ==================== UNIVERSAL SELECTOR ====================

    @Test
    void testUniversalSelector() {
        List<SvgElement> all = svg.css('*')
        assertTrue(all.size() >= 9) // All elements
    }

    // ==================== CSS_FIRST METHOD ====================

    @Test
    void testCssFirst_Circle() {
        SvgElement first = svg.cssFirst('circle')
        assertNotNull(first)
        assertEquals('logo', first.getId())
    }

    @Test
    void testCssFirst_Class() {
        SvgElement first = svg.cssFirst('.layer')
        assertNotNull(first)
        assertEquals('background', first.getId())
    }

    @Test
    void testCssFirst_NonExistent() {
        SvgElement first = svg.cssFirst('.nonexistent')
        assertNull(first)
    }

    // ==================== COMPLEX SELECTORS ====================

    @Test
    void testComplexSelector_RedCircles() {
        List<SvgElement> redCircles = svg.css('circle[fill="red"]')
        assertEquals(2, redCircles.size())
        assertTrue(redCircles.every { it instanceof Circle && it.getAttribute('fill') == 'red' })
    }

    @Test
    void testComplexSelector_HighlightedInContainer() {
        List<SvgElement> results = svg.css('.container .highlight')
        assertEquals(1, results.size())
        assertEquals('c1', results[0].getId())
    }

    // ==================== EDGE CASES ====================

    @Test
    void testEmptySelector() {
        List<SvgElement> results = svg.css('')
        assertEquals(0, results.size())
    }

    @Test
    void testNullSelector() {
        List<SvgElement> results = svg.css(null)
        assertEquals(0, results.size())
    }

    @Test
    void testWhitespaceSelector() {
        List<SvgElement> results = svg.css('   ')
        assertEquals(0, results.size())
    }

    // ==================== NESTED CONTAINER QUERIES ====================

    @Test
    void testNestedContainerQuery() {
        G group1 = svg.descendants(G).find { it.getId() == 'group1' }
        assertNotNull(group1)

        List<SvgElement> circles = group1.css('circle')
        assertEquals(2, circles.size())
    }

    @Test
    void testNestedContainerCssFirst() {
        G group1 = svg.descendants(G).find { it.getId() == 'group1' }
        assertNotNull(group1)

        SvgElement first = group1.cssFirst('circle')
        assertNotNull(first)
        assertEquals('c1', first.getId())
    }

    // ==================== MALFORMED SELECTORS ====================

    @Test
    void testMalformedSelector_UnclosedBracket() {
        // Unclosed bracket should return empty list (graceful handling)
        List<SvgElement> results = svg.css('[fill="red"')
        assertEquals(0, results.size())
    }

    @Test
    void testMalformedSelector_UnclosedParenthesis() {
        // Unclosed parenthesis in pseudo-class should return empty list
        List<SvgElement> results = svg.css(':nth-child(2')
        assertEquals(0, results.size())
    }

    @Test
    void testMalformedSelector_UnclosedBracketInCompound() {
        // Malformed compound selector should return empty list
        List<SvgElement> results = svg.css('circle[fill="red"')
        assertEquals(0, results.size())
    }

    @Test
    void testValidSelector_BracketWithSpaces() {
        // Selector with spaces inside attribute value should work correctly
        // Note: SVG attributes rarely have spaces, but selector should handle it
        svg.addCircle().id('test-spaces').addAttribute('data-value', 'red value')
        List<SvgElement> results = svg.css('[data-value="red value"]')
        assertEquals(1, results.size())
        assertEquals('test-spaces', results[0].getId())
    }

    @Test
    void testValidSelector_AttributeWithWhitespaceAroundEquals() {
        // Attribute selector with spaces around '=' should work
        List<SvgElement> results = svg.css('[fill = "red"]')
        assertEquals(3, results.size())
    }

    @Test
    void testValidSelector_AttributeInBrackets() {
        // Attribute selector with '>' inside brackets should not be treated as combinator
        svg.addRect().id('test-gt').addAttribute('data-count', '>5')
        List<SvgElement> results = svg.css('[data-count=">5"]')
        assertEquals(1, results.size())
        assertEquals('test-gt', results[0].getId())
    }

    // ==================== CHAINED COMBINATORS ====================

    @Test
    void testChainedCombinator_DoubleDescendant() {
        // "g g circle" should find circles in nested groups
        List<SvgElement> results = svg.css('g g circle')
        // Only nested-circle is inside a group that is inside another group
        assertEquals(1, results.size())
        assertEquals('nested-circle', results[0].getId())
    }

    @Test
    void testChainedCombinator_DescendantThenChild() {
        // "svg g > circle" should find circles that are direct children of groups
        List<SvgElement> results = svg.css('svg g > circle')
        // c1, c2 are direct children of group1, nested-circle is direct child of nested group
        assertEquals(3, results.size())
    }

    @Test
    void testChainedCombinator_ChildThenDescendant() {
        // "svg > g circle" should find circles in groups that are direct children of svg
        List<SvgElement> results = svg.css('svg > g circle')
        // group1 and group2 are direct children of svg
        // group1 contains c1, c2
        // group2 contains nested-circle (through nested group)
        assertEquals(3, results.size())
    }

    @Test
    void testChainedCombinator_MultipleChildCombinators() {
        // "g > g > circle" should find circles that are direct children of groups
        // that are direct children of groups
        List<SvgElement> results = svg.css('g > g > circle')
        // nested group is direct child of group2, and nested-circle is direct child of nested
        assertEquals(1, results.size())
        assertEquals('nested-circle', results[0].getId())
    }
}
