package geoscript.cookbook

import geoscript.carto.CartoBuilder
import geoscript.carto.CartoFactories
import geoscript.carto.DateTextItem
import geoscript.carto.HorizontalAlign
import geoscript.carto.MapItem
import geoscript.carto.NorthArrowItem
import geoscript.carto.PageSize
import geoscript.carto.RectangleItem
import geoscript.carto.ScaleTextItem
import geoscript.carto.TextItem
import geoscript.carto.VerticalAlign
import geoscript.geom.Bounds
import geoscript.layer.Layer
import geoscript.proj.Projection
import geoscript.render.Map
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
                .northArrow(new NorthArrowItem(pageSize.width - 60, pageSize.height - 80, 40, 60))
                .build(outputStream)

        }
        // end::northArrow[]
        File toFile = new File("src/docs/asciidoc/images/carto_northarrow.png")
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
}
