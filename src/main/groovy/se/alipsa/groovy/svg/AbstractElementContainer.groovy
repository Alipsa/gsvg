package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element

/**
 * Base class for SVG elements that can contain child elements and provide factory helpers.
 */
@CompileStatic
abstract class AbstractElementContainer<T extends AbstractElementContainer<T>> extends SvgElement<T> {

  /**
   * Creates a AbstractElementContainer.
   *
   * @param parent the parent SVG element
   * @param name the name of the element
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

  Svg addSvg(Map attributes) {
    Svg svg = addSvg()
    attributes.each {
      key, value -> svg.addAttribute(String.valueOf(key), value)
    }
    svg
  }

  /**
   * Creates and adds a new A child element.
   *
   * @return the created element
   */
  A addA() {
    add(new A(this))
  }

  A addA(Map attributes) {
    A a = addA()
    attributes.each {
      key, value -> a.addAttribute(String.valueOf(key), value)
    }
    a
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
   * @param id the unique identifier
   * @return the created element
   */
  Circle addCircle(String id) {
    add(new Circle(this).id(id))
  }

  Circle addCircle(Map attributes) {
    Circle circle = addCircle()
    attributes.each {
      key, value -> circle.addAttribute(String.valueOf(key), value)
    }
    circle
  }

  /**
   * Creates and adds a new ClipPath child element.
   *
   * @return the created element
   */
  ClipPath addClipPath() {
    add(new ClipPath(this))
  }

  ClipPath addClipPath(Map attributes) {
    ClipPath clipPath = addClipPath()
    attributes.each {
      key, value -> clipPath.addAttribute(String.valueOf(key), value)
    }
    clipPath
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
   * @param rx the x-axis radius
   * @param ry the y-axis radius
   * @return the created element
   */
  Ellipse addEllipse(Number rx, Number ry) {
    add(new Ellipse(this, rx, ry))
  }

  Ellipse addEllipse(Map attributes) {
    Ellipse ellipse = addEllipse()
    attributes.each {
      key, value -> ellipse.addAttribute(String.valueOf(key), value)
    }
    ellipse
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
   * @param id the unique identifier
   * @return the created element
   */
  Filter addFilter(String id) {
    add(new Filter(this).id(id))
  }

  Filter addFilter(Map attributes) {
    Filter filter = addFilter()
    attributes.each {
      key, value -> filter.addAttribute(String.valueOf(key), value)
    }
    filter
  }

  /**
   * Creates and adds a new G child element.
   *
   * @return the created element
   */
  G addG() {
    add(new G(this))
  }

  G addG(Map attributes) {
    G g = addG()
    attributes.each {
      key, value -> g.addAttribute(String.valueOf(key), value)
    }
    g
  }

  /**
   * Creates and adds a new Image child element.
   *
   * @return the created element
   */
  Image addImage() {
    add(new Image(this))
  }

  Image addImage(Map attributes) {
    Image img = addImage()
    attributes.each {
      key, value -> img.addAttribute(String.valueOf(key), value)
    }
    img
  }

  /**
   * Creates and adds a new Line child element.
   *
   * @return the created element
   */
  Line addLine() {
    add(new Line(this))
  }

  Line addLine(Map attributes) {
    Line l = addLine()
    attributes.each {
      key, value -> l.addAttribute(String.valueOf(key), value)
    }
    l
  }
  /**
   * Creates and adds a new Line child element.
   *
   * @param x1 the starting x-coordinate
   * @param y1 the starting y-coordinate
   * @param x2 the ending x-coordinate
   * @param y2 the ending y-coordinate
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
   * @param id the unique identifier
   * @return the created element
   */
  Mask addMask(String id) {
    add(new Mask(this).id(id))
  }

  Mask addMask(Map attributes) {
    Mask m = addMask()
    attributes.each {
      key, value -> m.addAttribute(String.valueOf(key), value)
    }
    m
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
   * @param id the unique identifier
   * @return the created element
   */
  Path addPath(String id) {
    addPath().id(id)
  }

  Path addPath(Map attributes) {
    Path p = addPath()
    attributes.each {
      key, value -> p.addAttribute(String.valueOf(key), value)
    }
    p
  }
  /**
   * Creates and adds a new Polygon child element.
   *
   * @param points the points defining the shape
   * @return the created element
   */
  Polygon addPolygon(String points) {
    add(new Polygon(this).points(points))
  }

  /**
   * Creates and adds a new Polygon child element.
   *
   * @param points the points defining the shape
   * @return the created element
   */
  Polygon addPolygon(Coordinate ... points) {
    add(new Polygon(this, points))
  }

  /**
   * Creates and adds a new Polygon child element.
   *
   * @param points the points defining the shape
   * @return the created element
   */
  Polygon addPolygon(List<Number>... points) {
    add(new Polygon(this, points))
  }

  Polygon addPolygon(Map attributes) {
    Polygon p = add(new Polygon(this))
    attributes.each {
      key, value -> p.addAttribute(String.valueOf(key), value)
    }
    p
  }


  /**
   * Creates and adds a new Polyline child element.
   *
   * @param points the points defining the shape
   * @return the created element
   */
  Polyline addPolyline(Coordinate ... points) {
    add(new Polyline(this, points))
  }

  /**
   * Creates and adds a new Polyline child element.
   *
   * @param points the points defining the shape
   * @return the created element
   */
  Polyline addPolyline(List<Number>... points) {
    add(new Polyline(this, points))
  }

  Polyline addPolyline(Map attributes) {
    Polyline p = add(new Polyline(this))
    attributes.each {
      key, value -> p.addAttribute(String.valueOf(key), value)
    }
    p
  }
  /**
   * Creates and adds a new Rect child element.
   *
   * @param width the width
   * @param height the height
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
   * @param id the unique identifier
   * @return the created element
   */
  Rect addRect(String id) {
    add(new Rect(this).id(id))
  }

  Rect addRect(Map attributes) {
    Rect r = addRect()
    attributes.each {
      key, value -> r.addAttribute(String.valueOf(key), value)
    }
    r
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
   * @param content the content
   * @return the created element
   */
  Text addText(String content) {
    add(new Text(this).addContent(content))
  }

  Text addText(Map attributes) {
    Text t = addText()
    attributes.each {
      key, value -> t.addAttribute(String.valueOf(key), value)
    }
    t
  }

  /**
   * Creates and adds a new Use child element.
   *
   * @return the created element
   */
  Use addUse() {
    add(new Use(this))
  }

  Use addUse(Map attributes) {
    Use u = addUse()
    attributes.each {
      key, value -> u.addAttribute(String.valueOf(key), value)
    }
    u
  }

  /**
   * Creates and adds a new ColorProfile child element.
   *
   * @return the created element
   */
  ColorProfile addColorProfile() {
    add(new ColorProfile(this))
  }

  ColorProfile addColorProfile(Map attributes) {
    ColorProfile c = addColorProfile()
    attributes.each {
      key, value -> c.addAttribute(String.valueOf(key), value)
    }
    c
  }

  /**
   * Creates and adds a new Cursor child element.
   *
   * @return the created element
   */
  Cursor addCursor() {
    add(new Cursor(this))
  }

  Cursor addCursor(Map attributes) {
    Cursor c = addCursor()
    attributes.each {
      key, value -> c.addAttribute(String.valueOf(key), value)
    }
    c
  }

  /**
   * Creates and adds a new Solidcolor child element.
   *
   * @return the created element
   */
  Solidcolor addSolidcolor() {
    add(new Solidcolor(this))
  }

  Solidcolor addSolidcolor(Map attributes) {
    Solidcolor s = addSolidcolor()
    attributes.each {
      key, value -> s.addAttribute(String.valueOf(key), value)
    }
    s
  }
  /**
   * Creates and adds a new MeshGradient child element.
   *
   * @return the created element
   */
  MeshGradient addMeshGradient() {
    add(new MeshGradient(this))
  }

  MeshGradient addMeshGradient(Map attributes) {
    MeshGradient mg = addMeshGradient()
    attributes.each {
      key, value -> mg.addAttribute(String.valueOf(key), value)
    }
    mg
  }

  /**
   * Creates and adds a new Mesh child element.
   *
   * @return the created element
   */
  Mesh addMesh() {
    add(new Mesh(this))
  }

  Mesh addMesh(Map attributes) {
    Mesh m = addMesh()
    attributes.each {
      key, value -> m.addAttribute(String.valueOf(key), value)
    }
    m
  }

  /**
   * Creates and adds a new MeshRow child element.
   *
   * @return the created element
   */
  MeshRow addMeshRow() {
    add(new MeshRow(this))
  }

  MeshRow addMeshRow(Map attributes) {
    MeshRow mr = addMeshRow()
    attributes.each {
      key, value -> mr.addAttribute(String.valueOf(key), value)
    }
    mr
  }

  /**
   * Creates and adds a new MeshPatch child element.
   *
   * @return the created element
   */
  MeshPatch addMeshPatch() {
    add(new MeshPatch(this))
  }

  MeshPatch addMeshPatch(Map attributes) {
    MeshPatch mp = addMeshPatch()
    attributes.each {
      key, value -> mp.addAttribute(String.valueOf(key), value)
    }
    mp
  }

  /**
   * Creates and adds a new Hatch child element.
   *
   * @return the created element
   */
  Hatch addHatch() {
    add(new Hatch(this))
  }

  Hatch addHatch(Map attributes) {
    Hatch h = addHatch()
    attributes.each {
      key, value -> h.addAttribute(String.valueOf(key), value)
    }
    h
  }

  /**
   * Creates and adds a new HatchPath child element.
   *
   * @return the created element
   */
  HatchPath addHatchPath() {
    add(new HatchPath(this))
  }

  HatchPath addHatchPath(Map attributes) {
    HatchPath hp = addHatchPath()
    attributes.each {
      key, value -> hp.addAttribute(String.valueOf(key), value)
    }
    hp
  }

  /**
   * Creates and adds a new Audio child element.
   *
   * @return the created element
   */
  Audio addAudio() {
    add(new Audio(this))
  }

  Audio addAudio(Map attributes) {
    Audio a = addAudio()
    attributes.each {
      key, value -> a.addAttribute(String.valueOf(key), value)
    }
    a
  }

  /**
   * Creates and adds a new Video child element.
   *
   * @return the created element
   */
  Video addVideo() {
    add(new Video(this))
  }

  Video addVideo(Map attributes) {
    Video v = addVideo()
    attributes.each {
      key, value -> v.addAttribute(String.valueOf(key), value)
    }
    v
  }

  /**
   * Creates and adds a new Discard child element.
   *
   * @return the created element
   */
  Discard addDiscard() {
    add(new Discard(this))
  }

  Discard addDiscard(Map attributes) {
    Discard d = addDiscard()
    attributes.each {
      key, value -> d.addAttribute(String.valueOf(key), value)
    }
    d
  }

  /**
   * Creates and adds a new Font child element.
   *
   * @return the created element
   */
  Font addFont() {
    add(new Font(this))
  }

  Font addFont(Map attributes) {
    Font f = addFont()
    attributes.each {
      key, value -> f.addAttribute(String.valueOf(key), value)
    }
    f
  }

  /**
   * Creates and adds a new FontFace child element.
   *
   * @return the created element
   */
  FontFace addFontFace() {
    add(new FontFace(this))
  }

  FontFace addFontFace(Map attributes) {
    FontFace ff = addFontFace()
    attributes.each {
      key, value -> ff.addAttribute(String.valueOf(key), value)
    }
    ff
  }

  /**
   * Creates and adds a new FontFaceSrc child element.
   *
   * @return the created element
   */
  FontFaceSrc addFontFaceSrc() {
    add(new FontFaceSrc(this))
  }

  FontFaceSrc addFontFaceSrc(Map attributes) {
    FontFaceSrc ffs = addFontFaceSrc()
    attributes.each {
      key, value -> ffs.addAttribute(String.valueOf(key), value)
    }
    ffs
  }

  /**
   * Creates and adds a new FontFaceUri child element.
   *
   * @return the created element
   */
  FontFaceUri addFontFaceUri() {
    add(new FontFaceUri(this))
  }

  FontFaceUri addFontFaceUri(Map attributes) {
    FontFaceUri ffu = addFontFaceUri()
    attributes.each {
      key, value -> ffu.addAttribute(String.valueOf(key), value)
    }
    ffu
  }

  /**
   * Creates and adds a new FontFaceName child element.
   *
   * @return the created element
   */
  FontFaceName addFontFaceName() {
    add(new FontFaceName(this))
  }

  FontFaceName addFontFaceName(Map attributes) {
    FontFaceName ffn = addFontFaceName()
    attributes.each {
      key, value -> ffn.addAttribute(String.valueOf(key), value)
    }
    ffn
  }

  /**
   * Creates and adds a new FontFaceFormat child element.
   *
   * @return the created element
   */
  FontFaceFormat addFontFaceFormat() {
    add(new FontFaceFormat(this))
  }

  FontFaceFormat addFontFaceFormat(Map attributes) {
    FontFaceFormat fff = addFontFaceFormat()
    attributes.each {
      key, value -> fff.addAttribute(String.valueOf(key), value)
    }
    fff
  }

  /**
   * Creates and adds a new Glyph child element.
   *
   * @return the created element
   */
  Glyph addGlyph() {
    add(new Glyph(this))
  }

  Glyph addGlyph(Map attributes) {
    Glyph g = addGlyph()
    attributes.each {
      key, value -> g.addAttribute(String.valueOf(key), value)
    }
    g
  }

  /**
   * Creates and adds a new MissingGlyph child element.
   *
   * @return the created element
   */
  MissingGlyph addMissingGlyph() {
    add(new MissingGlyph(this))
  }

  MissingGlyph addMissingGlyph(Map attributes) {
    MissingGlyph mg = addMissingGlyph()
    attributes.each {
      key, value -> mg.addAttribute(String.valueOf(key), value)
    }
    mg
  }

  /**
   * Creates and adds a new Hkern child element.
   *
   * @return the created element
   */
  Hkern addHkern() {
    add(new Hkern(this))
  }

  Hkern addHkern(Map attributes) {
    Hkern h = addHkern()
    attributes.each {
      key, value -> h.addAttribute(String.valueOf(key), value)
    }
    h
  }

  /**
   * Creates and adds a new Vkern child element.
   *
   * @return the created element
   */
  Vkern addVkern() {
    add(new Vkern(this))
  }

  Vkern addVkern(Map attributes) {
    Vkern v = addVkern()
    attributes.each {
      key, value -> v.addAttribute(String.valueOf(key), value)
    }
    v
  }

  /**
   * Creates and adds a new GlyphRef child element.
   *
   * @return the created element
   */
  GlyphRef addGlyphRef() {
    add(new GlyphRef(this))
  }

  GlyphRef addGlyphRef(Map attributes) {
    GlyphRef gr = addGlyphRef()
    attributes.each {
      key, value -> gr.addAttribute(String.valueOf(key), value)
    }
    gr
  }

  /**
   * Creates and adds a new AltGlyph child element.
   *
   * @return the created element
   */
  AltGlyph addAltGlyph() {
    add(new AltGlyph(this))
  }

  AltGlyph addAltGlyph(Map attributes) {
    AltGlyph ag = addAltGlyph()
    attributes.each {
      key, value -> ag.addAttribute(String.valueOf(key), value)
    }
    ag
  }

  /**
   * Creates and adds a new AltGlyphDef child element.
   *
   * @return the created element
   */
  AltGlyphDef addAltGlyphDef() {
    add(new AltGlyphDef(this))
  }

  AltGlyphDef addAltGlyphDef(Map attributes) {
    AltGlyphDef agd = addAltGlyphDef()
    attributes.each {
      key, value -> agd.addAttribute(String.valueOf(key), value)
    }
    agd
  }

  /**
   * Creates and adds a new AltGlyphItem child element.
   *
   * @return the created element
   */
  AltGlyphItem addAltGlyphItem() {
    add(new AltGlyphItem(this))
  }

  AltGlyphItem addAltGlyphItem(Map attributes) {
    AltGlyphItem agi = addAltGlyphItem()
    attributes.each {
      key, value -> agi.addAttribute(String.valueOf(key), value)
    }
    agi
  }

  /**
   * Creates and adds a new Tref child element.
   *
   * @return the created element
   */
  Tref addTref() {
    add(new Tref(this))
  }

  Tref addTref(Map attributes) {
    Tref t = addTref()
    attributes.each {
      key, value -> t.addAttribute(String.valueOf(key), value)
    }
    t
  }
}
