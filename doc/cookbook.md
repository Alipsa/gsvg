# gsvg Cookbook

This cookbook provides practical recipes for common SVG tasks using gsvg.

## Table of Contents

- [Creating Charts](#creating-charts)
- [Working with Paths](#working-with-paths)
- [Text Effects](#text-effects)
- [Responsive SVG](#responsive-svg)
- [Export and Optimization](#export-and-optimization)
- [Advanced Patterns](#advanced-patterns)

## Creating Charts

### Bar Chart with Legend

```groovy
import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.templates.ChartLegend

Svg svg = new Svg(700, 500)
svg.role('img').ariaLabel('Quarterly Sales Bar Chart')

// Title
svg.addText('Quarterly Sales 2024')
   .x(350).y(30)
   .fontSize(24)
   .fontWeight('bold')
   .textAnchor('middle')

// Data
def data = [
    [quarter: 'Q1', value: 50000, color: '#FF6B6B'],
    [quarter: 'Q2', value: 75000, color: '#4ECDC4'],
    [quarter: 'Q3', value: 60000, color: '#45B7D1'],
    [quarter: 'Q4', value: 90000, color: '#FFA07A']
]

// Chart settings
int maxValue = 100000
int chartHeight = 350
int barWidth = 80
int startX = 80
int startY = 420
int spacing = 120

// Draw bars
data.eachWithIndex { item, i ->
    int barHeight = (item.value / maxValue * chartHeight) as int
    int x = startX + (i * spacing)
    int y = startY - barHeight

    // Bar
    svg.addRect()
       .x(x).y(y)
       .width(barWidth).height(barHeight)
       .fill(item.color)
       .stroke('black')
       .strokeWidth(1)

    // Quarter label
    svg.addText(item.quarter)
       .x(x + barWidth/2).y(startY + 25)
       .textAnchor('middle')
       .fontSize(14)

    // Value label
    svg.addText("\$${item.value/1000}K")
       .x(x + barWidth/2).y(y - 10)
       .textAnchor('middle')
       .fontSize(12)
       .fontWeight('bold')
}

// Y-axis
svg.addLine()
   .x1(startX - 10).y1(startY)
   .x2(startX - 10).y2(startY - chartHeight)
   .stroke('black').strokeWidth(2)

// X-axis
svg.addLine()
   .x1(startX - 10).y1(startY)
   .x2(startX + (data.size() * spacing)).y2(startY)
   .stroke('black').strokeWidth(2)

// Add legend
ChartLegend legend = new ChartLegend()
def legendItems = data.collect { [color: it.color, label: it.quarter] }
legend.apply(svg, [x: 550, y: 100, items: legendItems])

// Save
new File('bar-chart.svg').text = svg.toXml()
```

### Line Chart with Grid

```groovy
import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.utils.PathBuilder

Svg svg = new Svg(800, 500)
svg.role('img').ariaLabel('Temperature Trends')

// Data points
def months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
def temperatures = [5, 7, 12, 16, 21, 25, 28, 27, 23, 17, 11, 6]

// Chart dimensions
int margin = 60
int width = 680
int height = 380
int maxTemp = 30

// Background
svg.addRect()
   .x(margin).y(margin)
   .width(width).height(height)
   .fill('white')
   .stroke('lightgray')

// Grid lines
(0..6).each { i ->
    int y = margin + (i * height / 6)
    svg.addLine()
       .x1(margin).y1(y)
       .x2(margin + width).y2(y)
       .stroke('#e0e0e0')
       .strokeWidth(1)
       .strokeDasharray('5,5')

    // Y-axis labels
    int temp = maxTemp - (i * 5)
    svg.addText("${temp}°C")
       .x(margin - 10).y(y + 5)
       .textAnchor('end')
       .fontSize(12)
}

// Plot line
PathBuilder line = PathBuilder.create()
temperatures.eachWithIndex { temp, i ->
    int x = margin + (i * width / (temperatures.size() - 1))
    int y = margin + height - ((temp / maxTemp) * height) as int

    if (i == 0) {
        line.M(x, y)
    } else {
        line.L(x, y)
    }

    // Data point
    svg.addCircle()
       .cx(x).cy(y).r(4)
       .fill('steelblue')
       .stroke('white')
       .strokeWidth(2)

    // Month label
    svg.addText(months[i])
       .x(x).y(margin + height + 20)
       .textAnchor('middle')
       .fontSize(12)
}

// Draw line
svg.addPath()
   .d(line)
   .fill('none')
   .stroke('steelblue')
   .strokeWidth(3)

// Title
svg.addText('Monthly Average Temperature')
   .x(400).y(30)
   .fontSize(20)
   .fontWeight('bold')
   .textAnchor('middle')
```

### Pie Chart with Labels

```groovy
import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.utils.Color

Svg svg = new Svg(600, 600)
svg.role('img').ariaLabel('Market Share Pie Chart')

// Data
def data = [
    [label: 'Product A', value: 35, color: Color.hex('#FF6B6B')],
    [label: 'Product B', value: 25, color: Color.hex('#4ECDC4')],
    [label: 'Product C', value: 20, color: Color.hex('#45B7D1')],
    [label: 'Product D', value: 20, color: Color.hex('#FFA07A')]
]

int total = data.sum { it.value } as int
double currentAngle = -Math.PI / 2
int cx = 300
int cy = 300
int radius = 200

data.each { item ->
    double angle = (item.value / total) * 2 * Math.PI
    double endAngle = currentAngle + angle
    double midAngle = currentAngle + angle / 2

    // Calculate arc endpoints
    double x1 = cx + radius * Math.cos(currentAngle)
    double y1 = cy + radius * Math.sin(currentAngle)
    double x2 = cx + radius * Math.cos(endAngle)
    double y2 = cy + radius * Math.sin(endAngle)

    int largeArc = (angle > Math.PI) ? 1 : 0

    // Slice
    String pathData = "M ${cx} ${cy} L ${x1} ${y1} A ${radius} ${radius} 0 ${largeArc} 1 ${x2} ${y2} Z"
    svg.addPath()
       .d(pathData)
       .fill(item.color.toString())
       .stroke('white')
       .strokeWidth(3)

    // Percentage label
    double labelX = cx + (radius * 0.6) * Math.cos(midAngle)
    double labelY = cy + (radius * 0.6) * Math.sin(midAngle)

    svg.addText("${item.value}%")
       .x(labelX as int).y(labelY as int)
       .textAnchor('middle')
       .fontSize(20)
       .fontWeight('bold')
       .fill('white')

    currentAngle = endAngle
}

// Title
svg.addText('Market Share Distribution')
   .x(300).y(50)
   .fontSize(24)
   .fontWeight('bold')
   .textAnchor('middle')
```

## Working with Paths

### Creating Smooth Curves

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

// Smooth curve through points
def points = [[50, 100], [150, 50], [250, 100], [350, 50]]

PathBuilder smooth = PathBuilder.moveTo(points[0][0], points[0][1])

for (int i = 1; i < points.size(); i++) {
    def p0 = i > 0 ? points[i-1] : points[i]
    def p1 = points[i]
    def p2 = i < points.size() - 1 ? points[i+1] : points[i]

    // Calculate control points for smooth curve
    double cx1 = p0[0] + (p1[0] - p0[0]) * 0.5
    double cy1 = p1[1]
    double cx2 = p1[0] + (p2[0] - p1[0]) * 0.5
    double cy2 = p1[1]

    smooth.Q(p1[0], p1[1], cx2, cy2)
}

svg.addPath().d(smooth).fill('none').stroke('blue').strokeWidth(2)
```

### Creating Rounded Rectangles with Individual Corner Radii

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

// Rectangle with different corner radii
PathBuilder roundedRect(x, y, width, height, tlr, trr, brr, blr) {
    PathBuilder.moveTo(x + tlr, y)
        .H(x + width - trr)
        .A(trr, trr, 0, 0, 1, x + width, y + trr)
        .V(y + height - brr)
        .A(brr, brr, 0, 0, 1, x + width - brr, y + height)
        .H(x + blr)
        .A(blr, blr, 0, 0, 1, x, y + height - blr)
        .V(y + tlr)
        .A(tlr, tlr, 0, 0, 1, x + tlr, y)
        .Z()
}

// Use it
svg.addPath()
   .d(roundedRect(50, 50, 200, 100, 20, 10, 5, 15))
   .fill('lightblue')
   .stroke('navy')
```

### Drawing Arrows with Custom Heads

```groovy
import se.alipsa.groovy.svg.presets.Shapes

// Simple arrow
Shapes.arrow(svg, x1: 50, y1: 100, x2: 300, y2: 100, headSize: 20)
    .stroke('black').strokeWidth(3).fill('none')

// Double-headed arrow
def drawDoubleArrow(svg, x1, y1, x2, y2) {
    // Main line
    svg.addLine().x1(x1).y1(y1).x2(x2).y2(y2)
       .stroke('black').strokeWidth(2)

    // Start arrow head
    Shapes.arrow(svg, x1: x1+20, y1: y1, x2: x1, y2: y1, headSize: 15)
        .stroke('black').strokeWidth(2).fill('none')

    // End arrow head
    Shapes.arrow(svg, x1: x2-20, y1: y2, x2: x2, y2: y2, headSize: 15)
        .stroke('black').strokeWidth(2).fill('none')
}
```

## Text Effects

### Outlined Text

```groovy
// Create text with outline effect
Defs defs = svg.addDefs()

// Define the text as a path for reuse
Text textDef = defs.addText('OUTLINED')
textDef.id('outlinedText')
textDef.fontSize(72).fontWeight('bold').fontFamily('Arial')

// Background stroke
svg.addUse('#outlinedText')
   .x(100).y(100)
   .stroke('black')
   .strokeWidth(8)
   .fill('none')

// Foreground fill
svg.addUse('#outlinedText')
   .x(100).y(100)
   .fill('yellow')
   .stroke('orange')
   .strokeWidth(2)
```

### Text with Shadow

```groovy
import se.alipsa.groovy.svg.effects.Effects

Defs defs = svg.addDefs()
Filter shadow = Effects.dropShadow(defs, id: 'textShadow', dx: 4, dy: 4, blur: 4)

svg.addText('Shadowed Text')
   .x(100).y(100)
   .fontSize(48)
   .fontWeight('bold')
   .fill('darkblue')
   .filter('url(#textShadow)')
```

### Text Along a Curved Path

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

Defs defs = svg.addDefs()

// Define curve path
PathBuilder curve = PathBuilder.moveTo(50, 200)
    .Q(300, 50, 550, 200)

Path curvePath = defs.addPath()
curvePath.id('textCurve').d(curve)

// Draw path for reference (optional)
svg.addPath().d(curve)
   .fill('none').stroke('lightgray').strokeWidth(1)

// Text along path
Text text = svg.addText()
text.fontSize(24).fill('purple')

TextPath textPath = text.addTextPath()
textPath.href('#textCurve')
textPath.content = 'Text follows the curve of this path!'
```

### Gradient Text

```groovy
Defs defs = svg.addDefs()

// Create gradient
LinearGradient gradient = defs.addLinearGradient('textGradient')
gradient.x1('0%').y1('0%').x2('100%').y2('0%')
gradient.addStop().offset('0%').stopColor('#FF6B6B')
gradient.addStop().offset('50%').stopColor('#4ECDC4')
gradient.addStop().offset('100%').stopColor('#45B7D1')

// Apply to text
svg.addText('Gradient Text')
   .x(100).y(100)
   .fontSize(60)
   .fontWeight('bold')
   .fill('url(#textGradient)')
```

## Responsive SVG

### Making SVG Responsive

```groovy
// Create SVG with viewBox instead of fixed width/height
Svg svg = new Svg()
svg.viewBox('0 0 800 600')  // Define coordinate system
svg.setAttribute('preserveAspectRatio', 'xMidYMid meet')

// Add content using viewBox coordinates
svg.addRect().x(100).y(100).width(600).height(400).fill('lightblue')

// SVG will scale to fit container while maintaining aspect ratio
```

### Creating Adaptive Graphics

```groovy
// Use percentage-based coordinates
Svg svg = new Svg()
svg.viewBox('0 0 100 100')

// Circle centered at 50% x 50%
svg.addCircle()
   .cx('50').cy('50').r('20')
   .fill('red')

// Rectangle using percentage widths
svg.addRect()
   .x('10').y('10')
   .width('80').height('30')
   .fill('blue')
```

## Export and Optimization

### Optimizing File Size

```groovy
import se.alipsa.groovy.svg.utils.NumberFormatter

// Set precision to reduce decimal places
NumberFormatter.setDefaultPrecision(2)

Svg svg = new Svg(400, 400)
svg.setMaxPrecision(2)  // Per-document setting

// Reuse definitions instead of duplicating
Defs defs = svg.addDefs()
Circle template = defs.addCircle().id('dot').r(5).fill('blue')

// Use <use> to reference
(0..100).each { i ->
    svg.addUse('#dot').x(i * 10).y(100)
}

// Result: Much smaller file than 100 separate circles
```

### Embedding Fonts

```groovy
// Use system fonts with fallbacks
svg.addText('Hello')
   .fontFamily('Arial, Helvetica, sans-serif')

// Or use web fonts
Defs defs = svg.addDefs()
Style style = defs.addStyle()
style.type('text/css')
style.addCData('''
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');
''')

svg.addText('Modern Font')
   .fontFamily('Roboto, sans-serif')
```

### Saving SVG

```groovy
// Save to file
Svg svg = new Svg(400, 300)
// ... add content ...

new File('output.svg').text = svg.toXml()

// Or with pretty formatting (larger file size)
import groovy.xml.XmlUtil
String formatted = XmlUtil.serialize(svg.element)
new File('output-pretty.svg').text = formatted
```

### Resizing SVG Output

```groovy
import se.alipsa.groovy.svg.export.SvgResizer

Svg svg = new Svg(400, 200)
// ... add content ...

// Resize proportionally by width
Svg resized = SvgResizer.resizeToWidth(svg, 480)

// Percentage sizing (uses viewBox/current size as the reference)
Svg scaled = SvgResizer.resizeToWidth(svg, '150%')

// Explicit reference for percentages
Svg referenced = SvgResizer.resizeToWidth(svg, '150%', [referenceWidth: 400])
```

## Advanced Patterns

### Clipping and Masking

```groovy
Defs defs = svg.addDefs()

// Define clipping path
ClipPath clip = defs.addClipPath('circleClip')
clip.addCircle().cx(200).cy(200).r(150)

// Apply clip to image or shape
svg.addRect()
   .x(50).y(50)
   .width(300).height(300)
   .fill('red')
   .clipPath('url(#circleClip)')

// Masking with gradient
Mask mask = defs.addMask('fadeMask')
LinearGradient gradient = mask.addRect().width('100%').height('100%')
// Add gradient definition...

svg.addRect()
   .width(400).height(300)
   .fill('blue')
   .mask('url(#fadeMask)')
```

### Pattern Fills

```groovy
Defs defs = svg.addDefs()

// Create pattern
Pattern pattern = defs.addPattern('dots')
pattern.x(0).y(0).width(20).height(20)
pattern.patternUnits('userSpaceOnUse')

pattern.addCircle().cx(10).cy(10).r(5).fill('steelblue')

// Use pattern as fill
svg.addRect()
   .x(50).y(50)
   .width(300).height(200)
   .fill('url(#dots)')
   .stroke('black')

// Stripe pattern
Pattern stripes = defs.addPattern('stripes')
stripes.width(10).height(10).patternUnits('userSpaceOnUse')
stripes.addRect().width(5).height(10).fill('red')
stripes.addRect().x(5).width(5).height(10).fill('white')
```

### Animated Diagrams

```groovy
// Rotating loader
svg.addCircle {
    cx 200
    cy 200
    r 50
    fill 'none'
    stroke 'blue'
    strokeWidth 8
    strokeDasharray '100 200'
}

Animate anim = svg.getChildren().get(0).addAnimate()
anim.attributeName('transform')
anim.type('rotate')
anim.from('0 200 200')
anim.to('360 200 200')
anim.dur('2s')
anim.repeatCount('indefinite')

// Pulsing circle
Circle pulse = svg.addCircle().cx(100).cy(100).r(30).fill('red')
Animate pulseAnim = pulse.addAnimate()
pulseAnim.attributeName('r')
pulseAnim.from('30')
pulseAnim.to('50')
pulseAnim.dur('1s')
pulseAnim.repeatCount('indefinite')
```

### Interactive Elements

```groovy
// Clickable button
G button = svg.addG()
button.addAttribute('cursor', 'pointer')
button.addAttribute('onclick', 'alert("Clicked!")')

Rect buttonBg = button.addRect()
buttonBg.x(100).y(100).width(150).height(50)
buttonBg.rx(5).fill('#4CAF50').stroke('darkgreen').strokeWidth(2)

Text buttonText = button.addText('Click Me')
buttonText.x(175).y(130).textAnchor('middle').fill('white').fontSize(18)

// Add accessibility
button.ariaLabel('Submit button')
button.role('button')
```

### Creating Icons

```groovy
// Define icon as reusable component
Defs defs = svg.addDefs()

G icon = defs.addG()
icon.id('checkIcon')
icon.viewBox('0 0 24 24')

// Checkmark path
icon.addPath()
   .d('M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z')
   .fill('green')

// Use icon
svg.addUse('#checkIcon').x(50).y(50).width(24).height(24)
svg.addUse('#checkIcon').x(100).y(50).width(48).height(48)
```

---

For more examples and recipes, see:
- [Examples Guide](examples.md)
- [Best Practices](best-practices.md)
- [Performance Guide](performance.md)
