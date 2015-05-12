import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BeatBoxMy2 {
	private JFrame frame;
	private JCheckBox check;
	private JButton btnStart, btnStop, btnTempoUp, btnTempoDown;
	private JLabel label;
	private JPanelMy panelCenterFull;
	private JPanelMy panelEast;
	private JPanelMy panelWest;
	private JPanelMy panelCenterRow;
	private JPanelMy contentPanel;
	
	private JList list;
	
	public static void main (String [] args ) {
		BeatBoxMy gui = new BeatBoxMy();
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
		westPanel();
		centerPanel();
		eastPanel();
		
		frame.getContentPane().add(contentPanel);
		frame.setVisible(true);
		
		
	}
	
	
	public void eastPanel() {
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
	
	public void centerPanel() {
		
		
	}
	
	public void westPanel() {
		String[] labelList = {"Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare","Crash Cymbal","Hand Clap","High Tom",
					"Hi Bingo","Maracas","Whistle","Low Conga","Cowbell","Vibraslap","Low-mid Tom","High Agogo", "Open Hi Conga"};
		
		panelCenterFull = new JPanelMy();
		panelCenterFull.setBackground(Color.BLUE);
		panelCenterFull.setLayout( new BoxLayout(panelCenterFull,BoxLayout.Y_AXIS) );
		
		for ( String labelString : labelList) {
			panelCenterRow = new JPanelMy();
			panelCenterRow.setBackground(Color.ORANGE);
			label = new JLabel(labelString);
			label.setPreferredSize( new Dimension(100, (int) label.getPreferredSize().getHeight() ) );
			panelCenterRow.add(label);
			for ( int j=0 ; j< 16 ; j++ ) {
				check = new JCheckBox();
				panelCenterRow.add(check);
			}
			panelCenterFull.add(panelCenterRow);
		}
		contentPanel.add( panelCenterFull);
	}

}	