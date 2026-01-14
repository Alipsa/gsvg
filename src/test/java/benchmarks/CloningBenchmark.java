package benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import se.alipsa.groovy.svg.*;
import se.alipsa.groovy.svg.io.SvgReader;

/**
 * Benchmarks for SVG cloning operations.
 */
public class CloningBenchmark extends BenchmarkBase {

    private Svg simpleSvg;
    private Svg complexSvg;
    private Circle circle;
    private G groupWithChildren;

    @Setup
    public void setup() {
        simpleSvg = SvgReader.parse(loadTestSvg("simple.svg"));
        complexSvg = SvgReader.parse(loadTestSvg("complex.svg"));

        // Create a circle for single element cloning
        Svg tempSvg = new Svg(100, 100);
        circle = tempSvg.addCircle().cx(50).cy(50).r(40).fill("red");

        // Create a group with children for clone with descendants
        Svg tempSvg2 = new Svg(200, 200);
        groupWithChildren = tempSvg2.addG().id("group1");
        groupWithChildren.addCircle().cx(50).cy(50).r(20).fill("blue");
        groupWithChildren.addRect().x(100).y(100).width(50).height(50).fill("green");
        var nestedG = groupWithChildren.addG().id("nested");
        nestedG.addCircle().cx(150).cy(150).r(30).fill("yellow");
    }

    @Benchmark
    public Circle benchmarkCloneSimpleElement() {
        Svg newSvg = new Svg(100, 100);
        return (Circle) circle.clone(newSvg);
    }

    @Benchmark
    public G benchmarkCloneWithChildren() {
        Svg newSvg = new Svg(200, 200);
        return (G) groupWithChildren.clone(newSvg);
    }

    @Benchmark
    public String benchmarkSerializeAndParse() {
        // Alternative approach to deep copy - serialize and parse
        String xml = simpleSvg.toXml();
        return xml;
    }
}
