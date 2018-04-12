package ch.epfl.imhof.osm;

import java.io.IOException;

import org.xml.sax.SAXException;

import ch.epfl.imhof.Map;
import ch.epfl.imhof.projection.CH1903Projection;

public class CPTest {

	public static void main(String[] args) throws SAXException, IOException
	{
		CPTest ref = new CPTest();
		
		/*
		String fileName = ref.getClass().getResource("/bc.osm").getFile();
		
		OSMMap map0 = OSMMapReader.readOSMFile(fileName, false);
		for(OSMWay w: map.ways())
		{
			for(OSMNode e: w.nodes())
			{
				System.out.println(e.id());
			}
		}
		System.out.println("nombre way map0: " + map0.ways().size());
		
		OSMToGeoTransformer transformeur = new OSMToGeoTransformer(new CH1903Projection());
		Map m0 = transformeur.transform(map0);
		System.out.println("nombre polyline map0: " + m0.polyLines().size());
		System.out.println("nombre polygon map0: " + m0.polygons().size());
		*/

		
		
		String fileName2 = ref.getClass().getResource("/lc.osm.gz").getFile();
		
		OSMMap map2 = OSMMapReader.readOSMFile(fileName2, true);
		/*
		for(OSMWay w: map2.ways())
		{
			for(OSMNode e: w.nodes())
			{
				System.out.println(e.id());
			}
			System.out.println(w.id());
			System.out.println("-------------------------------------------------");
		}
		*/
		System.out.println(map2.ways().size());
		
		OSMToGeoTransformer transformeur2 = new OSMToGeoTransformer(new CH1903Projection());
		Map m2 = transformeur2.transform(map2);
		System.out.println("nombre polyline map2: " + m2.polyLines().size());
		System.out.println("nombre polygon map2: " + m2.polygons().size());
		
		
		String fileName3 = ref.getClass().getResource("/interlaken.osm.gz").getFile();
		OSMMap map3 = OSMMapReader.readOSMFile(fileName3, true);
		
		OSMToGeoTransformer transformeur3 = new OSMToGeoTransformer(new CH1903Projection());
		Map m3 = transformeur3.transform(map3);
		System.out.println("nombre polyline Interlaken: " + m3.polyLines().size());
		System.out.println("nombre polygon Interlaken: " + m3.polygons().size());
		
		String fileName4 = ref.getClass().getResource("/berne.osm.gz").getFile();
		OSMMap map4 = OSMMapReader.readOSMFile(fileName4, true);
		
		
		//System.out.println("nombre way Berne: " + map4.ways().size());
		//System.out.println("nombre relation Berne: " + map4.relations().size());
		
		OSMToGeoTransformer transformeur4 = new OSMToGeoTransformer(new CH1903Projection());
		Map m4 = transformeur4.transform(map4);
		System.out.println("nombre polyline Berne: " + m4.polyLines().size());
		System.out.println("nombre polygon Berne: " + m4.polygons().size());
		
		/*
		String fileName5 = ref.getClass().getResource("/lausanne.osm.gz").getFile();
		OSMMap map5 = OSMMapReader.readOSMFile(fileName5, true);
		
		//System.out.println("nombre way Lausanne: " + map5.ways().size());
		
		OSMToGeoTransformer transformeur5 = new OSMToGeoTransformer(new CH1903Projection());
		Map m5 = transformeur5.transform(map5);
		System.out.println("nombre polyline Lausanne: " + m5.polyLines().size());
		System.out.println("nombre polygon Lausanne: " + m5.polygons().size());
		*/
	}

}
