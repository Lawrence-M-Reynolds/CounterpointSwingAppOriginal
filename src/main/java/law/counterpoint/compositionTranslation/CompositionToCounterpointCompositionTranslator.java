package law.counterpoint.compositionTranslation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import law.counterpoint.counterpointComposition.CounterpointBarEvent;
import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.permutation.BarNotePermutation;
import law.counterpoint.permutation.CompoundBarLinkInformation;
import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.counterpoint.permutation.speciesObjects.SpeciesInformation;
import law.counterpoint.permutation.speciesObjects.SpeciesType;
import law.counterpoint.solutionGenerator.CounterpointNoteGeneratorRestrictionsObject;
import law.musicRelatedClasses.chord.Chord;
import law.musicRelatedClasses.note.Note;
import law.musicRelatedClasses.note.StaveLineNote;
import law.musicRelatedClasses.time.BarMeter;
import law.musicRelatedClasses.time.TimeSignature;
import law.raw.composition.Composition;
import law.raw.composition.components.trackComponents.Bar;
import law.raw.composition.components.trackComponents.VoicePitchRangeLimitObject;
import law.raw.composition.components.trackComponents.barComponents.CompositionBarEvent;

public class CompositionToCounterpointCompositionTranslator {

	/**
	 * This method takes a composition object generated by the front end and converts each bar into a permutation
	 * object. 
	 * FIXME Translation for higher species isn't quite correct
	 * @param aCompositionDTO
	 * @return
	 */
	public static CounterpointComposition generateCounterpointCompositionFromComposition(Composition aCompositionDTO){
		Map<Integer, Collection<Chord>> harmonyLockdownMap = aCompositionDTO.getHarmonyLockdownMap();
		Map<Integer, VoicePitchRangeLimitObject> trackNumToVoicePitchRangeLimitObjectMap = aCompositionDTO.getTrackNumToVoicePitchRangeLimitObjectMap();
		List<BarNotePermutation> permutations = new ArrayList<BarNotePermutation>();
		/*
		 * This bar iterator returns a list that contains one bar from each voice one step at a time.
		 */
		for(List<Bar> bars : aCompositionDTO.getBarsIterator()){
			TimeSignature barTimeSignature = bars.get(0).getTimeSignature();
			SpeciesInformation speciesInformation = SpeciesInformation.getSpeciesInformationInstance(barTimeSignature);
			
			//All bars have the same number so just get the first one
			Collection<Chord> allowedHarmonies = harmonyLockdownMap.get(bars.get(0).getBarNumber());
			
			//Determine what the species of the notes are.			
			if(BarMeter.COMPOUND.equals(speciesInformation.getBarMeter())){
				/*
				 * Here a series of permutations need to be added
				 */
				List<List<Bar>> separatedCompoundBars = separateCompoundBars(bars);
				/*
				 * Each of these lists now needs to be converted into a permutation
				 * and added to the permutations list. They must have a CompoundBarLinkInformation
				 * object so that the full compound bar can be translated back again and the
				 * species information object for the bar.
				 */
				int separateBarSequenceLocationInCompoundBar = 0;
				for(List<Bar> separatedBars: separatedCompoundBars){
					CompoundBarLinkInformation compoundBarLinkInformation = new CompoundBarLinkInformation(
							barTimeSignature, separateBarSequenceLocationInCompoundBar);
					SpeciesInformation separatedBarSpeciesInformation = 
							SpeciesInformation.getSpeciesInformationInstance(separatedBars.get(0).getTimeSignature());
					
					BarNotePermutation barNotePermutation = new BarNotePermutation(separatedBarSpeciesInformation, 
							compoundBarLinkInformation, allowedHarmonies);
					
					permutations.add(convertBarsIntoPermutation(separatedBars, barNotePermutation, trackNumToVoicePitchRangeLimitObjectMap));
					
					separateBarSequenceLocationInCompoundBar++;
				}
			}else{
				//Here the bar will be converted directly and transfered to the object.
				
				BarNotePermutation barNotePermutation = new BarNotePermutation(speciesInformation, null, allowedHarmonies);
				permutations.add(convertBarsIntoPermutation(bars, barNotePermutation, trackNumToVoicePitchRangeLimitObjectMap));
			}
		}	
		return new CounterpointComposition(permutations, aCompositionDTO, aCompositionDTO.getInstrumentTrackDetails());
	}
	
