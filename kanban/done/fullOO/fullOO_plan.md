# Plan: Fully Object-Oriented SVG Manipulation (Zero Serialization)

## Goal
Eliminate all XML serialization/parsing from SVG manipulation operations (merge, copy, clone) by implementing a pure object-oriented approach that maintains both DOM4J tree and children list structures.

## Current Architecture Analysis

### The Dual Structure Problem
The API currently maintains two parallel structures:
1. **DOM4J Element tree** - For XML serialization/deserialization
2. **SvgElement children list** - For object-oriented navigation

**Problem**: When cloning DOM4J elements, only structure #1 is updated. The children lists remain unpopulated, breaking the object model.

**Current Hybrid Solution**: Build using object model + DOM cloning, then parse once at the end to populate children lists.

## Proposed Solution Architecture

### Phase 1: Foundation - Adopting Constructors (2-3 days)

#### 1.1 Add Constructors to All Element Classes
**Objective**: Enable all 113 SvgElement subclasses to adopt existing DOM4J Elements.

**Implementation**:
- Add `ClassName(SvgElement parent, Element element)` constructor to all concrete classes
- Constructor should:
  - Call `super(parent, element)`
  - Not create new DOM4J elements
  - Adopt the cloned element passed in

**Files to modify**: All 113 classes in `src/main/groovy/se/alipsa/groovy/svg/`

**Automated approach**:
```groovy
// Groovy script to generate constructors
def generateConstructor(String className) {
    """
    /**
     * Creates a ${className} by adopting an existing DOM4J Element.
     * Used for cloning/copying operations.
     *
     * @param parent the parent SVG element
     * @param element the DOM4J element to adopt
     */
    ${className}(SvgElement parent, Element element) {
        super(parent, element)
    }
    """
}
```

**Classes requiring special handling**:
- `Svg` - Already has special constructor, may need variant
- Classes with multiple constructors - Ensure all call patterns work
- Classes with initialization logic - Verify adoption doesn't break initialization

**Validation**:
- Compile all classes successfully
- Existing tests continue to pass
- Constructor is accessible from factory

---

### Phase 2: Factory Implementation (3-4 days)

#### 2.1 Complete SvgElementFactory

**Objective**: Create a comprehensive factory that can instantiate any SvgElement subclass from a DOM4J Element.

**Implementation**:

```groovy
@CompileStatic
class SvgElementFactory {

    /**
     * Creates appropriate SvgElement from DOM4J Element with full recursion.
     * This populates both DOM tree AND children lists.
     *
     * @param parent the parent container
     * @param element the DOM4J element to convert
     * @return the created SvgElement
     */
    static SvgElement fromElement(AbstractElementContainer parent, Element element) {
        String name = element.getName()
        SvgElement result = createElement(parent, element, name)

        if (result == null) {
            // Handle unknown/custom elements
            result = createGenericElement(parent, element)
        }

        // Add to parent's children list
        parent.add(result)

        // Recursively process children if this is a container
        if (result instanceof AbstractElementContainer) {
            AbstractElementContainer container = result as AbstractElementContainer
            element.elements().each { Element childElement ->
                fromElement(container, childElement)
            }
        }

        return result
    }

    /**
     * Creates element without recursion (for internal use).
     */
    private static SvgElement createElement(SvgElement parent, Element element, String name) {
        // Switch statement covering all 113+ element types
        switch (name) {
            case Circle.NAME: return new Circle(parent, element)
            case Rect.NAME: return new Rect(parent, element)
            case G.NAME: return new G(parent, element)
            // ... all other types
            default: return null
        }
    }

    /**
     * Deep copy an element with full child hierarchy.
     * Pure object-oriented - no serialization.
     *
     * @param source the element to copy
     * @param newParent the parent for the copy
     * @return the copied element
     */
    static <T extends SvgElement> T deepCopy(T source, AbstractElementContainer newParent) {
        // Clone the DOM4J element
        Element clonedElement = source.element.createCopy()

        // Create appropriate SvgElement wrapper with recursion
        SvgElement result = fromElement(newParent, clonedElement)

        return result as T
    }

    /**
     * Copy all children from source to target (pure OO).
     */
    static void copyChildren(AbstractElementContainer source, AbstractElementContainer target) {
        for (SvgElement child : source.getChildren()) {
            deepCopy(child, target)
        }
    }
}
```

