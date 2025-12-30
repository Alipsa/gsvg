package se.alipsa.groovy.svg

/**
 * SVG {@code <feDisplacementMap>} filter primitive that displaces pixels using a map input.
 */
class FeDisplacementMap extends FilterElement<FeDisplacementMap> {

  static final String NAME = 'feDisplacementMap'

  /**
   * Creates a FeDisplacementMap.
   *
   * @param parent value
   */
  FeDisplacementMap(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr value
   * @return this element for chaining
   */
  FeDisplacementMap in(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum value
   * @return this element for chaining
   */
  FeDisplacementMap in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }

  /**
   * Sets the in 2 attribute.
   *
   * @param inStr value
   * @return this element for chaining
   */
  FeDisplacementMap in2(String inStr) {
    addAttribute('in2', inStr)
  }

  String getIn2() {
    getAttribute('in2')
  }

  /**
   * Sets the scale attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeDisplacementMap scale(Number value) {
    addAttribute('scale', value)
  }

  String getScale() {
    getAttribute('scale')
  }

  /**
   * Sets the x channel selector attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeDisplacementMap xChannelSelector(String value) {
    addAttribute('xChannelSelector', value)
  }

  String getXchannelSelector() {
    getAttribute('xChannelSelector')
  }

  /**
   * Sets the y channel selector attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeDisplacementMap yChannelSelector(String value) {
    addAttribute('yChannelSelector', value)
  }

  String getYchannelSelector() {
    getAttribute('yChannelSelector')
  }
}
