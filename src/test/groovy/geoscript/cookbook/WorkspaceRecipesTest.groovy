package geoscript.cookbook

import geoscript.layer.Layer
import geoscript.workspace.Workspace
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

    @Test void getWorkspaceParameters() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        List<Map> params = recipes.getWorkspaceParameters()
        assertTrue(params.size() > 0)
        assertNotNull(params.find { it.key == "dbtype"} )
        assertNotNull(params.find { it.key == "database"} )
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

    @Test void createMemoryWorkspace() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Layer layer = recipes.createMemoryWorkspace()
        assertEquals("cities", layer.name)
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

    @Test void getWorkspaceFromString() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Map<String, Workspace> values = recipes.getWorkspaceFromString()
        values.each { String connectionString, Workspace workspace ->
            assertNotNull(workspace)
        }
    }

    @Test void getWorkspaceFromMap() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Map<String, Workspace> values = recipes.getWorkspaceFromMap()
        values.each { String name, Workspace workspace ->
            assertNotNull(workspace)
        }
    }

    @Test void createDirectoryWorkspaceFromName() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Workspace workspace = recipes.createDirectoryWorkspaceFromName()
        assertNotNull workspace
    }

    @Test void createDirectoryWorkspaceFromFile() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Workspace workspace = recipes.createDirectoryWorkspaceFromFile()
        assertNotNull workspace
    }

    @Test void createGeoPackageWorkspaceFromName() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Workspace workspace = recipes.createGeoPackageWorkspaceFromName()
        assertNotNull workspace
    }

    @Test void createGeoPackageWorkspaceFromFile() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Workspace workspace = recipes.createGeoPackageWorkspaceFromFile()
        assertNotNull workspace
    }

    @Test void createH2WorkspaceFromFile() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Workspace workspace = recipes.createH2WorkspaceFromFile()
        assertNotNull workspace
    }

    @Test void createGeobufWorkspaceFromFile() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Workspace workspace = recipes.createGeobufWorkspaceFromFile()
        assertNotNull workspace
    }

    @Test void createPropertyWorkspaceFromFile() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Workspace workspace = recipes.createPropertyWorkspaceFromFile()
        assertNotNull workspace
    }
}
