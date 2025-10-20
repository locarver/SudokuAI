import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SudokuGrid extends MyPanel {
	
	SudokuGrid(JLabel[][] cells){
		
		this.setLayout(new GridLayout(9,9)); //9x9 grids
		
		//creating cells to put in 9x9 grid and 2d cells array
		for(int row = 0; row < 9; row++ ) {
			for(int col = 0; col < 9; col++) {
				JLabel cell = new JLabel();
				cells[row][col] = cell;
				
				//Styling the cell 
				cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("Arial", Font.BOLD, 16));
                cell.setBorder(createCellBorder(row, col));
                cell.setBackground(EGGSHELL);
                cell.setOpaque(true);
                
                this.add(cell); //adding cell to grid
			}
		}
	}
	
	public void updateGrid(int[][] inpGrid, JLabel[][] cells) {
		//update the grid to contain the new puzzle
		for(int row = 0; row < 9; row++) {
			for(int col = 0; col < 9; col++) { 
				int value = inpGrid[row][col];
				
				updateCell(row,col,value,cells);
			}
		}
	}
	
	
	//Separated so I can update a single cell without having to update the whole grid when trying to live display the solving
	public void updateCell(int row, int col, int value, JLabel[][] cells) {
		JLabel cell = cells[row][col];
		
		if (value == 0) {
			cell.setText("");
		}
		else {
			cell.setText(String.valueOf(value));
			cell.setForeground(BURNTORANGE);
		}
	}
	
	public Border createCellBorder(int row, int col) {
		
		//Method to create the border and clearly define the sub-grids 
		int top = 1, left = 1, bottom = 1, right = 1; 
        if (row % 3 == 0 && row != 0) top = 3;
        if (col % 3 == 0 && col != 0) left = 3;
        if (row == 8) bottom = 3; // Thicker border for the bottom edge
        if (col == 8) right = 3; // Thicker border for the right edge
        if (row == 0) top = 3;   // Thicker border for the top edge
        if (col == 0) left = 3;   // Thicker border for the left edge
        
        Border cellBorder = BorderFactory.createMatteBorder(top, left, bottom, right, BURNTORANGE);
        
		return cellBorder;
	}
	
}
