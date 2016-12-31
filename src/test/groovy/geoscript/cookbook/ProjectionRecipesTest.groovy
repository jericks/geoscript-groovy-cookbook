package geoscript.cookbook

import geoscript.geom.Geometry
import geoscript.proj.Projection
import org.junit.Test
import static org.junit.Assert.*

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
        assertEquals("POINT (1158609.2040371667 703068.0661327887)", geom.wkt)
    }

    @Test void transformStr() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Geometry geom = recipes.transformStr()
        assertEquals("POINT (1158609.2040371667 703068.0661327887)", geom.wkt)
    }

    @Test void transformStaticProj() {
      ProjectionRecipes recipes = new ProjectionRecipes()
      Geometry geom = recipes.transformStaticProj()
      assertEquals("POINT (1158609.2040371667 703068.0661327887)", geom.wkt)
    }

    @Test void transformStaticStr() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Geometry geom = recipes.transformStaticStr()
        assertEquals("POINT (1158609.2040371667 703068.0661327887)", geom.wkt)
    }

}