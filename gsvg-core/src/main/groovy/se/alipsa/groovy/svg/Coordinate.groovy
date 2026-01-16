package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * Value object representing an x,y coordinate pair used in point lists.
 */
@CompileStatic
class Coordinate {

    Number x
    Number y

    /**
     * Creates a Coordinate.
     */
    Coordinate() {
    }

    /**
     * Creates a Coordinate.
     *
     * @param points the points defining the shape
     */
    Coordinate(List<Number> points) {
        x = points[0]
        y = points[1]
    }

    /**
     * Creates a Coordinate.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    Coordinate(Number x, Number y) {
        this.x = x
        this.y = y
    }

    /**
     * Sets the x attribute.
     *
     * @param x the x-coordinate
     * @return this element for chaining
     */
    Coordinate x(Number x) {
        this.x = x
        this
    }

    /**
     * Sets the y attribute.
     *
     * @param y the y-coordinate
     * @return this element for chaining
     */
    Coordinate y(Number y) {
        this.y = y
        this
    }

    /**
     * Returns the x value.
     *
     * @return the x value
     */
    Number getX() {
        return x
    }

    /**
     * Sets the x value.
     *
     * @param x the x-coordinate
     */
    void setX(Number x) {
        this.x = x
    }

    /**
     * Returns the y value.
     *
     * @return the y value
     */
    Number getY() {
        return y
    }

    /**
     * Sets the y value.
     *
     * @param y the y-coordinate
     */
    void setY(Number y) {
        this.y = y
    }

    /**
     * To string.
     *
     * @return the result
     */
    @Override
    String toString() {
        stringCoordinate()
    }

    /**
     * String coordinate.
     *
     * @return the result
     */
    String stringCoordinate() {
        "$x,$y"
    }
}