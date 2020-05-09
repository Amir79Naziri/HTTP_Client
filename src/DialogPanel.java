import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.InputMismatchException;

public class DialogPanel extends JPanel {

    private GridBagLayout layout ;
    private GridBagConstraints constraints;
    private JPanel basePanel;

    public DialogPanel () {
        super();
        setLayout (new BorderLayout (5, 5));
        basePanel = new JPanel ();
        constraints = new GridBagConstraints ();
        layout = new GridBagLayout ();
        addBasePanel ();
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
    }

    public void addBasePanel () {

        basePanel.setBorder (new EmptyBorder (5, 5, 5, 5));
        basePanel.setLayout (layout);
        add(basePanel);
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