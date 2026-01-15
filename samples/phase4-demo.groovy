#!/usr/bin/env groovy

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.effects.Effects
import se.alipsa.groovy.svg.utils.NumberFormatter

/**
 * Demonstration of all Phase 4 (Polish) features in gsvg v0.9.0:
 * - Numeric Precision Control
 * - Accessibility Helpers
 * - Builder Pattern Enhancements (Effects, Shapes, Templates, DSL)
 * - Quick Wins (cloneWith, factory methods, null-safe accessors)
 */

// Set numeric precision for file size optimization
NumberFormatter.setDefaultPrecision(2)

Svg svg = new Svg(800, 600)
    .viewBox(0, 0, 800, 600)
    .role('img')
    .ariaLabel('Demonstration of gsvg v0.9.0 Phase 4 features')

// Add CSS for styling
def style = svg.addStyle()
style.addRule('.card', [
    fill: '#f0f0f0',
    stroke: '#333',
    'stroke-width': '2',
    rx: '10'
])
style.addRule('.heading', [
    'font-family': 'Arial, sans-serif',
    'font-size': '24',
    'font-weight': 'bold',
    fill: '#333'
])

// 1. NUMERIC PRECISION DEMO
def precisionGroup = svg.addG()
    .transform('translate(20, 30)')
    .ariaLabel('Numeric precision examples')

precisionGroup.addText('1. Numeric Precision')
    .x(0).y(0)
    .addClass('heading')

// Circle with precise coordinates - note the compact output
precisionGroup.addCircle()
    .cx(50.123456789)
    .cy(50.987654321)
    .r(30.555555)
    .fill('steelblue')
    .ariaLabel('Circle with optimized numeric precision')

precisionGroup.addText('coordinates: cx=50.12, cy=50.99, r=30.56')
    .x(100).y(55)
    .fontSize(12)
    .fill('#666')

// 2. ACCESSIBILITY DEMO
def a11yGroup = svg.addG()
    .transform('translate(20, 150)')

a11yGroup.addText('2. Accessibility')
    .x(0).y(0)
    .addClass('heading')

// Interactive button with proper ARIA attributes
def button = a11yGroup.addRect()
    .x(0).y(20)
    .width(120).height(40)
    .fill('green')
    .rx(5)
    .role('button')
    .ariaLabel('Click to activate')

a11yGroup.addText('Click me')
    .x(60).y(45)
    .textAnchor('middle')
    .fill('white')
    .fontSize(16)

// Decorative element (hidden from screen readers)
a11yGroup.addCircle()
    .cx(180).cy(40)
    .r(15)
    .decorative()
    .fill('orange')

// 3. SHAPE FACTORY METHODS
def shapesGroup = svg.addG()
    .transform('translate(300, 30)')

shapesGroup.addText('3. Shape Factories')
    .x(0).y(0)
    .addClass('heading')

// Rounded rectangle using factory
def card = svg.createRoundedRect([
    x: 300,
    y: 50,
    width: 150,
    height: 80,
    rx: 10,
    ry: 10,
    fill: 'lightblue',
    stroke: 'navy',
    'stroke-width': 2
])
card.ariaLabel('Card component')

// Star using factory
def star = svg.createStar([
    cx: 550,
    cy: 90,
    points: 5,
    outerRadius: 30,
    innerRadius: 15,
    fill: 'gold',
    stroke: 'orange',
    'stroke-width': 2
])
star.ariaLabel('Five-pointed star')

// Arrow using factory
def arrow = svg.createArrow([
    x1: 300,
    y1: 160,
    x2: 400,
    y2: 160,
    headSize: 10,
    stroke: 'red',
    'stroke-width': 3,
    fill: 'red'
])

// 4. EFFECT PRESETS
def effectsGroup = svg.addG()
    .transform('translate(20, 250)')

effectsGroup.addText('4. Effect Presets')
    .x(0).y(0)
    .addClass('heading')

// Drop shadow effect
def shadowFilter = Effects.dropShadow(svg, [
    dx: 4,
    dy: 4,
    blur: 6,
    opacity: 0.3
])

