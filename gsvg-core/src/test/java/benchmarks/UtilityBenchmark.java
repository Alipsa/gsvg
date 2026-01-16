package benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import se.alipsa.groovy.svg.utils.Color;
import se.alipsa.groovy.svg.utils.PathBuilder;
import se.alipsa.groovy.svg.utils.ViewBox;

/**
 * Benchmarks for utility class operations (Color, PathBuilder, ViewBox).
 */
public class UtilityBenchmark extends BenchmarkBase {

    @Benchmark
    public Color benchmarkColorRgbCreation() {
        return Color.rgb(255, 128, 64);
    }

    @Benchmark
    public Color benchmarkColorHexParsing() {
        return Color.hex("#ff8040");
    }

    @Benchmark
    public Color benchmarkColorParsing() {
        return Color.parse("rgba(255, 128, 64, 0.5)");
    }

    @Benchmark
    public Color benchmarkColorConversionRgbToHsl() {
        Color color = Color.rgb(255, 128, 64);
        return Color.parse(color.toHsl());
    }

    @Benchmark
    public Color benchmarkColorInterpolation() {
        Color red = Color.rgb(255, 0, 0);
        Color blue = Color.rgb(0, 0, 255);
        return red.interpolate(blue, 0.5);
    }

    @Benchmark
    public PathBuilder benchmarkPathBuilding() {
        return PathBuilder.create()
                .moveTo(10, 10)
                .lineTo(100, 100)
                .curveTo(150, 50, 200, 150, 250, 100)
                .quadTo(300, 50, 350, 100)
                .arc(50, 50, 0, 0, 1, 400, 150)
                .closePath();
    }

    @Benchmark
    public PathBuilder benchmarkPathParsing() {
        return PathBuilder.parse("M 10 10 L 100 100 C 150 50 200 150 250 100 Q 300 50 350 100 A 50 50 0 0 1 400 150 Z");
    }

    @Benchmark
    public String benchmarkPathToString() {
        PathBuilder path = PathBuilder.create()
                .moveTo(10, 10)
                .lineTo(100, 100)
                .curveTo(150, 50, 200, 150, 250, 100)
                .closePath();
        return path.toString();
    }

    @Benchmark
    public ViewBox benchmarkViewBoxCreation() {
        return ViewBox.of(0, 0, 100, 100);
    }

    @Benchmark
    public ViewBox benchmarkViewBoxParsing() {
        return ViewBox.parse("0 0 100 100");
    }

    @Benchmark
    public ViewBox benchmarkViewBoxTransformations() {
        return ViewBox.of(0, 0, 100, 100)
                .scale(2.0)
                .translate(10, 10)
                .expand(5)
                .centerAt(50, 50);
    }

    @Benchmark
    public boolean benchmarkViewBoxContainment() {
        ViewBox outer = ViewBox.of(0, 0, 200, 200);
        ViewBox inner = ViewBox.of(50, 50, 100, 100);
        return outer.contains(inner);
    }
}
