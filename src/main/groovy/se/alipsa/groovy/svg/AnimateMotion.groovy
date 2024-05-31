package se.alipsa.groovy.svg

class AnimateMotion extends Animation<AnimateMotion> {

  static final String NAME='animateMotion'

  AnimateMotion(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  AnimateMotion path(String path) {
    addAttribute('path', path)
  }

  String getPath() {
    getAttribute('path')
  }

  AnimateMotion dur(String duration) {
    addAttribute('dur', duration)
  }

  String getDur(){
    getAttribute('dur')
  }

  AnimateMotion begin(String time) {
    addAttribute('begin', time)
  }

  String getBegin() {
    getAttribute('begin')
  }
}
