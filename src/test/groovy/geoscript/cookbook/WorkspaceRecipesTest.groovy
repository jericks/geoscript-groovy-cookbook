package geoscript.cookbook

import geoscript.layer.Layer
import geoscript.workspace.Workspace
import geoscript.workspace.WorkspaceFactory
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

    @Test void workspaceAddLayerNameChunk() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Layer layer = recipes.workspaceAddLayerNameChunk()
        assertEquals("random points", layer.name)
        assertEquals(500, layer.count())
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

    @Test void withWorkspaceFromString() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Map<String, Workspace> values = recipes.withWorkspaceFromString()
        assertEquals("GeoPackage", values.format)
        assertEquals(["countries","ocean","places","rivers","states"], values.names)
    }

    @Test void withWorkspaceFromMap() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Map<String, Workspace> values = recipes.withWorkspaceFromMap()
        assertEquals("GeoPackage", values.format)
        assertEquals(["countries","ocean","places","rivers","states"], values.names)
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

    @Test void createPostGISWorkspace() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Workspace workspace = recipes.createPostGISWorkspace()
        assertNotNull workspace
    }

    @Test void createMySQLWorkspace() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Workspace workspace = recipes.createMySQLWorkspace()
        assertNotNull workspace
    }

    @Test void createMySQLWorkspaceWithNamedParameters() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Workspace workspace = recipes.createMySQLWorkspaceWithNamedParameters()
        assertNotNull workspace
    }

    // Database

    @Test void databaseGetSql() {
        WorkspaceRecipes recipes = new WorkspaceRecipes()
        Map<String,Object> values = recipes.databaseGetSql()
        assertEquals(326, values.numberOfPlaces)
        assertEquals(0.0, values.minElevation, 0.1)
        assertEquals(2320.0, values.maxElevation, 0.1)
        assertEquals(30.085889570552148, values.avgElevation, 0.1)
        assertEquals(10, values.names.size())
        assertEquals(326, values.layer.count)
    }

}
