package law.counterpoint.rules.reporting;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashSet;

/**
 * This class is used when creating a rule report in a rule object. Because 
 * the point location of where the CompositionBarEvent that the species
 * element will be translated to is unknown this class is required. A referece
 * of this object is kept in the rule report and in the species object. 
 * 
 * The rule report has the logic to draw the lines based on the point location
 * information in this object while the SpecesElemet/CompositionBarEvent object
 * will populate it with the point location information.
 * @author BAZ
 *
 */
public class CounterpointAnnotationObject {
	private Collection<Point> annotationPoints = new HashSet<Point>();

	/**
	 * Adds a point to be stored. This is called by the CompositionBarEvent
	 * which sends in it's point location.
	 * @param point
	 */
	public void addPoint(Point point) {
		annotationPoints.add(point);
	}

	/**
	 * returns a collection of all the points in this object.
	 * @return
	 */
	public Collection<Point> getAnnotationPoints() {
		return annotationPoints;
	}
	
	/**
	 * Returns the Points object as an array.
	 * @return
	 */
	public Point[] getPointsArray(){
		return annotationPoints.toArray(new Point[annotationPoints.size()]);
	}
}
