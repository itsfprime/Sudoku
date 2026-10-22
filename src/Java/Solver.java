package Java;

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

    public int getSolutions(Board board) {
        currentBoard = board;
        currentSolution = new int[Board.ROWS][Board.COLS];

        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                currentSolution[r][c] = board.getBoard()[r][c].getVal();
            }
        }

        int solutions = recSolve(1, 1, 0);

        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                board.getBoard()[r][c].setVal(currentSolution[r][c]);
            }
        }

        previousSolution = new int[Board.ROWS][Board.COLS];
        for (int r = 0; r < Board.ROWS; r++) {
            previousSolution[r] = currentSolution[r].clone();
        }

        currentBoard = null;

        hasOneSolution = solutions == 1;

        return solutions;
    }

    public boolean isValid(){
        return hasOneSolution;
    }

    private int recSolve(int x, int y, int solutions) {
        if (solutions >= 2) return 2;
        if (x > Board.ROWS) return solutions + 1;

        Cell curr = currentBoard.getBoard()[x - 1][y - 1];
        int nextRow = x;
        int nextCol = y + 1;
        if (nextCol > Board.COLS) {
            nextRow++;
            nextCol = 1;
        }
        if (curr.getVal() != 0) return recSolve(nextRow, nextCol, solutions);

        ArrayList<Integer> candidates = currentBoard.getCandidates(curr);
        while (!candidates.isEmpty()) {
            int candidate = candidates.remove(RANDOM.nextInt(candidates.size()));
            curr.setVal(candidate);
            solutions = recSolve(nextRow, nextCol, solutions);
            curr.setVal(0); // undo this choice before trying another
            if (solutions >= 2) return 2;
        }
        return solutions;
    }
}
