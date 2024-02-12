package se.alipsa.groovy.svg

class Coordinate {

    Number x
    Number y

    Coordinate() {
    }

    Coordinate(List<Number> points) {
        x = points[0]
        y = points[1]
    }

    Coordinate(Number x, Number y) {
        this.x = x
        this.y = y
    }

    Coordinate x(Number x) {
        this.x = x
        this
    }

    Coordinate y(Number y) {
        this.y = y
        this
    }

    Number getX() {
        return x
    }

    void setX(Number x) {
        this.x = x
    }

    Number getY() {
        return y
    }

    void setY(Number y) {
        this.y = y
    }

    @Override
    String toString() {
        stringCoordinate()
    }

    String stringCoordinate() {
        "$x,$y"
    }
}
