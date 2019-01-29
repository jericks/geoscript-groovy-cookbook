package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.geom.Point
import geoscript.layer.Grid
import geoscript.layer.ImageTile
import geoscript.layer.ImageTileRenderer
import geoscript.layer.Layer
import geoscript.layer.MBTiles
import geoscript.layer.OSM
import geoscript.layer.Pyramid
import geoscript.layer.TMS
import geoscript.layer.Tile
import geoscript.layer.TileCursor
import geoscript.layer.TileGenerator
import geoscript.layer.TileLayer
import geoscript.layer.io.GdalTmsPyramidReader
import geoscript.layer.io.GdalTmsPyramidWriter
import geoscript.layer.io.PyramidReader
import geoscript.layer.io.PyramidReaders
import geoscript.layer.io.PyramidWriter
import geoscript.layer.io.PyramidWriters
import geoscript.proj.Projection
import geoscript.style.Fill
import geoscript.style.Stroke
import geoscript.workspace.GeoPackage
import geoscript.workspace.Workspace
import groovy.json.JsonOutput

import javax.imageio.ImageIO
import javax.media.jai.PlanarImage
import java.awt.image.BufferedImage
import java.awt.image.RenderedImage

class TileRecipes extends Recipes {

    // Tile

    Tile tileProperties() {
        // tag::tileProperties[]
        byte[] data = new File("src/main/resources/tile.png").bytes
        Tile tile = new Tile(2,1,3,data)
        println "Z = ${tile.z}"
        println "X = ${tile.x}"
        println "Y = ${tile.y}"
        println "Tile = ${tile.toString()}"
        println "# bytes = ${tile.data.length}"
        println "Data as base64 encoded string = ${tile.base64String}"
        // end::tileProperties[]
        writeFile("tile_properties","""Z = ${tile.z}
X = ${tile.x}
Y = ${tile.y}
Tile = ${tile.toString()}
# bytes = ${tile.data.length}
Data as base64 encoded string = ${tile.base64String.substring(0,50)}...
""")
        tile
    }

    ImageTile imageTile() {
        // tag::imageTile[]
        byte[] data = new File("src/main/resources/tile.png").bytes
        ImageTile tile = new ImageTile(0,0,0,data)
        BufferedImage image = tile.image
        // end::imageTile[]
        saveImage("tile_image_tile", image)
        tile
    }

    // TileLayer

    TileLayer tileLayerProperties() {
        // tag::tileLayerProperties[]
        File file = new File("src/main/resources/tiles.mbtiles")
        MBTiles mbtiles = new MBTiles(file)
        // end::tileLayerProperties[]

        // tag::tileLayerProperties_name[]
        String name = mbtiles.name
        println name
        // end::tileLayerProperties_name[]
        writeFile("tilelayer_properties_name", "${name}")

        // tag::tileLayerProperties_bounds[]
        Bounds bounds = mbtiles.bounds
        println bounds
        // end::tileLayerProperties_bounds[]
        writeFile("tilelayer_properties_bounds", "${bounds}")

        // tag::tileLayerProperties_proj[]
        Projection proj = mbtiles.proj
        println proj
        // end::tileLayerProperties_proj[]
        writeFile("tilelayer_properties_proj", "${proj}")

        // tag::tileLayerProperties_pyramid[]
        Pyramid pyramid = mbtiles.pyramid
        println pyramid
        // end::tileLayerProperties_pyramid[]
        writeFile("tilelayer_properties_pyramid", "${pyramid}")

        // tag::tileLayerProperties_tile[]
        Tile tile = mbtiles.get(0, 0, 0)
        println tile
        // end::tileLayerProperties_tile[]
        writeFile("tilelayer_properties_tile", "${tile}")
        saveImage("tilelayer_properties_tile", (tile as ImageTile).image)

        mbtiles
    }

