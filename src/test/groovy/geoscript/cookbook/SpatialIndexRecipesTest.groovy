package geoscript.cookbook

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

}
