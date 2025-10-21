import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanel extends MyPanel{
	
	JPanel buttonPanel, dataPanel, timePanel;
	MyButton bSolve, bFileSelect;
	JLabel dataLabel;
	JLabel timerLabel;
	
	ControlPanel() {
		
		this.setLayout(new BorderLayout(0,0));
		
		this.add(makeDataP(),BorderLayout.WEST);
		this.add(makeButtonP(),BorderLayout.CENTER);
		this.add(makeTimeP(),BorderLayout.EAST);
	}
	
	public MyButton getSolveButton() {
		return bSolve;
	}
	
	public MyButton getFileButton() {
		return bFileSelect;
	}
	
	public JPanel makeButtonP(){
		buttonPanel = new MyPanel();
		
		bSolve = new MyButton(EGGSHELL, "Solve");
		bFileSelect = new MyButton(EGGSHELL, "Load Puzzle");
		
		buttonPanel.add(bSolve);
		buttonPanel.add(bFileSelect);
		
		return buttonPanel;
	}
	
	public JPanel makeDataP() {
	    dataPanel = new MyPanel();

	    dataLabel = new JLabel("Backtracks: 0"); 
	    this.applyTheme(dataLabel);

	    dataPanel.add(dataLabel);

	    return dataPanel;
	}
	
	public void updateBacktrackCount(int count) {
	    dataLabel.setText("Backtracks: " + count);
	    dataLabel.repaint();
	}
	
	public JPanel makeTimeP() {
	    timePanel = new MyPanel();

	    // Create a label that will show the timer dynamically
	    timerLabel = new JLabel("Time: 0.00");
	    this.applyTheme(timerLabel); // apply your styling

	    // Add the label to the panel
	    timePanel.add(timerLabel);

	    return timePanel;
	}

	
	public void updateTimer(double seconds) {
	    timerLabel.setText(String.format("%.2f s", seconds));
	    timerLabel.repaint(); // ensures GUI refresh
	}

	
	public void applyTheme(JLabel label) {
		
		label.setFont(new Font("Arial", Font.BOLD, 16));
		label.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(BURNTORANGE, 3),
				BorderFactory.createEmptyBorder(5,10,5,10)
				));
		label.setForeground(BURNTORANGE);
		label.setBackground(EGGSHELL);
		label.setOpaque(true);
	}
}
