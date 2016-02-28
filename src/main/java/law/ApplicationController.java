package law;


import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;
import java.util.Set;

import javax.swing.JFrame;

import law.counterpoint.CounterpointOperationsFacade;
import law.counterpoint.evaluation.CounterpointEvaluater;
import law.counterpoint.exception.InvalidCounterpointSolutionException;
import law.counterpoint.exception.UnsupportedTimeSignatureException;
import law.musicRelatedClasses.Clef;
import law.musicRelatedClasses.chord.Chord;
import law.musicRelatedClasses.chord.chordTypes.ChordType;
import law.musicRelatedClasses.key.Key;
import law.musicRelatedClasses.key.KeyNote;
import law.musicRelatedClasses.key.MusicScale;
import law.musicRelatedClasses.note.Note;
import law.musicRelatedClasses.note.NoteLetter;
import law.musicRelatedClasses.time.Tempo;
import law.musicRelatedClasses.time.TimeSignature;
import law.raw.composition.Composition;
import law.raw.composition.components.trackComponents.InstrumentType;
import law.raw.composition.components.trackComponents.VoicePitchRangeLimitObject;
import law.utility.PropertiesManager;
import law.utility.serialization.Serializer;
import law.raw.view.compositionCreator.CompositionCreator;
import law.raw.view.stavePanel.CounterpointOperationsUI;
import law.raw.view.stavePanel.StavePanelScroller;
import law.raw.view.stavePanel.noteSelection.enumeration.Accidental;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;
import musicGeneration.MusicPlayerFacade;

/**
 * The starting point of the application and acts as the controller. It connects the 
 * stave UI, which is used for writing a composition and reviwing the output of the 
 * counterpoint algorithms, to both the sound generation counterpoint algorithm
 * parts of the application.  
 * @author BAZ
 *
 */
public class ApplicationController extends Observable{
	public static PropertiesManager PROPERTIES_MANAGER = new PropertiesManager();
	public static String[] fileExtensions = {".compapp"};
	
	//TODO This isn't a very good way to do this. Need to do it for the composition creator
	private ApplicationController compAppDriver = this;
	
	public static void main(String[] args)
	{
		new ApplicationController();
	}
	

	private StavePanelScroller stavePanelScroller;
	private Composition composition;
	private List<Composition> counterpointSolutions;
	private JFrame frame = new JFrame("Counterpoint Tutor");
	private Serializer serializer;
	private MusicPlayerFacade musicPlayerFacade = new MusicPlayerFacade();
	private CounterpointOperationsUI counterpointOperationsDisplay = new CounterpointOperationsUI();
	private CounterpointOperationsFacade counterpointOperationsFacade;
	
