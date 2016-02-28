package law.raw.view;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import law.raw.composition.CompositionDTO;
import law.raw.composition.components.InstrumentTrack;

public class StavePanel extends JPanel{
	CompositionDTO composition;
	BarDrawer drawer = new BarDrawer();
	
	public StavePanel(CompositionDTO composition){
		this.composition = composition;
	}

	public void paintComponent(Graphics g){
		//Required to prevent scroll smudging
		super.paintComponent(g);
		
		ArrayList<InstrumentTrack> tracks = composition.getInstrumentTracks();
		for(InstrumentTrack track: tracks){
			drawer.drawDefaultBar(g, track);
		}
		setPreferredSize(drawer.getScrollDimension());
		revalidate();
		
	}
}
