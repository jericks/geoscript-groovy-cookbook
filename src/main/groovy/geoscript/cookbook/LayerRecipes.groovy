package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.feature.Field
import geoscript.feature.Schema
import geoscript.geom.Bounds
import geoscript.geom.Point
import geoscript.layer.Layer
import geoscript.proj.Projection
import geoscript.workspace.GeoPackage
import geoscript.workspace.Memory
import geoscript.workspace.Workspace
import groovy.json.JsonOutput
import groovy.xml.XmlUtil

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

    int getLayerFeaturesFiltered() {
        // tag::getLayerFeaturesFiltered[]
        Workspace workspace = new GeoPackage("src/main/resources/data.gpkg")
        Layer layer = workspace.get("states")
        layer.eachFeature("NAME_1 LIKE 'M%'") { Feature feature ->
            println feature["NAME_1"]
        }
        // end::getLayerFeaturesFiltered[]
        int count = 0
        String str = ""
        layer.eachFeature("NAME_1 LIKE 'M%'") { Feature feature ->
            str += feature["NAME_1"] + NEW_LINE
            count++
        }
        writeFile("layer_features_filtered", "${str}")
        count
    }

    int getLayerFeaturesWithParameters() {
        // tag::getLayerFeaturesWithParameters[]
        Workspace workspace = new GeoPackage("src/main/resources/data.gpkg")
        Layer layer = workspace.get("states")
        layer.eachFeature(sort: ["NAME_1"], start: 0, max: 5, fields: ["NAME_1"], filter: "NAME_1 LIKE 'M%'") { Feature feature ->
            println feature["NAME_1"]
        }
        // end::getLayerFeaturesWithParameters[]
        int count = 0
        String str = ""
        layer.eachFeature(sort: ["NAME_1"], start: 0, max: 5, fields: ["NAME_1"], filter: "NAME_1 LIKE 'M%'") { Feature feature ->
            str += feature["NAME_1"] + NEW_LINE
            count++
        }
        writeFile("layer_features_params", "${str}")
        count
    }

    List<Feature> getLayerFeaturesInAList() {
        // tag::getLayerFeaturesInAList[]
        Workspace workspace = new GeoPackage("src/main/resources/data.gpkg")
        Layer layer = workspace.get("states")
        List<Feature> features = layer.features
        
        println "# Features = ${features.size()}"
        features.each { Feature feature ->
            println feature["NAME_1"]
        }
        // end::getLayerFeaturesInAList[]
        int count = 0
        int max = 10
        String str = "# Features = ${features.size()}${NEW_LINE}"
        features.each { Feature feature ->
            if (count < max) {
                str += feature["NAME_1"] + NEW_LINE
            }
            count++
        }
        writeFile("layer_list_features", "${str}...")
        features
    }

    // IO

    String layerToGeoJSONString() {
        // tag::layerToGeoJSONString[]
        Workspace workspace = new Memory()
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Layer layer = workspace.create(schema)
        layer.add([
            geom: new Point(-122.3204, 47.6024),
            id: 1,
            name: "Seattle"
        ])
        layer.add([
            geom: new Point(-122.48416, 47.2619),
            id: 2,
            name: "Tacoma"
        ])

        String geojson = layer.toJSONString()
        println geojson
        // end::layerToGeoJSONString[]
        writeFile("layer_to_geojson_string", JsonOutput.prettyPrint(geojson))
        geojson
    }

    String layerToKMLString() {
        // tag::layerToKMLString[]
        Workspace workspace = new Memory()
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Layer layer = workspace.create(schema)
        layer.add([
            geom: new Point(-122.3204, 47.6024),
            id: 1,
            name: "Seattle"
        ])
        layer.add([
            geom: new Point(-122.48416, 47.2619),
            id: 2,
            name: "Tacoma"
        ])

        String kml = layer.toKMLString()
        println kml
        // end::layerToKMLString[]
        writeFile("layer_to_kml_string", prettyPrintXml(kml))
        kml
    }

    String layerToGMLString() {
        // tag::layerToGMLString[]
        Workspace workspace = new Memory()
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Layer layer = workspace.create(schema)
        layer.add([
                geom: new Point(-122.3204, 47.6024),
                id: 1,
                name: "Seattle"
        ])
        layer.add([
                geom: new Point(-122.48416, 47.2619),
                id: 2,
                name: "Tacoma"
        ])

        String gml = layer.toGMLString()
        println gml
        // end::layerToGMLString[]
        writeFile("layer_to_gml_string", prettyPrintXml(gml))
        gml
    }

    private String prettyPrintXml(String xml) {
        StringWriter writer = new StringWriter()
        XmlNodePrinter nodePrinter = new XmlNodePrinter(new PrintWriter(writer))
        Node node = new XmlParser().parseText(xml)
        nodePrinter.print(node)
        writer.toString()
    }
}
