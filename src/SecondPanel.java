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


    public SecondPanel ()
    {
        super();
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

        String[] types = {"GET", "POST", "PUT", "PATCH", "DELETE"};
        type = new JComboBox<> (types);
        type.setFont (new Font ("Arial",Font.PLAIN,11));
        type.setBackground (Color.WHITE);

        url = new JTextField ("https://api.myproduct.com/v1/users");
        url.setBorder (new LineBorder (Color.WHITE,1));
        url.setFont (new Font ("Arial",Font.PLAIN,12));
        url.addKeyListener (new ComponentHandler ());

        send = new JButton ("Send");
        send.setFont (new Font ("Arial",Font.PLAIN,11));
        send.setBackground (Color.WHITE);

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


        queryPanel = new QueryPanel ();
        headerPanel = new HeaderPanel ();
        formUrlPanel = new FormUrlPanel ();
        jsonPanel = new JsonPanel ();
        bearerPanel = new BearerPanel ();
        tabbedPane.addTab ("Body",formUrlPanel);
        tabbedPane.addTab ("Bearer",bearerPanel);
        tabbedPane.addTab ("Query",queryPanel);
        tabbedPane.addTab ("Header",headerPanel);

        for (int i = 0; i < 10; i++)
        {
            queryPanel.addNewKeyAndValue ();
        }

        for (int i = 0; i < 17; i++)
        {
            headerPanel.addNewKeyAndValue ();
        }


        add (tabbedPane,BorderLayout.CENTER);
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

        }

        @Override
        public void itemStateChanged (ItemEvent e) {

        }
    }
}
