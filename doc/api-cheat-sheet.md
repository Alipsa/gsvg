# gsvg API Cheat Sheet

Quick reference for common gsvg operations.

## Table of Contents

- [Setup](#setup)
- [Basic Shapes](#basic-shapes)
- [Styling](#styling)
- [Transformations](#transformations)
- [Paths](#paths)
- [Text](#text)
- [Groups and Reuse](#groups-and-reuse)
- [Gradients and Patterns](#gradients-and-patterns)
- [Filters](#filters)
- [Accessibility](#accessibility)
- [Utilities](#utilities)
- [Validation](#validation)

## Setup

```groovy
// Import
import se.alipsa.groovy.svg.*

// Create SVG
Svg svg = new Svg(width, height)
Svg svg = new Svg()  // No dimensions

// With viewBox (responsive)
svg.viewBox('0 0 800 600')

// Parse from file/string
import se.alipsa.groovy.svg.io.SvgReader
Svg svg = SvgReader.parse(new File('input.svg'))
Svg svg = SvgReader.parse(xmlString)

// Output
String xml = svg.toXml()
new File('output.svg').text = xml
```

## Basic Shapes

```groovy
// Circle
svg.addCircle()
   .cx(100).cy(100).r(50)
   .fill('red').stroke('black').strokeWidth(2)

// Rectangle
svg.addRect()
   .x(10).y(10).width(100).height(50)
   .fill('blue').rx(5).ry(5)  // Rounded corners

// Ellipse
Ellipse e = svg.addEllipse()
e.cx(150).cy(100)
e.addAttribute('rx', 80)
e.addAttribute('ry', 40)
e.fill('yellow')

// Line
svg.addLine()
   .x1(0).y1(0).x2(100).y2(100)
   .stroke('green').strokeWidth(3)

// Polygon
svg.addPolygon('100,10 40,198 190,78 10,78 160,198')
   .fill('purple').stroke('black')

// Polyline
svg.addPolyline('20,20 40,25 60,40 80,120')
   .fill('none').stroke('red')
```

## Styling

```groovy
// Fill and Stroke
element.fill('red')
element.fill('#FF0000')
element.fill('rgb(255, 0, 0)')
element.stroke('black')
element.strokeWidth(3)

// Opacity
element.fillOpacity(0.5)
element.strokeOpacity(0.8)
element.opacity(0.7)

// Stroke properties
element.strokeLinecap('round')  // butt, round, square
element.strokeLinejoin('round')  // miter, round, bevel
element.strokeDasharray('5,5')  // Dashed line

// Using Color utility
import se.alipsa.groovy.svg.utils.Color

element.fill(Color.RED.toString())
element.fill(Color.rgb(255, 0, 0).toString())
element.fill(Color.hex('#FF0000').toString())
element.fill(Color.hsl(0, 100, 50).toString())

// Color manipulation
Color darker = Color.RED.darken(20)
Color lighter = Color.BLUE.lighten(30)
```

## Transformations

```groovy
// Rotate
element.rotate(45)
element.rotate(45, cx, cy)  // Around point

// Scale
element.scale(1.5)
element.scale(2, 0.5)  // scaleX, scaleY

// Translate
element.translate(50, 100)

// Skew
element.skewX(20)
element.skewY(15)

// Chaining
element.translate(50, 50).rotate(45).scale(1.5)

// Manual transform
element.transform('rotate(45) translate(10, 20)')
```

## Paths

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

// Basic path
PathBuilder path = PathBuilder.moveTo(10, 10)
    .L(90, 10)   // Line to (absolute)
    .L(90, 90)
    .L(10, 90)
    .Z()         // Close path

svg.addPath().d(path).fill('blue')

// Relative commands (lowercase)
PathBuilder rel = PathBuilder.moveTo(10, 10)
    .l(80, 0)    // Relative line to
    .l(0, 80)
    .l(-80, 0)
    .Z()

// Curves
PathBuilder curve = PathBuilder.moveTo(10, 50)
    .Q(50, 10, 90, 50)  // Quadratic bezier
    .C(100, 80, 120, 80, 150, 50)  // Cubic bezier

// Arcs
PathBuilder arc = PathBuilder.moveTo(50, 50)
    .A(40, 40, 0, 0, 1, 150, 50)  // rx, ry, rotation, large-arc, sweep, x, y

// Horizontal/Vertical lines
PathBuilder hv = PathBuilder.moveTo(10, 10)
    .H(100)  // Horizontal to x=100
    .V(100)  // Vertical to y=100
```

## Text

```groovy
// Basic text
svg.addText('Hello World')
   .x(50).y(50)
   .fontSize(24)
   .fill('black')

// With font
Text t = svg.addText()
t.addContent('Styled Text')
t.x(50).y(50).fontSize(18)
t.fontFamily('Arial, sans-serif')
t.addAttribute('font-weight', 'bold')
t.addAttribute('font-style', 'italic')

// Text anchor
t.textAnchor('middle')  // start, middle, end

// Multiline with TSpan
Text text = svg.addText().x(50).y(50)
text.addTSpan('Line 1')
text.addTSpan('Line 2').x(50).dy(25)
text.addTSpan('Line 3').x(50).dy(25)

// Text on path
Defs defs = svg.addDefs()
Path curve = defs.addPath()
curve.id('curve1').d('M 50 100 Q 150 50, 250 100')

Text pathText = svg.addText()
TextPath tp = pathText.addTextPath()
tp.href('#curve1')
tp.content = 'Text on curve!'
```

## Groups and Reuse

```groovy
// Group
G group = svg.addG()
group.fill('blue').stroke('black')
group.addCircle().cx(50).cy(50).r(20)
group.addRect().x(100).y(30).width(40).height(40)

// Definitions and reuse
Defs defs = svg.addDefs()
Circle template = defs.addCircle()
template.id('dot').r(5).fill('red')

svg.addUse('#dot').x(50).y(50)
svg.addUse('#dot').x(100).y(50)

// Clone
Svg copy = svg.clone()

// Clone with modifications
Circle original = svg.addCircle().cx(100).cy(100).r(50).fill('red')
Circle modified = original.cloneWith(svg, [fill: 'blue', r: 75])
```

## Gradients and Patterns

```groovy
Defs defs = svg.addDefs()

// Linear gradient
LinearGradient lg = defs.addLinearGradient('grad1')
lg.x1('0%').y1('0%').x2('100%').y2('0%')
lg.addStop().offset('0%').stopColor('red')
lg.addStop().offset('100%').stopColor('blue')

svg.addRect().fill('url(#grad1)')

// Radial gradient
RadialGradient rg = defs.addRadialGradient('grad2')
rg.cx('50%').cy('50%').r('50%')
rg.addStop().offset('0%').stopColor('white')
rg.addStop().offset('100%').stopColor('black')

// Pattern
Pattern pattern = defs.addPattern('dots')
pattern.x(0).y(0).width(20).height(20)
pattern.patternUnits('userSpaceOnUse')
pattern.addCircle().cx(10).cy(10).r(5).fill('blue')

svg.addRect().fill('url(#dots)')
```

## Filters

```groovy
import se.alipsa.groovy.svg.effects.Effects

Defs defs = svg.addDefs()

// Drop shadow
Filter shadow = Effects.dropShadow(defs,
    id: 'shadow1',
    dx: 3, dy: 3,
    blur: 5,
    opacity: 0.6)

element.filter('url(#shadow1)')

// Glow
Filter glow = Effects.glow(defs,
    id: 'glow1',
    color: 'yellow',
    blur: 5,
    strength: 3)

// Blur
Filter blur = Effects.blur(defs, 5.0, 'blur1')

// Color effects
Filter gray = Effects.grayscale(defs, 'gray1')
Filter sepia = Effects.sepia(defs, 'sepia1')
Filter bright = Effects.brightness(defs, amount: 1.5)
Filter contrast = Effects.contrast(defs, amount: 2.0)

// Apply filter
Effects.applyFilter(element, shadow)
// Or: element.filter('url(#shadow1)')
```

## Accessibility

```groovy
// Make SVG accessible
svg.role('img')
   .ariaLabel('Description of the graphic')

// Or with title/description
svg.addTitle('Chart Title')
svg.addDesc('Detailed description of the chart content')

// Reference title/desc
svg.role('img')
   .ariaLabelledBy('title1')
   .ariaDescribedBy('desc1')

svg.addTitle('Title').id('title1')
svg.addDesc('Description').id('desc1')

// Interactive elements
element.ariaLabel('Click for details')
element.addAttribute('tabindex', '0')

// Decorative elements
element.decorative()  // Sets role='presentation' + aria-hidden='true'

// Shorthand
element.accessibleImage('Description')  // Sets role='img' + aria-label

// ARIA attributes
element.role('button')
element.ariaLive('polite')
element.ariaHidden(true)
```

## Utilities

```groovy
// Numeric precision
import se.alipsa.groovy.svg.utils.NumberFormatter

NumberFormatter.setDefaultPrecision(2)  // Global
svg.setMaxPrecision(3)  // Per-document
NumberFormatter.resetPrecision()  // Back to default (3)

// ViewBox
import se.alipsa.groovy.svg.utils.ViewBox

ViewBox vb = new ViewBox(0, 0, 800, 600)
svg.viewBox(vb)

// Merging SVGs
import se.alipsa.groovy.svg.utils.SvgMerger

Svg merged = SvgMerger.mergeHorizontally([svg1, svg2])
Svg merged = SvgMerger.mergeVertically([svg1, svg2])
Svg merged = SvgMerger.mergeOnTop([background, foreground])

// Shape Presets (via Shapes utility)
import se.alipsa.groovy.svg.presets.Shapes

Rect rounded = Shapes.roundedRect(svg, x: 10, y: 10, width: 100, height: 60, radius: 10)
Polygon star = Shapes.star(svg, cx: 100, cy: 100, points: 5, outerRadius: 50)
Path arrow = Shapes.arrow(svg, x1: 10, y1: 50, x2: 100, y2: 50, headSize: 15)
Polygon hex = Shapes.regularPolygon(svg, cx: 100, cy: 100, sides: 6, radius: 50)
Path bubble = Shapes.speechBubble(svg, x: 10, y: 10, width: 100, height: 60, tailX: 50, tailY: 80)

// Shape Factory Methods (convenience methods on Svg)
Rect rounded = svg.createRoundedRect(x: 10, y: 10, width: 100, height: 60, radius: 10)
Polygon star = svg.createStar(cx: 100, cy: 100, points: 5, outerRadius: 50, innerRadius: 25)
Path arrow = svg.createArrow(x1: 10, y1: 50, x2: 100, y2: 50, headSize: 15)
Polygon hex = svg.createRegularPolygon(cx: 100, cy: 100, sides: 6, radius: 50)
Path bubble = svg.createSpeechBubble(x: 10, y: 10, width: 100, height: 60, tailX: 50, tailY: 80)

// Null-safe attribute access
String x = rect.getAttributeOrDefault('x', '0')  // Returns '0' if x is not set
String fill = circle.getAttributeOrDefault('fill', 'black')  // Returns 'black' if fill is not set
String stroke = element.getAttributeOrDefault('stroke', 'none')  // Prevents NullPointerException

// Templates
import se.alipsa.groovy.svg.templates.ChartLegend

ChartLegend legend = new ChartLegend()
legend.apply(svg, [
    x: 450,
    y: 50,
    items: [
        [color: 'red', label: 'Series 1'],
        [color: 'blue', label: 'Series 2']
    ]
])
```

## Validation

```groovy
import se.alipsa.groovy.svg.validation.*

// Quick validation
boolean isValid = svg.isValid()

// Detailed validation
ValidationEngine engine = ValidationEngine.createDefault()
ValidationReport report = engine.validate(svg)

if (!report.isValid()) {
    report.errors.each { println "ERROR: ${it.message}" }
}
if (report.hasWarnings()) {
    report.warnings.each { println "WARNING: ${it.message}" }
}

// Accessibility-only validation
ValidationEngine accEngine = ValidationEngine.createAccessibility()
ValidationReport accReport = accEngine.validate(svg)

// Custom validation
ValidationEngine custom = new ValidationEngine()
custom.addRule(new MyCustomRule())
ValidationReport customReport = custom.validate(svg)
```

## DSL Configuration

```groovy
// DSL-style element creation
svg.addCircle {
    cx 100
    cy 100
    r 50
    fill 'red'
    stroke 'black'
    strokeWidth 2
}

svg.addRect {
    x 10
    y 10
    width 100
    height 50
    fill 'blue'
}

svg.addG {
    fill 'green'
    stroke 'darkgreen'

    // Add children within closure
    addCircle().cx(50).cy(50).r(20)
    addRect().x(100).y(30).width(40).height(40)
}

svg.addText {
    addContent 'Hello!'
    x 50
    y 50
    fontSize 24
    fill 'purple'
}
```

## Selection and Querying

```groovy
// By type
def circles = svg[Circle]
def rects = svg[Rect]

// By name
def circleElements = svg['circle']

// By index
def first = svg[0]

// Filter
def largeCircles = svg.filter { it instanceof Circle && it.getR() as int > 20 }

// Find
def firstRed = svg.findFirst { it.getFill() == 'red' }
def allBlue = svg.findAll(Rect) { it.getFill() == 'blue' }

// Descendants (recursive)
def allCircles = svg.descendants(Circle)

// XPath
def redCircles = svg.xpath('//circle[@fill="red"]')

// Counting
int count = svg.count { it instanceof Circle }
boolean hasCircles = svg.any { it instanceof Circle }
boolean allHaveFill = svg.all { it.getFill() != null }

// Collect
def ids = svg.collect { it.getId() }
```

## Common Patterns

```groovy
// Save SVG
new File('output.svg').text = svg.toXml()

// Read SVG
Svg svg = SvgReader.parse(new File('input.svg'))

// Modify existing SVG
svg[Circle].each { it.fill('red') }

// Add to existing SVG
svg.addRect().x(10).y(10).width(100).height(50)

// Clone and modify
Svg copy = svg.clone()
copy[Circle].each { it.fill('blue') }

// Merge multiple SVGs
def combined = SvgMerger.mergeHorizontally([svg1, svg2, svg3])

// Export with styling
Defs defs = svg.addDefs()
Style style = defs.addStyle()
style.type('text/css')
style.addCData('''
    .highlight { fill: yellow; }
    .border { stroke: black; stroke-width: 2; }
''')
```

---

For detailed documentation, see:
- [Creating SVG Elements](creating.md)
- [Examples](examples.md)
- [Cookbook](cookbook.md)
- [Best Practices](best-practices.md)
