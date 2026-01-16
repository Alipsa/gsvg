package se.alipsa.groovy.svg.export

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.*

import static org.junit.jupiter.api.Assertions.*

class SvgOptimizerTest {

    @Test
    void testOptimizeReturnsNewSvg() {
        Svg original = new Svg(200, 200)
        original.addCircle().cx(100).cy(100).r(50).fill('red')

        Svg optimized = SvgOptimizer.optimize(original, [:])

        assertNotNull(optimized)
        assertNotSame(original, optimized)
    }

    @Test
    void testRemoveMetadata() {
        Svg svg = new Svg(200, 200)
        svg.addTitle().with { it.text = 'My SVG' }
        svg.addDesc().with { it.text = 'Description' }
        svg.addMetadata()
        svg.addCircle().cx(100).cy(100).r(50)

        assertEquals(4, svg.children.size())

        SvgOptimizer.optimizeInPlace(svg, [removeMetadata: true])

        // Should only have the circle left
        assertEquals(1, svg.children.size())
        assertTrue(svg.children[0] instanceof Circle)
    }

    @Test
    void testRemoveInvisibleElements() {
        Svg svg = new Svg(200, 200)
        svg.addCircle().cx(100).cy(100).r(50).fill('red')  // Visible
        Rect rect = svg.addRect().x(10).y(10).width(50).height(50).fill('blue')
        rect.addAttribute('display', 'none')  // Invisible
        Ellipse ellipse = svg.addEllipse().cx(150).cy(150)
        ellipse.addAttribute('rx', '30')
        ellipse.addAttribute('ry', '20')
        ellipse.opacity(0)  // Invisible
        Line line = svg.addLine().x1(0).y1(0).x2(100).y2(100)
        line.addAttribute('visibility', 'hidden')  // Invisible

        assertEquals(4, svg.children.size())

        SvgOptimizer.optimizeInPlace(svg, [removeInvisible: true])

        // Should only have the circle left
        assertEquals(1, svg.children.size())
        assertTrue(svg.children[0] instanceof Circle)
    }

    @Test
    void testRemoveDefaultAttributes() {
        Svg svg = new Svg(200, 200)
        Circle circle = svg.addCircle().cx(100).cy(100).r(50)
        circle.fill('black')  // Default value
        circle.stroke('none')  // Default value
        circle.opacity(1)  // Default value
        circle.strokeWidth(1)  // Default value

        assertEquals('black', circle.getAttribute('fill'))
        assertEquals('none', circle.getAttribute('stroke'))
        assertEquals('1', circle.getAttribute('opacity'))
        assertEquals('1', circle.getAttribute('stroke-width'))

        SvgOptimizer.optimizeInPlace(svg, [removeDefaults: true])

        // Defaults should be removed
        assertNull(circle.getAttribute('fill'))
        assertNull(circle.getAttribute('stroke'))
        assertNull(circle.getAttribute('opacity'))
        assertNull(circle.getAttribute('stroke-width'))
    }

    @Test
    void testShortenColors() {
        Svg svg = new Svg(200, 200)
        Circle circle1 = svg.addCircle().cx(50).cy(50).r(25).fill('#FF0000')  // Can be shortened to #F00
        Circle circle2 = svg.addCircle().cx(100).cy(100).r(25).fill('#123456')  // Cannot be shortened
        Circle circle3 = svg.addCircle().cx(150).cy(150).r(25).fill('#AABBCC')  // Can be shortened to #ABC

        SvgOptimizer.optimizeInPlace(svg, [shortenColors: true])

        assertEquals('#F00', circle1.getAttribute('fill'))
        assertEquals('#123456', circle2.getAttribute('fill'))
        assertEquals('#ABC', circle3.getAttribute('fill'))
    }

    @Test
    void testRoundNumericAttributes() {
        Svg svg = new Svg(200, 200)
        Circle circle = svg.addCircle()
        circle.cx(100.123456)
        circle.cy(100.789012)
        circle.r(50.555555)

        SvgOptimizer.optimizeInPlace(svg, [precision: 2])

        assertEquals('100.12', circle.getAttribute('cx'))
        assertEquals('100.79', circle.getAttribute('cy'))
        assertEquals('50.56', circle.getAttribute('r'))
    }

    @Test
    void testMinifyPathData() {
        Svg svg = new Svg(200, 200)
        Path path = svg.addPath()
        path.d('M 10 , 10   L  100 , 100   L  150 , 50  Z')

        SvgOptimizer.optimizeInPlace(svg, [minifyPathData: true])

        String minified = path.getAttribute('d')
        assertFalse(minified.contains('  '))
        assertFalse(minified.contains(' , '))
    }

