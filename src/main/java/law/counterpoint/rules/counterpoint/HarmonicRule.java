package law.counterpoint.rules.counterpoint;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.permutation.BarNotePermutation;
import law.counterpoint.permutation.analysisObjects.harmonicAnalysis.HarmonicAnalysisPairingKey;
import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.counterpoint.rules.Rule;
import law.counterpoint.rules.reporting.CounterpointAnnotationObject;
import law.counterpoint.rules.reporting.EvaluationScoringObject;
import law.counterpoint.rules.reporting.RuleReport;
import law.musicRelatedClasses.interval.Interval;
import law.musicRelatedClasses.interval.IntervalEvaluator.IntervalType;
import law.musicRelatedClasses.interval.intervalRelationships.HarmonicIntervalRelationship;

public class HarmonicRule extends Rule{

	private static String RULE_MESSAGE_FOR_DISSONANCE = "Dissonant intervals are not allowed in the first species";
	private static int PENALTY_VALUE_FOR_DISSONANCE = 1000;
	
	private static String RULE_MESSAGE_FOR_EXCESS_OCTAVES = "There is an excess of octave/unison intervals.";
	private static int PENALTY_VALUE_FOR_EXCESS_OCTAVES = 500;
	
	@Override
	public void evaluateCounterpointComposition(
			CounterpointComposition aCounterpointComposition, boolean annotateComposition) {
		
		ListIterator<BarNotePermutation> iter = aCounterpointComposition.getPermutationListIterator(0);
		EvaluationScoringObject evaluationScoringObject = aCounterpointComposition.getEvaluationScoringObject();
		
		int numberOfVoices = aCounterpointComposition.getNumberOfVoicesInComposition();
		int numberOfNotesInChord = 3; //TODO get this from the chord of the permutation
		//This rounds up the division
		int idealUnisonIntervalCount = (numberOfVoices - numberOfNotesInChord - 1)/numberOfNotesInChord;
		
		while(iter.hasNext()){
			BarNotePermutation thisBarNotePermutation = iter.next();
			
			Map<HarmonicAnalysisPairingKey, HarmonicIntervalRelationship> harmonicAnalysisResultsMap = 
					thisBarNotePermutation.getHarmonicAnalysisResultsMap();
			
			Collection<HarmonicIntervalRelationship> harmonicRelationships = 
					harmonicAnalysisResultsMap.values();
			
			List<HarmonicIntervalRelationship> octaveIntervals = new ArrayList<HarmonicIntervalRelationship>();
			for(HarmonicIntervalRelationship harmonicRelationship : harmonicRelationships){
				
				if(IntervalType.DISSONANT.equals(harmonicRelationship.getIntervalType())){
					if(annotateComposition){
						SpeciesElement higherSpeciesElement = harmonicRelationship.getHigherSpeciesElement();
						CounterpointAnnotationObject upperNoteAnnotationObject = new CounterpointAnnotationObject();
						higherSpeciesElement.addCounterpointAnnotationObject(upperNoteAnnotationObject);

						SpeciesElement lowerSpeciesElement = harmonicRelationship.getLowerSpeciesElement();
						CounterpointAnnotationObject lowerNoteAnnotationObject = new CounterpointAnnotationObject(); 
						lowerSpeciesElement.addCounterpointAnnotationObject(lowerNoteAnnotationObject);

						DissonanceRuleReport DissonanceRuleReport = new DissonanceRuleReport(RULE_MESSAGE_FOR_DISSONANCE, upperNoteAnnotationObject,
								lowerNoteAnnotationObject);
						aCounterpointComposition.addRuleReport(DissonanceRuleReport);		
					}else{
						evaluationScoringObject.addToScore(PENALTY_VALUE_FOR_DISSONANCE);
					}
				}else if(Interval.UNISON.equals(harmonicRelationship.getInterval())
						|| Interval.OCTAVE.equals(harmonicRelationship.getInterval())){
					//Don't want too many unisons or octaves so count them up and evaluate after the for loop
					//TODO this should probably be moved into a separate rule but it's more efficient because
					//it's using the same iteration.
					octaveIntervals.add(harmonicRelationship);					
				}
			}
			//Evaluating the number of unisons
			int unisonCountExcess = octaveIntervals.size() - idealUnisonIntervalCount;
			if(unisonCountExcess > 0){
				if(annotateComposition){
					//FIXME implement rule report
				}else{
					int unisonScore = PENALTY_VALUE_FOR_EXCESS_OCTAVES * unisonCountExcess;
					evaluationScoringObject.addToScore(unisonScore);
				}
			}
			
		}
	}
	
	private class DissonanceRuleReport extends RuleReport{
		private CounterpointAnnotationObject upperNoteAnnotationObject;
		private CounterpointAnnotationObject lowerNoteAnnotationObject;
		//TODO for consistancy have the annotations set separately
		public DissonanceRuleReport(String reportComments, CounterpointAnnotationObject upperNoteAnnotationObject, 
				CounterpointAnnotationObject lowerNoteAnnotationObject) {
			super(reportComments);
			this.upperNoteAnnotationObject = upperNoteAnnotationObject;
			this.lowerNoteAnnotationObject = lowerNoteAnnotationObject;
		}

		@Override
		public void drawResultMarkings(Graphics g) {
			Point upperPoint = upperNoteAnnotationObject.getPointsArray()[0];
			Point lowerPoint = lowerNoteAnnotationObject.getPointsArray()[0];
			
			g.drawLine(upperPoint.x, upperPoint.y, lowerPoint.x, lowerPoint.y);
		}
		
	}

}
