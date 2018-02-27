package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.layer.Grid
import geoscript.layer.ImageTile
import geoscript.layer.ImageTileRenderer
import geoscript.layer.Layer
import geoscript.layer.MBTiles
import geoscript.layer.Pyramid
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

}
