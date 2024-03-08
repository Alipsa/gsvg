package se.alipsa.groovy.svg

import groovy.transform.PackageScope
import org.dom4j.Element;

/**
 * <rect width="${width}" height="${height}" x="${x}" y="${y}" rx="${rx}" ry="${ry}" ${optionalAttr('fill', fill)} ${optionalAttr('style', style)}/>
 */
class Rect extends SvgElement<Rect>  {

  @PackageScope
  Rect(SvgElement parent, Number width, Number height) {
    super(parent.element.addElement('rect'))
    addAttribute('width', String.valueOf(width))
    addAttribute('height', String.valueOf(height))
    x(0)
    y(0)
    rx(0)
    ry(0)
  }

  Rect x(Number x) {
    addAttribute('x', String.valueOf(x))
  }

  Rect y(Number y) {
    addAttribute('y', String.valueOf(y))
  }

  Rect rx(Number rx) {
    addAttribute('rx', String.valueOf(rx))
  }

  Rect ry(Number ry) {
    addAttribute('ry', String.valueOf(ry))
  }

  Rect style(String style) {
    addAttribute('style', String.valueOf(style))
  }

  Rect fill(String fill) {
    addAttribute('fill', String.valueOf(fill))
  }
}
