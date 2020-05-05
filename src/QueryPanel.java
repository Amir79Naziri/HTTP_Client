import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class QueryPanel extends JPanel
{
    private JTextField previewURLText;
    private KeyAndValuePanel keyAndValuePanel;

    public QueryPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        setBackground (new Color (40, 38, 37, 255));
        addURLPreview ();
        keyAndValuePanel = new KeyAndValuePanel ();
        add (keyAndValuePanel,BorderLayout.CENTER);
    }

    private void addURLPreview ()
    {
        Color color = new Color (40, 38, 37, 255);
        JPanel panel = new JPanel (new FlowLayout (FlowLayout.LEFT));
        panel.setBackground (color);
        JLabel label = new JLabel ("URL PREVIEW");
        label.setFont (new Font ("Arial",Font.PLAIN,9));
        label.setForeground (Color.LIGHT_GRAY);
        label.setBackground (color);
        previewURLText = new JTextField ();
        previewURLText.setEditable (false);
        previewURLText.setPreferredSize (new Dimension (400,30));

        previewURLText.setForeground (Color.LIGHT_GRAY);
        previewURLText.setBackground (new Color (50, 48, 47, 255));
        previewURLText.setBorder (BorderFactory.createCompoundBorder (
                new LineBorder (Color.GRAY.darker (),1,true),
                new EmptyBorder (1,5,1,5)));

        panel.add (label);
        panel.add(previewURLText);
        add(panel,BorderLayout.NORTH);
    }


    public JTextField getPreviewURLText () {
        return previewURLText;
    }

    public void addNewKeyAndValue ()
    {
        keyAndValuePanel.addNewKeyAndValue ("name", "value");
    }

    public KeyAndValuePanel getKeyAndValuePanel () {
        return keyAndValuePanel;
    }
}
