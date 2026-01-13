package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.dom4j.Element
import se.alipsa.groovy.svg.utils.PathBuilder

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

  /**
   * Sets the path data from a PathBuilder (fluent API for building paths).
   * <p>Example:</p>
   * <pre>
   * path.d(PathBuilder.moveTo(10, 10)
   *     .lineTo(20, 20)
   *     .lineTo(30, 10)
   *     .closePath())
   * </pre>
   *
   * @param builder the PathBuilder containing the path commands
   * @return this element for chaining
   */
  Path d(PathBuilder builder) {
    addAttribute('d', builder.toString())
  }

  /**
   * Creates a new PathBuilder and sets it as this path's data.
   * This is a convenience method for starting a fluent path building chain.
   * <p>Example:</p>
   * <pre>
   * path.builder(PathBuilder.moveTo(10, 10)
   *     .lineTo(20, 20)
   *     .closePath())
   * </pre>
   *
   * @param builder the PathBuilder to use
   * @return this element for chaining
   */
  Path builder(PathBuilder builder) {
    d(builder)
  }

}
