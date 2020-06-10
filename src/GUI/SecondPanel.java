package GUI;

import ClientRequest.RequestType;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;

/**
 * this class represents secondPanel in program
 *
 * @author Amir Naziri
 */
public class SecondPanel extends JPanel
{
    private JTextField url; // url text
    private JComboBox<String> type; // type of requestGui
    private JButton send; // send button
    private JCheckBox save; // save button
    // private int messageBody;
    // panel's in second Panel
    private QueryPanel queryPanel;
    private KeyAndValueContainerPanel headerPanel;
    private KeyAndValueContainerPanel multiPartPanel;
    private KeyAndValueContainerPanel urlEncodedPanel;
    private JsonPanel jsonPanel;
    private BearerPanel bearerPanel;
    private BinaryFilePanel binaryFilePanel;
    private JComboBox<String> bodyType;
    private GUI gui; // gui
    private RequestGui requestGui; // requestGui which has this panel
    private Theme theme; // theme
    private boolean firstTimeChangeBody;
    private JPanel programThirdPanel; // third panel which user will see , it is null
    // panel at first
    private final ThirdPanel mainThirdPanel; // main third panel for  requestGui

    /**
     * creates a new GUI.SecondPanel
     * @param gui gui
     * @param requestGui owner requestGui
     * @param theme theme
     */
    protected SecondPanel (GUI gui, RequestGui requestGui, Theme theme)
    {
        super();
        if (gui == null || requestGui == null || theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        mainThirdPanel = new ThirdPanel (theme, requestGui);
        this.gui = gui;
        this.requestGui = requestGui;
        if (!requestGui.getClientRequest ().getResponseStorage ().isValid ())
            programThirdPanel = new NullPanel (2,theme);
        else
        {
            gui.setThirdPanel (mainThirdPanel);
            programThirdPanel = mainThirdPanel;
        }
        setLayout (new BorderLayout ());
        createURLPanel ();
        String[] bodyTypes = {"MultiPart ","form url encoded","JSON ", "Binary File"};
        bodyType = new JComboBox<> (bodyTypes);
        createBasePanel ();
        firstTimeChangeBody = false;
        switch (requestGui.getClientRequest ().getMessageBodyType ())
        {
            case 1 :
                bodyType.setSelectedIndex (0);
                break;
            case 2 :
                bodyType.setSelectedIndex (3);
                break;
            case 3 :
                bodyType.setSelectedIndex (1);
                break;
        }
        firstTimeChangeBody = true;
        properBack ();
    }

    /**
     * creates url panel in second panel
     */
    private void createURLPanel ()
    {
        GridBagConstraints constraints = new GridBagConstraints ();
        GridBagLayout layout = new GridBagLayout ();
        JPanel urlPanel = new JPanel ();
        urlPanel.setLayout (layout);
        urlPanel.setBackground (Color.WHITE);

        ComponentHandler componentHandler = new ComponentHandler ();
        String[] types = {"GET", "POST", "PUT", "PATCH", "DELETE"};
        type = new JComboBox<> (types);
        type.setFont (new Font ("Arial",Font.PLAIN,11));
        type.setBackground (Color.WHITE);
        type.setSelectedItem (requestGui.getRequestType ().toString ());
        type.addItemListener (componentHandler);

        url = new JTextField ("https://api.myproduct.com/v1/users");
        url.setBorder (new LineBorder (Color.WHITE,1));
        url.setFont (new Font ("Arial",Font.PLAIN,12));
        url.addKeyListener (componentHandler);

        send = new JButton ("Send");
        send.setFont (new Font ("Arial",Font.PLAIN,11));
        send.setBackground (Color.WHITE);
        send.addActionListener (componentHandler);

        save = new JCheckBox ("Save Output");
        save.setFont (new Font ("Arial",Font.PLAIN,11));
        save.setBackground (Color.WHITE);
        if (requestGui.getClientRequest ().isShouldSaveOutputInFile ())
            save.setSelected (true);
        else
            save.setSelected (false);
        save.addItemListener (componentHandler);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets (10,10,10,10);
        constraints.ipady = 10;
        GridBagAdder.addComponent (type,0,0,1,layout,constraints,urlPanel);

        constraints.weightx = 0.5;
        GridBagAdder.addComponent (url,0,1,10,layout,constraints,urlPanel);

        constraints.weightx = 0.0;
        GridBagAdder.addComponent (send,0,11,1,layout,constraints,urlPanel);

        GridBagAdder.addComponent (save,0,12,1,layout,constraints,urlPanel);

        add(urlPanel,BorderLayout.NORTH);
    }

    /**
     * create base panel in second panel
     */
    private void createBasePanel ()
    {

        JTabbedPane tabbedPane = new JTabbedPane ();
        tabbedPane.setTabLayoutPolicy (JTabbedPane.SCROLL_TAB_LAYOUT);


        queryPanel = new QueryPanel (theme, requestGui);
        headerPanel = new KeyAndValueContainerPanel (theme, requestGui,1);
        multiPartPanel = new KeyAndValueContainerPanel (theme, requestGui,2);
        urlEncodedPanel = new KeyAndValueContainerPanel (theme, requestGui,3);
        jsonPanel = new JsonPanel (theme);
        bearerPanel = new BearerPanel (theme);
        binaryFilePanel = new BinaryFilePanel (theme);
        queryPanel.getPreviewURLText ().setText (requestGui.getClientRequest ().getUrl()
        + requestGui.getClientRequest ().getQueryDataString ());

        bodyType.addItemListener (new ItemListener () {
            @Override
            public void itemStateChanged (ItemEvent e) {

                if (e.getStateChange () == ItemEvent.SELECTED)
                {
                    int res;
                    gui.getBaseFrame ().requestFocusInWindow ();
                    if (firstTimeChangeBody) {
                        res = JOptionPane.showOptionDialog (gui.getBaseFrame (),
                                "Current body will be lost. Are you sure wou want " +
                                        "to continue?", "Switch Body Type",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE, null,
                                null, null);
                    } else
                        res = 0;
                    if (res == 0)
                    {
                        if (bodyType.getSelectedIndex () == 0) {
                            tabbedPane.setComponentAt (0, multiPartPanel);
                            requestGui.getClientRequest ().setMessageBodyType (1);
                            binaryFilePanel.clearPath ();
                            urlEncodedPanel.getKeyAndValuePanel ().deleteAll ();
                            if (urlEncodedPanel.getKeyAndValuePanel ().isFixedKeyAndValuePanelDescVisible ())
                                urlEncodedPanel.getKeyAndValuePanel ().toggleAllDescription ();
                        } else if (bodyType.getSelectedIndex () == 1) {
                            tabbedPane.setComponentAt (0, urlEncodedPanel);
                            requestGui.getClientRequest ().setMessageBodyType (3);
                            multiPartPanel.getKeyAndValuePanel ().deleteAll ();
                            if (multiPartPanel.getKeyAndValuePanel ().isFixedKeyAndValuePanelDescVisible ())
                                multiPartPanel.getKeyAndValuePanel ().toggleAllDescription ();
                            binaryFilePanel.clearPath ();
                        } else if (bodyType.getSelectedIndex () == 2) {
                            tabbedPane.setComponentAt (0, jsonPanel);

                        } else if (bodyType.getSelectedIndex () == 3) {
                            tabbedPane.setComponentAt (0, binaryFilePanel);
                            requestGui.getClientRequest ().setMessageBodyType (2);
                            multiPartPanel.getKeyAndValuePanel ().deleteAll ();
                            if (multiPartPanel.getKeyAndValuePanel ().isFixedKeyAndValuePanelDescVisible ())
                                multiPartPanel.getKeyAndValuePanel ().toggleAllDescription ();
                            urlEncodedPanel.getKeyAndValuePanel ().deleteAll ();
                            if (urlEncodedPanel.getKeyAndValuePanel ().isFixedKeyAndValuePanelDescVisible ())
                                urlEncodedPanel.getKeyAndValuePanel ().toggleAllDescription ();
                        }
                    } else
                    {
                        firstTimeChangeBody = false;
                        switch (requestGui.getClientRequest ().getMessageBodyType ())
                        {
                            case 1 : bodyType.setSelectedIndex (0);
                                break;
                            case 2 : bodyType.setSelectedIndex (3);
                                break;
                            case 3 : bodyType.setSelectedIndex (1);
                        }
                        firstTimeChangeBody = true;
                    }

                }
                repaint ();

            }
        });
        bodyType.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                tabbedPane.setSelectedIndex (0);
            }
        });
        bodyType.setBackground (Color.GRAY);

        tabbedPane.add (multiPartPanel);
        tabbedPane.setTabComponentAt (0,bodyType);
        tabbedPane.addTab ("Bearer",bearerPanel);
        tabbedPane.addTab ("Query",queryPanel);
        tabbedPane.addTab ("Header", headerPanel);

        add (tabbedPane,BorderLayout.CENTER);
    }

    /**
     * @return program third panel
     */
    protected JPanel getProgramThirdPanel () {
        return programThirdPanel;
    }


    /**
     * this class handles components in this panel
     */
    private class ComponentHandler extends KeyAdapter
        implements ActionListener, ItemListener
    {
        @Override
        public void keyReleased (KeyEvent e) {
            if (e.getKeyCode () == KeyEvent.VK_ENTER)
            {
                if (initializeForSend (true))
                {
                    gui.setThirdPanel (mainThirdPanel);
                    programThirdPanel = mainThirdPanel;
                    mainThirdPanel.execute ();
                }
            }
            else
            {
                String a = url.getText ();
                try{
                    requestGui.getClientRequest ().setUrl (a);
                    queryPanel.getPreviewURLText ().setText (a +
                            requestGui.getClientRequest ().getQueryDataString ());
                } catch (MalformedURLException ex)
                {
                    try {
                        requestGui.getClientRequest ().setUrl ("http://" + a);
                        queryPanel.getPreviewURLText ().setText ("http://" + a +
                                requestGui.getClientRequest ().getQueryDataString ());
                    } catch (MalformedURLException ignore)
                    {
                    }
                }
            }
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource () == send) {

                if (initializeForSend (true))
                {
                    gui.setThirdPanel (mainThirdPanel);
                    programThirdPanel = mainThirdPanel;
                    mainThirdPanel.execute ();
                }
            }
        }

        @Override
        public void itemStateChanged (ItemEvent e) {
            if (e.getSource () == type)
            {
                switch (type.getSelectedIndex ()) {
                    case 0:
                        requestGui.setRequestType (RequestType.GET);
                        requestGui.getClientRequest ().
                                setRequestType (RequestType.GET.toString ());
                        break;
                    case 1:
                        requestGui.setRequestType (RequestType.POST);
                        requestGui.getClientRequest ().
                                setRequestType (RequestType.POST.toString ());
                        break;
                    case 2:
                        requestGui.setRequestType (RequestType.PUT);
                        requestGui.getClientRequest ().
                                setRequestType (RequestType.PUT.toString ());
                        break;
                    case 3:
                        requestGui.setRequestType (RequestType.PATCH);
                        requestGui.getClientRequest ().
                                setRequestType (RequestType.PATCH.toString ());
                        break;
                    case 4:
                        requestGui.setRequestType (RequestType.DELETE);
                        requestGui.getClientRequest ().
                                setRequestType (RequestType.DELETE.toString ());
                        break;
                }
            } else if (e.getSource () == save)
            {
                if (!save.isSelected ())
                    requestGui.getClientRequest ().setShouldSaveOutputInFile
                            (false,
                                    null);
                else
                    requestGui.getClientRequest ().setShouldSaveOutputInFile
                            (true,
                                    null);
            }
        }
    }

    /**
     * initialize a requestGui by take every data from GUI
     * @param isForSending isForSending true, isFor close false
     * @return result
     */
    protected boolean initializeForSend (boolean isForSending)
    {
        if (requestGui.getClientRequest ().getMessageBodyType () == 2)
        {
                if (requestGui.getClientRequest ().getRequestType () != RequestType.GET)
                {
                    File file;
                    if (isForSending) {
                        if (binaryFilePanel.getPath () != null) {
                            file = new File (binaryFilePanel.getPath ().toString ());
                            if (!file.exists ()) {
                                JOptionPane.showMessageDialog (this,
                                        "Invalid File !",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                return false;
                            }
                        } else {
                            JOptionPane.showMessageDialog (this,
                                    "Invalid File !", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return false;
                        }

                        requestGui.getClientRequest ().addUploadBinaryFile (
                                new File (binaryFilePanel.getPath ().toString ()));
                    }
                }
        }

        if (save.isSelected () && isForSending) {
            String name =
                    JOptionPane.showInputDialog (gui.getBaseFrame (),
                            "Name of Output file (By keeping this Empty " +
                                    "Program will consider a name for it)",
                            "Save Output", JOptionPane.PLAIN_MESSAGE);
            if (name == null)
                return false;
            if (name.length () == 0)
                requestGui.getClientRequest ().setShouldSaveOutputInFile (
                        true,null);
            else
                requestGui.getClientRequest ().setShouldSaveOutputInFile (
                        true,name);
        }
        return true;
    }

    /**
     * load data from requestGui on GUI
     */
    private void properBack ()
    {
        url.setText (requestGui.getClientRequest ().getUrl ());
        headerPanel.getKeyAndValuePanel ().properBack
                (requestGui.getClientRequest ().getCustomHeaders ());
        headerPanel.getKeyAndValuePanel ().properBackV2 (
                requestGui.getClientRequest ().getExtraData ().getDeActiveHeaders ());
        headerPanel.getKeyAndValuePanel ().properDescriptionBack (
                requestGui.getClientRequest ().getExtraData ().getHeadersDescription ());

        queryPanel.getKeyAndValuePanel ().properBack (requestGui.getClientRequest ()
        .getQueryData ());
        queryPanel.getKeyAndValuePanel ().properBackV2 (requestGui.getClientRequest ()
        .getExtraData ().getDeActiveQueries ());
        queryPanel.getKeyAndValuePanel ().properDescriptionBack (requestGui.getClientRequest ()
        .getExtraData ().getQueriesDescription ());

        multiPartPanel.getKeyAndValuePanel ().properBack (requestGui.getClientRequest ()
                .getFormUrlData ());
        multiPartPanel.getKeyAndValuePanel ().properBackV2 (requestGui.getClientRequest ()
        .getExtraData ().getDeActiveMultiMap ());
        multiPartPanel.getKeyAndValuePanel ().properDescriptionBack (requestGui.getClientRequest ()
        .getExtraData ().getMultiDescription ());

        urlEncodedPanel.getKeyAndValuePanel ().properBack (requestGui.getClientRequest ()
        .getFormUrlDataEncoded ());
        urlEncodedPanel.getKeyAndValuePanel ().properBackV2 (requestGui.getClientRequest ()
        .getExtraData ().getDeActiveEncodedMap ());
        urlEncodedPanel.getKeyAndValuePanel ().properDescriptionBack (requestGui.getClientRequest ()
        .getExtraData ().getEncodedDescription ());

        binaryFilePanel.setPath (requestGui.getClientRequest ().getUploadBinaryFilePath ());

        if (requestGui.getClientRequest ().getExtraData ().isToggledHeadersDescription ())
        {
            headerPanel.getKeyAndValuePanel ().toggleAllDescription ();
        }
        if (requestGui.getClientRequest ().getExtraData ().isToggledMultiMapDescription ())
        {
            multiPartPanel.getKeyAndValuePanel ().toggleAllDescription ();
        }
        if (requestGui.getClientRequest ().getExtraData ().isToggledQueriesDescription ())
        {
            queryPanel.getKeyAndValuePanel ().toggleAllDescription ();
        }
        if (requestGui.getClientRequest ().getExtraData ().isToggleEncodedMapDescription ())
        {
            urlEncodedPanel.getKeyAndValuePanel ().toggleAllDescription ();
        }
    }
}
