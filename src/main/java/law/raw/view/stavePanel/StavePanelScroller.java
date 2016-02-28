package law.raw.view.stavePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import law.counterpoint.rules.reporting.RuleReport;
import law.musicRelatedClasses.Clef;
import law.raw.composition.Composition;
import law.raw.view.stavePanel.barEventMapping.BarEventMappingObject;
import law.raw.view.stavePanel.noteSelection.NoteValueSelector;
import law.raw.view.stavePanel.noteSelection.enumeration.Accidental;
import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;
import law.utility.images.SymbolFactory;

/**
 * This class ecnapsulates the StavePanel which is the UI that 
 * displays the composition and any output from the counterpoint 
 * algorithms to display to the user.
 * @author BAZ
 *
 */
public class StavePanelScroller extends JPanel{
	private StavePanel stavePanel;
	private Composition composition;
	private CounterpointOperationsUI counterpointOperationsDisplay;

	public StavePanelScroller(final Composition aComposition, 
			CounterpointOperationsUI aCounterpointOperationsDisplay){
		stavePanel = new StavePanel();
		setOpaque(true);
		setLayout(new BorderLayout());
		JScrollPane scroller = new JScrollPane(stavePanel);
		add(scroller, BorderLayout.CENTER);
		
		//TODO The way this has been designed is very bad.
		counterpointOperationsDisplay = aCounterpointOperationsDisplay;		
		aCounterpointOperationsDisplay.setStavePanelScroller(this);
		composition = aComposition;
	}
	public void setCompositionDTO(Composition compositionDTO) {
		this.composition = compositionDTO;
		counterpointOperationsDisplay.setRuleReports(compositionDTO.getRuleReports());
	}
	
	public void reDrawStaves(){
		stavePanel.repaint();
	}

	/**
	 * The UI for the user to write notes on and view output from the counterpoint
	 * algorithms. This uses a CompositionDrawer to perform the actual drawing
	 * operations.
	 * @author BAZ
	 *
	 */
	private class StavePanel extends JPanel{
		private CompositionDrawer compositionDrawer = new CompositionDrawer();
		private Point notePoint = null;
		private NoteValueSelector noteSelector = new NoteValueSelector();
		
		
		public StavePanel(){
			setBackground(Color.WHITE);
			this.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					Point point = e.getPoint();	
					compositionDrawer.getaBarEventMappingObject().writeEvent(
							point, noteSelector.generateNoteValue());
					stavePanel.repaint();
				}
			});
			
			this.addMouseMotionListener(new MouseMotionAdapter(){
				public void mouseMoved(MouseEvent e) {
					/*It will refer to the mapper (if it's there) to see if a mouse over
					 * note should be drawn at that point.
					 */
					if(compositionDrawer.getaBarEventMappingObject() != null){
						notePoint = compositionDrawer.getaBarEventMappingObject().checkPoint(e.getPoint());
						stavePanel.repaint();
					}else{
						/*
						 * If the mappers not there, it sets the point back to null and
						 * calls repaint again so that the mouse over notevalue goes
						 * away.
						 */
						if(notePoint != null){
							notePoint = null;
							stavePanel.repaint();
						}		
					}
				}				
			});
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			revalidate();
			//This only draws if the notePoint is not null
			/*LOW this should really be set into a instance variable and
			 * checked if it is a new object. Otherwise it's just creating a
			 * new object every time the mouse is moved.
			 */
			noteSelector.generateNoteValue().drawSymbol(g, notePoint);
			
			compositionDrawer.drawComposition(g, composition);

			if(counterpointOperationsDisplay.getSelectedRuleReport() != null){
				compositionDrawer.drawRuleReport(g, counterpointOperationsDisplay.getSelectedRuleReport());
			}

			setPreferredSize(compositionDrawer.getDrawingDimension());
		}
	}
}