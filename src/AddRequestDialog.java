import javax.swing.*;

import java.awt.*;



public class AddRequestDialog extends JDialog {


    public AddRequestDialog (JFrame baseFrame, String title) {
        super (baseFrame, title);
        setBackground (Color.WHITE);
        setLayout (new BorderLayout (5, 5));
        setSize (600, 200);
        setLocationRelativeTo (baseFrame);
        setDefaultCloseOperation (WindowConstants.HIDE_ON_CLOSE);
        addBasePanel ();
        setResizable (false);
    }



    public void addBasePanel () {


    }

}