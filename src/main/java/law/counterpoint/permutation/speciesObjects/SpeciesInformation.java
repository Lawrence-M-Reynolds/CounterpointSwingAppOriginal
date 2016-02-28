package law.counterpoint.permutation.speciesObjects;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import law.musicRelatedClasses.note.StaveLineNote;
import law.musicRelatedClasses.time.BarMeter;
import law.musicRelatedClasses.time.TimeSignature;
import law.raw.view.stavePanel.noteSelection.CompositeNoteValue;
import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;

/**
 * This class draws the necessary information from the time signature of the bar. From
 * this it determines the meter of the bar (eg if it is 3/4 or 4/4) and then
 * works out what the equivlent note lengths the species objects are which
 * will be used when translating back.
 * If the time signature turns out to be neither binary or ternary then it is marked
 * as a compound time signature. In this case the length values of the species objects
 * is not worked out. When this happens the bar must be broken down into a combination
 * of ternary and binary bars and they will then have their own SpeciesInformation
 * objects.
 * @author BAZ
 *
 */
public class SpeciesInformation {
	//This so that the same instances will be used which will save memory.
	private static Map<TimeSignature, SpeciesInformation> INSTANCES_MAP = new HashMap<TimeSignature, SpeciesInformation>();
	
	//Make this immutable as they are to be shared.
	private final TimeSignature timeSignature;
	private final BarMeter barMeter;
	private final Map<SpeciesType, Integer> speciesTypeLengthMap = new HashMap<SpeciesType, Integer>();
	
	public static SpeciesInformation getSpeciesInformationInstance(TimeSignature aTimeSignature){
		if(INSTANCES_MAP.containsKey(aTimeSignature)){
			return INSTANCES_MAP.get(aTimeSignature);
		}else{
			SpeciesInformation speciesInformation = new SpeciesInformation(aTimeSignature);
			INSTANCES_MAP.put(aTimeSignature, speciesInformation);
			return speciesInformation;
		}
	}
	
	/**
	 * This constructor takes in the time signatre and determines the meter. If
	 * it;s not a compound meter then the note length values of the different
	 * species objects is determined.
	 * If it is compound then they are not determined and the bar must be broken
	 * down further.
	 * @param aTimeSignature
	 */
	private SpeciesInformation(TimeSignature aTimeSignature){
		timeSignature = aTimeSignature;
		barMeter = BarMeter.getBarMeterFromTimeSignature(timeSignature);
		
		/*
		 * The species objects will only be worked out for 
		 */
		if(!BarMeter.COMPOUND.equals(barMeter)){
			speciesTypeLengthMap.put(SpeciesType.FIRST, aTimeSignature.getNumberOf32ndNotesPerBar());
			
			/* The second species has half the 32nd notes as the first species object if it's
			 * a binary meter and a third if it's a ternary meter.
			 */
			int secondSpeciesNumberOf32ndNotes = (aTimeSignature.getNumberOf32ndNotesPerBar() / barMeter.getNumericalValue());
			speciesTypeLengthMap.put(SpeciesType.SECOND, secondSpeciesNumberOf32ndNotes);
			
			//The third has half of the second
			int thirdSpeciesNumberOf32ndNotes = (secondSpeciesNumberOf32ndNotes / 2);
			speciesTypeLengthMap.put(SpeciesType.THIRD, thirdSpeciesNumberOf32ndNotes);
			
			//The fourth is the same as the second species
			speciesTypeLengthMap.put(SpeciesType.FOURTH, secondSpeciesNumberOf32ndNotes);
			
			// All that is known about the length of the decoration is that it's less than the fourth species.
			speciesTypeLengthMap.put(SpeciesType.DECORATION, thirdSpeciesNumberOf32ndNotes - 1);//xxx
		}
	}
	
	public int getSpeciesElementTypeLength(SpeciesType speciesType) {
		return speciesTypeLengthMap.get(speciesType);
	}

	public BarMeter getBarMeter() {
		return barMeter;
	}

	public TimeSignature getTimeSignature() {
		return timeSignature;
	}

	/**
	 * Returns the number of inner elements a species element can have of the type one higher above. 
	 * @param speciesType
	 * @return
	 */
	public int getMaxNumberOfInnerSpeciesElements(SpeciesType speciesType){
		if(!SpeciesType.THIRD.equals(speciesType)){
			SpeciesType higherSpeciesType = SpeciesType.getNextHigherSpeciesType(speciesType);
			return speciesTypeLengthMap.get(speciesType) / speciesTypeLengthMap.get(higherSpeciesType);
		}else{
			return 1;
		}
	}

}
