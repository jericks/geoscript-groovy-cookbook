package geoscript.cookbook

import geoscript.plot.Bar
import geoscript.plot.Box
import geoscript.plot.Chart
import geoscript.plot.Pie

class PlotRecipes extends Recipes {

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
}
