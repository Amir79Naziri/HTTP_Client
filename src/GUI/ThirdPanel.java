package GUI;

import Storage.ResponseStorage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



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
    private RequestGui requestGui;
    private JProgressBar progressBar;
    private ResponseCalculator responseCalculator;
    private JPanel progressPanel;

    /**
     * creates new Third panel
     * @param theme theme
     * @param requestGui requestGui
     */
    protected ThirdPanel (Theme theme, RequestGui requestGui)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        this.requestGui = requestGui;
        setLayout (new BorderLayout ());
        addHeaderStatuses ();
        addBasePanel ();
        addProgressPanel ();
        if (requestGui.getClientRequest ().getResponseStorage ().isValid ())
            properBack ();
    }

    /**
     * add header for statuses
     */
    private void addHeaderStatuses ()
    {
        JPanel headerStatus = new JPanel ();
        GridBagLayout layout = new GridBagLayout ();
        GridBagConstraints constraints = new GridBagConstraints ();
        headerStatus.setLayout (layout);
        headerStatus.setBackground (Color.WHITE);
        headerStatus.setPreferredSize (new Dimension (200,55));



        statusMessage = new JLabel ("Connecting");
        statusMessage.setBackground (new Color (189, 143, 17));
        createLabel (statusMessage,60);


        time = new JLabel ("0 ms");
        time.setBackground (Color.GRAY);
        createLabel (time,50);

        size = new JLabel ("0 B");
        size.setBackground (Color.GRAY);
        createLabel (size,50);




        constraints.weightx = 0.1f;
        constraints.ipadx = 18;
        constraints.ipady = 8;

        GridBagAdder.addComponent (statusMessage,0,0,4,layout,constraints,
                headerStatus);

        GridBagAdder.addComponent (time,0,4,3,layout,constraints,headerStatus);

        GridBagAdder.addComponent (size,0,7,3,layout,constraints,headerStatus);


        GridBagAdder.addComponent (new JLabel (),0,10,3,layout,constraints,
                headerStatus);
        GridBagAdder.addComponent (new JLabel (),0,13,1,layout,constraints,
                headerStatus);

        add(headerStatus,BorderLayout.NORTH);
    }

    /**
     * set status unknown in error and cancelled
     * @param result cancelled of Error
     */
    protected void statusUnknown (String result)
    {
        getRequestGui ().getClientRequest ().getResponseStorage ().reset ();
        size.setText ("0 B");
        time.setText ("0 ms");
        statusMessage.setText (result);
        statusMessage.setBackground (new Color (189, 24, 15));
        visualPreviewPanel.removeImage ();
        rawPanel.getTextArea ().setText (
                "Error: URL using bad/illegal format or missing URL");
        getRequestGui ().getClientRequest ().getResponseStorage ().
                setResponseMessage (result);
    }

    /**
     * add progress panel
     */
    private void addProgressPanel ()
    {
        GridBagConstraints constraints = new GridBagConstraints ();
        GridBagLayout layout = new GridBagLayout ();
        progressPanel = new JPanel ();
        progressPanel.setBorder (new EmptyBorder (5,5,5,5));
        progressPanel.setLayout (layout);
        progressPanel.setBackground (theme.getBackGroundColorV4 ());


        progressBar = new JProgressBar ();

        progressBar.setBorder (new EmptyBorder (1,1,1,1));
        progressBar.setForeground (theme.getBackGroundColorV2 ());
        progressBar.setBackground (theme.getBackGroundColorV4 ());
        JButton cancel = new JButton ("Cancel");
        cancel.setBackground (theme.getBackGroundColorV4 ());
        cancel.setForeground (theme.getForeGroundColorV2 ());
        cancel.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                responseCalculator.cancel (true);
            }
        });

        constraints.weightx = 0.1f;
        constraints.ipadx = 22;
        constraints.ipady = -12;
        GridBagAdder.addComponent (progressBar,0,0,5,layout,constraints,
                progressPanel);
        constraints.weightx = 0.0f;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        GridBagAdder.addComponent (cancel,0,5,1,layout,constraints,
                progressPanel);
        add(progressPanel,BorderLayout.SOUTH);
        progressPanel.setVisible (false);

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

    /**
     * execute
     */
    protected void execute ()
    {
        if (responseCalculator != null)
            responseCalculator.cancel (true);
        responseCalculator = new ResponseCalculator (this);
        responseCalculator.addPropertyChangeListener (new PropertyChangeListener () {
        @Override
        public void propertyChange (PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("progress"))
            {
                int newValue = (Integer) evt.getNewValue();
                progressBar.setValue(newValue);
            }
        }
    });
        progressPanel.setVisible (true);
        progressBar.setValue (2);
        responseCalculator.execute ();
    }

    /**
     * load data from requestGui on GUI
     */
    protected void properBack ()
    {
        ResponseStorage responseStorage
                = requestGui.getClientRequest ().getResponseStorage ();
        String responseCode = responseStorage.getResponseCode () + "";
        if (responseCode.matches ("2(.*)"))
            statusMessage.setBackground (new Color (52, 174, 22));
        else if (responseCode.equals ("0"))
        {
            statusUnknown (responseStorage.getResponseMessage ());
        }
        else
            statusMessage.setBackground (new Color (133, 94, 8));

        if (responseStorage.getResponseCode () == 0)
            statusMessage.setText (responseStorage.getResponseMessage ());
        else if (responseStorage.getResponseMessage () == null)
            statusMessage.setText (responseStorage.getResponseCode () + "");
        else
            statusMessage.setText (responseStorage.getResponseCode ()
                    + " " + responseStorage.getResponseMessage ());

        size.setText (responseStorage.getReadLength ());
        time.setText (responseStorage.getResponseTime () + "ms");
        rawPanel.getTextArea ().setText (responseStorage.getResponseTextRawData ());


        resultHeaderPanel.clear ();
        visualPreviewPanel.removeImage ();
        visualPreviewPanel.removeEditor ();
        if (responseStorage.getResponseHeaders () != null)
        {
            for (String key : responseStorage.getResponseHeaders ().keySet ())
                for (String value : responseStorage.getResponseHeaders ().get (key))
                {
                    if (key != null) {
                        resultHeaderPanel.addResultKeyAndValue (key, value);

                        if (key.equals ("Content-Type") && value.split (";")[0].
                                equals ("image/png"))
                        {
                            visualPreviewPanel.addImage
                                    (responseStorage.getResponseBinaryRawData ());
                        } else if (key.equals ("Content-Type") && value.split (";")[0].
                                equals ("text/html") && responseCode.matches ("2(.*)"))
                        {
                            visualPreviewPanel.addEditor (requestGui.getClientRequest ()
                                    .getUrl () +
                                    requestGui.getClientRequest ().getQueryDataString ());

                        }
                    }
                }
        }
    }

    /**
     * @return requestGui
     */
    protected RequestGui getRequestGui () {
        return requestGui;
    }

    /**
     * @return progress panel
     */
    protected JPanel getProgressPanel () {
        return progressPanel;
    }

    /**
     * @return status message label
     */
    protected JLabel getStatusMessage () {
        return statusMessage;
    }
}
