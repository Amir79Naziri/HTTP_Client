import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class JsonPanel extends JPanel
{
    private JTextArea textArea;
    private JButton beautifyJSON;

    public JsonPanel ()
    {
        super();
        setLayout (new BorderLayout (15,15));
        setBackground (new Color (40, 38, 37, 255));
        addTextAria ();
        addBeautifyJSON ();
    }

    private void addTextAria ()
    {
        textArea = new JTextArea ("...");
        textArea.setBackground (new Color (40, 38, 37, 255));
        textArea.setForeground (new Color (226, 156,0));
        textArea.setFont (new Font ("Arial",Font.PLAIN,12));
        textArea.setLineWrap (true);
        textArea.setWrapStyleWord (true);


        JScrollPane scrollPane = new JScrollPane
                (textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder (new LineBorder (Color.GRAY,1));
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        add(scrollPane,BorderLayout.CENTER);
    }

    private void addBeautifyJSON ()
    {
        Color color = new Color (40, 38, 37, 255);
        JPanel panel = new JPanel (new FlowLayout (FlowLayout.LEFT));
        panel.setBackground (color);
        panel.setPreferredSize (new Dimension (panel.getPreferredSize ().width,
                50));

        beautifyJSON = new JButton ("Beautify JSON");
        beautifyJSON.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                String text = textArea.getText ();
                StringBuilder stringBuilder = new StringBuilder ();
                for (char a : text.toCharArray ())
                {
                    if (a != '\n')
                        stringBuilder.append (a);
                }
                textArea.setText (stringBuilder.toString ());
            }
        });
        beautifyJSON.setForeground (Color.LIGHT_GRAY);
        beautifyJSON.setBackground (color);
        beautifyJSON.setToolTipText ("Auto-Format request body whitespaces");
        panel.add (beautifyJSON);
        add (panel,BorderLayout.SOUTH);
    }
}
