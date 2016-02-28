package law.raw.view.stavePanel.noteSelection.lengthValues;

import java.awt.Graphics;
import java.awt.Point;

import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;
import law.utility.images.Symbol;
import law.utility.images.SymbolFactory;

public enum RestLengthValue implements LengthValue{
	
	WholeRest(1, SymbolFactory.WHOLE_REST), 
	HalfRest(2, SymbolFactory.HALF_REST), 
	QuaterRest(4, SymbolFactory.QUARTER_REST), 
	EigthRest(8, SymbolFactory.EIGTH_REST), 
	SixteenthRest(16, SymbolFactory.SIXTEENTH_REST), 
	ThirtySecondRest(32, SymbolFactory.THIRTY_SECOND_REST);
	
	public static int REST_PITCH_LINE_VALUE = 8;
	private int recipricolValue;
	private Symbol noteSymbol;

	//Get rid of this once all images have been calibrated
	RestLengthValue(final int aRecipricolValue, final Symbol aNoteSymbol){
		recipricolValue = aRecipricolValue;
		noteSymbol = aNoteSymbol;
	}
	
	public int getRecipricolValue() {
		return recipricolValue;
	}
	
//	public void drawSymbol(Graphics g, int xPosition, int yPosition){
//		noteSymbol.drawSymbol(g, xPosition, yPosition);
//	}
	
	public int getNumberof32ndNotes(){
		return 32/recipricolValue;
	}

	public void drawSymbol(Graphics g, Point point) {
		if(point != null){
			noteSymbol.drawSymbol(g, point.x, point.y);
		}
	}

	public boolean isRest() {
		return true;
	}
	
}




