package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.layer.Raster
import geoscript.layer.WMS
import geoscript.layer.WMSLayer
import geoscript.render.Map

import java.awt.image.BufferedImage

class WMSRecipes extends Recipes {

    void createWMS() {
        // tag::wms_create[]
        WMS wms = new WMS("https://lpdaacsvc.cr.usgs.gov/ogc/wms")
        println "Name = ${wms.name}"
        println "Title = ${wms.title}"
        println "Abstract = ${wms.abstract}"
        println "Keywords = ${wms.keywords.join(', ')}"
        println "Online Resource = ${wms.onlineResource}"
        println "Update Sequence = ${wms.updateSequence}"
        println "Version = ${wms.version}"
        println "Map Formats = ${wms.getMapFormats.join(', ')}"
        // end::wms_create[]
        writeFile("wms_create", """Name = ${wms.name}
Title = ${wms.title}                                           
Abstract = ${wms.abstract}
Keywords = ${wms.keywords.join(", ")}
Online Resource = ${wms.onlineResource}
Update Sequence = ${wms.updateSequence}
Version = ${wms.version}
Map Formats = ${wms.getMapFormats.join(", ")}
""")
    }

    void getLayers() {
        // tag::wms_layers[]
        WMS wms = new WMS("https://lpdaacsvc.cr.usgs.gov/ogc/wms")
        wms.layers.subList(0,10).each { WMS.Layer layer ->
            println layer
        }
        // end::wms_layers[]
        writeFile("wms_layers", wms.layers.collect { it }.join(NEW_LINE))
    }

    void getLayer() {
        // tag::wms_layer[]
        WMS wms = new WMS("https://lpdaacsvc.cr.usgs.gov/ogc/wms")
        WMS.Layer layer = wms.getLayer("MODIS:MOD09A1.2018185.006.SurRefl")
        println "Name = ${layer.name}"
        println "Title = ${layer.title}"
        println "Bounds = ${layer.bounds}"
        println "Lat/Lon Bounds = ${layer.latLonBounds}"
        println "Queryable = ${layer.queryable}"
        println "Min Scale = ${layer.minScale}"
        println "Max Scale = ${layer.maxScale}"
        // end::wms_layer[]
        writeFile("wms_layer", """             
Name = ${layer.name}
Title = ${layer.title}
Bounds = ${layer.bounds}
Lat/Lon Bounds = ${layer.latLonBounds}
Queryable = ${layer.queryable}
Min Scale = ${layer.minScale}
Max Scale = ${layer.maxScale}
""")
    }

    void getRaster() {
        // tag::wms_raster[]
        WMS wms = new WMS("https://lpdaacsvc.cr.usgs.gov/ogc/wms")
        Raster raster = wms.getRaster(["MODIS:MOD14A2.2018209.006.Fire"])
        // end::wms_raster[]
        draw("wms_raster", [raster])
    }

    void getImage() {
        // tag::wms_image[]
        WMS wms = new WMS("https://lpdaacsvc.cr.usgs.gov/ogc/wms")
        BufferedImage image = wms.getImage(["MODIS:MOD17A2H.2019041.006.GPP"])
        // end::wms_image[]
        saveImage("wms_image", image)
    }

    void getLegend() {
        // tag::wms_legend[]
        WMS wms = new WMS("https://lpdaacsvc.cr.usgs.gov/ogc/wms")
        BufferedImage image = wms.getLegend(["MODIS:MOD17A2H.2019041.006.GPP"])
        // end::wms_legend[]
        saveImage("wms_legend", image)
    }

    void drawWmsLayer() {
        // tag::drawWmsLayer[]
        WMS wms = new WMS("http://1maps.geo-solutions.it/geoserver/osm/wms?service=wms&version=1.1.1&request=GetCapabilities")
        WMSLayer layer = new WMSLayer(wms, ["icesheet_polygons", "ne_10m_admin_0_countries"])
        Map map = new Map(layers: [layer], backgroundColor: "#B0C4DE", bounds: new Bounds(-179, -85, 179, 85, "EPSG:4326").reproject("EPSG:3857"))
        BufferedImage image = map.renderToImage()
        // end::drawWmsLayer[]
        saveImage("wms_layer_map", image)
    }

    static void main(String[] args) {
        WMSRecipes recipes = new WMSRecipes()
        recipes.createWMS()
        recipes.getLayers()
        recipes.getLayer()
        recipes.getRaster()
        recipes.getImage()
        recipes.drawWmsLayer()
    }

}
