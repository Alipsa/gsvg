package test.alipsa.groovy.svg

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg

class CircleTest {

    @Test
    void simpleCircleTest() {
        def circle = new Circle()
        .cx(50)
        .cy(50)
        .r(40)
        .stroke('green')
        .strokeWidth(4)
        .fill('yellow')

        assertEquals('<circle cx="50" cy="50" r="40" stroke="green" stroke-width="4" fill="yellow" />', circle.toXml())
    }
}
