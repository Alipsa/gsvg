# Missing Convenience Methods - Implementation Plan

This document outlines convenience methods that should be added based on gaps discovered during test writing.

## Summary

During test development, several commonly-used SVG attributes were found to lack convenience methods, requiring fallback to `getAttribute()` or property access. While the new `getProperty()`/`setProperty()` overrides provide workarounds, explicit methods offer better:
- IDE autocomplete
- API documentation
- Type safety with @CompileStatic
- Prevention of typos

## High Priority - Commonly Used Attributes

### 1. Stop.groovy
**Missing:** `stopOpacity()` setter and getter

```groovy
/**
 * Sets the stop-opacity attribute.
 *
 * @param opacity the opacity value (0-1)
 * @return this element for chaining
 */
Stop stopOpacity(String opacity) {
  addAttribute("stop-opacity", opacity)
}

/**
 * Sets the stop-opacity attribute.
 *
 * @param opacity the opacity value (0-1)
 * @return this element for chaining
 */
Stop stopOpacity(Number opacity) {
  addAttribute("stop-opacity", opacity)
}

/**
 * Returns the stop opacity value.
 *
 * @return the stop opacity value
 */
String getStopOpacity() {
  getAttribute("stop-opacity")
}
```

**Rationale:** Stop opacity is a fundamental gradient attribute used in almost all advanced gradients.

---

### 2. RadialGradient.groovy
**Missing:** `fr()` (focal radius) setter and getter

```groovy
/**
 * Sets the fr attribute (focal point radius).
 *
 * @param fr the focal point radius
 * @return this element for chaining
 */
RadialGradient fr(String fr) {
  addAttribute('fr', fr)
}

/**
 * Sets the fr attribute (focal point radius).
 *
 * @param fr the focal point radius
 * @return this element for chaining
 */
RadialGradient fr(Number fr) {
  addAttribute('fr', "$fr")
}

/**
 * Returns the fr value.
 *
 * @return the fr value
 */
String getFr() {
  getAttribute('fr')
}
```

**Rationale:** The `fr` attribute controls the focal point radius in radial gradients, important for creating spotlight effects.

---

### 3. Gradient.groovy (base class)
**Missing:** Getters for existing setter methods

```groovy
/**
 * Returns the gradient units value.
 *
 * @return the gradient units value
 */
String getGradientUnits() {
  getAttribute('gradientUnits')
}

/**
 * Returns the gradient transform value.
 *
 * @return the gradient transform value
 */
String getGradientTransform() {
  getAttribute('gradientTransform')
}

/**
 * Returns the spread method value.
 *
 * @return the spread method value
 */
String getSpreadMethod() {
  getAttribute('spreadMethod')
}
```

**Rationale:** These setters exist but lack corresponding getters, making it asymmetric and harder to inspect gradient configuration.

---

### 4. Text.groovy and Tspan.groovy
**Missing:** Getters for x, y, dx, dy

**Text.groovy:**
```groovy
/**
 * Returns the x value.
 *
 * @return the x value
 */
String getX() {
  getAttribute('x')
}

/**
 * Returns the y value.
 *
 * @return the y value
 */
String getY() {
  getAttribute('y')
}

/**
 * Returns the dx value.
 *
 * @return the dx value
 */
String getDx() {
  getAttribute('dx')
}

/**
 * Returns the dy value.
 *
 * @return the dy value
 */
String getDy() {
  getAttribute('dy')
}

/**
 * Returns the fill value.
 *
 * @return the fill value
 */
String getFill() {
  getAttribute('fill')
}
```

**Tspan.groovy:**
```groovy
/**
 * Returns the x value.
 *
 * @return the x value
 */
String getX() {
  getAttribute('x')
}

/**
 * Returns the y value.
 *
 * @return the y value
 */
String getY() {
  getAttribute('y')
}

/**
 * Returns the dx value.
 *
 * @return the dx value
 */
String getDx() {
  getAttribute('dx')
}

/**
 * Returns the dy value.
 *
 * @return the dy value
 */
String getDy() {
  getAttribute('dy')
}

/**
 * Returns the fill value.
 *
 * @return the fill value
 */
String getFill() {
  getAttribute('fill')
}
```

**Rationale:** These position attributes have setters but no getters, creating API asymmetry.

---

## Medium Priority - Animation Attributes

### 5. Animate.groovy
**Missing:** Several animation timing/interpolation methods

