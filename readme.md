# Groovy Scalar Vector Graphics (gsvg)

This is an object model to create, parse and manipulate SVG documents for groovy.
If you have groovy in your classpath, you can use it in any JVM language (Java, Scala, etc.).

The goal is to have a simple to use and lightweight library to create SVG documents programmatically or parse existing ones for modification. It supports the SVG 1.1 specification and the SVG 2 draft.

The jvm should be java 17 or later.

See the test for various ways to create, parse and write SVG

to use it add the following to your Gradle build script
```groovy
implementation("se.apache.groovy:groovy:5.0.3")
implementation("se.alipsa.groovy:gsvg:0.2.0")
```
or if you prefer maven:
```xml
<dependencies>
  <dependency>
      <groupId>se.apache.groovy</groupId>
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