	/**
	 * This method works by accepting a list of bars - one from each voice asscoiated with a single
	 * permutation step. The BarNotePermutation object is created prior to this without any species
	 * objects added to it. This was done so that if it's a bar that has been separated out from
	 * a compound bar, the linking information object can be passed through. 
	 * @param bars
	 * @param barNotePermutation
	 * @param harmonyLockdownMap 
	 * @param trackNumToVoicePitchRangeLimitObjectMap 
	 * @return
	 */
	private static BarNotePermutation convertBarsIntoPermutation(List<Bar> bars, BarNotePermutation barNotePermutation, 
			Map<Integer, VoicePitchRangeLimitObject> trackNumToVoicePitchRangeLimitObjectMap){
		
		
		for(Bar bar: bars){
			VoicePitchRangeLimitObject voicePitchRangeLimitObject = trackNumToVoicePitchRangeLimitObjectMap.get(bar.getTrackNumber());
			
			List<CounterpointBarEvent> counterpointBarEvents = convertBarIntoCounterpointBarEventList(bar);
			
			ListIterator<CounterpointBarEvent> listIterator = counterpointBarEvents.listIterator();
			CounterpointBarEvent counterpointBarEvent = listIterator.next();	
				
				/*
				This species object also takes in a sequence number which indicates which one
				it is if it is wrapped with others inside another species object. In this case there
				is only one first species object so will always be 0.
				*/
			SpeciesElement firstSpeciesElement = new SpeciesElement(barNotePermutation.getSpeciesInformation(), 
					bar.getTrackNumber(), bar.getClefType(), bar.getPitchLineMidiMapper(), voicePitchRangeLimitObject);
			
			firstSpeciesElement = formSpeciesObject(listIterator, counterpointBarEvent, firstSpeciesElement, 0);
			firstSpeciesElement.setOuterSpeciesCounterpointEvents();
			barNotePermutation.addFirstSpeciesElement(firstSpeciesElement);
			barNotePermutation.setBarValues(bar);
			
		}
		return barNotePermutation;
	}
	