    Map<String, Boolean> tileLayerGetPutDelete() {

        File originalFile = new File("src/main/resources/tiles.mbtiles")
        File directory = new File("target")
        File file = new File(directory, "states_temp.mbtiles")
        file.withOutputStream { out ->
            originalFile.withInputStream { inp ->
                out << inp
            }
        }

        Map<String, Boolean> results = [:]

        // tag::tileLayerGetPutDelete_get[]
        MBTiles layer = new MBTiles(file)
        ImageTile tile = layer.get(0,0,0)
        // end::tileLayerGetPutDelete_get[]
        saveImage("tileLayerGetPutDelete_get", tile.image)
        results.get = tile.image != null

        // tag::tileLayerGetPutDelete_put[]
        File newTileFile = new File("src/main/resources/yellowtile.png")
        ImageTile newTile = new ImageTile(0,0,0, newTileFile.bytes)
        layer.put(newTile)
        newTile = layer.get(0,0,0)
        // end::tileLayerGetPutDelete_put[]
        saveImage("tileLayerGetPutDelete_put", newTile.image)
        results.put = newTile.image != null

        // tag::tileLayerGetPutDelete_delete[]
        layer.delete(newTile)
        newTile = layer.get(0,0,0)
        println "Image = ${newTile.image}"
        // end::tileLayerGetPutDelete_delete[]
        writeFile("tileLayerGetPutDelete_delete", "Image = ${newTile.image}")
        results.delete = newTile.image == null

        // tag::tileLayerGetPutDelete_close[]
        layer.close()
        // end::tileLayerGetPutDelete_close[]

        file.delete()

        results
    }

    // Grid

    Grid gridProperties() {
        // tag::gridProperties[]
        Grid grid = new Grid(1, 2, 2, 78206.0, 78206.0)
        println "Zoom Level: ${grid.z}"
        println "Width / # Columns: ${grid.width}"
        println "Height / # Rows: ${grid.height}"
        println "Size / # Tiles: ${grid.size}"
        println "X Resolution: ${grid.xResolution}"
        println "Y Resolution: ${grid.yResolution}"
        // end::gridProperties[]
        writeFile("tile_grid_properties", """Zoom Level: ${grid.z}
Width / # Columns: ${grid.width}
Height / # Rows: ${grid.height}
Size / # Tiles: ${grid.size}
X Resolution: ${grid.xResolution}
Y Resolution: ${grid.yResolution}
""")
        grid
    }

    // Pyramid

    Pyramid pyramidProperties() {
        // tag::pyramidPropertiesBounds[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid()

        Bounds bounds = pyramid.bounds
        println bounds
        // end::pyramidPropertiesBounds[]
        writeFile("tiles_pyramid_properties_bounds", "${bounds}")

        // tag::pyramidPropertiesProj[]
        Projection proj = pyramid.proj
        println proj
        // end::pyramidPropertiesProj[]
        writeFile("tiles_pyramid_properties_proj", "${proj}")

        // tag::pyramidPropertiesOrigin[]
        Pyramid.Origin origin = pyramid.origin
        println origin
        // end::pyramidPropertiesOrigin[]
        writeFile("tiles_pyramid_properties_origin", "${origin}")

        // tag::pyramidPropertiesWidthHeight[]
        int tileWidth = pyramid.tileWidth
        int tileHeight = pyramid.tileHeight
        println "${tileWidth} x ${tileHeight}"
        // end::pyramidPropertiesWidthHeight[]
        writeFile("tiles_pyramid_properties_wh", "${tileWidth} x ${tileHeight}")

        pyramid
    }

    Grid gridFromPyramidByZoomLevel() {
        // tag::gridFromPyramidByZoomLevel[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid()
        Grid grid = pyramid.grid(1)
        println "Zoom Level: ${grid.z}"
        println "Width / # Columns: ${grid.width}"
        println "Height / # Rows: ${grid.height}"
        println "Size / # Tiles: ${grid.size}"
        println "X Resolution: ${grid.xResolution}"
        println "Y Resolution: ${grid.yResolution}"
        // end::gridFromPyramidByZoomLevel[]
        writeFile("tile_pyramid_grid_zoomlevel", """Zoom Level: ${grid.z}
Width / # Columns: ${grid.width}
Height / # Rows: ${grid.height}
Size / # Tiles: ${grid.size}
X Resolution: ${grid.xResolution}
Y Resolution: ${grid.yResolution}
""")
        grid
    }

    Grid gridFromPyramidByBoundsAndResolution() {
        // tag::gridFromPyramidByBoundsAndResolution[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid()
        Bounds bounds = new Bounds(-123.09, 46.66, -121.13, 47.48, "EPSG:4326").reproject("EPSG:3857")
        Grid grid = pyramid.grid(bounds, bounds.width / 400.0, bounds.height / 200.0)
        println "Zoom Level: ${grid.z}"
        println "Width / # Columns: ${grid.width}"
        println "Height / # Rows: ${grid.height}"
        println "Size / # Tiles: ${grid.size}"
        println "X Resolution: ${grid.xResolution}"
        println "Y Resolution: ${grid.yResolution}"
        // end::gridFromPyramidByBoundsAndResolution[]
        writeFile("tile_pyramid_grid_bounds_res", """Zoom Level: ${grid.z}
Width / # Columns: ${grid.width}
Height / # Rows: ${grid.height}
Size / # Tiles: ${grid.size}
X Resolution: ${grid.xResolution}
Y Resolution: ${grid.yResolution}
""")
        grid
    }

