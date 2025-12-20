package test.alipsa.groovy.svg

import org.dom4j.QName
import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*

class SvgElementSafetyTest {

  @Test
  void removeAttributeIgnoresMissingAttribute() {
    Svg svg = new Svg(10, 10)
    Rect rect = svg.addRect().fill('red')

    rect.removeAttribute('nonExisting')
    assertEquals('red', rect.fill)
  }

  @Test
  void getQNameRequiresPrefixAndNamespace() {
    Svg svg = new Svg()
    assertThrows(IllegalArgumentException) { svg.getQName('noPrefix') }
    assertThrows(IllegalArgumentException) { svg.getQName('foo:bar') }

    svg.addNamespace('foo', 'http://example.com/foo')
    def qName = svg.getQName('foo:bar')
    assertEquals('bar', qName.getName())
    assertEquals('http://example.com/foo', qName.getNamespaceURI())
  }

  @Test
  void xlinkUsesProvidedLocalName() {
    Svg svg = new Svg()
    QName qName = svg.xlink('custom')

    assertEquals('custom', qName.getName())
    assertEquals(svg.xlinkNs, qName.getNamespace())
  }
}
