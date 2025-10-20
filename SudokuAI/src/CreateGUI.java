import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

public class CreateGUI extends JFrame implements ActionListener {
	
	//Variables
	Color PISTACHIO = new Color(147,197,114);
	Color EGGSHELL = new Color(240,234,214);
	MyPanel titlePanel;
	ControlPanel control;
	SudokuGrid grid;
	MyButton bSolve, bFileSelect;
	JLabel[][] cells = new JLabel[9][9]; // used to store the grid
	
	int[][] puzzle;
	

	CreateGUI() {
		
		//Creating the frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(10,10));
		this.setTitle("Sudoku Solver");
		this.setSize(800,800);
		this.getContentPane().setBackground(PISTACHIO);
		
		//Creating the Panels
		titlePanel = createTitle(titlePanel);//Creating title
		grid = new SudokuGrid(cells);//Creating sudoku grid
		control = createControlP(control);//Creating control panel for buttons
		//Creating side buffer panels
		MyPanel buff = new MyPanel();
		MyPanel buff2 = new MyPanel();		
		
		//Adding the panels to the frame
		this.add(titlePanel,BorderLayout.NORTH);
		this.add(grid,BorderLayout.CENTER);
		this.add(control,BorderLayout.SOUTH);
		this.add(buff,BorderLayout.WEST);
		this.add(buff2,BorderLayout.EAST);
		
	}
	public MyPanel createTitle(MyPanel titlePan) {
		titlePan = new MyPanel();
		titlePan.setBorder(new EmptyBorder(30,10,10,10)); //Place title in the middle of title panel
		JLabel title = new JLabel("SUDOKU SOLVER"); //JLabel to hold the text
		title.setFont(new Font("Arial", Font.BOLD, 36)); 
		title.setForeground(new Color(204,85,0)); //Set title colour
		title.setBackground(EGGSHELL);
		title.setOpaque(true);
		title.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(204,85,0), 3),
				BorderFactory.createEmptyBorder(15,20,15,20)
				));
		titlePan.add(title);
		return titlePan;
	}
	
	public ControlPanel createControlP(ControlPanel controlP) {
		controlP = new ControlPanel();
		
		bSolve = controlP.getSolveButton();
		bFileSelect = controlP.getFileButton();
		
		bSolve.addActionListener(this);
		bFileSelect.addActionListener(this);
		
		return controlP;
	}
	
	public void selectFile() {
		JFileChooser fileChooser = new JFileChooser();
		int response = fileChooser.showOpenDialog(null);
		
		//Checking to make sure the user has selected a file
		if (response == fileChooser.APPROVE_OPTION) {
			
			File selectedFile = fileChooser.getSelectedFile();
			fileHandler fileHand = new fileHandler();
			puzzle = fileHand.fileIntoArray(selectedFile);
			
			grid.updateGrid(puzzle, cells);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == bSolve) {
			
			bSolve.setEnabled(false); //set false so can't be pressed again while solving
			
			SwingWorker<Boolean, UpdateSudoku> worker = new SwingWorker<>() {
				
				@Override
				public Boolean doInBackground() throws Exception{
					sudokuSolver solver = new sudokuSolver();
					
					Consumer<UpdateSudoku> updater = new Consumer<UpdateSudoku>() {

						@Override
						public void accept(UpdateSudoku update) {
							publish(update);
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					};
					return solver.solveSudoku(puzzle, 0, 0, updater);
				}
				
				@Override
				public void process(List<UpdateSudoku> chunks) {
					for(UpdateSudoku update : chunks) {
						grid.updateCell(update.row, update.col, update.value, cells);
					}
				}
				@Override
				public void done() {
					try {
						if (get()) {
							JOptionPane.showMessageDialog(null, "SOLVED");
						} else {
							JOptionPane.showMessageDialog(null, "Cant be solved");
						}
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					//re-enable buttons
					bSolve.setEnabled(true);
				}
			};
			
			worker.execute();
			
			sudokuSolver solvedSudoku = new sudokuSolver();
			grid.updateGrid(puzzle, cells);
			
		}
		if(e.getSource() == bFileSelect) {
			selectFile();
		}
		
	}

}
