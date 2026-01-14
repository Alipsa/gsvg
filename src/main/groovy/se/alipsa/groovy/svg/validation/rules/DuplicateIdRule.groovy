package se.alipsa.groovy.svg.validation.rules

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElement
import se.alipsa.groovy.svg.validation.ValidationIssue
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.ValidationRule

/**
 * Validates that all element IDs within an SVG document are unique.
 * <p>
 * SVG requires that id attributes be unique within a document, as they're used
 * for referencing elements (href, clip-path, mask, etc.). Duplicate IDs can
 * cause unexpected behavior where references point to the wrong element.
 * <p>
 * This rule only validates when applied to the root SVG element, collecting
 * all IDs from the entire document tree.
 */
@CompileStatic
class DuplicateIdRule implements ValidationRule {
    private static final String RULE_ID = "DUPLICATE_ID"

    @Override
    String getRuleId() {
        RULE_ID
    }

    @Override
    String getDescription() {
        "Validates that all element IDs are unique within the SVG document"
    }

    @Override
    void validate(SvgElement element, ValidationReport report) {
        if (!element) {
            return
        }

        // Only run this check at the root level (Svg element)
        // to avoid duplicate checking
        if (!(element instanceof Svg)) {
            return
        }

        Map<String, List<SvgElement>> idMap = [:]
        collectIds(element, idMap)

        // Report duplicates
        idMap.each { id, elements ->
            if (elements.size() > 1) {
                report.addIssue(ValidationIssue.error(
                    "Duplicate ID '${id}' found on ${elements.size()} elements",
                    getElementPath(elements[0]),
                    elements[0],
                    RULE_ID
                ))

                // Report subsequent duplicates
                elements.drop(1).each { duplicate ->
                    report.addIssue(ValidationIssue.error(
                        "Duplicate ID '${id}' (also defined elsewhere)",
                        getElementPath(duplicate),
                        duplicate,
                        RULE_ID
                    ))
                }
            }
        }
    }

    private void collectIds(SvgElement element, Map<String, List<SvgElement>> idMap) {
        String id = element.id
        if (id) {
            idMap.computeIfAbsent(id, { [] }).add(element)
        }

        // Recursively collect from children
        element.children.each { child ->
            if (child instanceof SvgElement) {
                collectIds(child as SvgElement, idMap)
            }
        }
    }

    private String getElementPath(SvgElement element) {
        String id = element.id
        String name = element.name
        id ? "${name}#${id}" : name
    }
}
