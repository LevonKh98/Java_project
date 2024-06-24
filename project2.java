import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class project2 {

    public static void main(String[] args) {
        // Ensure an input file name is provided
        if (args.length < 1) {
            System.out.println("Please provide the input file name as a command line argument.");
            return;
        }

        String inputFileName = args[0];
        try {
            // Initialize file and scanner to read from it
            File inputFile = new File(inputFileName);
            Scanner scanner = new Scanner(inputFile);

            // Read matrix dimensions and target value
            int m = scanner.nextInt(); // Number of rows
            int n = scanner.nextInt(); // Number of columns
            int target = scanner.nextInt(); // Target value to find

            // Populate the array from file
            int[][] array = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    array[i][j] = scanner.nextInt();
                }
            }

            scanner.close();

            // Start divide and conquer search; if not found, print NOT FOUND
            if (!divideAndConquer(array, 0, 0, m - 1, n - 1, target, true)) {
                System.out.println("NOT FOUND");
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Recursive divide and conquer method to find the target
    private static boolean divideAndConquer(int[][] array, int startRow, int startCol, int endRow, int endCol, int target, boolean isFirstCall) {
        // Base case: check bounds
        if (startRow > endRow || startCol > endCol) {
            return false;
        }

        // Optimization: Target not in range
        if (target < array[startRow][startCol] || target > array[endRow][endCol]) {
            printSubarray(array, startRow, startCol, endRow, endCol);
            return false;
        }

        if (!isFirstCall) { // Skip printing the entire array
            printSubarray(array, startRow, startCol, endRow, endCol);
        }

        // Find mid-points
        int midRow = (startRow + endRow) / 2;
        int midCol = (startCol + endCol) / 2;

        // Check mid-point against target
        if (target == array[midRow][midCol]) {
            System.out.println("Target location at: " + (midRow + 1) + " " + (midCol + 1));
            return true;
        } else if (array[midRow][midCol] < target) {
            // Target is larger; search right and below
            return divideAndConquer(array, startRow, midCol + 1, midRow, endCol, target, false) ||
                   divideAndConquer(array, midRow + 1, startCol, endRow, midCol, target, false) ||
                   divideAndConquer(array, midRow + 1, midCol + 1, endRow, endCol, target, false);
        } else {
            // Target is smaller; search left and above
            return divideAndConquer(array, startRow, startCol, midRow - 1, midCol - 1, target, false) ||
                   divideAndConquer(array, startRow, midCol, midRow - 1, endCol, target, false) ||
                   divideAndConquer(array, midRow, startCol, endRow, midCol - 1, target, false);
        }
    }

    // Method to print the current subarray being searched
    private static void printSubarray(int[][] array, int startRow, int startCol, int endRow, int endCol) {
        System.out.println("Subarray:");
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
}
