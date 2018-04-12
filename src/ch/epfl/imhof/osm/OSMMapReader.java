package ch.epfl.imhof.osm;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.zip.GZIPInputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ch.epfl.imhof.PointGeo;

/**
 * Constructs an OSMMap from the data stocked in a file in OSM format
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class OSMMapReader 
{	
	private OSMMapReader() {}

	/**
	 * Reads the OSM map in the file of the given name by decompressing it with gzip if and only if the second argument is true
	 * @param fileName
	 * 			name of the file containing the OSM map
	 * @param unGZip
	 * 			boolean : true iff the file is "ziped"
	 * @return OSMMap
	 * 			the "unziped" OSM map
	 * @throws a SAXException in case of an error in the XML file containing the map,
	 * @throws an IOException in case of another in/outputs error, for example, if the file doesn't exist
	 */
	public static OSMMap readOSMFile(String fileName, boolean unGZip) throws SAXException, IOException
	{	
		OSMMap.Builder mapInConstruction = new OSMMap.Builder();
		try (InputStream i = new BufferedInputStream(new FileInputStream(fileName)))
		{
			XMLReader r = XMLReaderFactory.createXMLReader();

			r.setContentHandler(new DefaultHandler() {
				OSMNode.Builder noeudEC;
				OSMWay.Builder wayEC;
				OSMRelation.Builder relationEC;
				String id;
				Deque<OSMEntity.Builder> pile = new ArrayDeque<>();

				@Override
				public void startElement(String uri, String lName, String qName, Attributes atts) throws SAXException 
				{
					switch(qName)
					{
					case "node": 
						noeudEC= new OSMNode.Builder(Long.parseLong(atts.getValue("id")), 
								new PointGeo(Math.toRadians(Double.parseDouble(atts.getValue("lon"))), Math.toRadians(Double.parseDouble(atts.getValue("lat")))));
						pile.addLast(noeudEC);
						break;
					case "way": 
						wayEC = new OSMWay.Builder(Long.parseLong(atts.getValue("id")));
						pile.addLast(wayEC);
						break;
					case "nd":
						id = atts.getValue("ref");
						if(mapInConstruction.nodeForId(Long.parseLong(id)) != null)
						{
							wayEC.addNode(mapInConstruction.nodeForId(Long.parseLong(id)));
						}else
						{
							wayEC.setIncomplete();
						}
						break;	
					case "relation":
						relationEC = new OSMRelation.Builder(Long.parseLong(atts.getValue("id")));
						pile.addLast(relationEC);
						break;
					case "member":
						String type = atts.getValue("type");
						switch(type)
						{
						case "node":
							id = atts.getValue("ref");
							if(mapInConstruction.nodeForId(Long.parseLong(id)) != null)
							{
								relationEC.addMember(OSMRelation.Member.Type.NODE, atts.getValue("role"), mapInConstruction.nodeForId(Long.parseLong(id)));
							}else
							{
								relationEC.setIncomplete();
							}
							break;
						case "way":
							id = atts.getValue("ref");
							if(mapInConstruction.wayForId(Long.parseLong(id)) != null)
							{
								relationEC.addMember(OSMRelation.Member.Type.WAY, atts.getValue("role"), mapInConstruction.wayForId(Long.parseLong(id)));
							}else
							{
								relationEC.setIncomplete();
							}
							break;
						case "relation":
							id = atts.getValue("ref");
							if(mapInConstruction.relationForId(Long.parseLong(id)) != null)
							{
								relationEC.addMember(OSMRelation.Member.Type.RELATION, atts.getValue("role"), mapInConstruction.relationForId(Long.parseLong(id)));
							}else
							{
								relationEC.setIncomplete();
							}
							break;
						}
						break;
					case "tag":
						if(pile.peekLast() != null)
						{
							pile.peekLast().setAttribute(atts.getValue("k"), atts.getValue("v"));
						}
						break;
					}  
				}

				@Override
				public void endElement(String uri, String lName, String qName) 
				{
					switch(qName)
					{
					case "node":
						if(!noeudEC.isIncomplete())
							mapInConstruction.addNode(noeudEC.build());
						pile.removeLast();
						break;
					case "way":
						if(!wayEC.isIncomplete())
							mapInConstruction.addWay(wayEC.build());
						pile.removeLast();
						break;
					case "relation":
						if(!relationEC.isIncomplete())
							mapInConstruction.addRelation(relationEC.build());
						pile.removeLast();
						break;
					}
				}
			});

			if(unGZip)
			{
				GZIPInputStream gzi = new GZIPInputStream(i);
				r.parse(new InputSource(gzi));
			}else
			{
				r.parse(new InputSource(i));
			}
			return mapInConstruction.build();
		}
	}
}