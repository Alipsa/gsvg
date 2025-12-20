## 1. Code fixes (Priority: High - Security & Stability)

### 1.1. XXE Security Vulnerability [CRITICAL]
**File:** `src/main/groovy/se/alipsa/groovy/svg/io/SvgReader.groovy:248-257`  
**Issue:** No XXE protection configured in `createSAXParser()`. External entities and DTDs are not disabled.  
**Fix:** Add secure SAX features to the factory:
```groovy
factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
factory.setFeature("http://xml.org/sax/features/external-general-entities", false)
factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
factory.setXIncludeAware(false)
factory.setFeature(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, true)
```

### 1.2. SvgReader Element Dispatch Robustness
**File:** `src/main/groovy/se/alipsa/groovy/svg/io/SvgReader.groovy:93-185`  
**Issues:**  
- The switch dispatches on `qName`; prefixed SVG elements (e.g., `<svg:svg>`, `<svg:circle>`) fall through to the default branch and explode before `currentElement` is assigned.  
- Nested `<svg>` elements call `currentElement.addSvg()` without reassigning the returned element, so children of inner SVGs end up on the parent instead of the nested `<svg>`.  
**Fix:** Dispatch on `localName` (or strip any prefix from `qName`) and reassign `currentElement` when creating nested SVG elements so navigation follows the actual XML tree.

### 1.3. Namespace Declaration Handling
**File:** `src/main/groovy/se/alipsa/groovy/svg/io/SvgReader.groovy:187-199`  
**Issue:** Namespace declarations are only captured when `qName` contains `:`, so a default `xmlns="..."` is ignored and prefix extraction treats `xmlns` as part of the prefix rather than the attribute name. Default namespace changes (e.g., under `foreignObject` or metadata) are lost.  
**Fix:** Detect `xmlns` vs `xmlns:*` explicitly: use an empty prefix for the default namespace and the suffix after `:` for prefixed namespaces; avoid substringing when no colon is present.

### 1.4. ForeignObject Namespace Preservation
**Files:** `src/main/groovy/se/alipsa/groovy/svg/io/SvgReader.groovy:168-180`, `src/main/groovy/se/alipsa/groovy/svg/ForeignObject.groovy:55-57`, `src/main/groovy/se/alipsa/groovy/svg/ForeignElement.groovy`  
**Issue:** The parser builds a `QName` with the incoming namespace URI, but `ForeignObject.addElement` hardcodes XHTML and only accepts `String`, so namespace/URI information is dropped for foreign content without a prefix (e.g., MathML defaults).  
**Fix:** Thread the `QName`/`uri` from the reader into `addElement`, adding overloads that preserve the namespace instead of forcing XHTML as the default.

### 1.5. getQName Null Safety
**File:** `src/main/groovy/se/alipsa/groovy/svg/SvgElement.groovy:169-178`  
**Issues:**  
- No colon check before `indexOf(':')`, causing `StringIndexOutOfBoundsException`.  
- `element.getNamespaceForPrefix(prefix)` can return null, creating `QName` with a null namespace.  
**Fix:** Validate input and throw a descriptive exception when the prefix is missing or not bound.

### 1.6. Unused xlink Prefix Parameter
**File:** `src/main/groovy/se/alipsa/groovy/svg/SvgElement.groovy:165-167`  
**Issue:** `xlink(String prefix)` ignores the `prefix` parameter entirely, always returning `new QName('href', xlinkNs)`.  
**Used by:** `Image.groovy:60` - `addAttribute(xlink('href'), href)`  
**Fix:** Either use the prefix parameter or change method signature to `xlink()` with no parameter.

### 1.7. removeAttribute Null Safety
**File:** `src/main/groovy/se/alipsa/groovy/svg/SvgElement.groovy:98-101`  
**Issue:** `element.attribute(attribute)` returns null if attribute doesn't exist, passing null to `element.remove()`.  
**Fix:**
```groovy
T removeAttribute(String attribute) {
  def attr = element.attribute(attribute)
  if (attr != null) {
    element.remove(attr)
  }
  this as T
}
```

## 2. Docs (Priority: Medium)

