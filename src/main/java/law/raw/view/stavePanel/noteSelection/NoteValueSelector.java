package law.raw.view.stavePanel.noteSelection;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


import law.raw.view.stavePanel.noteSelection.enumeration.Accidental;
import law.raw.view.stavePanel.noteSelection.enumeration.WrittenNote;
import law.raw.view.stavePanel.noteSelection.lengthValues.NoteLengthValue;
import law.utility.images.IconGenerator;
import law.utility.images.Symbol;

/**
 * Used by the user to select the properties of the note that they wish to
 * draw. When generateNoteValue() is called a WrittenNote is returned with
 * all of the properties selected in the selector.
 * By default a Whole note is selected with no accidental. 
 * @author BAZ
 *
 */
public class NoteValueSelector extends JFrame{
	private NoteLengthValue noteLengthValueSelection = NoteLengthValue.WholeNote;
	private Accidental accidentalSelection;
	
	public NoteValueSelector(){
		setLayout(new GridLayout(2, 0));
		
		addNoteLengthValueButtons();
		addAccidentalButons();

		this.setLocation(1000, 500);
		setState ( Frame.ICONIFIED );
		setSize(150, 200);
		setAlwaysOnTop(true);
		setVisible(true);
	}
	
	public WrittenNote generateNoteValue(){
		final Accidental anAccidentalSelection = accidentalSelection;
		WrittenNote noteValueToReturn = new WrittenNote(noteLengthValueSelection, anAccidentalSelection);
		return noteValueToReturn;
	}
	
	private void addNoteLengthValueButtons(){
		ImageIcon wholeNotePic = IconGenerator.WHOLE_NOTE;
		ImageIcon halfNotePic = IconGenerator.HALF_NOTE;
		ImageIcon quarterNotePic = IconGenerator.QUARTER_NOTE;
		ImageIcon eigthNotePic = IconGenerator.EIGTH_NOTE;
		ImageIcon sixteenthNotePic = IconGenerator.SIXTEENTH_NOTE;
		ImageIcon thirtySecondNotePic = IconGenerator.THRIRTY_SECOND_NOTE;		
		
		JButton wholeNoteButton = new JButton(wholeNotePic);
		JButton halfNoteButton = new JButton(halfNotePic);
		JButton quarterNoteButton = new JButton(quarterNotePic);
		JButton eigthNoteButton = new JButton(eigthNotePic);
		JButton sixteenthNoteButton = new JButton(sixteenthNotePic);
		JButton thirtySecondNoteButton = new JButton(thirtySecondNotePic);
				
		wholeNoteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				noteLengthValueSelection = NoteLengthValue.WholeNote;
			}		
		});
		
		halfNoteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				noteLengthValueSelection = NoteLengthValue.HalfNote;
			}		
		});
		
		quarterNoteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				noteLengthValueSelection = NoteLengthValue.QuaterNote;
			}		
		});
		
		eigthNoteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				noteLengthValueSelection = NoteLengthValue.EigthNote;
			}		
		});
		
		sixteenthNoteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				noteLengthValueSelection = NoteLengthValue.SixteenthNote;
			}		
		});
		
		thirtySecondNoteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				noteLengthValueSelection = NoteLengthValue.ThirtySecondNote;
			}		
		});
		
		add(wholeNoteButton);
		add(halfNoteButton);
		add(quarterNoteButton);
		add(eigthNoteButton);
		add(sixteenthNoteButton);
		add(thirtySecondNoteButton);
	}

	private void addAccidentalButons(){
		ImageIcon sharpPic = IconGenerator.SHARP;
		ImageIcon naturalPic = IconGenerator.NATURAL;
		ImageIcon flatPic = IconGenerator.FLAT;
		
		JButton sharpButton = new JButton(sharpPic);
		JButton naturalButton = new JButton(naturalPic);
		JButton flatButton = new JButton(flatPic);
		JButton clearAccidentalButton = new JButton("Clear Accidental");
		
		sharpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				accidentalSelection = Accidental.SHARP;
			}		
		});
		
		naturalButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				accidentalSelection = Accidental.NATURAL;
			}		
		});
		
		flatButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				accidentalSelection = Accidental.FLAT;
			}		
		});
		
		clearAccidentalButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				accidentalSelection = null;
			}		
		});
		
		add(sharpButton);
		add(naturalButton);
		add(flatButton);
		add(clearAccidentalButton);
	}
}
