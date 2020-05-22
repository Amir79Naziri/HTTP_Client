import javax.swing.*;
import java.awt.*;

/**
 * this class represents MultiPartPanel
 *
 * @author Amir Naziri
 */
public class MultiPartPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel; // keyAndValue panel

    /**
     * create a new MultiPartPanel
     * @param theme theme
     */
    public MultiPartPanel (Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        setLayout (new BorderLayout ());
        keyAndValuePanel = new KeyAndValuePanel ("name", "value",theme);
        add(ScrollPaneAdder.fetchToJScrollPane (keyAndValuePanel,theme),BorderLayout.CENTER);
    }

    /**
     * add new keyAndValue in MultiPartPanel
     */
    public void addNewKeyAndValue ()
    {
        keyAndValuePanel.addNewKeyAndValue ();
    }

    /**
     * @return MultiPartPanel
     */
    public KeyAndValuePanel getKeyAndValuePanel () {
        return keyAndValuePanel;
    }
}
