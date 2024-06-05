package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader

import static org.junit.jupiter.api.Assertions.assertEquals

class ForeignObjectTest {


  @Test
  void testForeignObject() {
    String svgContent =
        '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 200">' +
            '<style>div { color: white; font: 18px serif; height: 100%; overflow: auto; }</style>' +
            '<polygon points="5,5 195,10 185,185 10,195"/>' +
            '<foreignObject x="20" y="20" width="160" height="160">' +
            '<div xmlns="http://www.w3.org/1999/xhtml">Lorem ipsum dolor sit amet,' +
            '<b>consectetur adipiscing elit</b>. Sed mollis mollis mi ut ultricies.' +
            '</div>' +
            '</foreignObject>' +
            '</svg>'

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, svg.toXml())
    svg = new Svg().viewBox("0 0 200 200")
    svg.addStyle().addContent('div { color: white; font: 18px serif; height: 100%; overflow: auto; }')
    svg.addPolygon("5,5 195,10 185,185 10,195")
    def fo = svg.addForeignObject().x(20).y(20).width(160).height(160)
    def div = fo.addElement('div').addContent('Lorem ipsum dolor sit amet,')
    div.addElement('b').addContent('consectetur adipiscing elit')
    div.addContent('. Sed mollis mollis mi ut ultricies.')
    assertEquals(svgContent, svg.toXml())
  }
}
