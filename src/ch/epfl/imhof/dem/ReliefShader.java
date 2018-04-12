package ch.epfl.imhof.dem;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.function.Function;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.painting.Color;
import ch.epfl.imhof.projection.Projection;

/**
 * Draws a colored shaded relief
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class ReliefShader 
{
	private final Projection projection;
	private final HGTDigitalElevationModel modelNum;
	private final Vector3 lightDirection;
	
	/**
	 * Draws the shaded relief
	 * @param projection
	 * 			given projection used to draw the relief
	 * @param modelNum
	 * 			the map ground's numerical model
	 * @param lightDirection
	 * 			vector indicating the direction of the light
	 */
	public ReliefShader(Projection projection, HGTDigitalElevationModel modelNum, Vector3 lightDirection)
	{
		this.projection = projection;
		this.modelNum = modelNum;
		this.lightDirection = lightDirection;
	}

	/**
	 * Creates a buffered image of the shaded relief
	 * @param pBL
	 * 			point bottom left
	 * @param pTR
	 * 			point top right
	 * @param widthPixel
	 * 			width of the image in pixel
	 * @param heightPixel
	 * 			height of the image in pixel
	 * @param r
	 * 			blur radius in double
	 * @return a buffered image of the shaded relief
	 */
	public BufferedImage shadedRelief(Point pBL, Point pTR, int widthPixel, int heightPixel, double r)
	{
		int rCeil = (int)Math.ceil(r);
		Function<Point, Point> projection = Point.alignedCoordinateChange(new Point(rCeil,heightPixel+ rCeil), pBL, new Point(widthPixel+ rCeil, rCeil ), pTR);
		if(r == 0)
		{
			return drawRawRelief(widthPixel, heightPixel, projection);
		}
		else
		{
			float[] kern = calculateKernelGauss(r);
			rCeil = kern.length /2;
			BufferedImage bi = drawRawRelief(widthPixel + 2*rCeil, heightPixel + 2*rCeil, projection);
			BufferedImage bif = flootImage(bi, kern);
			return bif.getSubimage(rCeil, rCeil, widthPixel, heightPixel);
		}
	}

	/**
	 * Draws the relief on a buffered image
	 * @param widthPixel
	 * 			given width of the relief on pixel
	 * @param heightPixel
	 * 			given height of the relief in pixel
	 * @param f
	 * 			given function to move the mark of the image to that of the plane
	 * @return a buffered image of the relief
	 */
	private BufferedImage drawRawRelief(int widthPixel, int heightPixel, Function<Point, Point> f)
	{
		BufferedImage image = new BufferedImage(widthPixel, heightPixel, BufferedImage.TYPE_INT_RGB);
		
		PointGeo pGeo;
		Vector3 normalVector;
		double cosinus;
		Color color;
		for (int i = 0; i < widthPixel; i++) 
		{
			for (int j = 0; j < heightPixel; j++) 
			{
				pGeo = projection.inverse(f.apply(new Point(i,j)));
				normalVector = modelNum.normalAt(pGeo);
				cosinus = lightDirection.scalarProduct(normalVector) / (lightDirection.norm() * normalVector.norm());
				color = Color.rgb(0.5 * (cosinus + 1), 0.5 * (cosinus + 1), 0.5 * (0.7 * cosinus + 1));
				//image.setRGB(i, j, color.toAWTColor().getRGB());
				image.setRGB(i, j, Color.packedColor(0.5 * (cosinus + 1), 0.5 * (cosinus + 1), 0.5 * (0.7 * cosinus + 1)));
			}
		}
		
		return image;
	}

	/**
	 * Calculates and returns the 
	 * @param r
	 * 			given blur radius
	 * @return the center of the shaded zone
	 */
	private float[] calculateKernelGauss(double r)
	{
		double sigma = r / 3d;
		int rCeil = (int)Math.ceil(r);
		int n = 2 * rCeil + 1;
		float[] kern = new float[n];
		float somme = 0;
		for(int i = 0; i < n; i++)
		{
			kern[i] = (float)(Math.exp(-Math.pow(i-(rCeil), 2)/(2 * sigma * sigma)));
			somme += kern[i];
		}
		for(int i = 0; i < n; i++)
		{
			kern[i] = kern[i] / somme;
		}
		return kern;
	}

	/**
	 * Puts the shade and blur on the given buffered image and returns it
	 * @param bi
	 *			given buffered image
	 * @param kern
	 * 			center of the shaded zone
	 * @return new buffered image with the shaded and blured zones
	 */
	private BufferedImage flootImage(BufferedImage bi, float[] kern)
	{
		Kernel kernelHorizontal = new Kernel(1, kern.length, kern);
		Kernel kernelVertical = new Kernel(kern.length, 1, kern);
		ConvolveOp co1 = new ConvolveOp(kernelHorizontal, ConvolveOp.EDGE_NO_OP, null);
		ConvolveOp co2 = new ConvolveOp(kernelVertical, ConvolveOp.EDGE_NO_OP, null);
		return co2.filter(co1.filter(bi, null), null);
	}
}
