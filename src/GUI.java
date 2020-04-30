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

    public void addRequestListPanel (JPanel requestList)
    {
        if (requestList == null)
            throw new InputMismatchException ("Input is not a matchPanel");
        baseFrame.add (requestList,BorderLayout.WEST);
    }

    public void addConfigurationPanel (JPanel configuration)
    {
        if (configuration == null)
            throw new InputMismatchException ("Input is not a matchPanel");
        baseFrame.add (configuration,BorderLayout.CENTER);
    }

    public void addResultPanel (JPanel result)
    {
        if (result == null)
            throw new InputMismatchException ("Input is not a matchPanel");
        baseFrame.add (result,BorderLayout.WEST);
    }

    public void setBaseFrameVisible ()
    {
        baseFrame.setVisible (true);
    }
}
