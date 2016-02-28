package law.musicRelatedClasses.time;

import java.io.Serializable;

import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;

public class TimeSignature implements Serializable{
	private int numberOfBeatsPerBar;
	private NoteLengthValue beatValue;

	public TimeSignature(int numberOfBeatsPerBar, NoteLengthValue beatValue) {
		this.numberOfBeatsPerBar = numberOfBeatsPerBar;
		this.beatValue = beatValue;
	}

	public int getNumberOfBeatsPerBar() {
		return numberOfBeatsPerBar;
	}

	public NoteLengthValue getBeatValue() {
		return beatValue;
	}
	
	public int getNumberOf32ndNotesPerBar(){
		return (int)((numberOfBeatsPerBar* 32)/beatValue.getRecipricolValue());
	}
	
	public String toString(){
		return (numberOfBeatsPerBar + "/" + beatValue.getRecipricolValue());
	}

	@Override
	public boolean equals(Object obj) {
		TimeSignature timeSignatureToComapare = (TimeSignature)obj;
		return (this.getNumberOf32ndNotesPerBar() == timeSignatureToComapare.getNumberOf32ndNotesPerBar() &&
				this.getBeatValue() == timeSignatureToComapare.getBeatValue());
	}

	@Override
	public int hashCode() {
		return beatValue.getRecipricolValue();
	}
	

}
