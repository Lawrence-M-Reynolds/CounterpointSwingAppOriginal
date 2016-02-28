//package law.raw.composition.components.trackComponents.barComponents;
//
//import java.util.Iterator;
//import java.util.TreeSet;
//
//
//import junit.framework.TestCase;
//import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;
//
//public class BarEventTest extends TestCase{
//	public void testBarEventSorting(){
//		TreeSet<BarEvent> noteEvents = new TreeSet<BarEvent>();
//		
//		BarEvent barEvent1 = new BarEvent(0, NoteLengthValue.WholeNote, 0, false);
//		BarEvent barEvent2 = new BarEvent(1, NoteLengthValue.WholeNote, 1, false);
//		BarEvent barEvent3 = new BarEvent(23, NoteLengthValue.WholeNote, 23, false);
//		BarEvent barEvent4 = new BarEvent(22, NoteLengthValue.WholeNote, 22, false);
//		BarEvent barEvent5 = new BarEvent(3, NoteLengthValue.WholeNote, 3, false);
//		BarEvent barEvent6 = new BarEvent(5, NoteLengthValue.WholeNote, 5, false);
//		
//		noteEvents.add(barEvent1);
//		noteEvents.add(barEvent2);
//		noteEvents.add(barEvent3);
//		noteEvents.add(barEvent4);
//		noteEvents.add(barEvent5);
//		noteEvents.add(barEvent6);
//		
//		Iterator<BarEvent> iter = noteEvents.iterator();
//		assertEquals(barEvent1, iter.next());
//		assertEquals(barEvent2, iter.next());
//		assertEquals(barEvent5, iter.next());
//		assertEquals(barEvent6, iter.next());
//		assertEquals(barEvent4, iter.next());
//		assertEquals(barEvent3, iter.next());
//	}
//}
