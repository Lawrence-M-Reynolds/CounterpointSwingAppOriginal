package law.musicRelatedClasses.interval.intervalRelationships;

import law.counterpoint.permutation.speciesObjects.SpeciesElement;

public class MelodicIntervalRelationship extends IntervalRelationship{
	
	private SpeciesElement firstSpeciesElement;
	private SpeciesElement secondSpeciesElement;
	private MelodicDirection melodicDirection;
	
	public MelodicIntervalRelationship(SpeciesElement firstSpeciesElement,
			SpeciesElement secondSpeciesElement) {
		super(firstSpeciesElement, secondSpeciesElement);
		this.firstSpeciesElement = firstSpeciesElement;
		this.secondSpeciesElement = secondSpeciesElement;
		if(this.numberOfSemiTones > 0){
			melodicDirection = MelodicDirection.ASCENDING;
		}else if(this.numberOfSemiTones < 0){
			melodicDirection = MelodicDirection.DESCENDING;
		}else{
			melodicDirection = MelodicDirection.STATIONARY;
		}			
	}

	public SpeciesElement getFirstSpeciesElement() {
		return firstSpeciesElement;
	}

	public SpeciesElement getSecondSpeciesElement() {
		return secondSpeciesElement;
	}

	public MelodicDirection getMelodicDirection() {
		return melodicDirection;
	}
	
	public boolean isLeap(){
		//If there's more than two semitones then it must be a leap - not a second.
		return Math.abs(this.numberOfSemiTones) > 2;
	}

	@Override
	public String toString() {
		return "MelodicIntervalRelationship [interval=" + interval
				+ ", numberOfOctaves=" + numberOfOctaves + "]";
	}



	public enum MelodicDirection{
		ASCENDING,
		DESCENDING,
		STATIONARY;
	}
	
}
