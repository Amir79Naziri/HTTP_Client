package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class build for representing JSON panel in second panel
 *
 * @author Amir Naziri
 */
public class JsonPanel extends JPanel
{
    private JTextArea textArea; // textArea in JSON panel
    private Theme theme;
    /**
     * creates a new JSON panel
     * @param theme theme
     */
    public JsonPanel (Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        setLayout (new BorderLayout (15,15));
        setBackground (theme.getBackGroundColorV4 ());
        addTextAria ();
        addBeautifyJSON ();
    }

    /**
     * adds text Area to panel
     */
    private void addTextAria ()
    {
        textArea = new JTextArea ("...");
        textArea.setBackground (theme.getBackGroundColorV4 ());
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

    /**
     * add beatify button
     */
    private void addBeautifyJSON ()
    {

        JPanel panel = new JPanel (new FlowLayout (FlowLayout.LEFT));
        panel.setBackground (theme.getBackGroundColorV4 ());
        panel.setPreferredSize (new Dimension (panel.getPreferredSize ().width,
                50));

        JButton beautifyJSON = new JButton ("Beautify JSON");
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
        beautifyJSON.setForeground (theme.getForeGroundColorV2 ());
        beautifyJSON.setBackground (theme.getBackGroundColorV4 ());
        beautifyJSON.setToolTipText ("Auto-Format request body whitespaces");
        panel.add (beautifyJSON);
        add (panel,BorderLayout.SOUTH);
    }
}
