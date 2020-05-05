import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.InputMismatchException;

public class KeyAndValue extends JPanel
{

    private JTextField key;
    private JTextField value;
    private JTextField describe;
    private JPanel panelDesc;
    private JCheckBox active;
    private JButton delete;
    private JButton settings;
    private boolean isEditable;
    private boolean showDescription;
    public KeyAndValue (String keyName, String valueName, boolean isEditable,
                        boolean showDescription)
    {
        this.isEditable = isEditable;
        this.showDescription = showDescription;
        setLayout (new FlowLayout (FlowLayout.CENTER,9,9));
        setBackground (new Color (40, 38, 37, 255));


        if (isEditable)
            createComponents (keyName, valueName);
        else
            createComponentsV2 (keyName, valueName);

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

        panelDesc = new JPanel ();
        describe = new JTextField ("description");
        createTextPanel (color,panelDesc,describe);
        panelDesc.setVisible (showDescription);


        active = new JCheckBox ("");
        active.setPreferredSize (new Dimension (30,30));
        active.setSelected (true);
        active.addItemListener (new ComponentHandler ());


        delete = new JButton (new ImageIcon ("./images/trashR1.png"));
        delete.setRolloverIcon (new ImageIcon ("./images/trashR2.png"));
        delete.setRolloverEnabled (true);
        delete.setPreferredSize (new Dimension (16,16));
        delete.setBackground (color);
        delete.setFocusPainted (false);
        delete.addActionListener (new ComponentHandler ());


        add(settings);
        add(panelKey);
        add(panelValue);
        add(panelDesc);
        add (active);
        add(delete);
    }

    private void createComponentsV2 (String keyName, String valueName)
    {
        Color color = new Color (40, 38, 37, 255);

        settings = new JButton ("a");
        settings.setPreferredSize (new Dimension (11,13));
        settings.setBackground (Color.WHITE);
        settings.setFocusable (true);
        settings.setFocusPainted (true);

        JPanel panelKey = new JPanel ();
        key = new JTextField (keyName);
        key.setEditable (false);
        createTextPanel (color, panelKey, key);

        JPanel panelValue = new JPanel ();
        value = new JTextField (valueName);
        value.setEditable (false);
        createTextPanel (color, panelValue, value);

        panelDesc = new JPanel ();
        describe = new JTextField ("description");
        createTextPanel (color,panelDesc,describe);
        panelDesc.setVisible (showDescription);

        JLabel fake = new JLabel ("");
        fake.setBackground (color);
        fake.setFocusable (false);
        fake.setPreferredSize (new Dimension (55,30));
        add(settings);
        add(panelKey);
        add(panelValue);
        add (panelDesc);
        add(fake);
    }

    public JButton getSettings () {
        return settings;
    }

    public JTextField getKey () {
        return key;
    }

    public JTextField getValue () {
        return value;
    }

    public JPanel getPanelDesc () {
        return panelDesc;
    }

    public JTextField getDescribe () {
        return describe;
    }

    private void createTextPanel (Color color, JPanel panel, JTextField textField) {

        if (panel == null || textField == null)
            throw new InputMismatchException ("input is not valid");
        panel.setPreferredSize (new Dimension (155,32));
        panel.setBackground (color);
        panel.setLayout (new BoxLayout (panel,BoxLayout.Y_AXIS));

        textField.setPreferredSize (new Dimension (155,30));
        if (isEditable)
            textField.addMouseListener (new ComponentHandler ());


        textField.setBackground (color);
        textField.setFont (new Font ("Arial",Font.PLAIN,11));
        textField.setBorder (new LineBorder (color,1));
        textField.setForeground (Color.GRAY);
        panel.add (textField);
        panel.add (new JSeparator (SwingConstants.HORIZONTAL));
    }


    public void toggleDescription ()
    {
        if (panelDesc.isVisible ())
            panelDesc.setVisible (false);
        else
            panelDesc.setVisible (true);
    }


    private class ComponentHandler extends MouseAdapter
            implements ActionListener, ItemListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource () == delete)
            {
                setVisible (false);
            }
        }

        @Override
        public void itemStateChanged (ItemEvent e) {

            if (!active.isSelected ())
            {
                key.setForeground (Color.DARK_GRAY);
                value.setForeground (Color.DARK_GRAY);
                describe.setForeground (Color.DARK_GRAY);

            } else
            {
                key.setForeground (Color.GRAY);
                value.setForeground (Color.GRAY);
                describe.setForeground (Color.GRAY);
            }
            repaint ();
        }

        @Override
        public void mouseEntered (MouseEvent e) {

            if (e.getComponent () instanceof JTextField && active.isSelected () )
            {
                JTextField textField = (JTextField)(e.getComponent ());
                textField.setFont (new Font ("Arial",Font.BOLD + Font.ITALIC,11));
            }
        }

        @Override
        public void mouseExited (MouseEvent e) {
            if (e.getComponent () instanceof JTextField && active.isSelected () )
            {
                JTextField textField = (JTextField)(e.getComponent ());
                textField.setFont (new Font ("Arial",Font.PLAIN,11));
            }
        }
    }



    @Override
    public Dimension getMaximumSize () {
        return new Dimension (600,45);
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension (500,45);
    }

    @Override
    public Dimension getMinimumSize () {
        return new Dimension (450,45);
    }
}
