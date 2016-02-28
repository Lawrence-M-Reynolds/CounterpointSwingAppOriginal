package law.raw.view.stavePanel.noteSelection.lengthValues;

import java.awt.Graphics;
import java.awt.Point;

import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;
import law.utility.images.Symbol;
import law.utility.images.SymbolFactory;

public enum NoteLengthValue implements LengthValue{
	
	WholeNote(1, SymbolFactory.WHOLE_NOTE),
	HalfNote(2, SymbolFactory.HALF_NOTE), 
	QuaterNote(4, SymbolFactory.QUARTER_NOTE), 
	EigthNote(8, SymbolFactory.EIGTH_NOTE), 
	SixteenthNote(16, SymbolFactory.SIXTEENTH_NOTE), 
	ThirtySecondNote(32, SymbolFactory.THIRTY_SECOND_NOTE);

	private int recipricolValue;
	private Symbol noteSymbol;

	//Get rid of this once all images have been calibrated
	NoteLengthValue(final int aRecipricolValue, final Symbol aNoteSymbol){
		recipricolValue = aRecipricolValue;
		noteSymbol = aNoteSymbol;
	}
	
	public int getRecipricolValue() {
		return recipricolValue;
	}
	
//	public void drawSymbol(Graphics g, int xPosition, int yPosition){
//		noteSymbol.drawSymbol(g, xPosition, yPosition);
//	}	
	public void drawSymbol(Graphics g, Point point){
		if(point != null){
			noteSymbol.drawSymbol(g, point.x, point.y);
		}
	}
	
	public int getNumberof32ndNotes(){
		return 32/recipricolValue;
	}

	public boolean isRest() {
		return false;
	}
	
	public static NoteLengthValue getNoteLengthValueEquivalentNoteLengthValue(
			LengthValue aLengthValue){
		for(NoteLengthValue noteLengthValue : values()){
			if(aLengthValue.getNumberof32ndNotes() == noteLengthValue.getNumberof32ndNotes()){
				return noteLengthValue;
			}			
		}
		//Shouldn't get here
		return null;
	}
}




