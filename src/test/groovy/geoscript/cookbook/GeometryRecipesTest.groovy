package geoscript.cookbook

import org.locationtech.jts.geom.IntersectionMatrix
import geoscript.geom.*
import geoscript.geom.io.Reader
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.*

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

    @Test void createPolygonWithHoles() {
        GeometryRecipes recipes = new GeometryRecipes()
        Polygon polygon = recipes.createPolygonWithHoles()
        assertEquals("POLYGON ((-122.39138603210449 47.58659965790016, -122.41250038146973 47.57681522195182, -122.40305900573729 47.56523364515569, -122.38117218017578 47.56621817878201, -122.3712158203125 47.57235661809739, -122.37602233886717 47.584747123985615, -122.39138603210449 47.58659965790016), (-122.39859580993652 47.578957532923376, -122.40468978881836 47.57548347095205, -122.39593505859376 47.570271945800094, -122.3920726776123 47.57606249728773, -122.39859580993652 47.578957532923376), (-122.3836612701416 47.58156292813543, -122.38829612731934 47.57114056934196, -122.37456321716309 47.57420959047542, -122.37868309020995 47.58023129789275, -122.3836612701416 47.58156292813543))", polygon.wkt)
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

    // CircularRing

    @Test void circularRingCurvedWkt() {
        GeometryRecipes recipes = new GeometryRecipes()
        CircularRing ring = recipes.circularRingCurvedWkt()
        assertNotNull(ring)
        assertNotNull(ring.wkt)
        assertNotNull(ring.curvedWkt)
    }

    @Test void circularRingControlPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.circularRingControlPoints()
        assertEquals(7, points.size())
    }

    @Test void circularRingToLinear() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry linear = recipes.circularRingToLinear()
        assertNotNull(linear)
        assertTrue(linear instanceof LinearRing)
    }

    // CircularString

    @Test void circularStringCurvedWkt() {
        GeometryRecipes recipes = new GeometryRecipes()
        CircularString circularString = recipes.circularStringCurvedWkt()
        assertEquals("LINESTRING (-122.464599609375 47.247542522268006, -122.4550237920096 47.23410579860605, " +
                "-122.4348780833765 47.21113402059009, -122.41190630536055 47.19098831195699, -122.38650151145254 47.17401337136692, " +
                "-122.3590983847198 47.16049964475972, -122.33016580025833 47.15067835574435, -122.30019880260986 47.144717549298825, " +
                "-122.26971013541258 47.1427192164741, -122.2392214682153 47.144717549298825, -122.20925447056683 47.15067835574435, " +
                "-122.18032188610536 47.16049964475972, -122.15291875937262 47.17401337136692, -122.12751396546462 47.19098831195699, " +
                "-122.10454218744866 47.21113402059009, -122.08439647881556 47.23410579860605, -122.06742153822549 47.259510592514054, " +
                "-122.05390781161829 47.28691371924678, -122.04408652260291 47.31584630370827, -122.0381257161574 47.34581330135674, " +
                "-122.03612738333267 47.37630196855401, -122.03613281249999 47.37789454155521, -122.0381257161574 47.40679063575128, " +
                "-122.04408652260291 47.43675763339975, -122.05390781161829 47.46569021786124, -122.06742153822549 47.493093344593966, " +
                "-122.08439647881556 47.51849813850197, -122.10454218744866 47.54146991651793, -122.12751396546462 47.56161562515103, " +
                "-122.15291875937262 47.5785905657411, -122.18032188610536 47.5921042923483, -122.20925447056683 47.60192558136367, " +
                "-122.2392214682153 47.607886387809195, -122.26971013541258 47.60988472063392, -122.30019880260986 47.607886387809195, " +
                "-122.33016580025833 47.60192558136367, -122.3590983847198 47.5921042923483, -122.37670898437499 47.58393661978134)", circularString.wkt)
        assertEquals("CIRCULARSTRING (-122.464599609375 47.247542522268006, -122.03613281249999 47.37789454155521, " +
                "-122.37670898437499 47.58393661978134)", circularString.curvedWkt)
    }

    @Test void circularStringControlPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.circularStringControlPoints()
        assertEquals(3, points.size())
    }

    @Test void circularStringToLinear() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry linear = recipes.circularStringToLinear()
        assertNotNull(linear)
        assertTrue(linear instanceof LineString)
    }

    // CompoundCurve

    @Test void compoundCurveCurvedWkt() {
        GeometryRecipes recipes = new GeometryRecipes()
        CompoundCurve compoundCurve = recipes.compoundCurveCurvedWkt()
        assertNotNull(compoundCurve)
        assertNotNull(compoundCurve.wkt)
        assertNotNull(compoundCurve.curvedWkt)
    }

    @Test void compoundCurveComponents() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<LineString> lineStrings = recipes.compoundCurveComponents()
        assertEquals(3, lineStrings.size())
    }

    @Test void compoundCurveToLinear() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry linear = recipes.compoundCurveToLinear()
        assertNotNull(linear)
        assertTrue(linear instanceof LineString)
    }

    // CompoundRing

    @Test void compoundRingCurvedWkt() {
        GeometryRecipes recipes = new GeometryRecipes()
        CompoundRing compoundRing = recipes.compoundRingCurvedWkt()
        assertNotNull(compoundRing)
        assertNotNull(compoundRing.wkt)
        assertNotNull(compoundRing.curvedWkt)
    }

    @Test void compoundRingComponents() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<LineString> lineStrings = recipes.compoundRingComponents()
        assertEquals(4, lineStrings.size())
    }

    @Test void compoundRingToLinear() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry linear = recipes.compoundRingToLinear()
        assertNotNull(linear)
        assertTrue(linear instanceof LineString)
    }

    // Polygon

    @Test void polygonGetExteriorRing() {
        GeometryRecipes recipes = new GeometryRecipes()
        LinearRing ring = recipes.polygonGetExteriorRing()
        assertEquals("LINEARRING (-122.39138603210449 47.58659965790016, -122.41250038146973 47.57681522195182, " +
                "-122.40305900573729 47.56523364515569, -122.38117218017578 47.56621817878201, -122.3712158203125 47.57235661809739, " +
                "-122.37602233886717 47.584747123985615, -122.39138603210449 47.58659965790016)", ring.wkt)
    }

    @Test void polygonInteriorRing() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<LinearRing> rings = recipes.polygonInteriorRing()
        assertEquals(2, rings.size())
        assertEquals("LINEARRING (-122.39859580993652 47.578957532923376, -122.40468978881836 47.57548347095205, -122.39593505859376 47.570271945800094, -122.3920726776123 47.57606249728773, -122.39859580993652 47.578957532923376)", rings[0].wkt)
        assertEquals("LINEARRING (-122.3836612701416 47.58156292813543, -122.38829612731934 47.57114056934196, -122.37456321716309 47.57420959047542, -122.37868309020995 47.58023129789275, -122.3836612701416 47.58156292813543)", rings[1].wkt)
    }

    @Test void plusPolygons() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPolygon multiPolygon = recipes.plusPolygons()
        assertEquals("MULTIPOLYGON (((-122.2723388671875 47.818687628247105, -122.37945556640624 47.66168780332917, " +
                "-121.95373535156249 47.67093619422418, -122.2723388671875 47.818687628247105)), " +
                "((-122.76672363281249 47.42437092240516, -122.76672363281249 47.59505101193038, " +
                "-122.52227783203125 47.59505101193038, -122.52227783203125 47.42437092240516, " +
                "-122.76672363281249 47.42437092240516)))", multiPolygon.wkt)
    }

    @Test void splitPolygon() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPolygon multiPolygon = recipes.splitPolygon()
        assertNotNull(multiPolygon)
        assertEquals(2, multiPolygon.numGeometries)
    }

    // MultiPolygon

    @Test void multiPolygonPlus() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPolygon multiPolygon = recipes.multiPolygonPlus()
        assertNotNull(multiPolygon)
        assertEquals(3, multiPolygon.numGeometries)
    }

    @Test void multiPolygonSplit() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry splitGeometry = recipes.multiPolygonSplit()
        assertNotNull(splitGeometry)
        assertTrue(splitGeometry instanceof MultiPolygon)
        assertEquals(6, splitGeometry.numGeometries)
    }

    // LineString

    @Test void lineStringCreatePointsAlong() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPoint points = recipes.lineStringCreatePointsAlong()
        assertEquals("MULTIPOINT ((-122.39423990249632 47.57926150237904), (-122.39346678909047 47.579895770194476), " +
                "(-122.39269367568463 47.580530038009904), (-122.39192056227877 47.58116430582534), " +
                "(-122.39093889290324 47.58121554959838), (-122.38993889290325 47.58121554959838), " +
                "(-122.38893889290324 47.58121554959838), (-122.38793889290324 47.58121554959838), " +
                "(-122.38693889290325 47.58121554959838), (-122.3865496589065 47.58185547421352), " +
                "(-122.3865030562198 47.58285438771808), (-122.38645645353309 47.58385330122264), " +
                "(-122.38640985084639 47.5848522147272), (-122.38588972053832 47.58535499390333), " +
                "(-122.38488972053833 47.58535499390333), (-122.38388972053832 47.58535499390333))", points.wkt)
    }

    @Test void getStartPointFromLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Geometry> geoms = recipes.getStartPointFromLineString()
        assertTrue(geoms.lineString instanceof LineString)
        assertTrue(geoms.point instanceof Point)
        assertEquals("POINT (3.1982421875 43.1640625)", geoms.point.wkt)
    }

    @Test void getEndPointFromLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Geometry> geoms = recipes.getEndPointFromLineString()
        assertTrue(geoms.lineString instanceof LineString)
        assertTrue(geoms.point instanceof Point)
        assertEquals("POINT (15.3271484375 53.798828125)", geoms.point.wkt)
    }

    @Test void reverseLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Geometry> geoms = recipes.reverseLineString()
        assertTrue(geoms.lineString instanceof LineString)
        assertTrue(geoms.startPoint instanceof Point)
        assertEquals("POINT (3.1982421875 43.1640625)", geoms.startPoint.wkt)
        assertTrue(geoms.reversedLineString instanceof LineString)
        assertTrue(geoms.reversedStartPoint instanceof Point)
        assertEquals("POINT (15.3271484375 53.798828125)", geoms.reversedStartPoint.wkt)
    }

    @Test void isLineStringClosed() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Object> geoms = recipes.isLineStringClosed()
        assertTrue(geoms.lineString1 instanceof LineString)
        assertFalse(geoms.isClosed1)
        assertTrue(geoms.lineString2 instanceof LineString)
        assertTrue(geoms.isClosed2)
    }

    @Test void isLineStringRing() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Object> geoms = recipes.isLineStringRing()
        assertTrue(geoms.lineString1 instanceof LineString)
        assertFalse(geoms.isRing1)
        assertTrue(geoms.lineString2 instanceof LineString)
        assertTrue(geoms.isRing2)
    }

    @Test void closeLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Geometry> geoms = recipes.closeLineString()
        assertTrue(geoms.lineString instanceof LineString)
        assertTrue(geoms.linearRing instanceof LinearRing)
    }

    @Test void lineStringPlus() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiLineString multiLineString = recipes.lineStringPlus()
        assertEquals("MULTILINESTRING ((-122.39142894744873 47.5812734461813, " +
                "-122.38237380981445 47.58121554959838), " +
                "(-122.38640785217285 47.58552866972616, " +
                "-122.38670825958253 47.57837853860192))", multiLineString.wkt)
    }

    @Test void lineStringPlusPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.lineStringPlusPoint()
        assertEquals("LINESTRING (-122.39142894744873 47.5812734461813, " +
                "-122.38237380981445 47.58121554959838, " +
                "-122.38640785217285 47.58552866972616)", lineString.wkt)
    }

    @Test void lineStringAddPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.lineStringAddPoint()
        assertEquals("LINESTRING (-122.39142894744873 47.5812734461813, " +
                "-122.38640785217285 47.58552866972616, " +
                "-122.38237380981445 47.58121554959838)", lineString.wkt)
    }

    @Test void lineStringSetPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.lineStringSetPoint()
        assertEquals("LINESTRING (-122.39142894744873 47.5812734461813, " +
                "-122.38640785217285 47.58552866972616)", lineString.wkt)
    }

    @Test void lineStringRemovePoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.lineStringRemovePoint()
        assertEquals("LINESTRING (-122.39142894744873 47.5812734461813, " +
                "-122.38237380981445 47.58121554959838)", lineString.wkt)
    }

    @Test void lineStringNegative() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<LineString> lineStrings = recipes.lineStringNegative()
        assertEquals(3, lineStrings.size())
        assertEquals("LINESTRING (-122.39423990249632 47.57926150237904, -122.3918581008911 47.58121554959838, -122.38657951354979 47.58121554959838, -122.38638639450075 47.58535499390333, -122.38374710083008 47.58535499390333)", lineStrings[0].wkt)
        assertEquals("LINESTRING (-122.39423990249632 47.57926150237904, -122.3918581008911 47.58121554959838, -122.38657951354979 47.58121554959838, -122.38638639450075 47.58535499390333)", lineStrings[1].wkt)
        assertEquals("LINESTRING (-122.39423990249632 47.57926150237904, -122.3918581008911 47.58121554959838, -122.38657951354979 47.58121554959838)", lineStrings[2].wkt)
    }

    @Test void lineStringInterpolatePoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.lineStringInterpolatePoint()
        assertEquals(3, points.size())
        assertEquals("POINT (-122.39423990249632 47.57926150237904)", points[0].wkt)
        assertEquals("POINT (-122.38736758304911 47.58121554959838)", points[1].wkt)
        assertEquals("POINT (-122.38374710083008 47.58535499390333)", points[2].wkt)
    }

    @Test void lineStringLocatePoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Double> positions = recipes.lineStringLocatePoint()
        assertEquals(3, positions.size())
        println positions
        assertEquals(0.0, positions[0], 0.1)
        assertEquals(0.5, positions[1], 0.1)
        assertEquals(1.0, positions[2], 0.1)
    }

    @Test void lineStringPlacePoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.lineStringPlacePoint()
        assertEquals(2, points.size())
        assertEquals("POINT (-122.35835385210422 47.57160050493058)", points[0].wkt)
        assertEquals("POINT (-122.33858529358729 47.57150884009016)", points[1].wkt)
    }

    @Test void lineStringSubLine() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.lineStringSubLine()
        assertEquals("LINESTRING (-122.38994182839951 47.58121554959838, " +
                "-122.38657951354979 47.58121554959838, " +
                "-122.38650332982382 47.58284852310433)", lineString.wkt)
    }

    // MultiLineString

    @Test void multiLineStringPlus() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiLineString multiLineString =  recipes.multiLineStringPlus()
        assertEquals("MULTILINESTRING ((-122.3822021484375 47.57837853860192, -122.32452392578125 47.48380086737799), " +
                "(-122.32452392578125 47.48380086737799, -122.29705810546874 47.303447043862626), " +
                "(-122.29705810546874 47.303447043862626, -122.42889404296875 47.23262467463881))", multiLineString.wkt)
    }

    @Test void multiLineStringMerge() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiLineString multiLineString =  recipes.multiLineStringMerge()
        assertEquals("MULTILINESTRING ((-122.3822021484375 47.57837853860192, -122.32452392578125 47.48380086737799, " +
                "-122.29705810546874 47.303447043862626, " +
                "-122.42889404296875 47.23262467463881))", multiLineString.wkt)
    }

    @Test void multiLineStringCreatePointsAlong() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPoint points =  recipes.multiLineStringCreatePointsAlong()
        assertEquals(49, points.numGeometries)
    }

    // Point

    @Test void getPointXYZ() {
        GeometryRecipes recipes = new GeometryRecipes()
        Point point =  recipes.getPointXYZ()
        assertEquals(-122.38632, point.x, 0.001)
        assertEquals(47.58208, point.y, 0.001)
        assertEquals(101.45, point.z, 0.001)
    }

    @Test void plusPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPoint points =  recipes.plusPoint()
        assertEquals(2, points.numGeometries)
        assertEquals("POINT (-122.38632 47.58208)", points[0].wkt)
        assertEquals("POINT (-122.37001 47.55868)", points[1].wkt)
    }

    @Test void getAngleBetweenPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Double> angles =  recipes.getAngleBetweenPoints()
        assertEquals(-29.663413013476646, angles[0], 0.01)
        assertEquals(-0.517724224464100, angles[1], 0.01)
    }

    @Test void getAzimuthBetweenPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        double azimuth =  recipes.getAzimuthBetweenPoints()
        assertEquals(129.21026122904846, azimuth, 0.01)
    }

    @Test void plusMultiPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPoint points =  recipes.plusMultiPoint()
        assertEquals(3, points.numGeometries)
        assertEquals("POINT (-122.83813 47.05141)", points[0].wkt)
        assertEquals("POINT (-122.3822 47.58023)", points[1].wkt)
        assertEquals("POINT (-122.48657 47.271775)", points[2].wkt)
    }

    // Geometry Collection

    @Test void geometryCollectionPlus() {
        GeometryRecipes recipes = new GeometryRecipes()
        GeometryCollection geometryCollection =  recipes.geometryCollectionPlus()
        assertEquals(4, geometryCollection.numGeometries)
        assertEquals("POINT (-122.38654196262358 47.581211931059826)", geometryCollection[0].wkt)
        assertEquals("LINESTRING (-122.3865446448326 47.58118841055313, -122.38657146692276 47.58067638459562)", geometryCollection[1].wkt)
        assertEquals("POLYGON ((-122.38693356513977 47.58088445228483, -122.38672703504562 47.58088445228483, " +
                "-122.38672703504562 47.58096225129535, -122.38693356513977 47.58096225129535, " +
                "-122.38693356513977 47.58088445228483))", geometryCollection[2].wkt)
        assertEquals("POINT (-122.38718032836913 47.58121374032914)", geometryCollection[3].wkt)
    }

    @Test void geometryCollectionSlice() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries =  recipes.geometryCollectionSlice()
        assertEquals(2, geometries.size())
        assertEquals("POLYGON ((-122.38693356513977 47.58088445228483, -122.38672703504562 47.58088445228483, " +
                "-122.38672703504562 47.58096225129535, -122.38693356513977 47.58096225129535, " +
                "-122.38693356513977 47.58088445228483))", geometries[0].wkt)
        assertEquals("GEOMETRYCOLLECTION (POINT (-122.38654196262358 47.581211931059826), " +
                "LINESTRING (-122.3865446448326 47.58118841055313, -122.38657146692276 47.58067638459562))", geometries[1].wkt)
    }

    @Test void geometryCollectionNarrow() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries =  recipes.geometryCollectionNarrow()
        assertEquals(2, geometries.size())
        assertEquals("MULTIPOINT ((-122.38654196262358 47.581211931059826), (-122.38718032836913 47.58121374032914))", geometries[0].wkt)
        assertEquals("GEOMETRYCOLLECTION (POINT (-122.38654196262358 47.581211931059826), POLYGON ((-122.38693356513977 47.58088445228483, " +
                "-122.38672703504562 47.58088445228483, -122.38672703504562 47.58096225129535, -122.38693356513977 47.58096225129535, " +
                "-122.38693356513977 47.58088445228483)))", geometries[1].wkt)
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

    @Test void variableBufferTwo() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.variableBufferTwo()
        assertTrue(geometry instanceof Polygon)
    }

    @Test void variableBufferThree() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.variableBufferThree()
        assertTrue(geometry instanceof Polygon)
    }

    @Test void variableBufferSameNumber() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.variableBufferSameNumber()
        assertTrue(geometry instanceof Polygon)
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

    @Test void cascadeUnion() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.cascadeUnion()
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
        assertEquals("POINT (-121.00204734961912 47.479)", geometry.wkt)
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
        assertEquals("POLYGON ((-122.2257331947807 47.638779115634634, -122.46386617574994 47.39421739887034, -122.1424298559692 47.08123059932572, -121.90429687499996 47.325792316090016, -122.2257331947807 47.638779115634634))", geom.wkt)
    }

    @Test void getMinimumBoundingCircle() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getMinimumBoundingCircle()
        assertEquals("POLYGON ((-121.89557442425158 47.390985711028655, -121.90033959135305 47.34260415752205, -121.91445196995807 47.29608187998401, -121.93736922927117 47.25320670345034, -121.9682106718691 47.21562629691054, -122.0057910784089 47.18478485431261, -122.04866625494257 47.16186759499951, -122.0951885324806 47.14775521639448, -122.14357008598722 47.14299004929302, -122.19195163949384 47.14775521639448, -122.23847391703187 47.16186759499951, -122.28134909356554 47.18478485431261, -122.31892950010534 47.21562629691054, -122.34977094270327 47.25320670345034, -122.37268820201638 47.29608187998401, -122.3868005806214 47.34260415752205, -122.39156574772286 47.390985711028655, -122.3868005806214 47.439367264535264, -122.37268820201638 47.4858895420733, -122.34977094270327 47.52876471860697, -122.31892950010534 47.56634512514677, -122.28134909356554 47.5971865677447, -122.23847391703187 47.620103827057804, -122.19195163949384 47.63421620566283, -122.14357008598722 47.63898137276429, -122.0951885324806 47.63421620566283, -122.04866625494257 47.620103827057804, -122.0057910784089 47.5971865677447, -121.9682106718691 47.56634512514677, -121.93736922927117 47.52876471860697, -121.91445196995807 47.4858895420733, -121.90033959135305 47.439367264535264, -121.89557442425158 47.390985711028655))", geom.wkt)
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

    @Test void getLargestEmptyCircle() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry circle = recipes.getLargestEmptyCircle()
        assertNotNull circle
        assertTrue circle.isValid()
        assertFalse circle.isEmpty()
        assertTrue(circle instanceof Polygon)
    }

    @Test void getMaximumInscribedCircle() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry circle = recipes.getMaximumInscribedCircle()
        assertNotNull circle
        assertTrue circle.isValid()
        assertFalse circle.isEmpty()
        assertTrue(circle instanceof Polygon)
    }

    @Test void offset() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geoms = recipes.offset()
        assertEquals(2, geoms.size())
        assertNotNull(geoms[0])
        assertNotNull(geoms[1])
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

    @Test void fix() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.fix()
        assertEquals("LINESTRING (0 0, 1 1)", geom.wkt)
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

    @Test void triangulate() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.triangulate()
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

    @Test void rotate() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.rotate()
        assertFalse geometries[0].equals(geometries[1])
        assertFalse geometries[0].equals(geometries[2])
        assertFalse geometries[0].equals(geometries[3])
        assertFalse geometries[0].equals(geometries[4])
    }

    @Test void shear() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.shear()
        assertFalse geometries[0].equals(geometries[1])
    }

    @Test void reflect() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.reflect()
        assertFalse geometries[0].equals(geometries[1])
        assertFalse geometries[0].equals(geometries[2])
    }

    @Test void reducePrecision() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.reducePrecision()
        assertEquals "POINT (5.19775390625 51.07421875)", geometries[0].wkt
        assertEquals "POINT (5.19775390625 51.07421875)", geometries[1].wkt
        assertEquals "POINT (5.2 51.07)", geometries[2].wkt
        assertEquals "POINT (5.19775390625 51.07421875)", geometries[3].wkt
    }

    @Test void getNearestPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.getNearestPoints()
        assertEquals 2, points.size()
    }

    @Test void getAt() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.getAt()
        assertEquals 3, points.size()
        assertEquals "POINT (-122.3876953125 47.5820839916191)", points[0].wkt
        assertEquals "POINT (-122.464599609375 47.25686404408872)", points[1].wkt
        assertEquals "POINT (-122.48382568359374 47.431803338643334)", points[2].wkt
    }

    @Test void asType() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Object> values = recipes.asType()
        assertEquals 2, values.size()
        assertEquals "(-122.64,46.308,-120.981,47.413)", values[0].toString()
        assertEquals "POINT (-121.73789467295867 46.95085967283822)", values[1].toString()
    }

    @Test void equals() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.equals()
        assertTrue values[0]
        assertFalse values[1]
        assertFalse values[2]
    }

    @Test void equalsTopo() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.equalsTopo()
        assertTrue values[0]
        assertFalse values[1]
        assertFalse values[2]
    }

    @Test void equalsNorm() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.equalsNorm()
        assertTrue values[0]
        assertFalse values[1]
        assertFalse values[2]
    }

    @Test void prepare() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Long> times = recipes.prepare()
        assertTrue times[0] > 0
        assertTrue times[1] > 0
    }

    @Test void prepareStatic() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Long> times = recipes.prepareStatic()
        assertTrue times[0] > 0
        assertTrue times[1] > 0
    }

    // Prepared Geometry

    @Test void preparedGeometryCreate() {
        GeometryRecipes recipes = new GeometryRecipes()
        PreparedGeometry preparedGeometry = recipes.preparedGeometryCreate()
        assertEquals("POLYGON ((-120.739 48.151, -121.003 47.07, -119.465 47.137, -119.553 46.581, " +
                "-121.267 46.513, -121.168 45.706, -118.476 45.951, " +
                "-118.762 48.195, -120.739 48.151))", preparedGeometry.geometry.wkt)
    }

    @Test void preparedGeometryContains() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryContains()
        assertTrue(results["1contains2"])
        assertFalse(results["1contains3"])
    }

    @Test void preparedGeometryContainsProperly() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryContainsProperly()
        assertTrue(results["1contains2"])
        assertFalse(results["1contains3"])
    }

    @Test void preparedGeometryCoveredBy() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryCoveredBy()
        assertTrue(results["2coveredBy1"])
        assertFalse(results["3coveredBy1"])
    }

    @Test void preparedGeometryCovers() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryCovers()
        assertTrue(results["1covers2"])
        assertFalse(results["1covers3"])
    }

    @Test void preparedGeometryCrosses() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryCrosses()
        assertTrue(results["12"])
        assertFalse(results["13"])
    }

    @Test void preparedGeometryDisjoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryDisjoint()
        assertFalse(results["12"])
        assertTrue(results["13"])
    }

    @Test void preparedGeometryIntersects() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryIntersects()
        assertTrue(results["12"])
        assertFalse(results["13"])
    }

    @Test void preparedGeometryOverlaps() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryOverlaps()
        assertTrue(results["12"])
        assertFalse(results["13"])
    }

    @Test void preparedGeometryTouches() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryTouches()
        assertTrue(results["touches_12"])
        assertFalse(results["touches_13"])
    }

    @Test void preparedGeometryWithin() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryWithin()
        assertTrue(results["1within2"])
        assertFalse(results["1within3"])
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

    @Test void fromString() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geoms = recipes.fromString()
        assertEquals 3, geoms.size()
        assertEquals "POINT (-123.15 46.237)", geoms[0].wkt
        assertEquals "LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geoms[1].wkt
        assertEquals "POINT (-123.15 46.237)", geoms[2].wkt
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

    // GeoPackage

    @Test void writeGeometryToGeoPackageString() {
        GeometryRecipes recipes = new GeometryRecipes()
        String str = recipes.writeGeometryToGeoPackageString()
        assertEquals("4750000200000000c05ec9999999999ac05ec9999999999a40471e560418937540471e56041893750000000001c05ec9999999999a40471e5604189375", str)
    }

    @Test void writeGeometryToGeoPackageBytes() {
        GeometryRecipes recipes = new GeometryRecipes()
        byte[] bytes = recipes.writeGeometryToGeoPackageBytes()
        assertEquals("4750000200000000c05ec9999999999ac05ec9999999999a40471e560418937540471e56041893750000000001c05ec9999999999a40471e5604189375", bytes.encodeHex().toString())
    }

    @Test void readGeometryFromGeoPackageString() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.readGeometryFromGeoPackageString()
        assertEquals("POINT (-123.15 46.237)", geometry.wkt)
    }

    @Test void readGeometryFromGeoPackageBytes() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.readGeometryFromGeoPackageBytes()
        assertEquals("POINT (-123.15 46.237)", geometry.wkt)
    }

    // TWKB

    @Test void readGeometryFromTWkb() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.readGeometryFromTWkb()
        assertEquals("POINT (-123.15 46.237)", geometry.wkt)
    }

    @Test void writeGeometryToTWKB() {
        GeometryRecipes recipes = new GeometryRecipes()
        String twkb = recipes.writeGeometryToTWKB()
        assertEquals("E2080104C0E7BF1E80B7D29B0300E0E2C221E0D3ED3E00A0D7C01CDFF2A74400C0F4C935C099EF6A00", twkb)
    }

    @Test void getGeometryFromTWKB() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getGeometryFromTWKB()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void getTWKBFromGeometry() {
        GeometryRecipes recipes = new GeometryRecipes()
        String twkb = recipes.getTWKBFromGeometry()
        assertEquals("E10801BFCBB99609A0D3F9B80300", twkb)
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

    // GeoYaml

    @Test void readGeometryFromYaml() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromYaml()
        assertEquals("POINT (-122.23 45.67)", geom.wkt)
    }

    @Test void writeGeometryToYaml() {
        GeometryRecipes recipes = new GeometryRecipes()
        String yaml = recipes.writeGeometryToYaml()
        assertEquals("""---
geometry:
  type: "LineString"
  coordinates:
  - - 3.198
    - 43.164
  - - 6.713
    - 49.755
  - - 9.702
    - 42.592
  - - 15.32
    - 53.798
""", yaml)
    }

    @Test void getGeometryFromYaml() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getGeometryFromYaml()
        assertEquals("POINT (-122.23 45.67)", geom.wkt)
    }

    @Test void getYamlFromGeometry() {
        GeometryRecipes recipes = new GeometryRecipes()
        String yaml = recipes.getYamlFromGeometry()
        assertEquals("""---
geometry:
  type: "Point"
  coordinates:
  - -123.15
  - 46.237
""", yaml)
    }
}
