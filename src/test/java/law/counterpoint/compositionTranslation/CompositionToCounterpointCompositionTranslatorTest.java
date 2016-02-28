package law.counterpoint.compositionTranslation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import junit.framework.TestCase;
import law.counterpoint.counterpointComposition.CounterpointComposition;
import law.counterpoint.permutation.BarNotePermutation;
import law.counterpoint.permutation.speciesObjects.SpeciesElement;
import law.counterpoint.permutation.speciesObjects.SpeciesType;
import law.musicRelatedClasses.Clef;
import law.musicRelatedClasses.chord.Chord;
import law.musicRelatedClasses.key.Key;
import law.musicRelatedClasses.key.MusicScale;
import law.musicRelatedClasses.note.Note;
import law.musicRelatedClasses.note.NoteLetter;
import law.musicRelatedClasses.time.Tempo;
import law.musicRelatedClasses.time.TimeSignature;
import law.raw.composition.Composition;
import law.raw.composition.components.trackComponents.Bar;
import law.raw.composition.components.trackComponents.InstrumentType;
import law.raw.composition.components.tracks.InstrumentTrack;
import law.raw.view.stavePanel.noteSelection.CompositeNoteValue;
import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;

public class CompositionToCounterpointCompositionTranslatorTest 
//extends TestCase
{

//	public void testgenerateCounterpointCompositionFromComposition_44_1WholeNote(){
//		Composition composition = new Composition("Test Title", new Key(MusicScale.Diatonic_Major, new Note(NoteLetter.C, null)), 
//				new TimeSignature(4, NoteLengthValue.QuaterNote), new Tempo(70, NoteLengthValue.QuaterNote), 10, new HashMap<Integer, Collection<Chord>>());
//		
//		List<InstrumentTrack> instrumentTracks = new ArrayList<InstrumentTrack>();
//		List<Bar> aListOfBars = new ArrayList<Bar>();
//		Bar bar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), new Key(MusicScale.Diatonic_Major, 
//				new Note(NoteLetter.C, null)),  new Tempo(70, NoteLengthValue.QuaterNote), 0, 0);
//		bar.writeCompositeNoteValue(0, new CompositeNoteValue(new WrittenNote(NoteLengthValue.WholeNote, null)), 7, false, null);
//		InstrumentTrack instrumentTrack = new InstrumentTrack(0, "Test Track", InstrumentType.Piano);
//		instrumentTrack.addBar(bar);
//		instrumentTracks.add(instrumentTrack);
//		composition.setInstrumentTracks(instrumentTracks);
//		
//		CounterpointComposition resultCounterpointComposition = 
//		CompositionToCounterpointCompositionTranslator.generateCounterpointCompositionFromComposition(composition);
//		
//		List<BarNotePermutation> permutations = resultCounterpointComposition.getPermutations();
//		BarNotePermutation barNotePermutation = permutations.get(0);
//		List<SpeciesElement> firstSpeciesElements = barNotePermutation.getFirstSpeciesElementsForEachVoice();
//		
//		assertEquals(1, firstSpeciesElements.size());
//		
//		SpeciesElement speciesElement = firstSpeciesElements.get(0);
//		assertEquals(1, speciesElement.getBaseInnerElementsList().size());
//		assertEquals(SpeciesType.FIRST, speciesElement.getBaseInnerElementsList().get(0).getSpeciesType());
//		assertEquals(32, speciesElement.getLegnth());
//		assertEquals(speciesElement.getStartTimeLocation(), 0);
//	}
//	
//	public void testgenerateCounterpointCompositionFromComposition_44_2HalfNotes(){
//		Composition composition = new Composition("Test Title", new Key(MusicScale.Diatonic_Major, new Note(NoteLetter.C, null)), 
//				new TimeSignature(4, NoteLengthValue.QuaterNote), new Tempo(70, NoteLengthValue.QuaterNote), 10, new HashMap<Integer, Collection<Chord>>());
//		
//		List<InstrumentTrack> instrumentTracks = new ArrayList<InstrumentTrack>();
//		List<Bar> aListOfBars = new ArrayList<Bar>();
//		Bar bar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), new Key(MusicScale.Diatonic_Major, 
//				new Note(NoteLetter.C, null)),  new Tempo(70, NoteLengthValue.QuaterNote), 0, 0);
//		bar.writeCompositeNoteValue(0, new CompositeNoteValue(new WrittenNote(NoteLengthValue.HalfNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(16, new CompositeNoteValue(new WrittenNote(NoteLengthValue.HalfNote, null)), 7, false, null);
//		InstrumentTrack instrumentTrack = new InstrumentTrack(0, "Test Track", InstrumentType.Piano);
//		instrumentTrack.addBar(bar);
//		instrumentTracks.add(instrumentTrack);
//		composition.setInstrumentTracks(instrumentTracks);
//		
//		CounterpointComposition resultCounterpointComposition = 
//		CompositionToCounterpointCompositionTranslator.generateCounterpointCompositionFromComposition(composition);
//		
//		List<BarNotePermutation> permutations = resultCounterpointComposition.getPermutations();
//		BarNotePermutation barNotePermutation = permutations.get(0);
//		List<SpeciesElement> firstSpeciesElements = barNotePermutation.getFirstSpeciesElementsForEachVoice();
//		
//		//Number of tracks
//		assertEquals(1, firstSpeciesElements.size());
//		
//		SpeciesElement speciesElement = firstSpeciesElements.get(0);
//		assertEquals(2, speciesElement.getBaseInnerElementsList().size());
//		
//		SpeciesElement firstSecondSpeciesElement = speciesElement.getBaseInnerElementsList().get(0);
//		assertEquals(SpeciesType.SECOND, firstSecondSpeciesElement.getSpeciesType());
//		assertEquals(16, firstSecondSpeciesElement.getLegnth());
//		assertEquals(0, firstSecondSpeciesElement.getStartTimeLocation());
//		
//		SpeciesElement secondSecondSpeciesElement = speciesElement.getBaseInnerElementsList().get(1);
//		assertEquals(SpeciesType.SECOND, secondSecondSpeciesElement.getSpeciesType());
//		assertEquals(16, secondSecondSpeciesElement.getLegnth());
//		assertEquals(16, secondSecondSpeciesElement.getStartTimeLocation());
//	}
//
//	public void testgenerateCounterpointCompositionFromComposition_44_4QuarterNotes(){
//		Composition composition = new Composition("Test Title", new Key(MusicScale.Diatonic_Major, new Note(NoteLetter.C, null)), 
//				new TimeSignature(4, NoteLengthValue.QuaterNote), new Tempo(70, NoteLengthValue.QuaterNote), 10, new HashMap<Integer, Collection<Chord>>());
//		
//		List<InstrumentTrack> instrumentTracks = new ArrayList<InstrumentTrack>();
//		List<Bar> aListOfBars = new ArrayList<Bar>();
//		Bar bar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), new Key(MusicScale.Diatonic_Major, 
//				new Note(NoteLetter.C, null)),  new Tempo(70, NoteLengthValue.QuaterNote), 0, 0);
//		
//		/*
//		 * 0-8
//		 * 8-16
//		 * 16-24
//		 * 24-32 
//		 */
//		
//		bar.writeCompositeNoteValue(0, new CompositeNoteValue(new WrittenNote(NoteLengthValue.QuaterNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(8, new CompositeNoteValue(new WrittenNote(NoteLengthValue.QuaterNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(16, new CompositeNoteValue(new WrittenNote(NoteLengthValue.QuaterNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(24, new CompositeNoteValue(new WrittenNote(NoteLengthValue.QuaterNote, null)), 7, false, null);
//		InstrumentTrack instrumentTrack = new InstrumentTrack(0, "Test Track", InstrumentType.Piano);
//		instrumentTrack.addBar(bar);
//		instrumentTracks.add(instrumentTrack);
//		composition.setInstrumentTracks(instrumentTracks);
//		
//		CounterpointComposition resultCounterpointComposition = 
//		CompositionToCounterpointCompositionTranslator.generateCounterpointCompositionFromComposition(composition);
//		
//		List<BarNotePermutation> permutations = resultCounterpointComposition.getPermutations();
//		BarNotePermutation barNotePermutation = permutations.get(0);
//		List<SpeciesElement> firstSpeciesElements = barNotePermutation.getFirstSpeciesElementsForEachVoice();
//		
//		//Number of tracks
//		assertEquals(1, firstSpeciesElements.size());
//		
//		SpeciesElement speciesElement = firstSpeciesElements.get(0);
//		assertEquals(4, speciesElement.getBaseInnerElementsList().size());
//		
//		SpeciesElement firstThirdSpeciesElement = speciesElement.getBaseInnerElementsList().get(0);
//		assertEquals(SpeciesType.THIRD, firstThirdSpeciesElement.getSpeciesType());
//		assertEquals(8, firstThirdSpeciesElement.getLegnth());
//		assertEquals(firstThirdSpeciesElement.getStartTimeLocation(), 0);
//		
//		SpeciesElement secondThirdSpeciesElement = speciesElement.getBaseInnerElementsList().get(1);
//		assertEquals(SpeciesType.THIRD, secondThirdSpeciesElement.getSpeciesType());
//		assertEquals(8, secondThirdSpeciesElement.getLegnth());
//		assertEquals(secondThirdSpeciesElement.getStartTimeLocation(), 8);
//		
//		SpeciesElement thirdThirdSpeciesElement = speciesElement.getBaseInnerElementsList().get(2);
//		assertEquals(SpeciesType.THIRD, thirdThirdSpeciesElement.getSpeciesType());
//		assertEquals(8, thirdThirdSpeciesElement.getLegnth());
//		assertEquals(thirdThirdSpeciesElement.getStartTimeLocation(), 16);
//		
//		SpeciesElement fourthThirdSpeciesElement = speciesElement.getBaseInnerElementsList().get(3);
//		assertEquals(SpeciesType.THIRD, fourthThirdSpeciesElement.getSpeciesType());
//		assertEquals(8, fourthThirdSpeciesElement.getLegnth());
//		assertEquals(fourthThirdSpeciesElement.getStartTimeLocation(), 24);
//	}
//	
//	public void testgenerateCounterpointCompositionFromComposition_44_8EigthNotes(){
//		Composition composition = new Composition("Test Title", new Key(MusicScale.Diatonic_Major, new Note(NoteLetter.C, null)), 
//				new TimeSignature(4, NoteLengthValue.QuaterNote), new Tempo(70, NoteLengthValue.QuaterNote), 10, new HashMap<Integer, Collection<Chord>>());
//		
//		List<InstrumentTrack> instrumentTracks = new ArrayList<InstrumentTrack>();
//		List<Bar> aListOfBars = new ArrayList<Bar>();
//		Bar bar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), new Key(MusicScale.Diatonic_Major, 
//				new Note(NoteLetter.C, null)),  new Tempo(70, NoteLengthValue.QuaterNote), 0, 0);
//		
//		/*
//		 * 0-8
//		 * 8-16
//		 * 16-24
//		 * 24-32 
//		 */
//		
//		bar.writeCompositeNoteValue(0, new CompositeNoteValue(new WrittenNote(NoteLengthValue.EigthNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(4, new CompositeNoteValue(new WrittenNote(NoteLengthValue.EigthNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(8, new CompositeNoteValue(new WrittenNote(NoteLengthValue.EigthNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(12, new CompositeNoteValue(new WrittenNote(NoteLengthValue.EigthNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(16, new CompositeNoteValue(new WrittenNote(NoteLengthValue.EigthNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(20, new CompositeNoteValue(new WrittenNote(NoteLengthValue.EigthNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(24, new CompositeNoteValue(new WrittenNote(NoteLengthValue.EigthNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(28, new CompositeNoteValue(new WrittenNote(NoteLengthValue.EigthNote, null)), 7, false, null);
//		InstrumentTrack instrumentTrack = new InstrumentTrack(0, "Test Track", InstrumentType.Piano);
//		instrumentTrack.addBar(bar);
//		instrumentTracks.add(instrumentTrack);
//		composition.setInstrumentTracks(instrumentTracks);
//		
//		CounterpointComposition resultCounterpointComposition = 
//		CompositionToCounterpointCompositionTranslator.generateCounterpointCompositionFromComposition(composition);
//		
//		List<BarNotePermutation> permutations = resultCounterpointComposition.getPermutations();
//		BarNotePermutation barNotePermutation = permutations.get(0);
//		List<SpeciesElement> firstSpeciesElements = barNotePermutation.getFirstSpeciesElementsForEachVoice();
//		
//		//Number of tracks
//		assertEquals(1, firstSpeciesElements.size());
//		
//		SpeciesElement speciesElement = firstSpeciesElements.get(0);
//		assertEquals(8, speciesElement.getBaseInnerElementsList().size());
//		
//		Iterator<SpeciesElement> iter = speciesElement.getBaseInnerElementsList().iterator();
//		SpeciesElement innerSpeciesElement = iter.next();
//		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
//		assertEquals(4, innerSpeciesElement.getLegnth());
//		assertEquals(0, innerSpeciesElement.getStartTimeLocation());
//		
//		innerSpeciesElement = iter.next();
//		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
//		assertEquals(4, innerSpeciesElement.getLegnth());
//		assertEquals(4, innerSpeciesElement.getStartTimeLocation());
//		
//		innerSpeciesElement = iter.next();
//		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
//		assertEquals(4, innerSpeciesElement.getLegnth());
//		assertEquals(8, innerSpeciesElement.getStartTimeLocation());
//		
//		innerSpeciesElement = iter.next();
//		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
//		assertEquals(4, innerSpeciesElement.getLegnth());
//		assertEquals(12, innerSpeciesElement.getStartTimeLocation());
//		
//		innerSpeciesElement = iter.next();
//		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
//		assertEquals(4, innerSpeciesElement.getLegnth());
//		assertEquals(16, innerSpeciesElement.getStartTimeLocation());
//		
//		innerSpeciesElement = iter.next();
//		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
//		assertEquals(4, innerSpeciesElement.getLegnth());
//		assertEquals(20, innerSpeciesElement.getStartTimeLocation());
//		
//		innerSpeciesElement = iter.next();
//		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
//		assertEquals(4, innerSpeciesElement.getLegnth());
//		assertEquals(24, innerSpeciesElement.getStartTimeLocation());
//		
//		innerSpeciesElement = iter.next();
//		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
//		assertEquals(4, innerSpeciesElement.getLegnth());
//		assertEquals(28, innerSpeciesElement.getStartTimeLocation());
//	}
//	
//	public void testgenerateCounterpointCompositionFromComposition_44_OddNotes(){
//		Composition composition = new Composition("Test Title", new Key(MusicScale.Diatonic_Major, new Note(NoteLetter.C, null)), 
//				new TimeSignature(4, NoteLengthValue.QuaterNote), new Tempo(70, NoteLengthValue.QuaterNote), 10, new HashMap<Integer, Collection<Chord>>());
//		
//		List<InstrumentTrack> instrumentTracks = new ArrayList<InstrumentTrack>();
//		List<Bar> aListOfBars = new ArrayList<Bar>();
//		Bar bar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), new Key(MusicScale.Diatonic_Major, 
//				new Note(NoteLetter.C, null)),  new Tempo(70, NoteLengthValue.QuaterNote), 0, 0);
//		
//		/*
//		 * 0-4 eigth
//		 * 4-20 half
//		 * 20-28 quarter
//		 * 28-32 eigth
//		 */
//		
//		bar.writeCompositeNoteValue(0, new CompositeNoteValue(new WrittenNote(NoteLengthValue.EigthNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(4, new CompositeNoteValue(new WrittenNote(NoteLengthValue.HalfNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(20, new CompositeNoteValue(new WrittenNote(NoteLengthValue.QuaterNote, null)), 7, false, null);
//		bar.writeCompositeNoteValue(28, new CompositeNoteValue(new WrittenNote(NoteLengthValue.EigthNote, null)), 7, false, null);
//		InstrumentTrack instrumentTrack = new InstrumentTrack(0, "Test Track", InstrumentType.Piano);
//		instrumentTrack.addBar(bar);
//		instrumentTracks.add(instrumentTrack);
//		composition.setInstrumentTracks(instrumentTracks);
//		
//		CounterpointComposition resultCounterpointComposition = 
//		CompositionToCounterpointCompositionTranslator.generateCounterpointCompositionFromComposition(composition);
//		
//		List<BarNotePermutation> permutations = resultCounterpointComposition.getPermutations();
//		BarNotePermutation barNotePermutation = permutations.get(0);
//		List<SpeciesElement> firstSpeciesElements = barNotePermutation.getFirstSpeciesElementsForEachVoice();
//		
//		//Number of tracks
//		assertEquals(1, firstSpeciesElements.size());
//		
//		/*
//		 * This should be broken down into 7 base species objects:
//		 * Two decorations (wrapped by a third species):
//		 * eigth note
//		 * eigth note tied
//		 * 
//		 * one third species:
//		 * quarter note tied
//		 * 
//		 * two decorations (wrapped by a third species):
//		 * eigth note
//		 * eigth note tied
//		 * 
//		 * two decorations (wrapped by a third species):
//		 * eigth note
//		 * eigth note
//		 * 
//		 * FIXME - I haven't impelemented this the best way. I shoudl separate notes out into halfs of the
//		 * species element above.
//		 */
//		
////		SpeciesElement speciesElement = firstSpeciesElements.get(0);
////		assertEquals(7, speciesElement.getBaseInnerElementsList().size());
////		SpeciesElement innerSpeciesElement;
////		Iterator<SpeciesElement> iter = speciesElement.getBaseInnerElementsList().iterator();
////		
////		innerSpeciesElement = iter.next();
////		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
////		assertEquals(4, innerSpeciesElement.getLegnth());
////		assertEquals(0, innerSpeciesElement.getStartTimeLocation());
////		
////		innerSpeciesElement = iter.next();
////		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
////		assertEquals(7, innerSpeciesElement.getLegnth());
////		assertEquals(4, innerSpeciesElement.getStartTimeLocation());
////		
////		innerSpeciesElement = iter.next();
////		assertEquals(SpeciesType.THIRD, innerSpeciesElement.getSpeciesType());
////		assertEquals(8, innerSpeciesElement.getLegnth());
////		assertEquals(11, innerSpeciesElement.getStartTimeLocation());
////		
////		innerSpeciesElement = iter.next();
////		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
////		assertEquals(4, innerSpeciesElement.getLegnth());
////		assertEquals(0, innerSpeciesElement.getStartTimeLocation());
////		
////		innerSpeciesElement = iter.next();
////		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
////		assertEquals(4, innerSpeciesElement.getLegnth());
////		assertEquals(0, innerSpeciesElement.getStartTimeLocation());
////		
////		innerSpeciesElement = iter.next();
////		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
////		assertEquals(4, innerSpeciesElement.getLegnth());
////		assertEquals(0, innerSpeciesElement.getStartTimeLocation());
////		
////		innerSpeciesElement = iter.next();
////		assertEquals(SpeciesType.DECORATION, innerSpeciesElement.getSpeciesType());
////		assertEquals(4, innerSpeciesElement.getLegnth());
////		assertEquals(0, innerSpeciesElement.getStartTimeLocation());
//	}
}
