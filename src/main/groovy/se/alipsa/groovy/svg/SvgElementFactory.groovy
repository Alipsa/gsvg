package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element

/**
 * Factory for creating SvgElement instances from DOM4J Elements.
 * Enables pure object-oriented copying and cloning without XML serialization.
 *
 * Sprint 1 supports 20 most common element types.
 * Future sprints will expand to all 113+ element types.
 */
@CompileStatic
class SvgElementFactory {

  /**
   * Creates appropriate SvgElement from DOM4J Element with full recursion.
   * Populates both DOM tree AND children lists for complete object model.
   *
   * @param parent the parent container
   * @param element the DOM4J element to convert
   * @return the created SvgElement
   */
  static SvgElement fromElement(AbstractElementContainer parent, Element element) {
    String name = element.getName()
    SvgElement result = createElement(parent, element, name)

    if (result == null) {
      // Unknown element - fall back to generic handling
      // For now, just return null and let caller handle
      return null
    }

    // Add to parent's children list
    parent.add(result)

    // Recursively process children if this is a container
    if (result instanceof AbstractElementContainer) {
      AbstractElementContainer container = result as AbstractElementContainer
      element.elements().each { Element childElement ->
        fromElement(container, childElement)
      }
    }

    return result
  }

  /**
   * Creates element without recursion (for internal use).
   * Currently supports 20 most common element types.
   *
   * @param parent the parent element
   * @param element the DOM4J element
   * @param name the element name
   * @return the created SvgElement or null if not supported
   */
  private static SvgElement createElement(SvgElement parent, Element element, String name) {
    switch (name) {
      // Shape elements (7)
      case Circle.NAME: return new Circle(parent, element)
      case Rect.NAME: return new Rect(parent, element)
      case Path.NAME: return new Path(parent, element)
      case Line.NAME: return new Line(parent, element)
      case Ellipse.NAME: return new Ellipse(parent, element)
      case Polygon.NAME: return new Polygon(parent, element)
      case Polyline.NAME: return new Polyline(parent, element)

      // Container elements (6)
      case G.NAME: return new G(parent, element)
      case Defs.NAME: return new Defs(parent, element)
      case Symbol.NAME: return new Symbol(parent, element)
      case A.NAME: return new A(parent, element)
      case ClipPath.NAME: return new ClipPath(parent, element)
      case Mask.NAME: return new Mask(parent, element)

      // Text elements (2)
      case Text.NAME: return new Text(parent, element)
      case Tspan.NAME: return new Tspan(parent, element)

      // Gradient elements (3)
      case LinearGradient.NAME: return new LinearGradient(parent, element)
      case RadialGradient.NAME: return new RadialGradient(parent, element)
      case Stop.NAME: return new Stop(parent, element)

      // Other common elements (2)
      case Image.NAME: return new Image(parent, element)
      case Use.NAME: return new Use(parent, element)

      default: return null
    }
  }

  /**
   * Deep copies an element with full child hierarchy.
   * Pure object-oriented - no XML serialization.
   *
   * @param source the element to copy
   * @param newParent the parent for the copy
   * @return the copied element
   */
  static <T extends SvgElement> T deepCopy(T source, AbstractElementContainer newParent) {
    // Clone the DOM4J element
    Element clonedElement = source.element.createCopy()

    // Create appropriate SvgElement wrapper with recursion
    SvgElement result = fromElement(newParent, clonedElement)

    if (result == null) {
      // Fall back to adding cloned DOM element directly
      // This handles elements not yet supported by the factory
      newParent.element.add(clonedElement)
      return null
    }

    return result as T
  }

  /**
   * Deep copies all children from source to target (pure OO).
   * Uses fromElement for full object model construction.
   *
   * @param source the source container
   * @param target the target container
   */
  static void copyChildren(AbstractElementContainer source, AbstractElementContainer target) {
    for (SvgElement child : source.getChildren()) {
      SvgElement copied = deepCopy(child, target)

      // If deepCopy returned null (unsupported element type),
      // fall back to direct DOM cloning
      if (copied == null) {
        Element cloned = child.element.createCopy()
        target.element.add(cloned)
      }
    }
  }
}
