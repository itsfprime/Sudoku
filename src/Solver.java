import java.util.ArrayList;
import java.util.Random;

public class Solver {
    private static final Random RANDOM = new Random();
    private int[][] previousSolution;
    private int[][] currentSolution;
    private boolean hasOneSolution = true;
    private Board currentBoard;

    public Solver(){

    }

    public boolean hasSolution(Board board) {
        currentBoard = board;
        currentSolution = new int[Board.ROWS][Board.COLS];

        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                currentSolution[r][c] = board.getBoard()[r][c].getVal();
            }
        }

        boolean solvable = recSolve(1, 1);

        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                board.getBoard()[r][c].setVal(currentSolution[r][c]);
            }
        }

        previousSolution = currentSolution;
        currentBoard = null;

        return solvable;
    }

    public boolean isValid(){
        if(previousSolution != currentSolution && previousSolution != null) hasOneSolution = false;
        else hasOneSolution = true;
        return hasOneSolution;
    }

    private boolean recSolve(int x, int y){
        // All cells have correct values, board is solvable
        if (x > Board.ROWS) {
            return true;
        }
        Cell curr = currentBoard.getBoard()[x-1][y-1];

        // Get next row & col accounting for wrap-around
        int nextRow = x;
        int nextCol = y + 1;
        if (nextCol > Board.COLS) {
            nextRow++;
            nextCol = 1;
        }
        if(curr.getVal() != 0) return recSolve(nextRow, nextCol);
        // Value is zero, get candidates, pick randomly & remove from candidates,

        ArrayList<Integer> candidates = currentBoard.getCandidates(curr);

        while (!candidates.isEmpty()) {
            int randomIndex = RANDOM.nextInt(candidates.size());
            int candidate = candidates.remove(randomIndex);
            curr.setVal(candidate);

            if (recSolve(nextRow, nextCol)) {
                return true;
            }
        }

        // No valid candidates exist for this cell
        curr.setVal(0);
        return false;
    }
}
