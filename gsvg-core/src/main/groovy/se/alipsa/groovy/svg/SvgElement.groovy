package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Attribute
import org.dom4j.Element
import org.dom4j.Namespace
import org.dom4j.QName
import se.alipsa.groovy.svg.utils.NumberFormatter

/**
 * Base class for all SVG elements backed by DOM4J elements.
 *
 * <h2>Constructor Pattern</h2>
 * Each concrete SvgElement subclass provides two types of constructors:
 *
 * <ol>
 * <li><b>Creating constructors</b> - Create new DOM elements and add them to the parent:
 *     <pre>ClassName(SvgElement parent)</pre>
 *     These constructors call {@code super(parent, NAME)} to create a new DOM element.
 * </li>
 * <li><b>Adopting constructors</b> - Adopt existing DOM elements for cloning/copying:
 *     <pre>ClassName(SvgElement parent, Element element)</pre>
 *     These constructors call {@code super(parent, element)} to wrap an existing DOM element.
 *     Used by {@link SvgElementFactory} for pure object-oriented element copying without
 *     XML serialization.
 * </li>
 * </ol>
 *
 * <h2>Cloning</h2>
 * All SvgElement instances can be cloned using the {@link #clone(AbstractElementContainer)} method,
 * which creates a deep copy including all child elements and attributes. The cloning uses the
 * pure object-oriented approach without XML serialization for optimal performance.
 *
 * <h3>Example:</h3>
 * <pre>
 * Circle original = svg.addCircle().cx(50).cy(50).r(25)
 * Circle copy = original.clone(targetSvg)
 * </pre>
 *
 * The adopting constructor pattern enables efficient deep copying of SVG element trees
 * while maintaining both the DOM4J structure and the SvgElement object hierarchy.
 *
 * @see SvgElementFactory#deepCopy(SvgElement, AbstractElementContainer)
 */
@CompileStatic
abstract class SvgElement<T extends SvgElement<T>> implements ElementContainer, Cloneable {

  Namespace xlinkNs = new Namespace('xlink', 'http://www.w3.org/1999/xlink')
  Element element
  SvgElement<? extends SvgElement> parent

  Desc desc
  Title title

  /**
   * Serializes this element and its children as XML.
   *
   * @return the result
   */
  String toXml() {
    element.asXML()
  }

  /**
   * Returns a brief string representation suitable for debugging.
   * Shows the element name, all attributes, and parent information.
   * For full XML serialization, use {@link #toXml()} instead.
   *
   * @return a concise string representation for debugging
   */
  @Override
  String toString() {
    StringBuilder sb = new StringBuilder()
    sb.append(getName())
    sb.append('(')

    boolean hasContent = false

    // Add id first if present
    String id = getId()
    if (id != null) {
      sb.append("id=").append(id)
      hasContent = true
    }

    // Add all other attributes (except id which we already showed)
    List attributes = element.attributes()
    for (Object attrObj : attributes) {
      Attribute attr = (Attribute) attrObj
      String attrName = attr.getName()

      // Skip id since we already showed it
      if ('id' == attrName) {
        continue
      }

      if (hasContent) {
        sb.append(', ')
      }
      sb.append(attrName).append('=').append(attr.getValue())
      hasContent = true
    }

    // Add parent information
    if (parent != null && parent != this) {
      if (hasContent) {
        sb.append(', ')
      }
      sb.append('parent=').append(parent.getName())
      String parentId = parent.getId()
      if (parentId != null) {
        sb.append('(id=').append(parentId).append(')')
      }
    }

    sb.append(')')
    return sb.toString()
  }

  /**
   * Creates a SvgElement.
   *
   * @param parent the parent SVG element
   * @param name the name of the element
   */
  SvgElement(SvgElement<? extends SvgElement> parent, String name) {
    this.parent = parent
    element = parent.element.addElement(name)
  }

