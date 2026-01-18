# Framework Compatibility Testing

This guide provides a minimal smoke-test plan to verify gsvg works inside popular Groovy frameworks.

## Grails (controller rendering)

1) Add gsvg dependency to your Grails app:

```groovy
// build.gradle
dependencies {
  implementation "se.alipsa.groovy:gsvg:1.0.0"
  // Optional: rendering/export helpers
  // implementation "se.alipsa.groovy:gsvg-export:1.0.0"
}
```

2) Add a simple controller that returns SVG:

```groovy
// grails-app/controllers/example/SvgController.groovy
package example

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgWriter

class SvgController {

  def index() {
    Svg svg = new Svg(120, 80)
    svg.addRect(100, 60).x(10).y(10).fill('#1976d2')
    svg.addText('gsvg').x(20).y(50).fill('white').fontSize(16)

    render(text: SvgWriter.toXml(svg), contentType: 'image/svg+xml')
  }
}
```

3) Run the app and verify the route renders:

```bash
./gradlew bootRun
# or: grails run-app
```

Browse to `http://localhost:8080/svg` (adjust for your URL mapping) and confirm the SVG renders.

## Ratpack (handler rendering)

1) Add gsvg dependency to your Ratpack app:

```groovy
// build.gradle
dependencies {
  implementation "org.apache.groovy:groovy:5.0.3"
  implementation "io.ratpack:ratpack-groovy:<your-version>"
  implementation "se.alipsa.groovy:gsvg:1.0.0"
}
```

2) Add a handler that returns SVG:

```groovy
// Ratpack.groovy
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgWriter

ratpack {
  handlers {
    get("svg") {
      Svg svg = new Svg(120, 80)
      svg.addCircle().cx(60).cy(40).r(30).fill('#26a69a')
      response.contentType("image/svg+xml")
      render SvgWriter.toXml(svg)
    }
  }
}
```

3) Run the app and verify the route renders:

```bash
./gradlew run
```

Browse to `http://localhost:5050/svg` (default Ratpack port) and confirm the SVG renders.

## What to record

- Framework and version used
- gsvg version used
- Notes on any warnings or runtime errors
- Confirmation that the SVG renders in the browser
