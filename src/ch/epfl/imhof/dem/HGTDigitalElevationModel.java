package ch.epfl.imhof.dem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel.MapMode;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;

/**
 * Represents an MNT stocked in an HGT file
 *
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class HGTDigitalElevationModel implements DigitalElevationModel
{
	private ShortBuffer buffer;
	private final PointGeo pointSW; 
	private final double numberLC;
	private final double resolution;

	/**
	 * Gets the pointSW
	 * @return PointGeo
	 */
	public PointGeo getPointSW()
	{
		return pointSW;
	}

	/**
	 * Gets the LC number
	 * @return numberLC (double)
	 */
	public double getNumberLC()
	{
		return numberLC;
	}

	/**
	 * Gets the resolution
	 * @return resolution (double)
	 */
	public double getResolution()
	{
		return resolution;
	}

	/**
	 * Takes a HGT file and reads its informations
	 * @param f
	 * 			the HGT file containing the numerical model of the map's ground
	 * @throws IllegalArgumentException
	 * 			if the name of the given file doesn't respect the conventions
	 * @throws IOException
	 * 			if the size of the given file in bytes divided by two is not an entire square root
	 */
	public HGTDigitalElevationModel(File f) throws IllegalArgumentException, IOException
	{
		String name = f.getName();		
		long length = f.length();
		char signNS = name.charAt(0);
		char signEW = name.charAt(3);
		int latitude;
		int longitude;
		
		if((signNS != 'N' && signNS != 'S') || (signEW != 'E' && signEW != 'W'))
		{
			throw new IllegalArgumentException("Invalid Name");
		}
		
		try
		{
			latitude = Integer.parseInt(name.substring(1, 3)); //est-ce vraiment des entiers?
			longitude = Integer.parseInt(name.substring(4, 7));
			if(latitude<0 || longitude<0)
			{
				throw new IllegalArgumentException("Invalid Name");
			}
		}catch(NumberFormatException nfe)
		{
			throw new IllegalArgumentException("Invalid Name");
		}
		
		if(!name.substring(7).equals(".hgt"))
		{
			throw new IllegalArgumentException("Invalid Name");
		}

		numberLC = Math.sqrt(length/2.0);
		if(numberLC != Math.floor(numberLC))
		{
			throw new IllegalArgumentException("Invalid Name");
		}

		
		resolution = Math.toRadians(1.0/(numberLC -1));
		if(signNS == 'S')
		{
			latitude = -latitude;
		}
		if(signEW == 'W')
		{
			longitude = -longitude;
		}

		pointSW = new PointGeo(Math.toRadians(longitude), Math.toRadians(latitude));

		try (FileInputStream stream = new FileInputStream(f)) 
		{
			buffer = stream.getChannel().map(MapMode.READ_ONLY, 0, length).asShortBuffer();
		}
	}

	@Override
	public void close() throws Exception 
	{
		//buffer.clear();
		buffer = null;
	}

	@Override
	public Vector3 normalAt(PointGeo p) throws IllegalArgumentException 
	{
		if(p.latitude() < pointSW.latitude() || p.latitude() >= (pointSW.latitude()+ Math.toRadians(1)) 
				|| p.longitude() < pointSW.longitude() || p.longitude() >= (pointSW.longitude()+ Math.toRadians(1)) ) //à vérifier les inégalités
		{
			throw new IllegalArgumentException("The point isn't contain in MNT");
		}
		double s = Earth.RADUIS * resolution;

		int i = (int)Math.floor((p.longitude() - pointSW.longitude()) / resolution);
		int j = (int)Math.floor((p.latitude() - pointSW.latitude()) / resolution);
		
		
		double zij = buffer.get((int)((numberLC-j-1)*numberLC + i ));
		double zi1j = buffer.get((int)((numberLC-j-1)*numberLC + i + 1));
		double zij1 = buffer.get((int)((numberLC-j-2)*numberLC + i ));
		double zi1j1 = buffer.get((int)((numberLC-j-2)*numberLC + i + 1));
		
		double x = (1/2.0)*s*(zij-zi1j+zij1-zi1j1);
		double y = (1/2.0)*s*(zij+zi1j-zij1-zi1j1);
		double z = s*s;
		
		return (new Vector3(x,y,z));
	}
}
