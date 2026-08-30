package se.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import org.dom4j.DocumentHelper

import static org.junit.jupiter.api.Assertions.assertEquals
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

  @Test
  void adoptingDetachedElementsDoesNotAliasTheSourceElement() {
    def prototype = DocumentHelper.createElement('rect').addAttribute('id', 'prototype')
    Svg target = new Svg()

    Rect copy = new Rect(target, prototype)
    prototype.addAttribute('id', 'changed')

    assertNotSame(prototype, copy.element)
    assertEquals('prototype', copy.id)
    assertEquals('changed', prototype.attributeValue('id'))
  }
}