    Grid gridFromPyramidByBoundsAndSize() {
        // tag::gridFromPyramidByBoundsAndSize[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid()
        Bounds bounds = new Bounds(-123.09, 46.66, -121.13, 47.48, "EPSG:4326").reproject("EPSG:3857")
        Grid grid = pyramid.grid(bounds, 400, 200)
        println "Zoom Level: ${grid.z}"
        println "Width / # Columns: ${grid.width}"
        println "Height / # Rows: ${grid.height}"
        println "Size / # Tiles: ${grid.size}"
        println "X Resolution: ${grid.xResolution}"
        println "Y Resolution: ${grid.yResolution}"
        // end::gridFromPyramidByBoundsAndSize[]
        writeFile("tile_pyramid_grid_bounds_size", """Zoom Level: ${grid.z}
Width / # Columns: ${grid.width}
Height / # Rows: ${grid.height}
Size / # Tiles: ${grid.size}
X Resolution: ${grid.xResolution}
Y Resolution: ${grid.yResolution}
""")
        grid
    }

    Grid maxGrid() {
        // tag::maxGrid[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid()
        Grid grid = pyramid.maxGrid
        println "Zoom Level: ${grid.z}"
        println "Width / # Columns: ${grid.width}"
        println "Height / # Rows: ${grid.height}"
        println "Size / # Tiles: ${grid.size}"
        println "X Resolution: ${grid.xResolution}"
        println "Y Resolution: ${grid.yResolution}"
        // end::maxGrid[]
        writeFile("tile_pyramid_maxgrid", """Zoom Level: ${grid.z}
Width / # Columns: ${grid.width}
Height / # Rows: ${grid.height}
Size / # Tiles: ${grid.size}
X Resolution: ${grid.xResolution}
Y Resolution: ${grid.yResolution}
""")
        grid
    }

    Grid minGrid() {
        // tag::minGrid[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid()
        Grid grid = pyramid.minGrid
        println "Zoom Level: ${grid.z}"
        println "Width / # Columns: ${grid.width}"
        println "Height / # Rows: ${grid.height}"
        println "Size / # Tiles: ${grid.size}"
        println "X Resolution: ${grid.xResolution}"
        println "Y Resolution: ${grid.yResolution}"
        // end::minGrid[]
        writeFile("tile_pyramid_mingrid", """Zoom Level: ${grid.z}
Width / # Columns: ${grid.width}
Height / # Rows: ${grid.height}
Size / # Tiles: ${grid.size}
X Resolution: ${grid.xResolution}
Y Resolution: ${grid.yResolution}
""")
        grid
    }

    Bounds getBoundsAroundPoint() {
        // tag::getBoundsAroundPoint[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid()
        Point point = Projection.transform(new Point(22.1539306640625, 37.67077737288316), "EPSG:4326", "EPSG:3857")
        int zoomLevel = 8
        int width = 400
        int height = 400
        Bounds bounds = pyramid.bounds(point, zoomLevel, width, height)
        println "The bounds around ${point} is ${bounds}"
        // end::getBoundsAroundPoint[]
        writeFile("tile_pyramid_bounds_around_point","The bounds around ${point} is ${bounds}")
        OSM osm = OSM.getWellKnownOSM("osm")
        RenderedImage image = osm.getRaster(osm.tiles(point, zoomLevel, width, height)).image
        saveImage("tile_pyramid_bounds_around_point", PlanarImage.wrapRenderedImage(image).getAsBufferedImage())
        bounds
    }

    Bounds getBoundsForTile() {
        // tag::getBoundsForTile[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid()
        Tile tile = new Tile(2, 1, 1)
        Bounds bounds = pyramid.bounds(tile)
        println "The bounds of ${tile} is ${bounds}"
        // end::getBoundsForTile[]
        writeFile("tile_pyramid_bounds_tiles","The bounds of ${tile} is ${bounds}")
        BufferedImage image = OSM.getWellKnownOSM("osm").get(tile.z, tile.x, tile.y).image
        saveImage("tile_pyramid_bounds_tiles", image)
        bounds
    }

