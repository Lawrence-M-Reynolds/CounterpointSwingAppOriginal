package law;

import law.musicRelatedClasses.note.StaveLineNote;
import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;

/**
 * This class was made so that the bar event objects on both the counterpoint
 * and composition side could be used polymorphically.
 * @author BAZ
 *
 */
public abstract class BarEvent {

	public abstract int getEventLength();

	public abstract boolean isTied();

	public abstract int getTimeLocation();

	public abstract int getStavePosition();

	public abstract WrittenNote getWrittenNote();

	public abstract boolean isRest();

	public abstract int getMidiValue();

	public abstract StaveLineNote getStaveLineNote();

}
