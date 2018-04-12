package ch.epfl.imhof;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.xml.sax.SAXException;

import ch.epfl.imhof.dem.Earth;
import ch.epfl.imhof.dem.HGTDigitalElevationModel;
import ch.epfl.imhof.dem.ReliefShader;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.painting.Color;
import ch.epfl.imhof.painting.Java2DCanvas;
import ch.epfl.imhof.painting.Painter;
import ch.epfl.imhof.painting.SwissPainter;
import ch.epfl.imhof.projection.CH1903Projection;
import ch.epfl.imhof.projection.Projection;

public final class Main 
{
	public static double rFl_mm = 1.7;
	public static void main(String[] args) throws SAXException, IOException 
	{
		Main m = new Main();
		
		if(args.length != 8)
		{
			throw new IllegalArgumentException("Incorrect number of commande line arguments");
		}
		String fileNameOSM = args[0];
		String fileNameHGT = args[1];
		double longitudeDegPBL, latitudeDegPBL, longitudeDegPTR, latitudeDegPTR;
		int resolutionDPI;
		try
		{
			longitudeDegPBL = Double.parseDouble(args[2]);
			latitudeDegPBL = Double.parseDouble(args[3]);
			longitudeDegPTR = Double.parseDouble(args[4]);
			latitudeDegPTR = Double.parseDouble(args[5]);
			resolutionDPI = Integer.parseInt(args[6]);
		}catch(NumberFormatException nfe)
		{
			throw new IllegalArgumentException("Problem with the numerical arguments");
		}
		String fileNamePNG = args[7];
		if(!fileNameOSM.substring(fileNameOSM.length()-7).equals(".osm.gz") 
				|| !fileNameHGT.substring(fileNameHGT.length()-4).equals(".hgt")
				|| !fileNamePNG.substring(fileNamePNG.length()-4).equals(".png"))
		{
			throw new IllegalArgumentException("Problem with the filenames");
		}
		PointGeo pointGeoBL = new PointGeo(Math.toRadians(longitudeDegPBL), Math.toRadians(latitudeDegPBL));
		PointGeo pointGeoTR = new PointGeo(Math.toRadians(longitudeDegPTR), Math.toRadians(latitudeDegPTR));
		Projection projectionCH1903 = new CH1903Projection();
		Point pointBL = projectionCH1903.project(pointGeoBL);
		Point pointTR = projectionCH1903.project(pointGeoTR);
		
		double resolutionPPM = resolutionDPI*100/2.54;
		
		int h = (int)Math.round(resolutionPPM * (pointGeoTR.latitude() - pointGeoBL.latitude()) * Earth.RADUIS / 25_000.0);
		int w = (int)Math.round((pointTR.x() - pointBL.x()) * h / (pointTR.y() - pointBL.y()));
		
		double rFl_pics = rFl_mm * resolutionPPM / 1000;
		

		Painter painter = SwissPainter.painter();
		String fileName = m.getClass().getResource("/" +  fileNameOSM).getFile();
		OSMMap osmMap = OSMMapReader.readOSMFile(fileName, true);
		OSMToGeoTransformer transformeur = new OSMToGeoTransformer(projectionCH1903);
		Map map = transformeur.transform(osmMap);
		Java2DCanvas canvas = new Java2DCanvas(pointBL, pointTR, w, h, resolutionDPI, Color.WHITE);
		painter.drawMap(map, canvas);
		BufferedImage bi = canvas.image();
		
		String fileName2 = m.getClass().getResource("/" +  fileNameHGT).getFile();
		HGTDigitalElevationModel modelNum = new HGTDigitalElevationModel(new File(fileName2));
		ReliefShader rS = new ReliefShader(projectionCH1903, modelNum, new Vector3(-1,1,1));
		BufferedImage bi2 = rS.shadedRelief(pointBL, pointTR, w, h, rFl_pics);
		
		Color color;
		
		for (int x = 0; x < w; x++) 
		{
			for (int y = 0; y < h; y++) 
			{
				color = Color.rgb(bi.getRGB(x, y)).multiplyColors(Color.rgb(bi2.getRGB(x, y)));
				bi.setRGB(x, y, Color.packedColor(color.r(), color.g(), color.b()));
			}
		}
		
		ImageIO.write(bi2, "png", new File("Relief.png"));
		ImageIO.write(bi, "png", new File(fileNamePNG));
		
	}
}
