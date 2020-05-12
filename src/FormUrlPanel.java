import javax.swing.*;
import java.awt.*;

/**
 * this class represents FormURLPanel
 *
 * @author Amir Naziri
 */
public class FormUrlPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel; // keyAndValue panel

    /**
     * create a new FormURL panel
     */
    public FormUrlPanel (Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        setLayout (new BorderLayout ());
        keyAndValuePanel = new KeyAndValuePanel ("name", "value",theme);
        add(ScrollPaneAdder.fetchToJScrollPane (keyAndValuePanel,theme),BorderLayout.CENTER);
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
