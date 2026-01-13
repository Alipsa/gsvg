# gsvg Library Improvement Plan

## Overview

This document outlines potential improvements to the gsvg library in terms of ease of use and feature completeness. Items are organized by priority and impact.

**Library Status**: Production-ready with excellent foundations
- ✅ Complete SVG element coverage (83 types)
- ✅ Secure parsing with XXE/DTD protection
- ✅ Comprehensive testing (259 tests)
- ✅ Solid documentation
- ✅ Pure OO architecture

---

## High-Priority Improvements (Ease of Use)

### 1. Path Data Manipulation Utilities ✅

**Current State**: Path `d` attributes are just strings
```groovy
path.d('M 10 10 L 20 20 C 30 30, 40 40, 50 50')
```

**Completed Enhancement**:
- ✅ Path command builder (fluent API)
- ✅ Path parser (parse existing `d` strings)
- ✅ Path command types (M, L, C, Q, A, Z, etc.)
- ✅ Path transformation utilities
- ✅ Common path generators (arcs, bezier helpers)

```groovy
// Example API:
path.moveTo(10, 10).lineTo(20, 20).curveTo(30, 30, 40, 40, 50, 50)
// or maintain string compatibility:
PathBuilder.parse('M 10 10 L 20 20').lineTo(30, 30).toString()
```

**Implementation Notes**:
- ✅ Created `PathBuilder` class in `utils/` package
- ✅ Supports both absolute and relative commands
- ✅ Maintains backward compatibility (string API still works)

---

### 2. Transform Utilities ✅

**Current State**: Transforms are strings
```groovy
rect.transform('rotate(45) translate(10, 20)')
```

**Completed Enhancement**:
- ✅ Individual transform methods on elements (rotate, scale, translate, skew)
- ✅ Transform chaining (each method appends to existing transform)
- ✅ Support for both single-axis and dual-axis operations

```groovy
// Current string API (keep for backward compatibility):
rect.transform('rotate(45) translate(10, 20)')

// New fluent API (add direct methods):
rect.rotate(45).translate(10, 20)
rect.scale(2).translate(100, 50)
rect.rotate(45, 50, 50)  // rotate around center point
rect.skewX(15).skewY(10)
```

**Implementation Notes**:
- Add transform methods to AbstractShape (inherited by all shapes)
- Methods append to existing transform attribute (chaining)
- Support optional center point for rotate
- Maintain backward compatibility (string API still works)

---

### 3. Color Utilities ✅

**Current State**: Colors are strings
```groovy
rect.fill('#ff0000').stroke('rgb(0, 255, 0)')
```

**Completed Enhancement**:
- ✅ Color class with format support
- ✅ RGB/HSL/Hex conversions
- ✅ Opacity/alpha manipulation
- ✅ Color interpolation
- ✅ Named color constants (147 SVG colors)
- ✅ Color parsing and validation
- ✅ Color manipulation (darken/lighten)

```groovy
// Create colors in various formats:
def red = Color.rgb(255, 0, 0)
def blue = Color.hex('#0000ff')
def green = Color.hsl(120, 100, 50)
def semiTransparent = Color.rgba(255, 0, 0, 0.5)
def namedColor = Color.named('crimson')

// Parse any color format:
def parsed = Color.parse('rgba(255, 128, 0, 0.8)')

// Use with SVG elements:
rect.fill(Color.rgb(255, 0, 0).toString())
    .stroke(Color.hex('#00ff00').withAlpha(0.5).toString())

// Color manipulation:
def darker = red.darken(0.2)
def lighter = blue.lighten(0.3)
def purple = red.interpolate(blue, 0.5)

// Multiple output formats:
red.toHex()       // "#ff0000"
red.toRgb()       // "rgb(255, 0, 0)"
red.toHsl()       // "hsl(0, 100%, 50%)"
red.toString()    // SVG-compatible (hex or rgba)
```

