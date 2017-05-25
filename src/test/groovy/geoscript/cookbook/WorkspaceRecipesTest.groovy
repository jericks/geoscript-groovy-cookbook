package geoscript.cookbook

import org.junit.Test
import static org.junit.Assert.*

class WorkspaceRecipesTest {

    @Test void getWorkspaceNames() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        List<String> names = recipes.getWorkspaceNames()
        assertTrue(names.size() > 0)
        assertTrue(names.contains("PostGIS"))
        assertTrue(names.contains("H2"))
    }

    @Test void createWorkspace() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Map<String,Object> values = recipes.createWorkspace()
        assertNotNull(values['workspace'])
        assertNotNull(values['layer'])
        assertTrue(values['hasCities'])
        assertTrue(values['hasStates'])
        List<String> names = values['names']
        assertTrue(names.contains('cities'))
        assertTrue(names.contains('states'))
        assertTrue(values['citiesRemoved'])
    }

    @Test void createDirectoryWorkspace() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Map<String,Object> values = recipes.createDirectoryWorkspace()
        assertTrue(values["directoryToString"].toString().startsWith("Directory"))
        assertEquals("Directory", values["format"])
        assertEquals("data", (values["file"] as File).name)
        List<String> names = values['names']
        assertTrue(names.contains('states'))
        assertEquals(49, values["count"])
    }

}
