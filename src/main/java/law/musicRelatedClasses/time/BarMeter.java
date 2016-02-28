package law.musicRelatedClasses.time;


public enum BarMeter {

	BINARY(2),
	TERNARY(3),
	COMPOUND(null);
	
	private Integer numericalValue;
	
	
	BarMeter(Integer aNumericalValue){
		numericalValue = aNumericalValue;
	}
	/**
	 * This method returns the Bar meter type given the time signature.
	 * @param aTimeSignature
	 * @return
	 */
	public static BarMeter getBarMeterFromTimeSignature(TimeSignature aTimeSignature){
		int beatsPerBar = aTimeSignature.getNumberOfBeatsPerBar();
		
		if(beatsPerBar % 2 == 0){
			return BINARY;
		}else if(beatsPerBar % 3 == 0){
			return TERNARY;
		}else{
			return COMPOUND;
		}
	}
	public Integer getNumericalValue() {
		return numericalValue;
	}
}
