package se.alipsa.groovy.svg.validation

import groovy.transform.CompileStatic

/**
 * Severity level for validation issues.
 * <p>
 * Used to categorize validation problems by their impact:
 * <ul>
 *   <li><b>ERROR</b> - Critical issues that prevent valid SVG rendering</li>
 *   <li><b>WARNING</b> - Potential problems that may cause unexpected behavior</li>
 *   <li><b>INFO</b> - Informational messages about best practices</li>
 * </ul>
 */
@CompileStatic
enum ValidationLevel {
    /** Critical issues that prevent valid SVG rendering */
    ERROR,

    /** Potential problems that may cause unexpected behavior */
    WARNING,

    /** Informational messages about best practices */
    INFO
}
