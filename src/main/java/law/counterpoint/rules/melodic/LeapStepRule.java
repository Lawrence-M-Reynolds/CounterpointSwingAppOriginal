package law.counterpoint.rules.melodic;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.rules.Rule;
import law.counterpoint.rules.reporting.EvaluationScoringObject;
import law.musicRelatedClasses.interval.intervalRelationships.MelodicIntervalRelationship;
import law.musicRelatedClasses.interval.intervalRelationships.MelodicIntervalRelationship.MelodicDirection;
/**
 * After a leap the melody should ideally move in step wise motion in the
 * oppposite direction.
 * @author BAZ
 *
 */
public class LeapStepRule extends Rule{
	private static String RULE_MESSAGE = "After a leap the melody should move stepwise in the opposite direction.";
	private static int PENALTY_VALUE = 1000;
	
	@Override
	public void evaluateCounterpointComposition(
			CounterpointComposition aCounterpointComposition,
			boolean annotateComposition) {
		
		EvaluationScoringObject evaluationScoringObject = aCounterpointComposition.getEvaluationScoringObject();
		Map<Integer, Set<MelodicIntervalRelationship>> melodicIntervalsMap = 
				aCounterpointComposition.getTrackNumberToMelodicIntervalRelationshipsMap();
		
		Set<Integer> voiceNumbers = melodicIntervalsMap.keySet();
		for(int voiceNumber : voiceNumbers){
			Set<MelodicIntervalRelationship> voiceMelodicRelationships =
					melodicIntervalsMap.get(voiceNumber);
			
			Iterator<MelodicIntervalRelationship> iter = voiceMelodicRelationships.iterator();
			while(iter.hasNext()){
				MelodicIntervalRelationship melodicIntervalRelationship = iter.next();
				if(melodicIntervalRelationship.isLeap()){
					if(iter.hasNext()){
						//Check that the next one is moving stepwise in the opposite direction.
						MelodicDirection thisMelodicDirection = 
								melodicIntervalRelationship.getMelodicDirection();
						
						MelodicIntervalRelationship nextMelodicIntervalRelationship = iter.next();
						MelodicDirection nextMelodicDirection = 
								melodicIntervalRelationship.getMelodicDirection();
										
						//Check if it's moving in the same direction or stationary or if it's a leap.
						if(thisMelodicDirection.equals(nextMelodicDirection)
								|| MelodicDirection.STATIONARY.equals(nextMelodicDirection)
										|| nextMelodicIntervalRelationship.isLeap()){
							if(annotateComposition){
								//FIXME implement rule report
							}else{
								evaluationScoringObject.addToScore(PENALTY_VALUE);
							}
						}
					}
				}
			}
		}
		
	}

}
