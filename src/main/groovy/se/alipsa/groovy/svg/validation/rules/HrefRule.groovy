package se.alipsa.groovy.svg.validation.rules

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElement
import se.alipsa.groovy.svg.validation.ValidationIssue
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.ValidationRule

/**
 * Validates that href/xlink:href references point to existing elements.
 * <p>
 * SVG uses href attributes to reference other elements (for use, image, pattern, etc.).
 * This rule verifies that local references (starting with #) point to elements with
 * matching IDs.
 * <p>
 * External URLs (http://, https://, etc.) are not validated by this rule.
 * <p>
 * This rule only runs at the root level to avoid O(N²) performance issues.
 */
@CompileStatic
class HrefRule implements ValidationRule {
    private static final String RULE_ID = "HREF_RULE"
    private static final List<String> HREF_ATTRIBUTES = ['href', 'xlink:href']
    private static final List<String> REFERENCE_ATTRIBUTES = [
        'clip-path', 'mask', 'fill', 'stroke', 'filter',
        'marker-start', 'marker-mid', 'marker-end'
    ]

    @Override
    String getRuleId() {
        RULE_ID
    }

    @Override
    String getDescription() {
        "Validates that href references point to existing elements"
    }

    @Override
    void validate(SvgElement element, ValidationReport report) {
        if (!element) {
            return
        }

        // Only run this check at the root level (Svg element) for performance
        // Checking every element would be O(N²)
        if (!(element instanceof Svg)) {
            return
        }

        // First pass: collect all IDs in the document
        Set<String> availableIds = new HashSet<>()
        collectIds(element, availableIds)

        // Second pass: validate all references
        validateReferences(element, availableIds, report)
    }

    private void collectIds(SvgElement element, Set<String> ids) {
        String id = element.id
        if (id) {
            ids.add(id)
        }

        // Recursively collect from children
        element.children.each { child ->
            if (child instanceof SvgElement) {
                collectIds(child as SvgElement, ids)
            }
        }
    }

    private void validateReferences(SvgElement element, Set<String> availableIds, ValidationReport report) {
        // Check all href-like attributes
        HREF_ATTRIBUTES.each { attrName ->
            String href = null
            try {
                href = element.getAttribute(attrName)
            } catch (IllegalArgumentException e) {
                // Namespace not bound (e.g., xlink:href without xlink namespace)
                // Return from closure to skip this attribute and continue with next one
                return
            }

            if (href && href.startsWith('#')) {
                String targetId = href.substring(1)
                if (!availableIds.contains(targetId)) {
                    report.addIssue(ValidationIssue.error(
                        "Reference ${attrName}='${href}' points to non-existent element with id='${targetId}'",
                        getElementPath(element),
                        element,
                        RULE_ID
                    ))
                }
            }
        }

        // Check other reference attributes
        REFERENCE_ATTRIBUTES.each { attrName ->
            checkReferenceAttribute(element, attrName, availableIds, report)
        }

        // Recursively check children
        element.children.each { child ->
            if (child instanceof SvgElement) {
                validateReferences(child as SvgElement, availableIds, report)
            }
        }
    }

    private void checkReferenceAttribute(SvgElement element, String attrName, Set<String> availableIds, ValidationReport report) {
        String value = element.getAttribute(attrName)
        if (!value) {
            return
        }

        // Extract ID from url(#id) format
        if (value.startsWith('url(#') && value.endsWith(')')) {
            String targetId = value.substring(5, value.length() - 1)
            if (!availableIds.contains(targetId)) {
                report.addIssue(ValidationIssue.error(
                    "Reference ${attrName}='${value}' points to non-existent element with id='${targetId}'",
                    getElementPath(element),
                    element,
                    RULE_ID
                ))
            }
        }
    }

    private String getElementPath(SvgElement element) {
        String id = element.id
        String name = element.name
        id ? "${name}#${id}" : name
    }
}
