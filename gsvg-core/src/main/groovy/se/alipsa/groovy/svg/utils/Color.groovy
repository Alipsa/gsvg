package se.alipsa.groovy.svg.utils

import groovy.transform.CompileStatic

/**
 * Utility class for creating and manipulating SVG colors.
 * Supports RGB, RGBA, HSL, HSLA, Hex, and named colors.
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * // Create colors
 * def red = Color.rgb(255, 0, 0)
 * def blue = Color.hex('#0000ff')
 * def green = Color.hsl(120, 100, 50)
 *
 * // Use with SVG elements
 * rect.fill(Color.rgb(255, 0, 0))
 * circle.stroke(Color.hex('#00ff00').withAlpha(0.5))
 *
 * // Color manipulation
 * def darker = red.darken(0.2)
 * def lighter = blue.lighten(0.3)
 * </pre>
 */
@CompileStatic
class Color {

  private int red     // 0-255
  private int green   // 0-255
  private int blue    // 0-255
  private double alpha = 1.0  // 0.0-1.0

  /**
   * Common named SVG colors.
   */
  static final Map<String, String> NAMED_COLORS = [
    'aliceblue': '#f0f8ff', 'antiquewhite': '#faebd7', 'aqua': '#00ffff',
    'aquamarine': '#7fffd4', 'azure': '#f0ffff', 'beige': '#f5f5dc',
    'bisque': '#ffe4c4', 'black': '#000000', 'blanchedalmond': '#ffebcd',
    'blue': '#0000ff', 'blueviolet': '#8a2be2', 'brown': '#a52a2a',
    'burlywood': '#deb887', 'cadetblue': '#5f9ea0', 'chartreuse': '#7fff00',
    'chocolate': '#d2691e', 'coral': '#ff7f50', 'cornflowerblue': '#6495ed',
    'cornsilk': '#fff8dc', 'crimson': '#dc143c', 'cyan': '#00ffff',
    'darkblue': '#00008b', 'darkcyan': '#008b8b', 'darkgoldenrod': '#b8860b',
    'darkgray': '#a9a9a9', 'darkgreen': '#006400', 'darkgrey': '#a9a9a9',
    'darkkhaki': '#bdb76b', 'darkmagenta': '#8b008b', 'darkolivegreen': '#556b2f',
    'darkorange': '#ff8c00', 'darkorchid': '#9932cc', 'darkred': '#8b0000',
    'darksalmon': '#e9967a', 'darkseagreen': '#8fbc8f', 'darkslateblue': '#483d8b',
    'darkslategray': '#2f4f4f', 'darkslategrey': '#2f4f4f', 'darkturquoise': '#00ced1',
    'darkviolet': '#9400d3', 'deeppink': '#ff1493', 'deepskyblue': '#00bfff',
    'dimgray': '#696969', 'dimgrey': '#696969', 'dodgerblue': '#1e90ff',
    'firebrick': '#b22222', 'floralwhite': '#fffaf0', 'forestgreen': '#228b22',
    'fuchsia': '#ff00ff', 'gainsboro': '#dcdcdc', 'ghostwhite': '#f8f8ff',
    'gold': '#ffd700', 'goldenrod': '#daa520', 'gray': '#808080',
    'green': '#008000', 'greenyellow': '#adff2f', 'grey': '#808080',
    'honeydew': '#f0fff0', 'hotpink': '#ff69b4', 'indianred': '#cd5c5c',
    'indigo': '#4b0082', 'ivory': '#fffff0', 'khaki': '#f0e68c',
    'lavender': '#e6e6fa', 'lavenderblush': '#fff0f5', 'lawngreen': '#7cfc00',
    'lemonchiffon': '#fffacd', 'lightblue': '#add8e6', 'lightcoral': '#f08080',
    'lightcyan': '#e0ffff', 'lightgoldenrodyellow': '#fafad2', 'lightgray': '#d3d3d3',
    'lightgreen': '#90ee90', 'lightgrey': '#d3d3d3', 'lightpink': '#ffb6c1',
    'lightsalmon': '#ffa07a', 'lightseagreen': '#20b2aa', 'lightskyblue': '#87cefa',
    'lightslategray': '#778899', 'lightslategrey': '#778899', 'lightsteelblue': '#b0c4de',
    'lightyellow': '#ffffe0', 'lime': '#00ff00', 'limegreen': '#32cd32',
    'linen': '#faf0e6', 'magenta': '#ff00ff', 'maroon': '#800000',
    'mediumaquamarine': '#66cdaa', 'mediumblue': '#0000cd', 'mediumorchid': '#ba55d3',
    'mediumpurple': '#9370db', 'mediumseagreen': '#3cb371', 'mediumslateblue': '#7b68ee',
    'mediumspringgreen': '#00fa9a', 'mediumturquoise': '#48d1cc', 'mediumvioletred': '#c71585',
    'midnightblue': '#191970', 'mintcream': '#f5fffa', 'mistyrose': '#ffe4e1',
    'moccasin': '#ffe4b5', 'navajowhite': '#ffdead', 'navy': '#000080',
    'oldlace': '#fdf5e6', 'olive': '#808000', 'olivedrab': '#6b8e23',
    'orange': '#ffa500', 'orangered': '#ff4500', 'orchid': '#da70d6',
    'palegoldenrod': '#eee8aa', 'palegreen': '#98fb98', 'paleturquoise': '#afeeee',
    'palevioletred': '#db7093', 'papayawhip': '#ffefd5', 'peachpuff': '#ffdab9',
    'peru': '#cd853f', 'pink': '#ffc0cb', 'plum': '#dda0dd',
    'powderblue': '#b0e0e6', 'purple': '#800080', 'red': '#ff0000',
    'rosybrown': '#bc8f8f', 'royalblue': '#4169e1', 'saddlebrown': '#8b4513',
    'salmon': '#fa8072', 'sandybrown': '#f4a460', 'seagreen': '#2e8b57',
    'seashell': '#fff5ee', 'sienna': '#a0522d', 'silver': '#c0c0c0',
    'skyblue': '#87ceeb', 'slateblue': '#6a5acd', 'slategray': '#708090',
    'slategrey': '#708090', 'snow': '#fffafa', 'springgreen': '#00ff7f',
    'steelblue': '#4682b4', 'tan': '#d2b48c', 'teal': '#008080',
    'thistle': '#d8bfd8', 'tomato': '#ff6347', 'turquoise': '#40e0d0',
    'violet': '#ee82ee', 'wheat': '#f5deb3', 'white': '#ffffff',
    'whitesmoke': '#f5f5f5', 'yellow': '#ffff00', 'yellowgreen': '#9acd32'
  ].asImmutable()

