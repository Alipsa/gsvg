package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals

class ImageTest {

  @Test
  void testRecursiveImage() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="5cm" height="3cm" viewBox="0 0 50 30">
        <title>Recursive SVG</title>
        <style type="text/css">#target:target { fill: red;}</style>
        <circle id="target" stroke="red" stroke-width="5" fill="none" cx="50%" cy="50%" r="12"/>
        <image xlink:href="#target" x="40%" y="40%" width="20%" height="20%"/>
      </svg>
      '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg('5cm', '3cm').viewBox("0 0 50 30").xlink()
    svg.addTitle('Recursive SVG')
    svg.addStyle().type("text/css").addContent('#target:target { fill: red;}')
    svg.addCircle("target")
        .stroke("red").strokeWidth(5).fill('none')
        .cx('50%').cy('50%').r(12)
    svg.addImage().xlinkHref("#target").x('40%').y('40%').width('20%').height('20%')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testImageAttributes() {
    Svg svg = new Svg()
    def image = svg.addImage()
      .href('image.png')
      .x(10).y(20)
      .width(100).height(50)
      .preserveAspectRatio('xMidYMid meet')
      .crossorigin('anonymous')

    assertEquals('image.png', image.href)
    assertEquals('10', image.x)
    assertEquals('20', image.y)
    assertEquals('100', image.width)
    assertEquals('50', image.height)
    assertEquals('xMidYMid meet', image.preserveAspectRatio)
    assertEquals('anonymous', image.crossorigin)
  }

  @Test
  void testImageWithNumbers() {
    Svg svg = new Svg()
    def image = svg.addImage()
      .xlinkHref('photo.jpg')
      .x(25)
      .y(50)
      .width(200)
      .height(150)

    assertEquals('25', image.x)
    assertEquals('50', image.y)
    assertEquals('200', image.width)
    assertEquals('150', image.height)
  }

  @Test
  void testImageWithMapAttributes() {
    Svg svg = new Svg()
    def image = svg.addImage([href: 'logo.svg', x: '0', y: '0', width: '50', height: '50'])

    assertEquals('logo.svg', image.href)
    assertEquals('0', image.x)
    assertEquals('0', image.y)
    assertEquals('50', image.width)
    assertEquals('50', image.height)
  }

  @Test
  void testImageDataUri() {
    Svg svg = new Svg()
    def dataUri = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA'
    def image = svg.addImage()
      .href(dataUri)
      .x(0).y(0)
      .width(5).height(5)

    assertEquals(dataUri, image.href)
  }
}
