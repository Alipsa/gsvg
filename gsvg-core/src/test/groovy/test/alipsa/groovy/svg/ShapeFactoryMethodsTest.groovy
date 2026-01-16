package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.*
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for convenience shape factory methods on Svg class.
 */
class ShapeFactoryMethodsTest {

    @Test
    void createRoundedRectShouldCreateRoundedRectangle() {
        Svg svg = new Svg(200, 200)
        Rect rect = svg.createRoundedRect(x: 10, y: 20, width: 100, height: 60, radius: 10)

        assertNotNull(rect)
        assertEquals('10', rect.getX())
        assertEquals('20', rect.getY())
        assertEquals('100', rect.getWidth())
        assertEquals('60', rect.getHeight())
        assertEquals('10', rect.getRx())
        assertEquals('10', rect.getRy())
    }

    @Test
    void createRoundedRectShouldUseDefaultRadius() {
        Svg svg = new Svg(200, 200)
        Rect rect = svg.createRoundedRect(width: 100, height: 60)

        assertEquals('5', rect.getRx())
        assertEquals('5', rect.getRy())
    }

    @Test
    void createStarShouldCreateStarPolygon() {
        Svg svg = new Svg(200, 200)
        Polygon star = svg.createStar(cx: 100, cy: 100, points: 5, outerRadius: 50, innerRadius: 25)

        assertNotNull(star)
        assertNotNull(star.getPoints())
        assertTrue(star.getPoints().contains(','))  // Has coordinates
    }

    @Test
    void createStarShouldCreateDefault5PointStar() {
        Svg svg = new Svg(200, 200)
        Polygon star = svg.createStar(cx: 100, cy: 100, outerRadius: 50)

        assertNotNull(star)
        // Default is 5 points, which creates 10 coordinates (5 outer + 5 inner)
        assertEquals(10, star.getPoints().split(' ').length)
    }

    @Test
    void createArrowShouldCreateArrowPath() {
        Svg svg = new Svg(200, 200)
        Path arrow = svg.createArrow(x1: 10, y1: 50, x2: 100, y2: 50, headSize: 15)

        assertNotNull(arrow)
        assertNotNull(arrow.getD())
        assertTrue(arrow.getD().contains('M'))  // Has move command
        assertTrue(arrow.getD().contains('L'))  // Has line command
    }

    @Test
    void createArrowShouldUseDefaultHeadSize() {
        Svg svg = new Svg(200, 200)
        Path arrow = svg.createArrow(x1: 10, y1: 50, x2: 100, y2: 50)

        assertNotNull(arrow)
        assertNotNull(arrow.getD())
    }

    @Test
    void createRegularPolygonShouldCreateHexagonByDefault() {
        Svg svg = new Svg(200, 200)
        Polygon hexagon = svg.createRegularPolygon(cx: 100, cy: 100, radius: 50)

        assertNotNull(hexagon)
        // Default is 6 sides
        assertEquals(6, hexagon.getPoints().split(' ').length)
    }

    @Test
    void createRegularPolygonShouldCreateTriangle() {
        Svg svg = new Svg(200, 200)
        Polygon triangle = svg.createRegularPolygon(cx: 100, cy: 100, sides: 3, radius: 50)

        assertNotNull(triangle)
        assertEquals(3, triangle.getPoints().split(' ').length)
    }

    @Test
    void createRegularPolygonShouldSupportRotation() {
        Svg svg = new Svg(200, 200)
        Polygon rotated = svg.createRegularPolygon(cx: 100, cy: 100, sides: 4, radius: 50, rotation: 45)

        assertNotNull(rotated)
        assertEquals(4, rotated.getPoints().split(' ').length)
    }

    @Test
    void createSpeechBubbleShouldCreateSpeechBubblePath() {
        Svg svg = new Svg(200, 200)
        Path bubble = svg.createSpeechBubble(x: 10, y: 10, width: 100, height: 60, tailX: 50, tailY: 80)

        assertNotNull(bubble)
        assertNotNull(bubble.getD())
        assertTrue(bubble.getD().contains('M'))  // Has move command
        assertTrue(bubble.getD().contains('A'))  // Has arc command (for rounded corners)
        assertTrue(bubble.getD().contains('Z'))  // Has close path command
    }

    @Test
    void createSpeechBubbleShouldUseDefaultDimensions() {
        Svg svg = new Svg(200, 200)
        Path bubble = svg.createSpeechBubble(tailX: 50, tailY: 80)

        assertNotNull(bubble)
        assertNotNull(bubble.getD())
    }

    @Test
    void shapeFactoryMethodsShouldIntegrateWithFluentAPI() {
        Svg svg = new Svg(400, 400)

        Rect rect = svg.createRoundedRect(x: 10, y: 10, width: 100, height: 60, radius: 10)
           .fill('lightblue')
           .stroke('navy')
           .strokeWidth(2)

        Polygon star = svg.createStar(cx: 200, cy: 200, points: 5, outerRadius: 50)
           .fill('gold')
           .stroke('orange')

        Path arrow = svg.createArrow(x1: 250, y1: 50, x2: 350, y2: 50)
           .stroke('red')
           .strokeWidth(3)

        assertEquals('lightblue', rect.getFill())
        assertEquals('navy', rect.getStroke())
        assertEquals('gold', star.getFill())
        assertEquals('orange', star.getStroke())
        assertEquals('red', arrow.getStroke())
        assertEquals(3, svg.getChildren().size())
    }

    @Test
    void multipleShapesCanBeCreatedOnSameSVG() {
        Svg svg = new Svg(400, 400)

        svg.createStar(cx: 100, cy: 100, points: 5, outerRadius: 30)
        svg.createRegularPolygon(cx: 200, cy: 100, sides: 6, radius: 30)
        svg.createRoundedRect(x: 250, y: 70, width: 60, height: 60, radius: 10)
        svg.createArrow(x1: 50, y1: 200, x2: 150, y2: 200)
        svg.createSpeechBubble(x: 200, y: 170, width: 120, height: 60, tailX: 260, tailY: 250)

        assertEquals(5, svg.getChildren().size())
    }
}
