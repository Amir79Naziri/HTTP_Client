import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;


public class KeyAndValuePanelType extends JPanel
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
}