	/**
	This method takes in an iterator a bar event and a spcies object which is to be 
	populated, either with higher species objects or with just the values of the
	bar event.
	*/
	private static SpeciesElement formSpeciesObject(
		ListIterator<CounterpointBarEvent> listIterator, CounterpointBarEvent counterpointBarEvent, SpeciesElement speciesElement,
		int speciesElementSequenceNumberModifier){
		
		if(speciesElement.getLegnth() < counterpointBarEvent.getEventLength()){//xxx
			/*
			If the bar event is longer than the species object length then it needs to be
			split into two (with the first having a tied state). One that will fill the this 
			species object with the other making up the rest.
			The first will be converted directly into the species object while the other 
			must be added back to the list iterator so that it can be picked up again.
			*/
			CounterpointBarEvent separatedCounterpointBarEvent = counterpointBarEvent.separateCounterpointBarEvent(speciesElement.getLegnth());
			listIterator.add(separatedCounterpointBarEvent);
			listIterator.previous();
			
			speciesElement.setCounterpointBarEvent(counterpointBarEvent);
			return speciesElement;
		}else if(SpeciesType.DECORATION.equals(speciesElement.getSpeciesType()) ||
				speciesElement.getLegnth() == counterpointBarEvent.getEventLength()){
			/*
			In this case the bar event is exactly the same length as the
			species object and so there are no higher species objects
			to populate this one with.
			Because the Decoration type doesn't have a specific length value it
			will always pass through.
			*/
			speciesElement.setCounterpointBarEvent(counterpointBarEvent);
			return speciesElement;
		}else if(speciesElement.getLegnth() > counterpointBarEvent.getEventLength()){
			/*
			If the event is shorter than the overall length of the bar event then this 
			must be a higher species object that belongs to the one in this method call.
			It may not be the only one however, so all of those that start within
			this species objects time window must be checked through here as
			well.
			*/
			
			CounterpointBarEvent innerCounterpointBarEvent  = counterpointBarEvent;
			boolean moreEventsToAdd = true;
			int innerSpeciesElementSequenceNumber = 0 + speciesElementSequenceNumberModifier;
			while(moreEventsToAdd){
				if(innerCounterpointBarEvent.getTimeLocation() < speciesElement.getEndTimeLocation()){
					SpeciesType thisElementSpeciesType = speciesElement.getSpeciesType();
					SpeciesType innerSpeciesElementType = SpeciesType.getNextHigherSpeciesType(thisElementSpeciesType);
					SpeciesInformation speciesInformation = speciesElement.getSpeciesInformation();
					SpeciesElement innerSpeciesElement = new SpeciesElement(
							speciesInformation, innerSpeciesElementType, innerSpeciesElementSequenceNumber, speciesElement.getTrackNumber());
					
					int sequenceNumberModifier = innerSpeciesElementSequenceNumber * speciesInformation.getMaxNumberOfInnerSpeciesElements(thisElementSpeciesType);
					innerSpeciesElement = formSpeciesObject(listIterator, innerCounterpointBarEvent, innerSpeciesElement, sequenceNumberModifier) ;
					speciesElement.addInnerSpeciesObject(innerSpeciesElement);
					
					moreEventsToAdd = listIterator.hasNext();
					if(moreEventsToAdd){
						innerCounterpointBarEvent = listIterator.next();
					}
					innerSpeciesElementSequenceNumber++;
				}else{
						/*
						The bar event is not an inner part of this species object but the next
						one so should be handled by going back out again. By this point the
						species object should be fully populated.
						*/
					listIterator.previous();
					return speciesElement;
				}
			}
			//There's no more events in this bar.
			return speciesElement;
		} 
		return null;
	}

	/**
	 * This method converts into the new event type and also combines tied notes together
	 * which would make the algorithm harder. Sequential rests are also combined into
	 * one for the same reason.
	 * @param bar
	 * @return
	 */
	private static List<CounterpointBarEvent> convertBarIntoCounterpointBarEventList(Bar bar){
		List<CounterpointBarEvent> counterpointBarEvents = new ArrayList<CounterpointBarEvent>();
		CounterpointBarEvent counterpointBarEvent = null;
		boolean tiedToPrevious = false;
		boolean previousRest = false;
		
		for(CompositionBarEvent barEvent : bar){
			boolean joinRest = barEvent.isRest() && previousRest;
			if(!tiedToPrevious && !joinRest){
				counterpointBarEvent = new CounterpointBarEvent(barEvent);
				counterpointBarEvents.add(counterpointBarEvent);
			}else{
				counterpointBarEvent.combineWithEvent(barEvent);
			}
			tiedToPrevious = barEvent.isTied();
			previousRest = barEvent.isRest();
		}
		return counterpointBarEvents;
	}
	
	/**
	 * This method takes in a list of bars which are from each voice in a single step.
	 * They are separated into a list of binary and ternary meter bars and returned.
	 * Note the returned list should contain a list of bars from each voice with the
	 * same separated part of the compound bar.
	 * Eg. the fisrt list in the list could have all the binary meter bars from each voice
	 * and the next list could have all the ternery ones.
	 * This isn't implemented yet so throws a runtime exception so that it shows up
	 * in the console.
	 * @param bars
	 * @return
	 */
	private static List<List<Bar>> separateCompoundBars(List<Bar> bars){		
		throw new RuntimeException(){
			public String toString(){
				return "Compound time signatures are not yet supported in the " +
						"counterpoint evaluation/generation algorithms.";
			}
		};
	}

}












