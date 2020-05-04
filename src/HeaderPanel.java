import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends KeyAndValuePanelType
{

    public HeaderPanel ()
    {
        super();
    }



    public void addNewKeyAndValue ()
    {
        KeyAndValue keyAndValue = new KeyAndValue ("header", "Value");
        getKeyAndValues ().add (keyAndValue);
        add (keyAndValue);
        JSeparator separator = new JSeparator ();
        separator.setPreferredSize (new Dimension (200,1));
    }
}