```groovy
/**
 * Sets the calcMode attribute (animation interpolation mode).
 *
 * @param mode the calculation mode (discrete, linear, paced, spline)
 * @return this element for chaining
 */
Animate calcMode(String mode) {
  addAttribute('calcMode', mode)
}

/**
 * Returns the calcMode value.
 *
 * @return the calcMode value
 */
String getCalcMode() {
  getAttribute('calcMode')
}

/**
 * Sets the additive attribute.
 *
 * @param additive whether animation is additive (replace, sum)
 * @return this element for chaining
 */
Animate additive(String additive) {
  addAttribute('additive', additive)
}

/**
 * Returns the additive value.
 *
 * @return the additive value
 */
String getAdditive() {
  getAttribute('additive')
}

/**
 * Sets the accumulate attribute.
 *
 * @param accumulate whether animation accumulates (none, sum)
 * @return this element for chaining
 */
Animate accumulate(String accumulate) {
  addAttribute('accumulate', accumulate)
}

/**
 * Returns the accumulate value.
 *
 * @return the accumulate value
 */
String getAccumulate() {
  getAttribute('accumulate')
}

/**
 * Sets the end attribute.
 *
 * @param time the end time for the animation
 * @return this element for chaining
 */
Animate end(String time) {
  addAttribute('end', time)
}

/**
 * Returns the end value.
 *
 * @return the end value
 */
String getEnd() {
  getAttribute('end')
}

/**
 * Sets the keyTimes attribute.
 *
 * @param times semicolon-separated time values
 * @return this element for chaining
 */
Animate keyTimes(String times) {
  addAttribute('keyTimes', times)
}

/**
 * Returns the keyTimes value.
 *
 * @return the keyTimes value
 */
String getKeyTimes() {
  getAttribute('keyTimes')
}

/**
 * Sets the repeatDur attribute.
 *
 * @param duration total duration for repeating
 * @return this element for chaining
 */
Animate repeatDur(String duration) {
  addAttribute('repeatDur', duration)
}

/**
 * Returns the repeatDur value.
 *
 * @return the repeatDur value
 */
String getRepeatDur() {
  getAttribute('repeatDur')
}

/**
 * Sets the repeatCount attribute.
 *
 * @param count number of times to repeat
 * @return this element for chaining
 */
Animate repeatCount(String count) {
  addAttribute('repeatCount', count)
}

/**
 * Returns the repeatCount value.
 *
 * @return the repeatCount value
 */
String getRepeatCount() {
  getAttribute('repeatCount')
}
```

**Rationale:** These are standard SMIL animation attributes needed for sophisticated animations. Currently only basic attributes are supported.

---

## Low Priority - Link Attributes

### 6. A.groovy
**Missing:** Additional hyperlink attributes

```groovy
/**
 * Sets the download attribute.
 *
 * @param filename the filename for download
 * @return this element for chaining
 */
A download(String filename) {
  addAttribute('download', filename)
}

/**
 * Returns the download value.
 *
 * @return the download value
 */
String getDownload() {
  getAttribute('download')
}

/**
 * Sets the type attribute (MIME type).
 *
 * @param type the MIME type
 * @return this element for chaining
 */
A type(String type) {
  addAttribute('type', type)
}

/**
 * Returns the type value.
 *
 * @return the type value
 */
String getType() {
  getAttribute('type')
}

/**
 * Sets the rel attribute.
 *
 * @param rel the relationship type
 * @return this element for chaining
 */
A rel(String rel) {
  addAttribute('rel', rel)
}

/**
 * Returns the rel value.
 *
 * @return the rel value
 */
String getRel() {
  getAttribute('rel')
}
```

**Rationale:** These HTML5 link attributes are less commonly used in SVG but complete the API for modern hyperlink functionality.

---

## Implementation Priority

1. **Phase 1 (High Value):**
   - Stop.stopOpacity()
   - RadialGradient.fr()
   - Gradient getters (gradientUnits, gradientTransform, spreadMethod)
   - Text/Tspan getters (x, y, dx, dy, fill)

2. **Phase 2 (Animation Support):**
   - Animate animation control methods

3. **Phase 3 (Completeness):**
   - A link attributes

## Testing Strategy

For each added method:
1. Add test in corresponding test file
2. Verify setter works with both String and Number (where applicable)
3. Verify getter returns correct value
4. Test with Map-based factory method (if applicable)
5. Verify property-style access works as fallback

## Estimated Impact

- **Files to modify:** 6 classes (Stop, RadialGradient, Gradient, Text, Tspan, Animate, A)
- **New methods:** ~40 methods total
- **New tests:** ~20-30 test cases
- **Test files to update:** 6 test files

## Notes

- All new methods should follow existing patterns in the codebase
- Maintain consistency with existing getter/setter naming
- Document SVG specification references where appropriate
- Consider adding enum support for attributes with fixed value sets (e.g., calcMode, additive, accumulate)