    Pyramid createGlobalMercatorPyramid() {
        // tag::pyramidGlobalMercatorPyramid[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid()
        println "Projection: ${pyramid.proj}"
        println "Origin: ${pyramid.origin}"
        println "Bounds: ${pyramid.bounds}"
        println "Max Zoom: ${pyramid.maxGrid.z}"
        // end::pyramidGlobalMercatorPyramid[]
        writeFile("tile_pyramid_global_mercator", """Projection: ${pyramid.proj}
Origin: ${pyramid.origin}
Bounds: ${pyramid.bounds}
Max Zoom: ${pyramid.maxGrid.z}
""")
        pyramid
    }

    Pyramid createGlobalGeodeticPyramid() {
        // tag::pyramidGlobalGeodeticPyramid[]
        Pyramid pyramid = Pyramid.createGlobalGeodeticPyramid()
        println "Projection: ${pyramid.proj}"
        println "Origin: ${pyramid.origin}"
        println "Bounds: ${pyramid.bounds}"
        println "Max Zoom: ${pyramid.maxGrid.z}"
        // end::pyramidGlobalGeodeticPyramid[]
        writeFile("tile_pyramid_global_geodetic", """Projection: ${pyramid.proj}
Origin: ${pyramid.origin}
Bounds: ${pyramid.bounds}
Max Zoom: ${pyramid.maxGrid.z}
""")
        pyramid
    }

    Map<String, Integer> pyramidGetTileCoordinatesByBoundsAndZoom() {
        // tag::pyramidGetTileCoordinatesByBoundsAndZoom[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid()
        Bounds bounds = new Bounds(-124.73142200000001, 24.955967, -66.969849, 49.371735, "EPSG:4326").reproject("EPSG:3857")
        long zoomLevel = 4
        Map<String, Integer> coords = pyramid.getTileCoordinates(bounds, zoomLevel)
        println "Min X = ${coords.minX}"
        println "Min Y = ${coords.minY}"
        println "Max X = ${coords.maxX}"
        println "Max Y = ${coords.maxY}"
        // end::pyramidGetTileCoordinatesByBoundsAndZoom[]
        writeFile("tile_pyramid_gettilecoordinates_bounds_zoom", "Min X = ${coords.minX}${NEW_LINE}Min Y = ${coords.minY}${NEW_LINE}" +
                "Max X = ${coords.maxX}${NEW_LINE}Max Y = ${coords.maxY}")
        coords
    }

    Map<String, Integer> pyramidGetTileCoordinatesByBoundsAndGrid() {
        // tag::pyramidGetTileCoordinatesByBoundsAndGrid[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid()
        Bounds bounds = new Bounds(20.798492,36.402494,22.765045,37.223768, "EPSG:4326").reproject("EPSG:3857")
        Grid grid = pyramid.grid(10)
        Map<String, Integer> coords = pyramid.getTileCoordinates(bounds, grid)
        println "Min X = ${coords.minX}"
        println "Min Y = ${coords.minY}"
        println "Max X = ${coords.maxX}"
        println "Max Y = ${coords.maxY}"
        // end::pyramidGetTileCoordinatesByBoundsAndGrid[]
        writeFile("tile_pyramid_gettilecoordinates_bounds_grid", "Min X = ${coords.minX}${NEW_LINE}Min Y = ${coords.minY}${NEW_LINE}" +
                "Max X = ${coords.maxX}${NEW_LINE}Max Y = ${coords.maxY}")
        coords
    }

    Pyramid createPyramidFromString() {
        // tag::createPyramidFromString_GlobalMercator[]
        Pyramid pyramid = Pyramid.fromString("mercator")
        println "Projection: ${pyramid.proj}"
        println "Origin: ${pyramid.origin}"
        println "Bounds: ${pyramid.bounds}"
        println "Max Zoom: ${pyramid.maxGrid.z}"
        // end::createPyramidFromString_GlobalMercator[]
        writeFile("tile_pyramid_fromstring_globalmercator", """Projection: ${pyramid.proj}
Origin: ${pyramid.origin}
Bounds: ${pyramid.bounds}
Max Zoom: ${pyramid.maxGrid.z}
""")
        pyramid
    }

    // Pyramid IO