**Key Design Decisions**:
- Use `fromElement()` for both parsing AND copying
- Recursion handles entire tree traversal
- Both DOM tree and children lists populated in one pass
- Type-safe return via generics

**Testing**:
- Unit tests for each element type creation
- Test deep hierarchies (nested groups, complex filters)
- Test with all element types
- Benchmark against serialization approach

---

### Phase 3: Cloneable Implementation (2-3 days)

#### 3.1 Implement Cloneable Interface

**Objective**: Make SvgElement cloning more idiomatic and Groovy-standard.

**Implementation**:

```groovy
@CompileStatic
abstract class SvgElement<T extends SvgElement<T>>
    implements ElementContainer, Cloneable {

    /**
     * Creates a deep clone of this element.
     * Clones both DOM4J structure and SvgElement hierarchy.
     *
     * @param newParent the parent for the cloned element
     * @return deep clone of this element
     */
    T clone(SvgElement newParent) {
        return SvgElementFactory.deepCopy(this, newParent as AbstractElementContainer) as T
    }

    /**
     * Standard clone() for Cloneable interface.
     * Note: Returns shallow clone - use clone(parent) for deep clone.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // Shallow clone - just the wrapper object
        return super.clone()
    }
}
```

**Usage Example**:
```groovy
// Old way (serialization)
String xml = circle.toXml()
Circle copy = SvgReader.parse("<svg>${xml}</svg>").children[0]

// New way (pure OO)
Circle copy = circle.clone(newParent)
```

**Benefits**:
- More idiomatic Groovy
- Type-safe
- Clear intent
- Better IDE support

---

### Phase 4: Update SvgMerger to Pure OO (1 day)

#### 4.1 Remove All Serialization

**Current Hybrid**:
```groovy
Svg result = new Svg(totalWidth, maxHeight)
G group = result.addG()
SvgElementFactory.copyChildren(sourceSvg, group)
// Still needs parse to populate children
return reader.parse(new InputSource(new StringReader(result.toXml())))
```

**Pure OO**:
```groovy
Svg result = new Svg(totalWidth, maxHeight)
G group = result.addG()
group.transform("translate(${currentX}, 0)")
// Pure OO - no parse needed!
SvgElementFactory.copyChildren(sourceSvg, group)
return result  // Children lists already populated
```

**Impact**:
- ~50% faster (no XML serialization)
- Lower memory usage (no intermediate strings)
- Cleaner code
- Same API surface

---

### Phase 5: Advanced Features (Optional) (3-5 days)

#### 5.1 Lazy Children List Population

**Problem**: Current approach populates children lists eagerly during parsing.

**Enhancement**: Make children list population lazy for memory efficiency:

```groovy
trait ElementContainer {
    private List<SvgElement> children = null
    private boolean childrenInitialized = false

    List<SvgElement> getChildren() {
        if (!childrenInitialized) {
            children = buildChildrenFromDOM()
            childrenInitialized = true
        }
        return children
    }

    private List<SvgElement> buildChildrenFromDOM() {
        // Build children list from DOM4J tree
        element.elements().collect { Element e ->
            SvgElementFactory.fromElement(this, e)
        }
    }
}
```

**Benefits**:
- Memory efficient for large documents
- Fast initial parsing
- Children loaded on-demand

**Challenges**:
- More complex state management
- Need to handle mutations correctly

#### 5.2 Copy-on-Write for Element Attributes

**Enhancement**: Share DOM elements until mutation:

```groovy
class SvgElement {
    Element element
    boolean isShared = false

    protected void ensureUnshared() {
        if (isShared) {
            element = element.createCopy()
            isShared = false
        }
    }

    T addAttribute(String name, Object value) {
        ensureUnshared()
        element.addAttribute(name, "$value")
        this as T
    }
}
```

**Benefits**:
- Memory efficient when copying large trees
- Fast read-only operations
- Mutations only clone when needed

#### 5.3 Element Pool/Flyweight Pattern

**Enhancement**: Reuse immutable element instances:

```groovy
class SvgElementPool {
    private Map<String, SvgElement> pool = [:]

    SvgElement getOrCreate(String elementXml) {
        pool.computeIfAbsent(elementXml) {
            // Create element
        }
    }
}
```

