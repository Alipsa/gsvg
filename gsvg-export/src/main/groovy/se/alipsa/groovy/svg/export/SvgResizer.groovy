package se.alipsa.groovy.svg.export

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.NumberFormatter
import se.alipsa.groovy.svg.utils.ViewBox

/**
 * Provides unit-aware SVG resizing utilities that return deep copies.
 *
 * <p>Behaviour highlights:</p>
 * <ul>
 *   <li>Original SVG is not modified</li>
 *   <li>Units are preserved when possible</li>
 *   <li>Percentages use reference sizes or fall back to viewBox/current size</li>
 * </ul>
 *
 * @since 1.1.0
 */
class SvgResizer {

  private static final Set<String> SUPPORTED_UNITS =
      ['','px','in','cm','mm','pt','pc','%'] as Set<String>
  private static final BigDecimal DEFAULT_DPI = new BigDecimal('96')

  /**
   * Resize to a width in user units (unitless/px), scaling height proportionally.
   *
   * @param svg the source SVG
   * @param width the target width
   * @param options optional configuration (preserveAspectRatio, referenceWidth/referenceHeight, dpi, outputUnit)
   * @return a resized copy of the SVG
   */
  static Svg resizeToWidth(Svg svg, Number width, Map options = [:]) {
    resizeToWidthInternal(svg, parseLength(width, 'width'), options)
  }

  /**
   * Resize to a width with units (e.g. "480px", "12cm", "150%"), scaling height proportionally.
   *
   * @param svg the source SVG
   * @param width the target width with units
   * @param options optional configuration (preserveAspectRatio, referenceWidth/referenceHeight, dpi, outputUnit)
   * @return a resized copy of the SVG
   */
  static Svg resizeToWidth(Svg svg, String width, Map options = [:]) {
    resizeToWidthInternal(svg, parseLength(width, 'width'), options)
  }

  /**
   * Resize to a height in user units (unitless/px), scaling width proportionally.
   *
   * @param svg the source SVG
   * @param height the target height
   * @param options optional configuration (preserveAspectRatio, referenceWidth/referenceHeight, dpi, outputUnit)
   * @return a resized copy of the SVG
   */
  static Svg resizeToHeight(Svg svg, Number height, Map options = [:]) {
    resizeToHeightInternal(svg, parseLength(height, 'height'), options)
  }

  /**
   * Resize to a height with units (e.g. "320px", "8cm", "80%"), scaling width proportionally.
   *
   * @param svg the source SVG
   * @param height the target height with units
   * @param options optional configuration (preserveAspectRatio, referenceWidth/referenceHeight, dpi, outputUnit)
   * @return a resized copy of the SVG
   */
  static Svg resizeToHeight(Svg svg, String height, Map options = [:]) {
    resizeToHeightInternal(svg, parseLength(height, 'height'), options)
  }

  /**
   * Resize to a target width and height in user units (unitless/px).
   *
   * @param svg the source SVG
   * @param width target width
   * @param height target height
   * @param options optional configuration (preserveAspectRatio, referenceWidth/referenceHeight, dpi, outputUnit)
   * @return a resized copy of the SVG
   */
  static Svg resize(Svg svg, Number width, Number height, Map options = [:]) {
    resizeInternal(svg, parseLength(width, 'width'), parseLength(height, 'height'), options)
  }

  /**
   * Resize to a target width and height with units.
   *
   * @param svg the source SVG
   * @param width target width with units
   * @param height target height with units
   * @param options optional configuration (preserveAspectRatio, referenceWidth/referenceHeight, dpi, outputUnit)
   * @return a resized copy of the SVG
   */
  static Svg resize(Svg svg, String width, String height, Map options = [:]) {
    resizeInternal(svg, parseLength(width, 'width'), parseLength(height, 'height'), options)
  }

