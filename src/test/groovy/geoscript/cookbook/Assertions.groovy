package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.geom.Point
import geoscript.layer.Pyramid
import org.apache.commons.text.similarity.LevenshteinDistance

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class Assertions {

    static void assertBoundsEquals(Bounds expected, Bounds actual, double delta) {
        assertEquals expected.minX, actual.minX, delta
        assertEquals expected.minY, actual.minY, delta
        assertEquals expected.maxX, actual.maxX, delta
        assertEquals expected.maxY, actual.maxY, delta
        assertEquals expected.proj, actual.proj
    }

    static void assertPyramidsAreEquals(Pyramid expected, Pyramid actual, double deltaForBounds) {
        assertBoundsEquals(expected.bounds ,actual.bounds, deltaForBounds)
        assertEquals(expected.proj, actual.proj)
        assertEquals(expected.origin, actual.origin)
        assertEquals(expected.tileWidth, actual.tileWidth)
        assertEquals(expected.tileHeight, actual.tileHeight)
        assertEquals(expected.grids, actual.grids)
    }

    static void assertPointsAreEqual(Point expected, Point actual, double delta) {
        assertEquals(expected.x, actual.x, delta)
        assertEquals(expected.y, actual.y, delta)
    }

    static void assertStringsAreSimilar(String expected, String actual, int maxDifference) {
        StringReader expectedReader = new StringReader(expected)
        StringReader actualReader = new StringReader(actual)
        List<String> expectedLines = expectedReader.readLines()
        List<String> actualLines = actualReader.readLines()
        assertEquals(expectedLines.size(), actualLines.size())
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance()
        expectedLines.eachWithIndex { String exp, int i ->
            String act = actualLines[i]
            int distance = levenshteinDistance.apply(exp, act)
            assertTrue(distance <= maxDifference, "The difference between ${exp} and ${act} is ${distance}")
        }
    }

}
