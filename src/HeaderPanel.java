import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel;

    public HeaderPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        setBackground (new Color (40, 38, 37, 255));
        keyAndValuePanel = new KeyAndValuePanel ();
        add(keyAndValuePanel,BorderLayout.CENTER);
    }

    public void addNewKeyAndValue ()
    {
        keyAndValuePanel.addNewKeyAndValue ("header", "value");
    }

    public KeyAndValuePanel getKeyAndValuePanel () {
        return keyAndValuePanel;
    }
}
