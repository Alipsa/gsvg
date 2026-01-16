package se.alipsa.groovy.svg.utils

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElementFactory

/**
 * Utility for merging multiple SVG documents using a pure object-oriented approach.
 *
 * <h2>Pure OO Implementation</h2>
 * This merger uses {@link SvgElementFactory} for efficient element copying without XML serialization.
 * The factory populates both DOM4J structures and SvgElement children lists in a single pass,
 * eliminating the need for XML parsing after merge operations.
 *
 * <h2>Performance</h2>
 * The pure OO approach provides:
 * <ul>
 * <li>Zero XML serialization overhead during merge operations</li>
 * <li>Direct object manipulation for better performance</li>
 * <li>Single-pass construction maintains both DOM and object model consistency</li>
 * </ul>
 *
 * @see SvgElementFactory for the underlying copy mechanism
 */
@CompileStatic
class SvgMerger {

  /**
   * Merge several svg files horizontally.
   *
   * @param svgs the svgs to merge
   * @return a new svg svg containing all the svgs merged horizontally.
   */
  static Svg mergeHorizontally(Svg... svgs) {
    if (svgs == null || svgs.length == 0) {
      return new Svg()
    }

    if (svgs.length == 1) {
      return svgs[0]
    }

    // Calculate dimensions
    BigDecimal totalWidth = 0
    BigDecimal maxHeight = 0

    List<SvgDimensions> dimensions = []

    for (Svg svg : svgs) {
      SvgDimensions dim = extractDimensions(svg)
      dimensions.add(dim)
      totalWidth += dim.width
      if (dim.height > maxHeight) {
        maxHeight = dim.height
      }
    }

    // Create result SVG using object model
    Svg result = new Svg(totalWidth, maxHeight)

    // Merge each SVG with horizontal offset
    BigDecimal currentX = 0

    for (int i = 0; i < svgs.length; i++) {
      Svg sourceSvg = svgs[i]
      SvgDimensions dim = dimensions[i]

      // Create a group for this SVG's content
      G group = result.addG()

      // Apply translation if not the first element
      if (currentX != 0) {
        group.transform("translate(${currentX}, 0)")
      }

      // Copy all child elements using factory
      // Factory uses pure OO approach: both DOM and children lists are populated
      SvgElementFactory.copyChildren(sourceSvg, group)

      // Update offset for next SVG
      currentX += dim.width
    }

    // Pure OO: No XML serialization needed!
    // Children lists are already populated by the factory.
    return result
  }

  /**
   * Merge several svg files vertically.
   *
   * @param svgs the svgs to merge
   * @return a new svg containing all the svgs merged vertically.
   */
  static Svg mergeVertically(Svg... svgs) {
    if (svgs == null || svgs.length == 0) {
      return new Svg()
    }

    if (svgs.length == 1) {
      return svgs[0]
    }

    // Calculate dimensions
    BigDecimal maxWidth = 0
    BigDecimal totalHeight = 0

    List<SvgDimensions> dimensions = []

    for (Svg svg : svgs) {
      SvgDimensions dim = extractDimensions(svg)
      dimensions.add(dim)
      totalHeight += dim.height
      if (dim.width > maxWidth) {
        maxWidth = dim.width
      }
    }

    // Create result SVG using object model
    Svg result = new Svg(maxWidth, totalHeight)

    // Merge each SVG with vertical offset
    BigDecimal currentY = 0

    for (int i = 0; i < svgs.length; i++) {
      Svg sourceSvg = svgs[i]
      SvgDimensions dim = dimensions[i]

      // Create a group for this SVG's content
      G group = result.addG()

      // Apply translation if not the first element
      if (currentY != 0) {
        group.transform("translate(0, ${currentY})")
      }

      // Copy all child elements using factory
      // Factory uses pure OO approach: both DOM and children lists are populated
      SvgElementFactory.copyChildren(sourceSvg, group)

      // Update offset for next SVG
      currentY += dim.height
    }

    // Pure OO: No XML serialization needed!
    // Children lists are already populated by the factory.
    return result
  }

  /**
   * Merge several svg files by layering them on top of each other.
   * Later SVGs in the array will appear on top of earlier ones.
   *
   * @param svgs the svgs to merge
   * @return a new svg containing all the svgs layered on top of each other.
   */
  static Svg mergeOnTop(Svg... svgs) {
    if (svgs == null || svgs.length == 0) {
      return new Svg()
    }

    if (svgs.length == 1) {
      return svgs[0]
    }

    // Calculate dimensions - use maximum width and height
    BigDecimal maxWidth = 0
    BigDecimal maxHeight = 0

    for (Svg svg : svgs) {
      SvgDimensions dim = extractDimensions(svg)
      if (dim.width > maxWidth) {
        maxWidth = dim.width
      }
      if (dim.height > maxHeight) {
        maxHeight = dim.height
      }
    }

    // Create result SVG using object model
    Svg result = new Svg(maxWidth, maxHeight)

    // Layer each SVG on top of each other (no translation)
    for (Svg sourceSvg : svgs) {
      // Create a group for each SVG's content
      G group = result.addG()

      // Copy all child elements using factory
      // Factory uses pure OO approach: both DOM and children lists are populated
      SvgElementFactory.copyChildren(sourceSvg, group)
    }

    // Pure OO: No XML serialization needed!
    // Children lists are already populated by the factory.
    return result
  }

  /**
   * Extracts dimensions from an SVG element.
   */
  private static SvgDimensions extractDimensions(Svg svg) {
    BigDecimal width = 100  // default
    BigDecimal height = 100 // default

    String widthStr = svg.getWidth()
    String heightStr = svg.getHeight()

    if (widthStr != null) {
      width = parseNumericValue(widthStr)
    }

    if (heightStr != null) {
      height = parseNumericValue(heightStr)
    }

    // If width/height not set, try to extract from viewBox
    if (widthStr == null || heightStr == null) {
      String viewBox = svg.getViewBox()
      if (viewBox != null) {
        String[] parts = viewBox.trim().split(/\s+/)
        if (parts.length == 4) {
          if (widthStr == null) {
            width = new BigDecimal(parts[2])
          }
          if (heightStr == null) {
            height = new BigDecimal(parts[3])
          }
        }
      }
    }

    return new SvgDimensions(width, height)
  }

  /**
   * Parses numeric value from string, stripping units like 'px', 'pt', etc.
   */
  private static BigDecimal parseNumericValue(String value) {
    if (value == null) {
      return new BigDecimal(100)
    }

    // Remove common units
    String numericPart = value.replaceAll(/(?i)(px|pt|em|rem|%|cm|mm|in)$/, '').trim()

    try {
      return new BigDecimal(numericPart)
    } catch (NumberFormatException e) {
      return new BigDecimal(100)
    }
  }

  /**
   * Helper class to store SVG dimensions.
   */
  private static class SvgDimensions {
    BigDecimal width
    BigDecimal height

    SvgDimensions(BigDecimal width, BigDecimal height) {
      this.width = width
      this.height = height
    }
  }
}
