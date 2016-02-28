/**
 * This class takes in a track object and draws the canvas.
 */

package law.raw.view.stavePanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import law.counterpoint.rules.reporting.RuleReport;
import law.musicRelatedClasses.Clef;
import law.raw.composition.Composition;
import law.raw.composition.components.trackComponents.Bar;
import law.raw.composition.components.trackComponents.barComponents.CompositionBarEvent;
import law.raw.composition.components.tracks.InstrumentTrack;
import law.raw.view.stavePanel.barEventMapping.BarEventMappingObject;

/**
 * This drawer holds all of the logic for drawing the composition and any
 * rule report details returned by the counterpoint algorithms. 
 * It also holds a BarEventMappingObject which relates every drawn object to it's
 * point so that the user can interact with them by clicking on that point
 * again.
 * @author BAZ
 *
 */
class CompositionDrawer {	
	private static int X_BAR_POSITION = 50; 
	private static int Y_BAR_POSITION = 50;
	private static int BAR_HEIGHT = 65;
	private static int BAR_LENGTH = 300;
	private static int FIRST_BARNOTE_INSET = 50;
	private static int LAST_BARNOTE_INSET = 15;
	private static int LENGTH_OF_NOTE_BAR_SECTION = (BAR_LENGTH - (FIRST_BARNOTE_INSET + LAST_BARNOTE_INSET));
	
	private Dimension drawingDimension;
	private BarEventMappingObject barEventMappingObject;
	
	public void drawComposition(Graphics g, Composition aCompositionDTO){
		barEventMappingObject = new BarEventMappingObject();
		
		for(InstrumentTrack track: aCompositionDTO){
			drawTrack(g, track, barEventMappingObject);
		}	
	}
	
	private void drawTrack(Graphics g, InstrumentTrack track, 
			final BarEventMappingObject aBarEventMappingObject) {

		// new bars shift downwards for each instrument
		int barYSeparation = (int) (BAR_HEIGHT + (BAR_HEIGHT * 1.4));

		int nextTrackYLocation = (Y_BAR_POSITION + (track.getInstrumentNum() * barYSeparation));
		int nextBarXLocation = 0;

		//Finding the y values of the horizontal pitch lines for the track 
		int[] yLineLocations = new int[17];
		for(int yLine = 0; yLine < 17; yLine++){
			yLineLocations[yLine] = nextTrackYLocation + (int)(BAR_HEIGHT * ((float)(12 - yLine)/8f));
		}
		
		
		//For each bar in the track
		Clef barClef = null;
		
		int extendedBarLength = BAR_LENGTH;
		for (Bar bar : track) {
			int spacingModifer = 0;
			extendedBarLength += spacingModifer;
			
			//Work out location of next bar
			nextBarXLocation = X_BAR_POSITION + (extendedBarLength * bar.getBarNumber()) ;
			//Drawing the clefs - only if it is different to the last bar
			if(bar.getClefType() != barClef){
				barClef = bar.getClefType();
				barClef.drawSymbol(g, nextBarXLocation + 20, yLineLocations[barClef.getpitchLineLocation()]);
				spacingModifer += barClef.getSpacingModifier();
			}
			
			//Draw outer rectangle for the bar
			g.drawRect(nextBarXLocation, nextTrackYLocation, extendedBarLength, BAR_HEIGHT);
			//Store this as a Rectangle in the mapper - I didn't really need to do this in the end...
//			int highestPitchLineLocation = yLineLocations[yLineLocations.length - 1];
//			int lowestPitchLineLocation = yLineLocations[0] - highestPitchLineLocation;
//			Rectangle rectangle = new Rectangle(nextBarXLocation, nextTrackYLocation, extendedBarLength, lowestPitchLineLocation);

			//Drawing/mapping notes
			int numberOf32ndNotes = bar.getNumberOf32ndNotes();
			int noteEventSeparation = LENGTH_OF_NOTE_BAR_SECTION/numberOf32ndNotes;
			for(CompositionBarEvent barEvent : bar){
				int xLocation = ((noteEventSeparation * barEvent.getTimeLocation()) + nextBarXLocation + FIRST_BARNOTE_INSET) + spacingModifer;
				int yLocation = yLineLocations[barEvent.getStavePosition()];
				Point barEventLocationPoint = new Point(xLocation, yLocation);
				barEvent.getWrittenNote().drawSymbol(g, barEventLocationPoint);
				
				//Set the point inside the bar event in case it is needed to draw the countperpoint rule report
				barEvent.addPointToRuleReports(barEventLocationPoint);
				
				//if it's an odd number then a line should be drawn in case it is off the staves
				if(barEvent.getStavePosition() % 2 == 0){
					//TODO Should really have lines for all those leading up to, or down to, the stave lines
					g.drawLine(xLocation - 15, yLocation, xLocation + 15, yLocation);
				}
				//Draw tied notes
				if(barEvent.isTied()){
					//LOW Just to show whether a note is tied or not. Will implement this better if i have time...
					g.fillOval(xLocation, yLocation - 50, 20, 10);
				}
				
				//mapping
				aBarEventMappingObject.mapEventPoint(new Point(xLocation, yLocation), track, bar, barEvent, yLineLocations);
			}
			
			
			//There's 3 extra lines drawn within each bar rectangle.
			for (int i = 6; i < 11; i+=2) {
				g.drawLine(nextBarXLocation, yLineLocations[i], nextBarXLocation + extendedBarLength, yLineLocations[i]);
			}
		}
		/*
		 * Set's the dimension for the scroll bars. The nextTrackYLocation is where the next
		 * set of bars would start if there were any. The x value has to be set an extra
		 * bar along.
		 */
		drawingDimension = new Dimension((nextBarXLocation + (BAR_LENGTH * 2)), (int) (nextTrackYLocation + (BAR_HEIGHT * 2)));
	}
	
	public void drawRuleReport(Graphics g, RuleReport ruleReport ){
		g.setColor(Color.RED);
		ruleReport.drawResultMarkings(g);
	}

	public Dimension getDrawingDimension() {
		return drawingDimension;
	}

	public BarEventMappingObject getaBarEventMappingObject() {
		return barEventMappingObject;
	}
}
