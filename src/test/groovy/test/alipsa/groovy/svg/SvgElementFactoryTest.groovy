package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.io.SvgReader

import static org.junit.jupiter.api.Assertions.*

class SvgElementFactoryTest {

    @Test
    void testFromElementWithCircle() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <circle cx="50" cy="50" r="40" fill="red"/>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Circle circle = svg.children[0] as Circle
        assertNotNull(circle)
        assertEquals('50', circle.cx)
        assertEquals('50', circle.cy)
        assertEquals('40', circle.r)
        assertEquals('red', circle.fill)
    }

    @Test
    void testFromElementWithRect() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <rect x="10" y="20" width="100" height="50" fill="blue"/>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Rect rect = svg.children[0] as Rect
        assertNotNull(rect)
        assertEquals('10', rect.x)
        assertEquals('20', rect.y)
        assertEquals('100', rect.width)
        assertEquals('50', rect.height)
        assertEquals('blue', rect.fill)
    }

    @Test
    void testFromElementWithPath() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <path d="M 10 10 L 90 90" stroke="black"/>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Path path = svg.children[0] as Path
        assertNotNull(path)
        assertEquals('M 10 10 L 90 90', path.d)
        assertEquals('black', path.stroke)
    }

    @Test
    void testFromElementWithLine() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <line x1="0" y1="0" x2="100" y2="100" stroke="green"/>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Line line = svg.children[0] as Line
        assertNotNull(line)
        assertEquals('0', line.x1)
        assertEquals('0', line.y1)
        assertEquals('100', line.x2)
        assertEquals('100', line.y2)
    }

    @Test
    void testFromElementWithEllipse() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <ellipse cx="50" cy="50" rx="30" ry="20"/>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Ellipse ellipse = svg.children[0] as Ellipse
        assertNotNull(ellipse)
        assertEquals('50', ellipse.cx)
        assertEquals('50', ellipse.cy)
        assertEquals('30', ellipse.rx)
        assertEquals('20', ellipse.ry)
    }

    @Test
    void testFromElementWithPolygon() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <polygon points="50,5 100,100 0,100"/>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Polygon polygon = svg.children[0] as Polygon
        assertNotNull(polygon)
        assertEquals('50,5 100,100 0,100', polygon.points)
    }

    @Test
    void testFromElementWithPolyline() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <polyline points="0,0 50,50 100,0"/>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Polyline polyline = svg.children[0] as Polyline
        assertNotNull(polyline)
        assertEquals('0,0 50,50 100,0', polyline.points)
    }

    @Test
    void testFromElementWithG() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <g fill="red">
                <circle cx="10" cy="10" r="5"/>
                <circle cx="20" cy="20" r="5"/>
            </g>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        G g = svg.children[0] as G
        assertNotNull(g)
        assertEquals('red', g.getAttribute('fill'))
        assertEquals(2, g.children.size())
        assertTrue(g.children[0] instanceof Circle)
        assertTrue(g.children[1] instanceof Circle)
    }

    @Test
    void testFromElementWithDefs() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <defs>
                <circle id="myCircle" cx="10" cy="10" r="5"/>
            </defs>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Defs defs = svg.children[0] as Defs
        assertNotNull(defs)
        assertEquals(1, defs.children.size())
        assertTrue(defs.children[0] instanceof Circle)
    }

    @Test
    void testFromElementWithSymbol() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <symbol id="mySymbol" viewBox="0 0 100 100">
                <circle cx="50" cy="50" r="40"/>
            </symbol>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Symbol symbol = svg.children[0] as Symbol
        assertNotNull(symbol)
        assertEquals('mySymbol', symbol.id)
        assertEquals('0 0 100 100', symbol.viewBox)
        assertEquals(1, symbol.children.size())
    }

    @Test
    void testFromElementWithA() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <a href="https://example.com">
                <text x="0" y="15">Link</text>
            </a>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        A a = svg.children[0] as A
        assertNotNull(a)
        assertEquals('https://example.com', a.href)
        assertEquals(1, a.children.size())
    }

    @Test
    void testFromElementWithClipPath() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <clipPath id="myClip">
                <circle cx="50" cy="50" r="40"/>
            </clipPath>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        ClipPath clipPath = svg.children[0] as ClipPath
        assertNotNull(clipPath)
        assertEquals('myClip', clipPath.id)
        assertEquals(1, clipPath.children.size())
    }

    @Test
    void testFromElementWithMask() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <mask id="myMask">
                <rect x="0" y="0" width="100" height="100" fill="white"/>
            </mask>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Mask mask = svg.children[0] as Mask
        assertNotNull(mask)
        assertEquals('myMask', mask.id)
        assertEquals(1, mask.children.size())
    }

    @Test
    void testFromElementWithText() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <text x="10" y="20" fill="black">Hello</text>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Text text = svg.children[0] as Text
        assertNotNull(text)
        assertEquals('10', text.x)
        assertEquals('20', text.y)
        assertEquals('black', text.fill)
        assertEquals('Hello', text.content)
    }

    @Test
    void testFromElementWithTspan() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <text x="10" y="20">
                <tspan x="10" y="30" fill="red">World</tspan>
            </text>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Text text = svg.children[0] as Text
        // Tspan handling depends on how Text children are structured
        // This test verifies basic parsing works
        assertNotNull(text)
    }

    @Test
    void testFromElementWithLinearGradient() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <defs>
                <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="0%">
                    <stop offset="0%" style="stop-color:rgb(255,255,0);stop-opacity:1" />
                    <stop offset="100%" style="stop-color:rgb(255,0,0);stop-opacity:1" />
                </linearGradient>
            </defs>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Defs defs = svg.children[0] as Defs
        assertEquals(1, defs.children.size())
        LinearGradient gradient = defs.children[0] as LinearGradient
        assertNotNull(gradient)
        assertEquals('grad1', gradient.id)
        assertEquals('0%', gradient.x1)
        assertEquals('100%', gradient.x2)
        assertEquals(2, gradient.children.size())
    }

    @Test
    void testFromElementWithRadialGradient() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <defs>
                <radialGradient id="grad2" cx="50%" cy="50%" r="50%">
                    <stop offset="0%" style="stop-color:rgb(255,255,255);stop-opacity:0" />
                    <stop offset="100%" style="stop-color:rgb(0,0,255);stop-opacity:1" />
                </radialGradient>
            </defs>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Defs defs = svg.children[0] as Defs
        assertEquals(1, defs.children.size())
        RadialGradient gradient = defs.children[0] as RadialGradient
        assertNotNull(gradient)
        assertEquals('grad2', gradient.id)
        assertEquals('50%', gradient.cx)
        assertEquals('50%', gradient.cy)
        assertEquals('50%', gradient.r)
        assertEquals(2, gradient.children.size())
    }

    @Test
    void testFromElementWithStop() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <defs>
                <linearGradient id="grad">
                    <stop offset="0%" stop-color="red" stop-opacity="1"/>
                </linearGradient>
            </defs>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        Defs defs = svg.children[0] as Defs
        LinearGradient gradient = defs.children[0] as LinearGradient
        assertEquals(1, gradient.children.size())
        Stop stop = gradient.children[0] as Stop
        assertNotNull(stop)
        assertEquals('0%', stop.offset)
        assertEquals('red', stop.stopColor)
        assertEquals('1', stop.stopOpacity)
    }

    @Test
    void testFromElementWithImage() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <image href="test.png" x="0" y="0" width="100" height="100"/>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Image image = svg.children[0] as Image
        assertNotNull(image)
        assertEquals('test.png', image.href)
        assertEquals('0', image.x)
        assertEquals('0', image.y)
        assertEquals('100', image.width)
        assertEquals('100', image.height)
    }

    @Test
    void testFromElementWithUse() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <use href="#myCircle" x="10" y="10"/>
        </svg>'''
        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())
        Use use = svg.children[0] as Use
        assertNotNull(use)
        assertEquals('#myCircle', use.href)
        assertEquals('10', use.x)
        assertEquals('10', use.y)
    }

    @Test
    void testDeepCopyCircle() {
        Svg svg1 = new Svg()
        Circle original = svg1.addCircle().cx(50).cy(50).r(25).fill('blue')

        Svg svg2 = new Svg()
        Circle copy = SvgElementFactory.deepCopy(original, svg2)

        assertNotNull(copy)
        assertEquals('50', copy.cx)
        assertEquals('50', copy.cy)
        assertEquals('25', copy.r)
        assertEquals('blue', copy.fill)
        assertEquals(1, svg2.children.size())
        assertSame(copy, svg2.children[0])
    }

    @Test
    void testDeepCopyWithChildren() {
        Svg svg1 = new Svg()
        G originalG = svg1.addG().fill('red')
        originalG.addCircle().cx(10).cy(10).r(5)
        originalG.addCircle().cx(20).cy(20).r(5)

        Svg svg2 = new Svg()
        G copyG = SvgElementFactory.deepCopy(originalG, svg2)

        assertNotNull(copyG)
        assertEquals('red', copyG.getAttribute('fill'))
        assertEquals(2, copyG.children.size())

        Circle c1 = copyG.children[0] as Circle
        assertEquals('10', c1.cx)

        Circle c2 = copyG.children[1] as Circle
        assertEquals('20', c2.cx)
    }

    @Test
    void testDeepCopyNestedStructure() {
        Svg svg1 = new Svg()
        G g1 = svg1.addG().id('g1')
        G g2 = g1.addG().id('g2')
        g2.addCircle().cx(50).cy(50).r(10)

        Svg svg2 = new Svg()
        G copyG1 = SvgElementFactory.deepCopy(g1, svg2)

        assertNotNull(copyG1)
        assertEquals('g1', copyG1.id)
        assertEquals(1, copyG1.children.size())

        G copyG2 = copyG1.children[0] as G
        assertEquals('g2', copyG2.id)
        assertEquals(1, copyG2.children.size())

        Circle copyCircle = copyG2.children[0] as Circle
        assertEquals('50', copyCircle.cx)
    }

    @Test
    void testCopyChildren() {
        Svg svg = new Svg()
        G source = svg.addG()
        source.addCircle().cx(10).cy(10).r(5)
        source.addRect().x(20).y(20).width(10).height(10)
        source.addPath().d('M 0 0 L 100 100')

        G target = svg.addG()
        SvgElementFactory.copyChildren(source, target)

        assertEquals(3, target.children.size())
        assertTrue(target.children[0] instanceof Circle)
        assertTrue(target.children[1] instanceof Rect)
        assertTrue(target.children[2] instanceof Path)

        Circle circle = target.children[0] as Circle
        assertEquals('10', circle.cx)
    }

    @Test
    void testCopyChildrenEmpty() {
        Svg svg = new Svg()
        G source = svg.addG()
        G target = svg.addG()

        SvgElementFactory.copyChildren(source, target)

        assertEquals(0, target.children.size())
    }

    @Test
    void testDeepCopyPreservesAttributes() {
        Svg svg1 = new Svg()
        Rect original = svg1.addRect()
            .x(10).y(20).width(100).height(50)
            .fill('red').stroke('blue')
            .id('myRect')

        Svg svg2 = new Svg()
        Rect copy = SvgElementFactory.deepCopy(original, svg2)

        assertEquals('10', copy.x)
        assertEquals('20', copy.y)
        assertEquals('100', copy.width)
        assertEquals('50', copy.height)
        assertEquals('red', copy.fill)
        assertEquals('blue', copy.stroke)
        assertEquals('myRect', copy.id)
    }

    @Test
    void testFromElementRecursion() {
        def svgString = '''<svg xmlns="http://www.w3.org/2000/svg">
            <g id="outer">
                <g id="middle">
                    <g id="inner">
                        <circle cx="50" cy="50" r="10"/>
                    </g>
                </g>
            </g>
        </svg>'''

        Svg svg = SvgReader.parse(svgString)
        assertEquals(1, svg.children.size())

        G outer = svg.children[0] as G
        assertEquals('outer', outer.id)
        assertEquals(1, outer.children.size())

        G middle = outer.children[0] as G
        assertEquals('middle', middle.id)
        assertEquals(1, middle.children.size())

        G inner = middle.children[0] as G
        assertEquals('inner', inner.id)
        assertEquals(1, inner.children.size())

        Circle circle = inner.children[0] as Circle
        assertEquals('50', circle.cx)
    }

    @Test
    void testCloneMethod() {
        // Test the new clone() method on SvgElement
        Svg svg1 = new Svg()
        Circle original = svg1.addCircle().cx(100).cy(100).r(50).fill('red').id('original-circle')

        Svg svg2 = new Svg()
        Circle cloned = original.clone(svg2)

        assertNotNull(cloned)
        assertEquals('100', cloned.cx)
        assertEquals('100', cloned.cy)
        assertEquals('50', cloned.r)
        assertEquals('red', cloned.fill)
        assertEquals('original-circle', cloned.id)
        assertEquals(1, svg2.children.size())
        assertSame(cloned, svg2.children[0])
    }

    @Test
    void testCloneMethodWithNestedElements() {
        // Test cloning a group with nested elements
        Svg svg1 = new Svg()
        G originalGroup = svg1.addG().id('group1').fill('blue')
        originalGroup.addCircle().cx(10).cy(10).r(5)
        originalGroup.addCircle().cx(20).cy(20).r(5)
        G innerGroup = originalGroup.addG().id('inner')
        innerGroup.addRect().x(30).y(30).width(10).height(10)

        Svg svg2 = new Svg()
        G clonedGroup = originalGroup.clone(svg2)

        assertNotNull(clonedGroup)
        assertEquals('group1', clonedGroup.id)
        assertEquals('blue', clonedGroup.fill)
        assertEquals(3, clonedGroup.children.size())

        Circle c1 = clonedGroup.children[0] as Circle
        assertEquals('10', c1.cx)

        Circle c2 = clonedGroup.children[1] as Circle
        assertEquals('20', c2.cx)

        G clonedInner = clonedGroup.children[2] as G
        assertEquals('inner', clonedInner.id)
        assertEquals(1, clonedInner.children.size())

        Rect rect = clonedInner.children[0] as Rect
        assertEquals('30', rect.x)
    }

    @Test
    void testCloneMethodTypeSafety() {
        // Verify type safety is preserved
        Svg svg = new Svg()
        Rect originalRect = svg.addRect().x(5).y(10).width(20).height(30)

        Svg targetSvg = new Svg()
        Rect clonedRect = originalRect.clone(targetSvg)

        // Type should be preserved
        assertTrue(clonedRect instanceof Rect)
        assertEquals('5', clonedRect.x)
        assertEquals('10', clonedRect.y)
    }
}
