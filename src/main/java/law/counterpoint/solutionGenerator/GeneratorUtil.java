package law.counterpoint.solutionGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import law.counterpoint.counterpointComposition.CounterpointComposition;

public class GeneratorUtil {
	public static int SOLUTION_LIST_FRAGMENTATION_SIZE = 1000;

	public static Collection<List<CounterpointComposition>> fragmentList(
			List<CounterpointComposition> solutionList){
		Collection<List<CounterpointComposition>> listFragments = 
				new ArrayList<List<CounterpointComposition>>();

		boolean keepFragmenting = true;
		int loopCount = 0;
		while(keepFragmenting){
			int x = loopCount * SOLUTION_LIST_FRAGMENTATION_SIZE;
			if(solutionList.size() > x){
				List<CounterpointComposition> fragment = 
						new ArrayList<CounterpointComposition>(
								solutionList.subList(x, (x + SOLUTION_LIST_FRAGMENTATION_SIZE)));
				listFragments.add(fragment);
			}else{
				listFragments.add(solutionList);
				keepFragmenting = false;
			}
		}
		
		return listFragments;
		
	}
}
