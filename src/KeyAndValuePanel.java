import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;


public class KeyAndValuePanel extends JPanel
{
    private ArrayList<KeyAndValue> keyAndValues;

    public KeyAndValuePanel ()
    {
        super();
        keyAndValues = new ArrayList<> ();
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        setBackground (new Color (40, 38, 37, 255));
    }

    public ArrayList<KeyAndValue> getKeyAndValues () {
        return keyAndValues;
    }

    public void addNewKeyAndValue (String key, String value)
    {
        KeyAndValue keyAndValue = new KeyAndValue (key, value,true);
        getKeyAndValues ().add (keyAndValue);
        add (keyAndValue);
        JSeparator separator = new JSeparator ();
        separator.setPreferredSize (new Dimension (200,1));
    }
}
