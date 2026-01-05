package se.alipsa.groovy.svg

/**
 * Trait for filter primitives that can contain light source elements.
 */
trait LightSourceContainer<T extends FilterElement<T>> {

  LightSourceElement lightSource

  /**
   * Creates and adds a new FeDistantLight child element.
   *
   * @return the created element
   */
  FeDistantLight addFeDistantLight() {
    (lightSource = new FeDistantLight(this))
  }

  /**
   * Creates and adds a new FePointLight child element.
   *
   * @return the created element
   */
  FePointLight addFePointLight() {
    (lightSource = new FePointLight(this))
  }

  /**
   * Creates and adds a new FeSpotLight child element.
   *
   * @return the created element
   */
  FeSpotLight addFeSpotLight() {
    (lightSource = new FeSpotLight(this))
  }

  /**
   * Returns the light source value.
   *
   * @return the light source value
   */
  T getLightSource() {
    lightSource as T
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr the input source string identifier
   * @return this element for chaining
   */
  T 'in'(String inStr) {
    addAttribute('in', inStr) as T
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum the input source enumeration
   * @return this element for chaining
   */
  T 'in'(In inEnum) {
    addAttribute('in', inEnum.name()) as T
  }

  /**
   * Returns the in value.
   *
   * @return the in value
   */
  String getIn() {
    getAttribute('in')
  }

  /**
   * Sets the lighting color attribute.
   *
   * @param color the color
   * @return this element for chaining
   */
  T lightingColor(String color) {
    addAttribute('lighting-color', color) as T
  }

  /**
   * Returns the lighting color value.
   *
   * @return the lighting color value
   */
  String getLightingColor() {
    getAttribute('lighting-color')
  }
}
