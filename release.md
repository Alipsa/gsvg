# gsvg release notes

## Version 0.8.0 - 2026-01-15

**Advanced Features**

This release implements three major features: CSS integration, validation helpers, and performance benchmarks. All features are fully backward compatible and opt-in.

### CSS Integration
- **New CSS object model** with ph-css 8.1.1 integration for robust parsing
- **CSS classes** (`se.alipsa.groovy.svg.css.CssRule`, `CssStyleSheet`, `CssParser`)
- **Style element enhancements** on `Style`:
  - `addRule(selector, declarations)` - Add CSS rules programmatically
  - `removeRule(selector)` - Remove rules by selector
  - `getStyleSheet()` - Get parsed CssStyleSheet object
  - `setStyleSheet(CssStyleSheet)` - Replace stylesheet
  - `getRules()` - Get all CSS rules
- **Inline style Map API** on `SvgElement`:
  - `style(Map<String, Object>)` - Set inline styles from map
  - `getStyleMap()` - Parse inline style to map
  - `getStyleProperty(property)` - Get specific style property
- **CSS class management** on `SvgElement`:
  - `addClass(className)` - Add CSS class
  - `removeClass(className)` - Remove CSS class
  - `toggleClass(className)` - Toggle CSS class
  - `hasClass(className)` - Check if class exists
  - `getClasses()` - Get all CSS classes as list
- **Full method chaining** support for all CSS methods
- **Maintains backward compatibility** - all string-based style/class APIs still work

```groovy
// CSS object model:
def style = svg.addStyle()
style.addRule('.highlight', [fill: 'red', stroke: 'black', 'stroke-width': '2'])
style.addRule('#logo', [transform: 'scale(2)'])

// Inline style Map API:
rect.style([fill: 'red', stroke: 'blue', 'stroke-width': '2'])
def fillColor = rect.getStyleProperty('fill') // 'red'

// Class management:
rect.addClass('highlight').addClass('selected')
rect.removeClass('selected')
if (rect.hasClass('highlight')) { /* ... */ }
```

### Validation Helpers
- **Complete opt-in validation system** - library remains permissive by default
- **Validation infrastructure**:
  - `ValidationLevel` enum (ERROR, WARNING, INFO)
  - `ValidationIssue` - Immutable issue objects with level, message, element path
  - `ValidationReport` - Aggregates issues with query methods
  - `ValidationRule` interface - Extensible validation rule system
  - `ValidationEngine` - Orchestrates validation with customizable rule sets
- **Six core validation rules**:
  - `RequiredAttributeRule` - Validates required attributes (circle needs cx/cy/r, etc.)
  - `AttributeValueRule` - Validates attribute value domains and ranges
  - `ElementNestingRule` - Validates valid parent-child relationships
  - `ViewBoxRule` - Validates viewBox format and dimensions
  - `HrefRule` - Validates href/xlink:href references point to existing IDs
  - `DuplicateIdRule` - Detects duplicate element IDs
- **Validation methods** on `Svg` and `SvgElement`:
  - `validate()` - Validate with default rules
  - `validate(ValidationEngine)` - Validate with custom rules
  - `isValid()` - Quick validation check (no errors)
- **Optimized performance** - All rules run in O(N) time
- **No breaking changes** - All invalid SVG creation continues to work, validation is purely opt-in

```groovy
// Basic validation:
def report = svg.validate()
if (report.isValid()) {
  // No errors (warnings/info don't fail validation)
}

// Inspect issues:
report.getErrors().each { println "ERROR: ${it.message}" }
report.getWarnings().each { println "WARNING: ${it.message}" }
report.getInfo().each { println "INFO: ${it.message}" }

// Custom validation:
def engine = ValidationEngine.createDefault()
engine.removeRule("VIEWBOX_RULE") // Disable specific rule
svg.validate(engine)
```

