# gsvg Performance Guide

This guide covers performance optimization techniques for gsvg, including file size reduction, runtime performance, and memory optimization.

## Table of Contents

- [File Size Optimization](#file-size-optimization)
- [Runtime Performance](#runtime-performance)
- [Memory Optimization](#memory-optimization)
- [Benchmarking](#benchmarking)
- [Best Practices Summary](#best-practices-summary)

## File Size Optimization

### Numeric Precision Control

**Impact**: 30-50% file size reduction for coordinate-heavy graphics

```groovy
import se.alipsa.groovy.svg.utils.NumberFormatter

// Set global precision (default: 3 decimals)
NumberFormatter.setDefaultPrecision(2)

Svg svg = new Svg(400, 400)

// Or per-document
svg.setMaxPrecision(2)

svg.addCircle()
   .cx(100.123456)  // Outputs: cx="100.12"
   .cy(200.987654)  // Outputs: cy="200.99"
   .r(50.5)         // Outputs: r="50.5"

// Reset to default
NumberFormatter.resetPrecision()
```

**Guidelines**:
- Default (3 decimals) is suitable for most graphics
- Use 2 decimals for simple shapes and icons
- Use 4-5 decimals only for precise technical drawings
- 0.001 precision difference is imperceptible to human eye

### Reuse Definitions

**Impact**: Significant reduction when repeating elements

**Bad** (Repeating elements):
```groovy
// Each circle adds ~50 bytes
for (int i = 0; i < 100; i++) {
    svg.addCircle().cx(i*10).cy(100).r(5).fill('blue')
}
// Total: ~5,000 bytes
```

**Good** (Using `<defs>` and `<use>`):
```groovy
Defs defs = svg.addDefs()
Circle template = defs.addCircle()
template.id('dot').r(5).fill('blue')

for (int i = 0; i < 100; i++) {
    svg.addUse('#dot').x(i*10).y(100)
}
// Total: ~1,500 bytes (70% reduction)
```

### Group Common Attributes

**Impact**: Reduces repetition of fill, stroke, etc.

**Bad**:
```groovy
svg.addCircle().cx(50).cy(50).r(20).fill('red').stroke('black').strokeWidth(2)
svg.addCircle().cx(100).cy(50).r(20).fill('red').stroke('black').strokeWidth(2)
svg.addCircle().cx(150).cy(50).r(20).fill('red').stroke('black').strokeWidth(2)
```

**Good**:
```groovy
G group = svg.addG()
group.fill('red').stroke('black').strokeWidth(2)
group.addCircle().cx(50).cy(50).r(20)
group.addCircle().cx(100).cy(50).r(20)
group.addCircle().cx(150).cy(50).r(20)
```

### Optimize Path Data

**Impact**: Smaller path strings

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

// Use relative commands when appropriate
PathBuilder path = PathBuilder.moveTo(100, 100)
    .l(50, 0)   // Relative lineTo (lowercase)
    .l(0, 50)
    .l(-50, 0)
    .Z()

// Simpler than absolute commands
// M 100 100 L 150 100 L 150 150 L 100 150 Z
// becomes: M 100 100 l 50 0 l 0 50 l -50 0 Z
```

### Remove Unnecessary Attributes

```groovy
// Don't set default values
svg.addRect()
   .x(10).y(10)
   .width(100).height(50)
   .fill('blue')
   // .stroke('none')      // Don't set - 'none' is default
   // .fillOpacity(1.0)    // Don't set - 1.0 is default
```

### Use CSS for Repeated Styles

**Impact**: Reduces inline style repetition

```groovy
Defs defs = svg.addDefs()
Style style = defs.addStyle()
style.type('text/css')
style.addCData('''
    .primary-button {
        fill: #4CAF50;
        stroke: darkgreen;
        stroke-width: 2;
        cursor: pointer;
    }
    .icon {
        fill: none;
        stroke: black;
        stroke-width: 2;
    }
''')

// Use classes instead of inline styles
svg.addRect().x(10).y(10).width(100).height(40).addAttribute('class', 'primary-button')
svg.addCircle().cx(50).cy(50).r(20).addAttribute('class', 'icon')
```

## Runtime Performance

### Lazy Validation

**Impact**: Avoid validation overhead during construction

```groovy
import se.alipsa.groovy.svg.validation.ValidationEngine

// Don't validate during construction (good)
Svg svg = new Svg(800, 600)
for (int i = 0; i < 1000; i++) {
    svg.addCircle().cx(Math.random() * 800).cy(Math.random() * 600).r(5)
}

// Validate once at the end if needed
ValidationEngine engine = ValidationEngine.createDefault()
def report = engine.validate(svg)
```

### Batch Operations

**Impact**: Reduce method call overhead

**Slower** (Individual method calls):
```groovy
for (int i = 0; i < 1000; i++) {
    Circle c = svg.addCircle()
    c.cx(i)
    c.cy(100)
    c.r(5)
    c.fill('blue')
}
```

**Faster** (Method chaining):
```groovy
for (int i = 0; i < 1000; i++) {
    svg.addCircle()
       .cx(i).cy(100).r(5).fill('blue')
}
```

**Fastest** (Map-based):
```groovy
for (int i = 0; i < 1000; i++) {
    svg.addCircle([cx: i, cy: 100, r: 5, fill: 'blue'])
}
```

### Avoid Repeated Queries

**Impact**: Query operations have O(N) complexity

**Bad**:
```groovy
for (int i = 0; i < 100; i++) {
    // This searches all elements each time
    def circles = svg[Circle]
    circles.each { it.fill('red') }
}
```

**Good**:
```groovy
// Query once, reuse result
def circles = svg[Circle]
for (int i = 0; i < 100; i++) {
    circles.each { it.fill('red') }
}
```

### Use Specific Selectors

```groovy
// Slower (searches all descendants recursively)
def allCircles = svg.descendants(Circle)

// Faster (only immediate children)
def directCircles = svg[Circle]

// Fastest (direct reference if you have it)
Circle myCircle = svg.addCircle()
// Use myCircle directly instead of searching
```

### Efficient PathBuilder Usage

```groovy
import se.alipsa.groovy.svg.utils.PathBuilder

// Build path once
PathBuilder builder = PathBuilder.moveTo(0, 0)
for (int i = 1; i <= 100; i++) {
    builder.L(i * 10, Math.sin(i * 0.1) * 50)
}

// Add to SVG once
svg.addPath().d(builder).stroke('blue').fill('none')

// Don't rebuild path in a loop
```

## Memory Optimization

### Avoid Memory Leaks

```groovy
// Don't hold references to removed elements
Circle c = svg.addCircle()
svg.removeChild(c)
c = null  // Allow garbage collection

// Don't accumulate in closures
def elements = []
1000.times {
    def elem = svg.addCircle()
    // elements.add(elem)  // Avoid if not needed
}
```

### Clone Efficiently

```groovy
// Deep clone when needed
Svg original = new Svg(400, 400)
// ... populate ...

Svg copy = original.clone()  // Creates deep copy

// Shallow operations when appropriate
G group = svg.addG()
// Just reference existing element, don't clone
```

### Stream Large Outputs

```groovy
// For very large SVGs, write incrementally
// (Note: gsvg builds in memory, consider streaming for huge files)

Svg svg = new Svg(10000, 10000)

// If memory is constrained, process in batches
(0..100).each { batch ->
    Svg section = new Svg(100, 100)
    // Populate section...

    // Write section
    new File("section-${batch}.svg").text = section.toString()
    section = null  // Release memory
}
```

### Optimize String Operations

```groovy
// Efficient toString usage
Svg svg = new Svg(400, 400)
// ... build SVG ...

// Call toString() once
String xml = svg.toString()
new File('output.svg').text = xml

// Don't repeatedly call toString() in loops
// Bad:
for (int i = 0; i < 100; i++) {
    String xml = svg.toString()  // Inefficient
    // process xml...
}

// Good:
String xml = svg.toString()
for (int i = 0; i < 100; i++) {
    // process xml...
}
```

## Benchmarking

### Using JMH Benchmarks

gsvg includes JMH benchmarks. Run them:

```bash
# Run all benchmarks
mvn clean test-compile exec:exec -Pbenchmark

# Run specific benchmark
mvn exec:exec -Pbenchmark -Dbenchmark.class=CreationBenchmark

# Quick mode (fewer iterations)
mvn exec:exec -Pbenchmark -Dbenchmark.mode=quick
```

### Benchmark Categories

**1. Parsing Benchmarks**
- Simple SVG (10 elements)
- Medium SVG (100 elements)
- Large SVG (1000 elements)
- Complex SVG (nested structures)

**2. Creation Benchmarks**
- Element creation overhead
- Fluent API performance
- Map-based construction

**3. Serialization Benchmarks**
- `toString()` performance
- Pretty printing overhead

**4. Utility Benchmarks**
- Color parsing and manipulation
- PathBuilder operations
- ViewBox calculations
- NumberFormatter overhead

**5. Selection Benchmarks**
- Type-based selection
- XPath queries
- Descendant searches

### Interpreting Results

Typical performance (on modern hardware):

```
Benchmark                                    Mode  Cnt    Score   Error  Units
CreationBenchmark.createSimpleSvg           thrpt   20  50000.0  ±1000  ops/s
CreationBenchmark.createComplexSvg          thrpt   20   5000.0   ±100  ops/s
SerializationBenchmark.toStringSmall        thrpt   20  100000.0 ±2000  ops/s
SerializationBenchmark.toStringLarge        thrpt   20   1000.0   ±50   ops/s
PathBuilderBenchmark.buildSimplePath        thrpt   20  200000.0 ±5000  ops/s
ColorBenchmark.parseHexColor                thrpt   20  500000.0 ±10000 ops/s
ValidationBenchmark.validateDefaultRules    thrpt   20   10000.0  ±200  ops/s
```

### Custom Benchmarks

```groovy
import org.openjdk.jmh.annotations.*
import se.alipsa.groovy.svg.*

@State(Scope.Benchmark)
class MyBenchmark {

    @Benchmark
    def testMyOperation() {
        Svg svg = new Svg(400, 400)
        for (int i = 0; i < 100; i++) {
            svg.addCircle().cx(i).cy(i).r(5)
        }
        return svg.toString()
    }
}
```

### Profiling with VisualVM

```bash
# Run with profiling
java -agentlib:hprof=cpu=samples,depth=20 -jar yourapp.jar

# Or use VisualVM/YourKit for visual profiling
```

## Best Practices Summary

### File Size

1. ✅ Use NumberFormatter with precision 2-3 for most graphics
2. ✅ Reuse definitions with `<defs>` and `<use>`
3. ✅ Group common attributes with `<g>`
4. ✅ Use CSS classes for repeated styles
5. ✅ Remove default attribute values
6. ✅ Use relative path commands when appropriate

### Runtime Performance

1. ✅ Validate once at the end, not during construction
2. ✅ Use method chaining or map-based construction
3. ✅ Query elements once, cache results
4. ✅ Use specific selectors (avoid recursive searches)
5. ✅ Build paths incrementally with PathBuilder

### Memory

1. ✅ Release references to removed elements
2. ✅ Avoid accumulating elements in collections
3. ✅ Call toString() once, reuse result
4. ✅ Process large SVGs in batches if memory-constrained
5. ✅ Clone only when necessary

### Measurement

1. ✅ Use JMH benchmarks for accurate performance testing
2. ✅ Profile with tools like VisualVM for real applications
3. ✅ Measure file sizes before/after optimization
4. ✅ Test with realistic data sizes
5. ✅ Consider both creation time and serialization time

### Performance Checklist

Before deploying:
- [ ] Set appropriate numeric precision
- [ ] Validate only when necessary
- [ ] Reuse common elements via definitions
- [ ] Group elements with shared attributes
- [ ] Profile memory usage for large SVGs
- [ ] Benchmark critical paths
- [ ] Test file sizes meet requirements
- [ ] Verify rendering performance in target browsers

---

For detailed benchmark results, see [doc/benchmarks.md](benchmarks.md).

For optimization examples, see [doc/cookbook.md](cookbook.md).
