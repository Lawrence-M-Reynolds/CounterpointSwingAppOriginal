package law.raw.composition.components.trackComponents.barComponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import law.musicRelatedClasses.Clef;
import law.musicRelatedClasses.chord.Chord;
import law.musicRelatedClasses.key.Key;
import law.musicRelatedClasses.key.KeyNote;
import law.musicRelatedClasses.note.NoteLetter;
import law.musicRelatedClasses.note.StaveLineNote;

public class PitchLineMidiMapper implements Serializable{
	//Maps the pitch line number to the staveline note
	private Map<Integer, StaveLineNote> staveLineMap = new HashMap<Integer, StaveLineNote>();
	//Maps each key note to a all of the staveline notes it is contained within.
	private Map<KeyNote, Collection<StaveLineNote>> keyNoteToStaveLineNoteMap = 
			new HashMap<KeyNote, Collection<StaveLineNote>>();
	private Clef clef;
	private Key key;
	
	public PitchLineMidiMapper(final Clef aClef, final Key aKey){
		clef = aClef;
		key = aKey;
		
		setNotePitchLineMappings();
	}
	
	public StaveLineNote getStaveLineNoteFromPitchLineLocation(int pitchLineLocation){
		return staveLineMap.get(pitchLineLocation);
	}
	
	/**
	 * FIXME - I think this logic is fixed but need to check the outputs of the notes
	 */	
	private void setNotePitchLineMappings(){
		/*
		 *  get the stave line note letter  of the clef which is worked out from the clef and
		 * key.
		 */
		StaveLineNote staveLineNote = findStaveLineNoteOfLowestPitchLineLocation();
		NoteLetter noteLetter = staveLineNote.getNoteLetter();
		int clefNoteLetterNumber = staveLineNote.getNoteNumber();
		/*
		 * Need to count back from the position of the clef's defining note to the lowest
		 * pitch line number. This 
		 */
		//It's easier to use a list
		List<NoteLetter> list = Arrays.asList(NoteLetter.values());
		for(int stavePitchPosition = 0; stavePitchPosition < 17; stavePitchPosition++){
			KeyNote keyNote = key.getKeyNoteFromNoteLetter(noteLetter);
			staveLineNote.setKeyNote(keyNote);
			mapStaveLineNoteToKeyNote(keyNote, staveLineNote);
			staveLineNote.setStaveLineLocation(stavePitchPosition);
			staveLineMap.put(stavePitchPosition, staveLineNote);
			
			int noteLetterSequenceLocation = list.indexOf(noteLetter);
			if(noteLetterSequenceLocation == 6){
				//The next note will be the next octave of note letters and so the 
				//clef value must be increased.
				clefNoteLetterNumber += 1;
			}
			noteLetter = noteLetter.getNextNoteLetter();
			
			staveLineNote = new StaveLineNote(clefNoteLetterNumber, noteLetter);
		}
	}
	
	/**
	 * When ever a new stave line is created it is added to a collection that's mapped to
	 * its keyNote. This is required for the generator when the allowed keynotes are obtained
	 * and this needs to be translated to the actual notes that can be placed
	 * on the stave.
	 * @param keyNote
	 * @param staveLineNote
	 */
	private void mapStaveLineNoteToKeyNote(KeyNote keyNote, StaveLineNote staveLineNote){
		Collection<StaveLineNote> staveLineNotesOfKeyNote;
		if(!keyNoteToStaveLineNoteMap.containsKey(keyNote)){
			staveLineNotesOfKeyNote = new HashSet<StaveLineNote>();
			keyNoteToStaveLineNoteMap.put(keyNote, staveLineNotesOfKeyNote);
		}else{
			staveLineNotesOfKeyNote = keyNoteToStaveLineNoteMap.get(keyNote);
		}
		staveLineNotesOfKeyNote.add(staveLineNote);
	}
	
	private StaveLineNote findStaveLineNoteOfLowestPitchLineLocation(){
		StaveLineNote staveLineNote = clef.getStaveLineNote();
		int clefNotePitchLineLocation = clef.getpitchLineLocation();
		NoteLetter noteLetter = staveLineNote.getNoteLetter();
		int clefNoteLetterOrdinal = noteLetter.ordinal();
		int clefNoteLetterNumber = staveLineNote.getNoteNumber();
		
		
		int diff = clefNoteLetterOrdinal - clefNotePitchLineLocation;
		NoteLetter lowestNoteLetter = null;
		int lowestNoteNumber = clefNoteLetterNumber;
		boolean noteFound = false;
		while(!noteFound){
			if(diff >= 0){
				lowestNoteLetter = NoteLetter.values()[diff];
				noteFound = true;
			}else{
				//Then we need to start from the highest letter and count down again.
				lowestNoteNumber -= 1;
				diff += 7;
			}
		}
		
		return new StaveLineNote(lowestNoteNumber, lowestNoteLetter);
	}
	
	public Collection<StaveLineNote> getStavlineNotesForChord(Chord chord){
		Collection<StaveLineNote> staveLineNotesForChord = null;
		for(KeyNote chordKeyNote : chord){
			if(staveLineNotesForChord == null){
				staveLineNotesForChord = keyNoteToStaveLineNoteMap.get(chordKeyNote);
			}else{
				staveLineNotesForChord.addAll(keyNoteToStaveLineNoteMap.get(chordKeyNote));
			}			
		}
		return staveLineNotesForChord;
	}
}









