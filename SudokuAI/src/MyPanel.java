import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class MyPanel extends JPanel{
	
	Color PISTACHIO = new Color(147,197,114);
	Color EGGSHELL = new Color(240,234,214);
	Color BURNTORANGE = new Color(204,85,0);
	
	MyPanel(){
		
		this.setPreferredSize(new Dimension(130,130));
		this.setBackground(PISTACHIO); 
		
	}
}
