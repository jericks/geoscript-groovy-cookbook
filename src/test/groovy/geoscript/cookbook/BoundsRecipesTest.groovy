package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.GeometryCollection
import geoscript.geom.LineString
import geoscript.geom.Point
import geoscript.geom.Polygon
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*
import static geoscript.cookbook.Assertions.*

class BoundsRecipesTest {

    @Test void createBounds() {
        BoundsRecipes recipes = new BoundsRecipes()
        Bounds bounds = recipes.createBounds()
        assertEquals("(-127.265,43.068,-113.554,50.289,EPSG:4326)", bounds.toString())
    }

    @Test void createBoundsNoProjection() {
        BoundsRecipes recipes = new BoundsRecipes()
        Bounds bounds = recipes.createBoundsNoProjection()
        assertEquals("(-127.265,43.068,-113.554,50.289,EPSG:4326)", bounds.toString())
    }

    @Test void createBoundsFromStringWithCommas() {
        BoundsRecipes recipes = new BoundsRecipes()
        Bounds bounds = recipes.createBoundsFromStringWithCommas()
        assertEquals("(-127.265,43.068,-113.554,50.289,EPSG:4326)", bounds.toString())
    }

    @Test void createBoundsFromStringWithSpaces() {
        BoundsRecipes recipes = new BoundsRecipes()
        Bounds bounds = recipes.createBoundsFromStringWithSpaces()
        assertEquals("(12.919921874999998,40.84706035607122,15.99609375,41.77131167976407,EPSG:4326)", bounds.toString())
    }

    @Test void expandByBounds() {
        BoundsRecipes recipes = new BoundsRecipes()
        Bounds bounds = recipes.expandByBounds()
        assertEquals("(-137.365,32.967999999999996,-103.45400000000001,60.389,EPSG:4326)", bounds.toString())
    }

    @Test void expandBounds() {
        BoundsRecipes recipes = new BoundsRecipes()
        Bounds bounds = recipes.expandBounds()
        assertEquals("(8.4375,31.952162238024975,30.937499999999996,46.07323062540835,EPSG:4326)", bounds.toString())
    }

    @Test void scaleBounds() {
        BoundsRecipes recipes = new BoundsRecipes()
        Bounds bounds = recipes.scaleBounds()
        assertEquals("(-134.1205,39.457499999999996,-106.6985,53.8995)", bounds.toString())
    }

    @Test void reprojectBounds() {
        BoundsRecipes recipes = new BoundsRecipes()
        Bounds actual = recipes.reprojectBounds()
        Bounds expected = new Bounds(1147444.7684517875, 703506.2231641772, 1155828.1202425088, 711367.9403610165, "EPSG:2927")
        assertBoundsEquals(expected, actual, 0.0000001)
    }

    @Test void boundsGetGeometry() {
        BoundsRecipes recipes = new BoundsRecipes()
        Geometry geometry = recipes.boundsGetGeometry()
        assertEquals("POLYGON ((-122.485 47.246, -122.485 47.267, -122.452 47.267, -122.452 47.246, -122.485 47.246))", geometry.wkt)
    }

    @Test void boundsGetPolygon() {
        BoundsRecipes recipes = new BoundsRecipes()
        Polygon polygon = recipes.boundsGetPolygon()
        assertEquals("POLYGON ((-122.485 47.246, -122.485 47.267, -122.452 47.267, -122.452 47.246, -122.485 47.246))", polygon.wkt)
    }

    @Test void boundsGetCorners() {
        BoundsRecipes recipes = new BoundsRecipes()
        List<Point> points = recipes.boundsGetCorners()
        assertEquals(4, points.size())
        assertEquals("GEOMETRYCOLLECTION (POINT (-122.485 47.246), POINT (-122.485 47.267), POINT (-122.452 47.267), POINT (-122.452 47.246))", new GeometryCollection(points).wkt)
    }

