package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.feature.Field
import geoscript.feature.Schema
import geoscript.feature.io.GeoJSONReader
import geoscript.feature.io.GeoJSONWriter
import geoscript.feature.io.GeobufReader
import geoscript.feature.io.GeobufWriter
import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.Point
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

        // tag::getSchemaProperties_spec[]
        String spec = schema.spec
        println spec
        // end::getSchemaProperties_spec[]
        writeFile("schema_properties_spec", "${spec}")

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

    Feature createFeatureFromSchemaMap() {
        // tag::createFeatureFromSchemaMap[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature = schema.feature([
                id: 1,
                name: 'Seattle',
                geom: new Point( -122.3204, 47.6024)
        ], "city.1")
        println feature
        // end::createFeatureFromSchemaMap[]
        writeFile("schema_create_feature_map","${feature}")
        feature
    }

    Feature createFeatureFromSchemaList() {
        // tag::createFeatureFromSchemaList[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature = schema.feature([
                new Point( -122.3204, 47.6024),
                1,
                'Seattle'
        ], "city.1")
        println feature
        // end::createFeatureFromSchemaList[]
        writeFile("schema_create_feature_list","${feature}")
        feature
    }

    Feature createFeatureFromSchemaFeature() {
        // tag::createFeatureFromSchemaFeature[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature1 = new Feature([
                id: 1,
                name: 'Seattle',
                geom: new Point( -122.3204, 47.6024)
        ], "city.1", schema)
        println feature1
        Feature feature2 = schema.feature(feature1)
        println feature2
        // end::createFeatureFromSchemaFeature[]
        writeFile("schema_create_feature_feature","${feature1}${NEW_LINE}${feature2}")
        feature2
    }

    Feature createFeatureFromSchemaEmpty() {
        // tag::createFeatureFromSchemaEmpty[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature = schema.feature()
        println feature
        // end::createFeatureFromSchemaEmpty[]
        writeFile("schema_create_feature_empty","${feature}")
        feature
    }

    Schema reprojectSchema() {
        // tag::reprojectSchema[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Schema reprojectedSchema = schema.reproject("EPSG:2927", "cities_spws")
        // end::reprojectSchema[]
        writeFile("schema_reproject","${reprojectedSchema}")
        reprojectedSchema
    }

    Schema changeGeometryTypeSchema() {
        // tag::changeGeometryTypeSchema[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Schema polyognSchema = schema.changeGeometryType("Polygon", "cities_buffer")
        // end::changeGeometryTypeSchema[]
        writeFile("schema_changegeometrytype","${polyognSchema}")
        polyognSchema
    }

    Schema changeFieldSchema() {
        // tag::changeFieldSchema[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Schema guidSchema = schema.changeField(schema.field('id'), new Field('guid','String'), 'cities_guid')
        // end::changeFieldSchema[]
        writeFile("schema_changefield","${guidSchema}")
        guidSchema
    }

    Schema changeFieldsSchema() {
        // tag::changeFieldsSchema[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Schema updatedSchema = schema.changeFields(
                [
                    (schema.field('id'))   : new Field('guid','String'),
                    (schema.field('name')) : new Field('description','String')
                ], 'cities_updated')
        // end::changeFieldsSchema[]
        writeFile("schema_changefields","${updatedSchema}")
        updatedSchema
    }

    Schema addFieldSchema() {
        // tag::addFieldSchema[]
        Schema schema = new Schema("countries", [
                new Field("geom", "Polygon", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Schema updatedSchema = schema.addField(new Field("area", "Double"), "countries_area")
        // end::addFieldSchema[]
        writeFile("schema_addfield","${updatedSchema}")
        updatedSchema
    }

    Schema addFieldsSchema() {
        // tag::addFieldsSchema[]
        Schema schema = new Schema("countries", [
                new Field("geom", "Polygon", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Schema updatedSchema = schema.addFields([
                new Field("area", "Double"),
                new Field("perimeter", "Double"),
        ], "countries_areaperimeter")
        // end::addFieldsSchema[]
        writeFile("schema_addfields","${updatedSchema}")
        updatedSchema
    }

    Schema removeFieldSchema() {
        // tag::removeFieldSchema[]
        Schema schema = new Schema("countries", [
                new Field("geom", "Polygon", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String"),
                new Field("area", "Double")
        ])
        Schema updatedSchema = schema.removeField(schema.field("area"), "countries_updated")
        // end::removeFieldSchema[]
        writeFile("schema_removefield","${updatedSchema}")
        updatedSchema
    }

    Schema removeFieldsSchema() {
        // tag::removeFieldsSchema[]
        Schema schema = new Schema("countries", [
                new Field("geom", "Polygon", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String"),
                new Field("area", "Double")
        ])
        Schema updatedSchema = schema.removeFields([
                schema.field("area"),
                schema.field("name")
        ], "countries_updated")
        // end::removeFieldsSchema[]
        writeFile("schema_removefields","${updatedSchema}")
        updatedSchema
    }

    Schema includeFieldsSchema() {
        // tag::includeFieldsSchema[]
        Schema schema = new Schema("countries", [
                new Field("geom", "Polygon", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String"),
                new Field("area", "Double")
        ])
        Schema updatedSchema = schema.includeFields([
                schema.field("geom"),
                schema.field("name")
        ], "countries_updated")
        // end::includeFieldsSchema[]
        writeFile("schema_includefields","${updatedSchema}")
        updatedSchema
    }

    Schema addSchemaNoPrefixNoDuplicates() {
        // tag::addSchemaNoPrefixNoDuplicates_1[]
        Schema shopSchema = new Schema("shops", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])

        Schema cafeSchema = new Schema("cafes", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String"),
                new Field("address", "String")
        ])

        Map result = shopSchema.addSchema(cafeSchema, "business")

        Schema combinedSchema = result.schema
        println combinedSchema
        // end::addSchemaNoPrefixNoDuplicates_1[]
        writeFile("schema_addschema_noprefix_noduplicates_1", "${combinedSchema}")

        // tag::addSchemaNoPrefixNoDuplicates_2[]
        Map<String,String> shopSchemaFieldMapping = result.fields[0]
        println shopSchemaFieldMapping
        // end::addSchemaNoPrefixNoDuplicates_2[]
        writeFile("schema_addschema_noprefix_noduplicates_2", "${shopSchemaFieldMapping}")

        // tag::addSchemaNoPrefixNoDuplicates_3[]
        Map<String,String> cafeSchemaSchemaFieldMapping = result.fields[1]
        println cafeSchemaSchemaFieldMapping
        // end::addSchemaNoPrefixNoDuplicates_3[]
        writeFile("schema_addschema_noprefix_noduplicates_3", "${cafeSchemaSchemaFieldMapping}")

        combinedSchema
    }

    Schema addSchemaPostFixAllNoDuplicates() {
        // tag::addSchemaPostFixAllNoDuplicates_1[]
        Schema shopSchema = new Schema("shops", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])

        Schema cafeSchema = new Schema("cafes", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String"),
                new Field("address", "String")
        ])

        Map result = shopSchema.addSchema(cafeSchema, "business", postfixAll: true, includeDuplicates: false)

        Schema combinedSchema = result.schema
        println combinedSchema
        // end::addSchemaPostFixAllNoDuplicates_1[]
        writeFile("schema_addschema_postfixall_noduplicates_1", "${combinedSchema}")

        // tag::addSchemaPostFixAllNoDuplicates_2[]
        Map<String,String> shopSchemaFieldMapping = result.fields[0]
        println shopSchemaFieldMapping
        // end::addSchemaPostFixAllNoDuplicates_2[]
        writeFile("schema_addschema_postfixall_noduplicates_2", "${shopSchemaFieldMapping}")

        // tag::addSchemaPostFixAllNoDuplicates_3[]
        Map<String,String> cafeSchemaSchemaFieldMapping = result.fields[1]
        println cafeSchemaSchemaFieldMapping
        // end::addSchemaPostFixAllNoDuplicates_3[]
        writeFile("schema_addschema_postfixall_noduplicates_3", "${cafeSchemaSchemaFieldMapping}")

        combinedSchema

    }

    Feature createFeatureFromMap() {
      // tag::createFeatureFromMap[]
      Feature feature = new Feature([
        id: 1,
        name: "Seattle",
        geom: new Point(-122.3204, 47.6024)
      ], "city.1")
      println feature
      // end::createFeatureFromMap[]
      writeFile("feature_create_map", "${feature}")
      feature
    }

    Feature createFeatureFromMapWithSchema() {
        // tag::createFeatureFromMapWithSchema[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature = new Feature([
                id: 1,
                name: "Seattle",
                geom: new Point(-122.3204, 47.6024)
        ], "city.1", schema)
        println feature
        // end::createFeatureFromMapWithSchema[]
        writeFile("feature_create_map_schema", "${feature}")
        feature
    }

    Feature createFeatureFromListWithSchema() {
        // tag::createFeatureFromListWithSchema[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature = new Feature([
                new Point(-122.3204, 47.6024),
                1,
                "Seattle"
        ], "city.1", schema)
        println feature
        // end::createFeatureFromListWithSchema[]
        writeFile("feature_create_list_schema", "${feature}")
        feature
    }

    Feature getFeatureProperties() {
        // tag::getFeatureProperties_1[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature = new Feature([
                new Point(-122.3204, 47.6024),
                1,
                "Seattle"
        ], "city.1", schema)

        String id = feature.id
        println id
        // end::getFeatureProperties_1[]
        writeFile("feature_properties_1", "${id}")

        // tag::getFeatureProperties_2[]
        Geometry geometry = feature.geom
        println geometry
        // end::getFeatureProperties_2[]
        writeFile("feature_properties_2", "${geometry}")

        // tag::getFeatureProperties_3[]
        Bounds bounds = feature.bounds
        println bounds
        // end::getFeatureProperties_3[]
        writeFile("feature_properties_3", "${bounds}")

        // tag::getFeatureProperties_4[]
        Map attributes = feature.attributes
        println attributes
        // end::getFeatureProperties_4[]
        writeFile("feature_properties_4", "${attributes}")

        feature
    }

    Feature getAndSetFeatureAttributes() {
        // tag::getAndSetFeatureAttributes_1[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature = new Feature([
                new Point(-122.3204, 47.6024),
                1,
                "Seattle"
        ], "city.1", schema)

        int id = feature.get("id")
        println id
        // end::getAndSetFeatureAttributes_1[]
        writeFile("feature_attribute_1", "${id}")

        // tag::getAndSetFeatureAttributes_2[]
        String name = feature.get(schema.field("name"))
        println name
        // end::getAndSetFeatureAttributes_2[]
        writeFile("feature_attribute_2", "${name}")

        // tag::getAndSetFeatureAttributes_3[]
        feature.set("name", "Tacoma")
        println feature["name"]
        // end::getAndSetFeatureAttributes_3[]
        writeFile("feature_attribute_3", "${feature['name']}")

        // tag::getAndSetFeatureAttributes_4[]
        feature.set(schema.field("name"), "Mercer Island")
        println feature["name"]
        // end::getAndSetFeatureAttributes_4[]
        writeFile("feature_attribute_4", "${feature['name']}")

        // tag::getAndSetFeatureAttributes_5[]
        feature.set([id: 2])
        println feature["id"]
        // end::getAndSetFeatureAttributes_5[]
        writeFile("feature_attribute_5", "${feature['id']}")

        // tag::getAndSetFeatureAttributes_6[]
        feature.geom = new Point(-122.2220, 47.5673)
        println feature.geom
        // end::getAndSetFeatureAttributes_6[]
        writeFile("feature_attribute_6", "${feature.geom}")

        feature
    }

    String featureGetGeoJson() {
        // tag::featureGetGeoJson[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature = new Feature([
                new Point(-122.3204, 47.6024),
                1,
                "Seattle"
        ], "city.1", schema)

        String geojson = feature.geoJSON
        println geojson
        // end::featureGetGeoJson[]
        writeFile("feature_get_geojson", "${geojson}")
        geojson
    }

    Feature featureFromGeoJSON() {
        // tag::featureFromGeoJSON[]
        String geojson = '{"type":"Feature","geometry":{"type":"Point","coordinates":[-122.3204,47.6024]},"properties":{"id":1,"name":"Seattle"},"id":"city.1"}'
        Feature feature = Feature.fromGeoJSON(geojson)
        println feature
        // end::featureFromGeoJSON[]
        writeFile("feature_from_geojson", "${feature}")
        feature
    }

    String writeFeatureToGeoJson() {
        // tag::writeFeatureToGeoJson[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature = new Feature([
                new Point(-122.3204, 47.6024),
                1,
                "Seattle"
        ], "city.1", schema)

        GeoJSONWriter writer = new GeoJSONWriter()
        String geojson = writer.write(feature)
        println geojson
        // end::writeFeatureToGeoJson[]
        writeFile("feature_write_geojson", "${geojson}")
        geojson
    }

    Feature readFeatureFromGeoJson() {
        // tag::readFeatureFromGeoJson[]
        GeoJSONReader reader = new GeoJSONReader()
        String geojson = '{"type":"Feature","geometry":{"type":"Point","coordinates":[-122.3204,47.6024]},"properties":{"id":1,"name":"Seattle"},"id":"city.1"}'
        Feature feature = reader.read(geojson)
        println feature
        // end::readFeatureFromGeoJson[]
        writeFile("feature_read_geojson", "${feature}")
        feature
    }

    // GeoBuf

    String featureGetGeobuf() {
        // tag::featureGetGeobuf[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature = new Feature([
                new Point(-122.3204, 47.6024),
                1,
                "Seattle"
        ], "city.1", schema)

        String geobuf = feature.geobuf
        println geobuf
        // end::featureGetGeobuf[]
        writeFile("feature_get_geobuf", "${geobuf}")
        geobuf
    }

    Feature featureFromGeoBuf() {
        // tag::featureFromGeoBuf[]
        String geobuf = '0a0269640a046e616d65100218062a1d0a0c08001a089fd8d374c0ebb22d6a0218016a090a0753656174746c65'
        Feature feature = Feature.fromGeobuf(geobuf)
        println feature
        // end::featureFromGeoBuf[]
        writeFile("feature_from_geobuf", "${feature}")
        feature
    }

    String writeFeatureToGeoBuf() {
        // tag::writeFeatureToGeoBuf[]
        Schema schema = new Schema("cities", [
                new Field("geom", "Point", "EPSG:4326"),
                new Field("id", "Integer"),
                new Field("name", "String")
        ])
        Feature feature = new Feature([
                new Point(-122.3204, 47.6024),
                1,
                "Seattle"
        ], "city.1", schema)

        GeobufWriter writer = new GeobufWriter()
        String geobuf = writer.write(feature)
        println geobuf
        // end::writeFeatureToGeoBuf[]
        writeFile("feature_write_geobuf", "${geobuf}")
        geobuf
    }

    Feature readFeatureFromGeoBuf() {
        // tag::readFeatureFromGeoBuf[]
        GeobufReader reader = new GeobufReader()
        String geobuf = '0a0269640a046e616d65100218062a1d0a0c08001a089fd8d374c0ebb22d6a0218016a090a0753656174746c65'
        Feature feature = reader.read(geobuf)
        println feature
        // end::readFeatureFromGeoBuf[]
        writeFile("feature_read_geobuf", "${feature}")
        feature
    }

}