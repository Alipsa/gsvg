# gsvg Examples

This guide provides comprehensive code examples demonstrating the various features of gsvg.

## Example Gallery

If you want runnable scripts, see the gsvg-examples module:
- Source tree: `gsvg-examples/src/main/groovy/examples`
- Catalog: `gsvg-examples/src/main/resources/examples/catalog.md`
- Output target (when run manually): `gsvg-examples/target/examples-output`

## Table of Contents

- [Basic Shapes](#basic-shapes)
- [Styling and Colors](#styling-and-colors)
- [Gradients](#gradients)
- [Filters and Effects](#filters-and-effects)
- [Text](#text)
- [Paths](#paths)
- [Transformations](#transformations)
- [Groups and Reuse](#groups-and-reuse)
- [Accessibility](#accessibility)
- [Charts and Graphs](#charts-and-graphs)
- [Shape Factory Methods](#shape-factory-methods)
- [Advanced Patterns](#advanced-patterns)
- [Performance Optimization](#performance-optimization)

## Basic Shapes

### Creating a Simple Circle

```groovy
import se.alipsa.groovy.svg.Svg

Svg svg = new Svg(200, 200)
svg.addCircle()
   .cx(100)
   .cy(100)
   .r(50)
   .fill('red')
   .stroke('black')
   .strokeWidth(2)

println svg.toXmlPretty()
```

### Rectangle with Rounded Corners

```groovy
import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.presets.Shapes

Svg svg = new Svg(300, 200)

// Method 1: Using convenience factory method (recommended)
Rect rounded = svg.createRoundedRect(
    x: 10, y: 10,
    width: 100, height: 60,
    radius: 10)
rounded.fill('lightblue')

// Method 2: Using Shapes utility directly
Rect rounded2 = Shapes.roundedRect(svg,
    x: 120, y: 10,
    width: 100, height: 60,
    radius: 10)
rounded2.fill('lightgreen')

// Method 3: Manual configuration
svg.addRect()
   .x(230).y(10)
   .width(100).height(60)
   .rx(10).ry(10)
   .fill('lightyellow')
```

### Ellipse

```groovy
Svg svg = new Svg(200, 150)
svg.addEllipse()
   .cx(100).cy(75)
   .rx(80).ry(40)
   .fill('yellow')
   .stroke('orange')
   .strokeWidth(3)
```

### Line

```groovy
Svg svg = new Svg(200, 100)
svg.addLine()
   .x1(10).y1(50)
   .x2(190).y2(50)
   .stroke('blue')
   .strokeWidth(5)
   .strokeLinecap('round')
```

### Polygon (Triangle)

```groovy
Svg svg = new Svg(200, 200)
svg.addPolygon('100,10 50,150 150,150')
   .fill('purple')
   .stroke('indigo')
   .strokeWidth(2)
```

### Star Shape

```groovy
import se.alipsa.groovy.svg.*

Svg svg = new Svg(300, 300)

// Using convenience factory method
Polygon star = svg.createStar(
    cx: 150, cy: 150,
    points: 5,
    outerRadius: 100,
    innerRadius: 40)
star.fill('gold').stroke('orange').strokeWidth(2)

// Create different star shapes
Polygon star6 = svg.createStar(cx: 150, cy: 150, points: 6, outerRadius: 80)
    .fill('yellow')
```

## Styling and Colors

### Using Color Utility

```groovy
import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.utils.Color

Svg svg = new Svg(400, 200)

// Named colors
svg.addRect().x(10).y(10).width(80).height(80)
   .fill(Color.CRIMSON)

// RGB
Color blue = Color.rgb(0, 100, 200)
svg.addRect().x(100).y(10).width(80).height(80)
   .fill(blue.toString())

// Hex colors
Color green = Color.hex('#00ff00')
svg.addRect().x(190).y(10).width(80).height(80)
   .fill(green.toString())

// HSL
Color purple = Color.hsl(280, 100, 50)
svg.addRect().x(280).y(10).width(80).height(80)
   .fill(purple.toString())

// Color with opacity
svg.addCircle().cx(50).cy(150).r(30)
   .fill(Color.RED.toString())
   .fillOpacity(0.5)

// Lighten/Darken
Color darkBlue = blue.darken(20)
svg.addCircle().cx(150).cy(150).r(30)
   .fill(darkBlue.toString())
```

### Stroke Styles

```groovy
Svg svg = new Svg(300, 300)

// Dashed line
svg.addLine().x1(10).y1(50).x2(290).y2(50)
   .stroke('black').strokeWidth(2)
   .strokeDasharray('5,5')

// Dotted line
svg.addLine().x1(10).y1(100).x2(290).y2(100)
   .stroke('black').strokeWidth(2)
   .strokeDasharray('2,3')

// Different line caps
svg.addLine().x1(10).y1(150).x2(290).y2(150)
   .stroke('red').strokeWidth(10)
   .strokeLinecap('round')

svg.addLine().x1(10).y1(200).x2(290).y2(200)
   .stroke('blue').strokeWidth(10)
   .strokeLinecap('square')

// Line join styles
Path path = svg.addPath()
   .d('M 50 250 L 150 250 L 150 280')
   .fill('none')
   .stroke('green').strokeWidth(8)
   .strokeLinejoin('round')
```

## Gradients

### Linear Gradient

```groovy
Svg svg = new Svg(400, 200)
Defs defs = svg.addDefs()

LinearGradient gradient = defs.addLinearGradient('gradient1')
gradient.x1('0%').y1('0%').x2('100%').y2('0%')
gradient.addStop().offset('0%').stopColor('red')
gradient.addStop().offset('50%').stopColor('yellow')
gradient.addStop().offset('100%').stopColor('green')

svg.addRect()
   .x(50).y(50)
   .width(300).height(100)
   .fill('url(#gradient1)')
```

### Radial Gradient

```groovy
Svg svg = new Svg(400, 400)
Defs defs = svg.addDefs()

RadialGradient gradient = defs.addRadialGradient('radial1')
gradient.cx('50%').cy('50%').r('50%')
gradient.addStop().offset('0%').stopColor('white')
gradient.addStop().offset('100%').stopColor('blue')

svg.addCircle()
   .cx(200).cy(200).r(150)
   .fill('url(#radial1)')
```

### Gradient with Opacity

```groovy
Svg svg = new Svg(400, 200)
Defs defs = svg.addDefs()

LinearGradient gradient = defs.addLinearGradient('fadeGradient')
gradient.x1('0%').y1('0%').x2('100%').y2('0%')
gradient.addStop().offset('0%').stopColor('black').stopOpacity(1)
gradient.addStop().offset('100%').stopColor('black').stopOpacity(0)

svg.addRect()
   .width(400).height(200)
   .fill('url(#fadeGradient)')
```

## Filters and Effects

### Drop Shadow

```groovy
import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.effects.Effects

Svg svg = new Svg(300, 300)
Defs defs = svg.addDefs()

// Create drop shadow filter
Filter shadow = Effects.dropShadow(defs,
    id: 'shadow1',
    dx: 3, dy: 3,
    blur: 5,
    opacity: 0.6,
    color: 'black')

// Apply to element
svg.addRect()
   .x(50).y(50)
   .width(150).height(100)
   .fill('lightblue')
   .filter('url(#shadow1)')
```

### Glow Effect

```groovy
import se.alipsa.groovy.svg.effects.Effects

Svg svg = new Svg(400, 300)
Defs defs = svg.addDefs()

Filter glow = Effects.glow(defs,
    id: 'glow1',
    color: 'yellow',
    blur: 5,
    strength: 3)

svg.addText('Glowing Text!')
   .x(100).y(150)
   .fontSize(48)
   .fontWeight('bold')
   .fill('orange')
   .filter('url(#glow1)')
```

### Blur

```groovy
import se.alipsa.groovy.svg.effects.Effects

Svg svg = new Svg(400, 200)
Defs defs = svg.addDefs()

Filter blur = Effects.blur(defs, 5.0, 'blur1')

// Sharp image
svg.addRect().x(50).y(50).width(100).height(100).fill('red')

// Blurred image
svg.addRect().x(200).y(50).width(100).height(100).fill('red')
   .filter('url(#blur1)')
```

### Grayscale and Sepia

```groovy
import se.alipsa.groovy.svg.effects.Effects

Svg svg = new Svg(600, 200)
Defs defs = svg.addDefs()

Filter grayscale = Effects.grayscale(defs, 'gray1')
Filter sepia = Effects.sepia(defs, 'sepia1')

// Original
svg.addCircle().cx(100).cy(100).r(50).fill('red')

// Grayscale
svg.addCircle().cx(300).cy(100).r(50).fill('red')
   .filter('url(#gray1)')

// Sepia
svg.addCircle().cx(500).cy(100).r(50).fill('red')
   .filter('url(#sepia1)')
```

### Brightness and Contrast

```groovy
import se.alipsa.groovy.svg.effects.Effects

Svg svg = new Svg(600, 200)
Defs defs = svg.addDefs()

Filter bright = Effects.brightness(defs, id: 'bright1', amount: 1.8)
Filter contrast = Effects.contrast(defs, id: 'contrast1', amount: 2.0)

// Original
svg.addRect().x(50).y(50).width(100).height(100).fill('blue')

// Brighter
svg.addRect().x(250).y(50).width(100).height(100).fill('blue')
   .filter('url(#bright1)')

// Higher contrast
svg.addRect().x(450).y(50).width(100).height(100).fill('blue')
   .filter('url(#contrast1)')
```

## Text

### Basic Text

```groovy
Svg svg = new Svg(400, 200)

svg.addText('Hello, SVG!')
   .x(100).y(100)
   .fontSize(32)
   .fill('black')
   .fontFamily('Arial, sans-serif')
```

### Styled Text

```groovy
Svg svg = new Svg(400, 300)

// Bold text
svg.addText('Bold Text')
   .x(50).y(50)
   .fontSize(24)
   .fontWeight('bold')

// Italic text
svg.addText('Italic Text')
   .x(50).y(100)
   .fontSize(24)
   .fontStyle('italic')

// Colored text with stroke
svg.addText('Outlined Text')
   .x(50).y(150)
   .fontSize(32)
   .fill('yellow')
   .stroke('black')
   .strokeWidth(1)

// Text anchor (alignment)
svg.addText('Centered')
   .x(200).y(200)
   .fontSize(24)
   .textAnchor('middle')
```

### Multiline Text with TSpan

```groovy
Svg svg = new Svg(400, 200)

Text text = svg.addText().x(50).y(50).fontSize(20)
text.addTSpan('First line')
text.addTSpan('Second line').x(50).dy(25)
text.addTSpan('Third line').x(50).dy(25)
```

### Text on a Path

```groovy
Svg svg = new Svg(500, 300)
Defs defs = svg.addDefs()

// Define a path
Path curvePath = defs.addPath()
curvePath.id('textPath1')
curvePath.d('M 50 150 Q 250 50, 450 150')

// Draw the path for reference
svg.addPath()
   .d('M 50 150 Q 250 50, 450 150')
   .fill('none').stroke('lightgray').strokeWidth(1)

// Text along the path
Text text = svg.addText().fontSize(24).fill('blue')
TextPath textPath = text.addTextPath()
textPath.href('#textPath1')
textPath.content = 'Text follows this curved path!'
```

## Paths

### Using PathBuilder

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

Svg svg = new Svg(300, 300)

// Build a path with fluent API
PathBuilder builder = PathBuilder.moveTo(50, 50)
    .L(150, 50)        // Line to
    .L(150, 150)
    .L(50, 150)
    .Z()               // Close path

svg.addPath().d(builder).fill('lightgreen').stroke('darkgreen').strokeWidth(2)
```

### Bezier Curves

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

Svg svg = new Svg(400, 300)

// Quadratic bezier
PathBuilder quad = PathBuilder.moveTo(50, 150)
    .Q(200, 50, 350, 150)

svg.addPath().d(quad).fill('none').stroke('blue').strokeWidth(3)

// Cubic bezier
PathBuilder cubic = PathBuilder.moveTo(50, 250)
    .C(100, 200, 300, 200, 350, 250)

svg.addPath().d(cubic).fill('none').stroke('red').strokeWidth(3)
```

### Arcs

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

Svg svg = new Svg(400, 300)

PathBuilder arc = PathBuilder.moveTo(100, 150)
    .A(50, 50, 0, 0, 1, 200, 150)  // rx, ry, rotation, large-arc, sweep, x, y

svg.addPath().d(arc).fill('none').stroke('purple').strokeWidth(3)
```

### Complex Shape (Heart)

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

Svg svg = new Svg(200, 200)

PathBuilder heart = PathBuilder.moveTo(100, 140)
    .c(-20, -40, -60, -40, -60, -80)
    .c(0, -30, 20, -50, 40, -50)
    .c(15, 0, 30, 10, 40, 25)
    .c(10, -15, 25, -25, 40, -25)
    .c(20, 0, 40, 20, 40, 50)
    .c(0, 40, -40, 40, -60, 80)
    .Z()

svg.addPath().d(heart).fill('red').stroke('darkred').strokeWidth(2)
```

### Arrow

```groovy
import se.alipsa.groovy.svg.presets.Shapes

Svg svg = new Svg(300, 150)

Path arrow = Shapes.arrow(svg,
    x1: 50, y1: 75,
    x2: 250, y2: 75,
    headSize: 15,
    headAngle: 30)
arrow.stroke('black').strokeWidth(3).fill('none')
```

## Transformations

### Rotate

```groovy
Svg svg = new Svg(300, 300)

// Rotate around origin
svg.addRect()
   .x(100).y(50)
   .width(80).height(40)
   .fill('blue')
   .rotate(45)

// Rotate around center point
svg.addRect()
   .x(100).y(150)
   .width(80).height(40)
   .fill('red')
   .rotate(45, 140, 170)  // angle, cx, cy
```

### Scale

```groovy
Svg svg = new Svg(400, 300)

// Original
svg.addRect().x(50).y(50).width(50).height(50).fill('green')

// Scaled uniformly
svg.addRect().x(150).y(50).width(50).height(50).fill('green')
   .scale(1.5)

// Scaled non-uniformly
svg.addRect().x(300).y(50).width(50).height(50).fill('green')
   .scale(1.5, 0.8)
```

### Translate

```groovy
Svg svg = new Svg(300, 200)

svg.addCircle()
   .cx(50).cy(50).r(25)
   .fill('purple')
   .translate(100, 50)
```

### Skew

```groovy
Svg svg = new Svg(300, 300)

// Original
svg.addRect().x(50).y(50).width(80).height(80).fill('orange')

// Skewed horizontally
svg.addRect().x(50).y(150).width(80).height(80).fill('orange')
   .skewX(20)

// Skewed vertically
svg.addRect().x(150).y(50).width(80).height(80).fill('orange')
   .skewY(20)
```

### Chaining Transformations

```groovy
Svg svg = new Svg(300, 300)

svg.addRect()
   .x(100).y(100)
   .width(50).height(50)
   .fill('teal')
   .translate(50, 50)
   .rotate(45)
   .scale(1.5)
```

## Groups and Reuse

### Grouping Elements

```groovy
Svg svg = new Svg(400, 300)

G group = svg.addG()
group.fill('blue').stroke('navy').strokeWidth(2)

// Elements inherit group attributes
group.addCircle().cx(100).cy(100).r(40)
group.addRect().x(200).y(60).width(80).height(80)
```

### Reusable Definitions

```groovy
Svg svg = new Svg(600, 200)
Defs defs = svg.addDefs()

// Define a reusable shape
G icon = defs.addG()
icon.id('smiley')
icon.addCircle().r(50).fill('yellow').stroke('black').strokeWidth(2)
icon.addCircle().cx(-15).cy(-10).r(5).fill('black')  // Left eye
icon.addCircle().cx(15).cy(-10).r(5).fill('black')   // Right eye
icon.addPath().d('M -20 10 Q 0 25, 20 10')
   .fill('none').stroke('black').strokeWidth(2)      // Smile

// Use the icon multiple times
Use use1 = svg.addUse('#smiley')
use1.x(100).y(100)

Use use2 = svg.addUse('#smiley')
use2.x(300).y(100).transform('scale(0.5)')

Use use3 = svg.addUse('#smiley')
use3.x(500).y(100).transform('scale(1.5)')
```

### Clipping Path

```groovy
Svg svg = new Svg(400, 300)
Defs defs = svg.addDefs()

// Define clipping path (circle)
ClipPath clip = defs.addClipPath('clip1')
clip.addCircle().cx(200).cy(150).r(100)

// Image clipped to circle
svg.addRect()
   .x(100).y(50)
   .width(200).height(200)
   .fill('red')
   .clipPath('url(#clip1)')
```

### Pattern Fill

```groovy
Svg svg = new Svg(400, 300)
Defs defs = svg.addDefs()

// Define pattern
Pattern pattern = defs.addPattern('dots')
pattern.x(0).y(0).width(20).height(20)
pattern.patternUnits('userSpaceOnUse')
pattern.addCircle().cx(10).cy(10).r(5).fill('blue')

// Use pattern as fill
svg.addRect()
   .x(50).y(50)
   .width(300).height(200)
   .fill('url(#dots)')
   .stroke('black')
```

### Cloning with Modifications

```groovy
Svg svg = new Svg(400, 200)

// Create an original element
Circle original = svg.addCircle()
    .cx(100).cy(100).r(50)
    .fill('red')
    .stroke('black')
    .strokeWidth(2)

// Clone with modifications
Circle clone1 = original.cloneWith(svg, [fill: 'blue', cx: 250])
Circle clone2 = original.cloneWith(svg, [fill: 'green', r: 75, cx: 400])

// Original remains unchanged
assert original.getFill() == 'red'
assert original.getCx() == '100'

// Clones have modifications applied
assert clone1.getFill() == 'blue'
assert clone1.getCx() == '250'
assert clone2.getFill() == 'green'
assert clone2.getR() == '75'
```

### Null-Safe Attribute Access

```groovy
Svg svg = new Svg(200, 200)
Circle circle = svg.addCircle().cx(100).cy(100).r(50)

// Safe access with defaults - no NullPointerException
String fill = circle.getAttributeOrDefault('fill', 'black')      // Returns 'black'
String stroke = circle.getAttributeOrDefault('stroke', 'none')   // Returns 'none'
String cx = circle.getAttributeOrDefault('cx', '0')              // Returns '100'

// Apply defaults safely
circle.fill(circle.getAttributeOrDefault('fill', 'red'))
circle.stroke(circle.getAttributeOrDefault('stroke', 'blue'))
```

## Accessibility

### Accessible Image

```groovy
Svg svg = new Svg(200, 200)
svg.role('img')
   .ariaLabel('A red circle representing a stop sign')

svg.addCircle()
   .cx(100).cy(100).r(80)
   .fill('red')
```

### With Title and Description

```groovy
Svg svg = new Svg(400, 300)
svg.addTitle('Monthly Sales Chart')
svg.addDesc('A bar chart showing sales for January ($50K), February ($75K), and March ($60K)')

// Add chart elements...
svg.addRect().x(50).y(150).width(50).height(100).fill('blue')
svg.addRect().x(150).y(100).width(50).height(150).fill('blue')
svg.addRect().x(250).y(120).width(50).height(130).fill('blue')
```

### Interactive Accessible Elements

```groovy
Svg svg = new Svg(300, 200)
svg.role('graphics-document')
   .ariaLabel('Interactive dashboard')

// Accessible link
A link = svg.addA()
link.href = '/report.pdf'
link.ariaLabel('Download PDF report')
link.addRect().x(100).y(75).width(100).height(50).fill('blue').rx(5)
link.addText('Download').x(150).y(105).fill('white').textAnchor('middle')
```

### Decorative Elements

```groovy
Svg svg = new Svg(400, 300)
svg.role('img').ariaLabel('Product diagram')

// Main content
svg.addRect().x(100).y(100).width(200).height(100).fill('lightblue')

// Decorative border (hidden from screen readers)
svg.addRect()
   .x(95).y(95).width(210).height(110)
   .fill('none').stroke('gold').strokeWidth(5)
   .decorative()
```

## Charts and Graphs

### Bar Chart

```groovy
Svg svg = new Svg(500, 400)
svg.role('img').ariaLabel('2024 Quarterly Sales Bar Chart')

// Title
svg.addText('Quarterly Sales 2024')
   .x(250).y(30)
   .fontSize(20)
   .fontWeight('bold')
   .textAnchor('middle')

// Data
def data = [
    [label: 'Q1', value: 50000],
    [label: 'Q2', value: 75000],
    [label: 'Q3', value: 60000],
    [label: 'Q4', value: 90000]
]

// Scales
int maxValue = 100000
int chartHeight = 300
int chartWidth = 400
int barWidth = 60
int startX = 50
int startY = 350

// Draw bars
data.eachWithIndex { item, i ->
    int barHeight = (item.value / maxValue * chartHeight) as int
    int x = startX + (i * (barWidth + 30))
    int y = startY - barHeight

    // Bar
    svg.addRect()
       .x(x).y(y)
       .width(barWidth).height(barHeight)
       .fill('steelblue')

    // Label
    svg.addText(item.label)
       .x(x + barWidth/2).y(startY + 20)
       .textAnchor('middle')

    // Value
    svg.addText("\$${item.value/1000}K")
       .x(x + barWidth/2).y(y - 5)
       .fontSize(12)
       .textAnchor('middle')
}

// Axis
svg.addLine()
   .x1(startX - 10).y1(startY)
   .x2(startX + chartWidth).y2(startY)
   .stroke('black').strokeWidth(2)
```

### Pie Chart

```groovy
import se.alipsa.groovy.svg.utils.Color

Svg svg = new Svg(500, 500)
svg.role('img').ariaLabel('Market Share Pie Chart')

// Title
svg.addText('Market Share')
   .x(250).y(30)
   .fontSize(20)
   .fontWeight('bold')
   .textAnchor('middle')

// Data
def data = [
    [label: 'Product A', value: 35, color: Color.hex('#FF6B6B')],
    [label: 'Product B', value: 25, color: Color.hex('#4ECDC4')],
    [label: 'Product C', value: 20, color: Color.hex('#45B7D1')],
    [label: 'Product D', value: 20, color: Color.hex('#FFA07A')]
]

// Calculate angles
int total = data.sum { it.value } as int
double currentAngle = -Math.PI / 2  // Start at top
int cx = 250
int cy = 250
int radius = 150

data.each { item ->
    double angle = (item.value / total) * 2 * Math.PI
    double endAngle = currentAngle + angle

    // Calculate arc endpoints
    double x1 = cx + radius * Math.cos(currentAngle)
    double y1 = cy + radius * Math.sin(currentAngle)
    double x2 = cx + radius * Math.cos(endAngle)
    double y2 = cy + radius * Math.sin(endAngle)

    // Large arc flag
    int largeArc = (angle > Math.PI) ? 1 : 0

    // Create slice path
    String pathData = "M ${cx} ${cy} L ${x1} ${y1} A ${radius} ${radius} 0 ${largeArc} 1 ${x2} ${y2} Z"
    svg.addPath()
       .d(pathData)
       .fill(item.color.toString())
       .stroke('white')
       .strokeWidth(2)

    // Label at midpoint
    double midAngle = currentAngle + angle / 2
    double labelX = cx + (radius * 0.7) * Math.cos(midAngle)
    double labelY = cy + (radius * 0.7) * Math.sin(midAngle)

    svg.addText("${item.value}%")
       .x(labelX as int).y(labelY as int)
       .textAnchor('middle')
       .fill('white')
       .fontWeight('bold')

    currentAngle = endAngle
}
```

### Line Graph

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

Svg svg = new Svg(600, 400)
svg.role('img').ariaLabel('Temperature trend line graph')

// Data points
def data = [10, 15, 13, 17, 20, 18, 22, 25, 23, 20, 15, 12]
def labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
              'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']

// Scale
int width = 500
int height = 300
int marginX = 50
int marginY = 50
int maxValue = 30

// Create line path
PathBuilder line = PathBuilder.create()
data.eachWithIndex { value, i ->
    int x = marginX + (i * (width / (data.size() - 1)))
    int y = marginY + height - ((value / maxValue) * height) as int

    if (i == 0) {
        line.M(x, y)
    } else {
        line.L(x, y)
    }

    // Data point
    svg.addCircle()
       .cx(x).cy(y).r(4)
       .fill('steelblue')

    // Label
    svg.addText(labels[i])
       .x(x).y(marginY + height + 20)
       .fontSize(10)
       .textAnchor('middle')
}

// Draw line
svg.addPath()
   .d(line)
   .fill('none')
   .stroke('steelblue')
   .strokeWidth(2)

// Axes
svg.addLine()
   .x1(marginX).y1(marginY)
   .x2(marginX).y2(marginY + height)
   .stroke('black').strokeWidth(1)

svg.addLine()
   .x1(marginX).y1(marginY + height)
   .x2(marginX + width).y2(marginY + height)
   .stroke('black').strokeWidth(1)
```

## Shape Factory Methods

The `Svg` class provides convenient factory methods for creating common shapes with sensible defaults.

### Using Shape Factories

```groovy
Svg svg = new Svg(800, 600)

// Rounded rectangle
Rect rounded = svg.createRoundedRect(
    x: 10, y: 10,
    width: 150, height: 100,
    radius: 15)
rounded.fill('lightblue').stroke('navy').strokeWidth(2)

// 5-point star
Polygon star = svg.createStar(
    cx: 250, cy: 60,
    points: 5,
    outerRadius: 50,
    innerRadius: 20)
star.fill('gold').stroke('orange').strokeWidth(2)

// Arrow
Path arrow = svg.createArrow(
    x1: 400, y1: 60,
    x2: 550, y2: 60,
    headSize: 15,
    headAngle: 30)
arrow.stroke('red').strokeWidth(3)

// Regular polygon (hexagon)
Polygon hexagon = svg.createRegularPolygon(
    cx: 650, cy: 60,
    sides: 6,
    radius: 50)
hexagon.fill('lightgreen').stroke('darkgreen').strokeWidth(2)

// Speech bubble
Path bubble = svg.createSpeechBubble(
    x: 50, y: 200,
    width: 200, height: 120,
    radius: 15,
    tailX: 150, tailY: 350,
    tailWidth: 30)
bubble.fill('white').stroke('black').strokeWidth(2)

// Add text to the bubble
svg.addText('Hello from gsvg!')
   .x(150).y(260)
   .textAnchor('middle')
   .fontSize(18)
```

### Creating Multiple Shapes

```groovy
Svg svg = new Svg(600, 200)

// Create a row of stars with different numbers of points
[3, 4, 5, 6, 7, 8].eachWithIndex { points, i ->
    svg.createStar(
        cx: 75 + i * 100,
        cy: 100,
        points: points,
        outerRadius: 40,
        innerRadius: 16
    ).fill('yellow').stroke('orange')
}
```

### Regular Polygons

```groovy
Svg svg = new Svg(400, 400)

// Triangle (3 sides)
svg.createRegularPolygon(
    cx: 100, cy: 100,
    sides: 3,
    radius: 60
).fill('red')

// Square (4 sides)
svg.createRegularPolygon(
    cx: 300, cy: 100,
    sides: 4,
    radius: 60,
    rotation: 45  // Diamond orientation
).fill('blue')

// Pentagon (5 sides)
svg.createRegularPolygon(
    cx: 100, cy: 300,
    sides: 5,
    radius: 60
).fill('green')

// Octagon (8 sides)
svg.createRegularPolygon(
    cx: 300, cy: 300,
    sides: 8,
    radius: 60
).fill('purple')
```

## Advanced Patterns

### Markers

```groovy
Svg svg = new Svg(400, 200)
Defs defs = svg.addDefs()

// Define arrow marker
Marker marker = defs.addMarker('arrowhead')
marker.markerWidth(10).markerHeight(10)
marker.refX(5).refY(5)
marker.orient('auto')
marker.addPolygon('0,0 10,5 0,10').fill('red')

// Use marker on line
svg.addLine()
   .x1(50).y1(100)
   .x2(350).y2(100)
   .stroke('black')
   .strokeWidth(2)
   .markerEnd('url(#arrowhead)')
```

### Mask

```groovy
Svg svg = new Svg(400, 300)
Defs defs = svg.addDefs()

// Define mask
Mask mask = defs.addMask('mask1')
mask.addCircle().cx(200).cy(150).r(100).fill('white')

// Apply mask to rectangle
svg.addRect()
   .x(100).y(50)
   .width(200).height(200)
   .fill('red')
   .mask('url(#mask1)')
```

### Animation (SMIL)

```groovy
Svg svg = new Svg(400, 200)

Circle circle = svg.addCircle()
circle.cx(50).cy(100).r(20).fill('blue')

// Animate position
Animate anim = circle.addAnimate()
anim.attributeName('cx')
anim.from('50')
anim.to('350')
anim.dur('3s')
anim.repeatCount('indefinite')
```

## Performance Optimization

### Numeric Precision Control

```groovy
import se.alipsa.groovy.svg.utils.NumberFormatter

// Set global precision (reduces file size)
NumberFormatter.setDefaultPrecision(2)

Svg svg = new Svg(200, 200)
svg.addCircle()
   .cx(100.123456)  // Outputs: cx="100.12"
   .cy(100.987654)  // Outputs: cy="100.99"
   .r(50.5)         // Outputs: r="50.5"

// Or set per-document
Svg svg2 = new Svg(200, 200)
svg2.setMaxPrecision(1)
svg2.addCircle()
   .cx(100.95)      // Outputs: cx="101.0"
   .cy(100.14)      // Outputs: cy="100.1"
   .r(50.5)         // Outputs: r="50.5"

// Reset to default (3 decimals)
NumberFormatter.resetPrecision()
```

### Reusing Definitions

```groovy
// BAD: Repeating the same element
Svg svg = new Svg(600, 200)
for (int i = 0; i < 10; i++) {
    svg.addCircle().cx(50 + i*50).cy(100).r(20).fill('blue')
}

// GOOD: Define once, reuse with <use>
Svg svg2 = new Svg(600, 200)
Defs defs = svg2.addDefs()
Circle template = defs.addCircle()
template.id('dot').r(20).fill('blue')

for (int i = 0; i < 10; i++) {
    svg2.addUse('#dot').x(50 + i*50).y(100)
}
```

### Lazy Validation

```groovy
import se.alipsa.groovy.svg.validation.ValidationEngine

// Don't validate during construction
Svg svg = new Svg(200, 200)
svg.addCircle().cx(100).cy(100).r(50)
svg.addRect().x(10).y(10).width(50).height(50)

// Validate only when needed
ValidationEngine engine = ValidationEngine.createDefault()
def report = engine.validate(svg)

if (!report.isValid()) {
    println "Validation errors found"
    report.errors.each { println it.message }
}
```

---

For more examples, see:
- [Creating SVG Elements](creating.md)
- [Accessibility Guide](accessibility.md)
- [Validation Guide](validation.md)
- [Performance Guide](performance.md)
- [Cookbook](cookbook.md)
