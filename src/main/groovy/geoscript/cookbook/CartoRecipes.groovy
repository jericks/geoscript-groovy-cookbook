package geoscript.cookbook

import geoscript.carto.CartoBuilder
import geoscript.carto.CartoFactories
import geoscript.carto.DateTextItem
import geoscript.carto.GridItem
import geoscript.carto.HorizontalAlign
import geoscript.carto.ImageCartoBuilder
import geoscript.carto.ImageItem
import geoscript.carto.LegendItem
import geoscript.carto.LineItem
import geoscript.carto.MapItem
import geoscript.carto.NorthArrowItem
import geoscript.carto.NorthArrowStyle
import geoscript.carto.OverviewMapItem
import geoscript.carto.PageSize
import geoscript.carto.ParagraphItem
import geoscript.carto.PdfCartoBuilder
import geoscript.carto.RectangleItem
import geoscript.carto.ScaleBarItem
import geoscript.carto.ScaleTextItem
import geoscript.carto.SvgCartoBuilder
import geoscript.carto.TextItem
import geoscript.carto.VerticalAlign
import geoscript.geom.Bounds
import geoscript.layer.Layer
import geoscript.proj.Projection
import geoscript.render.Map
import geoscript.style.Shape
import geoscript.style.Stroke
import geoscript.style.io.SLDReader
import geoscript.workspace.GeoPackage
import geoscript.workspace.Workspace

import javax.imageio.ImageIO
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage

class CartoRecipes extends Recipes {

