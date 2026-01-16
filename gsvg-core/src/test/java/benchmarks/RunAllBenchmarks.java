package benchmarks;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Main runner for all gsvg benchmarks.
 * <p>
 * Run with: mvn clean test-compile exec:java -Dexec.mainClass="benchmarks.RunAllBenchmarks"
 * Or compile and run: java -cp target/test-classes:... benchmarks.RunAllBenchmarks
 */
public class RunAllBenchmarks {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include("benchmarks\\..*Benchmark")
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
