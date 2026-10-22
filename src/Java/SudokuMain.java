package Java;

public class SudokuMain {
    public static void main(String[] args) {
        Board board = new Board();
        board.generateBoard();
        GUIController gui = new GUIController(board);

        System.out.println(board);
        gui.createWindow();

        System.out.println("Puzzle transformation:\n");
        System.out.println(board);

        debugBoard(board);
    }

    private static void generateAndDebugInvalidBoard(){
        System.out.println("=========================\nInvalid board test");
        Board b = new Board();
        b.generateBoard();
        for (Cell[] row : b.getBoard())
            for (Cell c : row) c.setVal(0);
        System.out.println(b);
        System.out.println("Board is valid: " + b.validBoard());
    }

    private static void debugBoard(Board board){
        System.out.println(board);
        System.out.println("Board is valid: " + board.validBoard());
        System.out.println("Board has one solution: " + (board.hasOneSolution() ? "Y" : "N"));
        System.out.println("Board is empty: " + (board.isEmpty() ? "Y" : "N"));
    }
}
