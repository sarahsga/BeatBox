import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.midi.*;

class BeatBoxMy4 {
	private JFrame frame;
	private JCheckBox[][] check;
	private JButton btnStart, btnStop, btnTempoUp, btnTempoDown;
	private JLabel label;
	private JPanelMy panelCenterFull;
	private JPanelMy panelEast;
	private JPanelMy panelWest;
	private JPanelMy panelCenterRow;
	private JPanelMy contentPanel;
	private JList list;
	private BeatInner beatInner = new BeatInner();
	
	public static void main (String [] args ) {
		BeatBoxMy4 gui = new BeatBoxMy4();
		System.out.println("Inside main");
		gui.go();
	}
	
	public void go() {
		frame = new JFrame("Cyber Beat Box");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,700);
		contentPanel = new JPanelMy();
		FlowLayout layout = new FlowLayout();
		layout.setAlignOnBaseline(true);
		contentPanel.setLayout(layout);
		
		beatInner.setUp();
		
		westPanel();
		eastPanel();
		
		frame.getContentPane().add(contentPanel);
		frame.setVisible(true);
		
		// adding all the action
		System.out.println("inside go");
		beatInner.setUpActionListeners();

		
	}
	
	
	
	
	public void eastPanel() {
		System.out.println("Inside eastPanel()");
		btnStart = new JButton("Start");
		btnStop = new JButton("Stop");
		btnTempoUp = new JButton("Tempo Up");
		btnTempoDown = new JButton("Tempo Down");
		panelEast = new JPanelMy();
		panelEast.setBackground(Color.PINK);
		panelEast.setLayout( new BoxLayout(panelEast, BoxLayout.Y_AXIS) );
		panelEast.add(btnStart);
		panelEast.add(btnStop);
		panelEast.add(btnTempoUp);
		panelEast.add(btnTempoDown);
		contentPanel.add(panelEast);
	}
	
	public void westPanel() {
		int count=0;
		String[] labelList = {"Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare","Crash Cymbal","Hand Clap","High Tom",
					"Hi Bingo","Maracas","Whistle","Low Conga","Cowbell","Vibraslap","Low-mid Tom","High Agogo", "Open Hi Conga"};
		
		panelCenterFull = new JPanelMy();
		panelCenterFull.setBackground(Color.BLUE);
		panelCenterFull.setLayout( new BoxLayout(panelCenterFull,BoxLayout.Y_AXIS) );
		check = new JCheckBox[16][16];
		
		for ( String labelString : labelList) {
			panelCenterRow = new JPanelMy();
			panelCenterRow.setBackground(Color.ORANGE);
			label = new JLabel(labelString);
			label.setPreferredSize( new Dimension(100, (int) label.getPreferredSize().getHeight() ) );
			panelCenterRow.add(label);
			
			for ( int j=0 ; j< 16 ; j++ ) {
				check[count][j] = new JCheckBox();
				panelCenterRow.add(check[count][j]);
			}
			panelCenterFull.add(panelCenterRow);
			count++;
		}
		contentPanel.add( panelCenterFull);
	}
	
	class BeatInner  {
		Sequencer player;
		Sequence sequence;
		Track[] track;
		ShortMessage msg;
		MidiEvent midi;
		int tempo = 100;
		BeatInnerStart actionStart = new BeatInnerStart();
		BeatInnerStop actionStop = new BeatInnerStop();
		BeatInnerTempoUp actionTempoUp = new BeatInnerTempoUp();
		BeatInnerTempoDown actionTempoDown = new BeatInnerTempoDown();
		
		
		
		public void setUp() {
			try {
				player = MidiSystem.getSequencer();
				player.open();
				sequence = new Sequence(Sequence.PPQ, 4);
				track = new Track[16]; 
				for ( int i = 0 ; i < 16 ; i++ ) {
					track[i] = sequence.createTrack();
				}
				player.setSequence(sequence);
				System.out.println("setting up the sound system (inside try)");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		public void createEvent(int one, int two, int three, int four, int five, int trk) {
			msg = new ShortMessage();
			try {
				msg.setMessage(one,two,three,four);
			} catch(Exception ex) { ex.printStackTrace(); }
			midi = new MidiEvent(msg, five);
			track[trk].add( midi );
		}
		
		public void setUpTrack() {
			int k=0;
			int[] instruments = {35,42,46,38,49,39,50,60,70,71,64,56,58,47,67,63};
			//for ( int ins : instruments) {
				createEvent(192,1,39,100,0,1);
				k++;
			//}

			for ( int i = 0 ; i < 60 ; i+=5 ) {
				createEvent(144,1,i,100,i+1,1);
				createEvent(128,1,i,100,i+3,1); 
			}
		}
		
		public void setUpActionListeners() {
			btnStart.addActionListener(actionStart);
			btnStop.addActionListener(actionStop);
			btnTempoUp.addActionListener(actionTempoUp);
			btnTempoDown.addActionListener(actionTempoDown);
		}
		
		class BeatInnerStart implements ActionListener {
			public void actionPerformed(ActionEvent event ) {
				long tickLength = player.getTickLength();
				System.out.println("Start Button Clicked");
				setUpTrack();
				if ( player.getTickPosition() < tickLength ) {
					player.start();
				}
				else {
					player.setTickPosition(0);
					player.start();
				}
					
				
			}
		}
		
		class BeatInnerStop implements ActionListener {
			public void actionPerformed(ActionEvent event ) {
				System.out.println("Stop Button clicked");
				player.stop();
			}
		}
		
		class BeatInnerTempoUp implements ActionListener {
			public void actionPerformed(ActionEvent event ) {
				System.out.println("Tempo Up button clicked");
				player.setTempoInBPM(tempo+=10);
			}
		}
		
		class BeatInnerTempoDown implements ActionListener {
			public void actionPerformed(ActionEvent event ) {
				System.out.println("Tempo Down button clicked");
				player.setTempoInBPM(tempo-=10);
			}
		}
	}

}	




