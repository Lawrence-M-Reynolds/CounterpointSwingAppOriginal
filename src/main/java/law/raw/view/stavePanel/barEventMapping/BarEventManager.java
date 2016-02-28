package law.raw.view.stavePanel.barEventMapping;


import law.raw.composition.components.trackComponents.Bar;
import law.raw.composition.components.trackComponents.barComponents.CompositionBarEvent;
import law.raw.composition.components.tracks.InstrumentTrack;
import law.raw.view.stavePanel.noteSelection.CompositeNoteValue;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;
import law.raw.view.stavePanel.noteSelection.lengthValues.RestLengthValue;
/**
 * This class manages how the CompositionBarEvent will change when those in a bar are modified.
 * It also has the logic to determine what rests should be created when a bar is first created.
 * If the CompositionBarEvent is goes beyond the bar that it was written into, the one that
 * was passed in, then this method calls itself with the next bar obtained from the track. 
 * @author BAZ
 *
 */
public class BarEventManager {

	public static void sortBarEvents(final InstrumentTrack track, final Bar bar, final CompositionBarEvent aBarEvent, 
			final CompositeNoteValue aCompositeNoteValue, final int pitchLineValue){
		int numOf32ndBeats = bar.getNumberOf32ndNotes();
		int barEventTimeLocation = aBarEvent.getTimeLocation();
		int noteLength = aCompositeNoteValue.getNumberof32ndNotes();
		int timeLeftInBar = numOf32ndBeats - barEventTimeLocation;
		int delta = timeLeftInBar - noteLength;
		
		if(delta == 0){
			//The note exactly matches the bar length so there's no need to do anything except write 
			//the note as no rests need to be created.
			bar.writeCompositeNoteValue(barEventTimeLocation, aCompositeNoteValue, pitchLineValue, false,
					null);
			
		}else if(delta < 0){
			/*
			 * The note is larger than the bar so:
			 * work out how much should be written here and then write it
			 * it should be drawn tied over to the next bar and then the 
			 * algorithm should be run again with what's left to check if rests will need to be 
			 * created in the next bar.
			 */
            bar.writeCompositeNoteValue(barEventTimeLocation, 
            		CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(
            				aCompositeNoteValue, timeLeftInBar, NoteLengthValue.values()), pitchLineValue, true, null);

			Bar nextBar = track.getNextBar(bar);	
			//Need a new "NoteEvent" of total value of delta. Problem is that this may have to be made
			//up of more than one note. So I've created a composite Note 
			CompositeNoteValue compositeNote = CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(
					aCompositeNoteValue, -delta, NoteLengthValue.values());
			sortBarEvents(track, nextBar, nextBar.getFirstBarEvent(), compositeNote, pitchLineValue);
			
		}else if(delta > 0){
            //The note is smaller than the bar length so the note must be drawn and any notes/rests 
			//that it overwrites should be deleted and then any resulting 32nd units should be broken 
			//down into as few rests as possible.
			bar.writeCompositeNoteValue(barEventTimeLocation, aCompositeNoteValue, pitchLineValue, false, null);
			//Make a composite rest of what's left in the bar up to the next event that wasn't over written.
			int restTimeLocation = barEventTimeLocation + aCompositeNoteValue.getNumberof32ndNotes();
			int timeLengthOfRests = bar.getTimeLengthUntilToNextEvent(restTimeLocation, delta);
			CompositeNoteValue compositeRest = CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(
					new CompositeNoteValue(null), timeLengthOfRests, RestLengthValue.values());
			if(compositeRest != null){
				bar.writeCompositeNoteValue(restTimeLocation, compositeRest, RestLengthValue.REST_PITCH_LINE_VALUE, false, null);
			}
		}
	}
}
