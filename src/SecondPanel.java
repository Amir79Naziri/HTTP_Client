import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class SecondPanel extends JPanel
{



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
    private Theme theme;

    private JPanel programThirdPanel;
    private final JPanel mainThirdPanel;

    public SecondPanel (GUI gui,Request request, Theme theme)
    {
        super();
        this.theme = theme;
        mainThirdPanel = new ThirdPanel (theme);
        this.gui = gui;
        this.request = request;
        programThirdPanel = new NullPanel (2,theme);
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


    private void createBasePanel ()
    {

        JTabbedPane tabbedPane = new JTabbedPane ();
        tabbedPane.setTabLayoutPolicy (JTabbedPane.SCROLL_TAB_LAYOUT);


        queryPanel = new QueryPanel (theme);
        headerPanel = new HeaderPanel (theme);
        formUrlPanel = new FormUrlPanel (theme);
        jsonPanel = new JsonPanel (theme);
        bearerPanel = new BearerPanel (theme);

        String[] bodyTypes = {"Form URL Encoded ","JSON "};
        JComboBox<String> body = new JComboBox<> (bodyTypes);
        body.addItemListener (new ItemListener () {
            @Override
            public void itemStateChanged (ItemEvent e) {

                if (body.getSelectedIndex () == 0)
                {
                    tabbedPane.setComponentAt (0,formUrlPanel);

                }
                else if (body.getSelectedIndex () == 1)
                {
                    tabbedPane.setComponentAt (0,jsonPanel);

                } else if (body.getSelectedIndex () == 2)
                {
                    // binary
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
                programThirdPanel = mainThirdPanel;
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
