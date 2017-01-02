package geoscript.cookbook

import geoscript.index.STRtree
import org.junit.Test
import static org.junit.Assert.*

class SpatialIndexRecipesTest {

    @Test void createSTRtree() {
        SpatialIndexRecipes recipes = new SpatialIndexRecipes()
        STRtree index = recipes.createSTRtree()
        assertEquals(4, index.size)
    }

}
