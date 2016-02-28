package law.raw.view.stavePanel.barEventMapping;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import law.raw.composition.components.trackComponents.Bar;
import law.raw.composition.components.trackComponents.barComponents.BarEvent;
import law.raw.composition.components.tracks.InstrumentTrack;
import law.raw.view.stavePanel.noteSelection.CompositeNoteValue;
import law.raw.view.stavePanel.noteSelection.enumeration.NoteValue;

public class BarEventMappingObject {
	
	private Map<Point, InstrumentTrack> trackMap = new HashMap<Point, InstrumentTrack>();
	private Map<Rectangle, Bar> barMap = new HashMap<Rectangle, Bar>();
	private Map<Point, BarEvent> barEventMap = new HashMap<Point, BarEvent>();
	private Map<Point, Integer> pitchLocationMap = new HashMap<Point, Integer>();;

	private static int POINT_WINDOW_BOX_SIZE = 10;
	
	
	public void writeEvent(final Point aPoint, final NoteValue aNoteValue){
		Point mappedPoint = pointWindowCheck(aPoint);
		if(mappedPoint != null){
			
			//Get the bar from the dimension map.
			Bar bar = null;
			for(Rectangle rectangle : barMap.keySet()){
				if(rectangle.contains(mappedPoint)){
					bar = barMap.get(rectangle);
				}
			}
			
			BarEventManager.sortBarEvents(trackMap.get(mappedPoint), bar,
					barEventMap.get(mappedPoint), new CompositeNoteValue(aNoteValue), pitchLocationMap.get(mappedPoint));
		}
	}
	
	
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

	//TODO - work out whether bars should come through here.
	public void mapEventPoint(Point point, InstrumentTrack track, BarEvent noteEvent, int[] yLineLocations){
		for(int pitchLocationNumber = 0; pitchLocationNumber < yLineLocations.length ; pitchLocationNumber++){
			Point pitchPoint = new Point(point.x, yLineLocations[pitchLocationNumber]);
			trackMap.put(pitchPoint, track);
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

	public void mapBarRectangle(Rectangle rectangle, Bar bar) {
		barMap.put(rectangle, bar);
	}
	
}
