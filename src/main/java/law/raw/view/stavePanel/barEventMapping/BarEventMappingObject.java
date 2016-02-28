package law.raw.view.stavePanel.barEventMapping;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import law.raw.composition.components.trackComponents.Bar;
import law.raw.composition.components.trackComponents.barComponents.CompositionBarEvent;
import law.raw.composition.components.tracks.InstrumentTrack;
import law.raw.view.stavePanel.noteSelection.CompositeNoteValue;
import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;

/**
 * When notes and other objects are drawn onto the stave panel, there needs to be some
 * way of relating the location to that object so the user can interact with it. That is what
 * this object is for.
 * 
 */
public class BarEventMappingObject {
	
	private Map<Point, InstrumentTrack> trackMap = new HashMap<Point, InstrumentTrack>();
	private Map<Point, Bar> barMap = new HashMap<Point, Bar>();
	private Map<Point, CompositionBarEvent> barEventMap = new HashMap<Point, CompositionBarEvent>();
	private Map<Point, Integer> pitchLocationMap = new HashMap<Point, Integer>();
	
	private static int POINT_WINDOW_BOX_SIZE = 10;


	/**
	 * Called when the user clicks on the stave panel. First it checks if the point
	 * is close to a mapped point, indicating that the user can interact with that
	 * point, and then calls the BarEventManager to perform the writing operation
	 * and make the change to the composition. 
	 * @param aPoint
	 * @param aNoteValue
	 */
	public void writeEvent(final Point aPoint, final WrittenNote aNoteValue){
		Point mappedPoint = pointWindowCheck(aPoint);
		if(mappedPoint != null){
			BarEventManager.sortBarEvents(trackMap.get(mappedPoint), barMap.get(mappedPoint),
					barEventMap.get(mappedPoint), new CompositeNoteValue(aNoteValue), pitchLocationMap.get(mappedPoint));
		}
	}
	
	/**
	 * This method is used in the stave panel so that when the mouse is moved over a
	 * possible place to write a note, it will stick to that location so that the
	 * user is aware.
	 * @param aPoint
	 * @return
	 */
	public Point checkPoint(final Point aPoint){
		Point mappedPoint = pointWindowCheck(aPoint);
		if(barEventMap.containsKey(mappedPoint)){
			return mappedPoint;
		}
		return null;
	}
	
	private Point pointWindowCheck(Point mouseListenerPoint){
		Set<Point> mappedPoints = barEventMap.keySet();
		int x0 = mouseListenerPoint.x - POINT_WINDOW_BOX_SIZE/2;
		int x1 = mouseListenerPoint.x + POINT_WINDOW_BOX_SIZE/2;
		int y0 = mouseListenerPoint.y - POINT_WINDOW_BOX_SIZE/2;
		int y1 = mouseListenerPoint.y + POINT_WINDOW_BOX_SIZE/2;
		for(Point mappedPoint : mappedPoints){
			int x = mappedPoint.x;
			int y = mappedPoint.y;
			if((x > x0 && x < x1) && (y > y0 && y < y1)){
				return mappedPoint;
			}
		}
		
		return null;
	}

	/**
	 * Used by the composition drawer, this method taks the CompositionBarEvent that was drawn 
	 * and also the point on which it was drawn so that they can be mapped together. 
	 * However, it also takes the Bar and  InstrumentTrack in which the note was contained. The reason
	 * for this is that it is not just a case of changing the properties of the CompositionBarEvent,
	 * it may overwrite an existing CompositionBarEvent or it may over run into the next bar. If this
	 * is the case then further logic is required in the track and bar to deal with it. 
	 * @param point
	 * @param track
	 * @param bar
	 * @param noteEvent
	 * @param yLineLocations
	 */
	public void mapEventPoint(Point point, InstrumentTrack track, Bar bar, CompositionBarEvent noteEvent, int[] yLineLocations){
		
		for(int pitchLocationNumber = 0; pitchLocationNumber < yLineLocations.length ; pitchLocationNumber++){
			Point pitchPoint = new Point(point.x, yLineLocations[pitchLocationNumber]);
			trackMap.put(pitchPoint, track);
			barMap.put(pitchPoint, bar);
			barEventMap.put(pitchPoint, noteEvent);
			pitchLocationMap.put(pitchPoint, pitchLocationNumber);
		}
	}
	
	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("trackMap: ");
		stringBuffer.append(trackMap.toString());
		stringBuffer.append("\nbarMap: ");
		stringBuffer.append(barMap.toString());
		stringBuffer.append("\nbarEventMap: ");
		stringBuffer.append(barEventMap.toString());
		stringBuffer.append("\npitchLocationMap: ");
		stringBuffer.append(pitchLocationMap.toString());

		return stringBuffer.toString();
	}
}
