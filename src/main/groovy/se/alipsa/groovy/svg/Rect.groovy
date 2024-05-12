package se.alipsa.groovy.svg

import groovy.transform.PackageScope

/**
 * <rect width="${width}" height="${height}" x="${x}" y="${y}" rx="${rx}" ry="${ry}" ${optionalAttr('fill', fill)} ${optionalAttr('style', style)}/>
 */
class Rect extends SvgElement<Rect>  {

  static final String NAME='rect'

  @PackageScope
  Rect(SvgElement parent) {
    super(parent, NAME)
  }

  @PackageScope
  Rect(SvgElement parent, Number width, Number height) {
    this(parent)
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

  Rect fill(String fill) {
    addAttribute('fill', fill)
  }
}
