package se.alipsa.groovy.svg

import org.dom4j.Element
import org.dom4j.Namespace
import org.dom4j.QName

abstract class SvgElement<T extends SvgElement<T>> {

  private List<Animation> animations = []

  Namespace xlinkNs = new Namespace('xlink', 'http://www.w3.org/1999/xlink')
  protected Element element
  SvgElement<? extends SvgElement> parent

  Desc desc
  Title title

  String toXml() {
    element.asXML()
  }

  SvgElement(SvgElement<? extends SvgElement> parent, String name) {
    this.parent = parent
    element = parent.element.addElement(name)
  }

  /**
   * Special constructor for Svg asit does not have a parent
   * Use this only to create Svg elements!
   */
  SvgElement(Element element) {
    this.element = element
  }

  String getAttribute(String name) {
    element.attributeValue(name)
  }

  T addAttribute(String name, Object value) {
    element.addAttribute(name, "$value")
    this as T
  }

  T addAttributes(Map<String, Object> attributes) {
    attributes.each {
      String key = it.key
      if ('strokeWidth' == key) {
        key = 'stroke-width'
      }
      addAttribute(key, it.value)
    }
    this as T
  }

  T changeAttribute(String attribute, Object value) {
    element.attribute(attribute)?.setValue("$value")
    this as T
  }

  T removeAttribute(String attribute) {
    element.remove(element.attribute(attribute))
    this as T
  }

  T style(String style) {
    addAttribute('style', style)
  }

  T styleClass(String styleClass) {
    addAttribute('class', styleClass)
  }

  T id(String id) {
    addAttribute('id', id)
  }

  String getId() {
    getAttribute('id')
  }

  T filter(String filter) {
    addAttribute('filter', filter)
  }

  // TODO: Intellij does not recognize the type
  <P extends SvgElement<P>> P getParent() {
    Class<P> clazz = parent.getClass() as Class<P>
    getParent(clazz)
  }

  <P extends SvgElement<P>> P getParent(Class<P> type) {
    return type.cast(parent)
  }

  String getName() {
    element.getName()
  }

  Namespace getXlinkNs() {
    xlinkNs
  }

  Title addTitle() {
    title = new Title(this)
  }

  Title addTitle(String content) {
    title = new Title(this).addContent(content)
  }

  Title getTitle() {
    title
  }

  Desc addDesc() {
    desc = new Desc(this)
  }

  Desc addDesc(String content) {
    desc = new Desc(this).addContent(content)
  }

  Desc getDesc() {
    desc
  }

  QName xlink(String prefix) {
    new QName('href', xlinkNs)
  }

  Animate addAnimate() {
    Animate animate = new Animate(this)
    animations << animate
    animate
  }

  AnimateMotion addAnimateMotion() {
    AnimateMotion motion = new AnimateMotion(this)
    animations << motion
    motion
  }

  AnimateTransform addAnimateTransform() {
    AnimateTransform transform = new AnimateTransform(this)
    animations << transform
    transform
  }

  List<Animation> getAnimations() {
    animations
  }

  Animation getAnimation() {
    if (animations.size() > 0) {
      animations[0]
    } else {
      null
    }
  }

}
