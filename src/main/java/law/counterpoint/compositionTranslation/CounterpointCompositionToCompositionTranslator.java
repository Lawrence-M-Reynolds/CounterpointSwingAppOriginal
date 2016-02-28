package law.counterpoint.compositionTranslation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import law.counterpoint.counterpointComposition.CounterpointBarEvent;
import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.permutation.BarNotePermutation;
import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.counterpoint.permutation.speciesObjects.SpeciesInformation;
import law.musicRelatedClasses.Clef;
import law.raw.composition.Composition;
import law.raw.composition.components.InstrumentTrackDetails;
import law.raw.composition.components.trackComponents.Bar;
import law.raw.composition.components.trackComponents.barComponents.CompositionBarEvent;
import law.raw.composition.components.tracks.InstrumentTrack;
import law.raw.view.stavePanel.noteSelection.CompositeNoteValue;
import law.raw.view.stavePanel.noteSelection.lengthValues.LengthValue;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;
import law.raw.view.stavePanel.noteSelection.lengthValues.RestLengthValue;

public class CounterpointCompositionToCompositionTranslator {
	public static Composition generateCompositionDTOFromCounterpointComposition(CounterpointComposition counterpointComposition){
		Composition compositionDTO = new Composition(counterpointComposition);
		
		List<InstrumentTrack> instrumentTracks = new ArrayList<InstrumentTrack>();
		Map<Integer, InstrumentTrackDetails> instrumentTrackDetailsMap = counterpointComposition.getInstrumentTrackDetailsMap();
		//LOW This doesn't work for converting back compound time
		List<BarNotePermutation> permutations = counterpointComposition.getPermutations();
		
		for(BarNotePermutation barNotePermutation : permutations){
			int voiceNumber = 0;
			for(SpeciesElement firstSpeciesElement : barNotePermutation){
				
				InstrumentTrack instrumentTrack;
				if(permutations.indexOf(barNotePermutation) == 0){
					instrumentTrack = new InstrumentTrack(instrumentTrackDetailsMap.get(voiceNumber));					
					instrumentTracks.add(instrumentTrack);
				}else{
					instrumentTrack = instrumentTracks.get(voiceNumber);
				}
				
				Bar bar = new Bar(firstSpeciesElement.getClef(), barNotePermutation.getTimeSignature(), barNotePermutation.getKey(),
						barNotePermutation.getTempo(), barNotePermutation.getBarNumber(), firstSpeciesElement.getTrackNumber());
				List<SpeciesElement> innerElementsList = firstSpeciesElement.getBaseInnerElementsList();
				for(SpeciesElement speciesElement : innerElementsList){
					CounterpointBarEvent counterpointBarEvent = speciesElement.getCounterpointBarEvent();
					
					LengthValue[] lengthValues = counterpointBarEvent.isRest() ? RestLengthValue.values() : NoteLengthValue.values();
					//LOW This needs cleaning up
					CompositeNoteValue aCompositeNoteValue = CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(
									new CompositeNoteValue(counterpointBarEvent.getWrittenNote()), counterpointBarEvent.getEventLength(), lengthValues);
					
					/*
					 * Takes the accidental value from counterpointBarEvent.getNoteValue() (the WrittenNote object) and uses the line 
					 * pitch value to create the composition bar event. writeCompositeNoteValue then obtains the stave line note
					 * from the bar's midi mapper.
					 */
					bar.writeCompositeNoteValue(counterpointBarEvent.getTimeLocation(), aCompositeNoteValue, 
							counterpointBarEvent.getLinePitchValue(), counterpointBarEvent.isTied(), speciesElement.getCounterpointAnnotationObjects());
				}
				instrumentTrack.addBar(bar);
				voiceNumber++;
			}
		}
		compositionDTO.setInstrumentTracks(instrumentTracks);
		
		return compositionDTO;
	}
}
