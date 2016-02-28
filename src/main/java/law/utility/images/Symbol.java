package law.utility.images;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
/**
 * This class wraps the buffered image object so that it can be drawn
 * easily without having to specify the image centering information
 * in the enums.
 * @author BAZ
 *
 */
public class Symbol{
	private BufferedImage symbolImage;
	private int xOffset;
	private int yOffset;
	
	public Symbol(final BufferedImage aSymbolImage, final int anXOffset, final int aYOffset){
		symbolImage = aSymbolImage;
		xOffset = anXOffset;
		yOffset = aYOffset;
	}
	
	public void drawSymbol(final Graphics g, final int xLocation, final int yLocation){
		g.drawImage(symbolImage, (xLocation - xOffset), (yLocation - yOffset), null);
	}
}
