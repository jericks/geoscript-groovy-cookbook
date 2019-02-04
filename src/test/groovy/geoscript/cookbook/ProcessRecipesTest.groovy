package geoscript.cookbook

import geoscript.filter.Function
import geoscript.filter.ProcessFunction
import geoscript.layer.Cursor
import org.junit.Test
import static org.junit.Assert.*

class ProcessRecipesTest {

    @Test void getProcessNames() {
        ProcessRecipes recipes = new ProcessRecipes()
        List<String> names = recipes.getProcessNames()
        assertTrue(names.size() > 0)
        assertTrue(names.contains("vec:Bounds"))
    }

    @Test void createBuiltInProcess() {
        ProcessRecipes recipes = new ProcessRecipes()
        Map<String,Object> values = recipes.createBuiltInProcess()
        assertEquals("vec:Bounds", values.name)
        assertEquals("Bounds", values.title)
        assertEquals("Computes the bounding box of the input features.", values.description)
        assertEquals("1.0.0", values.version)
        assertEquals("[features:class geoscript.layer.Cursor]", values.parameters.toString())
        assertEquals("[bounds:class geoscript.geom.Bounds]", values.results.toString())
        assertEquals("(-175.22056447761656,-41.29997393927641,179.21664709402887,64.15002361973922,EPSG:4326)", values.executeResults.bounds.toString())
    }

    @Test void createClosureProcess() {
        ProcessRecipes recipes = new ProcessRecipes()
        Map<String,Object> values = recipes.createClosureProcess()
        assertEquals("geoscript:convexhull", values.name)
        assertEquals("convexhull", values.title)
        assertEquals("Create a convexhull around the features", values.description)
        assertEquals("1.0.0", values.version)
        assertEquals("[features:class geoscript.layer.Cursor]", values.parameters.toString())
        assertEquals("[result:class geoscript.layer.Cursor]", values.results.toString())
        assertTrue(values.executeResults.result instanceof Cursor)
    }

    // Process Function

    @Test void processFunctionProcess() {
        ProcessRecipes recipes = new ProcessRecipes()
        Function function = recipes.processFunctionProcess()
        assertNotNull(function)
        assertTrue(function instanceof Function)
    }

    @Test void processProcessFunction() {
        ProcessRecipes recipes = new ProcessRecipes()
        Function function = recipes.processProcessFunction()
        assertNotNull(function)
        assertTrue(function instanceof ProcessFunction)
    }

}
