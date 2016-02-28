/**
 * This class aggregates the instrument tracks and also has information regarding the whole composition such as tempos, title etc.
 * It's basically a bean to carry the composition details between the view and the IO part.
 */

package law.raw.composition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.rules.reporting.EvaluationScoringObject;
import law.counterpoint.rules.reporting.RuleReport;
import law.musicRelatedClasses.Clef;
import law.musicRelatedClasses.chord.Chord;
import law.musicRelatedClasses.key.Key;
import law.musicRelatedClasses.note.Note;
import law.musicRelatedClasses.note.StaveLineNote;
import law.musicRelatedClasses.time.Tempo;
import law.musicRelatedClasses.time.TimeSignature;
import law.raw.composition.components.InstrumentTrackDetails;
import law.raw.composition.components.trackComponents.Bar;
import law.raw.composition.components.trackComponents.InstrumentType;
import law.raw.composition.components.trackComponents.VoicePitchRangeLimitObject;
import law.raw.composition.components.tracks.InstrumentTrack;
/*
 * LOW maybe it would be better to have a super abstract composition class that the front end 
 * and counterpoint end will both inherit from 
 */
/**
 * This object represents the enitire composition as created by the user. Includes the tracks,
 * notes etc.
 * @author BAZ
 *
 */
public class Composition implements Serializable, Iterable<InstrumentTrack> {
	private static final long serialVersionUID = 1L;
//	public static int DEFAULT_NUMBER_OF_BARS = 8;
	
	private String title;
	private Key key;
	private TimeSignature timeSignature;
	private Tempo tempo;
	private int numberOfBars;
	
	private Map<Integer, Collection<Chord>> harmonyLockdownMap;
	private Map<Integer, VoicePitchRangeLimitObject> trackNumToVoicePitchRangeLimitObjectMap = new HashMap<Integer, VoicePitchRangeLimitObject>();
	private List<InstrumentTrack> instrumentTracks = new ArrayList<InstrumentTrack>();
	
	private EvaluationScoringObject evaluationScoringObject;
	private List<RuleReport> ruleReports;
	
	public Composition(final String aTitle, final Key aKey, final TimeSignature aTimeSignature, 
			final Tempo aTempo, final int aNumberOfBars, Map<Integer, Collection<Chord>> aHarmonyLockdownMap){
		title = aTitle;
		key = aKey;
		timeSignature = aTimeSignature;
		tempo = aTempo;
		numberOfBars = aNumberOfBars;
		harmonyLockdownMap = aHarmonyLockdownMap;
	}
	
	public Composition(CounterpointComposition counterpointComposition) {
		title = counterpointComposition.getTitle();
		key = counterpointComposition.getKey();
		timeSignature = counterpointComposition.getTimeSignature();
		tempo = counterpointComposition.getTempo();
		evaluationScoringObject = counterpointComposition.getEvaluationScoringObject();
		harmonyLockdownMap = counterpointComposition.getHarmonyLockdownMap();
		ruleReports = counterpointComposition.getRuleReports();
	}

	/**
	 * Returns the size of any track already in the composition. They should all have the
	 * same number of bars so need to check all of them. If there are no other tracks
	 * in the composition then it will return a default value as defined in the driver
	 * class.
	 * @return
	 */
	public int getNumberOfBars(){
		if(instrumentTracks.isEmpty()){
			return numberOfBars;
		}else{			
			return instrumentTracks.get(0).getNumberOfBars();
		}
	}
	
	@Override
	public String toString(){
		String numberOfTracks = Integer.toString(instrumentTracks.size());
		return "This composition has " + numberOfTracks + " tracks.";
	}
	
