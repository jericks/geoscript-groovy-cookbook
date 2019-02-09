package geoscript.cookbook

import geoscript.feature.Field
import geoscript.feature.Schema
import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.layer.Layer
import geoscript.workspace.Directory
import geoscript.workspace.GeoPackage
import geoscript.workspace.Geobuf
import geoscript.workspace.H2
import geoscript.workspace.Memory
import geoscript.workspace.MySQL
import geoscript.workspace.PostGIS
import geoscript.workspace.Property
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

    Layer createMemoryWorkspace() {
        // tag::createMemoryWorkspace[]
        Workspace workspace = new Workspace()

        Layer layer = workspace.create("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        println layer

        workspace.remove(layer)
        println workspace.has(layer.name)

        // end::createMemoryWorkspace[]
        writeFile("workspace_create_memory", "${layer}${NEW_LINE}${workspace.has('cities')}")
        layer
    }

    Layer workspaceAddLayerNameChunk() {
        // tag::workspaceAddLayerNameChunk[]
        Workspace workspace = new Memory()
        Layer layer = workspace.create("points", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer")
        ])
        Bounds bounds = new Bounds(-180,-90, 180,90, "EPSG:4326")
        Geometry.createRandomPoints(bounds.geometry, 500).geometries.eachWithIndex { Geometry geom, int i ->
            layer.add([geom: geom, id: i])
        }
        println "Original Layer has ${layer.count} features."

        Layer copyOfLayer = workspace.add(layer, "random points", 100)
        println "Copied Layer has ${copyOfLayer.count} features."
        // end::workspaceAddLayerNameChunk[]
        writeFile("workspaceAddLayerNameChunk", "Original Layer has ${layer.count} features.${NEW_LINE}Copied Layer has ${copyOfLayer.count} features.")
        drawOnBasemap("workspaceAddLayerNameChunk", [copyOfLayer], bounds)
        copyOfLayer
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

        // tag::getWorkspaceFromString_H2[]
        connectionString = "dbtype=h2 database=layers.db"
        workspace = Workspace.getWorkspace(connectionString)
        // end::getWorkspaceFromString_H2[]
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

    Map<String,Object> withWorkspaceFromString() {
        Map<String,Object> values = [:]
        // tag::withWorkspaceFromString[]
        Workspace.withWorkspace("dbtype=geopkg database=src/main/resources/data.gpkg") { Workspace workspace ->
            println workspace.format
            println "----------"
            workspace.names.each { String name ->
                println "${name} (${workspace.get(name).count})"
            }
        }
        // end::withWorkspaceFromString[]
        Workspace.withWorkspace("dbtype=geopkg database=src/main/resources/data.gpkg") { Workspace workspace ->
            writeFile("workspace_with_string", "${workspace.format}${NEW_LINE}---------${NEW_LINE}${workspace.names.collect { "${it} (${workspace.get(it).count})" }.join(NEW_LINE)}")
            values.format = workspace.format
            values.names = workspace.names
        }
        values
    }

    Map<String,Object> withWorkspaceFromMap() {
        Map<String,Object> values = [:]
        // tag::withWorkspaceFromMap[]
        Workspace.withWorkspace([dbtype: 'geopkg', database: 'src/main/resources/data.gpkg']) { Workspace workspace ->
            println workspace.format
            println "----------"
            workspace.names.each { String name ->
                println "${name} (${workspace.get(name).count})"
            }
        }
        // end::withWorkspaceFromMap[]
        Workspace.withWorkspace([dbtype: 'geopkg', database: 'src/main/resources/data.gpkg']) { Workspace workspace ->
            writeFile("workspace_with_map", "${workspace.format}${NEW_LINE}---------${NEW_LINE}${workspace.names.collect { "${it} (${workspace.get(it).count})" }.join(NEW_LINE)}")
            values.format = workspace.format
            values.names = workspace.names
        }
        values
    }


    Workspace createDirectoryWorkspaceFromName() {
        // tag::createDirectoryWorkspaceFromName[]
        Workspace workspace = new Directory("src/main/resources/shapefiles")
        println workspace.format
        println "---------"
        workspace.names.each { String name ->
            println "${name} (${workspace.get(name).count})"
        }
        // end::createDirectoryWorkspaceFromName[]
        writeFile("workspace_directory_name", "${workspace.format}${NEW_LINE}---------${NEW_LINE}${workspace.names.collect { "${it} (${workspace.get(it).count})" }.join(NEW_LINE)}")
        workspace
    }

    Workspace createDirectoryWorkspaceFromFile() {
        // tag::createDirectoryWorkspaceFromFile[]
        Workspace workspace = new Directory(new File("src/main/resources/shapefiles"))
        println workspace.format
        println "---------"
        workspace.names.each { String name ->
            println "${name} (${workspace.get(name).count})"
        }
        // end::createDirectoryWorkspaceFromFile[]
        writeFile("workspace_directory_file", "${workspace.format}${NEW_LINE}---------${NEW_LINE}${workspace.names.collect { "${it} (${workspace.get(it).count})" }.join(NEW_LINE)}")
        workspace
    }

    Workspace createDirectoryWorkspaceFromUrl() {
        // tag::createDirectoryWorkspaceFromUrl[]
        Directory directory = Directory.fromURL(
            new URL("http://www.naturalearthdata.com/http//www.naturalearthdata.com/download/110m/cultural/ne_110m_admin_0_countries.zip"),
            new File("naturalearth")
        )
        println directory.format
        println "---------"
        directory.names.each { String name ->
            println "${name} (${directory.get(name).count})"
        }
        // end::createDirectoryWorkspaceFromUrl[]
        writeFile("workspace_directory_url", "${directory.format}${NEW_LINE}---------${NEW_LINE}${directory.names.collect { "${it} (${directory.get(it).count})" }.join(NEW_LINE)}")
        directory
    }

    Workspace createGeoPackageWorkspaceFromFile() {
        // tag::createGeoPackageWorkspaceFromFile[]
        Workspace workspace = new GeoPackage(new File("src/main/resources/data.gpkg"))
        println workspace.format
        println "----------"
        workspace.names.each { String name ->
            println "${name} (${workspace.get(name).count})"
        }
        // end::createGeoPackageWorkspaceFromFile[]
        writeFile("workspace_geopackage_file", "${workspace.format}${NEW_LINE}----------${NEW_LINE}${workspace.names.collect { "${it} (${workspace.get(it).count})" }.join(NEW_LINE)}")
        workspace
    }

    Workspace createGeoPackageWorkspaceFromName() {
        // tag::createGeoPackageWorkspaceFromName[]
        Workspace workspace = new GeoPackage("src/main/resources/data.gpkg")
        println workspace.format
        println "----------"
        workspace.names.each { String name ->
            println "${name} (${workspace.get(name).count})"
        }
        // end::createGeoPackageWorkspaceFromName[]
        writeFile("workspace_geopackage_name", "${workspace.format}${NEW_LINE}----------${NEW_LINE}${workspace.names.collect { "${it} (${workspace.get(it).count})" }.join(NEW_LINE)}")
        workspace
    }

    // H2

    Workspace createH2WorkspaceFromFile() {
        // tag::createH2WorkspaceFromFile[]
        Workspace workspace = new H2(new File("src/main/resources/h2/data.db"))
        println workspace.format
        println "--"
        workspace.names.each { String name ->
            println "${name} (${workspace.get(name).count})"
        }
        // end::createH2WorkspaceFromFile[]
        writeFile("workspace_h2_file", "${workspace.format}${NEW_LINE}--${NEW_LINE}${workspace.names.collect { "${it} (${workspace.get(it).count})" }.join(NEW_LINE)}")
        workspace
    }

    Workspace createH2Workspace() {
        // tag::createH2Workspace[]
        H2 h2 = new H2(
                "database",    // <1>
                "localhost",   // <2>
                "5421",        // <3>
                "geo",         // <4>
                "user",        // <5>
                "password"     // <6>
        )
        // end::createH2Workspace[]
        h2
    }

    Workspace createH2WorkspaceWithNamedParameters() {
        // tag::createH2WorkspaceWithNamedParameters[]
        H2 h2 = new H2("database",
                "host": "localhost",
                "port": "5412",
                "schema": "geo",
                "user": "user",
                "password": "secret"
        )
        // end::createH2WorkspaceWithNamedParameters[]
        h2
    }

    // Geobuf

    Workspace createGeobufWorkspaceFromFile() {
        // tag::createGeobufWorkspaceFromFile[]
        Workspace workspace = new Geobuf(new File("src/main/resources/geobuf"))
        println workspace.format
        println "------"
        workspace.names.each { String name ->
            println "${name} (${workspace.get(name).count})"
        }
        // end::createGeobufWorkspaceFromFile[]
        writeFile("workspace_geobuf_file", "${workspace.format}${NEW_LINE}------${NEW_LINE}${workspace.names.collect { "${it} (${workspace.get(it).count})" }.join(NEW_LINE)}")
        workspace
    }

    // Property

    Workspace createPropertyWorkspaceFromFile() {
        // tag::createPropertyWorkspaceFromFile[]
        Workspace workspace = new Property(new File("src/main/resources/property"))
        println workspace.format
        println "--------"
        workspace.names.each { String name ->
            println "${name} (${workspace.get(name).count})"
        }
        // end::createPropertyWorkspaceFromFile[]
        writeFile("workspace_property_file", "${workspace.format}${NEW_LINE}------${NEW_LINE}${workspace.names.collect { "${it} (${workspace.get(it).count})" }.join(NEW_LINE)}")
        workspace
    }

    // PostGIS

    Workspace createPostGISWorkspace() {
        // tag::createPostGISWorkspace[]
        PostGIS postgis = new PostGIS(
                "database",    // <1>
                "localhost",   // <2>
                "5432",        // <3>
                "public",      // <4>
                "user",        // <5>
                "password"     // <6>
        )
        // end::createPostGISWorkspace[]
        postgis
    }

    Workspace createPostGISWorkspaceWithParameters() {
        // tag::createPostGISWorkspaceWithParameters[]
        PostGIS postgis = new PostGIS(
                "database",                   // <1>
                "localhost",                  // <2>
                "5432",                       // <3>
                "public",                     // <4>
                "user",                       // <5>
                "password",                   // <6>
                true,                         // <7>
                true,                         // <8>
                "OWNER geo TABLESPACE points" // <9>
        )
        // end::createPostGISWorkspaceWithParameters[]
        postgis
    }

    Workspace createPostGISWorkspaceWithNamedParameters() {
        // tag::createPostGISWorkspaceWithNamedParameters[]
        PostGIS postgis = new PostGIS("database",
                "host": "localhost",
                "port": "5432",
                "schema": "public",
                "user": "user",
                "password": "secret",
                "estimatedExtent": false,
                "createDatabase": false,
                "createDatabaseParams": "OWNER geo TABLESPACE points"
        )
        // end::createPostGISWorkspaceWithNamedParameters[]
        postgis
    }

    // PostGIS
    void postGisDeleteDatabase() {
        // tag::postGisDeleteDatabase[]
        PostGIS.deleteDatabase(
                "database",    // <1>
                "localhost",   // <2>
                "5432",        // <3>
                "user",        // <4>
                "password"     // <5>
        )
        // end::postGisDeleteDatabase[]
    }

    void postGisDeleteDatabaseWithNamedParameters() {
        // tag::postGisDeleteDatabaseWithNamedParameters[]
        PostGIS.deleteDatabase("database",
                "host": "localhost",
                "port": "5432",
                "user": "user",
                "password": "secret"
        )
        // end::postGisDeleteDatabaseWithNamedParameters[]
    }

    // MySQL

    Workspace createMySQLWorkspace() {
        // tag::createMySQLWorkspace[]
        MySQL mysql = new MySQL(
                "database",    // <1>
                "localhost",   // <2>
                "3306",        // <3>
                "user",        // <4>
                "password"     // <5>
        )
        // end::createMySQLWorkspace[]
        mysql
    }

    Workspace createMySQLWorkspaceWithNamedParameters() {
        // tag::createMySQLWorkspaceWithNamedParameters[]
        MySQL mysql = new MySQL("database",
                "host": "localhost",
                "port": "3306",
                "user": "user",
                "password": "secret"
        )
        // end::createMySQLWorkspaceWithNamedParameters[]
        mysql
    }
}
