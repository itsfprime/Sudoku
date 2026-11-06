package Java;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Cell {
    private int row;
    private int col;
    private int val;
    private int correctVal;
    private CellButton button;
    private JLabel candidatesLabel;
    private ArrayList<Integer> candidates;
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

    public void setRow(int row){
        this.row = row;
    }

    public void setCol(int col){
        this.col = col;
    }

    public int getVal(){
        return val;
    }

    public void setVal(int val) {
        this.val = val;

        setButton(val);
        button.setText(val == 0 || val == 10 ? "" : Integer.toString(val));
    }

    public boolean isEmpty(){
        return val == 0 || val == 10;
    }

    public void setButton(int val){
        button = new CellButton(row, col, val);
    }

    public void setButtonText(int value){
        button.setText(value == 0 ? "" : value + "");
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

    public void setCandidates(ArrayList<Integer> candidates){
        this.candidates = new ArrayList<>();
        this.candidates.addAll(candidates);
        setCandidatesLabel();
    }

    private void setCandidatesLabel(){
        if(candidates.isEmpty()){
            candidatesLabel = new JLabel();
            candidatesLabel.setForeground(Color.LIGHT_GRAY);
            int[][] layers = new int[3][3];
            for(int i : candidates) {
                int relPos = i % 3;
                if (i < 4) layers[0][relPos] = i;
                else if (i < 7) layers[1][relPos] = i;
                else if (i < 10) layers[2][relPos] = i;
            }
            for(int i = 0; i < layers.length; i++) {
                for (int k = 0; k < layers[i].length; k++) {
                    if (layers[i][k] != 0) {
                        candidatesLabel.setText(candidates.toString() + layers[i][k] + " ");
                    } else {
                        candidatesLabel.setText(candidates.toString() + " ");
                    }

                    if (k == 2) candidatesLabel.setText(candidates.toString() + "\n");
                }
            }
        }
    }

    public void toggleCandidatesLabel(){
        if(val < 10 && val != 0){
            if(button == null) System.out.println("Null button");
            if(candidatesLabel == null) System.out.println("Null label");
            button.add(candidatesLabel);
        }
    }
}