import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CandidateButton extends JButton implements WindowListener, ActionListener{
    private int value;

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(value < 10){

        }
        else{

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // Do nothing
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // Do nothing
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // Do nothing
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // Do nothing
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // Do nothing
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // Do nothing
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // Do nothing
    }
}
