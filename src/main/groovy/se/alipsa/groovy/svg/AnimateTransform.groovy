package se.alipsa.groovy.svg

class AnimateTransform extends Animation<AnimateTransform> {

  static final String NAME='animateTransform'

  AnimateTransform(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  AnimateTransform attributeName(String name) {
    addAttribute('attributeName', name)
  }

  String getAttributeName() {
    getAttribute('attributeName')
  }

  AnimateTransform dur(String duration) {
    addAttribute('dur', duration)
  }

  String getDur(){
    getAttribute('dur')
  }

  AnimateTransform begin(String time) {
    addAttribute('begin', time)
  }

  String getBegin() {
    getAttribute('begin')
  }

  AnimateTransform from(Number fromNum) {
    addAttribute('from', "$fromNum")
  }

  AnimateTransform from(String from) {
    addAttribute('from', from)
  }

  String getFrom() {
    getAttribute('from')
  }

  AnimateTransform to(String to) {
    addAttribute('to', to)
  }

  String getTo() {
    getAttribute('to')
  }

  AnimateTransform type(String type) {
    addAttribute('type', type)
  }

  String getType() {
    getAttribute('type')
  }
}
