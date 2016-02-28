package law.counterpoint.compositionTranslation;

import java.util.List;

import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.permutation.BarNotePermutation;
import law.raw.composition.Composition;

public class Translator {

	public static CounterpointComposition generateCounterpointCompositionFromComposition(Composition aCompositionDTO){
		return CompositionToCounterpointCompositionTranslator.generateCounterpointCompositionFromComposition(aCompositionDTO);
	}
	
	public static Composition generateCompositionDTOFromCounterpointComposition(CounterpointComposition counterpointComposition){
		return CounterpointCompositionToCompositionTranslator.generateCompositionDTOFromCounterpointComposition(counterpointComposition);
	}
}