**Implementation Notes**:
- ✅ Created `Color` class in `utils/` package
- ✅ Supports: hex (#rgb, #rrggbb, #rrggbbaa), rgb(), rgba(), hsl(), hsla(), named colors
- ✅ toString() returns appropriate SVG format (hex for opaque, rgba for transparent)
- ✅ 147 named SVG colors supported
- ✅ Maintains backward compatibility (strings still work)

---

### 4. ViewBox/Coordinate Helpers ✅

**Current State**: ViewBox is a string attribute
```groovy
svg.viewBox('0 0 100 100')
```

**Completed Enhancement**:
- ✅ ViewBox class/builder
- ✅ Coordinate transformation utilities
- ✅ Bounding box calculations (contains, intersects)
- ✅ Aspect ratio preservation helpers
- ✅ Coordinate system conversions

```groovy
// Example API:
svg.viewBox(ViewBox.of(0, 0, 100, 100))
svg.viewBox(0, 0, 100, 100) // convenience method

def vb = ViewBox.of(0, 0, 100, 100)
def scaled = vb.scale(2.0)
def translated = vb.translate(10, 10)
def expanded = vb.expand(5)
def centered = vb.centerAt(50, 50)
def fitted = vb.fitToContain(other)
```

**Implementation Notes**:
- ✅ Created `ViewBox` class in `utils/` package
- ✅ Added transformation, query, and containment methods
- ✅ Immutable design with fluent transformations
- ✅ Integration with Svg, Symbol, and View elements

---

## Medium-Priority Improvements (Feature Completeness)

### 5. CSS Integration

**Current State**: `<style>` elements supported but only as text content

**Proposed Enhancement**:
- [ ] CSS rule parsing
- [ ] CSS rule manipulation
- [ ] Style object model
- [ ] Selector-based styling
- [ ] CSS class utilities
- [ ] Inline style parsing

```groovy
// Example API:
def style = svg.addStyle()
style.addRule('.highlight', [fill: 'red', stroke: 'black'])
style.addRule('#logo', [transform: 'scale(2)'])

// Or inline style parsing:
rect.style([fill: 'red', stroke: 'blue'])
rect.addClass('highlight')
```

**Implementation Notes**:
- Create `CssRule` and `CssStyleSheet` classes
- Add to Style element
- Consider using existing CSS parser library vs custom implementation

---

### 6. Validation Helpers

**Current State**: No structural validation

**Proposed Enhancement**:
- [ ] SVG structural validation
- [ ] Required attribute validation
- [ ] Valid attribute value checking
- [ ] Element nesting validation
- [ ] Validation report with warnings/errors

```groovy
// Example API:
def report = svg.validate()
report.errors.each { println it }
report.warnings.each { println it }

if (svg.isValid()) {
    // proceed
}

// Or validation on creation (opt-in):
svg.strictMode = true // throws on invalid operations
```

**Implementation Notes**:
- Create `ValidationReport` class
- Define validation rules based on SVG spec
- Make validation optional (performance consideration)
- Consider different validation levels (strict, lenient)

---

### 7. Performance Benchmarks

**Current State**: Performance claims ("40-60% faster") are estimates

**Proposed Enhancement**:
- [ ] JMH benchmark suite
- [ ] Parsing benchmarks
- [ ] Merging benchmarks
- [ ] Cloning benchmarks
- [ ] Memory usage profiling
- [ ] Document benchmark results

**Implementation Notes**:
- Add JMH dependency (test scope)
- Create benchmarks in `src/test/java/benchmarks/`
- Run on different JVM versions
- Publish results in docs

---

### 8. Enhanced Element Selection ✅

**Current State**: Basic selection by type or name
```groovy
svg[Rect]        // by type
svg['rect']      // by name
svg[0]           // by index
```

**Completed Enhancement**:
- ✅ XPath query support (with transparent namespace handling)
- ⏸️ CSS selector support (deferred to future version)
- ✅ Filter/find/collect helpers
- ✅ Predicates and matchers
- ✅ Recursive search options

```groovy
// Example API:
svg.xpath('//rect[@fill="red"]')              // XPath (namespace-aware)
svg.filter { it instanceof Rect && it.getX() as int > 100 }
svg.findAll(Rect) { it.getWidth() as int > 50 }
svg.findFirst { it.getId() == 'logo' }
svg.descendants(Circle)                       // recursive
svg.count { it.getFill() == 'red' }
svg.any { it instanceof Circle }
svg.all { it.getFill() != null }
svg.collect { it.getId() }
```

**Implementation Notes**:
- ✅ Leveraged existing Jaxen dependency for XPath
- ✅ Added transparent namespace transformation for simple queries
- ✅ Added filter(), findAll(), findFirst(), descendants(), xpath(), count(), any(), all(), collect()
- ✅ Added to ElementContainer trait
- CSS selector support deferred (XPath covers most use cases)

---

## Lower-Priority Improvements

### 9. Builder Pattern Enhancements

- [ ] Preset shape configurations
- [ ] Common effect presets (drop-shadow, glow, etc.)
- [ ] Template patterns
- [ ] Fluent configuration objects
- [ ] DSL improvements

```groovy
// Example:
svg.addRoundedRect {
    x 10
    y 10
    width 100
    height 50
    cornerRadius 5
    fill 'blue'
}

// Or presets:
def shadow = Effects.dropShadow(dx: 2, dy: 2, blur: 4)
rect.applyEffect(shadow)
```

---

### 10. Extended Documentation

- [ ] Interactive examples/playground
- [ ] Migration guide from other SVG libraries
- [ ] Cookbook with common recipes
- [ ] Performance tuning guide
- [ ] Video tutorials
- [ ] API cheat sheet

---

### 11. Accessibility Helpers

- [ ] ARIA attribute shortcuts
- [ ] Role/label helpers
- [ ] Accessibility validation
- [ ] Screen reader testing utilities

```groovy
// Example:
graphic.role('img')
       .ariaLabel('Company logo')
       .ariaDescribedBy('logo-desc')
```

---

### 12. Export Utilities (Optional - Separate Module)

**Architecture**: Multi-module Maven project
- Restructure current code as `gsvg-core` submodule
- Create `gsvg-export` as separate submodule with its own dependencies
- Parent POM coordinates both modules

**Prerequisites**:
- [ ] Refactor project to multi-module structure
  - [ ] Create parent POM
  - [ ] Move existing code to `gsvg-core/` submodule
  - [ ] Update artifact coordinates and dependencies
  - [ ] Verify all tests pass after restructure

**gsvg-export Module Features**:
- [ ] SVG to PNG export using [jsvg](https://github.com/weisJ/jsvg)
- [ ] SVG to JPEG export
- [ ] SVG optimization (SVGO-like features)
- [ ] SVG minification
- [ ] SVG prettification/formatting

**Implementation Notes**:
- Use **jsvg** instead of Batik (lighter weight, better SVG 2 support)
- jsvg provides: `com.github.weisj:jsvg` (pure Java, no native deps)
- Optional dependency keeps core lightweight
- Users only include `gsvg-export` if they need rendering

**Example Usage** (future):
```groovy
@Grab('se.alipsa.groovy:gsvg-core:0.7.0')
@Grab('se.alipsa.groovy:gsvg-export:0.7.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgExporter

def svg = new Svg(200, 200)
svg.addCircle().cx(100).cy(100).r(50).fill('red')

// Export to PNG
SvgExporter.toPng(svg, new File('output.png'), width: 400, height: 400)

// Optimize SVG
def optimized = SvgExporter.optimize(svg, removeComments: true, minify: true)
```

---

## Quick Wins (Easy Implementation, High Value)

These can be implemented quickly with significant user impact:

- [ ] **Default attribute values**: Allow `rect.x(10)` without requiring `y(0)`
- [ ] **Consistent method chaining**: Ensure ALL setters return `this`
- [ ] **Common shapes factory**: `Svg.createRoundedRect(...)` with sensible defaults
- [ ] **Null-safe accessors**: Return defaults instead of null for missing attributes
- ✅ **Attribute removal**: Add `element.removeAttribute(name)` method (already existed!)
- ✅ **Batch attribute setting**: `rect.attrs([x: 10, y: 20, width: 100])` (implemented as alias to addAttributes)
- [ ] **Clone with modifications**: `rect.cloneWith([fill: 'red'])`
- ✅ **Element existence checks**: `element.hasAttribute('fill')`

---

## Not Recommended (Out of Scope)

These items are intentionally excluded to maintain the library's lightweight philosophy:

- ❌ Built-in rendering engine (use Apache Batik separately)
- ❌ Animation timeline/sequencer (SMIL is sufficient)
- ❌ Heavy CSS framework integration
- ❌ Interactive SVG editor
- ❌ Desktop GUI tools

---

## Implementation Priority Recommendation

**Phase 1: Ease of Use (v0.6.0)** ✅
1. ✅ Quick wins (easy, high value)
2. ✅ Transform utilities
3. ✅ Color utilities

**Phase 2: Core Features (v0.7.0)** ✅
4. ✅ Path data manipulation
5. ✅ Enhanced element selection
6. ✅ ViewBox/coordinate helpers

**Phase 3: Advanced Features (v0.8.0)**
7. CSS integration
8. Validation helpers
9. Performance benchmarks

**Phase 4: Polish (v0.9.0)**
10. Builder pattern enhancements
11. Extended documentation
12. Accessibility helpers

---

## Notes

- Maintain backward compatibility throughout
- Keep core library lightweight
- Consider separate modules for heavy features
- All string-based APIs should continue to work
- Focus on Groovy-friendly APIs while maintaining Java compatibility

---

## Feedback

Please review and modify this plan as needed. Mark items with:
- ✅ Completed
- 🚧 In progress
- ❌ Rejected/Not needed
- 📝 Needs discussion
