package musicGeneration;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import law.musicRelatedClasses.time.Tempo;
import law.musicRelatedClasses.time.TimeSignature;
import law.raw.composition.Composition;
import law.raw.composition.components.trackComponents.Bar;
import law.raw.composition.components.trackComponents.barComponents.CompositionBarEvent;
import law.raw.composition.components.tracks.InstrumentTrack;


public class MusicPlayer implements MetaEventListener{
	Synthesizer synthesizer;
	Instrument instruments[];
	ChannelData channels[];
	ChannelData cc;    // current channel
	Sequence sequence;
	Sequencer sequencer;
	
	final int PROGRAM = 192;
    final int NOTEON = 144;
    final int NOTEOFF = 128;
    final int DEF_VELOCITY = 64;
    
    
	public void meta(MetaMessage meta) {
        if (meta.getType() == 47) {  // 47 is end of track
        	sequencer.close();
        }		
	}

	public void open() {
		
		try {
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
			Soundbank sb = synthesizer.getDefaultSoundbank();
			if (sb != null) {
				instruments = sb.getInstruments();
				synthesizer.loadInstrument(instruments[0]);
			}			
			
			sequencer = MidiSystem.getSequencer();
			sequencer.addMetaEventListener(this);
			sequence = new Sequence(Sequence.PPQ, 90);
			
		} catch (Exception e) {
			System.out.println("Unable to set up sound resources");
		}
	}

	public void playComposition(Composition composition) {
		createSequence(composition);
	
		playSequence();		
	}
	
	private void createSequence(Composition composition){		
		for(InstrumentTrack instrumentTrack : composition){
			Track track = sequence.createTrack();
			createShortEvent(track, PROGRAM,0, 0, 0);		
			
			for(Bar bar: instrumentTrack){
				int barNum = bar.getBarNumber();
				Tempo tempo = bar.getTempo();
				int timeLength32ndBeat = tempo.getNumberOfMiliSecondsPer32ndNoteLengthValue();
				int barMiliSecondLength = bar.getNumberOf32ndNotes() * timeLength32ndBeat;
				
				for(CompositionBarEvent compositionBarEvent : bar){					
					if(!compositionBarEvent.isRest()){											
						int midiValue = compositionBarEvent.getMidiValue();
						int noteOnTime = (compositionBarEvent.getTimeLocation() * timeLength32ndBeat)
								+ (barNum*barMiliSecondLength);
						int noteOffTime = noteOnTime + (compositionBarEvent.getEventLength() * timeLength32ndBeat);
						
						createShortEvent(track, NOTEON, midiValue, DEF_VELOCITY, noteOnTime);
						createShortEvent(track, NOTEOFF, midiValue, DEF_VELOCITY, noteOffTime);
					}
				}
			}
		}
	}
	
	private void createShortEvent(Track track, int type, int pitch, int velocity, long length) {
		//TODO - It works but I havn't really done this bit right
		ShortMessage message = new ShortMessage();
		try {
			long tick = length * sequence.getResolution() / 500;					
			message.setMessage(type, pitch, velocity);
			MidiEvent event = new MidiEvent(message, length);
			track.add(event);
		} catch (Exception ex) { ex.printStackTrace(); }
	}
	
	private void playSequence(){
		try {
			sequencer.open();
			sequencer.setSequence(sequence);
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		sequencer.start();
	}
}
