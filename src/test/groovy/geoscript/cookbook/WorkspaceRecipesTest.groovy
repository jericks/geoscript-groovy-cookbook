package geoscript.cookbook

import geoscript.layer.Layer
import org.junit.Test
import static org.junit.Assert.*

class WorkspaceRecipesTest {

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

}