    @Test
    void testCollapseRedundantGroups() {
        Svg svg = new Svg(200, 200)

        // Add a group with only one child and no attributes
        G group = svg.addG()
        Circle circle = group.addCircle().cx(100).cy(100).r(50)

        assertEquals(1, svg.children.size())
        assertTrue(svg.children[0] instanceof G)

        SvgOptimizer.optimizeInPlace(svg, [collapseGroups: true])

        // Group should be collapsed, circle should be direct child of svg
        assertEquals(1, svg.children.size())
        assertTrue(svg.children[0] instanceof Circle)
    }

    @Test
    void testDoNotCollapseGroupsWithAttributes() {
        Svg svg = new Svg(200, 200)

        // Add a group with attributes
        G group = svg.addG().fill('red')
        group.addCircle().cx(100).cy(100).r(50)

        SvgOptimizer.optimizeInPlace(svg, [collapseGroups: true])

        // Group should NOT be collapsed because it has attributes
        assertEquals(1, svg.children.size())
        assertTrue(svg.children[0] instanceof G)
    }

    @Test
    void testDoNotCollapseGroupsWithMultipleChildren() {
        Svg svg = new Svg(200, 200)

        // Add a group with multiple children
        G group = svg.addG()
        group.addCircle().cx(50).cy(50).r(25)
        group.addCircle().cx(150).cy(150).r(25)

        SvgOptimizer.optimizeInPlace(svg, [collapseGroups: true])

        // Group should NOT be collapsed because it has multiple children
        assertEquals(1, svg.children.size())
        assertTrue(svg.children[0] instanceof G)
    }

    @Test
    void testRemoveUnusedDefs() {
        Svg svg = new Svg(200, 200)
        Defs defs = svg.addDefs()

        // Add a used gradient
        LinearGradient usedGradient = defs.addLinearGradient().id('usedGradient')
        usedGradient.addStop().offset(0).stopColor('red')
        usedGradient.addStop().offset(1).stopColor('blue')

        // Add an unused gradient
        LinearGradient unusedGradient = defs.addLinearGradient().id('unusedGradient')
        unusedGradient.addStop().offset(0).stopColor('green')
        unusedGradient.addStop().offset(1).stopColor('yellow')

        // Use the first gradient
        svg.addRect().x(10).y(10).width(180).height(180).fill('url(#usedGradient)')

        assertEquals(2, defs.children.size())

        SvgOptimizer.optimizeInPlace(svg, [removeUnusedDefs: true])

        // Only used gradient should remain
        assertEquals(1, defs.children.size())
        assertEquals('usedGradient', (defs.children[0] as LinearGradient).getAttribute('id'))
    }

    @Test
    void testOptimizeWithAllOptions() {
        Svg svg = new Svg(200, 200)
        svg.addTitle().with { it.text = 'Test' }
        svg.addMetadata()

        Circle circle = svg.addCircle()
        circle.cx(100.123456)
        circle.cy(100.789012)
        circle.r(50.5)
        circle.fill('#FF0000')
        circle.stroke('none')
        circle.opacity(1)

        G redundantGroup = svg.addG()
        Rect hiddenRect = redundantGroup.addRect().x(10).y(10).width(50).height(50)
        hiddenRect.addAttribute('display', 'none')

        int originalSize = svg.toXml().length()

        Svg optimized = SvgOptimizer.optimize(svg, [
                removeMetadata: true,
                removeInvisible: true,
                removeDefaults: true,
                shortenColors: true,
                precision: 2,
                collapseGroups: true
        ])

        int optimizedSize = optimized.toXml().length()

        // Optimized should be smaller
        assertTrue(optimizedSize < originalSize)

        // Check specific optimizations
        def circles = optimized.findAll { it instanceof Circle }
        assertEquals(1, circles.size())

        Circle optimizedCircle = circles[0] as Circle
        assertEquals('#F00', optimizedCircle.getAttribute('fill'))
        assertNull(optimizedCircle.getAttribute('stroke'))  // Default removed
        assertNull(optimizedCircle.getAttribute('opacity'))  // Default removed
        assertEquals('100.12', optimizedCircle.getAttribute('cx'))
        assertEquals('100.79', optimizedCircle.getAttribute('cy'))
        assertEquals('50.5', optimizedCircle.getAttribute('r'))
    }

    @Test
    void testOptimizeInPlace() {
        Svg svg = new Svg(200, 200)
        svg.addTitle().with { it.text = 'Test' }
        Circle circle = svg.addCircle().cx(100).cy(100).r(50).fill('black')

        assertEquals(2, svg.children.size())

        SvgOptimizer.optimizeInPlace(svg, [removeMetadata: true, removeDefaults: true])

        // Should modify original
        assertEquals(1, svg.children.size())
        Circle optimizedCircle = svg.children[0] as Circle
        assertNull(optimizedCircle.getAttribute('fill'))  // Default removed
    }
}
