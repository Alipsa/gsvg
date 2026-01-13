package se.alipsa.groovy.svg.utils

import groovy.transform.CompileStatic

/**
 * Fluent API for building SVG path data strings.
 * <p>
 * This class provides a convenient way to construct SVG path data using method chaining
 * instead of manually building path data strings. It supports all SVG path commands
 * in both absolute and relative forms.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * // Build a triangle
 * def path = PathBuilder.moveTo(10, 10)
 *     .lineTo(20, 20)
 *     .lineTo(30, 10)
 *     .closePath()
 *     .toString()
 *
 * // Or parse and extend existing path
 * def extended = PathBuilder.parse("M 10 10 L 20 20")
 *     .lineTo(30, 30)
 *     .closePath()
 *     .toString()
 * </pre>
 *
 * <p>Supports both absolute and relative commands:</p>
 * <ul>
 *   <li>M/m - MoveTo</li>
 *   <li>L/l - LineTo</li>
 *   <li>H/h - Horizontal line</li>
 *   <li>V/v - Vertical line</li>
 *   <li>C/c - Cubic bezier curve</li>
 *   <li>S/s - Smooth cubic bezier</li>
 *   <li>Q/q - Quadratic bezier</li>
 *   <li>T/t - Smooth quadratic bezier</li>
 *   <li>A/a - Elliptical arc</li>
 *   <li>Z/z - ClosePath</li>
 * </ul>
 */
@CompileStatic
class PathBuilder {

  private final List<String> commands = []

  /**
   * Private constructor - use static factory methods to create instances
   */
  private PathBuilder() {}

  /**
   * Private constructor for parsing - use static parse() method
   */
  private PathBuilder(String pathData) {
    if (pathData != null && !pathData.trim().isEmpty()) {
      commands.add(pathData.trim())
    }
  }

  // ==================== STATIC FACTORY METHODS ====================

  /**
   * Start a new path with an absolute moveTo command
   * @param x the x coordinate
   * @param y the y coordinate
   * @return a new PathBuilder
   */
  static PathBuilder moveTo(Number x, Number y) {
    new PathBuilder().addCommand("M ${x} ${y}")
  }

  /**
   * Start a new path with a relative moveTo command
   * @param dx the relative x offset
   * @param dy the relative y offset
   * @return a new PathBuilder
   */
  static PathBuilder moveToRel(Number dx, Number dy) {
    new PathBuilder().addCommand("m ${dx} ${dy}")
  }

  /**
   * Parse an existing SVG path data string and return a builder that can extend it.
   * <p>
   * <strong>Note:</strong> The current implementation stores the path data as-is without
   * parsing it into individual commands. The parsed string will be treated as a single
   * command entry. When extending with new commands, they will be appended with proper spacing.
   * This is sufficient for extending existing paths but means getCommands() will return the
   * original string as one element.
   * </p>
   * @param pathData the existing path data string (e.g., "M 10 10 L 20 20")
   * @return a PathBuilder containing the parsed path
   */
  static PathBuilder parse(String pathData) {
    new PathBuilder(pathData)
  }

  /**
   * Create an empty path builder (useful for programmatic construction)
   * @return an empty PathBuilder
   */
  static PathBuilder create() {
    new PathBuilder()
  }

  // ==================== FLUENT API METHODS ====================

  /**
   * Add a raw command to the path
   * @param command the command string
   * @return this builder for chaining
   */
  PathBuilder addCommand(String command) {
    commands.add(command)
    this
  }

  /**
   * Add an absolute moveTo command
   * @param x the x coordinate
   * @param y the y coordinate
   * @return this builder for chaining
   */
  PathBuilder M(Number x, Number y) {
    addCommand("M ${x} ${y}")
  }

  /**
   * Add a relative moveTo command
   * @param dx the relative x offset
   * @param dy the relative y offset
   * @return this builder for chaining
   */
  PathBuilder m(Number dx, Number dy) {
    addCommand("m ${dx} ${dy}")
  }

  /**
   * Add an absolute lineTo command
   * @param x the x coordinate
   * @param y the y coordinate
   * @return this builder for chaining
   */
  PathBuilder lineTo(Number x, Number y) {
    addCommand("L ${x} ${y}")
  }

