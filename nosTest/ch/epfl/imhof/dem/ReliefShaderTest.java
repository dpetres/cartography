package ch.epfl.imhof.dem;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Test;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.projection.CH1903Projection;
import ch.epfl.imhof.projection.Projection;

public class ReliefShaderTest
{
    @Test
    public void drawRelieftest() throws Exception
    {
    	ReliefShaderTest m = new ReliefShaderTest();
        String fichier = "/N46E006.hgt";
        String basePath = m.getClass().getResource(fichier).getFile();
        File file = new File(basePath);
        
        Projection projection = new CH1903Projection();
        HGTDigitalElevationModel HGTModel = new HGTDigitalElevationModel(file);
        Vector3 lightVector = new Vector3(-1, 1, 1);
        
        Point   bottomLeft = projection.project(new PointGeo(Math.toRadians(6.5594), Math.toRadians(46.5032))),
                topRight = projection.project(new PointGeo(Math.toRadians(6.6508), Math.toRadians(46.5459)));
        int     width = 3396,
                height = 2246;
        double  radius = (300d*1.7)/25.4;
        
        ReliefShader rs = new ReliefShader(projection, HGTModel, lightVector);
        
        BufferedImage image = rs.shadedRelief(bottomLeft, topRight, width, height, radius);
        
        ImageIO.write(image, "png", new File("test.png"));
    }

}
