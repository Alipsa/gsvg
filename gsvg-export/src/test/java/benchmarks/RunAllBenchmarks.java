package benchmarks;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Main runner for gsvg-export benchmarks.
 * <p>
 * Run with: java -cp target/test-classes:... benchmarks.RunAllBenchmarks
 */
public class RunAllBenchmarks {

    public static void main(String[] args) throws RunnerException {
        System.setProperty("java.awt.headless", "true");
        Options opt = new OptionsBuilder()
                .include("benchmarks\\..*Benchmark")
                .forks(0)
                .build();

        new Runner(opt).run();
    }
}
