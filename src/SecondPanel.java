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
    }

    private void createURLPanel ()
    {
        JPanel urlPanel = new JPanel ();
        urlPanel.setLayout (new FlowLayout (FlowLayout.LEFT));
        urlPanel.setBackground (Color.WHITE);

        String[] types = {"GET", "POST", "PUT", "PATCH", "DELETE"};
        type = new JComboBox<> (types);
        type.setPreferredSize (new Dimension (70,35));
        type.setFont (new Font ("Arial",Font.PLAIN,11));
        

        url = new JTextField ("https://api.myproduct.com/v1/users");
        url.setPreferredSize (new Dimension (260,45));
        url.setBorder (new LineBorder (Color.WHITE,1));
        url.setFont (new Font ("Arial",Font.PLAIN,12));

        send = new JButton ("Send");
        send.setPreferredSize (new Dimension (75,35));
        send.setFont (new Font ("Arial",Font.PLAIN,11));

        urlPanel.add (type);
        urlPanel.add (url);
        urlPanel.add (send);

        add(urlPanel,BorderLayout.NORTH);

    }

    private void basePanel ()
    {
        JTabbedPane tabbedPane = new JTabbedPane ();

    }

}
