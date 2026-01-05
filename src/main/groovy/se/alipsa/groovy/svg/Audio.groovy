package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <audio>} element that embeds audio playback content.
 */
@CompileStatic
class Audio extends SvgElement<Audio> {

  static final String NAME = 'audio'

  /**
   * Creates a Audio.
   *
   * @param parent the parent SVG element
   */
  Audio(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
