package geoscript.cookbook

import geoscript.feature.Field
import geoscript.feature.Schema
import geoscript.proj.Projection

class FeatureRecipes extends Recipes {

    Field createField() {
        // tag::createField[]
        Field field = new Field("name", "String")
        println field
        // end::createField[]
        writeFile("feature_field_create", "${field}")
        field
    }

    Field createGeometryField() {
        // tag::createGeometryField[]
        Field field = new Field("geom", "Point", "EPSG:4326")
        println field
        // end::createGeometryField[]
        writeFile("feature_field_create_geometry", "${field}")
        field
    }

    Field createFieldFromList() {
        // tag::createFieldFromList[]
        Field field = new Field(["geom", "Polygon", "EPSG:4326"])
        println field
        // end::createFieldFromList[]
        writeFile("feature_field_create_list", "${field}")
        field
    }

    Field createFieldFromMap() {
        // tag::createFieldFromMap[]
        Field field = new Field([
                "name": "geom",
                "type": "LineString",
                "proj": new Projection("EPSG:4326")
        ])
        println field
        // end::createFieldFromMap[]
        writeFile("feature_field_create_map", "${field}")
        field
    }

    Field getFieldProperties() {
        // tag::getFieldProperties[]
        Field field = new Field("geom", "Point", "EPSG:4326")
        println "Name = ${field.name}"
        println "Type = ${field.typ}"
        println "Projection = ${field.proj}"
        println "Is Geometry = ${field.geometry}"
        // end::getFieldProperties[]
        writeFile("feature_field_properties", """Name = ${field.name}
Type = ${field.typ}
Projection = "${field.proj}
Is Geometry = ${field.geometry}
""")
        field
    }

    Schema createSchemaFromFields() {
        // tag::createSchemaFromFields[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        println schema
        // end::createSchemaFromFields[]
        writeFile("schema_create_fields", "${schema}")
        schema
    }

    Schema createSchemaFromLists() {
        // tag::createSchemaFromLists[]
        Schema schema = new Schema("cities", [
                ["geom", "Point", "EPSG:4326"],
                ["id", "Integer"],
                ["name", "String"]
        ])
        println schema
        // end::createSchemaFromLists[]
        writeFile("schema_create_lists", "${schema}")
        schema
    }

    Schema createSchemaFromMaps() {
        // tag::createSchemaFromMaps[]
        Schema schema = new Schema("cities", [
                [name: "geom", type: "Point", proj: "EPSG:4326"],
                [name: "id",   type: "Integer"],
                [name: "name", type: "String"]
        ])
        println schema
        // end::createSchemaFromMaps[]
        writeFile("schema_create_maps", "${schema}")
        schema
    }

    Schema createSchemaFromString() {
        // tag::createSchemaFromString[]
        Schema schema = new Schema("cities", "geom:Point:srid=4326,id:Integer,name:String")
        println schema
        // end::createSchemaFromString[]
        writeFile("schema_create_string", "${schema}")
        schema
    }

}