package se.alipsa.groovy.svg

/**
 * SVG {@code <title>} element that provides a short title for accessibility.
 */
class Title extends StringContentContainer<Title> {

  static final String NAME = 'title'

  /**
   * Creates a Title.
   *
   * @param parent value
   */
  Title(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

}
