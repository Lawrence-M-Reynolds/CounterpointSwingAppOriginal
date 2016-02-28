package law.counterpoint.permutation;

import law.musicRelatedClasses.time.TimeSignature;
/**
 * This contains the information about the compound bar to which the separeted bars belong.
 * It holds the time signature and the the location within in it that this bar
 * would be according to its sequence number.
 * @author BAZ
 *
 */
public class CompoundBarLinkInformation {
	private TimeSignature timeSignature;
	private int sequenceLocationInCompoundBar;
	/**
	 * @param timeSignature
	 * @param sequenceLocationInCompoundBar
	 */
	public CompoundBarLinkInformation(TimeSignature timeSignature,
			int sequenceLocationInCompoundBar) {
		super();
		this.timeSignature = timeSignature;
		this.sequenceLocationInCompoundBar = sequenceLocationInCompoundBar;
	}
}