	public ApplicationController(){
		counterpointOperationsFacade = new CounterpointOperationsFacade(counterpointOperationsDisplay);
		
		final Properties properties = ApplicationController.PROPERTIES_MANAGER.getAppProperties();
		serializer = new Serializer(properties.getProperty("LastSaveOrLoadLocation", "C:"),
				ApplicationController.fileExtensions, 0);

		
		CompAppMenuBar compAppMenuBar = new CompAppMenuBar();
		frame.setMenuBar(compAppMenuBar);	
		
		frame.setVisible(true);
		if(composition != null){
			stavePanelScroller = new StavePanelScroller(composition, counterpointOperationsDisplay);
			frame.setContentPane(stavePanelScroller);
		}
		
		int windowWidth = (int)Double.parseDouble(properties.getProperty("WindowClosingWidth", "700"));
		int windowHeight = (int)Double.parseDouble(properties.getProperty("WindowClosingHeight", "800"));
		
		frame.setPreferredSize(new Dimension(windowWidth, windowHeight));
		frame.pack();	
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				Dimension windowSize = frame.getSize();
				properties.setProperty("WindowClosingWidth", Double.toString(windowSize.getWidth()));
				properties.setProperty("WindowClosingHeight", Double.toString(windowSize.getHeight()));
				properties.setProperty("LastSaveOrLoadLocation", serializer.getLastSaveOrLoadLocation().getAbsolutePath());
				
				ApplicationController.PROPERTIES_MANAGER.saveAllProperties();
				System.exit(0);
			}			
		});
	}
	
	public void setComposition(Composition aComposition) {
		this.composition = aComposition;
		stavePanelScroller = new StavePanelScroller(composition, counterpointOperationsDisplay);
		frame.setContentPane(stavePanelScroller);
		frame.pack();
	}
	
	private class CompAppMenuBar extends MenuBar{
		private Menu compositionMenu;
		
		private CompAppMenuBar(){
			setupFileMenu();
//			setupCompositionMenu();
			setupCounterpointMenu();
			setupPlayBackMenu();
		}
		
		private void setupFileMenu(){
			Menu fileMenu = new Menu("File");
			MenuItem newMenuItem = new MenuItem("New");
			fileMenu.add(newMenuItem);
			newMenuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					CompositionCreator trackManagerFrame = new CompositionCreator(compAppDriver);
				}
			});	
			MenuItem openMenuItem = new MenuItem("Open");
			fileMenu.add(openMenuItem);
			openMenuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					Composition tempComposition= (Composition) serializer.loadObject(frame);
					if(tempComposition != null){
						composition = tempComposition;
						stavePanelScroller = new StavePanelScroller(composition, counterpointOperationsDisplay);
						frame.setContentPane(stavePanelScroller);
						frame.pack();
					}
				}
			});	
			MenuItem saveAsMenuItem = new MenuItem("Save As...");
			fileMenu.add(saveAsMenuItem);
			saveAsMenuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					serializer.saveObject(frame, composition);
				}
			});
			
			MenuItem closeMenuItem = new MenuItem("Close");
			fileMenu.add(closeMenuItem);
			closeMenuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
//LOW don't know how to implement this
				}
			});
			add(fileMenu);
		}
		
		
		private void setupCounterpointMenu(){
			Menu counterpointMenu = new Menu("Counterpoint");
			
			MenuItem evaluateMenuItem = new MenuItem("Evaluate Composition");
			counterpointMenu.add(evaluateMenuItem);
			evaluateMenuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					composition = counterpointOperationsFacade.evaluateComposition(composition);
					stavePanelScroller.setCompositionDTO(composition); 
					stavePanelScroller.reDrawStaves();
					frame.pack();
				}
			});	
			
			MenuItem generateFirstSpeciesSolutionsMenuItem = new MenuItem("Generate First Species Solutions");
			counterpointMenu.add(generateFirstSpeciesSolutionsMenuItem);
			final Menu firstSpeciesSolutionsSelecter = new Menu("First Species Solutions");
			counterpointMenu.add(firstSpeciesSolutionsSelecter);
			add(counterpointMenu);
			
			generateFirstSpeciesSolutionsMenuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					counterpointSolutions = counterpointOperationsFacade.generateFirstSpeciesSolutions(composition);
					MenuItem evaluateMenuItem = null;
					if(counterpointSolutions != null){
						for(final Composition counterpointSolution : counterpointSolutions){
							evaluateMenuItem = new MenuItem("Solution " + counterpointSolutions.indexOf(counterpointSolution));

							evaluateMenuItem.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e) {
									composition = counterpointSolution;
									stavePanelScroller.setCompositionDTO(composition); 
									stavePanelScroller.reDrawStaves();
									frame.pack();
								}					
							});						
							firstSpeciesSolutionsSelecter.add(evaluateMenuItem);
						}
					}
				}
			});	
			
			
		}
		private void setupPlayBackMenu(){
			Menu playBackMenu = new Menu("Playback");
			
			MenuItem playMenuItem = new MenuItem("Play");
			playBackMenu.add(playMenuItem);
			playMenuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
					musicPlayerFacade.playComposition(composition);
				}
			});	
			add(playBackMenu);
		}
	}
}
