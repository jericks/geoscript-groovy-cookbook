package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.layer.ImageTile
import geoscript.layer.MBTiles
import geoscript.layer.Pyramid
import geoscript.layer.Tile
import geoscript.proj.Projection

class TileRecipes extends Recipes {

    MBTiles getMBTiles() {
        // tag::getMBTiles[]
        File file = new File("src/main/resources/tiles.mbtiles")
        MBTiles mbtiles = new MBTiles(file)
        // end::getMBTiles[]

        // tag::getMBTiles_name[]
        String name = mbtiles.name
        println name
        // end::getMBTiles_name[]
        writeFile("tiles_get_mbtiles_name", "${name}")

        // tag::getMBTiles_bounds[]
        Bounds bounds = mbtiles.bounds
        println bounds
        // end::getMBTiles_bounds[]
        writeFile("tiles_get_mbtiles_bounds", "${bounds}")

        // tag::getMBTiles_proj[]
        Projection proj = mbtiles.proj
        println proj
        // end::getMBTiles_proj[]
        writeFile("tiles_get_mbtiles_proj", "${proj}")

        // tag::getMBTiles_pyramid[]
        Pyramid pyramid = mbtiles.pyramid
        println pyramid
        // end::getMBTiles_pyramid[]
        writeFile("tiles_get_mbtiles_pyramid", "${pyramid}")

        // tag::getMBTiles_tile[]
        Tile tile = mbtiles.get(0, 0, 0)
        println tile
        // end::getMBTiles_tile[]
        writeFile("tiles_get_mbtiles_tile", "${tile}")
        saveImage("tiles_get_mbtiles_tile", (tile as ImageTile).image)

        mbtiles
    }

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
}
