package law.counterpoint.permutation.analysisObjects.harmonicAnalysis;

import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.counterpoint.permutation.speciesObjects.SpeciesType;

public class HarmonicAnalysisPairingKey {
	protected int firstTrackNumber;
	protected int secondTrackNumber;

	public HarmonicAnalysisPairingKey(SpeciesElement firstSpeciesElement,
			SpeciesElement secondSpeciesElement) {
		super();
		this.firstTrackNumber = firstSpeciesElement.getTrackNumber();
		this.secondTrackNumber = secondSpeciesElement.getTrackNumber();
	}


	/**
	 * Implemented as described by:
	 * http://www.ibm.com/developerworks/java/library/j-jtp05273/index.html
	 */
	@Override
	public int hashCode() {
	    int hash = 1;
	    hash = hash * 15 + firstTrackNumber;
	    hash = hash * 15 + secondTrackNumber;
	    return hash;
	}
	

	/**
	 * It doesn't matter if the voices are placed in different instance variables
	 * as it checks if either one is in either location. 
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof HarmonicAnalysisPairingKey)){
			return false;
		}
		HarmonicAnalysisPairingKey harmonicAnalysisPairingKey = (HarmonicAnalysisPairingKey)obj;
		boolean firstVoiceCheck = firstTrackNumber == harmonicAnalysisPairingKey.firstTrackNumber
				|| firstTrackNumber == harmonicAnalysisPairingKey.secondTrackNumber;
		
		boolean secondVoiceCheck = secondTrackNumber == harmonicAnalysisPairingKey.firstTrackNumber
				|| secondTrackNumber == harmonicAnalysisPairingKey.secondTrackNumber;
		
		if(firstVoiceCheck && secondVoiceCheck){
			return true;
		}
		return false;
	}
	
	
	
}
