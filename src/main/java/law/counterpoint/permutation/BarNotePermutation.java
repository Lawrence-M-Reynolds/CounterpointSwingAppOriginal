package law.counterpoint.permutation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import law.counterpoint.permutation.analysisObjects.harmonicAnalysis.HarmonicAnalysisPairingKey;
import law.counterpoint.permutation.analysisObjects.harmonicAnalysis.PermutationAnalyser;
import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.counterpoint.permutation.speciesObjects.SpeciesInformation;
import law.musicRelatedClasses.chord.Chord;
import law.musicRelatedClasses.interval.intervalRelationships.HarmonicIntervalRelationship;
import law.musicRelatedClasses.key.Key;
import law.musicRelatedClasses.key.KeyNote;
import law.musicRelatedClasses.time.Tempo;
import law.musicRelatedClasses.time.TimeSignature;
import law.raw.composition.components.trackComponents.Bar;

public class BarNotePermutation implements Iterable<SpeciesElement>{
	private SpeciesInformation speciesInformation;
	private List<SpeciesElement> firstSpeciesElementsForEachVoice = new ArrayList<SpeciesElement>();
	//If this is null then it can be assumed that the bar didn't belong to a compound bar.
	private CompoundBarLinkInformation compoundBarLinkInformation;
	
	//Bar Related
	private TimeSignature timeSignature;
	private Key key;
	private Tempo tempo;
	private int barNumber;
	private Collection<Chord> allowedChords;
	private boolean analysisPerformed = false; 
	
	//Processor result maps.
	private Map<HarmonicAnalysisPairingKey, HarmonicIntervalRelationship> harmonicAnalysisResultsMap;
	
	public BarNotePermutation(SpeciesInformation aSpeciesInformation, CompoundBarLinkInformation aCompoundBarLinkInformation, 
			Collection<Chord> aColOfAllowedChords){
		speciesInformation = aSpeciesInformation;
		compoundBarLinkInformation = aCompoundBarLinkInformation;
		allowedChords = aColOfAllowedChords;
	}
	
	/**
	 * Copy constructor used in the generator which takes every property form the passed
	 * in permutation except the species element list. This is taken from the other
	 * paraemeter.
	 * @param barNotePermutation
	 * @param speciesElementList
	 */	
	public BarNotePermutation(
			BarNotePermutation barNotePermutation,
			List<SpeciesElement> firstSpeciesElementsForEachVoice) {
		super();
		this.speciesInformation = barNotePermutation.speciesInformation;
		this.firstSpeciesElementsForEachVoice = firstSpeciesElementsForEachVoice;
		this.compoundBarLinkInformation = barNotePermutation.compoundBarLinkInformation;
		this.timeSignature = barNotePermutation.timeSignature;
		this.key = barNotePermutation.key;
		this.tempo = barNotePermutation.tempo;
		this.barNumber = barNotePermutation.barNumber;
		this.allowedChords = barNotePermutation.allowedChords;
		this.analysisPerformed = barNotePermutation.analysisPerformed;
		this.harmonicAnalysisResultsMap = barNotePermutation.harmonicAnalysisResultsMap;
	}

	public void setBarValues(Bar bar){
		timeSignature = bar.getTimeSignature();
		key = bar.getKey();
		tempo = bar.getTempo();
		barNumber = bar.getBarNumber();
	}
	public SpeciesInformation getSpeciesInformation() {
		return speciesInformation;
	}
	
	/**
	 * This adds a first species element associated for one voice for this bar/step
	 * permutation object.
	 * The processors that perform analysis is done each time one is added.
	 * @param firstSpeciesElement
	 */
	public void addFirstSpeciesElement(SpeciesElement firstSpeciesElement) {
		firstSpeciesElementsForEachVoice.add(firstSpeciesElement);		
	}
	public Iterator<SpeciesElement> iterator() {
		return firstSpeciesElementsForEachVoice.iterator();
	}
	public List<SpeciesElement> getFirstSpeciesElementsForEachVoice() {
		return firstSpeciesElementsForEachVoice;
	}
	public CompoundBarLinkInformation getCompoundBarLinkInformation() {
		return compoundBarLinkInformation;
	}
	public TimeSignature getTimeSignature() {
		return timeSignature;
	}
	public Key getKey() {
		return key;
	}
	public Tempo getTempo() {
		return tempo;
	}
	public int getBarNumber() {
		return barNumber;
	}
	
