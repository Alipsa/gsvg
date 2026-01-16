package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.NumberFormatter

/**
 * Quick verification script to demonstrate file size reduction with numeric precision control.
 * Not a unit test - run manually to see the impact.
 */
class FileSizeVerification {

    static void main(String[] args) {
        // Create SVG with 1000 circles without precision control
        NumberFormatter.setDefaultPrecision(9) // Full precision
        Svg svg1 = new Svg(1000, 1000)
        1000.times { i ->
            double cx = Math.random() * 1000
            double cy = Math.random() * 1000
            double r = Math.random() * 50
            svg1.addCircle()
                .cx(cx)
                .cy(cy)
                .r(r)
                .fill("rgb(${(int)(Math.random()*255)},${(int)(Math.random()*255)},${(int)(Math.random()*255)})")
        }
        String xml1 = svg1.toXml()

        // Create same SVG with precision control
        NumberFormatter.setDefaultPrecision(3) // Default precision
        Svg svg2 = new Svg(1000, 1000)
        1000.times { i ->
            double cx = Math.random() * 1000
            double cy = Math.random() * 1000
            double r = Math.random() * 50
            svg2.addCircle()
                .cx(cx)
                .cy(cy)
                .r(r)
                .fill("rgb(${(int)(Math.random()*255)},${(int)(Math.random()*255)},${(int)(Math.random()*255)})")
        }
        String xml2 = svg2.toXml()

        int size1 = xml1.length()
        int size2 = xml2.length()
        int reduction = size1 - size2
        double percent = (reduction / (double)size1) * 100

        println "Full precision (9 decimals): ${size1} bytes"
        println "Default precision (3 decimals): ${size2} bytes"
        println "Reduction: ${reduction} bytes (${String.format('%.1f', percent)}%)"

        NumberFormatter.resetPrecision() // Reset to default
    }
}
