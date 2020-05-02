import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.InputMismatchException;

public class KeyAndValue extends JPanel
{

    private JTextField key;
    private JTextField value;
    private JCheckBox active;
    private JButton delete;
    private JButton settings;

    public KeyAndValue (String keyName, String valueName)
    {
        setLayout (new FlowLayout (FlowLayout.LEFT,9,9));
        setBackground (new Color (40, 38, 37, 255));
        createComponents (keyName, valueName);
    }

    private void createComponents (String keyName, String valueName)
    {
        Color color = new Color (40, 38, 37, 255);
        settings = new JButton (new ImageIcon ("./images/data.png"));
        settings.setPreferredSize (new Dimension (11,13));
        settings.setBackground (Color.WHITE);
        settings.setFocusable (false);
        settings.setFocusPainted (false);

        JPanel panelKey = new JPanel ();
        key = new JTextField (keyName);
        createTextPanel (color, panelKey, key);

        JPanel panelValue = new JPanel ();
        value = new JTextField (valueName);
        createTextPanel (color, panelValue, value);

        active = new JCheckBox ("");
        active.setPreferredSize (new Dimension (30,30));
        active.setSelected (true);


        delete = new JButton (new ImageIcon ("./images/trashR1.png"));
        delete.setRolloverIcon (new ImageIcon ("./images/trashR2.png"));
        delete.setRolloverEnabled (true);
        delete.setPreferredSize (new Dimension (16,18));
        delete.setBackground (color);
        delete.setFocusPainted (false);

        add(settings);
        add(panelKey);
        add(panelValue);
        add (active);
        add(delete);
    }

    private void createTextPanel (Color color, JPanel panel, JTextField textField) {

        if (panel == null || textField == null)
            throw new InputMismatchException ("input is not valid");
        panel.setPreferredSize (new Dimension (155,32));
        panel.setBackground (color);
        panel.setLayout (new BoxLayout (panel,BoxLayout.Y_AXIS));

        textField.setPreferredSize (new Dimension (155,30));
        textField.setBackground (color);
        textField.setBorder (new LineBorder (color,1));
        textField.setForeground (Color.WHITE);
        panel.add (textField);
        panel.add (new JSeparator (SwingConstants.HORIZONTAL));
    }

    @Override
    public Dimension getMaximumSize () {
        return new Dimension (450,45);
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension (450,45);
    }

    @Override
    public Dimension getMinimumSize () {
        return new Dimension (450,45);
    }
}
