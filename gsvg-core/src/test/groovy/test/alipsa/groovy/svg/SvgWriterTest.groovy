package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
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
}
