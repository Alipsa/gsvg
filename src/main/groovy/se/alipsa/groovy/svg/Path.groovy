package se.alipsa.groovy.svg

import groovy.transform.PackageScope
import org.dom4j.Element;

/**
 * <path ${optionalAttr('id', id)} d="$d" ${optionalAttr('style', style)} />
 */
class Path extends SvgElement<Path> {

  static final String NAME='path'

  @PackageScope
  Path(SvgElement parent) {
    super(parent, NAME)
  }

  Path d(String d) {
    addAttribute('d', d)
  }

  String getD() {
    getAttribute('d')
  }

  Path fill(String fill) {
    addAttribute('fill', fill)
  }

  String getFill() {
    getAttribute('fill')
  }

}