  private Color(int r, int g, int b, double a = 1.0) {
    this.red = clampInt(r, 0, 255)
    this.green = clampInt(g, 0, 255)
    this.blue = clampInt(b, 0, 255)
    this.alpha = clampDouble(a, 0.0, 1.0)
  }

  /**
   * Creates a color from RGB values (0-255).
   *
   * @param r red component (0-255)
   * @param g green component (0-255)
   * @param b blue component (0-255)
   * @return a new Color instance
   */
  static Color rgb(int r, int g, int b) {
    new Color(r, g, b)
  }

  /**
   * Creates a color from RGBA values.
   *
   * @param r red component (0-255)
   * @param g green component (0-255)
   * @param b blue component (0-255)
   * @param a alpha component (0.0-1.0)
   * @return a new Color instance
   */
  static Color rgba(int r, int g, int b, double a) {
    new Color(r, g, b, a)
  }

  /**
   * Creates a color from a hex string.
   * Supports formats: #RGB, #RRGGBB, #RRGGBBAA
   *
   * @param hex the hex color string
   * @return a new Color instance
   */
  static Color hex(String hex) {
    if (hex == null || hex.isEmpty()) {
      throw new IllegalArgumentException('Hex color cannot be null or empty')
    }

    String h = hex.trim()
    if (h.startsWith('#')) {
      h = h.substring(1)
    }

    int r, g, b
    double a = 1.0

    switch (h.length()) {
      case 3:  // #RGB
        r = Integer.parseInt(h.substring(0, 1) * 2, 16)
        g = Integer.parseInt(h.substring(1, 2) * 2, 16)
        b = Integer.parseInt(h.substring(2, 3) * 2, 16)
        break
      case 6:  // #RRGGBB
        r = Integer.parseInt(h.substring(0, 2), 16)
        g = Integer.parseInt(h.substring(2, 4), 16)
        b = Integer.parseInt(h.substring(4, 6), 16)
        break
      case 8:  // #RRGGBBAA
        r = Integer.parseInt(h.substring(0, 2), 16)
        g = Integer.parseInt(h.substring(2, 4), 16)
        b = Integer.parseInt(h.substring(4, 6), 16)
        a = Integer.parseInt(h.substring(6, 8), 16) / 255.0
        break
      default:
        throw new IllegalArgumentException("Invalid hex color format: $hex")
    }

    new Color(r, g, b, a)
  }

