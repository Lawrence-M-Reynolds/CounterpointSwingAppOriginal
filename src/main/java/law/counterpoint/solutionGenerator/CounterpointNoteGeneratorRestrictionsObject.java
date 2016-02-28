package law.counterpoint.solutionGenerator;

import java.util.Collection;

import law.musicRelatedClasses.chord.Chord;
import law.raw.composition.components.trackComponents.VoicePitchRangeLimitObject;

/**
 * Takes all of the objects required to determine what notes a track is allowed to have based on
 * the harmony, pitch range value objects and any notes that aleady exist in the set bars.
 * @author BAZ
 *
 */
public class CounterpointNoteGeneratorRestrictionsObject {

	private Collection<Chord> chords;
	private VoicePitchRangeLimitObject voicePitchRangeLimitObject;

	public CounterpointNoteGeneratorRestrictionsObject(Collection<Chord> chords,
			VoicePitchRangeLimitObject voicePitchRangeLimitObject) {
		super();
		this.chords = chords;
		this.voicePitchRangeLimitObject = voicePitchRangeLimitObject;
	}
}
