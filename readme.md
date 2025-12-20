![Groovy](https://img.shields.io/badge/groovy-4298B8.svg?style=for-the-badge&logo=apachegroovy&logoColor=white)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.alipsa.groovy/gsvg/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.alipsa.groovy/gsvg)
[![javadoc](https://javadoc.io/badge2/se.alipsa.groovy/gsvg/javadoc.svg)](https://javadoc.io/doc/se.alipsa.groovy/gsvg)
# Groovy Scalar Vector Graphics (gsvg)

This is an object model to create, parse and manipulate SVG documents for groovy.
If you have groovy in your classpath, you can use it in any JVM language (Java, Scala, etc.).

The goal is to have a simple to use and lightweight library to create SVG documents programmatically or parse existing ones for modification. It supports the SVG 1.1 specification and the SVG 2 draft.

The jvm used should be java 17 or later.

See the test for various ways to create, parse and write SVG

to use it add the following to your Gradle build script
```groovy
implementation("org.apache.groovy:groovy:5.0.3")
implementation("se.alipsa.groovy:gsvg:0.2.0")
```
or if you prefer maven:
```xml
<dependencies>
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
</dependencies>
```

## Quick start

Create an SVG in code:
```groovy
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgWriter

def svg = new Svg(200, 120)
svg.addRect(180, 100).x(10).y(10).rx(12).ry(12).fill('#1976d2')
svg.addText('Hello SVG')
   .x(30).y(70)
   .fill('white')
   .fontSize(24)

println SvgWriter.toXmlPretty(svg)
```

Read/parse an existing SVG file and change it:
```groovy
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter
import se.alipsa.groovy.svg.Rect

def svg = SvgReader.parse(new File('logo.svg'))
def rects = svg[Rect]
rects.each { it.stroke('red').strokeWidth(2) }

new File('logo-out.svg').text = SvgWriter.toXmlPretty(svg)
```
