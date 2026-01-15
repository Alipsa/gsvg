# gsvg Documentation Overview

This project provides a Groovy-friendly object model for creating, parsing, and manipulating SVG 1.1 and SVG 2 draft documents.

## Installation

Gradle:
```groovy
implementation "org.apache.groovy:groovy:5.0.3"
implementation "se.alipsa.groovy:gsvg:0.8.0"
```

Maven:
```xml
<dependency>
  <groupId>org.apache.groovy</groupId>
  <artifactId>groovy</artifactId>
  <version>5.0.3</version>
</dependency>
<dependency>
  <groupId>se.alipsa.groovy</groupId>
  <artifactId>gsvg</artifactId>
  <version>0.8.0</version>
</dependency>
```

## What you can do
- [Build SVGs fluently](creating.md) with a Groovy DSL (shapes, gradients, filters, text, animation).
- [Parse existing SVG](parsing.md) files/strings/streams and modify them.
- [Merge multiple SVG documents](merging.md) horizontally, vertically, or layered on top of each other.
- Work with CSS: parse and manipulate CSS rules, manage inline styles as maps, add/remove CSS classes.
- Validate SVG structure: opt-in validation with customizable rules for attributes, nesting, references, and more.
- Work with SVG 2 `href` and legacy `xlink:href`, foreign content, and SVG 1.1/2 draft elements.

## Core concepts
- `Svg` is the root document and an element container; most `addX()` methods return the new element for fluent chaining.
- Elements wrap a dom4j `Element` (`element` property) while also tracking `children` for quick navigation.
- Attributes can be set via fluent methods, `addAttribute(s)`, map-based adders (e.g. `addRect([x: 10, ...])`), or Groovy property assignment.
- Element containers support `svg[0]`, `svg['rect']`, and `svg[Rect]` selectors.
- Any element can be cloned into a new parent with `element.clone(targetSvg)` or `element.cloneWith(targetSvg, [fill: 'red'])` for reuse without XML serialization.
- Shape factory methods like `svg.createRoundedRect()`, `svg.createStar()`, and `svg.createArrow()` provide convenient presets.
- Null-safe attribute access with `element.getAttributeOrDefault('fill', 'black')` prevents NullPointerExceptions.

## Serialization and I/O
- Use `SvgWriter.toXml(svg)` or `SvgWriter.toXmlPretty(svg)` to serialize the full document.
- `element.toXml()` returns a fragment for just that element and its children.
- `SvgReader.parse(...)` accepts `File`, `InputStream`, `Reader`, `InputSource`, or a raw XML string.

## CSS Integration
Work with CSS rules and inline styles using a complete object model:

```groovy
// Add CSS rules to style elements
def style = svg.addStyle()
style.addRule('.highlight', [fill: 'red', stroke: 'black', 'stroke-width': '2'])
style.addRule('#logo', [transform: 'scale(2)'])

// Manage CSS classes on elements
rect.addClass('highlight').addClass('selected')
rect.removeClass('selected')
if (rect.hasClass('highlight')) { /* ... */ }

// Work with inline styles as maps
rect.style([fill: 'red', stroke: 'blue', 'stroke-width': '2'])
def fillColor = rect.getStyleProperty('fill') // 'red'
def styleMap = rect.getStyleMap() // [fill: 'red', stroke: 'blue', ...]
```

CSS classes: `CssRule`, `CssStyleSheet`, `CssParser` (in `se.alipsa.groovy.svg.css`)

## Validation
Validate SVG structure with opt-in validation (library remains permissive by default):

```groovy
// Basic validation
def report = svg.validate()
if (report.isValid()) {
    // No errors (warnings and info don't fail validation)
}

// Inspect validation issues
report.getErrors().each { println "ERROR: ${it.message}" }
report.getWarnings().each { println "WARNING: ${it.message}" }
report.getInfo().each { println "INFO: ${it.message}" }

// Customize validation rules
def engine = ValidationEngine.createDefault()
engine.removeRule("VIEWBOX_RULE") // Disable specific rule
def customReport = svg.validate(engine)
```

Six core validation rules:
- `RequiredAttributeRule` - Check required attributes (e.g., circle needs cx/cy/r)
- `AttributeValueRule` - Validate attribute value ranges
- `ElementNestingRule` - Validate parent-child relationships
- `ViewBoxRule` - Validate viewBox format
- `HrefRule` - Validate href references exist
- `DuplicateIdRule` - Detect duplicate IDs

Validation classes in `se.alipsa.groovy.svg.validation` and `se.alipsa.groovy.svg.validation.rules`.

## Where to go next
- `creating.md` for building SVGs with gradients, filters, reuse, text, foreign content, and scripts.
- `parsing.md` for parsing, navigation, and editing patterns.
- `merging.md` for combining SVG documents using `SvgMerger`.
- `benchmarks.md` for performance benchmarks and optimization tips.
