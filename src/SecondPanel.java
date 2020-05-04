import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.InputMismatchException;

public class SecondPanel extends JPanel
{
    private JTextField url;
    private JComboBox<String> type;
    private JButton send;
    private JButton save;

    private QueryPanel queryPanel;
    private HeaderPanel headerPanel;


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
        url.setPreferredSize (new Dimension (300,45));
        url.setMaximumSize (new Dimension (290,45));
        url.setMinimumSize (new Dimension (70,45));
        url.setBorder (new LineBorder (Color.WHITE,1));
        url.setFont (new Font ("Arial",Font.PLAIN,12));
        url.addKeyListener (new ComponentHandler ());

        send = new JButton ("Send");
        send.setPreferredSize (new Dimension (65,35));
        send.setFont (new Font ("Arial",Font.PLAIN,11));
        send.setBackground (Color.WHITE);

        save = new JButton ("Save");
        save.setPreferredSize (new Dimension (65,35));
        save.setFont (new Font ("Arial",Font.PLAIN,11));
        save.setBackground (Color.WHITE);

        urlPanel.add (type);
        urlPanel.add (url);
        urlPanel.add (send);
        urlPanel.add (save);

        add(urlPanel,BorderLayout.NORTH);

    }

    private void createBasePanel ()
    {
        JTabbedPane tabbedPane = new JTabbedPane ();

        queryPanel = new QueryPanel ();
        headerPanel = new HeaderPanel ();
        tabbedPane.addTab ("Query",fetchToJScrollPane (queryPanel));
        tabbedPane.addTab ("Header",fetchToJScrollPane (headerPanel));

        for (int i = 0; i < 18; i++)
        {
            queryPanel.addNewKeyAndValue ();
        }

        for (int i = 0; i < 7; i++)
        {
            headerPanel.addNewKeyAndValue ();
        }


        add (tabbedPane,BorderLayout.CENTER);
    }

    private JScrollPane fetchToJScrollPane (JPanel panel)
    {
        if (panel == null)
            throw new InputMismatchException ("Input is not valid");
        JScrollPane scrollPane = new JScrollPane (panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder (new LineBorder (new Color (16, 22, 30, 208),1));
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        return scrollPane;
    }

    private class ComponentHandler extends KeyAdapter
        implements ActionListener, ItemListener
    {
        @Override
        public void keyReleased (KeyEvent e) {
            String a = url.getText ();
            queryPanel.getPre ().setText (a);
        }

        @Override
        public void actionPerformed (ActionEvent e) {

        }

        @Override
        public void itemStateChanged (ItemEvent e) {

        }
    }
}
