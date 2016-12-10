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
    
    @Test void transformStatic() {
      ProjectionRecipes recipes = new ProjectionRecipes()
      Geometry geom = recipes.transformStatic()
      assertEquals("POINT (1158609.2040371667 703068.0661327887)", geom.wkt)
    }

}