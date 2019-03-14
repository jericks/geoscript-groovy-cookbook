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

    @Test void getNames() {
        FormatRecipes recipes = new FormatRecipes()
        List<String> names = recipes.getNames()
        assertEquals(1, names.size())
        assertEquals("earth", names[0])
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

    @Test void readGeoTiff() {
        FormatRecipes recipes = new FormatRecipes()
        Raster raster = recipes.readGeoTiff()
        assertNotNull(raster)
    }

    @Test void writeGeoTiffToArcGrid() {
        FormatRecipes recipes = new FormatRecipes()
        Raster raster = recipes.writeGeoTiffToArcGrid()
        assertNotNull(raster)
    }

    @Test void readWorldImage() {
        FormatRecipes recipes = new FormatRecipes()
        Raster raster = recipes.readWorldImage()
        assertNotNull(raster)
    }

    @Test void readArcGrid() {
        FormatRecipes recipes = new FormatRecipes()
        Raster raster = recipes.readArcGrid()
        assertNotNull(raster)
    }

    @Test void readArcGridGrass() {
        FormatRecipes recipes = new FormatRecipes()
        Raster raster = recipes.readArcGridGrass()
        assertNotNull(raster)
    }

    @Test void readNetCDF() {
        FormatRecipes recipes = new FormatRecipes()
        Raster raster = recipes.readNetCDF()
        assertNotNull(raster)
    }

}
