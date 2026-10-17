import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class CellButton extends JButton{
    private static final int DIVIDING_BORDER_WEIGHT = 6;

    public CellButton(int row, int col, int val){
        MatteBorder border = new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);

        setButtonVal(val);
        setBackground(Color.BLACK);
        setForeground(Color.LIGHT_GRAY);
        setFont(new Font("Arial", Font.BOLD, 32));
        setPreferredSize(new Dimension(50, 50));
        setFocusable(false);

        if(row % 3 == 0 && col % 3 == 0 && row != 9 && col != 9) border = new MatteBorder(1, 1, DIVIDING_BORDER_WEIGHT, DIVIDING_BORDER_WEIGHT, Color.LIGHT_GRAY);
        else if(col % 3 == 0 && col != 9) border = new MatteBorder(1, 1, 1, DIVIDING_BORDER_WEIGHT, Color.LIGHT_GRAY);
        else if(row % 3 == 0 && row != 9) border = new MatteBorder(1, 1, DIVIDING_BORDER_WEIGHT, 1, Color.LIGHT_GRAY);

        setBorder(border);
    }

    public void setButtonVal(int val){
        setText(val == 0 ? " " : val + "");
    }
}
