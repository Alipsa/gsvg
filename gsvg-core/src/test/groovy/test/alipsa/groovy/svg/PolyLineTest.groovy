package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Coordinate
import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Polyline

class PolyLineTest {

    @Test
    void testSimplePolyline() {
        Svg svg = new Svg(100,100)
        Polyline polyline = svg.addPolyline([0,40], [40,40], [40,80], [80,80], [80,120], [120,120], [120,160])
        .style("fill:yellow;stroke:red;stroke-width:4")
        assertEquals(
                '<polyline xmlns="http://www.w3.org/2000/svg" points="0,40 40,40 40,80 80,80 80,120 120,120 120,160" style="fill:yellow;stroke:red;stroke-width:4"/>',
                polyline.toXml()
        )
    }

    @Test
    void testAddPointsToNullPoints() {
        // Test adding points when points attribute doesn't exist yet
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline()
        polyline.addPoints(new Coordinate(10, 20), new Coordinate(30, 40))

        assertEquals("10,20 30,40", polyline.getPoints())
        assertEquals('<polyline xmlns="http://www.w3.org/2000/svg" points="10,20 30,40"/>',
                polyline.toXml())
    }

    @Test
    void testAddPointsToExistingPoints() {
        // Test appending points to existing points
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline([10, 20], [30, 40])
        polyline.addPoints(new Coordinate(50, 60), new Coordinate(70, 80))

        assertEquals("10,20 30,40 50,60 70,80", polyline.getPoints())
        assertEquals('<polyline xmlns="http://www.w3.org/2000/svg" points="10,20 30,40 50,60 70,80"/>',
                polyline.toXml())
    }

    @Test
    void testAddPointToNullPoints() {
        // Test adding a single point when points attribute doesn't exist yet
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline()
        polyline.addPoint(new Coordinate(15, 25))

        assertEquals("15,25", polyline.getPoints())
        assertEquals('<polyline xmlns="http://www.w3.org/2000/svg" points="15,25"/>',
                polyline.toXml())
    }

    @Test
    void testAddPointToExistingPoints() {
        // Test appending a single point to existing points
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline([10, 20], [30, 40])
        polyline.addPoint(new Coordinate(50, 60))

        assertEquals("10,20 30,40 50,60", polyline.getPoints())
        assertEquals('<polyline xmlns="http://www.w3.org/2000/svg" points="10,20 30,40 50,60"/>',
                polyline.toXml())
    }

    @Test
    void testMultipleAddPointCalls() {
        // Test adding multiple individual points sequentially
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline()
        polyline.addPoint(new Coordinate(10, 10))
                .addPoint(new Coordinate(20, 20))
                .addPoint(new Coordinate(30, 30))

        assertEquals("10,10 20,20 30,30", polyline.getPoints())
        assertEquals('<polyline xmlns="http://www.w3.org/2000/svg" points="10,10 20,20 30,30"/>',
                polyline.toXml())
    }

    @Test
    void testPointsWithString() {
        // Test setting points using a string
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline()
        polyline.points("100,100 200,200 150,250")

        assertEquals("100,100 200,200 150,250", polyline.getPoints())
        assertEquals('<polyline xmlns="http://www.w3.org/2000/svg" points="100,100 200,200 150,250"/>',
                polyline.toXml())
    }

    @Test
    void testPointsWithCoordinates() {
        // Test setting points using Coordinate objects
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline()
        polyline.points(new Coordinate(5, 10), new Coordinate(15, 20), new Coordinate(25, 30))

        assertEquals("5,10 15,20 25,30", polyline.getPoints())
        assertEquals('<polyline xmlns="http://www.w3.org/2000/svg" points="5,10 15,20 25,30"/>',
                polyline.toXml())
    }

    @Test
    void testMixedPointOperations() {
        // Test mixing different point operations
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline()
        polyline.points([10, 10])
                .addPoint(new Coordinate(20, 20))
                .addPoints(new Coordinate(30, 30), new Coordinate(40, 40))

        assertEquals("10,10 20,20 30,30 40,40", polyline.getPoints())
    }

    @Test
    void testAddPointsChaining() {
        // Test that addPoints returns the polyline for chaining
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline()
        Polyline result = polyline.addPoints(new Coordinate(10, 10))

        assertSame(polyline, result)
    }

    @Test
    void testAddPointChaining() {
        // Test that addPoint returns the polyline for chaining
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline()
        Polyline result = polyline.addPoint(new Coordinate(10, 10))

        assertSame(polyline, result)
    }

    @Test
    void testFillAttribute() {
        // Test the fill getter/setter
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline([10, 10], [20, 20])
        polyline.fill("blue")

        assertEquals("blue", polyline.getFill())
        assertTrue(polyline.toXml().contains('fill="blue"'))
    }

    @Test
    void testAnimatedPolyline() {
        // Test a more complex example with animation
        Svg svg = new Svg(100, 100)
        Polyline polyline = svg.addPolyline()
        polyline.addPoint(new Coordinate(0, 40))
                .addPoint(new Coordinate(40, 40))
                .addPoint(new Coordinate(40, 80))

        assertEquals("0,40 40,40 40,80", polyline.getPoints())
    }
}
