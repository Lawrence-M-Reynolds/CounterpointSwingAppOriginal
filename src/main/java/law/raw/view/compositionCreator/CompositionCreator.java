package law.raw.view.compositionCreator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import law.ApplicationController;
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
import law.raw.view.compositionCreator.informationObjects.TrackCreationInformation;
import law.raw.view.stavePanel.StavePanelScroller;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;

public class CompositionCreator extends JFrame{
	private static int MINIMUM_SELECTABLE_NUMBER_OF_BARS = 4;
	private static int MAXIMUM_SELECTABLE_NUMBER_OF_BARS = 15;
	
	private Composition composition;
	private int numberOfBars = MINIMUM_SELECTABLE_NUMBER_OF_BARS;
	private JSpinner bassClefTrackSelector;
	private JSpinner g_ClefTrackSelector;
	//TODO This is set up really badly but I will come back to it later.
	public CompositionCreator(final ApplicationController compAppDriver){
		//FrameConfiguration
		super("Composition Creator");
		setLayout(new GridLayout(2, 2));
		JPanel algorithmSettingsPanel = new JPanel();
		this.add(algorithmSettingsPanel, BorderLayout.NORTH);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(100, 100));
		this.setLayout(new GridLayout(1, 3));
		pack();
			
		//Creating the compositon
		
		SpinnerModel spinnerModel = new SpinnerNumberModel(MINIMUM_SELECTABLE_NUMBER_OF_BARS, 
				MINIMUM_SELECTABLE_NUMBER_OF_BARS, MAXIMUM_SELECTABLE_NUMBER_OF_BARS, 1);              
		JSpinner numberOfBarsSpinner = new JSpinner(spinnerModel);
		numberOfBarsSpinner.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				numberOfBars = (Integer)((JSpinner)e.getSource()).getValue();
			}
		});
		this.add(numberOfBarsSpinner);
		JLabel numberOfBarsLabel = new JLabel("Number of Bars");
		this.add(numberOfBarsLabel);
		
		//Tracks selection
		SpinnerModel bassTrackSelectorSpinnerModel = new SpinnerNumberModel(1, 1, 2, 1); 
		bassClefTrackSelector = new JSpinner(bassTrackSelectorSpinnerModel);
		this.add(bassClefTrackSelector);
		JLabel numberOfFClefTracks = new JLabel("Number of F-Cleff Tracks");
		this.add(numberOfFClefTracks);
		
		SpinnerModel gTrackSelectorSpinnerModel = new SpinnerNumberModel(1, 1, 2, 1);
		g_ClefTrackSelector = new JSpinner(gTrackSelectorSpinnerModel);
		this.add(g_ClefTrackSelector);
		JLabel numberOfGClefTracks = new JLabel("Number of G-Clef Tracks");
		this.add(numberOfGClefTracks);
		
		/*
		 * Using a window listener so that the changes are made once this window is
		 * closed.
		 */
		final CompositionCreator thisTrackManagerFrame = this;
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				createComposition();
				addTracksToComposition();
				setCompositionChordLockdownMap();
				
				compAppDriver.setComposition(composition);
				thisTrackManagerFrame.dispose();
			}			
		});
	}
	
	//TODO Just hardcoding these details for now
	private void createComposition(){
		composition = new Composition("title", new Key(MusicScale.Diatonic_Major, new Note(NoteLetter.C, null)), 
				new TimeSignature(4, NoteLengthValue.QuaterNote), new Tempo(70, NoteLengthValue.QuaterNote), numberOfBars,
				new HashMap<Integer, Collection<Chord>>());
	}

	private void addTracksToComposition(){
		
		composition.addNewTrack("First Track",
				InstrumentType.Piano, Clef.G_CLEF);
		composition.addTrackNumToVoicePitchRangeLimitObjectMapping(0, new VoicePitchRangeLimitObject(Clef.G_CLEF, 0, 8));
		
		int g_clefCount = (Integer)g_ClefTrackSelector.getValue();
		if(g_clefCount == 2){
			composition.addNewTrack("Second Track",
					InstrumentType.Piano, Clef.G_CLEF);
			composition.addTrackNumToVoicePitchRangeLimitObjectMapping(1, new VoicePitchRangeLimitObject(Clef.G_CLEF, 7, 15));
		}
		
		composition.addNewTrack("Third Track",
				InstrumentType.Piano, Clef.F_CLEF);
		composition.addTrackNumToVoicePitchRangeLimitObjectMapping(2, new VoicePitchRangeLimitObject(Clef.F_CLEF, 4, 12));
		
		int bassClefCount = (Integer)bassClefTrackSelector.getValue();
		if(bassClefCount == 2){
			composition.addNewTrack("Fourth Track",
					InstrumentType.Piano, Clef.F_CLEF);
			composition.addTrackNumToVoicePitchRangeLimitObjectMapping(3, new VoicePitchRangeLimitObject(Clef.F_CLEF, 9, 17));
		}
	}
	
	private void setCompositionChordLockdownMap(){
		//Obtain the lock down chords from the key for the beggining and the cadence
		Key key = composition.getKey();
		KeyNote tonicKeyNote = key.getKeyNoteFromNoteLetter(NoteLetter.C);
		KeyNote fifthKeyNote = key.getKeyNoteFromNoteLetter(NoteLetter.G);
		
		
		
		//Locking the first and last sets of bars to the tonic C chord
		Set<Chord> tonicChordOnly = new HashSet<Chord>();
		tonicChordOnly.add(tonicKeyNote.buildKeyChord(ChordType.TRIAD));
		//the first chord should always be the tonic
		composition.addHarmonyLockdownMapping(0, tonicChordOnly);
		// And the last bar: -1 because the bars start at zero
		composition.addHarmonyLockdownMapping(numberOfBars - 1, tonicChordOnly);
		
		//Locking the first and last sets of bars to the tonic C chord
		Set<Chord> cadenceChords = new HashSet<Chord>();
		cadenceChords.add(fifthKeyNote.buildKeyChord(ChordType.TRIAD));
		
		composition.addHarmonyLockdownMapping(numberOfBars - 2, cadenceChords);
	}
}
