package se.alipsa.groovy.svg.utils

import groovy.transform.CompileStatic
import groovy.transform.Immutable

/**
 * Represents an SVG viewBox attribute with helper methods for manipulation and conversion.
 * <p>
 * The viewBox attribute defines the coordinate system and aspect ratio for an SVG element.
 * It consists of four values: minX, minY, width, and height.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * // Create a viewBox
 * def vb = ViewBox.of(0, 0, 100, 100)
 *
 * // Parse from string
 * def parsed = ViewBox.parse("0 0 200 200")
 *
 * // Transform it
 * def scaled = vb.scale(2.0)        // 0 0 200 200
 * def translated = vb.translate(10, 10)  // 10 10 100 100
 *
 * // Use with SVG
 * svg.viewBox(vb.toString())
 * // or with convenience method:
 * svg.viewBox(vb)
 * </pre>
 */
@CompileStatic
@Immutable
class ViewBox {

  /**
   * The minimum x coordinate of the viewport
   */
  double minX

  /**
   * The minimum y coordinate of the viewport
   */
  double minY

  /**
   * The width of the viewport
   */
  double width

  /**
   * The height of the viewport
   */
  double height

  // ==================== STATIC FACTORY METHODS ====================

  /**
   * Create a ViewBox with the specified coordinates and dimensions
   * @param minX the minimum x coordinate
   * @param minY the minimum y coordinate
   * @param width the viewport width (must be positive)
   * @param height the viewport height (must be positive)
   * @return a new ViewBox
   * @throws IllegalArgumentException if width or height is not positive
   */
  static ViewBox of(Number minX, Number minY, Number width, Number height) {
    double w = width.doubleValue()
    double h = height.doubleValue()

    if (w <= 0) {
      throw new IllegalArgumentException("ViewBox width must be positive, got: ${w}")
    }
    if (h <= 0) {
      throw new IllegalArgumentException("ViewBox height must be positive, got: ${h}")
    }

    new ViewBox(minX.doubleValue(), minY.doubleValue(), w, h)
  }

  /**
   * Parse a viewBox string (e.g., "0 0 100 100" or "0,0,100,100")
   * @param viewBoxString the viewBox string to parse
   * @return a new ViewBox
   * @throws IllegalArgumentException if the string is invalid
   */
  static ViewBox parse(String viewBoxString) {
    if (viewBoxString == null || viewBoxString.trim().isEmpty()) {
      throw new IllegalArgumentException("ViewBox string cannot be null or empty")
    }

    // Split by whitespace or commas
    String[] parts = viewBoxString.trim().split(/[\s,]+/)

    if (parts.length != 4) {
      throw new IllegalArgumentException("ViewBox string must contain exactly 4 values, got: ${viewBoxString}")
    }

    try {
      double minX = Double.parseDouble(parts[0])
      double minY = Double.parseDouble(parts[1])
      double width = Double.parseDouble(parts[2])
      double height = Double.parseDouble(parts[3])

      return of(minX, minY, width, height)
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid ViewBox string: ${viewBoxString}", e)
    }
  }

  // ==================== TRANSFORMATION METHODS ====================

  /**
   * Scale the viewBox dimensions by a factor.
   * The minX and minY coordinates are preserved.
   * @param factor the scale factor (must be positive)
   * @return a new ViewBox with scaled dimensions
   * @throws IllegalArgumentException if factor is not positive
   */
  ViewBox scale(Number factor) {
    double f = factor.doubleValue()
    if (f <= 0) {
      throw new IllegalArgumentException("Scale factor must be positive, got: ${f}")
    }
    new ViewBox(minX, minY, width * f, height * f)
  }

  /**
   * Scale the viewBox dimensions by separate x and y factors.
   * The minX and minY coordinates are preserved.
   * @param scaleX the x-axis scale factor (must be positive)
   * @param scaleY the y-axis scale factor (must be positive)
   * @return a new ViewBox with scaled dimensions
   * @throws IllegalArgumentException if either factor is not positive
   */
  ViewBox scale(Number scaleX, Number scaleY) {
    double sx = scaleX.doubleValue()
    double sy = scaleY.doubleValue()
    if (sx <= 0 || sy <= 0) {
      throw new IllegalArgumentException("Scale factors must be positive, got: (${sx}, ${sy})")
    }
    new ViewBox(minX, minY, width * sx, height * sy)
  }

  /**
   * Translate (shift) the viewBox by the given offsets.
   * The width and height are preserved.
   * @param dx the x offset
   * @param dy the y offset
   * @return a new ViewBox with translated coordinates
   */
  ViewBox translate(Number dx, Number dy) {
    new ViewBox(minX + dx.doubleValue(), minY + dy.doubleValue(), width, height)
  }

  /**
   * Expand the viewBox by adding margins on all sides.
   * @param margin the margin to add (can be negative to shrink)
   * @return a new ViewBox expanded by the margin
   * @throws IllegalArgumentException if the resulting width or height would be non-positive
   */
  ViewBox expand(Number margin) {
    double m = margin.doubleValue()
    return expand(m, m, m, m)
  }

  /**
   * Expand the viewBox by adding different margins on each side.
   * @param top margin to add to the top (negative minY direction)
   * @param right margin to add to the right (positive width direction)
   * @param bottom margin to add to the bottom (positive height direction)
   * @param left margin to add to the left (negative minX direction)
   * @return a new ViewBox with expanded dimensions
   * @throws IllegalArgumentException if the resulting width or height would be non-positive
   */
  ViewBox expand(Number top, Number right, Number bottom, Number left) {
    double t = top.doubleValue()
    double r = right.doubleValue()
    double b = bottom.doubleValue()
    double l = left.doubleValue()

    double newMinX = minX - l
    double newMinY = minY - t
    double newWidth = width + l + r
    double newHeight = height + t + b

    return of(newMinX, newMinY, newWidth, newHeight)
  }