### 2.1. Readme Dependency Accuracy
**File:** `readme.md:12-29`  
**Issue:** Gradle/Maven snippets use `se.apache.groovy` instead of `org.apache.groovy`. Target library version 0.2.0 matches the readme.  
**Action:** Correct the Groovy groupId in the snippets; keep the gsvg version at 0.2.0 for release.

---

## 3. SVG 1.1 and SVG 2 Coverage (Priority: Low - Feature Enhancement)

### Current Implementation Status
**Total elements implemented:** 63 elements across 84 source files

**Well-covered areas:**
- Core shapes: circle, ellipse, rect, line, polygon, polyline, path (7/7)
- Filter primitives: 26 filter elements including all light sources and component transfer functions
- Animation: animate, animateMotion, animateTransform, set (4/4)
- Gradients: linearGradient, radialGradient, stop
- Text: text, tspan, textPath
- Containers: svg, g, defs, symbol, use, a, switch, view, clipPath, mask, pattern, marker, filter

### 3.1. SVG 1.1 Font/Text Module Elements [Low Priority - Deprecated in SVG 2]
**Note:** Most font elements are deprecated in SVG 2 in favor of CSS @font-face  
**Elements to add (if needed for legacy support):**
- `font`, `font-face`, `font-face-src`, `font-face-uri`, `font-face-name`, `font-face-format`
- `glyph`, `missing-glyph`, `hkern`, `vkern`
- `glyphRef`, `altGlyph`, `altGlyphDef`, `altGlyphItem`
- `tref` (deprecated)

**Implementation pattern:**
1. Create element class in `src/main/groovy/se/alipsa/groovy/svg/`
2. Add case to `SvgReader.groovy:93-166` switch statement
3. Add convenience method to `AbstractElementContainer.groovy`

### 3.2. Missing SVG 1.1 Elements
**Elements:**
- `color-profile` - ICC color profile reference
- `cursor` - Custom cursor definition

### 3.3. SVG 2 Draft Elements
**Paint servers:**
- `solidcolor` - Simple solid color paint server
- `meshGradient`, `mesh`, `meshrow`, `meshpatch` - Mesh gradient support
- `hatch`, `hatchpath` - Hatching patterns

**Media elements:**
- `audio`, `video` - Embedded media

**Other:**
- `discard` - Element lifecycle control

### 3.4. SVG 2 href Support (Alongside xlink:href)
**Current state:** `a`, `image`, `script`, and `textPath` expose plain `href`, but `use`, `mpath`, and `feImage` remain xlink-only and the reader/writer do not normalize between `href` and `xlink:href`.  
**Implementation:** Add `href()` helpers (or stop forcing `xlink` inside `Use.addAttribute`), teach parsing to accept both, and serialize with `href` by default while still supporting `xmlns:xlink` when needed.

### 3.5. Attribute Coverage Expansion
**Areas needing expansion:**
- Presentation attributes (fill-rule, stroke-linejoin, stroke-linecap, etc.)
- Paint server attributes (spreadMethod, gradientTransform, gradientUnits)
- Marker attributes (markerWidth, markerHeight, markerUnits, orient, refX, refY)
- Filter attributes (color-interpolation-filters, filterRes)
- Text attributes (baseline-shift, dominant-baseline, text-decoration, word-spacing, letter-spacing)

---

## Implementation Order (Recommended)

### Phase 1: Security & Stability
1. [x] 1.1 - XXE vulnerability fix (CRITICAL)
2. [x] 1.2 - SvgReader element dispatch robustness
3. [x] 1.3 - Namespace declaration handling fix
4. [x] 1.4 - ForeignObject namespace preservation
5. [x] 1.5 - getQName null safety
6. [x] 1.6 - xlink unused parameter cleanup
7. [x] 1.7 - removeAttribute null safety

### Phase 2: Documentation
8. [ ] 2.1 - Readme dependency accuracy

### Phase 3: Feature Enhancement (As Needed)
9. [ ] 3.4 - SVG 2 href support
10. [ ] 3.5 - Attribute coverage expansion
11. [ ] 3.2 - Missing SVG 1.1 elements (color-profile, cursor)
12. [ ] 3.3 - SVG 2 draft elements
13. [ ] 3.1 - Font module elements (low priority, deprecated)
