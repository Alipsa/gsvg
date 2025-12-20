package se.alipsa.groovy.svg

class Video extends SvgElement<Video> {

  static final String NAME = 'video'

  Video(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