    @Test void boundsTile() {
        BoundsRecipes recipes = new BoundsRecipes()
        List<Bounds> bounds = recipes.boundsTile()
        assertEquals(16, bounds.size())
        assertEquals("[" +
                "(-122.485,47.246,-122.47675,47.25125,EPSG:4326), " +
                "(-122.47675,47.246,-122.46849999999999,47.25125,EPSG:4326), " +
                "(-122.46849999999999,47.246,-122.46024999999999,47.25125,EPSG:4326), " +
                "(-122.46024999999999,47.246,-122.452,47.25125,EPSG:4326), " +
                "(-122.485,47.25125,-122.47675,47.2565,EPSG:4326), " +
                "(-122.47675,47.25125,-122.46849999999999,47.2565,EPSG:4326), " +
                "(-122.46849999999999,47.25125,-122.46024999999999,47.2565,EPSG:4326), " +
                "(-122.46024999999999,47.25125,-122.452,47.2565,EPSG:4326), " +
                "(-122.485,47.2565,-122.47675,47.261750000000006,EPSG:4326), " +
                "(-122.47675,47.2565,-122.46849999999999,47.261750000000006,EPSG:4326), " +
                "(-122.46849999999999,47.2565,-122.46024999999999,47.261750000000006,EPSG:4326), " +
                "(-122.46024999999999,47.2565,-122.452,47.261750000000006,EPSG:4326), " +
                "(-122.485,47.261750000000006,-122.47675,47.267,EPSG:4326), " +
                "(-122.47675,47.261750000000006,-122.46849999999999,47.267,EPSG:4326), " +
                "(-122.46849999999999,47.261750000000006,-122.46024999999999,47.267,EPSG:4326), " +
                "(-122.46024999999999,47.261750000000006,-122.452,47.267,EPSG:4326)]", bounds.toString())
    }

    @Test void boundsQuadTree() {
        BoundsRecipes recipes = new BoundsRecipes()
        List<Bounds> bounds = recipes.boundsQuadTree()
        assertEquals(5, bounds.size())
        assertEquals("[" +
                "(-180.0,-90.0,180.0,90.0,EPSG:4326), " +
                "(-180.0,-90.0,0.0,0.0,EPSG:4326), " +
                "(-180.0,0.0,0.0,90.0,EPSG:4326), " +
                "(0.0,-90.0,180.0,0.0,EPSG:4326), " +
                "(0.0,0.0,180.0,90.0,EPSG:4326)]", bounds.toString())
    }

    @Test void boundsIsEmpty() {
        BoundsRecipes recipes = new BoundsRecipes()
        Map<String,Boolean> result = recipes.boundsIsEmpty()
        assertFalse(result.bounds)
        assertTrue(result.emptyBounds)
    }

    @Test void getBoundsProperties() {
        BoundsRecipes recipes = new BoundsRecipes()
        Map properties = recipes.getBoundsProperties()
        assertEquals("(-127.265,43.068,-113.554,50.289,EPSG:4326)", properties.bounds)
        assertEquals(-127.265, properties.minX, 0.01)
        assertEquals(43.068, properties.minY, 0.01)
        assertEquals(-113.554, properties.maxX, 0.01)
        assertEquals(50.289, properties.maxY, 0.01)
        assertEquals("EPSG:4326", properties.proj.id)
        assertEquals(99.007, properties.area, 0.01)
        assertEquals(13.710, properties.width, 0.01)
        assertEquals(7.221, properties.height, 0.01)
        assertEquals(1.898, properties.aspect, 0.01)
    }

    @Test void boundsContainsBounds() {
        BoundsRecipes recipes = new BoundsRecipes()
        Map results = recipes.boundsContainsBounds()
        assertTrue(results.bounds2)
        assertFalse(results.bounds3)
    }

    @Test void boundsContainsPoint() {
        BoundsRecipes recipes = new BoundsRecipes()
        Map results = recipes.boundsContainsPoint()
        assertTrue(results.point1)
        assertFalse(results.point2)
    }

    @Test void boundsIntersectsBounds() {
        BoundsRecipes recipes = new BoundsRecipes()
        Map results = recipes.boundsIntersectsBounds()
        assertFalse(results.bounds2)
        assertTrue(results.bounds3)
    }

    @Test void boundsIntersection() {
        BoundsRecipes recipes = new BoundsRecipes()
        Bounds bounds = recipes.boundsIntersection()
        assertEquals("(-95.885,46.765,-95.839,46.792)", bounds.toString())
    }

