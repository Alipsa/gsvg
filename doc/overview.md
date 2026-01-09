# gsvg Documentation Overview

This project provides a Groovy-friendly object model for creating, parsing, and manipulating SVG 1.1 and SVG 2 draft documents.

## Installation

Gradle:
```groovy
implementation "org.apache.groovy:groovy:5.0.3"
implementation "se.alipsa.groovy:gsvg:0.5.0"
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
  <version>0.5.0</version>
</dependency>
```

## What you can do
- [Build SVGs fluently](creating.md) with a Groovy DSL (shapes, gradients, filters, text, animation).
- [Parse existing SVG](parsing.md) files/strings/streams and modify them.
- [Merge multiple SVG documents](merging.md) horizontally, vertically, or layered on top of each other.
- Work with SVG 2 `href` and legacy `xlink:href`, foreign content, and SVG 1.1/2 draft elements.

## Core concepts
- `Svg` is the root document and an element container; most `addX()` methods return the new element for fluent chaining.
- Elements wrap a dom4j `Element` (`element` property) while also tracking `children` for quick navigation.
- Attributes can be set via fluent methods, `addAttribute(s)`, map-based adders (e.g. `addRect([x: 10, ...])`), or Groovy property assignment.
- Element containers support `svg[0]`, `svg['rect']`, and `svg[Rect]` selectors.
- Any element can be cloned into a new parent with `element.clone(targetSvg)` for reuse without XML serialization.

## Serialization and I/O
- Use `SvgWriter.toXml(svg)` or `SvgWriter.toXmlPretty(svg)` to serialize the full document.
- `element.toXml()` returns a fragment for just that element and its children.
- `SvgReader.parse(...)` accepts `File`, `InputStream`, `Reader`, `InputSource`, or a raw XML string.

## Where to go next
- `creating.md` for building SVGs with gradients, filters, reuse, text, foreign content, and scripts.
- `parsing.md` for parsing, navigation, and editing patterns.
- `merging.md` for combining SVG documents using `SvgMerger`.
