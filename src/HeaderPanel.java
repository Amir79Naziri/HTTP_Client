import javax.swing.*;
import java.awt.*;

/**
 * this class build for representing Header panel in second Panel
 */
public class HeaderPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel; // keyAndValue panel
    private Theme theme;
    /**
     * create a new Header Panel
     */
    public HeaderPanel (Theme theme)
    {
        super();
        this.theme = theme;
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
