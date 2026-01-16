package test.alipsa.groovy.svg.validation

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.validation.ValidationIssue
import se.alipsa.groovy.svg.validation.ValidationLevel
import se.alipsa.groovy.svg.validation.ValidationReport

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for ValidationReport class.
 */
class ValidationReportTest {

    @Test
    void testEmptyReport() {
        ValidationReport report = new ValidationReport()

        assertFalse(report.hasIssues())
        assertFalse(report.hasErrors())
        assertFalse(report.hasWarnings())
        assertTrue(report.isValid())
        assertEquals(0, report.size())
        assertEquals(0, report.errorCount)
        assertEquals(0, report.warningCount)
        assertEquals(0, report.infoCount)
    }

    @Test
    void testAddError() {
        ValidationReport report = new ValidationReport()
        report.addIssue(ValidationIssue.error("Test error", "circle", null, "TEST"))

        assertTrue(report.hasIssues())
        assertTrue(report.hasErrors())
        assertFalse(report.hasWarnings())
        assertFalse(report.isValid())
        assertEquals(1, report.size())
        assertEquals(1, report.errorCount)
    }

    @Test
    void testAddWarning() {
        ValidationReport report = new ValidationReport()
        report.addIssue(ValidationIssue.warning("Test warning", "rect", null, "TEST"))

        assertTrue(report.hasIssues())
        assertFalse(report.hasErrors())
        assertTrue(report.hasWarnings())
        assertTrue(report.isValid())  // Warnings don't prevent valid
        assertEquals(1, report.size())
        assertEquals(1, report.warningCount)
    }

    @Test
    void testAddInfo() {
        ValidationReport report = new ValidationReport()
        report.addIssue(ValidationIssue.info("Test info", "svg", null, "TEST"))

        assertTrue(report.hasIssues())
        assertFalse(report.hasErrors())
        assertFalse(report.hasWarnings())
        assertTrue(report.isValid())  // Info doesn't prevent valid
        assertEquals(1, report.size())
        assertEquals(1, report.infoCount)
    }

    @Test
    void testMixedIssues() {
        ValidationReport report = new ValidationReport()
        report.addIssue(ValidationIssue.error("Error 1", null, null, "TEST"))
        report.addIssue(ValidationIssue.warning("Warning 1", null, null, "TEST"))
        report.addIssue(ValidationIssue.info("Info 1", null, null, "TEST"))

        assertEquals(3, report.size())
        assertEquals(1, report.errorCount)
        assertEquals(1, report.warningCount)
        assertEquals(1, report.infoCount)
        assertFalse(report.isValid())
    }

    @Test
    void testGetErrors() {
        ValidationReport report = new ValidationReport()
        report.addIssue(ValidationIssue.error("Error 1", null, null, "TEST"))
        report.addIssue(ValidationIssue.warning("Warning 1", null, null, "TEST"))
        report.addIssue(ValidationIssue.error("Error 2", null, null, "TEST"))

        List errors = report.errors
        assertEquals(2, errors.size())
        assertEquals("Error 1", errors[0].message)
        assertEquals("Error 2", errors[1].message)
    }

    @Test
    void testGetWarnings() {
        ValidationReport report = new ValidationReport()
        report.addIssue(ValidationIssue.warning("Warning 1", null, null, "TEST"))
        report.addIssue(ValidationIssue.error("Error 1", null, null, "TEST"))
        report.addIssue(ValidationIssue.warning("Warning 2", null, null, "TEST"))

        List warnings = report.warnings
        assertEquals(2, warnings.size())
        assertEquals("Warning 1", warnings[0].message)
        assertEquals("Warning 2", warnings[1].message)
    }

    @Test
    void testGetInfo() {
        ValidationReport report = new ValidationReport()
        report.addIssue(ValidationIssue.info("Info 1", null, null, "TEST"))
        report.addIssue(ValidationIssue.error("Error 1", null, null, "TEST"))
        report.addIssue(ValidationIssue.info("Info 2", null, null, "TEST"))

        List infos = report.info
        assertEquals(2, infos.size())
        assertEquals("Info 1", infos[0].message)
        assertEquals("Info 2", infos[1].message)
    }

    @Test
    void testClear() {
        ValidationReport report = new ValidationReport()
        report.addIssue(ValidationIssue.error("Error", null, null, "TEST"))
        report.addIssue(ValidationIssue.warning("Warning", null, null, "TEST"))

        assertEquals(2, report.size())

        report.clear()

        assertEquals(0, report.size())
        assertTrue(report.isValid())
        assertFalse(report.hasIssues())
    }

    @Test
    void testToStringEmpty() {
        ValidationReport report = new ValidationReport()
        String str = report.toString()

        assertTrue(str.contains("No issues found"))
    }

    @Test
    void testToStringWithIssues() {
        ValidationReport report = new ValidationReport()
        report.addIssue(ValidationIssue.error("Test error", "circle", null, "TEST"))
        report.addIssue(ValidationIssue.warning("Test warning", "rect", null, "TEST2"))

        String str = report.toString()

        assertTrue(str.contains("2 issue(s)"))
        assertTrue(str.contains("1 error(s)"))
        assertTrue(str.contains("1 warning(s)"))
        assertTrue(str.contains("Test error"))
        assertTrue(str.contains("Test warning"))
    }

    @Test
    void testImmutableLists() {
        ValidationReport report = new ValidationReport()
        report.addIssue(ValidationIssue.error("Error", null, null, "TEST"))

        assertThrows(UnsupportedOperationException.class, {
            report.errors.add(ValidationIssue.error("New error", null, null, "TEST"))
        })

        assertThrows(UnsupportedOperationException.class, {
            report.allIssues.clear()
        })
    }
}
