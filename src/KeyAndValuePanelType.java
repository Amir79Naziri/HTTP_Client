import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;


public abstract class KeyAndValuePanelType extends JPanel
{
    private ArrayList<KeyAndValue> keyAndValues;

    public KeyAndValuePanelType ()
    {
        super();
        keyAndValues = new ArrayList<> ();
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        setBackground (new Color (40, 38, 37, 255));
    }

    public ArrayList<KeyAndValue> getKeyAndValues () {
        return keyAndValues;
    }

    abstract public void addNewKeyAndValue ();
}
