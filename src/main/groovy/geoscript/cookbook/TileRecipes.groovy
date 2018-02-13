package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.layer.MBTiles
import geoscript.layer.TileLayer
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

        mbtiles
    }

}