	/**
	 * Returns a map of the harmonic relationships for every note between any of the two voice
	 * species elements.
	 * @return
	 */
	public Map<HarmonicAnalysisPairingKey, HarmonicIntervalRelationship> getHarmonicAnalysisResultsMap() {
		return harmonicAnalysisResultsMap;
	}
	
	/**
	 * This performs all of the analysis that can be done for once in a permutation so that it isn't
	 * repeated over and over again. This method is called by the counterpoint composition for
	 * all permutations before running the rules.
	 * LOW A command design pattern would be a much better way of doing this.
	 */
	public void performPermutationAnalysis(){
		List<SpeciesElement> firstSpeciesElementsForEachVoiceNoRests = new ArrayList<SpeciesElement>();
		ListIterator<SpeciesElement> listIter = firstSpeciesElementsForEachVoice.listIterator();
		while(listIter.hasNext()){
			SpeciesElement speciesElement = listIter.next();
			if(!speciesElement.isRest()){
				firstSpeciesElementsForEachVoiceNoRests.add(speciesElement);
			}
		}

		PermutationAnalyser.setFirstSpeciesVoicePitchOrdering(firstSpeciesElementsForEachVoiceNoRests);
		harmonicAnalysisResultsMap = 
				PermutationAnalyser.evaluateNoteHarmonyRelationships(firstSpeciesElementsForEachVoiceNoRests);


		analysisPerformed = true;

	}
	
	public void setSpeciesInformation(SpeciesInformation speciesInformation) {
		this.speciesInformation = speciesInformation;
	}

	public void setCompoundBarLinkInformation(
			CompoundBarLinkInformation compoundBarLinkInformation) {
		this.compoundBarLinkInformation = compoundBarLinkInformation;
	}
	public void setTimeSignature(TimeSignature timeSignature) {
		this.timeSignature = timeSignature;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public void setTempo(Tempo tempo) {
		this.tempo = tempo;
	}
	public void setBarNumber(int barNumber) {
		this.barNumber = barNumber;
	}

	public boolean isAnalysisPerformed() {
		return analysisPerformed;
	}
	
	public SpeciesElement getVoiceSpeciesElement(int voiceNumber){
		return firstSpeciesElementsForEachVoice.get(voiceNumber);
	}
	//FIXME this should use the track number
	public int getVoiceNumberOfSpeciesElementInPermutation(SpeciesElement speciesElement){
		return firstSpeciesElementsForEachVoice.indexOf(speciesElement);
	}
	
	public int getNumberOfFirstSpeciesElements(){
		return firstSpeciesElementsForEachVoice.size();
	}

	public Collection<Chord> getAllowedChords() {
		return allowedChords;
	}

	public Collection<KeyNote> getAllFirstSpeciesKeyNotes() {
		Set<KeyNote> firstSpeciesKeyNotes = new HashSet<KeyNote>();
		for(SpeciesElement firstSpeciesElement : firstSpeciesElementsForEachVoice){
			if(!firstSpeciesElement.isRest()){
				//TODO provide more getters so that it isn't done through all the objects like this
				KeyNote keyNote = firstSpeciesElement.getCounterpointBarEvent().getStaveLineNote().getKeyNote();
				firstSpeciesKeyNotes.add(keyNote);
			}
		}
		return firstSpeciesKeyNotes;
	}

	public Collection<SpeciesElement> getAllRestFirstSpeciesElements() {
		Set<SpeciesElement> firstSpeciesRests = new HashSet<SpeciesElement>();
		for(SpeciesElement firstSpeciesElement : firstSpeciesElementsForEachVoice){
			if(firstSpeciesElement.isRest()){
				firstSpeciesRests.add(firstSpeciesElement);
			}
		}
		return firstSpeciesRests;
	}

	public Map<Integer, SpeciesElement> getTrackNumToSpeciesElementMap() {
		Map<Integer, SpeciesElement> trackNumToSpeciesElementMap = new HashMap<Integer, SpeciesElement>();
		for(SpeciesElement speciesElement : firstSpeciesElementsForEachVoice){
			trackNumToSpeciesElementMap.put(speciesElement.getTrackNumber(), speciesElement);
		}
		return trackNumToSpeciesElementMap;
	}
}
