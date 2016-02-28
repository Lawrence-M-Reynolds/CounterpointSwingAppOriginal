package law.musicRelatedClasses.key;

import law.musicRelatedClasses.interval.Interval;

public enum MusicScale {
	//Diatonic major scale has the pattern TTSTTTS
	Diatonic_Major(new Interval[] {
			Interval.MAJOR_SECOND,
			Interval.MAJOR_SECOND,
			Interval.MINOR_SECOND,
			Interval.MAJOR_SECOND,
			Interval.MAJOR_SECOND,
			Interval.MAJOR_SECOND,
			Interval.MINOR_SECOND,
	});
	
	private Interval[] scaleIntervalSequence;
	
	private MusicScale(final Interval[] aScaleIntervalSequence){
		scaleIntervalSequence = aScaleIntervalSequence;
	}
	
	public Interval[] getIntervalArray(){
		return scaleIntervalSequence;
	}
	
	/**
	 * To String returns the quality scale. This is determined by counting the
	 * number of semitones between the first and third notes.
	 * If the interval between the first and fifth is not a perfect 5th (7
	 * semitones) but a diminished one (6 semitones) then it is 
	 */
	public String toString(){
		int semitonesBetweenTonicAndMediant = scaleIntervalSequence[0].getNumberOfSemitones() +
				scaleIntervalSequence[1].getNumberOfSemitones();
		if(semitonesBetweenTonicAndMediant == 3){
			return "Minor";
		}else{
			return "Major";
		}
	}
	
	/**
	 * Each of the 7 notes in a scale and the chords that 
	 * are built upon them have a certain function in the key. 
	 * This is called a degree and is represented in this enum 
	 * which is associated with each of the key notes and also
	 * with the key chords. 
	 *  
	 * This has been implemented as an inner class to the scale
	 * as it is so closely related to it.
	 * @author BAZ
	 *
	 */
	public enum ScaleDegree {
		TONIC,
		SUPERTONIC,
		MEDIANT,
		SUBDOMINANT,
		DOMINANT,
		SUBMEDIANT,
		SUBTONIC;
		
		public static ScaleDegree getScaleDegree(int degreeNumber){
			return values()[degreeNumber];
		}
		
		public int getScaleDegreeNumber(){
			return (ordinal() + 1);
		}
	}
}
