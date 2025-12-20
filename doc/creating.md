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
