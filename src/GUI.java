import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GUI
{
    private JFrame baseFrame;
    private OptionDialog optionDialog;
    private FirstPanel firstPanel;
    private SecondPanel secondPanel;
    private ThirdPanel thirdPanel;
    private JSplitPane splitPane;
    private JSplitPane splitPane2;

    public GUI ()
    {
        splitPane = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize (3);
        splitPane.setBorder (new LineBorder (Color.DARK_GRAY,1));
        splitPane2 = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT);
        splitPane2.setDividerSize (3);
        splitPane2.setBorder (new LineBorder (Color.DARK_GRAY,1));


        baseFrame = new JFrame ("HTTP client");
        baseFrame.setLocation (100,80);
        baseFrame.setMinimumSize (new Dimension (1000,650));
        baseFrame.setSize (1350,670);
        baseFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        baseFrame.setLayout (new BorderLayout ());
        optionDialog = new OptionDialog (baseFrame,"Options");

        firstPanel = new FirstPanel ();
        secondPanel = new SecondPanel ();
        thirdPanel = new ThirdPanel ();
        addMenuBar ();
        splitPane2.setRightComponent (thirdPanel);
        splitPane2.setLeftComponent (secondPanel);
        splitPane.setLeftComponent (firstPanel);
        splitPane.setRightComponent (splitPane2);
        baseFrame.add (splitPane);
    }

    private void addMenuBar ()
    {
        baseFrame.setJMenuBar (new MenuBar (this));
    }

    public OptionDialog getOptionDialog () {
        return optionDialog;
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

    public JSplitPane getSplitPane2 () {
        return splitPane2;
    }

    public SecondPanel getSecondPanel () {
        return secondPanel;
    }


    public void setBaseFrameVisible ()
    {
        baseFrame.setVisible (true);
    }
}
