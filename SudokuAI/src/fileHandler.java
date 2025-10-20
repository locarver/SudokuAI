
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class fileHandler {


    public static int[][] fileIntoArray(File sudokuFile) {
        int[][] grid = new int[9][9];


        try (BufferedReader br = new BufferedReader(new FileReader(sudokuFile))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null && row < 9) {

                String[] values = line.trim().split(",");

                if (values.length != 9) {
                    throw new IllegalArgumentException("error line length at this row " + (row + 1));
                }

                for (int col = 0; col < 9; col++) {
                    grid[row][col] = Integer.parseInt(values[col].trim());
                }

                row++;
            }

            if (row != 9) {
                throw new IllegalArgumentException("file error not right amount of rows");
            }

        } catch (IOException e) {
            System.err.println("file error " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("number in file error " + e.getMessage());
        }

        return grid;
        }
	}
