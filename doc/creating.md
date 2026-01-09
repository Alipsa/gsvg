# Creating SVGs programmatically

```groovy
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgWriter

def svg = new Svg(240, 140)
svg.addRect(220, 120).x(10).y(10).rx(12).ry(12)
   .fill('#1976d2')
   .stroke('#0d47a1')
   .strokeWidth(3)
svg.addText('Hello SVG')
   .x(32).y(80)
   .fill('white')
   .fontSize(28)

// Serialize
println SvgWriter.toXmlPretty(svg)
```

## Coordinate system and sizing
You can set `width`/`height` with numbers or strings (percentages, units) and define a `viewBox` to scale drawing units.
```groovy
def svg = new Svg(400, 200).viewBox('0 0 200 100')
svg.addRect([x: 0, y: 0, width: '100%', height: '100%', fill: '#fafafa'])
```

## Attributes and convenience APIs
Most elements have fluent setters, but you can also use map-based factories and attribute helpers.
```groovy
def rect = svg.addRect([x: 10, y: 10, width: 80, height: 40, rx: 6])
rect.addAttributes(fill: '#e3f2fd', stroke: '#1565c0', strokeWidth: 2)
rect.opacity = 0.9 // falls back to setting the "opacity" attribute
```
Note that hyphenated attributes can be set with `addAttribute('stroke-width', 2)` or `addAttributes(strokeWidth: 2)`.

## Groups and transforms
Use `<g>` to apply shared attributes or transforms.
```groovy
def g = svg.addG().transform('translate(20,20) rotate(-2)')
g.addRect(80, 40).fill('#ffe0b2').stroke('#f57c00')
g.addText('Grouped').x(10).y(28).fontSize(12)
```

## Reuse with symbols and `use`
Define reusable content in `<defs>` and instantiate it with `<use>`.
```groovy
def defs = svg.addDefs()
def badge = defs.addSymbol('badge').viewBox('0 0 40 40')
badge.addCircle().cx(20).cy(20).r(18).fill('#ff7043')
badge.addText('1').x(20).y(25).textAnchor('middle').fontSize(16).fill('white')

svg.addUse().href('#badge').addAttributes(x: 10, y: 10, width: 40, height: 40)
svg.addUse().href('#badge').addAttributes(x: 60, y: 10, width: 40, height: 40)
```
If you need legacy compatibility, call `svg.xlink()` and use `xlinkHref(...)`.

## Gradients
```groovy
def svg = new Svg(200, 200)
def defs = svg.addDefs()
def lg = defs.addLinearGradient().id('grad1')
lg.addStop().addAttributes(offset: '0%', 'stop-color': '#42a5f5')
lg.addStop().addAttributes(offset: '100%', 'stop-color': '#1e88e5')

svg.addRect(200, 200).fill('url(#grad1)')
println SvgWriter.toXml(svg)
```

## Filters and markers
Filters and markers are defined in `<defs>` and referenced by URL.
```groovy
def svg = new Svg(200, 120)
def defs = svg.addDefs()
defs.addFilter('blur').addFeGaussianBlur().stdDeviation(2)
defs.addMarker('arrow')
  .markerWidth(10).markerHeight(10).refX(5).refY(5).orient('auto')
  .addPath().d('M 0 0 L 10 5 L 0 10 z').fill('#424242')

svg.addLine(10, 20, 180, 20).stroke('#424242').strokeWidth(2).markerEnd('url(#arrow)')
svg.addRect(160, 30).x(10).y(50).fill('#90caf9').filter('url(#blur)')
```

## Text and text paths
```groovy
def svg = new Svg(200, 120)
def label = svg.addText().x(10).y(24).fontSize(14)
label.addTspan('Hello ').fill('#333')
label.addTspan('SVG').fill('#1976d2')

svg.addPath('curve').d('M10,80 Q95,10 180,80').fill('none').stroke('#bdbdbd')
svg.addText().addTextPath().href('#curve').addContent('Text follows the curve')
```

## Foreign content and metadata
`foreignObject` lets you embed HTML/XML; use `addElement` to create namespaced elements.
```groovy
def svg = new Svg(200, 120)
def fo = svg.addForeignObject().x(10).y(10).width(180).height(60)
def div = fo.addElement('div').addContent('Hello ')
div.addElement('b').addContent('HTML')
```

## Scripts and events
```groovy
def svg = new Svg(120, 120)
svg.addCircle().cx(40).cy(40).r(20).fill('#ef5350').onClick('pulse(evt)')
svg.addScript().type('application/ecmascript').addCdataContent('''
function pulse(evt) {
  evt.target.setAttribute('r', 30);
}
''')
```

## Animation
```groovy
def svg = new Svg(120, 120)
svg.addCircle().cx(60).cy(60).r(30).fill('#009688')
   .addAnimateTransform()
   .attributeName('transform')
   .type('rotate')
   .from('0 60 60')
   .to('360 60 60')
   .dur('2s')
   .repeatCount('indefinite')

println SvgWriter.toXml(svg)
```

## Cloning for reuse
You can deep-clone any element into a new parent without XML serialization.
```groovy
def svg = new Svg(120, 60)
def icon = svg.addG().id('icon')
icon.addCircle().cx(10).cy(10).r(8).fill('#26a69a')

def copy = icon.clone(svg).transform('translate(30, 0)')
```
