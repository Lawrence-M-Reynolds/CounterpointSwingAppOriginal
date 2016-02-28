package law.counterpoint;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

import law.counterpoint.compositionTranslation.Translator;
import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.evaluation.CounterpointEvaluater;
import law.counterpoint.permutation.BarNotePermutation;
import law.counterpoint.solutionGenerator.SolutionGenerator;
import law.raw.composition.Composition;
import law.raw.view.stavePanel.CounterpointOperationsUI;


public class CounterpointOperationsFacade {
	public static int MAX_NUMBER_OF_GENERATED_SOLUTIONS = 10000;
	
	private CounterpointOperationsUI counterpointOperationsTerminal;
	
	public CounterpointOperationsFacade(
			CounterpointOperationsUI aCounterpointOperationsTerminal) {
		counterpointOperationsTerminal = aCounterpointOperationsTerminal;
	}

	public List<Composition> generateFirstSpeciesSolutions(final Composition aCompositionDTO){
		counterpointOperationsTerminal.setMessage("Converting composition");
		/*
		 * First the permutations which will only be partially filled are generated from the composition. 
		 */
		CounterpointComposition convertedCounterpointComposition = 
				Translator.generateCounterpointCompositionFromComposition(aCompositionDTO);	

		counterpointOperationsTerminal.setMessage("Generating permutaitons");
		/*
		 * This list contains has a value for each step in the composition. These values are a collection
		 * of all the possible permutations of where the first species notes could go. 
		 */
		List<Collection<BarNotePermutation>> permutations;
		try {
			permutations = SolutionGenerator.generatePossiblePermutations(convertedCounterpointComposition);
		} catch (Exception e) {
			counterpointOperationsTerminal.setMessage("Every step must have at least one bar with a written" +
					" note in it.");
			return null;
		}

		counterpointOperationsTerminal.setMessage("Seeding Solutions");
		//create the CCO's with the first step permtuaitons here
		List<CounterpointComposition> counterpointCompositions = new ArrayList<CounterpointComposition>();
		Collection<BarNotePermutation> firstStepPermutations = permutations.remove(0);
		for(BarNotePermutation barNotePermutation : firstStepPermutations){
			CounterpointComposition generatedCounterpointComposition = 
					new CounterpointComposition(convertedCounterpointComposition, barNotePermutation);
			counterpointCompositions.add(generatedCounterpointComposition);
		}
		firstStepPermutations = null;
		counterpointOperationsTerminal.setMessage("Processing solutions");
		/*
		 * Have a for loop here which adds the next steps to each of those by going through the algorthm.
		 * The object responsible for doing that will then return the partially created CCO's and they
		 * will be sent off for evaluation.
		 */
		ListIterator<Collection<BarNotePermutation>> permutationListIter = permutations.listIterator();
		
		boolean enableMaxNumberOfGeneratedSolutionsLimit = 
				counterpointOperationsTerminal.isMaxCompositionLimitEnabled();
		int maxNumberOfGeneratedSolutions = counterpointOperationsTerminal.getMaxNumberOfGeneratedSolutions();
		while(permutationListIter.hasNext()){
			Collection<BarNotePermutation> barNotePermutations = permutationListIter.next();
			//May help with heap space issues to remove the permutations
			permutationListIter.remove();
			List<CounterpointComposition> evaluatedCounterpointCompositions = new ArrayList<CounterpointComposition>();
			//Iterate through one counterpoint composition at a time rather than building all of the permutations for
			//all of them at once. This requires less memory.
			int compositionCount = 0;
			try{
				for(CounterpointComposition counterpointComposition : counterpointCompositions){
					List<CounterpointComposition> constructedCounterpointCompositions = null;
					//Take the counterpoint composition and create news ones from it adding each permutation
					//to it.
					constructedCounterpointCompositions = 
							SolutionGenerator.buildFirstSpeciesCounterpointSolutionsForNextStep(
									barNotePermutations, counterpointComposition);


					String message = "Permutations left = " + permutations.size() + " --> Composition " + ++compositionCount + 
							" out of " + counterpointCompositions.size();

					counterpointOperationsTerminal.setMessage(message);
					//Evaluate each of the new counterpoint compositions
					constructedCounterpointCompositions = CounterpointEvaluater.evaluateGeneratedCounterpointSolutions(
							constructedCounterpointCompositions, enableMaxNumberOfGeneratedSolutionsLimit);

					//Add the evaluated solutions to the outer List where they are sorted by their 
					//success scoring. A set number of the best is then taken through with the others
					//being discarded.
					evaluatedCounterpointCompositions.addAll(constructedCounterpointCompositions);
					if(evaluatedCounterpointCompositions.size() > maxNumberOfGeneratedSolutions
							&& enableMaxNumberOfGeneratedSolutionsLimit){
						Collections.sort(evaluatedCounterpointCompositions);
						evaluatedCounterpointCompositions = new ArrayList<CounterpointComposition>(
								evaluatedCounterpointCompositions.subList(0, maxNumberOfGeneratedSolutions));
					}
				}
			}catch(OutOfMemoryError outOfMemoryError){
				//Too many possibilites so report back to the user.
				counterpointOperationsTerminal.setMessage("System ran out of memory. Please lower the search space by" +
						" either lowering the number of voices, placing more notes, declaring\n more harmonies or decreasing" +
						" the maximum number of generated solutions size.");
				return null;
			}
			//The most successful will either pass onto the next iteration to build more
			//solutions or be passed back out for translation.
			counterpointCompositions = evaluatedCounterpointCompositions;
		}
		permutations = null;
		permutationListIter = null;
		//Don't want to translate more than 100 back as that's more than will be looked at
		counterpointCompositions = new ArrayList<CounterpointComposition>(
				counterpointCompositions.subList(0, 100));
		/*
		 * At this point the solutions are fully built and they just need to be converted
		 * into CompositionDTOs that the front end can use. The CounterpointComposition
		 * implments comparable so that they are ordered according to how high their
		 * score is. This ensures that the returned solutions are in the right order
		 * and the best can be selected from the top.
		 */
		List<Composition> solutions = new ArrayList<Composition>();
		for(CounterpointComposition  counterpointComposition : counterpointCompositions){
			solutions.add(Translator.generateCompositionDTOFromCounterpointComposition(counterpointComposition));
			System.out.println(counterpointComposition.getEvaluationScoringObject().getOverallScore());
		}
		counterpointCompositions = null;
		counterpointOperationsTerminal.setMessage("Complete");
		return solutions;		
	}

	public Composition evaluateComposition(Composition composition) {
		CounterpointComposition convertedCounterpointComposition = 
				Translator.generateCounterpointCompositionFromComposition(composition);
		/*
		 * Analyse convertedBarNotePermutations, convert back to CO and return it.
		 */
		CounterpointEvaluater.evaluateCounterpointComposition(convertedCounterpointComposition);
		
		return Translator.generateCompositionDTOFromCounterpointComposition(convertedCounterpointComposition);
		
	}
}
