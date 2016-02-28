/**
 * Encapsulates the note information for each track that will be used for drawing in the view, converting to Sequence
 * objects which will in turn be used to save midi or play on the synthesiser through the Sequencer. 
 */
/*
 * Perhaps each note object should implement comparable with regard to their timing. rather than inserting and deleting
 * from within a collection it would be more suitable to just add them on top and then do a single sort when passing it
 * back to the model.
 */

package law.raw.composition.components.tracks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import law.raw.composition.components.InstrumentTrackDetails;
import law.raw.composition.components.trackComponents.Bar;
import law.raw.composition.components.trackComponents.InstrumentType;
import law.raw.composition.components.trackComponents.VoicePitchRangeLimitObject;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;

public class InstrumentTrack implements Serializable,
		Comparable<InstrumentTrack>, Iterable<Bar> {
	private static final long serialVersionUID = 1L;

	private int trackNum;
	private String trackName;

	private InstrumentType instrumentType;
	private List<Bar> bars = new ArrayList<Bar>();
	
	/**
	 * Constructor used when constructing a new track. The number of bars to add
	 * is passed in which is obtained from the composition object.
	 * @param numOfBarsToAdd
	 */
	public InstrumentTrack(final int aTrackNum, final String aTrackName,
			final InstrumentType anInstrumentType) {
		trackName = aTrackName;
		instrumentType = anInstrumentType;
		trackNum = aTrackNum;
	}

	public InstrumentTrack(InstrumentTrackDetails instrumentTrackDetails) {
		trackName = instrumentTrackDetails.getTrackName();
		instrumentType = instrumentTrackDetails.getInstrumentType();
		trackNum = instrumentTrackDetails.getTrackNum();
	}

	public int getInstrumentNum() {
		return trackNum;
	}
	
	public void addBar(Bar bar){
		bars.add(bar);
	}
	public void setTrackNum(int trackNum) {
		this.trackNum = trackNum;
	}

	public Bar getBar(final int barNumber) {
		return bars.get(barNumber);
	}

	/**
	 * Returns the name of the track. If the track doesn't have a name then the
	 * instrument name is returned instead.
	 * 
	 * @return
	 */
	public String getTrackName() {
		if (trackName != null) {
			return trackName;
		}
		return instrumentType.toString();
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	/**
	 * Comparable is implemented so that the track with the lowest track number
	 * will be at the top of the list.
	 * 
	 * @param track
	 * @return
	 */
	public int compareTo(InstrumentTrack track) {
		return (track.getInstrumentNum() - this.getInstrumentNum());
	}

	public int getNumberOfBars() {
		return bars.size();
	}

	public List<Bar> getBars() {
		return bars;
	}	
	
	/**
	 * This method accepts a bar which should exist within this track and returns one
	 * in front of it.
	 * @param aBar
	 * @return
	 */
	public Bar getNextBar(final Bar aBar){
		return bars.get((bars.indexOf(aBar) + 1));
	}

	public Iterator<Bar> iterator() {
		return bars.iterator();
	}

	public InstrumentType getInstrumentType() {
		return instrumentType;
	}
	
}
