package law.utility.images;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;



public class SymbolFactory {
	//TODO Would be better to have the buffered images loaded in a separate class and then
	//pull them in here.
	//Notes
	public static final Symbol WHOLE_NOTE = 
			new Symbol(createBufferedImage("Images/Note_Images/Whole_note.gif", 1.3f), 20, 62);
	public static final Symbol HALF_NOTE = 
			new Symbol(createBufferedImage("Images/Note_Images/Half_note.gif", 1.3f), 17, 63);
	public static final Symbol QUARTER_NOTE = 
			new Symbol(createBufferedImage("Images/Note_Images/Quarter_note.gif", 1.3f), 15, 62);
	public static final Symbol EIGTH_NOTE = 
			new Symbol(createBufferedImage("Images/Note_Images/Eigth_note.gif", 1.3f), 14, 61);
	public static final Symbol SIXTEENTH_NOTE = 
			new Symbol(createBufferedImage("Images/Note_Images/Sixteenth_note.gif", 1.3f), 13, 62);
	public static final Symbol THIRTY_SECOND_NOTE = 
			new Symbol(createBufferedImage("Images/Note_Images/32nd_note.gif", 1.3f), 12, 61);
	
	//Rests
	public static final Symbol WHOLE_REST = 
			new Symbol(createBufferedImage("Images/Rests/Whole_Rest.gif", 1f), 32, 10);
	public static final Symbol HALF_REST = 
			new Symbol(createBufferedImage("Images/Rests/Half_Rest.gif", 1f), 28, 9);
	public static final Symbol QUARTER_REST = 
			new Symbol(createBufferedImage("Images/Rests/Quarter_Rest.gif", 1f), 13, 26);
	public static final Symbol EIGTH_REST = 
			new Symbol(createBufferedImage("Images/Rests/Eigth_Rest.gif", 1f), 13, 16);
	public static final Symbol SIXTEENTH_REST = 
			new Symbol(createBufferedImage("Images/Rests/Sixteenth_Rest.gif", 1f), 11, 18);
	public static final Symbol THIRTY_SECOND_REST = 
			new Symbol(createBufferedImage("Images/Rests/32nd_Rest.gif", 1f), 13, 22);
	
	//Clefs
	public static final Symbol G_CLEF = new Symbol(
			createBufferedImage("Images/Clefs/G clef.gif", 0.45f), 70, 84);
	public static final Symbol BASS_CLEF = new Symbol(
			createBufferedImage("Images/Clefs/bassclef.gif", 0.27f), 20, 25);
	public static final Symbol C_CLEF = new Symbol(
			createBufferedImage("Images/Clefs/C clef.gif", 0.5f), 20, 36);
	
	//Accidentals
	public static final Symbol SHARP = 
			new Symbol(createBufferedImage("Images/Accidentals/sharp.gif", 0.30f), 10, 15);
	public static final Symbol NATURAL = 
			new Symbol(createBufferedImage("Images/Accidentals/naturalsign.gif", 0.25f), 7, 14);
	public static final Symbol FLAT = 
			new Symbol(createBufferedImage("Images/Accidentals/flat.gif", 0.26f), 6, 11);

	private SymbolFactory(){}

	
	private static BufferedImage createBufferedImage(String imageFileLocation, float resizeFactor){
		BufferedImage img = null;
		try {
			img = ImageIO.read(SymbolFactory.class.getResourceAsStream("/"+imageFileLocation));
			
		} catch (IOException e) {
			System.out.println("unable to load image file");
		}
		img = ImageTransformer.removeBackground(img);
		img = ImageTransformer.resize(img, resizeFactor);

		return img;
	}
}
