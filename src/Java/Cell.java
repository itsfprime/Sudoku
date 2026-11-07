package Java;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Cell {
    private final int row;
    private final int col;
    private int val;
    private int correctVal;
    private CellButton button;
    JLabel candidatesLabel;
    private boolean isClue;

    public Cell(int row, int col, int val){
        this.row = row;
        this.col = col;
        this.val = val;
        isClue = false;
    }

    public boolean isClue(){
        return isClue;
    }

    public void toggleIsClue(){
        isClue = !isClue;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public int getVal(){
        return val;
    }

    public void setVal(int val) {
        this.val = val;
        if (button != null) {
            if (candidatesLabel != null) button.remove(candidatesLabel); // avoid overlap with number
            setButtonText(val);
        }
    }

    public void setButton(int val){
        button = new CellButton(row, col, val);
    }

    public void setButtonText(int value){
        button.setText(value == 0 || value == 10 ? "" : value + "");
    }

    public CellButton getButton(){
        return button;
    }

    public int getCorrectVal() {
        return correctVal;
    }

    public void setCorrectVal(int correctVal){
        this.correctVal = correctVal;
    }

    // Create a 3x3 grid of characters
    // Track a pointer through character array
    // Track a pointer through candidates array
    // If value at pointer in candidates array is not equal to the absolute position(0-9) of value at pointer in character array, that character is a space
    // Otherwise, that character is candidates at candidates pointer
    public void setCandidates(ArrayList<Integer> candidates) {
        if (button != null && candidatesLabel != null) button.remove(candidatesLabel);
        candidatesLabel = null;
        if (button == null || candidates == null || candidates.isEmpty()) return;

        int[][] grid = new int[3][3];
        for (int c : candidates) grid[(c - 1) / 3][(c - 1) % 3] = c;

        StringBuilder sb = new StringBuilder("<html><div style='text-align:center'>");
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                sb.append(grid[r][c] == 0 ? "&nbsp;" : String.valueOf(grid[r][c]));
                if (c < 2) sb.append("&nbsp;&nbsp;");
            }
            if (r < 2) sb.append("<br>");
        }
        sb.append("</div></html>");

        candidatesLabel = new JLabel(sb.toString(), SwingConstants.CENTER);
        candidatesLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        candidatesLabel.setForeground(Color.LIGHT_GRAY);
    }

    public void setCandidatesVisible(boolean visible) {
        if (button == null) return;
        if (candidatesLabel != null) button.remove(candidatesLabel);
        if (visible && candidatesLabel != null && !isClue && val != correctVal) {
            button.setText("");
            button.setLayout(new BorderLayout());
            button.add(candidatesLabel, BorderLayout.CENTER);
        }
        button.revalidate();
        button.repaint();
    }
}
