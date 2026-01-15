# gsvg Library Improvement Plan

## Overview

This document outlines potential improvements to the gsvg library in terms of ease of use and feature completeness. Items are organized by priority and impact.

**Library Status**: Production-ready with excellent foundations
- ✅ Complete SVG element coverage (83 types)
- ✅ Secure parsing with XXE/DTD protection
- ✅ Comprehensive testing (593 tests)
- ✅ CSS object model with ph-css integration
- ✅ Opt-in validation system (6 rules)
- ✅ JMH performance benchmark suite
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

### 5. CSS Integration ✅

**Current State**: Complete CSS object model with ph-css integration

**Completed Enhancement**:
- ✅ CSS rule parsing (using ph-css 8.1.1)
- ✅ CSS rule manipulation
- ✅ Style object model (CssRule, CssStyleSheet, CssParser)
- ✅ CSS class utilities (addClass, removeClass, toggleClass, hasClass)
- ✅ Inline style parsing and Map API

```groovy
// CSS object model:
def style = svg.addStyle()
style.addRule('.highlight', [fill: 'red', stroke: 'black'])
style.addRule('#logo', [transform: 'scale(2)'])

// Parse existing CSS:
def stylesheet = CssParser.parseStyleSheet('.class { fill: red; }')

// Inline style Map API:
rect.style([fill: 'red', stroke: 'blue', 'stroke-width': '2'])
def styleMap = rect.getStyleMap()
def fillColor = rect.getStyleProperty('fill')

// CSS class management:
rect.addClass('highlight').addClass('selected')
rect.removeClass('selected')
rect.toggleClass('active')
if (rect.hasClass('highlight')) { /* ... */ }
def classes = rect.getClasses() // ['highlight']
```

**Implementation Notes**:
- ✅ Created `CssRule`, `CssStyleSheet`, and `CssParser` classes in `css/` package
- ✅ Integrated ph-css 8.1.1 library for robust parsing
- ✅ Added CSS methods to Style element
- ✅ Added inline style Map API and class utilities to SvgElement

---

### 6. Validation Helpers ✅

**Current State**: Complete opt-in validation system with 6 core rules

**Completed Enhancement**:
- ✅ SVG structural validation (6 validation rules)
- ✅ Required attribute validation (RequiredAttributeRule)
- ✅ Valid attribute value checking (AttributeValueRule)
- ✅ Element nesting validation (ElementNestingRule)
- ✅ Validation report with errors/warnings/info (ValidationReport)
- ✅ ViewBox validation (ViewBoxRule)
- ✅ Href reference validation (HrefRule)
- ✅ Duplicate ID detection (DuplicateIdRule)

```groovy
// Basic validation:
def report = svg.validate()
if (report.isValid()) {
    // No errors (warnings and info don't fail validation)
}

// Inspect issues:
report.getErrors().each { println "ERROR: ${it.message}" }
report.getWarnings().each { println "WARNING: ${it.message}" }
report.getInfo().each { println "INFO: ${it.message}" }

// Quick check:
if (svg.isValid()) {
    // proceed
}

// Custom validation engine:
def engine = ValidationEngine.createDefault()
engine.removeRule("VIEWBOX_RULE") // Disable specific rule
def customReport = svg.validate(engine)

// Validation levels:
// - ERROR: Invalid SVG (missing required attributes, invalid values)
// - WARNING: Valid but not recommended (missing optional attributes)
// - INFO: Best practice suggestions (consider adding viewBox)
```

**Implementation Notes**:
- ✅ Created `ValidationReport`, `ValidationIssue`, `ValidationLevel`, `ValidationRule`, `ValidationEngine` classes
- ✅ Implemented 6 core validation rules in `validation/rules/` package
- ✅ Validation is completely opt-in (no automatic validation)
- ✅ Library remains permissive by default - all invalid SVG creation continues to work
- ✅ Optimized for performance (O(N) complexity for all rules)

---

### 7. Performance Benchmarks ✅

**Current State**: Complete JMH benchmark suite with sanity tests

