import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RawPanel extends JPanel
{
    private JTextArea textArea;
    private Theme theme;

    public RawPanel (Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("invalid input");
        this.theme = theme;
        setLayout (new BorderLayout ());
        addTextArea ();

    }

    private void addTextArea ()
    {
        textArea = new JTextArea ("Error: URL using bad/illegal format or missing URL");
        textArea.setLineWrap (true);
        textArea.setLineWrap (true);
        textArea.setFont (new Font("Arial",Font.PLAIN,12));
        textArea.setBackground (theme.getBackGroundColorV4 ());
        textArea.setForeground (theme.getForeGroundColorV2 ());
        textArea.setEditable (false);

        JScrollPane scrollPane = new JScrollPane (textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
                ,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder
                (new LineBorder (theme.getBackGroundColorV4 (),1));
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        add(scrollPane);
    }

    public JTextArea getTextArea () {
        return textArea;
    }
}
