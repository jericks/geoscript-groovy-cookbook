package geoscript.cookbook

import org.junit.Test
import static org.junit.Assert.*

class LayerRecipesTest {

    @Test void getLayerProperties() {
        LayerRecipes recipes = new LayerRecipes()
        Map<String, Object> values = recipes.getLayerProperties()
        assertEquals("countries", values.name)
        assertEquals(177, values.count)
    }

}
