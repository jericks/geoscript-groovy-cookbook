package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.geom.Bounds
import geoscript.layer.Layer
import geoscript.proj.Projection
import geoscript.workspace.GeoPackage
import geoscript.workspace.Workspace

class LayerRecipes extends Recipes {

    Map<String, Object> getLayerProperties() {

        Map<String, Object> values = [:]

        // tag::getLayerProperties_name[]
        Workspace workspace = new GeoPackage("src/main/resources/data.gpkg")
        Layer layer = workspace.get("countries")
        String name = layer.name
        println "Name: ${name}"
        // end::getLayerProperties_name[]
        values.put("name", name)
        writeFile("layer_properties_name", "Name: ${name}")

        // tag::getLayerProperties_format[]
        String format = layer.format
        println "Format: ${format}"
        // end::getLayerProperties_format[]
        values.put("format", format)
        writeFile("layer_properties_format", "Format: ${format}")

        // tag::getLayerProperties_count[]
        int count = layer.count
        println "# of Features: ${count}"
        // end::getLayerProperties_count[]
        values.put("count", count)
        writeFile("layer_properties_count", "# of Features: ${count}")

        // tag::getLayerProperties_proj[]
        Projection proj = layer.proj
        println "Projection: ${proj}"
        // end::getLayerProperties_proj[]
        values.put("proj", proj)
        writeFile("layer_properties_proj", "Projection: ${proj}")

        // tag::getLayerProperties_bounds[]
        Bounds bounds = layer.bounds
        println "Bounds: ${bounds}"
        // end::getLayerProperties_bounds[]
        values.put("bounds", bounds)
        writeFile("layer_properties_bounds", "Bounds: ${bounds}")

        values
    }

    int getLayerFeatures() {
        // tag::getLayerFeatures[]
        Workspace workspace = new GeoPackage("src/main/resources/data.gpkg")
        Layer layer = workspace.get("states")
        layer.eachFeature { Feature feature ->
            println feature["NAME_1"]
        }
        // end::getLayerFeatures[]
        int max = 10
        int count = 0
        String str = ""
        layer.eachFeature { Feature feature ->
            if (count < max) {
                str += feature["NAME_1"] + NEW_LINE
            } 
            count++
        }
        writeFile("layer_features", "${str}..." )
        count
    }

}