### Performance Benchmarks
- **Complete JMH benchmark suite** (JMH 1.37)
- **9 benchmark classes** covering all major operations:
  - `ParsingBenchmark` - Parse simple/medium/large/complex SVGs
  - `SerializationBenchmark` - toXml() and toXmlPretty() performance
  - `CreationBenchmark` - Element creation and fluent API chaining
  - `MergingBenchmark` - Horizontal/vertical/on-top merge performance
  - `CloningBenchmark` - Deep copy operations
  - `UtilityBenchmark` - Color, PathBuilder, ViewBox utilities
  - `SelectionBenchmark` - Type filtering, XPath queries, descendants
  - `RunAllBenchmarks` - Convenience runner for all benchmarks
  - `BenchmarkBase` - Shared benchmark infrastructure
- **4 test SVG files** in `src/test/resources/benchmarks/`:
  - `simple.svg` (10 elements) - Basic operations
  - `medium.svg` (100 elements) - Typical documents
  - `large.svg` (1000 elements) - Stress testing
  - `complex.svg` - Real-world structure
- **Benchmark sanity tests** - 8 tests ensuring benchmarks execute without errors
- **Comprehensive results documented** in `doc/benchmarks.md` with performance analysis
- **Easy to run**: `mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass="benchmarks.RunAllBenchmarks"`

### Tests
- Added 146 new tests (28 CSS tests, 73 validation tests, 8 benchmark tests, 37 rule tests)
- Total test count: 593 tests (was 447 in v0.7.0)
- 100% pass rate
- Comprehensive coverage of all new features

### Bug Fixes
- Fixed HrefRule performance issue (O(N²) → O(N) optimization)
- Fixed HrefRule namespace exception when xlink namespace not bound
- Added proper exception handling for namespace-prefixed attributes

### Documentation
- Updated `kanban/in-progress/svgTodo.md` marking Phase 3 complete
- Added extensive inline documentation with usage examples
- All new classes fully documented with Groovydoc
- Library status updated to 593 tests

**API additions/updates**
- `se.alipsa.groovy.svg.css.CssRule`: CSS rule value object
- `se.alipsa.groovy.svg.css.CssStyleSheet`: CSS stylesheet management
- `se.alipsa.groovy.svg.css.CssParser`: CSS parsing utilities (package-private)
- `Style`: `addRule()`, `removeRule()`, `getStyleSheet()`, `setStyleSheet()`, `getRules()`
- `SvgElement`: `style(Map)`, `getStyleMap()`, `getStyleProperty()`, `addClass()`, `removeClass()`, `toggleClass()`, `hasClass()`, `getClasses()`
- `Svg`: `validate()`, `validate(ValidationEngine)`, `isValid()`
- `SvgElement`: `validate()`, `isValid()`
- `se.alipsa.groovy.svg.validation.*`: Complete validation infrastructure
- `se.alipsa.groovy.svg.validation.rules.*`: Six validation rule implementations

**Dependency updates**
- Added ph-css 8.1.1 for css management
- Added JMH 1.37 for benchmarking (test scope)

**Breaking changes**
- None - all additions are backward compatible and opt-in

## Version 0.7.0 - 2026-01-14

**Core Features Enhancement**

This release implements features focusing on advanced path manipulation, coordinate system utilities, and powerful element selection methods.

### Path Data Manipulation
- **New `PathBuilder` class** (`se.alipsa.groovy.svg.utils.PathBuilder`) for fluent path construction
- **All SVG path commands supported**:
  - MoveTo: `moveTo(x, y)`, `moveToRel(dx, dy)`, `M()`, `m()`
  - LineTo: `lineTo(x, y)`, `lineToRel(dx, dy)`, `L()`, `l()`
  - Horizontal/Vertical: `horizontalTo(x)`, `verticalTo(y)`, `H()`, `V()`, `h()`, `v()`
  - Cubic bezier: `curveTo()`, `curveToRel()`, `smoothCurveTo()`, `C()`, `S()`, `c()`, `s()`
  - Quadratic bezier: `quadTo()`, `quadToRel()`, `smoothQuadTo()`, `Q()`, `T()`, `q()`, `t()`
  - Arcs: `arc()`, `arcRel()`, `A()`, `a()`
  - Close path: `closePath()`, `Z()`, `z()`
