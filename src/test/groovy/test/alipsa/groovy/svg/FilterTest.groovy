package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Defs
import se.alipsa.groovy.svg.FeBlend
import se.alipsa.groovy.svg.FeColorMatrix
import se.alipsa.groovy.svg.FeComposite
import se.alipsa.groovy.svg.FeConvolveMatrix
import se.alipsa.groovy.svg.FeDiffuseLighting
import se.alipsa.groovy.svg.FeFlood
import se.alipsa.groovy.svg.FeGaussianBlur
import se.alipsa.groovy.svg.FeMorphology
import se.alipsa.groovy.svg.Filter
import se.alipsa.groovy.svg.FilterFunction
import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.In
import se.alipsa.groovy.svg.LinearGradient
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

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
                    0      0      0      1 0"/>
        </filter>
        <filter id="cmRGB">
          <feColorMatrix color-interpolation-filters="sRGB" in="SourceGraphic" type="matrix"
            values="0.0157 0      0      0 0
                    0      0.3059 0      0 0 
                    0      0      0.7765 0 0 
                    0      0      0      1 0"/>
        </filter>
      </defs>
      <rect width="100%" height="50%" fill="white" filter="url(#cm)"/>    
      <rect y="50%" width="100%" height="50%" fill="white" filter="url(#cmRGB)"/>
    </svg>
    '''.replaceAll("\\s+",' ').replace('> <', '><').trim()
    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXml(svg))

    svg = new Svg().xlink().width('100%').viewBox('0 0 640 480').height('100%')
    def defs = svg.addDefs()
    defs.addFilter('cm').addFeColorMatrix()
        .in('SourceGraphic')
        .type('matrix')
        .values('0.0157 0 0 0 0 0 0.3059 0 0 0 0 0 0.7765 0 0 0 0 0 1 0')
    defs.addFilter("cmRGB").addFeColorMatrix()
      .colorInterpolationFilters('sRGB')
      .in(In.SourceGraphic)
      .type(FeColorMatrix.Type.matrix)
      .values('0.0157 0 0 0 0 0 0.3059 0 0 0 0 0 0.7765 0 0 0 0 0 1 0')
    svg.addRect().width('100%').height('50%').fill("white").filter("url(#cm)")
    svg.addRect().y('50%').width('100%').height('50%').fill('white').filter('url(#cmRGB)')
    assertEquals(svgContent, SvgWriter.toXml(svg))
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

  @Test
  void testFeCompositeFeImage() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="800px" height="600px">
      <defs>
        <filter id="overlap-shadow" filterUnits="userSpaceOnUse">
          <feImage xlink:href="#ani-path" x="0" y="0" result="imported-ani"/>
          <feComposite operator="in" in="SourceGraphic" in2="imported-ani" result="overlap"/>
          <feGaussianBlur stdDeviation="4" in="overlap" result="blurred-overlap"/>
          <feComposite operator="over" in="blurred-overlap" in2="SourceGraphic"/>
        </filter>
      </defs>
      <line filter="url(#overlap-shadow)" x1="50" x2="400" y1="50" y2="50" stroke="red" stroke-width="5"/>
      <path id="ani-path" d="M 0 0 L 100 50 h 100 L 300 150" stroke="black" stroke-width="5" fill="none"/>
    </svg>
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().xlink().width('800px').height('600px')
    Filter f = svg.addDefs().addFilter('overlap-shadow').filterUnits('userSpaceOnUse')
    f.addFeImage().xlinkHref('#ani-path').x(0).y(0).result('imported-ani')
    f.addFeComposite().operator('in').in(In.SourceGraphic).in2('imported-ani').result('overlap')
    f.addFeGaussianBlur().stdDeviation(4).in('overlap').result('blurred-overlap')
    f.addFeComposite().operator('over').in('blurred-overlap').in2('SourceGraphic')
    svg.addLine().filter('url(#overlap-shadow)').x1(50).x2(400).y1(50).y2(50).stroke('red').strokeWidth(5)
    svg.addPath("ani-path").d("M 0 0 L 100 50 h 100 L 300 150").stroke('black').strokeWidth(5).fill('none')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testFeConvolveMatrix() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" width="100%" height="220" style="outline: 1px solid red">
      <defs>
        <filter id="convolve">
          <feConvolveMatrix kernelMatrix="1 0 0 0 0 0 0 0 -1"/>
        </filter>
      </defs>
      <g font-size="3em">
        <text x="225" y="75">Convolve</text>
        <text x="225" y="150" filter="url(#convolve)">Convolve</text>
      </g>
    </svg>
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().width('100%').height(220).style("outline: 1px solid red")
    svg.addDefs().addFilter('convolve').addFeConvolveMatrix().kernelMatrix("1 0 0 0 0 0 0 0 -1")
    G g = svg.addG().fontSize('3em')
    g.addText().x(225).y(75).addContent('Convolve')
    g.addText('Convolve').x(225).y(150).filter('url(#convolve)')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testFeCompositeAttributes() {
    Svg svg = new Svg()
    Filter filter = svg.addFilter('composite')
    def composite = filter.addFeComposite()
        .operator(FeComposite.Operator.arithmetic)
        .in(In.SourceGraphic)
        .in2('BackgroundImage')
        .k1('0.1')
        .k2('0.2')
        .k3('0.3')
        .k4('0.4')

    assertAll(
        { -> assertEquals('arithmetic', composite.getOperator()) },
        { -> assertEquals('SourceGraphic', composite.getIn()) },
        { -> assertEquals('BackgroundImage', composite.getIn2()) },
        { -> assertEquals('0.1', composite.getK1()) },
        { -> assertEquals('0.2', composite.getK2()) },
        { -> assertEquals('0.3', composite.getK3()) },
        { -> assertEquals('0.4', composite.getK4()) }
    )
  }

  @Test
  void testFeDiffuseLighting() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" width="440" height="140">
      <text text-anchor="middle" x="60" y="22">No Light</text>
      <circle cx="60" cy="80" r="50" fill="green"/>
      <text text-anchor="middle" x="170" y="22">fePointLight</text>
      <filter id="lightMe1">
        <feDiffuseLighting in="SourceGraphic" result="light" lighting-color="white">
          <fePointLight x="150" y="60" z="20"/>
        </feDiffuseLighting>
        <feComposite in="SourceGraphic" in2="light" operator="arithmetic" k1="1" k2="0" k3="0" k4="0"/>
      </filter>
      <circle cx="170" cy="80" r="50" fill="green" filter="url(#lightMe1)"/>
      <text text-anchor="middle" x="280" y="22">feDistantLight</text>
      <filter id="lightMe2">
        <feDiffuseLighting in="SourceGraphic" result="light" lighting-color="white">
          <title>the light source is a feDistantLight element</title>
          <feDistantLight azimuth="240" elevation="20"/>
        </feDiffuseLighting>
        <feComposite in="SourceGraphic" in2="light" operator="arithmetic" k1="1" k2="0" k3="0" k4="0"/>
      </filter>
      <circle cx="280" cy="80" r="50" fill="green" filter="url(#lightMe2)"/>
      <text text-anchor="middle" x="390" y="22">feSpotLight</text>
      <filter id="lightMe3">
        <feDiffuseLighting in="SourceGraphic" result="light" lighting-color="white">
          <desc>the light source is a feSpotLight source</desc>
          <feSpotLight x="360" y="5" z="30" limitingConeAngle="20" pointsAtX="390" pointsAtY="80" pointsAtZ="0"/>
        </feDiffuseLighting>
        <feComposite in="SourceGraphic" in2="light" operator="arithmetic" k1="1" k2="0" k3="0" k4="0"/>
      </filter>
      <circle cx="390" cy="80" r="50" fill="green" filter="url(#lightMe3)"/>
    </svg>
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg(440, 140)
    svg.addText('No Light').textAnchor('middle').x(60).y(22)
    svg.addCircle().cx(60).cy(80).r(50).fill('green')
    svg.addText('fePointLight').textAnchor('middle').x(170).y(22)
    def lightMe1 = svg.addFilter('lightMe1')
    lightMe1.addFeDiffuseLighting()
      .in(In.SourceGraphic)
      .result('light')
      .lightingColor('white')
      .addFePointLight().x(150).y(60).z(20)
    lightMe1.addFeComposite()
        .in("SourceGraphic")
        .in2("light")
        .operator('arithmetic')
        .k1(1)
        .k2(0)
        .k3(0)
        .k4(0)
    svg.addCircle().cx(170).cy(80).r(50).fill('green').filter("url(#lightMe1)")

    svg.addText('feDistantLight').textAnchor('middle').x(280).y(22)
    def lightMe2 = svg.addFilter('lightMe2')
    FeDiffuseLighting dl = lightMe2.addFeDiffuseLighting()
        .in(In.SourceGraphic)
        .result('light')
        .lightingColor('white')
    dl.addTitle('the light source is a feDistantLight element')
    dl.addFeDistantLight().azimuth(240).elevation(20)
    lightMe2.addFeComposite()
        .in("SourceGraphic")
        .in2("light")
        .operator('arithmetic')
        .k1(1)
        .k2(0)
        .k3(0)
        .k4(0)
    svg.addCircle().cx(280).cy(80).r(50).fill('green').filter("url(#lightMe2)")

    svg.addText('feSpotLight').textAnchor('middle').x(390).y(22)
    def lightMe3 = svg.addFilter('lightMe3')
    FeDiffuseLighting dl3 = lightMe3.addFeDiffuseLighting()
        .in(In.SourceGraphic)
        .result('light')
        .lightingColor('white')
    dl3.addDesc('the light source is a feSpotLight source')
    dl3.addFeSpotLight()
        .x(360).y(5).z(30)
        .limitingConeAngle(20)
        .pointsAtX(390).pointsAtY(80).pointsAtZ(0)
    lightMe3.addFeComposite()
        .in("SourceGraphic")
        .in2("light")
        .operator('arithmetic')
        .k1(1)
        .k2(0)
        .k3(0)
        .k4(0)
    svg.addCircle().cx(390).cy(80).r(50).fill('green').filter("url(#lightMe3)")
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testFeTileFeOffsetFeMerge() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" id="svg24" width="220" height="120">
      <defs id="defs15">
        <g transform="scale(0.25)" id="dotted-pattern">
          <rect style="fill-opacity:0" id="pattern-box" width="16" height="16"/>
          <circle style="fill:#000" id="pattern-circle1" cx="10" cy="10" r="2"/>
          <circle style="fill:#000" id="pattern-circle2" cx="2" cy="2" r="2"/>
        </g>
        <filter id="dropShadow" height="1.154449" x="-0.0044999999" y="-0.0049999999" width="1.0582259">
          <feImage width="4" height="4" result="pattern-image" xlink:href="#dotted-pattern"/>
          <feTile in="pattern-image" result="texture" id="feTile4"/>   
          <feOffset dx="11" dy="15" result="offsetblur" id="feOffset6"/>
          <feMerge id="feMerge12">
            <feMergeNode id="feMergeNode8"/> 
            <feMergeNode in="SourceGraphic" id="feMergeNode10"/>
          </feMerge>
        </filter>
      </defs>
      <rect fill="#ffffff" stroke="#000000" x="10" y="10" width="200" height="100" style="filter:url(#dropShadow)" id="node2"/>
    </svg>
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().xlink().version('1.1').id('svg24').width(220).height(120)
    def defs15 = svg.addDefs('defs15')
    G g = defs15.addG().transform('scale(0.25)').id('dotted-pattern')
    g.addRect().style('fill-opacity:0').id('pattern-box').width(16).height(16)
    g.addCircle().style('fill:#000').id('pattern-circle1').cx(10).cy(10).r(2)
    g.addCircle().style('fill:#000').id('pattern-circle2').cx(2).cy(2).r(2)
    Filter dropShadow = defs15.addFilter('dropShadow')
      .height(1.154449)
      .x(-0.0044999999)
      .y(-0.0049999999)
      .width(1.0582259)
    dropShadow.addFeImage()
      .width(4)
      .height(4)
      .result('pattern-image')
      .xlinkHref('#dotted-pattern')
    dropShadow.addFeTile().in('pattern-image').result('texture').id('feTile4')
    dropShadow.addFeOffset().dx(11).dy(15).result('offsetblur').id('feOffset6')
    def feMerge12 = dropShadow.addFeMerge('feMerge12')
    feMerge12.addFeMergeNode('feMergeNode8')
    feMerge12.addFeMergeNode().in(In.SourceGraphic).id('feMergeNode10')
    svg.addRect()
        .fill("#ffffff")
        .stroke("#000000")
        .x(10)
        .y(10)
        .width(200)
        .height(100)
        .style("filter:url(#dropShadow)")
        .id('node2')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testFeTurbulenceFeDisplacementMap() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" width="200" height="200" viewBox="0 0 220 220">
      <filter id="displacementFilter">
        <feTurbulence type="turbulence" baseFrequency="0.05" numOctaves="2" result="turbulence"/>
        <feDisplacementMap in2="turbulence" in="SourceGraphic" scale="50" xChannelSelector="R" yChannelSelector="G"/>
      </filter>
      <circle cx="100" cy="100" r="100" style="filter: url(#displacementFilter)"/>
    </svg>
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg(200, 200).viewBox('0 0 220 220')
    def displacementFilter = svg.addFilter('displacementFilter')
    displacementFilter.addFeTurbulence()
        .type('turbulence')
        .baseFrequency(0.05)
        .numOctaves(2)
        .result('turbulence')
    displacementFilter.addFeDisplacementMap()
        .in2('turbulence')
        .in('SourceGraphic')
        .scale(50)
        .xChannelSelector('R')
        .yChannelSelector('G')
    svg.addCircle().cx(100).cy(100).r(100).style('filter: url(#displacementFilter)')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testDropShadow() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 30 10">
      <defs>
        <filter id="shadow">
          <feDropShadow dx="0.2" dy="0.4" stdDeviation="0.2"/>
        </filter>
        <filter id="shadow2">
          <feDropShadow dx="0" dy="0" stdDeviation="0.5" flood-color="cyan"/>
        </filter>
        <filter id="shadow3">
          <feDropShadow dx="-0.8" dy="-0.8" stdDeviation="0" flood-color="red" flood-opacity="0.35"/>
        </filter>
      </defs>
      <circle cx="5" cy="50%" r="4" style="fill:pink; filter:url(#shadow);"/>
      <circle cx="15" cy="50%" r="4" style="fill:pink; filter:url(#shadow2);"/>
      <circle cx="25" cy="50%" r="4" style="fill:pink; filter:url(#shadow3);"/>
    </svg>    
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().viewBox("0 0 30 10")
    def defs = svg.addDefs()
    defs.addFilter('shadow').addFeDropShadow().dx(0.2).dy(0.4).stdDeviation(0.2)
    defs.addFilter('shadow2').addFeDropShadow()
        .dx(0).dy(0).stdDeviation(0.5).floodColor('cyan')
    defs.addFilter('shadow3').addFeDropShadow()
        .dx(-0.8).dy(-0.8).stdDeviation(0).floodColor('red').floodOpacity(0.35)
    svg.addCircle().cx(5).cy('50%').r(4).style("fill:pink; filter:url(#shadow);")
    svg.addCircle().cx(15).cy('50%').r(4).style("fill:pink; filter:url(#shadow2);")
    svg.addCircle().cx(25).cy('50%').r(4).style("fill:pink; filter:url(#shadow3);")
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testFeMorphology() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" width="300" height="100">
        <filter id="erode">
          <feMorphology operator="erode" radius="0.5"/>
        </filter>
        <filter id="dilate">
          <feMorphology operator="dilate" radius="1"/>
        </filter>
        <text x="10" y="20">Normal text</text>
        <text x="10" y="55" style="filter: url(#erode);" fill="blue" font-size="30">Thinned text</text>
        <text x="10" y="80" style="filter: url(#dilate);" font-size="20">Fattened text</text>
      </svg>
      '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg(300,100)
    svg.addFilter('erode').addFeMorphology().operator(FeMorphology.Operator.erode).radius(0.5)
    svg.addFilter('dilate').addFeMorphology().operator('dilate').radius("1")
    svg.addText('Normal text').x(10).y(20)
    svg.addText('Thinned text').x(10).y(55).style('filter: url(#erode);').fill('blue').fontSize(30)
    svg.addText('Fattened text').x(10).y(80).style('filter: url(#dilate);').fontSize(20)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testFeSpecularLighting() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" height="200" width="200" viewBox="0 0 220 220">
        <filter id="filter">
          <feSpecularLighting result="specOut" specularExponent="20" lighting-color="#bbbbbb">
            <fePointLight x="50" y="75" z="200"/>
          </feSpecularLighting>
          <feComposite in="SourceGraphic" in2="specOut" operator="arithmetic" k1="0" k2="1" k3="1" k4="0"/>
        </filter>
        <circle cx="110" cy="110" r="100" style="filter:url(#filter)"/>
      </svg>
      '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().height(200).width(200).viewBox('0 0 220 220')
    def filter = svg.addFilter('filter')
    filter.addFeSpecularLighting().result("specOut").specularExponent(20).lightingColor("#bbbbbb")
      .addFePointLight().x(50).y(75).z(200)
    filter.addFeComposite().in("SourceGraphic" ).in2('specOut').operator("arithmetic").k1(0).k2(1).k3(1).k4(0)
    svg.addCircle().cx(110).cy(110).r(100).style("filter:url(#filter)")
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testFeSpecularLightingAttributes() {
    Svg svg = new Svg()
    def filter = svg.addFilter('specTest')

    // Test all FeSpecularLighting attributes and light source types
    def spec1 = filter.addFeSpecularLighting()
      .surfaceScale(5)
      .specularConstant(1.5)
      .specularExponent(20)
      .lightingColor('white')
      .result('spec1')
      .in(In.SourceAlpha)

    assertEquals('5', spec1.surfaceScale)
    assertEquals('1.5', spec1.specularConstant)
    assertEquals('20', spec1.specularExponent)
    assertEquals('white', spec1.lightingColor)
    assertEquals('spec1', spec1.result)
    assertEquals('SourceAlpha', spec1.in)

    // Test with feDistantLight
    def spec2 = filter.addFeSpecularLighting()
      .surfaceScale('3')
      .specularConstant('2')
      .specularExponent('15')
    spec2.addFeDistantLight().azimuth(45).elevation(30)

    assertEquals('3', spec2.surfaceScale)
    assertEquals('2', spec2.specularConstant)
    assertEquals('15', spec2.specularExponent)

    // Test with feSpotLight
    def spec3 = filter.addFeSpecularLighting().lightingColor('#ff0000')
    spec3.addFeSpotLight()
      .x(100).y(100).z(50)
      .pointsAtX(200).pointsAtY(200).pointsAtZ(0)
      .limitingConeAngle(30)

    assertEquals('#ff0000', spec3.lightingColor)
  }

  @Test
  void testMapBasedFilterFactoryMethods() {
    Svg svg = new Svg(200, 200)
    def filter = svg.addFilter([id: 'mapTest', x: '0', y: '0'])

    // Test various filter element Map factories
    def blur = filter.addFeGaussianBlur([stdDeviation: '5', result: 'blur1'])
    assertEquals('5', blur.stdDeviation)
    assertEquals('blur1', blur.result)

    def blend = filter.addFeBlend([mode: 'multiply', in: 'blur1', in2: 'SourceGraphic'])
    assertEquals('multiply', blend.mode)
    assertEquals('blur1', blend.in)

    def flood = filter.addFeFlood(['flood-color': 'red', 'flood-opacity': '0.5'])
    assertEquals('red', flood.floodColor)
    assertEquals('0.5', flood.floodOpacity)

    def colorMatrix = filter.addFeColorMatrix([type: 'saturate', values: '0.5'])
    // Note: 'type' conflicts with Object.getClass() generic types, so use getAttribute for this specific case
    assertEquals('saturate', colorMatrix.getAttribute('type'))
    assertEquals('0.5', colorMatrix.values)

    def composite = filter.addFeComposite([operator: 'over', in: 'SourceGraphic'])
    assertEquals('over', composite.operator)
    assertEquals('SourceGraphic', composite.in)

    def offset = filter.addFeOffset([dx: '10', dy: '20'])
    assertEquals('10', offset.dx)
    assertEquals('20', offset.dy)

    def morphology = filter.addFeMorphology([operator: 'dilate', radius: '2'])
    assertEquals('dilate', morphology.operator)
    assertEquals('2', morphology.radius)

    def dropShadow = filter.addFeDropShadow([dx: '5', dy: '5', stdDeviation: '3'])
    assertEquals('5', dropShadow.dx)
    assertEquals('5', dropShadow.dy)
    assertEquals('3', dropShadow.stdDeviation)
  }

  @Test
  void testMapBasedElementFactoryMethods() {
    Svg svg = new Svg()

    // Test Map-based circle
    def circle = svg.addCircle([cx: '50', cy: '60', r: '25', fill: 'blue'])
    assertEquals('50', circle.cx)
    assertEquals('60', circle.cy)
    assertEquals('25', circle.r)
    assertEquals('blue', circle.fill)

    // Test Map-based rect
    def rect = svg.addRect([x: '10', y: '20', width: '100', height: '50', fill: 'green'])
    assertEquals('10', rect.x)
    assertEquals('20', rect.y)
    assertEquals('100', rect.width)
    assertEquals('50', rect.height)

    // Test Map-based text
    def text = svg.addText([x: '30', y: '40', fill: 'red'])
    assertEquals('30', text.x)
    assertEquals('40', text.y)
    assertEquals('red', text.fill)

    // Test Map-based line
    def line = svg.addLine([x1: '0', y1: '0', x2: '100', y2: '100', stroke: 'black'])
    assertEquals('0', line.x1)
    assertEquals('0', line.y1)
    assertEquals('100', line.x2)
    assertEquals('100', line.y2)
  }

  @Test
  void testMapBasedGradientMethods() {
    Svg svg = new Svg()
    def defs = svg.addDefs()

    // Test Map-based LinearGradient
    def lg = defs.addLinearGradient([id: 'lg1', x1: '0%', y1: '0%', x2: '100%', y2: '0%'])
    assertEquals('lg1', lg.id)
    assertEquals('0%', lg.x1)
    assertEquals('100%', lg.x2)

    // Test Map-based RadialGradient
    def rg = defs.addRadialGradient([id: 'rg1', cx: '50%', cy: '50%', r: '50%'])
    assertEquals('rg1', rg.id)
    assertEquals('50%', rg.cx)
    assertEquals('50%', rg.r)
  }

  @Test
  void testFeBlendModeEnum() {
    Svg svg = new Svg()
    def filter = svg.addFilter('blendTest')

    // Test all FeBlend.Mode enum values (5 values)
    def blend1 = filter.addFeBlend().mode(FeBlend.Mode.normal)
    assertEquals('normal', blend1.mode)

    def blend2 = filter.addFeBlend().mode(FeBlend.Mode.multiply)
    assertEquals('multiply', blend2.mode)

    def blend3 = filter.addFeBlend().mode(FeBlend.Mode.screen)
    assertEquals('screen', blend3.mode)

    def blend4 = filter.addFeBlend().mode(FeBlend.Mode.darken)
    assertEquals('darken', blend4.mode)

    def blend5 = filter.addFeBlend().mode(FeBlend.Mode.lighten)
    assertEquals('lighten', blend5.mode)

    // Verify all enum values are accessible
    assertNotNull(FeBlend.Mode.normal)
    assertNotNull(FeBlend.Mode.multiply)
    assertNotNull(FeBlend.Mode.screen)
    assertNotNull(FeBlend.Mode.darken)
    assertNotNull(FeBlend.Mode.lighten)

    // Test using string mode as well
    def blend6 = filter.addFeBlend().mode('multiply')
    assertEquals('multiply', blend6.mode)
  }

  @Test
  void testFeConvolveMatrixEdgeModeEnum() {
    Svg svg = new Svg()
    def filter = svg.addFilter('convolveTest')

    // Test FeConvolveMatrix.EdgeMode enum values
    def convolve1 = filter.addFeConvolveMatrix().edgeMode(FeConvolveMatrix.EdgeMode.duplicate)
    assertEquals('duplicate', convolve1.edgeMode)

    def convolve2 = filter.addFeConvolveMatrix().edgeMode(FeConvolveMatrix.EdgeMode.wrap)
    assertEquals('wrap', convolve2.edgeMode)

    def convolve3 = filter.addFeConvolveMatrix().edgeMode(FeConvolveMatrix.EdgeMode.none)
    assertEquals('none', convolve3.edgeMode)

    // Verify all enum values are accessible
    assertNotNull(FeConvolveMatrix.EdgeMode.duplicate)
    assertNotNull(FeConvolveMatrix.EdgeMode.wrap)
    assertNotNull(FeConvolveMatrix.EdgeMode.none)
  }

}
