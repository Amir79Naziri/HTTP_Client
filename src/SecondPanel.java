import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class SecondPanel extends JPanel
{

    private JPanel programThirdPanel;
    private final JPanel mainThirdPanel = new ThirdPanel ();

    private JTextField url;
    private JComboBox<String> type;
    private JButton send;
    private JButton save;

    private QueryPanel queryPanel;
    private HeaderPanel headerPanel;
    private FormUrlPanel formUrlPanel;
    private JsonPanel jsonPanel;
    private BearerPanel bearerPanel;
    private GUI gui;
    private Request request;


    public SecondPanel (GUI gui,Request request)
    {
        super();
        this.gui = gui;
        this.request = request;
        programThirdPanel = new NullPanel (2);
        setLayout (new BorderLayout ());
        createURLPanel ();
        createBasePanel ();
    }

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


        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets (10,10,10,10);
        constraints.ipady = 10;
        layout.setConstraints (type,constraints);
        urlPanel.add (type);

        constraints.gridx = 1;
        constraints.gridwidth = 10;
        constraints.weightx = 0.5;
        layout.setConstraints (url,constraints);
        urlPanel.add (url);

        constraints.gridx = 11;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        layout.setConstraints (send,constraints);
        urlPanel.add (send);

        constraints.gridx = 12;
        layout.setConstraints (save,constraints);
        urlPanel.add (save);

        add(urlPanel,BorderLayout.NORTH);

    }


    private void createBasePanel ()
    {

        JTabbedPane tabbedPane = new JTabbedPane ();
        tabbedPane.setTabLayoutPolicy (JTabbedPane.SCROLL_TAB_LAYOUT);


        queryPanel = new QueryPanel ();
        headerPanel = new HeaderPanel ();
        formUrlPanel = new FormUrlPanel ();
        jsonPanel = new JsonPanel ();
        bearerPanel = new BearerPanel ();

        String[] bodyTypes = {"Form URL Encoded ","JSON "};
        JComboBox<String> body = new JComboBox<> (bodyTypes);
        body.addItemListener (new ItemListener () {
            @Override
            public void itemStateChanged (ItemEvent e) {
                if (body.getSelectedIndex () == 0)
                {
                    tabbedPane.setComponentAt (0,formUrlPanel);
                    tabbedPane.setSelectedIndex (0);
                }
                else if (body.getSelectedIndex () == 1)
                {
                    tabbedPane.setComponentAt (0,jsonPanel);
                    tabbedPane.setSelectedIndex (0);
                } else if (body.getSelectedIndex () == 2)
                {
                    // binary
                }
                repaint ();

            }
        });
        body.setBackground (Color.GRAY);

        tabbedPane.add (formUrlPanel);
        tabbedPane.setTabComponentAt (0,body);
        tabbedPane.addTab ("Bearer",bearerPanel);
        tabbedPane.addTab ("Query",queryPanel);
        tabbedPane.addTab ("Header",headerPanel);

        add (tabbedPane,BorderLayout.CENTER);
    }

    public JPanel getProgramThirdPanel () {
        return programThirdPanel;
    }

    private class ComponentHandler extends KeyAdapter
        implements ActionListener, ItemListener
    {
        @Override
        public void keyReleased (KeyEvent e) {
            String a = url.getText ();
            queryPanel.getPreviewURLText ().setText (a);
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource () == send)
            {
                gui.setThirdPanel (mainThirdPanel);
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
