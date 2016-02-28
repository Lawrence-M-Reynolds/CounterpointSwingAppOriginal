package law.counterpoint.rules.reporting;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This abstract class is extended in each rule which is then instantiated whenever
 * a rule is broken. The report comments which is passed through the constructor
 * will be displayed to the user on the Counterpoint.
 * 
 * In order to draw markings relating the offending notes/SpeciesElements a separate
 * object is used - the CounterointAnnontationObject. Although this isn't defined in 
 * this abstract class it is in the extended class and the drawResultMarkings() method
 * is overridden to draw feedback based on points held within these objects. 
 * 
 * These objects are set passed into the the SpeciesElements in question as well as
 * the concrete RuleReport Object. It is then carried over to the CompositionBarEvents
 * that the speciesElements are translated into. When they are drawn by the drawerer
 * the point is passed into the CompositionBarEvents which then passes it to the
 * annotation object. Because it is the same object as is held in the Rule report, 
 * drawResultMarkings(Graphics g) will know where to draw. 
 * @author BAZ
 *
 */
public abstract class RuleReport {

	private String reportComments;

	/**
	 * All implementing classes should make a call to this super constructor
	 * with the appropriate message
	 * @param reportComments
	 */
	public RuleReport(String reportComments) {
		super();
		this.reportComments = reportComments;
	}


	/**
	 * This method should be overriden to use CounterointAnnontationObjects
	 * to draw markings between different notes.
	 */
	public abstract void drawResultMarkings(Graphics g);

	/**
	 * Retrieves the comment associated with the broken rule.
	 * @return
	 */
	public String getReportComments() {
		return reportComments;
	}	
}
