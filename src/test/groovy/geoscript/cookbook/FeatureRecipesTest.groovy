package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.feature.Field
import geoscript.feature.Schema
import geoscript.feature.io.Reader
import geoscript.feature.io.SchemaReader
import geoscript.feature.io.SchemaWriter
import geoscript.feature.io.Writer
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.*

class FeatureRecipesTest {

    @Test void createField() {
        FeatureRecipes recipes = new FeatureRecipes()
        Field field = recipes.createField()
        assertEquals("name: String", field.toString())
    }

    @Test void createGeometryField() {
        FeatureRecipes recipes = new FeatureRecipes()
        Field field = recipes.createGeometryField()
        assertEquals("geom: Point(EPSG:4326)", field.toString())
    }

    @Test void createFieldFromList() {
        FeatureRecipes recipes = new FeatureRecipes()
        Field field = recipes.createFieldFromList()
        assertEquals("geom: Polygon(EPSG:4326)", field.toString())
    }

    @Test void createFieldFromMap() {
        FeatureRecipes recipes = new FeatureRecipes()
        Field field = recipes.createFieldFromMap()
        assertEquals("geom: LineString(EPSG:4326)", field.toString())
    }

    @Test void getFieldProperties() {
        FeatureRecipes recipes = new FeatureRecipes()
        Field field = recipes.getFieldProperties()
        assertEquals("geom: Point(EPSG:4326)", field.toString())
    }

    @Test void createSchemaFromFields() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.createSchemaFromFields()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void createSchemaFromLists() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.createSchemaFromLists()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void createSchemaFromMaps() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.createSchemaFromMaps()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void createSchemaFromString() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.createSchemaFromString()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void getSchemaProperties() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.getSchemaProperties()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void getSchemaFields() {
        FeatureRecipes recipes = new FeatureRecipes()
        Map<String,Object> results = recipes.getSchemaFields()
        assertEquals(3, results.fields.size())
        assertNotNull(results.nameField)
        assertNotNull(results.idField)
        assertFalse(results.hasArea)
        assertTrue(results.hasGeom)
    }

