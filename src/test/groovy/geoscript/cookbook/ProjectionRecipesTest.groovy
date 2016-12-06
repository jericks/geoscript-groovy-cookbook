package geoscript.cookbook

import geoscript.proj.Projection
import org.junit.Test

import static org.junit.Assert.assertEquals

class ProjectionRecipesTest {

    private void writeFile(String name, String text) {
        File dir = new File("src/docs/asciidoc/output")
        if (!dir.exists()) {
            dir.mkdir()
        }
        File file = new File(dir, name)
        file.text = text
    }

    @Test void createProjectionFromEpsg() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Projection proj = recipes.createFromEpsg()
        assertEquals("EPSG:4326", proj.id)
        writeFile("projection_createprojectionfromepsg.txt", proj.wkt)
    }

    @Test void createProjectionFromWkt() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Projection proj = recipes.createFromWkt()
        assertEquals("EPSG:4326", proj.id)
        writeFile("projection_createprojectionfromwkt.txt", proj.wkt)
    }
    
    @Test void createProjectionFromName() {
        ProjectionRecipes recipes = new ProjectionRecipes()
        Projection proj = recipes.createFromName()
        //assertEquals("EPSG:4326", proj.id)
        writeFile("projection_createprojectionfromname.txt", proj.wkt)
    }


}