effectsGroup.addRect()
    .x(0).y(30)
    .width(100).height(60)
    .fill('coral')
    .filter("url(#${shadowFilter.getId()})")
    .ariaLabel('Rectangle with drop shadow')

// Glow effect
def glowFilter = Effects.glow(svg, [
    color: 'cyan',
    blur: 8,
    opacity: 0.7
])

effectsGroup.addCircle()
    .cx(180).cy(60)
    .r(30)
    .fill('darkblue')
    .filter("url(#${glowFilter.getId()})")
    .ariaLabel('Circle with glow effect')

// 5. DSL CONFIGURATION
def dslGroup = svg.addG()
    .transform('translate(300, 250)')

dslGroup.addText('5. DSL Configuration')
    .x(0).y(0)
    .addClass('heading')

// Use DSL-style configuration
dslGroup.addCircle {
    cx 350
    cy 60
    r 25
    fill 'purple'
    stroke 'magenta'
    strokeWidth 2
    ariaLabel 'Circle configured with DSL'
}

dslGroup.addRect {
    x 420
    y 35
    width 80
    height 50
    fill 'teal'
    rx 5
    ariaLabel 'Rectangle configured with DSL'
}

// 6. QUICK WINS: cloneWith()
def cloneGroup = svg.addG()
    .transform('translate(20, 370)')

cloneGroup.addText('6. Clone with Modifications')
    .x(0).y(0)
    .addClass('heading')

// Original element
def original = cloneGroup.addCircle()
    .cx(50).cy(50)
    .r(20)
    .fill('red')
    .stroke('darkred')
    .strokeWidth(2)

// Clone with modifications
def clone1 = original.cloneWith(cloneGroup, [cx: 100, fill: 'green', stroke: 'darkgreen'])
def clone2 = original.cloneWith(cloneGroup, [cx: 150, fill: 'blue', stroke: 'darkblue'])
def clone3 = original.cloneWith(cloneGroup, [cx: 200, fill: 'orange', stroke: 'darkorange'])

// 7. NULL-SAFE ACCESSORS
def safetyGroup = svg.addG()
    .transform('translate(300, 370)')

safetyGroup.addText('7. Null-Safe Accessors')
    .x(0).y(0)
    .addClass('heading')

def testRect = safetyGroup.addRect()
    .x(300).y(400)
    .width(80).height(40)

// Safe access with defaults
def fillColor = testRect.getAttributeOrDefault('fill', 'black')
def strokeColor = testRect.getAttributeOrDefault('stroke', 'none')

safetyGroup.addText("fill: ${fillColor}, stroke: ${strokeColor}")
    .x(300).y(455)
    .fontSize(11)
    .fill('#666')

// 8. TEMPLATES
def templateGroup = svg.addG()
    .transform('translate(20, 480)')

templateGroup.addText('8. Template System')
    .x(0).y(0)
    .addClass('heading')

// Create a legend using template
def legend = svg.addChartLegend([
    x: 20,
    y: 510,
    items: [
        [label: 'Series 1', color: 'red'],
        [label: 'Series 2', color: 'blue'],
        [label: 'Series 3', color: 'green']
    ],
    orientation: 'horizontal',
    spacing: 20
])

// Validate the SVG for accessibility
def report = svg.validate()
println "\n=== Validation Report ==="
println "Valid: ${report.isValid()}"
println "Errors: ${report.getErrors().size()}"
println "Warnings: ${report.getWarnings().size()}"
println "Info: ${report.getInfo().size()}"

if (!report.isValid()) {
    report.getErrors().each { println "ERROR: ${it.message}" }
}
report.getWarnings().each { println "WARNING: ${it.message}" }

// Output the SVG
def output = new File('phase4-demo.svg')
output.text = svg.toXmlPretty()
println "\n=== Phase 4 Demo SVG Generated ==="
println "File: ${output.absolutePath}"
println "Size: ${output.length()} bytes"
println "Precision: ${NumberFormatter.getDefaultPrecision()} decimals"
println "\n✓ All Phase 4 features demonstrated!"