  /**
   * Scale the SVG dimensions by a factor.
   *
   * @param svg the source SVG
   * @param factor the scale factor (must be positive)
   * @param options optional configuration (referenceWidth/referenceHeight, dpi, outputUnit)
   * @return a scaled copy of the SVG
   */
  static Svg scale(Svg svg, Number factor, Map options = [:]) {
    if (svg == null) {
      throw new IllegalArgumentException('Svg must not be null')
    }
    if (factor == null) {
      throw new IllegalArgumentException('Scale factor must not be null')
    }
    BigDecimal scale = toBigDecimal(factor, 'scale factor')
    if (scale <= 0) {
      throw new IllegalArgumentException("Scale factor must be positive, got: ${scale}")
    }

    BigDecimal dpi = resolveDpi(options)
    Integer precision = svg.getMaxPrecision()
    String outputUnit = resolveOutputUnit(options)

    ParsedLength width = svg.getWidth() ? parseLength(svg.getWidth(), 'width') : null
    ParsedLength height = svg.getHeight() ? parseLength(svg.getHeight(), 'height') : null

    Svg copy = svg.clone()

    if (width != null && height != null && outputUnit == null) {
      Object widthValue = scaleLengthValue(width, scale, precision)
      Object heightValue = scaleLengthValue(height, scale, precision)
      applyDimensions(copy, widthValue, heightValue)
      return copy
    }

    Size source = resolveSourceSize(svg, options, dpi)
    BigDecimal newWidthPx = source.width * scale
    BigDecimal newHeightPx = source.height * scale

    String widthUnit = outputUnit ?: (width?.unit ?: '')
    String heightUnit = outputUnit ?: (height?.unit ?: '')
    Object widthValue = valueFromPx(newWidthPx, widthUnit, resolveReferenceWidth(svg, options, dpi), dpi, precision)
    Object heightValue = valueFromPx(newHeightPx, heightUnit, resolveReferenceHeight(svg, options, dpi), dpi, precision)
    applyDimensions(copy, widthValue, heightValue)
    copy
  }

  private static Svg resizeToWidthInternal(Svg svg, ParsedLength targetWidth, Map options) {
    validateSvg(svg)
    ensurePositive(targetWidth, 'width')

    BigDecimal dpi = resolveDpi(options)
    Integer precision = svg.getMaxPrecision()
    Size source = resolveSourceSize(svg, options, dpi)

    BigDecimal targetWidthPx = lengthToPx(targetWidth, 'width', svg, options, dpi)
    BigDecimal scale = targetWidthPx / source.width
    BigDecimal targetHeightPx = source.height * scale

    String outputUnit = resolveOutputUnit(options)
    Object widthValue = resolveFixedOutputValue(targetWidth, targetWidthPx, outputUnit, 'width', svg, options, dpi, precision)
    String heightUnit = outputUnit ?: targetWidth.unit
    Object heightValue = valueFromPx(targetHeightPx, heightUnit, resolveReferenceHeight(svg, options, dpi), dpi, precision)

    Svg copy = svg.clone()
    applyDimensions(copy, widthValue, heightValue)
    copy
  }

  private static Svg resizeToHeightInternal(Svg svg, ParsedLength targetHeight, Map options) {
    validateSvg(svg)
    ensurePositive(targetHeight, 'height')

    BigDecimal dpi = resolveDpi(options)
    Integer precision = svg.getMaxPrecision()
    Size source = resolveSourceSize(svg, options, dpi)

    BigDecimal targetHeightPx = lengthToPx(targetHeight, 'height', svg, options, dpi)
    BigDecimal scale = targetHeightPx / source.height
    BigDecimal targetWidthPx = source.width * scale

    String outputUnit = resolveOutputUnit(options)
    Object heightValue = resolveFixedOutputValue(targetHeight, targetHeightPx, outputUnit, 'height', svg, options, dpi, precision)
    String widthUnit = outputUnit ?: targetHeight.unit
    Object widthValue = valueFromPx(targetWidthPx, widthUnit, resolveReferenceWidth(svg, options, dpi), dpi, precision)

    Svg copy = svg.clone()
    applyDimensions(copy, widthValue, heightValue)
    copy
  }