    List<PyramidReader> listPyramidReaders() {
        // tag::listPyramidReaders[]
        List<PyramidReader> readers = PyramidReaders.list()
        readers.each { PyramidReader reader ->
            println reader.class.simpleName
        }
        // end::listPyramidReaders[]
        writeFile("pyramid_list_readers", "${readers.collect{it.class.simpleName}.join(NEW_LINE)}")
        readers
    }

    Pyramid findPyramidReader() {
        // tag::findPyramidReader[]
        PyramidReader reader = PyramidReaders.find("csv")
        Pyramid pyramid = reader.read("""EPSG:3857
-2.0036395147881314E7,-2.0037471205137067E7,2.0036395147881314E7,2.003747120513706E7,EPSG:3857
BOTTOM_LEFT
256,256
0,1,1,156412.0,156412.0
1,2,2,78206.0,78206.0
2,4,4,39103.0,39103.0
3,8,8,19551.5,19551.5
4,16,16,9775.75,9775.75
""")
        println pyramid
        // end::findPyramidReader[]
        writeFile("pyramid_find_reader", "${pyramid}")
        pyramid
    }

    List<PyramidWriter> listPyramidWriters() {
        // tag::listPyramidWriters[]
        List<PyramidWriter> writers = PyramidWriters.list()
        writers.each { PyramidWriter writer ->
            println writer.class.simpleName
        }
        // end::listPyramidWriters[]
        writeFile("pyramid_list_writers", "${writers.collect{it.class.simpleName}.join(NEW_LINE)}")
        writers
    }

    String findPyramidWriter() {
        // tag::findPyramidWriter[]
        Pyramid pyramid = Pyramid.createGlobalGeodeticPyramid(maxZoom: 2)
        PyramidWriter writer = PyramidWriters.find("csv")
        String pyramidStr = writer.write(pyramid)
        println pyramidStr
        // end::findPyramidWriter[]
        writeFile("pyramid_find_writer", "${pyramidStr}")
        pyramidStr
    }

    String pyramidToJson() {
        // tag::pyramidToJson[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid(maxZoom: 4)
        String json = pyramid.json
        println json
        // end::pyramidToJson[]
        writeFile("tile_pyramid_to_json", JsonOutput.prettyPrint(json))
        json
    }

    String pyramidToXml() {
        // tag::pyramidToXml[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid(maxZoom: 4)
        String xml = pyramid.xml
        println xml
        // end::pyramidToXml[]
        writeFile("tile_pyramid_to_xml", xml)
        xml
    }

    String pyramidToCsv() {
        // tag::pyramidToCsv[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid(maxZoom: 4)
        String csv = pyramid.csv
        println csv
        // end::pyramidToCsv[]
        writeFile("tile_pyramid_to_csv", csv)
        csv
    }

    String writePyramidToGdalTms() {
        // tag::writePyramidToGdalTms[]
        Pyramid pyramid = Pyramid.createGlobalMercatorPyramid(maxZoom: 4)
        GdalTmsPyramidWriter writer = new GdalTmsPyramidWriter()
        String xml = writer.write(pyramid, serverUrl: 'https://myserver.com/${z}/${x}/${y}', imageFormat: 'png')
        println xml
        // end::writePyramidToGdalTms[]
        writeFile("tile_pyramid_write_gdaltms", xml)
        xml
    }

    Pyramid readPyramidFromGdalTms() {
        // tag::readPyramidFromGdalTms[]
        String xml = '''<GDAL_WMS>
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
</GDAL_WMS>'''
        GdalTmsPyramidReader reader = new GdalTmsPyramidReader()
        Pyramid pyramid = reader.read(xml)
        // end::readPyramidFromGdalTms[]
        writeFile("tile_pyramid_read_gdaltms", pyramid.toString())
        pyramid
    }

    // TileCursor

    TileCursor tileCursorByZoomLevel() {
        // tag::tileCursorByZoomLevel[]
        File file = new File("src/main/resources/tiles.mbtiles")
        MBTiles mbtiles = new MBTiles(file)

        long zoomLevel = 1
        TileCursor tileCursor = new TileCursor(mbtiles, zoomLevel)

        println "Zoom Level: ${tileCursor.z}"
        println "# of tiles: ${tileCursor.size}"
        println "Bounds: ${tileCursor.bounds}"
        println "Width / # Columns: ${tileCursor.width}"
        println "Height / # Rows: ${tileCursor.height}"
        println "MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}"

        println "Tiles:"
        tileCursor.each { Tile t ->
            println t
        }
        // end::tileCursorByZoomLevel[]
        writeFile("tile_cursor_zoomlevel", """
Zoom Level: ${tileCursor.z}
# of tiles: ${tileCursor.size}
Bounds: ${tileCursor.bounds}
Width / # Columns: ${tileCursor.width}
Height / # Rows: ${tileCursor.height}
MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}

Tiles:
${tileCursor.collect { it.toString() }.join(NEW_LINE)}
""")
        tileCursor
    }

