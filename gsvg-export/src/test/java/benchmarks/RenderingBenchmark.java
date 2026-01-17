package benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import se.alipsa.groovy.svg.Svg;
import se.alipsa.groovy.svg.export.SvgRenderer;
import se.alipsa.groovy.svg.io.SvgReader;

import java.awt.image.BufferedImage;

/**
 * Benchmarks for rendering SVG to raster images.
 */
public class RenderingBenchmark extends BenchmarkBase {

    private Svg simpleSvg;
    private Svg mediumSvg;
    private Svg largeSvg;
    private Svg complexSvg;

    @Setup
    public void setup() {
        simpleSvg = SvgReader.parse(loadTestSvg("simple.svg"));
        mediumSvg = SvgReader.parse(loadTestSvg("medium.svg"));
        largeSvg = SvgReader.parse(loadTestSvg("large.svg"));
        complexSvg = SvgReader.parse(loadTestSvg("complex.svg"));
    }

    @Benchmark
    public BufferedImage renderSimpleSvg() {
        return SvgRenderer.toBufferedImage(simpleSvg);
    }

    @Benchmark
    public BufferedImage renderMediumSvg() {
        return SvgRenderer.toBufferedImage(mediumSvg);
    }

    @Benchmark
    public BufferedImage renderLargeSvg() {
        return SvgRenderer.toBufferedImage(largeSvg);
    }

    @Benchmark
    public BufferedImage renderComplexSvg() {
        return SvgRenderer.toBufferedImage(complexSvg);
    }
}
