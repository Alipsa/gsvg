package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.TextPath
import se.alipsa.groovy.svg.io.SvgReader

import static org.junit.jupiter.api.Assertions.*

class TextPathTest {

  @Test
  void testTextPAth() {
    String svgContent = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100">' +
        '<path id="MyPath" fill="none" stroke="red" d="M10,90 Q90,90 90,45 Q90,10 50,10 Q10,10 10,40 Q10,70 45,70 Q70,70 75,50"/>' +
        '<text>' +
          '<textPath href="#MyPath">Quick brown fox jumps over the lazy dog.</textPath>' +
        '</text>' +
      '</svg>'

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, svg.toXml())

    svg = new Svg().viewBox("0 0 100 100")
    svg.addPath('MyPath').fill('none').stroke('red').d("M10,90 Q90,90 90,45 Q90,10 50,10 Q10,10 10,40 Q10,70 45,70 Q70,70 75,50")
    svg.addText().addTextPath().href('#MyPath').addContent('Quick brown fox jumps over the lazy dog.')
    assertEquals(svgContent, svg.toXml())
  }

  @Test
  void testTextPathHrefGetter() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .href('#myPath')

    assertEquals('#myPath', textPath.href)
    assertEquals('#myPath', textPath.getHref())
    assertTrue(textPath.toXml().contains('href="#myPath"'))
  }

  @Test
  void testTextPathStartOffsetNumber() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .startOffset(50)

    assertEquals('50', textPath.startOffset)
    assertEquals('50', textPath.getStartOffset())
    assertTrue(textPath.toXml().contains('startOffset="50"'))
  }

  @Test
  void testTextPathStartOffsetString() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .startOffset('25%')

    assertEquals('25%', textPath.startOffset)
    assertTrue(textPath.toXml().contains('startOffset="25%"'))
  }

  @Test
  void testTextPathMethod() {
    Svg svg = new Svg()

    // Test 'align'
    TextPath textPath1 = svg.addText().addTextPath()
            .method('align')
    assertEquals('align', textPath1.method)
    assertEquals('align', textPath1.getMethod())

    // Test 'stretch'
    TextPath textPath2 = svg.addText().addTextPath()
            .method('stretch')
    assertEquals('stretch', textPath2.method)
    assertTrue(textPath2.toXml().contains('method="stretch"'))
  }

  @Test
  void testTextPathSpacing() {
    Svg svg = new Svg()

    // Test 'auto'
    TextPath textPath1 = svg.addText().addTextPath()
            .spacing('auto')
    assertEquals('auto', textPath1.spacing)
    assertEquals('auto', textPath1.getSpacing())

    // Test 'exact'
    TextPath textPath2 = svg.addText().addTextPath()
            .spacing('exact')
    assertEquals('exact', textPath2.spacing)
  }

  @Test
  void testTextPathSide() {
    Svg svg = new Svg()

    // Test 'left'
    TextPath textPath1 = svg.addText().addTextPath()
            .side('left')
    assertEquals('left', textPath1.side)
    assertEquals('left', textPath1.getSide())

    // Test 'right'
    TextPath textPath2 = svg.addText().addTextPath()
            .side('right')
    assertEquals('right', textPath2.side)
    assertTrue(textPath2.toXml().contains('side="right"'))
  }

  @Test
  void testTextPathLengthAdjustNumber() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .lengthAdjust(100)

    assertEquals('100', textPath.lengthAdjust)
    assertEquals('100', textPath.getLengthAdjust())
  }

  @Test
  void testTextPathLengthAdjustString() {
    Svg svg = new Svg()

    // Test 'spacing'
    TextPath textPath1 = svg.addText().addTextPath()
            .lengthAdjust('spacing')
    assertEquals('spacing', textPath1.lengthAdjust)

    // Test 'spacingAndGlyphs'
    TextPath textPath2 = svg.addText().addTextPath()
            .lengthAdjust('spacingAndGlyphs')
    assertEquals('spacingAndGlyphs', textPath2.lengthAdjust)
    assertTrue(textPath2.toXml().contains('lengthAdjust="spacingAndGlyphs"'))
  }

  @Test
  void testTextPathTextLengthNumber() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .textLength(500)

    assertEquals('500', textPath.textLength)
    assertEquals('500', textPath.getTextLength())
    assertTrue(textPath.toXml().contains('textLength="500"'))
  }

  @Test
  void testTextPathTextLengthString() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .textLength('75%')

    assertEquals('75%', textPath.textLength)
  }

  @Test
  void testTextPathInlinePath() {
    // SVG 2 feature: path attribute instead of href
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .path('M 10 10 L 90 90')

    assertEquals('M 10 10 L 90 90', textPath.path)
    assertEquals('M 10 10 L 90 90', textPath.getPath())
    assertTrue(textPath.toXml().contains('path="M 10 10 L 90 90"'))
  }

  @Test
  void testTextPathCompleteAttributes() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .href('#myPath')
            .startOffset('25%')
            .method('stretch')
            .spacing('auto')
            .side('right')
            .lengthAdjust('spacingAndGlyphs')
            .textLength(500)

    assertEquals('#myPath', textPath.href)
    assertEquals('25%', textPath.startOffset)
    assertEquals('stretch', textPath.method)
    assertEquals('auto', textPath.spacing)
    assertEquals('right', textPath.side)
    assertEquals('spacingAndGlyphs', textPath.lengthAdjust)
    assertEquals('500', textPath.textLength)

    String xml = textPath.toXml()
    assertTrue(xml.contains('href="#myPath"'))
    assertTrue(xml.contains('method="stretch"'))
    assertTrue(xml.contains('side="right"'))
  }

  @Test
  void testTextPathWithTspan() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .href('#myPath')

    textPath.addTspan('Hello ')
    textPath.addTspan('World')

    assertEquals(2, textPath.children.size())
    assertTrue(textPath.toXml().contains('<tspan>Hello </tspan>'))
    assertTrue(textPath.toXml().contains('<tspan>World</tspan>'))
  }

  @Test
  void testTextPathWithStyledTspan() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .href('#myPath')

    textPath.addTspan('Hello').fill('red')
    textPath.addTspan(' World').fill('blue')

    String xml = textPath.toXml()
    assertTrue(xml.contains('fill="red"'))
    assertTrue(xml.contains('fill="blue"'))
  }

  @Test
  void testTextPathAddAnimate() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .href('#myPath')

    textPath.addAnimate()
            .attributeName('startOffset')
            .from('0%')
            .to('100%')
            .dur('5s')

    assertEquals(1, textPath.children.size())
    assertTrue(textPath.toXml().contains('<animate'))
    assertTrue(textPath.toXml().contains('attributeName="startOffset"'))
  }

  @Test
  void testTextPathAddSet() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .href('#myPath')

    textPath.addSet()
            .attributeName('fill')
            .to('red')

    assertEquals(1, textPath.children.size())
    assertTrue(textPath.toXml().contains('<set'))
  }

  @Test
  void testTextPathAddA() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()
            .href('#myPath')

    textPath.addA()
            .href('https://example.com')

    assertEquals(1, textPath.children.size())
    assertTrue(textPath.toXml().contains('<a'))
  }

  @Test
  void testTextPathMethodChaining() {
    Svg svg = new Svg()
    TextPath textPath = svg.addText().addTextPath()

    TextPath result = textPath
            .href('#path1')
            .startOffset(25)
            .method('stretch')
            .spacing('auto')
            .side('left')
            .lengthAdjust('spacing')
            .textLength(300)
            .path('M 0 0 L 100 100')

    assertSame(textPath, result)
  }

  @Test
  void testTextPathRealWorldScenario() {
    // Real-world scenario: Text following a circular path
    Svg svg = new Svg(300, 300)

    // Define a circular path
    svg.addPath('circlePath')
            .d('M 150,150 m -100,0 a 100,100 0 1,1 200,0 a 100,100 0 1,1 -200,0')
            .fill('none')
            .stroke('lightgray')

    // Create text that follows the path
    svg.addText()
            .addTextPath()
            .href('#circlePath')
            .startOffset('25%')
            .method('align')
            .spacing('auto')
            .addContent('Text follows this circular path!')

    String xml = svg.toXml()
    assertTrue(xml.contains('<textPath'))
    assertTrue(xml.contains('href="#circlePath"'))
    assertTrue(xml.contains('Text follows this circular path!'))
  }
}
