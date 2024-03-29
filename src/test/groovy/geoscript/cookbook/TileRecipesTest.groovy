package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.layer.DBTiles
import geoscript.layer.GeneratingTileLayer
import geoscript.layer.GeoPackage
import geoscript.layer.Grid
import geoscript.layer.ImageTile
import geoscript.layer.Layer
import geoscript.layer.MBTiles
import geoscript.layer.OSM
import geoscript.layer.Pyramid
import geoscript.layer.TMS
import geoscript.layer.Tile
import geoscript.layer.TileCursor
import geoscript.layer.TileLayer
import geoscript.layer.TileRenderer
import geoscript.layer.UTFGrid
import geoscript.layer.VectorTiles
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*
import static geoscript.cookbook.Assertions.*

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
        assertBoundsEquals(Bounds.fromString("-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857"), pyramid.bounds, 0.0000001)
        assertEquals("BOTTOM_LEFT", pyramid.origin.toString())
        assertEquals(256, pyramid.tileWidth)
        assertEquals(256, pyramid.tileHeight)
    }

    @Test void createGlobalMercatorPyramid() {
        TileRecipes recipes = new TileRecipes()
        Pyramid pyramid = recipes.createGlobalMercatorPyramid()
        assertEquals("EPSG:3857", pyramid.proj.id)
        assertBoundsEquals(Bounds.fromString("-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857"), pyramid.bounds, 0.00001)
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

    @Test void pyramidGetTileCoordinatesByBoundsAndZoom() {
        TileRecipes recipes = new TileRecipes()
        Map<String,Integer> coords = recipes.pyramidGetTileCoordinatesByBoundsAndZoom()
        assertEquals (2, coords.minX)
        assertEquals (9, coords.minY)
        assertEquals (5, coords.maxX)
        assertEquals (10, coords.maxY)
    }

    @Test void pyramidGetTileCoordinatesByBoundsAndGrid() {
        TileRecipes recipes = new TileRecipes()
        Map<String,Integer> coords = recipes.pyramidGetTileCoordinatesByBoundsAndGrid()
        assertEquals (571, coords.minX)
        assertEquals (623, coords.minY)
        assertEquals (576, coords.maxX)
        assertEquals (626, coords.maxY)
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

    @Test void gridFromPyramidByBoundsAndResolution() {
        TileRecipes recipes = new TileRecipes()
        Grid grid = recipes.gridFromPyramidByBoundsAndResolution()
        assertEquals(8, grid.z)
        assertEquals(256, grid.width)
        assertEquals(256, grid.height)
        assertEquals(65536, grid.size)
        assertEquals(610.984375, grid.xResolution, 0.1)
        assertEquals(610.984375, grid.yResolution, 0.1)
    }

    @Test void gridFromPyramidByBoundsAndSize() {
        TileRecipes recipes = new TileRecipes()
        Grid grid = recipes.gridFromPyramidByBoundsAndSize()
        assertEquals(8, grid.z)
        assertEquals(256, grid.width)
        assertEquals(256, grid.height)
        assertEquals(65536, grid.size)
        assertEquals(610.984375, grid.xResolution, 0.1)
        assertEquals(610.984375, grid.yResolution, 0.1)
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

    @Test void getBoundsForTile() {
        TileRecipes recipes = new TileRecipes()
        Bounds bounds = recipes.getBoundsForTile()
        assertBoundsEquals(Bounds.fromString("-1.0018197573940657E7,-1.0018735602568535E7,0.0,-3.725290298461914E-9,EPSG:3857"), bounds, 0.000001)
    }

    @Test void getBoundsAroundPoint() {
        TileRecipes recipes = new TileRecipes()
        Bounds actual = recipes.getBoundsAroundPoint()
        Bounds expected = Bounds.fromString("2343967.4055929263, 4410824.650424092, 2588361.1555929263, 4655218.400424092, EPSG:3857")
        assertBoundsEquals(expected, actual, 0.0000001)
    }

    @Test void createPyramidFromString() {
        TileRecipes recipes = new TileRecipes()
        Pyramid pyramid = recipes.createPyramidFromString()
        assertEquals("EPSG:3857", pyramid.proj.id)
        assertBoundsEquals(Bounds.fromString("-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857"), pyramid.bounds, 0.0000001)
        assertEquals("BOTTOM_LEFT", pyramid.origin.toString())
        assertEquals(19, pyramid.maxGrid.z)
        assertEquals(256, pyramid.tileWidth)
        assertEquals(256, pyramid.tileHeight)
    }

    // Pyramid IO

    @Test void listPyramidWriters() {
        TileRecipes recipes = new TileRecipes()
        List<Writer> writers = recipes.listPyramidWriters()
        assertTrue(writers.size() > 0)
    }

    @Test void findPyramidWriter() {
        TileRecipes recipes = new TileRecipes()
        String str = recipes.findPyramidWriter()
        assertNotNull(str)
    }

    @Test void listPyramidReaders() {
        TileRecipes recipes = new TileRecipes()
        List<Reader> readers = recipes.listPyramidReaders()
        assertTrue(readers.size() > 0)
    }

    @Test void findPyramidReader() {
        TileRecipes recipes = new TileRecipes()
        Pyramid pyramid = recipes.findPyramidReader()
        assertNotNull(pyramid)
    }

    @Test void pyramidToJson() {
        TileRecipes recipes = new TileRecipes()
        String json = recipes.pyramidToJson()
        assertStringsAreSimilar("""{
    "proj": "EPSG:3857",
    "bounds": {
        "minX": -2.0036395147881314E7,
        "minY": -2.0037471205137067E7,
        "maxX": 2.0036395147881314E7,
        "maxY": 2.003747120513706E7
    },
    "origin": "BOTTOM_LEFT",
    "tileSize": {
        "width": 256,
        "height": 256
    },
    "grids": [
        {
            "z": 0,
            "width": 1,
            "height": 1,
            "xres": 156412.0,
            "yres": 156412.0
        },
        {
            "z": 1,
            "width": 2,
            "height": 2,
            "xres": 78206.0,
            "yres": 78206.0
        },
        {
            "z": 2,
            "width": 4,
            "height": 4,
            "xres": 39103.0,
            "yres": 39103.0
        },
        {
            "z": 3,
            "width": 8,
            "height": 8,
            "xres": 19551.5,
            "yres": 19551.5
        },
        {
            "z": 4,
            "width": 16,
            "height": 16,
            "xres": 9775.75,
            "yres": 9775.75
        }
    ]
}""", json, 4)
    }

    @Test void pyramidToXml() {
        TileRecipes recipes = new TileRecipes()
        String xml = recipes.pyramidToXml()
        assertStringsAreSimilar("""<pyramid>
  <proj>EPSG:3857</proj>
  <bounds>
    <minX>-2.0036395147881314E7</minX>
    <minY>-2.0037471205137067E7</minY>
    <maxX>2.0036395147881314E7</maxX>
    <maxY>2.003747120513706E7</maxY>
  </bounds>
  <origin>BOTTOM_LEFT</origin>
  <tileSize>
    <width>256</width>
    <height>256</height>
  </tileSize>
  <grids>
    <grid>
      <z>0</z>
      <width>1</width>
      <height>1</height>
      <xres>156412.0</xres>
      <yres>156412.0</yres>
    </grid>
    <grid>
      <z>1</z>
      <width>2</width>
      <height>2</height>
      <xres>78206.0</xres>
      <yres>78206.0</yres>
    </grid>
    <grid>
      <z>2</z>
      <width>4</width>
      <height>4</height>
      <xres>39103.0</xres>
      <yres>39103.0</yres>
    </grid>
    <grid>
      <z>3</z>
      <width>8</width>
      <height>8</height>
      <xres>19551.5</xres>
      <yres>19551.5</yres>
    </grid>
    <grid>
      <z>4</z>
      <width>16</width>
      <height>16</height>
      <xres>9775.75</xres>
      <yres>9775.75</yres>
    </grid>
  </grids>
</pyramid>""", xml, 4)
    }

    @Test void pyramidToCsv() {
        TileRecipes recipes = new TileRecipes()
        String csv = recipes.pyramidToCsv()
        assertStringsAreSimilar("""EPSG:3857
-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857
BOTTOM_LEFT
256,256
0,1,1,156412.0,156412.0
1,2,2,78206.0,78206.0
2,4,4,39103.0,39103.0
3,8,8,19551.5,19551.5
4,16,16,9775.75,9775.75
""", csv, 4)
    }

    @Test void writePyramidToGdalTms() {
        TileRecipes recipes = new TileRecipes()
        String xml = recipes.writePyramidToGdalTms()
        assertStringsAreSimilar('''<GDAL_WMS>
  <Service name='TMS'>
    <ServerURL>https://myserver.com/${z}/${x}/${y}</ServerURL>
    <SRS>EPSG:3857</SRS>
    <ImageFormat>png</ImageFormat>
  </Service>
  <DataWindow>
    <UpperLeftX>-2.0036395147881314E7</UpperLeftX>
    <UpperLeftY>2.003747120513706E7</UpperLeftY>
    <LowerRightX>2.0036395147881314E7</LowerRightX>
    <LowerRightY>-2.0037471205137067E7</LowerRightY>
    <TileLevel>4</TileLevel>
    <TileCountX>1</TileCountX>
    <TileCountY>1</TileCountY>
    <YOrigin>bottom</YOrigin>
  </DataWindow>
  <Projection>EPSG:3857</Projection>
  <BlockSizeX>256</BlockSizeX>
  <BlockSizeY>256</BlockSizeY>
  <BandsCount>3</BandsCount>
</GDAL_WMS>''', xml, 4)
    }

    @Test void readPyramidFromGdalTms() {
        TileRecipes recipes = new TileRecipes()
        Pyramid pyramid = recipes.readPyramidFromGdalTms()
        assertEquals('geoscript.layer.Pyramid(proj:EPSG:3857, bounds:(-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857), origin:BOTTOM_LEFT, tileWidth:256, tileHeight:256)', pyramid.toString())
    }

    // TileLayer

    @Test void tileLayerProperties() {
        TileRecipes recipes = new TileRecipes()
        MBTiles mbtiles = recipes.tileLayerProperties()
        assertEquals("countries", mbtiles.name)
        assertBoundsEquals(Bounds.fromString("-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857"), mbtiles.bounds, 0.0000001)
        assertEquals("EPSG:3857", mbtiles.proj.id)
    }

    @Test void tileLayerGetPutDelete() {
        TileRecipes recipes = new TileRecipes()
        Map<String, Boolean> results = recipes.tileLayerGetPutDelete()
        assertTrue(results.get)
        assertTrue(results.put)
        assertTrue(results.delete)
    }

    @Test void tileLayerDeleteTiles() {
        TileRecipes recipes = new TileRecipes()
        List<Tile> tiles = recipes.tileLayerDeleteTiles()
        tiles.each { assertNull it.image }
    }

    @Test void tileLayerTilesByZoomLevel() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileLayerTilesByZoomLevel()
        assertEquals(4, tileCursor.size)
        assertEquals(2, tileCursor.width)
        assertEquals(2, tileCursor.height)
        assertEquals(0, tileCursor.minX)
        assertEquals(0, tileCursor.minY)
        assertEquals(1, tileCursor.maxX)
        assertEquals(1, tileCursor.maxY)
    }

    @Test void tileLayerTilesByZoomLevelAndMinMax() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileLayerTilesByZoomLevelAndMinMax()
        assertEquals(4, tileCursor.z)
        assertEquals(20, tileCursor.size)
        assertEquals(4, tileCursor.width)
        assertEquals(5, tileCursor.height)
        assertEquals(2, tileCursor.minX)
        assertEquals(4, tileCursor.minY)
        assertEquals(5, tileCursor.maxX)
        assertEquals(8, tileCursor.maxY)
    }

    @Test void tileLayerTilesByZoomLevelAndBounds() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileLayerTilesByZoomLevelAndBounds()
        assertEquals(8, tileCursor.z)
        assertEquals(24, tileCursor.size)
        assertEquals(6, tileCursor.width)
        assertEquals(4, tileCursor.height)
        assertEquals(54, tileCursor.minX)
        assertEquals(164, tileCursor.minY)
        assertEquals(59, tileCursor.maxX)
        assertEquals(167, tileCursor.maxY)
    }

    @Test void tileLayerTilesByZoomLevelAndResXY() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileLayerTilesByZoomLevelAndResXY()
        assertEquals(4, tileCursor.z)
        assertEquals(8, tileCursor.size)
        assertEquals(4, tileCursor.width)
        assertEquals(2, tileCursor.height)
        assertEquals(2, tileCursor.minX)
        assertEquals(9, tileCursor.minY)
        assertEquals(5, tileCursor.maxX)
        assertEquals(10, tileCursor.maxY)
    }

    @Test void tileLayerTilesByBoundsAndWidthHeight() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileLayerTilesByBoundsAndWidthHeight()
        assertEquals(7, tileCursor.z)
        assertEquals(6, tileCursor.size)
        assertEquals(3, tileCursor.width)
        assertEquals(2, tileCursor.height)
        assertEquals(27, tileCursor.minX)
        assertEquals(82, tileCursor.minY)
        assertEquals(29, tileCursor.maxX)
        assertEquals(83, tileCursor.maxY)
    }

    @Test void tileLayerTilesAroundPointAtZoomLevelAndWidthHeight() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileLayerTilesAroundPointAtZoomLevelAndWidthHeight()
        assertEquals(12, tileCursor.z)
        assertEquals(9, tileCursor.size)
        assertEquals(3, tileCursor.width)
        assertEquals(3, tileCursor.height)
        assertEquals(876, tileCursor.minX)
        assertEquals(2628, tileCursor.minY)
        assertEquals(878, tileCursor.maxX)
        assertEquals(2630, tileCursor.maxY)
    }

    @Test void tileLayerGetTileCoordinatesByBoundsAndGrid() {
        TileRecipes recipes = new TileRecipes()
        Map<String,Integer> coords = recipes.tileLayerGetTileCoordinatesByBoundsAndGrid()
        assertEquals (571, coords.minX)
        assertEquals (623, coords.minY)
        assertEquals (576, coords.maxX)
        assertEquals (626, coords.maxY)
    }

    @Test void tileLayerGetLayer() {
        TileRecipes recipes = new TileRecipes()
        Layer layer = recipes.tileLayerGetLayer()
        assertEquals(4, layer.count)
    }

    @Test void withTileLayer() {
        TileRecipes recipes = new TileRecipes()
        TileLayer tileLayer = recipes.withTileLayer()
        assertEquals("countries", tileLayer.name)
        assertEquals("EPSG:3857", tileLayer.proj.toString())
        assertBoundsEquals(Bounds.fromString("-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857"), tileLayer.bounds, 0.000001)
    }

    @Test void tileLayerFromString() {
        TileRecipes recipes =  new TileRecipes()
        Map<String, TileLayer> tileLayers = recipes.tileLayerFromString()
        assertEquals(6, tileLayers.size())
        assertNotNull(tileLayers["mbtiles"])
        assertNotNull(tileLayers["geopackage"])
        assertNotNull(tileLayers["tms"])
        assertNotNull(tileLayers["osm"])
        assertNotNull(tileLayers["pbf"])
        assertNotNull(tileLayers["utf"])
    }

    @Test void tileLayerFromMap() {
        TileRecipes recipes =  new TileRecipes()
        Map<String, TileLayer> tileLayers = recipes.tileLayerFromMap()
        assertEquals(6, tileLayers.size())
        assertNotNull(tileLayers["mbtiles"])
        assertNotNull(tileLayers["geopackage"])
        assertNotNull(tileLayers["tms"])
        assertNotNull(tileLayers["osm"])
        assertNotNull(tileLayers["pbf"])
        assertNotNull(tileLayers["utf"])
    }

    // TileRenderer

    @Test void getTileLayerRenderer() {
        TileRecipes recipes = new TileRecipes()
        TileRenderer tileRenderer = recipes.getTileRenderer()
        assertNotNull(tileRenderer)
    }

    @Test void useImageTileRenderer() {
        TileRecipes recipes = new TileRecipes()
        TileRenderer tileRenderer = recipes.useImageTileRenderer()
        assertNotNull(tileRenderer)
    }

    @Test void useUTFGridTileRenderer() {
        TileRecipes recipes = new TileRecipes()
        TileRenderer tileRenderer = recipes.useUTFGridTileRenderer()
        assertNotNull(tileRenderer)
    }

    @Test void useVectorTileRenderer() {
        TileRecipes recipes = new TileRecipes()
        TileRenderer tileRenderer = recipes.useVectorTileRenderer()
        assertNotNull(tileRenderer)
    }

    @Test void usePbfVectorTileRenderer() {
        TileRecipes recipes = new TileRecipes()
        TileRenderer tileRenderer = recipes.usePbfVectorTileRenderer()
        assertNotNull(tileRenderer)
    }

    @Test void useRasterTileRenderer() {
        TileRecipes recipes = new TileRecipes()
        TileRenderer tileRenderer = recipes.useRasterTileRenderer()
        assertNotNull(tileRenderer)
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

    @Test void tileCursorByZoomLevelAndMinMax() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileCursorByZoomLevelAndMinMax()
        assertEquals(4, tileCursor.z)
        assertEquals(20, tileCursor.size)
        assertEquals(4, tileCursor.width)
        assertEquals(5, tileCursor.height)
        assertEquals(2, tileCursor.minX)
        assertEquals(4, tileCursor.minY)
        assertEquals(5, tileCursor.maxX)
        assertEquals(8, tileCursor.maxY)
    }

    @Test void tileCursorByZoomLevelAndBounds() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileCursorByZoomLevelAndBounds()
        assertEquals(8, tileCursor.z)
        assertEquals(24, tileCursor.size)
        assertEquals(6, tileCursor.width)
        assertEquals(4, tileCursor.height)
        assertEquals(54, tileCursor.minX)
        assertEquals(164, tileCursor.minY)
        assertEquals(59, tileCursor.maxX)
        assertEquals(167, tileCursor.maxY)
    }

    @Test void tileCursorByZoomLevelAndResXY() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileCursorByZoomLevelAndResXY()
        assertEquals(4, tileCursor.z)
        assertEquals(8, tileCursor.size)
        assertEquals(4, tileCursor.width)
        assertEquals(2, tileCursor.height)
        assertEquals(2, tileCursor.minX)
        assertEquals(9, tileCursor.minY)
        assertEquals(5, tileCursor.maxX)
        assertEquals(10, tileCursor.maxY)
    }

    @Test void tileCursorByBoundsAndWidthHeight() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileCursorByBoundsAndWidthHeight()
        assertEquals(7, tileCursor.z)
        assertEquals(6, tileCursor.size)
        assertEquals(3, tileCursor.width)
        assertEquals(2, tileCursor.height)
        assertEquals(27, tileCursor.minX)
        assertEquals(82, tileCursor.minY)
        assertEquals(29, tileCursor.maxX)
        assertEquals(83, tileCursor.maxY)
    }

    @Test void tileCursorAroundPointAtZoomLevelAndWidthHeight() {
        TileRecipes recipes = new TileRecipes()
        TileCursor tileCursor = recipes.tileCursorAroundPointAtZoomLevelAndWidthHeight()
        assertEquals(12, tileCursor.z)
        assertEquals(9, tileCursor.size)
        assertEquals(3, tileCursor.width)
        assertEquals(3, tileCursor.height)
        assertEquals(876, tileCursor.minX)
        assertEquals(2628, tileCursor.minY)
        assertEquals(878, tileCursor.maxX)
        assertEquals(2630, tileCursor.maxY)
    }

    // TileGenerator

    @Test void generateTilesToMBTiles() {
        TileRecipes recipes = new TileRecipes()
        MBTiles mbtiles = recipes.generateTilesToMBTiles()
        assertEquals(0, mbtiles.minZoom)
        assertEquals(2, mbtiles.maxZoom)
    }

    @Test void generateTilesToMBTilesWithMetaTiles() {
        TileRecipes recipes = new TileRecipes()
        MBTiles mbtiles = recipes.generateTilesToMBTilesWithMetaTiles()
        assertEquals(0, mbtiles.minZoom)
        assertEquals(2, mbtiles.maxZoom)
    }

    @Test void generateTilesToDBTiles() {
        TileRecipes recipes = new TileRecipes()
        DBTiles dbtiles = recipes.generateTilesToDBTiles()
        assertEquals(0, dbtiles.minZoom)
        assertEquals(2, dbtiles.maxZoom)
    }

    @Test void generateTilesToGeoPackage() {
        TileRecipes recipes = new TileRecipes()
        GeoPackage geopackage = recipes.generateTilesToGeoPackage()
        assertEquals(0, geopackage.minZoom)
        assertEquals(2, geopackage.maxZoom)
    }

    @Test void generateTilesToTMS() {
        TileRecipes recipes = new TileRecipes()
        TMS tms = recipes.generateTilesToTMS()
        assertNotNull(tms)
        assertNotNull(tms.get(0,0,0).data)
        assertNotNull(tms.get(1,1,1).data)
        assertNotNull(tms.get(2,2,2).data)
    }

    @Test void generateUTFGrid() {
        TileRecipes recipes = new TileRecipes()
        UTFGrid utfGrid = recipes.generateUTFGrid()
        assertNotNull(utfGrid)
        assertNotNull(utfGrid.get(0,0,0).data)
        assertNotNull(utfGrid.get(1,1,1).data)
        assertNotNull(utfGrid.get(2,2,2).data)
    }

    @Test void generatePbfVectorTiles() {
        TileRecipes recipes = new TileRecipes()
        TileLayer tileLayer = recipes.generatePbfVectorTiles()
        assertNotNull(tileLayer)
        assertNotNull(tileLayer.get(0,0,0).data)
        assertNotNull(tileLayer.get(1,1,1).data)
        assertNotNull(tileLayer.get(2,2,2).data)
    }

    @Test void generatePbfVectorTilesToMBTiles() {
        TileRecipes recipes = new TileRecipes()
        TileLayer tileLayer = recipes.generatePbfVectorTilesToMBTiles()
        assertNotNull(tileLayer)
        assertNotNull(tileLayer.get(0,0,0).data)
        assertNotNull(tileLayer.get(1,1,1).data)
        assertNotNull(tileLayer.get(2,2,2).data)
    }

    // Tile Layers

    @Test void createTMS() {
        TileRecipes recipes = new TileRecipes()
        TMS tms = recipes.createTMS()
        assertNotNull(tms)
    }

    @Test void createMBTiles() {
        TileRecipes recipes = new TileRecipes()
        MBTiles mbtiles = recipes.createMBTiles()
        assertNotNull(mbtiles)
    }

    @Test void createDBTiles() {
        TileRecipes recipes = new TileRecipes()
        DBTiles dbtiles = recipes.createDBTiles()
        assertNotNull(dbtiles)
    }

    @Test void createGeoPackageWorld() {
        TileRecipes recipes = new TileRecipes()
        GeoPackage geopackage = recipes.createGeoPackageWorld()
        assertNotNull(geopackage)
    }

    @Test void createGeoPackageWorldMerc() {
        TileRecipes recipes = new TileRecipes()
        GeoPackage geopackage = recipes.createGeoPackageWorldMerc()
        assertNotNull(geopackage)
    }

    @Test void createUTFGrid() {
        TileRecipes recipes = new TileRecipes()
        UTFGrid uTFGrid = recipes.createUTFGrid()
        assertNotNull(uTFGrid)
    }

    @Test void createVectorTiles() {
        TileRecipes recipes = new TileRecipes()
        VectorTiles vectorTiles = recipes.createVectorTiles()
        assertNotNull(vectorTiles)
    }

    @Test void createVectorTilesFromMBTiles() {
        TileRecipes recipes = new TileRecipes()
        VectorTiles vectorTiles = recipes.createVectorTilesFromMBTiles()
        assertNotNull(vectorTiles)
    }

    @Test void createGeneratingTileLayer() {
        TileRecipes recipes = new TileRecipes()
        GeneratingTileLayer tileLayer = recipes.createGeneratingTileLayer()
        assertNotNull(tileLayer)
    }

    // OSM

    @Test void createOSM() {
        TileRecipes recipes = new TileRecipes()
        OSM osm = recipes.createOSM()
        assertNotNull(osm)
    }

    @Test void createOSMUrls() {
        TileRecipes recipes = new TileRecipes()
        OSM osm = recipes.createOSMUrls()
        assertNotNull(osm)
    }

    @Test void getWellKnownOSM() {
        TileRecipes recipes = new TileRecipes()
        OSM osm = recipes.getWellKnownOSM()
        assertNotNull(osm)
    }

    @Test void getWellKnownOSMWikiMedia() {
        TileRecipes recipes = new TileRecipes()
        OSM osm = recipes.getWellKnownOSMWikiMedia()
        assertNotNull(osm)
    }

    // USGS National Map

    @Test void getWellKnownUSGSTopo() {
        TileRecipes recipes = new TileRecipes()
        TileLayer tileLayer = recipes.getWellKnownUSGSTopo()
        assertNotNull(tileLayer)
    }

    @Test void getWellKnownUSGSShadedRelief() {
        TileRecipes recipes = new TileRecipes()
        TileLayer tileLayer = recipes.getWellKnownUSGSShadedRelief()
        assertNotNull(tileLayer)
    }

    @Test void getWellKnownUSGSImagery() {
        TileRecipes recipes = new TileRecipes()
        TileLayer tileLayer = recipes.getWellKnownUSGSImagery()
        assertNotNull(tileLayer)
    }

    @Test void getWellKnownUSGSImageryTopo() {
        TileRecipes recipes = new TileRecipes()
        TileLayer tileLayer = recipes.getWellKnownUSGSImageryTopo()
        assertNotNull(tileLayer)
    }

    @Test void getWellKnownUSGSHydro() {
        TileRecipes recipes = new TileRecipes()
        TileLayer tileLayer = recipes.getWellKnownUSGSHydro()
        assertNotNull(tileLayer)
    }

}
