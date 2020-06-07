package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
/**
 * this class represents a key and value type
 *
 * @author Amir Naziri
 */
public class KeyAndValue extends JPanel
{

    private JTextField key; // key
    private JTextField value; // value
    private JTextField describe; // describe
    private JPanel panelDesc; // describe panel
    private JCheckBox active; // active button
    private JButton delete; // delete button
    private JButton settings; // setting button
    private boolean showDescription; // show description for new keyAndValue
    private GridBagLayout layout;  // layout
    private GridBagConstraints constraints; // constrains
    private boolean isDeleted; // is Deleted
    private Theme theme; // theme
    private KeyAndValuePanel owner;


    /**
     * creates a new Key and Value
     * @param keyName keyName
     * @param valueName valueName
     * @param isEditable isEditable
     * @param showDescription  should show description
     * @param theme  theme
     */
    public KeyAndValue (String keyName, String valueName, boolean isEditable,
                        boolean showDescription, Theme theme, KeyAndValuePanel owner)
    {
        super();
        if (keyName == null || valueName == null || theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        isDeleted = false;
        this.owner = owner;
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

    /**
     * is deleted
     * @return is Deleted
     */
    public boolean isDeleted () {
        return isDeleted;
    }


    /**
     * sets IsDeleted true
     */
    public void delete () {
        isDeleted = true;
    }

    /**
     * create components of base Panel if is editable
     * @param keyName keyName
     * @param valueName value Name
     */
    private void createComponents (String keyName, String valueName)
    {
        if (keyName == null || valueName == null)
            throw new NullPointerException ("inValid input");
        settings = new JButton (theme.getData ());
        settings.setBackground (theme.getBackGroundColorV4 ());
        settings.setFocusable (false);
        settings.setFocusPainted (false);
        ComponentHandler componentHandler = new ComponentHandler ();
        KeyHandler keyHandler = new KeyHandler ();
        JPanel panelKey = new JPanel ();
        key = new JTextField (keyName);
        createTextPanel (panelKey, key, theme);

        key.addMouseListener (componentHandler);
        key.addKeyListener (keyHandler);


        JPanel panelValue = new JPanel ();
        value = new JTextField (valueName);

        createTextPanel (panelValue, value,theme);

        value.addMouseListener (componentHandler);
        value.addKeyListener (keyHandler);

        panelDesc = new JPanel ();
        panelDesc.setBackground (theme.getBackGroundColorV4 ());
        describe = new JTextField ("description");

        createTextPanel (panelDesc,describe,theme);
        panelDesc.setVisible (showDescription);

        describe.addMouseListener (componentHandler);


        active = new JCheckBox ("");
        active.setSelected (true);
        active.addItemListener (componentHandler);


        delete = new JButton (theme.getTrashR1 ());
        delete.setRolloverIcon (theme.getTrashR2 ());
        delete.setRolloverEnabled (true);
        delete.setPreferredSize (new Dimension (16,16));
        delete.setBackground (theme.getBackGroundColorV4 ());
        delete.setFocusPainted (false);
        delete.addActionListener (componentHandler);
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
        constraints.ipadx = -31;
        GridBagAdder.addComponent (delete,0,32,1,layout,constraints,this);
    }

    /**
     * create components of base Panel if is not editable
     * @param keyName keyName
     * @param valueName value Name
     */
    private void createComponentsV2 (String keyName, String valueName)
    {
        if (keyName == null || valueName == null)
            throw new NullPointerException ("inValid input");


        settings = new JButton (theme.getGear ());
        settings.setFocusable (false);


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
        constraints.ipady = -16;
        constraints.ipadx = -30;
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

    /**
     * @return setting button
     */
    public JButton getSettings () {
        return settings;
    }

    /**
     * @return key textField
     */
    public JTextField getKey () {
        return key;
    }

    /**
     * @return value textField
     */
    public JTextField getValue () {
        return value;
    }

    /**
     * @return isPanelDescVisible
     */
    public boolean isPanelDescVisible ()
    {
        return panelDesc.isVisible ();
    }

    /**
     * @return describeTextField
     */
    public JTextField getDescribe () {
        return describe;
    }

    /**
     * creates a text panel with a underLine
     * @param panel panel
     * @param textField textField
     * @param theme theme
     */
    public static void createTextPanel (JPanel panel, JTextField textField,
                                        Theme theme) {

        if (panel == null || textField == null || theme == null)
            throw new NullPointerException ("input is not valid");

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

    /**
     * toggle description
     */
    public void toggleDescription ()
    {
        if (panelDesc.isVisible ())
            panelDesc.setVisible (false);
        else
            panelDesc.setVisible (true);
    }

    public boolean isActive () {
        return active.isSelected ();
    }

    /**
     * class for handling all components
     */
    private class ComponentHandler extends MouseAdapter
            implements ActionListener, ItemListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource () == delete)
            {
                setVisible (false);
                delete ();
                owner.properData ();
                if (owner.getOwner () instanceof QueryPanel)
                {
                    QueryPanel queryPanel = (QueryPanel)owner.getOwner ();
                    queryPanel.getPreviewURLText ().
                            setText (owner.getRequest ().getClientRequest ().getUrl ()
                            + owner.getRequest ().getClientRequest ().getQueryDataString ());
                }
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
            owner.properData ();
            if (owner.getOwner () instanceof QueryPanel)
            {
                QueryPanel queryPanel = (QueryPanel)owner.getOwner ();
                queryPanel.getPreviewURLText ().
                        setText (owner.getRequest ().getClientRequest ().getUrl ()
                                + owner.getRequest ().getClientRequest ().getQueryDataString ());
            }
            repaint ();
        }

        @Override
        public void mouseEntered (MouseEvent e) {

            if (e.getComponent () instanceof JTextField && active.isSelected () )
            {
                JTextField textField = (JTextField)(e.getComponent ());
                textField.setFont (new Font ("Arial",Font.BOLD,11));
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

    /**
     * manage the key released
     */
    private class KeyHandler extends KeyAdapter
    {
        @Override
        public void keyReleased (KeyEvent e) {
            owner.properData ();
            if (owner.getOwner () instanceof QueryPanel)
            {
                QueryPanel queryPanel = (QueryPanel)owner.getOwner ();
                queryPanel.getPreviewURLText ().
                        setText (owner.getRequest ().getClientRequest ().getUrl ()
                                + owner.getRequest ().getClientRequest ().getQueryDataString ());
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
