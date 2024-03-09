package se.alipsa.groovy.svg

import groovy.transform.PackageScope

class Line extends SvgElement {

  static final String NAME='line'

  @PackageScope
  Line(SvgElement parent) {
    super(parent, NAME)
  }

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
    this(parent)
    addAttribute('x1', String.valueOf(x1))
    addAttribute('y1', String.valueOf(y1))
    addAttribute('x2', String.valueOf(x2))
    addAttribute('y2', String.valueOf(y2))
  }

  Line style(String style) {
    addAttribute('style', style)
  }

}
