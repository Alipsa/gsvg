# Parsing and modifying existing SVGs

Parse from file, tweak, and write back:
```groovy
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

def svg = SvgReader.parse(new File('logo.svg'))
svg[Rect].each { it.stroke('red').strokeWidth(2) }
new File('logo-out.svg').text = SvgWriter.toXmlPretty(svg)
```

Parse from string/stream:
```groovy
def content = '<svg xmlns="http://www.w3.org/2000/svg"><circle cx="50" cy="50" r="20"/></svg>'
def svg = SvgReader.parse(content)
println svg.toXml() // round-trips the original
```

Handling `href` and `xlink:href`:
```groovy
def svg = SvgReader.parse('<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"><use xlink:href="#target"/></svg>')
def use = svg.children[0]
assert use.href == '#target'

// When creating new content, you can use plain href (SVG 2)
def fresh = new Svg().xlink()
fresh.addUse().href('#target')
println fresh.toXml()
```