**Use Case**: When merging many SVGs with identical elements (icons, symbols).

---

## Implementation Strategy

We will use an **Incremental** approach to minimize risk while delivering value at each stage.

### Sprint 1: Foundation (Week 1)
**Goal**: Establish the foundation with base classes and most common elements.

**Tasks**:
1. Add adopting constructors to base classes:
   - `AbstractShape`
   - `AbstractElementContainer`
   - `SvgElement`
2. Add adopting constructors to 20 most common element classes:
   - Circle, Rect, Path, Line, Ellipse, Polygon, Polyline
   - G, Defs, Use, Symbol
   - Text, Tspan
   - LinearGradient, RadialGradient, Stop
   - Image, A
   - ClipPath, Mask
3. Implement basic `SvgElementFactory` supporting these 20 classes
4. Create unit tests for constructor adoption
5. Verify all existing tests still pass

**Deliverable**: Foundation for OO approach with most commonly used elements working.

---

### Sprint 2: Expand Coverage (Week 2)
**Goal**: Complete constructor coverage for all element types.

**Tasks**:
1. Add adopting constructors to remaining ~93 element classes:
   - All filter elements (FeBlend, FeColorMatrix, etc.)
   - All animation elements (Animate, AnimateMotion, etc.)
   - All text elements (TextPath, Tref)
   - All specialty elements (Switch, ForeignObject, etc.)
2. Expand `SvgElementFactory.createElement()` switch to cover all types
3. Add comprehensive test coverage:
   - Test each element type can be adopted
   - Test with complex nested structures
   - Test with all attribute types
4. Run full test suite and fix any issues
5. Document constructor pattern in code comments

**Deliverable**: Complete factory implementation supporting all SVG element types.

---

### Sprint 3: Deep Copy & SvgMerger (Week 3)
**Goal**: Implement pure OO deep copy and update SvgMerger.

**Tasks**:
1. Implement `SvgElementFactory.deepCopy()`:
   - Clone DOM4J element
   - Create SvgElement wrapper via factory
   - Recursively copy children
   - Populate children lists
2. Implement `SvgElementFactory.fromElement()` with full recursion
3. Update `SvgMerger` to use pure OO approach:
   - Remove all XML serialization
   - Use `SvgElementFactory.copyChildren()`
   - No parse step at the end
4. Performance benchmarking:
   - Compare old vs new merge performance
   - Memory usage profiling
   - Identify and optimize bottlenecks
5. Comprehensive integration testing

**Deliverable**: Pure OO SvgMerger with no serialization, performance metrics.

---

### Sprint 4: Polish & Advanced Features (Week 4)
**Goal**: Add polish, optional enhancements, and documentation.

**Tasks**:
1. Implement `Cloneable` interface on `SvgElement`:
   - Add `clone(newParent)` method
   - Update documentation
   - Add usage examples
2. Consider optional advanced features based on benchmarks:
   - Lazy children list population (if memory is concern)
   - Copy-on-write (if copying is frequently used)
3. Complete documentation:
   - API docs for new constructors
   - Create a merging.md in the doc folder describing the 3 different kinds of mergers of svg documents we can do. Update the overview.md with a link to the merging.md and a brief description in the "What you can do" section
   - Performance comparison charts
4. Create examples demonstrating new capabilities
5. Final testing:
   - Full regression test suite
   - Performance validation
   - Memory leak testing

**Deliverable**: Production-ready pure OO implementation with documentation.

---

### Timeline Summary

| Sprint | Focus       | Duration | Key Deliverable                   |
|--------|-------------|----------|-----------------------------------|
| 1      | Foundation  | Week 1   | Base classes + 20 common elements |
| 2      | Coverage    | Week 2   | All 113 elements supported        |
| 3      | Integration | Week 3   | Pure OO SvgMerger, benchmarks     |
| 4      | Polish      | Week 4   | Cloneable, docs, examples         |

**Total Time**: 4 weeks full-time or 8 weeks part-time

---

### Success Criteria

Each sprint must meet these criteria before proceeding:

**Sprint 1**:
- ✅ All existing tests pass
- ✅ 20 element types have adopting constructors
- ✅ Basic factory works for those 20 types
- ✅ No performance regression

