package law.raw.view.stavePanel.noteSelection.enumeration;
/**
 * This is what is selected in the note selector. It can have all the properties
 * that are selectable.
 */
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import law.raw.view.stavePanel.noteSelection.lengthValues.LengthValue;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;
public class WrittenNote implements Comparable<WrittenNote>, Serializable{
	private LengthValue noteLengthValue;
	private Accidental accidental;
	
	/**
	 * This constructor is used in the algorithm for creating a composite note value
	 * where the attributes of the first one should be carried over to the rest.
	 * @param noteValue
	 * @param lengthValue
	 */
	public WrittenNote(final WrittenNote noteValue, final LengthValue lengthValue){
		this.noteLengthValue = lengthValue;
		if(noteValue != null){
			this.accidental = noteValue.getAccidental();
		}
	}
	
	public WrittenNote(final LengthValue lengthValue, final Accidental anAccidental){
		this.noteLengthValue = lengthValue;
		this.accidental = anAccidental;
	}

	public void drawSymbol(final Graphics g, final Point point){
		noteLengthValue.drawSymbol(g, point);
		if(accidental != null){
			accidental.drawSymbol(g, point);
		}
	}
	
	public boolean isRest(){
		return noteLengthValue.isRest();
	}

	public int getNumberof32ndNotes(){
		return noteLengthValue.getNumberof32ndNotes();
	}
	
	public LengthValue getNoteLengthValue(){
		return noteLengthValue;
	}

	/**
	 * Comparable is implemented here so that when a composite note is drawn
	 * (or composite rests) the smaller ones will be placed first and the 
	 * larger ones afterwards.
	 */
	public int compareTo(WrittenNote aNoteValueToCompare) {
		return this.getNoteLengthValue().getNumberof32ndNotes() - 
				aNoteValueToCompare.getNoteLengthValue().getNumberof32ndNotes();
	}

	public Accidental getAccidental() {
		return accidental;
	}
	
	public int getAccidentalMidiModifier(){
		if(accidental != null){
			return accidental.getIntervalChangeValue();
		}
		return 0;
	}
}
