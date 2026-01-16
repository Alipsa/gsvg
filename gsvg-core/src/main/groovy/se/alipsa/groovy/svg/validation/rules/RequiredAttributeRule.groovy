package se.alipsa.groovy.svg.validation.rules

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.SvgElement
import se.alipsa.groovy.svg.validation.ValidationIssue
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.ValidationRule

/**
 * Validates that elements have their required attributes according to the SVG specification.
 * <p>
 * Different SVG elements have different required attributes. For example:
 * <ul>
 *   <li>circle requires: cx, cy, r</li>
 *   <li>rect requires: (none strictly required, but width/height recommended)</li>
 *   <li>line requires: x1, y1, x2, y2</li>
 *   <li>use requires: href or xlink:href</li>
 * </ul>
 * <p>
 * This rule uses a permissive approach - only truly required attributes generate errors.
 * Missing recommended attributes generate warnings.
 */
@CompileStatic
class RequiredAttributeRule implements ValidationRule {
    private static final String RULE_ID = "REQUIRED_ATTR"

    // Required attributes per element type
    private static final Map<String, List<String>> REQUIRED_ATTRS = [
        'circle': ['cx', 'cy', 'r'],
        'ellipse': ['cx', 'cy', 'rx', 'ry'],
        'line': ['x1', 'y1', 'x2', 'y2'],
        'image': ['href'],  // or xlink:href
        'use': ['href'],    // or xlink:href
    ]

    // Recommended attributes (warnings, not errors)
    private static final Map<String, List<String>> RECOMMENDED_ATTRS = [
        'rect': ['width', 'height'],
        'svg': ['width', 'height'],
    ]

    @Override
    String getRuleId() {
        RULE_ID
    }

    @Override
    String getDescription() {
        "Validates that elements have required attributes"
    }

    @Override
    void validate(SvgElement element, ValidationReport report) {
        if (!element) {
            return
        }

        String elementName = element.name

        // Check required attributes
        List<String> requiredAttrs = REQUIRED_ATTRS[elementName]
        if (requiredAttrs) {
            requiredAttrs.each { attrName ->
                String value = element.getAttribute(attrName)

                // Special handling for href (can be href or xlink:href)
                if (attrName == 'href') {
                    String xlinkHref = null
                    try {
                        xlinkHref = element.getAttribute('xlink:href')
                    } catch (IllegalArgumentException e) {
                        // xlink namespace not bound, ignore
                    }
                    if (!value && !xlinkHref) {
                        report.addIssue(ValidationIssue.error(
                            "Missing required attribute '${attrName}' (or 'xlink:href')",
                            getElementPath(element),
                            element,
                            RULE_ID
                        ))
                    }
                } else if (!value || value.trim().isEmpty()) {
                    report.addIssue(ValidationIssue.error(
                        "Missing required attribute '${attrName}'",
                        getElementPath(element),
                        element,
                        RULE_ID
                    ))
                }
            }
        }

        // Check recommended attributes
        List<String> recommendedAttrs = RECOMMENDED_ATTRS[elementName]
        if (recommendedAttrs) {
            recommendedAttrs.each { attrName ->
                String value = element.getAttribute(attrName)
                if (!value || value.trim().isEmpty()) {
                    report.addIssue(ValidationIssue.warning(
                        "Missing recommended attribute '${attrName}'",
                        getElementPath(element),
                        element,
                        RULE_ID
                    ))
                }
            }
        }
    }

    private String getElementPath(SvgElement element) {
        String id = element.id
        String name = element.name
        id ? "${name}#${id}" : name
    }
}
