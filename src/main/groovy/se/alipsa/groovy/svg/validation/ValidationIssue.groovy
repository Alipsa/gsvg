package se.alipsa.groovy.svg.validation

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.SvgElement

/**
 * Immutable validation issue describing a problem found during SVG validation.
 * <p>
 * Each issue includes:
 * <ul>
 *   <li>Severity level (ERROR, WARNING, INFO)</li>
 *   <li>Descriptive message</li>
 *   <li>Optional element path for locating the issue</li>
 *   <li>Optional reference to the problematic element</li>
 *   <li>Rule ID that detected the issue</li>
 * </ul>
 * <p>
 * Use the static factory methods to create instances:
 * <pre>
 * ValidationIssue.error("Missing required attribute 'cx'", "circle", circleElement, "REQUIRED_ATTR")
 * ValidationIssue.warning("Duplicate ID found", "/svg/rect[2]", rectElement, "DUPLICATE_ID")
 * ValidationIssue.info("Consider using viewBox for scalability", "/svg", svgElement, "VIEWBOX_BEST_PRACTICE")
 * </pre>
 */
@CompileStatic
class ValidationIssue {
    /** Severity level of this issue */
    final ValidationLevel level

    /** Human-readable description of the problem */
    final String message

    /** Optional path to the element (e.g., "/svg/rect[2]" or "circle") */
    final String elementPath

    /** Optional reference to the problematic element */
    final SvgElement element

    /** ID of the validation rule that detected this issue */
    final String ruleId

    /**
     * Creates a validation issue.
     *
     * @param level severity level
     * @param message description of the problem
     * @param elementPath optional path to the element
     * @param element optional reference to the element
     * @param ruleId ID of the rule that detected this issue
     */
    ValidationIssue(ValidationLevel level, String message, String elementPath = null, SvgElement element = null, String ruleId = null) {
        this.level = level
        this.message = message
        this.elementPath = elementPath
        this.element = element
        this.ruleId = ruleId
    }

    /**
     * Creates an ERROR level validation issue.
     *
     * @param message description of the problem
     * @param elementPath optional path to the element
     * @param element optional reference to the element
     * @param ruleId ID of the rule that detected this issue
     * @return new validation issue
     */
    static ValidationIssue error(String message, String elementPath = null, SvgElement element = null, String ruleId = null) {
        new ValidationIssue(ValidationLevel.ERROR, message, elementPath, element, ruleId)
    }

    /**
     * Creates a WARNING level validation issue.
     *
     * @param message description of the problem
     * @param elementPath optional path to the element
     * @param element optional reference to the element
     * @param ruleId ID of the rule that detected this issue
     * @return new validation issue
     */
    static ValidationIssue warning(String message, String elementPath = null, SvgElement element = null, String ruleId = null) {
        new ValidationIssue(ValidationLevel.WARNING, message, elementPath, element, ruleId)
    }

    /**
     * Creates an INFO level validation issue.
     *
     * @param message description of the problem
     * @param elementPath optional path to the element
     * @param element optional reference to the element
     * @param ruleId ID of the rule that detected this issue
     * @return new validation issue
     */
    static ValidationIssue info(String message, String elementPath = null, SvgElement element = null, String ruleId = null) {
        new ValidationIssue(ValidationLevel.INFO, message, elementPath, element, ruleId)
    }

    @Override
    String toString() {
        StringBuilder sb = new StringBuilder()
        sb.append(level.name())
        if (elementPath) {
            sb.append(" [").append(elementPath).append("]")
        }
        sb.append(": ").append(message)
        if (ruleId) {
            sb.append(" (").append(ruleId).append(")")
        }
        sb.toString()
    }
}
