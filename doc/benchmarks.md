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

Measured on: Linux Mint 22.3 (kernel 6.8.0-90-generic), JDK 21.0.9, gsvg 1.0.0-SNAPSHOT

All times are in microseconds (µs) unless noted. Lower is better.
Parsing and merging results use a more thorough run (5 warmup, 10 measurement, 3 forks).
Rendering results are from `gsvg-export` and run headless with no forks.

| Benchmark                 | Score (µs/op) | Error       | Description                      |
|---------------------------|---------------|-------------|----------------------------------|
| **Parsing**               |
| `parseSimpleSvg`          | 774.631       | ±214.668    | Parse 10-element SVG             |
| `parseMediumSvg`          | 3,386.189     | ±1,067.373  | Parse 100-element SVG            |
| `parseLargeSvg`           | 25,116.618    | ±17,095.831 | Parse 1000-element SVG           |
| `parseComplexSvg`         | 5,765.933     | ±1,812.964  | Parse real-world SVG structure   |
| **Serialization**         |
| `toXmlSimple`             | 8.421         | ±2.898      | Serialize simple SVG to XML      |
| `toXmlMedium`             | 54.424        | ±1.917      | Serialize 100-element SVG        |
| `toXmlLarge`              | 858.411       | ±29.323     | Serialize 1000-element SVG       |
| `toXmlComplex`            | 70.112        | ±11.920     | Serialize complex structure      |
| `toXmlPrettySimple`       | 10.558        | ±4.601      | Pretty-print simple SVG          |
| `toXmlPrettyComplex`      | 93.521        | ±28.848     | Pretty-print complex SVG         |
| **Creation**              |
| `createSimpleElements`    | 4.802         | ±0.393      | Create 10 basic shapes           |
| `createWithAttributes`    | 3.884         | ±0.615      | Create elements with attributes  |
| `createNestedStructure`   | 11.710        | ±5.957      | Create nested groups             |
| `fluentApiChaining`       | 12.090        | ±12.646     | Use fluent API for building      |
| **Cloning**               |
| `cloneSimpleElement`      | 1.936         | ±0.103      | Clone single element             |
| `cloneWithChildren`       | 10.194        | ±2.282      | Deep clone element tree          |
| `serializeAndParse`       | 6.666         | ±0.794      | Round-trip via XML               |
| **Merging**               |
| `mergeHorizontal`         | 192.208       | ±21.180     | Merge two SVGs side-by-side      |
| `mergeVertical`           | 284.760       | ±40.798     | Merge two SVGs top-to-bottom     |
| `mergeOnTop`              | 276.349       | ±41.962     | Layer two SVGs                   |
| `mergeLargeSvgs`          | 14,628.279    | ±3,282.474  | Merge large documents            |
| **Rendering (gsvg-export)** |
| `renderSimpleSvg`         | 593.167       | ±228.328    | Render 10-element SVG            |
| `renderMediumSvg`         | 4,811.688     | ±1,348.922  | Render 100-element SVG           |
| `renderLargeSvg`          | 20,087.839    | ±30,178.537 | Render 1000-element SVG          |
| `renderComplexSvg`        | 151,239.826   | ±77,387.525 | Render real-world SVG structure  |
| **Selection**             |
| `findAll`                 | 8.632         | ±0.910      | Find all elements of type        |
| `findFirst`               | 4.911         | ±0.927      | Find first matching element      |
| `descendants`             | 151.618       | ±28.568     | Get all descendants recursively  |
| `descendantsOfType`       | 167.482       | ±34.778     | Get typed descendants            |
| `xPathSimple`             | 203.144       | ±71.078     | Simple XPath query               |
| `xPathComplex`            | 296.220       | ±253.189    | Complex XPath query              |
| `cssSimple`               | 111.006       | ±25.915     | Simple CSS selector              |
| `cssFirst`                | 109.761       | ±26.101     | First match via CSS selector     |
| `cssComplex`              | 362.398       | ±36.524     | Complex CSS selector             |
| **Utilities**             |
| `colorRgbCreation`        | 0.015         | ±0.007      | Create RGB color                 |
| `colorHexParsing`         | 0.213         | ±0.401      | Parse hex color                  |
| `colorParsing`            | 2.618         | ±1.002      | Auto-detect and parse color      |
| `colorInterpolation`      | 0.051         | ±0.024      | Mix two colors                   |
| `colorConversionRgbToHsl` | 12.216        | ±7.470      | Convert RGB to HSL               |
| `pathParsing`             | 0.019         | ±0.009      | Parse path data string           |
| `pathBuilding`            | 1.615         | ±0.416      | Build complex path               |
| `pathToString`            | 0.943         | ±0.405      | Convert path to string           |
| `viewBoxCreation`         | 0.006         | ±0.001      | Create ViewBox object            |
| `viewBoxParsing`          | 0.537         | ±0.228      | Parse viewBox string             |
| `viewBoxTransformations`  | 0.023         | ±0.003      | Transform ViewBox                |
| `viewBoxContainment`      | 0.006         | ±0.002      | Check containment                |

