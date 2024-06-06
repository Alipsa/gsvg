package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals

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
}
