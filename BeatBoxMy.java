import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BeatBoxMy {
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
	
	public void westPanel() {
		panelWest = new JPanelMy();
		panelWest.setBackground(Color.GREEN);
		panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS));
		String[] labelList = {"Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare","Crash Cymbal","Hand Clap","High Tom",
					"Hi Bingo","Maracas","Whistle","Low Conga","Cowbell","Vibraslap","Low-mid Tom","High Agogo", "Open Hi Conga"};
		Font listFont = new Font(Font.SERIF, Font.ITALIC, 20);
		check = new JCheckBox();
		for ( String labelString : labelList) {
			label = new JLabel(labelString);
			label.setFont(listFont);
			label.setPreferredSize( new Dimension((int) label.getPreferredSize().getWidth(), (int) check.getPreferredSize().getHeight()));
			panelWest.add(label);
		}
		contentPanel.add(panelWest);
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
		
		panelCenterFull = new JPanelMy();
		panelCenterFull.setBackground(Color.BLUE);
		panelCenterFull.setLayout( new BoxLayout(panelCenterFull,BoxLayout.Y_AXIS) );
		
		for (int i = 0 ; i < 16 ; i++ ) {
			panelCenterRow = new JPanelMy();
			panelCenterRow.setBackground(Color.ORANGE);
			for ( int j=0 ; j< 16 ; j++ ) {
				check = new JCheckBox();
				panelCenterRow.add(check);
			}
			panelCenterFull.add(panelCenterRow);
		}
		contentPanel.add( panelCenterFull);
	}
	

}	