  /**
   * Add a relative lineTo command
   * @param dx the relative x offset
   * @param dy the relative y offset
   * @return this builder for chaining
   */
  PathBuilder lineToRel(Number dx, Number dy) {
    addCommand("l ${dx} ${dy}")
  }

  /**
   * Add an absolute lineTo command (alias for lineTo)
   * @param x the x coordinate
   * @param y the y coordinate
   * @return this builder for chaining
   */
  PathBuilder L(Number x, Number y) {
    lineTo(x, y)
  }

  /**
   * Add a relative lineTo command (alias for lineToRel)
   * @param dx the relative x offset
   * @param dy the relative y offset
   * @return this builder for chaining
   */
  PathBuilder l(Number dx, Number dy) {
    lineToRel(dx, dy)
  }

  /**
   * Add an absolute horizontal line command
   * @param x the x coordinate
   * @return this builder for chaining
   */
  PathBuilder horizontalTo(Number x) {
    addCommand("H ${x}")
  }

  /**
   * Add a relative horizontal line command
   * @param dx the relative x offset
   * @return this builder for chaining
   */
  PathBuilder horizontalToRel(Number dx) {
    addCommand("h ${dx}")
  }

  /**
   * Add an absolute horizontal line command (alias for horizontalTo)
   * @param x the x coordinate
   * @return this builder for chaining
   */
  PathBuilder H(Number x) {
    horizontalTo(x)
  }

  /**
   * Add a relative horizontal line command (alias for horizontalToRel)
   * @param dx the relative x offset
   * @return this builder for chaining
   */
  PathBuilder h(Number dx) {
    horizontalToRel(dx)
  }

  /**
   * Add an absolute vertical line command
   * @param y the y coordinate
   * @return this builder for chaining
   */
  PathBuilder verticalTo(Number y) {
    addCommand("V ${y}")
  }

  /**
   * Add a relative vertical line command
   * @param dy the relative y offset
   * @return this builder for chaining
   */
  PathBuilder verticalToRel(Number dy) {
    addCommand("v ${dy}")
  }

  /**
   * Add an absolute vertical line command (alias for verticalTo)
   * @param y the y coordinate
   * @return this builder for chaining
   */
  PathBuilder V(Number y) {
    verticalTo(y)
  }

  /**
   * Add a relative vertical line command (alias for verticalToRel)
   * @param dy the relative y offset
   * @return this builder for chaining
   */
  PathBuilder v(Number dy) {
    verticalToRel(dy)
  }

  /**
   * Add an absolute cubic bezier curve command
   * @param x1 first control point x
   * @param y1 first control point y
   * @param x2 second control point x
   * @param y2 second control point y
   * @param x end point x
   * @param y end point y
   * @return this builder for chaining
   */
  PathBuilder curveTo(Number x1, Number y1, Number x2, Number y2, Number x, Number y) {
    addCommand("C ${x1} ${y1}, ${x2} ${y2}, ${x} ${y}")
  }

  /**
   * Add a relative cubic bezier curve command
   * @param dx1 first control point x offset
   * @param dy1 first control point y offset
   * @param dx2 second control point x offset
   * @param dy2 second control point y offset
   * @param dx end point x offset
   * @param dy end point y offset
   * @return this builder for chaining
   */
  PathBuilder curveToRel(Number dx1, Number dy1, Number dx2, Number dy2, Number dx, Number dy) {
    addCommand("c ${dx1} ${dy1}, ${dx2} ${dy2}, ${dx} ${dy}")
  }

  /**
   * Add an absolute cubic bezier curve command (alias for curveTo)
   * @param x1 first control point x
   * @param y1 first control point y
   * @param x2 second control point x
   * @param y2 second control point y
   * @param x end point x
   * @param y end point y
   * @return this builder for chaining
   */
  PathBuilder C(Number x1, Number y1, Number x2, Number y2, Number x, Number y) {
    curveTo(x1, y1, x2, y2, x, y)
  }

  /**
   * Add a relative cubic bezier curve command (alias for curveToRel)
   * @param dx1 first control point x offset
   * @param dy1 first control point y offset
   * @param dx2 second control point x offset
   * @param dy2 second control point y offset
   * @param dx end point x offset
   * @param dy end point y offset
   * @return this builder for chaining
   */
  PathBuilder c(Number dx1, Number dy1, Number dx2, Number dy2, Number dx, Number dy) {
    curveToRel(dx1, dy1, dx2, dy2, dx, dy)
  }

