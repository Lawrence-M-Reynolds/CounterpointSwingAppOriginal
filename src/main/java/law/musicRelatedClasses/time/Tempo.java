package law.musicRelatedClasses.time;

import java.io.Serializable;

import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;

public class Tempo implements Serializable{
	private int beatsPerMinute;
	private NoteLengthValue beatValue;
	
	public Tempo(final int aBeatsPerMinute, final NoteLengthValue abeatValue){
		beatsPerMinute = aBeatsPerMinute;
		beatValue = abeatValue;
	}
	
	public int getNumberOfMiliSecondsPer32ndNoteLengthValue(){
		return (beatsPerMinute / beatValue.getNumberof32ndNotes());
	}
}
