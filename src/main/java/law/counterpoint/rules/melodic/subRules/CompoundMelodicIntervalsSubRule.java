package law.counterpoint.rules.melodic.subRules;

import java.util.ArrayList;
import java.util.List;

import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.rules.reporting.RuleReport;
import law.musicRelatedClasses.interval.Interval;
import law.musicRelatedClasses.interval.intervalRelationships.MelodicIntervalRelationship;
import law.musicRelatedClasses.interval.intervalRelationships.MelodicIntervalRelationship.MelodicDirection;

public class CompoundMelodicIntervalsSubRule extends MelodicSubRule{

	private MelodicDirection melodicDirectionOfLastInterval;
	private Interval interval;
	private int trackNumberOfLastInterval;
	private List<MelodicIntervalRelationship> compoundMelodicRelationships = new ArrayList<MelodicIntervalRelationship>();
	/*
	 * FIXME need to pass in the iterator as well so that the next one can be looked at
	 * The problem here is that it waits to get to the next interval to look at last one
	 * but what if we get to the very last one? It wouldn't do anything if it's a
	 * compound interval because it wouldn't look back on it.
	 * (non-Javadoc)
	 * @see law.counterpoint.rules.melodic.subRules.MelodicSubRule#evaluateInterval(law.musicRelatedClasses.interval.intervalRelationships.MelodicIntervalRelationship)
	 */
	@Override
	public void evaluateInterval(
			MelodicIntervalRelationship melodicIntervalRelationship, 
			CounterpointComposition aCounterpointComposition, boolean annotateComposition) {
		
		int thisTrackNumber = melodicIntervalRelationship.getSecondSpeciesElement().getTrackNumber();
		if(thisTrackNumber != trackNumberOfLastInterval){
			//TODO If the this list has more than one evaluate teh last interval
		//This is uneccsary as i'm passing thme in one voice at a time xxx	
			melodicDirectionOfLastInterval = null;
			trackNumberOfLastInterval = thisTrackNumber;
		}
		
		if(!melodicIntervalRelationship.getMelodicDirection().equals(melodicDirectionOfLastInterval)){
			melodicDirectionOfLastInterval = melodicIntervalRelationship.getMelodicDirection();
			interval = melodicIntervalRelationship.getInterval();
		}else{
			//There is a compound interval formed
			
		}


		
		
	}

}
