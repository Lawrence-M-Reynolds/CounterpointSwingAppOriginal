package law.raw.composition.components.trackComponents;

import law.musicRelatedClasses.key.MusicScale;
import law.musicRelatedClasses.time.TimeSignature;

public class CommonTrackEvent {

	private MusicScale key;
	private TimeSignature timeSignature;
	
	/**
	 * 
	 * @param aNumberOfBeatsPerBar
	 * @param aBeatValue
	 * @param aKey
	 */
	public CommonTrackEvent(final TimeSignature aTimeSignature, final MusicScale aKey){
		timeSignature = aTimeSignature;
		key = aKey;
	}

	public MusicScale getKey() {
		return key;
	}

	public TimeSignature getTimeSignature() {
		return timeSignature;
	}
}
