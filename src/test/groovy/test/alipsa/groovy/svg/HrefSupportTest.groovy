package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.Use
import se.alipsa.groovy.svg.io.SvgReader

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class HrefSupportTest {

  @Test
  void supportsPlainAndXlinkHref() {
    String xlinkSvg = '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"><use xlink:href="#target"/></svg>'
    Svg parsedXlink = SvgReader.parse(xlinkSvg)
    Use useWithXlink = parsedXlink.children[0] as Use
    assertEquals('#target', useWithXlink.href)
    assertEquals('#target', useWithXlink.xlinkHref)

    Svg svg = new Svg().xlink()
    svg.addUse().href('#target')
    String output = svg.toXml()
    assertTrue(output.contains('href="#target"'))
  }
}