  /**
   * Creates a SvgElement.
   *
   * @param parent the parent SVG element
   * @param name the name of the element
   * @param defaultNameSpace the default namespace
   */
  SvgElement(SvgElement<? extends SvgElement> parent, String name, String defaultNameSpace) {
    this.parent = parent
    element = parent.element.addElement(name, defaultNameSpace)
  }

  /**
   * Creates a SvgElement.
   *
   * @param parent the parent SVG element
   * @param qName the qualified name of the element
   */
  SvgElement(SvgElement<? extends SvgElement> parent, QName qName) {
    this.parent = parent
    element = parent.element.addElement(qName)
  }

  /**
   * Special constructor for Svg as it does not have a parent
   * Use this only to create Svg elements!
   */
  SvgElement(Element element) {
    this.element = element
  }

  /**
   * Creates a SvgElement by adopting an existing DOM4J Element.
   * This constructor is used for cloning/copying elements.
   * The element is detached from its old parent and added to the new parent.
   *
   * @param parent the parent SVG element
   * @param existingElement the DOM4J element to adopt
   */
  protected SvgElement(SvgElement<? extends SvgElement> parent, Element existingElement) {
    this.parent = parent
    Element cloned = existingElement.createCopy()
    this.element = cloned
    parent.element.add(cloned)
  }

  /**
   * Creates a deep clone of this element with all its children and attributes.
   * This is the recommended way to clone SVG elements as it uses the pure object-oriented
   * approach without XML serialization for optimal performance.
   *
   * <p>The clone will be attached to the specified parent container and will include:
   * <ul>
   * <li>All attributes from the original element</li>
   * <li>All child elements (recursively cloned)</li>
   * <li>Proper parent-child relationships in both DOM and object model</li>
   * </ul>
   *
   * <h3>Example:</h3>
   * <pre>
   * // Create an original element with children
   * G originalGroup = svg.addG().id("original")
   * originalGroup.addCircle().cx(10).cy(10).r(5)
   * originalGroup.addRect().x(20).y(20).width(10).height(10)
   *
   * // Clone it to another SVG
   * G clonedGroup = originalGroup.clone(anotherSvg)
   * // clonedGroup now has the same structure and children as originalGroup
   * </pre>
   *
   * @param newParent the parent container for the cloned element
   * @return a deep clone of this element with type safety preserved
   * @see SvgElementFactory#deepCopy(SvgElement, AbstractElementContainer)
   */
  T clone(AbstractElementContainer newParent) {
    return SvgElementFactory.deepCopy(this, newParent) as T
  }

  /**
   * Creates a clone of this element with the specified modifications.
   * <p>
   * This is a convenience method that clones the element and immediately
   * applies a set of attribute modifications. Useful for creating variations
   * of elements.
   * <p>
   * Example:
   * <pre>
   * Circle original = svg.addCircle().cx(100).cy(100).r(50).fill('red')
   * Circle modified = original.cloneWith(svg, [fill: 'blue', r: 30])
   * // Creates a new circle with same cx/cy but blue fill and radius 30
   * </pre>
   *
   * @param newParent the parent container for the cloned element
   * @param modifications map of attribute names to new values
   * @return a clone of this element with modifications applied
   * @since 0.9.0
   */
  T cloneWith(AbstractElementContainer newParent, Map modifications) {
    T cloned = clone(newParent)
    modifications.each { key, value ->
      cloned.addAttribute(String.valueOf(key), value)
    }
    return cloned
  }

  /**
   * Adds a namespace declaration to this element.
   *
   * @param prefix the namespace prefix
   * @param uri the URI
   * @return this element for chaining
   */
  T addNamespace(String prefix, String uri) {
    element.addNamespace(prefix, uri)
    this as T
  }

  /**
   * Returns the value of the requested attribute.
   *
   * @param name the name of the element
   * @return the result
   */
  String getAttribute(String name) {
    if(name.contains(':')) {
      getAttribute(getQName(name))
    } else {
      element.attributeValue(name)
    }
  }

  /**
   * Checks if this element has the specified attribute.
   *
   * @param name the name of the attribute
   * @return true if the attribute exists, false otherwise
   */
  boolean hasAttribute(String name) {
    if(name.contains(':')) {
      element.attribute(getQName(name)) != null
    } else {
      element.attribute(name) != null
    }
  }

