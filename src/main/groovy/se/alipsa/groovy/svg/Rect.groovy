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
  Rect(SvgElement parent, Number width, Number height, boolean... addDefaults) {
    this(parent)
    addAttribute('width', width)
    addAttribute('height', height)
    if (addDefaults.length > 0 && addDefaults[0]) {
      x(0)
      y(0)
      rx(0)
      ry(0)
    }
  }

  Rect width(Number width) {
    addAttribute('width', width)
  }

  Rect width(String width) {
    addAttribute('width', width)
  }

  String getWidth() {
    getAttribute('width')
  }

  Rect height(Number height) {
    addAttribute('height', height)
  }

  Rect height(String height) {
    addAttribute('height', height)
  }

  String getHeight() {
    getAttribute('height')
  }

  Rect x(Number x) {
    addAttribute('x', x)
  }

  String getX() {
    getAttribute('x')
  }

  Rect y(Number y) {
    addAttribute('y', y)
  }

  Rect y(String y) {
    addAttribute('y', y)
  }

  String getY() {
    getAttribute('y')
  }

  Rect rx(Number rx) {
    addAttribute('rx', rx)
  }

  String getRx() {
    getAttribute('rx')
  }

  Rect ry(Number ry) {
    addAttribute('ry', ry)
  }

  String getRy() {
    getAttribute('ry')
  }

  Rect fill(String fill) {
    addAttribute('fill', fill)
  }

  String getFill() {
    getAttribute('fill')
  }

  Rect stroke(String stroke) {
    addAttribute('stroke', stroke)
  }

  String getStroke() {
    getAttribute('stroke')
  }
}
