package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.feature.Field
import geoscript.feature.Schema
import geoscript.geom.Bounds
import geoscript.geom.Point
import geoscript.layer.Layer
import geoscript.layer.io.Writer
import geoscript.layer.io.Reader
import geoscript.layer.io.Readers
import geoscript.layer.io.Writers
import geoscript.proj.Projection
import geoscript.style.io.SimpleStyleReader
import geoscript.workspace.GeoPackage
import geoscript.workspace.Memory
import geoscript.workspace.Workspace
import groovy.json.JsonOutput

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

    List<String> collectFromFeature() {
        // tag::collectFromFeature[]
        Workspace workspace = new GeoPackage("src/main/resources/data.gpkg")
        Layer layer = workspace.get("states")
        List<String> names = layer.collectFromFeature { Feature f ->
            f["NAME_1"]
        }.sort()

        println "# Names = ${names.size()}"
        names.each { String name ->
            println name
        }
        // end::collectFromFeature[]
        int count = 0
        int max = 10
        String str = "# Names = ${names.size()}${NEW_LINE}"
        names.each { String name ->
            if (count < max) {
                str += name + NEW_LINE
            }
            count++
        }
        writeFile("layer_collect_from_feature", "${str}...")
        names
    }

    List<String> collectFromFeatureWithOptions() {
        // tag::collectFromFeatureWithOptions[]
        Workspace workspace = new GeoPackage("src/main/resources/data.gpkg")
        Layer layer = workspace.get("states")
        List<String> names = layer.collectFromFeature(
            sort: ["NAME_1"],
            start: 0,
            max: 5,
            fields: ["NAME_1"],
            filter: "NAME_1 LIKE 'M%'") { Feature f ->
                f["NAME_1"]
            }

        println "# Names = ${names.size()}"
        names.each { String name ->
            println name
        }
        // end::collectFromFeatureWithOptions[]
        String str = "# Names = ${names.size()}${NEW_LINE}"
        names.each { String name ->
            str += name + NEW_LINE
        }
        writeFile("layer_collect_from_feature_options", "${str}")
        names
    }

    // Layer Algebra

    void algebra() {
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        draw("layer_ab", [layerA, layerB])
    }

    Layer clip() {
        // tag::clip[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Layer layerC = layerA.clip(layerB)
        // end::clip[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_clip", [layerA, layerB, layerC])
        layerC
    }

    // IO

    List<Reader> listLayerReaders() {
        // tag::listLayerReaders[]
        List<Reader> readers = Readers.list()
        readers.each { Reader reader ->
            println reader.class.simpleName
        }
        // end::listLayerReaders[]
        writeFile("layer_list_readers", "${readers.collect{it.class.simpleName}.join(NEW_LINE)}")
        readers
    }

    Layer findLayerReader() {
        // tag::findLayerReader[]
        Reader reader = Readers.find("csv")
        Layer layer = reader.read(""""geom:Point:EPSG:4326","id:Integer","name:String"
"POINT (-122.3204 47.6024)","1","Seattle"
"POINT (-122.48416 47.2619)","2","Tacoma"
""")
        println "# features = ${layer.count}"
        // end::findLayerReader[]
        writeFile("layer_find_reader", "# features = ${layer.count}")
        layer
    }

    List<Writer> listLayerWriters() {
        // tag::listLayerWriters[]
        List<Writer> writers = Writers.list()
        writers.each { Writer writer ->
            println writer.class.simpleName
        }
        // end::listLayerWriters[]
        writeFile("layer_list_writers", "${writers.collect{it.class.simpleName}.join(NEW_LINE)}")
        writers
    }

    String findLayerWriter() {
        // tag::findLayerWriter[]
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

        Writer writer = Writers.find("csv")
        String csv = writer.write(layer)
        println csv
        // end::findLayerWriter[]
        writeFile("layer_find_writer", "${csv}")
        csv
    }

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

    String layerToGeobufString() {
        // tag::layerToGeobufString[]
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

        String geobuf = layer.toGeobufString()
        println geobuf
        // end::layerToGeobufString[]
        writeFile("layer_to_geobuf_string", geobuf)
        geobuf
    }

    private String prettyPrintXml(String xml) {
        StringWriter writer = new StringWriter()
        XmlNodePrinter nodePrinter = new XmlNodePrinter(new PrintWriter(writer))
        Node node = new XmlParser().parseText(xml)
        nodePrinter.print(node)
        writer.toString()
    }
}
