package law.raw.view.stavePanel.noteSelection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;
import law.raw.view.stavePanel.noteSelection.lengthValues.LengthValue;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;

public class CompositeNoteValue {
	
	private Set<WrittenNote> noteValues = new TreeSet<WrittenNote>();
	//The other properties of the passed in noteValue will be relevant to all of the others
	private WrittenNote noteValue;
	
	public CompositeNoteValue(final WrittenNote aNoteValue){
		noteValue = aNoteValue;
		if(aNoteValue != null){
			noteValues.add(aNoteValue);
		}
	}
	
	public WrittenNote getNoteValue() {
		return noteValue;
	}

	private void addNoteValue(WrittenNote aNoteValue){
		noteValues.add(aNoteValue);
	}
	
	public int getNumberof32ndNotes() {
		int numberOf32ndNotes = 0;
		for(WrittenNote noteValue : noteValues){
			numberOf32ndNotes += noteValue.getNumberof32ndNotes();
		}
		return numberOf32ndNotes;
	}
	
	/**
	 * This static method returns the Note or composite note that has the right number of
	 * 32nd notes.
	 * The aCompositeNoteValue is passed in to get the accidental value
	 * @param aRecipricolValue
	 * @return
	 */
	public static CompositeNoteValue getCompositeNoteValueWithNumberOf32ndNotes(
			final CompositeNoteValue aCompositeNoteValue, final int numberOf32ndNotes, 
			final LengthValue[] lengthValues){
		/*
		 * THE ONLY REASON aCompositeNoteValue IS PASSED IN HERE IS TO GET THE ACCIDENTAL
		 * PROPERTIES (AND ANY OTHERS THAT MAY BE ADDED IN FUTURE). This value
		 * is transfered in the NoteValue constructor.
		 */
		if(numberOf32ndNotes == 0){
			//if this is the case then there are no notes to write
			return null;
		}
		
		CompositeNoteValue compositeNoteValue = null;
		for(LengthValue lengthValue : lengthValues){

			int returnNoteNumberOf32ndNotes = lengthValue.getNumberof32ndNotes();
			if(compositeNoteValue != null){
				returnNoteNumberOf32ndNotes += compositeNoteValue.getNumberof32ndNotes();
			}

			int delta = numberOf32ndNotes - returnNoteNumberOf32ndNotes;
		
			if(delta == 0){
				//note matches
				if(compositeNoteValue == null){
					return new CompositeNoteValue(new WrittenNote(aCompositeNoteValue.getNoteValue(), lengthValue));
				}else{
					compositeNoteValue.addNoteValue(new WrittenNote(aCompositeNoteValue.getNoteValue(), lengthValue));
					return compositeNoteValue;
				}
					
			}else if(delta < 0){
				//note is too large so move onto the next one
				continue;
			}else if(delta > 0){
				/*
				 * This note is not large enough but by now we know that none of the notes will match 
				 * this number of 32nd notes exactly. So a composite Note will have to be returned. 
				 * This is found by adding smaller values on.
				 */
				if(compositeNoteValue == null){			
					compositeNoteValue = new CompositeNoteValue(new WrittenNote(aCompositeNoteValue.getNoteValue(), lengthValue));
				}else{
					compositeNoteValue.addNoteValue(new WrittenNote(aCompositeNoteValue.getNoteValue(), lengthValue));
				}
			}
		}
		return null;
	}

	public Iterator<WrittenNote> iterator() {
		return noteValues.iterator();
	}

	public Set<WrittenNote> getNoteValues() {
		return noteValues;
	}
}
