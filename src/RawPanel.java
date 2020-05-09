import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RawPanel extends JPanel
{
    private JTextArea textArea;

    public RawPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        addTextArea ();

    }

    private void addTextArea ()
    {
        Color color = new Color (40, 38, 37, 255);
        textArea = new JTextArea ("Error: URL using bad/illegal format or missing URL");
        textArea.setLineWrap (true);
        textArea.setLineWrap (true);
        textArea.setFont (new Font("Arial",Font.PLAIN,12));
        textArea.setBackground (color);
        textArea.setForeground (Color.LIGHT_GRAY);
        textArea.setEditable (false);

        JScrollPane scrollPane = new JScrollPane (textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
                ,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder
                (new LineBorder (new Color (40, 37, 38, 255),1));
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        add(scrollPane);
    }

    public JTextArea getTextArea () {
        return textArea;
    }
}
