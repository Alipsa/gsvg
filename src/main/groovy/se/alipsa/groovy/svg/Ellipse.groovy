package se.alipsa.groovy.svg

import groovy.transform.PackageScope
import org.dom4j.Element;

/**
 * <ellipse rx="${rx}" ry="${ry}" ${optionalAttr('cx', cx)} ${optionalAttr('cy', cy)} ${optionalAttr('style', style)} />
 */
class Ellipse extends SvgElement<Ellipse>  {

  @PackageScope
  Ellipse(Element parent, Number rx, Number ry) {
    super(parent.addElement('ellipse'))
    addAttribute('rx', "${rx}")
    addAttribute('ry', "${ry}")
  }

  Ellipse cx(Number cx) {
    addAttribute('cx', "${cx}")
  }

  Ellipse cy(Number cy) {
    addAttribute('cy', "${cy}")
  }

  Ellipse style(String style) {
    addAttribute('style', "${style}")
  }
}
