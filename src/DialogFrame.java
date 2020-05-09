import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.InputMismatchException;

public class DialogFrame extends JDialog {

    private GridBagLayout layout ;
    private GridBagConstraints constraints;
    private JPanel basePanel;

    public DialogFrame (JFrame baseFrame, String title) {
        super (baseFrame, title);
        setBackground (Color.WHITE);
        setLayout (new BorderLayout (5, 5));
        setLocationRelativeTo (baseFrame);
        setDefaultCloseOperation (WindowConstants.HIDE_ON_CLOSE);
        basePanel = new JPanel ();
        constraints = new GridBagConstraints ();
        layout = new GridBagLayout ();
        addBasePanel ();
        setResizable (false);
    }


    public void addComponent (JComponent component, int row, int col, int width) {
        if (getLayout () == null || getConstraints () == null)
            throw new InputMismatchException ("inValid input");
        getConstraints ().gridy = row;
        getConstraints ().gridx = col;
        getConstraints ().gridheight = 1;
        getConstraints ().gridwidth = width;

        getLayout ().setConstraints (component, getConstraints ());
        getBasePanel ().add (component);
        setVisible (false);
    }

    public void addBasePanel () {

        basePanel.setBackground (Color.WHITE);
        basePanel.setBorder (new EmptyBorder (5, 5, 5, 5));
        basePanel.setLayout (layout);
        add(basePanel);
    }

    public void changeVisible () {
        if (isVisible ())
            setVisible (false);
        else
            setVisible (true);
    }

    @Override
    public GridBagLayout getLayout () {
        return layout;
    }

    public GridBagConstraints getConstraints () {
        return constraints;
    }

    public JPanel getBasePanel () {
        return basePanel;
    }
}