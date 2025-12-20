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

## Navigating the SVG tree

Elements implement `ElementContainer`, so you can use:
- Index access: `svg[0]` (first child), or filtered: `svg[Rect]` to get all rects.
- Chaining with `getParent()`: many adders return the new element; call `.getParent(Svg)` (or `.parent` typed) to move back up.
- Names and attributes: `element.name`, `element.getAttribute('fill')`, `element.element` for raw dom4j access.

Examples:
```groovy
import se.alipsa.groovy.svg.Rect

def rects = svg[Rect]
rects.each { r ->
  println "Rect id=${r.id} fill=${r.fill}"
}

// Walk children recursively
def walk(element, depth = 0) {
  println "${'  '*depth}${element.name}"
  element.children.each { walk(it, depth + 1) }
}
walk(svg)
```

## Extracting data
- Attributes: `shape.fill`, `shape.getAttribute('stroke-width')`
- Text content: for text-like elements, `text.content` (from `StringContentContainer`)
- Namespaces: `element.element.getNamespaceURI()` or `element.getAttribute('xmlns')`
- Raw XML: `element.toXml()` for a fragment, `SvgWriter.toXml(svg)` for the whole document
