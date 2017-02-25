package geoscript.cookbook

import geoscript.geom.*
import geoscript.geom.io.Reader
import org.junit.Test
import static org.junit.Assert.*

class GeometryRecipesTest {

    // Create Geometries

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

    // Geometry Operations

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

    @Test void contains() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.contains()
        assertTrue(results["1contains2"])
        assertFalse(results["1contains3"])
    }

    @Test void convexHull() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.convexHull()
        assertEquals("POLYGON ((-90.7031 34.016, -111.796 42.553, -119.882 47.279, -100.195 46.316, -90.7031 34.016))", geom.wkt)
    }

    @Test void covers() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.covers()
        assertTrue(results["1covers2"])
        assertFalse(results["1covers3"])
    }

    @Test void coveredBy() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.coveredBy()
        assertTrue(results["2coveredBy1"])
        assertFalse(results["3coveredBy1"])
    }


    @Test void bounds() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.bounds()
        assertNotNull(bounds)
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

    // Geometry IO

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

    // WKB

    @Test void readGeometryFromWKBReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromWKBReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromWKB() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromWKB()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToWKB() {
        GeometryRecipes recipes = new GeometryRecipes()
        String wkb = recipes.writeGeometryToWKB()
        assertEquals("0000000001C05EC9999999999A40471E5604189375", wkb)
    }

    @Test void writeGeometryToWKBUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String wkb = recipes.writeGeometryToWKBUsingWriter()
        assertEquals("000000000200000004400995810624DD2F404594FDF3B645A2401ADA1CAC0831274048E0A3D70A3D714023676C8B43958" +
                "140454BC6A7EF9DB2402EA3D70A3D70A4404AE624DD2F1AA0", wkb)

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

    // GeoJSON

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

    // KML

    @Test void readGeometryFromKMLReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromKMLReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromKML() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromKML()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToKML() {
        GeometryRecipes recipes = new GeometryRecipes()
        String kml = recipes.writeGeometryToKML()
        assertEquals("<Point><coordinates>-123.15,46.237</coordinates></Point>", kml)
    }

    @Test void writeGeometryToKMLUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String kml = recipes.writeGeometryToKMLUsingWriter()
        assertEquals("<LineString><coordinates>3.198,43.164 6.713,49.755 9.702,42.592 15.32,53.798</coordinates></LineString>", kml)

    }
}