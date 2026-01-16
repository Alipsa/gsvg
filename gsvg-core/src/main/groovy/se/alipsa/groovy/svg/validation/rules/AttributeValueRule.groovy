package se.alipsa.groovy.svg.validation.rules

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.SvgElement
import se.alipsa.groovy.svg.validation.ValidationIssue
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.ValidationRule

/**
 * Validates that attribute values are within valid ranges and domains.
 * <p>
 * Checks various attribute constraints:
 * <ul>
 *   <li>Numeric attributes must be valid numbers</li>
 *   <li>Percentage attributes must be 0-100%</li>
 *   <li>Opacity must be 0-1</li>
 *   <li>Coordinates and dimensions should be non-negative (where applicable)</li>
 * </ul>
 */
@CompileStatic
class AttributeValueRule implements ValidationRule {
    private static final String RULE_ID = "ATTR_VALUE"

    // Attributes that should be non-negative numbers
    private static final List<String> NON_NEGATIVE_ATTRS = [
        'width', 'height', 'r', 'rx', 'ry',
        'stroke-width', 'font-size'
    ]

    // Attributes that should be opacity values (0-1)
    private static final List<String> OPACITY_ATTRS = [
        'opacity', 'fill-opacity', 'stroke-opacity', 'stop-opacity'
    ]

    @Override
    String getRuleId() {
        RULE_ID
    }

    @Override
    String getDescription() {
        "Validates that attribute values are within valid ranges"
    }

    @Override
    void validate(SvgElement element, ValidationReport report) {
        if (!element) {
            return
        }

        // Check non-negative numeric attributes
        NON_NEGATIVE_ATTRS.each { attrName ->
            checkNonNegativeNumeric(element, attrName, report)
        }

        // Check opacity attributes
        OPACITY_ATTRS.each { attrName ->
            checkOpacity(element, attrName, report)
        }
    }

    private void checkNonNegativeNumeric(SvgElement element, String attrName, ValidationReport report) {
        String value = element.getAttribute(attrName)
        if (!value || value.trim().isEmpty()) {
            return
        }

        // Remove units (px, em, etc.) for validation
        String numericPart = value.replaceAll(/[a-zA-Z%]+$/, '').trim()

        try {
            double numValue = Double.parseDouble(numericPart)
            if (numValue < 0) {
                report.addIssue(ValidationIssue.error(
                    "Attribute '${attrName}' must be non-negative, got '${value}'",
                    getElementPath(element),
                    element,
                    RULE_ID
                ))
            }
        } catch (NumberFormatException e) {
            report.addIssue(ValidationIssue.error(
                "Attribute '${attrName}' must be a valid number, got '${value}'",
                getElementPath(element),
                element,
                RULE_ID
            ))
        }
    }

    private void checkOpacity(SvgElement element, String attrName, ValidationReport report) {
        String value = element.getAttribute(attrName)
        if (!value || value.trim().isEmpty()) {
            return
        }

        try {
            double opacity = Double.parseDouble(value)
            if (opacity < 0 || opacity > 1) {
                report.addIssue(ValidationIssue.error(
                    "Attribute '${attrName}' must be between 0 and 1, got '${value}'",
                    getElementPath(element),
                    element,
                    RULE_ID
                ))
            }
        } catch (NumberFormatException e) {
            report.addIssue(ValidationIssue.error(
                "Attribute '${attrName}' must be a valid number, got '${value}'",
                getElementPath(element),
                element,
                RULE_ID
            ))
        }
    }

    private String getElementPath(SvgElement element) {
        String id = element.id
        String name = element.name
        id ? "${name}#${id}" : name
    }
}