    TileCursor tileCursorByZoomLevelAndMinMax() {
        // tag::tileCursorByZoomLevelAndMinMax[]
        File file = new File("src/main/resources/tiles.mbtiles")
        MBTiles mbtiles = new MBTiles(file)

        long zoomLevel = 4
        long minX = 2
        long minY = 4
        long maxX = 5
        long maxY = 8
        TileCursor tileCursor = new TileCursor(mbtiles, zoomLevel, minX, minY, maxX, maxY)

        println "Zoom Level: ${tileCursor.z}"
        println "# of tiles: ${tileCursor.size}"
        println "Bounds: ${tileCursor.bounds}"
        println "Width / # Columns: ${tileCursor.width}"
        println "Height / # Rows: ${tileCursor.height}"
        println "MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}"

        println "Tiles:"
        tileCursor.each { Tile t ->
            println t
        }
        // end::tileCursorByZoomLevelAndMinMax[]
        writeFile("tile_cursor_zoomlevelminmax", """
Zoom Level: ${tileCursor.z}
# of tiles: ${tileCursor.size}
Bounds: ${tileCursor.bounds}
Width / # Columns: ${tileCursor.width}
Height / # Rows: ${tileCursor.height}
MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}

Tiles:
${tileCursor.collect { it.toString() }.join(NEW_LINE)}
""")
        tileCursor
    }

    TileCursor tileCursorByZoomLevelAndBounds() {
        // tag::tileCursorByZoomLevelAndBounds[]
        File file = new File("src/main/resources/tiles.mbtiles")
        MBTiles mbtiles = new MBTiles(file)

        Bounds bounds = new Bounds(-102.875977, 45.433154, -96.481934, 48.118434, "EPSG:4326").reproject("EPSG:3857")
        int zoomLevel = 8
        TileCursor tileCursor = new TileCursor(mbtiles, bounds, zoomLevel)

        println "Zoom Level: ${tileCursor.z}"
        println "# of tiles: ${tileCursor.size}"
        println "Bounds: ${tileCursor.bounds}"
        println "Width / # Columns: ${tileCursor.width}"
        println "Height / # Rows: ${tileCursor.height}"
        println "MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}"

        println "Tiles:"
        tileCursor.each { Tile t ->
            println t
        }
        // end::tileCursorByZoomLevelAndBounds[]
        writeFile("tile_cursor_zoomlevelbounds", """
Zoom Level: ${tileCursor.z}
# of tiles: ${tileCursor.size}
Bounds: ${tileCursor.bounds}
Width / # Columns: ${tileCursor.width}
Height / # Rows: ${tileCursor.height}
MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}

Tiles:
${tileCursor.collect { it.toString() }.join(NEW_LINE)}
""")
        tileCursor
    }

    TileCursor tileCursorByZoomLevelAndResXY() {
        // tag::tileCursorByZoomLevelAndResXY[]
        File file = new File("src/main/resources/tiles.mbtiles")
        MBTiles mbtiles = new MBTiles(file)

        Bounds bounds = new Bounds(-124.73142200000001, 24.955967, -66.969849, 49.371735, "EPSG:4326").reproject("EPSG:3857")
        double resolutionX = bounds.width / 400
        double resolutionY = bounds.height / 300
        TileCursor tileCursor = new TileCursor(mbtiles, bounds, resolutionX, resolutionY)

        println "Zoom Level: ${tileCursor.z}"
        println "# of tiles: ${tileCursor.size}"
        println "Bounds: ${tileCursor.bounds}"
        println "Width / # Columns: ${tileCursor.width}"
        println "Height / # Rows: ${tileCursor.height}"
        println "MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}"

        println "Tiles:"
        tileCursor.each { Tile t ->
            println t
        }
        // end::tileCursorByZoomLevelAndResXY[]
        writeFile("tile_cursor_zoomlevelresxy", """
Zoom Level: ${tileCursor.z}
# of tiles: ${tileCursor.size}
Bounds: ${tileCursor.bounds}
Width / # Columns: ${tileCursor.width}
Height / # Rows: ${tileCursor.height}
MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}

Tiles:
${tileCursor.collect { it.toString() }.join(NEW_LINE)}
""")
        tileCursor
    }

