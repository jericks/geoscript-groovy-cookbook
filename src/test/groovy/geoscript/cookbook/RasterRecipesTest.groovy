package geoscript.cookbook

import geoscript.geom.Point
import geoscript.layer.Band
import geoscript.layer.Histogram
import geoscript.layer.Layer
import geoscript.layer.Raster
import org.junit.Test
import static org.junit.Assert.*

class RasterRecipesTest {

    @Test void properties() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.properties()
        assertNotNull(raster)
        assertEquals("(-179.99999999999997,-89.99999999998205,179.99999999996405,90.0,EPSG:4326)", raster.bounds.toString())
        assertEquals("EPSG:4326", raster.proj.id)
        assertEquals([800,400], raster.size)
        assertEquals(800, raster.cols)
        assertEquals(400, raster.rows)
        assertEquals(3, raster.bands.size())
        assertEquals([800,8], raster.blockSize)
        assertEquals(0.44999999999995505, raster.pixelSize[0], 0.00001)
        assertEquals(0.4499999999999551, raster.pixelSize[1], 0.00001)
    }

    @Test void band() {
        RasterRecipes recipes = new RasterRecipes()
        List<Band> bands = recipes.band()
        assertEquals(3, bands.size())
    }

    @Test void values() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.values()
        assertEquals(3069.0, raster.getValue(new Point(-121.799927,46.867703)), 0.01)
        assertEquals(288.0, raster.getValue([100,200]), 0.01)
    }

    @Test void rasterMath() {
        RasterRecipes recipes = new RasterRecipes()
        List<Raster> rasters = recipes.rasterMath()
        Raster originalRaster = rasters[0]
        Raster plus100Raster = rasters[1]
        originalRaster.eachCell { double value, double x, double y ->
            assertEquals(value + 100, plus100Raster.getValue([x as int,y as int]), 0.1)
        }
    }

    @Test void getHistogram() {
        RasterRecipes recipes = new RasterRecipes()
        Histogram histogram = recipes.getHistogram()
        assertEquals(3, histogram.numberOfBands)
    }

    @Test void crop() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.crop()
        assertEquals("(-161.10000000000187,6.750000000008299,-34.6500000000145,57.60000000000323,EPSG:4326)", raster.bounds.toString())
    }

    @Test void reproject() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.reproject()
        assertEquals("EPSG:3857", raster.proj.id)
    }

    @Test void contours() {
        RasterRecipes recipes = new RasterRecipes()
        Layer layer = recipes.contours()
        assertTrue(layer.count > 100)
    }

    @Test void stylize() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.stylize()
        assertEquals(1, raster.bands.size())
    }

    @Test void reclassify() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.reclassify()
        assertEquals(1, raster.bands.size())
        assertEquals(1, raster.extrema.min[0], 0.1)
        assertEquals(6, raster.extrema.max[0], 0.1)
    }

    @Test void scale() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.scale()
        assertEquals(400, raster.size[0], 0.1)
        assertEquals(200, raster.size[1], 0.1)
    }

    @Test void resample() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.resample()
        assertEquals(400, raster.size[0], 0.1)
        assertEquals(400, raster.size[1], 0.1)
    }

    @Test void normalize() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.normalize()
        assertNotNull(raster)
    }

    @Test void convolveRadius() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.convolveRadius()
        assertNotNull(raster)
    }

    @Test void convolveWidthHeight() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.convolveWidthHeight()
        assertNotNull(raster)
    }

    @Test void polygonLayer() {
        RasterRecipes recipes = new RasterRecipes()
        Layer layer = recipes.polygonLayer()
        assertTrue(layer.count > 6)
    }

    @Test void pointLayer() {
        RasterRecipes recipes = new RasterRecipes()
        Layer layer = recipes.pointLayer()
        assertTrue(layer.count > 1000)
    }

    @Test void footPrint() {
        RasterRecipes recipes = new RasterRecipes()
        Layer layer = recipes.footPrint()
        assertTrue(layer.count == 1)
    }

    @Test void invert() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.invert()
        assertNotNull(raster)
    }

    @Test void log() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.log()
        assertNotNull(raster)
    }

    @Test void exp() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.exp()
        assertNotNull(raster)
    }

    @Test void add() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.add()
        assertNotNull(raster)
    }

    @Test void minus() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.minus()
        assertNotNull(raster)
    }

    @Test void minusFrom() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.minusFrom()
        assertNotNull(raster)
    }


    @Test void multiply() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.multiply()
        assertNotNull(raster)
    }

    @Test void divide() {
        RasterRecipes recipes = new RasterRecipes()
        Raster raster = recipes.divide()
        assertNotNull(raster)
    }
}
