# SVG Accessibility Guide

This guide covers how to create accessible SVG graphics using gsvg's accessibility features.

## Table of Contents

- [Why SVG Accessibility Matters](#why-svg-accessibility-matters)
- [Basic Accessibility Patterns](#basic-accessibility-patterns)
- [ARIA Attributes](#aria-attributes)
- [Interactive Elements](#interactive-elements)
- [Decorative Graphics](#decorative-graphics)
- [Validation](#validation)
- [Best Practices](#best-practices)
- [Resources](#resources)

## Why SVG Accessibility Matters

SVG graphics can convey important information to users, but they're not automatically accessible to screen readers and assistive technologies. Proper accessibility ensures that:

- Screen reader users understand the purpose and content of graphics
- Keyboard-only users can interact with interactive SVG elements
- Users with cognitive disabilities benefit from clear, well-labeled graphics
- Your application meets WCAG 2.1 Level AA compliance

## Basic Accessibility Patterns

### Making an SVG Accessible

Every non-decorative SVG should have an accessible name. Here are the main patterns:

#### Pattern 1: Role and Label (Recommended)

```groovy
Svg svg = new Svg(200, 200)
svg.role('img')
   .ariaLabel('A bar chart showing monthly sales for 2024')

// Add your graphics
svg.addRect().x(10).y(50).width(30).height(100).fill('blue')
svg.addRect().x(50).y(30).width(30).height(120).fill('green')
```

**When to use:** For simple graphics where a brief description suffices.

#### Pattern 2: Title and Description

```groovy
Svg svg = new Svg(400, 300)
svg.addTitle('Monthly Sales Chart')
svg.addDesc('A bar chart showing monthly sales for 2024. January: $50K, February: $75K, March: $60K')

// Add your graphics
```

**When to use:** For complex graphics that need both a title and detailed description.

#### Pattern 3: Referenced Title and Description

```groovy
Svg svg = new Svg(400, 300)
svg.role('img')
   .ariaLabelledBy('chartTitle')
   .ariaDescribedBy('chartDesc')

svg.addTitle('Sales Dashboard').id('chartTitle')
svg.addDesc('Interactive dashboard showing quarterly sales trends and comparisons').id('chartDesc')

// Add your graphics
```

**When to use:** When you need fine-grained control or want to reference elements from multiple places.

## ARIA Attributes

gsvg provides helper methods for all common ARIA attributes:

### role()

Sets the ARIA role. Common values for SVG:
- `'img'` - Static image/graphic
- `'graphics-document'` - Complex graphic with multiple elements
- `'presentation'` - Decorative graphic (see Decorative Graphics)

```groovy
svg.role('img')
```

### ariaLabel()

Provides a text label for screen readers:

```groovy
circle.ariaLabel('Data point: January sales $50,000')
```

### ariaLabelledBy()

References one or more elements that provide the label:

```groovy
svg.ariaLabelledBy('title1')
svg.addTitle('Chart Title').id('title1')
```

Multiple references separated by spaces:

```groovy
svg.ariaLabelledBy('title1 subtitle1')
```

### ariaDescribedBy()

References elements that provide additional description:

```groovy
svg.ariaDescribedBy('desc1')
svg.addDesc('Detailed description here').id('desc1')
```

### ariaHidden()

Hides an element from screen readers:

```groovy
decorativeCircle.ariaHidden(true)
```

### ariaLive()

Announces dynamic content changes. Values: `'polite'`, `'assertive'`, `'off'`

```groovy
statusText.ariaLive('polite')
```

### Convenience Methods

#### decorative()

Shorthand for marking an element as decorative:

```groovy
backgroundRect.decorative()
// Equivalent to:
// backgroundRect.role('presentation').ariaHidden(true)
```

#### accessibleImage()

Shorthand for creating an accessible image with a label:

```groovy
logo.accessibleImage('Company Logo')
// Equivalent to:
// logo.role('img').ariaLabel('Company Logo')
```

## Interactive Elements

Interactive SVG elements (links, buttons, clickable graphics) need accessible names:

### Interactive Links

```groovy
Svg svg = new Svg(200, 100)
svg.role('img').ariaLabel('Navigation icons')

A link = svg.addA()
link.addTitle('View sales report')  // Accessible name for the link
link.href = '/reports/sales'

// Visual representation
link.addCircle().cx(50).cy(50).r(20).fill('blue')
link.addText('Report').x(35).y(55).fill('white')
```

### Clickable Elements

Elements with `onclick`, `href`, or similar interactive attributes should have labels:

```groovy
Circle dataPoint = svg.addCircle().cx(100).cy(50).r(5)
dataPoint.addAttribute('onclick', 'showDetails()')
dataPoint.ariaLabel('January: $50,000 - Click for details')
```

### Keyboard Navigation

For interactive SVG elements, ensure keyboard accessibility:

```groovy
A button = svg.addA()
button.addTitle('Download chart')
button.addAttribute('tabindex', '0')  // Make keyboard focusable
button.addAttribute('role', 'button')

// Add keyboard event handler
button.addAttribute('onkeypress', 'if(event.key==="Enter") download()')
```

## Decorative Graphics

Not all graphics convey meaningful information. Mark purely decorative elements to reduce screen reader noise:

### Individual Decorative Elements

```groovy
// Background decoration
Rect background = svg.addRect().width(200).height(200).fill('lightgray')
background.decorative()

// Decorative border
Circle border = svg.addCircle().cx(100).cy(100).r(50).stroke('gold').strokeWidth(2)
border.decorative()
```

### Entire Decorative SVG

```groovy
Svg decorativeBorder = new Svg(400, 20)
decorativeBorder.role('presentation')
decorativeBorder.ariaHidden(true)

// Add decorative patterns
```

## Validation

gsvg includes an `AccessibilityRule` to validate your SVG for common accessibility issues:

### Basic Validation

```groovy
import se.alipsa.groovy.svg.validation.ValidationEngine
import se.alipsa.groovy.svg.validation.ValidationReport

Svg svg = new Svg(200, 200)
// ... build your SVG ...

ValidationEngine engine = ValidationEngine.createDefault()
ValidationReport report = engine.validate(svg)

if (report.hasErrors()) {
    println "Errors:"
    report.errors.each { println "  - ${it}" }
}

if (report.hasWarnings()) {
    println "Warnings:"
    report.warnings.each { println "  - ${it}" }
}
```

### Accessibility-Only Validation

To check only accessibility issues:

```groovy
ValidationEngine engine = ValidationEngine.createAccessibility()
ValidationReport report = engine.validate(svg)

if (!report.isValid()) {
    println report.toString()
}
```

### What Gets Validated

The AccessibilityRule checks for:

1. **Root SVG accessibility** (WARNING)
   - SVG should have a role or accessible name (title, aria-label, aria-labelledby)
   - SVG with `role='img'` should have a label

2. **Interactive elements** (WARNING)
   - `<a>` elements should have accessible names
   - Elements with `onclick`, `href` should have labels

3. **ARIA references** (ERROR)
   - `aria-labelledby` references must exist
   - `aria-describedby` references must exist

### Example: Fixing Validation Issues

```groovy
// Before (has warnings)
Svg svg = new Svg(200, 200)
svg.addCircle().cx(100).cy(100).r(50).fill('blue')

// After validation
ValidationReport report = engine.validate(svg)
// WARNING: Root <svg> should have an accessible name

// Fixed
svg.role('img').ariaLabel('Blue circle graphic')

ValidationReport report2 = engine.validate(svg)
// No issues!
```

## Best Practices

### 1. Provide Context-Appropriate Descriptions

**Bad:**
```groovy
svg.ariaLabel('Chart')  // Too vague
```

**Good:**
```groovy
svg.ariaLabel('Bar chart showing quarterly revenue for 2024')
```

### 2. Keep Descriptions Concise

Aim for 150 characters or less for `ariaLabel`. Use `ariaDescribedBy` for longer descriptions.

**Bad:**
```groovy
svg.ariaLabel('This is a comprehensive visualization of sales data including revenue, profit margins, customer acquisition costs, and year-over-year growth percentages for each quarter of 2024 with comparisons to 2023 baseline metrics...')
```

**Good:**
```groovy
svg.role('img')
   .ariaLabelledBy('chartTitle')
   .ariaDescribedBy('chartDetails')

svg.addTitle('2024 Sales Dashboard').id('chartTitle')
svg.addDesc('Quarterly revenue, profit margins, customer acquisition costs, and YoY growth vs 2023').id('chartDetails')
```

### 3. Use Semantic Roles

Choose appropriate ARIA roles:

```groovy
// Static infographic
svg.role('img')

// Interactive data visualization
svg.role('graphics-document')

// Decorative flourish
svg.role('presentation')
```

### 4. Structure Complex Graphics

For complex graphics, provide hierarchical labeling:

```groovy
Svg svg = new Svg(600, 400)
svg.role('graphics-document')
   .ariaLabel('Sales comparison dashboard')

G revenueSection = svg.addG()
revenueSection.role('group')
              .ariaLabel('Revenue by quarter')

G profitSection = svg.addG()
profitSection.role('group')
             .ariaLabel('Profit margins by quarter')
```

### 5. Test with Screen Readers

Always test your accessible SVGs with actual screen readers:

- **NVDA** (Windows, free)
- **JAWS** (Windows, commercial)
- **VoiceOver** (macOS/iOS, built-in)
- **TalkBack** (Android, built-in)

### 6. Don't Duplicate Information

If an SVG has both visual text and an aria-label, they should convey the same information:

**Bad:**
```groovy
Text title = svg.addText('Sales Chart').x(10).y(20)
svg.ariaLabel('Revenue data')  // Conflicting information
```

**Good:**
```groovy
Text title = svg.addText('Sales Chart').x(10).y(20)
svg.ariaLabel('Sales Chart')  // Matches visual
```

### 7. Consider Color Contrast

While not handled by ARIA, ensure sufficient color contrast (4.5:1 for normal text, 3:1 for large text):

```groovy
// Good contrast
text.fill('#000000')  // Black on white = 21:1 ratio

// Poor contrast - avoid
text.fill('#CCCCCC')  // Light gray on white = 1.6:1 ratio
```

## Resources

### W3C Standards

- [WAI-ARIA Graphics Module](https://www.w3.org/TR/graphics-aria-1.0/)
- [SVG Accessibility Guidelines](https://www.w3.org/TR/SVG-access/)
- [WCAG 2.1](https://www.w3.org/TR/WCAG21/)

### Testing Tools

- [axe DevTools](https://www.deque.com/axe/devtools/) - Browser extension for accessibility testing
- [WAVE](https://wave.webaim.org/) - Web accessibility evaluation tool
- [Pa11y](https://pa11y.org/) - Automated accessibility testing

### Learning Resources

- [WebAIM: Accessible SVG](https://webaim.org/techniques/images/)
- [CSS-Tricks: Accessible SVGs](https://css-tricks.com/accessible-svgs/)
- [Deque University](https://dequeuniversity.com/) - Comprehensive accessibility courses

### gsvg Documentation

- [Creating SVG Elements](creating.md) - Basic SVG creation
- [Validation](validation.md) - SVG validation guide
- [Examples](examples.md) - More SVG examples

---

**Have questions or suggestions?** Please open an issue on the [gsvg GitHub repository](https://github.com/Alipsa/gsvg).
