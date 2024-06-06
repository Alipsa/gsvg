package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.ClipPath
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter

import static org.junit.jupiter.api.Assertions.*

class ClipPathTest {

  @Test
  void buildClipPathProgrammatically() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 100 100">
      <defs>
        <clipPath id="myClip">
          <circle cx="40" cy="35" r="35"/>
        </clipPath>
      </defs>
      <path id="heart" d="M10,30 A20,20,0,0,1,50,30 A20,20,0,0,1,90,30 Q90,60,50,90 Q10,60,10,30 Z"/>
      <use clip-path="url(#myClip)" xlink:href="#heart" fill="red"/>
    </svg>
    '''.stripIndent()
    Svg svg = new Svg()
    svg
        .xlink()
        .viewBox("0 0 100 100")
    ClipPath cp = svg.addDefs().addClipPath().id('myClip')
    cp.addCircle()
      .cx(40)
      .cy(35)
      .r(35)

    svg.addPath()
      .id('heart')
      .d("M10,30 A20,20,0,0,1,50,30 A20,20,0,0,1,90,30 Q90,60,50,90 Q10,60,10,30 Z" )

    svg.addUse()
      .clipPath('url(#myClip)')
      .href('#heart')
      .fill('red')

    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void parseClipPath(){
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 100 100">
      <defs>
        <clipPath id="myClip">
          <circle cx="40" cy="35" r="35"/>
        </clipPath>
      </defs>
      <path id="heart" d="M10,30 A20,20,0,0,1,50,30 A20,20,0,0,1,90,30 Q90,60,50,90 Q10,60,10,30 Z"/>
      <use clip-path="url(#myClip)" xlink:href="#heart" fill="red"/>
    </svg>
    '''.stripIndent()
    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }
}
