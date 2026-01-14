package se.alipsa.groovy.svg.validation

import groovy.transform.CompileStatic

/**
 * Aggregates validation issues found during SVG validation.
 * <p>
 * A mutable container that collects {@link ValidationIssue}s and provides convenient
 * methods to query and filter them by severity level.
 * <p>
 * Example usage:
 * <pre>
 * ValidationReport report = new ValidationReport()
 * report.addIssue(ValidationIssue.error("Missing required attribute", ...))
 * report.addIssue(ValidationIssue.warning("Duplicate ID", ...))
 *
 * if (report.hasErrors()) {
 *     println "Validation failed with ${report.errorCount} errors"
 *     report.errors.each { println it }
 * }
 * </pre>
 */
@CompileStatic
class ValidationReport {
    private final List<ValidationIssue> issues = []

    /**
     * Adds a validation issue to this report.
     *
     * @param issue the issue to add
     * @return this report for method chaining
     */
    ValidationReport addIssue(ValidationIssue issue) {
        if (issue) {
            issues.add(issue)
        }
        this
    }

    /**
     * Returns all validation issues.
     *
     * @return unmodifiable list of all issues
     */
    List<ValidationIssue> getAllIssues() {
        Collections.unmodifiableList(issues)
    }

    /**
     * Returns only ERROR level issues.
     *
     * @return unmodifiable list of errors
     */
    List<ValidationIssue> getErrors() {
        Collections.unmodifiableList(issues.findAll { it.level == ValidationLevel.ERROR })
    }

    /**
     * Returns only WARNING level issues.
     *
     * @return unmodifiable list of warnings
     */
    List<ValidationIssue> getWarnings() {
        Collections.unmodifiableList(issues.findAll { it.level == ValidationLevel.WARNING })
    }

    /**
     * Returns only INFO level issues.
     *
     * @return unmodifiable list of info messages
     */
    List<ValidationIssue> getInfo() {
        Collections.unmodifiableList(issues.findAll { it.level == ValidationLevel.INFO })
    }

    /**
     * Checks if this report contains any ERROR level issues.
     *
     * @return true if there are errors
     */
    boolean hasErrors() {
        issues.any { it.level == ValidationLevel.ERROR }
    }

    /**
     * Checks if this report contains any WARNING level issues.
     *
     * @return true if there are warnings
     */
    boolean hasWarnings() {
        issues.any { it.level == ValidationLevel.WARNING }
    }

    /**
     * Checks if this report contains any issues at all.
     *
     * @return true if there are any issues
     */
    boolean hasIssues() {
        !issues.isEmpty()
    }

    /**
     * Checks if validation passed (no errors).
     * Note: Warnings and info messages don't prevent validation from passing.
     *
     * @return true if there are no errors
     */
    boolean isValid() {
        !hasErrors()
    }

    /**
     * Returns the total number of issues.
     *
     * @return total issue count
     */
    int size() {
        issues.size()
    }

    /**
     * Returns the number of ERROR level issues.
     *
     * @return error count
     */
    int getErrorCount() {
        errors.size()
    }

    /**
     * Returns the number of WARNING level issues.
     *
     * @return warning count
     */
    int getWarningCount() {
        warnings.size()
    }

    /**
     * Returns the number of INFO level issues.
     *
     * @return info count
     */
    int getInfoCount() {
        info.size()
    }

    /**
     * Clears all issues from this report.
     */
    void clear() {
        issues.clear()
    }

    @Override
    String toString() {
        if (issues.isEmpty()) {
            return "Validation Report: No issues found"
        }

        StringBuilder sb = new StringBuilder()
        sb.append("Validation Report: ")
           .append(issues.size()).append(" issue(s)")

        int errors = errorCount
        int warnings = warningCount
        int infos = infoCount

        sb.append(" (")
        if (errors > 0) sb.append(errors).append(" error(s)")
        if (warnings > 0) {
            if (errors > 0) sb.append(", ")
            sb.append(warnings).append(" warning(s)")
        }
        if (infos > 0) {
            if (errors > 0 || warnings > 0) sb.append(", ")
            sb.append(infos).append(" info")
        }
        sb.append(")\n")

        issues.each { issue ->
            sb.append("  - ").append(issue.toString()).append("\n")
        }

        sb.toString()
    }
}