- **Fluent API**: Full method chaining support for building complex paths
- **Path parsing**: `PathBuilder.parse(pathString)` to parse and extend existing paths
- **Path integration**: `Path.d(PathBuilder)` accepts PathBuilder objects directly
- **Utility methods**: `copy()`, `getCommands()`, `isEmpty()`, `toString()`

### ViewBox / Coordinate Helpers
- **New `ViewBox` class** (`se.alipsa.groovy.svg.utils.ViewBox`) for viewport management
- **Creation methods**:
  - `ViewBox.of(minX, minY, width, height)` - Create viewBox
  - `ViewBox.parse(string)` - Parse viewBox strings
- **Transformations** (all immutable, return new ViewBox):
  - `scale(factor)`, `scale(scaleX, scaleY)` - Scale dimensions
  - `translate(dx, dy)` - Shift coordinates
  - `expand(margin)` - Add margins
  - `centerAt(cx, cy)` - Center at point
  - `withAspectRatio(ratio)` - Set aspect ratio
  - `fitToContain(other)` - Fit viewBox to contain another
- **Query methods**:
  - `getCenterX()`, `getCenterY()`, `getMaxX()`, `getMaxY()`, `getAspectRatio()`
  - `contains(x, y)`, `contains(viewBox)`, `intersects(viewBox)`
- **Integration with Svg, Symbol, and View elements**:
  - `svg.viewBox(ViewBox)` - Accept ViewBox objects
  - `svg.viewBox(minX, minY, width, height)` - Four-parameter convenience
  - `svg.getViewBoxObject()` - Parse and return ViewBox object
- **Output**: `toString()` produces SVG-compatible viewBox strings, `toMap()` for data access

### Enhanced Element Selection
- **Filter and find methods**:
  - `filter(predicate)` - Filter elements by closure
  - `findAll(Class, predicate)` - Find all of a type, optionally filtered
  - `findFirst(predicate)` - Find first matching element
  - `findFirst(Class)` - Find first of a type
- **Recursive search**:
  - `descendants()` - Get all descendant elements recursively
  - `descendants(Class)` - Get all descendants of a specific type
- **XPath queries**:
  - `xpath(query)` - Execute XPath queries on the SVG DOM
  - Full XPath 1.0 support via dom4j/Jaxen
- **Aggregate operations**:
  - `count(predicate)` - Count matching elements
  - `any(predicate)` - Check if any element matches
  - `all(predicate)` - Check if all elements match
  - `collect(transform)` - Transform and collect values from elements

### Tests
- Added 113 new tests (29 PathBuilder tests, 54 ViewBox tests, 30 enhanced selection tests)
- Total test count: 447 tests (all passing)
- 100% pass rate
- Comprehensive coverage of new utilities
- XPath namespace handling fully functional with transparent query transformation

### Documentation
- Updated `kanban/in-progress/svgTodo.md` marking Phase 2 complete
- Added extensive inline documentation with usage examples
- All new classes fully documented with Groovydoc

**API additions/updates**
- `se.alipsa.groovy.svg.utils.PathBuilder`: Complete path building utility
- `se.alipsa.groovy.svg.utils.ViewBox`: ViewBox manipulation and coordinate helpers
- `Path`: `d(PathBuilder)`, `builder(PathBuilder)` methods
- `Svg`, `Symbol`, `View`: `viewBox(ViewBox)`, `viewBox(minX, minY, width, height)`, `getViewBoxObject()` methods
- `ElementContainer`: `filter()`, `findAll()`, `findFirst()`, `descendants()`, `xpath()`, `count()`, `any()`, `all()`, `collect()` methods

**Breaking changes**
- None - all additions are backward compatible

## Version 0.6.0 - 2026-01-13
**Ease of Use Enhancements**

This release focuses on improving API ergonomics and developer experience with new utilities for common SVG operations.

