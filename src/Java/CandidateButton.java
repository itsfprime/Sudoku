package Java;

import javax.swing.*;
import java.awt.*;

public class CandidateButton extends JButton{
    private final int value;

    public CandidateButton(int value){
        super();
        this.value = value;
        setBackground(Color.BLACK);
        setForeground(Color.LIGHT_GRAY);
        setFont(new Font("Arial", Font.BOLD, 32));
        setPreferredSize(new Dimension(75, 50));
        setFocusable(false);

        if(value < 10){
            setText(value + "");
        }
        else{
            setText("X");
        }
    }

    public int getValue(){
        return value;
    }
}