    TileCursor tileCursorByBoundsAndWidthHeight() {
        // tag::tileCursorByBoundsAndWidthHeight[]
        File file = new File("src/main/resources/tiles.mbtiles")
        MBTiles mbtiles = new MBTiles(file)

        Bounds bounds = new Bounds(-102.875977, 45.433154, -96.481934, 48.118434, "EPSG:4326").reproject("EPSG:3857")
        int width = 400
        int height = 400
        TileCursor tileCursor = new TileCursor(mbtiles, bounds, width, height)

        println "Zoom Level: ${tileCursor.z}"
        println "# of tiles: ${tileCursor.size}"
        println "Bounds: ${tileCursor.bounds}"
        println "Width / # Columns: ${tileCursor.width}"
        println "Height / # Rows: ${tileCursor.height}"
        println "MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}"

        println "Tiles:"
        tileCursor.each { Tile t ->
            println t
        }
        // end::tileCursorByBoundsAndWidthHeight[]
        writeFile("tile_cursor_boundswidthheight", """
Zoom Level: ${tileCursor.z}
# of tiles: ${tileCursor.size}
Bounds: ${tileCursor.bounds}
Width / # Columns: ${tileCursor.width}
Height / # Rows: ${tileCursor.height}
MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}

Tiles:
${tileCursor.collect { it.toString() }.join(NEW_LINE)}
""")
        tileCursor
    }

    // Point p, long z, int w, int h

    TileCursor tileCursorAroundPointAtZoomLevelAndWidthHeight() {
        // tag::tileCursorAroundPointAtZoomLevelAndWidthHeight[]
        File file = new File("src/main/resources/tiles.mbtiles")
        MBTiles mbtiles = new MBTiles(file)

        Point point = Projection.transform(new Point(-102.875977, 45.433154), "EPSG:4326", "EPSG:3857")
        int zoomLevel = 12
        int width = 400
        int height = 400
        TileCursor tileCursor = new TileCursor(mbtiles, point, zoomLevel, width, height)

        println "Zoom Level: ${tileCursor.z}"
        println "# of tiles: ${tileCursor.size}"
        println "Bounds: ${tileCursor.bounds}"
        println "Width / # Columns: ${tileCursor.width}"
        println "Height / # Rows: ${tileCursor.height}"
        println "MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}"

        println "Tiles:"
        tileCursor.each { Tile t ->
            println t
        }
        // end::tileCursorAroundPointAtZoomLevelAndWidthHeight[]
        writeFile("tile_cursor_point_zoom_widthheight", """
Zoom Level: ${tileCursor.z}
# of tiles: ${tileCursor.size}
Bounds: ${tileCursor.bounds}
Width / # Columns: ${tileCursor.width}
Height / # Rows: ${tileCursor.height}
MinX: ${tileCursor.minX}, MinY: ${tileCursor.minY}, MaxX: ${tileCursor.maxX}, MaxY: ${tileCursor.maxY}

Tiles:
${tileCursor.collect { it.toString() }.join(NEW_LINE)}
""")
        tileCursor
    }

    // TileGenerator

    MBTiles generateTilesToMBTiles() {
        // tag::generateTilesToMBTiles[]
        File file = new File("target/world.mbtiles")
        MBTiles mbtiles = new MBTiles(file, "World", "World Tiles")

        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")

        ImageTileRenderer renderer = new ImageTileRenderer(mbtiles, [ocean, countries])
        TileGenerator generator = new TileGenerator()
        generator.generate(mbtiles, renderer, 0, 2)
        // end::generateTilesToMBTiles[]
        RenderedImage image = mbtiles.getRaster(mbtiles.tiles(1)).image
        saveImage("tile_generate_mbtiles", PlanarImage.wrapRenderedImage(image).getAsBufferedImage())
        mbtiles
    }

