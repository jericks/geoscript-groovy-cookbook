package geoscript.cookbook

import geoscript.layer.MBTiles
import org.junit.Test
import static org.junit.Assert.*

class TileRecipesTest {

    @Test void getMBTiles() {
        TileRecipes recipes = new TileRecipes()
        MBTiles mbtiles = recipes.getMBTiles()
        assertEquals("countries", mbtiles.name)
        assertEquals("(-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857)", mbtiles.bounds.toString())
        assertEquals("EPSG:3857", mbtiles.proj.id)
    }

}
