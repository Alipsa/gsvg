package se.alipsa.groovy.svg

enum FilterEffect {

    Blend('feBlend'),
    ColorMatrix('feColorMatrix'),
    feComponentTransfer,
    feComposite,
    feConvolveMatrix,
    feDiffuseLighting,
    feDisplacementMap,
    feDistantLight,
    feDropShadow,
    feFlood,
    feGaussianBlur,
    feImage,
    feMerge,
    feMergeNode,
    feMorphology,
    feOffset,
    fePointLight,
    feSpecularLighting,
    feSpotLight,
    feTile,
    feTurbulence

    String tagName

    FilterEffect(String tagName) {
        this.tagName = tagName
    }
}