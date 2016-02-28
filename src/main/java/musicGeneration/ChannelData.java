package musicGeneration;

import javax.sound.midi.MidiChannel;

public class ChannelData {
    MidiChannel channel;
    boolean solo, mono, mute, sustain;
    int velocity, pressure, bend, reverb;
    int row, col, num;

    public ChannelData(MidiChannel channel, int num) {
        this.channel = channel;
        this.num = num;
        velocity = pressure = bend = reverb = 64;
    }
}
