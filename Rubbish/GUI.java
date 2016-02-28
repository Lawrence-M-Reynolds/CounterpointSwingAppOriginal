package RUBBIS;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import law.raw.StaveDrawing.Composition;
import law.raw.view.NoteSelector;
import law.raw.view.StavePanel;

public class GUI {
	
	public GUI(Composition composition){
		
		JFrame frame = new JFrame();
		StavePanel staves = new StavePanel(composition);
		staves.setSize(500, 500);
	
		JScrollPane staveScroller = new JScrollPane(staves, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		staves.setPreferredSize(new Dimension(21900, 21000));
//		staves.revalidate();		
		JViewport vp = staveScroller.getViewport();
		vp.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		staveScroller.setSize(new Dimension(100, 100));
		
		
		
		
		frame.add(staveScroller);
		frame.setSize(700, 800);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {System.exit(0);}
		});
		
		//The Note Selector
		NoteSelector noteSelector = new NoteSelector();			
		noteSelector.setSize(150, 200);
		noteSelector.setLocation(1000, 500);
		noteSelector.setVisible(true);
	}
}