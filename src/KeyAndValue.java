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
    private boolean showDescription;
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    private boolean isDeleted;
    private Theme theme;

    public KeyAndValue (String keyName, String valueName, boolean isEditable,
                        boolean showDescription, Theme theme)
    {
        super();
        if (keyName == null || valueName == null || theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        isDeleted = false;
        this.showDescription = showDescription;
        constraints = new GridBagConstraints ();
        layout = new GridBagLayout ();
        setLayout (layout);
        setBackground (theme.getBackGroundColorV4 ());


        if (isEditable)
            createComponents (keyName, valueName);
        else
            createComponentsV2 (keyName, valueName);

    }

    public boolean isDeleted () {
        return isDeleted;
    }

    public void delete () {
        isDeleted = true;
    }

    private void createComponents (String keyName, String valueName)
    {
        if (keyName == null || valueName == null)
            throw new NullPointerException ("inValid input");
        settings = new JButton (new ImageIcon ("./images/data.png"));
        settings.setBackground (theme.getBackGroundColorV4 ());
        settings.setFocusable (false);
        settings.setFocusPainted (false);
        ComponentHandler componentHandler = new ComponentHandler ();
        JPanel panelKey = new JPanel ();
        key = new JTextField (keyName);
        createTextPanel (panelKey, key, theme);

        key.addMouseListener (componentHandler);


        JPanel panelValue = new JPanel ();
        value = new JTextField (valueName);

        createTextPanel (panelValue, value,theme);

        value.addMouseListener (componentHandler);

        panelDesc = new JPanel ();
        panelDesc.setBackground (theme.getBackGroundColorV4 ());
        describe = new JTextField ("description");

        createTextPanel (panelDesc,describe,theme);
        panelDesc.setVisible (showDescription);

        describe.addMouseListener (componentHandler);


        active = new JCheckBox ("");
        active.setSelected (true);
        active.addItemListener (componentHandler);


        delete = new JButton (new ImageIcon ("./images/trashR1.png"));
        delete.setRolloverIcon (new ImageIcon ("./images/trashR2.png"));
        delete.setRolloverEnabled (true);
        delete.setPreferredSize (new Dimension (16,16));
        delete.setBackground (theme.getBackGroundColorV4 ());
        delete.setFocusPainted (false);
        delete.addActionListener (new ComponentHandler ());
        delete.setBackground (theme.getBackGroundColorV4 ());


        constraints.insets = new Insets (2,5,2,5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = -15;
        constraints.ipadx = -30;
        GridBagAdder.addComponent (settings,0,0,1,layout,constraints,this);
        constraints.ipady = 32;
        constraints.weightx = 0.5;
        constraints.ipadx = 0;
        GridBagAdder.addComponent (panelKey,0,1,10,layout,constraints,this);
        GridBagAdder.addComponent (panelValue,0,11,10,layout,constraints,this);
        GridBagAdder.addComponent (panelDesc,0,21,10,layout,constraints,this);
        constraints.weightx = 0.0;
        constraints.ipady = 0;
        constraints.ipadx = 0;
        GridBagAdder.addComponent (active,0,31,1,layout,constraints,this);
        constraints.ipady = -13;
        constraints.ipadx = -30;
        GridBagAdder.addComponent (delete,0,32,1,layout,constraints,this);
    }

    private void createComponentsV2 (String keyName, String valueName)
    {
        if (keyName == null || valueName == null)
            throw new NullPointerException ("inValid input");


        settings = new JButton ("a");
        settings.setBackground (theme.getBackGroundColorV4 ());
        settings.setFocusable (true);
        settings.setFocusPainted (true);

        JPanel panelKey = new JPanel ();
        key = new JTextField (keyName);
        key.setEditable (false);

        key.setCursor (Cursor.getPredefinedCursor (Cursor.TEXT_CURSOR));
        createTextPanel (panelKey, key,theme);

        JPanel panelValue = new JPanel ();
        value = new JTextField (valueName);

        value.setEditable (false);
        value.setCursor (Cursor.getPredefinedCursor (Cursor.TEXT_CURSOR));
        createTextPanel (panelValue, value,theme);

        panelDesc = new JPanel ();
        describe = new JTextField ("description");
        describe.setEditable (false);

        describe.setCursor (Cursor.getPredefinedCursor (Cursor.TEXT_CURSOR));
        createTextPanel (panelDesc,describe,theme);
        panelDesc.setVisible (showDescription);

        JLabel fake = new JLabel ("");
        fake.setFocusable (false);


        constraints.insets = new Insets (2,5,2,5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = -13;
        constraints.ipadx = -17;
        GridBagAdder.addComponent (settings,0,0,1,layout,constraints,this);
        constraints.weightx = 0.5;
        constraints.ipady = 32;
        GridBagAdder.addComponent (panelKey,0,1,10,layout,constraints,this);
        GridBagAdder.addComponent (panelValue,0,11,10,layout,constraints,this);
        GridBagAdder.addComponent (panelDesc,0,21,10,layout,constraints,this);
        constraints.ipadx = 40;
        constraints.weightx = 0.0;
        constraints.ipady = 0;
        GridBagAdder.addComponent (fake,0,31,1,layout,constraints,this);
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

    public static void createTextPanel (JPanel panel, JTextField textField,
                                        Theme theme) {

        if (panel == null || textField == null)
            throw new InputMismatchException ("input is not valid");

        panel.setBackground (theme.getBackGroundColorV4 ());
        GridBagConstraints constraints2 = new GridBagConstraints ();
        GridBagLayout layout2 = new GridBagLayout ();
        panel.setLayout (layout2);

        textField.setBackground (theme.getBackGroundColorV4 ());
        textField.setFont (new Font ("Arial",Font.PLAIN,11));
        textField.setBorder (new LineBorder (theme.getBackGroundColorV4 (),1));
        textField.setForeground (theme.getForeGroundColorV2 ());
        JSeparator separator = new JSeparator (SwingConstants.HORIZONTAL);


        constraints2.fill = GridBagConstraints.HORIZONTAL;
        constraints2.weightx = 0.5;
        constraints2.insets = new Insets (4,0,4,0);
        GridBagAdder.addComponent (textField,0,0,10,layout2,constraints2,panel);

        GridBagAdder.addComponent (separator,1,0,10,layout2,constraints2,panel);
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
                delete ();
            }
        }

        @Override
        public void itemStateChanged (ItemEvent e) {

            if (!active.isSelected ())
            {
                key.setForeground (theme.getForeGroundColorV3 ());
                value.setForeground (theme.getForeGroundColorV3 ());
                describe.setForeground (theme.getForeGroundColorV3 ());

            } else
            {
                key.setForeground (theme.getForeGroundColorV2 ());
                value.setForeground (theme.getForeGroundColorV2 ());
                describe.setForeground (theme.getForeGroundColorV2 ());
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
