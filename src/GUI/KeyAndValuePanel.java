package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * this class represents a key and value panel
 *
 * @author Amir Naziri
 */
public class KeyAndValuePanel extends JPanel
{
    private ArrayList<KeyAndValue> keyAndValues; // list of key and values
    private KeyAndValue fixedKeyAndValue; // fixed key and values
    private JPopupMenu popupMenu; // pop up menu for setting
    private JMenuItem toggleDescription; // toggle description
    private JMenuItem deleteAll; // delete all
    private String key; // key
    private String value; // value
    private Theme theme; // theme

    /**
     * creates a new keyAndValue panel
     * @param key key
     * @param value value
     * @param theme theme
     */
    public KeyAndValuePanel (String key, String value, Theme theme)
    {
        super();
        if (key == null || value == null || theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        keyAndValues = new ArrayList<> ();
        this.key = key;
        this.value = value;
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        setBackground (theme.getBackGroundColorV4 ());
        fixedKeyAndValue = new KeyAndValue ("New " + key, "New " + value,
                false,false,theme);
        add(fixedKeyAndValue);
        ComponentHandler componentHandler = new ComponentHandler ();
        fixedKeyAndValue.getKey ().addMouseListener (componentHandler);
        fixedKeyAndValue.getValue ().addMouseListener (componentHandler);
        fixedKeyAndValue.getSettings ().addMouseListener (componentHandler);
        fixedKeyAndValue.getDescribe ().addMouseListener (componentHandler);

        deleteAll = new JMenuItem ("Delete All Items");
        deleteAll.addActionListener (componentHandler);
        toggleDescription = new JMenuItem ("Toggle Description");
        toggleDescription.addActionListener (componentHandler);
        popupMenu = new JPopupMenu ();
        popupMenu.add (deleteAll);
        popupMenu.add (toggleDescription);
    }

    /**
     * @return keyAndValue list
     */
    public ArrayList<KeyAndValue> getKeyAndValues () {
        return keyAndValues;
    }

    /**
     * add a new key and value
     */
    public void addNewKeyAndValue ()
    {
        KeyAndValue keyAndValue = new KeyAndValue (key, value,true,
                fixedKeyAndValue.isPanelDescVisible (),theme);
        getKeyAndValues ().add (keyAndValue);
        add (keyAndValue,keyAndValues.size () - 1);
        JSeparator separator = new JSeparator ();
        separator.setPreferredSize (new Dimension (200,1));
        keyAndValue.setVisible (false);
        keyAndValue.setVisible (true);
    }

    /**
     * toggle description for keyAndValues
     */
    public void toggleAllDescription ()
    {
        for (KeyAndValue keyAndValue : keyAndValues)
            keyAndValue.toggleDescription ();
        fixedKeyAndValue.toggleDescription ();
    }

    /**
     * delete all keyAndValues
     */
    public void deleteAll ()
    {
        for (KeyAndValue keyAndValue : keyAndValues)
        {
            keyAndValue.setVisible (false);
            keyAndValue.delete ();
        }
        keyAndValues.clear ();
    }


    /**
     * this class handles components in this panel
     */
    private class ComponentHandler extends MouseAdapter
            implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource () == deleteAll)
                deleteAll ();
            else if (e.getSource () == toggleDescription)
                toggleAllDescription ();
        }

        @Override
        public void mousePressed (MouseEvent e) {
            if (e.getComponent () == fixedKeyAndValue.getSettings())
                popupMenu.show (e.getComponent (),e.getX (),e.getY ());
            else if (e.getComponent () == fixedKeyAndValue.getValue () ||
            e.getComponent () == fixedKeyAndValue.getKey () || e.getComponent () ==
            fixedKeyAndValue.getDescribe ())
            {
                addNewKeyAndValue ();
            }
        }
    }
}
