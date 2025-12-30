package se.alipsa.groovy.svg

import org.dom4j.Element

/**
 * Base class for SVG elements that can contain child elements and provide factory helpers.
 */
abstract class AbstractElementContainer<T extends AbstractElementContainer<T>> extends SvgElement<T> {

  /**
   * Creates a AbstractElementContainer.
   *
   * @param parent value
   * @param name value
   */
  AbstractElementContainer(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  /**
   * Special constructor for Svg as it does not have a parent
   * Use this only to create Svg elements!
   */
  AbstractElementContainer(Element element) {
    super(element)
  }

  /**
   * Creates and adds a new Svg child element.
   *
   * @return the created element
   */
  Svg addSvg() {
    add(new Svg(this))
  }

  /**
   * Creates and adds a new A child element.
   *
   * @return the created element
   */
  A addA() {
    add(new A(this))
  }

  /**
   * Creates and adds a new Circle child element.
   *
   * @return the created element
   */
  Circle addCircle() {
    add(new Circle(this))
  }

  /**
   * Creates and adds a new Circle child element.
   *
   * @param id value
   * @return the created element
   */
  Circle addCircle(String id) {
    add(new Circle(this).id(id))
  }

  /**
   * Creates and adds a new ClipPath child element.
   *
   * @return the created element
   */
  ClipPath addClipPath() {
    add(new ClipPath(this))
  }

  /**
   * Creates and adds a new Ellipse child element.
   *
   * @return the created element
   */
  Ellipse addEllipse() {
    add(new Ellipse(this))
  }

  /**
   * Creates and adds a new Ellipse child element.
   *
   * @param rx value
   * @param ry value
   * @return the created element
   */
  Ellipse addEllipse(Number rx, Number ry) {
    add(new Ellipse(this, rx, ry))
  }

  /**
   * Creates and adds a new Filter child element.
   *
   * @return the created element
   */
  Filter addFilter() {
    add(new Filter(this))
  }

  /**
   * Creates and adds a new Filter child element.
   *
   * @param id value
   * @return the created element
   */
  Filter addFilter(String id) {
    add(new Filter(this).id(id))
  }

  /**
   * Creates and adds a new G child element.
   *
   * @return the created element
   */
  G addG() {
    add(new G(this))
  }

  /**
   * Creates and adds a new Image child element.
   *
   * @return the created element
   */
  Image addImage() {
    add(new Image(this))
  }

  /**
   * Creates and adds a new Line child element.
   *
   * @return the created element
   */
  Line addLine() {
    add(new Line(this))
  }

  /**
   * Creates and adds a new Line child element.
   *
   * @param x1 value
   * @param y1 value
   * @param x2 value
   * @param y2 value
   * @return the created element
   */
  Line addLine(Number x1, Number y1, Number x2, Number y2) {
    add(new Line(this, x1, y1, x2, y2))
  }

  /**
   * Creates and adds a new Mask child element.
   *
   * @return the created element
   */
  Mask addMask() {
    add(new Mask(this))
  }

  /**
   * Creates and adds a new Mask child element.
   *
   * @param id value
   * @return the created element
   */
  Mask addMask(String id) {
    add(new Mask(this).id(id))
  }

  /**
   * Creates and adds a new Path child element.
   *
   * @return the created element
   */
  Path addPath() {
    add(new Path(this))
  }

  /**
   * Creates and adds a new Path child element.
   *
   * @param id value
   * @return the created element
   */
  Path addPath(String id) {
    addPath().id(id)
  }

  /**
   * Creates and adds a new Polygon child element.
   *
   * @param points value
   * @return the created element
   */
  Polygon addPolygon(String points) {
    add(new Polygon(this).points(points))
  }

  /**
   * Creates and adds a new Polygon child element.
   *
   * @param points value
   * @return the created element
   */
  Polygon addPolygon(Coordinate ... points) {
    add(new Polygon(this, points))
  }

  /**
   * Creates and adds a new Polygon child element.
   *
   * @param points value
   * @return the created element
   */
  Polygon addPolygon(List<Number>... points) {
    add(new Polygon(this, points))
  }

  /**
   * Creates and adds a new Polyline child element.
   *
   * @param points value
   * @return the created element
   */
  Polyline addPolyline(Coordinate ... points) {
    add(new Polyline(this, points))
  }

  /**
   * Creates and adds a new Polyline child element.
   *
   * @param points value
   * @return the created element
   */
  Polyline addPolyline(List<Number>... points) {
    add(new Polyline(this, points))
  }

  /**
   * Creates and adds a new Rect child element.
   *
   * @param width value
   * @param height value
   * @return the created element
   */
  Rect addRect(Number width, Number height) {
    add(new Rect(this, width, height))
  }

  /**
   * Creates and adds a new Rect child element.
   *
   * @return the created element
   */
  Rect addRect() {
    add(new Rect(this))
  }

  /**
   * Creates and adds a new Rect child element.
   *
   * @param id value
   * @return the created element
   */
  Rect addRect(String id) {
    add(new Rect(this).id(id))
  }

  /**
   * Creates and adds a new Text child element.
   *
   * @return the created element
   */
  Text addText() {
    add(new Text(this))
  }

  /**
   * Creates and adds a new Text child element.
   *
   * @param content value
   * @return the created element
   */
  Text addText(String content) {
    add(new Text(this).addContent(content))
  }

  /**
   * Creates and adds a new Use child element.
   *
   * @return the created element
   */
  Use addUse() {
    add(new Use(this))
  }

  /**
   * Creates and adds a new ColorProfile child element.
   *
   * @return the created element
   */
  ColorProfile addColorProfile() {
    add(new ColorProfile(this))
  }

  /**
   * Creates and adds a new Cursor child element.
   *
   * @return the created element
   */
  Cursor addCursor() {
    add(new Cursor(this))
  }

  /**
   * Creates and adds a new Solidcolor child element.
   *
   * @return the created element
   */
  Solidcolor addSolidcolor() {
    add(new Solidcolor(this))
  }

  /**
   * Creates and adds a new MeshGradient child element.
   *
   * @return the created element
   */
  MeshGradient addMeshGradient() {
    add(new MeshGradient(this))
  }

  /**
   * Creates and adds a new Mesh child element.
   *
   * @return the created element
   */
  Mesh addMesh() {
    add(new Mesh(this))
  }

  /**
   * Creates and adds a new MeshRow child element.
   *
   * @return the created element
   */
  MeshRow addMeshRow() {
    add(new MeshRow(this))
  }

  /**
   * Creates and adds a new MeshPatch child element.
   *
   * @return the created element
   */
  MeshPatch addMeshPatch() {
    add(new MeshPatch(this))
  }

  /**
   * Creates and adds a new Hatch child element.
   *
   * @return the created element
   */
  Hatch addHatch() {
    add(new Hatch(this))
  }

  /**
   * Creates and adds a new HatchPath child element.
   *
   * @return the created element
   */
  HatchPath addHatchPath() {
    add(new HatchPath(this))
  }

  /**
   * Creates and adds a new Audio child element.
   *
   * @return the created element
   */
  Audio addAudio() {
    add(new Audio(this))
  }

  /**
   * Creates and adds a new Video child element.
   *
   * @return the created element
   */
  Video addVideo() {
    add(new Video(this))
  }

  /**
   * Creates and adds a new Discard child element.
   *
   * @return the created element
   */
  Discard addDiscard() {
    add(new Discard(this))
  }

  /**
   * Creates and adds a new Font child element.
   *
   * @return the created element
   */
  Font addFont() {
    add(new Font(this))
  }

  /**
   * Creates and adds a new FontFace child element.
   *
   * @return the created element
   */
  FontFace addFontFace() {
    add(new FontFace(this))
  }

  /**
   * Creates and adds a new FontFaceSrc child element.
   *
   * @return the created element
   */
  FontFaceSrc addFontFaceSrc() {
    add(new FontFaceSrc(this))
  }

  /**
   * Creates and adds a new FontFaceUri child element.
   *
   * @return the created element
   */
  FontFaceUri addFontFaceUri() {
    add(new FontFaceUri(this))
  }

  /**
   * Creates and adds a new FontFaceName child element.
   *
   * @return the created element
   */
  FontFaceName addFontFaceName() {
    add(new FontFaceName(this))
  }

  /**
   * Creates and adds a new FontFaceFormat child element.
   *
   * @return the created element
   */
  FontFaceFormat addFontFaceFormat() {
    add(new FontFaceFormat(this))
  }

  /**
   * Creates and adds a new Glyph child element.
   *
   * @return the created element
   */
  Glyph addGlyph() {
    add(new Glyph(this))
  }

  /**
   * Creates and adds a new MissingGlyph child element.
   *
   * @return the created element
   */
  MissingGlyph addMissingGlyph() {
    add(new MissingGlyph(this))
  }

  /**
   * Creates and adds a new Hkern child element.
   *
   * @return the created element
   */
  Hkern addHkern() {
    add(new Hkern(this))
  }

  /**
   * Creates and adds a new Vkern child element.
   *
   * @return the created element
   */
  Vkern addVkern() {
    add(new Vkern(this))
  }

  /**
   * Creates and adds a new GlyphRef child element.
   *
   * @return the created element
   */
  GlyphRef addGlyphRef() {
    add(new GlyphRef(this))
  }

  /**
   * Creates and adds a new AltGlyph child element.
   *
   * @return the created element
   */
  AltGlyph addAltGlyph() {
    add(new AltGlyph(this))
  }

  /**
   * Creates and adds a new AltGlyphDef child element.
   *
   * @return the created element
   */
  AltGlyphDef addAltGlyphDef() {
    add(new AltGlyphDef(this))
  }

  /**
   * Creates and adds a new AltGlyphItem child element.
   *
   * @return the created element
   */
  AltGlyphItem addAltGlyphItem() {
    add(new AltGlyphItem(this))
  }

  /**
   * Creates and adds a new Tref child element.
   *
   * @return the created element
   */
  Tref addTref() {
    add(new Tref(this))
  }
}
