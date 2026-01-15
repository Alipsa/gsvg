# Migration Guide

This guide helps you migrate to gsvg from other SVG libraries.

## Table of Contents

- [From Apache Batik](#from-apache-batik)
- [From JFreeSVG](#from-jfreesvg)
- [From SVGSalamander](#from-svgsalamander)
- [From Python svgwrite](#from-python-svgwrite)
- [From JavaScript SVG.js](#from-javascript-svgjs)
- [Key Differences](#key-differences)
- [Common Patterns](#common-patterns)

## From Apache Batik

Apache Batik is a comprehensive SVG toolkit focused on rendering and manipulation. gsvg is lighter weight and focused on programmatic SVG generation.

### Basic Creation

**Batik**:
```java
DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
Document doc = impl.createDocument(svgNS, "svg", null);

Element svgRoot = doc.getDocumentElement();
svgRoot.setAttributeNS(null, "width", "400");
svgRoot.setAttributeNS(null, "height", "400");

Element circle = doc.createElementNS(svgNS, "circle");
circle.setAttributeNS(null, "cx", "200");
circle.setAttributeNS(null, "cy", "200");
circle.setAttributeNS(null, "r", "100");
svgRoot.appendChild(circle);
```

**gsvg**:
```groovy
import se.alipsa.groovy.svg.Svg

Svg svg = new Svg(400, 400)
svg.addCircle()
   .cx(200).cy(200).r(100)
```

**Benefits**: Much more concise, fluent API, no namespace management.

### Reading SVG

**Batik**:
```java
String parser = XMLResourceDescriptor.getXMLParserClassName();
SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
Document doc = f.createDocument("file.svg");
```

**gsvg**:
```groovy
import se.alipsa.groovy.svg.SvgReader

Svg svg = SvgReader.read(new File('file.svg'))
```

### Styling

**Batik**:
```java
Element rect = doc.createElementNS(svgNS, "rect");
rect.setAttributeNS(null, "fill", "red");
rect.setAttributeNS(null, "stroke", "black");
rect.setAttributeNS(null, "stroke-width", "2");
```

**gsvg**:
```groovy
svg.addRect()
   .fill('red')
   .stroke('black')
   .strokeWidth(2)
```

### Transforms

**Batik**:
```java
Element rect = doc.createElementNS(svgNS, "rect");
rect.setAttributeNS(null, "transform", "rotate(45) translate(10, 20)");
```

**gsvg**:
```groovy
svg.addRect()
   .rotate(45)
   .translate(10, 20)
```

### When to Stay with Batik

- **Rendering**: Batik excels at rendering SVG to raster formats
- **CSS Processing**: Advanced CSS support
- **Animation**: SMIL animation support
- **Scripting**: JavaScript execution in SVG

### When to Switch to gsvg

- **Programmatic Generation**: Creating SVG from scratch
- **Data Visualization**: Charts, graphs, diagrams
- **Groovy Integration**: Native Groovy support with DSL
- **Lightweight**: Smaller dependency footprint

## From JFreeSVG

JFreeSVG is designed for generating SVG from Java2D Graphics2D calls. gsvg provides direct SVG element manipulation.

### Basic Graphics

**JFreeSVG**:
```java
SVGGraphics2D g2 = new SVGGraphics2D(600, 400);
g2.setColor(Color.RED);
g2.fillOval(100, 100, 200, 200);

String svgElement = g2.getSVGElement();
```

**gsvg**:
```groovy
Svg svg = new Svg(600, 400)
svg.addCircle()
   .cx(200).cy(200).r(100)
   .fill('red')

String xml = svg.toString()
```

### Paths

**JFreeSVG**:
```java
Path2D path = new Path2D.Double();
path.moveTo(10, 10);
path.lineTo(100, 10);
path.lineTo(100, 100);
path.closePath();

g2.setColor(Color.BLUE);
g2.fill(path);
```

**gsvg**:
```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

PathBuilder path = PathBuilder.moveTo(10, 10)
    .L(100, 10)
    .L(100, 100)
    .Z()

svg.addPath()
   .d(path)
   .fill('blue')
```

### Text

**JFreeSVG**:
```java
g2.setColor(Color.BLACK);
g2.setFont(new Font("Arial", Font.PLAIN, 18));
g2.drawString("Hello World", 50, 50);
```

**gsvg**:
```groovy
svg.addText('Hello World')
   .x(50).y(50)
   .fontSize(18)
   .fontFamily('Arial')
   .fill('black')
```

### Migration Strategy

1. **Identify Graphics2D calls**: List all drawing operations
2. **Map to SVG elements**: Convert shapes to direct SVG
3. **Update styling**: Replace Color/Paint with SVG fill/stroke
4. **Test rendering**: Verify visual output matches

## From SVGSalamander

SVGSalamander is focused on loading and rendering existing SVG files. gsvg is better for creation.

### Loading SVG

**SVGSalamander**:
```java
SVGUniverse universe = new SVGUniverse();
URI uri = universe.loadSVG(new File("image.svg"));
SVGDiagram diagram = universe.getDiagram(uri);
```

**gsvg**:
```groovy
Svg svg = SvgReader.read(new File('image.svg'))
```

### Modifying SVG

**SVGSalamander**: Read-mostly; modification is complex

**gsvg**: Full modification support
```groovy
Svg svg = SvgReader.read(new File('image.svg'))

// Modify all circles
svg[Circle].each { it.fill('red') }

// Add new elements
svg.addRect().x(10).y(10).width(100).height(50)

// Save
new File('modified.svg').text = svg.toString()
```

### When to Stay with SVGSalamander

- **Rendering Only**: If you only need to display existing SVG
- **Android**: SVGSalamander has Android support

### When to Switch to gsvg

- **Modification**: Need to edit/transform SVG
- **Generation**: Creating SVG programmatically
- **Validation**: Built-in validation support

## From Python svgwrite

Python's svgwrite is similar in approach to gsvg - programmatic SVG generation.

### Basic Creation

**Python svgwrite**:
```python
import svgwrite

dwg = svgwrite.Drawing('test.svg', size=('400px', '400px'))
dwg.add(dwg.circle(center=(200, 200), r=100, fill='red'))
dwg.save()
```

**gsvg**:
```groovy
Svg svg = new Svg(400, 400)
svg.addCircle()
   .cx(200).cy(200).r(100)
   .fill('red')

new File('test.svg').text = svg.toString()
```

### Groups

**Python svgwrite**:
```python
g = dwg.g(fill='blue', stroke='black')
g.add(dwg.circle(center=(50, 50), r=20))
g.add(dwg.rect(insert=(100, 30), size=(40, 40)))
dwg.add(g)
```

**gsvg**:
```groovy
G g = svg.addG()
g.fill('blue').stroke('black')
g.addCircle().cx(50).cy(50).r(20)
g.addRect().x(100).y(30).width(40).height(40)
```

### Gradients

**Python svgwrite**:
```python
gradient = dwg.defs.add(dwg.linearGradient(id='grad1'))
gradient.add_stop_color(offset='0%', color='red')
gradient.add_stop_color(offset='100%', color='blue')

dwg.add(dwg.rect(size=(200, 100), fill='url(#grad1)'))
```

**gsvg**:
```groovy
Defs defs = svg.addDefs()
LinearGradient gradient = defs.addLinearGradient('grad1')
gradient.addStop().offset('0%').stopColor('red')
gradient.addStop().offset('100%').stopColor('blue')

svg.addRect().width(200).height(100).fill('url(#grad1)')
```

### Key Similarities

- Programmatic element creation
- Fluent/builder API
- Direct SVG generation (no Graphics2D abstraction)
- Support for all SVG features

### Migration Tips

1. Replace `dwg.add()` with direct `svg.addX()` calls
2. Replace `insert=` with `x=` and `y=`
3. Replace `size=` with `width=` and `height=`
4. Use Groovy's more concise syntax

## From JavaScript SVG.js

SVG.js is a popular JavaScript library for SVG manipulation.

### Basic Creation

**SVG.js**:
```javascript
var draw = SVG().addTo('body').size(400, 400)
draw.circle(200).center(200, 200).fill('red')
```

**gsvg**:
```groovy
Svg svg = new Svg(400, 400)
svg.addCircle()
   .cx(200).cy(200).r(100)
   .fill('red')
```

### Paths

**SVG.js**:
```javascript
var path = draw.path('M 10 10 L 100 10 L 100 100 Z')
path.fill('blue').stroke({ color: 'black', width: 2 })
```

**gsvg**:
```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

PathBuilder path = PathBuilder.moveTo(10, 10)
    .L(100, 10).L(100, 100).Z()

svg.addPath()
   .d(path)
   .fill('blue')
   .stroke('black')
   .strokeWidth(2)
```

### Animation

**SVG.js**: Has extensive animation support
```javascript
circle.animate(1000).center(300, 300)
```

**gsvg**: Use SVG's native SMIL animation
```groovy
Circle circle = svg.addCircle().cx(200).cy(200).r(50)
Animate anim = circle.addAnimate()
anim.attributeName('cx')
anim.from('200')
anim.to('300')
anim.dur('1s')
```

### Events

**SVG.js**: DOM event handling
```javascript
circle.on('click', function() {
    this.fill('blue')
})
```

**gsvg**: Add attributes for client-side handling
```groovy
Circle circle = svg.addCircle()
circle.addAttribute('onclick', 'handleClick(this)')
```

### Migration Tips

1. No DOM - gsvg generates SVG strings
2. Replace event handlers with attributes
3. Animation uses SMIL, not JavaScript
4. Use Groovy closures for iteration/manipulation

## Key Differences

### Philosophy

| Feature | Other Libraries | gsvg |
|---------|----------------|------|
| Focus | Rendering, Display | Generation, Manipulation |
| API Style | Imperative | Fluent/Builder |
| Language | Java | Groovy (Java-compatible) |
| Dependencies | Heavy (Batik) | Lightweight |
| Validation | Limited | Built-in |
| Type Safety | Weak | Strong (@CompileStatic) |

### Advantages of gsvg

✅ **Groovy Native**: DSL support, closures, operator overloading
✅ **Fluent API**: Method chaining, readable code
✅ **Built-in Validation**: Multiple validation rules
✅ **Accessibility First**: ARIA support, validation
✅ **Modern Patterns**: Templates, presets, utilities
✅ **Performance**: Numeric precision control, optimization
✅ **Pure Generation**: No rendering overhead

### Tradeoffs

⚠️ **No Rendering**: Use external tools for rasterization
⚠️ **No Animation Engine**: Uses SMIL (SVG native)
⚠️ **Groovy Required**: Though Java-compatible

## Common Patterns

### Pattern 1: Simple Shape Creation

**Any Library** → **gsvg**:
```groovy
// Always: create SVG, add elements, configure
Svg svg = new Svg(width, height)
svg.addShape().prop1(val1).prop2(val2)
```

### Pattern 2: Reading and Modifying

**Any Library** → **gsvg**:
```groovy
// Read
Svg svg = SvgReader.read(new File('input.svg'))

// Modify
svg[Circle].each { it.fill('red') }

// Save
new File('output.svg').text = svg.toString()
```

### Pattern 3: Gradients

**Any Library** → **gsvg**:
```groovy
Defs defs = svg.addDefs()
LinearGradient grad = defs.addLinearGradient('myGrad')
grad.addStop().offset('0%').stopColor('color1')
grad.addStop().offset('100%').stopColor('color2')

element.fill('url(#myGrad)')
```

### Pattern 4: Groups for Reuse

**Any Library** → **gsvg**:
```groovy
G template = defs.addG().id('icon')
// ... add icon elements ...

svg.addUse('#icon').x(50).y(50)
svg.addUse('#icon').x(100).y(50)
```

## Migration Checklist

- [ ] Identify SVG operations in existing code
- [ ] Map library-specific calls to gsvg equivalents
- [ ] Update imports to gsvg packages
- [ ] Test with small examples first
- [ ] Validate output SVG
- [ ] Update tests
- [ ] Check performance
- [ ] Review accessibility
- [ ] Document migration notes

## Getting Help

- [API Cheat Sheet](api-cheat-sheet.md) - Quick reference
- [Examples](examples.md) - Common patterns
- [Cookbook](cookbook.md) - Recipes
- [GitHub Issues](https://github.com/Alipsa/gsvg/issues) - Ask questions

---

**Have migration questions?** Open an issue on the [gsvg GitHub repository](https://github.com/Alipsa/gsvg).
