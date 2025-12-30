package se.alipsa.groovy.svg

/**
 * Enumerates filter input keywords for the 'in' attribute of filter primitives.
 */
enum In {

  /**
   * This keyword represents the graphics elements that were the original input into the ‘filter’ element.
   * For raster effects filter primitives, the graphics elements will be rasterized into an initially clear RGBA
   * raster in image space. Pixels left untouched by the original graphic will be left clear.
   * The image is specified to be rendered in linear RGBA pixels. The alpha channel of this image captures any
   * anti-aliasing specified by SVG. (Since the raster is linear, the alpha channel of this image will represent
   * the exact percent coverage of each pixel.)
   */
  SourceGraphic,

  /**
   * This keyword represents the graphics elements that were the original input into the ‘filter’ element.
   * SourceAlpha has all of the same rules as SourceGraphic except that only the alpha channel is used.
   * The input image is an RGBA image consisting of implicitly black color values for the RGB channels,
   * but whose alpha channel is the same as SourceGraphic. If this option is used, then some implementations might
   * need to rasterize the graphics elements in order to extract the alpha channel.
   */
  SourceAlpha,

  /**
   * This keyword represents an image snapshot of the canvas under the filter region at the time that the ‘filter’
   * element was invoked.
   */
  BackgroundImage,

  /**
   * Same as BackgroundImage except only the alpha channel is used.
   */
  BackgroundAlpha,

  /**
   * This keyword represents the value of the ‘fill’ property on the target element for the filter effect.
   * The FillPaint image has conceptually infinite extent. Frequently this image is opaque everywhere,
   * but it might not be if the "paint" itself has alpha, as in the case of a gradient or pattern which itself
   * includes transparent or semi-transparent parts.
   */
  FillPaint,

  /**
   * This keyword represents the value of the ‘stroke’ property on the target element for the filter effect.
   * The StrokePaint image has conceptually infinite extent. Frequently this image is opaque everywhere,
   * but it might not be if the "paint" itself has alpha, as in the case of a gradient or pattern which itself
   * includes transparent or semi-transparent parts.
   */
  StrokePaint
}
