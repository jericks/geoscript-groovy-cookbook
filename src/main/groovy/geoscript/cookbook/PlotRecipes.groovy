package geoscript.cookbook

import geoscript.plot.Bar
import geoscript.plot.Chart

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

}
