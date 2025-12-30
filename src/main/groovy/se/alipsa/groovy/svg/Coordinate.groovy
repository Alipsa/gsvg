package se.alipsa.groovy.svg

/**
 * Value object representing an x,y coordinate pair used in point lists.
 */
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
     * @param points value
     */
    Coordinate(List<Number> points) {
        x = points[0]
        y = points[1]
    }

    /**
     * Creates a Coordinate.
     *
     * @param x value
     * @param y value
     */
    Coordinate(Number x, Number y) {
        this.x = x
        this.y = y
    }

    /**
     * Sets the x attribute.
     *
     * @param x value
     * @return this element for chaining
     */
    Coordinate x(Number x) {
        this.x = x
        this
    }

    /**
     * Sets the y attribute.
     *
     * @param y value
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
     * @param x value
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
     * @param y value
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