## Memory Usage (alloc/op)

Measured with the JMH GC profiler (`-prof gc`). Parsing and merging values use the thorough run (5 warmup, 10 measurement, 3 forks).

| Benchmark         | Alloc (B/op) | Notes                      |
|-------------------|--------------:|----------------------------|
| `parseSimpleSvg`  | 249,394       | 10-element SVG             |
| `parseMediumSvg`  | 613,901       | 100-element SVG            |
| `parseLargeSvg`   | 4,838,766     | 1000-element SVG           |
| `parseComplexSvg` | 859,861       | Real-world structure       |
| `mergeHorizontal` | 187,847       | Typical two-svg merge      |
| `mergeVertical`   | 191,786       | Typical two-svg merge      |
| `mergeOnTop`      | 191,898       | Typical two-svg merge      |
| `mergeLargeSvgs`  | 1,855,691     | Large-document merge       |

### Performance Comparison (v0.8 vs v0.9 vs v1.0)

All comparison runs use the same machine and JDK as the results above.
v1.0 parsing and merging values use the thorough run noted above; v0.8/v0.9 values use the default JMH settings.

| Benchmark | v0.8.0 (µs/op) | v0.9.0 (µs/op) | v1.0.0 (µs/op) |
|-----------|----------------:|----------------:|----------------:|
| **Parsing** | | | |
| `parseSimpleSvg` | 1857.934 | 1044.564 | 774.631 |
| `parseMediumSvg` | 9513.890 | 5832.055 | 3,386.189 |
| `parseLargeSvg` | 70610.880 | 39588.633 | 25,116.618 |
| `parseComplexSvg` | 14939.948 | 9074.524 | 5,765.933 |
| **Serialization** | | | |
| `toXmlSimple` | 8.749 | 8.435 | 8.421 |
| `toXmlMedium` | 63.996 | 58.288 | 54.424 |
| `toXmlLarge` | 871.224 | 1063.729 | 858.411 |
| `toXmlComplex` | 67.038 | 76.313 | 70.112 |
| `toXmlPrettySimple` | 11.414 | 10.089 | 10.558 |
| `toXmlPrettyComplex` | 105.745 | 82.097 | 93.521 |
| **Creation** | | | |
| `createSimpleElements` | 15.046 | 6.255 | 4.802 |
| `createWithAttributes` | 7.313 | 4.882 | 3.884 |
| `createNestedStructure` | 22.226 | 15.294 | 11.710 |
| `fluentApiChaining` | 12.844 | 9.369 | 12.090 |
| **Cloning** | | | |
| `cloneSimpleElement` | 2.965 | 2.222 | 1.936 |
| `cloneWithChildren` | 15.520 | 10.406 | 10.194 |
| `serializeAndParse` | 11.708 | 8.555 | 6.666 |
| **Merging** | | | |
| `mergeHorizontal` | 247.233 | 392.189 | 192.208 |
| `mergeVertical` | 278.015 | 316.429 | 284.760 |
| `mergeOnTop` | 298.019 | 365.397 | 276.349 |
| `mergeLargeSvgs` | 15115.115 | 18179.597 | 14,628.279 |
| **Selection** | | | |
| `findAll` | 11.782 | 9.759 | 8.632 |
| `findFirst` | 5.778 | 5.185 | 4.911 |
| `descendants` | 151.591 | 185.968 | 151.618 |
| `descendantsOfType` | 175.397 | 236.260 | 167.482 |
| `xPathSimple` | 260.764 | 217.150 | 203.144 |
| `xPathComplex` | 373.805 | 233.142 | 296.220 |
| `cssSimple` | — | — | 111.006 |
| `cssFirst` | — | — | 109.761 |
| `cssComplex` | — | — | 362.398 |
| **Utilities** | | | |
| `colorRgbCreation` | 0.014 | 0.017 | 0.015 |
| `colorHexParsing` | 0.158 | 0.181 | 0.213 |
| `colorParsing` | 2.497 | 2.922 | 2.618 |
| `colorInterpolation` | 0.048 | 0.062 | 0.051 |
| `colorConversionRgbToHsl` | 16.039 | 11.870 | 12.216 |
| `pathParsing` | 0.020 | 0.020 | 0.019 |
| `pathBuilding` | 1.909 | 1.523 | 1.615 |
| `pathToString` | 1.084 | 0.938 | 0.943 |
| `viewBoxCreation` | 0.008 | 0.009 | 0.006 |
| `viewBoxParsing` | 0.534 | 0.565 | 0.537 |
| `viewBoxTransformations` | 0.021 | 0.024 | 0.023 |
| `viewBoxContainment` | 0.006 | 0.006 | 0.006 |

