package law.musicRelatedClasses.note;

import java.util.Arrays;


public enum NoteLetter {
	
	C(12, 2),
	D(14, 2),
	E(16, 1),
	F(17, 2),
	G(19, 2),
	A(21, 2),
	B(23, 1);
	
	private int zeroOctaveMidiValue;
	private int intervalToNextNoteLetter;

	/*
	 * Each instance of the note letter is mapped to the midi value of the
	 * zero octave that it occurs in.
	 */
	NoteLetter(final int aZeroOctaveMidiValue, int intervalToNextNoteLetter){
		this.zeroOctaveMidiValue = aZeroOctaveMidiValue;
		this.intervalToNextNoteLetter = intervalToNextNoteLetter;
	} 
	
	/**
	 * Returns the next defined NoteLetter enum value.
	 * @return
	 */
	public NoteLetter getNextNoteLetter(){
		return values()[(ordinal()+1) % values().length];
	}

	/**
	 * 
	 * @param noteLetterOctaveNumber
	 * @return
	 */
	public int getNoteLetterMidiValue(final int noteLetterOctaveNumber){
		return (zeroOctaveMidiValue + (noteLetterOctaveNumber * 12)); 
	}

	public int getFirstMidiValue() {
		return zeroOctaveMidiValue;
	}
	
	public int getIntervalToNextNoteLetter() {
		return intervalToNextNoteLetter;
	}
}
