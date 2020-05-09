import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.InputMismatchException;

public class GUI
{

    private JFrame baseFrame;
    private OptionDialog optionDialog;
    private FirstPanel firstPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
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
        optionDialog = new OptionDialog (this);
        firstPanel = new FirstPanel (this);
        secondPanel = new NullPanel (1);
        thirdPanel = new NullPanel (2);
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
        if (secondPanel instanceof SecondPanel)
            return (SecondPanel)secondPanel;
        else
            return null;
    }

    public ThirdPanel getThirdPanel () {
        if (thirdPanel instanceof ThirdPanel)
            return (ThirdPanel) thirdPanel;
        else
            return null;
    }

    public void setSecondPanel (JPanel secondPanel) {
        if (secondPanel == null)
            throw new InputMismatchException ("inValid input");
        this.secondPanel = secondPanel;
        secondPanel.setVisible (false);
        secondPanel.setVisible (true);
        splitPane2.setLeftComponent (secondPanel);
    }

    public void setThirdPanel (JPanel thirdPanel) {
        if (thirdPanel == null)
            throw new InputMismatchException ("inValid input");
        this.thirdPanel = thirdPanel;
        thirdPanel.setVisible (false);
        thirdPanel.setVisible (true);
        splitPane2.setRightComponent (thirdPanel);
    }

    public void setBaseFrameVisible ()
    {
        baseFrame.setVisible (true);
    }
}
