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

    @Test void createBarChartCategoryStacked() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createBarChartCategoryStacked()
        assertNotNull(chart)
    }

    @Test void createBarChartCategory3D() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createBarChartCategory3D()
        assertNotNull(chart)
    }

    @Test void createPieChart() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createPieChart()
        assertNotNull(chart)
    }

    @Test void createPieChart3D() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createPieChart3D()
        assertNotNull(chart)
    }

    @Test void createBoxPlot() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createBoxPlot()
        assertNotNull(chart)
    }

    @Test void createCurve() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createCurve()
        assertNotNull(chart)
    }

    @Test void createCurveSmooth() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createCurveSmooth()
        assertNotNull(chart)
    }

    @Test void createCurve3D() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createCurve3D()
        assertNotNull(chart)
    }
}
