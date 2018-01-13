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

    // Geoprocessing

    @Test void buffer() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.buffer()
        assertEquals("Polygon", layer.schema.geom.typ)
        assertEquals(326, layer.count)
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

    @Test void clipToWorkspace() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.clipToWorkspace()
        // Check schema
        assertEquals "ba_clip", layer.name
        assertTrue layer.schema.has("B")
        assertFalse layer.schema.has("A")
        assertEquals "MultiPolygon", layer.schema.geom.typ
        // Check features
        assertEquals 3, layer.count
        assertEquals 2, layer.count("B = 4")
        assertEquals 1, layer.count("B = 3")
        assertEquals "MULTIPOLYGON (((97 100, 97 105, 100 105, 100 100, 97 100)))", layer.getFeatures("B = 4")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((120 105, 125 105, 125 100, 120 100, 120 105)))", layer.getFeatures("B = 4")[1].geom.wkt
        assertEquals "MULTIPOLYGON (((90 105, 95 105, 95 100, 90 100, 90 105)))", layer.getFeatures("B = 3")[0].geom.wkt
    }

    @Test void union() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.union()
        // Check schema
        assertEquals "a_b_union", layer.name
        assertTrue layer.schema.has("A")
        assertTrue layer.schema.has("B")
        assertEquals "Polygon", layer.schema.geom.typ
        // Check features
        assertEquals 7, layer.count
        assertEquals 1, layer.count("A = 1 AND B = 3")
        assertEquals 1, layer.count("A = 1 AND B = 4")
        assertEquals 1, layer.count("A = 1 AND B IS NULL")
        assertEquals 1, layer.count("A = 2 AND B = 4")
        assertEquals 1, layer.count("A = 2 AND B IS NULL")
        assertEquals 1, layer.count("A IS NULL AND B = 3")
        assertEquals 1, layer.count("A IS NULL AND B = 4")
        assertEquals "POLYGON ((90 100, 90 105, 95 105, 95 100, 90 100))", layer.getFeatures("A = 1 AND B = 3")[0].geom.wkt
        assertEquals "POLYGON ((100 105, 100 100, 97 100, 97 105, 100 105))", layer.getFeatures("A = 1 AND B = 4")[0].geom.wkt
        assertEquals "POLYGON ((90 105, 90 110, 100 110, 100 105, 97 105, 97 100, 95 100, 95 105, 90 105))", layer.getFeatures("A = 1 AND B IS NULL")[0].geom.wkt
        assertEquals "POLYGON ((120 100, 120 105, 125 105, 125 100, 120 100))", layer.getFeatures("A = 2 AND B = 4")[0].geom.wkt
        assertEquals "POLYGON ((120 105, 120 110, 130 110, 130 100, 125 100, 125 105, 120 105))", layer.getFeatures("A = 2 AND B IS NULL")[0].geom.wkt
        assertEquals "POLYGON ((85 95, 85 105, 90 105, 90 100, 95 100, 95 95, 85 95))", layer.getFeatures("A IS NULL AND B = 3")[0].geom.wkt
        assertEquals "POLYGON ((97 95, 97 100, 100 100, 100 105, 120 105, 120 100, 125 100, 125 95, 97 95))", layer.getFeatures("A IS NULL AND B = 4")[0].geom.wkt
    }

    @Test void unionToWorkspace() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.unionToWorkspace()
        // Check schema
        assertEquals "ba_union", layer.name
        assertTrue layer.schema.has("A")
        assertTrue layer.schema.has("B")
        assertEquals "MultiPolygon", layer.schema.geom.typ
        // Check features
        assertEquals 7, layer.count
        assertEquals 1, layer.count("A = 1 AND B = 3")
        assertEquals 1, layer.count("A = 1 AND B = 4")
        assertEquals 1, layer.count("A = 1 AND B IS NULL")
        assertEquals 1, layer.count("A = 2 AND B = 4")
        assertEquals 1, layer.count("A = 2 AND B IS NULL")
        assertEquals 1, layer.count("A IS NULL AND B = 3")
        assertEquals 1, layer.count("A IS NULL AND B = 4")
        assertEquals "MULTIPOLYGON (((90 105, 95 105, 95 100, 90 100, 90 105)))", layer.getFeatures("A = 1 AND B = 3")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((97 100, 97 105, 100 105, 100 100, 97 100)))", layer.getFeatures("A = 1 AND B = 4")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((90 105, 90 110, 100 110, 100 105, 97 105, 97 100, 95 100, 95 105, 90 105)))", layer.getFeatures("A = 1 AND B IS NULL")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((120 105, 125 105, 125 100, 120 100, 120 105)))", layer.getFeatures("A = 2 AND B = 4")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((120 105, 120 110, 130 110, 130 100, 125 100, 125 105, 120 105)))", layer.getFeatures("A = 2 AND B IS NULL")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((85 95, 85 105, 90 105, 90 100, 95 100, 95 95, 85 95)))", layer.getFeatures("A IS NULL AND B = 3")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((97 95, 97 100, 100 100, 100 105, 120 105, 120 100, 125 100, 125 95, 97 95)))", layer.getFeatures("A IS NULL AND B = 4")[0].geom.wkt
    }

    @Test void intersection() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.intersection()
        // Check schema
        assertEquals "a_b_intersection", layer.name
        assertTrue layer.schema.has("A")
        assertTrue layer.schema.has("B")
        assertEquals "Polygon", layer.schema.geom.typ
        // Check features
        assertEquals 3, layer.count
        assertEquals 1, layer.count("A = 1 AND B = 3")
        assertEquals 1, layer.count("A = 1 AND B = 4")
        assertEquals 1, layer.count("A = 2 AND B = 4")
        assertEquals "POLYGON ((90 100, 90 105, 95 105, 95 100, 90 100))", layer.getFeatures("A = 1 AND B = 3")[0].geom.wkt
        assertEquals "POLYGON ((100 105, 100 100, 97 100, 97 105, 100 105))", layer.getFeatures("A = 1 AND B = 4")[0].geom.wkt
        assertEquals "POLYGON ((120 100, 120 105, 125 105, 125 100, 120 100))", layer.getFeatures("A = 2 AND B = 4")[0].geom.wkt
    }

    @Test void intersectionToWorkspace() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.intersectionToWorkspace()
        // Check schema
        assertEquals "ba_intersection", layer.name
        assertTrue layer.schema.has("A")
        assertTrue layer.schema.has("B")
        assertEquals "MultiPolygon", layer.schema.geom.typ
        // Check features
        assertEquals 3, layer.count
        assertEquals 1, layer.count("A = 1 AND B = 3")
        assertEquals 1, layer.count("A = 1 AND B = 4")
        assertEquals 1, layer.count("A = 2 AND B = 4")
        assertEquals "MULTIPOLYGON (((90 105, 95 105, 95 100, 90 100, 90 105)))", layer.getFeatures("A = 1 AND B = 3")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((97 100, 97 105, 100 105, 100 100, 97 100)))", layer.getFeatures("A = 1 AND B = 4")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((120 105, 125 105, 125 100, 120 100, 120 105)))", layer.getFeatures("A = 2 AND B = 4")[0].geom.wkt
    }

    @Test void identity() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.identity()
        // Check schema
        assertEquals "a_b_identity", layer.name
        assertTrue layer.schema.has("A")
        assertTrue layer.schema.has("B")
        assertEquals "Polygon", layer.schema.geom.typ
        // Check features
        assertEquals 5, layer.count
        assertEquals 1, layer.count("A = 1 AND B = 3")
        assertEquals 1, layer.count("A = 1 AND B = 4")
        assertEquals 1, layer.count("A = 1 AND B IS NULL")
        assertEquals 1, layer.count("A = 2 AND B = 4")
        assertEquals 1, layer.count("A = 2 AND B IS NULL")
        assertEquals "POLYGON ((90 100, 90 105, 95 105, 95 100, 90 100))", layer.getFeatures("A = 1 AND B = 3")[0].geom.wkt
        assertEquals "POLYGON ((100 105, 100 100, 97 100, 97 105, 100 105))", layer.getFeatures("A = 1 AND B = 4")[0].geom.wkt
        assertEquals "POLYGON ((90 105, 90 110, 100 110, 100 105, 97 105, 97 100, 95 100, 95 105, 90 105))", layer.getFeatures("A = 1 AND B IS NULL")[0].geom.wkt
        assertEquals "POLYGON ((120 100, 120 105, 125 105, 125 100, 120 100))", layer.getFeatures("A = 2 AND B = 4")[0].geom.wkt
        assertEquals "POLYGON ((120 105, 120 110, 130 110, 130 100, 125 100, 125 105, 120 105))", layer.getFeatures("A = 2 AND B IS NULL")[0].geom.wkt
    }

    @Test void identityToWorkspace() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.identityToWorkspace()
        // Check schema
        assertEquals "ba_identity", layer.name
        assertTrue layer.schema.has("A")
        assertTrue layer.schema.has("B")
        assertEquals "MultiPolygon", layer.schema.geom.typ
        // Check features
        assertEquals 5, layer.count
        assertEquals 1, layer.count("B = 3 AND A IS NULL")
        assertEquals 1, layer.count("B = 4 AND A IS NULL")
        assertEquals 1, layer.count("B = 3 AND A = 1")
        assertEquals 1, layer.count("B = 4 AND A = 1")
        assertEquals 1, layer.count("B = 4 AND A = 2")
        assertEquals "MULTIPOLYGON (((85 95, 85 105, 90 105, 90 100, 95 100, 95 95, 85 95)))", layer.getFeatures("B = 3 AND A IS NULL")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((97 95, 97 100, 100 100, 100 105, 120 105, 120 100, 125 100, 125 95, 97 95)))", layer.getFeatures("B = 4 AND A IS NULL")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((90 105, 95 105, 95 100, 90 100, 90 105)))", layer.getFeatures("B = 3 AND A = 1")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((97 100, 97 105, 100 105, 100 100, 97 100)))", layer.getFeatures("B = 4 AND A = 1")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((120 105, 125 105, 125 100, 120 100, 120 105)))", layer.getFeatures("B = 4 AND A = 2")[0].geom.wkt
    }

    @Test void erase() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.erase()
        // Check schema
        assertEquals "a_b_erase", layer.name
        assertTrue layer.schema.has("A")
        assertFalse layer.schema.has("B")
        assertEquals "Polygon", layer.schema.geom.typ
        // Check features
        assertEquals 2, layer.count
        assertEquals 1, layer.count("A = 1")
        assertEquals 1, layer.count("A = 2")
        assertEquals "POLYGON ((90 105, 90 110, 100 110, 100 105, 97 105, 97 100, 95 100, 95 105, 90 105))", layer.getFeatures("A = 1")[0].geom.wkt
        assertEquals "POLYGON ((120 105, 120 110, 130 110, 130 100, 125 100, 125 105, 120 105))", layer.getFeatures("A = 2")[0].geom.wkt
    }

    @Test void eraseToWorkspace() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.eraseToWorkspace()
        // Check schema
        assertEquals "ba_erase", layer.name
        assertTrue layer.schema.has("B")
        assertFalse layer.schema.has("A")
        assertEquals "MultiPolygon", layer.schema.geom.typ
        // Check features
        assertEquals 2, layer.count
        assertEquals 1, layer.count("B = 3")
        assertEquals 1, layer.count("B = 4")
        assertEquals "MULTIPOLYGON (((85 95, 85 105, 90 105, 90 100, 95 100, 95 95, 85 95)))", layer.getFeatures("B = 3")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((97 95, 97 100, 100 100, 100 105, 120 105, 120 100, 125 100, 125 95, 97 95)))", layer.getFeatures("B = 4")[0].geom.wkt
    }

    @Test void update() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.update()
        // Check schema
        assertEquals "a_b_update", layer.name
        assertTrue layer.schema.has("A")
        assertFalse layer.schema.has("B")
        assertEquals "Polygon", layer.schema.geom.typ
        // Check features
        assertEquals 4, layer.count
        assertEquals 1, layer.count("A = 1")
        assertEquals 1, layer.count("A = 2")
        assertEquals 2, layer.count("A IS NULL")
        assertEquals "POLYGON ((90 105, 90 110, 100 110, 100 105, 97 105, 97 100, 95 100, 95 105, 90 105))", layer.getFeatures("A = 1")[0].geom.wkt
        assertEquals "POLYGON ((120 105, 120 110, 130 110, 130 100, 125 100, 125 105, 120 105))", layer.getFeatures("A = 2")[0].geom.wkt
        assertEquals "POLYGON ((85 95, 85 105, 95 105, 95 95, 85 95))", layer.getFeatures("A IS NULL")[0].geom.wkt
        assertEquals "POLYGON ((97 95, 97 105, 125 105, 125 95, 97 95))", layer.getFeatures("A IS NULL")[1].geom.wkt
    }

    @Test void updateToWorkspace() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.updateToWorkspace()
        // Check schema
        assertEquals "ba_update", layer.name
        assertTrue layer.schema.has("B")
        assertFalse layer.schema.has("A")
        assertEquals "MultiPolygon", layer.schema.geom.typ
        // Check features
        assertEquals 4, layer.count
        assertEquals 1, layer.count("B = 3")
        assertEquals 1, layer.count("B = 4")
        assertEquals 2, layer.count("B IS NULL")
        assertEquals "MULTIPOLYGON (((85 95, 85 105, 90 105, 90 100, 95 100, 95 95, 85 95)))", layer.getFeatures("B = 3")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((97 95, 97 100, 100 100, 100 105, 120 105, 120 100, 125 100, 125 95, 97 95)))", layer.getFeatures("B = 4")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((120 100, 120 110, 130 110, 130 100, 120 100)))", layer.getFeatures("B IS NULL")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((90 100, 90 110, 100 110, 100 100, 90 100)))", layer.getFeatures("B IS NULL")[1].geom.wkt
    }

    @Test void symDifference() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.symDifference()
        // Check schema
        assertEquals "a_b_symdifference", layer.name
        assertTrue layer.schema.has("A")
        assertTrue layer.schema.has("B")
        assertEquals "Polygon", layer.schema.geom.typ
        // Check features
        assertEquals 4, layer.count
        assertEquals 1, layer.count("A = 1 AND B IS NULL")
        assertEquals 1, layer.count("A = 2 AND B IS NULL")
        assertEquals 1, layer.count("A IS NULL AND B = 3")
        assertEquals 1, layer.count("A IS NULL AND B = 4")
        assertEquals "POLYGON ((90 105, 90 110, 100 110, 100 105, 97 105, 97 100, 95 100, 95 105, 90 105))",layer.getFeatures("A = 1 AND B IS NULL")[0].geom.wkt
        assertEquals "POLYGON ((120 105, 120 110, 130 110, 130 100, 125 100, 125 105, 120 105))", layer.getFeatures("A = 2 AND B IS NULL")[0].geom.wkt
        assertEquals "POLYGON ((85 95, 85 105, 90 105, 90 100, 95 100, 95 95, 85 95))", layer.getFeatures("A IS NULL AND B = 3")[0].geom.wkt
        assertEquals "POLYGON ((97 95, 97 100, 100 100, 100 105, 120 105, 120 100, 125 100, 125 95, 97 95))", layer.getFeatures("A IS NULL AND B = 4")[0].geom.wkt
    }

    @Test void symDifferenceToWorkspace() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.symDifferenceToWorkspace()
        // Check schema
        assertEquals "ba_symdifference", layer.name
        assertTrue layer.schema.has("A")
        assertTrue layer.schema.has("B")
        assertEquals "MultiPolygon", layer.schema.geom.typ
        // Check features
        assertEquals 4, layer.count
        assertEquals 1, layer.count("A = 1 AND B IS NULL")
        assertEquals 1, layer.count("A = 2 AND B IS NULL")
        assertEquals 1, layer.count("A IS NULL AND B = 3")
        assertEquals 1, layer.count("A IS NULL AND B = 4")
        assertEquals "MULTIPOLYGON (((90 105, 90 110, 100 110, 100 105, 97 105, 97 100, 95 100, 95 105, 90 105)))",layer.getFeatures("A = 1 AND B IS NULL")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((120 105, 120 110, 130 110, 130 100, 125 100, 125 105, 120 105)))", layer.getFeatures("A = 2 AND B IS NULL")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((85 95, 85 105, 90 105, 90 100, 95 100, 95 95, 85 95)))", layer.getFeatures("A IS NULL AND B = 3")[0].geom.wkt
        assertEquals "MULTIPOLYGON (((97 95, 97 100, 100 100, 100 105, 120 105, 120 100, 125 100, 125 95, 97 95)))", layer.getFeatures("A IS NULL AND B = 4")[0].geom.wkt
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
