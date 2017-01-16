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

    Schema getSchemaProperties() {
        // tag::getSchemaProperties_name[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ], "https://github.com/jericks/geoscript-groovy-cookbook")
        String name = schema.name
        println name
        // end::getSchemaProperties_name[]
        writeFile("schema_properties_name", "${name}")

        // tag::getSchemaProperties_geom[]
        Field geomField = schema.geom
        println geomField
        // end::getSchemaProperties_geom[]
        writeFile("schema_properties_geom", "${geomField}")

        // tag::getSchemaProperties_proj[]
        Projection proj = schema.proj
        println proj
        // end::getSchemaProperties_proj[]
        writeFile("schema_properties_proj", "${proj}")

        // tag::getSchemaProperties_uri[]
        String uri = schema.uri
        println uri
        // end::getSchemaProperties_uri[]
        writeFile("schema_properties_uri", "${uri}")

        schema
    }

    Map<String, Object> getSchemaFields() {
        Map<String, Object> results = [:]

        // tag::getSchemaFields_fields[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        List<Field> fields = schema.fields
        fields.each { Field field ->
            println field
        }
        // end::getSchemaFields_fields[]
        results.fields = fields
        writeFile("schema_fields_fields", "${fields.join(NEW_LINE)}")

        // tag::getSchemaFields_field_name[]
        Field nameField = schema.field("name")
        println nameField
        // end::getSchemaFields_field_name[]
        results.nameField = nameField
        writeFile("schema_fields_field", "${nameField}")

        // tag::getSchemaFields_get[]
        Field idField = schema.get("id")
        println idField
        // end::getSchemaFields_get[]
        results.idField = idField
        writeFile("schema_fields_get", "${idField}")

        // tag::getSchemaFields_has[]
        boolean hasArea = schema.has("area")
        println "Has area Field? ${hasArea}"

        boolean hasGeom = schema.has("geom")
        println "Has geom Field? ${hasGeom}"
        // end::getSchemaFields_has[]
        results.hasArea = hasArea
        results.hasGeom = hasGeom
        writeFile("schema_fields_has", "${hasArea}${NEW_LINE}${hasGeom}")

        results
    }

}