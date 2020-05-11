import javax.swing.*;
import java.awt.*;

/**
 * this class build for representing Header panel in second Panel
 */
public class HeaderPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel; // keyAndValue panel

    /**
     * create a new Header Panel
     */
    public HeaderPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        setBackground (new Color (40, 38, 37, 255));
        keyAndValuePanel = new KeyAndValuePanel ("header", "value");
        add(ScrollPaneAdder.fetchToJScrollPane (keyAndValuePanel),BorderLayout.CENTER);
    }

    /**
     * add new Header keyAndValue
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
