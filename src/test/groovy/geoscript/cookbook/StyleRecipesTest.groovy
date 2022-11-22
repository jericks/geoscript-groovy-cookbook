package geoscript.cookbook

import geoscript.style.Composite
import geoscript.style.Fill
import geoscript.style.Shape
import geoscript.style.Stroke
import geoscript.style.Style
import geoscript.style.StyleRepository
import geoscript.style.Symbolizer
import geoscript.style.io.Reader
import geoscript.style.io.Writer
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.*

class StyleRecipesTest {

    // Basic

    @Test void createBasicSymbolizer() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createBasicSymbolizer()
        assertNotNull(symbolizer)
    }

    @Test void createBasicComposite() {
        StyleRecipes recipes = new StyleRecipes()
        Composite composite = recipes.createBasicComposite()
        assertNotNull(composite)
    }

    @Test void createWithTitle() {
        StyleRecipes recipes = new StyleRecipes()
        List<String> titles = recipes.createWithTitle()
        assertEquals("States", titles[0])
        assertEquals("States with Outline", titles[1])
    }

    @Test void createBasicSymbolizerWithScale() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createBasicSymbolizerWithScale()
        assertNotNull(symbolizer)
    }

    @Test void createBasicSymbolizerWithZindex() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createBasicSymbolizerWithZindex()
        assertNotNull(symbolizer)
    }

    @Test void createBasicSymbolizerWithWhere() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createBasicSymbolizerWithWhere()
        assertNotNull(symbolizer)
    }

    // Stroke

    @Test void createStrokeWithColor() {
        StyleRecipes recipes = new StyleRecipes()
        Stroke stroke = recipes.createStrokeWithColor()
        assertNotNull(stroke)
    }

    @Test void createStrokeWithColorAndWidth() {
        StyleRecipes recipes = new StyleRecipes()
        Stroke stroke = recipes.createStrokeWithColorAndWidth()
        assertNotNull(stroke)
    }

    @Test void createStrokeWithCasing() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer stroke = recipes.createStrokeWithCasing()
        assertNotNull(stroke)
    }

    @Test void createStrokeWithDashes() {
        StyleRecipes recipes = new StyleRecipes()
        Stroke stroke = recipes.createStrokeWithDashes()
        assertNotNull(stroke)
    }

    @Test void createStrokeWithHatch() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer stroke = recipes.createStrokeWithHatch()
        assertNotNull(stroke)
    }

    @Test void createStrokeWithSpacedSymbols() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer stroke = recipes.createStrokeWithSpacedSymbols()
        assertNotNull(stroke)
    }
    
    @Test void createStrokeWithAlternatingSymbols() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer stroke = recipes.createStrokeWithAlternatingSymbols()
        assertNotNull(stroke)
    }

    // Fill

    @Test void createFillWithColor() {
        StyleRecipes recipes = new StyleRecipes()
        Fill fill = recipes.createFillWithColor()
        assertNotNull(fill)
    }

    @Test void createFillWithStroke() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createFillWithStroke()
        assertNotNull(symbolizer)
    }

    @Test void createFillWithColorAndOpacity() {
        StyleRecipes recipes = new StyleRecipes()
        Fill fill = recipes.createFillWithColorAndOpacity()
        assertNotNull(fill)
    }

    @Test void createFillWithMap() {
        StyleRecipes recipes = new StyleRecipes()
        Fill fill = recipes.createFillWithMap()
        assertNotNull(fill)
    }

    @Test void createFillWithIcon() {
        StyleRecipes recipes = new StyleRecipes()
        Fill fill = recipes.createFillWithIcon()
        assertNotNull(fill)
    }

    @Test void createFillWithHatch() {
        StyleRecipes recipes = new StyleRecipes()
        Fill fill = recipes.createFillWithHatch()
        assertNotNull(fill)
    }

    @Test void createFillWithRandom() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer fill = recipes.createFillWithRandom()
        assertNotNull(fill)
    }

    @Test void createFillWithRecodeFunction() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer fill = recipes.createFillWithRecodeFunction()
        assertNotNull(fill)
    }

    // Shape

    @Test void createShapeWithColor() {
        StyleRecipes recipes = new StyleRecipes()
        Shape shape = recipes.createShapeWithColor()
        assertNotNull(shape)
    }

    @Test void createShape() {
        StyleRecipes recipes = new StyleRecipes()
        Shape shape = recipes.createShape()
        assertNotNull(shape)
    }

    @Test void createShapeWithNamedParams() {
        StyleRecipes recipes = new StyleRecipes()
        Shape shape = recipes.createShapeWithNamedParams()
        assertNotNull(shape)
    }

    @Test void createShapeWithStroke() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createShapeWithStroke()
        assertNotNull(symbolizer)
    }

    // Icon

    @Test void createIcon() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createIcon()
        assertNotNull(symbolizer)
    }

    @Test void createIconWithParams() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createIconWithParams()
        assertNotNull(symbolizer)
    }

    // Labels

    @Test void createLabelForPoints() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForPoints()
        assertNotNull(symbolizer)
    }

    @Test void createLabelForPointsWithFont() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForPointsWithFont()
        assertNotNull(symbolizer)
    }

    @Test void createLabelForPointsWithHalo() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForPointsWithHalo()
        assertNotNull(symbolizer)
    }

    @Test void createLabelForPolygons() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForPolygons()
        assertNotNull(symbolizer)
    }

    @Test void createLabelForPolygonsWithExpression() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForPolygonsWithExpression()
        assertNotNull(symbolizer)
    }

    @Test void createLabelForPolygonsWithStrikeThrough() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForPolygonsWithStrikeThrough()
        assertNotNull(symbolizer)
    }

    @Test void createLabelForPolygonsWithUnderline() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForPolygonsWithUnderline()
        assertNotNull(symbolizer)
    }

    @Test void createLabelForPolygonsWithSpacing() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForPolygonsWithSpacing()
        assertNotNull(symbolizer)
    }

    @Test void createLabelWithExpression() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelWithExpression()
        assertNotNull(symbolizer)
    }

    @Disabled
    @Test void createLabelForLines() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForLines()
        assertNotNull(symbolizer)
    }

    // Transform

    @Test void createNormalTransform() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createNormalTransform()
        assertNotNull(symbolizer)
    }

    @Test void createRenderingTransform() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createRenderingTransform()
        assertNotNull(symbolizer)
    }

    // Gradient

    @Test void createGradientOnFieldWithQuantile() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createGradientOnFieldWithQuantile()
        assertNotNull(symbolizer)
    }

    @Test void createGradientOnFieldWithEqualInterval() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createGradientOnFieldWithEqualInterval()
        assertNotNull(symbolizer)
    }

    @Test void createGradientCustom() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createGradientCustom()
        assertNotNull(symbolizer)
    }

    // Unique Values

    @Test void createUniqueValues() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createUniqueValues()
        assertNotNull(symbolizer)
    }

    @Test void createUniqueValuesWithClosure() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createUniqueValuesWithClosure()
        assertNotNull(symbolizer)
    }

    @Test void createUniqueValuesWithPalette() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createUniqueValuesWithPalette()
        assertNotNull(symbolizer)
    }

    @Test void createUniqueValuesReader() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createUniqueValuesReader()
        assertNotNull(symbolizer)
    }

    // Raster

    @Test void createRasterColorMap() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createRasterColorMap()
        assertNotNull(symbolizer)
    }

    @Test void createRasterColorMapWithOpacity() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createRasterColorMapWithOpacity()
        assertNotNull(symbolizer)
    }

    @Test void createRasterColorMapWithPalette() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createRasterColorMapWithPalette()
        assertNotNull(symbolizer)
    }

    @Test void createRasterColorMapWithIntervals() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createRasterColorMapWithIntervals()
        assertNotNull(symbolizer)
    }

    @Test void createRasterChannelContrastEnhancementNormalize() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createRasterChannelContrastEnhancementNormalize()
        assertNotNull(symbolizer)
    }

    @Test void createRasterChannelContrastEnhancementHistogram() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createRasterChannelContrastEnhancementHistogram()
        assertNotNull(symbolizer)
    }

    // Style IO

    @Test void listStyleReaders() {
        StyleRecipes recipes = new StyleRecipes()
        List<Reader> readers = recipes.listStyleReaders()
        assertTrue(readers.size() > 0)
    }

    @Test void findStyleReader() {
        StyleRecipes recipes = new StyleRecipes()
        Reader reader = recipes.findStyleReader()
        assertNotNull(reader)
    }

    @Test void listStyleWriters() {
        StyleRecipes recipes = new StyleRecipes()
        List<Writer> writers = recipes.listStyleWriters()
        assertTrue(writers.size() > 0)
    }

    @Test void findStyleWriter() {
        StyleRecipes recipes = new StyleRecipes()
        Writer writer = recipes.findStyleWriter()
        assertNotNull(writer)
    }

    // SLD

    @Test void writeSld() {
        StyleRecipes recipes = new StyleRecipes()
        String sld = recipes.writeSld()
        assertNotNull(sld)
    }

    @Test void writeSldWithNamedLayer() {
        StyleRecipes recipes = new StyleRecipes()
        String sld = recipes.writeSldWithNamedLayer()
        assertNotNull(sld)
    }

    @Test void readSld() {
        StyleRecipes recipes = new StyleRecipes()
        Style style = recipes.readSld()
        assertNotNull(style)
    }

    // CSS

    @Test void readCss() {
        StyleRecipes recipes = new StyleRecipes()
        Style style = recipes.readCss()
        assertNotNull(style)
    }

    // YSLD

    @Test void writeYSld() {
        StyleRecipes recipes = new StyleRecipes()
        String ysld = recipes.writeYSLD()
        assertNotNull(ysld)
    }

    @Test void readYSld() {
        StyleRecipes recipes = new StyleRecipes()
        Style style = recipes.readYSLD()
        assertNotNull(style)
    }

    // Simple

    @Test void readSimpleStyleFillStrokeString() {
        StyleRecipes recipes = new StyleRecipes()
        Style style = recipes.readSimpleStyleFillStrokeString()
        assertNotNull(style)
    }

    @Test void readSimpleStyleFillStrokeAndLabelString() {
        StyleRecipes recipes = new StyleRecipes()
        Style style = recipes.readSimpleStyleFillStrokeAndLabelString()
        assertNotNull(style)
    }

    @Test void readSimpleStyleShapeString() {
        StyleRecipes recipes = new StyleRecipes()
        Style style = recipes.readSimpleStyleShapeString()
        assertNotNull(style)
    }

    @Test void readSimpleStyleFillStrokeMap() {
        StyleRecipes recipes = new StyleRecipes()
        Style style = recipes.readSimpleStyleFillStrokeMap()
        assertNotNull(style)
    }

    @Test void readSimpleStyleLineLabelString() {
        StyleRecipes recipes = new StyleRecipes()
        Style style = recipes.readSimpleStyleLineLabelString()
        assertNotNull(style)
    }

    @Test void readSimpleStylePointLabelString() {
        StyleRecipes recipes = new StyleRecipes()
        Style style = recipes.readSimpleStylePointLabelString()
        assertNotNull(style)
    }

    // ColorTable

    @Test void writeColorTable() {
        StyleRecipes recipes = new StyleRecipes()
        String str = recipes.writeColorTable()
        assertEquals("""25.0 178 156 195
473.75 79 142 187
922.5 143 146 56
1371.25 193 132 55
1820.0 181 214 177""".normalize(), str.normalize())
    }

    @Test void readColorTable() {
        StyleRecipes recipes = new StyleRecipes()
        Style style = recipes.readColorTable()
        assertNotNull(style)
    }

    // Style Repositories

    @Test void useDirectoryStyleRepository() {
        StyleRecipes recipes = new StyleRecipes()
        StyleRepository styleRepository = recipes.useDirectoryStyleRepository()
        assertNotNull(styleRepository)
    }

    @Test void useNestedDirectoryStyleRepository() {
        StyleRecipes recipes = new StyleRecipes()
        StyleRepository styleRepository = recipes.useNestedDirectoryStyleRepository()
        assertNotNull(styleRepository)
    }

    @Test void useSqliteDatabaseStyleRepository() {
        StyleRecipes recipes = new StyleRecipes()
        StyleRepository styleRepository = recipes.useSqliteDatabaseStyleRepository()
        assertNotNull(styleRepository)
    }

    @Test void useH2DatabaseStyleRepository() {
        StyleRecipes recipes = new StyleRecipes()
        StyleRepository styleRepository = recipes.useH2DatabaseStyleRepository()
        assertNotNull(styleRepository)
    }

    @Disabled @Test void usePostGISDatabaseStyleRepository() {
        StyleRecipes recipes = new StyleRecipes()
        StyleRepository styleRepository = recipes.usePostGISDatabaseStyleRepository()
        assertNotNull(styleRepository)
    }

}
