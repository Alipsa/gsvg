package se.alipsa.groovy.svg

import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.css.CssRule
import se.alipsa.groovy.svg.css.CssStyleSheet

/**
 * SVG {@code <style>} element that defines CSS rules for the document.
 *
 * <p>Supports both traditional text-based CSS and object-oriented CSS rule management:
 * <pre>{@code
 * // Traditional string API
 * svg.addStyle().addContent('.highlight { fill: red; }')
 *
 * // Object-oriented CSS API
 * svg.addStyle()
 *    .addRule('.highlight', [fill: 'red', stroke: 'black'])
 *    .addRule('#logo', [transform: 'scale(2)'])
 * }</pre>
 */
@CompileStatic
class Style extends StringContentContainer<Style> {

  static final String NAME='style'

  /**
   * Creates a Style.
   *
   * @param parent the parent SVG element
   */
  Style(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  Style(SvgElement parent, Element element) {
    super(parent, element)
  }


  /**
   * Sets the type attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Style type(String value) {
    addAttribute('type', value)
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
   * Adds a CSS rule to this style element.
   *
   * @param selector the CSS selector (e.g., ".highlight", "#logo")
   * @param declarations the CSS property declarations as a map
   * @return this element for chaining
   */
  Style addRule(String selector, Map<String, String> declarations) {
    CssStyleSheet stylesheet = getStyleSheet()
    stylesheet.addRule(selector, declarations)
    setStyleSheet(stylesheet)
    this
  }

  /**
   * Adds a CSS rule to this style element.
   *
   * @param rule the CSS rule to add
   * @return this element for chaining
   */
  Style addRule(CssRule rule) {
    if (rule != null) {
      CssStyleSheet stylesheet = getStyleSheet()
      stylesheet.addRule(rule)
      setStyleSheet(stylesheet)
    }
    this
  }

  /**
   * Alias for {@link #addRule(String, Map)} for fluent API consistency.
   *
   * @param selector the CSS selector
   * @param declarations the CSS property declarations
   * @return this element for chaining
   */
  Style rule(String selector, Map<String, String> declarations) {
    addRule(selector, declarations)
  }

  /**
   * Removes all CSS rules with the specified selector.
   *
   * @param selector the CSS selector to remove
   * @return this element for chaining
   */
  Style removeRule(String selector) {
    CssStyleSheet stylesheet = getStyleSheet()
    stylesheet.removeRule(selector)
    setStyleSheet(stylesheet)
    this
  }

  /**
   * Gets the stylesheet by parsing the current content.
   * If the content is empty, returns an empty stylesheet.
   *
   * @return the parsed CSS stylesheet
   */
  CssStyleSheet getStyleSheet() {
    String content = getContent()
    if (!content || content.trim().isEmpty()) {
      return CssStyleSheet.empty()
    }
    try {
      return CssStyleSheet.parse(content)
    } catch (Exception e) {
      // If parsing fails, return empty stylesheet
      return CssStyleSheet.empty()
    }
  }

  /**
   * Sets the stylesheet by converting it to CSS text and replacing the content.
   *
   * @param stylesheet the CSS stylesheet
   * @return this element for chaining
   */
  Style setStyleSheet(CssStyleSheet stylesheet) {
    if (stylesheet == null || stylesheet.isEmpty()) {
      replaceContent('')
    } else {
      replaceContent(stylesheet.toString())
    }
    this
  }

  /**
   * Gets all CSS rules from this style element.
   *
   * @return a list of CSS rules
   */
  List<CssRule> getRules() {
    getStyleSheet().getRules()
  }
}