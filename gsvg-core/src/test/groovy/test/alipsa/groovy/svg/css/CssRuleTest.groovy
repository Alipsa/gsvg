package test.alipsa.groovy.svg.css

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.css.CssRule

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for CssRule class.
 */
class CssRuleTest {

    @Test
    void testCreateSimpleRule() {
        CssRule rule = CssRule.of('.highlight', [fill: 'red', stroke: 'black'])

        assertEquals('.highlight', rule.selector)
        assertEquals('red', rule.declarations['fill'])
        assertEquals('black', rule.declarations['stroke'])
        assertEquals(2, rule.declarations.size())
    }

    @Test
    void testToString() {
        CssRule rule = CssRule.of('.highlight', [fill: 'red', stroke: 'black'])
        String css = rule.toString()

        assertTrue(css.contains('.highlight'))
        assertTrue(css.contains('fill: red'))
        assertTrue(css.contains('stroke: black'))
    }

    @Test
    void testEmptyDeclarations() {
        CssRule rule = CssRule.of('#logo', [:])

        assertEquals('#logo', rule.selector)
        assertTrue(rule.declarations.isEmpty())
        assertEquals('#logo { }', rule.toString())
    }

    @Test
    void testGetDeclaration() {
        CssRule rule = CssRule.of('rect', [fill: 'blue', opacity: '0.5'])

        assertEquals('blue', rule.getDeclaration('fill'))
        assertEquals('0.5', rule.getDeclaration('opacity'))
        assertNull(rule.getDeclaration('stroke'))
    }

    @Test
    void testWithDeclaration() {
        CssRule rule = CssRule.of('.test', [fill: 'red'])
        CssRule modified = rule.withDeclaration('stroke', 'black')

        // Original is unchanged (immutable)
        assertEquals(1, rule.declarations.size())
        assertEquals('red', rule.getDeclaration('fill'))
        assertNull(rule.getDeclaration('stroke'))

        // New rule has additional declaration
        assertEquals(2, modified.declarations.size())
        assertEquals('red', modified.getDeclaration('fill'))
        assertEquals('black', modified.getDeclaration('stroke'))
    }

    @Test
    void testWithDeclarationUpdate() {
        CssRule rule = CssRule.of('.test', [fill: 'red', stroke: 'black'])
        CssRule modified = rule.withDeclaration('fill', 'blue')

        // Updated value
        assertEquals('blue', modified.getDeclaration('fill'))
        assertEquals('black', modified.getDeclaration('stroke'))

        // Original unchanged
        assertEquals('red', rule.getDeclaration('fill'))
    }

    @Test
    void testWithoutDeclaration() {
        CssRule rule = CssRule.of('.test', [fill: 'red', stroke: 'black', opacity: '0.8'])
        CssRule modified = rule.withoutDeclaration('stroke')

        // Original unchanged
        assertEquals(3, rule.declarations.size())

        // Modified has one less
        assertEquals(2, modified.declarations.size())
        assertEquals('red', modified.getDeclaration('fill'))
        assertNull(modified.getDeclaration('stroke'))
        assertEquals('0.8', modified.getDeclaration('opacity'))
    }

    @Test
    void testWithoutDeclarationNonExistent() {
        CssRule rule = CssRule.of('.test', [fill: 'red'])
        CssRule modified = rule.withoutDeclaration('stroke')

        // Should return same instance if nothing to remove
        assertSame(rule, modified)
    }

    @Test
    void testParseCssRule() {
        CssRule rule = CssRule.parse('.highlight { fill: red; stroke: black; }')

        assertEquals('.highlight', rule.selector)
        assertEquals('red', rule.getDeclaration('fill'))
        assertEquals('black', rule.getDeclaration('stroke'))
    }

    @Test
    void testParseComplexSelector() {
        CssRule rule = CssRule.parse('rect.active { fill: blue; }')

        assertEquals('rect.active', rule.selector)
        assertEquals('blue', rule.getDeclaration('fill'))
    }

    @Test
    void testParseIdSelector() {
        CssRule rule = CssRule.parse('#logo { transform: scale(2); }')

        assertEquals('#logo', rule.selector)
        assertEquals('scale(2)', rule.getDeclaration('transform'))
    }

    @Test
    void testImmutability() {
        CssRule rule = CssRule.of('.test', [fill: 'red'])

        // Should not be able to modify the map directly
        Map<String, String> declarations = rule.declarations

        CssRule modified = rule.withDeclaration('stroke', 'black')

        // Original should still have only one declaration
        assertEquals(1, rule.declarations.size())
        assertEquals(2, modified.declarations.size())
    }
}
