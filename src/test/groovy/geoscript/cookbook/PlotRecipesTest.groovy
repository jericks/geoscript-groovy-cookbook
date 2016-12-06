package geoscript.cookbook

import geoscript.plot.Chart
import javafx.scene.chart.BarChart
import org.junit.Test
import static org.junit.Assert.*

class PlotRecipesTest {

    @Test void createBarChart() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createBarChart()
        assertNotNull(chart)
    }

    @Test void createBarChartWithCategories() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createBarChartWithCategories()
        assertNotNull(chart)
    }

}
