# gsvg Best Practices

This guide outlines recommended practices for using gsvg effectively and maintaining high-quality SVG code.

## Table of Contents

- [Code Organization](#code-organization)
- [Naming and IDs](#naming-and-ids)
- [Accessibility](#accessibility)
- [Performance](#performance)
- [Security](#security)
- [Testing](#testing)
- [Documentation](#documentation)
- [Common Pitfalls](#common-pitfalls)

## Code Organization

### Structure Your SVG Logically

**Good**:
```groovy
Svg svg = new Svg(800, 600)
svg.role('img').ariaLabel('Sales Dashboard')

// Definitions (gradients, filters, patterns)
Defs defs = svg.addDefs()
LinearGradient gradient = defs.addLinearGradient('blueGradient')
// ... configure gradient ...

// Background layer
G background = svg.addG()
background.id('background-layer')
background.addRect().width(800).height(600).fill('white')

// Data layer
G data = svg.addG()
data.id('data-layer')
// ... add chart elements ...

// Annotation layer
G annotations = svg.addG()
annotations.id('annotation-layer')
// ... add labels, legends ...
```

**Bad**:
```groovy
// Mixing layers without organization
Svg svg = new Svg(800, 600)
svg.addRect().fill('white')  // Background
svg.addCircle()              // Data
svg.addText('Label')         // Annotation
svg.addRect().fill('blue')   // More data?
```

### Use Groups for Logical Units

```groovy
// Group related elements
G chart = svg.addG()
chart.id('bar-chart')

data.each { item ->
    G bar = chart.addG()
    bar.id("bar-${item.id}")

    // Bar rectangle
    bar.addRect()
       .x(item.x).y(item.y)
       .width(item.width).height(item.height)
       .fill(item.color)

    // Bar label
    bar.addText(item.label)
       .x(item.x + item.width/2)
       .y(item.y - 5)
       .textAnchor('middle')
}
```

### Extract Reusable Components

```groovy
// Bad: Repeating code
svg.addRect().x(10).y(10).width(100).height(50).rx(5).fill('blue')
svg.addText('Button 1').x(60).y(40).textAnchor('middle').fill('white')

svg.addRect().x(120).y(10).width(100).height(50).rx(5).fill('blue')
svg.addText('Button 2').x(170).y(40).textAnchor('middle').fill('white')

// Good: Reusable method
def createButton(svg, x, y, label) {
    G button = svg.addG()
    button.addRect()
        .x(x).y(y).width(100).height(50)
        .rx(5).fill('blue')
    button.addText(label)
        .x(x + 50).y(y + 30)
        .textAnchor('middle').fill('white')
    return button
}

createButton(svg, 10, 10, 'Button 1')
createButton(svg, 120, 10, 'Button 2')

// Better: Use Template pattern
import se.alipsa.groovy.svg.templates.Template

class ButtonTemplate extends Template {
    SvgElement apply(AbstractElementContainer parent, Map params) {
        G button = parent.addG()
        button.addRect()
            .x(params.x).y(params.y)
            .width(100).height(50)
            .rx(5).fill('blue')
        button.addText(params.label)
            .x(params.x + 50).y(params.y + 30)
            .textAnchor('middle').fill('white')
        return button
    }
}

ButtonTemplate btn = new ButtonTemplate()
btn.apply(svg, [x: 10, y: 10, label: 'Button 1'])
btn.apply(svg, [x: 120, y: 10, label: 'Button 2'])

// Best: Use cloneWith for variations of the same element
Circle prototype = svg.addCircle()
    .cx(100).cy(100).r(50)
    .fill('blue')
    .stroke('black')
    .strokeWidth(2)

// Clone with modifications for variations
Circle red = prototype.cloneWith(svg, [fill: 'red', cx: 250])
Circle large = prototype.cloneWith(svg, [r: 75, fill: 'green', cx: 400])
```

### Use Shape Factory Methods for Common Patterns

```groovy
// Good: Use factory methods for common shapes
Rect rounded = svg.createRoundedRect(x: 10, y: 10, width: 100, height: 60, radius: 10)
Polygon star = svg.createStar(cx: 150, cy: 60, points: 5, outerRadius: 50)
Path arrow = svg.createArrow(x1: 250, y1: 60, x2: 350, y2: 60)

// Instead of: Manual path calculations for arrows, stars, etc.
```

### Use Null-Safe Attribute Access

```groovy
// Good: Prevent NullPointerException with defaults
String fill = circle.getAttributeOrDefault('fill', 'black')
String stroke = rect.getAttributeOrDefault('stroke', 'none')

// Apply defaults conditionally
if (!element.hasAttribute('fill')) {
    element.fill(element.getAttributeOrDefault('fill', 'currentColor'))
}

// Bad: Direct access may throw NPE
String fill = circle.getFill()  // May return null
if (fill == null) fill = 'black'  // Verbose null checking
```

## Naming and IDs

### Use Descriptive IDs

**Good**:
```groovy
svg.addRect().id('chart-background')
svg.addG().id('data-series-revenue')
svg.addFilter('drop-shadow-strong')
```

**Bad**:
```groovy
svg.addRect().id('r1')
svg.addG().id('g2')
svg.addFilter('f3')
```

### Follow Naming Conventions

```groovy
// Use kebab-case for IDs
svg.addG().id('sales-chart-2024')

// Use consistent prefixes
defs.addGradient('gradient-primary')
defs.addGradient('gradient-secondary')
defs.addFilter('filter-shadow')
defs.addFilter('filter-glow')

// Descriptive definition names
defs.addPattern('pattern-dots-blue')
defs.addClipPath('clip-circle-large')
```

### Avoid ID Collisions

```groovy
// Bad: Could collide if SVGs are merged
svg1.addRect().id('background')
svg2.addRect().id('background')

// Good: Use prefixes
svg1.addRect().id('dashboard-background')
svg2.addRect().id('chart-background')

// Better: Generate unique IDs when merging
import se.alipsa.groovy.svg.utils.SvgMerger

def merged = SvgMerger.mergeHorizontally([svg1, svg2])
// IDs are automatically made unique
```

## Accessibility

### Always Provide Accessible Names

```groovy
// Every non-decorative SVG should have a role and label
Svg svg = new Svg(400, 300)
svg.role('img')
   .ariaLabel('Monthly revenue chart showing growth trend')

// Or use title/desc for complex graphics
svg.addTitle('Revenue Dashboard')
svg.addDesc('Interactive chart showing monthly revenue from January to December 2024')
```

### Label Interactive Elements

```groovy
// Links
A link = svg.addA()
link.ariaLabel('Download PDF report')
link.addTitle('Download PDF report')  // Tooltip
link.href = '/reports/2024.pdf'

// Clickable elements
Circle dataPoint = svg.addCircle()
dataPoint.addAttribute('onclick', 'showDetails()')
dataPoint.ariaLabel('January sales: $50,000 - Click for details')
dataPoint.addAttribute('tabindex', '0')  // Keyboard accessible
```

### Mark Decorative Elements

```groovy
// Decorative borders, backgrounds, etc.
svg.addRect()
   .width(400).height(300)
   .fill('lightgray')
   .decorative()  // Hides from screen readers

// Don't label purely decorative graphics
Rect decoration = svg.addRect().stroke('gold').strokeWidth(5).fill('none')
decoration.ariaHidden(true)
```

### Validate Accessibility

```groovy
import se.alipsa.groovy.svg.validation.ValidationEngine

Svg svg = new Svg(400, 300)
// ... build SVG ...

ValidationEngine engine = ValidationEngine.createAccessibility()
def report = engine.validate(svg)

if (!report.isValid()) {
    println "Accessibility issues found:"
    report.errors.each { println "  ERROR: ${it.message}" }
    report.warnings.each { println "  WARNING: ${it.message}" }
}
```

## Performance

### Set Appropriate Numeric Precision

```groovy
import se.alipsa.groovy.svg.utils.NumberFormatter

// For icons and simple graphics
NumberFormatter.setDefaultPrecision(2)

// For data visualizations (default)
NumberFormatter.setDefaultPrecision(3)

// For technical drawings
NumberFormatter.setDefaultPrecision(4)
```

### Reuse Instead of Duplicate

```groovy
// Define once in <defs>
Defs defs = svg.addDefs()

Circle iconDot = defs.addCircle()
iconDot.id('icon-dot').r(3).fill('blue')

// Reuse many times
svg.addUse('#icon-dot').x(50).y(100)
svg.addUse('#icon-dot').x(100).y(100)
svg.addUse('#icon-dot').x(150).y(100)
```

### Validate Strategically

```groovy
// Don't validate during construction
for (int i = 0; i < 1000; i++) {
    svg.addCircle().cx(i).cy(100).r(5)
}

// Validate once at the end if needed
def report = svg.validate()
```

### Optimize Path Data

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

// Use relative commands for smaller output
PathBuilder path = PathBuilder.moveTo(100, 100)
    .l(50, 0)   // relative lineTo
    .l(0, 50)
    .l(-50, 0)
    .Z()

// More compact than:
// M 100 100 L 150 100 L 150 150 L 100 150 Z
```

## Security

### Sanitize User Input

```groovy
// NEVER directly use untrusted input in SVG
// Bad:
String userInput = request.getParameter('color')
svg.addRect().fill(userInput)  // XSS vulnerability!

// Good:
import org.apache.commons.text.StringEscapeUtils

String userInput = request.getParameter('color')
String safeColor = validateColor(userInput) ?: 'black'
svg.addRect().fill(safeColor)

def validateColor(String input) {
    // Whitelist approach
    def validColors = ['red', 'blue', 'green', 'black', 'white']
    if (input in validColors) return input

    // Or regex for hex colors
    if (input ==~ /#[0-9A-Fa-f]{6}/) return input

    return null  // Invalid
}
```

### Disable External Entities

```groovy
// gsvg automatically disables XXE attacks
// SvgReader uses secure SAX parser configuration

import se.alipsa.groovy.svg.io.SvgReader

// Safe by default - XXE protection enabled
Svg svg = SvgReader.parse(userUploadedFile)
```

### Avoid Script Injection

```groovy
// Bad: Direct user input in event handlers
String userAction = request.getParameter('action')
svg.addRect().addAttribute('onclick', userAction)  // XSS!

// Good: Predefined handlers only
def validHandlers = [
    'showDetails': 'showDetails()',
    'highlight': 'highlightElement(this)'
]
String action = validHandlers[userAction]
if (action) {
    svg.addRect().addAttribute('onclick', action)
}
```

### Content Security Policy

When serving SVGs:
```groovy
// Set CSP headers
response.setHeader('Content-Security-Policy',
    "default-src 'none'; style-src 'unsafe-inline'; img-src 'self'")

// Or sanitize on output
String safeSvg = sanitizeSvg(svg.toXml())
```

## Testing

### Write Unit Tests for SVG Generation

```groovy
import spock.lang.Specification

class ChartGeneratorSpec extends Specification {

    def "should create bar chart with correct structure"() {
        given:
        def data = [[label: 'Q1', value: 100], [label: 'Q2', value: 150]]

        when:
        Svg svg = generateBarChart(data)

        then:
        svg.getChildren().size() == 2  // 2 bars
        svg.getChildren()[0].getWidth() == '50'
        svg.getChildren()[1].getHeight() == '75'
    }

    def "should handle empty data gracefully"() {
        when:
        Svg svg = generateBarChart([])

        then:
        svg.getChildren().size() == 0
        notThrown(Exception)
    }
}
```

### Validate Generated SVG

```groovy
import se.alipsa.groovy.svg.validation.ValidationEngine

def "generated SVG should be valid"() {
    when:
    Svg svg = generateChart(testData)
    ValidationEngine engine = ValidationEngine.createDefault()
    def report = engine.validate(svg)

    then:
    report.isValid()
}
```

### Test Accessibility

```groovy
def "chart should be accessible"() {
    when:
    Svg svg = generateChart(testData)

    then:
    svg.getRole() == 'img'
    svg.getAriaLabel() != null
    svg.getAriaLabel().length() > 0
}
```

### Snapshot Testing

```groovy
def "should match expected SVG output"() {
    when:
    Svg svg = generateIcon('checkmark')
    String actual = svg.toXml()

    then:
    actual == expectedCheckmarkSvg()  // Compare against known good output
}
```

## Documentation

### Document Complex Algorithms

```groovy
/**
 * Generates a polar plot for the given data points.
 *
 * @param data List of data points with angle and radius
 * @param maxRadius Maximum radius for scaling
 * @return Configured SVG containing the polar plot
 */
Svg generatePolarPlot(List<Map> data, double maxRadius) {
    Svg svg = new Svg(400, 400)

    // Convert polar coordinates to Cartesian
    data.each { point ->
        double angle = Math.toRadians(point.angle)
        double r = (point.value / maxRadius) * 150

        double x = 200 + r * Math.cos(angle)
        double y = 200 + r * Math.sin(angle)

        svg.addCircle().cx(x).cy(y).r(3).fill('red')
    }

    return svg
}
```

### Add Usage Examples

```groovy
/**
 * Creates a button with customizable appearance.
 *
 * Example:
 * <pre>
 * Svg svg = new Svg(400, 100)
 * createButton(svg, [
 *     x: 50,
 *     y: 25,
 *     label: 'Submit',
 *     color: 'blue'
 * ])
 * </pre>
 *
 * @param parent Parent SVG element
 * @param options Button configuration
 * @return Button group element
 */
G createButton(AbstractElementContainer parent, Map options) {
    // Implementation
}
```

### Document Dependencies

```groovy
/**
 * Generates a chart legend using the ChartLegend template.
 *
 * Requires:
 * - import se.alipsa.groovy.svg.templates.ChartLegend
 *
 * @see se.alipsa.groovy.svg.templates.ChartLegend
 */
void addLegend(Svg svg, List items) {
    ChartLegend legend = new ChartLegend()
    legend.apply(svg, [x: 450, y: 50, items: items])
}
```

## Common Pitfalls

### Don't Modify Elements After Adding

**Problem**:
```groovy
Circle c = new Circle(svg)
c.cx(100).cy(100).r(50)
svg.addChild(c)  // Wrong way to add
```

**Solution**:
```groovy
Circle c = svg.addCircle()  // Correct
c.cx(100).cy(100).r(50)
```

### Don't Mix String and Numeric APIs

**Problem**:
```groovy
svg.addCircle()
   .cx('100')    // String
   .cy(100)      // Number
   .r('50px')    // Invalid - don't include units
```

**Solution**:
```groovy
svg.addCircle()
   .cx(100)      // Consistent numeric
   .cy(100)
   .r(50)        // No units - SVG uses user units
```

### Don't Forget to Set ViewBox for Responsive SVG

**Problem**:
```groovy
Svg svg = new Svg(800, 600)  // Fixed size only
```

**Solution**:
```groovy
Svg svg = new Svg()
svg.viewBox('0 0 800 600')   // Responsive
svg.setAttribute('preserveAspectRatio', 'xMidYMid meet')
```

### Don't Hardcode Coordinates

**Problem**:
```groovy
// Brittle - breaks if SVG size changes
svg.addCircle().cx(400).cy(300).r(50)  // Assumes 800x600 SVG
```

**Solution**:
```groovy
// Relative to SVG size
int width = 800
int height = 600
svg.addCircle()
   .cx(width / 2)   // Center X
   .cy(height / 2)  // Center Y
   .r(Math.min(width, height) * 0.1)  // 10% of smallest dimension
```

### Don't Ignore Validation Warnings

```groovy
ValidationEngine engine = ValidationEngine.createDefault()
ValidationReport report = engine.validate(svg)

// Don't ignore warnings!
if (report.hasWarnings()) {
    report.warnings.each { warning ->
        // Fix accessibility issues
        // Fix missing viewBox
        // Fix invalid attribute values
        println "WARNING: ${warning.message}"
    }
}
```

### Don't Skip Accessibility

**Problem**:
```groovy
// No role, no label - inaccessible
Svg svg = new Svg(400, 300)
svg.addRect()  // What does this represent?
```

**Solution**:
```groovy
Svg svg = new Svg(400, 300)
svg.role('img')
   .ariaLabel('Company logo')
```

---

## Summary Checklist

Before deploying your SVG code:

**Code Quality**
- [ ] Logical structure with grouped elements
- [ ] Reusable components extracted
- [ ] Consistent naming conventions
- [ ] Documented complex algorithms

**Accessibility**
- [ ] All SVGs have role and label
- [ ] Interactive elements have accessible names
- [ ] Decorative elements marked as such
- [ ] Accessibility validation passes

**Performance**
- [ ] Appropriate numeric precision set
- [ ] Common elements defined once in `<defs>`
- [ ] Validation runs only when needed
- [ ] File size optimized

**Security**
- [ ] User input sanitized
- [ ] No script injection vulnerabilities
- [ ] XXE protection enabled (default)
- [ ] CSP headers set when serving

**Testing**
- [ ] Unit tests for SVG generation
- [ ] Validation tests pass
- [ ] Accessibility tests pass
- [ ] Edge cases handled

For more details, see:
- [Performance Guide](performance.md)
- [Accessibility Guide](accessibility.md)
- [Cookbook](cookbook.md)
