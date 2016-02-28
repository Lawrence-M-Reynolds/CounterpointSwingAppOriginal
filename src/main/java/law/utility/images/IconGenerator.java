package law.utility.images;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconGenerator {

	public static ImageIcon WHOLE_NOTE = setIcon("/Images/Note_Images/Whole_note.gif", 1f);
	public static ImageIcon HALF_NOTE = setIcon("/Images/Note_Images/Half_note.gif", 1f);
	public static ImageIcon QUARTER_NOTE = setIcon("/Images/Note_Images/Quarter_note.gif", 1f);
	public static ImageIcon EIGTH_NOTE = setIcon("/Images/Note_Images/Eigth_note.gif", 1f);
	public static ImageIcon SIXTEENTH_NOTE = setIcon("/Images/Note_Images/Sixteenth_note.gif", 1f);
	public static ImageIcon THRIRTY_SECOND_NOTE = setIcon("/Images/Note_Images/32nd_note.gif", 1f);
	
	public static ImageIcon SHARP = setIcon("/Images/Accidentals/sharp.gif", 0.5f);
	public static ImageIcon NATURAL = setIcon("/Images/Accidentals/naturalsign.gif", 0.5f);
	public static ImageIcon FLAT = setIcon("/Images/Accidentals/flat.gif", 0.5f);
	
	private static ImageIcon setIcon(String imageFileString, float resizeFactor){
		BufferedImage img = null;
		try {
			img = ImageIO.read(
					SymbolFactory.class.getResourceAsStream(imageFileString));
			
		} catch (IOException e) {
			System.out.println("unable to load image file");
		}
		
		img = ImageTransformer.resize(img, resizeFactor);
		return new ImageIcon(img);
	}
}