  /**
   * Creates a color from HSL values.
   *
   * @param h hue (0-360 degrees)
   * @param s saturation (0-100 percent)
   * @param l lightness (0-100 percent)
   * @return a new Color instance
   */
  static Color hsl(double h, double s, double l) {
    hsla(h, s, l, 1.0)
  }

  /**
   * Creates a color from HSLA values.
   *
   * @param h hue (0-360 degrees)
   * @param s saturation (0-100 percent)
   * @param l lightness (0-100 percent)
   * @param a alpha (0.0-1.0)
   * @return a new Color instance
   */
  static Color hsla(double h, double s, double l, double a) {
    // Normalize values
    h = ((h % 360) + 360) % 360  // Wrap to 0-360
    s = clampDouble(s, 0.0, 100.0) / 100.0
    l = clampDouble(l, 0.0, 100.0) / 100.0

    double c = (1 - Math.abs(2 * l - 1)) * s
    double x = c * (1 - Math.abs((h / 60) % 2 - 1))
    double m = l - c / 2

    double r1, g1, b1
    if (h < 60) {
      r1 = c; g1 = x; b1 = 0
    } else if (h < 120) {
      r1 = x; g1 = c; b1 = 0
    } else if (h < 180) {
      r1 = 0; g1 = c; b1 = x
    } else if (h < 240) {
      r1 = 0; g1 = x; b1 = c
    } else if (h < 300) {
      r1 = x; g1 = 0; b1 = c
    } else {
      r1 = c; g1 = 0; b1 = x
    }

    int r = (int) Math.round((r1 + m) * 255)
    int g = (int) Math.round((g1 + m) * 255)
    int b = (int) Math.round((b1 + m) * 255)

    new Color(r, g, b, a)
  }

  /**
   * Creates a color from a named color.
   *
   * @param name the color name (e.g., 'red', 'blue')
   * @return a new Color instance
   */
  static Color named(String name) {
    String hexValue = NAMED_COLORS[name.toLowerCase()]
    if (hexValue == null) {
      throw new IllegalArgumentException("Unknown color name: $name")
    }
    hex(hexValue)
  }

  /**
   * Parses a color string in any supported format.
   * Supports: hex (#rgb, #rrggbb), rgb(), rgba(), hsl(), hsla(), named colors
   *
   * @param color the color string
   * @return a new Color instance
   */
  static Color parse(String color) {
    if (color == null || color.isEmpty()) {
      throw new IllegalArgumentException('Color string cannot be null or empty')
    }

    String c = color.trim().toLowerCase()

    // Hex color
    if (c.startsWith('#')) {
      return hex(c)
    }

    // rgb() or rgba()
    if (c.startsWith('rgb')) {
      def matcher = c =~ /rgba?\s*\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*(?:,\s*([\d.]+)\s*)?\)/
      if (matcher.matches()) {
        int r = matcher.group(1) as int
        int g = matcher.group(2) as int
        int b = matcher.group(3) as int
        String alphaStr = matcher.group(4)
        double a = 1.0
        if (alphaStr) {
          a = alphaStr as double
        }
        return new Color(r, g, b, a)
      }
    }

    // hsl() or hsla()
    if (c.startsWith('hsl')) {
      def matcher = c =~ /hsla?\s*\(\s*([\d.]+)\s*,\s*([\d.]+)%?\s*,\s*([\d.]+)%?\s*(?:,\s*([\d.]+)\s*)?\)/
      if (matcher.matches()) {
        double h = matcher.group(1) as double
        double s = matcher.group(2) as double
        double l = matcher.group(3) as double
        String alphaStr = matcher.group(4)
        double a = 1.0
        if (alphaStr) {
          a = alphaStr as double
        }
        return hsla(h, s, l, a)
      }
    }

    // Named color
    if (NAMED_COLORS.containsKey(c)) {
      return named(c)
    }

