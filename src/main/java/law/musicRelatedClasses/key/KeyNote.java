package law.musicRelatedClasses.key;

import java.io.Serializable;
import java.util.Map;

import law.musicRelatedClasses.chord.Chord;
import law.musicRelatedClasses.chord.chordTypes.ChordType;
import law.musicRelatedClasses.key.MusicScale.ScaleDegree;
import law.musicRelatedClasses.note.Note;
import law.musicRelatedClasses.note.NoteLetter;
import law.raw.view.stavePanel.noteSelection.enumeration.Accidental;

/**
 * A key note represents a note that is in the key - a diatonic
 * note. This has a key function enum associated with it.
 * @author BAZ
 *
 */
public class KeyNote extends Note implements Comparable<KeyNote>, Serializable{
	private ScaleDegree scaleDegree;
	private Map<ScaleDegree, KeyNote> keyNoteHarmonics;

	public KeyNote(NoteLetter noteLetter, Accidental accidental, ScaleDegree scaleDegree){
		super(noteLetter, accidental);
		this.scaleDegree = scaleDegree;
	}

	public int compareTo(KeyNote keyNote) {
		return this.scaleDegree.getScaleDegreeNumber() - keyNote.getScaleDegree().getScaleDegreeNumber();
	}

	public ScaleDegree getScaleDegree() {
		return scaleDegree;
	}

	/**
	 * Returns the harmonic keyNote based on the ScaleDegree value which is in
	 * relation to this note. This different to the scale degree of the key.
	 * For example, this keynote may be anyother scale degree of the key
	 * but with regards to it's harmonics it is treated as the tonic and that
	 * is how it is mapped.
	 * @param aScaleDegree
	 * @return
	 */
	public KeyNote getKeyNoteHarmonic(ScaleDegree aScaleDegree) {
		return keyNoteHarmonics.get(aScaleDegree);
	}

	public void setKeyNoteHarmonics(Map<ScaleDegree, KeyNote> keyNoteHarmonics) {
		this.keyNoteHarmonics = keyNoteHarmonics;
	}

	/**
	 * Only creates basic triads at the moment.
	 * @param chordType
	 * @param chordRootKeyNote
	 * @return
	 */
	public Chord buildKeyChord(ChordType chordType) {
		KeyNote third = this.getKeyNoteHarmonic(ScaleDegree.MEDIANT);
		KeyNote fifth = this.getKeyNoteHarmonic(ScaleDegree.DOMINANT);
		
		return new Chord(this, third, fifth, chordType);
			
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof KeyNote)){
			return false;
		}
		KeyNote keyNote = (KeyNote)obj;
		if(this.accidental != keyNote.accidental
				|| this.noteLetter != keyNote.noteLetter
				|| this.scaleDegree != keyNote.scaleDegree){
			return false;
		}		
		return super.equals(obj);
	}


	
}
