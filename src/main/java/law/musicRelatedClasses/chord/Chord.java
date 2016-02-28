package law.musicRelatedClasses.chord;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import law.musicRelatedClasses.chord.chordTypes.ChordType;
import law.musicRelatedClasses.key.KeyNote;

public class Chord implements Iterable<KeyNote>, Serializable{
	private ChordType chordType;
	/*
	 * Used to contain all of the notes as well as to find the type of the note
	 * in the chord.
	 */
	private Map<KeyNote, ChordNoteType> chordNotesMap = new HashMap<KeyNote, ChordNoteType>();

	/**
	 * For basic triads
	 * @param chordRootKeyNote
	 * @param third
	 * @param fifth
	 * @param aChordType
	 */
	public Chord(KeyNote chordRootKeyNote, KeyNote third, KeyNote fifth,
			ChordType aChordType) {
		chordType = aChordType;
		chordNotesMap.put(chordRootKeyNote, ChordNoteType.ROOT);
		chordNotesMap.put(third, ChordNoteType.THIRD);
		chordNotesMap.put(fifth, ChordNoteType.FIFTH);
	}

	/**
	 * Iterates through each of the keynotes of the chord.
	 */
	public Iterator<KeyNote> iterator() {
		return chordNotesMap.keySet().iterator();
	}

	//TODO improve this
	@Override
	public String toString() {
		return chordNotesMap.toString();
	}

	
}
