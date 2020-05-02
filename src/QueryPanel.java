import javax.swing.*;
import java.awt.*;

public class QueryPanel extends KeyAndValuePanelType
{
    public QueryPanel ()
    {
        super();
    }

    public void addNewKeyAndValue ()
    {
        KeyAndValue keyAndValue = new KeyAndValue ("name", "Value");
        getKeyAndValues ().add (keyAndValue);
        add (keyAndValue);
        JSeparator separator = new JSeparator ();
        separator.setPreferredSize (new Dimension (200,1));
    }
}
