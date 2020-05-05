import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class KeyAndValuePanel extends JPanel
{
    private ArrayList<KeyAndValue> keyAndValues;
    private KeyAndValue fixedKeyAndValue;
    private JPopupMenu popupMenu;
    private JMenuItem toggleDescription;
    private JMenuItem deleteAll;
    private String key;
    private String value;

    public KeyAndValuePanel (String key, String value)
    {
        super();
        keyAndValues = new ArrayList<> ();
        this.key = key;
        this.value = value;
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        setBackground (new Color (40, 38, 37, 255));
        fixedKeyAndValue = new KeyAndValue ("New " + key, "New " + value,
                false,false);
        add(fixedKeyAndValue);
        ComponentHandler componentHandler = new ComponentHandler ();
        fixedKeyAndValue.getKey ().addMouseListener (componentHandler);
        fixedKeyAndValue.getValue ().addMouseListener (componentHandler);
        fixedKeyAndValue.getSettings ().addMouseListener (componentHandler);

        deleteAll = new JMenuItem ("Delete All Items");
        deleteAll.addActionListener (componentHandler);
        toggleDescription = new JMenuItem ("Toggle Description");
        toggleDescription.addActionListener (componentHandler);
        popupMenu = new JPopupMenu ();
        popupMenu.add (deleteAll);
        popupMenu.add (toggleDescription);
    }

    public ArrayList<KeyAndValue> getKeyAndValues () {
        return keyAndValues;
    }

    public void addNewKeyAndValue ()
    {
        KeyAndValue keyAndValue = new KeyAndValue (key, value,true,
                fixedKeyAndValue.getPanelDesc ().isVisible ());
        getKeyAndValues ().add (keyAndValue);
        add (keyAndValue,keyAndValues.size () - 1);
        JSeparator separator = new JSeparator ();
        separator.setPreferredSize (new Dimension (200,1));
    }

    public void toggleAllDescription ()
    {
        for (KeyAndValue keyAndValue : keyAndValues)
            keyAndValue.toggleDescription ();
        fixedKeyAndValue.toggleDescription ();
    }

    public void deleteAll ()
    {
        for (KeyAndValue keyAndValue : keyAndValues)
        {
            keyAndValue.setVisible (false);
//            keyAndValues.remove (keyAndValue);
        }
    }

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
        public void mouseClicked (MouseEvent e) {
            if (e.getComponent () == fixedKeyAndValue.getSettings())
                popupMenu.show (e.getComponent (),e.getX (),e.getY ());
            else if (e.getComponent () == fixedKeyAndValue.getValue () ||
            e.getComponent () == fixedKeyAndValue.getKey () || e.getComponent () ==
            fixedKeyAndValue.getDescribe ())
            {
                addNewKeyAndValue ();
                repaint ();
            }


        }
    }
}
