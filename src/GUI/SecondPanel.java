package GUI;

import Client.RequestType;


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
    private JComboBox<String> type; // type of request
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
    private Request request; // request which has this panel
    private Theme theme; // theme

    private JPanel programThirdPanel; // third panel which user will see , it is null
    // panel at first
    private final ThirdPanel mainThirdPanel; // main third panel for  request

    /**
     * creates a new GUI.SecondPanel
     * @param gui gui
     * @param request owner request
     * @param theme theme
     */
    public SecondPanel (GUI gui, Request request, Theme theme)
    {
        super();
        if (gui == null || request == null || theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        mainThirdPanel = new ThirdPanel (theme,request);
        this.gui = gui;
        this.request = request;
        programThirdPanel = new NullPanel (2,theme);
        setLayout (new BorderLayout ());
        createURLPanel ();
        String[] bodyTypes = {"MultiPart ","form url encoded","JSON ", "Binary File"};
        bodyType = new JComboBox<> (bodyTypes);
        createBasePanel ();
        switch (request.getClientRequest ().getMessageBodyType ())
        {
            case 1 :
                bodyType.setSelectedIndex (0);
                break;
            case 2 :
                bodyType.setSelectedIndex (3);
                break;
            case 3 :
                bodyType.setSelectedIndex (2);
                break;
        }
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
        type.setSelectedItem (request.getRequestType ().toString ());
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
        save.setSelected (false);

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


        queryPanel = new QueryPanel (theme,request);
        headerPanel = new KeyAndValueContainerPanel (theme,request,1);
        multiPartPanel = new KeyAndValueContainerPanel (theme,request,2);
        urlEncodedPanel = new KeyAndValueContainerPanel (theme,request,3);
        jsonPanel = new JsonPanel (theme);
        bearerPanel = new BearerPanel (theme);
        binaryFilePanel = new BinaryFilePanel (theme);
        queryPanel.getPreviewURLText ().setText (request.getClientRequest ().getUrl()
        + request.getClientRequest ().getQueryDataString ());

        bodyType.addItemListener (new ItemListener () {
            @Override
            public void itemStateChanged (ItemEvent e) {

                if (bodyType.getSelectedIndex () == 0)
                {
                    tabbedPane.setComponentAt (0, multiPartPanel);
                    request.getClientRequest ().setMessageBodyType (1);
                    binaryFilePanel.clearPath ();
                    urlEncodedPanel.getKeyAndValuePanel ().deleteAll ();
                }
                else if (bodyType.getSelectedIndex () == 1)
                {
                    tabbedPane.setComponentAt (0, urlEncodedPanel);
                    request.getClientRequest ().setMessageBodyType (3);
                    multiPartPanel.getKeyAndValuePanel ().deleteAll ();
                    binaryFilePanel.clearPath ();
                }
                else if (bodyType.getSelectedIndex () == 2)
                {
                    tabbedPane.setComponentAt (0,jsonPanel);

                }
                else if (bodyType.getSelectedIndex () == 3)
                {
                    tabbedPane.setComponentAt (0,binaryFilePanel);
                    request.getClientRequest ().setMessageBodyType (2);
                    urlEncodedPanel.getKeyAndValuePanel ().deleteAll ();
                    urlEncodedPanel.getKeyAndValuePanel ().deleteAll ();
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
    public JPanel getProgramThirdPanel () {
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
                    request.getClientRequest ().setUrl (a);
                    queryPanel.getPreviewURLText ().setText (a +
                            request.getClientRequest ().getQueryDataString ());
                } catch (MalformedURLException ex)
                {
                    try {
                        request.getClientRequest ().setUrl ("http://" + a);
                        queryPanel.getPreviewURLText ().setText ("http://" + a +
                                request.getClientRequest ().getQueryDataString ());
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
                } else
                    System.out.println ("Some thing went wrong in initialize For Send ");
            }
        }

        @Override
        public void itemStateChanged (ItemEvent e) {
            if (e.getSource () == type)
            {
                switch (type.getSelectedIndex ()) {
                    case 0:
                        request.setRequestType (RequestType.GET);
                        request.getClientRequest ().
                                setRequestType (RequestType.GET.toString ());
                        break;
                    case 1:
                        request.setRequestType (RequestType.POST);
                        request.getClientRequest ().
                                setRequestType (RequestType.POST.toString ());
                        break;
                    case 2:
                        request.setRequestType (RequestType.PUT);
                        request.getClientRequest ().
                                setRequestType (RequestType.PUT.toString ());
                        break;
                    case 3:
                        request.setRequestType (RequestType.PATCH);
                        request.getClientRequest ().
                                setRequestType (RequestType.PATCH.toString ());
                        break;
                    case 4:
                        request.setRequestType (RequestType.DELETE);
                        request.getClientRequest ().
                                setRequestType (RequestType.DELETE.toString ());
                        break;
                }
            }
            //TODO : create save panel and add functionality
        }
    }

    public boolean initializeForSend (boolean isForSending)
    {
        headerPanel.getKeyAndValuePanel ().properData ();
        queryPanel.getKeyAndValuePanel ().properData ();
        if (request.getClientRequest ().getRequestType () != RequestType.GET)
        {
            switch (request.getClientRequest ().getMessageBodyType ())
            {
                case 1 :
                    multiPartPanel.getKeyAndValuePanel ().properData ();
                    break;
                case 2 :
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


                        request.getClientRequest ().addUploadBinaryFile (
                                new File (binaryFilePanel.getPath ().toString ()));
                    }
                    break;
                case 3 :
                    urlEncodedPanel.getKeyAndValuePanel ().properData ();
            }
        }
        return true;
    }



    //TODO : add jurl -S request to list



    private void properBack ()
    {
        url.setText (request.getClientRequest ().getUrl ());
        headerPanel.getKeyAndValuePanel ().properBack
                (request.getClientRequest ().getCustomHeaders ());
        queryPanel.getKeyAndValuePanel ().properBack (request.getClientRequest ()
        .getQueryData ());

        multiPartPanel.getKeyAndValuePanel ().properBack (request.getClientRequest ()
                .getFormData ());
        urlEncodedPanel.getKeyAndValuePanel ().properBack (request.getClientRequest ()
        .getFormDataEncoded ());
        binaryFilePanel.setPath (request.getClientRequest ().getUploadBinaryFilePath ());
    }
}
