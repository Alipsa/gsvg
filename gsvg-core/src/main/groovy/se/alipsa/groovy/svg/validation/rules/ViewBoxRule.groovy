package se.alipsa.groovy.svg.validation.rules

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElement
import se.alipsa.groovy.svg.validation.ValidationIssue
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.ValidationRule

/**
 * Validates viewBox attribute on SVG root elements.
 * <p>
 * Checks that:
 * <ul>
 *   <li>viewBox has valid format (4 numbers)</li>
 *   <li>Width and height are positive</li>
 *   <li>Provides informational message if viewBox is missing (best practice)</li>
 * </ul>
 */
@CompileStatic
class ViewBoxRule implements ValidationRule {
    private static final String RULE_ID = "VIEWBOX_RULE"

    @Override
    String getRuleId() {
        RULE_ID
    }

    @Override
    String getDescription() {
        "Validates viewBox attribute format and dimensions"
    }

    @Override
    void validate(SvgElement element, ValidationReport report) {
        if (!element || !(element instanceof Svg)) {
            return
        }

        Svg svg = element as Svg
        String viewBox = svg.getAttribute('viewBox')

        if (!viewBox || viewBox.trim().isEmpty()) {
            // Info message: viewBox is optional but recommended
            report.addIssue(ValidationIssue.info(
                "Consider adding viewBox attribute for better scalability",
                "svg",
                svg,
                RULE_ID
            ))
            return
        }

        // Parse viewBox: "minX minY width height"
        String[] parts = viewBox.trim().split(/\s+/)
        if (parts.length != 4) {
            report.addIssue(ValidationIssue.error(
                "viewBox must have exactly 4 values (minX minY width height), found ${parts.length}",
                "svg",
                svg,
                RULE_ID
            ))
            return
        }

        try {
            double minX = Double.parseDouble(parts[0])
            double minY = Double.parseDouble(parts[1])
            double width = Double.parseDouble(parts[2])
            double height = Double.parseDouble(parts[3])

            if (width <= 0) {
                report.addIssue(ValidationIssue.error(
                    "viewBox width must be positive, got ${width}",
                    "svg",
                    svg,
                    RULE_ID
                ))
            }

            if (height <= 0) {
                report.addIssue(ValidationIssue.error(
                    "viewBox height must be positive, got ${height}",
                    "svg",
                    svg,
                    RULE_ID
                ))
            }
        } catch (NumberFormatException e) {
            report.addIssue(ValidationIssue.error(
                "viewBox values must be numbers: ${viewBox}",
                "svg",
                svg,
                RULE_ID
            ))
        }
    }
}
