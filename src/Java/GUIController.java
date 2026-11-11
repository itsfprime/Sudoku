package Java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUIController {
    private final Board board;
    private JPanel masterPanel;
    private JPanel boardPanel;
    private JPanel candidatesPanel;
    private JPanel controlPanel;
    private JLabel mistakesText;
    private JLabel selectedText;
    private static final int CANDIDATE_BUTTONS_COUNT = 10;
    private static final Dimension FRAME_SIZE = new Dimension(800, 800);
    private int mistakes;
    private int selected;

    public GUIController(Board board){
        this.board = board;
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

    public void newBoardAsync(){
        SwingWorker<Board, Void> sw = new SwingWorker<>() {
            @Override
            protected Board doInBackground() {
                board.generateBoard();
                rebuildBoardPanel();
                rebuildControlPanel();
                return board;
            }
        };
        sw.execute();
    }

    public void createWindow(){
        JFrame mainFrame = new JFrame("Fprime's Sudoku");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(FRAME_SIZE);
        mainFrame.setLocation(575, 120);

        createMasterPanel();
        mainFrame.add(masterPanel);

        mainFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_NUMPAD0:
                    case KeyEvent.VK_0:
                        selected = 10;
                        break;
                    case KeyEvent.VK_NUMPAD1:
                    case KeyEvent.VK_1:
                        selected = 1;
                        break;
                    case KeyEvent.VK_NUMPAD2:
                    case KeyEvent.VK_2:
                        selected = 2;
                        break;
                    case KeyEvent.VK_NUMPAD3:
                    case KeyEvent.VK_3:
                        selected = 3;
                        break;
                    case KeyEvent.VK_NUMPAD4:
                    case KeyEvent.VK_4:
                        selected = 4;
                        break;
                    case KeyEvent.VK_NUMPAD5:
                    case KeyEvent.VK_5:
                        selected = 5;
                        break;
                    case KeyEvent.VK_NUMPAD6:
                    case KeyEvent.VK_6:
                        selected = 6;
                        break;
                    case KeyEvent.VK_NUMPAD7:
                    case KeyEvent.VK_7:
                        selected = 7;
                        break;
                    case KeyEvent.VK_NUMPAD8:
                    case KeyEvent.VK_8:
                        selected = 8;
                        break;
                    case KeyEvent.VK_NUMPAD9:
                    case KeyEvent.VK_9:
                        selected = 9;
                        break;
                    default:
                        break;
                }
                updateSelectedText();
                highlightCells();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

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

    private void refreshCandidates() {
        boolean on = SudokuCommons.autoCandidates.get();
        for (Cell[] row : board.getBoard()) {
            for (Cell cell : row) {
                cell.setCandidates(board.getCandidates(cell));
                cell.setCandidatesVisible(on);
            }
        }
    }

    private void createControlPanel(){
        controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(Color.LIGHT_GRAY);
        Font controlPanelFont = new Font("Arial", Font.PLAIN, 18);
        Dimension textBoxSize = new Dimension(120, 35);

        JButton newPuzzleButton = new JButton("New Puzzle");
        newPuzzleButton.setBackground(Color.BLACK);
        newPuzzleButton.setForeground(Color.LIGHT_GRAY);
        newPuzzleButton.setFont(controlPanelFont);
        newPuzzleButton.setPreferredSize(new Dimension(150,  35));
        newPuzzleButton.setFocusable(false);

        newPuzzleButton.addActionListener(_ -> new SwingWorker<>() {
            @Override
            protected Object doInBackground() {
                newPuzzleButton.setText("Loading...");
                newPuzzleButton.setEnabled(false);
                newBoardAsync();
                newPuzzleButton.setText("New Puzzle");
                newPuzzleButton.setEnabled(true);
                return newPuzzleButton;
            }
        }.execute());

        mistakesText = new JLabel("Mistakes: " + mistakes);
        mistakesText.setFont(controlPanelFont);
        mistakesText.setPreferredSize(textBoxSize);
        mistakesText.setFocusable(false);

        selectedText = new JLabel("Selected: " + selected);
        selectedText.setFont(controlPanelFont);
        selectedText.setPreferredSize(textBoxSize);
        selectedText.setFocusable(false);

        JCheckBox autoCandidatesToggle = new JCheckBox("Auto Candidates");
        autoCandidatesToggle.setFont(controlPanelFont);
        autoCandidatesToggle.setFocusable(false);
        autoCandidatesToggle.setBackground(Color.LIGHT_GRAY);
        autoCandidatesToggle.setSelected(SudokuCommons.autoCandidates.get());
        if(autoCandidatesToggle.isSelected()) refreshCandidates();

        autoCandidatesToggle.addActionListener(_ -> {
            SudokuCommons.autoCandidates.set(autoCandidatesToggle.isSelected());
            refreshCandidates();
        });

        JLabel clueCountText = new JLabel("Clues: " + board.countClues());
        clueCountText.setFont(controlPanelFont);
        clueCountText.setPreferredSize(textBoxSize);
        clueCountText.setFocusable(false);

        controlPanel.add(newPuzzleButton);
        controlPanel.add(mistakesText);
        controlPanel.add(selectedText);
        controlPanel.add(clueCountText);
        controlPanel.add(autoCandidatesToggle);
    }

    public void updateMistakesText(){
        mistakesText.setText("Mistakes: " + mistakes);
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
        selectedText.setText("Selected: " + selected);
    }

    public void incrementMistakes(){
        mistakes++;
        updateMistakesText();
    }

    private void createCandidatesPanel(){
        candidatesPanel = new JPanel(new GridLayout(10, 1));
        candidatesPanel.setBackground(Color.BLACK);

        for(int i = 0; i < CANDIDATE_BUTTONS_COUNT; i++){
            CandidateButton button = new CandidateButton(i+1);
            candidatesPanel.add(button);

            button.addActionListener(_ -> {
                selected = button.getValue();
                updateSelectedText();
                highlightCells();
            });
        }

        // Last element is clear, essentially do nothing with only clue board state
        selected = CANDIDATE_BUTTONS_COUNT;
    }

    private void highlightCells(){
        for(Cell[] row : board.getBoard()){
            for(Cell c : row){
                if(c.getVal() == selected && c.getVal() != 10) c.getButton().setBackground(Color.BLUE);
                else c.getButton().setBackground(Color.BLACK);
            }
        }
    }

    private void createBoardPanel(){
        boardPanel = new JPanel(new GridLayout(9, 9));
        boardPanel.setBackground(Color.BLACK);
        boardPanel.setPreferredSize(new Dimension(450, 500));
        for(int i = 0; i < Board.ROWS; i++){
            for(int k = 0; k < Board.COLS; k++){
                Cell curr = board.getBoard()[i][k];
                boardPanel.add(curr.getButton());
                for (ActionListener l : curr.getButton().getActionListeners()) {
                    curr.getButton().removeActionListener(l);
                }
                if(!curr.isClue()){
                    curr.getButton().addActionListener(_ -> {
                        System.out.printf("(%d, %d): Clicked!\nSelected: %d\n", curr.getRow(), curr.getCol(), selected);
                        curr.setVal(selected);
                        if(selected != curr.getCorrectVal() && selected != 10){
                            curr.getButton().setForeground(Color.RED);
                            incrementMistakes();
                        }
                        else{
                            curr.getButton().setForeground(Color.CYAN);
                        }
                        highlightCells();
                        refreshCandidates();
                        System.out.printf("Selected: %d\nCorrect: %d\n", selected, curr.getCorrectVal());
                    });
                }
                else{
                    curr.getButton().addActionListener(_ -> {
                        selected = curr.getVal();
                        updateSelectedText();
                        highlightCells();
                    });
                }
            }
        }
    }
}
