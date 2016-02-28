package law.counterpoint.rules.counterpoint;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.permutation.BarNotePermutation;
import law.counterpoint.permutation.analysisObjects.harmonicAnalysis.HarmonicAnalysisPairingKey;
import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.counterpoint.rules.Rule;
import law.counterpoint.rules.reporting.CounterpointAnnotationObject;
import law.counterpoint.rules.reporting.EvaluationScoringObject;
import law.counterpoint.rules.reporting.RuleReport;
import law.musicRelatedClasses.MotionType;
import law.musicRelatedClasses.interval.IntervalEvaluator.IntervalType;
import law.musicRelatedClasses.interval.intervalRelationships.HarmonicIntervalRelationship;

/**
 * This rule evaluates the type of motion used between notes by different voices
 * with respect to each other.
 * TODO If two voices crossover into a perfect interval then this rule views them as being the same 
 * voice moving in the same direction. I'm not sure if this is bad or not but am leaving it for now.
 * @author BAZ
 *
 */
public class MotionRule extends Rule{
	private static int PENALTY_VALUE = 1000;
	private static int CONTRARY_MOTION_BONUS_VALUE = -400;
	private static String PARALLEL_MOTION_RULE_MESSAGE = 
			"Parallel motion is not allowed when moving into a perfect consonant harmonic relationship.";
/*
 * TODO Most of this should be used as a processor method because the motion info may be used in other 
 * rules.
 * @see law.counterpoint.rules.Rule#evaluateCounterpointComposition(law.counterpoint.counterpointComposition.CounterpointComposition, boolean)
 */
	@Override
	public void evaluateCounterpointComposition(CounterpointComposition aCounterpointComposition, boolean annotateComposition) {
		
		ListIterator<BarNotePermutation> iter = aCounterpointComposition.getPermutationListIterator(0);
		EvaluationScoringObject evaluationScoringObject = aCounterpointComposition.getEvaluationScoringObject();
		
		
		while(iter.hasNext()){
			BarNotePermutation thisBarNotePermutation = iter.next();
			if(!iter.hasNext()){
				// This must be the last bar;
				return;
			}
			BarNotePermutation nextBarNotePermutation = iter.next();
			
			Map<HarmonicAnalysisPairingKey, HarmonicIntervalRelationship> thisHarmonicAnalysisResultsMap = 
					thisBarNotePermutation.getHarmonicAnalysisResultsMap();
			Map<HarmonicAnalysisPairingKey, HarmonicIntervalRelationship> nextHarmonicAnalysisResultsMap = 
					nextBarNotePermutation.getHarmonicAnalysisResultsMap();
			
			Set<HarmonicAnalysisPairingKey> harmonicAnalysisPairingKeys = 
					thisHarmonicAnalysisResultsMap.keySet();
			
			/*
			 * Iterate through each pair of voices and look at each relationship.
			 */
			for(HarmonicAnalysisPairingKey harmonicAnalysisPairingKey : harmonicAnalysisPairingKeys){
				HarmonicIntervalRelationship thisHarmonicRelationship = 
						thisHarmonicAnalysisResultsMap.get(harmonicAnalysisPairingKey);
				HarmonicIntervalRelationship nextHarmonicRelationship = 
						nextHarmonicAnalysisResultsMap.get(harmonicAnalysisPairingKey);
				/*
				 * LOW if there are no notes in the next bar then no evaluation is made at all - not
				 * even with the bar after that. 
				 */
				if(nextHarmonicRelationship != null){
					MotionType motionType = MotionType.determineMotionType(thisHarmonicRelationship, nextHarmonicRelationship);				
					IntervalType nextBarIntervalType = nextHarmonicRelationship.getIntervalType();

					if(IntervalType.PERFECT_CONSONANT.equals(nextBarIntervalType) && MotionType.PARALLEL.equals(motionType)){
						/*
						 * The rule has been broken.
						 */
						if(annotateComposition){
							MotionRuleReport motionRuleReport = new MotionRuleReport(PARALLEL_MOTION_RULE_MESSAGE);
							aCounterpointComposition.addRuleReport(motionRuleReport);

							setAnnotationObjects(motionRuleReport, thisHarmonicRelationship, nextHarmonicRelationship);
						}else{
							evaluationScoringObject.addToScore(PENALTY_VALUE);
						}
					}else if(MotionType.CONTRARY.equals(motionType)){
						evaluationScoringObject.addToScore(CONTRARY_MOTION_BONUS_VALUE);
					}
				}
			}
			//set back one as it was looking at the next one.
			iter.previous();
		}
		
	}
	/**
	 * Setting the Annotation objects in both the RuleReport and in the SpeciesElements.
	 * @param motionRuleReport
	 * @param thisHarmonicRelationship
	 * @param nextHarmonicRelationship
	 */
	private void setAnnotationObjects(MotionRuleReport motionRuleReport, HarmonicIntervalRelationship thisHarmonicRelationship,
			HarmonicIntervalRelationship nextHarmonicRelationship){
		//Setting the upper voice annotation objects
		CounterpointAnnotationObject upperVoiceAnnotationObject = new CounterpointAnnotationObject();
		motionRuleReport.setUpperVoiceAnnotationObject(upperVoiceAnnotationObject);
		
		SpeciesElement firstHigerSpeciesElement = thisHarmonicRelationship.getHigherSpeciesElement();
		firstHigerSpeciesElement.addCounterpointAnnotationObject(upperVoiceAnnotationObject);
		SpeciesElement secondHigerSpeciesElement = nextHarmonicRelationship.getHigherSpeciesElement();
		secondHigerSpeciesElement.addCounterpointAnnotationObject(upperVoiceAnnotationObject);
		
		//Setting the lower voice annotation objects
		CounterpointAnnotationObject lowerVoiceAnnotationObject = new CounterpointAnnotationObject();
		motionRuleReport.setLowerVoiceAnnotationObject(lowerVoiceAnnotationObject);
		SpeciesElement firstLowerSpeciesElement = thisHarmonicRelationship.getLowerSpeciesElement();
		firstLowerSpeciesElement.addCounterpointAnnotationObject(lowerVoiceAnnotationObject);
		SpeciesElement secondLowerSpeciesElement = nextHarmonicRelationship.getLowerSpeciesElement();
		secondLowerSpeciesElement.addCounterpointAnnotationObject(lowerVoiceAnnotationObject);
	}
	
