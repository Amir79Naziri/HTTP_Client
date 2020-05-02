import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SecondPanel extends JPanel
{
    private JTextField url;
    private JComboBox<String> type;
    private JButton send;


    public SecondPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        createURLPanel ();
        createBasePanel ();
    }

    private void createURLPanel ()
    {
        JPanel urlPanel = new JPanel ();
        urlPanel.setLayout (new FlowLayout (FlowLayout.CENTER));
        urlPanel.setBackground (Color.WHITE);

        String[] types = {"GET", "POST", "PUT", "PATCH", "DELETE"};
        type = new JComboBox<> (types);
        type.setPreferredSize (new Dimension (76,35));
        type.setFont (new Font ("Arial",Font.PLAIN,11));
        type.setBackground (Color.WHITE);
        

        url = new JTextField ("https://api.myproduct.com/v1/users");
        url.setPreferredSize (new Dimension (370,45));
        url.setMaximumSize (new Dimension (300,45));
        url.setMinimumSize (new Dimension (70,45));
        url.setBorder (new LineBorder (Color.WHITE,1));
        url.setFont (new Font ("Arial",Font.PLAIN,12));

        send = new JButton ("Send");
        send.setPreferredSize (new Dimension (75,35));
        send.setFont (new Font ("Arial",Font.PLAIN,11));
        send.setBackground (Color.WHITE);

        urlPanel.add (type);
        urlPanel.add (url);
        urlPanel.add (send);

        add(urlPanel,BorderLayout.NORTH);

    }

    private void createBasePanel ()
    {
        JTabbedPane tabbedPane = new JTabbedPane ();

        tabbedPane.addTab ("Header",new JScrollPane ((new HeaderPanel ())));

        add (tabbedPane,BorderLayout.CENTER);


    }

}
