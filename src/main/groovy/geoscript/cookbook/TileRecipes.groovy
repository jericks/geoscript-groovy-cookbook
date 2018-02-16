package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.layer.ImageTile
import geoscript.layer.MBTiles
import geoscript.layer.Pyramid
import geoscript.layer.Tile
import geoscript.layer.TileLayer
import geoscript.proj.Projection

import java.awt.image.BufferedImage

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

    // Pyramid

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


}
