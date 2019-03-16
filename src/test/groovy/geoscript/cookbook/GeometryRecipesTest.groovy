package geoscript.cookbook

import org.locationtech.jts.geom.IntersectionMatrix
import geoscript.geom.*
import geoscript.geom.io.Reader
import org.junit.Test
import static org.junit.Assert.*

class GeometryRecipesTest {

    // Create Geometries

    @Test void createPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        Point point = recipes.createPoint()
        assertEquals(new Point(-123,46), point)
    }

    @Test void createLineStringFromCoordinates() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.createLineStringFromCoordinates()
        assertEquals("LINESTRING (3.1982421875 43.1640625, 6.7138671875 49.755859375, 9.7021484375 42.5927734375, 15.3271484375 53.798828125)", lineString.wkt)
    }

    @Test void createPolygon() {
        GeometryRecipes recipes = new GeometryRecipes()
        Polygon polygon = recipes.createPolygon()
        assertEquals("POLYGON ((-101.35986328125 47.754097979680026, -101.5576171875 46.93526088057719, -100.12939453125 46.51351558059737, -99.77783203125 47.44294999517949, -100.45898437499999 47.88688085106901, -101.35986328125 47.754097979680026))", polygon.wkt)
    }

    @Test void createPolygonWithHoles() {
        GeometryRecipes recipes = new GeometryRecipes()
        Polygon polygon = recipes.createPolygonWithHoles()
        assertEquals("POLYGON ((-122.39138603210449 47.58659965790016, -122.41250038146973 47.57681522195182, -122.40305900573729 47.56523364515569, -122.38117218017578 47.56621817878201, -122.3712158203125 47.57235661809739, -122.37602233886717 47.584747123985615, -122.39138603210449 47.58659965790016), (-122.39859580993652 47.578957532923376, -122.40468978881836 47.57548347095205, -122.39593505859376 47.570271945800094, -122.3920726776123 47.57606249728773, -122.39859580993652 47.578957532923376), (-122.3836612701416 47.58156292813543, -122.38829612731934 47.57114056934196, -122.37456321716309 47.57420959047542, -122.37868309020995 47.58023129789275, -122.3836612701416 47.58156292813543))", polygon.wkt)
    }

    @Test void createMultiPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPoint multiPoint = recipes.createMultiPoint()
        assertEquals("MULTIPOINT ((-122.3876953125 47.5820839916191), (-122.464599609375 47.25686404408872), (-122.48382568359374 47.431803338643334))", multiPoint.wkt)
    }

    @Test void createMultiLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiLineString multiLineString = recipes.createMultiLineString()
        assertEquals("MULTILINESTRING ((-122.3822021484375 47.57837853860192, -122.32452392578125 47.48380086737799), (-122.32452392578125 47.48380086737799, -122.29705810546874 47.303447043862626), (-122.29705810546874 47.303447043862626, -122.42889404296875 47.23262467463881))", multiLineString.wkt)
    }

    @Test void createMultiPolygon() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPolygon multiPolygon = recipes.createMultiPolygon()
        assertEquals("MULTIPOLYGON (((-122.2723388671875 47.818687628247105, -122.37945556640624 47.66168780332917, -121.95373535156249 47.67093619422418, -122.2723388671875 47.818687628247105)), ((-122.76672363281249 47.42437092240516, -122.76672363281249 47.59505101193038, -122.52227783203125 47.59505101193038, -122.52227783203125 47.42437092240516, -122.76672363281249 47.42437092240516)), ((-122.20367431640624 47.543163654317304, -122.3712158203125 47.489368981370724, -122.33276367187499 47.35371061951363, -122.11029052734374 47.3704545156932, -122.08831787109375 47.286681888764214, -122.28332519531249 47.2270293988673, -122.2174072265625 47.154237057576594, -121.904296875 47.32579231609051, -122.06085205078125 47.47823216312885, -122.20367431640624 47.543163654317304)))", multiPolygon.wkt)
    }

    @Test void createGeometryCollection() {
        GeometryRecipes recipes = new GeometryRecipes()
        GeometryCollection geometryCollection = recipes.createGeometryCollection()
        assertEquals("GEOMETRYCOLLECTION (" +
                "LINESTRING (-157.044 58.722, -156.461 58.676), " +
                "POINT (-156.648 58.739), " +
                "POLYGON ((-156.395 58.7083, -156.412 58.6026, -155.874 58.5825, " +
                "-155.313 58.4822, -155.385 58.6655, -156.203 58.7368, -156.395 58.7083)), " +
                "POINT (-156.741 58.582))", geometryCollection.wkt)
    }

    @Test void createCircularString() {
        GeometryRecipes recipes = new GeometryRecipes()
        CircularString circularString = recipes.createCircularString()
        assertEquals("CIRCULARSTRING (-122.464599609375 47.247542522268006, -122.03613281249999 47.37789454155521, -122.37670898437499 47.58393661978134)", circularString.curvedWkt)
    }

    @Test void createCircularRing() {
        GeometryRecipes recipes = new GeometryRecipes()
        CircularRing circularRing = recipes.createCircularRing()
        assertEquals("CIRCULARSTRING (-118.47656249999999 41.508577297439324, -109.6875 57.51582286553883, -93.8671875 42.032974332441405, -62.57812500000001 30.14512718337613, -92.10937499999999 7.36246686553575, -127.265625 14.604847155053898, -118.47656249999999 41.508577297439324)", circularRing.curvedWkt)
    }

    @Test void createCompoundCurve() {
        GeometryRecipes recipes = new GeometryRecipes()
        CompoundCurve compoundCurve = recipes.createCompoundCurve()
        assertEquals("COMPOUNDCURVE (" +
                "CIRCULARSTRING (27.0703125 23.885837699862005, 5.9765625 40.17887331434696, 22.5 47.98992166741417), " +
                "(22.5 47.98992166741417, 71.71875 49.15296965617039), " +
                "CIRCULARSTRING (71.71875 49.15296965617039, 81.5625 39.36827914916011, 69.9609375 24.5271348225978)" +
                ")", compoundCurve.curvedWkt)
    }

    @Test void createCompoundRing() {
        GeometryRecipes recipes = new GeometryRecipes()
        CompoundRing compoundRing = recipes.createCompoundRing()
        assertEquals("COMPOUNDCURVE (" +
                "CIRCULARSTRING (27.0703125 23.885837699862005, 5.9765625 40.17887331434696, 22.5 47.98992166741417), " +
                "(22.5 47.98992166741417, 71.71875 49.15296965617039), " +
                "CIRCULARSTRING (71.71875 49.15296965617039, 81.5625 39.36827914916011, 69.9609375 24.5271348225978), " +
                "(69.9609375 24.5271348225978, 27.0703125 23.885837699862005)" +
                ")", compoundRing.curvedWkt)
    }

    // CircularRing

    @Test void circularRingCurvedWkt() {
        GeometryRecipes recipes = new GeometryRecipes()
        CircularRing ring = recipes.circularRingCurvedWkt()
        assertEquals("LINEARRING (-118.47656249999999 41.508577297439324, -118.58869123015452 41.901774579059115, " +
                "-118.91479847512254 43.54122641035517, -119.02412442253558 45.209218040904574, -118.91479847512252 46.877209671453976, " +
                "-118.58869123015451 48.516661502750026, -118.05138247300694 50.09952205941734, -117.31206570548474 51.59870815847947, " +
                "-116.38339084245754 52.98856831012856, -115.28124776830906 54.245321621827856, -114.02449445660976 55.347464695976335, " +
                "-112.63463430496067 56.27613955900354, -111.13544820589854 57.01545632652574, -109.6875 57.51582286553883, " +
                "-109.55258764923123 57.552765083673314, -107.91313581793517 57.87887232864133, -106.24514418738576 57.98819827605437, " +
                "-104.57715255683635 57.87887232864133, -102.9377007255403 57.552765083673314, -101.35484016887298 57.01545632652574, " +
                "-99.85565406981085 56.27613955900354, -98.46579391816176 55.347464695976335, -97.20904060646247 54.245321621827856, " +
                "-96.10689753231398 52.98856831012856, -95.17822266928678 51.59870815847947, -94.43890590176458 50.09952205941733, " +
                "-93.901597144617 48.51666150275002, -93.575489899649 46.87720967145396, -93.46616395223595 45.20921804090456, " +
                "-93.575489899649 43.54122641035516, -93.8671875 42.032974332441405, -92.52944589377746 42.87620255408929, " +
                "-90.13998424943787 44.0545546346726, -87.61715897053445 44.91093841996455, -85.00413629704853 45.43070094596436, " +
                "-82.34562577139025 45.60494893174677, -79.68711524573196 45.43070094596437, -77.07409257224604 44.910938419964566, " +
                "-74.55126729334262 44.05455463467261, -72.16180564900301 42.876202554089296, -69.94659199044581 41.396044109014326, " +
                "-67.9435292375422 39.639405220820365, -66.18689034934823 37.63634246791675, -64.70673190427325 35.42112880935955, " +
                "-63.528379823689946 33.03166716501995, -62.67199603839799 30.50884188611652, -62.57812500000001 30.14512718337613, " +
                "-62.15223351239818 27.895819212630613, -61.97798552661578 25.237308686972327, -62.15223351239818 22.578798161314037," +
                " -62.671996038397985 19.965775487828132, -63.52837982368993 17.442950208924703, -64.70673190427325 15.0534885645851, " +
                "-66.18689034934822 12.838274906027902, -67.94352923754218 10.835212153124278, -69.9465919904458 9.078573264930323, " +
                "-72.16180564900299 7.598414819855346, -74.55126729334259 6.42006273927203, -77.07409257224602 5.563678953980077, " +
                "-79.68711524573193 5.0439164279802675, -82.34562577139022 4.869668442197863, -85.00413629704852 5.043916427980264, " +
                "-87.61715897053442 5.5636789539800695, -90.13998424943784 6.420062739272023, -92.10937499999999 7.36246686553575, " +
                "-94.00832592648507 5.722586433241059, -96.3693387982898 4.1450080684486466, -98.91606817456314 2.88910012519991, " +
                "-101.60493880959022 1.9763515366073037, -104.38994338130999 1.4223796840833636, -107.22342968935115 1.2366631796147907," +
                " -110.05691599739232 1.4223796840833813, -112.84192056911208 1.9763515366073356, -115.53079120413916 2.8891001251999597, " +
                "-118.07752058041248 4.1450080684487105, -120.43853345221721 5.722586433241133, -122.57343223472037 7.594842416368293, " +
                "-124.4456882178475 9.729741198871439, -126.02326658263992 12.09075407067618, -127.265625 14.604847155053898, " +
                "-127.27917452588866 14.637483446949503, -128.19192311448128 17.326354081976593, -128.7458949670052 20.111358653696357, " +
                "-128.9316114714738 22.944844961737523, -128.7458949670052 25.77833126977869, -128.19192311448126 28.56333584149845, " +
                "-127.27917452588864 31.252206476525537, -126.02326658263989 33.798935852798856, -124.44568821784746 36.159948724603595, " +
                "-122.57343223472031 38.29484750710673, -120.43853345221717 40.16710349023388, -118.47656249999999 41.508577297439324)", ring.wkt)
        assertEquals("CIRCULARSTRING (-118.47656249999999 41.508577297439324, -109.6875 57.51582286553883, " +
                "-93.8671875 42.032974332441405, -62.57812500000001 30.14512718337613, -92.10937499999999 7.36246686553575, " +
                "-127.265625 14.604847155053898, -118.47656249999999 41.508577297439324)", ring.curvedWkt)
    }

    @Test void circularRingControlPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.circularRingControlPoints()
        assertEquals(7, points.size())
    }

    @Test void circularRingToLinear() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry linear = recipes.circularRingToLinear()
        assertNotNull(linear)
        assertTrue(linear instanceof LinearRing)
    }

    // CircularString

    @Test void circularStringCurvedWkt() {
        GeometryRecipes recipes = new GeometryRecipes()
        CircularString circularString = recipes.circularStringCurvedWkt()
        assertEquals("LINESTRING (-122.464599609375 47.247542522268006, -122.4550237920096 47.23410579860605, " +
                "-122.4348780833765 47.21113402059009, -122.41190630536055 47.19098831195699, -122.38650151145254 47.17401337136692, " +
                "-122.3590983847198 47.16049964475972, -122.33016580025833 47.15067835574435, -122.30019880260986 47.144717549298825, " +
                "-122.26971013541258 47.1427192164741, -122.2392214682153 47.144717549298825, -122.20925447056683 47.15067835574435, " +
                "-122.18032188610536 47.16049964475972, -122.15291875937262 47.17401337136692, -122.12751396546462 47.19098831195699, " +
                "-122.10454218744866 47.21113402059009, -122.08439647881556 47.23410579860605, -122.06742153822549 47.259510592514054, " +
                "-122.05390781161829 47.28691371924678, -122.04408652260291 47.31584630370827, -122.0381257161574 47.34581330135674, " +
                "-122.03612738333267 47.37630196855401, -122.03613281249999 47.37789454155521, -122.0381257161574 47.40679063575128, " +
                "-122.04408652260291 47.43675763339975, -122.05390781161829 47.46569021786124, -122.06742153822549 47.493093344593966, " +
                "-122.08439647881556 47.51849813850197, -122.10454218744866 47.54146991651793, -122.12751396546462 47.56161562515103, " +
                "-122.15291875937262 47.5785905657411, -122.18032188610536 47.5921042923483, -122.20925447056683 47.60192558136367, " +
                "-122.2392214682153 47.607886387809195, -122.26971013541258 47.60988472063392, -122.30019880260986 47.607886387809195, " +
                "-122.33016580025833 47.60192558136367, -122.3590983847198 47.5921042923483, -122.37670898437499 47.58393661978134)", circularString.wkt)
        assertEquals("CIRCULARSTRING (-122.464599609375 47.247542522268006, -122.03613281249999 47.37789454155521, " +
                "-122.37670898437499 47.58393661978134)", circularString.curvedWkt)
    }

    @Test void circularStringControlPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.circularStringControlPoints()
        assertEquals(3, points.size())
    }

    @Test void circularStringToLinear() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry linear = recipes.circularStringToLinear()
        assertNotNull(linear)
        assertTrue(linear instanceof LineString)
    }

    // CompoundCurve

    @Test void compoundCurveCurvedWkt() {
        GeometryRecipes recipes = new GeometryRecipes()
        CompoundCurve compoundCurve = recipes.compoundCurveCurvedWkt()
        assertEquals("LINESTRING (27.0703125 23.885837699862005, 27.022331449414295 23.8488525605847, " +
                "25.524767337284196 22.84821221194479, 23.909405319245263 22.051603821364203, 22.203884687289108 21.472657579267334, " +
                "20.43738737228815 21.12127941637069, 18.64013863306764 21.00348151046186, 16.842889893847133 21.1212794163707, " +
                "15.076392578846175 21.472657579267356, 13.370871946890022 22.051603821364232, 11.755509928851094 22.848212211944833, " +
                "10.257945816720998 23.848852560584746, 8.903803347661494 25.036403633488835, 7.716252274757412 26.390546102548342, " +
                "6.715611926117504 27.88811021467844, 5.919003535536911 29.50347223271737, 5.340057293440038 31.20899286467353, " +
                "4.988679130543391 32.97549017967449, 4.8708812246345605 34.772738918894994, 4.988679130543396 36.5699876581155, " +
                "5.340057293440047 38.336484973116455, 5.9190035355369215 40.04200560507262, 5.9765625 40.17887331434696, " +
                "6.715611926117516 41.65736762311154, 7.71625227475743 43.15493173524164, 8.903803347661514 44.50907420430114, " +
                "10.257945816721021 45.69662527720523, 11.755509928851117 46.69726562584515, 13.37087194689005 47.49387401642574, " +
                "15.076392578846209 48.07282025852261, 16.84288989384717 48.42419842141926, 18.64013863306768 48.5419963273281, " +
                "20.437387372288192 48.42419842141926, 22.20388468728915 48.07282025852261, 22.5 47.98992166741417, 71.71875 49.15296965617039, " +
                "72.58658076138724 48.953451060440116, 74.12646468490568 48.43073090444654, 75.58494601643167 47.71148750685245, " +
                "76.93706973601537 46.80802732160263, 78.15970063193521 45.735808802953194, 79.23191915058464 44.513177907033366, " +
                "80.13537933583446 43.16105418744966, 80.85462273342854 41.70257285592367, 81.37734288942212 40.162688932405224, " +
                "81.5625 39.36827914916011, 81.69459591702075 38.567750257762206, 81.80095352896302 36.9450466749183, 81.69459591702075 35.3223430920744, " +
                "81.37734288942212 33.72740441743138, 80.85462273342854 32.187520493912935, 80.13537933583446 30.729039162386947, " +
                "79.23191915058464 29.376915442803238, 78.15970063193521 28.15428454688341, 76.93706973601537 27.082066028233974, " +
                "75.58494601643167 26.178605842984155, 74.12646468490568 25.459362445390067, 72.58658076138724 24.936642289396488, " +
                "70.99164208674422 24.61938926179787, 69.9609375 24.5271348225978)", compoundCurve.wkt)
        assertEquals("COMPOUNDCURVE (CIRCULARSTRING (27.0703125 23.885837699862005, 5.9765625 40.17887331434696, 22.5 47.98992166741417), " +
                "(22.5 47.98992166741417, 71.71875 49.15296965617039), CIRCULARSTRING (71.71875 49.15296965617039, 81.5625 39.36827914916011, " +
                "69.9609375 24.5271348225978))", compoundCurve.curvedWkt)
    }

    @Test void compoundCurveComponents() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<LineString> lineStrings = recipes.compoundCurveComponents()
        assertEquals(3, lineStrings.size())
    }

    @Test void compoundCurveToLinear() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry linear = recipes.compoundCurveToLinear()
        assertNotNull(linear)
        assertTrue(linear instanceof LineString)
    }

    // CompoundRing

    @Test void compoundRingCurvedWkt() {
        GeometryRecipes recipes = new GeometryRecipes()
        CompoundRing compoundRing = recipes.compoundRingCurvedWkt()
        assertEquals("LINEARRING (27.0703125 23.885837699862005, 27.022331449414295 23.8488525605847, 25.524767337284196 22.84821221194479, " +
                "23.909405319245263 22.051603821364203, 22.203884687289108 21.472657579267334, 20.43738737228815 21.12127941637069, " +
                "18.64013863306764 21.00348151046186, 16.842889893847133 21.1212794163707, 15.076392578846175 21.472657579267356, " +
                "13.370871946890022 22.051603821364232, 11.755509928851094 22.848212211944833, 10.257945816720998 23.848852560584746, " +
                "8.903803347661494 25.036403633488835, 7.716252274757412 26.390546102548342, 6.715611926117504 27.88811021467844, " +
                "5.919003535536911 29.50347223271737, 5.340057293440038 31.20899286467353, 4.988679130543391 32.97549017967449, " +
                "4.8708812246345605 34.772738918894994, 4.988679130543396 36.5699876581155, 5.340057293440047 38.336484973116455, " +
                "5.9190035355369215 40.04200560507262, 5.9765625 40.17887331434696, 6.715611926117516 41.65736762311154, " +
                "7.71625227475743 43.15493173524164, 8.903803347661514 44.50907420430114, 10.257945816721021 45.69662527720523, " +
                "11.755509928851117 46.69726562584515, 13.37087194689005 47.49387401642574, 15.076392578846209 48.07282025852261, " +
                "16.84288989384717 48.42419842141926, 18.64013863306768 48.5419963273281, 20.437387372288192 48.42419842141926, " +
                "22.20388468728915 48.07282025852261, 22.5 47.98992166741417, 71.71875 49.15296965617039, 72.58658076138724 48.953451060440116, " +
                "74.12646468490568 48.43073090444654, 75.58494601643167 47.71148750685245, 76.93706973601537 46.80802732160263, " +
                "78.15970063193521 45.735808802953194, 79.23191915058464 44.513177907033366, 80.13537933583446 43.16105418744966, " +
                "80.85462273342854 41.70257285592367, 81.37734288942212 40.162688932405224, 81.5625 39.36827914916011, " +
                "81.69459591702075 38.567750257762206, 81.80095352896302 36.9450466749183, 81.69459591702075 35.3223430920744, " +
                "81.37734288942212 33.72740441743138, 80.85462273342854 32.187520493912935, 80.13537933583446 30.729039162386947, " +
                "79.23191915058464 29.376915442803238, 78.15970063193521 28.15428454688341, 76.93706973601537 27.082066028233974, " +
                "75.58494601643167 26.178605842984155, 74.12646468490568 25.459362445390067, 72.58658076138724 24.936642289396488, " +
                "70.99164208674422 24.61938926179787, 69.9609375 24.5271348225978, 27.0703125 23.885837699862005)", compoundRing.wkt)
        assertEquals("COMPOUNDCURVE (CIRCULARSTRING (27.0703125 23.885837699862005, 5.9765625 40.17887331434696, 22.5 47.98992166741417), " +
                "(22.5 47.98992166741417, 71.71875 49.15296965617039), CIRCULARSTRING (71.71875 49.15296965617039, 81.5625 39.36827914916011, " +
                "69.9609375 24.5271348225978), (69.9609375 24.5271348225978, 27.0703125 23.885837699862005))", compoundRing.curvedWkt)
    }

    @Test void compoundRingComponents() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<LineString> lineStrings = recipes.compoundRingComponents()
        assertEquals(4, lineStrings.size())
    }

    @Test void compoundRingToLinear() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry linear = recipes.compoundRingToLinear()
        assertNotNull(linear)
        assertTrue(linear instanceof LineString)
    }

    // Polygon

    @Test void polygonGetExteriorRing() {
        GeometryRecipes recipes = new GeometryRecipes()
        LinearRing ring = recipes.polygonGetExteriorRing()
        assertEquals("LINEARRING (-122.39138603210449 47.58659965790016, -122.41250038146973 47.57681522195182, " +
                "-122.40305900573729 47.56523364515569, -122.38117218017578 47.56621817878201, -122.3712158203125 47.57235661809739, " +
                "-122.37602233886717 47.584747123985615, -122.39138603210449 47.58659965790016)", ring.wkt)
    }

    @Test void polygonInteriorRing() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<LinearRing> rings = recipes.polygonInteriorRing()
        assertEquals(2, rings.size())
        assertEquals("LINEARRING (-122.39859580993652 47.578957532923376, -122.40468978881836 47.57548347095205, -122.39593505859376 47.570271945800094, -122.3920726776123 47.57606249728773, -122.39859580993652 47.578957532923376)", rings[0].wkt)
        assertEquals("LINEARRING (-122.3836612701416 47.58156292813543, -122.38829612731934 47.57114056934196, -122.37456321716309 47.57420959047542, -122.37868309020995 47.58023129789275, -122.3836612701416 47.58156292813543)", rings[1].wkt)
    }

    @Test void plusPolygons() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPolygon multiPolygon = recipes.plusPolygons()
        assertEquals("MULTIPOLYGON (((-122.2723388671875 47.818687628247105, -122.37945556640624 47.66168780332917, " +
                "-121.95373535156249 47.67093619422418, -122.2723388671875 47.818687628247105)), " +
                "((-122.76672363281249 47.42437092240516, -122.76672363281249 47.59505101193038, " +
                "-122.52227783203125 47.59505101193038, -122.52227783203125 47.42437092240516, " +
                "-122.76672363281249 47.42437092240516)))", multiPolygon.wkt)
    }

    @Test void splitPolygon() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPolygon multiPolygon = recipes.splitPolygon()
        assertNotNull(multiPolygon)
        assertEquals(2, multiPolygon.numGeometries)
    }

    // MultiPolygon

    @Test void multiPolygonPlus() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPolygon multiPolygon = recipes.multiPolygonPlus()
        assertNotNull(multiPolygon)
        assertEquals(3, multiPolygon.numGeometries)
    }

    @Test void multiPolygonSplit() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry splitGeometry = recipes.multiPolygonSplit()
        assertNotNull(splitGeometry)
        assertTrue(splitGeometry instanceof MultiPolygon)
        assertEquals(6, splitGeometry.numGeometries)
    }

    // LineString

    @Test void getStartPointFromLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Geometry> geoms = recipes.getStartPointFromLineString()
        assertTrue(geoms.lineString instanceof LineString)
        assertTrue(geoms.point instanceof Point)
        assertEquals("POINT (3.1982421875 43.1640625)", geoms.point.wkt)
    }

    @Test void getEndPointFromLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Geometry> geoms = recipes.getEndPointFromLineString()
        assertTrue(geoms.lineString instanceof LineString)
        assertTrue(geoms.point instanceof Point)
        assertEquals("POINT (15.3271484375 53.798828125)", geoms.point.wkt)
    }

    @Test void reverseLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Geometry> geoms = recipes.reverseLineString()
        assertTrue(geoms.lineString instanceof LineString)
        assertTrue(geoms.startPoint instanceof Point)
        assertEquals("POINT (3.1982421875 43.1640625)", geoms.startPoint.wkt)
        assertTrue(geoms.reversedLineString instanceof LineString)
        assertTrue(geoms.reversedStartPoint instanceof Point)
        assertEquals("POINT (15.3271484375 53.798828125)", geoms.reversedStartPoint.wkt)
    }

    @Test void isLineStringClosed() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Object> geoms = recipes.isLineStringClosed()
        assertTrue(geoms.lineString1 instanceof LineString)
        assertFalse(geoms.isClosed1)
        assertTrue(geoms.lineString2 instanceof LineString)
        assertTrue(geoms.isClosed2)
    }

    @Test void isLineStringRing() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Object> geoms = recipes.isLineStringRing()
        assertTrue(geoms.lineString1 instanceof LineString)
        assertFalse(geoms.isRing1)
        assertTrue(geoms.lineString2 instanceof LineString)
        assertTrue(geoms.isRing2)
    }

    @Test void closeLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Geometry> geoms = recipes.closeLineString()
        assertTrue(geoms.lineString instanceof LineString)
        assertTrue(geoms.linearRing instanceof LinearRing)
    }

    @Test void lineStringPlus() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiLineString multiLineString = recipes.lineStringPlus()
        assertEquals("MULTILINESTRING ((-122.39142894744873 47.5812734461813, " +
                "-122.38237380981445 47.58121554959838), " +
                "(-122.38640785217285 47.58552866972616, " +
                "-122.38670825958253 47.57837853860192))", multiLineString.wkt)
    }

    @Test void lineStringPlusPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.lineStringPlusPoint()
        assertEquals("LINESTRING (-122.39142894744873 47.5812734461813, " +
                "-122.38237380981445 47.58121554959838, " +
                "-122.38640785217285 47.58552866972616)", lineString.wkt)
    }

    @Test void lineStringAddPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.lineStringAddPoint()
        assertEquals("LINESTRING (-122.39142894744873 47.5812734461813, " +
                "-122.38640785217285 47.58552866972616, " +
                "-122.38237380981445 47.58121554959838)", lineString.wkt)
    }

    @Test void lineStringSetPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.lineStringSetPoint()
        assertEquals("LINESTRING (-122.39142894744873 47.5812734461813, " +
                "-122.38640785217285 47.58552866972616)", lineString.wkt)
    }

    @Test void lineStringRemovePoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.lineStringRemovePoint()
        assertEquals("LINESTRING (-122.39142894744873 47.5812734461813, " +
                "-122.38237380981445 47.58121554959838)", lineString.wkt)
    }

    @Test void lineStringNegative() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<LineString> lineStrings = recipes.lineStringNegative()
        assertEquals(3, lineStrings.size())
        assertEquals("LINESTRING (-122.39423990249632 47.57926150237904, -122.3918581008911 47.58121554959838, -122.38657951354979 47.58121554959838, -122.38638639450075 47.58535499390333, -122.38374710083008 47.58535499390333)", lineStrings[0].wkt)
        assertEquals("LINESTRING (-122.39423990249632 47.57926150237904, -122.3918581008911 47.58121554959838, -122.38657951354979 47.58121554959838, -122.38638639450075 47.58535499390333)", lineStrings[1].wkt)
        assertEquals("LINESTRING (-122.39423990249632 47.57926150237904, -122.3918581008911 47.58121554959838, -122.38657951354979 47.58121554959838)", lineStrings[2].wkt)
    }

    @Test void lineStringInterpolatePoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.lineStringInterpolatePoint()
        assertEquals(3, points.size())
        assertEquals("POINT (-122.39423990249632 47.57926150237904)", points[0].wkt)
        assertEquals("POINT (-122.38736758304911 47.58121554959838)", points[1].wkt)
        assertEquals("POINT (-122.38374710083008 47.58535499390333)", points[2].wkt)
    }

    @Test void lineStringLocatePoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Double> positions = recipes.lineStringLocatePoint()
        assertEquals(3, positions.size())
        println positions
        assertEquals(0.0, positions[0], 0.1)
        assertEquals(0.5, positions[1], 0.1)
        assertEquals(1.0, positions[2], 0.1)
    }

    @Test void lineStringPlacePoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.lineStringPlacePoint()
        assertEquals(2, points.size())
        assertEquals("POINT (-122.35835385210422 47.57160050493058)", points[0].wkt)
        assertEquals("POINT (-122.33858529358729 47.57150884009016)", points[1].wkt)
    }

    @Test void lineStringSubLine() {
        GeometryRecipes recipes = new GeometryRecipes()
        LineString lineString = recipes.lineStringSubLine()
        assertEquals("LINESTRING (-122.38994182839951 47.58121554959838, " +
                "-122.38657951354979 47.58121554959838, " +
                "-122.38650332982382 47.58284852310433)", lineString.wkt)
    }

    // MultiLineString

    @Test void multiLineStringPlus() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiLineString multiLineString =  recipes.multiLineStringPlus()
        assertEquals("MULTILINESTRING ((-122.3822021484375 47.57837853860192, -122.32452392578125 47.48380086737799), " +
                "(-122.32452392578125 47.48380086737799, -122.29705810546874 47.303447043862626), " +
                "(-122.29705810546874 47.303447043862626, -122.42889404296875 47.23262467463881))", multiLineString.wkt)
    }

    @Test void multiLineStringMerge() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiLineString multiLineString =  recipes.multiLineStringMerge()
        assertEquals("MULTILINESTRING ((-122.3822021484375 47.57837853860192, -122.32452392578125 47.48380086737799, " +
                "-122.29705810546874 47.303447043862626, " +
                "-122.42889404296875 47.23262467463881))", multiLineString.wkt)
    }

    // Point

    @Test void getPointXYZ() {
        GeometryRecipes recipes = new GeometryRecipes()
        Point point =  recipes.getPointXYZ()
        assertEquals(-122.38632, point.x, 0.001)
        assertEquals(47.58208, point.y, 0.001)
        assertEquals(101.45, point.z, 0.001)
    }

    @Test void plusPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPoint points =  recipes.plusPoint()
        assertEquals(2, points.numGeometries)
        assertEquals("POINT (-122.38632 47.58208)", points[0].wkt)
        assertEquals("POINT (-122.37001 47.55868)", points[1].wkt)
    }

    @Test void getAngleBetweenPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Double> angles =  recipes.getAngleBetweenPoints()
        assertEquals(-29.663413013476646, angles[0], 0.01)
        assertEquals(-0.517724224464100, angles[1], 0.01)
    }

    @Test void getAzimuthBetweenPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        double azimuth =  recipes.getAzimuthBetweenPoints()
        assertEquals(129.21026122904846, azimuth, 0.01)
    }

    @Test void plusMultiPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        MultiPoint points =  recipes.plusMultiPoint()
        assertEquals(3, points.numGeometries)
        assertEquals("POINT (-122.83813 47.05141)", points[0].wkt)
        assertEquals("POINT (-122.3822 47.58023)", points[1].wkt)
        assertEquals("POINT (-122.48657 47.271775)", points[2].wkt)
    }

    // Geometry Collection

    @Test void geometryCollectionPlus() {
        GeometryRecipes recipes = new GeometryRecipes()
        GeometryCollection geometryCollection =  recipes.geometryCollectionPlus()
        assertEquals(4, geometryCollection.numGeometries)
        assertEquals("POINT (-122.38654196262358 47.581211931059826)", geometryCollection[0].wkt)
        assertEquals("LINESTRING (-122.3865446448326 47.58118841055313, -122.38657146692276 47.58067638459562)", geometryCollection[1].wkt)
        assertEquals("POLYGON ((-122.38693356513977 47.58088445228483, -122.38672703504562 47.58088445228483, " +
                "-122.38672703504562 47.58096225129535, -122.38693356513977 47.58096225129535, " +
                "-122.38693356513977 47.58088445228483))", geometryCollection[2].wkt)
        assertEquals("POINT (-122.38718032836913 47.58121374032914)", geometryCollection[3].wkt)
    }

    @Test void geometryCollectionSlice() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries =  recipes.geometryCollectionSlice()
        assertEquals(2, geometries.size())
        assertEquals("POLYGON ((-122.38693356513977 47.58088445228483, -122.38672703504562 47.58088445228483, " +
                "-122.38672703504562 47.58096225129535, -122.38693356513977 47.58096225129535, " +
                "-122.38693356513977 47.58088445228483))", geometries[0].wkt)
        assertEquals("GEOMETRYCOLLECTION (POINT (-122.38654196262358 47.581211931059826), " +
                "LINESTRING (-122.3865446448326 47.58118841055313, -122.38657146692276 47.58067638459562))", geometries[1].wkt)
    }

    @Test void geometryCollectionNarrow() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries =  recipes.geometryCollectionNarrow()
        assertEquals(2, geometries.size())
        assertEquals("MULTIPOINT ((-122.38654196262358 47.581211931059826), (-122.38718032836913 47.58121374032914))", geometries[0].wkt)
        assertEquals("GEOMETRYCOLLECTION (POINT (-122.38654196262358 47.581211931059826), POLYGON ((-122.38693356513977 47.58088445228483, " +
                "-122.38672703504562 47.58088445228483, -122.38672703504562 47.58096225129535, -122.38693356513977 47.58096225129535, " +
                "-122.38693356513977 47.58088445228483)))", geometries[1].wkt)
    }

    // Geometry Operations

    @Test void bufferPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.bufferPoint()
        assertTrue(geom instanceof Polygon)
    }

    @Test void bufferLineString() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.bufferLineString()
        assertTrue(geometries.size() > 0)
        geometries.each { Geometry g -> assertNotNull(g) }
    }

    @Test void bufferLineStringSingleSided() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.bufferLineStringSingleSided()
        assertTrue(geometries.size() > 0)
        geometries.each { Geometry g -> assertNotNull(g) }
    }

    @Test void contains() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.contains()
        assertTrue(results["1contains2"])
        assertFalse(results["1contains3"])
    }

    @Test void within() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.within()
        assertTrue(results["1within2"])
        assertFalse(results["1within3"])
    }

    @Test void touches() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.touches()
        assertTrue(results["touches_12"])
        assertFalse(results["touches_13"])
    }

    @Test void convexHull() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.convexHull()
        assertEquals("POLYGON ((-90.7031 34.016, -111.796 42.553, -119.882 47.279, -100.195 46.316, -90.7031 34.016))", geom.wkt)
    }

    @Test void covers() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.covers()
        assertTrue(results["1covers2"])
        assertFalse(results["1covers3"])
    }

    @Test void coveredBy() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.coveredBy()
        assertTrue(results["2coveredBy1"])
        assertFalse(results["3coveredBy1"])
    }


    @Test void bounds() {
        GeometryRecipes recipes = new GeometryRecipes()
        Bounds bounds = recipes.bounds()
        assertNotNull(bounds)
    }

    @Test void getGeometryType() {
        GeometryRecipes recipes = new GeometryRecipes()
        String type = recipes.getGeometryType()
        assertEquals("Point", type)
    }

    @Test void getArea() {
        GeometryRecipes recipes = new GeometryRecipes()
        double area = recipes.getArea()
        assertEquals(62.402, area, 0.01)
    }

    @Test void getLength() {
        GeometryRecipes recipes = new GeometryRecipes()
        double length = recipes.getLength()
        assertEquals(23.247, length, 0.01)
    }

    @Test void createSierpinskiCarpet() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.createSierpinskiCarpet()
        assertNotNull geometry
    }

    @Test void createKochSnowflake() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.createKochSnowflake()
        assertNotNull geometry
    }

    @Test void cascadeUnion() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.cascadeUnion()
        assertNotNull geometry
    }

    @Test void createRandomPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.createRandomPoints()
        assertNotNull geometry
        assertEquals(100, geometry.getNumGeometries())
    }

    @Test void createRandomPointsInGrid() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.createRandomPointsInGrid()
        assertNotNull geometry
        assertEquals(100, geometry.getNumGeometries())
    }

    @Test void crosses() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String, Boolean> results = recipes.crosses()
        assertFalse(results["12"])
        assertFalse(results["13"])
        assertTrue(results["23"])
    }

    @Test void difference() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.difference()
        assertEquals("POLYGON ((-122.11534856491807 46.59496055948802, " +
                "-122.64 46.995, -121.915 47.39, " +
                "-121.409 47.413, -120.981 47.316, " +
                "-121.15214608098509 46.82269659010183, " +
                "-121.541 46.995, " +
                "-122.11534856491807 46.59496055948802))", geometry.wkt)
    }

    @Test void symDifference() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.symDifference()
        assertEquals("MULTIPOLYGON (((-122.11534856491807 46.59496055948802, -122.64 46.995, -121.915 47.39, " +
                "-121.409 47.413, -120.981 47.316, -121.15214608098509 46.82269659010183, " +
                "-121.541 46.995, -122.11534856491807 46.59496055948802)), ((-122.11534856491807 46.59496055948802, " +
                "-121.739 46.308, -121.168 46.777, -121.15214608098509 46.82269659010183, -120.794 46.664, " +
                "-120.959 46.096, -121.937 45.89, -122.2 46.536, -122.11534856491807 46.59496055948802)))", geometry.wkt)
    }

    @Test void disjoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String, Boolean> results = recipes.disjoint()
        assertFalse(results["12"])
        assertTrue(results["13"])
        assertTrue(results["23"])
    }

    @Test void distance() {
        GeometryRecipes recipes = new GeometryRecipes()
        double distance = recipes.distance()
        assertEquals(0.37694827231332195, distance, 0.001)
    }

    @Test void boundary() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.boundary()
        assertEquals("MULTILINESTRING (" +
                "(-121.915 47.39, -122.64 46.995, -121.739 46.308, -121.168 46.777, -120.981 47.316, " +
                "-121.409 47.413, -121.915 47.39), " +
                "(-122.255 46.935, -121.992 46.935, -121.992 47.1, -122.255 47.1, -122.255 46.935), " +
                "(-121.717 46.777, -121.398 46.777, -121.398 47.002, -121.717 47.002, -121.717 46.777" +
                "))", geometry.wkt)
    }

    @Test void centroid() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.centroid()
        assertEquals("POINT (-120.43206854809475 47.34584003114768)", geometry.wkt)
    }

    @Test void interiorPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.interiorPoint()
        assertEquals("POINT (-121.00204734961912 47.479)", geometry.wkt)
    }

    @Test void getNumGeometries() {
        GeometryRecipes recipes = new GeometryRecipes()
        int number = recipes.getNumGeometries()
        assertEquals(3, number)
    }

    @Test void getGeometryN() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.getGeometryN()
        assertEquals(3, geometries.size())
    }

    @Test void getGeometries() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.getGeometries()
        assertEquals(3, geometries.size())
    }

    @Test void getNumPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        int number = recipes.getNumPoints()
        assertEquals(5, number)
    }

    @Test void union() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.union()
        assertEquals("POLYGON ((-122.11534856491807 46.59496055948802, -122.64 46.995, -121.915 47.39, -121.409 47.413, -120.981 47.316, -121.15214608098509 46.82269659010183, -120.794 46.664, -120.959 46.096, -121.937 45.89, -122.2 46.536, -122.11534856491807 46.59496055948802))", geom.wkt)
    }

    @Test void intersection() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.intersection()
        assertEquals("POLYGON ((-121.15214608098509 46.82269659010183, -121.168 46.777, -121.739 46.308, -122.11534856491807 46.59496055948802, -121.541 46.995, -121.15214608098509 46.82269659010183))", geom.wkt)
    }

    @Test void intersects() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String, Boolean> results = recipes.intersects()
        assertTrue(results["12"])
        assertFalse(results["13"])
        assertFalse(results["23"])
    }

    @Test void overlaps() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String, Boolean> results = recipes.overlaps()
        assertTrue(results["12"])
        assertFalse(results["13"])
        assertFalse(results["23"])
    }

    @Test void getOctagonalEnvelope() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getOctagonalEnvelope()
        assertEquals("POLYGON ((-122.3712158203125 47.308045651326594, -122.3712158203125 47.48936898137072, -122.3174211473659 47.543163654317304, -122.1216682132268 47.543163654317304, -121.904296875 47.325792316090514, -122.07585213351392 47.154237057576594, -122.2174072265625 47.154237057576594, -122.3712158203125 47.308045651326594))", geom.wkt)
    }

    @Test void getMinimumRectangle() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getMinimumRectangle()
        assertEquals("POLYGON ((-122.2257331947806 47.63877911563473, -122.46386617575001 47.394217398870474, -122.14242985596937 47.08123059932578, -121.90429687499996 47.32579231609004, -122.2257331947806 47.63877911563473))", geom.wkt)
    }

    @Test void getMinimumBoundingCircle() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getMinimumBoundingCircle()
        assertEquals("POLYGON ((-121.89557442425158 47.390985711028655, -121.90033959135305 47.34260415752205, -121.91445196995807 47.29608187998401, -121.93736922927117 47.25320670345034, -121.9682106718691 47.21562629691054, -122.0057910784089 47.18478485431261, -122.04866625494257 47.16186759499951, -122.0951885324806 47.14775521639448, -122.14357008598722 47.14299004929302, -122.19195163949384 47.14775521639448, -122.23847391703187 47.16186759499951, -122.28134909356554 47.18478485431261, -122.31892950010534 47.21562629691054, -122.34977094270327 47.25320670345034, -122.37268820201638 47.29608187998401, -122.3868005806214 47.34260415752205, -122.39156574772286 47.390985711028655, -122.3868005806214 47.439367264535264, -122.37268820201638 47.4858895420733, -122.34977094270326 47.52876471860697, -122.31892950010534 47.56634512514677, -122.28134909356554 47.5971865677447, -122.23847391703187 47.620103827057804, -122.19195163949382 47.63421620566283, -122.14357008598722 47.63898137276429, -122.0951885324806 47.63421620566283, -122.04866625494257 47.620103827057804, -122.0057910784089 47.5971865677447, -121.9682106718691 47.56634512514677, -121.93736922927117 47.52876471860697, -121.91445196995807 47.4858895420733, -121.90033959135305 47.439367264535264, -121.89557442425158 47.390985711028655))", geom.wkt)
    }

    @Test void getMinimumDiameter() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getMinimumDiameter()
        assertEquals("LINESTRING (-121.9792742455931 47.39879877434085, -122.2174072265625 47.154237057576594)", geom.wkt)
    }

    @Test void getMinimumClearance() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getMinimumClearance()
        assertEquals("LINESTRING (-122.08831787109375 47.286681888764214, -122.06231070736864 47.23921547912073)", geom.wkt)
    }

    @Test void offset() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geoms = recipes.offset()
        assertEquals("LINESTRING (2.1391631297989706 43.72868086766145, 5.65416312979897 50.319680867661454, 5.778377087150159 50.50764866357158, 5.935537150898572 50.669085071679376, 6.120103319712697 50.79829934654647, 6.325569508034951 50.89073659529652, 6.544692890113443 50.94313834075062, 6.769749214636951 50.95365738500962, 6.992805089955028 50.92192292446214, 7.205997641640225 50.84905362086928, 7.4018116842692585 50.737618167761084, 7.573344636925451 50.591544742206665, 7.714549843967106 50.41598253384599, 7.820449723835849 50.217120232381035, 9.801865449076663 45.46874925884475, 14.247262358720448 54.33580475358813)", geoms[0].wkt)
        assertEquals("LINESTRING (5.315673740402058 42.0346382646771, 6.448010547942489 44.15789058288449, 7.487100552328302 41.66775953523793, 7.701682600838272 41.265828705403834, 7.988357285657427 40.911693882775936, 8.336792679562427 40.61811829832088, 8.734430962409043 40.395682591815266, 9.166941012486353 40.252403479255264, 9.618734908720752 40.193444825613916, 10.073529728681235 40.22093153605678, 10.514934394849579 40.33387297308797, 10.927040418864596 40.52819865971841, 11.294995252964318 40.79690498189284, 11.605537584696997 41.13030760293216, 11.847475282559104 41.51639049282375, 17.465475282559105 52.722390492823756)", geoms[1].wkt)
    }

    @Test void getDimension() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String, Integer> dimensions = recipes.getDimension()
        assertEquals(0, dimensions.point)
        assertEquals(1, dimensions.lineString)
        assertEquals(2, dimensions.polygon)
    }

    @Test void isEmpty() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isEmpty()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void isRectangle() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isRectangle()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void isSimple() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isSimple()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void isValid() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isValid()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void isCurved() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isCurved()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void isWithinDistance() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.isWithinDistance()
        assertTrue(values[0])
        assertFalse(values[1])
    }

    @Test void getDelaunayTriangleDiagram() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getDelaunayTriangleDiagram()
        assertNotNull(geom)
    }

    @Test void getVoronoiDiagram() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.getVoronoiDiagram()
        assertNotNull(geom)
    }

    @Test void normalize() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.normalize()
        assertEquals("POLYGON ((1 3, 2 4, 4 4, 6 3, 6 1, 2 1, 1 3))", geom.wkt)
    }

    @Test void norm() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Geometry> geoms = recipes.norm()
        assertEquals("POLYGON ((2 4, 1 3, 2 1, 6 1, 6 3, 4 4, 2 4))", geoms.geometry.wkt)
        assertEquals("POLYGON ((1 3, 2 4, 4 4, 6 3, 6 1, 2 1, 1 3))", geoms.normalizedGeometry.wkt)
    }

    @Test void smooth() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.smooth()
        assertNotNull(geom)
    }

    @Test void relateIntersectionMatrix() {
        GeometryRecipes recipes = new GeometryRecipes()
        IntersectionMatrix matrix = recipes.relateIntersectionMatrix()
        assertEquals("212101212", matrix.toString())
    }

    @Test void relate() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.relate()
        assertTrue(values[0])
        assertFalse(values[1])
        assertFalse(values[2])
    }

    @Test void densify() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.densify()
        assertEquals(50, geom.numPoints)
    }

    @Test void simplify() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.simplify()
        assertEquals(5, geom.numPoints)
    }

    @Test void simplifyPreservingTopology() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.simplifyPreservingTopology()
        assertEquals(11, geom.numPoints)
    }

    @Test void translate() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.translate()
        assertFalse geometries[0].equals(geometries[1])
    }

    @Test void scaleXY() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.scaleXY()
        assertFalse geometries[0].equals(geometries[1])
    }

    @Test void scaleXYAroundPoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.scaleXYAroundPoint()
        assertFalse geometries[0].equals(geometries[1])
    }

    @Test void rotate() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.rotate()
        assertFalse geometries[0].equals(geometries[1])
        assertFalse geometries[0].equals(geometries[2])
        assertFalse geometries[0].equals(geometries[3])
        assertFalse geometries[0].equals(geometries[4])
    }

    @Test void shear() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.shear()
        assertFalse geometries[0].equals(geometries[1])
    }

    @Test void reflect() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.reflect()
        assertFalse geometries[0].equals(geometries[1])
        assertFalse geometries[0].equals(geometries[2])
    }

    @Test void reducePrecision() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geometries = recipes.reducePrecision()
        assertEquals "POINT (5.19775390625 51.07421875)", geometries[0].wkt
        assertEquals "POINT (5.19775390625 51.07421875)", geometries[1].wkt
        assertEquals "POINT (5.2 51.07)", geometries[2].wkt
        assertEquals "POINT (5.19775390625 51.07421875)", geometries[3].wkt
    }

    @Test void getNearestPoints() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.getNearestPoints()
        assertEquals 2, points.size()
    }

    @Test void getAt() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Point> points = recipes.getAt()
        assertEquals 3, points.size()
        assertEquals "POINT (-122.3876953125 47.5820839916191)", points[0].wkt
        assertEquals "POINT (-122.464599609375 47.25686404408872)", points[1].wkt
        assertEquals "POINT (-122.48382568359374 47.431803338643334)", points[2].wkt
    }

    @Test void asType() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Object> values = recipes.asType()
        assertEquals 2, values.size()
        assertEquals "(-122.64,46.308,-120.981,47.413)", values[0].toString()
        assertEquals "POINT (-121.73789467295867 46.95085967283822)", values[1].toString()
    }

    @Test void equals() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.equals()
        assertTrue values[0]
        assertFalse values[1]
        assertFalse values[2]
    }

    @Test void equalsTopo() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.equalsTopo()
        assertTrue values[0]
        assertFalse values[1]
        assertFalse values[2]
    }

    @Test void equalsNorm() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Boolean> values = recipes.equalsNorm()
        assertTrue values[0]
        assertFalse values[1]
        assertFalse values[2]
    }

    @Test void prepare() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Long> times = recipes.prepare()
        assertTrue times[0] > 0
        assertTrue times[1] > 0
    }

    @Test void prepareStatic() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Long> times = recipes.prepareStatic()
        assertTrue times[0] > 0
        assertTrue times[1] > 0
    }

    // Prepared Geometry

    @Test void preparedGeometryCreate() {
        GeometryRecipes recipes = new GeometryRecipes()
        PreparedGeometry preparedGeometry = recipes.preparedGeometryCreate()
        assertEquals("POLYGON ((-120.739 48.151, -121.003 47.07, -119.465 47.137, -119.553 46.581, " +
                "-121.267 46.513, -121.168 45.706, -118.476 45.951, " +
                "-118.762 48.195, -120.739 48.151))", preparedGeometry.geometry.wkt)
    }

    @Test void preparedGeometryContains() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryContains()
        assertTrue(results["1contains2"])
        assertFalse(results["1contains3"])
    }

    @Test void preparedGeometryContainsProperly() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryContainsProperly()
        assertTrue(results["1contains2"])
        assertFalse(results["1contains3"])
    }

    @Test void preparedGeometryCoveredBy() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryCoveredBy()
        assertTrue(results["2coveredBy1"])
        assertFalse(results["3coveredBy1"])
    }

    @Test void preparedGeometryCovers() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryCovers()
        assertTrue(results["1covers2"])
        assertFalse(results["1covers3"])
    }

    @Test void preparedGeometryCrosses() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryCrosses()
        assertTrue(results["12"])
        assertFalse(results["13"])
    }

    @Test void preparedGeometryDisjoint() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryDisjoint()
        assertFalse(results["12"])
        assertTrue(results["13"])
    }

    @Test void preparedGeometryIntersects() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryIntersects()
        assertTrue(results["12"])
        assertFalse(results["13"])
    }

    @Test void preparedGeometryOverlaps() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryOverlaps()
        assertTrue(results["12"])
        assertFalse(results["13"])
    }

    @Test void preparedGeometryTouches() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryTouches()
        assertTrue(results["touches_12"])
        assertFalse(results["touches_13"])
    }

    @Test void preparedGeometryWithin() {
        GeometryRecipes recipes = new GeometryRecipes()
        Map<String,Boolean> results = recipes.preparedGeometryWithin()
        assertTrue(results["1within2"])
        assertFalse(results["1within3"])
    }

    // Geometry IO

    @Test void getGeometryReaders() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Reader> readers = recipes.getGeometryReaders()
        assertTrue(readers.size() > 0)
    }

    @Test void getGeometryWriters() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Writer> writers = recipes.getGeometryWriters()
        assertTrue(writers.size() > 0)
    }

    @Test void findGeometryReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.findGeometryReader()
        assertEquals("POINT (-123.15 46.237)", geometry.wkt)
    }

    @Test void findGeometryWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String str = recipes.findGeometryWriter()
        assertEquals('{"type":"Point","coordinates":[-122.45,43.21]}', str)
    }

    @Test void fromString() {
        GeometryRecipes recipes = new GeometryRecipes()
        List<Geometry> geoms = recipes.fromString()
        assertEquals 3, geoms.size()
        assertEquals "POINT (-123.15 46.237)", geoms[0].wkt
        assertEquals "LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geoms[1].wkt
        assertEquals "POINT (-123.15 46.237)", geoms[2].wkt
    }

    // WKB

    @Test void readGeometryFromWKBReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromWKBReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromWKB() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromWKB()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToWKB() {
        GeometryRecipes recipes = new GeometryRecipes()
        String wkb = recipes.writeGeometryToWKB()
        assertEquals("0000000001C05EC9999999999A40471E5604189375", wkb)
    }

    @Test void writeGeometryToWKBUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String wkb = recipes.writeGeometryToWKBUsingWriter()
        assertEquals("000000000200000004400995810624DD2F404594FDF3B645A2401ADA1CAC0831274048E0A3D70A3D714023676C8B43958" +
                "140454BC6A7EF9DB2402EA3D70A3D70A4404AE624DD2F1AA0", wkb)

    }

    // WKT

    @Test void readGeometryFromWKTReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromWKTReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromWKT() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromWKT()
        assertEquals("LINESTRING (3.198 43.164, 6.7138 49.755, 9.702 42.592, 15.327 53.798)", geom.wkt)
    }

    @Test void writeGeometryToWKT() {
        GeometryRecipes recipes = new GeometryRecipes()
        String wkt = recipes.writeGeometryToWKT()
        assertEquals("POINT (-123.15 46.237)", wkt)
    }

    @Test void writeGeometryToWKTUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String wkt = recipes.writeGeometryToWKTUsingWriter()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", wkt)
    }

    // GeoJSON

    @Test void readGeometryFromGeoJSONReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGeoJSONReader()
        assertEquals('{"type":"Point","coordinates":[-123.15,46.237]}', geom.geoJSON)
    }

    @Test void readGeometryFromGeoJSON() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGeoJSON()
        assertEquals('{"type":"LineString","coordinates":[[3.198,43.164],[6.713,49.755],[9.702,42.592],[15.32,53.798]]}', geom.geoJSON)
    }

    @Test void writeGeometryToGeoJSON() {
        GeometryRecipes recipes = new GeometryRecipes()
        String json = recipes.writeGeometryToGeoJSON()
        assertEquals('{"type":"Point","coordinates":[-123.15,46.237]}', json)
    }

    @Test void writeGeometryToGeoJSONUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String json = recipes.writeGeometryToGeoJSONUsingWriter()
        assertEquals('{"type":"LineString","coordinates":[[3.198,43.164],[6.713,49.755],[9.702,42.592],[15.32,53.798]]}', json)
    }

    // GeoPackage

    @Test void writeGeometryToGeoPackageString() {
        GeometryRecipes recipes = new GeometryRecipes()
        String str = recipes.writeGeometryToGeoPackageString()
        assertEquals("4750000200000000c05ec9999999999ac05ec9999999999a40471e560418937540471e56041893750000000001c05ec9999999999a40471e5604189375", str)
    }

    @Test void writeGeometryToGeoPackageBytes() {
        GeometryRecipes recipes = new GeometryRecipes()
        byte[] bytes = recipes.writeGeometryToGeoPackageBytes()
        assertEquals("4750000200000000c05ec9999999999ac05ec9999999999a40471e560418937540471e56041893750000000001c05ec9999999999a40471e5604189375", bytes.encodeHex().toString())
    }

    @Test void readGeometryFromGeoPackageString() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.readGeometryFromGeoPackageString()
        assertEquals("POINT (-123.15 46.237)", geometry.wkt)
    }

    @Test void readGeometryFromGeoPackageBytes() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geometry = recipes.readGeometryFromGeoPackageBytes()
        assertEquals("POINT (-123.15 46.237)", geometry.wkt)
    }

    // KML

    @Test void readGeometryFromKMLReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromKMLReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromKML() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromKML()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToKML() {
        GeometryRecipes recipes = new GeometryRecipes()
        String kml = recipes.writeGeometryToKML()
        assertEquals("<Point><coordinates>-123.15,46.237</coordinates></Point>", kml)
    }

    @Test void writeGeometryToKMLUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String kml = recipes.writeGeometryToKMLUsingWriter()
        assertEquals("<LineString><coordinates>3.198,43.164 6.713,49.755 9.702,42.592 15.32,53.798</coordinates></LineString>", kml)
    }

    // Geobuf

    @Test void readGeometryFromGeobufReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGeobufReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromGeobuf() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGeobuf()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToGeobuf() {
        GeometryRecipes recipes = new GeometryRecipes()
        String geobuf = recipes.writeGeometryToGeobuf()
        assertEquals("10021806320c08001a08dffab87590958c2c", geobuf)
    }

    @Test void writeGeometryToGeobufUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String geobuf = recipes.writeGeometryToGeobufUsingWriter()
        assertEquals("10021806322408021a20e0b08603c0859529f089ad03b0c8a40690efec02efb1ea06a0e5ad05e0f5d70a", geobuf)
    }

    // GML 2

    @Test void readGeometryFromGml2Reader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGml2Reader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromGml2() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGml2()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToGml2() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gml = recipes.writeGeometryToGml2()
        assertEquals("<gml:Point><gml:coordinates>-123.15,46.237</gml:coordinates></gml:Point>", gml)
    }

    @Test void writeGeometryToGml2UsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gml = recipes.writeGeometryToGml2UsingWriter()
        assertEquals("<gml:LineString><gml:coordinates>3.198,43.164 6.713,49.755 9.702,42.592 15.32,53.798</gml:coordinates></gml:LineString>", gml)
    }

    // GML 3

    @Test void readGeometryFromGml3Reader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGml3Reader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromGml3() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGml3()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToGml3() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gml = recipes.writeGeometryToGml3()
        assertEquals("<gml:Point><gml:pos>-123.15 46.237</gml:pos></gml:Point>", gml)
    }

    @Test void writeGeometryToGml3UsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gml = recipes.writeGeometryToGml3UsingWriter()
        assertEquals("<gml:LineString><gml:posList>3.198 43.164 6.713 49.755 9.702 42.592 15.32 53.798</gml:posList></gml:LineString>", gml)
    }

    // GPX

    @Test void readGeometryFromGpxReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGpxReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void readGeometryFromGpx() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGpx()
        assertEquals("LINESTRING (3.198 43.164, 6.713 49.755, 9.702 42.592, 15.32 53.798)", geom.wkt)
    }

    @Test void writeGeometryToGpx() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gpx = recipes.writeGeometryToGpx()
        assertEquals("<wpt lat='46.237' lon='-123.15'/>", gpx)
    }

    @Test void writeGeometryToGpxUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String gpx = recipes.writeGeometryToGpxUsingWriter()
        assertEquals("<rte><rtept lat='43.164' lon='3.198' /><rtept lat='49.755' lon='6.713' /><rtept lat='42.592' lon='9.702' /><rtept lat='53.798' lon='15.32' /></rte>", gpx)
    }

    // GeoRSS

    @Test void readGeometryFromGeoRSSReader() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGeoRSSReader()
        assertEquals("POINT (-123.15 46.237)", geom.wkt)
    }

    @Test void writeGeometryToGeoRSSUsingWriter() {
        GeometryRecipes recipes = new GeometryRecipes()
        String georss = recipes.writeGeometryToGeoRSSUsingWriter()
        assertEquals("<georss:line>43.164 3.198 49.755 6.713 42.592 9.702 53.798 15.32</georss:line>", georss)
    }

    // Google Polyline

    @Test void readGeometryFromGooglePolyline() {
        GeometryRecipes recipes = new GeometryRecipes()
        Geometry geom = recipes.readGeometryFromGooglePolyline()
        assertEquals("LINESTRING (-120.2 38.5, -120.95 40.7, -126.453 43.252)", geom.wkt)
    }

    @Test void writeGeometryToGooglePolyline() {
        GeometryRecipes recipes = new GeometryRecipes()
        String georss = recipes.writeGeometryToGooglePolyline()
        assertEquals("_nmfGoroRwhfg@womTv_vj@gxfQotkcAogha@", georss)
    }
}
