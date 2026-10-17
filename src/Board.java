import java.util.ArrayList;
import java.util.Random;

public class Board {
    private Cell[][] cells;
    // Rows span left to right
    public static final int ROWS = 9;
    // Cols span top to bottom
    public static final int COLS = 9;
    private static final Random RANDOM = new Random();
    private Solver solver;

    public Board(){}

    public void setSolver(){
        solver = new Solver();
    }

    public void generateBoard() {
        cells = new Cell[ROWS][COLS];
        recGen(1, 1);
        setSolver();
    }

    public boolean validBoard(){
        return solver.hasSolution(this);
    }

    public boolean hasOneSolution(){
        return solver.isValid();
    }

    public void setPuzzle(){
        while(!(getNonZeroElements() < 50)){
            // Pick a random cell value, if that cell's value is 0 or is a clue cell pick a new one
            Cell curr;
            do{
                curr = cells[RANDOM.nextInt(0, ROWS)][RANDOM.nextInt(0, COLS)];
            } while (curr.getVal() == 0 || curr.isClue());

            // Store the initial value of the cell
            int initialValue = curr.getVal();
            // Remove the value from this cell
            curr.setVal(0);
            // If this altered board can't be solved, restore original value and set this cell as a clue cell
            if(!solver.hasSolution(this)){
                curr.setVal(initialValue);
                curr.toggleIsClue();
            }
        }
    }

    public int getNonZeroElements(){
        int nonZeroElements = ROWS * COLS;
        for(int i = 0; i < ROWS; i++){
            for(int k = 0; k < COLS; k++){
                if(cells[i][k].getVal() == 0) nonZeroElements--;
            }
        }
        return nonZeroElements;
    }

    private boolean recGen(int row, int col) {
        // Past the last row means every cell was filled successfully.
        if (row > ROWS) {
            return true;
        }

        if (cells[row - 1][col - 1] == null) {
            cells[row - 1][col - 1] = new Cell(row, col, 0);
        }

        Cell curr = cells[row - 1][col - 1];
        curr.setVal(0);

        ArrayList<Integer> candidates = getCandidates(curr);

        if (candidates.isEmpty()) {
            return false;
        }

        // Debug
        //System.out.println("Valid candidates (" + row + ", " + col + "): " + candidates.size());

        // Advance to the next cell.
        int nextRow = row;
        int nextCol = col + 1;
        if (nextCol > COLS) {
            nextRow++;
            nextCol = 1;
        }

        while (!candidates.isEmpty()) {
            int randomIndex = RANDOM.nextInt(candidates.size());
            int candidate = candidates.remove(randomIndex);
            curr.setVal(candidate);

            if (recGen(nextRow, nextCol)) {
                curr.setButton();
                return true;
            }
        }

        // Every candidate led to a dead end; undo this choice.
        curr.setVal(0);
        curr.setButton();
        return false;
    }

    public ArrayList<Integer> getCandidates(Cell curr){
        ArrayList<Integer> grid = gridValues(curr);
        ArrayList<Integer> rowVals = rowValues(curr);
        ArrayList<Integer> colVals = columnValues(curr);
        ArrayList<Integer> candidates = new ArrayList<>();

        for (int value = 1; value <= 9; value++) {
            if (!grid.contains(value)
                    && !rowVals.contains(value)
                    && !colVals.contains(value)) {
                candidates.add(value);
            }
        }

        return candidates;
    }

    private ArrayList<Integer> rowValues(Cell c) {
        ArrayList<Integer> visited = new ArrayList<>();
        int row = c.getRow() - 1;

        for (int col = 0; col < COLS; col++) {
            Cell curr = cells[row][col];
            if (curr != null && curr.getVal() != 0) {
                visited.add(curr.getVal());
            }
        }

        return visited;
    }

    private ArrayList<Integer> columnValues(Cell c) {
        ArrayList<Integer> visited = new ArrayList<>();
        int col = c.getCol() - 1;

        for (int row = 0; row < ROWS; row++) {
            Cell curr = cells[row][col];
            if (curr != null && curr.getVal() != 0) {
                visited.add(curr.getVal());
            }
        }

        return visited;
    }

    private ArrayList<Integer> gridValues(Cell c) {
        ArrayList<Integer> visited = new ArrayList<>();

        int originRow = ((c.getRow() - 1) / 3) * 3;
        int originCol = ((c.getCol() - 1) / 3) * 3;

        for (int row = originRow; row < originRow + 3; row++) {
            for (int col = originCol; col < originCol + 3; col++) {
                Cell curr = cells[row][col];
                if (curr != null && curr.getVal() != 0) {
                    visited.add(curr.getVal());
                }
            }
        }

        return visited;
    }

    public String toString(){
        if(isEmpty()) return "Board is empty";
        StringBuilder ret = new StringBuilder();
        for(int row = 0; row < ROWS; row++){
            for(int column = 0; column < COLS; column++){
                Cell curr = cells[row][column];
                if(column % 3 == 0 && column != 0) ret.append(" | ");
                if(row % 3 == 0 && column == 0 && row != 0) ret.append("-".repeat(35)).append("\n");
                if(curr.getVal() != 0) ret.append("(").append(cells[row][column].getVal()).append(")");
                else ret.append("( )");
            }
            ret.append("\n");
        }
        return ret.toString();
    }

    public Cell[][] getBoard(){
        return cells;
    }

    public boolean isEmpty(){
        for(int i = 0; i < ROWS; i++){
            for(int k = 0; k < COLS; k++){
                if(cells[i][k].getVal() != 0) return false;
            }
        }
        return true;
    }
}
