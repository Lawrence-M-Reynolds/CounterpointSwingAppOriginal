package law.counterpoint.rules.reporting;

import java.util.ArrayList;
import java.util.List;

/**UPDATE
 * This class holds the rule reports which have information on how the
 * rules have been broken, but also holds scoring values based on them
 * which is used in the generator to dispose of the weaker solutions.
 * @author BAZ
 *
 */
public class EvaluationScoringObject {
	
	//Various scoring values
	//The lower this value the better - a score of zero would mean that no rules
	//were broken.
	private int overallScore = 0;
	private boolean totalReject = false;

	public int getOverallScore() {
		return overallScore;
	}
	
	public void addToScore(int scoring){
		overallScore += scoring;
	}

	public boolean isTotalReject() {
		return totalReject;
	}

	public void setTotalReject(boolean totalReject) {
		this.totalReject = totalReject;
	}

	public void reset() {
		overallScore = 0;
		totalReject = false;		
	}

	@Override
	public String toString() {
		return "EvaluationScoringObject [overallScore=" + overallScore;
	}
}
