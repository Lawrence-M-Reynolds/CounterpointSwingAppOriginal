package law.counterpoint.permutation.analysisObjects.harmonicAnalysis;

import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.counterpoint.permutation.speciesObjects.SpeciesType;

public class HarmonicAnalysisPairingKey {
	protected SpeciesElement firstSpeciesElementFromVoice1;
	protected SpeciesElement firstSpeciesElementFromVoice2;
	/**
	 * @param firstSpeciesElementFromVoice1
	 * @param firstSpeciesElementFromVoice2
	 * @param speciesType
	 */
	public HarmonicAnalysisPairingKey(
			SpeciesElement firstSpeciesElementFromVoice1,
			SpeciesElement firstSpeciesElementFromVoice2) {
		super();
		this.firstSpeciesElementFromVoice1 = firstSpeciesElementFromVoice1;
		this.firstSpeciesElementFromVoice2 = firstSpeciesElementFromVoice2;
	}

	/**
	 * Implemented as described by:
	 * http://www.ibm.com/developerworks/java/library/j-jtp05273/index.html
	 */
	@Override
	public int hashCode() {
	    int hash = 1;
	    hash = hash * 15 + firstSpeciesElementFromVoice1.getMidiValue();
	    hash = hash * 15 + firstSpeciesElementFromVoice2.getMidiValue();
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
		boolean firstVoiceCheck = this.firstSpeciesElementFromVoice1.equals(harmonicAnalysisPairingKey.firstSpeciesElementFromVoice1)
				|| this.firstSpeciesElementFromVoice2.equals(harmonicAnalysisPairingKey.firstSpeciesElementFromVoice1);
		
		boolean secondVoiceCheck = this.firstSpeciesElementFromVoice1.equals(harmonicAnalysisPairingKey.firstSpeciesElementFromVoice2)
				|| this.firstSpeciesElementFromVoice2.equals(harmonicAnalysisPairingKey.firstSpeciesElementFromVoice2);
		
		if(firstVoiceCheck && secondVoiceCheck){
			return true;
		}
		return false;
	}
	
	
	
}
