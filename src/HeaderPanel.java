import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;


public class HeaderPanel extends JPanel
{
    private ArrayList<KeyAndValue> headers;

    public HeaderPanel ()
    {
        super();
        headers = new ArrayList<> ();
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        setBackground (new Color (31, 38, 38, 255));
        addNewHeader ();
        addNewHeader ();
    }

    public void addNewHeader ()
    {
        KeyAndValue keyAndValue = new KeyAndValue ("New header", "New Value");
        headers.add (keyAndValue);
        add (keyAndValue);
        JSeparator separator = new JSeparator ();
        separator.setPreferredSize (new Dimension (200,1));
//        add(new JSeparator ());
    }



}
