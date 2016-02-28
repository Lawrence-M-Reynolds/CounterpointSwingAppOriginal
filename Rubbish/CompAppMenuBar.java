package law.raw.view;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import law.raw.CompAppDriver;
import law.raw.composition.CompositionDTO;

public class CompAppMenuBar extends MenuBar{
	private CompAppDriver compAppDriver;
	private Menu fileMenu = new Menu("File");
	
	public CompAppMenuBar(CompAppDriver compAppDriver){
		this.compAppDriver = compAppDriver;
		
		MenuItem newMenuItem = new MenuItem("New");
		fileMenu.add(newMenuItem);
		newMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				composition = new CompositionDTO();
				stavePanelScroller = new StavePanelScroller(composition);
				frame.setContentPane(stavePanelScroller);
				frame.pack();
			}
		});	
	}

}
