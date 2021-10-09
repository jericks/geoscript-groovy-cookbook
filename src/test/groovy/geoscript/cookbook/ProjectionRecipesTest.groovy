package geoscript.cookbook

import geoscript.geom.Geometry
import geoscript.geom.Point
import geoscript.proj.DecimalDegrees
import geoscript.proj.Geodetic
import geoscript.proj.Projection
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.*

class ProjectionRecipesTest {

    @Test void createProjectionFromEpsg() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Projection proj = recipes.createFromEpsg()
        assertEquals("EPSG:4326", proj.id)
    }

    @Test void createProjectionFromWkt() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Projection proj = recipes.createFromWkt()
        assertEquals("EPSG:4326", proj.id)
    }
    
    @Test void createProjectionFromName() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Projection proj = recipes.createFromName()
        assertTrue(proj.wkt.contains("Mollweide"))
    }

    @Test void getAllProjections() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        List<Projection> projections = recipes.getAllProjections()
        assertFalse(projections.isEmpty())
    }

    @Test void getProperties() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Map properties = recipes.getProperties()
        println properties
        assertEquals("EPSG:4326", properties.id)
        assertEquals("EPSG:4326", properties.srs)
        assertEquals(4326, properties.epsg)
        assertEquals("""GEOGCS["WGS 84",
  DATUM["World Geodetic System 1984",
    SPHEROID["WGS 84", 6378137.0, 298.257223563, AUTHORITY["EPSG","7030"]],
    AUTHORITY["EPSG","6326"]],
  PRIMEM["Greenwich", 0.0, AUTHORITY["EPSG","8901"]],
  UNIT["degree", 0.017453292519943295],
  AXIS["Geodetic longitude", EAST],
  AXIS["Geodetic latitude", NORTH],
  AUTHORITY["EPSG","4326"]]
""".replaceAll(" ","").replaceAll("\n",""), properties.wkt.replaceAll(" ","").replaceAll(System.getProperty("line.separator"),""))
        assertEquals("(-180.0,-90.0,180.0,90.0,EPSG:4326)", properties.bounds.toString())
        assertEquals("(-180.0,-90.0,180.0,90.0,EPSG:4326)", properties.geoBounds.toString())
    }

    @Test void transformProj() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Geometry geom = recipes.transformProj()
        assertEquals("POINT (1158609.2040371667 703068.0661327888)", geom.wkt)
    }

    @Test void transformStr() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Geometry geom = recipes.transformStr()
        assertEquals("POINT (1158609.2040371667 703068.0661327888)", geom.wkt)
    }

    @Test void transformStaticProj() {
      ProjectionRecipes recipes = new ProjectionRecipes()
      Geometry geom = recipes.transformStaticProj()
      assertEquals("POINT (1158609.2040371667 703068.0661327888)", geom.wkt)
    }

    @Test void transformStaticStr() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Geometry geom = recipes.transformStaticStr()
        assertEquals("POINT (1158609.2040371667 703068.0661327888)", geom.wkt)
    }

    @Test void createGeodetic() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Geodetic geodetic = recipes.createGeodetic()
        assertEquals('Geodetic [SPHEROID["WGS 84", 6378137.0, 298.257223563]]', geodetic.toString())
    }

    @Test void inverseGeodetic() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Map results = recipes.inverseGeodetic()
        assertEquals(-66.53, results.forwardAzimuth, 0.1)
        assertEquals(75.65, results.backAzimuth, 0.1)
        assertEquals(4164050.459, results.distance, 0.1)
    }

    @Test void forwardGeodetic() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Map results = recipes.forwardGeodetic()
        assertEquals("POINT (-123.6835797667373 45.516427795897236)", results.point.wkt)
        assertEquals(75.65, results.backAzimuth, 0.1)
    }

    @Test void placePointsGeodetic() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        List<Point> points = recipes.placePointsGeodetic()
        assertEquals(10, points.size())
    }

    @Test void createDecimalDegreesFromLongitudeAndLatitude() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        DecimalDegrees decimalDegrees = recipes.createDecimalDegreesFromLongitudeAndLatitude()
        assertEquals(-122.525619, decimalDegrees.longitude, 0.00001)
        assertEquals(47.212023, decimalDegrees.latitude, 0.00001)
    }

    @Test void createDecimalDegreesFromPoint() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        DecimalDegrees decimalDegrees = recipes.createDecimalDegreesFromPoint()
        assertEquals(-122.525619, decimalDegrees.longitude, 0.00001)
        assertEquals(47.212023, decimalDegrees.latitude, 0.00001)
    }

    @Test void createDecimalDegreesFromLongitudeLatitudeString() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        DecimalDegrees decimalDegrees = recipes.createDecimalDegreesFromLongitudeLatitudeString()
        assertEquals(-122.525619, decimalDegrees.longitude, 0.00001)
        assertEquals(47.212023, decimalDegrees.latitude, 0.00001)
    }

    @Test void createDecimalDegreesFromTwoStringsWithGlyphs() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        DecimalDegrees decimalDegrees = recipes.createDecimalDegreesFromTwoStringsWithGlyphs()
        assertEquals(-122.525619, decimalDegrees.longitude, 0.00001)
        assertEquals(47.212023, decimalDegrees.latitude, 0.00001)
    }

    @Test void createDecimalDegreesFromTwoStringsWithCharacters() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        DecimalDegrees decimalDegrees = recipes.createDecimalDegreesFromTwoStringsWithCharacters()
        assertEquals(-122.525619, decimalDegrees.longitude, 0.00001)
        assertEquals(47.212023, decimalDegrees.latitude, 0.00001)
    }

    @Test void createDecimalDegreesFromOneDMSString() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        DecimalDegrees decimalDegrees = recipes.createDecimalDegreesFromOneDMSString()
        assertEquals(-122.525619, decimalDegrees.longitude, 0.00001)
        assertEquals(47.212023, decimalDegrees.latitude, 0.00001)
    }

    @Test void createDecimalDegreesFromOneDDMGlyphsString() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        DecimalDegrees decimalDegrees = recipes.createDecimalDegreesFromOneDDMGlyphsString()
        assertEquals(-122.525619, decimalDegrees.longitude, 0.00001)
        assertEquals(47.212023, decimalDegrees.latitude, 0.00001)
    }

    @Test void createDecimalDegreesFromOneDDMString() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        DecimalDegrees decimalDegrees = recipes.createDecimalDegreesFromOneDDMString()
        assertEquals(-122.525619, decimalDegrees.longitude, 0.00001)
        assertEquals(47.212023, decimalDegrees.latitude, 0.00001)
    }

    @Test void decimalDegreesGetDms() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Map dms = recipes.decimalDegreesGetDms()
        assertEquals(-122, dms.longitude.degrees, 0.1)
        assertEquals(31, dms.longitude.minutes, 0.1)
        assertEquals(32.229, dms.longitude.seconds, 0.1)
        assertEquals(47, dms.latitude.degrees, 0.1)
        assertEquals(12, dms.latitude.minutes, 0.1)
        assertEquals(43.280, dms.latitude.seconds, 0.1)
    }

    @Test void decimalDegreesToDms() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Map dms = recipes.decimalDegreesToDms()
        assertEquals("-122\u00B0 31' 32.2300\" W, 47\u00B0 12' 43.2800\" N", dms.true)
        assertEquals("-122d 31m 32.2300s W, 47d 12m 43.2800s N", dms.false)
    }

    @Test void decimalDegreesGetDdm() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Map ddm = recipes.decimalDegreesGetDdm()
        assertEquals(-122, ddm.longitude.degrees, 0.1)
        assertEquals(31.537, ddm.longitude.minutes, 0.1)
        assertEquals(47, ddm.latitude.degrees, 0.1)
        assertEquals(12.721, ddm.latitude.minutes, 0.1)
    }

    @Test void decimalDegreesToDdm() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Map ddm = recipes.decimalDegreesToDdm()
        assertEquals("-122\u00B0 31.5372' W, 47\u00B0 12.7213' N", ddm.true)
        assertEquals("-122d 31.5372m W, 47d 12.7213m N", ddm.false)
    }

    @Test void decimalDegreesGetPoint() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Point point = recipes.decimalDegreesGetPoint()
        assertEquals("POINT (-122.52561944444444 47.212022222222224)", point.wkt)
    }

}