    throw new IllegalArgumentException("Unable to parse color: $color")
  }

  /**
   * Returns a new color with the specified alpha value.
   *
   * @param a the new alpha value (0.0-1.0)
   * @return a new Color instance
   */
  Color withAlpha(double a) {
    new Color(red, green, blue, a)
  }

  /**
   * Darkens the color by the specified amount.
   *
   * @param amount amount to darken (0.0-1.0)
   * @return a new darker Color instance
   */
  Color darken(double amount) {
    amount = clampDouble(amount, 0.0, 1.0)
    double factor = 1.0 - amount
    new Color(
      (int) (red * factor),
      (int) (green * factor),
      (int) (blue * factor),
      alpha
    )
  }

  /**
   * Lightens the color by the specified amount.
   *
   * @param amount amount to lighten (0.0-1.0)
   * @return a new lighter Color instance
   */
  Color lighten(double amount) {
    amount = clampDouble(amount, 0.0, 1.0)
    new Color(
      (int) (red + (255 - red) * amount),
      (int) (green + (255 - green) * amount),
      (int) (blue + (255 - blue) * amount),
      alpha
    )
  }

  /**
   * Interpolates between this color and another.
   *
   * @param other the other color
   * @param t interpolation factor (0.0 = this color, 1.0 = other color)
   * @return a new interpolated Color instance
   */
  Color interpolate(Color other, double t) {
    t = clampDouble(t, 0.0, 1.0)
    new Color(
      (int) (red + (other.red - red) * t),
      (int) (green + (other.green - green) * t),
      (int) (blue + (other.blue - blue) * t),
      alpha + (other.alpha - alpha) * t
    )
  }

  /**
   * Returns the color as a hex string.
   *
   * @param includeAlpha whether to include alpha channel
   * @return hex color string (e.g., "#ff0000" or "#ff0000ff")
   */
  String toHex(boolean includeAlpha = false) {
    String hex = String.format('#%02x%02x%02x', red, green, blue)
    if (includeAlpha) {
      hex += String.format('%02x', (int) Math.round(alpha * 255))
    }
    hex
  }

  /**
   * Returns the color as an rgb() or rgba() string.
   *
   * @return rgb/rgba string (e.g., "rgb(255, 0, 0)" or "rgba(255, 0, 0, 0.5)")
   */
  String toRgb() {
    if (alpha < 1.0) {
      "rgba($red, $green, $blue, $alpha)"
    } else {
      "rgb($red, $green, $blue)"
    }
  }

  /**
   * Converts to HSL and returns as hsl() or hsla() string.
   *
   * @return hsl/hsla string
   */
  String toHsl() {
    double r = red / 255.0
    double g = green / 255.0
    double b = blue / 255.0

    double max = Math.max(r, Math.max(g, b))
    double min = Math.min(r, Math.min(g, b))
    double delta = max - min

    double h = 0, s = 0, l = (max + min) / 2

    if (delta != 0) {
      s = l > 0.5 ? delta / (2 - max - min) : delta / (max + min)

      if (max == r) {
        h = ((g - b) / delta + (g < b ? 6 : 0)) / 6
      } else if (max == g) {
        h = ((b - r) / delta + 2) / 6
      } else {
        h = ((r - g) / delta + 4) / 6
      }
    }

    int hInt = (int) Math.round(h * 360)
    int sInt = (int) Math.round(s * 100)
    int lInt = (int) Math.round(l * 100)

    if (alpha < 1.0) {
      "hsla($hInt, ${sInt}%, ${lInt}%, $alpha)"
    } else {
      "hsl($hInt, ${sInt}%, ${lInt}%)"
    }
  }

  /**
   * Returns the color as an SVG-compatible string.
   * Uses hex format for opaque colors, rgba() for colors with alpha.
   *
   * @return SVG color string
   */
  @Override
  String toString() {
    if (alpha < 1.0) {
      toRgb()
    } else {
      toHex()
    }
  }

  /**
   * Gets the red component.
   *
   * @return red value (0-255)
   */
  int getRed() { red }

  /**
   * Gets the green component.
   *
   * @return green value (0-255)
   */
  int getGreen() { green }

  /**
   * Gets the blue component.
   *
   * @return blue value (0-255)
   */
  int getBlue() { blue }

  /**
   * Gets the alpha component.
   *
   * @return alpha value (0.0-1.0)
   */
  double getAlpha() { alpha }

  /**
   * Clamps an integer value between min and max.
   */
  private static int clampInt(int value, int min, int max) {
    if (value < min) {
      return min
    }
    if (value > max) {
      return max
    }
    return value
  }

  /**
   * Clamps a double value between min and max.
   */
  private static double clampDouble(double value, double min, double max) {
    if (value < min) {
      return min
    }
    if (value > max) {
      return max
    }
    return value
  }

  @Override
  boolean equals(Object obj) {
    if (!(obj instanceof Color)) {
      return false
    }
    Color other = (Color) obj
    return red == other.red &&
           green == other.green &&
           blue == other.blue &&
           Math.abs(alpha - other.alpha) < 0.0001
  }

  @Override
  int hashCode() {
    int result = red
    result = 31 * result + green
    result = 31 * result + blue
    result = 31 * result + (int) (alpha * 1000)
    return result
  }
}
