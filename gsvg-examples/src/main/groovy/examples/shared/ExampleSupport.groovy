package examples.shared

import java.nio.file.Files
import java.nio.file.Path

final class ExampleSupport {

  private ExampleSupport() {
  }

  static Path outputDir() {
    Path dir = Path.of("target", "examples-output")
    Files.createDirectories(dir)
    return dir
  }
}
