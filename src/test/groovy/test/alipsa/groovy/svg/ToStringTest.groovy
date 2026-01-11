package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class ToStringTest {

    @Test
    void testSvgToStringWithoutId() {
        Svg svg = new Svg(200, 120)
        String result = svg.toString()
        assertEquals('svg(width=200, height=120)', result)
    }

    @Test
    void testSvgToStringWithId() {
        Svg svg = new Svg(200, 120).id('mainSvg')
        String result = svg.toString()
        assertEquals('svg(id=mainSvg, width=200, height=120)', result)
    }

    @Test
    void testCircleToStringWithId() {
        Svg svg = new Svg(200, 120)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25).id('myCircle')
        String result = circle.toString()
        assertEquals('circle(id=myCircle, cx=50, cy=50, r=25, parent=svg)', result)
    }

    @Test
    void testCircleToStringWithoutId() {
        Svg svg = new Svg(200, 120)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)
        String result = circle.toString()
        assertEquals('circle(cx=50, cy=50, r=25, parent=svg)', result)
    }

    @Test
    void testCircleToStringWithParentId() {
        Svg svg = new Svg(200, 120).id('mainSvg')
        Circle circle = svg.addCircle().cx(50).cy(50).r(25).id('myCircle')
        String result = circle.toString()
        assertEquals('circle(id=myCircle, cx=50, cy=50, r=25, parent=svg(id=mainSvg))', result)
    }

    @Test
    void testRectToStringWithoutId() {
        Svg svg = new Svg(200, 120)
        Rect rect = svg.addRect(100, 50).x(10).y(10)
        String result = rect.toString()
        assertEquals('rect(width=100, height=50, x=10, y=10, parent=svg)', result)
    }

    @Test
    void testRectToStringWithId() {
        Svg svg = new Svg(200, 120)
        Rect rect = svg.addRect(100, 50).x(10).y(10).id('myRect')
        String result = rect.toString()
        assertEquals('rect(id=myRect, width=100, height=50, x=10, y=10, parent=svg)', result)
    }

    @Test
    void testLineToStringWithoutId() {
        Svg svg = new Svg(200, 120)
        Line line = svg.addLine(0, 0, 100, 100)
        String result = line.toString()
        assertEquals('line(x1=0, y1=0, x2=100, y2=100, parent=svg)', result)
    }

    @Test
    void testEllipseToStringWithoutId() {
        Svg svg = new Svg(200, 120)
        Ellipse ellipse = svg.addEllipse(30, 20).cx(100).cy(60)
        String result = ellipse.toString()
        assertEquals('ellipse(rx=30, ry=20, cx=100, cy=60, parent=svg)', result)
    }

    @Test
    void testMarkerToStringWithId() {
        Svg svg = new Svg(200, 120)
        Defs defs = svg.addDefs()
        Marker marker = defs.addMarker().refX(5).refY(5).id('arrow')
        String result = marker.toString()
        assertEquals('marker(id=arrow, refX=5, refY=5, parent=defs)', result)
    }

    @Test
    void testMarkerToStringWithoutId() {
        Svg svg = new Svg(200, 120)
        Defs defs = svg.addDefs()
        Marker marker = defs.addMarker().refX(5).refY(5).markerWidth(10).markerHeight(10)
        String result = marker.toString()
        assertEquals('marker(refX=5, refY=5, markerWidth=10, markerHeight=10, parent=defs)', result)
    }

    @Test
    void testNestedGroupWithParentId() {
        Svg svg = new Svg(200, 120).id('mainSvg')
        G group = svg.addG().id('group1')
        Circle circle = group.addCircle().cx(50).cy(50).r(25)
        String result = circle.toString()
        assertEquals('circle(cx=50, cy=50, r=25, parent=g(id=group1))', result)
    }

    @Test
    void testElementWithoutAttributes() {
        Svg svg = new Svg()
        Circle circle = svg.addCircle()
        String result = circle.toString()
        assertEquals('circle(parent=svg)', result)
    }

    @Test
    void testElementWithIdButNoOtherAttributes() {
        Svg svg = new Svg()
        Circle circle = svg.addCircle().id('emptyCircle')
        String result = circle.toString()
        assertEquals('circle(id=emptyCircle, parent=svg)', result)
    }
}
