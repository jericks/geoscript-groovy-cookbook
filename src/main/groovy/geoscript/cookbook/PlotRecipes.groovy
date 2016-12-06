package geoscript.cookbook

import geoscript.plot.Bar
import geoscript.plot.Chart

class PlotRecipes {

    private void drawChart(String name, Chart chart) {
        File file = new File("src/docs/asciidoc/images/${name}.png")
        chart.save(file, size: [300,300])
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

}
