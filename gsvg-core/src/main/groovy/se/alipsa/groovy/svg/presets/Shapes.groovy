package se.alipsa.groovy.svg.presets

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.AbstractElementContainer
import se.alipsa.groovy.svg.Path
import se.alipsa.groovy.svg.Polygon
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.utils.PathBuilder

/**
 * Provides preset shapes and common shape patterns.
 * <p>
 * This utility class offers factory methods for creating commonly used shapes
 * that require complex path calculations or multiple elements, such as stars,
 * arrows, and rounded rectangles.
 * <p>
 * <h2>Usage Examples:</h2>
 * <pre>
 * // Create a 5-point star
 * Polygon star = Shapes.star(svg, cx: 100, cy: 100, points: 5, outerRadius: 50, innerRadius: 25)
 *
 * // Create a rounded rectangle
 * Rect roundedRect = Shapes.roundedRect(svg, x: 10, y: 10, width: 100, height: 60, radius: 10)
 *
 * // Create an arrow
 * Path arrow = Shapes.arrow(svg, x1: 10, y1: 50, x2: 100, y2: 50, headSize: 10)
 * </pre>
 *
 * @since 0.9.0
 */
@CompileStatic
class Shapes {

    /**
     * Creates a rounded rectangle with uniform corner radius.
     * <p>
     * Options:
     * <ul>
     *   <li>x - X coordinate (default: 0)</li>
     *   <li>y - Y coordinate (default: 0)</li>
     *   <li>width - Rectangle width (required)</li>
     *   <li>height - Rectangle height (required)</li>
     *   <li>radius - Corner radius (default: 5)</li>
     * </ul>
     *
     * @param parent parent element
     * @param options configuration map
     * @return configured Rect element
     */
    static Rect roundedRect(AbstractElementContainer parent, Map options) {
        Number x = options.x != null ? options.x as Number : 0
        Number y = options.y != null ? options.y as Number : 0
        Number width = options.width as Number
        Number height = options.height as Number
        Number radius = options.radius != null ? options.radius as Number : 5

        Rect rect = parent.addRect()
        rect.x(x).y(y).width(width).height(height)
        rect.rx(radius).ry(radius)
        rect
    }

    /**
     * Creates a star polygon with specified number of points.
     * <p>
     * Options:
     * <ul>
     *   <li>cx - Center X coordinate (default: 0)</li>
     *   <li>cy - Center Y coordinate (default: 0)</li>
     *   <li>points - Number of star points (default: 5)</li>
     *   <li>outerRadius - Radius to outer points (default: 50)</li>
     *   <li>innerRadius - Radius to inner points (default: outerRadius / 2.5)</li>
     * </ul>
     *
     * @param parent parent element
     * @param options configuration map
     * @return configured Polygon element
     */
    static Polygon star(AbstractElementContainer parent, Map options) {
        double cx = options.cx != null ? (options.cx as Number).doubleValue() : 0
        double cy = options.cy != null ? (options.cy as Number).doubleValue() : 0
        int points = options.points != null ? (options.points as Number).intValue() : 5
        double outerRadius = options.outerRadius != null ? (options.outerRadius as Number).doubleValue() : 50
        double innerRadius = options.innerRadius != null ? (options.innerRadius as Number).doubleValue() : outerRadius / 2.5

        List<String> coords = []
        double angleStep = Math.PI / points

        for (int i = 0; i < points * 2; i++) {
            double angle = i * angleStep - Math.PI / 2
            double radius = (i % 2 == 0) ? outerRadius : innerRadius
            double x = cx + radius * Math.cos(angle)
            double y = cy + radius * Math.sin(angle)
            coords.add(x.toString() + ',' + y.toString())
        }

        String pointsStr = coords.join(' ')
        parent.addPolygon(pointsStr)
    }

    /**
     * Creates an arrow path from point (x1,y1) to (x2,y2).
     * <p>
     * Options:
     * <ul>
     *   <li>x1 - Start X coordinate (required)</li>
     *   <li>y1 - Start Y coordinate (required)</li>
     *   <li>x2 - End X coordinate (required)</li>
     *   <li>y2 - End Y coordinate (required)</li>
     *   <li>headSize - Arrow head size in pixels (default: 10)</li>
     *   <li>headAngle - Arrow head angle in degrees (default: 30)</li>
     * </ul>
     *
     * @param parent parent element
     * @param options configuration map
     * @return configured Path element
     */
    static Path arrow(AbstractElementContainer parent, Map options) {
        double x1 = (options.x1 as Number).doubleValue()
        double y1 = (options.y1 as Number).doubleValue()
        double x2 = (options.x2 as Number).doubleValue()
        double y2 = (options.y2 as Number).doubleValue()
        double headSize = options.headSize != null ? (options.headSize as Number).doubleValue() : 10
        double headAngle = options.headAngle != null ? (options.headAngle as Number).doubleValue() : 30

        // Calculate arrow direction
        double dx = x2 - x1
        double dy = y2 - y1
        double angle = Math.atan2(dy, dx)

        // Calculate arrow head points
        double angleRad = Math.toRadians(headAngle)
        double angle1 = angle + Math.PI - angleRad
        double angle2 = angle + Math.PI + angleRad

        double headX1 = x2 + headSize * Math.cos(angle1)
        double headY1 = y2 + headSize * Math.sin(angle1)
        double headX2 = x2 + headSize * Math.cos(angle2)
        double headY2 = y2 + headSize * Math.sin(angle2)

        // Build path
        PathBuilder builder = PathBuilder.moveTo(x1, y1)
        builder.L(x2, y2)
        builder.M(headX1, headY1)
        builder.L(x2, y2)
        builder.L(headX2, headY2)

        parent.addPath().d(builder)
    }

