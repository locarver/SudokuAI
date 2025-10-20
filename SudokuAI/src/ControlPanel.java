import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanel extends MyPanel{
	
	JPanel buttonPanel, dataPanel, timePanel;
	MyButton bSolve, bFileSelect;
	
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
		JLabel data = new JLabel("Calls");
		
		this.applyTheme(data);
		
		dataPanel.add(data);
		
		return dataPanel;
	}
	
	public JPanel makeTimeP() {
		timePanel = new MyPanel();
		JLabel timeL = new JLabel("Time");
		JLabel timer = new JLabel("place holder");
		
		this.applyTheme(timeL);
		this.applyTheme(timer);
		
		timePanel.add(timeL);
		timePanel.add(timer);
		
		return timePanel;
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
