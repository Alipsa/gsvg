package benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import se.alipsa.groovy.svg.Svg;
import se.alipsa.groovy.svg.io.SvgReader;
import se.alipsa.groovy.svg.utils.SvgMerger;

/**
 * Benchmarks for SVG merging operations.
 */
public class MergingBenchmark extends BenchmarkBase {

    private Svg svg1;
    private Svg svg2;
    private Svg svg3;

    @Setup
    public void setup() {
        svg1 = SvgReader.parse(loadTestSvg("simple.svg"));
        svg2 = SvgReader.parse(loadTestSvg("simple.svg"));
        svg3 = SvgReader.parse(loadTestSvg("simple.svg"));
    }

    @Benchmark
    public Svg benchmarkMergeHorizontal() {
        return SvgMerger.mergeHorizontally(svg1, svg2, svg3);
    }

    @Benchmark
    public Svg benchmarkMergeVertical() {
        return SvgMerger.mergeVertically(svg1, svg2, svg3);
    }

    @Benchmark
    public Svg benchmarkMergeOnTop() {
        return SvgMerger.mergeOnTop(svg1, svg2, svg3);
    }

    @Benchmark
    public Svg benchmarkMergeLargeSvgs() {
        Svg large1 = SvgReader.parse(loadTestSvg("medium.svg"));
        Svg large2 = SvgReader.parse(loadTestSvg("medium.svg"));
        return SvgMerger.mergeHorizontally(large1, large2);
    }
}
