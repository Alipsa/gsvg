package se.alipsa.groovy.svg

/**
 * The Tspan element is used to mark up parts of a text (just like the HTML <span> element).
 * It must be a child of a <text> element or another <tspan> element.
 */
class Tspan implements SvgElement {

    /**The x position of the start of the text. Default is 0 */
    Number x

    /** The y position of the start of the text. Default is 0 */
    Number y

    /** The horizontal shift position for text (from previous text position) */
    Number dx

    /** The vertical shift position for text (from previous text position)*/
    Number dy

    /** The rotation (in degrees) applied to each letter of text */
    Number rotate

    /** The width that the text must fit in */
    Number textLength

    List<Tspan> tspans

    @Override
    String toXml() {
        return null
    }
}
