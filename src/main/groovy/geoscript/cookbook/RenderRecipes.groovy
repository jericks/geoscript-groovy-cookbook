package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.layer.Layer
import geoscript.render.Map
import geoscript.style.Fill
import geoscript.style.Stroke
import geoscript.workspace.GeoPackage
import geoscript.workspace.Workspace

class RenderRecipes extends Recipes {

    Map createMap() {
        // tag::createMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
            width: 800,
            height: 300,
            layers: [ocean, countries]
        )
        File file = new File("map.png")
        map.render(file)
        // end::createMap[]
        moveFile(file, new File("src/docs/asciidoc/images/map_create.png"))
        map
    }

}
