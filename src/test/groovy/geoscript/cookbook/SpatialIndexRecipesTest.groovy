package geoscript.cookbook

import geoscript.geom.Point
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
}
