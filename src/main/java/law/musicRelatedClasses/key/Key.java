package law.musicRelatedClasses.key;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import law.musicRelatedClasses.chord.HarmonyEvaluator;
import law.musicRelatedClasses.interval.Interval;
import law.musicRelatedClasses.key.MusicScale.ScaleDegree;
import law.musicRelatedClasses.note.Note;
import law.musicRelatedClasses.note.NoteLetter;
import law.raw.view.stavePanel.noteSelection.enumeration.Accidental;

public class Key implements Serializable{
	private MusicScale scale;
	private Note keyTonicNote;
	/*
	 * noteLetterAccidentalMap is a one:one map relating each letter A-G to what it's 
	 * properties are in the key. This includes whether it is sharpened or flatened 
	 * and also the degree/function of the note in the scale. 
	 */
	private Map<NoteLetter, KeyNote> noteLetterKeyNoteMap = new HashMap<NoteLetter, KeyNote>();
	// This map relates all key notes to a collection of keychords that contain it.	 

	public Key(MusicScale scale, Note keyTonicNote) {
		super();
		this.scale = scale;
		this.keyTonicNote = keyTonicNote;
		
		populateAccidentalsMap();
		setKeyNoteHarmonics();
	}
	
	/**
	 * This method runs at creation and populates the note letter accidentals map
	 * which will show which letters should have sharps or flats.
	 */
	private void populateAccidentalsMap(){
		//Making this final as it should only have one type of accidental.
		/*
		 * Creating a list of all note letters with the letter of the key being the first. This
		 * will allow the pattern to be applied straight to it.
		 */
		List<NoteLetter> noteLettersList = Arrays.asList(NoteLetter.values());
		int indexOfKeyNoteLetter = noteLettersList.indexOf(keyTonicNote.getNoteLetter());
		/*
		 * The abstract list returned from the subList method below had to be converted to an
		 * ArrayList in order for the addAll method to work.
		 */
		List<NoteLetter> rearrangedNoteLettersList = 
				new ArrayList<NoteLetter>(noteLettersList.subList(indexOfKeyNoteLetter, noteLettersList.size()));
		rearrangedNoteLettersList.addAll(noteLettersList.subList(0, indexOfKeyNoteLetter));
		Accidental accidental = keyTonicNote.getAccidental();
		for(int scaleDegree = 0; scaleDegree < 7; scaleDegree++){
			NoteLetter noteLetter = rearrangedNoteLettersList.get(scaleDegree);
			
			KeyNote thisKeyNote = new KeyNote(noteLetter, accidental, ScaleDegree.getScaleDegree(scaleDegree));
			noteLetterKeyNoteMap.put(noteLetter, thisKeyNote);
//FIXME G# failed on this			
			int intervalFromThisNoteLetterToNextNoteLetter = noteLetter.getIntervalToNextNoteLetter();
			int thisNoteAccidentalModifier = (accidental == null ? 0 : accidental.getIntervalChangeValue());
			
			int intervalFromThisNoteToNextNoteLetter = 
					intervalFromThisNoteLetterToNextNoteLetter - (thisNoteAccidentalModifier);
			
			Interval intervalToNextNote = MusicScale.Diatonic_Major.getIntervalArray()[scaleDegree];
			
			int numberOfSemitonesToNextKeyNote = intervalToNextNote.getNumberOfSemitones();
			
			/*
			If this is -1 then we need then we need to sharpen the next note letter, if it's +1 then
			it needs to be flattened and if it's 0 then it doesn't need either.
			*/
			int requiredSemitoneModifierOfNextNote = numberOfSemitonesToNextKeyNote - intervalFromThisNoteToNextNoteLetter;

			accidental = Accidental.getAccidentalForSemitoneModifier(requiredSemitoneModifierOfNextNote);

		}
	}
	
	/**
	 * After all of the keyNotes have been created this method generates all of the KeyChords
	 * and puts them in the map.
	 */
	private void setKeyNoteHarmonics(){
		//The key notes need to be in the order of their degree number so sort them first
		List<KeyNote> sortedKeyNoteList = new ArrayList<KeyNote>(noteLetterKeyNoteMap.values());
		Collections.sort(sortedKeyNoteList);
		HarmonyEvaluator.evaluateKeyNoteHarmonics(sortedKeyNoteList);
	}
	
	/**
	 * This method the KeyNote from the note letter. This specifies more
	 * details including the sharp or flat that may be associated with the
	 * key.
	 * @param noteLetter
	 * @return
	 */
	public KeyNote getKeyNoteFromNoteLetter(NoteLetter noteLetter){
		return noteLetterKeyNoteMap.get(noteLetter);
	}

	public String toString(){
		return (keyTonicNote.toString() + scale.toString());
	}

	/**
	 * Returns a list of all of the KeyNotes for this key sorted by there
	 * scale degree number.
	 * @return
	 */
	public List<KeyNote> getKeyNotes(){
		List<KeyNote> keyNoteList = new ArrayList<KeyNote>(noteLetterKeyNoteMap.values());
		Collections.sort(keyNoteList);
		return keyNoteList;
	}

}











