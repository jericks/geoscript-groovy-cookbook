package geoscript.cookbook

import geoscript.feature.Field
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

}
