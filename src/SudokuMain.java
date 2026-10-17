public class SudokuMain {
    public static void main(String[] args) {
        Board board = new Board();
        board.generateBoard();
        GUIController gui = new GUIController(board);

        System.out.println(board);
        gui.createWindow();

        System.out.println("Puzzle transformation:\n");
        board.setPuzzle();

        debugBoard(board);

        while(board.hasOneSolution()){
            gui.newBoard();
            debugBoard(board);
        }
    }

    private static void debugBoard(Board board){
        System.out.println(board);
        System.out.println("Board is valid: " + board.validBoard());
        System.out.println("Board has one solution: " + (board.hasOneSolution() ? "Y" : "N"));
        System.out.println("Board is empty: " + (board.isEmpty() ? "Y" : "N"));
    }
}
