package law.counterpoint.permutation.analysisObjects.harmonicAnalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.musicRelatedClasses.interval.intervalRelationships.HarmonicIntervalRelationship;

public class PermutationAnalyser {

	public static Map<HarmonicAnalysisPairingKey, HarmonicIntervalRelationship> evaluateNoteHarmonyRelationships(
			List<SpeciesElement> firstSpeciesElementsForEachVoice){
		
		Map<HarmonicAnalysisPairingKey, HarmonicIntervalRelationship> permutationHarmonicResultsMap = 
				new HashMap<HarmonicAnalysisPairingKey, HarmonicIntervalRelationship>();
		
		ListIterator<SpeciesElement> voiceElementIter = firstSpeciesElementsForEachVoice.listIterator();
		
		while(voiceElementIter.hasNext()){
			SpeciesElement thisSpeciesElement = voiceElementIter.next();
			
			//Iterating through each voice below this one.
			int upperVoiceElementNumber = firstSpeciesElementsForEachVoice.indexOf(thisSpeciesElement);
			ListIterator<SpeciesElement> lowerVoiceElementIter = 
					firstSpeciesElementsForEachVoice.listIterator(upperVoiceElementNumber + 1);
			
			/*
			 * HIGHER_SPECIES At the moment it's only looking at the first species element. Have to look at
			 * their inner elements to go to the higher species.
			 */
			while(lowerVoiceElementIter.hasNext()){
				SpeciesElement nextSpeciesElement = lowerVoiceElementIter.next();
				HarmonicIntervalRelationship harmonicRelationship = 
						new HarmonicIntervalRelationship(thisSpeciesElement, nextSpeciesElement);
				HarmonicAnalysisPairingKey harmonicAnalysisPairingKey = 
						new HarmonicAnalysisPairingKey(thisSpeciesElement, nextSpeciesElement);
				
				permutationHarmonicResultsMap.put(harmonicAnalysisPairingKey, harmonicRelationship);
			}
			
		}
		return permutationHarmonicResultsMap;
	}
	
	/**
	 * Finding the pitch ordering of the notes. This is important for some of the rules: eg
	 * the bass line has different rules for intervals. Only need to do it on the first outer
	 * iteration.
	 */
	public static void setFirstSpeciesVoicePitchOrdering(List<SpeciesElement> firstSpeciesElementsForEachVoice){
		Comparator<SpeciesElement> pitchComparator = new Comparator<SpeciesElement>(){
			public int compare(SpeciesElement speciesElement1, SpeciesElement speciesElement2) {
				return speciesElement1.getMidiValue() - speciesElement2.getMidiValue();
			}	
		};
		
		ArrayList<SpeciesElement> sorter = new ArrayList<SpeciesElement>();					
		sorter.addAll(firstSpeciesElementsForEachVoice);
		Collections.sort(sorter, pitchComparator);
		//set the value in the species objects themselves
		int pitchSequenceValue = 0;
		for(SpeciesElement speciesElement : sorter){
			speciesElement.setPitchSequenceValue(pitchSequenceValue);
			pitchSequenceValue ++;
		}
		
	}
}
