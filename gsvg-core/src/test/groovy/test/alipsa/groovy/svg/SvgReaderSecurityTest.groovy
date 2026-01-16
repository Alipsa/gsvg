package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import org.xml.sax.SAXException
import se.alipsa.groovy.svg.io.SvgReader

import static org.junit.jupiter.api.Assertions.assertThrows

class SvgReaderSecurityTest {

  @Test
  void disallowExternalEntities() {
    String payload = '''<!DOCTYPE svg [
<!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<svg xmlns="http://www.w3.org/2000/svg">
  <title>&xxe;</title>
</svg>'''

    assertThrows(SAXException) {
      SvgReader.parse(payload)
    }
  }
}
