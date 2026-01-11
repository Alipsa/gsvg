# gsvg release notes

## Version 0.6.0 - In progress
- Add toString() implementation for debugging SVG elements

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
