package se.alipsa.groovy.svg

class Animate extends Animation<Animate> {

  static final String NAME='animate'

  Animate(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Animate attributeName(String name) {
    addAttribute('attributeName', name)
  }

  String getAttributeName() {
    getAttribute('attributeName')
  }

  Animate values(String vals) {
    addAttribute('values', vals)
  }

  String getValues() {
    getAttribute('values')
  }

  Animate dur(String duration) {
    addAttribute('dur', duration)
  }

  String getDur(){
    getAttribute('dur')
  }

  Animate begin(String time) {
    addAttribute('begin', time)
  }

  String getBegin() {
    getAttribute('begin')
  }

  Animate from(Number fromNum) {
    addAttribute('from', "$fromNum")
  }

  String getFrom() {
    getAttribute('from')
  }

  Animate to(String to) {
    addAttribute('to', to)
  }

  String getTo() {
    getAttribute('to')
  }

  Animate fill(String fill) {
    addAttribute('fill', "$fill")
  }

  String getFill() {
    getAttribute('fill')
  }
}
