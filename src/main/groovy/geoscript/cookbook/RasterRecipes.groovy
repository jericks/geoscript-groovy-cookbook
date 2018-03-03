package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.layer.Band
import geoscript.layer.Format
import geoscript.layer.Raster
import geoscript.proj.Projection

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
