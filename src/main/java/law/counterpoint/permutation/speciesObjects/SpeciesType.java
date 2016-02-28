package law.counterpoint.permutation.speciesObjects;

public enum SpeciesType {
	FIRST,
	SECOND,
	THIRD,
	FOURTH,
	DECORATION;

	public static SpeciesType getNextHigherSpeciesType(final SpeciesType speciesType) {
		if(FIRST.equals(speciesType)){
			return SECOND;
		}else if(SECOND.equals(speciesType)){
			return THIRD;
		}else if(THIRD.equals(speciesType)){
			return DECORATION;
		}else{
			/*
			 * This should never happen but I'm throwing a runtime exception here so that it 
			 * will be easy to locate the problem if it does happen for some reason.
			 */
			throw new RuntimeException(){
				public String toString(){
					return "A call was made to SpeciesType.getNextHigherSpeciesType(" + speciesType 
							+ "). Species type " + speciesType + " doesn't have a next higher" +
									"type.";
				}
			};
		}
	}
}
