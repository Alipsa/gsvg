package se.alipsa.groovy.svg.validation

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElement
import se.alipsa.groovy.svg.validation.rules.*

/**
 * Orchestrates SVG validation by applying a set of {@link ValidationRule}s.
 * <p>
 * The engine maintains a collection of validation rules and applies them to
 * SVG elements, collecting issues in a {@link ValidationReport}.
 * <p>
 * By default, the engine is configured with a set of core validation rules.
 * You can customize the rules by adding or removing them:
 * <pre>
 * ValidationEngine engine = ValidationEngine.createDefault()
 * engine.removeRule("VIEWBOX_RULE")  // Disable viewBox validation
 * engine.addRule(new MyCustomRule())  // Add custom rule
 *
 * ValidationReport report = engine.validate(svg)
 * </pre>
 */
@CompileStatic
class ValidationEngine {
    private final Map<String, ValidationRule> rules = [:]

    /**
     * Creates a new validation engine with no rules.
     */
    ValidationEngine() {
    }

    /**
     * Creates a validation engine configured with default core rules.
     * <p>
     * Default rules include:
     * <ul>
     *   <li>RequiredAttributeRule - Validates required attributes per element type</li>
     *   <li>AttributeValueRule - Validates attribute value ranges and domains</li>
     *   <li>ElementNestingRule - Validates parent-child relationships</li>
     *   <li>ViewBoxRule - Validates viewBox dimensions</li>
     *   <li>HrefRule - Validates href references exist</li>
     *   <li>DuplicateIdRule - Checks for duplicate IDs</li>
     * </ul>
     *
     * @return engine with default rules
     */
    static ValidationEngine createDefault() {
        ValidationEngine engine = new ValidationEngine()
        engine.addRule(new RequiredAttributeRule())
        engine.addRule(new AttributeValueRule())
        engine.addRule(new ElementNestingRule())
        engine.addRule(new ViewBoxRule())
        engine.addRule(new HrefRule())
        engine.addRule(new DuplicateIdRule())
        engine
    }

    /**
     * Adds a validation rule to this engine.
     * If a rule with the same ID already exists, it will be replaced.
     *
     * @param rule the rule to add
     * @return this engine for method chaining
     */
    ValidationEngine addRule(ValidationRule rule) {
        if (rule) {
            rules[rule.ruleId] = rule
        }
        this
    }

    /**
     * Removes a validation rule by its ID.
     *
     * @param ruleId the ID of the rule to remove
     * @return this engine for method chaining
     */
    ValidationEngine removeRule(String ruleId) {
        rules.remove(ruleId)
        this
    }

    /**
     * Returns all currently registered rules.
     *
     * @return unmodifiable collection of rules
     */
    Collection<ValidationRule> getRules() {
        Collections.unmodifiableCollection(rules.values())
    }

    /**
     * Gets a specific rule by its ID.
     *
     * @param ruleId the rule ID
     * @return the rule, or null if not found
     */
    ValidationRule getRule(String ruleId) {
        rules[ruleId]
    }

    /**
     * Validates an SVG element and all its descendants.
     * <p>
     * Applies all registered rules to the element and recursively to its children.
     *
     * @param element the element to validate
     * @return validation report with all issues found
     */
    ValidationReport validate(SvgElement element) {
        ValidationReport report = new ValidationReport()
        if (element) {
            validateRecursive(element, report)
        }
        report
    }

    /**
     * Validates an SVG document (root element and all descendants).
     *
     * @param svg the SVG document to validate
     * @return validation report with all issues found
     */
    ValidationReport validate(Svg svg) {
        validate(svg as SvgElement)
    }

    private void validateRecursive(SvgElement element, ValidationReport report) {
        // Apply all rules to this element
        rules.values().each { rule ->
            try {
                rule.validate(element, report)
            } catch (Exception e) {
                // Don't let one rule's exception break validation
                report.addIssue(ValidationIssue.error(
                    "Validation rule '${rule.ruleId}' threw exception: ${e.message}",
                    element.name,
                    element,
                    rule.ruleId
                ))
            }
        }

        // Recursively validate children
        element.children.each { child ->
            if (child instanceof SvgElement) {
                validateRecursive(child as SvgElement, report)
            }
        }
    }
}
