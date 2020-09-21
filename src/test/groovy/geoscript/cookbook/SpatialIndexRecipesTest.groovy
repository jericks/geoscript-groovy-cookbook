package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.geom.Point
import geoscript.index.HPRtree
import geoscript.index.Quadtree
import geoscript.index.STRtree
import org.junit.Test
import static org.junit.Assert.*

class SpatialIndexRecipesTest {

    @Test void createSTRtree() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        STRtree index = recipes.createSTRtree()
        assertEquals(4, index.size)
    }

    @Test void createHPRtree() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        HPRtree index = recipes.createHPRtree()
        assertEquals(4, index.size)
    }

    @Test void createQuadtree() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        Quadtree index = recipes.createQuadtree()
        assertEquals(3, index.size)
    }

    @Test void geohashEncodePoint() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        String hash = recipes.geohashEncodePoint()
        assertEquals("ww8p1r4t8", hash)
    }

    @Test void geohashDecodeString() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        Point point = recipes.geohashDecodeString()
        assertEquals("POINT (112.55838632583618 37.83238649368286)", point.wkt)
    }

    @Test void geohashEncodePointAsLong() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        long hash = recipes.geohashEncodePointAsLong()
        assertEquals(4064984913515641, hash)
    }

    @Test void geohashDecodeLong() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        Point point = recipes.geohashDecodeLong()
        assertEquals("POINT (112.55839973688126 37.83240124583244)", point.wkt)
    }

    @Test void geohashDecodeBoundsFromString() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        Bounds bounds = recipes.geohashDecodeBoundsFromString()
        assertEquals("(112.55836486816406,37.83236503601074,112.5584077835083,37.83240795135498)", bounds.toString())
    }

    @Test void geohashDecodeBoundsFromLong() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        Bounds bounds = recipes.geohashDecodeBoundsFromLong()
        assertEquals("(112.55839705467224,37.832399904727936,112.55840241909027,37.83240258693695)", bounds.toString())
    }

    @Test void geohashNeighborFromString() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        String str = recipes.geohashNeighborFromString()
        assertEquals("""
 dqcjt dqcjw dqcjx
 dqcjm dqcjq dqcjr
 dqcjj dqcjn dqcjp
""", str)
    }

    @Test void geohashNeighborFromLong() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        String str = recipes.geohashNeighborFromLong()
        assertEquals("""
 1702789434 1702789520 1702789522
 1702789423 1702789509 1702789511
 1702789422 1702789508 1702789510
""", str)
    }

    @Test void geohashNeighborsFromString() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        String str = recipes.geohashNeighborsFromString()
        assertEquals("""
 dqcjt dqcjw dqcjx
 dqcjm dqcjq dqcjr
 dqcjj dqcjn dqcjp
""", str)
    }

    @Test void geohashNeighborsFromLong() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        String str = recipes.geohashNeighborsFromLong()
        assertEquals("""
 1702789434 1702789520 1702789522
 1702789423 1702789509 1702789511
 1702789422 1702789508 1702789510
""", str)
    }

    @Test void geohashBboxesAsStrings() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        List bboxes = recipes.geohashBboxesAsStrings()
        assertEquals 2, bboxes.size()
        assertEquals "wtm6dtm6", bboxes[0]
        assertEquals "wtm6dtm7", bboxes[1]
    }

    @Test void geohashBboxesAsLongs() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        List bboxes = recipes.geohashBboxesAsLongs()
        assertEquals 2, bboxes.size()
        assertEquals 989560464998, bboxes[0]
        assertEquals 989560464999, bboxes[1]
    }
}