## Performance Characteristics

### Parsing Performance
- **Simple SVGs (10 elements)**: ~0.8 ms - Fast for small documents
- **Medium SVGs (100 elements)**: ~3.4 ms - Linear scaling
- **Large SVGs (1000 elements)**: ~25 ms - Larger variance on this dataset
- **Complex SVGs**: ~5.8 ms - Real-world structure cost
- **Throughput**: Can parse ~280-300 medium documents per second

### Serialization Performance
- **Simple to XML**: ~8.4 µs - Very fast for basic output
- **Medium to XML**: ~54 µs - Scales linearly with element count
- **Large to XML**: ~0.86 ms - Maintains linear scaling
- **Pretty printing overhead**: ~20-35% slower than compact

### Creation Performance
- **Fluent API**: ~12 µs per chain - Minimal overhead from method chaining
- **With attributes**: ~3.9 µs per element - Efficient attribute handling
- **Nested structures**: ~11-12 µs - Grouping has minimal overhead

### Cloning Performance
- **Simple element**: ~1.9 µs - Very fast shallow operations
- **Deep clone**: ~10 µs - Efficient recursive copying
- **XML round-trip**: ~6.7 µs - Competitive with object cloning

### Merging Performance
- **Simple merges**: ~190-285 µs - Fast for typical use cases
- **Large documents**: ~14.6 ms - Scales with total element count
- **Memory efficient**: Pure object copying, no XML serialization

### Selection Performance
- **Type-based search**: ~8.6 µs - Very fast filtering
- **First match**: ~4.9 µs - Early exit optimization works well
- **Recursive descendants**: ~152 µs - Efficient tree traversal
- **XPath queries**: ~203-296 µs - Good performance for complex queries
- **CSS selectors**: ~110-362 µs - Simple selectors faster than complex

