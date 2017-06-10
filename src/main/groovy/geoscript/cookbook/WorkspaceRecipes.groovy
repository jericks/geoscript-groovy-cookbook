package geoscript.cookbook

import geoscript.feature.Field
import geoscript.feature.Schema
import geoscript.layer.Layer
import geoscript.workspace.Directory
import geoscript.workspace.Workspace

class WorkspaceRecipes extends Recipes {

    List<String> getWorkspaceNames() {
        // tag::getWorkspaceNames[]
        List<String> names = Workspace.getWorkspaceNames()
        names.each { String name ->
            println name
        }
        // end::getWorkspaceNames[]
        writeFile("workspace_get_names", "${names.collect { it }.join(NEW_LINE)}")
        names
    }

    List<String> getWorkspaceParameters() {
        // tag::getWorkspaceParameters[]
        List<Map> parameters = Workspace.getWorkspaceParameters("GeoPackage")
        parameters.each { Map param ->
            println "Parameter = ${param.key} Type = ${param.type} Required? ${param.required}"
        }
        // end::getWorkspaceParameters[]
        writeFile("workspace_get_parameters", "${parameters.collect { Map param -> "Parameter = ${param.key} Type = ${param.type} Required? ${param.required}" }.join(NEW_LINE)}")
        parameters 
    }

    Map<String,Object> createWorkspace() {

        Map<String,Object> values = [:]

        // tag::createWorkspace[]
        Workspace workspace = new Workspace()
        // end::createWorkspace[]
        values['workspace'] = workspace

        // tag::createLayer[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Layer layer = workspace.create(schema)
        println layer
        // end::createLayer[]
        writeFile("workspace_create_layer", "${layer}")
        values['layer'] = layer

        // tag::hasLayer[]
        boolean hasCities = workspace.has("cities")
        println hasCities
        // end::hasLayer[]
        writeFile("workspace_has_layer", "${hasCities}")
        values['hasCities'] = hasCities

        // tag::getLayer[]
        Layer citiesLayer = workspace.get('cities')
        println citiesLayer
        // end::getLayer[]
        writeFile("workspace_get_layer", "${citiesLayer}")
        values['citiesLayer'] = citiesLayer

        // tag::addLayer[]
        Schema statesSchema = new Schema("states", [
                new Field("geom", "Polygon", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Layer statesLayer = new Layer("states", statesSchema)
        workspace.add(statesLayer)
        println workspace.has("states")
        // end::addLayer[]
        writeFile("workspace_add_layer", "${workspace.has('states')}")
        values['hasStates'] = workspace.has('states')

        // tag::getNames[]
        List<String> names = workspace.names
        names.each { String name ->
            println name
        }
        // end::getNames[]
        writeFile("workspace_get_names", "${names.collect { it }.join(NEW_LINE)}")
        values["names"] = names

        // tag::removeLayer[]
        workspace.remove("cities")
        println workspace.has('cities')
        // end::removeLayer[]
        writeFile("workspace_remove_layer", "${workspace.has('cities')}")
        values["citiesRemoved"] = !workspace.has('cities')

        // tag::closeWorkspace[]
        workspace.close()
        // end::closeWorkspace[]

        values
    }

    Map<String,Object> createDirectoryWorkspace() {

        Map<String,Object> values = [:]

        // tag::createDirectoryWorkspace_1[]
        Directory directory = new Directory("src/main/resources/data")
        println directory.toString()
        // end::createDirectoryWorkspace_1[]
        writeFile("workspace_directory_1", "${directory.toString()}")
        values["directoryToString"] = directory.toString()

        // tag::createDirectoryWorkspace_2[]
        String format = directory.format
        println format
        // end::createDirectoryWorkspace_2[]
        writeFile("workspace_directory_2", "${format}")
        values["format"] = format

        // tag::createDirectoryWorkspace_3[]
        File file = directory.file
        println file
        // end::createDirectoryWorkspace_3[]
        writeFile("workspace_directory_3", "${file}")
        values["file"] = file

        // tag::createDirectoryWorkspace_4[]
        List names = directory.names
        names.each { String name ->
            println name
        }
        // end::createDirectoryWorkspace_4[]
        writeFile("workspace_directory_4", "${names.collect { it }.join(NEW_LINE)}")
        values["names"] = names

        // tag::createDirectoryWorkspace_5[]
        Layer layer = directory.get("states")
        int count = layer.count
        println "Layer ${layer.name} has ${count} Features."
        // end::createDirectoryWorkspace_5[]
        writeFile("workspace_directory_5", "Layer ${layer.name} has ${count} Features.")
        values["count"] = count

        // tag::createDirectoryWorkspace_6[]
        directory.close()
        // end::createDirectoryWorkspace_6[]

        values
    }

    Map<String, Workspace> getWorkspaceFromString() {
        Map<String, Workspace> values = [:]
        // tag::getWorkspaceFromString_Shapefile[]
        String connectionString = "url='states.shp' 'create spatial index'=true"
        Workspace workspace = Workspace.getWorkspace(connectionString)
        // end::getWorkspaceFromString_Shapefile[]
        values.put(connectionString, workspace)

        // tag::getWorkspaceFromString_GeoPackage[]
        connectionString = "dbtype=geopkg database=layers.gpkg"
        workspace = Workspace.getWorkspace(connectionString)
        // end::getWorkspaceFromString_GeoPackage[]
        values.put(connectionString, workspace)

        // tag::getWorkspaceFromString_Spatialite[]
        connectionString = "dbtype=spatialite database=layers.sqlite"
        workspace = Workspace.getWorkspace(connectionString)
        // end::getWorkspaceFromString_Spatialite[]
        values.put(connectionString, workspace)

        values
    }

    Map<String, Workspace> getWorkspaceFromMap() {
        Map<String, Workspace> values = [:]
        // tag::getWorkspaceFromMap_H2[]
        Map params = [dbtype: 'h2', database: 'test.db']
        Workspace workspace = Workspace.getWorkspace(params)
        // end::getWorkspaceFromMap_H2[]
        values.put('h2', workspace)

        // tag::getWorkspaceFromMap_PostGIS[]
        params = [
            dbtype: 'postgis',
            database: 'postgres',
            host: 'localhost',
            port: 5432,
            user: 'postgres',
            passwd: 'postgres'
        ]
        workspace = Workspace.getWorkspace(params)
        // end::getWorkspaceFromMap_PostGIS[]
        values.put('postgis', workspace)

        // tag::getWorkspaceFromMap_Geobuf[]
        params = [file: 'layers.pbf', precision: 6, dimension:2]
        workspace = Workspace.getWorkspace(params)
        // end::getWorkspaceFromMap_Geobuf[]
        values.put('geobuf', workspace)

        values
    }

}
