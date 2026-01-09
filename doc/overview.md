# gsvg Documentation Overview

This project provides a Groovy-friendly object model for creating, parsing, and manipulating SVG 1.1 and SVG 2 draft documents.

## Installation

Gradle:
```groovy
implementation "org.apache.groovy:groovy:5.0.3"
implementation "se.alipsa.groovy:gsvg:0.2.0"
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
  <version>0.2.0</version>
</dependency>
```

## What you can do
- [Build SVGs fluently](creating.md) with a Groovy DSL (shapes, gradients, filters, text, animation).
- [Parse existing SVG](parsing.md) files/strings/streams and modify them.
- [Merge multiple SVG documents](merging.md) horizontally, vertically, or layered on top of each other.
- Work with SVG 2 `href` and legacy `xlink:href`, foreign content, and SVG 1.1/2 draft elements.
