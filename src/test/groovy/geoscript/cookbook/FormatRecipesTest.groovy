package geoscript.cookbook

import geoscript.layer.Raster
import org.junit.Test
import static org.junit.Assert.*

class FormatRecipesTest {

    @Test void read() {
        FormatRecipes recipes = new FormatRecipes()
        Raster raster = recipes.read()
        assertNotNull(raster)
    }

    @Test void write() {
        FormatRecipes recipes = new FormatRecipes()
        Raster raster = recipes.write()
        assertNotNull(raster)
    }

}
