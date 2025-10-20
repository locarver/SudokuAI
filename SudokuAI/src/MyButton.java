import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MyButton extends JButton{
	
	MyButton(Color colour, String text){
		
		this.setText(text);
		this.setFont(new Font("Arial", Font.BOLD, 16));
		this.setForeground(new Color(204,85,0));
		this.setPreferredSize(new Dimension(150,50));
		this.setFocusable(false);
		this.setBackground(colour);
		this.setBorder(BorderFactory.createLineBorder(new Color(204,85,0), 3));
	}
}
