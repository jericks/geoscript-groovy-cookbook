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
}
