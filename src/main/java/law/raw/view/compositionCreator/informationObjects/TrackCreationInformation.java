package law.raw.view.compositionCreator.informationObjects;

import law.musicRelatedClasses.Clef;
import law.raw.composition.components.trackComponents.VoicePitchRangeLimitObject;

public class TrackCreationInformation {
	private Clef clef;
	private int trackNumber;
	private VoicePitchRangeLimitObject voicePitchRangeLimitObject;
	

	public TrackCreationInformation(Clef clef, int trackNumber,
			VoicePitchRangeLimitObject voicePitchRangeLimitObject) {
		super();
		this.clef = clef;
		this.trackNumber = trackNumber;
		this.voicePitchRangeLimitObject = voicePitchRangeLimitObject;
	}

	public Clef getClef() {
		return clef;
	}

	public Integer getTrackNumber() {
		return trackNumber;
	}

	public VoicePitchRangeLimitObject getVoicePitchRangeLimitObject() {
		return voicePitchRangeLimitObject;
	}

}
