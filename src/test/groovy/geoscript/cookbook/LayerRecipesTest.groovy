package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.layer.Layer
import org.junit.Test
import static org.junit.Assert.*

class LayerRecipesTest {

    @Test void getLayerProperties() {
        LayerRecipes recipes = new LayerRecipes()
        Map<String, Object> values = recipes.getLayerProperties()
        assertEquals("countries", values.name)
        assertEquals("GeoPackage", values.format)
        assertEquals(177, values.count)
        assertEquals("EPSG:4326", values.proj.id)
        assertEquals("(-179.99999999999997,-90.00000000000003,180.00000000000014,83.64513000000002,EPSG:4326)", values.bounds.toString())
    }

    @Test void getLayerFeatures() {
        LayerRecipes recipes = new LayerRecipes()
        assertEquals(52, recipes.getLayerFeatures())
    }

    @Test void getLayerFeaturesFiltered() {
        LayerRecipes recipes = new LayerRecipes()
        assertEquals(8, recipes.getLayerFeaturesFiltered())
    }

    @Test void getLayerFeaturesWithParameters() {
        LayerRecipes recipes = new LayerRecipes()
        assertEquals(5, recipes.getLayerFeaturesWithParameters())
    }

    @Test void getLayerFeaturesInAList() {
        LayerRecipes recipes = new LayerRecipes()
        List<Feature> features = recipes.getLayerFeaturesInAList()
        assertEquals(52, features.size())
    }

    @Test void collectFromFeature() {
        LayerRecipes recipes = new LayerRecipes()
        List<String> values = recipes.collectFromFeature()
        assertEquals(52, values.size())
    }

    @Test void collectFromFeatureWithOptions() {
        LayerRecipes recipes = new LayerRecipes()
        List<String> values = recipes.collectFromFeatureWithOptions()
        assertEquals(5, values.size())
    }

    // Layer Algebra

    @Test void algebra() {
        LayerRecipes recipes = new LayerRecipes()
        recipes.algebra()
    }

    @Test void clip() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.clip()
        // Check schema
        assertEquals "a_b_clipped", layer.name
        assertTrue layer.schema.has("A")
        assertFalse layer.schema.has("B")
        assertEquals "Polygon", layer.schema.geom.typ
        // Check features
        assertEquals 3, layer.count
        assertEquals 2, layer.count("A = 1")
        assertEquals 1, layer.count("A = 2")
        assertEquals "POLYGON ((90 100, 90 105, 95 105, 95 100, 90 100))", layer.getFeatures("A = 1")[0].geom.wkt
        assertEquals "POLYGON ((100 105, 100 100, 97 100, 97 105, 100 105))", layer.getFeatures("A = 1")[1].geom.wkt
        assertEquals "POLYGON ((120 100, 120 105, 125 105, 125 100, 120 100))", layer.getFeatures("A = 2")[0].geom.wkt
    }


    // IO

    @Test void listLayerReaders() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.listLayerReaders()
        assertNotNull(str)
    }

    @Test void findLayerReader() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.findLayerReader()
        assertNotNull(layer)
    }

    @Test void listLayerWriters() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.listLayerWriters()
        assertNotNull(str)
    }

    @Test void findLayerWriter() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.findLayerWriter()
        assertNotNull(str)
    }

    @Test void layerToGeoJSONString() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.layerToGeoJSONString()
        assertNotNull(str)
    }

    @Test void layerToKMLString() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.layerToKMLString()
        assertNotNull(str)
    }

    @Test void layerToGMLString() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.layerToGMLString()
        assertNotNull(str)
    }
    
    @Test void layerToGeobufString() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.layerToGeobufString()
        assertNotNull(str)
    }

}
