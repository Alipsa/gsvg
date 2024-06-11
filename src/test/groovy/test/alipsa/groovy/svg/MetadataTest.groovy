package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Metadata
import se.alipsa.groovy.svg.MetadataElement
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals

class MetadataTest {

  @Test
  void testMetadata() {
    String svgContent = '''<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="100" height="100">
      <circle cx="50" cy="50" r="40" stroke="green" stroke-width="4" fill="yellow"/>
      <metadata>
        <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:dc="http://purl.org/dc/elements/1.1/">
          <rdf:Description about="http://example.org/myfoo" dc:title="Circle" dc:description="A round circle with yellow color" dc:publisher="geek for geeks" dc:date="2022-06-17" dc:format="image/svg+xml" dc:language="en"/>
        </rdf:RDF>
      </metadata>
    </svg>'''.stripIndent().replaceAll(">\\s+<", "><")

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXml(svg))
    checkContent(svg)

    svg = new Svg().xlink().width(100).height(100)
    svg.addCircle().cx(50).cy(50).r(40).stroke('green').strokeWidth(4).fill('yellow')
    def meta = svg.addMetadata()

    def rdf = meta.addElement('RDF', 'rdf', "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
      .addNamespace('rdfs',"http://www.w3.org/2000/01/rdf-schema#")
      .addNamespace('dc', "http://purl.org/dc/elements/1.1/")
    rdf.addElement('rdf:Description')
      .addAttribute('about', "http://example.org/myfoo")
      .addAttribute('dc:title', "Circle")
      .addAttribute('dc:description', "A round circle with yellow color")
      .addAttribute('dc:publisher', "geek for geeks")
      .addAttribute('dc:date', "2022-06-17")
      .addAttribute('dc','format', "image/svg+xml")
      .addAttribute('dc', 'language', 'en')
    assertEquals(svgContent, SvgWriter.toXml(svg))
    checkContent(svg)
  }

  private static void checkContent(Svg svg) {
    Metadata md = svg[1]
    MetadataElement desc = md[0][0]
    assertEquals("http://example.org/myfoo", desc.getAttribute('about'))
    assertEquals("Circle", desc.getAttribute('dc:title'))
    assertEquals("Circle", desc.getAttribute('dc', 'title'))

    assertEquals("image/svg+xml", desc.getAttribute('dc:format'))
    assertEquals("image/svg+xml", desc.getAttribute('dc', 'format'))
  }
}
