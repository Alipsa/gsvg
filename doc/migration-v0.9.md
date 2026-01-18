# Migration Guide: v0.9 to v1.0

This guide highlights changes when moving from gsvg v0.9.x to v1.0.x.

## Summary of Changes

- **New modules**: `gsvg-export` adds raster rendering and optimization helpers.
- **CSS selectors**: `css(...)` and `cssFirst(...)` support for element queries.
- **Performance**: Parsing and merging are faster; cloning behavior optimized.
- **Parsing behavior**: Raw attribute values are preserved on parse.

## Dependency Updates

Gradle:
```groovy
implementation "se.alipsa.groovy:gsvg:1.0.0"
implementation "se.alipsa.groovy:gsvg-export:1.0.0" // optional
```

Maven:
```xml
<dependency>
  <groupId>se.alipsa.groovy</groupId>
  <artifactId>gsvg</artifactId>
  <version>1.0.0</version>
</dependency>
<dependency>
  <groupId>se.alipsa.groovy</groupId>
  <artifactId>gsvg-export</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Behavioral Notes

### Parsing preserves raw attribute strings

In v1.0, parsed attributes keep their original string value. Numeric formatting (e.g., precision trimming)
is only applied when you set numeric attributes programmatically.

If you rely on normalized formatting after parsing, re-assign numeric attributes:
```groovy
def svg = SvgReader.parse(new File('input.svg'))
svg[Circle].each { c ->
    if (c.cx?.isNumber()) {
        c.cx(new BigDecimal(c.cx)) // re-apply programmatic formatting
    }
}
```

### CSS selector queries

Use CSS selectors alongside type filters and XPath:
```groovy
def logo = svg.cssFirst('#logo')
def redRects = svg.css('rect[fill=\"red\"]')
```

## No Required Code Changes

Most v0.9 APIs remain unchanged. If your project only uses core creation/parsing,
you can usually bump the version and run your test suite.

## Checklist

- [ ] Update dependencies to 1.0.0
- [ ] Run the test suite
- [ ] Review any code that relies on parsed attribute formatting
- [ ] Consider using CSS selectors where appropriate
