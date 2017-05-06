package geoscript.cookbook

import geoscript.feature.Field
import geoscript.feature.Schema
import geoscript.layer.Layer
import geoscript.workspace.Workspace

class WorkspaceRecipes extends Recipes {

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

}
