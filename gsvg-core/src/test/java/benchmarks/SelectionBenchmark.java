package benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import se.alipsa.groovy.svg.Circle;
import se.alipsa.groovy.svg.Rect;
import se.alipsa.groovy.svg.Svg;
import se.alipsa.groovy.svg.SvgElement;
import se.alipsa.groovy.svg.io.SvgReader;

import java.util.List;

/**
 * Benchmarks for SVG element selection operations.
 */
public class SelectionBenchmark extends BenchmarkBase {

    private Svg mediumSvg;
    private Svg largeSvg;
    private Svg complexSvg;

    @Setup
    public void setup() {
        mediumSvg = SvgReader.parse(loadTestSvg("medium.svg"));
        largeSvg = SvgReader.parse(loadTestSvg("large.svg"));
        complexSvg = SvgReader.parse(loadTestSvg("complex.svg"));
    }

    @Benchmark
    public List<Circle> benchmarkFindAllByType() {
        return mediumSvg.findAll(Circle.class);
    }

    @Benchmark
    public List benchmarkDescendants() {
        return complexSvg.descendants();
    }

    @Benchmark
    public List<Rect> benchmarkDescendantsOfType() {
        return complexSvg.descendants(Rect.class);
    }

    @Benchmark
    public List benchmarkXPathSimple() {
        return complexSvg.xpath("//circle");
    }

    @Benchmark
    public List benchmarkXPathComplex() {
        return complexSvg.xpath("//g[@id='content']//rect[@class='card']");
    }

    @Benchmark
    public List<SvgElement> benchmarkCssSimple() {
        return mediumSvg.css("circle");
    }

    @Benchmark
    public List<SvgElement> benchmarkCssComplex() {
        return complexSvg.css("#content .card rect");
    }

    @Benchmark
    public SvgElement benchmarkCssFirst() {
        return mediumSvg.cssFirst("circle");
    }

    @Benchmark
    public Circle benchmarkFindFirst() {
        return (Circle) mediumSvg.findFirst(Circle.class);
    }
}
