package musicGeneration;

import law.raw.composition.Composition;

public class MusicPlayerFacade {
	
	MusicPlayer musicPlayer = new MusicPlayer();
	
	public void playComposition(Composition composition){
		musicPlayer.open();
		musicPlayer.playComposition(composition);
	}

}
