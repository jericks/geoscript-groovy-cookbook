package geoscript.cookbook

import geoscript.layer.Format
import geoscript.layer.Raster
import org.junit.Test
import static org.junit.Assert.*

class FormatRecipesTest {

    @Test void getFormat() {
        FormatRecipes recipes = new FormatRecipes()
        Format format = recipes.getFormat()
        assertEquals("GeoTIFF", format.name)
    }

    @Test void hasRaster() {
        FormatRecipes recipes = new FormatRecipes()
        Format format = recipes.hasRaster()
        assertTrue(format.has("earth"))
        assertFalse(format.has("world"))
    }

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
