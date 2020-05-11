import javax.swing.*;
import java.awt.*;

/**
 * this class represents FormURLPanel
 */
public class FormUrlPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel; // keyAndValue panel

    /**
     * create a new FormURL panel
     */
    public FormUrlPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        setBackground (new Color (40, 38, 37, 255));
        keyAndValuePanel = new KeyAndValuePanel ("name", "value");
        add(ScrollPaneAdder.fetchToJScrollPane (keyAndValuePanel),BorderLayout.CENTER);
    }

    /**
     * add new keyAndValue in formURL panel
     */
    public void addNewKeyAndValue ()
    {
        keyAndValuePanel.addNewKeyAndValue ();
    }

    /**
     * @return keyAndValue panel
     */
    public KeyAndValuePanel getKeyAndValuePanel () {
        return keyAndValuePanel;
    }
}
