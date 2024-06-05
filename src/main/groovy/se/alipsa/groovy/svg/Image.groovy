package se.alipsa.groovy.svg

class Image extends SvgElement<Image> {

  static final String NAME='image'

  Image(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Image width(Number width) {
    addAttribute('width', width)
  }

  Image width(String width) {
    addAttribute('width', width)
  }

  String getWidth() {
    getAttribute('width')
  }

  Image height(Number height) {
    addAttribute('height', height)
  }

  Image height(String height) {
    addAttribute('height', height)
  }

  String getHeight() {
    getAttribute('height')
  }

  Image x(Number x) {
    addAttribute('x', x)
  }

  Image x(String x) {
    addAttribute('x', x)
  }

  String getX() {
    getAttribute('x')
  }

  Image y(Number y) {
    addAttribute('y', y)
  }

  Image y(String y) {
    addAttribute('y', y)
  }

  String getY() {
    getAttribute('y')
  }

  Image xlinkHref(String href) {
    addAttribute(xlink('href'), href)
  }

  Image href(String href) {
    addAttribute('href', href)
  }

  String getHref() {
    getAttribute('href')
  }

  /**
   * <align> [<scaleMethod>]
   * align is one of none, xMinYMin, xMidYMin, xMaxYMin, xMinYMid, xMidYMid, xMaxYMid, xMinYMax, xMidYMax, xMaxYMax
   * scaleMethod is an optional argument that have the value "meet" or "slice"
   *
   * @param value the alignment folloowed by an (optional) scaleMethod
   * @return this image
   */
  Image preserveAspectRatio(String value) {
    addAttribute('preserveAspectRatio', value)
  }

  String getPreserveAspectRatio() {
    getAttribute('preserveAspectRatio')
  }

  /**
   *
   * @param value one of "anonymous", "use-credentials", ""
   * @return this image
   */
  Image crossorigin(String value) {
    addAttribute('crossorigin', value)
  }

  String getCrossorigin() {
    getAttribute('crossorigin')
  }

  /**
   *
   * @param value one of "sync", "async", "auto" (default)
   * @return this image
   */
  Image decoding(String value) {
    addAttribute('decoding', value)
  }
}