  /**
   * Add an absolute smooth cubic bezier curve command
   * @param x2 second control point x
   * @param y2 second control point y
   * @param x end point x
   * @param y end point y
   * @return this builder for chaining
   */
  PathBuilder smoothCurveTo(Number x2, Number y2, Number x, Number y) {
    addCommand("S ${x2} ${y2}, ${x} ${y}")
  }

  /**
   * Add a relative smooth cubic bezier curve command
   * @param dx2 second control point x offset
   * @param dy2 second control point y offset
   * @param dx end point x offset
   * @param dy end point y offset
   * @return this builder for chaining
   */
  PathBuilder smoothCurveToRel(Number dx2, Number dy2, Number dx, Number dy) {
    addCommand("s ${dx2} ${dy2}, ${dx} ${dy}")
  }

  /**
   * Add an absolute smooth cubic bezier curve command (alias for smoothCurveTo)
   * @param x2 second control point x
   * @param y2 second control point y
   * @param x end point x
   * @param y end point y
   * @return this builder for chaining
   */
  PathBuilder S(Number x2, Number y2, Number x, Number y) {
    smoothCurveTo(x2, y2, x, y)
  }

  /**
   * Add a relative smooth cubic bezier curve command (alias for smoothCurveToRel)
   * @param dx2 second control point x offset
   * @param dy2 second control point y offset
   * @param dx end point x offset
   * @param dy end point y offset
   * @return this builder for chaining
   */
  PathBuilder s(Number dx2, Number dy2, Number dx, Number dy) {
    smoothCurveToRel(dx2, dy2, dx, dy)
  }

  /**
   * Add an absolute quadratic bezier curve command
   * @param x1 control point x
   * @param y1 control point y
   * @param x end point x
   * @param y end point y
   * @return this builder for chaining
   */
  PathBuilder quadTo(Number x1, Number y1, Number x, Number y) {
    addCommand("Q ${x1} ${y1}, ${x} ${y}")
  }

  /**
   * Add a relative quadratic bezier curve command
   * @param dx1 control point x offset
   * @param dy1 control point y offset
   * @param dx end point x offset
   * @param dy end point y offset
   * @return this builder for chaining
   */
  PathBuilder quadToRel(Number dx1, Number dy1, Number dx, Number dy) {
    addCommand("q ${dx1} ${dy1}, ${dx} ${dy}")
  }

  /**
   * Add an absolute quadratic bezier curve command (alias for quadTo)
   * @param x1 control point x
   * @param y1 control point y
   * @param x end point x
   * @param y end point y
   * @return this builder for chaining
   */
  PathBuilder Q(Number x1, Number y1, Number x, Number y) {
    quadTo(x1, y1, x, y)
  }

  /**
   * Add a relative quadratic bezier curve command (alias for quadToRel)
   * @param dx1 control point x offset
   * @param dy1 control point y offset
   * @param dx end point x offset
   * @param dy end point y offset
   * @return this builder for chaining
   */
  PathBuilder q(Number dx1, Number dy1, Number dx, Number dy) {
    quadToRel(dx1, dy1, dx, dy)
  }

  /**
   * Add an absolute smooth quadratic bezier curve command
   * @param x end point x
   * @param y end point y
   * @return this builder for chaining
   */
  PathBuilder smoothQuadTo(Number x, Number y) {
    addCommand("T ${x} ${y}")
  }

  /**
   * Add a relative smooth quadratic bezier curve command
   * @param dx end point x offset
   * @param dy end point y offset
   * @return this builder for chaining
   */
  PathBuilder smoothQuadToRel(Number dx, Number dy) {
    addCommand("t ${dx} ${dy}")
  }

  /**
   * Add an absolute smooth quadratic bezier curve command (alias for smoothQuadTo)
   * @param x end point x
   * @param y end point y
   * @return this builder for chaining
   */
  PathBuilder T(Number x, Number y) {
    smoothQuadTo(x, y)
  }

  /**
   * Add a relative smooth quadratic bezier curve command (alias for smoothQuadToRel)
   * @param dx end point x offset
   * @param dy end point y offset
   * @return this builder for chaining
   */
  PathBuilder t(Number dx, Number dy) {
    smoothQuadToRel(dx, dy)
  }

