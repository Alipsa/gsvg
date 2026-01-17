package se.alipsa.groovy.svg.templates

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.AbstractElementContainer
import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElement

/**
 * Template for creating chart legends.
 * <p>
 * This template creates a legend box with color swatches and labels for chart data.
 * It's commonly used with bar charts, pie charts, and line graphs.
 * <p>
 * <h2>Parameters:</h2>
 * <ul>
 *   <li><b>x</b> - X coordinate of legend box (default: 0)</li>
 *   <li><b>y</b> - Y coordinate of legend box (default: 0)</li>
 *   <li><b>items</b> - List of legend items, each with 'color' and 'label' (required)</li>
 *   <li><b>swatchSize</b> - Size of color swatches (default: 15)</li>
 *   <li><b>spacing</b> - Vertical spacing between items (default: 25)</li>
 *   <li><b>fontSize</b> - Font size for labels (default: 14)</li>
 *   <li><b>padding</b> - Padding inside legend box (default: 10)</li>
 *   <li><b>showBorder</b> - Whether to show border around legend (default: true)</li>
 *   <li><b>borderColor</b> - Border color (default: 'black')</li>
 *   <li><b>borderWidth</b> - Border width (default: 1)</li>
 *   <li><b>backgroundColor</b> - Background color (default: 'white')</li>
 *   <li><b>backgroundOpacity</b> - Background opacity 0-1 (default: 0.9)</li>
 * </ul>
 * <p>
 * <h2>Usage Example:</h2>
 * <pre>
 * Svg svg = new Svg(600, 400)
 *
 * // Create chart data
 * def chartData = [
 *     [color: 'red', label: 'Product A'],
 *     [color: 'blue', label: 'Product B'],
 *     [color: 'green', label: 'Product C']
 * ]
 *
 * // Add legend
 * ChartLegend legend = new ChartLegend()
 * legend.apply(svg, [
 *     x: 450,
 *     y: 50,
 *     items: chartData
 * ])
 * </pre>
 *
 * @since 0.9.0
 */
@CompileStatic
class ChartLegend extends Template {

    @Override
    SvgElement apply(AbstractElementContainer parent, Map params) {
        Map merged = mergeParams(params)

        Number x = merged.x as Number
        Number y = merged.y as Number
        List<Map> items = merged.items as List<Map>
        Number swatchSize = merged.swatchSize as Number
        Number spacing = merged.spacing as Number
        Number fontSize = merged.fontSize as Number
        Number padding = merged.padding as Number
        boolean showBorder = merged.showBorder as boolean
        String borderColor = merged.borderColor as String
        Number borderWidth = merged.borderWidth as Number
        String backgroundColor = merged.backgroundColor as String
        Number backgroundOpacity = merged.backgroundOpacity as Number

        // Create group for legend
        G legendGroup = parent.addG()
        legendGroup.id(merged.id as String ?: null)

        // Calculate dimensions
        int maxLabelWidth = 150  // Estimate - could be made configurable
        int legendWidth = (padding.intValue() * 2) + swatchSize.intValue() + 10 + maxLabelWidth
        int legendHeight = (padding.intValue() * 2) + (items.size() * spacing.intValue())

        // Background
        Rect background = legendGroup.addRect()
        background.x(x).y(y)
                  .width(legendWidth)
                  .height(legendHeight)
                  .fill(backgroundColor)
                  .fillOpacity(backgroundOpacity)

        // Border
        if (showBorder) {
            background.stroke(borderColor)
                      .strokeWidth(borderWidth)
        } else {
            background.stroke('none')
        }

        // Add legend items
        items.eachWithIndex { Map item, int index ->
            int itemY = y.intValue() + padding.intValue() + (index * spacing.intValue())
            int itemX = x.intValue() + padding.intValue()

            // Color swatch
            legendGroup.addRect()
                .x(itemX)
                .y(itemY)
                .width(swatchSize)
                .height(swatchSize)
                .fill(item.color as String)
                .stroke('black')
                .strokeWidth(1)

            // Label
            legendGroup.addText(item.label as String)
                .x(itemX + swatchSize.intValue() + 10)
                .y(itemY + (swatchSize.intValue() / 2) + 5)
                .fontSize(fontSize)
                .fill('black')
        }

        return legendGroup
    }

    @Override
    Map getDefaults() {
        return [
            x: 0,
            y: 0,
            items: [],
            swatchSize: 15,
            spacing: 25,
            fontSize: 14,
            padding: 10,
            showBorder: true,
            borderColor: 'black',
            borderWidth: 1,
            backgroundColor: 'white',
            backgroundOpacity: 0.9,
            id: null
        ]
    }
}
