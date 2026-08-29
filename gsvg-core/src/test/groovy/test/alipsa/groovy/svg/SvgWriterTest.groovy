package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue

class SvgWriterTest {

    @Test
    void testToXml() {
        Svg svg = new Svg(300, 130)
        .addRect(200, 100)
            .x(10)
            .y(10)
            .rx(20)
            .ry(20)
            .fill('blue')
        .getParent(Svg)
        .addCircle()
            .r(45)
            .cx(50)
            .cy(50)
            .fill('red')
        .getParent(Svg)
        .addText('I Love ')
            .x(15)
            .y(35)
            .fill('white')
            .fontSize(15)
            .addTspan('SVG')
                .fill('none')
                .stroke('yellow')
            .getParent()
            .addContent('!')
        .getParent(Svg)

        assertEquals('<svg xmlns="http://www.w3.org/2000/svg" width="300" height="130">' +
                '<rect width="200" height="100" x="10" y="10" rx="20" ry="20" fill="blue"/>' +
                '<circle r="45" cx="50" cy="50" fill="red"/>' +
                '<text x="15" y="35" fill="white" font-size="15">I Love ' +
                '<tspan fill="none" stroke="yellow">SVG</tspan>!' +
                '</text>' +
                '</svg>', SvgWriter.toXml(svg))
    }

    @Test
    void testPrettyPrint() {
        Svg svg = new Svg(100,100)
        svg.addCircle().addAttributes(
                cx: 50,
                cy: 50,
                r: 40,
                stroke: 'green',
                strokeWidth: 4,
                fill: 'yellow'
        )

        assertEquals('''
        <svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
          <circle cx="50" cy="50" r="40" stroke="green" stroke-width="4" fill="yellow"/>
        </svg>
        '''.stripIndent(),
                SvgWriter.toXmlPretty(svg)
        )
    }

    @Test
    void namespacesIdsAndReferencesWithoutMutatingTheSource() {
        Svg svg = new Svg()
        svg.addDefs().addClipPath().id('clip').addRect(10, 10)
        svg.addRect(20, 20).addAttribute('clip-path', 'url(#clip)')
        svg.addStyle().addContent('''
          #clip { fill: red; }
          @keyframes fade { from { opacity: 0; } }
          .mark { animation: fade 1s; animation-name: fade; }
        '''.stripIndent())

        String xml = SvgWriter.toXml(svg, 'nb-')

        assertTrue(xml.contains('id="nb-clip"'))
        assertTrue(xml.contains('url(#nb-clip)'))
        assertTrue(xml.contains('#nb-clip {'))
        assertTrue(xml.contains('@keyframes nb-fade'))
        assertTrue(xml.contains('animation: nb-fade 1s'))
        assertFalse(SvgWriter.toXml(svg).contains('nb-clip'))
    }

    @Test
    void namespacesIdsWithoutRewritingColorValues() {
        Svg svg = new Svg()
        svg.addRect(10, 10).id('abc').fill('#abc')
        svg.addStyle().addContent('#abc { fill: #abc; } .shape { fill: url(#abc); }')

        String xml = SvgWriter.toXml(svg, 'nb-')

        assertTrue(xml.contains('id="nb-abc"'))
        assertTrue(xml.contains('#nb-abc {'))
        assertTrue(xml.contains('fill: #abc'))
        assertTrue(xml.contains('url(#nb-abc)'))
        assertTrue(xml.contains('fill="#abc"'))
    }

    @Test
    void namespacedSerializationDoesNotDuplicateNestedElements() {
        Svg svg = new Svg()
        svg.addDefs().addRect(10, 10).id('r')

        String xml = SvgWriter.toXml(svg, 'p-')

        assertEquals(1, xml.count('id="p-r"'))
        assertEquals(1, xml.count('<rect'))
    }

    @Test
    void namespacesKeyframesAcrossShorthandOrdersAndInlineStyles() {
        Svg svg = new Svg()
        svg.addStyle().addContent('@media print { #clip { animation: 1s ease fade; } } @keyframes fade { from { opacity: 0; } }')
        svg.addRect(10, 10).id('clip').addAttribute('style', 'animation: fade 2s')

        String xml = SvgWriter.toXml(svg, 'p-')

        assertTrue(xml.contains('@keyframes p-fade'))
        assertTrue(xml.contains('animation: 1s ease p-fade'))
        assertTrue(xml.contains('animation: p-fade 2s'))
        assertTrue(xml.contains('#p-clip'))
    }

    @Test
    void namespacedSerializationPreservesStyleCdata() {
        Svg svg = SvgReader.parse('<svg xmlns="http://www.w3.org/2000/svg"><style><![CDATA[#clip { fill: url(#clip); }]]></style><rect id="clip"/></svg>')

        String xml = SvgWriter.toXml(svg, 'p-')

        assertTrue(xml.contains('<![CDATA['))
        assertTrue(xml.contains('#p-clip { fill: url(#p-clip); }'), xml)
    }

    @Test
    void namespacingAvoidsExistingPrefixedIdCollisions() {
        Svg svg = new Svg()
        svg.addRect(10, 10).id('x')
        svg.addRect(10, 10).id('merge-0-x')

        String xml = SvgWriter.toXml(svg, 'merge-0-')

        assertTrue(xml.contains('id="merge-0-x-1"'))
        assertEquals(1, xml.count('id="merge-0-x"'))
    }

    @Test
    void clonePreservesTextForeignAndMetadataChildren() {
        Svg svg = SvgReader.parse('''<svg xmlns="http://www.w3.org/2000/svg">
          <text>Hello <tspan>world</tspan>!</text>
          <metadata><rdf xmlns="urn:rdf">metadata</rdf></metadata>
          <foreignObject><div xmlns="http://www.w3.org/1999/xhtml">content</div></foreignObject>
        </svg>''')

        String xml = SvgWriter.toXml(svg.clone())

        assertTrue(xml.contains('<text>Hello <tspan>world</tspan>!</text>'))
        assertTrue(xml.contains('<rdf xmlns="urn:rdf">metadata</rdf>'))
        assertTrue(xml.contains('<div xmlns="http://www.w3.org/1999/xhtml">content</div>'))
    }

    @Test
    void namespacesAllStyleTextNodesAndDoesNotMatchHyphenatedKeyframes() {
        Svg svg = new Svg()
        svg.addRect(10, 10).id('clip').style('animation: my-fade 2s')
        svg.addStyle().addContent('@keyframes fade { from { opacity: 0; } } ')
            .addContent('@keyframes my-fade { from { opacity: 1; } } #clip { animation: fade 1s; }')

        String xml = SvgWriter.toXml(svg, 'p-')

        assertTrue(xml.contains('@keyframes p-fade'))
        assertTrue(xml.contains('@keyframes p-my-fade'))
        assertTrue(xml.contains('animation: p-my-fade 2s'))
        assertFalse(xml.contains('my-p-fade'))
        assertTrue(xml.contains('#p-clip'))
    }
}
