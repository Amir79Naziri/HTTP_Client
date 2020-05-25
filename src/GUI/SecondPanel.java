package GUI;

import Client.RequestType;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
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
    private JButton save; // save button

    // panel's in second Panel
    private QueryPanel queryPanel;
    private HeaderPanel headerPanel;
    private MultiPartPanel multiPartPanel;
    private JsonPanel jsonPanel;
    private BearerPanel bearerPanel;
    private BinaryFilePanel binaryFilePanel;

    private GUI gui; // gui
    private Request request; // request which has this panel
    private Theme theme; // theme

    private JPanel programThirdPanel; // third panel which user will see , it is null panel at first
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
        createBasePanel ();
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

        save = new JButton ("Save");
        save.setFont (new Font ("Arial",Font.PLAIN,11));
        save.setBackground (Color.WHITE);

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


        queryPanel = new QueryPanel (theme);
        headerPanel = new HeaderPanel (theme);
        multiPartPanel = new MultiPartPanel (theme);
        jsonPanel = new JsonPanel (theme);
        bearerPanel = new BearerPanel (theme);
        binaryFilePanel = new BinaryFilePanel (theme);

        String[] bodyTypes = {"MultiPart ","JSON ", "Binary File"};
        JComboBox<String> body = new JComboBox<> (bodyTypes);
        body.addItemListener (new ItemListener () {
            @Override
            public void itemStateChanged (ItemEvent e) {

                if (body.getSelectedIndex () == 0)
                {
                    tabbedPane.setComponentAt (0, multiPartPanel);
                    request.getClientRequest ().setMessageBodyType (1);

                }
                else if (body.getSelectedIndex () == 1)
                {
                    tabbedPane.setComponentAt (0,jsonPanel);


                } else if (body.getSelectedIndex () == 2)
                {
                    tabbedPane.setComponentAt (0,binaryFilePanel);
                    request.getClientRequest ().setMessageBodyType (2);
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
        body.setBackground (Color.GRAY);

        tabbedPane.add (multiPartPanel);
        tabbedPane.setTabComponentAt (0,body);
        tabbedPane.addTab ("Bearer",bearerPanel);
        tabbedPane.addTab ("Query",queryPanel);
        tabbedPane.addTab ("Header",headerPanel);

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
            String a = url.getText ();
            queryPanel.getPreviewURLText ().setText (a);
            try{
                request.getClientRequest ().setUrl (a);
            } catch (MalformedURLException ex)
            {
                try {
                    request.getClientRequest ().setUrl ("http://" + a);
                } catch (MalformedURLException ignore)
                {
                }
            }
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource () == send)
            {
                gui.setThirdPanel (mainThirdPanel);
                programThirdPanel = mainThirdPanel;
                mainThirdPanel.execute ();
            }
        }

        @Override
        public void itemStateChanged (ItemEvent e) {
            switch (type.getSelectedIndex ()) {
                case 0:
                    request.setRequestType (RequestType.GET);
                    break;
                case 1:
                    request.setRequestType (RequestType.POST);
                    break;
                case 2:
                    request.setRequestType (RequestType.PUT);
                    break;
                case 3:
                    request.setRequestType (RequestType.PATCH);
                    break;
                case 4:
                    request.setRequestType (RequestType.DELETE);
                    break;
            }
        }
    }

}
