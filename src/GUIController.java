import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIController {
    private Board board;
    private JFrame mainFrame;
    private JPanel masterPanel;
    private JPanel boardPanel;
    private JPanel candidatesPanel;
    private JPanel controlPanel;
    private JLabel mistakesText;
    private JLabel selectedText;
    private JLabel clueCountText;
    private JButton newPuzzleButton;
    private CandidateButton[] candidateButtons;
    private static final int CANDIDATE_BUTTONS_COUNT = 10;
    private static final Dimension FRAME_SIZE = new Dimension(800, 800);
    private int mistakes;
    private int selected;

    public GUIController(Board board){
        this.board = board;
    }

    public void refreshBoard() {
        boardPanel.removeAll();

        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLS; col++) {
                Cell cell = board.getBoard()[row][col];
                cell.getButton().setText(cell.getVal() == 0 ? " " : Integer.toString(cell.getVal()));
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void rebuildBoardPanel() {
        masterPanel.remove(boardPanel);

        createBoardPanel(); // Build a panel using the current board's cells/buttons

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        masterPanel.add(boardPanel, gbc);

        masterPanel.revalidate();
        masterPanel.repaint();
    }

    public void newBoard() {
        board.generatePuzzle(SudokuCommons.clueCount);
        System.out.println(board);
        System.out.println("Board is valid: " + board.validBoard());
        System.out.println("Board has one solution: " + (board.hasOneSolution() ? "Y" : "N"));
        System.out.println("Board is empty: " + (board.isEmpty() ? "Y" : "N"));
        rebuildBoardPanel();
        rebuildControlPanel();
    }

    public void createWindow(){
        mainFrame = new JFrame("Fprime's Sudoku");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(FRAME_SIZE);
        mainFrame.setLocation(575, 120);

        createMasterPanel();
        mainFrame.add(masterPanel);

        mainFrame.pack();
        mainFrame.setMinimumSize(new Dimension(mainFrame.getWidth(), mainFrame.getHeight() + 225));
        mainFrame.setVisible(true);
    }

    private void createMasterPanel(){
        masterPanel = new JPanel(new GridBagLayout());
        masterPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        createBoardPanel();
        createCandidatesPanel();
        createControlPanel();
        masterPanel.add(boardPanel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 4, 0, 0);
        masterPanel.add(candidatesPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(4, 0, 0, 0);
        masterPanel.add(controlPanel, gbc);
    }

    private void createControlPanel(){
        controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(Color.LIGHT_GRAY);
        Font controlPanelFont = new Font("Arial", Font.PLAIN, 18);
        Dimension textBoxSize = new Dimension(120, 35);

        newPuzzleButton = new JButton("New Puzzle");
        newPuzzleButton.setBackground(Color.BLACK);
        newPuzzleButton.setForeground(Color.LIGHT_GRAY);
        newPuzzleButton.setFont(controlPanelFont);
        newPuzzleButton.setPreferredSize(new Dimension(150,  35));
        newPuzzleButton.setFocusable(false);

        newPuzzleButton.addActionListener(e -> newBoard());

        mistakesText = new JLabel("Mistakes: " + mistakes);
        mistakesText.setFont(controlPanelFont);
        mistakesText.setPreferredSize(textBoxSize);
        mistakesText.setFocusable(false);

        selectedText = new JLabel("Selected: " + selected);
        selectedText.setFont(controlPanelFont);
        selectedText.setPreferredSize(textBoxSize);
        selectedText.setFocusable(false);

        clueCountText = new JLabel("Clues: " + board.countClues());
        clueCountText.setFont(controlPanelFont);
        clueCountText.setPreferredSize(textBoxSize);
        clueCountText.setFocusable(false);

        controlPanel.add(newPuzzleButton);
        controlPanel.add(mistakesText);
        controlPanel.add(selectedText);
        controlPanel.add(clueCountText);
    }

    public void updateMistakesText(){
        mistakesText = new JLabel("Mistakes: " + mistakes);
        rebuildControlPanel();
    }

    private void rebuildControlPanel(){
        masterPanel.remove(controlPanel);

        createControlPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(4, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        masterPanel.add(controlPanel, gbc);

        masterPanel.revalidate();
        masterPanel.repaint();
    }

    public void updateSelectedText(){
        selectedText = new JLabel("Selected: " + selected);
        rebuildControlPanel();
    }

    public void incrementMistakes(){
        mistakes++;
        updateMistakesText();
    }

    private void createCandidatesPanel(){
        candidatesPanel = new JPanel(new GridLayout(10, 1));
        candidatesPanel.setBackground(Color.BLACK);
        candidateButtons = new CandidateButton[CANDIDATE_BUTTONS_COUNT];

        for(int i = 0; i < candidateButtons.length; i++){
            CandidateButton button = new CandidateButton(i+1);
            candidateButtons[i] = button;
            candidatesPanel.add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selected = button.getValue();
                    updateSelectedText();
                }
            });
        }

        // Last element is clear, essentially do nothing with only clue board state
        selected = CANDIDATE_BUTTONS_COUNT;
    }

    private void createBoardPanel(){
        boardPanel = new JPanel(new GridLayout(9, 9));
        boardPanel.setBackground(Color.BLACK);
        boardPanel.setPreferredSize(new Dimension(450, 500));
        for(int i = 0; i < Board.ROWS; i++){
            for(int k = 0; k < Board.COLS; k++){
                boardPanel.add(board.getBoard()[i][k].getButton());
            }
        }
        mistakes = 0;

    }

    public void setBoard(Board board){
        this.board = board;
    }
}
