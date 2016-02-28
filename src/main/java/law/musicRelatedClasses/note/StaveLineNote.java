package law.musicRelatedClasses.note;

import java.io.Serializable;

import law.musicRelatedClasses.key.KeyNote;
import law.raw.view.stavePanel.noteSelection.enumeration.Accidental;

/**
 * This represents the note at each bar line/space. If the key has sharps or flats
 * then these are set via the setter.
 * This extends Note and is a more specific type as it also defines where it is located
 * on the stave in the bar, the note sequence number and it's midi value.
 * @author BAZ
 *
 */
public class StaveLineNote extends Note implements Serializable{
	private int staveLineLocation;
	private int noteNumber;
	private int midiValue;
	private KeyNote keyNote;
	//LOW rather than extending note it may be better for it to just encapsulate one.
	
	public StaveLineNote(final int aNoteNumber, final NoteLetter aNoteLetter){
		super(aNoteLetter, null);
		noteNumber = aNoteNumber;
		/*
		 * TODO This isn't so good - the multiplication shouldn't be done in the letter
		 * and the method should be more descriptive.
		 */
		
		midiValue = aNoteLetter.getNoteLetterMidiValue(aNoteNumber);
	}
	
	@Override
	public String toString(){
		return (Integer.toString(noteNumber) + super.toString());
	}

	public int getMidiValue() {
		return midiValue;
	}

	public int getNoteNumber() {
		return noteNumber;
	}

	public int getStaveLineLocation() {
		return staveLineLocation;
	}

	public void setStaveLineLocation(int staveLineLocation) {
		this.staveLineLocation = staveLineLocation;
	}

	public void setKeyNote(KeyNote keyNote) {
		this.keyNote = keyNote;
	}

	public KeyNote getKeyNote() {
		return keyNote;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StaveLineNote)){
			return false;
		}
		StaveLineNote otherStaveLineNote = (StaveLineNote)obj;
		if(this.accidental != otherStaveLineNote.accidental
				|| this.keyNote != otherStaveLineNote.keyNote
				|| this.midiValue != otherStaveLineNote.midiValue
				|| this.noteLetter != otherStaveLineNote.noteLetter
				|| this.noteNumber != otherStaveLineNote.noteNumber
				|| this.staveLineLocation != otherStaveLineNote.staveLineLocation){
			
		}
		return super.equals(obj);
	}
	
}