  /**
   * Returns the value of the requested attribute.
   *
   * @param name the name of the element
   * @return the result
   */
  String getAttribute(QName name) {
    element.attributeValue(name)
  }

  /**
   * Returns the value of the requested attribute.
   *
   * @param nsPrefix the namespace prefix
   * @param localName the local name of the element
   * @return the result
   */
  String getAttribute(String nsPrefix, String localName) {
    getAttribute(getQName(nsPrefix, localName))
  }

  /**
   * Returns the value of the requested attribute, or a default value if the attribute doesn't exist.
   * <p>
   * This is a null-safe accessor that prevents NullPointerExceptions when working with
   * optional attributes.
   * <p>
   * Example:
   * <pre>
   * String x = rect.getAttributeOrDefault('x', '0')
   * String fill = circle.getAttributeOrDefault('fill', 'black')
   * </pre>
   *
   * @param name the name of the attribute
   * @param defaultValue the value to return if the attribute doesn't exist
   * @return the attribute value, or defaultValue if the attribute is null or missing
   */
  String getAttributeOrDefault(String name, String defaultValue) {
    String value = getAttribute(name)
    value != null ? value : defaultValue
  }

  /**
   * Returns the value of the requested attribute, or a default value if the attribute doesn't exist.
   *
   * @param qname the qualified name of the attribute
   * @param defaultValue the value to return if the attribute doesn't exist
   * @return the attribute value, or defaultValue if the attribute is null or missing
   */
  String getAttributeOrDefault(QName qname, String defaultValue) {
    String value = getAttribute(qname)
    value != null ? value : defaultValue
  }

  /**
   * Returns the value of the requested attribute, or a default value if the attribute doesn't exist.
   *
   * @param nsPrefix the namespace prefix
   * @param localName the local name of the element
   * @param defaultValue the value to return if the attribute doesn't exist
   * @return the attribute value, or defaultValue if the attribute is null or missing
   */
  String getAttributeOrDefault(String nsPrefix, String localName, String defaultValue) {
    String value = getAttribute(nsPrefix, localName)
    value != null ? value : defaultValue
  }

  /**
   * Returns all attributes as a map of attribute names to values.
   * <p>
   * This is useful when you need to iterate over all attributes of an element,
   * such as when searching for patterns across all attribute values.
   * </p>
   * <p>Example:</p>
   * <pre>
   * Map&lt;String, String&gt; attrs = circle.getAttributes()
   * attrs.each { name, value ->
   *   println "$name = $value"
   * }
   * </pre>
   *
   * @return a map of attribute names to values (never null, empty map if no attributes)
   */
  Map<String, String> getAttributes() {
    element.attributes().collectEntries { attr ->
      [(attr.name): attr.value]
    } as Map<String, String>
  }

  /**
   * Adds an attribute to this element.
   * <p>
   * Numeric values are automatically formatted with configurable precision
   * (default: 3 decimal places). This reduces file size while maintaining
   * visual quality.
   *
   * @param name the name of the element
   * @param value the value
   * @return this element for chaining
   */
  T addAttribute(String name, Object value) {
    if (name.contains(':')) {
      addAttribute(getQName(name), value)
    } else {
      String formatted = NumberFormatter.format(value, getDocumentPrecision())
      element.addAttribute(name, formatted)
    }
    this as T
  }

  /**
   * Adds an attribute to this element.
   *
   * @param prefix the namespace prefix
   * @param localName the local name of the element
   * @param value the value
   * @return this element for chaining
   */
  T addAttribute(String prefix, String localName, Object value) {
    addAttribute(getQName(prefix, localName), value)
  }

  /**
   * Adds an attribute to this element.
   * <p>
   * Numeric values are automatically formatted with configurable precision.
   *
   * @param qname the qualified name
   * @param value the value
   * @return this element for chaining
   */
  T addAttribute(QName qname, Object value) {
    String formatted = NumberFormatter.format(value, getDocumentPrecision())
    element.addAttribute(qname, formatted)
    this as T
  }

