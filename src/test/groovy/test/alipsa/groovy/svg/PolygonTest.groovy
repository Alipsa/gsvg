package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Coordinate
import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Polygon

class PolygonTest {

    @Test
    void testSimplePolygon() {
        Svg svg = new Svg(100,100)
        Polygon polygon = svg.addPolygon()
        .points([200,10], [250,190], [150,190])
        .style("fill:lime;stroke:purple;stroke-width:3")

        assertEquals('<polygon xmlns="http://www.w3.org/2000/svg" points="200,10 250,190 150,190" style="fill:lime;stroke:purple;stroke-width:3"/>',
            polygon.toXml())

        assertEquals(
                '<polygon xmlns="http://www.w3.org/2000/svg" points="200,10 250,190 150,190"/>',
            svg.addPolygon([200,10], [250,190], [150,190]).toXml()
        )
    }

    @Test
    void testAddPointsToNullPoints() {
        // Test adding points when points attribute doesn't exist yet
        Svg svg = new Svg(100, 100)
        Polygon polygon = svg.addPolygon()
        polygon.addPoints(new Coordinate(10, 20), new Coordinate(30, 40))

        assertEquals("10,20 30,40", polygon.getPoints())
        assertEquals('<polygon xmlns="http://www.w3.org/2000/svg" points="10,20 30,40"/>',
                polygon.toXml())
    }

    @Test
    void testAddPointsToExistingPoints() {
        // Test appending points to existing points
        Svg svg = new Svg(100, 100)
        Polygon polygon = svg.addPolygon([10, 20], [30, 40])
        polygon.addPoints(new Coordinate(50, 60), new Coordinate(70, 80))

        assertEquals("10,20 30,40 50,60 70,80", polygon.getPoints())
        assertEquals('<polygon xmlns="http://www.w3.org/2000/svg" points="10,20 30,40 50,60 70,80"/>',
                polygon.toXml())
    }

    @Test
    void testAddPointToNullPoints() {
        // Test adding a single point when points attribute doesn't exist yet
        Svg svg = new Svg(100, 100)
        Polygon polygon = svg.addPolygon()
        polygon.addPoint(new Coordinate(15, 25))

        assertEquals("15,25", polygon.getPoints())
        assertEquals('<polygon xmlns="http://www.w3.org/2000/svg" points="15,25"/>',
                polygon.toXml())
    }

    @Test
    void testAddPointToExistingPoints() {
        // Test appending a single point to existing points
        Svg svg = new Svg(100, 100)
        Polygon polygon = svg.addPolygon([10, 20], [30, 40])
        polygon.addPoint(new Coordinate(50, 60))

        assertEquals("10,20 30,40 50,60", polygon.getPoints())
        assertEquals('<polygon xmlns="http://www.w3.org/2000/svg" points="10,20 30,40 50,60"/>',
                polygon.toXml())
    }

    @Test
    void testMultipleAddPointCalls() {
        // Test adding multiple individual points sequentially
        Svg svg = new Svg(100, 100)
        Polygon polygon = svg.addPolygon()
        polygon.addPoint(new Coordinate(10, 10))
                .addPoint(new Coordinate(20, 20))
                .addPoint(new Coordinate(30, 30))

        assertEquals("10,10 20,20 30,30", polygon.getPoints())
        assertEquals('<polygon xmlns="http://www.w3.org/2000/svg" points="10,10 20,20 30,30"/>',
                polygon.toXml())
    }

    @Test
    void testPointsWithString() {
        // Test setting points using a string
        Svg svg = new Svg(100, 100)
        Polygon polygon = svg.addPolygon()
        polygon.points("100,100 200,200 150,250")

        assertEquals("100,100 200,200 150,250", polygon.getPoints())
        assertEquals('<polygon xmlns="http://www.w3.org/2000/svg" points="100,100 200,200 150,250"/>',
                polygon.toXml())
    }

    @Test
    void testPointsWithCoordinates() {
        // Test setting points using Coordinate objects
        Svg svg = new Svg(100, 100)
        Polygon polygon = svg.addPolygon()
        polygon.points(new Coordinate(5, 10), new Coordinate(15, 20), new Coordinate(25, 30))

        assertEquals("5,10 15,20 25,30", polygon.getPoints())
        assertEquals('<polygon xmlns="http://www.w3.org/2000/svg" points="5,10 15,20 25,30"/>',
                polygon.toXml())
    }

    @Test
    void testMixedPointOperations() {
        // Test mixing different point operations
        Svg svg = new Svg(100, 100)
        Polygon polygon = svg.addPolygon()
        polygon.points([10, 10])
                .addPoint(new Coordinate(20, 20))
                .addPoints(new Coordinate(30, 30), new Coordinate(40, 40))

        assertEquals("10,10 20,20 30,30 40,40", polygon.getPoints())
    }

    @Test
    void testAddPointsChaining() {
        // Test that addPoints returns the polygon for chaining
        Svg svg = new Svg(100, 100)
        Polygon polygon = svg.addPolygon()
        Polygon result = polygon.addPoints(new Coordinate(10, 10))

        assertSame(polygon, result)
    }

    @Test
    void testAddPointChaining() {
        // Test that addPoint returns the polygon for chaining
        Svg svg = new Svg(100, 100)
        Polygon polygon = svg.addPolygon()
        Polygon result = polygon.addPoint(new Coordinate(10, 10))

        assertSame(polygon, result)
    }

    @Test
    void testFillAttribute() {
        // Test the fill getter/setter
        Svg svg = new Svg(100, 100)
        Polygon polygon = svg.addPolygon([10, 10], [20, 20])
        polygon.fill("red")

        assertEquals("red", polygon.getFill())
        assertTrue(polygon.toXml().contains('fill="red"'))
    }
}
