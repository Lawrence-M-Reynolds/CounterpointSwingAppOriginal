package law.counterpoint.counterpointComposition;

import law.BarEvent;
import law.musicRelatedClasses.note.StaveLineNote;
import law.raw.composition.components.trackComponents.barComponents.CompositionBarEvent;
import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;
import law.raw.view.stavePanel.noteSelection.lengthValues.LengthValue;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;

/**
 * Equivalent class to the BarEvent class used for the interface.
 * @author BAZ
 *
 */
public class CounterpointBarEvent extends BarEvent{
	
	private int eventLength;
	//This is not necessarily the same as the barEvent as it may have been separated.
	private boolean tied;
	private int timeLocation;
	private int stavePosition;
	private WrittenNote writtenNote;
	private StaveLineNote staveLineNote;
	private boolean isRest;
	private int midiValue;
	

	/**
	 * This method creates an equivalent CounterpointBarEvent to the passed in
	 * barEvent.
	 * @param barEvent
	 */
	public CounterpointBarEvent(BarEvent aBarEvent) {
		eventLength = aBarEvent.getEventLength();
		tied = aBarEvent.isTied();
		writtenNote = aBarEvent.getWrittenNote();
		isRest = aBarEvent.isRest();
		midiValue = aBarEvent.getMidiValue();
		stavePosition = aBarEvent.getStavePosition();
		staveLineNote = aBarEvent.getStaveLineNote();
		timeLocation = aBarEvent.getTimeLocation();//xxx - test added
	}

	/**
	 * This is constructor is used for the separateCounterpointBarEvent method
	 * where every thing needs to be copied from the existing CounterpointBarEvent
	 * except the length which is also passed through.
	 * @param counterpointBarEvent
	 * @param lengthOfSeparatedEvent
	 */
	private CounterpointBarEvent(CounterpointBarEvent counterpointBarEvent,
			int lengthOfSeparatedEvent, int eventTimeLocation) {
		this(counterpointBarEvent);
		eventLength = lengthOfSeparatedEvent;
		tied = counterpointBarEvent.isTied();
		timeLocation = eventTimeLocation;
	}

	/**
	 * Copy consturctor called by the Species element's copy constructor when the
	 * generator is creating new species elements.
	 * @param counterpointBarEvent
	 * @param newStaveLineNote
	 */
	public CounterpointBarEvent(CounterpointBarEvent counterpointBarEvent,
			StaveLineNote newStaveLineNote) {
		super();
		this.eventLength = counterpointBarEvent.eventLength;
		this.tied = false;//AFTER_GENERATOR - could set the notes that can be tied over
		this.timeLocation = counterpointBarEvent.timeLocation;//Same as the rest
		this.stavePosition = newStaveLineNote.getStaveLineLocation();
		
		//Need to create a new written note object
		LengthValue restLengthValue = counterpointBarEvent.writtenNote.getNoteLengthValue();
		NoteLengthValue noteLengthValue = 
				NoteLengthValue.getNoteLengthValueEquivalentNoteLengthValue(restLengthValue);
		this.writtenNote = new WrittenNote(noteLengthValue, null);//there shouldn't be any accidental 
		
		this.staveLineNote = newStaveLineNote;
		this.isRest = false;
		this.midiValue = newStaveLineNote.getMidiValue();
	}

	/**
	 * Alters this CounterpointBarEvent so that it is equivent to having
	 * this bar Event combined with it. This is used for dealing with 
	 * the tied notes so that they can easily be combined into one.
	 * 
	 * Note: the events are assumed to have the same pitch value. If not, 
	 * the resulting note will have the same pitch value as the original 
	 * CounterpointBarEvent.
	 * @param barEvent
	 */
	public void combineWithEvent(CompositionBarEvent aBarEvent) {
		eventLength += aBarEvent.getEventLength();	
		tied = aBarEvent.isTied();
	}
	
	public int getLinePitchValue(){
		return stavePosition;
	}

	public int getEventLength() {
		return eventLength;
	}
	
	public WrittenNote getWrittenNote(){
		return writtenNote;
	}

	public int getTimeLocation() {
		return timeLocation;
	}

	/**
	This method returns the second half that was cut off and the 
	counterpointBarEvent that is performing the operation should modify itself
	accordingly. It should also set its tied status to true.
	*/
	public CounterpointBarEvent separateCounterpointBarEvent(int legnthToCutBy) {
		if(legnthToCutBy > eventLength){
			throw new RuntimeException(){
				public String toString(){
					return "CounterpointBarEvent.separateCounterpointBarEvent() was called where" +
							"the length to cut by was longer than the note itself.";
				}
			};
		}
		int lengthOfSeparatedEvent = (eventLength - legnthToCutBy);
		eventLength = legnthToCutBy;
		//Starting location of separated event
		int timeLocationForSeparatedEvent = timeLocation + legnthToCutBy;
		CounterpointBarEvent removedCounterpointBarEvent = 
				new CounterpointBarEvent(this, lengthOfSeparatedEvent, timeLocationForSeparatedEvent); 
		/*Set this one to being tied so that it knows when it comes to writing these
		notes back.
		*/
		tied = true;
		return removedCounterpointBarEvent;
	}
	

	public boolean isRest(){
		return isRest;
	}
	
	public int getMidiValue(){
		return midiValue;
	}

	public boolean isTied() {
		return tied;
	}

	@Override
	public int getStavePosition() {
		return stavePosition;
	}

	@Override
	public StaveLineNote getStaveLineNote() {
		return staveLineNote;
	}
}