  /**
   * Adds a raw attribute without numeric formatting.
   * Intended for parsing paths where attribute values should be preserved.
   *
   * @param name the attribute name
   * @param value the attribute value
   * @return this element for chaining
   */
  T addAttributeRaw(String name, String value) {
    if (value == null) {
      return this as T
    }
    if (name.contains(':')) {
      addAttributeRaw(getQName(name), value)
    } else {
      element.addAttribute(name, value)
    }
    this as T
  }

  /**
   * Adds a raw attribute without numeric formatting.
   *
   * @param prefix the namespace prefix
   * @param localName the local name
   * @param value the attribute value
   * @return this element for chaining
   */
  T addAttributeRaw(String prefix, String localName, String value) {
    addAttributeRaw(getQName(prefix, localName), value)
  }

  /**
   * Adds a raw attribute without numeric formatting.
   *
   * @param qname the qualified name
   * @param value the attribute value
   * @return this element for chaining
   */
  T addAttributeRaw(QName qname, String value) {
    if (value == null) {
      return this as T
    }
    element.addAttribute(qname, value)
    this as T
  }

  /**
   * Adds multiple attributes to this element.
   * This is an alias for {@link #addAttributes(Map)} for consistency.
   *
   * @param attributes the attributes
   * @return this element for chaining
   */
  T attrs(Map<String, Object> attributes) {
    addAttributes(attributes)
  }

  /**
   * Adds multiple attributes to this element.
   *
   * @param attributes the attributes
   * @return this element for chaining
   */
  T addAttributes(Map<String, Object> attributes) {
    attributes.each {
      String key = it.key
      if ('strokeWidth' == key) {
        key = 'stroke-width'
      }
      addAttribute(key, it.value)
    }
    this as T
  }

  /**
   * Updates an existing attribute on this element.
   *
   * @param attribute the attribute
   * @param value the value
   * @return this element for chaining
   */
  T changeAttribute(String attribute, Object value) {
    element.attribute(attribute)?.setValue("$value")
    this as T
  }

  /**
   * Removes an attribute from this element.
   *
   * @param attribute the attribute
   * @return this element for chaining
   */
  T removeAttribute(String attribute) {
    def attr = element.attribute(attribute)
    if (attr != null) {
      element.remove(attr)
    }
    this as T
  }

  /**
   * Removes all attributes that match the given predicate.
   * <p>Example:</p>
   * <pre>
   * // Remove all data- attributes
   * circle.removeAttributes { it.startsWith('data-') }
   *
   * // Remove all aria- attributes
   * rect.removeAttributes { it.startsWith('aria-') }
   *
   * // Remove specific attributes
   * element.removeAttributes { it in ['opacity', 'transform', 'filter'] }
   * </pre>
   *
   * @param predicate a closure that takes an attribute name and returns true if it should be removed
   * @return the number of attributes removed
   */
  int removeAttributes(Closure<Boolean> predicate) {
    def attributesToRemove = getAttributes().keySet().findAll(predicate)
    attributesToRemove.each { removeAttribute(it) }
    return attributesToRemove.size()
  }

  /**
   * Gets the numeric precision setting for this element's document.
   * <p>
   * Walks up the parent chain to find the root Svg element and returns
   * its precision setting. Returns null if no custom precision is set,
   * which causes NumberFormatter to use the global default.
   *
   * @return the document's precision setting, or null for global default
   */
  private Integer getDocumentPrecision() {
    SvgElement current = this
    while (current != null) {
      if (current instanceof Svg) {
        return ((Svg) current).getEffectivePrecision()
      }
      current = current.parent
    }
    return null  // Use global default
  }

  /**
   * Sets the style attribute.
   *
   * @param style the style
   * @return this element for chaining
   */
  T style(String style) {
    addAttribute('style', style)
  }

