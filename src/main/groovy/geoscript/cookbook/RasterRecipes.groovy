package geoscript.cookbook

import geoscript.filter.Color
import geoscript.geom.Bounds
import geoscript.geom.Point
import geoscript.layer.Band
import geoscript.layer.Format
import geoscript.layer.Layer
import geoscript.layer.Raster
import geoscript.proj.Projection
import geoscript.style.ColorMap
import geoscript.style.Gradient
import geoscript.style.Stroke
import geoscript.style.UniqueValues

class RasterRecipes extends Recipes {

    Raster properties() {
        // tag::properties[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("earth")
        // end::properties[]
        draw("raster_properties", [raster])

        // tag::properties_bounds[]
        Bounds bounds = raster.bounds
        println "Bounds: ${bounds}"
        // end::properties_bounds[]
        writeFile("raster_properties_bounds","Bounds: ${bounds}")

        // tag::properties_projection[]
        Projection projection = raster.proj
        println "Projection: ${projection}"
        // end::properties_projection[]
        writeFile("raster_properties_projection","Projection: ${projection}")

        // tag::properties_size[]
        List size = raster.size
        println "Size: ${size[0]}x${size[1]}"
        // end::properties_size[]
        writeFile("raster_properties_size","Size: ${size[0]}x${size[1]}")

        // tag::properties_colsrows[]
        int cols = raster.cols
        int rows = raster.rows
        println "Columns: ${cols} Rows: ${rows}"
        // end::properties_colsrows[]
        writeFile("raster_properties_colsrows","Columns: ${cols} Rows: ${rows}")

        // tag::properties_bands[]
        List<Band> bands = raster.bands
        println "Bands:"
        bands.each { Band band ->
            println "  ${band}"
        }
        // end::properties_bands[]
        writeFile("raster_properties_bands","Band:${NEW_LINE}${bands.collect{ '  ' + it.toString()}.join(NEW_LINE)}")

        // tag::properties_blocksize[]
        List blockSize = raster.blockSize
        println "Block size: ${blockSize[0]}x${blockSize[1]}"
        // end::properties_blocksize[]
        writeFile("raster_properties_blocksize","Block size: ${blockSize[0]}x${blockSize[1]}")

        // tag::properties_pixelsize[]
        List pixelSize = raster.pixelSize
        println "Pixel size: ${pixelSize[0]}x${pixelSize[1]}"
        // end::properties_pixelsize[]
        writeFile("raster_properties_pixelsize","Pixel size: ${pixelSize[0]}x${pixelSize[1]}")

        raster
    }

    Raster values() {
        // tag::values[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        // end::values[]
        raster.style = new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ])
        draw("raster_values", [raster])

        // tag::values_1[]
        double elevation = raster.getValue(new Point(-121.799927,46.867703))
        println elevation
        // end::values_1[]
        writeFile("raster_values_1", "${elevation}")

        // tag::values_2[]
        List pixel = [100,200]
        elevation = raster.getValue(pixel)
        println elevation
        // end::values_2[]
        writeFile("raster_values_2", "${elevation}")


        raster
    }

    // Processing

    Raster crop() {
        // tag::crop[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("earth")
        Raster croppedRaster = raster.crop(new Bounds(-160.927734,6.751896,-34.716797,57.279043, "EPSG:4326"))
        // end::crop[]
        draw("raster_crop", [croppedRaster])
        croppedRaster
    }

    Raster reproject() {
        // tag::reproject[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("earth")
        Projection projection = new Projection("EPSG:3857")
        Raster projectedRaster = raster.crop(projection.geoBounds).reproject(projection)
        // end::reproject[]
        draw("raster_reproject", [projectedRaster])
        projectedRaster
    }

    Layer contours() {
        // tag::contours[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        int band = 0
        int interval = 300
        boolean simplify = true
        boolean smooth = true
        Layer contours = raster.contours(band, interval, simplify, smooth)
        // end::contours[]
        contours.style = new Stroke("black", 0.1)
        raster.style = new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ])
        draw("raster_contours", [raster, contours])
        contours
    }

    Raster stylize() {
        // tag::stylize[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        Raster stylizedRaster = raster.stylize(new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ]))
        // end::stylize[]
        draw("raster_stylize", [stylizedRaster])
        stylizedRaster
    }

    Raster reclassify() {
        // tag::reclassify[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        Raster reclassifiedRaster = raster.reclassify([
                [min:0,    max:0,    value: 1],
                [min:0,    max:50,   value: 2],
                [min:50,   max:200,  value: 3],
                [min:200,  max:1000, value: 4],
                [min:1000, max:1500, value: 5],
                [min:1500, max:4000, value: 6]
        ])
        // end::reclassify[]
        reclassifiedRaster.style = new ColorMap([
                [color: "#9fd182", quantity:1],
                [color: "#3e7f3c", quantity:2],
                [color: "#133912", quantity:3],
                [color: "#08306b", quantity:4],
                [color: "#FFF8DC", quantity:5],
                [color: "#ffffff", quantity:6],
        ])
        draw("raster_reclassify", [reclassifiedRaster])
        reclassifiedRaster
    }

    Layer polygonLayer() {
        // tag::polygonLayer[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        Raster reclassifiedRaster = raster.reclassify([
                [min:0,    max:0,    value: 1],
                [min:0,    max:50,   value: 2],
                [min:50,   max:200,  value: 3],
                [min:200,  max:1000, value: 4],
                [min:1000, max:1500, value: 5],
                [min:1500, max:4000, value: 6]
        ])
        Layer layer = reclassifiedRaster.polygonLayer
        // end::polygonLayer[]
        layer.style = new UniqueValues(layer, "value" , {int index, double value -> Color.getRandom()})
        draw("raster_polygonLayer", [layer])
        layer
    }

    Layer pointLayer() {
        // tag::pointLayer[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc").crop(new Bounds(-121.878548,46.808402,-121.636505,46.896097, "EPSG:4326"))
        Layer layer = raster.pointLayer
        // end::pointLayer[]
        layer.style = new Gradient(layer, "GRAY_INDEX", "quantile", 8, "MutedTerrain")
        draw("raster_pointLayer", [layer])
        layer
    }

    // Band

    List<Band> band() {
        // tag::band[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("earth")
        List<Band> bands = raster.bands
        bands.each { Band band ->
            println "${band}"
            println "  Min = ${band.min}"
            println "  Max = ${band.max}"
            println "  No Data = ${band.noData}"
            println "  Is No Data = ${band.isNoData(12.45)}"
            println "  Unit = ${band.unit}"
            println "  Scale = ${band.scale}"
            println "  Offset = ${band.offset}"
            println "  Type = ${band.type}"
        }
        // end::band[]
        writeFile("raster_band",  bands.collect { Band band ->
"""${band}
  Min = ${band.min}
  Max = ${band.max}
  No Data = ${band.noData}
  Is No Data = ${band.isNoData(12.45)}
  Unit = ${band.unit}
  Scale = ${band.scale}
  Offset = ${band.offset}
  Type = ${band.type}
""" }.join(NEW_LINE))
        bands
    }
}
