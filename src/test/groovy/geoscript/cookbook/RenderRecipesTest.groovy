package geoscript.cookbook

import geoscript.render.Map
import org.junit.Test
import static org.junit.Assert.*

class RenderRecipesTest {

    @Test void createMap() {
        RenderRecipes recipes = new RenderRecipes()
        Map map = recipes.createMap()
        assertNotNull(map)
    }

}
