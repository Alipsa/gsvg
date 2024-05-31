package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Defs
import se.alipsa.groovy.svg.FeBlend
import se.alipsa.groovy.svg.FeFlood
import se.alipsa.groovy.svg.FeGaussianBlur
import se.alipsa.groovy.svg.Filter
import se.alipsa.groovy.svg.FilterFunction
import se.alipsa.groovy.svg.LinearGradient
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter

import static org.junit.jupiter.api.Assertions.*

class FilterTest {

  @Test
  void testFilterWithGaussianBlur() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
      <defs>
        <filter id="f1" x="0" y="0">
          <feGaussianBlur in="SourceGraphic" stdDeviation="15"/>
        </filter>
      </defs>
      <rect width="90" height="90" fill="red" filter="url(#f1)"/>
    </svg>
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkGaussianFilterContent(svg)

    svg = new Svg(100, 100)
    svg.addDefs()
      .addFilter('f1').x(0).y(0)
      .addFeGaussianBlur().in('SourceGraphic').stdDeviation(15)
    svg.addRect(90, 90).fill('red').filter('url(#f1)')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkGaussianFilterContent(svg)
  }

  void checkGaussianFilterContent(Svg svg) {
    Filter filter = svg[0][0]
    assertEquals('f1', filter.id)
    assertEquals('0', filter.x)
    assertEquals('0', filter.y)
    FeGaussianBlur feGaussianBlur = filter[0]
    assertEquals('SourceGraphic', feGaussianBlur.in)
    assertEquals('15', feGaussianBlur.stdDeviation)
  }

  @Test
  void testFloodAndBlend() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" width="200" height="200"> 
        <defs> 
          <filter id="spotlight"> 
            <feFlood result="floodFill" x="0" y="0" width="100%" height="100%" flood-color="green" flood-opacity="1"/> 
            <feBlend in="FillPaint" in2="floodFill" mode="multiply"/> 
          </filter> 
        </defs> 
        <rect x="40" y="40" width="100" height="100" style="stroke: #000000; fill: lightgreen; filter: url(#spotlight);"/> 
        <rect x="40" y="40" width="100" height="100" style="stroke: #000000; fill: green;"/> 
        <g fill="#FFFFFF" stroke="black" font-size="10" font-family="Verdana"> 
          <text x="50" y="90">GeeksForGeeks</text> 
        </g> 
      </svg>
      '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkFloodAndBlendAttributes(svg)

    svg = new Svg(200, 200)
    Filter filter = svg.addDefs().addFilter('spotlight')
    filter.addFeFlood()
      .result('floodFill')
      .x(0)
      .y(0)
      .width('100%')
      .height('100%')
      .floodColor('green')
      .floodOpacity(1)
    filter.addFeBlend()
      .in('FillPaint')
      .in2('floodFill')
      .mode('multiply')
    svg.addRect()
      .x(40)
      .y(40)
      .width(100)
      .height(100)
      .style('stroke: #000000; fill: lightgreen; filter: url(#spotlight);')
    svg.addRect()
      .x(40)
      .y(40)
      .width(100)
      .height(100)
      .style('stroke: #000000; fill: green;')
    svg.addG()
      .fill('#FFFFFF')
      .stroke('black')
      .fontSize(10)
      .fontFamily('Verdana')
    .addText('GeeksForGeeks')
      .x(50).y(90)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkFloodAndBlendAttributes(svg)
  }

  private void checkFloodAndBlendAttributes(Svg svg) {
    FeFlood feFlood = svg[0][0][0] as FeFlood
    assertEquals('floodFill', feFlood.result)
    assertEquals('0', feFlood.x)
    assertEquals('0', feFlood.y)
    assertEquals('100%', feFlood.width)
    assertEquals('100%', feFlood.height)
    assertEquals('green', feFlood.floodColor)
    assertEquals('1', feFlood.floodOpacity)

    FeBlend feBlend = svg[0][0][1] as FeBlend
    assertEquals('FillPaint', feBlend.in)
    assertEquals('floodFill', feBlend.in2)
    assertEquals('multiply', feBlend.mode)
  }

  @Test
  void testFeColorMatrix() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="100%" viewBox="0 0 640 480" height="100%">
      <defs>
        <filter id="cm">
          <feColorMatrix in="SourceGraphic" type="matrix"
            values="0.0157 0      0      0 0
                    0      0.3059 0      0 0 
                    0      0      0.7765 0 0 
                    0      0      0      1 0  "/>
        </filter>
        <filter id="cmRGB">
          <feColorMatrix color-interpolation-filters="sRGB" in="SourceGraphic" type="matrix"
            values="0.0157 0      0      0 0
                    0      0.3059 0      0 0 
                    0      0      0.7765 0 0 
                    0      0      0      1 0  "/>
        </filter>
      </defs>
      <rect width="100%" height="50%" fill="white" filter="url(#cm)"/>    
      <rect y="50%" width="100%" height="50%" fill="white" filter="url(#cmRGB)"/>
    </svg>
    '''
    Svg svg = SvgReader.parse(svgContent)
    println(svg.toXml())
  }

  @Test
  void testFeComponentTransfer() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" width="8cm" height="4cm" viewBox="0 0 800 400" version="1.1">
      <title>Example feComponentTransfer - Examples of feComponentTransfer operations</title>
      <desc>Four text strings showing the effects of feComponentTransfer: an identity function acting as a reference, use of the feComponentTransfer table, linear, and gamma option.</desc>
      <defs>
        <linearGradient id="MyGradient" gradientUnits="userSpaceOnUse" x1="100" y1="0" x2="600" y2="0">
          <stop offset="0" stop-color="#ff0000"/>
          <stop offset="0.33" stop-color="#00ff00"/>
          <stop offset="0.67" stop-color="#0000ff"/>
          <stop offset="1" stop-color="#000000"/>
        </linearGradient>
        <filter id="Identity" filterUnits="objectBoundingBox" x="0%" y="0%" width="100%" height="100%">
          <feComponentTransfer>
            <feFuncR type="identity"/>
            <feFuncG type="identity"/>
            <feFuncB type="identity"/>
            <feFuncA type="identity"/>
          </feComponentTransfer>
        </filter>
        <filter id="Table" filterUnits="objectBoundingBox" x="0%" y="0%" width="100%" height="100%">
          <feComponentTransfer>
            <feFuncR type="table" tableValues="0 0 1 1"/>
            <feFuncG type="table" tableValues="1 1 0 0"/>
            <feFuncB type="table" tableValues="0 1 1 0"/>
          </feComponentTransfer>
        </filter>
        <filter id="Linear" filterUnits="objectBoundingBox" x="0%" y="0%" width="100%" height="100%">
          <feComponentTransfer>
            <feFuncR type="linear" slope="0.5" intercept="0.25"/>
            <feFuncG type="linear" slope="0.5" intercept="0"/>
            <feFuncB type="linear" slope="0.5" intercept="0.5"/>
          </feComponentTransfer>
        </filter>
        <filter id="Gamma" filterUnits="objectBoundingBox" x="0%" y="0%" width="100%" height="100%">
          <feComponentTransfer>
            <feFuncR type="gamma" amplitude="2" exponent="5" offset="0"/>
            <feFuncG type="gamma" amplitude="2" exponent="3" offset="0"/>
            <feFuncB type="gamma" amplitude="2" exponent="1" offset="0"/>
          </feComponentTransfer>
        </filter>
      </defs>
      <rect fill="none" stroke="blue" x="1" y="1" width="798" height="398"/>
      <g font-family="Verdana" font-size="75" font-weight="bold" fill="url(#MyGradient)">
        <rect x="100" y="0" width="600" height="20"/>
        <text x="100" y="90">Identity</text>
        <text x="100" y="190" filter="url(#Table)">TableLookup</text>
        <text x="100" y="290" filter="url(#Linear)">LinearFunc</text>
        <text x="100" y="390" filter="url(#Gamma)">GammaFunc</text>
      </g>
    </svg>
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().width('8cm').height('4cm').viewBox('0 0 800 400').version('1.1')
    svg.addTitle('Example feComponentTransfer - Examples of feComponentTransfer operations')
    svg.addDesc('Four text strings showing the effects of feComponentTransfer: an identity function acting as a reference, use of the feComponentTransfer table, linear, and gamma option.')
    Defs defs = svg.addDefs()
    LinearGradient lg = defs.addLinearGradient()
      .id('MyGradient')
      .gradientUnits('userSpaceOnUse')
      .x1(100)
      .y1(0)
      .x2(600)
      .y2(0)
    lg.addStop().offset(0).stopColor('#ff0000')
    lg.addStop().offset(.33).stopColor('#00ff00')
    lg.addStop().offset(.67).stopColor('#0000ff')
    lg.addStop().offset(1).stopColor('#000000')
    Filter identityFilter = defs.addFilter()
      .id('Identity')
      .filterUnits('objectBoundingBox')
      .x('0%')
      .y('0%')
      .width('100%')
      .height('100%')
    def ct = identityFilter.addFeComponentTransfer()
    ct.addFeFuncR().type(FilterFunction.FilterType.identity)
    ct.addFeFuncG().type('identity')
    ct.addFeFuncB('identity')
    ct.addFeFuncA('identity')

    def tableFilter = defs.addFilter('Table')
    tableFilter
        .filterUnits('objectBoundingBox')
        .x('0%')
        .y('0%')
        .width('100%')
        .height('100%')
    def ct2 = tableFilter.addFeComponentTransfer()
    ct2.addFeFuncR().type(FilterFunction.FilterType.table).tableValues("0 0 1 1")
    ct2.addFeFuncG('table').tableValues("1 1 0 0")
    ct2.addFeFuncB('table').tableValues('0 1 1 0')
    def lf = defs.addFilter('Linear')
      .filterUnits('objectBoundingBox')
      .x('0%')
      .y('0%')
      .width('100%')
      .height('100%')
    def ct3 = lf.addFeComponentTransfer()
    ct3.addFeFuncR('linear').slope(.5).intercept(.25)
    ct3.addFeFuncG('linear').slope(.5).intercept(0)
    ct3.addFeFuncB('linear').slope(.5).intercept(.5)
    def gf = defs.addFilter('Gamma')
        .filterUnits('objectBoundingBox')
        .x('0%')
        .y('0%')
        .width('100%')
        .height('100%')
    def ct4 = gf.addFeComponentTransfer()
    ct4.addFeFuncR('gamma').amplitude(2).exponent(5).offset(0)
    ct4.addFeFuncG('gamma').amplitude(2).exponent(3).offset(0)
    ct4.addFeFuncB('gamma').amplitude(2).exponent(1).offset(0)
    svg.addRect().fill('none').stroke('blue').x(1).y(1).width(798).height(398)
    def g = svg.addG()
      .fontFamily('Verdana')
      .fontSize(75)
      .fontWeight('bold')
      .fill('url(#MyGradient)')
    g.addRect().x(100).y(0).width(600).height(20)
    g.addText('Identity').x(100).y(90)
    g.addText('TableLookup').x(100).y(190).filter('url(#Table)')
    g.addText('LinearFunc').x(100).y(290).filter('url(#Linear)')
    g.addText('GammaFunc').x(100).y(390).filter('url(#Gamma)')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }
}
