package test.alipsa.groovy.svg.css

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.css.CssRule
import se.alipsa.groovy.svg.css.CssStyleSheet

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for CssStyleSheet class.
 */
class CssStyleSheetTest {

    @Test
    void testEmptyStyleSheet() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()

        assertTrue(stylesheet.isEmpty())
        assertEquals(0, stylesheet.size())
        assertEquals('', stylesheet.toString())
    }

    @Test
    void testAddRule() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()
            .addRule('.highlight', [fill: 'red', stroke: 'black'])

        assertFalse(stylesheet.isEmpty())
        assertEquals(1, stylesheet.size())

        List<CssRule> rules = stylesheet.getRules()
        assertEquals(1, rules.size())
        assertEquals('.highlight', rules[0].selector)
    }

    @Test
    void testAddMultipleRules() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()
            .addRule('.highlight', [fill: 'red'])
            .addRule('#logo', [transform: 'scale(2)'])
            .addRule('rect', [opacity: '0.8'])

        assertEquals(3, stylesheet.size())

        List<CssRule> rules = stylesheet.getRules()
        assertEquals('.highlight', rules[0].selector)
        assertEquals('#logo', rules[1].selector)
        assertEquals('rect', rules[2].selector)
    }

    @Test
    void testAddRuleObject() {
        CssRule rule = CssRule.of('.test', [fill: 'blue'])
        CssStyleSheet stylesheet = CssStyleSheet.empty().addRule(rule)

        assertEquals(1, stylesheet.size())
        assertEquals('blue', stylesheet.findFirstBySelector('.test').getDeclaration('fill'))
    }

    @Test
    void testAddNullRule() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()
        stylesheet.addRule(null)

        assertTrue(stylesheet.isEmpty())
    }

    @Test
    void testRemoveRule() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()
            .addRule('.highlight', [fill: 'red'])
            .addRule('#logo', [transform: 'scale(2)'])
            .addRule('rect', [opacity: '0.8'])

        assertEquals(3, stylesheet.size())

        stylesheet.removeRule('#logo')

        assertEquals(2, stylesheet.size())
        assertNull(stylesheet.findFirstBySelector('#logo'))
        assertNotNull(stylesheet.findFirstBySelector('.highlight'))
        assertNotNull(stylesheet.findFirstBySelector('rect'))
    }

    @Test
    void testRemoveMultipleSameSelector() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()
            .addRule('.test', [fill: 'red'])
            .addRule('.test', [stroke: 'black'])
            .addRule('#other', [opacity: '0.5'])

        assertEquals(3, stylesheet.size())

        stylesheet.removeRule('.test')

        assertEquals(1, stylesheet.size())
        assertNull(stylesheet.findFirstBySelector('.test'))
    }

    @Test
    void testFindBySelector() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()
            .addRule('.highlight', [fill: 'red'])
            .addRule('.highlight', [stroke: 'black'])
            .addRule('#logo', [transform: 'scale(2)'])

        List<CssRule> highlights = stylesheet.findBySelector('.highlight')
        assertEquals(2, highlights.size())
        assertEquals('.highlight', highlights[0].selector)
        assertEquals('.highlight', highlights[1].selector)

        List<CssRule> logos = stylesheet.findBySelector('#logo')
        assertEquals(1, logos.size())
    }

    @Test
    void testFindFirstBySelector() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()
            .addRule('.highlight', [fill: 'red'])
            .addRule('.highlight', [stroke: 'black'])

        CssRule first = stylesheet.findFirstBySelector('.highlight')
        assertNotNull(first)
        assertEquals('red', first.getDeclaration('fill'))
    }

    @Test
    void testFindNonExistent() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()
            .addRule('.test', [fill: 'red'])

        assertNull(stylesheet.findFirstBySelector('#nonexistent'))
        assertTrue(stylesheet.findBySelector('#nonexistent').isEmpty())
    }

    @Test
    void testClear() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()
            .addRule('.test', [fill: 'red'])
            .addRule('#logo', [stroke: 'black'])

        assertEquals(2, stylesheet.size())

        stylesheet.clear()

        assertTrue(stylesheet.isEmpty())
        assertEquals(0, stylesheet.size())
    }

    @Test
    void testToString() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()
            .addRule('.highlight', [fill: 'red', stroke: 'black'])
            .addRule('#logo', [transform: 'scale(2)'])

        String css = stylesheet.toString()

        assertTrue(css.contains('.highlight'))
        assertTrue(css.contains('fill: red'))
        assertTrue(css.contains('#logo'))
        assertTrue(css.contains('transform: scale(2)'))
    }

    @Test
    void testParse() {
        String cssText = '''
            .highlight { fill: red; stroke: black; }
            #logo { transform: scale(2); }
            rect { opacity: 0.8; }
        '''

        CssStyleSheet stylesheet = CssStyleSheet.parse(cssText)

        assertEquals(3, stylesheet.size())

        CssRule highlight = stylesheet.findFirstBySelector('.highlight')
        assertNotNull(highlight)
        assertEquals('red', highlight.getDeclaration('fill'))
        assertEquals('black', highlight.getDeclaration('stroke'))

        CssRule logo = stylesheet.findFirstBySelector('#logo')
        assertNotNull(logo)
        assertEquals('scale(2)', logo.getDeclaration('transform'))

        CssRule rect = stylesheet.findFirstBySelector('rect')
        assertNotNull(rect)
        assertEquals('0.8', rect.getDeclaration('opacity'))
    }

    @Test
    void testParseEmpty() {
        CssStyleSheet stylesheet = CssStyleSheet.parse('')

        assertTrue(stylesheet.isEmpty())
    }

    @Test
    void testParseWhitespace() {
        CssStyleSheet stylesheet = CssStyleSheet.parse('   \n  \t  ')

        assertTrue(stylesheet.isEmpty())
    }

    @Test
    void testGetRulesIsUnmodifiable() {
        CssStyleSheet stylesheet = CssStyleSheet.empty()
            .addRule('.test', [fill: 'red'])

        List<CssRule> rules = stylesheet.getRules()

        // Should throw UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, {
            rules.add(CssRule.of('.new', [fill: 'blue']))
        })
    }
}
