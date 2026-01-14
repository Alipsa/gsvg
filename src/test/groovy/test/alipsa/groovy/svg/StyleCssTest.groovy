package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.Style
import se.alipsa.groovy.svg.css.CssRule
import se.alipsa.groovy.svg.css.CssStyleSheet

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for Style element CSS methods.
 */
class StyleCssTest {

    @Test
    void testAddRule() {
        Svg svg = new Svg(100, 100)
        Style style = svg.addStyle()
            .addRule('.highlight', [fill: 'red', stroke: 'black'])

        String content = style.getContent()
        assertTrue(content.contains('.highlight'))
        assertTrue(content.contains('fill: red'))
        assertTrue(content.contains('stroke: black'))
    }

    @Test
    void testAddMultipleRules() {
        Svg svg = new Svg(100, 100)
        Style style = svg.addStyle()
            .addRule('.highlight', [fill: 'red'])
            .addRule('#logo', [transform: 'scale(2)'])
            .addRule('rect', [opacity: '0.8'])

        String content = style.getContent()
        assertTrue(content.contains('.highlight'))
        assertTrue(content.contains('#logo'))
        assertTrue(content.contains('rect'))
        assertTrue(content.contains('opacity: 0.8'))
    }

    @Test
    void testAddRuleObject() {
        Svg svg = new Svg(100, 100)
        CssRule rule = CssRule.of('.test', [fill: 'blue', stroke: 'green'])
        Style style = svg.addStyle().addRule(rule)

        String content = style.getContent()
        assertTrue(content.contains('.test'))
        assertTrue(content.contains('fill: blue'))
        assertTrue(content.contains('stroke: green'))
    }

    @Test
    void testRuleAlias() {
        Svg svg = new Svg(100, 100)
        Style style = svg.addStyle()
            .rule('.test', [fill: 'red'])

        String content = style.getContent()
        assertTrue(content.contains('.test'))
        assertTrue(content.contains('fill: red'))
    }

    @Test
    void testRemoveRule() {
        Svg svg = new Svg(100, 100)
        Style style = svg.addStyle()
            .addRule('.highlight', [fill: 'red'])
            .addRule('#logo', [transform: 'scale(2)'])
            .addRule('rect', [opacity: '0.8'])

        style.removeRule('#logo')

        String content = style.getContent()
        assertTrue(content.contains('.highlight'))
        assertFalse(content.contains('#logo'))
        assertTrue(content.contains('rect'))
    }

    @Test
    void testGetStyleSheet() {
        Svg svg = new Svg(100, 100)
        Style style = svg.addStyle()
            .addRule('.highlight', [fill: 'red'])
            .addRule('#logo', [transform: 'scale(2)'])

        CssStyleSheet stylesheet = style.getStyleSheet()

        assertEquals(2, stylesheet.size())
        assertNotNull(stylesheet.findFirstBySelector('.highlight'))
        assertNotNull(stylesheet.findFirstBySelector('#logo'))
    }

    @Test
    void testSetStyleSheet() {
        Svg svg = new Svg(100, 100)
        Style style = svg.addStyle()

        CssStyleSheet stylesheet = CssStyleSheet.empty()
            .addRule('.test', [fill: 'blue'])
            .addRule('#logo', [stroke: 'black'])

        style.setStyleSheet(stylesheet)

        String content = style.getContent()
        assertTrue(content.contains('.test'))
        assertTrue(content.contains('#logo'))
    }

    @Test
    void testGetRules() {
        Svg svg = new Svg(100, 100)
        Style style = svg.addStyle()
            .addRule('.highlight', [fill: 'red'])
            .addRule('#logo', [transform: 'scale(2)'])

        List<CssRule> rules = style.getRules()

        assertEquals(2, rules.size())
        assertEquals('.highlight', rules[0].selector)
        assertEquals('#logo', rules[1].selector)
    }

    @Test
    void testBackwardCompatibilityWithStringContent() {
        Svg svg = new Svg(100, 100)
        Style style = svg.addStyle()
            .addContent('.old-style { fill: red; }')

        // Should be able to parse existing content
        CssStyleSheet stylesheet = style.getStyleSheet()
        assertEquals(1, stylesheet.size())

        CssRule rule = stylesheet.findFirstBySelector('.old-style')
        assertNotNull(rule)
        assertEquals('red', rule.getDeclaration('fill'))
    }

    @Test
    void testMixedApiUsage() {
        Svg svg = new Svg(100, 100)
        Style style = svg.addStyle()
            .addContent('.first { fill: red; }')

        // Add rule using new API
        style.addRule('.second', [stroke: 'black'])

        String content = style.getContent()
        assertTrue(content.contains('.first'))
        assertTrue(content.contains('.second'))
    }

    @Test
    void testEmptyStyleElement() {
        Svg svg = new Svg(100, 100)
        Style style = svg.addStyle()

        CssStyleSheet stylesheet = style.getStyleSheet()
        assertTrue(stylesheet.isEmpty())

        List<CssRule> rules = style.getRules()
        assertTrue(rules.isEmpty())
    }

    @Test
    void testSetEmptyStyleSheet() {
        Svg svg = new Svg(100, 100)
        Style style = svg.addStyle()
            .addRule('.test', [fill: 'red'])

        style.setStyleSheet(CssStyleSheet.empty())

        assertEquals('', style.getContent())
    }

    @Test
    void testToXmlWithCssRules() {
        Svg svg = new Svg(100, 100)
        svg.addStyle()
            .type('text/css')
            .addRule('.highlight', [fill: 'red', stroke: 'black'])
            .addRule('#logo', [transform: 'scale(2)'])

        String xml = svg.toXml()

        assertTrue(xml.contains('<style'))
        assertTrue(xml.contains('type="text/css"'))
        assertTrue(xml.contains('.highlight'))
        assertTrue(xml.contains('fill: red'))
        assertTrue(xml.contains('#logo'))
    }
}
