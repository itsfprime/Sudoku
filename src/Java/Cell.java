package Java;

public class Cell {
    private int row;
    private int col;
    private int val;
    private int correctVal;
    private CellButton button;
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

        if (button != null) {
            button.setText(val == 0 || val == 10 ? "" : Integer.toString(val));
        }
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
}
