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

}
