package law.counterpoint.permutation.speciesObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import law.counterpoint.counterpointComposition.CounterpointBarEvent;
import law.counterpoint.rules.reporting.CounterpointAnnotationObject;
import law.counterpoint.solutionGenerator.CounterpointNoteGeneratorRestrictionsObject;
import law.musicRelatedClasses.Clef;
import law.musicRelatedClasses.interval.Interval;
import law.musicRelatedClasses.note.StaveLineNote;
import law.raw.composition.components.trackComponents.VoicePitchRangeLimitObject;
import law.raw.composition.components.trackComponents.barComponents.PitchLineMidiMapper;
/**
 * Represents the notes as a species objects which is easier for the algorithms
 * to work with.
 * 
 * TODO - Consider making the firstspecies element a separate type or having all species element held in
 * another object associated with the track.
 * @author BAZ
 *
 */
public class SpeciesElement implements Iterable<SpeciesElement>{
	private SpeciesType speciesType;
	private SpeciesInformation speciesInformation;
	/*
	 *If the species element has inner elements with non-rest bar events then the first
	 *one is set as the event of this object. This is set in the addInnerSpeciesObject
	 *method.
	 */
	private CounterpointBarEvent counterpointBarEvent;
	private int innerSpeciesElementSequenceNumber;
	private int trackNumber;
	private Clef clef;
	private PitchLineMidiMapper pitchLineMidiMapper;
	private VoicePitchRangeLimitObject voicePitchRangeLimitObject;


	private Collection<CounterpointAnnotationObject> counterpointAnnotationObjects; 
	
	
	//This stores the sequence the species object has wrt pitch and the other notes.
	private int pitchSequenceValue;
	
	//These are the inner elements directly beneath this one.
	protected List<SpeciesElement> innerSpeciesElements = new ArrayList<SpeciesElement>();
	//base inner elements are the lowest level elemements that make up this one.
	private List<SpeciesElement> baseInnerElementsList;
	
	public SpeciesElement(SpeciesInformation aSpeciesInformation,
			SpeciesType aSpeciesType, int anInnerSpeciesElementSequenceNumber, int aTrackNumber) {
		speciesType = aSpeciesType;
		speciesInformation = aSpeciesInformation;
		innerSpeciesElementSequenceNumber = anInnerSpeciesElementSequenceNumber;
		trackNumber = aTrackNumber;
	}
	
	/**
	 * Used when adding the first species element.
	 * @param aSpeciesInformation
	 * @param aPitchLineMidiMapper 
	 * @param voicePitchRangeLimitObject 
	 * @param aSpeciesType
	 * @param anInnerSpeciesElementSequenceNumber
	 */
	public SpeciesElement(SpeciesInformation aSpeciesInformation, int aTrackNumber, Clef aClef, 
			PitchLineMidiMapper aPitchLineMidiMapper, VoicePitchRangeLimitObject aVoicePitchRangeLimitObject) {
		speciesType = SpeciesType.FIRST;
		speciesInformation = aSpeciesInformation;
		innerSpeciesElementSequenceNumber = 0;
		trackNumber = aTrackNumber;
		clef = aClef;
		pitchLineMidiMapper = aPitchLineMidiMapper;
		voicePitchRangeLimitObject = aVoicePitchRangeLimitObject;
	}
	
	/**
	 * Copy constructor used by the generator to create a species element with a 
	 * new set of speciess elements.
	 * @param existingSpeciesElement
	 * @param newStaveLineNote
	 */
	public SpeciesElement(SpeciesElement existingSpeciesElement,
			StaveLineNote newStaveLineNote) {
		super();
		this.speciesType = existingSpeciesElement.speciesType;
		this.speciesInformation = existingSpeciesElement.speciesInformation;
		this.counterpointBarEvent = new CounterpointBarEvent(
				existingSpeciesElement.counterpointBarEvent, newStaveLineNote);
		this.innerSpeciesElementSequenceNumber = 
				existingSpeciesElement.innerSpeciesElementSequenceNumber;
		this.trackNumber = existingSpeciesElement.trackNumber;
		this.clef = existingSpeciesElement.clef;
		this.pitchLineMidiMapper = existingSpeciesElement.pitchLineMidiMapper;
		this.voicePitchRangeLimitObject = existingSpeciesElement.voicePitchRangeLimitObject;
		this.counterpointAnnotationObjects = existingSpeciesElement.counterpointAnnotationObjects;
		this.pitchSequenceValue = existingSpeciesElement.pitchSequenceValue;
		this.innerSpeciesElements = existingSpeciesElement.innerSpeciesElements;
		this.baseInnerElementsList = null;
	}

