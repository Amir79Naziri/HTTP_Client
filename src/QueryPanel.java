import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class QueryPanel extends KeyAndValuePanelType
{
    private JTextField pre;

    public QueryPanel ()
    {
        super();
        addURLPreview ();
    }

    private void addURLPreview ()
    {
        JPanel panel = new JPanel (new FlowLayout (FlowLayout.LEFT));
        panel.setBackground (new Color (40, 38, 37, 255));
        JLabel label = new JLabel ("URL PREVIEW");
        label.setFont (new Font ("Arial",Font.PLAIN,9));
        label.setForeground (Color.LIGHT_GRAY);
        label.setBackground (new Color (40, 38, 37, 255));
        pre = new JTextField ();
        pre.setEditable (false);
        pre.setPreferredSize (new Dimension (400,30));

        pre.setForeground (Color.LIGHT_GRAY);
        pre.setBackground (new Color (50, 48, 47, 255));
        pre.setBorder (BorderFactory.createCompoundBorder (
                new LineBorder (Color.GRAY.darker (),1,true),
                new EmptyBorder (1,5,1,5)));

        panel.add (label);
        panel.add(pre);
        add(panel);
    }


    public void addNewKeyAndValue ()
    {
        KeyAndValue keyAndValue = new KeyAndValue ("name", "Value");
        getKeyAndValues ().add (keyAndValue);
        add (keyAndValue);
        JSeparator separator = new JSeparator ();
        separator.setPreferredSize (new Dimension (200,1));
    }

    public JTextField getPre () {
        return pre;
    }
}
