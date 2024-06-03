package se.alipsa.groovy.svg

class FeTurbulence extends FilterElement<FeTurbulence> {

  static final String NAME = 'feTurbulence'

  FeTurbulence(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeTurbulence type(String type) {
    addAttribute('type', type)
  }

  String getType() {
    getAttribute('type')
  }

  FeTurbulence baseFrequency(Number value) {
    addAttribute('baseFrequency', value)
  }

  String getBaseFrequency() {
    getAttribute('baseFrequency')
  }

  FeTurbulence numOctaves(Number value) {
    addAttribute('numOctaves', value)
  }


}