    BufferedImage map() {
        // tag::map[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
            layers: [ocean, countries],
            bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
            projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                    .create(pageSize)
                    .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                        .fillColor(Color.WHITE)
                    )
                    .map(new MapItem(20, 20, pageSize.width - 40, pageSize.height - 40).map(map))
                    .build(outputStream)

        }
        // end::map[]
        File toFile = new File("src/docs/asciidoc/images/carto_map.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage overViewMap() {
        // tag::overViewMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-166.436348,6.574916,-12.451973,60.715022, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )
        Map overViewMap = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                .create(pageSize)
                .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                    .fillColor(Color.WHITE)
                )
                .map(new MapItem(20, 20, pageSize.width - 40, pageSize.height - 40).map(map))
                .rectangle(new RectangleItem(20, 20, pageSize.width - 40, pageSize.height - 40))
                .overViewMap(new OverviewMapItem(40, pageSize.height - 240, 200, 200)
                    .linkedMap(map)
                    .overviewMap(overViewMap)
                    .zoomIntoBounds(false)
                )
                .rectangle(new RectangleItem(40, pageSize.height - 240, 200,200))
                .build(outputStream)

        }
        // end::overViewMap[]
        File toFile = new File("src/docs/asciidoc/images/carto_overviewmap.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage text() {
        // tag::text[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                    .create(pageSize)
                    .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                        .fillColor(Color.WHITE)
                    )
                    .text(new TextItem(20,20, pageSize.width - 40, 60)
                        .text("World Map")
                        .font(new Font("Arial", Font.BOLD, 42))
                        .verticalAlign(VerticalAlign.MIDDLE)
                        .horizontalAlign(HorizontalAlign.CENTER)
                    )
                    .map(new MapItem(20, 80, pageSize.width - 40, pageSize.height - 100).map(map))
                    .build(outputStream)

        }
        // end::text[]
        File toFile = new File("src/docs/asciidoc/images/carto_text.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage rectangle() {
        // tag::rectangle[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                    .create(pageSize)
                    .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                        .fillColor(Color.WHITE)
                    )
                    .rectangle(new RectangleItem(10,10, pageSize.width - 20, pageSize.height - 20))
                    .rectangle(new RectangleItem(20,20, pageSize.width - 40, 60))
                    .rectangle(new RectangleItem(20,90, pageSize.width - 40, pageSize.height - 110))
                    .text(new TextItem(20,20, pageSize.width - 40, 60)
                        .text("World Map")
                        .font(new Font("Arial", Font.BOLD, 32))
                        .verticalAlign(VerticalAlign.MIDDLE)
                        .horizontalAlign(HorizontalAlign.CENTER)
                    )
                    .map(new MapItem(30, 100, pageSize.width - 60, pageSize.height - 120).map(map))
                    .build(outputStream)

        }
        // end::rectangle[]
        File toFile = new File("src/docs/asciidoc/images/carto_rect.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage northArrow() {
        // tag::northArrow[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                .create(pageSize)
                .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                    .fillColor(Color.WHITE)
                )
                .map(new MapItem(20, 20, pageSize.width - 40, pageSize.height - 40).map(map))
                .northArrow(new NorthArrowItem(pageSize.width - 60, pageSize.height - 100, 40, 80)
                    .font(new Font("Arial", Font.BOLD, 24))
                    .drawText(true))
                .build(outputStream)

        }
        // end::northArrow[]
        File toFile = new File("src/docs/asciidoc/images/carto_northarrow.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage northArrow2() {
        // tag::northArrow2[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                    .create(pageSize)
                    .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                            .fillColor(Color.WHITE)
                    )
                    .map(new MapItem(20, 20, pageSize.width - 40, pageSize.height - 40).map(map))
                    .northArrow(new NorthArrowItem(pageSize.width - 100, pageSize.height - 100, 80, 80)
                        .style(NorthArrowStyle.NorthEastSouthWest)
                        .font(new Font("Arial", Font.BOLD, 14))
                        .drawText(true))
                    .build(outputStream)

        }
        // end::northArrow2[]
        File toFile = new File("src/docs/asciidoc/images/carto_northarrow2.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage legendFromMap() {
        // tag::legendFromMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Layer places = workspace.get("places")
        places.style = new Shape("red", 8, "star")
        Layer rivers = workspace.get("rivers")
        rivers.style = new Stroke("blue", 1)
        Map map = new Map(
                layers: [ocean, countries, rivers, places],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                    .create(pageSize)
                    .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                            .fillColor(Color.WHITE)
                    )
                    .map(new MapItem(220, 20, pageSize.width - 240, pageSize.height - 40).map(map))
                    .legend(new LegendItem(20, 20, 200, pageSize.height - 40).addMap(map))
                    .build(outputStream)

        }
        // end::legendFromMap[]
        File toFile = new File("src/docs/asciidoc/images/carto_legendFromMap.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage dateText() {
        // tag::dateText[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                .create(pageSize)
                .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                    .fillColor(Color.WHITE)
                )
                .text(new TextItem(20,15, pageSize.width - 40, 60)
                    .text("World Map")
                    .font(new Font("Arial", Font.BOLD, 42))
                    .verticalAlign(VerticalAlign.TOP)
                    .horizontalAlign(HorizontalAlign.CENTER)
                )
                .dateText(new DateTextItem(20,58, pageSize.width - 40, 20)
                    .font(new Font("Arial", Font.ITALIC, 18))
                    .verticalAlign(VerticalAlign.BOTTOM)
                    .horizontalAlign(HorizontalAlign.CENTER)
                )
                .map(new MapItem(20, 80, pageSize.width - 40, pageSize.height - 100).map(map))
                .build(outputStream)

        }
        // end::dateText[]
        File toFile = new File("src/docs/asciidoc/images/carto_datetext.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage scaleText() {
        // tag::scaleText[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                .create(pageSize)
                .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                    .fillColor(Color.WHITE)
                )
                .text(new TextItem(20,15, pageSize.width - 40, 60)
                    .text("World Map")
                    .font(new Font("Arial", Font.BOLD, 42))
                    .verticalAlign(VerticalAlign.TOP)
                    .horizontalAlign(HorizontalAlign.CENTER)
                )
                .scaleText(new ScaleTextItem(20,58, pageSize.width - 40, 20)
                    .map(map)
                    .format("#")
                    .prefixText("Scale: ")
                    .font(new Font("Arial", Font.ITALIC, 18))
                    .verticalAlign(VerticalAlign.BOTTOM)
                    .horizontalAlign(HorizontalAlign.CENTER)
                )
                .map(new MapItem(20, 80, pageSize.width - 40, pageSize.height - 100).map(map))
                .build(outputStream)

        }
        // end::scaleText[]
        File toFile = new File("src/docs/asciidoc/images/carto_scaletext.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage scaleBar() {
        // tag::scaleBar[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                    .create(pageSize)
                    .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                            .fillColor(Color.WHITE)
                    )
                    .text(new TextItem(20,15, pageSize.width - 40, 60)
                            .text("World Map")
                            .font(new Font("Arial", Font.BOLD, 42))
                            .verticalAlign(VerticalAlign.TOP)
                            .horizontalAlign(HorizontalAlign.CENTER)
                    )
                    .map(new MapItem(20, 80, pageSize.width - 40, pageSize.height - 100).map(map))
                    .scaleBar(new ScaleBarItem(20,pageSize.height - 40, 300, 20)
                            .map(map)
                            .units(ScaleBarItem.Units.METRIC)
                    )
                    .build(outputStream)

        }
        // end::scaleBar[]
        File toFile = new File("src/docs/asciidoc/images/carto_scalebar.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage line() {
        // tag::line[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                    .create(pageSize)
                    .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                        .fillColor(Color.WHITE)
                    )
                    .text(new TextItem(20,20, pageSize.width - 40, 60)
                        .text("World Map")
                        .font(new Font("Arial", Font.BOLD, 42))
                        .verticalAlign(VerticalAlign.MIDDLE)
                        .horizontalAlign(HorizontalAlign.CENTER)
                    )
                    .line(new LineItem(20, 70, pageSize.width - 40, 1)
                        .strokeWidth(2)
                        .strokeColor(Color.DARK_GRAY)
                    )
                    .map(new MapItem(20, 80, pageSize.width - 40, pageSize.height - 100).map(map))
                    .build(outputStream)

        }
        // end::line[]
        File toFile = new File("src/docs/asciidoc/images/carto_line.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage grid() {
        // tag::grid[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                    .create(pageSize)
                    .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                        .fillColor(Color.WHITE)
                    )
                    .grid(new GridItem(0,0,pageSize.width, pageSize.height)
                        .size(20)
                        .strokeColor(Color.GRAY)
                        .strokeWidth(1.0)
                    )
                    .text(new TextItem(20,20, pageSize.width - 40, 60)
                        .text("World Map")
                        .font(new Font("Arial", Font.BOLD, 42))
                        .verticalAlign(VerticalAlign.MIDDLE)
                        .horizontalAlign(HorizontalAlign.CENTER)
                    )
                    .map(new MapItem(20, 80, pageSize.width - 40, pageSize.height - 100).map(map))
                    .build(outputStream)

        }
        // end::grid[]
        File toFile = new File("src/docs/asciidoc/images/carto_grid.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage paragraph() {
        // tag::paragraph[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                .create(pageSize)
                .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                    .fillColor(Color.WHITE)
                )
                .map(new MapItem(20, 20, pageSize.width - 40, pageSize.height - 100).map(map))
                .paragraph(new ParagraphItem(20, pageSize.height - 60, pageSize.width - 40, 60)
                    .font(new Font("Arial", Font.PLAIN, 12))
                    .color(Color.BLACK)
                    .text("""Natural Earth is a public domain map dataset available at 1:10m, 1:50m, and 1:110 million scales. 
Featuring tightly integrated vector and raster data, with Natural Earth you can make a variety of visually pleasing, 
well-crafted maps with cartography or GIS software.
""")
                )
                .build(outputStream)

        }
        // end::paragraph[]
        File toFile = new File("src/docs/asciidoc/images/carto_paragraph.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage image() {
        // tag::image[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            PageSize pageSize = PageSize.LETTER_LANDSCAPE

            CartoFactories.findByName("png")
                    .create(pageSize)
                    .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                        .fillColor(Color.WHITE)
                    )
                    .map(new MapItem(20, 20, pageSize.width - 40, pageSize.height - 40).map(map))
                    .image(new ImageItem(pageSize.width - 100, pageSize.height - 100, 80, 80)
                        .path(new File("src/main/resources/image.png"))
                    )
                    .build(outputStream)

        }
        // end::image[]
        File toFile = new File("src/docs/asciidoc/images/carto_image.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    BufferedImage imageBuilder() {
        // tag::image_builder[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            new ImageCartoBuilder(PageSize.LETTER_LANDSCAPE, ImageCartoBuilder.ImageType.PNG)
                .rectangle(new RectangleItem(0, 0, 792, 612).strokeColor(Color.WHITE).fillColor(Color.WHITE))
                .rectangle(new RectangleItem(10, 10, 772, 592))
                .rectangle(new RectangleItem(20, 20, 752, 80))
                .text(new TextItem(30, 50, 200, 20).text("Map Title").font(new Font("Arial", Font.BOLD, 36)))
                .dateText(new DateTextItem(30, 85, 200, 10).font(new Font("Arial", Font.ITALIC, 14)))
                .scaleText(new ScaleTextItem(150, 85, 200, 10).map(map).font(new Font("Arial", Font.ITALIC, 14)))
                .paragraph(new ParagraphItem(250, 30, 380, 70).text("""Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.
""").font(new Font("Arial", Font.PLAIN, 8)))
                .line(new LineItem(710, 30, 1, 60))
                .image(new ImageItem(640, 30, 60, 60).path(new File(getClass().getClassLoader().getResource("image.png").toURI())))
                .northArrow(new NorthArrowItem(720, 30, 40, 60))
                .map(new MapItem(20, 110, 752, 480).map(map))
                .rectangle(new RectangleItem(20, 110, 752, 480))

                .build(outputStream)

        }
        // end::image_builder[]
        File toFile = new File("src/docs/asciidoc/images/carto_image_builder.png")
        moveFile(file, toFile)
        ImageIO.read(toFile)
    }

    File pdfBuilder() {
        // tag::pdf_builder[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.pdf")
        file.withOutputStream { OutputStream outputStream ->

            new PdfCartoBuilder(PageSize.LETTER_LANDSCAPE)
                .rectangle(new RectangleItem(0, 0, 792, 612).strokeColor(Color.WHITE).fillColor(Color.WHITE))
                .rectangle(new RectangleItem(10, 10, 772, 592))
                .rectangle(new RectangleItem(20, 20, 752, 80))
                .text(new TextItem(30, 50, 200, 20).text("Map Title").font(new Font("Arial", Font.BOLD, 36)))
                .dateText(new DateTextItem(30, 85, 200, 10).font(new Font("Arial", Font.ITALIC, 14)))
                .scaleText(new ScaleTextItem(150, 85, 200, 10).map(map).font(new Font("Arial", Font.ITALIC, 14)))
                .paragraph(new ParagraphItem(250, 30, 380, 70).text("""Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.
""").font(new Font("Arial", Font.PLAIN, 8)))
                .line(new LineItem(710, 30, 1, 60))
                .image(new ImageItem(640, 30, 60, 60).path(new File(getClass().getClassLoader().getResource("image.png").toURI())))
                .northArrow(new NorthArrowItem(720, 30, 40, 60))
                .map(new MapItem(20, 110, 752, 480).map(map))
                .rectangle(new RectangleItem(20, 110, 752, 480))
                .build(outputStream)

        }
        // end::pdf_builder[]
        file
    }


    File svgBuilder() {
        // tag::svg_builder[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        Map map = new Map(
                layers: [ocean, countries],
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                projection: new Projection("EPSG:3857")
        )

        File file = new File("map.svg")
        file.withOutputStream { OutputStream outputStream ->

            new SvgCartoBuilder(PageSize.LETTER_LANDSCAPE)
                .rectangle(new RectangleItem(0, 0, 792, 612).strokeColor(Color.WHITE).fillColor(Color.WHITE))
                .rectangle(new RectangleItem(10, 10, 772, 592))
                .rectangle(new RectangleItem(20, 20, 752, 80))
                .text(new TextItem(30, 50, 200, 20).text("Map Title").font(new Font("Arial", Font.BOLD, 36)))
                .dateText(new DateTextItem(30, 85, 200, 10).font(new Font("Arial", Font.ITALIC, 14)))
                .scaleText(new ScaleTextItem(150, 85, 200, 10).map(map).font(new Font("Arial", Font.ITALIC, 14)))
                .paragraph(new ParagraphItem(250, 30, 380, 70).text("""Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.
""").font(new Font("Arial", Font.PLAIN, 8)))
                .line(new LineItem(710, 30, 1, 60))
                .image(new ImageItem(640, 30, 60, 60).path(new File(getClass().getClassLoader().getResource("image.png").toURI())))
                .northArrow(new NorthArrowItem(720, 30, 40, 60))
                .map(new MapItem(20, 110, 752, 480).map(map))
                .rectangle(new RectangleItem(20, 110, 752, 480))
                .build(outputStream)

        }
        // end::svg_builder[]
        file
    }

}
