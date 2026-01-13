package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * Trait that stores child elements and provides selection helpers.
 */
@CompileStatic
trait ElementContainer {

  private List<SvgElement<? extends SvgElement>> children = []

  /**
   * Returns the children value.
   *
   * @return the children value
   */
  List<SvgElement<? extends SvgElement>> getChildren() {
    children
  }

  /**
   * Adds a child element to this container.
   *
   * @param svgElement the SVG element
   * @return the added element
   */
  <E extends SvgElement> E add(E svgElement) {
    children.add(svgElement)
    svgElement
  }

  /**
   * Returns child element(s) for the provided selector.
   *
   * @param index the index
   * @return the result
   */
  SvgElement getAt(Integer index) {
    children[index]
  }

  /**
   * Returns child element(s) for the provided selector.
   *
   * @param name the name of the element
   * @return the result
   */
  List<SvgElement> getAt(String name) {
    children.stream().filter (e -> e.name == name).findAll()
  }

  /**
   * Returns child element(s) for the provided selector.
   *
   * @param clazz the CSS class
   * @return the result
   */
  List<SvgElement> getAt(Class<? extends SvgElement> clazz) {
    children.stream().filter (e -> e.class == clazz).findAll()
  }

  // ==================== ENHANCED SELECTION METHODS ====================

  /**
   * Filter child elements using a predicate closure.
   * <p>Example:</p>
   * <pre>
   * def largeRects = svg.filter { it instanceof Rect && it.getWidth() as int > 100 }
   * def redElements = svg.filter { it.getFill() == 'red' }
   * </pre>
   *
   * @param predicate a closure that returns true for elements to include
   * @return a list of matching elements
   */
  List<SvgElement> filter(Closure<Boolean> predicate) {
    children.findAll(predicate)
  }

  /**
   * Find all child elements of a specific type, optionally filtered by a predicate.
   * <p>Example:</p>
   * <pre>
   * def allCircles = svg.findAll(Circle)
   * def largeCircles = svg.findAll(Circle) { it.getR() as int > 50 }
   * </pre>
   *
   * @param clazz the element type to find
   * @param predicate optional predicate closure to filter results
   * @return a list of matching elements
   */
  <T extends SvgElement> List<T> findAll(Class<T> clazz, Closure<Boolean> predicate = null) {
    List<T> results = children.findAll { it.class == clazz } as List<T>
    predicate ? results.findAll(predicate) : results
  }

  /**
   * Find the first child element matching a predicate.
   * <p>Example:</p>
   * <pre>
   * def firstRed = svg.findFirst { it.getFill() == 'red' }
   * def logoElement = svg.findFirst { it.getId() == 'logo' }
   * </pre>
   *
   * @param predicate a closure that returns true for the element to find
   * @return the first matching element, or null if none found
   */
  SvgElement findFirst(Closure<Boolean> predicate) {
    children.find(predicate)
  }

  /**
   * Find the first child element of a specific type.
   * <p>Example:</p>
   * <pre>
   * def firstCircle = svg.findFirst(Circle)
   * </pre>
   *
   * @param clazz the element type to find
   * @return the first matching element, or null if none found
   */
  <T extends SvgElement> T findFirst(Class<T> clazz) {
    children.find { it.class == clazz } as T
  }

  /**
   * Get all descendant elements (recursive search through all children and their children).
   * <p>Example:</p>
   * <pre>
   * def allElements = svg.descendants()
   * def allCircles = svg.descendants(Circle)
   * </pre>
   *
   * @param clazz optional class filter, if null returns all descendants
   * @return a list of all descendant elements (or only those of the specified type)
   */
  <T extends SvgElement> List<T> descendants(Class<T> clazz = null) {
    List<T> results = []
    collectDescendants(children, clazz, results)
    results
  }

  /**
   * Helper method for recursive descendant collection
   */
  private <T extends SvgElement> void collectDescendants(
      List<SvgElement<? extends SvgElement>> elements,
      Class<T> clazz,
      List<T> results) {
    for (SvgElement element : elements) {
      // Add to results if it matches the type filter (or no filter)
      if (clazz == null || element.class == clazz) {
        results.add(element as T)
      }

      // Recurse into child containers
      if (element instanceof ElementContainer) {
        collectDescendants(((ElementContainer) element).children, clazz, results)
      }
    }
  }

  /**
   * Transform XPath queries to be namespace-aware for SVG elements.
   * Converts simple element queries like //circle to //*[local-name()='circle']
   * to work with namespaced SVG elements.
   *
   * @param xpathQuery the original XPath query
   * @return the transformed, namespace-aware query
   */
  private String transformXPathForNamespace(String xpathQuery) {
    // Don't transform if already using local-name() or namespace prefixes
    if (xpathQuery.contains('local-name()') || xpathQuery.contains(':')) {
      return xpathQuery
    }

    // Transform //elementName to //*[local-name()='elementName']
    String result = xpathQuery.replaceAll(
      /\/\/([\w-]+)(?=[\/@\[]|$)/,
      "//*[local-name()='\$1']"
    )

    // Transform /elementName to /*[local-name()='elementName']
    // This needs to run in a loop to handle paths like /svg/circle/g
    while (result =~ /(^|[\])])\/(?!\/)([\w-]+)(?=[\/@\[]|$)/) {
      result = result.replaceAll(
        /(^|[\]])\/(?!\/)([\w-]+)(?=[\/@\[]|$)/,
        '\$1/*[local-name()=\'\$2\']'
      )
    }

    return result
  }

