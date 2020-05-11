import javax.swing.*;
import java.awt.*;

/**
 * this class represents FormURLPanel
 */
public class FormUrlPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel; // keyAndValue panel
    private Theme theme;
    /**
     * create a new FormURL panel
     */
    public FormUrlPanel (Theme theme)
    {
        super();
        this.theme = theme;
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
