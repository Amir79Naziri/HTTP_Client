package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


/**
 * this class represents third panel in program
 *
 * @author Amir Naziri
 */
public class ThirdPanel extends JPanel
{
    private JLabel statusMessage; // status message
    private JLabel time; // time
    private JLabel size; // size
    // third panel base panel's
    private RawPanel rawPanel;
    private VisualPreviewPanel visualPreviewPanel;
    private ResultHeaderPanel resultHeaderPanel;
    private Theme theme; // theme
    private Request request;
    private ResponseCalculator responseCalculator;

    /**
     * creates new Third panel
     * @param theme theme
     */
    public ThirdPanel (Theme theme, Request request)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        this.request = request;
        setLayout (new BorderLayout ());
        addHeaderStatuses ();
        addBasePanel ();
    }

    /**
     * add header for statuses
     */
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

    /**
     * create label for status
     * @param label label
     * @param width width of label
     */
    private void createLabel (JLabel label, int width) {
        if (label == null)
            throw new NullPointerException ("inValid input");
        label.setForeground (Color.WHITE);
        label.setOpaque (true);

        label.setMaximumSize (new Dimension (width + 50,25));
        label.setMinimumSize (new Dimension (width,25));
        label.setHorizontalAlignment (SwingConstants.CENTER);
    }

    /**
     * add base panel
     */
    private void addBasePanel ()
    {
        rawPanel = new RawPanel (theme);
        visualPreviewPanel = new VisualPreviewPanel (theme);
        resultHeaderPanel = new ResultHeaderPanel (theme);
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
                }
                else if (body.getSelectedIndex () == 1)
                {
                    tabbedPane.setComponentAt (0,visualPreviewPanel);
                }
                repaint ();
            }
        });
        body.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                tabbedPane.setSelectedIndex (0);
            }
        });
        add(tabbedPane);

        tabbedPane.add (rawPanel);
        tabbedPane.setTabComponentAt (0,body);
        tabbedPane.addTab ("Header",resultHeaderPanel);

    }

    public void execute ()
    {
        responseCalculator = new ResponseCalculator (request,statusMessage,time,
                size,rawPanel,resultHeaderPanel,visualPreviewPanel);
        responseCalculator.execute ();
    }


}
