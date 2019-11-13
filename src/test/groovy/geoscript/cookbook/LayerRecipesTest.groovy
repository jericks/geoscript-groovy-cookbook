package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.layer.Layer
import geoscript.layer.Property
import geoscript.layer.Raster
import geoscript.layer.Shapefile
import geoscript.workspace.Workspace
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

    @Test void getLayerMinMax() {
        LayerRecipes recipes = new LayerRecipes()
        Map<String, Double> stats = recipes.getLayerMinMax()
        assertEquals(0.0, stats.min, 0.01)
        assertEquals(36400.0, stats.max, 0.01)
    }

    @Test void getLayerHistogram() {
        LayerRecipes recipes = new LayerRecipes()
        List<Double> values = recipes.getLayerHistogram()
        assertEquals(10, values.size())
        values.each { assertEquals(2, it.size()) }
    }

    @Test void getLayerInterpolate() {
        LayerRecipes recipes = new LayerRecipes()
        Map<String, List<Double>> values = recipes.getLayerInterpolate()
        assertEquals(11, values.linear.size())
        assertEquals(9, values.exp.size())
        assertEquals(13, values.log.size())
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

    @Test void first() {
        LayerRecipes recipes = new LayerRecipes()
        Feature feature = recipes.first()
        assertEquals("Washington", feature.get("NAME_1"))
    }

    @Test void firstSort() {
        LayerRecipes recipes = new LayerRecipes()
        Map<String,Feature> features = recipes.firstSort()
        assertEquals("Alabama", features.asc.get("NAME_1"))
        assertEquals("Wyoming", features.desc.get("NAME_1"))
    }

    @Test void filter() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.filter()
        assertEquals(3, layer.count)
    }

    // Cursor

    @Test void getCursor() {
        LayerRecipes recipes = new LayerRecipes()
        assertEquals(52, recipes.getCursor())
    }

    @Test void getCursorWithFilter() {
        LayerRecipes recipes = new LayerRecipes()
        assertEquals(8, recipes.getCursorWithFilter())
    }

    @Test void getCursorWithParameters() {
        LayerRecipes recipes = new LayerRecipes()
        assertEquals(5, recipes.getCursorWithParameters())
    }

    // Add, Update, Delete

    @Test void addToLayer() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.addToLayer()
        assertEquals("Point", layer.schema.geom.typ)
        assertEquals(5, layer.count)
    }

    @Test void deleteFromLayer() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.deleteFromLayer()
        assertEquals("Point", layer.schema.geom.typ)
        assertEquals(2, layer.count)
    }

    @Test void updateLayer() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.updateLayer()
        assertEquals("Point", layer.schema.geom.typ)
        assertEquals(5, layer.count)
    }

    @Test void updateSetLayer() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.updateSetLayer()
        assertEquals("Point", layer.schema.geom.typ)
        assertEquals(2, layer.count)
        assertEquals("Seattle", layer.features[0].get("name"))
        assertEquals("WA", layer.features[0].get("state"))
        assertEquals("Tacoma", layer.features[1].get("name"))
        assertEquals("WA", layer.features[1].get("state"))
    }

    @Test void readShapefile() {
        LayerRecipes recipes = new LayerRecipes()
        List<Shapefile> shapefiles = recipes.readShapefile()
        assertEquals(177, shapefiles[0].count)
        assertEquals(2, shapefiles[1].count)
    }

    @Test void createShapefile() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createShapefile()
        assertEquals(5, layer.count)
    }

    @Test void readProperty() {
        LayerRecipes recipes = new LayerRecipes()
        List<Property> properties = recipes.readProperty()
        assertEquals(10, properties[0].count)
        assertEquals(10, properties[1].count)
    }

    @Test void createProperty() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createProperty()
        assertEquals(5, layer.count)
    }

    @Test void addUsingWriter() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.addUsingWriter()
        assertEquals(100, layer.count)
    }

    @Test void addWithWriter() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.addWithWriter()
        assertEquals(100, layer.count)
    }

    // Geoprocessing

    @Test void reproject() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.reproject()
        assertEquals("EPSG:3857", layer.proj.id)
    }

    @Test void dissolve() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.dissolve()
        assertEquals("MultiPolygon", layer.schema.geom.typ)
        assertEquals(9, layer.count)
    }

    @Test void buffer() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.buffer()
        assertEquals("Polygon", layer.schema.geom.typ)
        assertEquals(326, layer.count)
    }

    @Test void merge() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.merge()
        assertEquals("Point", layer.schema.geom.typ)
        assertEquals(100, layer.count)
    }

    @Test void splitByField() {
        LayerRecipes recipes = new LayerRecipes()
        Workspace workspace = recipes.splitByField()
        assertEquals(7, workspace.layers.size())
    }

    @Test void splitByLayer() {
        LayerRecipes recipes = new LayerRecipes()
        Workspace workspace = recipes.splitByLayer()
        assertEquals(6, workspace.layers.size())
    }

    @Test void transform() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.transform()
        assertEquals("Point", layer.schema.geom.typ)
        assertEquals(49, layer.count)
    }

    @Test void getRaster() {
        LayerRecipes recipes = new LayerRecipes()
        Raster raster = recipes.getRaster()
        assertNotNull(raster)
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

    // GeoJSON

    @Test void layerToGeoJSONString() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.layerToGeoJSONString()
        assertNotNull(str)
    }

    @Test void writeLayerToGeoJson() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.writeLayerToGeoJson()
        assertNotNull(str)
    }

    @Test void readLayerFromGeoJsonString() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.readLayerFromGeoJsonString()
        assertEquals(2, layer.count)
    }

    // Kml

    @Test void layerToKMLString() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.layerToKMLString()
        assertNotNull(str)
    }

    @Test void writeLayerToKml() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.writeLayerToKml()
        assertNotNull(str)
    }

    @Test void readLayerFromKmlString() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.readLayerFromKmlString()
        assertEquals(2, layer.count)
    }

    // GML

    @Test void layerToGMLString() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.layerToGMLString()
        assertNotNull(str)
    }

    @Test void writeLayerToGml() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.writeLayerToGml()
        assertNotNull(str)
    }

    @Test void readLayerFromGmlString() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.readLayerFromGmlString()
        assertEquals(2, layer.count)
    }

    // Geobuf

    @Test void layerToGeobufString() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.layerToGeobufString()
        assertNotNull(str)
    }

    @Test void writeLayerToGeoBuf() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.writeLayerToGeoBuf()
        assertNotNull(str)
    }

    @Test void readLayerFromGeoBufString() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.readLayerFromGeoBufString()
        assertEquals(2, layer.count)
    }

    // CSV

    @Test void writeLayerToCsv() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.writeLayerToCsv()
        assertNotNull(str)
    }

    @Test void readLayerFromCsvString() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.readLayerFromCsvString()
        assertEquals(2, layer.count)
    }

    // GeoRSS

    @Test void writeLayerToGeoRss() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.writeLayerToGeoRss()
        assertNotNull(str)
    }

    @Test void readLayerFromGeoRssString() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer  = recipes.readLayerFromGeoRssString()
        assertEquals(2, layer.count)
    }

    // GPX

    @Test void writeLayerToGpx() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.writeLayerToGpx()
        assertNotNull(str)
    }

    @Test void readLayerFromGpxString() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.readLayerFromGpxString()
        assertEquals(2, layer.count)
    }

    // MVT

    @Test void writeLayerToMvt() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.writeLayerToMvt()
        assertNotNull(str)
    }

    @Test void readLayerFromMvtString() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.readLayerFromMvtString()
        assertEquals(2, layer.count)
    }

    // PBF

    @Test void writeLayerToPbf() {
        LayerRecipes recipes = new LayerRecipes()
        String str = recipes.writeLayerToPbf()
        assertNotNull(str)
    }

    @Test void readLayerFromPbfString() {
        LayerRecipes recipes = new LayerRecipes()
        List<Layer> layers = recipes.readLayerFromPbfString()
        assertEquals(1, layers.size())
        assertEquals(2, layers[0].count)
    }

    // Graticule

    @Test void createSquareGraticule() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createSquareGraticule()
        assertEquals("Polygon", layer.schema.geom.typ)
        assertEquals(162, layer.count)
    }

    @Test void createSquareGraticuleToShapefile() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createSquareGraticuleToShapefile()
        assertEquals("MultiPolygon", layer.schema.geom.typ)
        assertEquals(72, layer.count)
    }

    @Test void createHexagonGraticule() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createHexagonGraticule()
        assertEquals("Polygon", layer.schema.geom.typ)
        assertEquals(50, layer.count)
    }

    @Test void createAngledHexagonGraticule() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createAngledHexagonGraticule()
        assertEquals("Polygon", layer.schema.geom.typ)
        assertEquals(220, layer.count)
    }

    @Test void createIntersectingOnlyHexagonGraticule() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createIntersectingOnlyHexagonGraticule()
        assertEquals("Polygon", layer.schema.geom.typ)
        assertEquals(239, layer.count)
    }

    @Test void createCustomSchemaHexagonGraticule() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createCustomSchemaHexagonGraticule()
        assertEquals("Polygon", layer.schema.geom.typ)
        assertEquals(133, layer.count)
    }

    @Test void createLineGraticule() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createLineGraticule()
        assertEquals("LineString", layer.schema.geom.typ)
        assertEquals(56, layer.count)
    }

    @Test void createRectangularGraticule() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createRectangularGraticule()
        assertEquals("Polygon", layer.schema.geom.typ)
        assertEquals(324, layer.count)
    }

    @Test void createOvalGraticule() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createOvalGraticule()
        assertEquals("Polygon", layer.schema.geom.typ)
        assertEquals(162, layer.count)
    }

    @Test void createIntersectingOnlyOvalGraticule() {
        LayerRecipes recipes = new LayerRecipes()
        Layer layer = recipes.createIntersectingOnlyOvalGraticule()
        assertEquals("Polygon", layer.schema.geom.typ)
        assertEquals(167, layer.count)
    }
}
