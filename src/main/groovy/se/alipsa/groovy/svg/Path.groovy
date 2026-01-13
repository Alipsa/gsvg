package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.dom4j.Element

/**
 * SVG {@code <path>} element that defines shapes using path data commands.
 */
@CompileStatic
class Path extends AbstractShape<Path> implements Animatable<Path> {

  static final String NAME='path'

  @PackageScope
  Path(SvgElement parent) {
    super(parent, NAME)
  }

  @PackageScope
  Path(SvgElement parent, Element element) {
    super(parent, element)
  }

  /**
   * Sets the path data string (commands and coordinates defining the path geometry).
   *
   * @param d the path data
   * @return this element for chaining
   */
  Path d(String d) {
    addAttribute('d', d)
  }

  /**
   * Returns the path data string.
   *
   * @return the d value
   */
  String getD() {
    getAttribute('d')
  }

}
