package law.counterpoint.evaluation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.evaluation.components.RuleRunner;
import law.counterpoint.exception.InvalidCounterpointSolutionException;
import law.counterpoint.exception.UnsupportedTimeSignatureException;
import law.raw.composition.Composition;
import law.raw.composition.components.trackComponents.Bar;
import law.raw.composition.components.tracks.InstrumentTrack;

public class CounterpointEvaluater {

	public static CounterpointComposition evaluateCounterpointComposition(final CounterpointComposition aCounterpointComposition) {
				
		RuleRunner.runRulesOnCounterpointComposition(aCounterpointComposition, true);
		return aCounterpointComposition;
	}

	public static List<CounterpointComposition> evaluateGeneratedCounterpointSolutions(
			List<CounterpointComposition> generatedCounterpointCompositions, boolean enableMaxNumberOfGeneratedSolutionsLimit){
		ListIterator<CounterpointComposition> listIter = generatedCounterpointCompositions.listIterator();
		while(listIter.hasNext()){
			CounterpointComposition aCounterpointComposition = listIter.next();
			aCounterpointComposition.resetEvaluationScoringObject();
			RuleRunner.runRulesOnCounterpointComposition(aCounterpointComposition, false);
			if(aCounterpointComposition.getEvaluationScoringObject().getOverallScore() > 10000
					&& !enableMaxNumberOfGeneratedSolutionsLimit){
				listIter.remove();
			}
		}
		return generatedCounterpointCompositions;
	}

}