  /**
   * Add an absolute elliptical arc command
   * @param rx x-axis radius
   * @param ry y-axis radius
   * @param xAxisRotation rotation of the ellipse in degrees
   * @param largeArcFlag 0 for small arc, 1 for large arc
   * @param sweepFlag 0 for counter-clockwise, 1 for clockwise
   * @param x end point x
   * @param y end point y
   * @return this builder for chaining
   */
  PathBuilder arc(Number rx, Number ry, Number xAxisRotation, int largeArcFlag, int sweepFlag, Number x, Number y) {
    addCommand("A ${rx} ${ry} ${xAxisRotation} ${largeArcFlag} ${sweepFlag} ${x} ${y}")
  }

  /**
   * Add a relative elliptical arc command
   * @param rx x-axis radius
   * @param ry y-axis radius
   * @param xAxisRotation rotation of the ellipse in degrees
   * @param largeArcFlag 0 for small arc, 1 for large arc
   * @param sweepFlag 0 for counter-clockwise, 1 for clockwise
   * @param dx end point x offset
   * @param dy end point y offset
   * @return this builder for chaining
   */
  PathBuilder arcRel(Number rx, Number ry, Number xAxisRotation, int largeArcFlag, int sweepFlag, Number dx, Number dy) {
    addCommand("a ${rx} ${ry} ${xAxisRotation} ${largeArcFlag} ${sweepFlag} ${dx} ${dy}")
  }

  /**
   * Add an absolute elliptical arc command (alias for arc)
   * @param rx x-axis radius
   * @param ry y-axis radius
   * @param xAxisRotation rotation of the ellipse in degrees
   * @param largeArcFlag 0 for small arc, 1 for large arc
   * @param sweepFlag 0 for counter-clockwise, 1 for clockwise
   * @param x end point x
   * @param y end point y
   * @return this builder for chaining
   */
  PathBuilder A(Number rx, Number ry, Number xAxisRotation, int largeArcFlag, int sweepFlag, Number x, Number y) {
    arc(rx, ry, xAxisRotation, largeArcFlag, sweepFlag, x, y)
  }

  /**
   * Add a relative elliptical arc command (alias for arcRel)
   * @param rx x-axis radius
   * @param ry y-axis radius
   * @param xAxisRotation rotation of the ellipse in degrees
   * @param largeArcFlag 0 for small arc, 1 for large arc
   * @param sweepFlag 0 for counter-clockwise, 1 for clockwise
   * @param dx end point x offset
   * @param dy end point y offset
   * @return this builder for chaining
   */
  PathBuilder a(Number rx, Number ry, Number xAxisRotation, int largeArcFlag, int sweepFlag, Number dx, Number dy) {
    arcRel(rx, ry, xAxisRotation, largeArcFlag, sweepFlag, dx, dy)
  }

  /**
   * Add a closePath command (connects the current point to the first point of the path)
   * @return this builder for chaining
   */
  PathBuilder closePath() {
    addCommand("Z")
  }

  /**
   * Add a closePath command (alias for closePath)
   * @return this builder for chaining
   */
  PathBuilder Z() {
    closePath()
  }

  /**
   * Add a closePath command (lowercase alias for closePath)
   * @return this builder for chaining
   */
  PathBuilder z() {
    closePath()
  }

  // ==================== OUTPUT METHODS ====================

  /**
   * Convert the path to an SVG path data string
   * @return the path data string suitable for use in a path element's 'd' attribute
   */
  @Override
  String toString() {
    commands.join(' ')
  }

  /**
   * Get the list of commands that make up this path
   * @return a copy of the commands list
   */
  List<String> getCommands() {
    new ArrayList<>(commands)
  }

  /**
   * Check if this path is empty
   * @return true if the path has no commands
   */
  boolean isEmpty() {
    commands.isEmpty()
  }

  // ==================== UTILITY METHODS ====================

  /**
   * Create a copy of this PathBuilder
   * @return a new PathBuilder with the same commands
   */
  PathBuilder copy() {
    PathBuilder result = new PathBuilder()
    result.@commands.addAll(this.@commands)
    result
  }

  @Override
  boolean equals(Object obj) {
    if (this.is(obj)) return true
    if (obj == null || getClass() != obj.getClass()) return false
    PathBuilder that = (PathBuilder) obj
    commands == that.commands
  }

  @Override
  int hashCode() {
    commands.hashCode()
  }
}
