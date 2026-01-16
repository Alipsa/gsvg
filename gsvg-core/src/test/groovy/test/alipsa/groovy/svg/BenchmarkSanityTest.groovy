package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader

import static org.junit.jupiter.api.Assertions.*

/**
 * Sanity tests for benchmark resources and operations.
 * These tests verify that benchmark test files exist and basic operations work.
 */
class BenchmarkSanityTest {

    @Test
    void testBenchmarkResourcesExist() {
        // Verify all benchmark SVG files exist and can be loaded
        def simple = loadBenchmarkResource("simple.svg")
        def medium = loadBenchmarkResource("medium.svg")
        def large = loadBenchmarkResource("large.svg")
        def complex = loadBenchmarkResource("complex.svg")

        assertNotNull(simple, "simple.svg should exist")
        assertNotNull(medium, "medium.svg should exist")
        assertNotNull(large, "large.svg should exist")
        assertNotNull(complex, "complex.svg should exist")

        assertTrue(simple.length() > 0, "simple.svg should not be empty")
        assertTrue(medium.length() > 0, "medium.svg should not be empty")
        assertTrue(large.length() > 0, "large.svg should not be empty")
        assertTrue(complex.length() > 0, "complex.svg should not be empty")
    }

    @Test
    void testBenchmarkResourcesParse() {
        // Verify all benchmark SVG files parse correctly
        Svg simple = SvgReader.parse(loadBenchmarkResource("simple.svg"))
        Svg medium = SvgReader.parse(loadBenchmarkResource("medium.svg"))
        Svg large = SvgReader.parse(loadBenchmarkResource("large.svg"))
        Svg complex = SvgReader.parse(loadBenchmarkResource("complex.svg"))

        assertNotNull(simple, "simple.svg should parse")
        assertNotNull(medium, "medium.svg should parse")
        assertNotNull(large, "large.svg should parse")
        assertNotNull(complex, "complex.svg should parse")

        // Verify basic structure
        assertEquals("200", simple.getWidth())
        assertEquals("1000", medium.getWidth())
        assertEquals("5000", large.getWidth())
        assertEquals("800", complex.getWidth())
    }

    @Test
    void testSimpleSvgStructure() {
        Svg svg = SvgReader.parse(loadBenchmarkResource("simple.svg"))

        // Should have approximately 10 elements
        def elements = svg.descendants()
        assertTrue(elements.size() >= 9, "simple.svg should have at least 9 descendants")
    }

    @Test
    void testMediumSvgStructure() {
        Svg svg = SvgReader.parse(loadBenchmarkResource("medium.svg"))

        // Should have many elements (100+)
        def elements = svg.descendants()
        assertTrue(elements.size() >= 50, "medium.svg should have at least 50 descendants")
    }

    @Test
    void testLargeSvgStructure() {
        Svg svg = SvgReader.parse(loadBenchmarkResource("large.svg"))

        // Should have 1000 elements
        def elements = svg.descendants()
        assertTrue(elements.size() >= 900, "large.svg should have at least 900 descendants")
    }

    @Test
    void testComplexSvgStructure() {
        Svg svg = SvgReader.parse(loadBenchmarkResource("complex.svg"))

        // Should have many descendants (complex structure)
        def descendants = svg.descendants()
        assertTrue(descendants.size() > 20, "complex.svg should have many descendants")

        // Should have gradients
        def gradients = descendants.findAll {
            it.name == 'linearGradient' || it.name == 'radialGradient'
        }
        assertTrue(gradients.size() > 0, "complex.svg should have gradients")
    }

    @Test
    void testParsingPerformanceReasonable() {
        // Verify parsing doesn't throw exceptions and completes
        long start = System.currentTimeMillis()
        SvgReader.parse(loadBenchmarkResource("large.svg"))
        long end = System.currentTimeMillis()

        // Should parse within reasonable time (10 seconds max for sanity test)
        assertTrue(end - start < 10000, "Parsing large.svg should complete within 10 seconds")
    }

    @Test
    void testSerializationWorks() {
        Svg svg = SvgReader.parse(loadBenchmarkResource("simple.svg"))

        // Should be able to serialize without exceptions
        String xml = svg.toXml()
        assertNotNull(xml)
        assertTrue(xml.contains("<svg"))
        assertTrue(xml.contains("</svg>"))
    }

    private String loadBenchmarkResource(String filename) {
        def stream = getClass().getClassLoader().getResourceAsStream("benchmarks/" + filename)
        if (stream == null) {
            fail("Could not find benchmark resource: " + filename)
        }
        return stream.text
    }
}
