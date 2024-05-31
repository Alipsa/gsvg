package se.alipsa.groovy.svg

abstract class Animation<T extends SvgElement<T>> extends SvgElement<T> {

  Animation(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  T repeatCount(String repeats) {
    addAttribute('repeatCount', repeats)
  }

  String getRepeatCount() {
    getAttribute('repeatCount')
  }
}