    @Test void createFeatureFromSchemaMap() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromSchemaMap()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void createFeatureFromSchemaList() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromSchemaList()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void createFeatureFromSchemaFeature() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromSchemaFeature()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void createFeatureFromSchemaEmpty() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromSchemaEmpty()
        assertNotNull(feature.id)
        assertNull(feature.geom)
        assertNull(feature['id'])
        assertNull(feature['name'])
    }

    @Test void reprojectSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.reprojectSchema()
        assertEquals("cities_spws geom: Point(EPSG:2927), id: Integer, name: String", schema.toString())
    }

    @Test void changeGeometryTypeSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.changeGeometryTypeSchema()
        assertEquals("cities_buffer geom: Polygon(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void changeFieldSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.changeFieldSchema()
        assertEquals("cities_guid geom: Point(EPSG:4326), guid: String, name: String", schema.toString())
    }

    @Test void changeFieldsSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.changeFieldsSchema()
        assertEquals("cities_updated geom: Point(EPSG:4326), guid: String, description: String", schema.toString())
    }

    @Test void addFieldSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.addFieldSchema()
        assertEquals("countries_area geom: Polygon(EPSG:4326), id: Integer, name: String, area: Double", schema.toString())
    }

    @Test void addFieldsSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.addFieldsSchema()
        assertEquals("countries_areaperimeter geom: Polygon(EPSG:4326), id: Integer, name: String, area: Double, perimeter: Double", schema.toString())
    }

    @Test void removeFieldSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.removeFieldSchema()
        assertEquals("countries_updated geom: Polygon(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void removeFieldsSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.removeFieldsSchema()
        assertEquals("countries_updated geom: Polygon(EPSG:4326), id: Integer", schema.toString())
    }

    @Test void includeFieldsSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.includeFieldsSchema()
        assertEquals("countries_updated geom: Polygon(EPSG:4326), name: String", schema.toString())
    }

    @Test void addSchemaNoPrefixNoDuplicates() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.addSchemaNoPrefixNoDuplicates()
        assertEquals("business geom: Point(EPSG:4326), id: Integer, name: String, address: String", schema.toString())
    }

    @Test void addSchemaPostFixAllNoDuplicates() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.addSchemaPostFixAllNoDuplicates()
        assertEquals("business geom: Point(EPSG:4326), id1: Integer, name1: String, id2: Integer, name2: String, address2: String", schema.toString())
    }

    @Test void listSchemaReaders() {
        FeatureRecipes recipes = new FeatureRecipes()
        List<SchemaReader> readers = recipes.listSchemaReaders()
        assertTrue(readers.size() > 0)
    }

    @Test void findSchemaReader() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.findSchemaReader()
        assertEquals("layer geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void listSchemaWriters() {
        FeatureRecipes recipes = new FeatureRecipes()
        List<SchemaWriter> writers = recipes.listSchemaWriters()
        assertTrue(writers.size() > 0)
    }

    @Test void findSchemaWriter() {
        FeatureRecipes recipes = new FeatureRecipes()
        String schemaStr = recipes.findSchemaWriter()
        assertEquals("geom:Point:srid=4326,id:Integer,name:String", schemaStr)
    }

    @Test void readSchemaFromString() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.readSchemaFromString()
        assertEquals("points geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void writeSchemaToString() {
        FeatureRecipes recipes = new FeatureRecipes()
        String schemaStr = recipes.writeSchemaToString()
        assertEquals("geom:Point:srid=4326,id:Integer,name:String", schemaStr)
    }

    @Test void readSchemaFromJson() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.readSchemaFromJson()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void writeSchemaToJson() {
        FeatureRecipes recipes = new FeatureRecipes()
        String schemaStr = recipes.writeSchemaToJson()
        assertEquals("""{
    "name": "cities",
    "projection": "EPSG:4326",
    "geometry": "geom",
    "fields": [
        {
            "name": "geom",
            "type": "Point",
            "geometry": true,
            "projection": "EPSG:4326"
        },
        {
            "name": "id",
            "type": "Integer"
        },
        {
            "name": "name",
            "type": "String"
        }
    ]
}""".normalize(), schemaStr.normalize())
    }

    @Test void readSchemaFromXml() {
        FeatureRecipes recipes = new FeatureRecipes()
        Schema schema = recipes.readSchemaFromXml()
        assertEquals("cities geom: Point(EPSG:4326), id: Integer, name: String", schema.toString())
    }

    @Test void writeSchemaToXml() {
        FeatureRecipes recipes = new FeatureRecipes()
        String schemaStr = recipes.writeSchemaToXml()
        assertEquals("""<schema>
  <name>cities</name>
  <projection>EPSG:4326</projection>
  <geometry>geom</geometry>
  <fields>
    <field>
      <name>geom</name>
      <type>Point</type>
      <projection>EPSG:4326</projection>
    </field>
    <field>
      <name>id</name>
      <type>Integer</type>
    </field>
    <field>
      <name>name</name>
      <type>String</type>
    </field>
  </fields>
</schema>""".normalize(), schemaStr.normalize())
    }

    @Test void createFeatureFromMap() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromMap()
        assertEquals("feature.city.1 id: 1, name: Seattle, geom: POINT (-122.3204 47.6024)", feature.toString())
    }

    @Test void createFeatureFromMapWithSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromMapWithSchema()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void createFeatureFromListWithSchema() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.createFeatureFromListWithSchema()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void getFeatureProperties() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.getFeatureProperties()
        assertEquals("cities.city.1 geom: POINT (-122.3204 47.6024), id: 1, name: Seattle", feature.toString())
    }

    @Test void getAndSetFeatureAttributes() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.getAndSetFeatureAttributes()
        assertEquals("cities.city.1 geom: POINT (-122.222 47.5673), id: 2, name: Mercer Island", feature.toString())
    }

    // GeoJSON

    @Test void writeFeatureToGeoJson() {
        FeatureRecipes recipes = new FeatureRecipes()
        String geojson = recipes.writeFeatureToGeoJson()
        assertEquals('{"type":"Feature","geometry":{"type":"Point","coordinates":[-122.3204,47.6024]},' +
                '"properties":{"id":1,"name":"Seattle"},"id":"city.1"}', geojson)
    }

    @Test void featureGetGeoJson() {
        FeatureRecipes recipes = new FeatureRecipes()
        String geojson = recipes.featureGetGeoJson()
        assertEquals('{"type":"Feature","geometry":{"type":"Point","coordinates":[-122.3204,47.6024]},' +
                '"properties":{"id":1,"name":"Seattle"},"id":"city.1"}', geojson)
    }

    @Test void featureFromGeoJSON() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.featureFromGeoJSON()
        assertEquals('feature.city.1 id: 1, name: Seattle, geometry: POINT (-122.3204 47.6024)', feature.toString())
    }

    @Test void readFeatureFromGeoJson() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.readFeatureFromGeoJson()
        assertEquals('feature.city.1 id: 1, name: Seattle, geometry: POINT (-122.3204 47.6024)', feature.toString())
    }

    // GeoBuf

    @Test void featureGetGeobuf() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.featureGetGeobuf()
        assertNotNull str
    }

    @Test void featureFromGeoBuf() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.featureFromGeoBuf()
        assertEquals('features. geom: POINT (-122.3204 47.6024), id: 1, name: Seattle', feature.toString())
    }

    @Test void writeFeatureToGeoBuf() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.writeFeatureToGeoBuf()
        assertNotNull str
    }

    @Test void readFeatureFromGeoBuf() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.readFeatureFromGeoBuf()
        assertEquals('features. geom: POINT (-122.3204 47.6024), id: 1, name: Seattle', feature.toString())
    }

    // GeoRSS

    @Test void featureGetGeoRSS() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.featureGetGeoRSS()
        assertTrue(str.contains("<entry xmlns:georss='http://www.georss.org/georss' xmlns='http://www.w3.org/2005/Atom'>"))
        assertTrue(str.contains('<title>city.1</title>'))
        assertTrue(str.contains('<summary'))
        assertTrue(str.contains('>[geom:POINT (-122.3204 47.6024), id:1, name:Seattle]</summary>'))
        assertTrue(str.contains('<georss:point'))
        assertTrue(str.contains('>47.6024 -122.3204</georss:point>'))
    }

    @Test void featureFromGeoRSS() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.featureFromGeoRSS()
        assertEquals("POINT (-122.3204 47.6024)", feature.geom.wkt)
    }

    @Test void writeFeatureToGeoRSS() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.writeFeatureToGeoRSS()
        assertTrue(str.contains("<entry xmlns:georss='http://www.georss.org/georss' xmlns='http://www.w3.org/2005/Atom'>"))
        assertTrue(str.contains('<title>city.1</title>'))
        assertTrue(str.contains('<summary'))
        assertTrue(str.contains('>[geom:POINT (-122.3204 47.6024), id:1, name:Seattle]</summary>'))
        assertTrue(str.contains('<georss:point'))
        assertTrue(str.contains('>47.6024 -122.3204</georss:point>'))
    }

    @Test void readFeatureFromGeoRSS() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.readFeatureFromGeoRSS()
        assertEquals("POINT (-122.3204 47.6024)", feature.geom.wkt)
    }

    // GML

    @Test void featureGetGml() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.featureGetGml()
        assertEquals("""<gsf:cities xmlns:gsf="http://geoscript.org/feature" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:gml="http://www.opengis.net/gml" xmlns:xlink="http://www.w3.org/1999/xlink" fid="city.1">
<gml:name>Seattle</gml:name>
<gsf:geom>
<gml:Point>
<gml:coord>
<gml:X>-122.3204</gml:X>
<gml:Y>47.6024</gml:Y>
</gml:coord>
</gml:Point>
</gsf:geom>
<gsf:id>1</gsf:id>
</gsf:cities>
""".normalize(), str.normalize())
    }

    @Test void featureFromGml() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.featureFromGml()
        assertEquals("feature.city.1 name: Seattle, id: 1, geom: POINT (-122.3204 47.6024)", feature.toString())
    }

    @Test void writeFeatureToGml() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.writeFeatureToGml()
        assertEquals("""<gsf:cities xmlns:gsf="http://geoscript.org/feature" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:gml="http://www.opengis.net/gml" xmlns:xlink="http://www.w3.org/1999/xlink" fid="city.1">
<gml:name>Seattle</gml:name>
<gsf:geom>
<gml:Point>
<gml:coord>
<gml:X>-122.3204</gml:X>
<gml:Y>47.6024</gml:Y>
</gml:coord>
</gml:Point>
</gsf:geom>
<gsf:id>1</gsf:id>
</gsf:cities>
""".normalize(), str.normalize())
    }

    @Test void readFeatureFromGml() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.readFeatureFromGml()
        assertEquals("feature.city.1 name: Seattle, id: 1, geom: POINT (-122.3204 47.6024)", feature.toString())
    }

    // GPX

    @Test void featureGetGpx() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.featureGetGpx()
        assertEquals("<wpt lat='47.6024' lon='-122.3204' xmlns='http://www.topografix.com/GPX/1/1'><name>city.1</name></wpt>", str)
    }

    @Test void featureFromGpx() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.featureFromGpx()
        assertEquals("POINT (-122.3204 47.6024)", feature.geom.wkt)
        assertEquals("city.1", feature['name'])
    }

    @Test void writeFeatureToGpx() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.writeFeatureToGpx()
        assertEquals("<wpt lat='47.6024' lon='-122.3204' xmlns='http://www.topografix.com/GPX/1/1'><name>city.1</name></wpt>", str)
    }

    @Test void readFeatureFromGpx() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.readFeatureFromGpx()
        assertEquals("POINT (-122.3204 47.6024)", feature.geom.wkt)
        assertEquals("city.1", feature['name'])
    }

    // KML

    @Test void featureGetKml() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.featureGetKml()
        assertEquals('<kml:Placemark xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:kml="http://earth.google.com/kml/2.1" id="city.1">' +
            "<kml:name>Seattle</kml:name>" +
            "<kml:Point>" +
            "<kml:coordinates>-122.3204,47.6024</kml:coordinates>" +
            "</kml:Point>" +
            "</kml:Placemark>", str)
    }

    @Test void featureFromKml() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.featureFromKml()
        assertEquals("placemark.city.1 name: Seattle, description: null, Geometry: POINT (-122.3204 47.6024)", feature.toString())
    }

    @Test void writeFeatureToKml() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.writeFeatureToKml()
        assertEquals("""<kml:Placemark xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:kml="http://earth.google.com/kml/2.1" id="city.1">""" +
                "<kml:name>Seattle</kml:name>" +
                "<kml:Point>" +
                "<kml:coordinates>-122.3204,47.6024</kml:coordinates>" +
                "</kml:Point>" +
                "</kml:Placemark>", str)
    }

    @Test void readFeatureFromKml() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.readFeatureFromKml()
        assertEquals("placemark.city.1 name: Seattle, description: null, Geometry: POINT (-122.3204 47.6024)", feature.toString())
    }

    // Yaml

    @Test void getYamlFromFeature() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.getYamlFromFeature()
        assertEquals("""---
type: "Feature"
properties:
  id: 1
  name: "Seattle"
geometry:
  type: "Point"
  coordinates:
  - -122.3204
  - 47.6024
""", str)
    }

    @Test void getFeatureFromYaml() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.getFeatureFromYaml()
        assertEquals("feature.1 id: 1, name: Seattle, geom: POINT (-122.3204 47.6024)", feature.toString())
    }

    @Test void writeFeatureToYml() {
        FeatureRecipes recipes = new FeatureRecipes()
        String str = recipes.writeFeatureToYml()
        assertEquals("""---
type: "Feature"
properties:
  id: 1
  name: "Seattle"
geometry:
  type: "Point"
  coordinates:
  - -122.3204
  - 47.6024
""", str)
    }

    @Test void readFeatureFromYml() {
        FeatureRecipes recipes = new FeatureRecipes()
        Feature feature = recipes.readFeatureFromYml()
        assertEquals("feature.1 id: 1, name: Seattle, geom: POINT (-122.3204 47.6024)", feature.toString())
    }

    // Writers and Readers

    @Test void listFeatureWriters() {
        FeatureRecipes recipes = new FeatureRecipes()
        List<Writer> writers = recipes.listFeatureWriters()
        assertTrue(writers.size() > 0)
    }

    @Test void getFeatureWriter() {
        FeatureRecipes recipes = new FeatureRecipes()
        Writer writer = recipes.getFeatureWriter()
        assertNotNull(writer)
    }

    @Test void listFeatureReaders() {
        FeatureRecipes recipes = new FeatureRecipes()
        List<Reader> readers = recipes.listFeatureReaders()
        assertTrue(readers.size() > 0)
    }

    @Test void getFeatureReader() {
        FeatureRecipes recipes = new FeatureRecipes()
        Reader reader = recipes.getFeatureReader()
        assertNotNull(reader)
    }
}
