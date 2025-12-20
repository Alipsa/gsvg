## 1. Code fixes
- 1.1. Fix namespace declaration handling in `src/main/groovy/se/alipsa/groovy/svg/io/SvgReader.groovy`: `startElement` only treats namespace declarations when `attributes.getLocalName(i)` is blank, which likely misses `xmlns`/`xmlns:prefix` attributes; detect the XMLNS namespace or `qName` starting with `xmlns`, and correctly handle the default namespace (empty prefix) instead of `"xmlns"`.
- 1.2. Harden XML parsing in `src/main/groovy/se/alipsa/groovy/svg/io/SvgReader.groovy` by disabling external entity resolution and DTDs (XXE); set secure SAX features on the factory and/or parser.
- 1.3. Guard `src/main/groovy/se/alipsa/groovy/svg/SvgElement.groovy` `getQName` against unknown namespace prefixes (avoid `new QName(..., null)`), or throw a clearer error when a prefix is not declared.
- 1.4. Fix or remove the unused `prefix` parameter in `src/main/groovy/se/alipsa/groovy/svg/SvgElement.groovy` `xlink(String prefix)`; currently it always returns `href` in the xlink namespace.
- 1.5. Consider null-safety in `src/main/groovy/se/alipsa/groovy/svg/SvgElement.groovy` `removeAttribute`: `element.remove(element.attribute(attribute))` can pass `null` if the attribute does not exist.

## 2. Docs
- 2.1. Update `readme.md` dependency examples to match the project’s next release versions (e.g., Groovy 5.x and `gsvg` 0.2.0).

## 3. SVG 1.1 and SVG 2 coverage
- 3.1. Add SVG 1.1 font/text module elements and wiring (classes + `SvgReader` + `AbstractElementContainer`): `font`, `font-face`, `font-face-src`, `font-face-uri`, `font-face-name`, `font-face-format`, `glyph`, `missing-glyph`, `hkern`, `vkern`, `glyphRef`, `altGlyph`, `altGlyphDef`, `altGlyphItem`, `tref`.
- 3.2. Add remaining SVG 1.1 elements that are currently absent: `color-profile`, `cursor`.
- 3.3. Add SVG 2 draft elements and support in the reader: `solidcolor`, `meshGradient`, `mesh`, `meshrow`, `meshpatch`, `hatch`, `hatchpath`, `discard`, `audio`, `video`.
- 3.4. Support SVG 2 `href` (no xlink namespace) alongside SVG 1.1 `xlink:href` across elements like `a`, `use`, `image`, `mpath`, `script`, and update parsing/serialization to accept both.
- 3.5. Expand attribute coverage for SVG 1.1/2 (presentation attributes, paint servers, marker attributes, filter attributes, text attributes) so element classes expose the full spec rather than a minimal subset.