  /**
   * Sets the inline style attribute using a map of CSS properties.
   *
   * @param styleProps the CSS properties as a map (e.g., [fill: 'red', stroke: 'blue'])
   * @return this element for chaining
   */
  T style(Map<String, Object> styleProps) {
    if (styleProps == null || styleProps.isEmpty()) {
      addAttribute('style', '')
    } else {
      String styleText = se.alipsa.groovy.svg.css.CssParser.toInlineStyle(
        styleProps.collectEntries { k, v -> [(k as String): (v as String)] } as Map<String, String>
      )
      addAttribute('style', styleText)
    }
    this as T
  }

  /**
   * Gets the inline style attribute as a map of CSS properties.
   *
   * @return a map of CSS property names to values
   */
  Map<String, String> getStyleMap() {
    String styleAttr = getAttribute('style')
    if (!styleAttr || styleAttr.trim().isEmpty()) {
      return [:]
    }
    return se.alipsa.groovy.svg.css.CssParser.parseInlineStyle(styleAttr)
  }

  /**
   * Gets a specific CSS property from the inline style attribute.
   *
   * @param property the CSS property name
   * @return the property value, or null if not found
   */
  String getStyleProperty(String property) {
    getStyleMap().get(property)
  }

  /**
   * Sets the style class attribute.
   *
   * @param styleClass the CSS style class
   * @return this element for chaining
   */
  T styleClass(String styleClass) {
    addAttribute('class', styleClass)
  }

  /**
   * Adds a CSS class to the element's class attribute.
   * If the class already exists, it will not be added again.
   *
   * @param className the CSS class name to add
   * @return this element for chaining
   */
  T addClass(String className) {
    if (!className || className.trim().isEmpty()) {
      return this as T
    }
    List<String> classes = getClasses()
    if (!classes.contains(className)) {
      classes.add(className)
      addAttribute('class', classes.join(' '))
    }
    this as T
  }

  /**
   * Removes a CSS class from the element's class attribute.
   *
   * @param className the CSS class name to remove
   * @return this element for chaining
   */
  T removeClass(String className) {
    if (!className || className.trim().isEmpty()) {
      return this as T
    }
    List<String> classes = getClasses()
    if (classes.remove(className)) {
      addAttribute('class', classes.join(' '))
    }
    this as T
  }

  /**
   * Toggles a CSS class on the element.
   * If the class exists, it is removed; if it doesn't exist, it is added.
   *
   * @param className the CSS class name to toggle
   * @return this element for chaining
   */
  T toggleClass(String className) {
    if (hasClass(className)) {
      removeClass(className)
    } else {
      addClass(className)
    }
    this as T
  }

  /**
   * Checks if the element has the specified CSS class.
   *
   * @param className the CSS class name to check
   * @return true if the class exists, false otherwise
   */
  boolean hasClass(String className) {
    if (!className || className.trim().isEmpty()) {
      return false
    }
    return getClasses().contains(className)
  }

  /**
   * Gets all CSS classes from the element's class attribute.
   *
   * @return a list of CSS class names
   */
  List<String> getClasses() {
    String classAttr = getAttribute('class')
    if (!classAttr || classAttr.trim().isEmpty()) {
      return []
    }
    return classAttr.split(/\s+/).findAll { it } as List<String>
  }

  /**
   * Sets the id attribute.
   *
   * @param id the unique identifier
   * @return this element for chaining
   */
  T id(String id) {
    addAttribute('id', id)
  }

  /**
   * Returns the id value.
   *
   * @return the id value
   */
  String getId() {
    getAttribute('id')
  }

  /**
   * Sets the filter attribute.
   *
   * @param filter the filter
   * @return this element for chaining
   */
  T filter(String filter) {
    addAttribute('filter', filter)
  }

  // TODO: Intellij does not recognize the type
  /**
   * Returns the parent value.
   *
   * @return the parent value
   */
  <P extends SvgElement<P>> P getParent() {
    Class<P> clazz = parent.getClass() as Class<P>
    getParent(clazz)
  }

  /**
   * Get parent.
   *
   * @param type the type
   * @return the result
   */
  <P extends SvgElement<P>> P getParent(Class<P> type) {
    return type.cast(parent)
  }