    @Test void boundsGetPolygonGridByColumnsAndRows() {
        BoundsRecipes recipes = new BoundsRecipes()
        Geometry geometry = recipes.boundsGetPolygonGridByColumnsAndRows()
        assertTrue(geometry instanceof GeometryCollection)
        assertEquals(20, geometry.numGeometries)
    }

    @Test void boundsGeneratePointGridByColumnsAndRows() {
        BoundsRecipes recipes = new BoundsRecipes()
        Geometry geometry = recipes.boundsGeneratePointGridByColumnsAndRows()
        assertTrue(geometry instanceof GeometryCollection)
        assertEquals(80, geometry.numGeometries)
    }

    @Test void boundsGetCircleGridByCellWidthAndHeight() {
        BoundsRecipes recipes = new BoundsRecipes()
        Geometry geometry = recipes.boundsGetCircleGridByCellWidthAndHeight()
        assertTrue(geometry instanceof GeometryCollection)
        assertEquals(15, geometry.numGeometries)
    }

    @Test void boundsGetHexagonGridByColumnsAndRows() {
        BoundsRecipes recipes = new BoundsRecipes()
        Geometry geometry = recipes.boundsGetHexagonGridByColumnsAndRows()
        assertTrue(geometry instanceof GeometryCollection)
        assertEquals(15, geometry.numGeometries)
    }

    @Test void boundsGetHexagonInvGridByColumnsAndRows() {
        BoundsRecipes recipes = new BoundsRecipes()
        Geometry geometry = recipes.boundsGetHexagonInvGridByColumnsAndRows()
        assertTrue(geometry instanceof GeometryCollection)
        assertEquals(25, geometry.numGeometries)
    }

    @Test void boundsGetTriangleGridByColumnsAndRows() {
        BoundsRecipes recipes = new BoundsRecipes()
        Geometry geometry = recipes.boundsGetTriangleGridByColumnsAndRows()
        assertTrue(geometry instanceof GeometryCollection)
        assertEquals(25, geometry.numGeometries)
    }

    @Test void boundsCreateRectangle() {
        BoundsRecipes recipes = new BoundsRecipes()
        Polygon polygon = recipes.boundsCreateRectangle()
        assertEquals("POLYGON ((10 -4.14213562373095, 12.82842712474619 -1.3137084989847603, " +
                "15.65685424949238 1.5147186257614296, 18.48528137423857 4.343145750507619, " +
                "21.31370849898476 7.171572875253809, 24.14213562373095 10, 21.31370849898476 12.828427124746188, " +
                "18.48528137423857 15.65685424949238, 15.656854249492381 18.485281374238568, " +
                "12.828427124746192 21.31370849898476, 10.000000000000002 24.14213562373095, " +
                "7.171572875253812 21.31370849898476, 4.343145750507622 18.485281374238568, " +
                "1.5147186257614322 15.65685424949238, -1.3137084989847594 12.828427124746192, " +
                "-4.142135623730949 10, -1.3137084989847594 7.171572875253811, 1.5147186257614305 4.343145750507621, " +
                "4.34314575050762 1.5147186257614305, 7.17157287525381 -1.3137084989847598, " +
                "10 -4.14213562373095))", polygon.wkt)
    }

    @Test void boundsCreateEllipse() {
        BoundsRecipes recipes = new BoundsRecipes()
        Polygon polygon = recipes.boundsCreateEllipse()
        assertNotNull(polygon)
        assertFalse(polygon.isEmpty())
    }

    @Test void boundsCreateSquircle() {
        BoundsRecipes recipes = new BoundsRecipes()
        Polygon polygon = recipes.boundsCreateSquircle()
        assertEquals("POLYGON ((10 20, 14.204482076268572 19.920942416179404, 18.408964152537145 18.408964152537145, " +
                "19.920942416179404 14.204482076268572, 20 10, 19.920942416179404 5.795517923731428, " +
                "18.408964152537145 1.5910358474628534, 14.204482076268572 0.079057583820596, 10 0, " +
                "5.795517923731428 0.079057583820596, 1.5910358474628534 1.5910358474628552, " +
                "0.079057583820596 5.795517923731428, 0 10, 0.079057583820596 14.204482076268572, " +
                "1.5910358474628552 18.408964152537145, 5.795517923731428 19.920942416179404, " +
                "10 20))", polygon.wkt)
    }

