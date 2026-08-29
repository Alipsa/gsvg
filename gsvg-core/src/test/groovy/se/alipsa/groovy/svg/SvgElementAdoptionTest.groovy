package se.alipsa.groovy.svg

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotSame
import static org.junit.jupiter.api.Assertions.assertTrue

class SvgElementAdoptionTest {

  @Test
  void adoptingDocumentRootCopiesInsteadOfMovingIt() {
    Svg source = new Svg()
    source.addG().id('source-group')
    Svg target = new Svg()

    G copy = new G(target, source.element)

    assertNotSame(source.element, copy.element)
    assertNull(source.element.parent)
    assertTrue(source.toXml().contains('id="source-group"'))
    assertTrue(target.element.elements()[0].asXML().contains('id="source-group"'))
  }
}