	public void addNewTrack(final String aTrackName, final InstrumentType anInstrumentType, final Clef aClef){
		InstrumentTrack instrumentTrack = new InstrumentTrack(instrumentTracks.size(), 
				aTrackName, anInstrumentType);
		//This should be in the bar/track/composition manager object
		
		if(instrumentTracks.size() == 0){
			for (int barNumber = 0; barNumber < getNumberOfBars(); barNumber++) {
				instrumentTrack.addBar(new Bar(aClef, timeSignature, key, tempo, barNumber, instrumentTracks.size()));
			}	
		}else{
			for(Bar barFromOtherTrack : instrumentTracks.get(0).getBars()){
				instrumentTrack.addBar(new Bar(aClef, barFromOtherTrack.getTimeSignature(), barFromOtherTrack.getKey(), 
						barFromOtherTrack.getTempo(), barFromOtherTrack.getBarNumber(), instrumentTracks.size()));
			}
		}
		instrumentTracks.add(instrumentTrack);
	}

	public TimeSignature getTimeSignature() {
		return timeSignature;
	}
	
	public int getNumberOfTracks(){
		return instrumentTracks.size();
	}

	public Iterator<InstrumentTrack> iterator() {
		return instrumentTracks.iterator();
	}

	/**
	 * This inner iterator is used so that all of the voices across a single
	 * bar can be iterated through at once. The other iterator just iterates
	 * through each of the melody lists which isn't so easy to work with.
	 * @author BAZ
	 *
	 */
	public Iterable<List<Bar>> getBarsIterator(){

		class BarsIterator implements Iterator<List<Bar>>{
			private int barNumber = 0;

			public boolean hasNext() {
				return (barNumber < getNumberOfBars());
			}
			/**
			 * Starts with notes from the first voice.
			 */
			public List<Bar> next() {
				List<Bar> voiceNotes = new ArrayList<Bar>();
				for(InstrumentTrack instrumentTrack : instrumentTracks){
					voiceNotes.add(instrumentTrack.getBar(barNumber));
				}
				
				barNumber++;
				
				return voiceNotes;
			}

			public void remove() {
				// GENERATED_TAG Auto-generated method stub
				
			}
			
		}

		
		return new Iterable<List<Bar>>(){
			public Iterator<List<Bar>> iterator() {
				return new BarsIterator();
			}		
		};
	}

	public Key getKey() {
		return key;
	}

	public List<RuleReport> getRuleReports() {
		return ruleReports;
	}
	
	public Map<Integer, InstrumentTrackDetails> getInstrumentTrackDetails(){
		
		Map<Integer, InstrumentTrackDetails> instrumentTrackDetailsMap = new HashMap<Integer, InstrumentTrackDetails>();
		for(InstrumentTrack instrumentTrack: instrumentTracks){
			instrumentTrackDetailsMap.put(instrumentTrack.getInstrumentNum(), 
					new InstrumentTrackDetails(instrumentTrack.getInstrumentNum(), instrumentTrack.getTrackName(), 
					instrumentTrack.getInstrumentType()));
		}
		
		return instrumentTrackDetailsMap;
	}

	public String getTitle() {
		return title;
	}

	public Tempo getTempo() {
		return tempo;
	}

	public void setInstrumentTracks(List<InstrumentTrack> instrumentTracks) {
		this.instrumentTracks = instrumentTracks;
	}
	public Map<Integer, Collection<Chord>> getHarmonyLockdownMap() {
		return harmonyLockdownMap;
	}

	public void addHarmonyLockdownMapping(Integer barNum, Collection<Chord> harmonies) {
		this.harmonyLockdownMap.put(barNum, harmonies);
	}

	public Map<Integer, VoicePitchRangeLimitObject> getTrackNumToVoicePitchRangeLimitObjectMap() {
		return trackNumToVoicePitchRangeLimitObjectMap;
	}

	public void addTrackNumToVoicePitchRangeLimitObjectMapping(Integer trackNum, 
			VoicePitchRangeLimitObject voicePitchRangeLimitObject) {
		this.trackNumToVoicePitchRangeLimitObjectMap.put(trackNum, voicePitchRangeLimitObject);
	}

}


