  /**
   * Execute XPath query on the SVG DOM and return matching elements.
   * <p>
   * <strong>Namespace Handling:</strong> This method automatically handles SVG namespaces
   * for simple element queries. Queries like {@code //circle} or {@code //g/rect} are
   * transparently transformed to work with namespaced SVG elements.
   * </p>
   * <p><strong>Supported Patterns:</strong></p>
   * <ul>
   *   <li>Element queries: {@code //circle}, {@code /svg/rect}, {@code //g/path}</li>
   *   <li>Attribute queries: {@code //rect[@fill="red"]}, {@code //*[@id="logo"]}</li>
   *   <li>Mixed queries: {@code //g[@id="group1"]/circle}</li>
   * </ul>
   * <p>
   * <strong>Performance Note:</strong> This method builds a map of all descendants for O(1) element
   * lookups, making it O(n+m) where n is the number of XPath results and m is the total number of
   * descendants. For most use cases, this provides good performance.
   * </p>
   * <p>
   * <strong>Limitations:</strong> XPath numeric comparisons on attributes may not work as expected.
   * For complex queries, use filter() with predicates instead.
   * </p>
   * <p>Example:</p>
   * <pre>
   * def redRects = svg.xpath('//rect[@fill="red"]')
   * def allPaths = svg.xpath('//path')
   * def specificElement = svg.xpath('//*[@id="logo"]')
   * def nested = svg.xpath('//g/circle')  // Finds circles inside groups
   * </pre>
   *
   * @param xpathQuery the XPath query string
   * @return a list of SvgElement objects matching the query
   */
  List<SvgElement> xpath(String xpathQuery) {
    // Get the dom4j element from the container
    org.dom4j.Element domElement = null
    if (this instanceof SvgElement) {
      domElement = ((SvgElement) this).element
    } else {
      throw new UnsupportedOperationException("XPath queries require the container to be an SvgElement")
    }

    // Transform query to be namespace-aware and execute
    String transformedQuery = transformXPathForNamespace(xpathQuery)
    List<org.dom4j.Node> nodes = domElement.selectNodes(transformedQuery)

    // Build a map of dom4j elements to SvgElement wrappers for O(1) lookups
    // This avoids O(n*m) complexity by doing a single O(m) traversal upfront
    Map<org.dom4j.Element, SvgElement> elementMap = descendants().collectEntries {
      [(it.element): it]
    } as Map<org.dom4j.Element, SvgElement>

    // Convert dom4j elements back to SvgElement wrappers using the map
    List<SvgElement> results = []
    for (org.dom4j.Node node : nodes) {
      if (node instanceof org.dom4j.Element) {
        SvgElement svgElement = elementMap.get((org.dom4j.Element) node)
        if (svgElement != null) {
          results.add(svgElement)
        }
      }
    }
    results
  }

  /**
   * Count the number of child elements matching a predicate.
   * <p>Example:</p>
   * <pre>
   * def redCount = svg.count { it.getFill() == 'red' }
   * </pre>
   *
   * @param predicate a closure that returns true for elements to count
   * @return the count of matching elements
   */
  int count(Closure<Boolean> predicate) {
    children.count(predicate) as int
  }

  /**
   * Check if any child element matches a predicate.
   * <p>Example:</p>
   * <pre>
   * if (svg.any { it instanceof Circle }) {
   *   println "Contains at least one circle"
   * }
   * </pre>
   *
   * @param predicate a closure that returns true for matching elements
   * @return true if at least one element matches
   */
  boolean any(Closure<Boolean> predicate) {
    children.any(predicate)
  }

  /**
   * Check if all child elements match a predicate.
   * <p>Example:</p>
   * <pre>
   * if (svg.all { it.getFill() != null }) {
   *   println "All elements have a fill color"
   * }
   * </pre>
   *
   * @param predicate a closure that returns true for matching elements
   * @return true if all elements match
   */
  boolean all(Closure<Boolean> predicate) {
    children.every(predicate)
  }

  /**
   * Collect values from child elements using a transformation closure.
   * <p>Example:</p>
   * <pre>
   * def allFillColors = svg.collect { it.getFill() }
   * def allIds = svg.collect { it.getId() }
   * </pre>
   *
   * @param transform a closure that transforms each element to a value
   * @return a list of collected values
   */
  <R> List<R> collect(Closure<R> transform) {
    children.collect(transform)
  }
}
