package geoscript.cookbook

import geoscript.filter.Expression
import geoscript.filter.Function
import geoscript.filter.ProcessFunction
import geoscript.geom.Bounds
import geoscript.geom.GeometryCollection
import geoscript.layer.Cursor
import geoscript.layer.Layer
import geoscript.process.Process
import geoscript.style.Fill
import geoscript.style.Stroke
import geoscript.style.Symbolizer
import geoscript.style.Transform
import geoscript.workspace.GeoPackage
import geoscript.workspace.Workspace

class ProcessRecipes extends Recipes {

    List<String> getProcessNames() {
        // tag::getProcessNames[]
        List<String> processes = Process.processNames
        processes.each { String name ->
            println name
        }
        // end::getProcessNames[]
        writeFile("process_names", "${processes.join(NEW_LINE)}")
        processes
    }

    Map<String,Object> createBuiltInProcess() {
        Map<String,Object> values = [:]

        // tag::createBuiltInProcess_name[]
        Process process = new Process("vec:Bounds")
        String name = process.name
        println name
        // end::createBuiltInProcess_name[]
        writeFile("process_create_builtin_name","${name}")
        values.name = name

        // tag::createBuiltInProcess_title[]
        String title = process.title
        println title
        // end::createBuiltInProcess_title[]
        writeFile("process_create_builtin_title","${title}")
        values.title = title

        // tag::createBuiltInProcess_description[]
        String description = process.description
        println description
        // end::createBuiltInProcess_description[]
        writeFile("process_create_builtin_description","${description}")
        values.description = description

        // tag::createBuiltInProcess_version[]
        String version = process.version
        println version
        // end::createBuiltInProcess_version[]
        writeFile("process_create_builtin_version","${version}")
        values.version = version

        // tag::createBuiltInProcess_parameters[]
        Map parameters = process.parameters
        println parameters
        // end::createBuiltInProcess_parameters[]
        writeFile("process_create_builtin_parameters","${parameters}")
        values.parameters = parameters

        // tag::createBuiltInProcess_results[]
        Map results = process.results
        println results
        // end::createBuiltInProcess_results[]
        writeFile("process_create_builtin_results","${results}")
        values.results = results

        // tag::createBuiltInProcess_execute[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer layer = workspace.get("places")
        Map executeResults = process.execute([features: layer])
        Bounds bounds = executeResults.bounds
        // end::createBuiltInProcess_execute[]
        drawOnBasemap("process_execute_bounds", [
            layer,
            createLayerFromGeometry("bounds", bounds.geometry, "fill=#0066FF fill-opacity=0.15 stroke=#0047b2 stroke-width=0.5")
        ])
        values.executeResults = executeResults
        
        values
    }

    Map<String,Object> createClosureProcess() {
        Map<String,Object> values = [:]

        // tag::createClosureProcess_name[]
        Process process = new Process("convexhull",
                "Create a convexhull around the features",
                [features: geoscript.layer.Cursor],
                [result: geoscript.layer.Cursor],
                { inputs ->
                    def geoms = new GeometryCollection(inputs.features.collect{f -> f.geom})
                    def output = new Layer()
                    output.add([geoms.convexHull])
                    [result: output]
                }
        )
        String name = process.name
        println name
        // end::createClosureProcess_name[]
        writeFile("process_closure_name","${name}")
        values.name = name

        // tag::createClosureProcess_title[]
        String title = process.title
        println title
        // end::createClosureProcess_title[]
        writeFile("process_closure_title","${title}")
        values.title = title

        // tag::createClosureProcess_description[]
        String description = process.description
        println description
        // end::createClosureProcess_description[]
        writeFile("process_closure_description","${description}")
        values.description = description

        // tag::createClosureProcess_version[]
        String version = process.version
        println version
        // end::createClosureProcess_version[]
        writeFile("process_closure_version","${version}")
        values.version = version

        // tag::createClosureProcess_parameters[]
        Map parameters = process.parameters
        println parameters
        // end::createClosureProcess_parameters[]
        writeFile("process_closure_parameters","${parameters}")
        values.parameters = parameters

        // tag::createClosureProcess_results[]
        Map results = process.results
        println results
        // end::createClosureProcess_results[]
        writeFile("process_closure_results","${results}")
        values.results = results

        // tag::executeClosureProcess[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer layer = workspace.get("places")
        Map executeResults = process.execute([features: layer.cursor])
        Cursor convexHullCursor = executeResults.result
        // end::executeClosureProcess[]
        drawOnBasemap("process_execute_closure", [
                layer,
                createLayerFromCursor(convexHullCursor, "fill=#0066FF fill-opacity=0.15 stroke=#0047b2 stroke-width=0.5")
        ])
        values.executeResults = executeResults

        values
    }

    // Rendering Transformations

    // Process Function

    Function processFunctionProcess() {
        // tag::processFunctionProcess[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        Process process = new Process("convexhull",
                "Create a convexhull around the features",
                [features: geoscript.layer.Cursor],
                [result: geoscript.layer.Cursor],
                { inputs ->
                    def geoms = new GeometryCollection(inputs.features.collect{ f -> f.geom})
                    println geoms
                    def output = new Layer()
                    output.add([geoms.convexHull])
                    [result: output]
                }
        )
        Function function = new Function(process, new Function("parameter", new Expression("features")))
        Symbolizer symbolizer =  new Transform(function, Transform.RENDERING) + new Fill("aqua", 0.75) + new Stroke("navy", 0.5)
        places.style = symbolizer
        // end::processFunctionProcess[]
        drawOnBasemap("processFunctionProcess", [workspace.get("places"), places])
        function
    }

    Function processProcessFunction() {
        // tag::processProcessFunction[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        Process process = new Process("bounds",
                "Create a bounds around the features",
                [features: geoscript.layer.Cursor],
                [result: geoscript.layer.Cursor],
                { inputs ->
                    def geoms = new GeometryCollection(inputs.features.collect{ f -> f.geom})
                    def output = new Layer()
                    output.add([geoms.bounds.geometry])
                    [result: output]
                }
        )
        ProcessFunction processFunction = new ProcessFunction(process, new Function("parameter", new Expression("features")))
        Symbolizer symbolizer =  new Transform(processFunction, Transform.RENDERING) + new Fill("aqua", 0.75) + new Stroke("navy", 0.5)
        places.style = symbolizer
        // end::processProcessFunction[]
        drawOnBasemap("processProcessFunction", [workspace.get("places"), places])
        processFunction
    }

}
