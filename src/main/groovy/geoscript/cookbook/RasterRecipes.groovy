package geoscript.cookbook

import geoscript.filter.Color
import geoscript.geom.Bounds
import geoscript.geom.Point
import geoscript.layer.ArcGrid
import geoscript.layer.Band
import geoscript.layer.Format
import geoscript.layer.GeoTIFF
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

        // tag::properties_extrema[]
        Map extrema = raster.extrema
        println "Min values: ${extrema.min} Max values: ${extrema.max}"
        // end::properties_extrema[]
        writeFile("raster_properties_extrema","Min value: ${extrema.min} Max value: ${extrema.max}")

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

    List<Raster> rasterMath() {
        // tag::rasterMath[]
        File file = new File("src/main/resources/earth.tif")
        GeoTIFF geotiff = new GeoTIFF(file)
        Raster raster = geotiff.read("earth")

        File arcGridFile = new File("target/earth.asc")
        ArcGrid arcGrid = new ArcGrid(arcGridFile)
        arcGrid.write(raster)
        Raster arcGridRaster = arcGrid.read("earth")

        arcGridRaster.eachCell {double value, double x, double y ->
            double newValue = value + 100
            arcGridRaster.setValue([x as int, y as int], newValue)
        }

        File arcGridAddFile = new File("target/earth_100.asc")
        ArcGrid arcGridAdd = new ArcGrid(arcGridAddFile)
        arcGridAdd.write(arcGridRaster)
        Raster arcGridRasterAdd = arcGridAdd.read("earth_100")

        List pixels = [
            [92, 298],
            [393.0, 343.0],
            [795.0, 399.0]
        ]
        pixels.each { List pixel ->
            println "Original: ${raster.getValue(pixel)} New: ${arcGridRasterAdd.getValue(pixel)}"
        }

        // end::rasterMath[]
        draw("raster_math_orig", [raster])
        draw("raster_math_100", [arcGridRasterAdd])
        writeFile("raster_math", pixels.collect { List pixel ->
            "Original: ${raster.getValue(pixel)} New: ${arcGridRasterAdd.getValue(pixel)}"
        }.join(NEW_LINE))
        [raster, arcGridRasterAdd]
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

    Raster scale() {
        // tag::scale[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        println "Original Raster Size = ${raster.size[0]}x${raster.size[1]}"

        Raster scaledRaster = raster.scale(0.5, 0.5)
        println "Scaled Raster Size = ${scaledRaster.size[0]}x${scaledRaster.size[1]}"
        // end::scale[]
        scaledRaster.style = new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ])
        draw("raster_scaled", [scaledRaster])
        writeFile("raster_scaled","Original Raster Size = ${raster.size[0]}x${raster.size[1]}${NEW_LINE}Scaled Raster Size = ${scaledRaster.size[0]}x${scaledRaster.size[1]}")
        scaledRaster
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

    Raster invert() {
        // tag::invert[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        Raster invertedRaster = raster.invert()
        // end::invert[]
        invertedRaster.style = new ColorMap([
                [color: "#9fd182", quantity:-4370],
                [color: "#3e7f3c", quantity:-1000],
                [color: "#133912", quantity:0],
                [color: "#08306b", quantity:20000],
                [color: "#fffff5", quantity:32768],
        ])
        draw("raster_invert", [invertedRaster])
        invertedRaster
    }

    Raster log() {
        // tag::log[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        Raster logRaster = raster.log()
        // end::log[]
        logRaster.style = new ColorMap([
                [color: "#9fd182", quantity:0],
                [color: "#3e7f3c", quantity:2],
                [color: "#133912", quantity:4],
                [color: "#08306b", quantity:6],
                [color: "#fffff5", quantity:10],
        ])
        draw("raster_log", [logRaster])
        logRaster
    }

    Raster exp() {
        // tag::exp[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        Raster expRaster = raster.exp()
        // end::exp[]
        expRaster.style = new ColorMap([
                [color: "#9fd182", quantity:Math.exp(0)],
                [color: "#3e7f3c", quantity:Math.exp(50)],
                [color: "#133912", quantity:Math.exp(100)],
                [color: "#08306b", quantity:Math.exp(200)],
                [color: "#fffff5", quantity:Math.exp(500)],
        ])
        draw("raster_exp", [expRaster])
        expRaster
    }

    // Raster Algebra

    Raster add() {
        // tag::add[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        double elevation1 = raster.getValue(new Point(-121.799927,46.867703))
        println elevation1

        Raster higherRaster = raster.add(100.00)
        double elevation2 = higherRaster.getValue(new Point(-121.799927,46.867703))
        println elevation2

        // end::add[]
        higherRaster.style = new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ])
        draw("raster_add", [higherRaster])
        writeFile("raster_add", "${elevation1}${NEW_LINE}${elevation2}")
        higherRaster
    }

    Raster minus() {
        // tag::minus[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        double elevation1 = raster.getValue(new Point(-121.799927,46.867703))
        println elevation1

        Raster lowerRaster = raster.minus(50.00)
        double elevation2 = lowerRaster.getValue(new Point(-121.799927,46.867703))
        println elevation2

        // end::minus[]
        lowerRaster.style = new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ])
        draw("raster_minus", [lowerRaster])
        writeFile("raster_minus", "${elevation1}${NEW_LINE}${elevation2}")
        lowerRaster
    }

    Raster minusFrom() {
        // tag::minusFrom[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        double elevation1 = raster.getValue(new Point(-121.799927,46.867703))
        println elevation1

        Raster lowerRaster = raster.minusFrom(2000.0)
        double elevation2 = lowerRaster.getValue(new Point(-121.799927,46.867703))
        println elevation2

        // end::minusFrom[]
        lowerRaster.style = new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ])
        draw("raster_minusfrom", [lowerRaster])
        writeFile("raster_minusfrom", "${elevation1}${NEW_LINE}${elevation2}")
        lowerRaster
    }

    Raster multiply() {
        // tag::multiply[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        double elevation1 = raster.getValue(new Point(-121.799927,46.867703))
        println elevation1

        Raster higherRaster = raster.multiply(2.0)
        double elevation2 = higherRaster.getValue(new Point(-121.799927,46.867703))
        println elevation2

        // end::multiply[]
        higherRaster.style = new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ])
        draw("raster_multiply", [higherRaster])
        writeFile("raster_multiply", "${elevation1}${NEW_LINE}${elevation2}")
        higherRaster
    }

    Raster divide() {
        // tag::divide[]
        File file = new File("src/main/resources/pc.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("pc")
        double elevation1 = raster.getValue(new Point(-121.799927,46.867703))
        println elevation1

        Raster lowerRaster = raster.divide(2.0)
        double elevation2 = lowerRaster.getValue(new Point(-121.799927,46.867703))
        println elevation2

        // end::divide[]
        lowerRaster.style = new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ])
        draw("raster_divide", [lowerRaster])
        writeFile("raster_divide", "${elevation1}${NEW_LINE}${elevation2}")
        lowerRaster
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
