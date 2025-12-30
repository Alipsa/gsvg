package se.alipsa.groovy.svg

/**
 * Base class for SVG animation elements that target attributes over time.
 */
abstract class Animation<T extends SvgElement<T>> extends SvgElement<T> {

  /**
   * Creates a Animation.
   *
   * @param parent value
   * @param name value
   */
  Animation(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  /**
   * Sets the repeat count attribute.
   *
   * @param repeats value
   * @return this element for chaining
   */
  T repeatCount(String repeats) {
    addAttribute('repeatCount', repeats)
  }

  String getRepeatCount() {
    getAttribute('repeatCount')
  }
}
