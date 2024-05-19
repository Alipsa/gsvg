package se.alipsa.groovy.svg

class A extends AbstractElementContainer<A> implements GradientContainer {

    static final String NAME = 'a'

    A(SvgElement<? extends SvgElement> parent) {
        super(parent, NAME)
    }

    A(SvgElement<? extends SvgElement> parent, String href) {
        super(parent, NAME)
        href(href)
    }

    A href(String href) {
        addAttribute('href', href)
    }

    String getHref() {
        getAttribute('href')
    }

    A target(String target) {
        addAttribute('target', target)
    }

    String getTarget() {
        getAttribute('target')
    }

}
