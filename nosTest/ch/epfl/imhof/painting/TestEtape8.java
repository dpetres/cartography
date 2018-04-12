package ch.epfl.imhof.painting;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import org.xml.sax.SAXException;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.projection.CH1903Projection;

public class TestEtape8 {

	public static void main(String[] args)throws SAXException, IOException 
	{
		Predicate<Attributed<?>> isLake = Filters.tagged("natural", "water");
		Painter lakesPainter = Painter.polygon(Color.BLUE).when(isLake);

		Predicate<Attributed<?>> isBuilding = Filters.tagged("building");
		Painter buildingsPainter = Painter.polygon(Color.BLACK).when(isBuilding);

		Painter painter = buildingsPainter.above(lakesPainter);
		
		TestEtape8 ref = new TestEtape8();
		String fileName = ref.getClass().getResource("/lausanne.osm.gz").getFile();
		OSMMap mapOSM= OSMMapReader.readOSMFile(fileName, true);
		OSMToGeoTransformer transformeur = new OSMToGeoTransformer(new CH1903Projection());
		Map map = transformeur.transform(mapOSM);
		
		Point bl = new Point(532510, 150590);
		Point tr = new Point(539570, 155260);
		Java2DCanvas canvas = new Java2DCanvas(bl, tr, 800, 530, 96, Color.WHITE);
		
		painter.drawMap(map, canvas);
		ImageIO.write(canvas.image(), "png", new File("loz.png"));
	}

}