### Rendering Performance (gsvg-export)
- **Simple SVGs**: ~0.6 ms - Fast rasterization for small documents
- **Medium SVGs**: ~4.8 ms - Scales with element count
- **Large SVGs**: ~20 ms - Larger variance on this dataset
- **Complex SVGs**: ~151 ms - Real-world structure cost

### Utility Performance
- **Color operations**: 0.015-12.2 µs - HSL conversion is most expensive
- **Path operations**: 0.019-1.6 µs - Efficient parsing and building
- **ViewBox operations**: 0.006-0.54 µs - Minimal overhead for coordinate helpers

## Phase 4 Feature Impact (v1.0)

- **Numeric precision control**: Parsing preserves raw attribute strings, so precision formatting is only applied when setting numeric attributes programmatically.
- **Accessibility helpers**: Thin attribute wrappers; no measurable creation/serialization regressions.
- **Template/DSL overhead**: Not separately benchmarked; creation benchmarks remain in line with v0.9 and are dominated by element creation cost.
- **Factory methods and merges**: Removing redundant cloning reduces merge costs in v1.0.

## Performance Tips

### 1. Parsing
- **Reuse parsed documents** when possible instead of re-parsing
- For large documents, **parse once and clone** rather than re-parse
- Use `SvgReader.parse()` directly rather than string conversions
- Parsed attributes keep their original string values; normalize numeric formatting by setting attributes programmatically

### 2. Creation
- **Fluent API has no overhead** - use it freely for readability
- **Batch attribute setting** with `addAttributes(Map)` is efficient
- Creating elements is very cheap (~4-12 µs each)

### 3. Serialization
- **toXml() is very fast** - don't avoid it for performance
- **Pretty printing overhead is minimal** - use it for debugging
- For large documents, serialization is ~30x faster than parsing

### 4. Searching
- **Use type-based search** (`svg[Rect]`) for best performance
- **findFirst() is faster** than filtering when you only need one match
- XPath is powerful but ~20-35x slower than simple type searches
- Simple CSS selectors are faster than XPath, complex selectors approach XPath cost
- **Cache search results** if querying repeatedly

### 5. Merging
- **Merging is efficient** (~190-285 µs for typical documents)
- No XML serialization overhead - pure object operations
- For many merges, consider batching operations

### 6. Cloning
- **Direct cloning is faster** than serialize/parse roundtrip
- Deep cloning is ~5x slower than shallow
- Cloning is very cheap for reusing element patterns

## Benchmark Methodology

All benchmarks use JMH (Java Microbenchmark Harness) with:
- **JVM warmup**: 3 iterations to allow JIT compilation
- **Measurement**: 5 iterations for statistical significance
- **Fork count**: 1 (single JVM instance)
- **Threads**: 1 (single-threaded measurement)
- **Blackhole**: Compiler mode to prevent dead code elimination
Parsing and merging results use a more thorough run (5 warmup, 10 measurement, 3 forks).
Memory usage numbers are collected with the JMH GC profiler (`-prof gc`).
Rendering results are run with `java.awt.headless=true` and `@Fork(0)` to avoid X11 and socket restrictions.

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

### v1.0.0 (2026-01-17)
- Added CSS selector benchmarks
- Added gsvg-export rendering benchmarks
- Baseline measurements on Linux Mint 22.3 / JDK 21.0.9

### v0.9.0 (2026-01-16)
- Baseline measurements on Linux Mint 22.3 / JDK 21.0.9

### v0.8.0 (2026-01-14)
- Initial benchmark suite
- 39 benchmark methods across 8 categories
- Baseline measurements re-run on Linux Mint 22.3 / JDK 21.0.9 for comparison

## Related Documentation

- [Creating SVGs](creating.md) - Learn fluent API patterns
- [Parsing SVGs](parsing.md) - Efficient parsing techniques
- [Merging SVGs](merging.md) - Combining documents
- [API Documentation](https://javadoc.io/doc/se.alipsa.groovy/gsvg) - Full API reference
