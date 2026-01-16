package benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import se.alipsa.groovy.svg.Svg;

/**
 * Benchmarks for SVG element creation performance.
 */
public class CreationBenchmark extends BenchmarkBase {

    @Benchmark
    public Svg benchmarkCreateSimpleElements() {
        Svg svg = new Svg(200, 200);
        svg.addCircle().cx(50).cy(50).r(40).fill("red");
        svg.addRect().x(100).y(10).width(80).height(60).fill("blue");
        svg.addCircle().cx(50).cy(150).r(30).fill("green");
        svg.addLine().x1(120).y1(100).x2(180).y2(160).stroke("black").strokeWidth(String.valueOf(2));
        return svg;
    }

    @Benchmark
    public Svg benchmarkCreateWithAttributes() {
        Svg svg = new Svg(400, 400);
        svg.addCircle()
                .cx(200).cy(200).r(100)
                .fill("red").stroke("black").strokeWidth(2)
                .id("circle1").styleClass("shape");
        svg.addRect()
                .x(50).y(50).width(300).height(300)
                .fill("blue").stroke("green").strokeWidth(3)
                .rx(10).ry(10);
        return svg;
    }

    @Benchmark
    public Svg benchmarkCreateNestedStructure() {
        Svg svg = new Svg(800, 600);
        var g1 = svg.addG().id("group1");
        var g2 = g1.addG().id("group2");
        var g3 = g2.addG().id("group3");

        g3.addCircle().cx(100).cy(100).r(50).fill("red");
        g3.addRect().x(200).y(50).width(100).height(100).fill("blue");

        var defs = svg.addDefs();
        var grad = defs.addLinearGradient().id("grad1");
        grad.addStop().offset("0%").stopColor("yellow");
        grad.addStop().offset("100%").stopColor("orange");

        return svg;
    }

    @Benchmark
    public Svg benchmarkFluentApiChaining() {
        Svg svg = new Svg(1000, 1000);
        svg.addCircle()
                .cx(100).cy(100).r(50)
                .fill("red").stroke("black").strokeWidth(2)
                .opacity(0.9)
                .rotate(45)
                .scale(1.5).translate(10, 20)
                .id("circle-chained").styleClass("animated");

        svg.addRect()
                .x(200).y(200).width(150).height(100)
                .fill("blue").stroke("green").strokeWidth(3)
                .rx(15).ry(15)
                .opacity(0.7)
                .rotate(30, 275, 250)
                .scale(1.2).skewX(10)
                .id("rect-chained");

        return svg;
    }
}
