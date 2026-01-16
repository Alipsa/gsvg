package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element

/**
 * Factory for creating SvgElement instances from DOM4J Elements.
 * Enables pure object-oriented copying and cloning without XML serialization.
 *
 * <h2>Performance</h2>
 * The pure OO approach eliminates XML serialization overhead from merge operations.
 * Both DOM structure and children lists are populated in a single pass during copying.
 *
 * @see se.alipsa.groovy.svg.utils.SvgMerger SvgMerger for usage examples
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
   * Supports all 83 concrete SVG element types.
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
      case Ellipse.NAME: return new Ellipse(parent, element)
      case Line.NAME: return new Line(parent, element)
      case Path.NAME: return new Path(parent, element)
      case Polygon.NAME: return new Polygon(parent, element)
      case Polyline.NAME: return new Polyline(parent, element)
      case Rect.NAME: return new Rect(parent, element)

      // Container elements (10)
      case A.NAME: return new A(parent, element)
      case ClipPath.NAME: return new ClipPath(parent, element)
      case Defs.NAME: return new Defs(parent, element)
      case ForeignObject.NAME: return new ForeignObject(parent, element)
      case G.NAME: return new G(parent, element)
      case Marker.NAME: return new Marker(parent, element)
      case Mask.NAME: return new Mask(parent, element)
      case Pattern.NAME: return new Pattern(parent, element)
      case Switch.NAME: return new Switch(parent, element)
      case Symbol.NAME: return new Symbol(parent, element)

      // Text elements (7)
      case AltGlyph.NAME: return new AltGlyph(parent, element)
      case AltGlyphDef.NAME: return new AltGlyphDef(parent, element)
      case AltGlyphItem.NAME: return new AltGlyphItem(parent, element)
      case Text.NAME: return new Text(parent, element)
      case TextPath.NAME: return new TextPath(parent, element)
      case Tref.NAME: return new Tref(parent, element)
      case Tspan.NAME: return new Tspan(parent, element)

      // Gradient elements (5)
      case LinearGradient.NAME: return new LinearGradient(parent, element)
      case Mesh.NAME: return new Mesh(parent, element)
      case MeshGradient.NAME: return new MeshGradient(parent, element)
      case RadialGradient.NAME: return new RadialGradient(parent, element)
      case Stop.NAME: return new Stop(parent, element)

      // Filter elements (25)
      case FeBlend.NAME: return new FeBlend(parent, element)
      case FeColorMatrix.NAME: return new FeColorMatrix(parent, element)
      case FeComponentTransfer.NAME: return new FeComponentTransfer(parent, element)
      case FeComposite.NAME: return new FeComposite(parent, element)
      case FeConvolveMatrix.NAME: return new FeConvolveMatrix(parent, element)
      case FeDiffuseLighting.NAME: return new FeDiffuseLighting(parent, element)
      case FeDisplacementMap.NAME: return new FeDisplacementMap(parent, element)
      case FeDistantLight.NAME: return new FeDistantLight(parent, element)
      case FeDropShadow.NAME: return new FeDropShadow(parent, element)
      case FeFlood.NAME: return new FeFlood(parent, element)
      case FeFuncA.NAME: return new FeFuncA(parent, element)
      case FeFuncB.NAME: return new FeFuncB(parent, element)
      case FeFuncG.NAME: return new FeFuncG(parent, element)
      case FeFuncR.NAME: return new FeFuncR(parent, element)
      case FeGaussianBlur.NAME: return new FeGaussianBlur(parent, element)
      case FeImage.NAME: return new FeImage(parent, element)
      case FeMerge.NAME: return new FeMerge(parent, element)
      case FeMergeNode.NAME: return new FeMergeNode(parent, element)
      case FeMorphology.NAME: return new FeMorphology(parent, element)
      case FeOffset.NAME: return new FeOffset(parent, element)
      case FePointLight.NAME: return new FePointLight(parent, element)
      case FeSpecularLighting.NAME: return new FeSpecularLighting(parent, element)
      case FeSpotLight.NAME: return new FeSpotLight(parent, element)
      case FeTile.NAME: return new FeTile(parent, element)
      case FeTurbulence.NAME: return new FeTurbulence(parent, element)

      // Animation elements (4)
      case Animate.NAME: return new Animate(parent, element)
      case AnimateMotion.NAME: return new AnimateMotion(parent, element)
      case AnimateTransform.NAME: return new AnimateTransform(parent, element)
      case Set.NAME: return new Set(parent, element)

      // Font elements (10)
      case Font.NAME: return new Font(parent, element)
      case FontFace.NAME: return new FontFace(parent, element)
      case FontFaceFormat.NAME: return new FontFaceFormat(parent, element)
      case FontFaceName.NAME: return new FontFaceName(parent, element)
      case FontFaceSrc.NAME: return new FontFaceSrc(parent, element)
      case FontFaceUri.NAME: return new FontFaceUri(parent, element)
      case Glyph.NAME: return new Glyph(parent, element)
      case GlyphRef.NAME: return new GlyphRef(parent, element)
      case Hkern.NAME: return new Hkern(parent, element)
      case Vkern.NAME: return new Vkern(parent, element)

      // Metadata elements (5)
      case Desc.NAME: return new Desc(parent, element)
      case Metadata.NAME: return new Metadata(parent, element)
      case Script.NAME: return new Script(parent, element)
      case Style.NAME: return new Style(parent, element)
      case Title.NAME: return new Title(parent, element)

      // Other elements (15)
      case Audio.NAME: return new Audio(parent, element)
      case ColorProfile.NAME: return new ColorProfile(parent, element)
      case Cursor.NAME: return new Cursor(parent, element)
      case Discard.NAME: return new Discard(parent, element)
      case Filter.NAME: return new Filter(parent, element)
      case Hatch.NAME: return new Hatch(parent, element)
      case HatchPath.NAME: return new HatchPath(parent, element)
      case Image.NAME: return new Image(parent, element)
      case MeshPatch.NAME: return new MeshPatch(parent, element)
      case MeshRow.NAME: return new MeshRow(parent, element)
      case MissingGlyph.NAME: return new MissingGlyph(parent, element)
      case Mpath.NAME: return new Mpath(parent, element)
      case Solidcolor.NAME: return new Solidcolor(parent, element)
      case Use.NAME: return new Use(parent, element)
      case Video.NAME: return new Video(parent, element)
      case View.NAME: return new View(parent, element)

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
