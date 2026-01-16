package examples.runner

import groovy.transform.CompileStatic
import org.codehaus.groovy.control.CompilerConfiguration

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

@CompileStatic
final class RunExamples {

  private RunExamples() {
  }

  static void main(String[] args) {
    System.setProperty('groovy.grape.enable', System.getProperty('groovy.grape.enable', 'false'))
    System.setProperty('groovy.grape.autoDownload', System.getProperty('groovy.grape.autoDownload', 'false'))
    System.setProperty('java.awt.headless', System.getProperty('java.awt.headless', 'true'))

    String baseDirProp = System.getProperty('examples.baseDir')
    Path baseDir = baseDirProp ? Path.of(baseDirProp) : Path.of('')
    Path root = baseDir.resolve(Path.of('src', 'main', 'groovy', 'examples'))
    Path shared = root.resolve('shared')
    Path runner = root.resolve('runner')

    if (!Files.isDirectory(root)) {
      println "No examples directory found at ${root.toAbsolutePath()}"
      return
    }

    List<Path> scripts = Files.walk(root)
      .filter { Files.isRegularFile(it) && it.toString().endsWith('.groovy') }
      .filter { !it.startsWith(shared) }
      .filter { !it.startsWith(runner) }
      .sorted()
      .collect(Collectors.toList())

    if (scripts.isEmpty()) {
      println "No example scripts found under ${root.toAbsolutePath()}"
      return
    }

    int failures = 0
    for (Path script : scripts) {
      String relative = root.relativize(script).toString()
      println "Running ${relative}"
      try {
        GroovyShell shell = new GroovyShell(new Binding(), new CompilerConfiguration())
        shell.evaluate(script.toFile())
      } catch (Throwable ex) {
        failures++
        System.err.println("Example failed: ${relative}")
        ex.printStackTrace(System.err)
      }
    }

    if (failures > 0) {
      throw new RuntimeException("Failed examples: ${failures}")
    }
  }
}
