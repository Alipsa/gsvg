package test.alipsa.groovy.svg

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader

class SvgReaderTest {

  @Test
  void testSimpleSvg() {
    String svgString = '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">' +
    '<circle cx="50" cy="50" r="40" stroke="green" stroke-width="4" fill="yellow"/>' +
        '</svg>'

    Svg svg = SvgReader.parse(svgString)
    println(svg.toXml())
    assertEquals(svgString, svg.toXml())
  }

  @Test
  void testTextAndTspan() {
    String svgString = '<svg xmlns="http://www.w3.org/2000/svg" height="40" width="250">' +
        '<text x="5" y="30" fill="red" font-size="35">I Love' +
        '<tspan fill="none" stroke="green">SVG</tspan>!' +
        '</text>' +
        '</svg>'
    Svg svg = SvgReader.parse(svgString)
    println(svg.toXml())
    assertEquals(svgString, svg.toXml())
  }

  @Test
  void testDeeperNesting() {
    String svgString = '<svg xmlns="http://www.w3.org/2000/svg" width="300" height="130">' +
        '<rect width="200" height="100" x="10" y="10" rx="20" ry="20" fill="blue"/>' +
        '<circle r="45" cx="50" cy="50" fill="red"/>' +
        '<text x="15" y="35" fill="white" font-size="15">I Love ' +
        '<tspan fill="none" stroke="yellow">SVG</tspan>!' +
        '</text>' +
        '</svg>'
    Svg svg = SvgReader.parse(svgString)
    println(svg.toXml())
    assertEquals(svgString, svg.toXml())
  }
}
