package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <title>} element that provides a short title for accessibility.
 */
@CompileStatic
class Title extends StringContentContainer<Title> {

  static final String NAME = 'title'

  /**
   * Creates a Title.
   *
   * @param parent the parent SVG element
   */
  Title(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  Title(SvgElement parent, Element element) {
    super(parent, element)
  }


}