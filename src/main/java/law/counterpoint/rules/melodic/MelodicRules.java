package law.counterpoint.rules.melodic;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import law.counterpoint.CounterpointOperationsFacade;
import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.rules.Rule;
import law.counterpoint.rules.counterpoint.HarmonicRule;
import law.counterpoint.rules.counterpoint.MotionRule;
import law.counterpoint.rules.melodic.subRules.AllowedIntervalsMelodicSubRule;
import law.counterpoint.rules.melodic.subRules.CompoundMelodicIntervalsSubRule;
import law.counterpoint.rules.melodic.subRules.MelodicSubRule;
import law.counterpoint.rules.reporting.RuleReport;
import law.musicRelatedClasses.interval.intervalRelationships.MelodicIntervalRelationship;
/**
 * This rule command runs many sub task command rules that evaluate all of the different 
 * FIXME I don't want to do it this way now as it's not really worth it. Get rid of this
 * class and make the sub rule work on its own.
 * melodic rules.
 * @author BAZ
 *
 */
public class MelodicRules extends Rule{
	private static Collection<MelodicSubRule> MELODIC_SUB_RULES = new HashSet<MelodicSubRule>();
	
	//LOW - use spring to inject these
	// Static constructor
	static {
		MELODIC_SUB_RULES.add(new AllowedIntervalsMelodicSubRule());
//		MELODIC_SUB_RULES.add(new CompoundMelodicIntervalsSubRule());
	}

	@Override
	public void evaluateCounterpointComposition(
			CounterpointComposition aCounterpointComposition, boolean annotateComposition) {
		
		Map<Integer, Set<MelodicIntervalRelationship>> melodicIntervalsMap = 
				aCounterpointComposition.getTrackNumberToMelodicIntervalRelationshipsMap();
		
		Set<Integer> voiceNumbers = melodicIntervalsMap.keySet();
		for(int voiceNumber : voiceNumbers){
			Set<MelodicIntervalRelationship> voiceMelodicRelationships =
					melodicIntervalsMap.get(voiceNumber);
			for(MelodicIntervalRelationship MelodicIntervalRelationship : voiceMelodicRelationships){				
				for(MelodicSubRule melodicSubRule : MELODIC_SUB_RULES){
					melodicSubRule.evaluateInterval(MelodicIntervalRelationship, aCounterpointComposition, annotateComposition);
				}
			}
		}
	}
}