    geoscript.layer.GeoPackage generateTilesToGeoPackage() {
        // tag::generateTilesToGeoPackage[]
        File file = new File("target/world.gpkg")
        geoscript.layer.GeoPackage geopackage = new geoscript.layer.GeoPackage(file, "World", Pyramid.createGlobalGeodeticPyramid())

        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")

        ImageTileRenderer renderer = new ImageTileRenderer(geopackage, [ocean, countries])
        TileGenerator generator = new TileGenerator()
        generator.generate(geopackage, renderer, 0, 2)
        // end::generateTilesToGeoPackage[]
        RenderedImage image = geopackage.getRaster(geopackage.tiles(1)).image
        saveImage("tile_generate_geopackage", PlanarImage.wrapRenderedImage(image).getAsBufferedImage())
        geopackage
    }

    TMS generateTilesToTMS() {
        // tag::generateTilesToTMS[]
        File directory = new File("target/tiles")
        directory.mkdir()
        TMS tms = new TMS("world", "png", directory, Pyramid.createGlobalMercatorPyramid())

        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")

        ImageTileRenderer renderer = new ImageTileRenderer(tms, [ocean, countries])
        TileGenerator generator = new TileGenerator()
        generator.generate(tms, renderer, 0, 2)
        // end::generateTilesToTMS[]
        RenderedImage image = tms.getRaster(tms.tiles(1)).image
        saveImage("tile_generate_tms", PlanarImage.wrapRenderedImage(image).getAsBufferedImage())
        tms
    }

    // OSM

    OSM createOSM() {
        // tag::createOSM[]
        OSM osm = new OSM()
        // end::createOSM[]
        RenderedImage image = osm.getRaster(osm.tiles(1)).image
        saveImage("tile_osm_default", PlanarImage.wrapRenderedImage(image).getAsBufferedImage())
        osm
    }

    OSM createOSMUrls() {
        // tag::createOSMUrls[]
        OSM osm = new OSM("OSM", [
            "http://a.tile.openstreetmap.org",
            "http://b.tile.openstreetmap.org",
            "http://c.tile.openstreetmap.org"
        ])
        // end::createOSMUrls[]
        RenderedImage image = osm.getRaster(osm.tiles(1)).image
        saveImage("tile_osm_urls", PlanarImage.wrapRenderedImage(image).getAsBufferedImage())
        osm
    }

    OSM getWellKnownOSM() {
        // tag::getWellKnownOSM[]
        OSM osm = OSM.getWellKnownOSM("osm")
        // end::getWellKnownOSM[]
        RenderedImage image = osm.getRaster(osm.tiles(1)).image
        saveImage("tile_osm_wellknown", PlanarImage.wrapRenderedImage(image).getAsBufferedImage())
        osm
    }

    OSM getWellKnownOSMStamenToner() {
        // tag::getWellKnownOSMStamenToner[]
        OSM osm = OSM.getWellKnownOSM("stamen-toner")
        // end::getWellKnownOSMStamenToner[]
        RenderedImage image = osm.getRaster(osm.tiles(1)).image
        saveImage("tile_osm_stamen_toner", PlanarImage.wrapRenderedImage(image).getAsBufferedImage())
        osm
    }

    OSM getWellKnownOSMStamenTonerLite() {
        // tag::getWellKnownOSMStamenTonerLite[]
        OSM osm = OSM.getWellKnownOSM("stamen-toner-lite")
        // end::getWellKnownOSMStamenTonerLite[]
        RenderedImage image = osm.getRaster(osm.tiles(1)).image
        saveImage("tile_osm_stamen_toner_lite", PlanarImage.wrapRenderedImage(image).getAsBufferedImage())
        osm
    }

    OSM getWellKnownOSMStamenWaterColor() {
        // tag::getWellKnownOSMStamenWaterColor[]
        OSM osm = OSM.getWellKnownOSM("stamen-watercolor")
        // end::getWellKnownOSMStamenWaterColor[]
        RenderedImage image = osm.getRaster(osm.tiles(1)).image
        saveImage("tile_osm_stamen_water_color", PlanarImage.wrapRenderedImage(image).getAsBufferedImage())
        osm
    }

    OSM getWellKnownOSMStamenTerrain() {
        // tag::getWellKnownOSMStamenTerrain[]
        OSM osm = OSM.getWellKnownOSM("stamen-terrain")
        // end::getWellKnownOSMStamenTerrain[]
        RenderedImage image = osm.getRaster(osm.tiles(1)).image
        saveImage("tile_osm_stamen_terrain", PlanarImage.wrapRenderedImage(image).getAsBufferedImage())
        osm
    }
}
