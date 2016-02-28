package law.utility.images;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageTransformer {
	
	public static BufferedImage removeBackground(BufferedImage image) {
		BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dimg.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.drawImage(image, null, 0, 0);
		g.dispose();
		for(int i = 0; i < dimg.getHeight(); i++) {
			for(int j = 0; j < dimg.getWidth(); j++) {
				if(dimg.getRGB(j, i) == Color.WHITE.getRGB()) {
					dimg.setRGB(j, i, 0x8F1C1C);
				}
			}
		}
		return dimg;
	}

	public static BufferedImage resize(BufferedImage img, float factor) {
		int w = img.getWidth();
		int h = img.getHeight();
		int newW = (int) (factor * w);
		int newH = (int) (factor * h);
		
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}

}
