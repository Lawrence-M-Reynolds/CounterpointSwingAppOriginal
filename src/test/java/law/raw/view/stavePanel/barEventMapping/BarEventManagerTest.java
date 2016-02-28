//package law.raw.view.stavePanel.barEventMapping;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//import junit.framework.TestCase;
//import law.raw.composition.components.InstrumentTrack;
//import law.raw.composition.components.trackComponents.Bar;
//import law.raw.composition.components.trackComponents.InstrumentType;
//import law.raw.composition.components.trackComponents.barComponents.BarEvent;
//import law.raw.composition.components.trackComponents.barComponents.Clef;
//import law.raw.composition.components.trackComponents.commonTrackEventComponents.DiatonicScale;
//import law.raw.composition.components.trackComponents.commonTrackEventComponents.Tempo;
//import law.raw.composition.components.trackComponents.commonTrackEventComponents.TimeSignature;
//import law.raw.view.stavePanel.noteSelection.CompositeNoteValue;
//import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;
//import law.raw.view.stavePanel.noteSelection.lengthValues.RestLengthValue;
//
//public class BarEventManagerTest extends TestCase{
//	
//	public void testOverWriteWholeRestWithWholeNoteIn44Bar(){
//		List<Bar> bars = new ArrayList<Bar>();
//		InstrumentTrack track = new InstrumentTrack(bars, "TestTrack", InstrumentType.Piano);
//		Bar firstBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 0);
//		Bar secondBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 1);
//		
//		bars.add(firstBar);
//		bars.add(secondBar);
//		
//		track.setBars(bars);
//		
//		
//		
//		CompositeNoteValue aCompositeNoteValue = new CompositeNoteValue(NoteLengthValue.WholeNote);
//		//get the first bar event to be written over
//		BarEvent aBarEvent = firstBar.getFirstBarEvent();
//		int stavePositionWrittentTo = 5;
//		BarEventManager.sortBarEvents(track, firstBar, aBarEvent, aCompositeNoteValue, stavePositionWrittentTo);
//		
//		assertEquals(1, firstBar.getNumberOfEventsInBar());
//		assertEquals(1, secondBar.getNumberOfEventsInBar());
//		Iterator<BarEvent> firstBarIterator = firstBar.iterator();
//		assertEquals(new BarEvent(0, NoteLengthValue.WholeNote, stavePositionWrittentTo, false), firstBarIterator.next());
//		Iterator<BarEvent> secondBarIterator = secondBar.iterator();
//		assertEquals(new BarEvent(0, RestLengthValue.WholeRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), secondBarIterator.next());
//		
//	}
//	
//	public void testOverWriteWholeRestWithHalfNoteIn44Bar(){
//		List<Bar> bars = new ArrayList<Bar>();
//		InstrumentTrack track = new InstrumentTrack(bars, "TestTrack", InstrumentType.Piano);
//		Bar firstBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 0);
//		Bar secondBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 1);
//		
//		bars.add(firstBar);
//		bars.add(secondBar);
//		
//		track.setBars(bars);
//		
//		CompositeNoteValue aCompositeNoteValue = new CompositeNoteValue(NoteLengthValue.HalfNote);
//		//get the first bar event to be written over
//		BarEvent aBarEvent = firstBar.getFirstBarEvent();
//		int stavePositionWrittentTo = 5;
//		BarEventManager.sortBarEvents(track, firstBar, aBarEvent, aCompositeNoteValue, stavePositionWrittentTo);
//		
//		assertEquals(2, firstBar.getNumberOfEventsInBar());
//		assertEquals(1, secondBar.getNumberOfEventsInBar());
//		
//		Iterator<BarEvent> firstBarIterator = firstBar.iterator();
//		assertEquals(new BarEvent(0, NoteLengthValue.HalfNote, stavePositionWrittentTo, false), firstBarIterator.next());
//		assertEquals(new BarEvent(16, RestLengthValue.HalfRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//
//		Iterator<BarEvent> secondBarIterator = secondBar.iterator();
//		assertEquals(new BarEvent(0, RestLengthValue.WholeRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), secondBarIterator.next());
//		
//	}
//	
//	public void testOverWriteWholeRestWithQuarterNoteIn44Bar(){
//		List<Bar> bars = new ArrayList<Bar>();
//		InstrumentTrack track = new InstrumentTrack(bars, "TestTrack", InstrumentType.Piano);
//		Bar firstBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 0);
//		Bar secondBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 1);
//		
//		bars.add(firstBar);
//		bars.add(secondBar);
//		
//		track.setBars(bars);
//		
//		
//		
//		CompositeNoteValue aCompositeNoteValue = new CompositeNoteValue(NoteLengthValue.QuaterNote);
//		//get the first bar event to be written over
//		BarEvent aBarEvent = firstBar.getFirstBarEvent();
//		int stavePositionWrittentTo = 5;
//		BarEventManager.sortBarEvents(track, firstBar, aBarEvent, aCompositeNoteValue, stavePositionWrittentTo);
//		
//		assertEquals(3, firstBar.getNumberOfEventsInBar());
//		assertEquals(1, secondBar.getNumberOfEventsInBar());
//		
//		Iterator<BarEvent> firstBarIterator = firstBar.iterator();
//		assertEquals(new BarEvent(0, NoteLengthValue.QuaterNote, stavePositionWrittentTo, false), firstBarIterator.next());
//		assertEquals(new BarEvent(8, RestLengthValue.HalfRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//		assertEquals(new BarEvent(24, RestLengthValue.QuaterRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//
//		Iterator<BarEvent> secondBarIterator = secondBar.iterator();
//		assertEquals(new BarEvent(0, RestLengthValue.WholeRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), secondBarIterator.next());
//		
//	}
//	
//	public void testOverWriteWholeRestWithEigthNoteIn44Bar(){
//		List<Bar> bars = new ArrayList<Bar>();
//		InstrumentTrack track = new InstrumentTrack(bars, "TestTrack", InstrumentType.Piano);
//		Bar firstBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 0);
//		Bar secondBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 1);
//		
//		bars.add(firstBar);
//		bars.add(secondBar);
//		
//		track.setBars(bars);
//		
//		
//		
//		CompositeNoteValue aCompositeNoteValue = new CompositeNoteValue(NoteLengthValue.EigthNote);
//		//get the first bar event to be written over
//		BarEvent aBarEvent = firstBar.getFirstBarEvent();
//		int stavePositionWrittentTo = 5;
//		BarEventManager.sortBarEvents(track, firstBar, aBarEvent, aCompositeNoteValue, stavePositionWrittentTo);
//		
//		assertEquals(4, firstBar.getNumberOfEventsInBar());
//		assertEquals(1, secondBar.getNumberOfEventsInBar());
//		
//		/*
//		 * eigth (0) -> Half (4)-> quarter (20) -> eigth (28)
//		 */
//		
//		Iterator<BarEvent> firstBarIterator = firstBar.iterator();
//		assertEquals(new BarEvent(0, NoteLengthValue.EigthNote, stavePositionWrittentTo, false), firstBarIterator.next());
//		assertEquals(new BarEvent(4, RestLengthValue.HalfRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//		assertEquals(new BarEvent(20, RestLengthValue.QuaterRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//		assertEquals(new BarEvent(28, RestLengthValue.EigthRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//
//		Iterator<BarEvent> secondBarIterator = secondBar.iterator();
//		assertEquals(new BarEvent(0, RestLengthValue.WholeRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), secondBarIterator.next());
//		
//	}
//	
//	public void testOverWriteWholeRestWithSixteenthNoteIn44Bar(){
//		List<Bar> bars = new ArrayList<Bar>();
//		InstrumentTrack track = new InstrumentTrack(bars, "TestTrack", InstrumentType.Piano);
//		Bar firstBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 0);
//		Bar secondBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 1);
//		
//		bars.add(firstBar);
//		bars.add(secondBar);
//		
//		track.setBars(bars);
//		
//		
//		
//		CompositeNoteValue aCompositeNoteValue = new CompositeNoteValue(NoteLengthValue.SixteenthNote);
//		//get the first bar event to be written over
//		BarEvent aBarEvent = firstBar.getFirstBarEvent();
//		int stavePositionWrittentTo = 5;
//		BarEventManager.sortBarEvents(track, firstBar, aBarEvent, aCompositeNoteValue, stavePositionWrittentTo);
//		
//		assertEquals(5, firstBar.getNumberOfEventsInBar());
//		assertEquals(1, secondBar.getNumberOfEventsInBar());
//		
//		/*
//		 * Sixteenth (0-2) -> Half (2-18)-> quarter (18-26) -> eigth(26-30) -> sixteenth (30-32)
//		 */
//		
//		Iterator<BarEvent> firstBarIterator = firstBar.iterator();
//		assertEquals(new BarEvent(0, NoteLengthValue.SixteenthNote, stavePositionWrittentTo, false), firstBarIterator.next());
//		assertEquals(new BarEvent(2, RestLengthValue.HalfRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//		assertEquals(new BarEvent(18, RestLengthValue.QuaterRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//		assertEquals(new BarEvent(26, RestLengthValue.EigthRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//		assertEquals(new BarEvent(30, RestLengthValue.SixteenthRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//
//		Iterator<BarEvent> secondBarIterator = secondBar.iterator();
//		assertEquals(new BarEvent(0, RestLengthValue.WholeRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), secondBarIterator.next());
//		
//	}
//	
//	public void testOverWriteWholeRestWithThirtySecondNoteIn44Bar(){
//		List<Bar> bars = new ArrayList<Bar>();
//		InstrumentTrack track = new InstrumentTrack(bars, "TestTrack", InstrumentType.Piano);
//		Bar firstBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 0);
//		Bar secondBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 1);
//		
//		bars.add(firstBar);
//		bars.add(secondBar);
//		
//		track.setBars(bars);
//		
//		
//		
//		CompositeNoteValue aCompositeNoteValue = new CompositeNoteValue(NoteLengthValue.ThirtySecondNote);
//		//get the first bar event to be written over
//		BarEvent aBarEvent = firstBar.getFirstBarEvent();
//		int stavePositionWrittentTo = 5;
//		BarEventManager.sortBarEvents(track, firstBar, aBarEvent, aCompositeNoteValue, stavePositionWrittentTo);
//		
//		assertEquals(6, firstBar.getNumberOfEventsInBar());
//		assertEquals(1, secondBar.getNumberOfEventsInBar());
//		
//		/*
//		 * ThirtySecond (0-1) -> Half (1-17)-> quarter (17-25) -> eigth(25-29) -> sixteenth (29-31) -> thirtysecond
//		 */
//		
//		Iterator<BarEvent> firstBarIterator = firstBar.iterator();
//		assertEquals(new BarEvent(0, NoteLengthValue.ThirtySecondNote, stavePositionWrittentTo, false), firstBarIterator.next());
//		assertEquals(new BarEvent(1, RestLengthValue.HalfRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//		assertEquals(new BarEvent(17, RestLengthValue.QuaterRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//		assertEquals(new BarEvent(25, RestLengthValue.EigthRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//		assertEquals(new BarEvent(29, RestLengthValue.SixteenthRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//		assertEquals(new BarEvent(31, RestLengthValue.ThirtySecondRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//
//		Iterator<BarEvent> secondBarIterator = secondBar.iterator();
//		assertEquals(new BarEvent(0, RestLengthValue.WholeRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), secondBarIterator.next());
//		
//	}
//
//	public void testWriteSecondQuarterNoteIn44Bar(){
//		List<Bar> bars = new ArrayList<Bar>();
//		InstrumentTrack track = new InstrumentTrack(bars, "TestTrack", InstrumentType.Piano);
//		Bar firstBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 0);
//		Bar secondBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 1);
//		
//		//Manually change the existing Bar events int the first bar
//		Set<BarEvent> firstBarEvents = firstBar.getNoteEvents();
//		firstBarEvents.clear();
//		BarEvent barEventToOverWrite = new BarEvent(8, RestLengthValue.HalfRest, RestLengthValue.REST_PITCH_LINE_VALUE, false);
//		
//		firstBarEvents.add(new BarEvent(0, NoteLengthValue.QuaterNote, 6, false));
//		firstBarEvents.add(barEventToOverWrite);
//		firstBarEvents.add(new BarEvent(24, RestLengthValue.QuaterRest, RestLengthValue.REST_PITCH_LINE_VALUE, false));
//		
//		bars.add(firstBar);
//		bars.add(secondBar);
//		
//		track.setBars(bars);
//		
//		
//		
//		CompositeNoteValue aCompositeNoteValue = new CompositeNoteValue(NoteLengthValue.QuaterNote);
//
//		int stavePositionWrittentTo = 5;
//		BarEventManager.sortBarEvents(track, firstBar, barEventToOverWrite, aCompositeNoteValue, stavePositionWrittentTo);
//		assertEquals(4, firstBar.getNumberOfEventsInBar());
//		assertEquals(1, secondBar.getNumberOfEventsInBar());
//		
//		/*
//		 * QuarterNote (0-8) -> QuarterNote(8-16)-> HalfRest (16-32)
//		 */
//		
//		Iterator<BarEvent> firstBarIterator = firstBar.iterator();
//		assertEquals(new BarEvent(0, NoteLengthValue.QuaterNote, 6, false), firstBarIterator.next());
//		assertEquals(new BarEvent(8, NoteLengthValue.QuaterNote, stavePositionWrittentTo, false), firstBarIterator.next());
//		assertEquals(new BarEvent(16, RestLengthValue.QuaterRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//		assertEquals(new BarEvent(24, RestLengthValue.QuaterRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), firstBarIterator.next());
//
//		Iterator<BarEvent> secondBarIterator = secondBar.iterator();
//		assertEquals(new BarEvent(0, RestLengthValue.WholeRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), secondBarIterator.next());
//		
//	}
//	
//	public void testWriteWholeNoteThatOverwritesToTheNextBarWith44Bar(){
//		List<Bar> bars = new ArrayList<Bar>();
//		InstrumentTrack track = new InstrumentTrack(bars, "TestTrack", InstrumentType.Piano);
//		Bar firstBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 0);
//		Bar secondBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 1);
//		
//		//Manually change the existing Bar events - half note and then half rest
//		Set<BarEvent> firstBarEvents = firstBar.getNoteEvents();
//		firstBarEvents.clear();
//		BarEvent barEventToOverWrite = new BarEvent(16, RestLengthValue.HalfRest, RestLengthValue.REST_PITCH_LINE_VALUE, false);
//		
//		firstBarEvents.add(new BarEvent(0, NoteLengthValue.HalfNote, 6, false));
//		firstBarEvents.add(barEventToOverWrite);
//		
//		bars.add(firstBar);
//		bars.add(secondBar);
//		
//		track.setBars(bars);
//		
//		
//		//Overwrite the half rest in the second half with a whole note
//		CompositeNoteValue aCompositeNoteValue = new CompositeNoteValue(NoteLengthValue.WholeNote);
//
//		int stavePositionWrittentTo = 5;
//		BarEventManager.sortBarEvents(track, firstBar, barEventToOverWrite, aCompositeNoteValue, stavePositionWrittentTo);
//		
//		//Should end up with two half notes in the first bar - second one tied. The second should have one half note
//		//and one half rest
//		assertEquals(2, firstBar.getNumberOfEventsInBar());
//		assertEquals(2, secondBar.getNumberOfEventsInBar());
//		
//	
//		Iterator<BarEvent> firstBarIterator = firstBar.iterator();
//		assertEquals(new BarEvent(0, NoteLengthValue.HalfNote, 6, false), firstBarIterator.next());
//		assertEquals(new BarEvent(16, NoteLengthValue.HalfNote, stavePositionWrittentTo, true), firstBarIterator.next());
//
//		Iterator<BarEvent> secondBarIterator = secondBar.iterator();
//		assertEquals(new BarEvent(0, NoteLengthValue.HalfNote, stavePositionWrittentTo, false), secondBarIterator.next());
//		assertEquals(new BarEvent(16, RestLengthValue.HalfRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), secondBarIterator.next());
//		
//	}
//	
//	public void testOverWriteFirstNoteInBarThatShouldNotOverWriteSecond(){
//		List<Bar> bars = new ArrayList<Bar>();
//		InstrumentTrack track = new InstrumentTrack(bars, "TestTrack", InstrumentType.Piano);
//		Bar firstBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 0);
//		Bar secondBar = new Bar(Clef.G_CLEF, new TimeSignature(4, NoteLengthValue.QuaterNote), 
//				DiatonicScale.C, new Tempo(70, NoteLengthValue.QuaterNote), 1);
//		//Manually change the existing Bar events - two half notes
//		Set<BarEvent> firstBarEvents = firstBar.getNoteEvents();
//		firstBarEvents.clear();
//		BarEvent barEventToOverWrite = new BarEvent(0, NoteLengthValue.HalfNote, 6, false);
//		
//		firstBarEvents.add(barEventToOverWrite);
//		firstBarEvents.add(new BarEvent(16, NoteLengthValue.HalfNote, 6, false));
//		
//		bars.add(firstBar);
//		bars.add(secondBar);
//		
//		track.setBars(bars);
//		
//		
//		//Overwrite the first half note with another half note at a different pitch
//		CompositeNoteValue aCompositeNoteValue = new CompositeNoteValue(NoteLengthValue.HalfNote);
//
//		int stavePositionWrittentTo = 5;
//		BarEventManager.sortBarEvents(track, firstBar, barEventToOverWrite, aCompositeNoteValue, stavePositionWrittentTo);
//		
//		//Should end up with two half notes, as before, but with the first one at pitch position 5 
//		assertEquals(2, firstBar.getNumberOfEventsInBar());
//		assertEquals(1, secondBar.getNumberOfEventsInBar());
//		
//	
//		Iterator<BarEvent> firstBarIterator = firstBar.iterator();
//		assertEquals(new BarEvent(0, NoteLengthValue.HalfNote, stavePositionWrittentTo, false), firstBarIterator.next());
//		assertEquals(new BarEvent(16, NoteLengthValue.HalfNote, 6, false), firstBarIterator.next());
//
//		Iterator<BarEvent> secondBarIterator = secondBar.iterator();
//		assertEquals(new BarEvent(0, RestLengthValue.WholeRest, RestLengthValue.REST_PITCH_LINE_VALUE, false), secondBarIterator.next());
//		
//	}
//}