  private static Svg resizeInternal(Svg svg, ParsedLength targetWidth, ParsedLength targetHeight, Map options) {
    validateSvg(svg)
    ensurePositive(targetWidth, 'width')
    ensurePositive(targetHeight, 'height')

    BigDecimal dpi = resolveDpi(options)
    Integer precision = svg.getMaxPrecision()
    boolean preserveAspectRatio =
        options.containsKey('preserveAspectRatio') ? options.preserveAspectRatio as boolean : true
    String outputUnit = resolveOutputUnit(options)

    Svg copy = svg.clone()

    if (!preserveAspectRatio) {
      Object widthValue = resolveDirectOutputValue(targetWidth, 'width', outputUnit, svg, options, dpi, precision)
      Object heightValue = resolveDirectOutputValue(targetHeight, 'height', outputUnit, svg, options, dpi, precision)
      applyDimensions(copy, widthValue, heightValue)
      return copy
    }

    Size source = resolveSourceSize(svg, options, dpi)
    BigDecimal targetWidthPx = lengthToPx(targetWidth, 'width', svg, options, dpi)
    BigDecimal targetHeightPx = lengthToPx(targetHeight, 'height', svg, options, dpi)
    BigDecimal scale = [targetWidthPx / source.width, targetHeightPx / source.height].min()

    BigDecimal newWidthPx = source.width * scale
    BigDecimal newHeightPx = source.height * scale
    String widthUnit = outputUnit ?: targetWidth.unit
    String heightUnit = outputUnit ?: targetHeight.unit

    Object widthValue = valueFromPx(newWidthPx, widthUnit, resolveReferenceWidth(svg, options, dpi), dpi, precision)
    Object heightValue = valueFromPx(newHeightPx, heightUnit, resolveReferenceHeight(svg, options, dpi), dpi, precision)
    applyDimensions(copy, widthValue, heightValue)
    copy
  }

  private static Object resolveFixedOutputValue(
      ParsedLength target,
      BigDecimal targetPx,
      String outputUnit,
      String axis,
      Svg svg,
      Map options,
      BigDecimal dpi,
      Integer precision) {
    if (outputUnit) {
      BigDecimal reference = axis == 'width' ? resolveReferenceWidth(svg, options, dpi) : resolveReferenceHeight(svg, options, dpi)
      return valueFromPx(targetPx, outputUnit, reference, dpi, precision)
    }
    if (target.raw != null) {
      return target.raw
    }
    targetPx
  }

  private static Object resolveDirectOutputValue(
      ParsedLength target,
      String axis,
      String outputUnit,
      Svg svg,
      Map options,
      BigDecimal dpi,
      Integer precision) {
    if (outputUnit) {
      BigDecimal reference = axis == 'width' ? resolveReferenceWidth(svg, options, dpi) : resolveReferenceHeight(svg, options, dpi)
      BigDecimal targetPx = lengthToPx(target, axis, svg, options, dpi)
      return valueFromPx(targetPx, outputUnit, reference, dpi, precision)
    }
    if (target.raw != null) {
      return target.raw
    }
    target.value
  }

  private static Object scaleLengthValue(ParsedLength length, BigDecimal factor, Integer precision) {
    BigDecimal scaled = length.value * factor
    if (!length.unit) {
      return scaled
    }
    NumberFormatter.format(scaled, precision) + length.unit
  }

  private static void applyDimensions(Svg target, Object widthValue, Object heightValue) {
    if (widthValue instanceof CharSequence) {
      target.width(widthValue.toString())
    } else {
      target.width(widthValue as Number)
    }

    if (heightValue instanceof CharSequence) {
      target.height(heightValue.toString())
    } else {
      target.height(heightValue as Number)
    }
  }

  private static void validateSvg(Svg svg) {
    if (svg == null) {
      throw new IllegalArgumentException('Svg must not be null')
    }
  }

