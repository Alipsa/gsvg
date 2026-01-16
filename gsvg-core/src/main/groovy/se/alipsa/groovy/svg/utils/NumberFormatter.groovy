package se.alipsa.groovy.svg.utils

import groovy.transform.CompileStatic

import java.math.RoundingMode

/**
 * Utility for formatting numeric values in SVG attributes with configurable precision.
 * <p>
 * By default, numbers are formatted with up to 3 decimal places, matching industry
 * standards (SVGO, Adobe Illustrator). This reduces file size by 30-50% for
 * coordinate-heavy graphics without affecting visual quality.
 * <p>
 * Features:
 * <ul>
 *   <li>Removes trailing zeros (12.120 → 12.12)</li>
 *   <li>Outputs integers without decimals (50.0 → 50)</li>
 *   <li>Configurable precision globally or per-attribute</li>
 *   <li>Thread-safe configuration</li>
 * </ul>
 * <p>
 * Example usage:
 * <pre>
 * // Default formatting (3 decimals)
 * NumberFormatter.format(12.123456789)  // Returns "12.123"
 * NumberFormatter.format(50.0)          // Returns "50"
 *
 * // Custom precision
 * NumberFormatter.format(12.123456, 5)  // Returns "12.12346"
 *
 * // Global configuration
 * NumberFormatter.setDefaultPrecision(2)
 * NumberFormatter.format(12.123)        // Returns "12.12"
 * </pre>
 *
 * @since 0.9.0
 */
@CompileStatic
class NumberFormatter {

    /**
     * Default maximum decimal places (3 decimals = industry standard).
     */
    private static final int DEFAULT_MAX_DECIMALS = 3

    /**
     * Thread-local storage for configurable precision.
     * Allows different threads to use different precision settings.
     */
    private static final ThreadLocal<Integer> maxPrecision = ThreadLocal.withInitial(() -> DEFAULT_MAX_DECIMALS)

    /**
     * Formats a value as a string with appropriate numeric precision.
     * <p>
     * Non-numeric values are converted to strings unchanged.
     * Null values return null.
     * Numbers are formatted with the specified precision (or default if not provided).
     *
     * @param value the value to format (can be Number, String, or any Object)
     * @param precision optional precision override (number of decimal places), null to use default
     * @return formatted string, or null if value is null
     */
    static String format(Object value, Integer precision = null) {
        if (value == null) {
            return null
        }

        // Pass through non-numeric values unchanged
        if (!(value instanceof Number)) {
            return value.toString()
        }

        // Use provided precision, thread-local, or default
        int decimals = precision != null ? precision : maxPrecision.get()

        double d = ((Number) value).doubleValue()

        // Handle special floating-point values
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            return String.valueOf(d)
        }

        // If it's a whole number, return as integer (no decimal point)
        if (d == Math.floor(d) && !Double.isInfinite(d)) {
            // Handle very large numbers that exceed long range
            if (d > Long.MAX_VALUE || d < Long.MIN_VALUE) {
                return String.valueOf(d)
            }
            return String.valueOf((long) d)
        }

        // Round to specified decimal places using BigDecimal for precision
        BigDecimal bd = new BigDecimal(String.valueOf(d))
        bd = bd.setScale(decimals, RoundingMode.HALF_UP)

        // Remove trailing zeros after decimal point
        bd = bd.stripTrailingZeros()

        // Get plain string (no scientific notation)
        String result = bd.toPlainString()

        return result
    }

    /**
     * Sets the default precision for all subsequent format() calls in this thread.
     * <p>
     * This setting is thread-local and does not affect other threads.
     * The precision must be between 0 and 10 inclusive.
     *
     * @param decimals maximum number of decimal places (0-10)
     * @throws IllegalArgumentException if decimals is negative or greater than 10
     */
    static void setDefaultPrecision(int decimals) {
        if (decimals < 0 || decimals > 10) {
            throw new IllegalArgumentException("Precision must be between 0 and 10, got: ${decimals}")
        }
        maxPrecision.set(decimals)
    }

    /**
     * Gets the current default precision for this thread.
     *
     * @return current maximum decimal places
     */
    static int getDefaultPrecision() {
        maxPrecision.get()
    }

    /**
     * Resets the precision to the default value (3 decimals) for this thread.
     */
    static void resetPrecision() {
        maxPrecision.set(DEFAULT_MAX_DECIMALS)
    }
}
