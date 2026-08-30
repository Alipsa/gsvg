# gsvg-export

Export and transformation module for the [gsvg](../readme.md) library. Provides SVG rendering to raster formats, optimization, prettification, and unit-aware resizing.

## Dependency

Gradle:
```groovy
implementation("se.alipsa.groovy:gsvg-export:1.2.0")
```

Maven:
```xml
<dependency>
    <groupId>se.alipsa.groovy</groupId>
    <artifactId>gsvg-export</artifactId>
    <version>1.2.0</version>
</dependency>
```

## Classes

### SvgRenderer

Renders SVG to PNG, JPEG, or writes SVG to files and output streams. Uses the [jsvg](https://github.com/weisJ/jsvg) library for high-quality rasterization.

```groovy
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import se.alipsa.groovy.svg.export.RendererOptionsBuilder

def svg = new Svg(200, 120)
svg.addRect(180, 100).x(10).y(10).fill('#1976d2')

// Write SVG to file or stream
SvgRenderer.toSvgFile(svg, new File('output.svg'))
SvgRenderer.toSvgFile(svg, outputStream)

// Render to PNG (file or stream)
SvgRenderer.toPng(svg, new File('output.png'))
SvgRenderer.toPng(svg, outputStream, [scale: 2.0])

// Render to JPEG with options
def options = RendererOptionsBuilder.create()
    .size(600, 400)
    .backgroundColor('white')
    .quality(0.9)
    .antialiasing(true)
    .build()

SvgRenderer.toJpeg(svg, new File('output.jpg'), options)
SvgRenderer.toJpeg(svg, outputStream, options)
```

### RendererOptionsBuilder

Fluent builder for renderer options maps. Supports `width`, `height`, `size`, `scale`, `backgroundColor`, `quality`, and `antialiasing`.

```groovy
def options = RendererOptionsBuilder.builder()
    .scale(2.0)
    .antialiasing(true)
    .build()
```

### SvgOptimizer

Reduces SVG file size by removing metadata, collapsing redundant groups, shortening colors, minifying path data, and removing default/invisible attributes.

```groovy
import se.alipsa.groovy.svg.export.SvgOptimizer

// Returns a new optimized copy (original unchanged)
def optimized = SvgOptimizer.optimize(svg)

// Or optimize in place
SvgOptimizer.optimizeInPlace(svg, [precision: 2, shortenColors: true])
```

### SvgFormatter

Produces human-readable, pretty-printed SVG XML with configurable indentation, attribute sorting, and element grouping.

```groovy
import se.alipsa.groovy.svg.export.SvgFormatter

String formatted = SvgFormatter.prettify(svg, [indent: '    ', sortAttributes: true])
// Or use convenience methods
String pretty = SvgFormatter.prettyPrint(svg)
```

### SvgResizer

Unit-aware resizing that returns deep copies, preserving the original SVG. Supports `px`, `in`, `cm`, `mm`, `pt`, `pc`, and `%` units.

```groovy
import se.alipsa.groovy.svg.export.SvgResizer

// Resize proportionally by width
def resized = SvgResizer.resizeToWidth(svg, '12cm')

// Resize to exact dimensions
def fixed = SvgResizer.resize(svg, 800, 600, [preserveAspectRatio: false])

// Scale by a factor
def doubled = SvgResizer.scale(svg, 2.0)
```