**Completed Enhancement**:
- ✅ JMH benchmark suite (JMH 1.37)
- ✅ Parsing benchmarks (4 complexity levels)
- ✅ Serialization benchmarks (toXml, toXmlPretty)
- ✅ Creation benchmarks (element creation, fluent API)
- ✅ Merging benchmarks (horizontal, vertical, on-top)
- ✅ Cloning benchmarks (deep copy performance)
- ✅ Utility benchmarks (Color, PathBuilder, ViewBox)
- ✅ Selection benchmarks (type filtering, XPath, descendants)
- ✅ Benchmark sanity tests (8 tests ensuring benchmarks don't throw exceptions)
- ✅ Document benchmark results in doc/benchmarks.md

**Implementation Notes**:
- ✅ Added JMH 1.37 dependency (test scope)
- ✅ Created 9 benchmark classes in `src/test/java/benchmarks/`
- ✅ Created 4 test SVG files (simple, medium, large, complex)
- ✅ Created BenchmarkSanityTest.groovy to verify benchmarks work
- ✅ Ran full benchmark suite and documented results
- ✅ Created comprehensive doc/benchmarks.md with performance analysis and tips

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

### 9. Builder Pattern Enhancements ✅

**Current State**: COMPLETED (v0.9.0) - All builder enhancements implemented

**Completed Enhancement**:
- ✅ Common effect presets (drop-shadow, glow, blur, grayscale, sepia, brightness, contrast)
- ✅ Preset shape configurations (star, arrow, rounded rect, regular polygon, speech bubble)
- ✅ Template patterns (Template base class, ChartLegend example)
- ✅ DSL improvements (closure-based configuration for all common shapes)

```groovy
// Actual implemented Effects presets (effects/Effects.groovy):
Defs defs = svg.addDefs()
Filter shadow = Effects.dropShadow(defs, dx: 2, dy: 2, blur: 3, opacity: 0.5)
Filter glow = Effects.glow(defs, color: 'yellow', blur: 4, strength: 2)
Filter blur = Effects.blur(defs, 5.0, 'blur1')
Filter gray = Effects.grayscale(defs, 'gray1')
Filter sepia = Effects.sepia(defs, 'sepia1')
Filter bright = Effects.brightness(defs, amount: 1.5)
Filter contrast = Effects.contrast(defs, amount: 1.5)

// Apply filters:
Effects.applyFilter(rect, shadow)  // Or: rect.filter('url(#dropShadow)')

// Actual implemented Shapes presets (presets/Shapes.groovy):
Rect rounded = Shapes.roundedRect(svg, x: 10, y: 10, width: 100, height: 60, radius: 10)
Polygon star = Shapes.star(svg, cx: 100, cy: 100, points: 5, outerRadius: 50, innerRadius: 25)
Path arrow = Shapes.arrow(svg, x1: 10, y1: 50, x2: 100, y2: 50, headSize: 10)
Polygon hexagon = Shapes.regularPolygon(svg, cx: 100, cy: 100, sides: 6, radius: 50)
Path bubble = Shapes.speechBubble(svg, x: 10, y: 10, width: 100, height: 60, tailX: 50, tailY: 80)
```

**Implementation Details**:
- ✅ Created `effects/Effects.groovy` with 8 filter presets
- ✅ Created `presets/Shapes.groovy` with 5 shape presets
- ✅ All presets use Map-based configuration for flexibility
- ✅ Effects integrated with existing Filter API
- ✅ Shapes use PathBuilder and Polygon for complex geometries

**Template System** (templates/Template.groovy, templates/ChartLegend.groovy):
```groovy
// Use the ChartLegend template
ChartLegend legend = new ChartLegend()
legend.apply(svg, [
    x: 450,
    y: 50,
    items: [
        [color: 'red', label: 'Product A'],
        [color: 'blue', label: 'Product B'],
        [color: 'green', label: 'Product C']
    ]
])

// Create custom templates by extending Template base class
class CustomTemplate extends Template {
    SvgElement apply(AbstractElementContainer parent, Map params) {
        // Implementation
    }
}
```

**DSL Configuration Closures** (AbstractElementContainer.groovy):
```groovy
// DSL-style configuration for all common shapes
svg.addCircle {
    cx 100
    cy 100
    r 50
    fill 'red'
    stroke 'black'
    strokeWidth 2
}

svg.addRect {
    x 10
    y 10
    width 100
    height 50
    fill 'blue'
}

svg.addG {
    fill 'green'
    stroke 'darkgreen'

    // Add children within the closure
    addCircle().cx(50).cy(50).r(20)
    addRect().x(100).y(30).width(40).height(40)
}
```

**Tests Created**:
- ✅ TemplateTest.groovy - Tests for Template base class
- ✅ ChartLegendTest.groovy - Tests for ChartLegend template
- ✅ DslConfigurationTest.groovy - 18 tests for DSL closure configuration

---

### 10. Extended Documentation ✅

**Current State**: COMPLETED (v0.9.0) - Comprehensive documentation suite

**Completed Documentation**:
- ✅ **examples.md** - Comprehensive code examples covering all major features
  - Basic shapes, styling, gradients, filters, text
  - Paths, transformations, groups and reuse
  - Accessibility, charts and graphs, advanced patterns
  - Performance optimization examples

- ✅ **cookbook.md** - Practical recipes for common tasks
  - Creating charts (bar, line, pie with legends)
  - Working with paths (curves, arrows, custom shapes)
  - Text effects (outlined, shadowed, gradient, on paths)
  - Responsive SVG techniques
  - Export and optimization strategies
  - Advanced patterns (clipping, masking, patterns, animations)

- ✅ **performance.md** - Performance tuning and optimization
  - File size optimization (30-50% reduction techniques)
  - Runtime performance (validation, batching, querying)
  - Memory optimization (avoiding leaks, efficient cloning)
  - Benchmarking with JMH
  - Best practices summary with checklist

- ✅ **best-practices.md** - Best practices and patterns
  - Code organization and structure
  - Naming conventions and ID management
  - Accessibility best practices
  - Performance guidelines
  - Security considerations (XSS prevention, input sanitization)
  - Testing strategies
  - Documentation standards
  - Common pitfalls and solutions

- ✅ **api-cheat-sheet.md** - Quick reference guide
  - Setup and basic operations
  - All shape types with syntax
  - Styling, transformations, paths
  - Text, groups, gradients, filters
  - Accessibility, utilities, validation
  - DSL configuration, selection, common patterns

- ✅ **migration-guide.md** - Migrating from other libraries
  - From Apache Batik (rendering → generation)
  - From JFreeSVG (Graphics2D → direct SVG)
  - From SVGSalamander (loading → full manipulation)
  - From Python svgwrite (similar patterns)
  - From JavaScript SVG.js (DOM → generation)
  - Key differences and migration checklist

**Deferred to Future**:
- ⏸️ Interactive examples/playground (web-based demo site)
- ⏸️ Video tutorials

---

### 11. Accessibility Helpers ✅

**Current State**: COMPLETED (v0.9.0) - Full ARIA support with validation

**Completed Enhancement**:
- ✅ ARIA attribute helper methods (8 methods on SvgElement)
- ✅ Role/label helpers with convenience methods
- ✅ Accessibility validation (AccessibilityRule)
- ✅ Comprehensive accessibility documentation (`doc/accessibility.md`)

```groovy
// Actual implemented API:
graphic.role('img')
       .ariaLabel('Company logo')
       .ariaDescribedBy('logo-desc')

// ARIA attributes:
element.role('button')                    // Set ARIA role
element.ariaLabel('Click me')             // Set aria-label
element.ariaLabelledBy('title1 desc1')    // Set aria-labelledby
element.ariaDescribedBy('details')        // Set aria-describedby
element.ariaHidden(true)                  // Set aria-hidden
element.ariaLive('polite')                // Set aria-live

// Convenience methods:
element.decorative()                      // role='presentation' + ariaHidden(true)
element.accessibleImage('Logo')           // role='img' + ariaLabel('Logo')

// Validation:
ValidationEngine engine = ValidationEngine.createAccessibility()
ValidationReport report = engine.validate(svg)
if (!report.isValid()) {
    report.errors.each { println "ERROR: ${it.message}" }
    report.warnings.each { println "WARNING: ${it.message}" }
}
```

**Implementation Details**:
- ✅ Added 8 ARIA helper methods to `SvgElement` base class (inherited by all elements)
- ✅ Created `AccessibilityRule` validation rule in `validation/rules/`
- ✅ Validates: root SVG accessibility, interactive elements, ARIA references
- ✅ Added `ValidationEngine.createAccessibility()` factory method
- ✅ Created comprehensive `doc/accessibility.md` guide with examples and best practices
- ✅ 40 new tests covering all ARIA functionality
- ✅ 100% backward compatible

---

### 13. Numeric Precision Control ✅

**Current State**: COMPLETED (v0.9.0) - Numbers now use intelligent precision control

**Completed Enhancement**:
- ✅ Configurable number formatter with intelligent rounding
- ✅ Default maximum of 3 decimal places (matching SVGO industry standard)
- ✅ Remove trailing zeros (12.120 → 12.12)
- ✅ Keep integers clean (50 not 50.0)
- ✅ Only reduce decimals if exceeding the maximum
- ✅ Apply formatting at `SvgElement.addAttribute()` level
- ✅ Keep calculations at full precision, round only at output
- ✅ Thread-local global configuration plus per-document settings

```groovy
// Actual implemented API:
circle.cx(12.123456789)      // Outputs: cx="12.123" (default 3 decimals)
rect.x(50.0)                 // Outputs: x="50" (no trailing zeros)
line.x1(10.12)               // Outputs: x1="10.12" (preserves existing precision)
path.d(PathBuilder.moveTo(1.2345, 5.6789))  // Outputs: d="M 1.235 5.679"

// Configuration:
NumberFormatter.setDefaultPrecision(5)  // Change globally (thread-local)
svg.setMaxPrecision(2)                  // Per-document setting
NumberFormatter.resetPrecision()        // Reset to default (3)
```

**Implementation Details**:
- ✅ Created `NumberFormatter` utility class (`utils/NumberFormatter.groovy`)
- ✅ Modified `SvgElement.addAttribute()` to detect Number types and format
- ✅ Default precision: 3 decimals (SVGO standard)
- ✅ Uses BigDecimal with HALF_UP rounding + stripTrailingZeros()
- ✅ Special handling for NaN, Infinity, and whole numbers
- ✅ Integration with ViewBox and PathBuilder
- ✅ Per-document precision via `Svg.setMaxPrecision()` and `getEffectivePrecision()`

**Benefits Achieved**:
- **30-50% file size reduction** for graphics with many coordinates
- **Industry standard alignment** (matches SVGO, Adobe Illustrator defaults)
- **Visual quality maintained** (0.001 precision imperceptible)
- **Cleaner output** (no trailing zeros, no .0 for integers)
- **User control** (configurable for special cases requiring higher precision)
- **100% backward compatible** - all 650 tests pass

---

### 12. Export Utilities (Optional - Separate Module)

**Architecture**: Multi-module Maven project
- Restructure current code as `gsvg-core` submodule but the artifact should still be named `gsvg`
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

- [ ] **Default attribute values**: Allow `rect.x(10)` without requiring `y(0)` (not needed - optional behavior)
- ✅ **Consistent method chaining**: Ensure ALL setters return `this` (already implemented)
- ✅ **Common shapes factory**: `Svg.createRoundedRect(...)` with sensible defaults (completed - 5 factory methods added)
- ✅ **Null-safe accessors**: Return defaults instead of null for missing attributes (completed - getAttributeOrDefault added)
- ✅ **Attribute removal**: Add `element.removeAttribute(name)` method (already existed!)
- ✅ **Batch attribute setting**: `rect.attrs([x: 10, y: 20, width: 100])` (implemented as alias to addAttributes)
- ✅ **Clone with modifications**: `rect.cloneWith([fill: 'red'])` (completed - cloneWith method added)
- ✅ **Element existence checks**: `element.hasAttribute('fill')` (already existed!)

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

**Phase 3: Advanced Features (v0.8.0)** ✅
7. ✅ CSS integration
8. ✅ Validation helpers
9. ✅ Performance benchmarks

**Phase 4: Polish (v0.9.0)** ✅
10. ✅ Numeric precision control (completed)
11. ✅ Accessibility helpers (completed)
12. ✅ Builder pattern enhancements - Effects & Shapes (completed)
13. ✅ Builder pattern enhancements - Templates & DSL (completed)
14. ✅ Extended documentation (completed)
15. ✅ Builder enhancement tests (completed)
16. Do a new round of performance benchmarks and compare with the doc/benchmarks.md

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
