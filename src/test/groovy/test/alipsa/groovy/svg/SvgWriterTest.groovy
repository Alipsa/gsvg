package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgWriter
import se.alipsa.groovy.svg.Text

import static org.junit.jupiter.api.Assertions.assertEquals

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
}
