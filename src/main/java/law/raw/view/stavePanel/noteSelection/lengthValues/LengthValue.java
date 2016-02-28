package law.raw.view.stavePanel.noteSelection.lengthValues;

import java.awt.Graphics;
import java.awt.Point;

public interface LengthValue {
	public void drawSymbol(Graphics g, Point point);

	public int getNumberof32ndNotes();
	
	public boolean isRest();
	
}
