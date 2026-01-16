package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.*

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for DSL-style closure configuration of SVG elements.
 */
class DslConfigurationTest {

    @Test
    void testCircleDslConfiguration() {
        Svg svg = new Svg(200, 200)

        Circle circle = svg.addCircle {
            cx 100
            cy 100
            r 50
            fill 'red'
            stroke 'black'
            strokeWidth 2
        }

        assertNotNull(circle)
        assertEquals('100', circle.getCx())
        assertEquals('100', circle.getCy())
        assertEquals('50', circle.getR())
        assertEquals('red', circle.getFill())
        assertEquals('black', circle.getStroke())
        assertEquals('2', circle.getStrokeWidth())
    }

    @Test
    void testRectDslConfiguration() {
        Svg svg = new Svg(300, 200)

        Rect rect = svg.addRect {
            x 10
            y 20
            width 150
            height 100
            fill 'blue'
            rx 5
            ry 5
        }

        assertNotNull(rect)
        assertEquals('10', rect.getX())
        assertEquals('20', rect.getY())
        assertEquals('150', rect.getWidth())
        assertEquals('100', rect.getHeight())
        assertEquals('blue', rect.getFill())
        assertEquals('5', rect.getRx())
        assertEquals('5', rect.getRy())
    }

    @Test
    void testEllipseDslConfiguration() {
        Svg svg = new Svg(300, 200)

        // Note: Ellipse doesn't have rx/ry setter methods, only getters
        // Use addAttribute for rx/ry
        Ellipse ellipse = svg.addEllipse {
            cx 150
            cy 100
            addAttribute 'rx', 100
            addAttribute 'ry', 50
            fill 'yellow'
            stroke 'orange'
            strokeWidth 3
        }

        assertNotNull(ellipse)
        assertEquals('150', ellipse.getCx())
        assertEquals('100', ellipse.getCy())
        assertEquals('100', ellipse.getRx())
        assertEquals('50', ellipse.getRy())
        assertEquals('yellow', ellipse.getFill())
        assertEquals('orange', ellipse.getStroke())
        assertEquals('3', ellipse.getStrokeWidth())
    }

    @Test
    void testLineDslConfiguration() {
        Svg svg = new Svg(300, 200)

        Line line = svg.addLine {
            x1 10
            y1 50
            x2 290
            y2 150
            stroke 'green'
            strokeWidth 5
            strokeLinecap 'round'
        }

        assertNotNull(line)
        assertEquals('10', line.getX1())
        assertEquals('50', line.getY1())
        assertEquals('290', line.getX2())
        assertEquals('150', line.getY2())
        assertEquals('green', line.getStroke())
        assertEquals('5', line.getStrokeWidth())
        assertEquals('round', line.getStrokeLinecap())
    }

    @Test
    void testPathDslConfiguration() {
        Svg svg = new Svg(300, 300)

        Path path = svg.addPath {
            d 'M 10 10 L 100 100 L 10 100 Z'
            fill 'purple'
            stroke 'indigo'
            strokeWidth 2
        }

        assertNotNull(path)
        assertEquals('M 10 10 L 100 100 L 10 100 Z', path.getD())
        assertEquals('purple', path.getFill())
        assertEquals('indigo', path.getStroke())
        assertEquals('2', path.getStrokeWidth())
    }

    @Test
    void testTextDslConfiguration() {
        Svg svg = new Svg(400, 200)

        // Note: Text doesn't have fontWeight method, use addAttribute
        Text text = svg.addText {
            addContent 'Hello DSL!'
            x 100
            y 100
            fontSize 24
            addAttribute 'font-weight', 'bold'
            fill 'navy'
        }

        assertNotNull(text)
        assertEquals('Hello DSL!', text.getContent())
        assertEquals('100', text.getX())
        assertEquals('100', text.getY())
        // Note: Text doesn't have getFontSize(), only fontSize() setter
        assertEquals('24', text.getAttribute('font-size'))
        assertEquals('bold', text.getAttribute('font-weight'))
        assertEquals('navy', text.getFill())
    }

