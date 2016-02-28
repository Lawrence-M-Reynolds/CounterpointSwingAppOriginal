package law.counterpoint.rules.melodic.subRules;

import java.util.HashSet;
import java.util.Set;

import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.rules.reporting.RuleReport;
import law.musicRelatedClasses.interval.Interval;
import law.musicRelatedClasses.interval.intervalRelationships.MelodicIntervalRelationship;

public abstract class MelodicSubRule {
	
	public abstract void evaluateInterval(MelodicIntervalRelationship melodicIntervalRelationship, 
			CounterpointComposition aCounterpointComposition, boolean annotateComposition);


}
