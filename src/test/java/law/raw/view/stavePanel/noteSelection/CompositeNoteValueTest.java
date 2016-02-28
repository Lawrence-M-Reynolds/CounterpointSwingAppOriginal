//package law.raw.view.stavePanel.noteSelection;
//
//import java.util.Iterator;
//import java.util.TreeSet;
//
//import junit.framework.TestCase;
//import law.raw.view.stavePanel.noteSelection.enumeration.NoteValue;
//import law.raw.view.stavePanel.noteSelection.lengthValues.RestLengthValue;
//
//public class CompositeNoteValueTest extends TestCase {
//	//Testing CompositeNoteValue with one NoteValue
//	public void testGetCompositeNoteValueWithNumberOf32ndNotesWithOne32ndNote(){
//		CompositeNoteValue aCompositeNoteValue = 
//				CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(1, RestLengthValue.values());
//		
//		TreeSet<NoteValue> noteValues = aCompositeNoteValue.getNoteValues();
//		
//		assertEquals(1, noteValues.size());
//		assertEquals(RestLengthValue.ThirtySecondRest, noteValues.first());
//	}
//	
//	public void testGetCompositeNoteValueWithNumberOf32ndNotesWithOne16thNote(){
//		CompositeNoteValue aCompositeNoteValue = 
//				CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(2, RestLengthValue.values());
//		
//		TreeSet<NoteValue> noteValues = aCompositeNoteValue.getNoteValues();
//		
//		assertEquals(1, noteValues.size());
//		assertEquals(RestLengthValue.SixteenthRest, noteValues.first());
//	}
//	
//	public void testGetCompositeNoteValueWithNumberOf32ndNotesWithOne8thNote(){
//		CompositeNoteValue aCompositeNoteValue = 
//				CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(4, RestLengthValue.values());
//		
//		TreeSet<NoteValue> noteValues = aCompositeNoteValue.getNoteValues();
//		
//		assertEquals(1, noteValues.size());
//		assertEquals(RestLengthValue.EigthRest, noteValues.first());
//	}
//	
//	public void testGetCompositeNoteValueWithNumberOf32ndNotesWithOneQuarterNote(){
//		CompositeNoteValue aCompositeNoteValue = 
//				CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(8, RestLengthValue.values());
//		
//		TreeSet<NoteValue> noteValues = aCompositeNoteValue.getNoteValues();
//		
//		assertEquals(1, noteValues.size());
//		assertEquals(RestLengthValue.QuaterRest, noteValues.first());
//	}
//	
//	public void testGetCompositeNoteValueWithNumberOf32ndNotesWithOneHalfNote(){
//		CompositeNoteValue aCompositeNoteValue = 
//				CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(16, RestLengthValue.values());
//		
//		TreeSet<NoteValue> noteValues = aCompositeNoteValue.getNoteValues();
//		
//		assertEquals(1, noteValues.size());
//		assertEquals(RestLengthValue.HalfRest, noteValues.first());
//	}
//	
//	public void testGetCompositeNoteValueWithNumberOf32ndNotesWithOneWholeNote(){
//		CompositeNoteValue aCompositeNoteValue = 
//				CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(32, RestLengthValue.values());
//		
//		TreeSet<NoteValue> noteValues = aCompositeNoteValue.getNoteValues();
//		
//		assertEquals(1, noteValues.size());
//		assertEquals(RestLengthValue.WholeRest, noteValues.first());
//	}
//	
//	//Testing CompositeNoteValue with more than one NoteValue
//	
//	//Equivalent to 3/4 time signature
//	//This should break down to a half note and a quarter note
//	public void testGetCompositeNoteValueWithNumberOf32ndNotesWith(){
//		CompositeNoteValue aCompositeNoteValue = 
//				CompositeNoteValue.getCompositeNoteValueWithNumberOf32ndNotes(24, RestLengthValue.values());
//		
//		TreeSet<NoteValue> noteValues = aCompositeNoteValue.getNoteValues();
//		
//		assertEquals(2, noteValues.size());
//		Iterator<NoteValue> iter = aCompositeNoteValue.iterator();
//		assertEquals(RestLengthValue.HalfRest, iter.next());
//		assertEquals(RestLengthValue.QuaterRest, iter.next());
//	}
//}
//
//
//
//
//
//
//
//
