# Modifying SVG Content

This guide covers how to modify SVG documents dynamically by adding, editing, and removing elements and attributes.

## Table of Contents

- [Adding Elements](#adding-elements)
- [Selecting Elements](#selecting-elements)
- [Editing Attributes](#editing-attributes)
- [Removing Elements](#removing-elements)
- [Removing Attributes](#removing-attributes)
- [Reparenting Elements](#reparenting-elements)
- [Replacing Elements](#replacing-elements)
- [Batch Operations](#batch-operations)

## Adding Elements

### Basic Element Addition

All container elements provide factory methods for adding child elements:

```groovy
Svg svg = new Svg(400, 400)

// Add elements using factory methods
Circle circle = svg.addCircle().cx(100).cy(100).r(50).fill('red')
Rect rect = svg.addRect().x(10).y(10).width(80).height(80).fill('blue')
Line line = svg.addLine().x1(0).y1(0).x2(100).y2(100).stroke('black')
```

### Adding to Groups

Elements can be added to any container, including groups:

```groovy
G group = svg.addG().fill('green')
group.addCircle().cx(50).cy(50).r(25)
group.addCircle().cx(100).cy(50).r(25)
```

### Adding with DSL Configuration

Use closures for concise element configuration:

```groovy
svg.addCircle {
    cx 100
    cy 100
    r 50
    fill 'purple'
    stroke 'black'
    strokeWidth 2
}
```

### Adding Multiple Elements

```groovy
// Add multiple elements in a loop
(1..5).each { i ->
    svg.addCircle()
       .cx(i * 50)
       .cy(100)
       .r(20)
       .fill("hsl(${i * 60}, 70%, 50%)")
}
```

## Selecting Elements

Before modifying elements, you often need to select them. GSVG provides idiomatic Groovy syntax for element selection.

### Selecting by Type (Bracket Notation)

The most idiomatic way to select elements by type is using bracket notation:

```groovy
// Select all circles - returns List<Circle>
List<Circle> circles = svg[Circle]

// Select all rectangles
List<Rect> rectangles = svg[Rect]

// Select all groups
List<G> groups = svg[G]

// Works with any element type
List<Path> paths = svg[Path]
List<Text> textElements = svg[Text]
```

This bracket notation (`svg[Type]`) is equivalent to calling `getAt(Class)` and is more concise and idiomatic than using filters or predicates.

**Important**: Bracket notation uses **exact class matching** (not `instanceof`). For abstract classes or interfaces, use `filter { it instanceof Type }` instead. See [Navigating the SVG Model](navigating.md#direct-children) for detailed explanation of exact matching behavior.

### Selecting by Name

Select elements by their tag name:

```groovy
// Returns all elements with name 'circle'
List<SvgElement> circles = svg['circle']

// Returns all elements with name 'rect'
List<SvgElement> rects = svg['rect']
```

### Selecting with Predicates

For complex selection criteria, use `filter()` or `findAll()`:

```groovy
// Select circles with radius > 50
def largeCircles = svg[Circle].findAll { it.r as int > 50 }

// Select red elements
def redElements = svg.filter { it.getAttribute('fill') == 'red' }

// Select elements with specific class
def highlighted = svg.filter { it.hasClass('highlighted') }
```

### Combining Selection Methods

```groovy
// Get all large circles and make them red
svg[Circle].findAll { it.r as int > 50 }.each { circle ->
    circle.fill('red')
}

// Find all groups and iterate their circles
svg[G].each { group ->
    group[Circle].each { circle ->
        circle.opacity(0.8)
    }
}
```

### Performance Note

- **Type selection** (`svg[Circle]`) is fast - O(n) single pass
- **Predicate filtering** requires evaluation of the closure for each element
- For best performance, select by type first, then filter:
  ```groovy
  // Efficient: filter only circles
  svg[Circle].findAll { it.r as int > 50 }

  // Less efficient: check type for every element
  svg.filter { it instanceof Circle && it.r as int > 50 }
  ```

## Editing Attributes

### Setting Attributes

Use fluent API methods for common attributes:

```groovy
circle.cx(150)
      .cy(150)
      .r(75)
      .fill('orange')
      .stroke('black')
      .strokeWidth(3)
```

### Using addAttribute

For arbitrary attributes or programmatic setting:

```groovy
// Set single attribute
circle.addAttribute('data-id', 'circle-1')
circle.addAttribute('opacity', '0.8')

// Set multiple attributes
circle.addAttributes([
    'data-category': 'shape',
    'data-color': 'primary',
    'aria-label': 'Circle shape'
])
```

### Property-Style Assignment

For convenience, use Groovy property syntax:

```groovy
circle.opacity = '0.5'
circle.transform = 'rotate(45 100 100)'
```

### Getting Attributes

```groovy
String fill = circle.getAttribute('fill')
String cx = circle.getCx()

// Or use property access
String fill = circle.fill
String cx = circle.cx
```

### Modifying CSS Classes

```groovy
// Add classes
rect.addClass('highlighted')
rect.addClass('active selected')

// Remove classes
rect.removeClass('highlighted')

// Toggle classes
rect.toggleClass('active')

// Check if has class
if (rect.hasClass('highlighted')) {
    // ...
}
```

### Modifying Inline Styles

Inline styles are treated as a map:

```groovy
// Get style as map
Map<String, String> style = circle.getStyle()

// Modify specific style properties
style.opacity = '0.8'
style.filter = 'blur(2px)'

// Set back
circle.style(style)

// Or chain directly
circle.style([
    opacity: '0.8',
    filter: 'blur(2px)'
])
```

## Removing Elements

### Basic Removal

Remove child elements using the `remove()` or `removeChild()` method:

```groovy
Circle c = svg.addCircle().cx(100).cy(100).r(50)

// Remove the element
svg.remove(c)
c = null  // Allow garbage collection

// Or use removeChild() alias
svg.removeChild(c)
```

### Removing Elements by Name

Remove all child elements with a specific name:

```groovy
// Add multiple circles
svg.addCircle().cx(50).cy(50).r(25)
svg.addCircle().cx(100).cy(100).r(30)
svg.addCircle().cx(150).cy(150).r(35)

// Remove all circles
int removed = svg.removeChild('circle')
println "Removed $removed circles"

// Remove all rectangles
svg.removeChild('rect')

// Remove title elements
svg.removeChild('title')
```

### Conditional Removal

Remove elements based on criteria:

```groovy
// Find and remove all red circles
svg[Circle].each { circle ->
    if (circle.fill == 'red') {
        svg.remove(circle)
    }
}

// Remove all invisible elements
svg.children.findAll { element ->
    element.getAttribute('display') == 'none' ||
    element.getAttribute('visibility') == 'hidden'
}.each { element ->
    svg.remove(element)
}
```

### Removing from Groups

```groovy
G group = svg.addG()
Circle c1 = group.addCircle().cx(50).cy(50).r(25)
Circle c2 = group.addCircle().cx(100).cy(50).r(25)

// Remove specific child from group
group.remove(c1)
```

### Removing Multiple Elements

```groovy
// Collect elements to remove first to avoid concurrent modification
def toRemove = svg[Circle].findAll { it.r as int < 10 }
toRemove.each { svg.remove(it) }
```

### Removing Nested Elements

```groovy
// Remove all circles from all groups
svg[G].each { group ->
    def circles = group[Circle]
    circles.each { circle ->
        group.remove(circle)
    }
}
```

### Best Practices for Removal

1. **Always collect before removing** to avoid concurrent modification:
   ```groovy
   // Good
   def toRemove = svg.children.findAll { /* criteria */ }
   toRemove.each { svg.remove(it) }

   // Bad - may cause ConcurrentModificationException
   svg.children.each { element ->
       if (/* criteria */) {
           svg.remove(element)  // Modifying while iterating
       }
   }
   ```

2. **Set references to null** after removal for garbage collection:
   ```groovy
   svg.remove(circle)
   circle = null
   ```

3. **Check parent before removing**:
   ```groovy
   if (element.parent != null) {
       element.parent.remove(element)
   }
   ```

## Removing Attributes

### Remove Single Attribute

```groovy
// Remove specific attribute
circle.removeAttribute('opacity')
circle.removeAttribute('data-id')

// Verify removal
assert circle.getAttribute('opacity') == null
```

### Remove Multiple Attributes

```groovy
// Remove several attributes
['opacity', 'transform', 'filter'].each { attr ->
    circle.removeAttribute(attr)
}
```

### Conditional Attribute Removal

```groovy
// Remove all data- attributes
int removed = circle.removeAttributes { it.startsWith('data-') }
println "Removed $removed data- attributes"

// Remove all aria- attributes
rect.removeAttributes { it.startsWith('aria-') }

// Remove specific attributes
element.removeAttributes { it in ['opacity', 'transform', 'filter'] }
```

## Reparenting Elements

### Moving Elements Between Containers

To move an element from one parent to another:

```groovy
Svg svg = new Svg(400, 400)
G group1 = svg.addG().id('group1')
G group2 = svg.addG().id('group2')

// Create circle in group1
Circle circle = group1.addCircle().cx(100).cy(100).r(50)

// Move to group2
group1.remove(circle)
group2.element.add(circle.element)
group2.children.add(circle)
circle.parent = group2
```

### Helper Method for Reparenting

```groovy
// You can create a helper method
def moveElement(SvgElement element, container) {
    if (element.parent instanceof ElementContainer) {
        element.parent.remove(element)
    }
    container.element.add(element.element)
    if (container instanceof ElementContainer) {
        container.children.add(element)
    }
    element.parent = container
}

// Usage
moveElement(circle, group2)
```

## Replacing Elements

### Basic Replacement

```groovy
// Replace circle with rectangle at same position
Circle circle = svg.addCircle().cx(100).cy(100).r(50)
int index = svg.children.indexOf(circle)

svg.remove(circle)
Rect rect = svg.addRect().x(75).y(75).width(50).height(50)

// Reorder if needed
svg.children.remove(rect)
svg.children.add(index, rect)
```

### Replace While Preserving Attributes

```groovy
// Copy common attributes before replacing
Circle circle = svg.addCircle().cx(100).cy(100).r(50).fill('red')

// Create replacement with same visual attributes
Rect replacement = svg.addRect()
    .x(75)
    .y(75)
    .width(50)
    .height(50)
    .fill(circle.fill)
    .stroke(circle.stroke)

svg.remove(circle)
```

## Batch Operations

### Adding Elements in Batch

```groovy
// Create grid of circles
(0..<5).each { row ->
    (0..<5).each { col ->
        svg.addCircle()
           .cx(50 + col * 60)
           .cy(50 + row * 60)
           .r(20)
           .fill("hsl(${(row * 5 + col) * 15}, 70%, 50%)")
    }
}
```

### Batch Attribute Updates

```groovy
// Update all circles to red
svg[Circle].each { circle ->
    circle.fill('red').strokeWidth(2)
}

// Set opacity on all shapes
svg.descendants().each { element ->
    if (element instanceof AbstractShape) {
        element.opacity(0.8)
    }
}
```

### Batch Removal

```groovy
// Remove all small circles
def smallCircles = svg[Circle].findAll { it.r as int < 20 }
smallCircles.each { svg.remove(it) }

// Remove all metadata elements
def metadata = svg.children.findAll {
    it instanceof Title || it instanceof Desc || it instanceof Metadata
}
metadata.each { svg.remove(it) }
```

### Transform All Elements

```groovy
// Apply transformation to all shapes (direct children only)
// Note: Bracket notation uses exact class matching, not instanceof
// Use filter() with instanceof to match abstract classes or subclasses
svg.filter { it instanceof AbstractShape }.each { element ->
    element.transform("translate(50, 50) rotate(45)")
}

// Or transform all shape descendants
svg.descendants().each { element ->
    if (element instanceof AbstractShape) {
        element.transform("translate(50, 50) rotate(45)")
    }
}

// For concrete types, bracket notation is idiomatic
svg[Circle].each { circle ->
    circle.transform("translate(10, 10)")
}
```

## Advanced Examples

### Dynamic Element Replacement Based on Type

```groovy
// Replace all circles with rectangles
svg[Circle].each { circle ->
    // Calculate equivalent rect dimensions
    double r = circle.r as double
    double cx = circle.cx as double
    double cy = circle.cy as double

    // Create equivalent rectangle
    Rect rect = svg.addRect()
        .x(cx - r)
        .y(cy - r)
        .width(r * 2)
        .height(r * 2)
        .fill(circle.fill)
        .stroke(circle.stroke)

    // Remove original circle
    svg.remove(circle)
}
```

### Conditional Modification

```groovy
// Make all large shapes semi-transparent
svg.descendants().each { element ->
    if (element instanceof Circle && (element.r as int) > 50) {
        element.opacity(0.5)
    }
    if (element instanceof Rect) {
        int width = element.width as int
        int height = element.height as int
        if (width * height > 5000) {
            element.opacity(0.5)
        }
    }
}
```

### Cleanup and Optimization

```groovy
// Remove empty groups
svg[G].each { group ->
    if (group.children.isEmpty()) {
        svg.remove(group)
    }
}

// Remove invisible elements
svg.descendants().findAll { element ->
    element.getAttribute('display') == 'none' ||
    element.getAttribute('visibility') == 'hidden' ||
    element.getAttribute('opacity') == '0'
}.each { element ->
    if (element.parent instanceof ElementContainer) {
        element.parent.remove(element)
    }
}

// Remove default attribute values
svg.descendants().each { element ->
    if (element.getAttribute('fill') == 'black') {
        element.removeAttribute('fill')  // black is default
    }
    if (element.getAttribute('stroke') == 'none') {
        element.removeAttribute('stroke')  // none is default
    }
}
```

## Memory Management

When working with many elements, proper cleanup is important:

```groovy
// Remove elements and clear references
def circles = svg[Circle]
circles.each { circle ->
    svg.remove(circle)
}
circles.clear()
circles = null  // Allow list to be garbage collected

// Don't hold references to removed elements
Circle temp = svg.addCircle()
svg.remove(temp)
temp = null  // Release reference
```

## See Also

- [Navigating the SVG Model](navigating.md) - How to find and traverse elements
- [Creating SVG Documents](creating.md) - Creating new SVG documents
- [Parsing SVG](parsing.md) - Loading and parsing existing SVG files
- [API Cheat Sheet](api-cheat-sheet.md) - Quick reference for all methods
- [Examples](examples.md) - More complete examples
