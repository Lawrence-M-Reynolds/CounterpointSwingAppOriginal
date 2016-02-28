package law.raw.view.compositionCreator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;

import law.raw.composition.Composition;
import law.raw.view.compositionCreator.informationObjects.TrackCreationInformation;

public class TrackCreator  extends JFrame{

	private Composition composition;
	private List<TrackCreationInformation> trackCreationInformationObjects;
	
	public TrackCreator(){
		super("TrackCreator");
		this.setVisible(false);
		
		final TrackCreator thisTrackCreator = this;
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				thisTrackCreator.setVisible(false);
			}			
		});
	}

	public void setComposition(Composition composition) {
		this.composition = composition;
	}

	public List<TrackCreationInformation> getTrackCreationInformationObjects() {
		return trackCreationInformationObjects;
	}
}
