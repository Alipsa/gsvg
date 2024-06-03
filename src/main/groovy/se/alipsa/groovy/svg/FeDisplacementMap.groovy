package se.alipsa.groovy.svg

class FeDisplacementMap extends FilterElement<FeDisplacementMap> {

  static final String NAME = 'feDisplacementMap'

  FeDisplacementMap(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeDisplacementMap in(String inStr) {
    addAttribute('in', inStr)
  }

  FeDisplacementMap in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }

  FeDisplacementMap in2(String inStr) {
    addAttribute('in2', inStr)
  }

  String getIn2() {
    getAttribute('in2')
  }

  FeDisplacementMap scale(Number value) {
    addAttribute('scale', value)
  }

  String getScale() {
    getAttribute('scale')
  }

  FeDisplacementMap xChannelSelector(String value) {
    addAttribute('xChannelSelector', value)
  }

  String getXchannelSelector() {
    getAttribute('xChannelSelector')
  }

  FeDisplacementMap yChannelSelector(String value) {
    addAttribute('yChannelSelector', value)
  }

  String getYchannelSelector() {
    getAttribute('yChannelSelector')
  }
}
