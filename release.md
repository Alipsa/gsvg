# gsvg release notes

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

