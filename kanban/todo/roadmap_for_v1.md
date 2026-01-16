# gsvg v1.0 Roadmap

## Overview

Version 1.0 represents the production-complete release of gsvg, incorporating deferred features, multi-module architecture, and final polish for enterprise adoption.

**Current Status (v0.9.0)**: Feature-complete for single-module use
- ✅ All Phase 1-4 features implemented (701 tests passing)
- ✅ Complete SVG 1.1/2 element coverage
- ✅ Comprehensive documentation suite
- ✅ Accessibility, validation, and performance optimizations
- ✅ Quick Wins all implemented

**v1.0 Goals**:
- Multi-module architecture (core + optional modules)
- Export/rendering capabilities (optional module)
- Enhanced selection with CSS selectors
- Enhanced documentation and examples
- Performance validation and optimization
- Production-ready stability and polish

---

## Phase 5: Multi-Module Architecture (v1.0-alpha)

### 1. Project Restructuring

**Status**: ✅ Completed (v1.0.0-SNAPSHOT)

**Goal**: Separate concerns into core library and optional modules while maintaining backward compatibility.

**Completion Summary**:
- Restructured from single-module to multi-module Maven architecture
- Created parent POM with three modules: gsvg-core, gsvg-export, gsvg-examples
- Moved all source code to gsvg-core module (git history preserved)
- 807 total tests passing (769 in gsvg-core, 38 in gsvg-export)
- 100% backward compatibility maintained (artifact ID unchanged)
- Zero regressions to existing functionality

#### 1.1 Multi-Module Maven Setup
- [x] Create parent POM at project root (artifactId: `gsvg-parent`)
- [x] Create `gsvg-core/` submodule
  - [x] Move existing source code to `gsvg-core/src/`
  - [x] Move existing test code to `gsvg-core/src/test/`
  - [x] Update package structure (no changes needed)
  - [x] Artifact ID remains `gsvg` for backward compatibility
- [x] Update all POMs with correct parent/module relationships
- [x] Update dependency management in parent POM
- [x] Verify all 701 tests pass after restructure (now 769 tests)
- [ ] Update GitHub Actions CI/CD for multi-module build (deferred - not critical for v1.0)

#### 1.2 Module Dependency Strategy ✅

**Implemented Structure**:
```xml
<!-- Parent POM structure -->
<modules>
  <module>gsvg-core</module>
  <module>gsvg-export</module>
  <module>gsvg-examples</module>
</modules>
```

**Artifact naming**:
- ✅ Core library: `se.alipsa.groovy:gsvg:1.0.0-SNAPSHOT` (unchanged for backward compatibility)
- ✅ Export module: `se.alipsa.groovy:gsvg-export:1.0.0-SNAPSHOT`
- ✅ Examples module: `se.alipsa.groovy:gsvg-examples:1.0.0-SNAPSHOT` (placeholder)

**Module Dependencies**:
- ✅ gsvg-core: No dependencies on other modules (independent)
- ✅ gsvg-export: Depends on gsvg-core (includes jsvg library)
- ✅ gsvg-examples: Depends on gsvg-core and gsvg-export (placeholder)

**Benefits Achieved**:
- ✅ Core library remains lightweight (~200KB)
- ✅ Optional features don't bloat core dependency
- ✅ Users only include what they need
- ✅ Easier to maintain and test separately
- ✅ Multi-module reactor build working perfectly

---

## Phase 6: Export Module (v1.0-beta)

### 2. gsvg-export Module

**Status**: ✅ Completed (v1.0.0-SNAPSHOT)

**Goal**: Provide SVG rendering, optimization, and export capabilities as an optional module.

**Completion Summary**:
- Created SvgRenderer for PNG/JPEG export using jsvg
- Created SvgOptimizer for file size reduction
- Created SvgFormatter for pretty printing
- 38 comprehensive tests (all passing)
- 807 total tests passing (769 from phases 1-7 + 38 export)
- Zero regressions to existing functionality

#### 2.1 Module Setup
- [x] Create `gsvg-export/` submodule structure
- [x] Add dependency on `gsvg-core`
- [x] Add jsvg dependency: `com.github.weisj:jsvg:1.5.0`
- [x] Create package structure: `se.alipsa.groovy.svg.export`

#### 2.2 SVG Rendering (PNG/JPEG Export)

