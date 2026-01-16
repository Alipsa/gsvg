package se.alipsa.groovy.svg.validation.rules

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.SvgElement
import se.alipsa.groovy.svg.validation.ValidationIssue
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.ValidationRule

/**
 * Validates that elements are nested according to SVG specification rules.
 * <p>
 * SVG has specific rules about which elements can contain which children.
 * For example:
 * <ul>
 *   <li>defs should only contain definition elements (gradients, patterns, etc.)</li>
 *   <li>text can only contain tspan, textPath, etc.</li>
 *   <li>linearGradient/radialGradient can only contain stop elements</li>
 * </ul>
 * <p>
 * This rule implements a subset of the most common nesting constraints.
 */
@CompileStatic
class ElementNestingRule implements ValidationRule {
    private static final String RULE_ID = "ELEMENT_NESTING"

    // Elements that can only have specific children
    private static final Map<String, List<String>> ALLOWED_CHILDREN = [
        'linearGradient': ['stop', 'animate', 'animateTransform', 'set'],
        'radialGradient': ['stop', 'animate', 'animateTransform', 'set'],
        'text': ['tspan', 'textPath', 'animate', 'set', 'animateTransform'],
        'textPath': ['tspan', 'animate', 'set'],
        'clipPath': ['rect', 'circle', 'ellipse', 'line', 'polyline', 'polygon', 'path', 'text', 'use'],
        'marker': ['rect', 'circle', 'ellipse', 'line', 'polyline', 'polygon', 'path', 'text', 'use', 'g'],
    ]

    // Elements that cannot have children
    private static final List<String> NO_CHILDREN_ELEMENTS = [
        'circle', 'ellipse', 'line', 'rect', 'path', 'polyline', 'polygon',
        'image', 'stop', 'animate', 'animateMotion', 'animateTransform', 'set'
    ]

    @Override
    String getRuleId() {
        RULE_ID
    }

    @Override
    String getDescription() {
        "Validates that elements are nested according to SVG specification"
    }

    @Override
    void validate(SvgElement element, ValidationReport report) {
        if (!element) {
            return
        }

        String elementName = element.name

        // Check if element shouldn't have children
        if (NO_CHILDREN_ELEMENTS.contains(elementName)) {
            List<SvgElement> childElements = element.children.findAll { it instanceof SvgElement } as List<SvgElement>
            if (!childElements.isEmpty()) {
                report.addIssue(ValidationIssue.warning(
                    "Element '${elementName}' should not have child elements, found ${childElements.size()} children",
                    getElementPath(element),
                    element,
                    RULE_ID
                ))
            }
        }

        // Check if element has restricted child types
        List<String> allowedChildren = ALLOWED_CHILDREN[elementName]
        if (allowedChildren) {
            element.children.each { child ->
                if (child instanceof SvgElement) {
                    SvgElement childElement = child as SvgElement
                    String childName = childElement.name

                    if (!allowedChildren.contains(childName)) {
                        report.addIssue(ValidationIssue.warning(
                            "Element '${childName}' is not allowed as a child of '${elementName}'",
                            getElementPath(childElement),
                            childElement,
                            RULE_ID
                        ))
                    }
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
