package geoscript.cookbook

import geoscript.feature.Feature
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

    // IO

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

}
