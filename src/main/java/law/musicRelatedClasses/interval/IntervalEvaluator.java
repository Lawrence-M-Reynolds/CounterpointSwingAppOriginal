package law.musicRelatedClasses.interval;

import java.util.HashMap;
import java.util.Map;

import law.musicRelatedClasses.interval.intervalRelationships.HarmonicIntervalRelationship;


/**
 * Because the definitions of what the different types of consonant are
 * can be disputed they are being defined in this class so that it is
 * easier to redefine them if necessary. 
 * @author BAZ
 *
 */
public class IntervalEvaluator {
	
	private static Map<Interval, IntervalType> INTERVAL_TO_INTERVALTYPE_MAP = new HashMap<Interval, IntervalType>();
	
	static{
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.UNISON, IntervalType.PERFECT_CONSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.MINOR_SECOND, IntervalType.DISSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.MAJOR_SECOND, IntervalType.DISSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.MINOR_THIRD, IntervalType.IMPERFECT_CONSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.MAJOR_THIRD, IntervalType.IMPERFECT_CONSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.FOURTH, IntervalType.PERFECT_CONSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.TRITONE, IntervalType.DISSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.PERFECT_FIFTH, IntervalType.PERFECT_CONSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.MINOR_SIXTH, IntervalType.IMPERFECT_CONSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.MAJOR_SIXTH, IntervalType.IMPERFECT_CONSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.MINOR_SEVENTH, IntervalType.DISSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.MAJOR_SEVENTH, IntervalType.DISSONANT);
		INTERVAL_TO_INTERVALTYPE_MAP.put(Interval.OCTAVE, IntervalType.PERFECT_CONSONANT);
	}

	/**
	 * Determines whether an interval is consonant or dissonant.
	 * @param nextHarmonicAnalysisResult 
	 * @param thisHarmonicAnalysisResult 
	 * @param firstInterval 
	 */
	public static IntervalType determineHarmonicIntervalType(HarmonicIntervalRelationship harmonicRelationship){
		Interval interval = harmonicRelationship.getInterval();
		/*
		 * Fourths are awkward because they are different for the bass
		 */
		if(0 == harmonicRelationship.getLowerSpeciesElement().getPitchSequenceValue() && 
				Interval.FOURTH.equals(interval)){
			return IntervalType.DISSONANT;
		}else{
			return INTERVAL_TO_INTERVALTYPE_MAP.get(interval);
		}
	}
	
	public enum IntervalType{
		PERFECT_CONSONANT,
		IMPERFECT_CONSONANT,
		DISSONANT;
	}
}
