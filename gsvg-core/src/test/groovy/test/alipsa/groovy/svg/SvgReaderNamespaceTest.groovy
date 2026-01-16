package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.ForeignElement
import se.alipsa.groovy.svg.ForeignObject
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader

import static org.junit.jupiter.api.Assertions.*

class SvgReaderNamespaceTest {

  @Test
  void parsePrefixedAndNestedSvg() {
    String svgContent = '''<svg:svg xmlns:svg="http://www.w3.org/2000/svg" width="10" height="10">
  <svg:svg width="5" height="5">
    <svg:circle cx="2" cy="2" r="1"/>
  </svg:svg>
</svg:svg>'''

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(1, svg.children.size())
    assertTrue(svg.children[0] instanceof Svg)
    Svg inner = svg.children[0] as Svg
    assertEquals(1, inner.children.size())
    assertEquals('circle', inner.children[0].name)
  }

  @Test
  void preserveForeignDefaultNamespace() {
    String svgContent = '''<svg xmlns="http://www.w3.org/2000/svg">
  <foreignObject>
    <math xmlns="http://www.w3.org/1998/Math/MathML">
      <mi>x</mi>
    </math>
  </foreignObject>
</svg>'''

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(1, svg.children.size())
    ForeignObject fo = svg.children[0] as ForeignObject
    assertEquals(1, fo.children.size())
    ForeignElement math = fo.children[0] as ForeignElement
    assertEquals('math', math.name)
    assertEquals('http://www.w3.org/1998/Math/MathML', math.element.getNamespace().getURI())
    ForeignElement mi = math.children[0] as ForeignElement
    assertEquals('mi', mi.name)
    assertEquals('http://www.w3.org/1998/Math/MathML', mi.element.getNamespace().getURI())
  }
}
