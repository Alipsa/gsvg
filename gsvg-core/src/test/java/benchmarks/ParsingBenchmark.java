package benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import se.alipsa.groovy.svg.Svg;
import se.alipsa.groovy.svg.io.SvgReader;

/**
 * Benchmarks for SVG parsing performance.
 */
public class ParsingBenchmark extends BenchmarkBase {

    private String simpleSvg;
    private String mediumSvg;
    private String largeSvg;
    private String complexSvg;

    @Setup
    public void setup() {
        simpleSvg = loadTestSvg("simple.svg");
        mediumSvg = loadTestSvg("medium.svg");
        largeSvg = loadTestSvg("large.svg");
        complexSvg = loadTestSvg("complex.svg");
    }

    @Benchmark
    public Svg benchmarkParseSimpleSvg() {
        return SvgReader.parse(simpleSvg);
    }

    @Benchmark
    public Svg benchmarkParseMediumSvg() {
        return SvgReader.parse(mediumSvg);
    }

    @Benchmark
    public Svg benchmarkParseLargeSvg() {
        return SvgReader.parse(largeSvg);
    }

    @Benchmark
    public Svg benchmarkParseComplexSvg() {
        return SvgReader.parse(complexSvg);
    }
}
