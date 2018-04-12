package ch.epfl.imhof.painting;

import java.util.Arrays;

/**
 * Represents a painter for roads
 * 
 * @author Diana Petrescu (250971)
 * @author Clarisse Fleurimont (246866)
 */
public final class RoadPainterGenerator 
{
	private RoadPainterGenerator(){}

	/**
	 * Creates an empty painter, draws an empty map on the canvas
	 * @return an empty painter
	 */
	private static Painter emptyPainter()
	{
		return (map, canvas) -> {};
	}

	/**
	 * Creates a painter for the roads
	 * @param roadSpec
	 * 			indicates the type of road, color, width of the lines 
	 * @return a painter that draws the given roads of the map on the canvas
	 */
	public static Painter painterForRoads(RoadSpec... roadSpec)
	{
		return (map, canvas) ->
		{
			Painter painterTunnel = emptyPainter();
			Painter painterRoadInner = emptyPainter();
			Painter painterRoadOuter = emptyPainter();
			Painter painterBridgeInner = emptyPainter();
			Painter painterBridgeOuter = emptyPainter();

			for(RoadSpec r: Arrays.asList(roadSpec))
			{
				painterTunnel = painterTunnel.above(r.pTunnel());
				painterRoadInner = painterRoadInner.above(r.pRoadInner());
				painterRoadOuter = painterRoadOuter.above(r.pRoadOuter());
				painterBridgeInner = painterBridgeInner.above(r.pBridgeInner());
				painterBridgeOuter = painterBridgeOuter.above(r.pBridgeOuter());
			}
			painterBridgeInner.above(painterBridgeOuter.above(painterRoadInner.above(painterRoadOuter.above(painterTunnel)))).drawMap(map, canvas);
		};
	}
}
