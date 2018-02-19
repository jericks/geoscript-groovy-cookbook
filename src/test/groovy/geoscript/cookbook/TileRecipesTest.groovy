package geoscript.cookbook

import geoscript.layer.Grid
import geoscript.layer.ImageTile
import geoscript.layer.MBTiles
import geoscript.layer.Pyramid
import geoscript.layer.Tile
import geoscript.layer.TileCursor
import org.junit.Test
import static org.junit.Assert.*

class TileRecipesTest {

    // Tile

    @Test void tileProperties() {
        TileRecipes recipes = new TileRecipes()
        Tile tile = recipes.tileProperties()
        assertEquals(2, tile.z)
        assertEquals(1, tile.x)
        assertEquals(3, tile.y)
        assertEquals("Tile(x:1, y:3, z:2)", tile.toString())
        assertTrue(tile.base64String.startsWith("iVBORw0KGgoAAAANSUhEUgA"))
    }

    @Test void imageTile() {
        TileRecipes recipes = new TileRecipes()
        ImageTile tile = recipes.imageTile()
        assertEquals(0, tile.z)
        assertEquals(0, tile.x)
        assertEquals(0, tile.y)
        assertNotNull(tile.image)
    }

    // Grid

    @Test void gridProperties() {
        TileRecipes recipes = new TileRecipes()
        Grid grid = recipes.gridProperties()
        assertEquals(1, grid.z)
        assertEquals(2, grid.width)
        assertEquals(2, grid.height)
        assertEquals(4, grid.size)
        assertEquals(78206.0, grid.xResolution, 0.1)
        assertEquals(78206.0, grid.yResolution, 0.1)
    }

    // Pyramid

    @Test void pyramidProperties() {
        TileRecipes recipes = new TileRecipes()
        Pyramid pyramid = recipes.pyramidProperties()
        assertEquals("EPSG:3857", pyramid.proj.id)
        assertEquals("(-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857)", pyramid.bounds.toString())
        assertEquals("BOTTOM_LEFT", pyramid.origin.toString())
        assertEquals(256, pyramid.tileWidth)
        assertEquals(256, pyramid.tileHeight)
    }

    @Test void createGlobalMercatorPyramid() {
        TileRecipes recipes = new TileRecipes()
        Pyramid pyramid = recipes.createGlobalMercatorPyramid()
        assertEquals("EPSG:3857", pyramid.proj.id)
        assertEquals("(-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857)", pyramid.bounds.toString())
        assertEquals("BOTTOM_LEFT", pyramid.origin.toString())
        assertEquals(19, pyramid.maxGrid.z)
        assertEquals(256, pyramid.tileWidth)
        assertEquals(256, pyramid.tileHeight)
    }

    @Test void createGlobalGeodeticPyramid() {
        TileRecipes recipes = new TileRecipes()
        Pyramid pyramid = recipes.createGlobalGeodeticPyramid()
        assertEquals("EPSG:4326", pyramid.proj.id)
        assertEquals("(-179.99,-89.99,179.99,89.99,EPSG:4326)", pyramid.bounds.toString())
        assertEquals("BOTTOM_LEFT", pyramid.origin.toString())
        assertEquals(19, pyramid.maxGrid.z)
        assertEquals(256, pyramid.tileWidth)
        assertEquals(256, pyramid.tileHeight)
    }

    @Test void gridFromPyramidByZoomLevel() {
        TileRecipes recipes = new TileRecipes()
        Grid grid = recipes.gridFromPyramidByZoomLevel()
        assertEquals(1, grid.z)
        assertEquals(2, grid.width)
        assertEquals(2, grid.height)
        assertEquals(4, grid.size)
        assertEquals(78206.0, grid.xResolution, 0.1)
        assertEquals(78206.0, grid.yResolution, 0.1)
    }

    @Test void minGrid() {
        TileRecipes recipes = new TileRecipes()
        Grid grid = recipes.minGrid()
        assertEquals(0, grid.z)
        assertEquals(1, grid.width)
        assertEquals(1, grid.height)
        assertEquals(1, grid.size)
        assertEquals(156412.0, grid.xResolution, 0.1)
        assertEquals(156412.0, grid.yResolution, 0.1)
    }

    @Test void maxGrid() {
        TileRecipes recipes = new TileRecipes()
        Grid grid = recipes.maxGrid()
        assertEquals(19, grid.z)
        assertEquals(524288, grid.width)
        assertEquals(524288, grid.height)
        assertEquals(274877906944, grid.size)
        assertEquals(0.29833221435546875, grid.xResolution, 0.0001)
        assertEquals(0.29833221435546875, grid.yResolution, 0.0001)
    }

    @Test void createPyramidFromString() {
        TileRecipes recipes = new TileRecipes()
        Pyramid pyramid = recipes.createPyramidFromString()
        assertEquals("EPSG:3857", pyramid.proj.id)
        assertEquals("(-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857)", pyramid.bounds.toString())
        assertEquals("BOTTOM_LEFT", pyramid.origin.toString())
        assertEquals(19, pyramid.maxGrid.z)
        assertEquals(256, pyramid.tileWidth)
        assertEquals(256, pyramid.tileHeight)
    }

    // TileLayer

    @Test void tileLayerProperties() {
        TileRecipes recipes = new TileRecipes()
        MBTiles mbtiles = recipes.tileLayerProperties()
        assertEquals("countries", mbtiles.name)
        assertEquals("(-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857)", mbtiles.bounds.toString())
        assertEquals("EPSG:3857", mbtiles.proj.id)
    }

    // TileCursor

    @Test void tileCursorByZoomLevel() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileCursorByZoomLevel()
        assertEquals(4, tileCursor.size)
        assertEquals(2, tileCursor.width)
        assertEquals(2, tileCursor.height)
        assertEquals(0, tileCursor.minX)
        assertEquals(0, tileCursor.minY)
        assertEquals(1, tileCursor.maxX)
        assertEquals(1, tileCursor.maxY)
    }

}
