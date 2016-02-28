package law.counterpoint.evaluation.components;

import java.util.Collection;
import java.util.HashSet;

import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.rules.Rule;
import law.counterpoint.rules.counterpoint.HarmonicRule;
import law.counterpoint.rules.counterpoint.MotionRule;
import law.counterpoint.rules.melodic.LeapStepRule;
import law.counterpoint.rules.melodic.MelodicRules;
import law.counterpoint.rules.melodic.RepeatingNotesRule;
/**
 * Uses the command design pattern to run all of the rules against a 
 * composition.
 * @author BAZ
 *
 */
public class RuleRunner {
	private static Collection<Rule> RULES = new HashSet<Rule>();
	
	//LOW - use spring to inject these
	// Static constructor
	static {
		RULES.add(new MotionRule());
		RULES.add(new HarmonicRule());
		RULES.add(new MelodicRules());
		RULES.add(new LeapStepRule());
		RULES.add(new RepeatingNotesRule());
	}

	public static CounterpointComposition runRulesOnCounterpointComposition(
			CounterpointComposition aCounterpointComposition, boolean annotateComposition){
		
		aCounterpointComposition.performPreRuleEvaluationAnalysis();
		
		for(Rule rule : RULES){
			rule.evaluateCounterpointComposition(aCounterpointComposition, annotateComposition);
		}
		return aCounterpointComposition;
	}
}
