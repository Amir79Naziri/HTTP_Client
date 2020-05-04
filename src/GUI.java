import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GUI
{
    private JFrame baseFrame;
    private FirstPanel firstPanel;
    private SecondPanel secondPanel;
    private JSplitPane splitPane;

    public GUI ()
    {
        splitPane = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize (1);
        splitPane.setBorder (new LineBorder (Color.DARK_GRAY,1));
        baseFrame = new JFrame ("HTTP client");
        baseFrame.setLocation (100,80);
        baseFrame.setMinimumSize (new Dimension (1000,650));
        baseFrame.setSize (1350,670);
        baseFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
//        baseFrame.setLayout (new BorderLayout ());

        addMenuBar ();
        addFirstPanel ();
        addSecondPanel ();
        baseFrame.add (splitPane);
    }

    private void addMenuBar ()
    {
        baseFrame.setJMenuBar (new MenuBar (this));
    }

    private void addFirstPanel ()
    {
        firstPanel = new FirstPanel ();
        splitPane.add (firstPanel);

    }

    public FirstPanel getFirstPanel () {
        return firstPanel;
    }

    public JFrame getBaseFrame () {
        return baseFrame;
    }

    public JSplitPane getSplitPane () {
        return splitPane;
    }

    public SecondPanel getSecondPanel () {
        return secondPanel;
    }

    private void addSecondPanel ()
    {
        secondPanel = new SecondPanel ();
        splitPane.add (secondPanel);
    }
    public void setBaseFrameVisible ()
    {
        baseFrame.setVisible (true);
    }
}
