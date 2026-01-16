package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.SelfType
import se.alipsa.groovy.svg.utils.Color

/**
 * Trait for filter primitives that can contain light source elements.
 * <p>
 * This trait uses the {@code @SelfType} annotation to declare that any class implementing
 * this trait must extend {@link FilterElement}. This allows the trait to call methods from
 * {@code FilterElement} (such as {@code addAttribute()}) while maintaining type safety under
 * static compilation. Without {@code @SelfType}, the static compiler would not be able to
 * verify these method calls.
 */
@CompileStatic
@SelfType(FilterElement)
trait LightSourceContainer<T extends FilterElement<T>> {

  LightSourceElement lightSource

  /**
   * Creates and adds a new FeDistantLight child element.
   *
   * @return the created element
   */
  FeDistantLight addFeDistantLight() {
    def light = new FeDistantLight(this)
    lightSource = light
    light
  }

  /**
   * Creates and adds a new FePointLight child element.
   *
   * @return the created element
   */
  FePointLight addFePointLight() {
    def light = new FePointLight(this)
    lightSource = light
    light
  }

  /**
   * Creates and adds a new FeSpotLight child element.
   *
   * @return the created element
   */
  FeSpotLight addFeSpotLight() {
    def light = new FeSpotLight(this)
    lightSource = light
    light
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
   * Sets the lighting color using a Color object.
   *
   * @param color the color
   * @return this element for chaining
   */
  T lightingColor(Color color) {
    addAttribute('lighting-color', color.toString()) as T
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
