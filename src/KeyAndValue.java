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
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    public KeyAndValue (String keyName, String valueName, boolean isEditable,
                        boolean showDescription)
    {
        this.isEditable = isEditable;
        this.showDescription = showDescription;
        constraints = new GridBagConstraints ();
        layout = new GridBagLayout ();
        setLayout (layout);
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


        constraints.insets = new Insets (2,5,2,5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = -15;
        constraints.ipadx = -30;
        addComponent (settings,0,1);
        constraints.ipady = 32;
        constraints.weightx = 0.5;
        constraints.ipadx = 0;
        addComponent (panelKey,1,10);
        addComponent (panelValue,11,10);
        addComponent (panelDesc,21,10);
        constraints.weightx = 0.0;
        constraints.ipady = 0;
        constraints.ipadx = 0;
        addComponent (active,31,1);
        constraints.ipady = -13;
        constraints.ipadx = -30;
        addComponent (delete,32,1);
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

        constraints.insets = new Insets (2,5,2,5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = -13;
        constraints.ipadx = -17;
        addComponent (settings,0,1);
        constraints.weightx = 0.5;
        constraints.ipady = 32;
        addComponent (panelKey,1,10);
        addComponent (panelValue,11,10);
        addComponent (panelDesc,21,10);
        constraints.ipadx = 40;
        constraints.weightx = 0.0;
        constraints.ipady = 0;
        addComponent (fake,31,1);

    }

    private void addComponent (JComponent component, int col, int width)
    {
        if (component == null)
            throw new InputMismatchException ("inValid input");
        constraints.gridx = col;
        constraints.gridy = 0;
        constraints.gridwidth = width;
        constraints.gridheight = 1;
        layout.setConstraints (component,constraints);
        add (component);
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

        panel.setBackground (color);
        GridBagConstraints constraints2 = new GridBagConstraints ();
        GridBagLayout layout2 = new GridBagLayout ();
        panel.setLayout (layout2);


        if (isEditable)
            textField.addMouseListener (new ComponentHandler ());


        textField.setBackground (color);
        textField.setFont (new Font ("Arial",Font.PLAIN,11));
        textField.setBorder (new LineBorder (color,1));
        textField.setForeground (Color.GRAY);
        JSeparator separator = new JSeparator (SwingConstants.HORIZONTAL);

        constraints2.gridx = 0;
        constraints2.gridy = 0;
        constraints2.gridwidth = 10;
        constraints2.gridheight = 1;
        constraints2.fill = GridBagConstraints.HORIZONTAL;
        constraints2.weightx = 0.5;
        layout2.setConstraints (textField,constraints2);
        panel.add (textField);

        constraints2.gridy = 1;
        layout2.setConstraints (separator,constraints2);
        panel.add (separator);
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
        return new Dimension (900,45);
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension (100,45);
    }

    @Override
    public Dimension getMinimumSize () {
        return new Dimension (100,45);
    }
}
