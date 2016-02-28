package law.counterpoint.counterpointComposition;

import java.util.List;

import law.counterpoint.exception.InvalidCounterpointSolutionException;
import law.counterpoint.exception.UnsupportedTimeSignatureException;
import law.raw.composition.Composition;

public abstract class AbstractCounterpointComposition {
	
	/**
	This constructor is used when a user submits a solution of theirs to be evaluated. The
	Compositiono object is passed into the constructor so that an equivalent 
	CounterpointCompositionObject is created. In doing so it determines the species at
	each bar for each voice and if it can't recognise any parts it will throw an exception.
	This is then reported back to the user.
	
	This will also be used for the generator. A partial compostion DTO is submitted by the
	user and the 
	 * @throws UnsupportedTimeSignatureException 
	*/
	public AbstractCounterpointComposition(Composition compositionDTO) throws InvalidCounterpointSolutionException, UnsupportedTimeSignatureException{

		
		compositionDTO.getNumberOfTracks();
		compositionDTO.getTimeSignature();
		
		/*
		Logic will go here to:
		- 
		- identify the species of counterpoint used for each melody at each bar. Any failures will result in
		a invalidCounterpointSolutionException being thrown.
		- Convert the bars into permutation objects and any other details that may be necessary. 
		- Add each note to the melody objects as well of which there should be one of for each voice in the
		composition object.
		*/
	}
}