**Sprint 2**:
- ✅ All 113 element types have adopting constructors
- ✅ Factory handles all types
- ✅ Full test coverage for factory
- ✅ All 215+ tests pass

**Sprint 3**:
- ✅ SvgMerger uses zero serialization
- ✅ 30%+ performance improvement
- ✅ 20%+ memory reduction
- ✅ All tests pass

**Sprint 4**:
- ✅ Cloneable interface implemented
- ✅ Complete documentation
- ✅ No known bugs
- ✅ Ready for release

---

## Automated Implementation Tools

### Script 1: Constructor Generator

```groovy
#!/usr/bin/env groovy
// generateConstructors.groovy

import java.nio.file.*

def baseDir = Paths.get("src/main/groovy/se/alipsa/groovy/svg")

// Find all classes
Files.walk(baseDir)
    .filter { it.toString().endsWith(".groovy") }
    .each { file ->
        def content = file.text
        def className = file.fileName.toString() - ".groovy"

        // Skip if already has adopting constructor
        if (content.contains("${className}(SvgElement parent, Element element)")) {
            println "Skipping $className (already has constructor)"
            return
        }

        // Find where to insert constructor
        def lines = content.readLines()
        def insertLine = findConstructorInsertionPoint(lines, className)

        if (insertLine < 0) {
            println "WARNING: Could not find insertion point for $className"
            return
        }

        // Generate and insert constructor
        def constructor = generateConstructor(className)
        lines.add(insertLine, constructor)

        file.text = lines.join('\n')
        println "Added constructor to $className"
    }

String generateConstructor(String className) {
    """
    /**
     * Creates a ${className} by adopting an existing DOM4J Element.
     * Used for cloning/copying operations.
     *
     * @param parent the parent SVG element
     * @param element the DOM4J element to adopt
     */
    ${className}(SvgElement parent, Element element) {
        super(parent, element)
    }
"""
}

int findConstructorInsertionPoint(List<String> lines, String className) {
    // Find last constructor
    for (int i = lines.size() - 1; i >= 0; i--) {
        if (lines[i].contains("${className}(")) {
            // Find its closing brace
            for (int j = i; j < lines.size(); j++) {
                if (lines[j].trim() == "}") {
                    return j + 1
                }
            }
        }
    }
    return -1
}
```

### Script 2: Factory Case Generator

```groovy
#!/usr/bin/env groovy
// generateFactoryCases.groovy

import java.nio.file.*

def baseDir = Paths.get("src/main/groovy/se/alipsa/groovy/svg")
def cases = []

Files.walk(baseDir)
    .filter { it.toString().endsWith(".groovy") }
    .filter { !it.toString().contains("Abstract") }
    .filter { !it.toString().contains("Interface") }
    .each { file ->
        def className = file.fileName.toString() - ".groovy"
        def content = file.text

        // Extract NAME constant
        def nameMatcher = content =~ /static final String NAME\s*=\s*'([^']+)'/
        if (nameMatcher.find()) {
            def elementName = nameMatcher.group(1)
            cases << "case ${className}.NAME: return new ${className}(parent, element)"
        }
    }

// Sort and output
println "// Generated factory cases:"
cases.sort().each { println "            $it" }
```

---

## Testing Strategy

### Unit Tests
- **Constructor Tests**: Verify each element type can adopt a DOM element
- **Factory Tests**: Test createElement for all 113 types
- **Deep Copy Tests**: Test copying complex hierarchies
- **Children List Tests**: Verify lists are properly populated

### Integration Tests
- **Merge Tests**: Verify merges work with pure OO approach
- **Round-trip Tests**: Create → Copy → Compare
- **Performance Tests**: Benchmark vs serialization approach

### Performance Benchmarks

```groovy
@Benchmark
void testSerializationApproach() {
    Svg result = buildSvg()
    SvgReader reader = new SvgReader()
    Svg parsed = reader.parse(new InputSource(new StringReader(result.toXml())))
}

@Benchmark
void testPureOOApproach() {
    Svg result = buildSvg()
    // No parsing - result already has populated children
}
```

**Expected Improvements**:
- 40-60% faster merge operations
- 30-50% less memory usage
- Scales better with document size

