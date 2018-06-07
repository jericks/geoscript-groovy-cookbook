package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.MultiPoint
import geoscript.geom.Point
import geoscript.plot.Bar
import geoscript.plot.Box
import geoscript.plot.Chart
import geoscript.plot.Curve
import geoscript.plot.Pie
import geoscript.plot.Regression
import geoscript.plot.Scatter

import java.awt.image.BufferedImage

class PlotRecipes extends Recipes {

    Chart showChart() {
        // tag::showChart[]
        List data = [
                [1,10],[45,12],[23,3],[5,20]
        ]
        Chart chart = Bar.xy(data)
        // end::showChart[]
        chart.show()
        chart
    }

    BufferedImage getImageChart() {
        // tag::getImageChart[]
        Map data = [
                "A":20,"B":45,"C":2,"D":14
        ]
        Chart chart = Pie.pie(data)
        BufferedImage image = chart.image
        // end::getImageChart[]
        saveImage("plot_getimage", image)
        image
    }

    Chart saveChart() {
        // tag::saveChart[]
        Map data = [
                "A":[1,10,20],
                "B":[45,39,10],
                "C":[40,30,20],
                "D":[14,25,19]
        ]
        Chart chart = Box.box(data)
        File file = new File("chart.png")
        chart.save(file)
        // end::saveChart[]
        moveFile(file, new File("src/docs/asciidoc/images/plot_save.png"))
        chart
    }

    Chart overlayCharts() {
        // tag::overlayCharts[]
        List data = [
                [1,10],[45,12],[23,3],[5,20]
        ]
        Chart chart1 = Bar.xy(data)
        Chart chart2 = Curve.curve(data)
        Chart chart3 = Regression.linear(data)
        chart1.overlay([chart2,chart3])
        // end::overlayCharts[]
        drawChart("plot_overlay", chart1)
        chart1
    }

    Chart createBarChart() {
        // tag::createBarChart[]
        List data = [
                [1,10],[45,12],[23,3],[5,20]
        ]
        Chart chart = Bar.xy(data)
        // end::createBarChart[]
        drawChart("plot_bar_chart", chart)
        chart
    }

    Chart createBarChartWithCategories() {
        // tag::createBarChartWithCategories[]
        Map data = [
                "A":20,"B":45,"C":2,"D":14
        ]
        Chart chart = Bar.category(data)
        // end::createBarChartWithCategories[]
        drawChart("plot_bar_chart_with_categories", chart)
        chart
    }

    Chart createBarChartCategoryStacked() {
        // tag::createBarChartCategoryStacked[]
        Map data = [
                "A": ["B":50,"C":25,"D":25],
                "F": ["G":75,"H":10,"I":15]
        ]
        Chart chart = Bar.category(data, stacked: true)
        // end::createBarChartCategoryStacked[]
        drawChart("plot_bar_chart_with_categories_stacked", chart)
        chart
    }

    Chart createBarChartCategory3D() {
        // tag::createBarChartCategory3D[]
        Map data = [
                "A":20,"B":45,"C":2,"D":14
        ]
        Chart chart = Bar.category(data, trid: true)
        // end::createBarChartCategory3D[]
        drawChart("plot_bar_chart_with_categories_3d", chart)
        chart
    }

    Chart createPieChart() {
        // tag::createPieChart[]
        Map data = [
                "A":20,"B":45,"C":2,"D":14
        ]
        Chart chart = Pie.pie(data)
        // end::createPieChart[]
        drawChart("plot_pie_chart", chart)
        chart
    }

    Chart createPieChart3D() {
        // tag::createPieChart3D[]
        Map data = [
                "A":20,"B":45,"C":2,"D":14
        ]
        Chart chart = Pie.pie(data, trid: true)
        // end::createPieChart3D[]
        drawChart("plot_pie_chart3d", chart)
        chart
    }

    Chart createBoxPlot() {
        // tag::createBoxPlot[]
        Map data = [
                "A":[1,10,20],
                "B":[45,39,10],
                "C":[40,30,20],
                "D":[14,25,19]
        ]
        Chart chart = Box.box(data)
        // end::createBoxPlot[]
        drawChart("plot_box", chart)
        chart
    }

    Chart createCurve() {
        // tag::createCurve[]
        List data = [
                [1,10],[45,12],[23,3],[5,20]
        ]
        Chart chart = Curve.curve(data)
        // end::createCurve[]
        drawChart("plot_curve", chart)
        chart
    }

    Chart createCurveSmooth() {
        // tag::createCurveSmooth[]
        List data = [
                [1,10],[45,12],[23,3],[5,20]
        ]
        Chart chart = Curve.curve(data, smooth: true)
        // end::createCurveSmooth[]
        drawChart("plot_curve_smooth", chart)
        chart
    }

    Chart createCurve3D() {
        // tag::createCurve3D[]
        List data = [
                [1,10],[45,12],[23,3],[5,20]
        ]
        Chart chart = Curve.curve(data, trid: true)
        // end::createCurve3D[]
        drawChart("plot_curve3d", chart)
        chart
    }

    Chart createLinearRegression() {
        // tag::createLinearRegression[]
        MultiPoint mulitPoint = Geometry.createRandomPoints(new Bounds(0,0,100,100).geometry, 10)
        List data = mulitPoint.geometries.collect{ Point pt ->
            [pt.x, pt.y]
        }
        Chart chart = Regression.linear(data)
        // end::createLinearRegression[]
        drawChart("plot_linear_regression", chart)
        chart
    }

    Chart createPowerRegression() {
        // tag::createPowerRegression[]
        MultiPoint mulitPoint = Geometry.createRandomPoints(new Bounds(0,0,100,100).geometry, 10)
        List data = mulitPoint.geometries.collect{ Point pt ->
            [pt.x, pt.y]
        }
        Chart chart = Regression.power(data)
        // end::createPowerRegression[]
        drawChart("plot_power_regression", chart)
        chart
    }

    Chart createScatterPlot() {
        // tag::createScatterPlot[]
        MultiPoint mulitPoint = Geometry.createRandomPoints(new Bounds(0,0,100,100).geometry, 10)
        List data = mulitPoint.geometries.collect{ Point pt ->
            [pt.x, pt.y]
        }
        Chart chart = Scatter.scatterplot(data)
        // end::createScatterPlot[]
        drawChart("plot_scatterplot", chart)
        chart
    }

    Chart createScatterPlotWithOptions() {
        // tag::createScatterPlotWithOptions[]
        MultiPoint mulitPoint = Geometry.createRandomPoints(new Bounds(0,0,100,100).geometry, 10)
        List data = mulitPoint.geometries.collect{ Point pt ->
            [pt.x, pt.y]
        }
        Chart chart = Scatter.scatterplot(data, legend: false, xLabel: "X Coordinates", yLabel: "Y Coordinates")
        // end::createScatterPlotWithOptions[]
        drawChart("plot_scatterplot_options", chart)
        chart
    }

}
