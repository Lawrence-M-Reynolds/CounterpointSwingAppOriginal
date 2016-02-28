package law.raw.view.stavePanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import law.counterpoint.rules.reporting.RuleReport;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;

/**
 * This is UI used to interact with the counterpoint operations part of the applicatoin.
 * It allows the user to select any evaluation reports produced which will then be
 * drawn on the stave panel.
 * The genrator algorithm also makes use of this UI. As the solutions are being genrated
 * the progress is reported to this display for the user to see. There are also controls
 * allowing parameters to be passed into the generator algorithm.
 *  
 * @author BAZ
 *
 */
public class CounterpointOperationsUI extends JFrame{
	
	private ListIterator<RuleReport> reportIter;
	private RuleReport selectedRuleReport;
	private StavePanelScroller stavePanelScroller;
	private TextArea textArea = new TextArea();
	private SpinnerModel spinnerModel = new SpinnerNumberModel(100, 50, 10000, 50);              
	private JSpinner spinner = new JSpinner(spinnerModel);
	private JCheckBox enableMaxCompositionLimit = new JCheckBox("Enable Max Composition limit",
			true);
	
	public CounterpointOperationsUI(){
		setLayout(new BorderLayout());
		JPanel algorithmSettingsPanel = new JPanel();
		this.add(algorithmSettingsPanel, BorderLayout.NORTH);
		algorithmSettingsPanel.add(spinner);
		algorithmSettingsPanel.add(enableMaxCompositionLimit);
		
		JButton previousReportButton = new JButton("Previous");
		previousReportButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(reportIter != null && reportIter.hasPrevious()){
					selectedRuleReport = reportIter.previous();
					textArea.setText(selectedRuleReport.getReportComments());
					
					stavePanelScroller.reDrawStaves();
				}
			}		
		});
		this.add(previousReportButton, BorderLayout.WEST);
		
		JButton nextReportButton = new JButton("Next");
		nextReportButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(reportIter != null && reportIter.hasNext()){
					selectedRuleReport = reportIter.next();
					textArea.setText(selectedRuleReport.getReportComments());					
				}else{
					selectedRuleReport = null;
					textArea.setText("No more rule reports");
				}
				stavePanelScroller.reDrawStaves();
			}		
		});
		this.add(nextReportButton, BorderLayout.EAST);
		textArea.setEditable(false);
		this.add(textArea, BorderLayout.CENTER);
		
		
		this.setLocation(0, 0);
		setState ( Frame.ICONIFIED );
		setLocation(500, 500);
		setSize(1000, 200);
		setAlwaysOnTop(true);
		setVisible(true);
	}
	
	/**
	 * If the composition has a list of RuleReport objects from the counterpoint
	 * evaluator then the list iterator is obtained from it and used to cycle
	 * through each of them.
	 * @param ruleReports
	 */
	public void setRuleReports(List<RuleReport> ruleReports) {
		if(ruleReports != null){
			reportIter = ruleReports.listIterator();
		}else{
			reportIter = null;
		}
		selectedRuleReport = null;
		textArea.setText("");
		this.stavePanelScroller.reDrawStaves();
	}

	/**
	 * Gets the currently selected RuleReport that was cycled over with the
	 * list iterator.
	 * @return
	 */
	public RuleReport getSelectedRuleReport() {
		return selectedRuleReport;
	}

	/**
	 * Sets the StavePanelScroller instance which is required so that this
	 * class can tell it to redraw the composition along with the reults from
	 * the selected rule report.
	 * @param stavePanelScroller
	 */
	public void setStavePanelScroller(StavePanelScroller stavePanelScroller) {
		this.stavePanelScroller = stavePanelScroller;
	}
	
	/**
	 * Sets a message to be displayed on the front panel. This is used by the 
	 * generator algorithm to display the progress, or to display any erors
	 * that have occurred.
	 * @param message
	 */
	public void setMessage(String message){
		textArea.setText(message);
	}
	
	/**
	 * Gets the maximum number of solutions that the generator algorithm should
	 * carry over from one iteration to the next. Set by the user.
	 * @return
	 */
	public int getMaxNumberOfGeneratedSolutions(){
		return (Integer)spinner.getValue();
	}
	
	/**
	 * This boolean tells the generator algorithm whether to operate differently
	 * and just carry over all solutions over from one iteration to the next.
	 * This doesn't really work in it's current state but could be developed
	 * to work in future.
	 * @return
	 */
	public boolean isMaxCompositionLimitEnabled(){
		return enableMaxCompositionLimit.isSelected();
	}
}

