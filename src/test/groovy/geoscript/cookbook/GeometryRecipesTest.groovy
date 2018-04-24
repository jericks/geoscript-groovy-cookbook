package geoscript.cookbook

import com.vividsolutions.jts.geom.IntersectionMatrix
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

    @Test void createGeometryCollection() {
        GeometryRecipes recipes = new GeometryRecipes()
        GeometryCollection geometryCollection = recipes.createGeometryCollection()
        assertEquals("GEOMETRYCOLLECTION (" +
                "LINESTRING (-157.044 58.722, -156.461 58.676), " +
                "POINT (-156.648 58.739), " +
                "POLYGON ((-156.395 58.7083, -156.412 58.6026, -155.874 58.5825, " +
                "-155.313 58.4822, -155.385 58.6655, -156.203 58.7368, -156.395 58.7083)), " +
                "POINT (-156.741 58.582))", geometryCollection.wkt)
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

    @Test void within() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.within()
        assertTrue(results["1within2"])
        assertFalse(results["1within3"])
    }

    @Test void touches() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.touches()
        assertTrue(results["touches_12"])
        assertFalse(results["touches_13"])
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

    @Test void getGeometryType() {
        GeometryRecipes recipes = new GeometryRecipes()
        String type = recipes.getGeometryType()
        assertEquals("Point", type)
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

    @Test void crosses() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String, Boolean> results = recipes.crosses()
        assertFalse(results["12"])
        assertFalse(results["13"])
        assertTrue(results["23"])
    }

    @Test void difference() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.difference()
        assertEquals("POLYGON ((-122.11534856491807 46.59496055948802, " +
                "-122.64 46.995, -121.915 47.39, " +
                "-121.409 47.413, -120.981 47.316, " +
                "-121.15214608098509 46.82269659010183, " +
                "-121.541 46.995, " +
                "-122.11534856491807 46.59496055948802))", geometry.wkt)
    }

    @Test void symDifference() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.symDifference()
        assertEquals("MULTIPOLYGON (((-122.11534856491807 46.59496055948802, -122.64 46.995, -121.915 47.39, " +
                "-121.409 47.413, -120.981 47.316, -121.15214608098509 46.82269659010183, " +
                "-121.541 46.995, -122.11534856491807 46.59496055948802)), ((-122.11534856491807 46.59496055948802, " +
                "-121.739 46.308, -121.168 46.777, -121.15214608098509 46.82269659010183, -120.794 46.664, " +
                "-120.959 46.096, -121.937 45.89, -122.2 46.536, -122.11534856491807 46.59496055948802)))", geometry.wkt)
    }

    @Test void disjoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String, Boolean> results = recipes.disjoint()
        assertFalse(results["12"])
        assertTrue(results["13"])
        assertTrue(results["23"])
    }

    @Test void distance() {
        GeometryRecipes recipes = new GeometryRecipes()
        double distance = recipes.distance()
        assertEquals(0.37694827231332195, distance, 0.001)
    }

    @Test void boundary() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.boundary()
        assertEquals("MULTILINESTRING (" +
                "(-121.915 47.39, -122.64 46.995, -121.739 46.308, -121.168 46.777, -120.981 47.316, " +
                "-121.409 47.413, -121.915 47.39), " +
                "(-122.255 46.935, -121.992 46.935, -121.992 47.1, -122.255 47.1, -122.255 46.935), " +
                "(-121.717 46.777, -121.398 46.777, -121.398 47.002, -121.717 47.002, -121.717 46.777" +
                "))", geometry.wkt)
    }

    @Test void centroid() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.centroid()
        assertEquals("POINT (-120.43206854809475 47.34584003114768)", geometry.wkt)
    }

    @Test void interiorPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.interiorPoint()
        assertEquals("POINT (-121.02232180851064 47.343500000000006)", geometry.wkt)
    }

    @Test void getNumGeometries() {
        GeometryRecipes recipes = new GeometryRecipes()
        int number = recipes.getNumGeometries()
        assertEquals(3, number)
    }

    @Test void getGeometryN() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.getGeometryN()
        assertEquals(3, geometries.size())
    }

    @Test void getGeometries() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.getGeometries()
        assertEquals(3, geometries.size())
    }

    @Test void getNumPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        int number = recipes.getNumPoints()
        assertEquals(5, number)
    }

    @Test void union() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.union()
        assertEquals("POLYGON ((-122.11534856491807 46.59496055948802, -122.64 46.995, -121.915 47.39, -121.409 47.413, -120.981 47.316, -121.15214608098509 46.82269659010183, -120.794 46.664, -120.959 46.096, -121.937 45.89, -122.2 46.536, -122.11534856491807 46.59496055948802))", geom.wkt)
    }

    @Test void intersection() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.intersection()
        assertEquals("POLYGON ((-121.15214608098509 46.82269659010183, -121.168 46.777, -121.739 46.308, -122.11534856491807 46.59496055948802, -121.541 46.995, -121.15214608098509 46.82269659010183))", geom.wkt)
    }

    @Test void intersects() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String, Boolean> results = recipes.intersects()
        assertTrue(results["12"])
        assertFalse(results["13"])
        assertFalse(results["23"])
    }

    @Test void overlaps() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String, Boolean> results = recipes.overlaps()
        assertTrue(results["12"])
        assertFalse(results["13"])
        assertFalse(results["23"])
    }

    @Test void getOctagonalEnvelope() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getOctagonalEnvelope()
        assertEquals("POLYGON ((-122.3712158203125 47.308045651326594, -122.3712158203125 47.48936898137072, -122.3174211473659 47.543163654317304, -122.1216682132268 47.543163654317304, -121.904296875 47.325792316090514, -122.07585213351392 47.154237057576594, -122.2174072265625 47.154237057576594, -122.3712158203125 47.308045651326594))", geom.wkt)
    }

    @Test void getMinimumRectangle() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getMinimumRectangle()
        assertEquals("POLYGON ((-122.2257331947806 47.63877911563473, -122.46386617575001 47.394217398870474, -122.14242985596937 47.08123059932578, -121.90429687499996 47.32579231609004, -122.2257331947806 47.63877911563473))", geom.wkt)
    }

    @Test void getMinimumBoundingCircle() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getMinimumBoundingCircle()
        assertEquals("POLYGON ((-121.89557442425158 47.390985711028655, -121.90033959135305 47.34260415752205, -121.91445196995807 47.29608187998401, -121.93736922927117 47.25320670345034, -121.9682106718691 47.21562629691054, -122.0057910784089 47.18478485431261, -122.04866625494257 47.16186759499951, -122.0951885324806 47.14775521639448, -122.14357008598722 47.14299004929302, -122.19195163949384 47.14775521639448, -122.23847391703187 47.16186759499951, -122.28134909356554 47.18478485431261, -122.31892950010534 47.21562629691054, -122.34977094270327 47.25320670345034, -122.37268820201638 47.29608187998401, -122.3868005806214 47.34260415752205, -122.39156574772286 47.390985711028655, -122.3868005806214 47.439367264535264, -122.37268820201638 47.4858895420733, -122.34977094270326 47.52876471860697, -122.31892950010534 47.56634512514677, -122.28134909356554 47.5971865677447, -122.23847391703187 47.620103827057804, -122.19195163949382 47.63421620566283, -122.14357008598722 47.63898137276429, -122.0951885324806 47.63421620566283, -122.04866625494257 47.620103827057804, -122.0057910784089 47.5971865677447, -121.9682106718691 47.56634512514677, -121.93736922927117 47.52876471860697, -121.91445196995807 47.4858895420733, -121.90033959135305 47.439367264535264, -121.89557442425158 47.390985711028655))", geom.wkt)
    }

    @Test void getMinimumDiameter() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getMinimumDiameter()
        assertEquals("LINESTRING (-121.9792742455931 47.39879877434085, -122.2174072265625 47.154237057576594)", geom.wkt)
    }

    @Test void getMinimumClearance() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getMinimumClearance()
        assertEquals("LINESTRING (-122.08831787109375 47.286681888764214, -122.06231070736864 47.23921547912073)", geom.wkt)
    }

    @Test void offset() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geoms = recipes.offset()
        assertEquals("LINESTRING (2.1391631297989706 43.72868086766145, 5.65416312979897 50.319680867661454, 5.778377087150159 50.50764866357158, 5.935537150898572 50.669085071679376, 6.120103319712697 50.79829934654647, 6.325569508034951 50.89073659529652, 6.544692890113443 50.94313834075062, 6.769749214636951 50.95365738500962, 6.992805089955028 50.92192292446214, 7.205997641640225 50.84905362086928, 7.4018116842692585 50.737618167761084, 7.573344636925451 50.591544742206665, 7.714549843967106 50.41598253384599, 7.820449723835849 50.217120232381035, 9.801865449076663 45.46874925884475, 14.247262358720448 54.33580475358813)", geoms[0].wkt)
        assertEquals("LINESTRING (5.315673740402058 42.0346382646771, 6.448010547942489 44.15789058288449, 7.487100552328302 41.66775953523793, 7.701682600838272 41.265828705403834, 7.988357285657427 40.911693882775936, 8.336792679562427 40.61811829832088, 8.734430962409043 40.395682591815266, 9.166941012486353 40.252403479255264, 9.618734908720752 40.193444825613916, 10.073529728681235 40.22093153605678, 10.514934394849579 40.33387297308797, 10.927040418864596 40.52819865971841, 11.294995252964318 40.79690498189284, 11.605537584696997 41.13030760293216, 11.847475282559104 41.51639049282375, 17.465475282559105 52.722390492823756)", geoms[1].wkt)
    }

    @Test void getDimension() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String, Integer> dimensions = recipes.getDimension()
        assertEquals(0, dimensions.point)
        assertEquals(1, dimensions.lineString)
        assertEquals(2, dimensions.polygon)
    }

    @Test void isEmpty() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isEmpty()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void isRectangle() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isRectangle()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void isSimple() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isSimple()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void isValid() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isValid()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void isCurved() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isCurved()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void isWithinDistance() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isWithinDistance()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void getDelaunayTriangleDiagram() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getDelaunayTriangleDiagram()
        assertNotNull(geom)
    }

    @Test void getVoronoiDiagram() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getVoronoiDiagram()
        assertNotNull(geom)
    }

    @Test void normalize() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.normalize()
        assertEquals("POLYGON ((1 3, 2 4, 4 4, 6 3, 6 1, 2 1, 1 3))", geom.wkt)
    }

    @Test void norm() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Geometry> geoms = recipes.norm()
        assertEquals("POLYGON ((2 4, 1 3, 2 1, 6 1, 6 3, 4 4, 2 4))", geoms.geometry.wkt)
        assertEquals("POLYGON ((1 3, 2 4, 4 4, 6 3, 6 1, 2 1, 1 3))", geoms.normalizedGeometry.wkt)
    }

    @Test void smooth() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.smooth()
        assertNotNull(geom)
    }

    @Test void relateIntersectionMatrix() {
        GeometryRecipes recipes = new GeometryRecipes()
        IntersectionMatrix matrix = recipes.relateIntersectionMatrix()
        assertEquals("212101212", matrix.toString())
    }

    @Test void relate() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.relate()
        assertTrue(values[0])
        assertFalse(values[1])
        assertFalse(values[2])
    }

    @Test void densify() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.densify()
        assertEquals(50, geom.numPoints)
    }

    @Test void simplify() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.simplify()
        assertEquals(5, geom.numPoints)
    }

    @Test void simplifyPreservingTopology() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.simplifyPreservingTopology()
        assertEquals(11, geom.numPoints)
    }

    @Test void translate() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.translate()
        assertFalse geometries[0].equals(geometries[1])
    }

    @Test void scaleXY() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.scaleXY()
        assertFalse geometries[0].equals(geometries[1])
    }

    @Test void scaleXYAroundPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.scaleXYAroundPoint()
        assertFalse geometries[0].equals(geometries[1])
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

    // Geobuf

    @Test void readGeometryFromGeobufReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGeobufReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromGeobuf() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGeobuf()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToGeobuf() {
        GeometryRecipes recipes = new GeometryRecipes()
        String geobuf = recipes.writeGeometryToGeobuf()
        assertEquals("10021806320c08001a08dffab87590958c2c", geobuf)
    }

    @Test void writeGeometryToGeobufUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String geobuf = recipes.writeGeometryToGeobufUsingWriter()
        assertEquals("10021806322408021a20e0b08603c0859529f089ad03b0c8a40690efec02efb1ea06a0e5ad05e0f5d70a", geobuf)
    }

    // GML 2

    @Test void readGeometryFromGml2Reader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGml2Reader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromGml2() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGml2()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToGml2() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gml = recipes.writeGeometryToGml2()
        assertEquals("<gml:Point><gml:coordinates>-123.15,46.237</gml:coordinates></gml:Point>", gml)
    }

    @Test void writeGeometryToGml2UsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gml = recipes.writeGeometryToGml2UsingWriter()
        assertEquals("<gml:LineString><gml:coordinates>3.198,43.164 6.713,49.755 9.702,42.592 15.32,53.798</gml:coordinates></gml:LineString>", gml)
    }

    // GML 3

    @Test void readGeometryFromGml3Reader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGml3Reader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromGml3() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGml3()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToGml3() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gml = recipes.writeGeometryToGml3()
        assertEquals("<gml:Point><gml:pos>-123.15 46.237</gml:pos></gml:Point>", gml)
    }

    @Test void writeGeometryToGml3UsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gml = recipes.writeGeometryToGml3UsingWriter()
        assertEquals("<gml:LineString><gml:posList>3.198 43.164 6.713 49.755 9.702 42.592 15.32 53.798</gml:posList></gml:LineString>", gml)
    }

    // GPX

    @Test void readGeometryFromGpxReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGpxReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromGpx() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGpx()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToGpx() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gpx = recipes.writeGeometryToGpx()
        assertEquals("<wpt lat='46.237' lon='-123.15'/>", gpx)
    }

    @Test void writeGeometryToGpxUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gpx = recipes.writeGeometryToGpxUsingWriter()
        assertEquals("<rte><rtept lat='43.164' lon='3.198' /><rtept lat='49.755' lon='6.713' /><rtept lat='42.592' lon='9.702' /><rtept lat='53.798' lon='15.32' /></rte>", gpx)
    }

    // GeoRSS

    @Test void readGeometryFromGeoRSSReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGeoRSSReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void writeGeometryToGeoRSSUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String georss = recipes.writeGeometryToGeoRSSUsingWriter()
        assertEquals("<georss:line>43.164 3.198 49.755 6.713 42.592 9.702 53.798 15.32</georss:line>", georss)
    }

    // Google Polyline

    @Test void readGeometryFromGooglePolyline() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGooglePolyline()
        assertEquals("LINESTRING (-120.2 38.5, -120.95 40.7, -126.453 43.252)", geom.wkt)
    }

    @Test void writeGeometryToGooglePolyline() {
        GeometryRecipes recipes = new GeometryRecipes()
        String georss = recipes.writeGeometryToGooglePolyline()
        assertEquals("_nmfGoroRwhfg@womTv_vj@gxfQotkcAogha@", georss)
    }
}