**Technology Choice**: ✅ [jsvg](https://github.com/weisJ/jsvg)
- Pure Java implementation (no native dependencies)
- Better SVG 2 support than Batik
- Active development and modern codebase
- Lightweight (~500KB vs Batik's ~15MB)

**Implementation**:
- [x] Create `SvgRenderer` class
  - [x] `toPng(Svg svg, File output, Map options)` - Render to PNG
  - [x] `toPng(Svg svg, OutputStream output, Map options)` - Stream output
  - [x] `toJpeg(Svg svg, File output, Map options)` - Render to JPEG
  - [x] `toBufferedImage(Svg svg, Map options)` - Get BufferedImage
- [x] Support rendering options:
  - [x] `width`, `height` - Output dimensions (scale from SVG viewBox)
  - [x] `scale` - Scale factor (alternative to width/height)
  - [x] `backgroundColor` - Background color for JPEG
  - [x] `quality` - JPEG quality (0.0-1.0)
  - [x] `antialiasing` - Enable/disable antialiasing

**Example Usage**:
```groovy
@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer

Svg svg = new Svg(200, 200)
svg.addCircle().cx(100).cy(100).r(50).fill('red')

// Export to PNG
SvgRenderer.toPng(svg, new File('output.png'), [
    width: 800,
    height: 800,
    antialiasing: true
])

// Export to JPEG with background
SvgRenderer.toJpeg(svg, new File('output.jpg'), [
    width: 800,
    height: 800,
    backgroundColor: 'white',
    quality: 0.95
])

// Get BufferedImage for further processing
def image = SvgRenderer.toBufferedImage(svg, [scale: 2.0])
```

#### 2.3 SVG Optimization

**Goal**: Reduce SVG file size through various optimization techniques.

**Implementation**:
- [x] Create `SvgOptimizer` class
  - [x] `optimize(Svg svg, Map options)` - Optimize and return new Svg
  - [x] `optimizeInPlace(Svg svg, Map options)` - Optimize existing Svg
- [x] Optimization features:
  - [x] Remove XML comments
  - [x] Remove metadata elements
  - [x] Remove unused defs
  - [x] Collapse redundant groups
  - [x] Round numbers to precision (already have NumberFormatter)
  - [x] Remove default attributes
  - [x] Convert colors to shorter formats
  - [x] Minify path data (remove unnecessary spaces)
  - [x] Remove invisible elements (opacity=0, display=none)
- [ ] Merge similar paths (deferred to post-v1.0)

**Example Usage**:
```groovy
import se.alipsa.groovy.svg.export.SvgOptimizer

Svg svg = SvgReader.read(new File('input.svg'))

Svg optimized = SvgOptimizer.optimize(svg, [
    removeComments: true,
    removeMetadata: true,
    removeUnusedDefs: true,
    precision: 2,
    minifyPathData: true,
    removeDefaults: true
])

println "Original: ${svg.toString().length()} bytes"
println "Optimized: ${optimized.toString().length()} bytes"

new File('output.svg').text = optimized.toString()
```

#### 2.4 SVG Prettification

**Goal**: Format SVG for human readability.

**Implementation**:
- [x] Create `SvgFormatter` class
  - [x] `prettify(Svg svg, Map options)` - Return formatted XML string
  - [x] `format(Svg svg, Map options)` - Return formatted Svg
  - [x] `prettifyWithSortedAttributes(Svg svg)` - Sort attributes alphabetically
  - [x] `prettifyWithGrouping(Svg svg)` - Group similar elements with blank lines
- [x] Formatting options:
  - [x] `indent` - Indentation string (default: "  ")
  - [x] `newline` - Newline character (default: "\n")
  - [x] `sortAttributes` - Sort attributes alphabetically
  - [x] `groupElements` - Group similar elements with blank lines
- [ ] `maxLineLength` - Wrap long attribute lists (deferred to post-v1.0)

**Example Usage**:
```groovy
import se.alipsa.groovy.svg.export.SvgFormatter

String formatted = SvgFormatter.prettify(svg, [
    indent: '    ',
    sortAttributes: true,
    groupElements: true
])

new File('formatted.svg').text = formatted
```

#### 2.5 Testing
- [x] Create test suite for gsvg-export module (38 tests total)
  - [x] Rendering tests (verify PNG/JPEG output) - 10 tests
  - [x] Optimization tests (verify file size reduction) - 13 tests
  - [x] Prettification tests (verify formatting) - 15 tests
  - [x] Integration tests with complex SVGs
- [ ] Performance benchmarks for rendering (deferred to Phase 9)
- [ ] Memory usage tests for large SVGs (deferred to Phase 9)

---

## Phase 7: Enhanced Selection (v1.0-beta)

### 3. CSS Selector Support

**Status**: ✅ Completed (v1.0.0-SNAPSHOT)

**Goal**: Add CSS selector support for element selection alongside existing XPath.

**Completion Summary**:
- Created custom CSS selector engine (400+ lines)
- 49 comprehensive tests (all passing)
- 769 total tests passing (720 original + 49 CSS selector)
- Documentation updated in `doc/navigating.md`
- Zero regressions to existing functionality

#### 3.1 CSS Selector Implementation

**Technology Choice**: ✅ Implemented custom selector parser (Option 3)
- Lightweight and performant
- No additional dependencies required
- Full control over implementation

**Implementation**:
- [x] Create `CssSelectorEngine` class in `se.alipsa.groovy.svg.utils`
- [x] Support common CSS selectors:
  - [x] Type selectors: `rect`, `circle`, `path`
  - [x] Class selectors: `.highlight`, `.selected`
  - [x] ID selectors: `#logo`, `#background`
  - [x] Attribute selectors: `[fill="red"]`, `[stroke]`
  - [x] Combinators: `g rect` (descendant), `g > rect` (child)
  - [x] Pseudo-classes: `:first-child`, `:last-child`, `:nth-child(n)`
  - [x] Universal selector: `*`
  - [x] Compound selectors: `circle[fill="red"]`, `rect:first-child`
  - [x] Chained combinators: `g g circle`, `svg > g > circle`
- [x] Add to ElementContainer:
  - [x] `css(String selector)` - Select elements by CSS selector
  - [x] `cssFirst(String selector)` - Select first matching element

**Not Implemented** (deferred):
- ❌ Comparison operators `[width > 100]` - Not part of CSS specification
  - Use `filter()` instead: `svg.filter { it.getAttribute('width') as int > 100 }`
- ❌ Sibling combinator `rect + circle` - Deferred to post-v1.0

**Example Usage**:
```groovy
// Select all rectangles
def rects = svg.css('rect')

// Select elements with class
def highlighted = svg.css('.highlight')

// Select by ID
def logo = svg.cssFirst('#logo')

// Complex selectors
def redRects = svg.css('rect[fill="red"]')
def childCircles = svg.css('g > circle')
def firstRect = svg.cssFirst('rect:first-child')

// Chained combinators
def nested = svg.css('g g circle')
```

#### 3.2 Performance Optimization
- [ ] Benchmark CSS selector vs XPath performance (deferred to Phase 9)
- [ ] Add caching for frequently used selectors (deferred to post-v1.0)
- [ ] Optimize attribute lookups (deferred to post-v1.0)

**Note**: Current implementation is efficient for typical use cases. Advanced optimizations deferred based on real-world performance data.

#### 3.3 Testing
- [x] Create comprehensive test suite for CSS selectors (49 tests)
- [x] Test all selector types (type, class, ID, attribute, combinators, pseudo-classes)
- [x] Test edge cases and invalid selectors (malformed selectors, unclosed brackets/parentheses)
- [x] Test chained combinators (descendant and child combinations)
- [x] Test nested container queries
- [ ] Performance benchmarks (deferred to Phase 9)

---

## Phase 8: Examples Documentation (v1.0-rc)

### 4. Examples

**Status**: Deferred from v0.9.0 (Phase 4, Item 10)

**Goal**: Provide examples for learning gsvg.

#### 4.1 gsvg-examples Module

**Architecture**: Self contained groovy scripts using Grab for dependencies
- [ ] Create `gsvg-examples/` submodule
- [ ] Add well documented examples showing main features

#### 4.3 Example Gallery

**Focus**: Showcase the Svg model API - various ways of navigating and modifying SVG content programmatically

**Features**:
- [ ] Categorized example library demonstrating Svg model usage patterns

**Example Categories**:
1. Navigation and Selection (10 examples)
   - Finding elements by type, ID, class
   - XPath queries
   - CSS selectors (when available)
   - Parent/child traversal

2. Content Modification (10 examples)
   - Adding elements dynamically
   - Removing elements
   - Moving/reparenting elements
   - Replacing elements

3. Attribute Manipulation (8 examples)
   - Getting and setting attributes
   - Bulk attribute updates
   - Conditional attribute changes
   - Style manipulation

4. Element Cloning and Templates (6 examples)
   - Cloning elements
   - Creating templates
   - Batch element creation
   - Pattern replication

5. Tree Transformation (8 examples)
   - Restructuring element hierarchies
   - Flattening groups
   - Creating groups from selections
   - Optimizing element trees

6. Builder Pattern Usage (8 examples)
   - Fluent API chains
   - DSL-style construction
   - Factory methods
   - Programmatic SVG generation

7. Reading and Parsing (6 examples)
   - Loading SVG from files/strings
   - Querying existing SVG structure
   - Extracting information
   - Validating content

8. Iteration and Filtering (6 examples)
   - Iterating over elements
   - Filtering by criteria
   - Mapping transformations
   - Collecting results

9. Advanced Manipulation (8 examples)
   - Conditional modifications
   - Batch operations
   - Complex transformations
   - Combining navigation and modification

10. Practical Use Cases (6 examples)
    - Dynamic chart generation
    - SVG templating systems
    - Automated SVG modifications
    - Content injection and updates

---

## Phase 9: Performance Validation (v1.0-rc)

### 5. Performance Benchmarking and Optimization

**Goal**: Validate and optimize performance for v1.0 release.

#### 5.1 Benchmark Updates

**Task**: Re-run benchmarks and compare with `doc/benchmarks.md` baseline
- [ ] Run full JMH benchmark suite
- [ ] Compare with v0.8.0 baseline in doc/benchmarks.md
- [ ] Identify any performance regressions
- [ ] Analyze impact of Phase 4 features:
  - Numeric precision control overhead
  - Accessibility helper methods
  - Template/DSL overhead
  - New factory methods

#### 5.2 Performance Analysis

**Metrics to Track**:
- [ ] Parsing performance (small/medium/large/complex SVGs)
- [ ] Serialization performance (toXml, toXmlPretty)
- [ ] Creation performance (fluent API, DSL)
- [ ] Selection performance (type, XPath, CSS selectors)
- [ ] Cloning performance
- [ ] Memory usage (heap, GC pressure)

**Expected Results**:
- Parsing: < 5% regression acceptable (precision formatting overhead)
- Creation: No significant regression (new methods are shortcuts)
- Selection: CSS selectors should be faster than XPath for simple cases
- Memory: No increase in baseline memory usage

#### 5.3 Optimization Opportunities

**If regressions found**:
- [ ] Profile hot paths with JProfiler/YourKit
- [ ] Optimize NumberFormatter if bottleneck
- [ ] Cache compiled CSS selectors
- [ ] Lazy initialization for rarely-used features
- [ ] Review object allocation patterns

#### 5.4 Documentation
- [ ] Update `doc/benchmarks.md` with v1.0 results
- [ ] Add performance comparison table (v0.8 vs v0.9 vs v1.0)
- [ ] Document any breaking changes or optimization tips
- [ ] Update `doc/performance.md` with new best practices

---

## Phase 10: Final Polish (v1.0)

### 6. Production Readiness

**Goal**: Ensure gsvg v1.0 is production-ready for enterprise adoption.

#### 6.1 Code Quality
- [ ] Run static analysis (CodeNarc, SpotBugs)
- [ ] Fix all critical and high-priority issues
- [ ] Review and improve Groovydoc coverage
- [ ] Ensure all public APIs have comprehensive documentation
- [ ] Add package-level documentation

#### 6.2 Testing
- [ ] Verify all tests pass (target: 750+ tests)
- [ ] Achieve >85% code coverage
- [ ] Add integration tests for multi-module scenarios
- [ ] Test with different Groovy versions (4.0, 5.0)
- [ ] Test with different Java versions (17, 21, 23)

#### 6.3 Documentation Audit
- [ ] Review all documentation for accuracy
- [ ] Update all code examples to use v1.0 APIs
- [ ] Add migration guide from v0.9 to v1.0
- [ ] Update README with new features
- [ ] Create CHANGELOG.md with detailed release notes

#### 6.4 Security Audit
- [ ] Review all dependencies for CVEs
- [ ] Update dependencies to latest stable versions
- [ ] Verify XXE/XSS protection still active
- [ ] Review input validation in export module
- [ ] Add security.md with reporting guidelines

#### 6.5 Compatibility Testing
- [ ] Test backward compatibility with v0.9 code
- [ ] Verify Maven Central release process
- [ ] Test Gradle and Maven integration
- [ ] Test Groovy Grape (@Grab) functionality
- [ ] Test with popular Groovy frameworks (Grails, Ratpack)

#### 6.6 Release Preparation
- [ ] Update version numbers to 1.0.0
- [ ] Create release branch: `release/1.0`
- [ ] Tag release: `v1.0.0`
- [ ] Build and sign artifacts
- [ ] Deploy to Maven Central
- [ ] Create GitHub release with release notes
- [ ] Update documentation website
- [ ] Announce on Groovy mailing list, Twitter, Reddit

---

## Success Criteria for v1.0

### Functional
- ✅ Multi-module architecture working smoothly
- ✅ gsvg-export module provides rendering and optimization
- ✅ CSS selector support working alongside XPath
- ✅ Interactive playground deployed and accessible
- ✅ All deferred features from v0.9 implemented

### Quality
- ✅ 750+ tests passing (650 core + 100 export/examples)
- ✅ >85% code coverage across all modules
- ✅ Zero critical/high security vulnerabilities
- ✅ All documentation accurate and complete
- ✅ Performance within 10% of v0.8 baseline

### Compatibility
- ✅ 100% backward compatible with v0.9 API
- ✅ Works with Groovy 4.0+ and Java 17+
- ✅ Maven Central release successful
- ✅ Easy integration with Gradle and Maven

### User Experience
- ✅ Documentation is comprehensive and easy to navigate
- ✅ Export module is easy to use and well-documented
- ✅ Migration from v0.9 is straightforward

---

## Timeline Estimate

**Phase 5: Multi-Module Architecture**
- Duration: 1-2 weeks
- Complexity: Medium
- Risk: Low (mostly organizational)

**Phase 6: Export Module**
- Duration: 3-4 weeks
- Complexity: High
- Risk: Medium (jsvg integration, testing)

**Phase 7: Enhanced Selection**
- Duration: 1-2 weeks
- Complexity: Medium
- Risk: Low (leveraging existing ph-css)

**Phase 8: Examples Documentation**
- Duration: 2-3 weeks
- Complexity: Medium
- Risk: Low (frontend work)

**Phase 9: Performance Validation**
- Duration: 1 week
- Complexity: Low
- Risk: Low (benchmarking and analysis)

**Phase 10: Final Polish**
- Duration: 1-2 weeks
- Complexity: Low
- Risk: Low (cleanup and release prep)

**Total Estimated Time**: 9-14 weeks (2-3.5 months)

---

## Post-1.0 Considerations

### Future Enhancements (v1.1+)
- Advanced animation support (SMIL builder API)
- SVG sprite sheet generation
- Automatic icon generation (multiple sizes)
- Integration with popular charting libraries
- Kotlin DSL support
- GraalVM native image support

### Community and Ecosystem
- Encourage community contributions
- Create plugin system for custom extensions
- Build integrations with popular frameworks
- Develop official examples repository
- Regular maintenance releases (quarterly)

---

## Notes

**Backward Compatibility**:
- v1.0 must maintain 100% backward compatibility with v0.9
- All existing APIs continue to work
- New features are purely additive
- Deprecation warnings for any planned changes in v2.0

**Dependency Management**:
- Keep core module dependencies minimal
- Only add dependencies to export/examples modules
- Regular dependency updates for security
- Pin dependency versions for stability

**Module Independence**:
- gsvg-core: No dependency on export or examples
- gsvg-export: Depends only on gsvg-core
- gsvg-examples: Depends on gsvg-core and gsvg-export

**Testing Strategy**:
- Each module has its own test suite
- Integration tests across modules
- Performance regression tests
- Cross-version compatibility tests

---

## Deferred Items (Post-v1.0)

These items are out of scope for v1.0 but may be considered for future releases:

### Advanced Features
- ❌ Built-in rendering engine in core (use export module)
- ❌ Animation timeline/sequencer (SMIL is sufficient)
- ❌ Heavy CSS framework integration
- ❌ Interactive SVG editor (desktop app)
- ❌ Real-time collaboration features

### Performance
- ⏸️ Lazy loading for large SVGs
- ⏸️ Streaming parser for huge files
- ⏸️ Parallel processing for batch operations

### Advanced Export
- ⏸️ PDF export (requires Apache PDFBox or iText)
- ⏸️ SVG to Canvas conversion
- ⏸️ SVG to WebP/AVIF export

### Developer Experience
- ⏸️ IntelliJ IDEA plugin
- ⏸️ VS Code extension
- ⏸️ Live reload during development
- ⏸️ Visual diff tool for SVG changes

---

## Feedback and Contributions

This roadmap is open for discussion and community input. Please provide feedback through:
- GitHub Issues: Feature requests and suggestions
- GitHub Discussions: Architecture and design discussions
- Pull Requests: Code contributions

**Review Schedule**: This roadmap will be reviewed quarterly and updated based on:
- Community feedback
- Technology evolution
- Dependency updates
- Real-world usage patterns
