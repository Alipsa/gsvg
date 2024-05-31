package se.alipsa.groovy.svg

class FeFlood extends  FilterElement<FeFlood> {
  static final String NAME = 'feFlood'

  FeFlood(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeFlood floodColor(String color) {
    addAttribute('flood-color', color)
  }

  String getFloodColor() {
    getAttribute('flood-color')
  }

  FeFlood floodOpacity(Number opacity) {
    addAttribute('flood-opacity', opacity)
  }

  String getFloodOpacity() {
    getAttribute('flood-opacity')
  }
}
