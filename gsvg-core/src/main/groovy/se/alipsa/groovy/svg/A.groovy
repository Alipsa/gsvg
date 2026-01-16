package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element

/**
 * SVG {@code <a>} element that makes its children into a hyperlink.
 */
@CompileStatic
class A extends AbstractElementContainer<A> implements GradientContainer, Animatable<A> {

    static final String NAME = 'a'

    /**
     * Creates a A.
     *
     * @param parent the parent SVG element
     */
    A(SvgElement<? extends SvgElement> parent) {
        super(parent, NAME)
    }

    /**
     * Creates a A.
     *
     * @param parent the parent SVG element
     * @param href the hyperlink URL
     */
    A(SvgElement<? extends SvgElement> parent, String href) {
        super(parent, NAME)
        this.href(href)
    }

    /**
     * Creates a A by adopting an existing DOM4J Element.
     * Used for cloning/copying operations.
     *
     * @param parent the parent SVG element
     * @param element the DOM4J element to adopt
     */
    A(SvgElement parent, Element element) {
        super(parent, element)
    }

    /**
     * Sets the href attribute.
     *
     * @param href the hyperlink URL
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
     * @param href the hyperlink URL
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
     * @param target the target
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

    /**
     * Sets the download attribute.
     *
     * @param filename the filename for download
     * @return this element for chaining
     */
    A download(String filename) {
        addAttribute('download', filename)
    }

    /**
     * Returns the download value.
     *
     * @return the download value
     */
    String getDownload() {
        getAttribute('download')
    }

    /**
     * Sets the type attribute (MIME type).
     *
     * @param type the MIME type
     * @return this element for chaining
     */
    A type(String type) {
        addAttribute('type', type)
    }

    /**
     * Returns the type value.
     *
     * @return the type value
     */
    String getType() {
        getAttribute('type')
    }

    /**
     * Sets the rel attribute.
     *
     * @param rel the relationship type
     * @return this element for chaining
     */
    A rel(String rel) {
        addAttribute('rel', rel)
    }

    /**
     * Returns the rel value.
     *
     * @return the rel value
     */
    String getRel() {
        getAttribute('rel')
    }

}
