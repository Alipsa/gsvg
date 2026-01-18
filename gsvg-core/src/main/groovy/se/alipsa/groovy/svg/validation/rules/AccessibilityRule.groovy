package se.alipsa.groovy.svg.validation.rules

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.A
import se.alipsa.groovy.svg.ElementContainer
import se.alipsa.groovy.svg.Desc
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElement
import se.alipsa.groovy.svg.Title
import se.alipsa.groovy.svg.validation.ValidationIssue
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.ValidationRule

/**
 * Validates accessibility attributes and best practices for SVG elements.
 * <p>
 * This rule checks for common accessibility issues to ensure SVG graphics
 * work well with assistive technologies like screen readers.
 * <p>
 * Validation checks:
 * <ul>
 *   <li>Root SVG should have role or accessible name (WARNING)</li>
 *   <li>SVG with role='img' needs a label (WARNING)</li>
 *   <li>Interactive elements should have accessible labels (WARNING)</li>
 *   <li>aria-labelledby/aria-describedby references must exist (ERROR)</li>
 *   <li>Decorative elements are valid without labels</li>
 * </ul>
 * <p>
 * This rule only validates when applied to the root SVG element for efficiency.
 *
 * @since 0.9.0
 */
@CompileStatic
class AccessibilityRule implements ValidationRule {
    private static final String RULE_ID = "ACCESSIBILITY_RULE"

    @Override
    String getRuleId() {
        RULE_ID
    }

    @Override
    String getDescription() {
        "Validates accessibility attributes and best practices for SVG graphics"
    }

    @Override
    void validate(SvgElement element, ValidationReport report) {
        if (!element) {
            return
        }

        // Only run at root level to avoid redundant checking
        if (!(element instanceof Svg)) {
            return
        }

        Svg svg = (Svg) element

        // Check root SVG accessibility
        validateRootSvg(svg, report)

        // Collect all IDs for reference validation
        java.util.Set<String> allIds = collectAllIds(svg)

        // Validate all elements recursively
        validateElement(svg, report, "/svg", allIds)
    }

    private void validateRootSvg(Svg svg, ValidationReport report) {
        String role = svg.getRole()
        String ariaLabel = svg.getAriaLabel()
        String ariaLabelledBy = svg.getAriaLabelledBy()

        boolean hasTitle = svg.getTitle() != null

        // Root SVG should have some form of accessible name
        if (!role && !ariaLabel && !ariaLabelledBy && !hasTitle) {
            report.addIssue(ValidationIssue.warning(
                "Root <svg> should have an accessible name. Add role='img' with aria-label, or add a <title> element",
                "/svg",
                svg,
                RULE_ID
            ))
        }

        // If it has role='img', it should have a label
        if (role == 'img' && !ariaLabel && !ariaLabelledBy && !hasTitle) {
            report.addIssue(ValidationIssue.warning(
                "SVG with role='img' should have an accessible label (aria-label, aria-labelledby, or <title>)",
                "/svg",
                svg,
                RULE_ID
            ))
        }
    }

    private void validateElement(SvgElement element, ValidationReport report, String path, java.util.Set<String> allIds) {
        String elementName = element.getName()
        String elementPath = buildElementPath(path, element)

        // Validate aria-labelledby references
        String labelledBy = element.getAriaLabelledBy()
        if (labelledBy) {
            validateIdReferences(element, labelledBy, 'aria-labelledby', report, elementPath, allIds)
        }

        // Validate aria-describedby references
        String describedBy = element.getAriaDescribedBy()
        if (describedBy) {
            validateIdReferences(element, describedBy, 'aria-describedby', report, elementPath, allIds)
        }

        // Check for interactive elements without labels
        if (isInteractive(element) && !hasAccessibleName(element)) {
            report.addIssue(ValidationIssue.warning(
                "Interactive element <${elementName}> should have an accessible name (aria-label, <title>, or aria-labelledby)",
                elementPath,
                element,
                RULE_ID
            ))
        }

        // Recursively validate children
        if (element instanceof ElementContainer) {
            ElementContainer container = (ElementContainer) element
            container.children.each { child ->
                if (child instanceof SvgElement) {
                    validateElement((SvgElement) child, report, elementPath, allIds)
                }
            }
        }
    }

    private void validateIdReferences(SvgElement element, String ids, String attrName,
                                     ValidationReport report, String path, java.util.Set<String> allIds) {
        // Check each referenced ID exists
        ids.split(/\s+/).each { refId ->
            if (refId && !allIds.contains(refId)) {
                report.addIssue(ValidationIssue.error(
                    "Attribute ${attrName}='${ids}' references non-existent ID '${refId}'",
                    path,
                    element,
                    RULE_ID
                ))
            }
        }
    }

    private java.util.Set<String> collectAllIds(SvgElement element) {
        java.util.Set<String> ids = new java.util.HashSet<>()
        collectIds(element, ids)
        ids
    }

    private void collectIds(SvgElement element, java.util.Set<String> ids) {
        String id = element.getId()
        if (id) {
            ids.add(id)
        }

        Title title = element.getTitle()
        if (title) {
            String titleId = title.getId()
            if (titleId) {
                ids.add(titleId)
            }
        }
        Desc desc = element.getDesc()
        if (desc) {
            String descId = desc.getId()
            if (descId) {
                ids.add(descId)
            }
        }

        if (element instanceof ElementContainer) {
            ElementContainer container = (ElementContainer) element
            container.children.each { child ->
                if (child instanceof SvgElement) {
                    collectIds((SvgElement) child, ids)
                }
            }
        }
    }

    private boolean isInteractive(SvgElement element) {
        // Interactive elements: <a>, elements with onclick/href, etc.
        if (element instanceof A) {
            return true
        }

        // Check for event handlers
        String onclick = element.getAttribute('onclick')
        String href = element.getAttribute('href')

        onclick || href
    }

    private boolean hasAccessibleName(SvgElement element) {
        // Check various ways to provide accessible name
        String ariaLabel = element.getAriaLabel()
        String ariaLabelledBy = element.getAriaLabelledBy()

        boolean hasTitle = element.getTitle() != null

        ariaLabel || ariaLabelledBy || hasTitle
    }

    private String buildElementPath(String parentPath, SvgElement element) {
        String path = "${parentPath}/${element.getName()}"
        String id = element.getId()
        if (id) {
            path += "[@id='${id}']"
        }
        path
    }

    private String getElementPath(SvgElement element) {
        if (!element.parent) {
            return "/${element.getName()}"
        }

        String parentPath = getElementPath(element.parent)
        buildElementPath(parentPath, element)
    }
}
