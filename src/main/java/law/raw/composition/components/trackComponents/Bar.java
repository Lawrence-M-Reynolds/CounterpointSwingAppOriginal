package law.raw.composition.components.trackComponents;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;

import law.counterpoint.rules.reporting.CounterpointAnnotationObject;
import law.counterpoint.rules.reporting.RuleReport;
import law.musicRelatedClasses.Clef;
import law.musicRelatedClasses.key.Key;
import law.musicRelatedClasses.key.MusicScale;
import law.musicRelatedClasses.note.StaveLineNote;
import law.musicRelatedClasses.time.Tempo;
import law.musicRelatedClasses.time.TimeSignature;
import law.raw.composition.components.trackComponents.barComponents.CompositionBarEvent;
import law.raw.composition.components.trackComponents.barComponents.PitchLineMidiMapper;
import law.raw.view.stavePanel.noteSelection.CompositeNoteValue;
import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;
import law.raw.view.stavePanel.noteSelection.lengthValues.RestLengthValue;

public class Bar implements Comparable<Bar>, Iterable<CompositionBarEvent>, Serializable{
	//Track Events
	private Clef clefType;
	private TimeSignature timeSignature;
	private Key key;
	private Tempo tempo;
	private PitchLineMidiMapper pitchLineMidiMapper;
	private int barNumber;
	private int trackNumber;
	
	
	protected List<CompositionBarEvent> noteEvents = new ArrayList<CompositionBarEvent>();
	
	public Bar(final Clef aClefType, final TimeSignature aTimeSignature, 
			final Key aKey, final Tempo aTempo, final int aBarNumber, final int aTrackNumber){
		clefType = aClefType;
		timeSignature = aTimeSignature;
		key = aKey;
		tempo = aTempo;
		barNumber = aBarNumber;
		pitchLineMidiMapper = new PitchLineMidiMapper(aClefType, aKey);
		trackNumber = aTrackNumber;
		//Default rest(s) should be added to every new bar based on the time signature
		this.writeCompositeNoteValue(0, CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(new CompositeNoteValue(null),
				timeSignature.getNumberOf32ndNotesPerBar(), RestLengthValue.values()), RestLengthValue.REST_PITCH_LINE_VALUE, false, null);
	}

	public int getBarNumber() {
		return barNumber;
	}

	public void setBarNumber(int barNumber) {
		this.barNumber = barNumber;
	}

	public Clef getClefType() {
		return clefType;
	}

	public void setClefType(Clef clefType) {
		this.clefType = clefType;
	}
	public int compareTo(Bar aBar) {
		return (this.getBarNumber() - aBar.getBarNumber());
	}

	public TimeSignature getTimeSignature() {
		return timeSignature;
	}

	public Key getKey() {
		return key;
	}

	public Tempo getTempo() {
		return tempo;
	}

	public void setNoteEvents(List<CompositionBarEvent> noteEvents) {
		this.noteEvents = noteEvents;
	}

	public Iterator<CompositionBarEvent> iterator() {
		return noteEvents.iterator();
	}
	
	public CompositionBarEvent getFirstBarEvent(){
		return noteEvents.get(0);
	}
	
	public int getNumberOf32ndNotes(){
		return timeSignature.getNumberOf32ndNotesPerBar();
	}
	
	public int getNumberOfEventsInBar(){
		return noteEvents.size();
	}
	
	public void writeCompositeNoteValue(final int barEventTimeLocation, final CompositeNoteValue aCompositeNoteValue, 
			final int pitchLineValue, final boolean isTied,
			Collection<CounterpointAnnotationObject> aColOfCounterpointAnnotationObjects){
		//First delete the notes that will be overwritten
		deleteOverWrittenBarEvents(barEventTimeLocation, barEventTimeLocation + aCompositeNoteValue.getNumberof32ndNotes());
		int newEventTimeLocation = barEventTimeLocation;
		Iterator<WrittenNote> compositeNoteValueIterator = aCompositeNoteValue.iterator();	
		while(compositeNoteValueIterator.hasNext()){
			WrittenNote noteValue = compositeNoteValueIterator.next();
			
			boolean tieNote = ((noteValue.getNoteLengthValue() instanceof NoteLengthValue) && (isTied || compositeNoteValueIterator.hasNext()));
			StaveLineNote barEventStaveLineNote = pitchLineMidiMapper.getStaveLineNoteFromPitchLineLocation(pitchLineValue);
			noteEvents.add(new CompositionBarEvent(barEventStaveLineNote, newEventTimeLocation, noteValue, pitchLineValue, tieNote, 
					trackNumber, aColOfCounterpointAnnotationObjects));
			newEventTimeLocation += noteValue.getNumberof32ndNotes();
		}
	}
	
	/**
	 * This method deletes methods from the start time to the end time.
	 * @param barEventStartTime
	 * @param barEventEndTime
	 */
	private void deleteOverWrittenBarEvents(final int barEventStartTime, final int barEventEndTime){
		/*
		 * Gets the noteEventsCollection into array and then clears it. They are only added back from
		 * the array if they don't occur between the end and start time.
		 */
		Object[] noteEventsArray = noteEvents.toArray();
		noteEvents.clear();
		for(int noteEventNum = 0; noteEventNum < noteEventsArray.length; noteEventNum++){
			CompositionBarEvent barEvent = (CompositionBarEvent)noteEventsArray[noteEventNum];
			if(!(barEvent.getTimeLocation() >= barEventStartTime && barEvent.getTimeLocation() < barEventEndTime)){
				noteEvents.add(barEvent);
			}
		}	
	}

	public List<CompositionBarEvent> getNoteEvents() {
		return noteEvents;
	}

	public int getTimeLengthUntilToNextEvent(int barEventTimeLocation, int delta) {
		int nextBarEventTimeLocation = 0;
		ListIterator<CompositionBarEvent> reverseIterator = noteEvents.listIterator(noteEvents.size());
		while(reverseIterator.hasPrevious()){
			CompositionBarEvent barEvent = reverseIterator.previous();
			if(barEvent.getTimeLocation() < barEventTimeLocation){
				break;
			}
			nextBarEventTimeLocation = barEvent.getTimeLocation();
		}
		
		if(nextBarEventTimeLocation == 0){
			return delta;
		}
		
		return nextBarEventTimeLocation - barEventTimeLocation;
	}

	public int getTrackNumber() {
		return trackNumber;
	}

	public PitchLineMidiMapper getPitchLineMidiMapper() {
		return pitchLineMidiMapper;
	}
}












