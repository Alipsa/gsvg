package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Script
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.*

class ScriptTest {

  @Test
  void testScript1() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 10 10" height="120px" width="120px">
        <circle cx="5" cy="5" r="4"/>
        <script>// <![CDATA[
          function getColor() {
            const R = Math.round(Math.random() * 255)
              .toString(16)
              .padStart(2, "0");
      
            const G = Math.round(Math.random() * 255)
              .toString(16)
              .padStart(2, "0");
      
            const B = Math.round(Math.random() * 255)
              .toString(16)
              .padStart(2, "0");
      
            return `#${R}${G}${B}`;
          }
      
          document.querySelector("circle").addEventListener("click", (e) => {
            e.target.style.fill = getColor();
          });
          // ]]> </script>
      </svg>
      '''.stripIndent()
    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().viewBox("0 0 10 10").height("120px").width("120px")
    svg.addCircle().cx(5).cy(5).r(4)
    def script = svg.addScript()
    script.addContent('// ')
    script.addCdataContent('''
    function getColor() {
      const R = Math.round(Math.random() * 255)
        .toString(16)
        .padStart(2, "0");

      const G = Math.round(Math.random() * 255)
        .toString(16)
        .padStart(2, "0");

      const B = Math.round(Math.random() * 255)
        .toString(16)
        .padStart(2, "0");

      return `#${R}${G}${B}`;
    }

    document.querySelector("circle").addEventListener("click", (e) => {
      e.target.style.fill = getColor();
    });
    // ''')
    script.addContent(' ')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testScript2() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" width="6cm" height="5cm" viewBox="0 0 600 500">
        <desc>Example script01 - invoke an ECMAScript function from an onclick event</desc>
        <script type="application/ecmascript"><![CDATA[
          function circle_click(evt) {
            var circle = evt.target;
            var currentRadius = circle.getAttribute("r");
            if (currentRadius == 100)
              circle.setAttribute("r", currentRadius*2);
            else
              circle.setAttribute("r", currentRadius*0.5);
          }]]></script>
        <rect x="1" y="1" width="598" height="498" fill="none" stroke="blue"/>
        <circle onclick="circle_click(evt)" cx="300" cy="225" r="100" fill="red"/>
        <text x="300" y="480" font-family="Verdana" font-size="35" text-anchor="middle">Click on circle to change its size</text>
      </svg>
      '''.stripIndent()
    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().width("6cm").height("5cm").viewBox("0 0 600 500")
    svg.addDesc('Example script01 - invoke an ECMAScript function from an onclick event')
    svg.addScript().type("application/ecmascript").addCdataContent('''
      function circle_click(evt) {
        var circle = evt.target;
        var currentRadius = circle.getAttribute("r");
        if (currentRadius == 100)
          circle.setAttribute("r", currentRadius*2);
        else
          circle.setAttribute("r", currentRadius*0.5);
      }'''.stripIndent(2))
    svg.addRect().x(1).y(1).width(598).height(498).fill('none').stroke('blue')
    svg.addCircle().onClick('circle_click(evt)').cx(300).cy(225).r(100).fill('red')
    svg.addText('Click on circle to change its size').x(300).y(480)
        .fontFamily('Verdana').fontSize(35).textAnchor('middle')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

  }

  @Test
  void testScriptTypeGetter() {
    Svg svg = new Svg()
    Script script = svg.addScript()
            .type('text/javascript')

    assertEquals('text/javascript', script.type)
    assertEquals('text/javascript', script.getType())
    assertTrue(script.toXml().contains('type="text/javascript"'))
  }

  @Test
  void testScriptHref() {
    Svg svg = new Svg()
    Script script = svg.addScript()
            .href('scripts/main.js')

    assertEquals('scripts/main.js', script.href)
    assertEquals('scripts/main.js', script.getHref())
    assertTrue(script.toXml().contains('href="scripts/main.js"'))
  }

  @Test
  void testScriptExternalReference() {
    Svg svg = new Svg()
    Script script = svg.addScript()
            .type('text/javascript')
            .href('https://example.com/script.js')

    assertEquals('https://example.com/script.js', script.href)
    assertTrue(script.toXml().contains('href="https://example.com/script.js"'))
  }

  @Test
  void testScriptXlinkHref() {
    Svg svg = new Svg()
    Script script = svg.addScript()
            .xlinkHref('legacy/script.js')

    assertEquals('legacy/script.js', script.xlinkHref)
    assertEquals('legacy/script.js', script.getXlinkHref())
    assertTrue(script.toXml().contains('xlink:href="legacy/script.js"'))
  }

  @Test
  void testScriptHrefFallbackToXlink() {
    Svg svg = new Svg()
    Script script = svg.addScript()
            .xlinkHref('fallback.js')

    // getHref() should fall back to xlink:href if href is not set
    assertEquals('fallback.js', script.getHref())
  }

  @Test
  void testScriptCrossoriginAnonymous() {
    Svg svg = new Svg()
    Script script = svg.addScript()
            .href('https://cdn.example.com/lib.js')
            .crossorigin('anonymous')

    assertEquals('anonymous', script.crossorigin)
    assertEquals('anonymous', script.getCrossorigin())
    assertTrue(script.toXml().contains('crossorigin="anonymous"'))
  }

  @Test
  void testScriptCrossoriginUseCredentials() {
    Svg svg = new Svg()
    Script script = svg.addScript()
            .href('https://cdn.example.com/lib.js')
            .crossorigin('use-credentials')

    assertEquals('use-credentials', script.crossorigin)
    assertTrue(script.toXml().contains('crossorigin="use-credentials"'))
  }

  @Test
  void testScriptCompleteAttributes() {
    Svg svg = new Svg()
    Script script = svg.addScript()
            .type('text/javascript')
            .href('https://example.com/script.js')
            .crossorigin('anonymous')

    assertEquals('text/javascript', script.type)
    assertEquals('https://example.com/script.js', script.href)
    assertEquals('anonymous', script.crossorigin)

    String xml = script.toXml()
    assertTrue(xml.contains('type="text/javascript"'))
    assertTrue(xml.contains('href="https://example.com/script.js"'))
    assertTrue(xml.contains('crossorigin="anonymous"'))
  }

  @Test
  void testScriptMethodChaining() {
    Svg svg = new Svg()
    Script script = svg.addScript()

    Script result = script
            .type('text/javascript')
            .href('app.js')
            .crossorigin('anonymous')

    assertSame(script, result)
  }
}
