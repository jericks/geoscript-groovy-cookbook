package geoscript.cookbook

import geoscript.layer.ArcGrid
import geoscript.layer.Format
import geoscript.layer.GeoTIFF
import geoscript.layer.Raster
import geoscript.layer.WorldImage

class FormatRecipes extends Recipes {

    Format getFormat() {
        // tag::getFormat[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)
        println format.name
        // end::getFormat[]
        writeFile("format_get", "${format.name}")
        format
    }

    List<String> getNames() {
        // tag::getNames[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)
        List<String> names = format.names
        names.each { String name ->
            println name
        }
        // end::getNames[]
        writeFile("format_names", "${names.collect { it }.join(NEW_LINE)}")
        names
    }

    Format hasRaster() {
        // tag::hasRaster[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)

        boolean hasEarth = format.has("earth")
        println "Has raster named earth? ${hasEarth}"

        boolean hasWorld = format.has("world")
        println "Has raster named world? ${hasWorld}"
        // end::hasRaster[]
        writeFile("format_has", "Has raster named earth? ${hasEarth}${NEW_LINE}Has raster named world? ${hasWorld}")
        format
    }

    Raster read() {
        // tag::read[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("earth")
        // end::read[]
        draw("format_read", [raster])
        raster
    }

    Raster write() {
        // tag::write[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("earth")

        File outFile = new File("target/earth.png")
        Format outFormat = Format.getFormat(outFile)
        outFormat.write(raster)
        Raster outRaster = outFormat.read("earth")

        // end::write[]
        draw("format_write", [outRaster])
        outRaster
    }

    Raster readGeoTiff() {
        // tag::readGeoTiff[]
        File file = new File("src/main/resources/earth.tif")
        GeoTIFF geotiff = new GeoTIFF(file)
        Raster raster = geotiff.read("earth")
        // end::readGeoTiff[]
        draw("format_geotiff_read", [raster])
        raster
    }

    Raster writeGeoTiffToArcGrid() {
        // tag::writeGeoTiffToArcGrid[]
        File file = new File("src/main/resources/earth.tif")
        GeoTIFF geotiff = new GeoTIFF(file)
        Raster raster = geotiff.read("earth")

        File arcGridFile = new File("target/earth.asc")
        ArcGrid arcGrid = new ArcGrid(arcGridFile)
        arcGrid.write(raster)
        Raster arcGridRaster = arcGrid.read("earth")

        // end::writeGeoTiffToArcGrid[]
        draw("format_write_geotiff_to_arcgrid", [arcGridRaster])
        raster
    }

    Raster readWorldImage() {
        // tag::readWorldImage[]
        File file = new File("src/main/resources/earth.png")
        WorldImage worldImage = new WorldImage(file)
        Raster raster = worldImage.read("earth")
        // end::readWorldImage[]
        draw("format_worldimage_read", [raster])
        raster
    }

}