    @Test
    void testGroupDslConfiguration() {
        Svg svg = new Svg(400, 400)

        G group = svg.addG {
            fill 'blue'
            stroke 'black'
            strokeWidth 1
        }

        assertNotNull(group)
        assertEquals('blue', group.getFill())
        assertEquals('black', group.getStroke())
        assertEquals('1', group.getStrokeWidth())
    }

    @Test
    void testGroupDslWithChildren() {
        Svg svg = new Svg(400, 400)

        G group = svg.addG {
            fill 'red'
            stroke 'darkred'
            strokeWidth 2

            // Add children within the closure
            addCircle().cx(50).cy(50).r(20)
            addRect().x(100).y(30).width(40).height(40)
        }

        assertNotNull(group)
        assertEquals(2, group.getChildren().size())

        Circle circle = group.getChildren().get(0) as Circle
        assertEquals('50', circle.getCx())

        Rect rect = group.getChildren().get(1) as Rect
        assertEquals('100', rect.getX())
    }

    @Test
    void testPolygonDslConfiguration() {
        Svg svg = new Svg(300, 300)

        Polygon polygon = svg.addPolygon {
            points '100,10 40,198 190,78 10,78 160,198'
            fill 'lime'
            stroke 'green'
            strokeWidth 2
        }

        assertNotNull(polygon)
        assertEquals('100,10 40,198 190,78 10,78 160,198', polygon.getPoints())
        assertEquals('lime', polygon.getFill())
        assertEquals('green', polygon.getStroke())
        assertEquals('2', polygon.getStrokeWidth())
    }

    @Test
    void testPolylineDslConfiguration() {
        Svg svg = new Svg(400, 200)

        Polyline polyline = svg.addPolyline {
            points '20,20 40,25 60,40 80,120 120,140 200,180'
            fill 'none'
            stroke 'blue'
            strokeWidth 3
        }

        assertNotNull(polyline)
        assertEquals('20,20 40,25 60,40 80,120 120,140 200,180', polyline.getPoints())
        assertEquals('none', polyline.getFill())
        assertEquals('blue', polyline.getStroke())
        assertEquals('3', polyline.getStrokeWidth())
    }

    @Test
    void testDslWithTransformations() {
        Svg svg = new Svg(300, 300)

        Rect rect = svg.addRect {
            x 50
            y 50
            width 100
            height 100
            fill 'orange'
            rotate 45
            scale 1.5
        }

        assertNotNull(rect)
        assertEquals('50', rect.getX())
        assertEquals('orange', rect.getFill())
        String transform = rect.getTransform()
        assertTrue(transform.contains('rotate(45)'))
        assertTrue(transform.contains('scale(1.5)'))
    }

    @Test
    void testDslWithAccessibility() {
        Svg svg = new Svg(300, 200)

        Circle circle = svg.addCircle {
            cx 150
            cy 100
            r 50
            fill 'green'
            role 'img'
            ariaLabel 'A green circle'
        }

        assertNotNull(circle)
        assertEquals('img', circle.getRole())
        assertEquals('A green circle', circle.getAriaLabel())
    }

    @Test
    void testMultipleDslElements() {
        Svg svg = new Svg(500, 400)

        svg.addCircle {
            cx 100
            cy 100
            r 50
            fill 'red'
        }

        svg.addRect {
            x 200
            y 75
            width 100
            height 50
            fill 'blue'
        }

        svg.addEllipse {
            cx 400
            cy 100
            addAttribute 'rx', 60
            addAttribute 'ry', 40
            fill 'green'
        }

        assertEquals(3, svg.getChildren().size())

        Circle circle = svg.getChildren().get(0) as Circle
        assertEquals('red', circle.getFill())

        Rect rect = svg.getChildren().get(1) as Rect
        assertEquals('blue', rect.getFill())

        Ellipse ellipse = svg.getChildren().get(2) as Ellipse
        assertEquals('green', ellipse.getFill())
    }

