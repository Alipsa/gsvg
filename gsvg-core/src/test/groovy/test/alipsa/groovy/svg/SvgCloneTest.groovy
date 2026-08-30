package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.MetadataElement
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotSame
import static org.junit.jupiter.api.Assertions.assertTrue

class SvgCloneTest {

  @Test
  void testCloneCopiesAttributesAndChildren() {
    Svg svg = new Svg(100, 80)
    svg.viewBox(0, 0, 100, 80)
    def rect = svg.addRect().x(10).y(10).width(20).height(20)

    Svg copy = svg.clone()

    assertNotSame(svg, copy)
    assertEquals(svg.getWidth(), copy.getWidth())
    assertEquals(svg.getHeight(), copy.getHeight())
    assertEquals(svg.getViewBox(), copy.getViewBox())
    assertEquals(1, copy.children.size())
    assertNotSame(rect, copy.children[0])
  }

  @Test
  void testCloneOverridesNumericSize() {
    Svg svg = new Svg(100, 80)
    svg.viewBox(0, 0, 100, 80)

    Svg resized = svg.clone(200, 160)

    assertEquals('200', resized.getWidth())
    assertEquals('160', resized.getHeight())
    assertEquals(svg.getViewBox(), resized.getViewBox())
    assertEquals('100', svg.getWidth())
    assertEquals('80', svg.getHeight())
  }

  @Test
  void testCloneOverridesStringSize() {
    Svg svg = new Svg('100px', '80px')
    svg.viewBox(0, 0, 100, 80)

    Svg resized = svg.clone('480px', '320px')

    assertEquals('480px', resized.getWidth())
    assertEquals('320px', resized.getHeight())
    assertEquals(svg.getViewBox(), resized.getViewBox())
    assertTrue(svg.getWidth().endsWith('px'))
  }

  @Test
  void testClonePreservesRootLevelComments() {
    Svg svg = new Svg()
    svg.element.addComment('hello')
    svg.addRect(1, 1)

    Svg copy = svg.clone()

    assertTrue(copy.toXml().contains('<!--hello-->'))
  }

  @Test
  void testClonePreservesRootContentOrderAroundComments() {
    Svg svg = new Svg()
    svg.element.addText('   ')
    svg.element.addComment('comment')
    svg.addRect(1, 1)

    assertEquals('<svg xmlns="http://www.w3.org/2000/svg">   <!--comment--><rect width="1" height="1"/></svg>', svg.clone().toXml())
  }

  @Test
  void testCloneSupportsNestedSvgElements() {
    Svg svg = SvgReader.parse('<svg xmlns="http://www.w3.org/2000/svg"><svg id="inner"><rect id="r"/></svg></svg>')

    Svg copy = svg.clone()

    assertTrue(copy.toXml().contains('<svg id="inner"><rect id="r"/></svg>'))
  }

  @Test
  void testClonePreservesRootNamespaceDeclarations() {
    Svg svg = SvgReader.parse('<svg xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape"><use xlink:href="#target"/></svg>')

    Svg copy = svg.clone()
    String xml = copy.toXml()

    assertTrue(xml.contains('xmlns:svg="http://www.w3.org/2000/svg"'), xml)
    assertTrue(xml.contains('xmlns:xlink="http://www.w3.org/1999/xlink"'), xml)
    assertTrue(xml.contains('xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape"'), xml)
  }

  @Test
  void testClonePreservesDocumentLevelComments() {
    Svg svg = SvgReader.parse('<!--generator--><svg xmlns="http://www.w3.org/2000/svg"><rect/></svg>')

    assertTrue(SvgWriter.toXml(svg).startsWith('<!--generator--><svg'))
    String xml = SvgWriter.toXml(svg.clone())
    assertTrue(xml.startsWith('<!--generator--><svg'), xml)
  }

  @Test
  void testCloneSupportsDetachedSvg() {
    Svg root = new Svg()
    Svg inner = root.addSvg()
    inner.addRect(1, 1)
    root.element.remove(inner.element)
    root.children.remove(inner)

    Svg copy = inner.clone()

    assertEquals('<svg xmlns="http://www.w3.org/2000/svg"><rect width="1" height="1"/></svg>', copy.toXml())
  }

  @Test
  void testCloneRetainsMetadataElementWrappersForCollidingNames() {
    Svg svg = new Svg()
    MetadataElement title = svg.addMetadata().addElement('rdf', 'urn:rdf').addElement('title')
    title.addContent('metadata title')

    MetadataElement copy = title.clone(new Svg())

    assertTrue(copy instanceof MetadataElement)
    assertEquals('title', copy.name)
  }
}