  /**
   * Returns the name value.
   *
   * @return the name value
   */
  String getName() {
    element.getName()
  }

  /**
   * Returns the xlink ns value.
   *
   * @return the xlink ns value
   */
  Namespace getXlinkNs() {
    xlinkNs
  }

  /**
   * Creates and adds a new Title child element.
   *
   * @return the created element
   */
  Title addTitle() {
    title = add(new Title(this))
    return title
  }

  /**
   * Creates and adds a new Title child element.
   *
   * @param content the content
   * @return the created element
   */
  Title addTitle(String content) {
    title = new Title(this).addContent(content)
  }

  Title addTitle(Map attributes) {
    Title t = addTitle()
    attributes.each {
      key, value -> t.addAttribute(String.valueOf(key), value)
    }
    t
  }

  /**
   * Returns the title value.
   *
   * @return the title value
   */
  Title getTitle() {
    title
  }

  /**
   * Creates and adds a new Desc child element.
   *
   * @return the created element
   */
  Desc addDesc() {
    desc = add(new Desc(this))
    return desc
  }

  /**
   * Creates and adds a new Desc child element.
   *
   * @param content the content
   * @return the created element
   */
  Desc addDesc(String content) {
    desc = new Desc(this).addContent(content)
  }

  Desc addDesc(Map attributes) {
    Desc d = addDesc()
    attributes.each {
      key, value -> d.addAttribute(String.valueOf(key), value)
    }
    d
  }

  /**
   * Returns the desc value.
   *
   * @return the desc value
   */
  Desc getDesc() {
    desc
  }

  // ==================== ARIA ACCESSIBILITY HELPERS ====================

  /**
   * Sets the role attribute for accessibility.
   * <p>
   * Common values for SVG graphics:
   * <ul>
   *   <li>'img' - Marks the SVG as an image</li>
   *   <li>'graphics-document' - Container for graphic elements</li>
   *   <li>'graphics-symbol' - Reusable graphic symbol</li>
   *   <li>'presentation' - Decorative element (hidden from assistive technology)</li>
   * </ul>
   *
   * @param role the ARIA role
   * @return this element for chaining
   */
  T role(String role) {
    addAttribute('role', role)
  }

  /**
   * Returns the role attribute value.
   *
   * @return the role, or null if not set
   */
  String getRole() {
    getAttribute('role')
  }

  /**
   * Sets the aria-label attribute for accessibility.
   * <p>
   * Provides a text alternative that screen readers announce to users.
   * Use this to give a concise, descriptive label for the element.
   * <p>
   * Example:
   * <pre>
   * svg.ariaLabel('Sales chart for Q1 2026')
   * </pre>
   *
   * @param label the accessible label
   * @return this element for chaining
   */
  T ariaLabel(String label) {
    addAttribute('aria-label', label)
  }

  /**
   * Returns the aria-label attribute value.
   *
   * @return the label, or null if not set
   */
  String getAriaLabel() {
    getAttribute('aria-label')
  }

  /**
   * Sets the aria-labelledby attribute.
   * <p>
   * References one or more element IDs that label this element.
   * Screen readers will use the text content of the referenced elements
   * as the accessible name.
   * <p>
   * Example:
   * <pre>
   * svg.addTitle().id('chart-title').content('Sales Chart')
   * svg.ariaLabelledBy('chart-title')
   * </pre>
   *
   * @param ids space-separated list of element IDs
   * @return this element for chaining
   */
  T ariaLabelledBy(String ids) {
    addAttribute('aria-labelledby', ids)
  }

  /**
   * Returns the aria-labelledby attribute value.
   *
   * @return the IDs, or null if not set
   */
  String getAriaLabelledBy() {
    getAttribute('aria-labelledby')
  }

