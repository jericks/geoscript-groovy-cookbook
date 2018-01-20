package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.feature.Field
import geoscript.feature.Schema
import geoscript.filter.Color
import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.MultiPoint
import geoscript.geom.Point
import geoscript.layer.Graticule
import geoscript.layer.Layer
import geoscript.layer.io.Writer
import geoscript.layer.io.Reader
import geoscript.layer.io.Readers
import geoscript.layer.io.Writers
import geoscript.proj.Projection
import geoscript.style.Shape
import geoscript.style.Stroke
import geoscript.style.UniqueValues
import geoscript.style.io.SimpleStyleReader
import geoscript.workspace.Directory
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

    // Geoprocessing

    Layer merge() {
        // tag::merge[]
        Workspace workspace = new Directory(new File("src/main/resources/data"))
        Layer states = workspace.get("states")
        Feature washington = states.first(filter: "STATE_NAME='Washington'")
        Feature oregon = states.first(filter: "STATE_NAME='Oregon'")

        Schema waSchema = new Schema("washington",[
                new Field("geom","Point","EPSG:4326"),
                new Field("id","int")
        ])
        Layer waLayer = new Memory().create(waSchema)
        waLayer.withWriter { geoscript.layer.Writer writer ->
            Geometry.createRandomPoints(washington.geom, 50).points.eachWithIndex { Point pt, int index ->
                writer.add(waSchema.feature([geom: pt, id: index]))
            }
        }
        println "The Washington Layer  has ${waLayer.count} features"

        Schema orSchema = new Schema("oregon",[
                new Field("geom","Point","EPSG:4326"),
                new Field("id","int")
        ])
        Layer orLayer = new Memory().create(orSchema)
        orLayer.withWriter { geoscript.layer.Writer writer ->
            Geometry.createRandomPoints(oregon.geom, 50).points.eachWithIndex { Point pt, int index ->
                writer.add(orSchema.feature([geom: pt, id: index]))
            }
        }
        println "The Oregon Layer  has ${orLayer.count} features"

        Layer mergedLayer = orLayer.merge(waLayer)
        println "The merged Layer has ${mergedLayer.count} features"
        // end::merge[]
        states.style = new Stroke("black", 0.5)
        mergedLayer.style = new Shape("#ADD8E6", 8) + new Stroke("blue", 0.5)
        drawOnBasemap("layer_merge", [mergedLayer, states], washington.bounds.expand(oregon.bounds))
        writeFile("layer_merge","""
The Washington Layer  has ${waLayer.count} features 
The Oregon Layer  has ${orLayer.count} features
The merged Layer has ${mergedLayer.count} features
""")
        mergedLayer
    }

    Layer dissolve() {
        // tag::dissolve[]
        Workspace workspace = new Directory(new File("src/main/resources/data"))
        Layer states = workspace.get("states")
        Layer regions = states.dissolve(states.schema.get("SUB_REGION"))
        // end::dissolve[]
        states.style = new Stroke("black", 0.5)
        regions.style = new UniqueValues(regions, regions.schema.get("SUB_REGION"), Color.getPaletteColors("MutedTerrain"))
        drawOnBasemap("layer_dissolve", [regions, states], states.bounds)
        regions
    }

    Layer buffer() {
        // tag::buffer[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/data.gpkg"))
        Layer places = workspace.get("places")
        Layer buffer = places.buffer(5)
        // end::buffer[]
        places.style = new Shape("navy")
        buffer.style = new SimpleStyleReader().read("fill=deepskyblue fill-opacity=0.75 stroke=black stroke-width=0.2")
        drawOnBasemap("layer_buffer", [buffer, places])
        buffer
    }

    Workspace splitByLayer() {
        // tag::splitByLayer[]
        Schema schema = new Schema("grid",[
                new Field("geom","Polygon","EPSG:4326"),
                new Field("col","int"),
                new Field("row","int"),
                new Field("row_col","string")
        ])
        Workspace gridWorkspace = new Directory("target")
        Layer gridLayer = gridWorkspace.create(schema)
        new Bounds(-180,-90,180,90,"EPSG:4326").generateGrid(2, 3, "polygon", {cell, col, row ->
            gridLayer.add([
                    "geom": cell,
                    "col": col,
                    "row": row,
                    "row_col": "${row} ${col}"
            ])
        })

        Workspace workspace = new GeoPackage(new File("src/main/resources/data.gpkg"))
        Layer countries = workspace.get("countries")

        Workspace outWorkspace = new Memory()
        countries.split(gridLayer,gridLayer.schema.get("row_col"),outWorkspace)
        
        outWorkspace.layers.each { Layer layer ->
            println "${layer.name} has ${layer.count} features"
        }
        // end::splitByLayer[]
        List<Layer> layers = outWorkspace.layers
        List<Color> colors = Color.getPaletteColors("Dark2")
        layers.eachWithIndex { Layer layer, int index ->
            layer.style = new Stroke(colors[index], 1)
        }
        gridLayer.style = new Stroke("black", 1.0, [5,2])
        drawOnBasemap("layer_splitbylayer", [layers, gridLayer].flatten())
        writeFile("layer_splitbylayer", outWorkspace.layers.collect { Layer layer -> "${layer.name} has ${layer.count} features" }.join(NEW_LINE))
        outWorkspace
    }

    Workspace splitByField() {
        // tag::splitByField[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/data.gpkg"))
        Layer rivers = workspace.get("rivers")
        Workspace outWorkspace = new Memory()
        rivers.split(rivers.schema.get("scalerank"), outWorkspace)

        outWorkspace.layers.each { Layer layer ->
            println "${layer.name} has ${layer.count} features"
        }
        // end::splitByField[]
        List<Layer> layers = outWorkspace.layers
        List<Color> colors = Color.getPaletteColors("BlueToYellowToRedHeatMap")
        layers.eachWithIndex { Layer layer, int index ->
            layer.style = new Stroke(colors[index], 1)
        }
        drawOnBasemap("layer_splitbyfield", layers)
        writeFile("layer_splitbyfield", outWorkspace.layers.collect { Layer layer -> "${layer.name} has ${layer.count} features" }.join(NEW_LINE))
        outWorkspace
    }

    Layer transform() {
        // tag::transform[]
        Workspace workspace = new Directory(new File("src/main/resources/data"))
        Layer states = workspace.get("states")
        Layer centroids = states.transform("centroids", [
            geom: "centroid(the_geom)",
            name: "strToUpperCase(STATE_NAME)",
            ratio: "FEMALE / MALE"
        ])
        centroids.eachFeature(max: 5) {Feature f ->
            println "${f.geom} ${f['name']} = ${f['ratio']}"
        }
        // end::transform[]
        states.style = new Stroke("black", 0.5)
        Workspace outWorkspace = new Directory("target")
        Layer layer = outWorkspace.add(centroids)
        layer.style = new Shape("#ADD8E6", 8) + new Stroke("blue", 0.5)
        drawOnBasemap("layer_transform", [layer, states], states.bounds)
        writeFile("layer_transform", centroids.collectFromFeature(max: 5) {Feature f ->
            "${f['name']} = ${f['ratio']}"
        }.join(NEW_LINE))
        centroids
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

    Layer clipToWorkspace() {
        // tag::clipToWorkspace[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Workspace outWorkspace = new Directory("target")
        Layer layerC = layerB.clip(layerA, outWorkspace: outWorkspace, outLayer: "ba_clip")
        // end::clipToWorkspace[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_clip_to_workspace", [layerA, layerB, layerC])
        layerC
    }

    Layer union() {
        // tag::union[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Layer layerC = layerA.union(layerB)
        // end::union[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_union", [layerA, layerB, layerC])
        layerC
    }

    Layer unionToWorkspace() {
        // tag::unionToWorkspace[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Workspace outWorkspace = new Directory("target")
        Layer layerC = layerB.union(layerA, outWorkspace: outWorkspace, outLayer: "ba_union")
        // end::unionToWorkspace[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_union_to_workspace", [layerA, layerB, layerC])
        layerC
    }

    Layer intersection() {
        // tag::intersection[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Layer layerC = layerA.intersection(layerB)
        // end::intersection[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_intersection", [layerA, layerB, layerC])
        layerC
    }

    Layer intersectionToWorkspace() {
        // tag::intersectionToWorkspace[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Workspace outWorkspace = new Directory("target")
        Layer layerC = layerB.intersection(layerA, outWorkspace: outWorkspace, outLayer: "ba_intersection")
        // end::intersectionToWorkspace[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_intersection_to_workspace", [layerA, layerB, layerC])
        layerC
    }

    Layer identity() {
        // tag::identity[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Layer layerC = layerA.identity(layerB)
        // end::identity[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_identity", [layerA, layerB, layerC])
        layerC
    }

    Layer identityToWorkspace() {
        // tag::identityToWorkspace[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Workspace outWorkspace = new Directory("target")
        Layer layerC = layerB.identity(layerA, outWorkspace: outWorkspace, outLayer: "ba_identity")
        // end::identityToWorkspace[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_identity_to_workspace", [layerA, layerB, layerC])
        layerC
    }

    Layer erase() {
        // tag::erase[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Layer layerC = layerA.erase(layerB)
        // end::erase[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_erase", [layerA, layerB, layerC])
        layerC
    }

    Layer eraseToWorkspace() {
        // tag::eraseToWorkspace[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Workspace outWorkspace = new Directory("target")
        Layer layerC = layerB.erase(layerA, outWorkspace: outWorkspace, outLayer: "ba_erase")
        // end::eraseToWorkspace[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_erase_to_workspace", [layerA, layerB, layerC])
        layerC
    }

    Layer update() {
        // tag::update[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Layer layerC = layerA.update(layerB)
        // end::update[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_update", [layerA, layerB, layerC])
        layerC
    }

    Layer updateToWorkspace() {
        // tag::updateToWorkspace[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Workspace outWorkspace = new Directory("target")
        Layer layerC = layerB.update(layerA, outWorkspace: outWorkspace, outLayer: "ba_update")
        // end::updateToWorkspace[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_update_to_workspace", [layerA, layerB, layerC])
        layerC
    }

    Layer symDifference() {
        // tag::symDifference[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Layer layerC = layerA.symDifference(layerB)
        // end::symDifference[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_symDifference", [layerA, layerB, layerC])
        layerC
    }

    Layer symDifferenceToWorkspace() {
        // tag::symDifferenceToWorkspace[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/layeralgebra.gpkg"))
        Layer layerA = workspace.get("a")
        Layer layerB = workspace.get("b")
        Workspace outWorkspace = new Directory("target")
        Layer layerC = layerB.symDifference(layerA, outWorkspace: outWorkspace, outLayer: "ba_symdifference")
        // end::symDifferenceToWorkspace[]
        layerA.style = new SimpleStyleReader().read("fill=red fill-opacity=0.75")
        layerB.style = new SimpleStyleReader().read("fill=green fill-opacity=0.75")
        layerC.style = new SimpleStyleReader().read("fill=blue fill-opacity=0.75")
        draw("layer_symDifference_to_workspace", [layerA, layerB, layerC])
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

    // Graticule

    Layer createSquareGraticule() {
        // tag::createSquareGraticule[]
        Bounds bounds = new Bounds(-180,-90,180,90,"EPSG:4326")
        double length = 20
        double spacing = 5
        Layer layer = Graticule.createSquares(bounds, length, spacing)
        // end::createSquareGraticule[]
        layer.style = new Stroke("black", 0.5)
        drawOnBasemap("layer_graticule_square", [layer])
        layer
    }

    Layer createSquareGraticuleToShapefile() {
        // tag::createSquareGraticuleToShapefile[]
        Bounds bounds = new Bounds(-180,-90,180,90,"EPSG:4326")
        double length = 30
        double spacing = -1
        Workspace workspace = new Directory("target")
        Layer layer = Graticule.createSquares(bounds, length, spacing, workspace: workspace, layer: "squares")
        // end::createSquareGraticuleToShapefile[]
        layer.style = new Stroke("black", 0.5)
        drawOnBasemap("layer_graticule_square_shp", [layer])
        layer
    }
}
