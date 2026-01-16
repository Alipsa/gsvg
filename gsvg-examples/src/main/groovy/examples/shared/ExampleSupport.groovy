package examples.shared

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgWriter

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

  static Path writeSvg(Svg svg, String fileName) {
    return writeText(SvgWriter.toXmlPretty(svg), fileName)
  }

  static Path writeText(String text, String fileName) {
    Path dir = outputDir()
    Path path = dir.resolve(fileName)
    Files.writeString(path, text)
    return path
  }
}