    @Test
    void testDslGeneratedSvgIsValid() {
        Svg svg = new Svg(400, 400)

        svg.addCircle {
            cx 100
            cy 100
            r 50
            fill 'purple'
        }

        svg.addRect {
            x 200
            y 75
            width 100
            height 50
            fill 'teal'
        }

        String xml = svg.toXml()

        assertTrue(xml.contains('<svg'))
        assertTrue(xml.contains('<circle'))
        assertTrue(xml.contains('cx="100"'))
        assertTrue(xml.contains('fill="purple"'))
        assertTrue(xml.contains('<rect'))
        assertTrue(xml.contains('x="200"'))
        assertTrue(xml.contains('fill="teal"'))
        assertTrue(xml.contains('</svg>'))
    }

    @Test
    void testNestedDslConfiguration() {
        Svg svg = new Svg(600, 600)

        svg.addG {
            id 'outerGroup'
            fill 'lightblue'

            addG {
                id 'innerGroup'
                stroke 'black'
                strokeWidth 2

                addCircle {
                    cx 100
                    cy 100
                    r 40
                }

                addRect {
                    x 200
                    y 75
                    width 50
                    height 50
                }
            }
        }

        assertEquals(1, svg.getChildren().size())

        G outerGroup = svg.getChildren().get(0) as G
        assertEquals('outerGroup', outerGroup.getId())
        assertEquals('lightblue', outerGroup.getFill())

        assertEquals(1, outerGroup.getChildren().size())

        G innerGroup = outerGroup.getChildren().get(0) as G
        assertEquals('innerGroup', innerGroup.getId())
        assertEquals('black', innerGroup.getStroke())

        assertEquals(2, innerGroup.getChildren().size())
    }

    @Test
    void testDslWithMethodChaining() {
        Svg svg = new Svg(300, 300)

        // DSL closure can be mixed with method chaining
        Circle circle = svg.addCircle {
            cx 150
            cy 150
            r 60
        }.fill('coral')
          .stroke('darkred')
          .strokeWidth(3)

        assertNotNull(circle)
        assertEquals('150', circle.getCx())
        assertEquals('60', circle.getR())
        assertEquals('coral', circle.getFill())
        assertEquals('darkred', circle.getStroke())
        assertEquals('3', circle.getStrokeWidth())
    }

    @Test
    void testDslEmptyClosure() {
        Svg svg = new Svg(200, 200)

        // Empty closure should work - creates element with no configuration
        Circle circle = svg.addCircle {}

        assertNotNull(circle)
        assertNull(circle.getCx())  // No attributes set
        assertNull(circle.getFill())
    }

    @Test
    void testDslWithComplexScene() {
        // Create a complete scene using DSL
        Svg svg = new Svg(800, 600)

        svg.role('img')
        svg.ariaLabel('Simple house scene')

        // House
        svg.addRect {
            x 200
            y 300
            width 400
            height 250
            fill 'beige'
            stroke 'brown'
            strokeWidth 3
        }

        // Roof
        svg.addPolygon {
            points '200,300 400,150 600,300'
            fill 'darkred'
            stroke 'maroon'
            strokeWidth 2
        }

        // Door
        svg.addRect {
            x 350
            y 450
            width 100
            height 100
            fill 'brown'
        }

        // Window 1
        svg.addRect {
            x 250
            y 350
            width 80
            height 80
            fill 'lightblue'
            stroke 'blue'
            strokeWidth 2
        }

        // Window 2
        svg.addRect {
            x 470
            y 350
            width 80
            height 80
            fill 'lightblue'
            stroke 'blue'
            strokeWidth 2
        }

        // Sun
        svg.addCircle {
            cx 700
            cy 100
            r 50
            fill 'yellow'
            stroke 'orange'
            strokeWidth 3
        }

        // Verify structure
        assertNotNull(svg)
        assertEquals(6, svg.getChildren().size())  // house, roof, door, 2 windows, sun

        // Verify accessibility
        assertEquals('img', svg.getRole())
        assertEquals('Simple house scene', svg.getAriaLabel())
    }
}
