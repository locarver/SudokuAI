import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class sudokuSolver {
	
	public static int backtracks = 0; 
	
	
	 // Function to check if it is safe to place a num at specific index in the grid
   private static boolean placeableCheck(int[][] grid, int row, int col, int num) {
       
       // Check if that num is already in that row
       for (int x = 0; x < 9; x++)
           if (grid[row][x] == num)
               return false;

       // Check if that num is already in that column
       for (int x = 0; x < 9; x++)
           if (grid[x][col] == num)
               return false;

       // Check if that num is already in that 3x3 sub-gird
       int startRow = row - (row % 3);
       int startCol = col - (col % 3);
       
       for (int i = 0; i < 3; i++)
           for (int j = 0; j < 3; j++)
               if (grid[i + startRow][j + startCol] == num)
                   return false;

       return true;
   }
   
   //this is for the future checking gives each index in the grid a list of possible values it could be its domain
   private static Set<Integer>[][] initializeDomains(int[][] grid) {
       Set<Integer>[][] domains = new HashSet[9][9];

       for (int r = 0; r < 9; r++) {
           for (int c = 0; c < 9; c++) {
               domains[r][c] = new HashSet<>();

               // if the index is empty check what values can be placed
               if (grid[r][c] == 0) {
                   for (int num = 1; num <= 9; num++) {
                       if (placeableCheck(grid, r, c, num))
                           domains[r][c].add(num);
                   }
               } else {
                   // if the cell already has a number then thats its only possible value
                   domains[r][c].add(grid[r][c]);
               }
           }
       }
       

       return domains;
   }
   
   // creates a backup of the domains so that you can revert back when you backtrack
   private static Set<Integer>[][] copyDomains(Set<Integer>[][] domains) {
       Set<Integer>[][] copy = new HashSet[9][9];
       for (int r = 0; r < 9; r++)
           for (int c = 0; c < 9; c++)
               copy[r][c] = new HashSet<>(domains[r][c]);
       return copy;
   }
   
   // this removes a num from the domains of all index that are in the same row, column or sub-grid
   private static boolean forwardCheck(Set<Integer>[][] domains, int row, int col, int num) {

       // removes num if in the same row and column
       for (int i = 0; i < 9; i++) {
           if (i != col && domains[row][i].remove(num) && domains[row][i].isEmpty())
               return false;
           if (i != row && domains[i][col].remove(num) && domains[i][col].isEmpty())
               return false;
       }

       // removes num if in the same sub-grid
       int startRow = row - (row % 3);
       int startCol = col - (col % 3);

       for (int i = 0; i < 3; i++)
           for (int j = 0; j < 3; j++) {
               int r = startRow + i;
               int c = startCol + j;
               if ((r != row || c != col) && domains[r][c].remove(num) && domains[r][c].isEmpty())
                   return false;
           }

       return true;
   }
   
   
   // main function to recursively fill in the grid
   public static boolean solveSudoku(int[][] grid, int row, int col, Consumer<UpdateSudoku> onUpdate) {
	   
       // initialize the domains for each index
       Set<Integer>[][] domains = initializeDomains(grid);

       // start solving
       return solveWithForwardChecking(grid, domains, onUpdate);
       
       
   }

   
   // helper function that solves recursively
   private static boolean solveWithForwardChecking(int[][] grid, Set<Integer>[][] domains, Consumer<UpdateSudoku> onUpdate) {
	   Main.endTime = System.nanoTime();
       // find the next empty index
       int row = -1, col = -1;
       boolean found = false;

       for (int r = 0; r < 9 && !found; r++) {
           for (int c = 0; c < 9; c++) {
               if (grid[r][c] == 0) {
                   row = r;
                   col = c;
                   found = true;
                   break;
               }
           }
       }

       // base case
       if (!found) {
    	   Main.endTime = System.nanoTime();
    	   return true;
       }
       
       // try each num from the domain of that index
       for (int num : domains[row][col]) {

           // if it is safe to place num at that index then place the num
           if (placeableCheck(grid, row, col, num)) {
               grid[row][col] = num;
               
               onUpdate.accept(new UpdateSudoku(row,col,num)); //report the new placement

               // make a copy of the domains 
               Set<Integer>[][] newDomains = copyDomains(domains);

               //  keep forward checking
               if (forwardCheck(newDomains, row, col, num) && solveWithForwardChecking(grid, newDomains, onUpdate))
                   return true;
               
               // backtrack
               grid[row][col] = 0;
               onUpdate.accept(new UpdateSudoku(row,col,0)); // report backtrack
               backtracks++;
           }
       }

       return false;
   }


}