	/**
	 * Species elements that contain inner elements won't have counterpoint event objects
	 * associated with them. This method iterates through the inner event list and sets
	 * the counterpoint events of all outer species objects to that of the first non
	 * rest event of the innerspecies objects.
	 * Must be called after all the inner elements are set.
	 */
	public void setOuterSpeciesCounterpointEvents(){
		/*
		 * There shouldn't be any counterpoint event associated with a species object if it has
		 * inner elements.
		 */
		for(SpeciesElement speciesElement : getBaseInnerElementsList()){
			CounterpointBarEvent innerCounterpointBarEvent = speciesElement.getCounterpointBarEvent();
			if(!innerCounterpointBarEvent.isRest()){
				counterpointBarEvent = innerCounterpointBarEvent;
				break;
			}
		}
	}
	
	public void addCounterpointAnnotationObject(CounterpointAnnotationObject counterpointAnnotationObject){
		if(counterpointAnnotationObjects == null){
			counterpointAnnotationObjects = new HashSet<CounterpointAnnotationObject>();
		}
		counterpointAnnotationObjects.add(counterpointAnnotationObject);
	}
	
	/**
	 * Indicates whether this species element is broken down into 
	 * smaller ones of a higher type. Eg 1st species containing 2
	 * 2nd species objects would return true while one that is just
	 * one whole 1 species note wouldn't contain any.
	 * @return
	 */
	public boolean hasInnerSpeciesElement(){
		return (innerSpeciesElements.size() > 0);
	}

	public int getLegnth() {
		if(counterpointBarEvent != null && SpeciesType.DECORATION.equals(speciesType)){
			return counterpointBarEvent.getEventLength();
		}
		return speciesInformation.getSpeciesElementTypeLength(speciesType);//xxx
	}

	public void setCounterpointBarEvent(
			CounterpointBarEvent aCounterpointBarEvent) {
		counterpointBarEvent = aCounterpointBarEvent;		
	}

	/**
	 * Returns the bar time location of where this species element would end.
	 * TODO Note - this value doesn't mean anything for decoration elements as the species
	 * type information only returns 1 for the getMaxNumberOfInnerSpeciesElements(SpeciesType speciesType)
	 * which is uded in the translator.
	 * @return
	 */
	public int getEndTimeLocation() {
		return (innerSpeciesElementSequenceNumber + 1)* speciesInformation.getSpeciesElementTypeLength(speciesType);
	}

	public SpeciesType getSpeciesType() {
		return speciesType;
	}

	public SpeciesInformation getSpeciesInformation() {
		return speciesInformation;
	}

	public void addInnerSpeciesObject(SpeciesElement innerSpeciesElement) {
			innerSpeciesElements.add(innerSpeciesElement);
	}

	public boolean isRest(){
		return counterpointBarEvent.isRest();
	}

	public Iterator<SpeciesElement> iterator() {
		return innerSpeciesElements.iterator();
	}

	public int getMidiValue() {
		return counterpointBarEvent.getMidiValue();
	}
		
	/**
	 * Returns all base species elements. If there is no inner elments
	 * then this object itself is returned.
	 * This creates the list if it doesn't already exist (Lazy loading).
	 * @return
	 */
	public List<SpeciesElement> getBaseInnerElementsList(){
		if(baseInnerElementsList == null){
			baseInnerElementsList = new ArrayList<SpeciesElement>();
			if(innerSpeciesElements.isEmpty()){
				baseInnerElementsList.add(this);
			}else{
				for(SpeciesElement speciesElement : innerSpeciesElements){
					baseInnerElementsList.addAll(speciesElement.getBaseInnerElementsList());
				}
			}
		}
		return baseInnerElementsList;
	}

	public CounterpointBarEvent getCounterpointBarEvent() {
		return counterpointBarEvent;
	}
	
	public int getStartTimeLocation(){
		return counterpointBarEvent.getTimeLocation();
	}

	public int getInnerSpeciesElementSequenceNumber() {
		return innerSpeciesElementSequenceNumber;
	}

	public int getTrackNumber() {
		return trackNumber;
	}

	public int getPitchSequenceValue() {
		return pitchSequenceValue;
	}

	public void setPitchSequenceValue(int pitchSequenceValue) {
		this.pitchSequenceValue = pitchSequenceValue;
	}

	public Collection<CounterpointAnnotationObject> getCounterpointAnnotationObjects() {
		return counterpointAnnotationObjects;
	}

	public Clef getClef() {
		return clef;
	}

	public PitchLineMidiMapper getPitchLineMidiMapper() {
		return pitchLineMidiMapper;
	}

	@Override
	public String toString() {
		return counterpointBarEvent.getStaveLineNote().toString();
	}

	public boolean isSameMidiValue(SpeciesElement speciesElement) {
		return this.getMidiValue() == speciesElement.getMidiValue();
	}

	
}