	/**
	 * This extension of the RuleReport class has two CounterpointAnnotationObjects
	 * associated with it because two separate lines needs to be joined between two
	 * separate pairs of points. One pair will be the two notes of the upper voice
	 * and the other pair for the lower voice.
	 * @author BAZ
	 *
	 */
	private class MotionRuleReport extends RuleReport{
		private CounterpointAnnotationObject upperVoiceAnnotationObject;
		private CounterpointAnnotationObject lowerVoiceAnnotationObject;

		public MotionRuleReport(String reportComments) {
			super(reportComments);
		}

		/**
		 * This works by joining the top two points together and then the lower two
		 * points.
		 */
		@Override
		public void drawResultMarkings(Graphics g) {
			//Draw annotation for upper voice			
			Point[] points = upperVoiceAnnotationObject.getPointsArray();
			Point firstPoint = points[0];
			Point secondPoint = points[1];
			
			g.drawLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
			
			//Draw annotation for lower voice
			points = lowerVoiceAnnotationObject.getPointsArray();
			firstPoint = points[0];
			secondPoint = points[1];
			
			g.drawLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
		}

		/**
		 * Sets the uppervoice annotation object.
		 * @param upperVoiceAnnotationObject
		 */
		public void setUpperVoiceAnnotationObject(
				CounterpointAnnotationObject upperVoiceAnnotationObject) {
			this.upperVoiceAnnotationObject = upperVoiceAnnotationObject;
		}

		/**
		 * Sets the lower voice annnotation object.
		 * @param lowerVoiceAnnotationObject
		 */
		public void setLowerVoiceAnnotationObject(
				CounterpointAnnotationObject lowerVoiceAnnotationObject) {
			this.lowerVoiceAnnotationObject = lowerVoiceAnnotationObject;
		}
		
	}
}
