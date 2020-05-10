import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.InputMismatchException;


public class HeaderPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel;

    public HeaderPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        setBackground (new Color (40, 38, 37, 255));
        keyAndValuePanel = new KeyAndValuePanel ("header", "value");
        add(QueryPanel.fetchToJScrollPane (keyAndValuePanel),BorderLayout.CENTER);
    }

    public void addNewKeyAndValue ()
    {
        keyAndValuePanel.addNewKeyAndValue ();
    }

    public KeyAndValuePanel getKeyAndValuePanel () {
        return keyAndValuePanel;
    }

}