    @Test void boundsCreateSuperCircle() {
        BoundsRecipes recipes = new BoundsRecipes()
        Polygon polygon = recipes.boundsCreateSuperCircle()
        assertEquals("POLYGON ((10 20, 13.36475048158089 19.12140071446215, 16.72950096316178 16.72950096316178, " +
                "19.12140071446215 13.36475048158089, 20 10, 19.12140071446215 6.63524951841911, " +
                "16.72950096316178 3.2704990368382196, 13.36475048158089 0.8785992855378488, " +
                "10 0, 6.63524951841911 0.8785992855378488, 3.2704990368382196 3.2704990368382196, " +
                "0.8785992855378488 6.63524951841911, 0 10, 0.8785992855378488 13.36475048158089, " +
                "3.2704990368382196 16.72950096316178, 6.63524951841911 19.12140071446215, " +
                "10 20))", polygon.wkt)
    }

    @Test void boundsCreateArc() {
        BoundsRecipes recipes = new BoundsRecipes()
        LineString lineString = recipes.boundsCreateArc()
        assertEquals("LINESTRING (17.071067811865476 17.071067811865476, 16.462992378609407 17.630840681998066, " +
                "15.810768154019383 18.13848717270195, 15.11885049089601 18.590539543698853, " +
                "14.391965888473703 18.983909818919788, 13.635079705638297 19.31591088051279, " +
                "12.853362242491052 19.58427482458253, 12.052153421956344 19.787168453273544, " +
                "11.236926312693475 19.92320579737045, 10.413249742488134 19.99145758387301, " +
                "9.586750257511868 19.99145758387301, 8.763073687306525 19.92320579737045, " +
                "7.9478465780436585 19.787168453273544, 7.146637757508948 19.58427482458253, " +
                "6.364920294361703 19.31591088051279, 5.6080341115262975 18.98390981891979, " +
                "4.881149509103989 18.590539543698853, 4.1892318459806175 18.13848717270195, " +
                "3.537007621390593 17.630840681998066, 2.9289321881345254 17.071067811865476)", lineString.wkt)
    }

    @Test void boundsCreateArcPolygon() {
        BoundsRecipes recipes = new BoundsRecipes()
        Polygon polygon = recipes.boundsCreateArcPolygon()
        assertEquals("POLYGON ((10 10, 17.071067811865476 17.071067811865476, 16.462992378609407 17.630840681998066, " +
                "15.810768154019383 18.13848717270195, 15.11885049089601 18.590539543698853, 14.391965888473703 18.983909818919788, " +
                "13.635079705638297 19.31591088051279, 12.853362242491052 19.58427482458253, 12.052153421956344 19.787168453273544, " +
                "11.236926312693475 19.92320579737045, 10.413249742488134 19.99145758387301, 9.586750257511868 19.99145758387301, " +
                "8.763073687306525 19.92320579737045, 7.9478465780436585 19.787168453273544, 7.146637757508948 19.58427482458253, " +
                "6.364920294361703 19.31591088051279, 5.6080341115262975 18.98390981891979, 4.881149509103989 18.590539543698853, " +
                "4.1892318459806175 18.13848717270195, 3.537007621390593 17.630840681998066, 2.9289321881345254 17.071067811865476, " +
                "10 10))", polygon.wkt)
    }

    @Test void boundsCreateSineStar() {
        BoundsRecipes recipes = new BoundsRecipes()
        Polygon polygon = recipes.boundsCreateSineStar()
        assertNotNull(polygon)
        assertFalse(polygon.isEmpty())
    }

    @Test void boundsCreateHexagon() {
        BoundsRecipes recipes = new BoundsRecipes()
        Polygon polygon = recipes.boundsCreateHexagon()
        assertEquals("POLYGON ((5 0, 15 0, 20 10, 15 20, 5 20, 0 10, 5 0))", polygon.wkt)
    }
    
}
