package se.alipsa.groovy.svg

/**
 * SVG {@code <a>} element that makes its children into a hyperlink.
 */
class A extends AbstractElementContainer<A> implements GradientContainer, Animatable<A> {

    static final String NAME = 'a'

    /**
     * Creates a A.
     *
     * @param parent value
     */
    A(SvgElement<? extends SvgElement> parent) {
        super(parent, NAME)
    }

    /**
     * Creates a A.
     *
     * @param parent value
     * @param href value
     */
    A(SvgElement<? extends SvgElement> parent, String href) {
        super(parent, NAME)
        href(href)
    }

    /**
     * Sets the href attribute.
     *
     * @param href value
     * @return this element for chaining
     */
    A href(String href) {
        addAttribute('href', href)
    }

    /**
     * Returns the href value.
     *
     * @return the href value
     */
    String getHref() {
        getAttribute('href')
    }

    /**
     * Sets the xlink href attribute.
     *
     * @param href value
     * @return this element for chaining
     */
    A xlinkHref(String href) {
        addAttribute(xlink('href'), href)
    }

    /**
     * Returns the xlink href value.
     *
     * @return the xlink href value
     */
    String getXlinkHref() {
        getAttribute(xlink('href'))
    }

    /**
     * Sets the target attribute.
     *
     * @param target value
     * @return this element for chaining
     */
    A target(String target) {
        addAttribute('target', target)
    }

    /**
     * Returns the target value.
     *
     * @return the target value
     */
    String getTarget() {
        getAttribute('target')
    }

}