  /**
   * Sets the aria-describedby attribute.
   * <p>
   * References one or more element IDs that describe this element.
   * Screen readers will announce this description after the accessible name.
   * <p>
   * Example:
   * <pre>
   * svg.addDesc().id('chart-desc').content('Monthly sales from Jan to Mar')
   * svg.ariaDescribedBy('chart-desc')
   * </pre>
   *
   * @param ids space-separated list of element IDs
   * @return this element for chaining
   */
  T ariaDescribedBy(String ids) {
    addAttribute('aria-describedby', ids)
  }

  /**
   * Returns the aria-describedby attribute value.
   *
   * @return the IDs, or null if not set
   */
  String getAriaDescribedBy() {
    getAttribute('aria-describedby')
  }

  /**
   * Sets the aria-hidden attribute.
   * <p>
   * When true, hides this element and its children from assistive technologies
   * like screen readers. Use this for decorative graphics that don't convey
   * meaningful information.
   * <p>
   * Example:
   * <pre>
   * decorativeRect.ariaHidden(true)
   * </pre>
   *
   * @param hidden true to hide from screen readers, false to expose
   * @return this element for chaining
   */
  T ariaHidden(boolean hidden) {
    addAttribute('aria-hidden', String.valueOf(hidden))
  }

  /**
   * Returns the aria-hidden attribute value.
   *
   * @return true if hidden from screen readers, false otherwise
   */
  boolean isAriaHidden() {
    'true'.equalsIgnoreCase(getAttribute('aria-hidden'))
  }

  /**
   * Sets the aria-live attribute for dynamic content.
   * <p>
   * Indicates that an element will be updated, and describes the types
   * of updates screen readers should announce to users.
   * <p>
   * Valid values:
   * <ul>
   *   <li>'off' - Updates will not be announced (default)</li>
   *   <li>'polite' - Announce updates when user is idle</li>
   *   <li>'assertive' - Announce updates immediately, interrupting user</li>
   * </ul>
   *
   * @param live the live region mode ('off', 'polite', or 'assertive')
   * @return this element for chaining
   */
  T ariaLive(String live) {
    addAttribute('aria-live', live)
  }

  /**
   * Returns the aria-live attribute value.
   *
   * @return the live mode, or null if not set
   */
  String getAriaLive() {
    getAttribute('aria-live')
  }

  /**
   * Convenience method to mark an element as decorative.
   * <p>
   * Sets both role='presentation' and aria-hidden='true' to hide the element
   * from assistive technologies. Use this for purely visual elements that
   * don't convey meaningful information.
   * <p>
   * Example:
   * <pre>
   * backgroundRect.decorative()
   * </pre>
   *
   * @return this element for chaining
   */
  T decorative() {
    role('presentation').ariaHidden(true)
  }

  /**
   * Convenience method to set up basic accessibility for an image-like graphic.
   * <p>
   * Sets role='img' and aria-label in one call. This is the recommended
   * pattern for making SVG graphics accessible.
   * <p>
   * Example:
   * <pre>
   * svg.accessibleImage('Company logo')
   * </pre>
   *
   * @param label the accessible label describing the graphic
   * @return this element for chaining
   */
  T accessibleImage(String label) {
    role('img').ariaLabel(label)
  }

  /**
   * Builds an xlink-qualified name for the provided local name.
   *
   * @param localName the local name of the element
   * @return the result
   */
  QName xlink(String localName) {
    if (localName == null || localName.isBlank()) {
      throw new IllegalArgumentException("Local name must not be blank for xlink")
    }
    new QName(localName, xlinkNs)
  }

  /**
   * Resolves a QName for the provided name.
   *
   * @param name the name of the element
   * @return the result
   */
  QName getQName(String name) {
    int idx = name.indexOf(':')
    if (idx < 0) {
      throw new IllegalArgumentException("Name '$name' must contain a namespace prefix")
    }
    String prefix = name.substring(0, idx)
    String localName = name.substring(idx + 1)
    if (localName.isBlank()) {
      throw new IllegalArgumentException("Local name must not be blank in '$name'")
    }
    getQName(prefix, localName)
  }

