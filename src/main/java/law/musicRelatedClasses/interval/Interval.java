package law.musicRelatedClasses.interval;


public enum Interval {
//TODO - I think that the quality of the interval should be a separate enum
	UNISON,
	MINOR_SECOND,
	MAJOR_SECOND,
	MINOR_THIRD,
	MAJOR_THIRD,
	FOURTH,
	TRITONE,
	PERFECT_FIFTH,
	MINOR_SIXTH,
	MAJOR_SIXTH,
	MINOR_SEVENTH,
	MAJOR_SEVENTH,
	OCTAVE;
	
	public int getNumberOfSemitones() {
		return ordinal();
	} 
}

