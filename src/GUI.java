import javax.swing.*;
import java.awt.*;

public class GUI
{
    private JFrame baseFrame;
    private FirstPanel firstPanel;
    private SecondPanel secondPanel;

    public GUI ()
    {
        baseFrame = new JFrame ("HTTP client");
        baseFrame.setLocation (100,80);
        baseFrame.setMinimumSize (new Dimension (1000,650));
        baseFrame.setSize (1350,670);
        baseFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        baseFrame.setLayout (new BorderLayout ());

        addMenuBar ();
        addFirstPanel ();
        addSecondPanel ();
    }

    private void addMenuBar ()
    {
        baseFrame.setJMenuBar (new MenuBar (this));
    }

    private void addFirstPanel ()
    {
        firstPanel = new FirstPanel ();
        baseFrame.add (firstPanel,BorderLayout.WEST);
    }

    public FirstPanel getFirstPanel () {
        return firstPanel;
    }

    public JFrame getBaseFrame () {
        return baseFrame;
    }

    public SecondPanel getSecondPanel () {
        return secondPanel;
    }

    private void addSecondPanel ()
    {
        secondPanel = new SecondPanel ();
        baseFrame.add (secondPanel,BorderLayout.CENTER);
    }
    public void setBaseFrameVisible ()
    {
        baseFrame.setVisible (true);
    }
}