  /**
   * Resolves a QName for the provided name.
   *
   * @param prefix the namespace prefix
   * @param localName the local name of the element
   * @return the result
   */
  QName getQName(String prefix, String localName) {
    Namespace ns = element.getNamespaceForPrefix(prefix)
    if (ns == null) {
      throw new IllegalArgumentException("No namespace bound for prefix '$prefix' on element '${element.getName()}'")
    }
    if (localName == null || localName.isBlank()) {
      throw new IllegalArgumentException("Local name must not be blank for prefix '$prefix'")
    }
    new QName(localName, ns)
  }

  /**
   * Overrides Groovy's property access to provide automatic fallback to getAttribute().
   * <p>
   * This allows intuitive property-style access to SVG attributes. When accessing a property
   * that doesn't have an explicit getter (e.g., {@code element.values}), this method will
   * automatically try to retrieve it as an attribute using {@code getAttribute('values')}.
   * <p>
   * This maintains backward compatibility with existing explicit getters while providing
   * a more natural API for accessing arbitrary SVG attributes.
   *
   * @param propertyName the name of the property to access
   * @return the property value, or the attribute value if no property exists
   */
  @Override
  Object getProperty(String propertyName) {
    // Check if a real property/getter exists using MetaClass
    def metaProperty = this.metaClass.hasProperty(this, propertyName)
    if (metaProperty != null) {
      return metaProperty.getProperty(this)
    }

    // If no property exists, try to get it as an attribute
    return getAttribute(propertyName)
  }

  /**
   * Overrides Groovy's property assignment to provide automatic fallback to addAttribute().
   * <p>
   * This allows intuitive property-style assignment to SVG attributes. When assigning to a property
   * that doesn't have an explicit setter (e.g., {@code element.values = '0.5'}), this method will
   * automatically set it as an attribute using {@code addAttribute('values', '0.5')}.
   * <p>
   * This maintains backward compatibility with existing explicit setters while providing
   * a more natural API for setting arbitrary SVG attributes.
   * <p>
   * <b>Note:</b> Use with care - typos in property names will silently create incorrect attributes
   * rather than failing at compile time. For critical code, consider using the explicit fluent API
   * methods (e.g., {@code .gradientUnits('value')}) which provide better IDE autocomplete support.
   *
   * @param propertyName the name of the property to set
   * @param newValue the value to assign
   */
  @Override
  void setProperty(String propertyName, Object newValue) {
    // Check if a method exists that matches the property name (fluent API style)
    // This handles methods like x1(String) that return 'this' for chaining
    def metaMethod = this.metaClass.getMetaMethod(propertyName, newValue)
    if (metaMethod != null) {
      // Call the fluent API method
      metaMethod.invoke(this, newValue)
      return
    }

    // Check if a real property/setter exists using MetaClass
    def metaProperty = this.metaClass.hasProperty(this, propertyName)
    if (metaProperty != null) {
      try {
        // Try to use the real property setter
        metaProperty.setProperty(this, newValue)
        return
      } catch (GroovyRuntimeException e) {
        // If it's read-only (fluent API), fall through to addAttribute
        if (!e.message?.contains('read-only')) {
          throw e
        }
      }
    }

    // If no setter exists or it's read-only, set it as an attribute
    addAttribute(propertyName, newValue)
  }

  /**
   * Validates this element and all its descendants using default validation rules.
   * <p>
   * Applies all default validation rules to this element's subtree and returns
   * a report with any issues found. This validates just this element's subtree,
   * not the entire document.
   * <p>
   * For document-wide validation, use {@link Svg#validate()} on the root element.
   *
   * @return validation report with all issues found
   */
  se.alipsa.groovy.svg.validation.ValidationReport validate() {
    se.alipsa.groovy.svg.validation.ValidationEngine.createDefault().validate(this)
  }

  /**
   * Checks if this element passes validation (no errors).
   * <p>
   * This is a convenience method equivalent to {@code validate().isValid()}.
   * Note that warnings and info messages don't prevent validation from passing.
   *
   * @return true if validation passes (no errors)
   */
  boolean isValid() {
    validate().isValid()
  }

}
