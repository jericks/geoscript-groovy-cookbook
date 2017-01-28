package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.feature.Field
import geoscript.feature.Schema
import org.junit.Test
import static org.junit.Assert.*

class FeatureRecipesTest {

    @Test void createField() {
        FeatureRecipes recipes = new FeatureRecipes()
        Field field = recipes.createField()
        assertEquals("name: String", field.toString())
    }

    @Test void createGeometryField() {
        FeatureRecipes recipes = new FeatureRecipes()
        Field field = recipes.createGeometryField()
        assertEquals("geom: Point(EPSG:4326)", field.toString())
    }

    @Test void createFieldFromList() {
        FeatureRecipes recipes = new FeatureRecipes()
        Field field = recipes.createFieldFromList()
        assertEquals("geom: Polygon(EPSG:4326)", field.toString())
    }

    @Test void createFieldFromMap() {
        FeatureRecipes recipes = new FeatureRecipes()
        Field field = recipes.createFieldFromMap()
        assertEquals("geom: LineString(EPSG:4326)", field.toString())
    }

    @Test void getFieldProperties() {
        FeatureRecipes recipes = new FeatureRecipes()
        Field field = recipes.getFieldProperties()
        assertEquals("geom: Point(EPSG:4326)", field.toString())
    }

    @Test void createSchemaFromFields() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.createSchemaFromFields()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void createSchemaFromLists() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.createSchemaFromLists()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void createSchemaFromMaps() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.createSchemaFromMaps()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void createSchemaFromString() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.createSchemaFromString()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void getSchemaProperties() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.getSchemaProperties()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void getSchemaFields() {
        FeatureRecipes recipes = new FeatureRecipes()
        Map<String,Object> results = recipes.getSchemaFields()
        assertEquals(3, results.fields.size())
        assertNotNull(results.nameField)
        assertNotNull(results.idField)
        assertFalse(results.hasArea)
        assertTrue(results.hasGeom)
    }

    @Test void createFeatureFromSchemaMap() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromSchemaMap()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void createFeatureFromSchemaList() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromSchemaList()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void createFeatureFromSchemaFeature() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromSchemaFeature()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void createFeatureFromSchemaEmpty() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromSchemaEmpty()
        assertNotNull(feature.id)
        assertNull(feature.geom)
        assertNull(feature['id'])
        assertNull(feature['name'])
    }

    @Test void reprojectSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.reprojectSchema()
        assertEquals("cities_spws geom: Point(EPSG:2927), id: Integer, name: String", schema.toString())
    }

    @Test void changeGeometryTypeSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.changeGeometryTypeSchema()
        assertEquals("cities_buffer geom: Polygon(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void changeFieldSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.changeFieldSchema()
        assertEquals("cities_guid geom: Point(EPSG:4326), guid: String, name: String", schema.toString())
    }

    @Test void changeFieldsSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.changeFieldsSchema()
        assertEquals("cities_updated geom: Point(EPSG:4326), guid: String, description: String", schema.toString())
    }

    @Test void addFieldSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.addFieldSchema()
        assertEquals("countries_area geom: Polygon(EPSG:4326), id: Integer, name: String, area: Double", schema.toString())
    }

    @Test void addFieldsSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.addFieldsSchema()
        assertEquals("countries_areaperimeter geom: Polygon(EPSG:4326), id: Integer, name: String, area: Double, perimeter: Double", schema.toString())
    }

    @Test void removeFieldSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.removeFieldSchema()
        assertEquals("countries_updated geom: Polygon(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void removeFieldsSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.removeFieldsSchema()
        assertEquals("countries_updated geom: Polygon(EPSG:4326), id: Integer", schema.toString())
    }

    @Test void includeFieldsSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.includeFieldsSchema()
        assertEquals("countries_updated geom: Polygon(EPSG:4326), name: String", schema.toString())
    }

    @Test void addSchemaNoPrefixNoDuplicates() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.addSchemaNoPrefixNoDuplicates()
        assertEquals("business geom: Point(EPSG:4326), id: Integer, name: String, address: String", schema.toString())
    }

    @Test void addSchemaPostFixAllNoDuplicates() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.addSchemaPostFixAllNoDuplicates()
        assertEquals("business geom: Point(EPSG:4326), id1: Integer, name1: String, id2: Integer, name2: String, address2: String", schema.toString())
    }
    
    @Test void createFeatureFromMap() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromMap()
        assertEquals("feature.city.1 id: 1, name: Seattle, geom: POINT (-122.3204 47.6024)", feature.toString())
    }

    @Test void createFeatureFromMapWithSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromMapWithSchema()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void createFeatureFromListWithSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromListWithSchema()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void getFeatureProperties() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.getFeatureProperties()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void getAndSetFeatureAttributes() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.getAndSetFeatureAttributes()
        assertEquals("cities.city.1 geom: POINT (-122.222 47.5673), id: 2, name: Mercer Island", feature.toString())
    }

    // GeoJSON

    @Test void writeFeatureToGeoJson() {
        FeatureRecipes recipes = new FeatureRecipes()
        String geojson = recipes.writeFeatureToGeoJson()
        assertEquals('{"type":"Feature","geometry":{"type":"Point","coordinates":[-122.3204,47.6024]},' +
                '"properties":{"id":1,"name":"Seattle"},"id":"city.1"}', geojson)
    }

    @Test void featureGetGeoJson() {
        FeatureRecipes recipes = new FeatureRecipes()
        String geojson = recipes.featureGetGeoJson()
        assertEquals('{"type":"Feature","geometry":{"type":"Point","coordinates":[-122.3204,47.6024]},' +
                '"properties":{"id":1,"name":"Seattle"},"id":"city.1"}', geojson)
    }

    @Test void featureFromGeoJSON() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.featureFromGeoJSON()
        assertEquals('feature.city.1 id: 1, name: Seattle, geometry: POINT (-122.3204 47.6024)', feature.toString())
    }

    @Test void readFeatureFromGeoJson() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.readFeatureFromGeoJson()
        assertEquals('feature.city.1 id: 1, name: Seattle, geometry: POINT (-122.3204 47.6024)', feature.toString())
    }

    // GeoBuf

    @Test void featureGetGeobuf() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.featureGetGeobuf()
        assertEquals('0a0269640a046e616d65100218062a1d0a0c08001a089fd8d374c0ebb22d6a0218016a090a0753656174746c65', str)
    }

    @Test void featureFromGeoBuf() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.featureFromGeoBuf()
        assertEquals('features.0 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle', feature.toString())
    }

    @Test void writeFeatureToGeoBuf() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.writeFeatureToGeoBuf()
        assertEquals('0a0269640a046e616d65100218062a1d0a0c08001a089fd8d374c0ebb22d6a0218016a090a0753656174746c65', str)
    }

    @Test void readFeatureFromGeoBuf() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.readFeatureFromGeoBuf()
        assertEquals('features.0 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle', feature.toString())
    }

}
