package law.counterpoint.solutionGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.musicRelatedClasses.chord.Chord;
import law.musicRelatedClasses.chord.chordTypes.ChordType;
import law.musicRelatedClasses.key.KeyNote;
import law.musicRelatedClasses.key.MusicScale.ScaleDegree;
import law.musicRelatedClasses.note.StaveLineNote;
import law.raw.composition.components.trackComponents.barComponents.PitchLineMidiMapper;

/**
 * Used by the SolutionGenerator class to handle sub tasks/algorithms. Really just
 * to break it down and make it more readable.
 * @author BAZ
 *
 */
public class SolutionGeneratorTaskMethods {

	/**
	 * Takes a colleciton of KeyNotes and returns a collection of the possible 
	 * chords that those keyNotes are contained within. 
	 * This collection will contain the most relavent chords for the given keynotes.
	 * So if there are any chords that contain all, or more than one of the keynotes, 
	 * then any chords that contained a fewer number of key notes will be ommitted from
	 * the collection.
	 * @param keyNotesInPermutation
	 * @return
	 */
	public static Collection<Chord> generateTriadChordsContainingKeyNotes(
			Collection<KeyNote> keyNotesInPermutation) {
				
		Map<Chord, Integer> chordCountMap = new HashMap<Chord, Integer>();
		for(KeyNote keyNote : keyNotesInPermutation){
			//There's three triad chords that can contain any one note
			KeyNote tonic = keyNote.getKeyNoteHarmonic(ScaleDegree.TONIC);
			Chord chordWithKeyNoteAsRoot = tonic.buildKeyChord(ChordType.TRIAD);
			addChordToMap(chordCountMap, chordWithKeyNoteAsRoot);
			
			KeyNote subDominant = keyNote.getKeyNoteHarmonic(ScaleDegree.SUBDOMINANT);
			Chord chordWithKeyNoteAsFifth = subDominant.buildKeyChord(ChordType.TRIAD);
			addChordToMap(chordCountMap, chordWithKeyNoteAsFifth);
			
			KeyNote submediant = keyNote.getKeyNoteHarmonic(ScaleDegree.SUBMEDIANT);
			Chord chordWithKeyNoteAsThird = submediant.buildKeyChord(ChordType.TRIAD);
			addChordToMap(chordCountMap, chordWithKeyNoteAsThird);			
		}
		
		Set<Chord> mostCommonChords = new HashSet<Chord>();
		int maxChordCount = 0;
		for(Chord chord : chordCountMap.keySet()){
			int thisChordCount = chordCountMap.get(chord);
			if(thisChordCount == maxChordCount){
				mostCommonChords.add(chord);
			}else if(thisChordCount > maxChordCount){
				maxChordCount = thisChordCount;
				//Get rid of the less common ones and add this one.
				mostCommonChords.clear();
				mostCommonChords.add(chord);
			}
		}
		
		return mostCommonChords;
	}

	private static void addChordToMap(Map<Chord, Integer> chordCountMap,
			Chord chordToAdd) {
		if(!chordCountMap.containsKey(chordToAdd)){
			chordCountMap.put(chordToAdd, 0);
		}else{
//			Increase the count the chord is mapped to by one.
			chordCountMap.put(chordToAdd, (chordCountMap.get(chordToAdd)+ 1));
		}
		
	}

	public static Map<Integer, Collection<StaveLineNote>> getStaveLineNotesForEachSpeciesElementMap(
			Chord chord, Collection<SpeciesElement> restsInPermutation) {
		
		Map<Integer, Collection<StaveLineNote>> speciesElementStaveLineNoteMap = 
				new HashMap<Integer, Collection<StaveLineNote>>();
		for(SpeciesElement restSpeciesElement : restsInPermutation){
			PitchLineMidiMapper speciesElementMidiMapper = restSpeciesElement.getPitchLineMidiMapper();
			Collection<StaveLineNote> staveLineNotesForChord = 
					speciesElementMidiMapper.getStavlineNotesForChord(chord);
			speciesElementStaveLineNoteMap.put(restSpeciesElement.getTrackNumber(), staveLineNotesForChord);
		}
		return speciesElementStaveLineNoteMap;
	}

	public static ArrayList<List<SpeciesElement>> generatePermutationSpeciesElementLists(
			Map<Integer, SpeciesElement> trackNumToSpeciesElementMap, 
			Map<Integer, Collection<StaveLineNote>> trackNumToPossibleStavelineNoteMap) {
		
		
		ArrayList<List<SpeciesElement>> permutationLists = new ArrayList<List<SpeciesElement>>();
		boolean firstIteration = true;
		
		for(Integer trackNum : trackNumToSpeciesElementMap.keySet()){
			ArrayList<List<SpeciesElement>>  tempPermutations = new ArrayList<List<SpeciesElement>>();
			SpeciesElement existingSpeciesElement = trackNumToSpeciesElementMap.get(trackNum);
			if(trackNumToPossibleStavelineNoteMap.containsKey(trackNum)){
				//The original was a rest and the collection from the map are the possible notes
				Collection<StaveLineNote> allowedStaveLineNotes = trackNumToPossibleStavelineNoteMap.get(trackNum);
				for(StaveLineNote staveLineNote : allowedStaveLineNotes){
					addNoteToPermutationSpeciesElementList(firstIteration, permutationLists, tempPermutations, existingSpeciesElement, staveLineNote);
				}
			}else{
				//if it wasn't in the map just pass null so that it uses the existing species element that has a note
				addNoteToPermutationSpeciesElementList(firstIteration, permutationLists, tempPermutations, existingSpeciesElement, null);
			}
			permutationLists = tempPermutations;
			firstIteration = false;
		}
		return permutationLists;
	}
	
	private static ArrayList<List<SpeciesElement>> addNoteToPermutationSpeciesElementList(boolean firstIteration, 
			ArrayList<List<SpeciesElement>> permutationLists, ArrayList<List<SpeciesElement>> tempPermutationLists, 
			SpeciesElement existingSpeciesElement, StaveLineNote newStaveLineNote){

		/*If the staveline note is null then there was a note in the existingSpecies element and this 
			should just be added. otherwise create a new species element from the stave line note and add that.
		 */
		SpeciesElement speciesElementToAdd;
		if(newStaveLineNote == null){
			speciesElementToAdd = existingSpeciesElement;
		}else{
			speciesElementToAdd = new SpeciesElement(existingSpeciesElement, newStaveLineNote);
		}
		if(firstIteration){
			List<SpeciesElement> newList = new ArrayList<SpeciesElement>();
			newList.add(speciesElementToAdd); 
			tempPermutationLists.add(newList);
		}else{
			for(List<SpeciesElement> permutation : permutationLists){				
				List<SpeciesElement> permutationClone = new ArrayList<SpeciesElement>(permutation);
				permutationClone.add(speciesElementToAdd);
				tempPermutationLists.add(permutationClone);
			}
		}
		return tempPermutationLists;
	}

	
}
