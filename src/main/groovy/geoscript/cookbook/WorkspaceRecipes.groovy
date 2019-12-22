package geoscript.cookbook

import geoscript.feature.Field
import geoscript.feature.Schema
import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.Polygon
import geoscript.layer.Layer
import geoscript.style.Shape
import geoscript.workspace.Database
import geoscript.workspace.Directory
import geoscript.workspace.FlatGeobuf
import geoscript.workspace.GeoPackage
import geoscript.workspace.Geobuf
import geoscript.workspace.H2
import geoscript.workspace.Memory
import geoscript.workspace.MySQL
import geoscript.workspace.OGR
import geoscript.workspace.PostGIS
import geoscript.workspace.Property
import geoscript.workspace.SpatiaLite
import geoscript.workspace.Sqlite
import geoscript.workspace.WFS
import geoscript.workspace.Workspace
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

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

    // Flatgeobuf

    Workspace createFlatgeobufWorkspaceFromFile() {
        // tag::createFlatgeobufWorkspaceFromFile[]
        Workspace workspace = new FlatGeobuf(new File("src/main/resources/flatgeobuf"))
        println workspace.format
        println "------"
        workspace.names.each { String name ->
            println "${name} (${workspace.get(name).count})"
        }
        // end::createFlatgeobufWorkspaceFromFile[]
        writeFile("workspace_flatgeobuf_file", "${workspace.format}${NEW_LINE}------${NEW_LINE}${workspace.names.collect { "${it} (${workspace.get(it).count})" }.join(NEW_LINE)}")
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

    // SQLite

    Workspace createSqliteWorkspaceFromFile() {
        // tag::createSqliteWorkspaceFromFile[]
        Workspace workspace = new Sqlite(new File("src/main/resources/data.sqlite"))
        println workspace.format
        println "--------"
        workspace.names.each { String name ->
            println "${name} (${workspace.get(name).count})"
        }
        // end::createSqliteWorkspaceFromFile[]
        writeFile("workspace_sqlite_file", "${workspace.format}${NEW_LINE}------${NEW_LINE}${workspace.names.collect { "${it} (${workspace.get(it).count})" }.join(NEW_LINE)}")
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

    // Spatialite

    Workspace createSpatiaLiteWorkspace() {
        // tag::createSpatiaLiteWorkspace[]
        SpatiaLite spatialite = new SpatiaLite("db.sqlite")
        // end::createSpatiaLiteWorkspace[]
        spatialite
    }

    Workspace createSpatiaLiteWorkspaceFile() {
        // tag::createSpatiaLiteWorkspaceFile[]
        File directory = new File("databases")
        File file = new File("db.sqlite")
        SpatiaLite spatialite = new SpatiaLite(file)
        // end::createSpatiaLiteWorkspaceFile[]
        spatialite
    }

    // WFS

    Workspace createWFSWorkspace() {
        // tag::createWFSWorkspace[]
        WFS wfs = new WFS("http://localhost:8080/geoserver/ows?service=wfs&version=1.1.0&request=GetCapabilities")
        // end::createWFSWorkspace[]
        wfs
    }

    // OGR

    boolean ogrIsAvailable() {
        // tag::ogrIsAvailable[]
        boolean isAvailable = OGR.isAvailable()
        // end::ogrIsAvailable[]
        isAvailable
    }

    Set<String> ogrDrivers() {
        // tag::ogrDrivers[]
        Set<String> drivers = OGR.drivers
        // end::ogrDrivers[]
        drivers
    }

    Workspace createOGRShapefileWorkspace() {
        // tag::createOGRShapefileWorkspace[]
        File file = new File("states.shp")
        OGR ogr = new OGR("ESRI Shapefile", file.absolutePath)
        // end::createOGRShapefileWorkspace[]
        ogr
    }

    Workspace createOGRSqliteWorkspace() {
        // tag::createOGRSqliteWorkspace[]
        File file = new File("states.sqlite")
        OGR ogr = new OGR("SQLite", file.absolutePath)
        // end::createOGRSqliteWorkspace[]
        ogr
    }

    Workspace createOGRGeoJsonWorkspace() {
        // tag::createOGRGeoJsonWorkspace[]
        File file = new File("states.json")
        OGR ogr = new OGR("GeoJSON", file.absolutePath)
        // end::createOGRGeoJsonWorkspace[]
        ogr
    }

    // Database

    Map<String,Object> databaseGetSql() {
        Map<String,Object> values = [:]
        // tag::databaseGetSql[]
        Database workspace = new H2(new File("src/main/resources/h2/data.db"))
        Sql sql = workspace.sql
        // end::databaseGetSql[]

        // tag::databaseGetSql_count[]
        int numberOfPlaces = sql.firstRow("SELECT COUNT(*) as count FROM \"places\"").get("count") as int
        println "# of Places = ${numberOfPlaces}"
        // end::databaseGetSql_count[]
        values.numberOfPlaces = numberOfPlaces
        writeFile("workspace_database_getsql_count", "# of Places = ${numberOfPlaces}")

        // tag::databaseGetSql_stats[]
        GroovyRowResult result = sql.firstRow("SELECT MIN(ELEVATION) as min_elev, MAX(ELEVATION) as max_elev, AVG(ELEVATION) as avg_elev FROM \"places\"")
        println "Mininum Elevation = ${result.get('min_elev')}"
        println "Maximum Elevation = ${result.get('max_elev')}"
        println "Average Elevation = ${result.get('avg_elev')}"
        // end::databaseGetSql_stats[]
        values.minElevation = result.get('min_elev')
        values.maxElevation = result.get('max_elev')
        values.avgElevation = result.get('avg_elev')
        writeFile("workspace_database_getsql_stats", """Mininum Elevation = ${result.get('min_elev')}
Maximum Elevation = ${result.get('max_elev')}
Average Elevation = ${result.get('avg_elev')} 
""")

        // tag::databaseGetSql_select[]
        List<String> names = []
        sql.eachRow "SELECT TOP 10 \"NAME\" FROM \"places\" ORDER BY \"NAME\" DESC ", {
            names.add(it["NAME"])
        }
        names.each { String name ->
            println name
        }
        // end::databaseGetSql_select[]
        values.names = names
        writeFile("workspace_database_getsql_select", " ${names.collect { it }.join(NEW_LINE)}")

        // tag::databaseGetSql_spatial[]
        Workspace memory = new Memory()
        Layer layer = memory.create("places_polys", [new Field("buffer", "Polygon"), new Field("name", "String")])
        sql.eachRow "SELECT ST_Buffer(\"the_geom\", 10) as buffer, \"NAME\" as name FROM \"places\"", {row ->
            Geometry poly = Geometry.fromWKB(row.buffer as byte[])
            layer.add([buffer: poly, name: row.NAME])
        }
        // end::databaseGetSql_spatial[]
        drawOnBasemap("workspace_database_getsql", [layer, workspace.get("places")])
        values.layer = layer

        values
    }

    Map<String,Object> databaseView() {
        Map<String,Object> values = [:]
        // tag::databaseView_create[]
        Database workspace = new H2(new File("src/main/resources/h2/data.db"))
        Layer layer = workspace.createView(
                "megacities",                                             // <1>
                "SELECT * FROM \"places\" WHERE \"MEGACITY\" = '%mega%'", // <2>
                new Field("the_geom","Point","EPSG:4326"),                // <3>
                params: [['mega', '1']]                                   // <4>
        )
        boolean hasLayer1 = workspace.has("megacities")
        println "Does layer exist? ${hasLayer1}"
        // end::databaseView_create[]
        values.count = layer.count
        values.hasLayer1 = hasLayer1
        layer.style = new Shape("blue", 6, "star")
        drawOnBasemap("workspace_database_view", [workspace.get("places"), layer])
        writeFile("workspace_database_view_create", "Does layer exist? ${hasLayer1}")

        // tag::databaseView_delete[]
        workspace.deleteView("megacities")
        boolean hasLayer2 = workspace.has("megacities")
        println "Does layer exist? ${hasLayer2}"
        // end::databaseView_delete[]
        values.hasLayer2 = hasLayer2
        writeFile("workspace_database_view_delete", "Does layer exist? ${hasLayer2}")
        values
    }

    Map<String,Object> databaseIndex() {
        Map<String, Object> values = [:]

        // tag::databaseIndex_create[]
        Database workspace = new H2(new File("src/main/resources/h2/data.db"))
        workspace.createIndex("places","name_idx","NAME",true)
        workspace.createIndex("places","megacity_idx","MEGACITY",false)
        workspace.createIndex("places","a3_idx", ["SOV_A3", "ADM0_A3"],false)
        // end::databaseIndex_create[]
        values.nameIndexCreated = workspace.getIndexes("places").find { it.name == "name_idx"} != null
        values.megacityIndexCreated = workspace.getIndexes("places").find { it.name == "megacity_idx"} != null
        values.a3IndexCreated = workspace.getIndexes("places").find { it.name == "a3_idx"} != null

        // tag::databaseIndex_get[]
        List<Map> indexes = workspace.getIndexes("places")
        indexes.each { Map index ->
            println "Index name = ${index.name}, unique = ${index.unique}, attributes = ${index.attributes}"
        }
        // end::databaseIndex_get[]
        writeFile("workspace_database_index", indexes.collect { Map index -> "Index name = ${index.name}, unique = ${index.unique}, attributes = ${index.attributes}" }.join(NEW_LINE))

        // tag::databaseIndex_delete[]
        workspace.deleteIndex("places", "name_idx")
        workspace.deleteIndex("places", "megacity_idx")
        workspace.deleteIndex("places", "a3_idx")
        // end::databaseIndex_delete[]
        values.nameIndexDeleted = workspace.getIndexes("places").find { it.name == "name_idx"} == null
        values.megacityIndexDeleted = workspace.getIndexes("places").find { it.name == "megacity_idx"} == null
        values.a3IndexDeleted = workspace.getIndexes("places").find { it.name == "a3_idx"} == null

        values
    }

}
