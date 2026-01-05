package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <video>} element that embeds video playback content.
 */
@CompileStatic
class Video extends SvgElement<Video> {

  static final String NAME = 'video'

  /**
   * Creates a Video.
   *
   * @param parent the parent SVG element
   */
  Video(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