  private static ParsedLength parseLength(Object value, String label) {
    if (value == null) {
      throw new IllegalArgumentException("${label} must not be null")
    }
    if (value instanceof Number) {
      return new ParsedLength(toBigDecimal(value, label), '', null)
    }
    if (!(value instanceof String)) {
      throw new IllegalArgumentException("${label} must be a Number or String, got: ${value.getClass().simpleName}")
    }

    String raw = value.toString().trim()
    if (!raw) {
      throw new IllegalArgumentException("${label} must not be blank")
    }

    def matcher = raw =~ /(?i)^([+-]?\d*\.?\d+)([a-z%]*)$/
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid ${label} value: ${value}")
    }

    BigDecimal number = new BigDecimal(matcher[0][1])
    String unit = matcher[0][2]?.toLowerCase() ?: ''
    if (!SUPPORTED_UNITS.contains(unit)) {
      throw new IllegalArgumentException("Unsupported unit '${unit}' for ${label}")
    }
    new ParsedLength(number, unit, raw)
  }

  private static void ensurePositive(ParsedLength length, String label) {
    if (length.value <= 0) {
      throw new IllegalArgumentException("${label} must be positive, got: ${length.value}${length.unit}")
    }
  }

  private static BigDecimal resolveDpi(Map options) {
    Object dpiValue = options.dpi ?: DEFAULT_DPI
    BigDecimal dpi = toBigDecimal(dpiValue, 'dpi')
    if (dpi <= 0) {
      throw new IllegalArgumentException("Dpi must be positive, got: ${dpi}")
    }
    dpi
  }

  private static String resolveOutputUnit(Map options) {
    if (!options.containsKey('outputUnit') || options.outputUnit == null) {
      return null
    }
    String unit = options.outputUnit.toString().trim().toLowerCase()
    if (!SUPPORTED_UNITS.contains(unit)) {
      throw new IllegalArgumentException("Unsupported output unit '${unit}'")
    }
    unit
  }

  private static Size resolveSourceSize(Svg svg, Map options, BigDecimal dpi) {
    boolean preferViewBox =
        options.containsKey('preferViewBox') ? options.preferViewBox as boolean : false
    ViewBox viewBox = svg.getViewBoxObject()

    if (preferViewBox && viewBox != null) {
      return new Size(toBigDecimal(viewBox.width, 'viewBox width'),
          toBigDecimal(viewBox.height, 'viewBox height'))
    }

    if (svg.getWidth() != null && svg.getHeight() != null) {
      ParsedLength width = parseLength(svg.getWidth(), 'width')
      ParsedLength height = parseLength(svg.getHeight(), 'height')
      BigDecimal widthPx = lengthToPx(width, 'width', svg, options, dpi)
      BigDecimal heightPx = lengthToPx(height, 'height', svg, options, dpi)
      return new Size(widthPx, heightPx)
    }

    if (viewBox != null) {
      return new Size(toBigDecimal(viewBox.width, 'viewBox width'),
          toBigDecimal(viewBox.height, 'viewBox height'))
    }

    throw new IllegalArgumentException('Cannot determine SVG size: set width/height or viewBox')
  }

  private static BigDecimal resolveReferenceWidth(Svg svg, Map options, BigDecimal dpi) {
    if (options.referenceWidth != null) {
      ParsedLength ref = parseLength(options.referenceWidth, 'referenceWidth')
      if (ref.isPercent()) {
        throw new IllegalArgumentException('referenceWidth cannot be a percentage')
      }
      return toPx(ref, null, dpi)
    }
    ViewBox viewBox = svg.getViewBoxObject()
    if (viewBox != null) {
      return toBigDecimal(viewBox.width, 'viewBox width')
    }
    if (svg.getWidth() != null) {
      ParsedLength width = parseLength(svg.getWidth(), 'width')
      if (width.isPercent()) {
        throw new IllegalArgumentException('Cannot resolve reference width from percentage width without viewBox')
      }
      return toPx(width, null, dpi)
    }
    throw new IllegalArgumentException('Cannot resolve reference width: provide referenceWidth or set viewBox/width')
  }

  private static BigDecimal resolveReferenceHeight(Svg svg, Map options, BigDecimal dpi) {
    if (options.referenceHeight != null) {
      ParsedLength ref = parseLength(options.referenceHeight, 'referenceHeight')
      if (ref.isPercent()) {
        throw new IllegalArgumentException('referenceHeight cannot be a percentage')
      }
      return toPx(ref, null, dpi)
    }
    ViewBox viewBox = svg.getViewBoxObject()
    if (viewBox != null) {
      return toBigDecimal(viewBox.height, 'viewBox height')
    }
    if (svg.getHeight() != null) {
      ParsedLength height = parseLength(svg.getHeight(), 'height')
      if (height.isPercent()) {
        throw new IllegalArgumentException('Cannot resolve reference height from percentage height without viewBox')
      }
      return toPx(height, null, dpi)
    }
    throw new IllegalArgumentException('Cannot resolve reference height: provide referenceHeight or set viewBox/height')
  }

  private static BigDecimal lengthToPx(
      ParsedLength length,
      String axis,
      Svg svg,
      Map options,
      BigDecimal dpi) {
    if (!length.isPercent()) {
      return toPx(length, null, dpi)
    }
    BigDecimal reference = axis == 'width' ? resolveReferenceWidth(svg, options, dpi) : resolveReferenceHeight(svg, options, dpi)
    toPx(length, reference, dpi)
  }

  private static BigDecimal toPx(ParsedLength length, BigDecimal referencePx, BigDecimal dpi) {
    if (!length.unit || length.unit == 'px') {
      return length.value
    }
    if (length.unit == '%') {
      if (referencePx == null) {
        throw new IllegalArgumentException('Percentage lengths require a reference size')
      }
      return referencePx * length.value / 100
    }
    switch (length.unit) {
      case 'in': return length.value * dpi
      case 'cm': return length.value * dpi / new BigDecimal('2.54')
      case 'mm': return length.value * dpi / new BigDecimal('25.4')
      case 'pt': return length.value * dpi / new BigDecimal('72')
      case 'pc': return length.value * dpi / new BigDecimal('6')
      default:
        throw new IllegalArgumentException("Unsupported unit '${length.unit}'")
    }
  }

  private static Object valueFromPx(
      BigDecimal px,
      String unit,
      BigDecimal referencePx,
      BigDecimal dpi,
      Integer precision) {
    if (!unit) {
      return px
    }
    if (unit == '%') {
      if (referencePx == null) {
        throw new IllegalArgumentException('Percentage output requires a reference size')
      }
      BigDecimal percent = px * 100 / referencePx
      return NumberFormatter.format(percent, precision) + '%'
    }
    BigDecimal value = fromPx(px, unit, dpi)
    NumberFormatter.format(value, precision) + unit
  }

  private static BigDecimal fromPx(BigDecimal px, String unit, BigDecimal dpi) {
    switch (unit) {
      case 'px': return px
      case 'in': return px / dpi
      case 'cm': return px * new BigDecimal('2.54') / dpi
      case 'mm': return px * new BigDecimal('25.4') / dpi
      case 'pt': return px * new BigDecimal('72') / dpi
      case 'pc': return px * new BigDecimal('6') / dpi
      default:
        throw new IllegalArgumentException("Unsupported unit '${unit}'")
    }
  }

  private static BigDecimal toBigDecimal(Object value, String label) {
    try {
      return new BigDecimal(value.toString())
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid ${label}: ${value}", e)
    }
  }

  private static class ParsedLength {
    final BigDecimal value
    final String unit
    final String raw

    ParsedLength(BigDecimal value, String unit, String raw) {
      this.value = value
      this.unit = unit
      this.raw = raw
    }

    boolean isPercent() {
      unit == '%'
    }
  }

  private static class Size {
    final BigDecimal width
    final BigDecimal height

    Size(BigDecimal width, BigDecimal height) {
      this.width = width
      this.height = height
    }
  }
}
