import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.sound.midi.*;
import java.awt.event.*;

class BeatBoxTest {
	JPanel mainPanel;
	JFrame theFrame;
	ArrayList<JCheckBox> checkboxList;
	String [] instrumentNames = {"Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare","Crash Cymbal","Hand Clap","High Tom",
					"Hi Bingo","Maracas","Whistle","Low Conga","Cowbell","Vibraslap","Low-mid Tom","High Agogo", "Open Hi Conga"};
	int[] instruments = {35,42,46,38,49,39,50,60,70,71,64,56,58,47,67,63};
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	
	public static void main(String [] args) {
		new BeatBoxTest().buildGui();
	}
	public void buildGui() {
		theFrame = new JFrame("Cyber Beat Box");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		checkboxList = new ArrayList<JCheckBox>();
		Box buttonBox = new Box(BoxLayout.Y_AXIS);
		
		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");
		JButton upTempo = new JButton("Tempo Up");
		JButton downTempo = new JButton("Tempo Down");
	
		start.addActionListener(new MyStartListener() );
		stop.addActionListener(new MyStopListener() );
		upTempo.addActionListener(new MyUpTempoListener());
		downTempo.addActionListener(new MyDownTempoListener() );
	
		buttonBox.add(start);
		buttonBox.add(stop);
		buttonBox.add(upTempo);
		buttonBox.add(downTempo);
		
		background.add(BorderLayout.EAST,buttonBox);
		
		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for ( String str : instrumentNames ) {
			nameBox.add( new Label(str) );
		} 
		
		background.add(BorderLayout.WEST, nameBox);
		
		background.setBackground(Color.PINK);
		theFrame.getContentPane().add(background);
		
		GridLayout grid = new GridLayout(16,16);
		mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER, mainPanel);
		
		for ( int i = 0 ; i < 256 ; i++ ) {
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);
		}
		
		setUpMidi();
		theFrame.setBounds(50,50,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}
	
	public void setUpMidi() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ,4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public void buildTrackAndStart() {
		int[] trackList = null;
		sequence.deleteTrack(track);
		track = sequence.createTrack();
		
		for ( int i=0 ; i<16 ; i++ ) {
			trackList = new int[16];
			
			int key = instruments[i];
			
			for ( int j = 0 ; j < 16 ; j++ ) {
				JCheckBox jc = (JCheckBox) checkboxList.get(j + (16*i));
				if ( jc.isSelected() ) {
					trackList[j] = key;
				} else {
					trackList[j] = 0;
				}
			}
			
			makeTracks(trackList);
			
			try {
				sequencer.setSequence(sequence);
				sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
				sequencer.start();
				sequencer.setTempoInBPM(120);
			} catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public void makeTracks(int[] list) {
		for ( int i = 0 ; i < 16 ; i++ ) {
			int key = list[i];
			
			if ( key != 0 ) {
				track.add(makeEvent(144,2,key,100,i));
				track.add(makeEvent(128,2,key,100,i+1));
			}
		}
	}
	
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event = new MidiEvent(a,tick);
		} catch(Exception e) { e.printStackTrace(); }
		return event;
	}
	
	public class MyStartListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			buildTrackAndStart();
		}
	}
	public class MyStopListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			sequencer.stop();
		}
	}
	public class MyUpTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor( (float) (tempoFactor * 1.03) );
		}
	}
		
	public class MyDownTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor( (float) (tempoFactor * .97) );
		}
	}
}