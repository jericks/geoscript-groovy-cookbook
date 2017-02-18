package geoscript.cookbook

import geoscript.geom.*
import geoscript.geom.io.Reader
import org.junit.Test
import static org.junit.Assert.*

class GeometryRecipesTest {

    @Test void createPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        Point point = recipes.createPoint()
        assertEquals(new Point(-123,46), point)
    }

    @Test void createLineStringFromCoordinates() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.createLineStringFromCoordinates()
        assertEquals("LINESTRING (3.1982421875 43.1640625, 6.7138671875 49.755859375, 9.7021484375 42.5927734375, 15.3271484375 53.798828125)", lineString.wkt)
    }

    @Test void createPolygon() {
        GeometryRecipes recipes = new GeometryRecipes()
        Polygon polygon = recipes.createPolygon()
        assertEquals("POLYGON ((-101.35986328125 47.754097979680026, -101.5576171875 46.93526088057719, -100.12939453125 46.51351558059737, -99.77783203125 47.44294999517949, -100.45898437499999 47.88688085106901, -101.35986328125 47.754097979680026))", polygon.wkt)
    }

    @Test void createMultiPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPoint multiPoint = recipes.createMultiPoint()
        assertEquals("MULTIPOINT ((-122.3876953125 47.5820839916191), (-122.464599609375 47.25686404408872), (-122.48382568359374 47.431803338643334))", multiPoint.wkt)
    }

    @Test void createMultiLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiLineString multiLineString = recipes.createMultiLineString()
        assertEquals("MULTILINESTRING ((-122.3822021484375 47.57837853860192, -122.32452392578125 47.48380086737799), (-122.32452392578125 47.48380086737799, -122.29705810546874 47.303447043862626), (-122.29705810546874 47.303447043862626, -122.42889404296875 47.23262467463881))", multiLineString.wkt)
    }

    @Test void createMultiPolygon() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPolygon multiPolygon = recipes.createMultiPolygon()
        assertEquals("MULTIPOLYGON (((-122.2723388671875 47.818687628247105, -122.37945556640624 47.66168780332917, -121.95373535156249 47.67093619422418, -122.2723388671875 47.818687628247105)), ((-122.76672363281249 47.42437092240516, -122.76672363281249 47.59505101193038, -122.52227783203125 47.59505101193038, -122.52227783203125 47.42437092240516, -122.76672363281249 47.42437092240516)), ((-122.20367431640624 47.543163654317304, -122.3712158203125 47.489368981370724, -122.33276367187499 47.35371061951363, -122.11029052734374 47.3704545156932, -122.08831787109375 47.286681888764214, -122.28332519531249 47.2270293988673, -122.2174072265625 47.154237057576594, -121.904296875 47.32579231609051, -122.06085205078125 47.47823216312885, -122.20367431640624 47.543163654317304)))", multiPolygon.wkt)
    }

    @Test void createCircularString() {
        GeometryRecipes recipes = new GeometryRecipes()
        CircularString circularString = recipes.createCircularString()
        assertEquals("CIRCULARSTRING (-122.464599609375 47.247542522268006, -122.03613281249999 47.37789454155521, -122.37670898437499 47.58393661978134)", circularString.curvedWkt)
    }

    @Test void createCircularRing() {
        GeometryRecipes recipes = new GeometryRecipes()
        CircularRing circularRing = recipes.createCircularRing()
        assertEquals("CIRCULARSTRING (-118.47656249999999 41.508577297439324, -109.6875 57.51582286553883, -93.8671875 42.032974332441405, -62.57812500000001 30.14512718337613, -92.10937499999999 7.36246686553575, -127.265625 14.604847155053898, -118.47656249999999 41.508577297439324)", circularRing.curvedWkt)
    }

    @Test void createCompoundCurve() {
        GeometryRecipes recipes = new GeometryRecipes()
        CompoundCurve compoundCurve = recipes.createCompoundCurve()
        assertEquals("COMPOUNDCURVE (" +
                "CIRCULARSTRING (27.0703125 23.885837699862005, 5.9765625 40.17887331434696, 22.5 47.98992166741417), " +
                "(22.5 47.98992166741417, 71.71875 49.15296965617039), " +
                "CIRCULARSTRING (71.71875 49.15296965617039, 81.5625 39.36827914916011, 69.9609375 24.5271348225978)" +
                ")", compoundCurve.curvedWkt)
    }

    @Test void createCompoundRing() {
        GeometryRecipes recipes = new GeometryRecipes()
        CompoundRing compoundRing = recipes.createCompoundRing()
        assertEquals("COMPOUNDCURVE (" +
                "CIRCULARSTRING (27.0703125 23.885837699862005, 5.9765625 40.17887331434696, 22.5 47.98992166741417), " +
                "(22.5 47.98992166741417, 71.71875 49.15296965617039), " +
                "CIRCULARSTRING (71.71875 49.15296965617039, 81.5625 39.36827914916011, 69.9609375 24.5271348225978), " +
                "(69.9609375 24.5271348225978, 27.0703125 23.885837699862005)" +
                ")", compoundRing.curvedWkt)
    }

    @Test void createBounds() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.createBounds()
        assertEquals("(-127.265,43.068,-113.554,50.289,EPSG:4326)", bounds.toString())
    }

    @Test void createBoundsNoProjection() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.createBoundsNoProjection()
        assertEquals("(-127.265,43.068,-113.554,50.289,EPSG:4326)", bounds.toString())
    }

    @Test void createBoundsFromStringWithCommas() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.createBoundsFromStringWithCommas()
        assertEquals("(-127.265,43.068,-113.554,50.289,EPSG:4326)", bounds.toString())
    }

    @Test void createBoundsFromStringWithSpaces() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.createBoundsFromStringWithSpaces()
        assertEquals("(12.919921874999998,40.84706035607122,15.99609375,41.77131167976407,EPSG:4326)", bounds.toString())
    }

    @Test void expandByBounds() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.expandByBounds()
        assertEquals("(-137.365,32.967999999999996,-103.45400000000001,60.389,EPSG:4326)", bounds.toString())
    }

    @Test void expandBounds() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.expandBounds()
        assertEquals("(8.4375,31.952162238024975,30.937499999999996,46.07323062540835,EPSG:4326)", bounds.toString())
    }

    @Test void scaleBounds() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.scaleBounds()
        assertEquals("(-134.1205,39.457499999999996,-106.6985,53.8995)", bounds.toString())
    }

    @Test void reprojectBounds() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.reprojectBounds()
        assertEquals("(1147444.7684517875,703506.223164177,1155828.120242509,711367.9403610165,EPSG:2927)", bounds.toString())
    }

    @Test void boundsGetGeometry() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.boundsGetGeometry()
        assertEquals("POLYGON ((-122.485 47.246, -122.485 47.267, -122.452 47.267, -122.452 47.246, -122.485 47.246))", geometry.wkt)
    }

    @Test void boundsGetPolygon() {
        GeometryRecipes recipes = new GeometryRecipes()
        Polygon polygon = recipes.boundsGetPolygon()
        assertEquals("POLYGON ((-122.485 47.246, -122.485 47.267, -122.452 47.267, -122.452 47.246, -122.485 47.246))", polygon.wkt)
    }

    @Test void boundsGetCorners() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.boundsGetCorners()
        assertEquals(4, points.size())
        assertEquals("GEOMETRYCOLLECTION (POINT (-122.485 47.246), POINT (-122.485 47.267), POINT (-122.452 47.267), POINT (-122.452 47.246))", new GeometryCollection(points).wkt)
    }

    @Test void boundsTile() {
        GeometryRecipes recipes = new GeometryRecipes()
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
        GeometryRecipes recipes = new GeometryRecipes()
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
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> result = recipes.boundsIsEmpty()
        assertFalse(result.bounds)
        assertTrue(result.emptyBounds)
    }

    @Test void bufferPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.bufferPoint()
        assertTrue(geom instanceof Polygon)
    }

    @Test void bufferLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.bufferLineString()
        assertTrue(geometries.size() > 0)
        geometries.each { Geometry g -> assertNotNull(g) }
    }

    @Test void bufferLineStringSingleSided() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.bufferLineStringSingleSided()
        assertTrue(geometries.size() > 0)
        geometries.each { Geometry g -> assertNotNull(g) }
    }

    @Test void bounds() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.bounds()
        assertNotNull(bounds)
    }

    @Test void getBoundsProperties() {
        GeometryRecipes recipes = new GeometryRecipes()
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
        GeometryRecipes recipes = new GeometryRecipes()
        Map results = recipes.boundsContainsBounds()
        assertTrue(results.bounds2)
        assertFalse(results.bounds3)
    }

    @Test void boundsContainsPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map results = recipes.boundsContainsPoint()
        assertTrue(results.point1)
        assertFalse(results.point2)
    }

    @Test void boundsIntersectsBounds() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map results = recipes.boundsIntersectsBounds()
        assertFalse(results.bounds2)
        assertTrue(results.bounds3)
    }

    @Test void boundsIntersection() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.boundsIntersection()
        assertEquals("(-95.885,46.765,-95.839,46.792)", bounds.toString())
    }

    @Test void boundsGetPolygonGridByColumnsAndRows() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.boundsGetPolygonGridByColumnsAndRows()
        assertTrue(geometry instanceof GeometryCollection)
        assertEquals(20, geometry.numGeometries)
    }

    @Test void boundsGeneratePointGridByColumnsAndRows() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.boundsGeneratePointGridByColumnsAndRows()
        assertTrue(geometry instanceof GeometryCollection)
        assertEquals(80, geometry.numGeometries)
    }

    @Test void boundsGetCircleGridByCellWidthAndHeight() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.boundsGetCircleGridByCellWidthAndHeight()
        assertTrue(geometry instanceof GeometryCollection)
        assertEquals(15, geometry.numGeometries)
    }

    @Test void boundsGetHexagonGridByColumnsAndRows() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.boundsGetHexagonGridByColumnsAndRows()
        assertTrue(geometry instanceof GeometryCollection)
        assertEquals(15, geometry.numGeometries)
    }

    @Test void boundsGetHexagonInvGridByColumnsAndRows() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.boundsGetHexagonInvGridByColumnsAndRows()
        assertTrue(geometry instanceof GeometryCollection)
        assertEquals(25, geometry.numGeometries)
    }

    @Test void boundsCreateRectangle() {
        GeometryRecipes recipes = new GeometryRecipes()
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
        GeometryRecipes recipes = new GeometryRecipes()
        Polygon polygon = recipes.boundsCreateEllipse()
        assertEquals("POLYGON ((20 10, 19.510565162951536 13.090169943749475, 18.090169943749473 15.877852522924732, " +
                "15.877852522924732 18.090169943749473, 13.090169943749475 19.510565162951536, 10 20, " +
                "6.909830056250526 19.510565162951536, 4.12214747707527 18.090169943749473, " +
                "1.9098300562505273 15.877852522924734, 0.4894348370484654 13.090169943749475, 0 10.000000000000002, " +
                "0.4894348370484636 6.909830056250527, 1.9098300562505255 4.12214747707527, " +
                "4.122147477075267 1.9098300562505273, 6.909830056250525 0.4894348370484654, 9.999999999999998 0, " +
                "13.090169943749473 0.4894348370484636, 15.87785252292473 1.9098300562505237, " +
                "18.090169943749473 4.122147477075266, 19.510565162951536 6.909830056250524, " +
                "20 10))", polygon.wkt)
    }

    @Test void boundsCreateSquircle() {
        GeometryRecipes recipes = new GeometryRecipes()
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
        GeometryRecipes recipes = new GeometryRecipes()
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
        GeometryRecipes recipes = new GeometryRecipes()
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
        GeometryRecipes recipes = new GeometryRecipes()
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
        GeometryRecipes recipes = new GeometryRecipes()
        Polygon polygon = recipes.boundsCreateSineStar()
        assertEquals("POLYGON ((20 10, 14.755282581475768 11.545084971874736, 10 10, 12.938926261462365 14.045084971874736, " +
                "13.090169943749475 19.510565162951536, 10 15, 10 10, 7.061073738537635 14.045084971874736, " +
                "1.9098300562505273 15.877852522924734, 5.244717418524233 11.545084971874738, 10 10, " +
                "5.244717418524233 8.454915028125264, 1.9098300562505255 4.12214747707527, " +
                "7.061073738537633 5.954915028125264, 10 10, 9.999999999999998 5.000000000000001, " +
                "13.090169943749473 0.4894348370484636, 12.938926261462365 5.954915028125262, " +
                "10 10, 14.755282581475766 8.454915028125262, 20 10))", polygon.wkt)
    }

    @Test void boundsCreateHexagon() {
        GeometryRecipes recipes = new GeometryRecipes()
        Polygon polygon = recipes.boundsCreateHexagon()
        assertEquals("POLYGON ((5 0, 15 0, 20 10, 15 20, 5 20, 0 10, 5 0))", polygon.wkt)
    }

    @Test void getArea() {
        GeometryRecipes recipes = new GeometryRecipes()
        double area = recipes.getArea()
        assertEquals(62.402, area, 0.01)
    }

    @Test void getLength() {
        GeometryRecipes recipes = new GeometryRecipes()
        double length = recipes.getLength()
        assertEquals(23.247, length, 0.01)
    }

    @Test void createSierpinskiCarpet() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.createSierpinskiCarpet()
        assertNotNull geometry
    }

    @Test void createKochSnowflake() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.createKochSnowflake()
        assertNotNull geometry
    }

    @Test void createRandomPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.createRandomPoints()
        assertNotNull geometry
        assertEquals(100, geometry.getNumGeometries())
    }

    @Test void createRandomPointsInGrid() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.createRandomPointsInGrid()
        assertNotNull geometry
        assertEquals(100, geometry.getNumGeometries())
    }

    // IO

    @Test void getGeometryReaders() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Reader> readers = recipes.getGeometryReaders()
        assertTrue(readers.size() > 0)
    }

    @Test void getGeometryWriters() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Writer> writers = recipes.getGeometryWriters()
        assertTrue(writers.size() > 0)
    }

    @Test void findGeometryReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.findGeometryReader()
        assertEquals("POINT (-123.15 46.237)", geometry.wkt)
    }

    @Test void findGeometryWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String str = recipes.findGeometryWriter()
        assertEquals('{"type":"Point","coordinates":[-122.45,43.21]}', str)
    }

    // WKT

    @Test void readGeometryFromWKTReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromWKTReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromWKT() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromWKT()
        assertEquals("LINESTRING (3.198 43.164, 6.7138 49.755, 9.702 42.592, 15.327 53.798)", geom.wkt)
    }

    @Test void writeGeometryToWKT() {
        GeometryRecipes recipes = new GeometryRecipes()
        String wkt = recipes.writeGeometryToWKT()
        assertEquals("POINT (-123.15 46.237)", wkt)
    }

    @Test void writeGeometryToWKTUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String wkt = recipes.writeGeometryToWKTUsingWriter()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", wkt)
    }

    @Test void readGeometryFromGeoJSONReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGeoJSONReader()
        assertEquals('{"type":"Point","coordinates":[-123.15,46.237]}', geom.geoJSON)
    }

    @Test void readGeometryFromGeoJSON() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGeoJSON()
        assertEquals('{"type":"LineString","coordinates":[[3.198,43.164],[6.713,49.755],[9.702,42.592],[15.32,53.798]]}', geom.geoJSON)
    }

    @Test void writeGeometryToGeoJSON() {
        GeometryRecipes recipes = new GeometryRecipes()
        String json = recipes.writeGeometryToGeoJSON()
        assertEquals('{"type":"Point","coordinates":[-123.15,46.237]}', json)
    }

    @Test void writeGeometryToGeoJSONUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String json = recipes.writeGeometryToGeoJSONUsingWriter()
        assertEquals('{"type":"LineString","coordinates":[[3.198,43.164],[6.713,49.755],[9.702,42.592],[15.32,53.798]]}', json)
    }
}