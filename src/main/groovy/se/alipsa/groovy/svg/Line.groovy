package se.alipsa.groovy.svg

import groovy.transform.PackageScope
import org.dom4j.Element;

class Line extends SvgElement {

  /**
   * <line x1="$x1" y1="$y1" x2="$x2" y2="$y2" style="$style" />
   * @param parent the parent dom4j element
   * @param x1 The start of the line on the x-axis
   * @param y1 The start of the line on the y-axis
   * @param x2 The end of the line on the x-axis
   * @param y2 The end of the line on the y-axis
   */
  @PackageScope
  Line(SvgElement parent, Number x1, Number y1, Number x2, Number y2) {
    super(parent.element.addElement('line'))
    addAttribute('x1', String.valueOf(x1))
    addAttribute('y1', String.valueOf(y1))
    addAttribute('x2', String.valueOf(x2))
    addAttribute('y2', String.valueOf(y2))
  }

  Line style(String style) {
    addAttribute('style', style)
  }

}
