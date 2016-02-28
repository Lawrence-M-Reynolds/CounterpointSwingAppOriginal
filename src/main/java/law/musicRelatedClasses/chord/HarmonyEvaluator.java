package law.musicRelatedClasses.chord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import law.musicRelatedClasses.key.KeyNote;
import law.musicRelatedClasses.key.MusicScale.ScaleDegree;
import law.musicRelatedClasses.note.NoteLetter;
/**
 * Holds all of the logic to generate chords.
 * @author BAZ
 *
 */
public class HarmonyEvaluator {

	/**UPDATE
	 * Takes a list of the key notes, which should be in ascending order of their
	 * scale degree number, and maps them to a collection of the key triad chords
	 * to which they could belong. 
	 * @param sortedKeyNoteList
	 * @return
	 */
	public static void  evaluateKeyNoteHarmonics(List<KeyNote> sortedKeyNoteList){
		sortedKeyNoteList.toArray();

		int listLength = sortedKeyNoteList.size();
		for(int i = 0; i < listLength; i++){
			/*
			 * This list must retain its order as it represents the notes
			 * function in the harmony. 
			 */
			//			List<KeyNote> keyChordNotes = new ArrayList<KeyNote>();
			Map<ScaleDegree, KeyNote> keyChordNotes = new HashMap<ScaleDegree, KeyNote>();
			//iterating through all of the harmonics of the root note to add to the
			//chord.
			for(int harmonicSequenceNumber = 0; harmonicSequenceNumber < 7; harmonicSequenceNumber++){
				//This gets every third note
				KeyNote chordNote = sortedKeyNoteList.get((i + (harmonicSequenceNumber*2)) % listLength);
				keyChordNotes.put(ScaleDegree.getScaleDegree(
						(harmonicSequenceNumber*2) % listLength), chordNote);
			}

			/*UPDATE
			 * Setting the chord into the root note so that the note can be used
			 * to select the chord.
			 */			
			KeyNote rootNote = keyChordNotes.get(ScaleDegree.TONIC);
			rootNote.setKeyNoteHarmonics(keyChordNotes);

		}
	


	}
}








