package geoscript.cookbook

import geoscript.filter.Color

class FilterRecipes extends Recipes {

    // Color

    Color createColorFromRBGString() {
        // tag::createColorFromRBGString[]
        Color color = new Color("0,255,0")
        // end::createColorFromRBGString[]
        saveImage("filter_color_rbgstring", Color.drawToImage([color]))
        color
    }

    Color createColorFromCSSColorName() {
        // tag::createColorFromCSSColorName[]
        Color color = new Color("silver")
        // end::createColorFromCSSColorName[]
        saveImage("filter_color_csscolorname", Color.drawToImage([color]))
        color
    }

    Color createColorFromHexadecimal() {
        // tag::createColorFromHexadecimal[]
        Color color = new Color("#0000ff")
        // end::createColorFromHexadecimal[]
        saveImage("filter_color_hexadecimal", Color.drawToImage([color]))
        color
    }

    Color createColorFromRGBList() {
        // tag::createColorFromRGBList[]
        Color color = new Color([255,0,0])
        // end::createColorFromRGBList[]
        saveImage("filter_color_rgblist", Color.drawToImage([color]))
        color
    }

    Color createColorFromRGBMap() {
        // tag::createColorFromRGBMap[]
        Color color = new Color([r: 5, g: 35, b:45])
        // end::createColorFromRGBMap[]
        saveImage("filter_color_rgbmap", Color.drawToImage([color]))
        color
    }

    Color createColorFromHLSMap() {
        // tag::createColorFromHLSMap[]
        Color color = new Color([h: 0, s: 1.0, l: 0.5])
        // end::createColorFromHLSMap[]
        saveImage("filter_color_hlsmap", Color.drawToImage([color]))
        color
    }

    Color getRandomColor() {
        // tag::getRandomColor[]
        Color color = Color.getRandom()
        // end::getRandomColor[]
        saveImage("filter_color_random", Color.drawToImage([color]))
        color
    }

    Color getRandomPastelColor() {
        // tag::getRandomPastelColor[]
        Color color = Color.getRandomPastel()
        // end::getRandomPastelColor[]
        saveImage("filter_color_random_pastel", Color.drawToImage([color]))
        color
    }

    // Color Palettes

    Map<String, List<String>> getColorPalettes() {
        Map<String, List<String>> paletteMap = [:]

        // tag::getColorPalettes_all[]
        List<String> allPalettes = Color.getPaletteNames("all")
        allPalettes.each { String name ->
            println name
        }
        // end::getColorPalettes_all[]
        writeFile("getColorPalettes_all","${allPalettes.join(NEW_LINE)}")
        paletteMap.put('all', allPalettes)

        // tag::getColorPalettes_diverging[]
        List<String> divergingPalettes = Color.getPaletteNames("diverging")
        divergingPalettes.each { String name ->
            println name
        }
        // end::getColorPalettes_diverging[]
        writeFile("getColorPalettes_diverging","${divergingPalettes.join(NEW_LINE)}")
        paletteMap.put('diverging', divergingPalettes)

        // tag::getColorPalettes_qualitative[]
        List<String> qualitativePalettes = Color.getPaletteNames("qualitative")
        qualitativePalettes.each { String name ->
            println name
        }
        // end::getColorPalettes_qualitative[]
        writeFile("getColorPalettes_qualitative","${qualitativePalettes.join(NEW_LINE)}")
        paletteMap.put('qualitative', qualitativePalettes)

        // tag::getColorPalettes_sequential[]
        List<String> sequentialPalettes = Color.getPaletteNames("sequential")
        sequentialPalettes.each { String name ->
            println name
        }
        // end::getColorPalettes_sequential[]
        writeFile("getColorPalettes_sequential","${sequentialPalettes.join(NEW_LINE)}")
        paletteMap.put('sequential', sequentialPalettes)

        paletteMap
    }

    Map<String, List<Color>> getPaletteColors() {

        Map<String, List<Color>> colorMap = [:]

        // tag::getPaletteColors_BuGn[]
        List colors = Color.getPaletteColors("BuGn")
        // end::getPaletteColors_BuGn[]
        saveImage("color_palette_bugn", Color.drawToImage(colors, "horizontal"))
        colorMap.put("BuGn", colors)

        // tag::getPaletteColors_Purples[]
        colors = Color.getPaletteColors("Purples", 4)
        // end::getPaletteColors_Purples[]
        saveImage("color_palette_purples", Color.drawToImage(colors, "horizontal"))
        colorMap.put("Purples", colors)

        // tag::getPaletteColors_MutedTerrain[]
        colors = Color.getPaletteColors("MutedTerrain")
        // end::getPaletteColors_MutedTerrain[]
        saveImage("color_palette_mutedterrain", Color.drawToImage(colors, "horizontal"))
        colorMap.put("MutedTerrain", colors)

        // tag::getPaletteColors_BlueToYellowToRedHeatMap[]
        colors = Color.getPaletteColors("BlueToYellowToRedHeatMap")
        // end::getPaletteColors_BlueToYellowToRedHeatMap[]
        saveImage("color_palette_bluetoyellowtoredheatmap", Color.drawToImage(colors, "horizontal"))
        colorMap.put("BlueToYellowToRedHeatMap", colors)

        colorMap
    }

    // Color properties

    Map<String, String> getColorProperties() {

        Map<String,String> props = [:]

        // tag::getColorProperties_create[]
        Color color = new Color("wheat")
        // end::getColorProperties_create[]
        saveImage("getColorProperties", Color.drawToImage([color]))

        // tag::getColorProperties_hex[]
        String hex = color.hex
        println hex
        // end::getColorProperties_hex[]
        writeFile("getColorProperties_hex", "${hex}")
        props.put("hex", hex)

        // tag::getColorProperties_rgb[]
        List rgb = color.rgb
        println rgb
        // end::getColorProperties_rgb[]
        writeFile("getColorProperties_rgb", "${rgb}")
        props.put("rgb", rgb)

        // tag::getColorProperties_hsl[]
        List hsl = color.hsl
        println hsl
        // end::getColorProperties_hsl[]
        writeFile("getColorProperties_hsl", "${hsl}")
        props.put("hsl", hsl)

        props
    }

}
