package law.musicRelatedClasses;

import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.musicRelatedClasses.interval.intervalRelationships.HarmonicIntervalRelationship;

public enum MotionType{
	PARALLEL,
	OBLIQUE,
	CONTRARY;
	
	public static MotionType determineMotionType(HarmonicIntervalRelationship thisHarmonicRelationship,
			HarmonicIntervalRelationship nextHarmonicRelationship){

		
		SpeciesElement firstLowerSpeciesElement = thisHarmonicRelationship.getLowerSpeciesElement();
		SpeciesElement secondLowerSpeciesElement = nextHarmonicRelationship.getLowerSpeciesElement();
		int lowerVoicePitchLevelChangevalue = firstLowerSpeciesElement.getMidiValue() - secondLowerSpeciesElement.getMidiValue();
		
		SpeciesElement firstHigherSpeciesElement = thisHarmonicRelationship.getHigherSpeciesElement();
		SpeciesElement secondHigherSpeciesElement = nextHarmonicRelationship.getHigherSpeciesElement();
		int higherVoicePitchLevelChangevalue = firstHigherSpeciesElement.getMidiValue() - secondHigherSpeciesElement.getMidiValue();


		if(lowerVoicePitchLevelChangevalue == 0 ||
				higherVoicePitchLevelChangevalue == 0){
			/*
			 * One if the notes hasn't moved so it must be oblique
			 */
			return MotionType.OBLIQUE;
		}else if((lowerVoicePitchLevelChangevalue > 0 && higherVoicePitchLevelChangevalue > 0) ||
				lowerVoicePitchLevelChangevalue < 0 && higherVoicePitchLevelChangevalue < 0){
			return MotionType.PARALLEL;
		}
		return MotionType.CONTRARY;
		

	}
}