package benchmarks;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Base class for gsvg-export benchmarks providing common setup and utilities.
 */
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(0)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public abstract class BenchmarkBase {

    /**
     * Load SVG content from test resources.
     *
     * @param filename Name of the SVG file in src/test/resources/benchmarks/
     * @return SVG content as String
     */
    protected String loadTestSvg(String filename) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("benchmarks/" + filename)) {
            if (is == null) {
                throw new RuntimeException("Could not find resource: benchmarks/" + filename);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error loading test SVG: " + filename, e);
        }
    }
}
