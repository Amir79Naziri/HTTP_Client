import javax.swing.*;
import java.awt.*;

/**
 * this class build for representing Header panel in second Panel
 *
 * @author Amir Naziri
 */
public class HeaderPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel; // keyAndValue panel

    /**
     * create a new Header Panel
     * @param theme theme
     */
    public HeaderPanel (Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        setLayout (new BorderLayout ());
        keyAndValuePanel = new KeyAndValuePanel ("header", "value",theme);
        add(ScrollPaneAdder.fetchToJScrollPane (keyAndValuePanel,theme),BorderLayout.CENTER);
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
