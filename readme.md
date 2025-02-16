# Groovy Scalar Vector Graphics (gsvg)

This is an object model to create, parse and manipulate SVG documents for groovy.
If you have groovy in your classpath, you can use it in any JVM language (Java, Scala, etc.).

The jvm should be java 17 or later.

See the test for various ways to create, parse and write SVG

to use it add the following to your Gradle build script
```groovy
implementation("se.apache.groovy:groovy:4.0.25")
implementation("se.alipsa.groovy:gsvg:0.1.0")
```
or if you prefer maven:
```xml
<dependencies>
  <dependency>
      <groupId>se.apache.groovy</groupId>
      <artifactId>groovy</artifactId>
      <version>4.0.25</version>
  </dependency>
  <dependency>
      <groupId>se.alipsa.groovy</groupId>
      <artifactId>gsvg</artifactId>
      <version>0.1.0</version>
  </dependency>
</dependencies>
```