---

## Migration Path

### For Library Users

**Version N (Current)**:
- Hybrid approach (no breaking changes)
- Performance improvements automatically

**Version N+1**:
- Pure OO as default
- Hybrid available via flag if needed
- Deprecation warnings

**Version N+2**:
- Pure OO only
- Remove serialization code paths
- Clean up API

### For Library Maintainers

1. **Week 1-2**: Implement foundation
2. **Week 3-4**: Complete implementation
3. **Week 5**: Testing and benchmarking
4. **Week 6**: Documentation and release

---

## Risks and Mitigations

### Risk 1: Constructor Complexity
**Risk**: Some classes have complex initialization that breaks with adoption.

**Mitigation**:
- Test each class individually
- Special handling for complex classes
- Fall back to parse for problematic types

### Risk 2: Performance Regression
**Risk**: Pure OO might be slower than expected.

**Mitigation**:
- Benchmark early
- Keep hybrid approach as fallback
- Profile and optimize hot paths

### Risk 3: Memory Leaks
**Risk**: Improper parent/child references could cause leaks.

**Mitigation**:
- Memory profiling with large documents
- Weak references where appropriate
- Thorough leak testing

### Risk 4: Incomplete Coverage
**Risk**: Some edge-case elements don't work.

**Mitigation**:
- Comprehensive test matrix
- Real-world SVG test suite
- User feedback in beta

---

## Success Metrics

### Functional
- ✅ All 215+ tests pass
- ✅ Zero serialization in merge operations
- ✅ Children lists properly populated
- ✅ Backward compatible API

### Performance
- ✅ 40%+ faster merge operations
- ✅ 30%+ less memory usage
- ✅ Scales linearly with document size

### Code Quality
- ✅ No code duplication
- ✅ Clear separation of concerns
- ✅ Well-documented API
- ✅ Maintainable implementation

---

## Future Enhancements

### 1. Stream Processing
Process large SVG files without loading entire DOM:
```groovy
SvgStream.merge("file1.svg", "file2.svg")
    .horizontal()
    .writeTo("merged.svg")
```

### 2. Parallel Processing
Merge multiple SVGs in parallel:
```groovy
SvgMerger.mergeHorizontally(svgs)
    .parallel()
    .build()
```

### 3. Diff/Patch Support
```groovy
SvgDiff diff = SvgDiff.compare(svg1, svg2)
Svg patched = diff.applyTo(svg1)
```

### 4. Functional API
```groovy
Svg merged = svgs
    .map { transformIt() }
    .reduce { a, b -> SvgMerger.mergeHorizontally(a, b) }
```

---

## Conclusion

This plan provides a clear path from the current hybrid approach to a fully object-oriented implementation. The **incremental sprint-based strategy** minimizes risk while delivering value at each stage. The automated tools make implementation tractable despite the large number of classes.

### Next Steps

1. **Review and Approval**
   - Review this plan with the team
   - Approve sprint structure and timeline
   - Allocate resources

2. **Sprint 1 Kickoff**
   - Create feature branch `feature/pure-oo-implementation`
   - Set up automated constructor generation script
   - Begin implementation of base classes

3. **Continuous Integration**
   - Run full test suite after each sprint
   - Benchmark performance at Sprint 3
   - Document learnings and adjust as needed

4. **Release Planning**
   - After Sprint 4 completion
   - Beta release for community feedback
   - Production release after validation

### Project Summary

| Aspect               | Details                                                      |
|----------------------|--------------------------------------------------------------|
| **Estimated Effort** | 4 weeks full-time or 8 weeks part-time                       |
| **Risk Level**       | Low-Medium (mitigated by incremental approach)               |
| **Value**            | High (40%+ performance, better maintainability, cleaner API) |
| **Breaking Changes** | None (fully backward compatible)                             |
| **Dependencies**     | DOM4J (existing), no new dependencies                        |

### Key Success Factors

✅ **Clear sprint goals** - Each sprint has measurable deliverables
✅ **Automated tooling** - Scripts reduce manual work
✅ **Comprehensive testing** - All 215+ tests must pass each sprint
✅ **Performance validation** - Benchmarks ensure improvements
✅ **Incremental delivery** - Value delivered progressively