    /**
     * Creates a regular polygon (triangle, hexagon, etc).
     * <p>
     * Options:
     * <ul>
     *   <li>cx - Center X coordinate (default: 0)</li>
     *   <li>cy - Center Y coordinate (default: 0)</li>
     *   <li>sides - Number of sides (default: 6)</li>
     *   <li>radius - Distance from center to vertices (default: 50)</li>
     *   <li>rotation - Rotation angle in degrees (default: 0)</li>
     * </ul>
     *
     * @param parent parent element
     * @param options configuration map
     * @return configured Polygon element
     */
    static Polygon regularPolygon(AbstractElementContainer parent, Map options) {
        double cx = options.cx != null ? (options.cx as Number).doubleValue() : 0
        double cy = options.cy != null ? (options.cy as Number).doubleValue() : 0
        int sides = options.sides != null ? (options.sides as Number).intValue() : 6
        double radius = options.radius != null ? (options.radius as Number).doubleValue() : 50
        double rotation = options.rotation != null ? (options.rotation as Number).doubleValue() : 0

        List<String> coords = []
        double angleStep = 2 * Math.PI / sides
        double startAngle = Math.toRadians(rotation) - Math.PI / 2

        for (int i = 0; i < sides; i++) {
            double angle = startAngle + i * angleStep
            double x = cx + radius * Math.cos(angle)
            double y = cy + radius * Math.sin(angle)
            coords.add(x.toString() + ',' + y.toString())
        }

        String pointsStr = coords.join(' ')
        parent.addPolygon(pointsStr)
    }

    /**
     * Creates a speech bubble with a tail pointing to a specific location.
     * <p>
     * Options:
     * <ul>
     *   <li>x - Bubble X coordinate (default: 0)</li>
     *   <li>y - Bubble Y coordinate (default: 0)</li>
     *   <li>width - Bubble width (default: 100)</li>
     *   <li>height - Bubble height (default: 60)</li>
     *   <li>radius - Corner radius (default: 10)</li>
     *   <li>tailX - Tail point X coordinate (required)</li>
     *   <li>tailY - Tail point Y coordinate (required)</li>
     *   <li>tailWidth - Width of tail base (default: 20)</li>
     * </ul>
     *
     * @param parent parent element
     * @param options configuration map
     * @return configured Path element
     */
    static Path speechBubble(AbstractElementContainer parent, Map options) {
        double x = options.x != null ? (options.x as Number).doubleValue() : 0
        double y = options.y != null ? (options.y as Number).doubleValue() : 0
        double width = options.width != null ? (options.width as Number).doubleValue() : 100
        double height = options.height != null ? (options.height as Number).doubleValue() : 60
        double radius = options.radius != null ? (options.radius as Number).doubleValue() : 10
        double tailX = (options.tailX as Number).doubleValue()
        double tailY = (options.tailY as Number).doubleValue()
        double tailWidth = options.tailWidth != null ? (options.tailWidth as Number).doubleValue() : 20

        // Determine tail position (bottom edge for now)
        double tailStart = x + width / 2 - tailWidth / 2
        double tailEnd = x + width / 2 + tailWidth / 2

        // Start at top-left, after radius
        PathBuilder builder = PathBuilder.moveTo(x + radius, y)

        // Top edge
        builder.H(x + width - radius)

        // Top-right corner
        builder.A(radius, radius, 0, 0, 1, x + width, y + radius)

        // Right edge
        builder.V(y + height - radius)

        // Bottom-right corner
        builder.A(radius, radius, 0, 0, 1, x + width - radius, y + height)

        // Bottom edge to tail
        builder.H(tailEnd)
        builder.L(tailX, tailY)
        builder.L(tailStart, y + height)
        builder.H(x + radius)

        // Bottom-left corner
        builder.A(radius, radius, 0, 0, 1, x, y + height - radius)

        // Left edge
        builder.V(y + radius)

        // Top-left corner
        builder.A(radius, radius, 0, 0, 1, x + radius, y)
        builder.Z()

        parent.addPath().d(builder)
    }
}
