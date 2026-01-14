package benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import se.alipsa.groovy.svg.Svg;
import se.alipsa.groovy.svg.io.SvgReader;
import se.alipsa.groovy.svg.io.SvgWriter;

/**
 * Benchmarks for SVG serialization performance.
 */
public class SerializationBenchmark extends BenchmarkBase {

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
    public String benchmarkToXmlSimple() {
        return SvgWriter.toXml(simpleSvg);
    }

    @Benchmark
    public String benchmarkToXmlMedium() {
        return SvgWriter.toXml(mediumSvg);
    }

    @Benchmark
    public String benchmarkToXmlLarge() {
        return SvgWriter.toXml(largeSvg);
    }

    @Benchmark
    public String benchmarkToXmlComplex() {
        return SvgWriter.toXml(complexSvg);
    }

    @Benchmark
    public String benchmarkToXmlPrettySimple() {
        return SvgWriter.toXmlPretty(simpleSvg);
    }

    @Benchmark
    public String benchmarkToXmlPrettyComplex() {
        return SvgWriter.toXmlPretty(complexSvg);
    }
}