### Transform Utilities
- **Fluent transform API**: Added transform methods to `AbstractShape` for all SVG transformations
  - `rotate(angle)` and `rotate(angle, cx, cy)` - rotation with optional center point
  - `translate(tx, ty)` and `translate(tx)` - translation (dual-axis or x-axis only)
  - `scale(sx, sy)` and `scale(s)` - scaling (non-uniform or uniform)
  - `skewX(angle)` and `skewY(angle)` - skewing transformations
  - `matrix(a, b, c, d, e, f)` - direct matrix transform
  - `transform(string)` - backward-compatible string setter
- **Chaining support**: All transform methods return `this` for method chaining
- **Append behavior**: Transforms append to existing transform attribute (e.g., `rect.rotate(45).translate(10, 20)`)
- **Backward compatible**: String-based transform API still works (`rect.transform('rotate(45)')`)

### Color Utilities
- **New `Color` class** (`se.alipsa.groovy.svg.utils.Color`) for color creation and manipulation
- **Multiple creation methods**:
  - `Color.rgb(r, g, b)` - RGB values (0-255)
  - `Color.rgba(r, g, b, a)` - RGBA with alpha (0.0-1.0)
  - `Color.hex(string)` - Hex colors (#RGB, #RRGGBB, #RRGGBBAA)
  - `Color.hsl(h, s, l)` and `Color.hsla(h, s, l, a)` - HSL/HSLA values
  - `Color.named(name)` - Named colors (147 SVG color names supported)
  - `Color.parse(string)` - Auto-detect and parse any color format
- **Color manipulation**:
  - `withAlpha(a)` - Create new color with different alpha
  - `darken(amount)` - Darken by percentage (0.0-1.0)
  - `lighten(amount)` - Lighten by percentage
  - `interpolate(other, t)` - Mix two colors (t=0.0 to 1.0)
- **Multiple output formats**:
  - `toHex(includeAlpha)` - Hex string representation
  - `toRgb()` - RGB/RGBA string
  - `toHsl()` - HSL/HSLA string
  - `toString()` - SVG-compatible format (hex for opaque, rgba for transparent)
- **147 named SVG colors**: All standard SVG color names (red, blue, crimson, etc.)
- **Type safety**: Proper value clamping, equals/hashCode support

### Quick Wins
- **`hasAttribute(name)`**: Check if an SVG attribute exists on an element
- **`attrs(map)`**: Convenient alias for `addAttributes(map)` for batch attribute setting

### Debugging
- **`toString()` implementation**: Added toString() for all SVG elements showing element name, attributes, and parent information for easier debugging

### Tests
- Added 45 new tests (12 transform tests, 30 color tests, 3 utility tests)
- Total test count: 303 tests (all passing)
- Comprehensive coverage of new utilities

### Documentation
- Updated `svgTodo.md` with implementation roadmap and completed features
- Improved code documentation with usage examples

**API additions/updates**
- `AbstractShape`: `rotate()`, `translate()`, `scale()`, `skewX()`, `skewY()`, `matrix()`, `transform()`
- `SvgElement`: `hasAttribute(name)`, `attrs(map)`
- `se.alipsa.groovy.svg.utils.Color`: Full color utility class with creation, manipulation, and conversion methods
- All `SvgElement` subclasses: `toString()` for debugging

**Breaking changes**
- None - all additions are backward compatible

## Version 0.5.0 - 2026-01-09
- Merging: new `SvgMerger` utility to merge SVGs horizontally, vertically, or layered on top using pure object-oriented copying (no XML serialization).
- Cloning support: added `SvgElementFactory` and adopting constructors across all SVG element types to enable deep copies that preserve DOM and children lists.
- API ergonomics: `SvgElement.clone(AbstractElementContainer)` for deep cloning of element trees.
- Documentation: new `doc/merging.md`, updated `doc/overview.md`, and comprehensive sprint summaries.
- Tests: added `SvgMergerTest` and `SvgElementFactoryTest` for merge/copy behavior and coverage.
- Build cleanup: upgraded JUnit, JaCoCo, and central-publishing plugins; removed legacy Gradle artifacts.

**API additions/updates**
- `SvgMerger.mergeHorizontally`, `SvgMerger.mergeVertically`, `SvgMerger.mergeOnTop`.
- `SvgElementFactory.fromElement`, `SvgElementFactory.deepCopy`, `SvgElementFactory.copyChildren`.
- `SvgElement.clone(AbstractElementContainer)` deep clone helper.

## Version 0.4.0 - 2026-01-05
- API ergonomics: property-style attribute access on SvgElement, addAttributes(Map), and Map-based addX(Map) helpers across element containers (filters, gradients, defs, text, etc.).
- Attribute coverage: expanded fluent setters/getters across shapes, text, gradients, filters, and animation elements (Phase 1-3 convenience methods).
- Animation/text improvements: more complete Animate/AnimateMotion/AnimateTransform/Set attribute helpers plus additional Text/Tspan/TextPath conveniences.
- Bug fixes: AbstractPoly.addPoint() and addPoints() now handle missing points attributes correctly.
- Build & tests: @CompileStatic across core classes, JaCoCo coverage reporting, Gradle wrapper removal, and expanded unit tests (filters, gradients, text, shapes, script/switch, polygon/polyline).

**API additions/updates**
- Property-style attribute read/write fallback when no explicit getter/setter exists.
- Map-based attributes in element factory methods (for example, addRect([x: 1, y: 2])) plus addAttributes(Map).
- Additional attribute setters for gradients, stops (stop-opacity), filter primitives, and text positioning/typography.

## Version 0.3.0 - 2025-12-30
- Documentation overhaul: new docs in doc/creating.md, doc/parsing.md, and doc/overview.md, plus expanded Groovydoc across the public API and readme tweaks.
- API ergonomics: added many String overloads for numeric SVG attributes to allow unit/percent values across shapes, text, filters, patterns, markers, symbols, and foreignObject; plus new paint helpers
  (dash array/offset, opacity, miterlimit) on shapes.
- Build fix: Maven sources JAR now includes Groovy sources (build-helper source roots + source plugin includes).
- Test coverage: added tests for parsing docs examples and expanded filter/FeComposite coverage.

**API additions/updates**
- New convenience setters for SVG attributes across shapes and filters (String overloads for unit/percent values).
- Additional stroke/fill presentation helpers on shapes (dasharray/dashoffset, opacity, miterlimit).
- Animation and filter helper extensions (e.g., String overloads for from/k1‑k4, etc.).

## Version 0.2.0 - 2025-12-20
- Security/Parsing
  - Hardened SAX parser against XXE/DTD and enabled secure processing in SvgReader (prevents external entity resolution).
  - Improved element dispatch for prefixed SVG tags and nested <svg> handling; namespace declarations now captured before attribute parsing and foreign/default namespaces preserved (e.g. foreignObject/metadata).
  - Safer QName handling and null-safe attribute removal in SvgElement.
- SVG 1.1 / SVG 2 Coverage
  - Added missing elements: color-profile, cursor, solidcolor, mesh gradients (meshGradient, mesh, meshrow, meshpatch), hatching (hatch, hatchpath), media (audio, video), discard, and font module elements (font, font-face*, glyph, missing-glyph, hkern, vkern, glyphRef, altGlyph*, tref).
  - Added href parity for SVG2: use, image, mpath, feImage, textPath, script accept href with xlink fallback; getters normalized.
- Attribute/API Enhancements
  - Gradients gain gradientTransform and spreadMethod; filters gain colour-interpolation-filters and filterRes.
  - Markers add markerUnits and orient.
  - Shapes add fill-rule, stroke-linejoin, stroke-linecap.
  - Text adds baseline-shift, dominant-baseline, text-decoration, word-spacing, letter-spacing.
  - Fluent chaining fixed across new setters; Use.getHref() aligned with other elements.
- Build & Publishing
  - Added Maven POM with SCM, license, developer, and OSSRH distribution metadata; Groovy groupId corrected to org.apache.groovy.
  - Dependency updates and documentation tweaks.
- Tests
  - New regression tests for security (XXE block), namespace preservation, href support, and new element parsing; full suite passes with Maven.

## Version 0.1.0 - 2024-02-16
- Initial release of the Groovy Scalar Vector Graphics (gsvg) library.
