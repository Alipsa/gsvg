package se.alipsa.groovy.svg

import groovy.transform.PackageScope

/**
 * <ellipse rx="${rx}" ry="${ry}" ${optionalAttr('cx', cx)} ${optionalAttr('cy', cy)} ${optionalAttr('style', style)} />
 */
class Ellipse extends SvgElement<Ellipse>  {

  static final String NAME='ellipse'

  @PackageScope
  Ellipse(SvgElement parent) {
    super(parent,NAME)
  }

  @PackageScope
  Ellipse(SvgElement parent, Number rx, Number ry) {
    this(parent)
    addAttribute('rx', "${rx}")
    addAttribute('ry', "${ry}")
  }

  Ellipse cx(Number cx) {
    addAttribute('cx', "${cx}")
  }

  String getCx() {
    getAttribute('cx')
  }

  Ellipse cy(Number cy) {
    addAttribute('cy', "${cy}")
  }

  String getCy() {
    getAttribute('cy')
  }

  Ellipse fill(String fill) {
    addAttribute('fill', fill)
  }

  String getFill() {
    getAttribute('fill')
  }

  String getRx() {
    getAttribute('rx')
  }

  String getRy() {
    getAttribute('ry')
  }
}
