package ch.epfl.imhof.dem;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class TestEtape10 
{
	@Test(expected = IllegalArgumentException.class)
	public void nameCheck1()
	{
		File file = new File("N46E-07.hgt");
		try
		{
			new HGTDigitalElevationModel(file);
		}catch(IOException e)
		{
			System.out.println("HEY");
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nameCheck2()
	{
		File file = new File("N46E007.hgt");
		try
		{
			new HGTDigitalElevationModel(file);
		}catch(IOException e)
		{
			System.out.println("HEY");
		}
	}
	
	@Test
	public void nameCheck3()
	{
		File file = new File("N46E007.hgt");
		try
		{
			new HGTDigitalElevationModel(file);
		}catch(IOException e)
		{
			System.out.println("HEY");
		}
	}
}
