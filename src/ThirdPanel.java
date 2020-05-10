import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.InputMismatchException;

public class ThirdPanel extends JPanel
{
    private JLabel statusMessage;
    private JLabel time;
    private JLabel size;
    private RawPanel rawPanel;
    private VisualPreviewPanel visualPreviewPanel;

    public ThirdPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        addHeaderStatuses ();
        addBasePanel ();
    }

    private void addHeaderStatuses ()
    {
        JPanel headerStatus = new JPanel ();
        headerStatus.setLayout (new BoxLayout (headerStatus,BoxLayout.X_AXIS));
        headerStatus.add (Box.createHorizontalStrut (20));
        headerStatus.setBackground (Color.WHITE);
        headerStatus.setPreferredSize (new Dimension (200,55));

        statusMessage = new JLabel ("ERROR");
        statusMessage.setBackground (new Color (189, 24, 15));
        createLabel (statusMessage,60);


        time = new JLabel ("0 B");
        time.setBackground (Color.GRAY);
        createLabel (time,50);

        size = new JLabel ("0 ms");
        size.setBackground (Color.GRAY);
        createLabel (size,50);

        headerStatus.add (statusMessage);
        headerStatus.add (Box.createHorizontalStrut (15));
        headerStatus.add (time);
        headerStatus.add (Box.createHorizontalStrut (15));
        headerStatus.add (size);

        add(headerStatus,BorderLayout.NORTH);
    }

    private void createLabel (JLabel label, int width) {
        if (label == null)
            throw new InputMismatchException ("inValid input");
        label.setForeground (Color.WHITE);
        label.setOpaque (true);
        label.setPreferredSize (new Dimension (width,25));
        label.setMaximumSize (new Dimension (width,25));
        label.setMinimumSize (new Dimension (width,25));
        label.setHorizontalAlignment (SwingConstants.CENTER);
    }

    private void addBasePanel ()
    {
        rawPanel = new RawPanel ();
        visualPreviewPanel = new VisualPreviewPanel (null);
        JTabbedPane tabbedPane = new JTabbedPane ();
        tabbedPane.setTabLayoutPolicy (JTabbedPane.SCROLL_TAB_LAYOUT);

        String[] bodyTypes = {"Raw ","Visual Preview "};
        JComboBox<String> body = new JComboBox<> (bodyTypes);
        body.setBackground (Color.GRAY);
        body.addItemListener (new ItemListener () {
            @Override
            public void itemStateChanged (ItemEvent e) {
                if (body.getSelectedIndex () == 0)
                {
                    tabbedPane.setComponentAt (0,rawPanel);
                    tabbedPane.setSelectedIndex (0);
                }
                else if (body.getSelectedIndex () == 1)
                {
                    tabbedPane.setComponentAt (0,visualPreviewPanel);
                    tabbedPane.setSelectedIndex (0);
                }
                repaint ();
            }
        });
        add(tabbedPane);

        tabbedPane.add (rawPanel);
        tabbedPane.setTabComponentAt (0,body);

    }

}
