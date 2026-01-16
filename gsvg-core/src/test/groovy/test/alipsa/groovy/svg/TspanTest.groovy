package test.alipsa.groovy.svg

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.Text
import se.alipsa.groovy.svg.Tspan

class TspanTest {

    @Test
    void testTspans() {

        Svg svg = new Svg(660, 220)
        Text text = svg.addText().x(240).y(120)
        Tspan tspan1 = text.addTspan('SVG 1')
        Tspan tspan2 = text.addTspan('SVG 2')
        tspan2.dx(50,10,10,0,5)
        .dy(50,10,10,10)
        .fill('red')

        assertEquals('<svg xmlns="http://www.w3.org/2000/svg" width="660" height="220">' +
        '<text x="240" y="120">' +
        '<tspan>SVG 1</tspan>' +
        '<tspan dx="50,10,10,0,5" dy="50,10,10,10" fill="red">SVG 2</tspan>' +
        '</text>' +
        '</svg>', svg.toXml())

        assertEquals(Text, tspan1.getParent(Text).class, 'parent of tspan1')
        assertEquals(Text, tspan2.getParent().class, 'parent of tspan2')
    }
}