  /**
   * Center the viewBox at a specific point while maintaining its dimensions.
   * @param centerX the new center x coordinate
   * @param centerY the new center y coordinate
   * @return a new ViewBox centered at the specified point
   */
  ViewBox centerAt(Number centerX, Number centerY) {
    double cx = centerX.doubleValue()
    double cy = centerY.doubleValue()
    double newMinX = cx - (width / 2.0)
    double newMinY = cy - (height / 2.0)
    new ViewBox(newMinX, newMinY, width, height)
  }

  /**
   * Set a specific aspect ratio for the viewBox by adjusting the height.
   * The width is preserved.
   * @param ratio the desired width/height ratio (must be positive)
   * @return a new ViewBox with the specified aspect ratio
   * @throws IllegalArgumentException if ratio is not positive
   */
  ViewBox withAspectRatio(Number ratio) {
    double r = ratio.doubleValue()
    if (r <= 0) {
      throw new IllegalArgumentException("Aspect ratio must be positive, got: ${r}")
    }
    double newHeight = width / r
    new ViewBox(minX, minY, width, newHeight)
  }

  /**
   * Scale and center another viewBox to fit inside this viewBox.
   * <p>
   * When {@code preserveAspectRatio} is true (default), 'other' is uniformly scaled to fit
   * inside this viewBox while maintaining its aspect ratio, then centered.
   * </p>
   * <p>
   * When {@code preserveAspectRatio} is false, 'other' is scaled non-uniformly to exactly
   * fill this viewBox. The returned ViewBox has this viewBox's position and dimensions,
   * representing where 'other' would appear after being stretched/squashed to fill.
   * </p>
   *
   * @param other the viewBox to scale and fit inside this one
   * @param preserveAspectRatio whether to preserve aspect ratio (if false, scales to fill)
   * @return a new ViewBox representing the fitted result
   */
  ViewBox fitToContain(ViewBox other, boolean preserveAspectRatio = true) {
    if (!preserveAspectRatio) {
      // Scale to fill without preserving aspect ratio
      // Result represents 'other' stretched to exactly match this viewBox's dimensions
      return new ViewBox(minX, minY, width, height)
    }

    // Calculate scale factor that fits 'other' inside 'this' while preserving aspect ratio
    Number scaleX = width / other.width
    Number scaleY = height / other.height
    Number scale = Math.min(scaleX.doubleValue(), scaleY.doubleValue())

    Number newWidth = other.width * scale.doubleValue()
    Number newHeight = other.height * scale.doubleValue()

    // Center the fitted viewBox within this viewBox's bounds
    Number newMinX = minX + (width - newWidth.doubleValue()) / 2.0
    Number newMinY = minY + (height - newHeight.doubleValue()) / 2.0

    new ViewBox(newMinX.doubleValue(), newMinY.doubleValue(), newWidth.doubleValue(), newHeight.doubleValue())
  }

  // ==================== QUERY METHODS ====================

  /**
   * Get the center x coordinate of the viewBox
   * @return the center x coordinate
   */
  double getCenterX() {
    minX + (width / 2.0)
  }

  /**
   * Get the center y coordinate of the viewBox
   * @return the center y coordinate
   */
  double getCenterY() {
    minY + (height / 2.0)
  }

  /**
   * Get the maximum x coordinate of the viewBox
   * @return the maximum x coordinate
   */
  double getMaxX() {
    minX + width
  }

  /**
   * Get the maximum y coordinate of the viewBox
   * @return the maximum y coordinate
   */
  double getMaxY() {
    minY + height
  }

  /**
   * Get the aspect ratio (width / height) of the viewBox
   * @return the aspect ratio
   */
  double getAspectRatio() {
    width / height
  }

  /**
   * Check if a point is contained within this viewBox.
   * Uses inclusive bounds on all edges for consistency with SVG rendering behavior.
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true if the point is inside or on the boundary of the viewBox
   */
  boolean contains(Number x, Number y) {
    double px = x.doubleValue()
    double py = y.doubleValue()
    px >= minX && px <= maxX && py >= minY && py <= maxY
  }

  /**
   * Check if another viewBox is entirely contained within this one
   * @param other the other viewBox
   * @return true if the other viewBox is entirely inside this one
   */
  boolean contains(ViewBox other) {
    other.minX >= minX && other.maxX <= maxX &&
    other.minY >= minY && other.maxY <= maxY
  }

  /**
   * Check if this viewBox intersects with another viewBox
   * @param other the other viewBox
   * @return true if the viewBoxes intersect
   */
  boolean intersects(ViewBox other) {
    !(other.maxX < minX || other.minX > maxX ||
      other.maxY < minY || other.minY > maxY)
  }

  // ==================== OUTPUT METHODS ====================

  /**
   * Convert to SVG viewBox attribute string (space-separated values)
   * @return the viewBox string (e.g., "0 0 100 100")
   */
  @Override
  String toString() {
    "${formatNumber(minX)} ${formatNumber(minY)} ${formatNumber(width)} ${formatNumber(height)}"
  }

  /**
   * Format a number nicely - remove unnecessary decimals
   * @param n the number to format
   * @return formatted string
   */
  private static String formatNumber(double n) {
    if (n == (long) n) {
      return String.valueOf((long) n)
    }
    return String.valueOf(n)
  }

  /**
   * Convert to a map representation
   * @return a map with keys: minX, minY, width, height
   */
  Map<String, Double> toMap() {
    [minX: minX, minY: minY, width: width, height: height]
  }
}
