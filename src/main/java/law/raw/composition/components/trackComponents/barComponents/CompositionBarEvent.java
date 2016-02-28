package law.raw.composition.components.trackComponents.barComponents;

import java.awt.Point;
import java.io.Serializable;
import java.util.Collection;

import law.BarEvent;
import law.counterpoint.rules.reporting.CounterpointAnnotationObject;
import law.counterpoint.rules.reporting.RuleReport;
import law.musicRelatedClasses.note.StaveLineNote;
import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;
//LOW - remove stavePosition from classes if possible as this should now only be held in the StaveLineNote
public class CompositionBarEvent  extends BarEvent implements Comparable<CompositionBarEvent>, Serializable{
	private WrittenNote noteValue;
	private StaveLineNote staveLineNote;
	private int stavePosition;
	private int timeLocation;
	private boolean isTied;
	private int trackNumber;
	
	private Collection<CounterpointAnnotationObject> counterpointAnnotationObjects; 
	
	public CompositionBarEvent(final StaveLineNote aStaveLineNote, final int aTimeLocation, 
			final WrittenNote aNoteValue, final int aStavePosition, final boolean isTiedBoolean, int aTrackNumber,
			Collection<CounterpointAnnotationObject> aColOfCounterpointAnnotationObjects){
		timeLocation = aTimeLocation;
		noteValue = aNoteValue;
		stavePosition = aStavePosition;
		isTied = isTiedBoolean;
		staveLineNote = aStaveLineNote;
		trackNumber = aTrackNumber;
		counterpointAnnotationObjects = aColOfCounterpointAnnotationObjects;
	}
	
	public void addPointToRuleReports(Point point){
		if(counterpointAnnotationObjects != null){
			for(CounterpointAnnotationObject counterpointAnnotationObject : counterpointAnnotationObjects){
				counterpointAnnotationObject.addPoint(point);
			}
		}
	}
	
	/**
	 * Works out the midi value by adding the midi value associated with the midi line to the 
	 * modified amount of the accidental of the written note.
	 */
	public int getMidiValue(){
		return (staveLineNote.getMidiValue() + noteValue.getAccidentalMidiModifier());
	}
	
	public WrittenNote getWrittenNote() {
		return noteValue;
	}
	public int getStavePosition() {
		return stavePosition;
	}
	public void setWrittenNote(WrittenNote aNoteValue) {
		this.noteValue = aNoteValue;
	}
	public void setStavePosition(int stavePosition) {
		this.stavePosition = stavePosition;
	}	

	public int compareTo(CompositionBarEvent noteEvent) {
		return this.timeLocation - noteEvent.timeLocation;
	}
	
	public boolean equals(Object object){
		if(!(object instanceof CompositionBarEvent)){
			return false;
		}
		
		CompositionBarEvent aBarEvent = (CompositionBarEvent) object;		
		if(		this.timeLocation != aBarEvent.timeLocation ||
				this.noteValue != aBarEvent.noteValue ||
				this.stavePosition != aBarEvent.stavePosition ||
				this.isTied != aBarEvent.isTied
		){
			return false;
		}
		return true;
	}

	public int getTimeLocation() {
		return timeLocation;
	}

	public boolean isTied() {
		return isTied;
	}

	public void setTied(boolean isTied) {
		this.isTied = isTied;
	}
	
	public String toString(){
		return staveLineNote.toString();
	}
	
	public int getEventLength(){
		return this.noteValue.getNumberof32ndNotes();
	}
	
	public boolean isRest(){
		return this.noteValue.isRest();
	}

	public int getTrackNumber() {
		return trackNumber;
	}

	public StaveLineNote getStaveLineNote() {
		return staveLineNote;
	}
}
