package law.raw.composition.components.trackComponents;

import java.io.Serializable;

import law.musicRelatedClasses.Clef;
import law.musicRelatedClasses.note.StaveLineNote;
import law.raw.composition.components.trackComponents.barComponents.PitchLineMidiMapper;

public class VoicePitchRangeLimitObject implements Serializable{
	private int maxRangePitchLineNumber;
	private int minRangePitchLineNumber;
	private Clef clef;

	public VoicePitchRangeLimitObject(Clef aClef, int maxRangePitchLineNumber,
			int minRangePitchLineNumber) {
		super();
		this.maxRangePitchLineNumber = maxRangePitchLineNumber;
		this.minRangePitchLineNumber = minRangePitchLineNumber;
		this.clef = aClef;
	}


}

