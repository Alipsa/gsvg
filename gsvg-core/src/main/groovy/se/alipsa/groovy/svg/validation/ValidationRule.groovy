package se.alipsa.groovy.svg.validation

import se.alipsa.groovy.svg.SvgElement

/**
 * Interface for SVG validation rules.
 * <p>
 * Each rule implements a specific validation check (e.g., required attributes,
 * valid nesting, duplicate IDs). Rules are executed by {@link ValidationEngine}
 * and report issues to a {@link ValidationReport}.
 * <p>
 * Example implementation:
 * <pre>
 * class MyCustomRule implements ValidationRule {
 *     String getRuleId() { "MY_CUSTOM_RULE" }
 *     String getDescription() { "Checks my custom constraint" }
 *
 *     void validate(SvgElement element, ValidationReport report) {
 *         if (element.getAttribute("myAttr") == null) {
 *             report.addIssue(ValidationIssue.error(
 *                 "Custom validation failed",
 *                 element.name,
 *                 element,
 *                 ruleId
 *             ))
 *         }
 *     }
 * }
 * </pre>
 */
interface ValidationRule {
    /**
     * Returns the unique identifier for this rule.
     * Should be a stable, descriptive ID (e.g., "REQUIRED_ATTR", "DUPLICATE_ID").
     *
     * @return rule identifier
     */
    String getRuleId()

    /**
     * Returns a human-readable description of what this rule validates.
     *
     * @return rule description
     */
    String getDescription()

    /**
     * Validates an SVG element and reports any issues found.
     * <p>
     * Implementations should:
     * <ul>
     *   <li>Check the element against the rule's constraints</li>
     *   <li>Add issues to the report using {@link ValidationReport#addIssue}</li>
     *   <li>Not throw exceptions (report issues instead)</li>
     *   <li>Handle null or invalid input gracefully</li>
     * </ul>
     *
     * @param element the element to validate (may be null)
     * @param report the report to add issues to
     */
    void validate(SvgElement element, ValidationReport report)
}
