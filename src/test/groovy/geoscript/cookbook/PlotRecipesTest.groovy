package geoscript.cookbook

import geoscript.plot.Chart
import javafx.scene.chart.BarChart
import org.junit.Test

import java.awt.image.BufferedImage

import static org.junit.Assert.*

class PlotRecipesTest {

    @Test void getImageChart() {
        PlotRecipes plotRecipes = new PlotRecipes()
        BufferedImage image = plotRecipes.getImageChart()
        assertNotNull(image)
    }

    @Test void saveChart() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.saveChart()
        assertNotNull(chart)
    }

    @Test void overlayCharts() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.overlayCharts()
        assertNotNull(chart)
    }

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

    @Test void createLinearRegression() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createLinearRegression()
        assertNotNull(chart)
    }

    @Test void createPowerRegression() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createPowerRegression()
        assertNotNull(chart)
    }

    @Test void createScatterPlot() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createScatterPlot()
        assertNotNull(chart)
    }

    @Test void createScatterPlotWithOptions() {
        PlotRecipes plotRecipes = new PlotRecipes()
        Chart chart = plotRecipes.createScatterPlotWithOptions()
        assertNotNull(chart)
    }
}
