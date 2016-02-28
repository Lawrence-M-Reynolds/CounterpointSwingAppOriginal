package law.musicRelatedClasses.interval.intervalRelationships;

import java.util.ArrayList;
import java.util.List;

import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.musicRelatedClasses.interval.IntervalEvaluator;
import law.musicRelatedClasses.interval.IntervalEvaluator.IntervalType;

public class HarmonicIntervalRelationship extends IntervalRelationship{
	
	private final SpeciesElement higherSpeciesElement; 
	private final SpeciesElement lowerSpeciesElement;
	
	public HarmonicIntervalRelationship(SpeciesElement firstSpeciesElement, 
			SpeciesElement secondSpeciesElement){
		super(firstSpeciesElement, secondSpeciesElement);

		
		if(numberOfSemiTones < 0){
			higherSpeciesElement = secondSpeciesElement;
			lowerSpeciesElement = firstSpeciesElement;
			numberOfSemiTones = -(numberOfSemiTones);
		}else{
			higherSpeciesElement = firstSpeciesElement;
			lowerSpeciesElement = secondSpeciesElement;
		}
		
		intervalType = IntervalEvaluator.determineHarmonicIntervalType(this);
	}

	public SpeciesElement getHigherSpeciesElement() {
		return higherSpeciesElement;
	}

	public SpeciesElement getLowerSpeciesElement() {
		return lowerSpeciesElement;
	}

}
