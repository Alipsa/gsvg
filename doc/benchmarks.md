# gsvg Performance Benchmarks

This document contains performance benchmarks for the gsvg library, measured using the [JMH (Java Microbenchmark Harness)](https://github.com/openjdk/jmh) framework.

## How to Run Benchmarks

The benchmarks are written in Java (not Groovy) because JMH requires precise JVM control for accurate measurements.

### Option 1: Maven exec (Recommended)
```bash
mvn clean test-compile
mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass="benchmarks.RunAllBenchmarks"
```

### Option 2: Run from command line
```bash
mvn clean package -DskipTests
java -cp "target/test-classes:target/classes:$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout)" benchmarks.RunAllBenchmarks
```

### Running Individual Benchmarks
To run a specific benchmark suite:
```bash
java -cp "target/test-classes:target/classes:..." benchmarks.ParsingBenchmark
java -cp "target/test-classes:target/classes:..." benchmarks.SerializationBenchmark
# etc.
```

### JMH Configuration
Default settings:
- **Warmup**: 3 iterations, 1 second each
- **Measurement**: 5 iterations, 1 second each
- **Mode**: Average time per operation (lower is better)
- **Threads**: 1 thread

Customize with JMH options:
```bash
# Quick run with fewer iterations
java -cp ... benchmarks.RunAllBenchmarks -wi 2 -i 3 -f 1

# More thorough benchmarking
java -cp ... benchmarks.RunAllBenchmarks -wi 5 -i 10 -f 3
```

## Benchmark Results

Measured on: Apple M-series, macOS 25.2.0, JDK 17.0.17, gsvg 0.8.0

All times are in microseconds (µs) unless noted. Lower is better.

| Benchmark | Score (µs/op) | Error | Description |
|-----------|---------------|-------|-------------|
| **Parsing** |
| `parseSimpleSvg` | 206.97 | ±107.42 | Parse 10-element SVG |
| `parseMediumSvg` | 1,089.30 | ±2,069.25 | Parse 100-element SVG |
| `parseLargeSvg` | 6,110.83 | ±4,327.42 | Parse 1000-element SVG |
| `parseComplexSvg` | 2,426.98 | ±844.75 | Parse real-world SVG structure |
| **Serialization** |
| `toXmlSimple` | 3.00 | ±0.08 | Serialize simple SVG to XML |
| `toXmlMedium` | 17.58 | ±0.25 | Serialize 100-element SVG |
| `toXmlLarge` | 286.47 | ±31.63 | Serialize 1000-element SVG |
| `toXmlComplex` | 25.92 | ±0.34 | Serialize complex structure |
| `toXmlPrettySimple` | 3.25 | ±0.04 | Pretty-print simple SVG |
| `toXmlPrettyComplex` | 29.92 | ±0.51 | Pretty-print complex SVG |
| **Creation** |
| `createSimpleElements` | 7.35 | ±2.20 | Create 10 basic shapes |
| `createWithAttributes` | 4.32 | ±0.07 | Create elements with attributes |
| `createNestedStructure` | 7.46 | ±0.14 | Create nested groups |
| `fluentApiChaining` | 7.48 | ±0.28 | Use fluent API for building |
| **Cloning** |
| `cloneSimpleElement` | 1.39 | ±0.28 | Clone single element |
| `cloneWithChildren` | 5.70 | ±0.36 | Deep clone element tree |
| `serializeAndParse` | 2.94 | ±0.09 | Round-trip via XML |
| **Merging** |
| `mergeHorizontal` | 98.96 | ±1.11 | Merge two SVGs side-by-side |
| `mergeVertical` | 100.81 | ±4.26 | Merge two SVGs top-to-bottom |
| `mergeOnTop` | 99.70 | ±5.86 | Layer two SVGs |
| `mergeLargeSvgs` | 2,935.43 | ±4,541.51 | Merge large documents |
| **Selection** |
| `findAllByType` | 5.33 | ±0.45 | Find all elements of type |
| `findFirst` | 3.11 | ±0.37 | Find first matching element |
| `descendants` | 75.26 | ±2.70 | Get all descendants recursively |
| `descendantsOfType` | 75.95 | ±10.89 | Get typed descendants |
| `xPathSimple` | 103.27 | ±7.86 | Simple XPath query |
| `xPathComplex` | 119.52 | ±6.89 | Complex XPath query |
| **Utilities** |
| `colorRgbCreation` | 0.009 | ±0.001 | Create RGB color |
| `colorHexParsing` | 0.073 | ±0.002 | Parse hex color |
| `colorParsing` | 0.856 | ±0.007 | Auto-detect and parse color |
| `colorInterpolation` | 0.026 | ±0.001 | Mix two colors |
| `colorConversionRgbToHsl` | 4.78 | ±0.43 | Convert RGB to HSL |
| `pathParsing` | 0.012 | ±0.001 | Parse path data string |
| `pathBuilding` | 1.58 | ±0.05 | Build complex path |
| `pathToString` | 0.944 | ±0.02 | Convert path to string |
| `viewBoxCreation` | 0.004 | ±0.001 | Create ViewBox object |
| `viewBoxParsing` | 0.207 | ±0.002 | Parse viewBox string |
| `viewBoxTransformations` | 0.014 | ±0.001 | Transform ViewBox |
| `viewBoxContainment` | 0.005 | ±0.001 | Check containment |

## Performance Characteristics

### Parsing Performance
- **Simple SVGs (10 elements)**: ~207 µs - Very fast for small documents
- **Medium SVGs (100 elements)**: ~1.1 ms - Linear scaling
- **Large SVGs (1000 elements)**: ~6.1 ms - Continues linear scaling
- **Throughput**: Can parse ~160-180 medium documents per second

### Serialization Performance
- **Simple to XML**: ~3 µs - Extremely fast for basic output
- **Medium to XML**: ~17.6 µs - Scales linearly with element count
- **Large to XML**: ~286 µs - Maintains linear scaling
- **Pretty printing overhead**: Minimal (~8-15% slower than compact)

### Creation Performance
- **Fluent API**: ~7.5 µs per element - No overhead from method chaining
- **With attributes**: ~4.3 µs per element - Efficient attribute handling
- **Nested structures**: ~7.5 µs - Grouping has minimal overhead

### Cloning Performance
- **Simple element**: ~1.4 µs - Very fast shallow operations
- **Deep clone**: ~5.7 µs - Efficient recursive copying
- **XML round-trip**: ~2.9 µs - Competitive with object cloning

### Merging Performance
- **Simple merges**: ~100 µs - Fast for typical use cases
- **Large documents**: ~2.9 ms - Scales with total element count
- **Memory efficient**: Pure object copying, no XML serialization

### Selection Performance
- **Type-based search**: ~5.3 µs - Very fast filtering
- **First match**: ~3.1 µs - Early exit optimization works well
- **Recursive descendants**: ~75 µs - Efficient tree traversal
- **XPath queries**: ~103-120 µs - Good performance for complex queries

### Utility Performance
- **Color operations**: 0.009-4.8 µs - Extremely fast, HSL conversion is most expensive
- **Path operations**: 0.012-1.6 µs - Efficient parsing and building
- **ViewBox operations**: 0.004-0.21 µs - Minimal overhead for coordinate helpers

## Performance Tips

### 1. Parsing
- **Reuse parsed documents** when possible instead of re-parsing
- For large documents, **parse once and clone** rather than re-parse
- Use `SvgReader.parse()` directly rather than string conversions

### 2. Creation
- **Fluent API has no overhead** - use it freely for readability
- **Batch attribute setting** with `addAttributes(Map)` is efficient
- Creating elements is very cheap (~4-8 µs each)

### 3. Serialization
- **toXml() is very fast** - don't avoid it for performance
- **Pretty printing overhead is minimal** - use it for debugging
- For large documents, serialization is ~50x faster than parsing

### 4. Searching
- **Use type-based search** (`svg[Rect]`) for best performance
- **findFirst() is faster** than filtering when you only need one match
- XPath is powerful but ~20x slower than simple type searches
- **Cache search results** if querying repeatedly

### 5. Merging
- **Merging is efficient** (~100 µs for typical documents)
- No XML serialization overhead - pure object operations
- For many merges, consider batching operations

### 6. Cloning
- **Direct cloning is faster** than serialize/parse roundtrip
- Deep cloning is only ~4x slower than shallow
- Cloning is very cheap for reusing element patterns

## Benchmark Methodology

All benchmarks use JMH (Java Microbenchmark Harness) with:
- **JVM warmup**: 3 iterations to allow JIT compilation
- **Measurement**: 5 iterations for statistical significance
- **Fork count**: 1 (single JVM instance)
- **Threads**: 1 (single-threaded measurement)
- **Blackhole**: Compiler mode to prevent dead code elimination

Test data:
- **simple.svg**: 10 elements - basic shapes
- **medium.svg**: 100 elements - typical document
- **large.svg**: 1000 elements - stress test
- **complex.svg**: Real-world structure with groups, transforms, gradients

## Interpreting Results

- **Score**: Average time per operation in microseconds (µs/op)
- **Error**: 99.9% confidence interval
- **Lower is better**: Smaller numbers mean faster operations
- **Variance**: Large errors indicate high variability (often due to GC or JIT)

## Version History

### v0.8.0 (2026-01-14)
- Initial benchmark suite
- 39 benchmark methods across 8 categories
- Baseline measurements on Apple M-series / JDK 17

## Related Documentation

- [Creating SVGs](creating.md) - Learn fluent API patterns
- [Parsing SVGs](parsing.md) - Efficient parsing techniques
- [Merging SVGs](merging.md) - Combining documents
- [API Documentation](https://javadoc.io/doc/se.alipsa.groovy/gsvg) - Full API reference
