# Navigating the SVG Model

This guide covers the various ways to navigate and traverse the SVG element tree in GSVG.

## Table of Contents

- [Understanding the Element Tree](#understanding-the-element-tree)
- [Direct Children Access](#direct-children-access)
- [Recursive Descendants](#recursive-descendants)
- [Type-Based Selection](#type-based-selection)
- [Predicate-Based Filtering](#predicate-based-filtering)
- [XPath Queries](#xpath-queries)
- [Parent and Hierarchy Navigation](#parent-and-hierarchy-navigation)
- [Collection Methods](#collection-methods)
- [Performance Considerations](#performance-considerations)
- [Common Patterns](#common-patterns)

## Understanding the Element Tree

SVG documents form a hierarchical tree structure:

```groovy
Svg (root)
├── Circle (direct child)
├── Rect (direct child)
└── G (group, direct child)
    ├── Circle (grandchild, nested)
    └── Rect (grandchild, nested)
```

GSVG provides different methods for navigating this tree depending on whether you want:
- Only direct children (1 level deep)
- All descendants (recursive, all levels)
- Specific types
- Elements matching criteria

## Direct Children Access

### The `.children` Property

Access only the **immediate children** of an element:

```groovy
Svg svg = new Svg(200, 200)
svg.addCircle().id('c1')
svg.addRect().id('r1')
G group = svg.addG().id('g1')
group.addCircle().id('c2')

// Only direct children
svg.children.size()  // Returns 3: c1, r1, g1
group.children.size()  // Returns 1: c2
```

**Use when:** You only care about immediate children, not nested elements.

### Index-Based Access

Access children by numeric index:

```groovy
SvgElement first = svg[0]      // First child
SvgElement second = svg[1]     // Second child
SvgElement last = svg[-1]      // Last child (Groovy negative indexing)
```

### Name-Based Access

Select all direct children by element name:

```groovy
List<SvgElement> circles = svg['circle']   // All direct circle children
List<SvgElement> rects = svg['rect']       // All direct rect children
```

**Note:** Returns all children with that tag name, regardless of type.

### Type-Based Access (Bracket Notation)

Select all direct children of a specific class (exact match):

```groovy
List<Circle> circles = svg[Circle]   // Type-safe, all direct Circle children
List<Rect> rects = svg[Rect]         // Type-safe, all direct Rect children
List<G> groups = svg[G]              // Type-safe, all direct G children
```

**Important:** Uses exact class matching, not `instanceof`. Does not match subclasses:

```groovy
svg[AbstractShape]  // Returns [] - abstract class, no exact matches
svg.filter { it instanceof AbstractShape }  // Use this instead for polymorphism
```

## Recursive Descendants

### The `.descendants()` Method

Search **recursively through the entire tree**:

```groovy
Svg svg = new Svg(200, 200)
svg.addCircle().id('c1')
G layer1 = svg.addG().id('layer1')
G layer2 = layer1.addG().id('layer2')
layer2.addCircle().id('c2')
layer2.addRect().id('r1')

// Direct children only
svg.children.size()  // 2: c1, layer1

// ALL descendants (recursive)
svg.descendants().size()  // 5: c1, layer1, layer2, c2, r1
```

### Typed Descendants

Filter descendants by type:

```groovy
// Find ALL circles anywhere in the tree
List<Circle> allCircles = svg.descendants(Circle)

// Find ALL groups anywhere in the tree
List<G> allGroups = svg.descendants(G)

// Find ALL rectangles anywhere in the tree
List<Rect> allRects = svg.descendants(Rect)
```

**Use when:** You need to find all instances of a type, regardless of nesting level.

### Comparison: Children vs Descendants

```groovy
Svg svg = new Svg()
svg.addCircle().id('direct-circle')
G group = svg.addG()
group.addCircle().id('nested-circle')

// Children - only direct
svg.children.size()              // 2: circle, group
svg[Circle].size()               // 1: direct-circle only

// Descendants - recursive
svg.descendants().size()         // 3: circle, group, nested-circle
svg.descendants(Circle).size()   // 2: both circles
```

## Type-Based Selection

### Using `findAll(Class)`

Find all direct children of a specific type (exact class match):

```groovy
List<Circle> circles = svg.findAll(Circle)
List<Rect> rects = svg.findAll(Rect)
```

**Equivalent to:** `svg[Circle]` but more explicit.

### With Predicates

Combine type selection with filtering:

```groovy
// Find all large circles (direct children only)
List<Circle> largeCircles = svg.findAll(Circle) {
    it.r as int > 50
}

// Find all red rectangles (direct children only)
List<Rect> redRects = svg.findAll(Rect) {
    it.fill == 'red'
}
```

### Find First

Get the first element of a type:

```groovy
Circle firstCircle = svg.findFirst(Circle)
Rect firstRect = svg.findFirst(Rect)

// Returns null if not found
Path path = svg.findFirst(Path)  // null if no paths
```

## Predicate-Based Filtering

### Using `filter()`

Filter children by any criteria:

```groovy
// Find all red elements
List<SvgElement> redElements = svg.filter { it.fill == 'red' }

// Find all elements with specific class
List<SvgElement> highlighted = svg.filter { it.hasClass('highlighted') }

// Find all visible elements
List<SvgElement> visible = svg.filter {
    it.getAttribute('display') != 'none'
}

// Polymorphic type matching
List<SvgElement> shapes = svg.filter { it instanceof AbstractShape }
```

**Use when:** You need flexible, criteria-based selection.

### Using `findFirst()`

Find the first element matching a predicate:

```groovy
// Find first red element
SvgElement redElement = svg.findFirst { it.fill == 'red' }

// Find element by ID
SvgElement logo = svg.findFirst { it.id == 'company-logo' }

// Returns null if no match
SvgElement purple = svg.findFirst { it.fill == 'purple' }  // null
```

## XPath Queries

For complex structural queries, use XPath expressions. GSVG provides full XPath support with automatic namespace handling.

### Basic XPath Queries

```groovy
// Find all circles anywhere in the tree
List<SvgElement> circles = svg.xpath('//circle')

// Find element by ID
List<SvgElement> elements = svg.xpath('//*[@id="logo"]')
SvgElement logo = elements.isEmpty() ? null : elements[0]

// Find by attribute value
List<SvgElement> redElements = svg.xpath('//*[@fill="red"]')

// Find elements with specific attribute (regardless of value)
List<SvgElement> withDataAttr = svg.xpath('//*[@data-category]')

// Find direct children only
List<SvgElement> directCircles = svg.xpath('/svg/circle')

// Find circles inside groups (one level)
List<SvgElement> groupCircles = svg.xpath('//g/circle')

// Find circles anywhere inside groups (any depth)
List<SvgElement> nestedCircles = svg.xpath('//g//circle')
```

### Advanced XPath Patterns

```groovy
// Multiple attributes
List<SvgElement> results = svg.xpath('//circle[@fill="red"][@r="50"]')

// Attribute contains
List<SvgElement> dataElements = svg.xpath('//*[contains(@class, "highlight")]')

// Position-based
List<SvgElement> firstChild = svg.xpath('/svg/*[1]')  // First child of svg
List<SvgElement> lastRect = svg.xpath('//rect[last()]')  // Last rect

// Combining paths
List<SvgElement> shapes = svg.xpath('//circle | //rect | //ellipse')

// Parent axis
List<SvgElement> parents = svg.xpath('//circle[@fill="red"]/parent::*')

// Sibling navigation
List<SvgElement> siblings = svg.xpath('//circle[@id="c1"]/following-sibling::*')
```

### Namespace Handling

**GSVG automatically handles SVG namespaces** for you. Simple queries like `//circle` are automatically transformed to work with namespaced SVG elements:

```groovy
// You write this (simple, clean)
svg.xpath('//circle')

// GSVG transforms it internally to (namespace-aware)
svg.xpath('//*[local-name()="circle"]')
```

This means you don't need to worry about XML namespaces in your queries - just use element names naturally.

### XPath on Nested Containers

You can call `xpath()` on any element container, not just the root:

```groovy
G group = svg.findFirst(G)

// Search within the group only
List<SvgElement> circlesInGroup = group.xpath('.//circle')

// Relative paths work too
List<SvgElement> directChildren = group.xpath('./*')
```

### Important Limitations

**Numeric Comparisons:** XPath numeric comparisons on attributes don't work reliably because attributes are stored as strings:

```groovy
// ❌ Don't use XPath for numeric comparisons
svg.xpath('//circle[@r>50]')  // Doesn't work reliably

// ✅ Use filter() with type conversion instead
svg.descendants(Circle).findAll { it.r as int > 50 }

// ✅ Or combine XPath with Groovy filtering
svg.xpath('//circle').findAll { (it.getAttribute('r') as int) > 50 }
```

**When to Use XPath:**
- ✅ Finding elements by structure (parent/child relationships)
- ✅ Attribute-based queries (exact matches, contains)
- ✅ Complex tree navigation
- ✅ Using standard XPath axes (parent, ancestor, sibling)

**When NOT to Use XPath:**
- ❌ Numeric comparisons on attributes
- ❌ Type-safe selection (use `[Type]` instead)
- ❌ Simple direct children selection (use `[Type]` or `filter()`)

### Practical Examples

**Find all visible circles:**
```groovy
List<SvgElement> visible = svg.xpath('//circle[not(@display="none")]')
```

**Find elements in specific layer:**
```groovy
List<SvgElement> layerElements = svg.xpath('//g[@id="layer1"]//*')
```

**Find circles that are grandchildren of groups:**
```groovy
List<SvgElement> nested = svg.xpath('//g/*/circle')
```

**Find all elements with CSS classes:**
```groovy
List<SvgElement> styled = svg.xpath('//*[@class]')
```

**Modify results:**
```groovy
// Find and modify in one go
svg.xpath('//rect[@fill="red"]').each { rect ->
    rect.stroke('black')
    rect.strokeWidth(2)
}
```

## Parent and Hierarchy Navigation

### Accessing the Parent

Every element has a `parent` property:

```groovy
Circle circle = svg.addCircle()
G group = svg.addG()
Rect rect = group.addRect()

// Navigate up the tree
assert circle.parent == svg
assert rect.parent == group
assert group.parent == svg
```

### Walking Up the Hierarchy

```groovy
// Find the root SVG element
SvgElement element = /* some deep element */
while (!(element instanceof Svg)) {
    element = element.parent
}
Svg root = element as Svg
```

### Finding Ancestors

```groovy
// Check if element is inside a specific group
boolean isInGroup(SvgElement element, String groupId) {
    SvgElement current = element
    while (current != null) {
        if (current.id == groupId) return true
        current = current.parent
    }
    return false
}
```

## Collection Methods

GSVG provides functional-style collection methods on direct children:

### `any()` - Check if Any Match

```groovy
// Are there any red elements?
boolean hasRed = svg.any { it.fill == 'red' }

// Are there any circles?
boolean hasCircles = svg.any { it instanceof Circle }
```

### `all()` - Check if All Match

```groovy
// Are all elements visible?
boolean allVisible = svg.all { it.getAttribute('display') != 'none' }

// Do all elements have an ID?
boolean allHaveId = svg.all { it.id != null }
```

### `count()` - Count Matches

```groovy
// Count red elements
int redCount = svg.count { it.fill == 'red' }

// Count circles
int circleCount = svg.count { it instanceof Circle }
```

### `collect()` - Transform Elements

```groovy
// Collect all IDs
List<String> ids = svg.collect { it.id }

// Collect all fill colors
List<String> colors = svg.collect { it.fill }

// Collect element types
List<String> types = svg.collect { it.class.simpleName }
```

## Performance Considerations

### Choose the Right Method

| Need | Method | Performance |
|------|--------|-------------|
| Direct children only | `.children` | O(1) - direct access |
| Direct children by type | `[Type]` | O(n) - single pass |
| Direct children by criteria | `.filter { }` | O(n) - single pass |
| All descendants | `.descendants()` | O(n) - full tree traversal |
| Specific descendant | `.descendants().find { }` | O(n) - stops at first match |
| Complex queries | `.xpath()` | Varies - depends on query |

### Optimization Tips

1. **Prefer specific over general:**
   ```groovy
   // Fast - only checks direct children
   svg[Circle]

   // Slower - searches entire tree
   svg.descendants(Circle)
   ```

2. **Filter before recursing:**
   ```groovy
   // Better - filter after getting type
   svg.descendants(Circle).findAll { it.r as int > 50 }

   // Slower - checks every element
   svg.descendants().findAll { it instanceof Circle && it.r as int > 50 }
   ```

3. **Use `findFirst` to stop early:**
   ```groovy
   // Stops at first match
   Circle first = svg.findFirst(Circle)

   // Finds all, then gets first
   Circle first = svg.findAll(Circle)[0]  // Wasteful if many circles
   ```

4. **Cache frequent lookups:**
   ```groovy
   // Don't repeat expensive searches
   List<Circle> circles = svg.descendants(Circle)  // Once
   circles.each { /* use it */ }

   // Avoid
   svg.descendants(Circle).each { /* repeats search */ }
   ```

## Common Patterns

### Find All Elements of a Type Anywhere

```groovy
// Direct children only
List<Circle> directCircles = svg[Circle]

// Entire tree
List<Circle> allCircles = svg.descendants(Circle)
```

### Find Element by ID

```groovy
// Using findFirst
SvgElement element = svg.descendants().find { it.id == 'logo' }

// Using XPath
List<SvgElement> elements = svg.xpath('//*[@id="logo"]')
SvgElement element = elements.isEmpty() ? null : elements[0]
```

### Get All Elements with a Specific Attribute

```groovy
// Using filter on descendants
List<SvgElement> dataElements = svg.descendants().findAll {
    it.getAttribute('data-category') != null
}

// Using XPath
List<SvgElement> dataElements = svg.xpath('//*[@data-category]')
```

### Modify All Elements of a Type

```groovy
// Direct children
svg[Circle].each { it.fill('red') }

// Entire tree
svg.descendants(Circle).each { it.fill('red') }
```

### Find Deeply Nested Elements

```groovy
// Navigate through specific structure
G layer = svg.findFirst(G)
G sublayer = layer?.findFirst(G)
Circle circle = sublayer?.findFirst(Circle)

// Or search entire tree
Circle circle = svg.descendants(Circle).find {
    it.id == 'deep-circle'
}
```

### Group Elements by Type

```groovy
def byType = svg.descendants().groupBy { it.class.simpleName }
println "Circles: ${byType['Circle']?.size() ?: 0}"
println "Rects: ${byType['Rect']?.size() ?: 0}"
println "Groups: ${byType['G']?.size() ?: 0}"
```

### Find Elements in Specific Container

```groovy
// Find all circles inside groups
List<Circle> groupCircles = svg[G].collectMany { group ->
    group[Circle]
}

// Or using descendants on each group
svg[G].each { group ->
    group.descendants(Circle).each { circle ->
        // Process circles inside this group
    }
}
```

### Conditional Navigation

```groovy
// Find all red circles in visible groups
List<Circle> results = svg[G]
    .findAll { it.getAttribute('display') != 'none' }
    .collectMany { it[Circle] }
    .findAll { it.fill == 'red' }
```

## Quick Reference

### Scope Comparison

| Method | Scope | Type Filter | Returns |
|--------|-------|-------------|---------|
| `.children` | Direct children | No | `List<SvgElement>` |
| `[index]` | Direct children | No | `SvgElement` |
| `['name']` | Direct children | By tag name | `List<SvgElement>` |
| `[Type]` | Direct children | Exact class | `List<Type>` |
| `.findAll(Type)` | Direct children | Exact class | `List<Type>` |
| `.findFirst(Type)` | Direct children | Exact class | `Type` or null |
| `.filter { }` | Direct children | By predicate | `List<SvgElement>` |
| `.findFirst { }` | Direct children | By predicate | `SvgElement` or null |
| `.descendants()` | All descendants | No | `List<SvgElement>` |
| `.descendants(Type)` | All descendants | Exact class | `List<Type>` |
| `.xpath(query)` | By query | By XPath | `List<SvgElement>` |

### When to Use What

```groovy
// I want all direct circles
svg[Circle]

// I want all circles anywhere in the tree
svg.descendants(Circle)

// I want the first red element
svg.findFirst { it.fill == 'red' }

// I want all elements with a specific class
svg.filter { it.hasClass('highlight') }

// I want to check if any element is invisible
svg.any { it.getAttribute('display') == 'none' }

// I want all circles with radius > 50 anywhere
svg.descendants(Circle).findAll { it.r as int > 50 }

// I want element by ID (anywhere)
svg.descendants().find { it.id == 'logo' }

// I want all elements (entire tree)
svg.descendants()
```

## See Also

- [Modifying SVG Content](modifying.md) - How to add, edit, and remove elements
- [Creating SVG Documents](creating.md) - Creating new SVG documents
- [API Cheat Sheet](api-cheat-sheet.md) - Quick reference for all methods
