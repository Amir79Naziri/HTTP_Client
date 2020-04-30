import javax.swing.*;
import java.awt.*;
import java.util.InputMismatchException;

public class GUI
{
    private JFrame baseFrame;

    public GUI ()
    {
        baseFrame = new JFrame ("HTTP client");
        baseFrame.setLocation (100,80);
        baseFrame.setMinimumSize (new Dimension (1000,650));
        baseFrame.setSize (1350,670);
        baseFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        baseFrame.setLayout (new BorderLayout ());

        addMenuBar ();
    }

    private void addMenuBar ()
    {
        new MenuBar (baseFrame);
    }



    public void setBaseFrameVisible ()
    {
        baseFrame.setVisible (true);
    }
}
