package ch.epfl.imhof.painting;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.xml.sax.SAXException;

import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.projection.CH1903Projection;

public class TestEtape9 
{
	public static void main(String[] args)throws SAXException, IOException 
	{
		Painter painter = SwissPainter.painter();
		
		TestEtape9 ref = new TestEtape9();
		String fileName = ref.getClass().getResource("/lausanne.osm.gz").getFile();
		OSMMap mapOSM= OSMMapReader.readOSMFile(fileName, true);
		OSMToGeoTransformer transformeur = new OSMToGeoTransformer(new CH1903Projection());
		Map map = transformeur.transform(mapOSM);
		
		Point bl = new Point(532510, 150590);
		Point tr = new Point(539570, 155260);
		Java2DCanvas canvas = new Java2DCanvas(bl, tr, 1600, 1060, 150, Color.WHITE);
	
		painter.drawMap(map, canvas);
		ImageIO.write(canvas.image(), "png", new File("loz.png"));
		
		String fileName1 = ref.getClass().getResource("/interlaken.osm").getFile();
		OSMMap mapOSM1 = OSMMapReader.readOSMFile(fileName1, false);
		OSMToGeoTransformer transformeur1 = new OSMToGeoTransformer(new CH1903Projection());
		Map map1 = transformeur1.transform(mapOSM1);
		
		Point bl1 = new Point(628590, 168210);
		Point tr1 = new Point(635660, 172870);
		Java2DCanvas canvas1 = new Java2DCanvas(bl1, tr1, 800, 530, 72, Color.WHITE);
	
		painter.drawMap(map1, canvas1);
		ImageIO.write(canvas1.image(), "png", new File("interlaken.png"));
	}
}
