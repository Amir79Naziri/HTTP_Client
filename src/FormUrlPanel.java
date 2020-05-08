import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.InputMismatchException;

public class FormUrlPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel;

    public FormUrlPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        setBackground (new Color (40, 38, 37, 255));
        keyAndValuePanel = new KeyAndValuePanel ("name", "value");
        add(fetchToJScrollPane (keyAndValuePanel),BorderLayout.CENTER);
    }

    public void addNewKeyAndValue ()
    {
        keyAndValuePanel.addNewKeyAndValue ();
    }

    public KeyAndValuePanel getKeyAndValuePanel () {
        return keyAndValuePanel;
    }


    private JScrollPane fetchToJScrollPane (JPanel panel)
    {
        if (panel == null)
            throw new InputMismatchException ("Input is not valid");
        JScrollPane scrollPane = new JScrollPane
                (panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder (new LineBorder (new Color (40, 38, 37, 208),1));
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        return scrollPane;
    }
}
