package se.alipsa.groovy.svg;

class Svg {
  Number width
  Number height
  String xmlns="http://www.w3.org/2000/svg"
  List<SvgElement> svgElements = []

  Svg(Number width, Number height) {
    this.width = width
    this.height = height
  }

  Svg add(SvgElement element) {
    svgElements << element
    this
  }

  Svg leftShift(SvgElement element) {
    add(element)
  }

  String toXml() {
    def svg = new StringBuilder("""<svg width="${width}" height="${height}" xmlns="${xmlns}">\n  """)
    svgElements.each {
      svg.append(it.toXml()).append('\n')
    }
    svg.append('</svg>')
  }
}
