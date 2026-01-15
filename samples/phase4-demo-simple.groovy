#!/usr/bin/env groovy

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.effects.Effects
import se.alipsa.groovy.svg.utils.NumberFormatter
import se.alipsa.groovy.svg.io.SvgWriter

/**
 * Simplified demonstration of Phase 4 (Polish) features in gsvg v0.9.0:
 * - Numeric Precision Control
 * - Accessibility Helpers
 * - Shape Factory Methods
 * - Effect Presets
 * - DSL Configuration
 * - Quick Wins (cloneWith, null-safe accessors)
 */

println "=== Phase 4 Features Demo ==="

// 1. NUMERIC PRECISION CONTROL
println "\n1. Numeric Precision Control"
NumberFormatter.setDefaultPrecision(2)
Svg svg = new Svg(800, 600).viewBox(0, 0, 800, 600)

def circle = svg.addCircle()
    .cx(50.123456789)
    .cy(50.987654321)
    .r(30.555555)
    .fill('steelblue')

println "   Circle with high precision input:"
println "   Input:  cx=50.123456789, cy=50.987654321, r=30.555555"
println "   Output: ${circle.toXml()}"
println "   Note: Numbers automatically rounded to 2 decimals"

// 2. ACCESSIBILITY HELPERS
println "\n2. Accessibility Helpers"
svg.role('img').ariaLabel('Phase 4 demonstration')
def button = svg.addRect()
    .x(10).y(10)
    .width(100).height(40)
    .fill('green')
    .role('button')
    .ariaLabel('Click to activate')

println "   Button with ARIA:"
println "   ${button.toXml()}"
println "   Note: role and aria-label for screen readers"

def decorative = svg.addCircle()
    .cx(200).cy(30)
    .r(10)
    .decorative()
    .fill('orange')

println "   Decorative element:"
println "   ${decorative.toXml()}"
println "   Note: role='presentation' and aria-hidden='true'"

// 3. SHAPE FACTORY METHODS
println "\n3. Shape Factory Methods"
def star = svg.createStar([
    cx: 100,
    cy: 150,
    points: 5,
    outerRadius: 30,
    innerRadius: 15,
    fill: 'gold'
])
println "   Star (5 points): createStar() factory method"
println "   Fill: ${star.getFill()}, Points: ${star.getPoints()}"

def roundedRect = svg.createRoundedRect([
    x: 200,
    y: 130,
    width: 80,
    height: 50,
    rx: 10,
    fill: 'lightblue'
])
println "   Rounded rect: createRoundedRect() factory"
println "   ${roundedRect.getRx()},${roundedRect.getRy()} corner radius"

// 4. EFFECT PRESETS
println "\n4. Effect Presets"
def shadowFilter = Effects.dropShadow(svg, [
    dx: 3,
    dy: 3,
    blur: 5,
    opacity: 0.3
])
def shadowRect = svg.addRect()
    .x(50).y(200)
    .width(80).height(50)
    .fill('coral')
    .filter("url(#${shadowFilter.getId()})")

println "   Drop shadow filter: id=${shadowFilter.getId()}"
println "   Applied to: ${shadowRect.toXml()}"

// 5. DSL CONFIGURATION
println "\n5. DSL-Style Configuration"
def dslCircle = svg.addCircle {
    cx 400
    cy 100
    r 25
    fill 'purple'
    stroke 'magenta'
    strokeWidth 2
}
println "   Circle configured with DSL closure"
println "   ${dslCircle.toXml()}"

// 6. QUICK WINS - cloneWith()
println "\n6. Quick Wins: cloneWith()"
def original = svg.addCircle()
    .cx(50).cy(300)
    .r(15)
    .fill('red')
    .stroke('darkred')

def clone1 = original.cloneWith(svg, [cx: 80, fill: 'green'])
def clone2 = original.cloneWith(svg, [cx: 110, fill: 'blue'])
def clone3 = original.cloneWith(svg, [cx: 140, fill: 'orange'])

println "   Original: fill=${original.getFill()}, cx=${original.getCx()}"
println "   Clone 1:  fill=${clone1.getFill()}, cx=${clone1.getCx()}"
println "   Clone 2:  fill=${clone2.getFill()}, cx=${clone2.getCx()}"
println "   Clone 3:  fill=${clone3.getFill()}, cx=${clone3.getCx()}"

// 7. NULL-SAFE ACCESSORS
println "\n7. Quick Wins: Null-Safe Accessors"
def testRect = svg.addRect().x(200).y(300).width(50).height(30)
def fillColor = testRect.getAttributeOrDefault('fill', 'black')
def strokeColor = testRect.getAttributeOrDefault('stroke', 'none')
def opacity = testRect.getAttributeOrDefault('opacity', '1.0')

println "   Rectangle with no fill/stroke set:"
println "   fill=${fillColor} (default: black)"
println "   stroke=${strokeColor} (default: none)"
println "   opacity=${opacity} (default: 1.0)"
println "   No NullPointerException!"

// 8. VALIDATION
println "\n8. Validation Report"
def report = svg.validate()
println "   Valid: ${report.isValid()}"
println "   Errors: ${report.getErrors().size()}"
println "   Warnings: ${report.getWarnings().size()}"
println "   Info: ${report.getInfo().size()}"

if (!report.isValid()) {
    report.getErrors().each { println "   ERROR: ${it.message}" }
}

// Output SVG
def output = new File('phase4-demo.svg')
output.text = SvgWriter.toXmlPretty(svg)
println "\n=== SVG Generated ==="
println "File: ${output.absolutePath}"
println "Size: ${output.length()} bytes"
println "Precision: ${NumberFormatter.getDefaultPrecision()} decimals"

println "\n✓ All Phase 4 features demonstrated successfully!"

NumberFormatter.resetPrecision()
