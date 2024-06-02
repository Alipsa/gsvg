package se.alipsa.groovy.svg

/**
 * Note: Batik support for FeConvolveMatrix is sketchy and not working at all for shapes (only external images)
 */
class FeConvolveMatrix extends FilterElement<FeConvolveMatrix> {

  static final String NAME='feConvolveMatrix'

  enum EdgeMode {
    duplicate, wrap, none
  }

  FeConvolveMatrix(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeConvolveMatrix in(String inStr) {
    addAttribute('in', inStr)
  }

  FeConvolveMatrix in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }

  FeConvolveMatrix order(Integer order) {
    addAttribute('order', order)
  }

  FeConvolveMatrix order(String order) {
    addAttribute('order', order)
  }

  String getOrder() {
    getAttribute('order')
  }

  FeConvolveMatrix kernelMatrix(String values) {
    addAttribute('kernelMatrix', values)
  }

  String getKernelMatrix() {
    getAttribute('kernelMatrix')
  }

  FeConvolveMatrix divisor(Number value) {
    addAttribute('divisor', value)
  }

  String getDivisor() {
    getAttribute('divisor')
  }

  FeConvolveMatrix bias(Number value) {
    addAttribute('bias', value)
  }

  String getBias() {
    getAttribute('bias')
  }

  FeConvolveMatrix targetX(Integer value) {
    addAttribute('targetX', value)
  }

  String getTargetX() {
    getAttribute('targetX')
  }

  FeConvolveMatrix targetY(Integer value) {
    addAttribute('targetY', value)
  }

  String getTargetY() {
    getAttribute('targetY')
  }

  FeConvolveMatrix edgeMode(EdgeMode mode) {
    addAttribute('edgeMode', mode)
  }

  FeConvolveMatrix edgeMode(String mode) {
    addAttribute('edgeMode', mode)
  }

  String getEdgeMode() {
    getAttribute('edgeMode')
  }

  FeConvolveMatrix preserveAlpha(boolean value) {
    addAttribute('preserveAlpha', value ? 'true' : 'false')
  }

  FeConvolveMatrix preserveAlpha(String value) {
    addAttribute('preserveAlpha', value)
  }

  String getPreserveAlpha() {
    getAttribute('preserveAlpha')
  }
}
