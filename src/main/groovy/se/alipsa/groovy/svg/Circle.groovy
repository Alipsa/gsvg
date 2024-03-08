package se.alipsa.groovy.svg

import groovy.transform.PackageScope
import org.dom4j.Element;

class Circle extends SvgElement<Circle> {


  @PackageScope
  Circle(SvgElement parent) {
    super(parent.element.addElement('circle'))
  }

  Circle cx(Number cx) {
    addAttribute('cx', "$cx")
  }

  Circle cy(Number cy) {
    addAttribute('cy', "$cy")
  }

  Circle r(Number r) {
    addAttribute('r', "$r")
  }

  Circle stroke(String stroke) {
    addAttribute('stroke', "$stroke")
  }

  Circle strokeWidth(Number strokeWidth) {
    addAttribute('stroke-width', "$strokeWidth")
  }

  Circle fill(String fill) {
    addAttribute('fill', "$fill")
  }

}
