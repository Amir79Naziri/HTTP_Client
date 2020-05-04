import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstPanel extends JPanel
{

    private JButton addRequest;
    private JTextField searchText;


    public FirstPanel ()
    {
        super();
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        createHeaderPanel ();
        createFilterPanel ();
        createRequestsPanel ();
    }

    private void createHeaderPanel ()
    {
        Color color = new Color (122, 103,218);
        JPanel header = new JPanel (new BorderLayout (3,4));
        header.setPreferredSize (new Dimension (350,55));
//        header.setMaximumSize (new Dimension (350,55));
        header.setMinimumSize ((new Dimension (300,55)));

        JLabel label = new JLabel ("  HTTP client");
        label.setFont (new Font ("Arial",Font.PLAIN,20));
        label.setForeground (Color.WHITE);

        header.setBackground (color);
        header.add (label,BorderLayout.WEST);
        add(header);
    }

    private void createFilterPanel ()
    {
        Color color = new Color (45, 46, 42, 255);
        JPanel filterPanel = new JPanel ();
        filterPanel.setPreferredSize (new Dimension (350,40));
//        filterPanel.setMaximumSize (new Dimension (350,40));
        filterPanel.setMinimumSize ((new Dimension (300,40)));
        filterPanel.setBackground (color);
        filterPanel.setLayout (new FlowLayout (FlowLayout.LEFT));
        add(filterPanel);

        searchText = new JTextField ("filter");
        searchText.setPreferredSize (new Dimension (270,30));
        searchText.setMinimumSize (new Dimension (200,30));
        searchText.setFont (new Font ("Arial",Font.PLAIN,15));
        searchText.setBackground (color);
        searchText.setForeground (Color.LIGHT_GRAY);
        searchText.setBorder (BorderFactory.createCompoundBorder (
                new LineBorder (Color.GRAY,1,true),
                new EmptyBorder (1,5,1,5)));





        addRequest = new JButton ();
        addRequest.setPreferredSize (new Dimension (22,22));
        addRequest.setIcon (new ImageIcon ("./images/addR1.png"));
        addRequest.setRolloverIcon (new ImageIcon ("./images/addR2.png"));
        addRequest.setRolloverEnabled (true);
        addRequest.setBackground (color);



        filterPanel.add (searchText);

        filterPanel.add (addRequest);
    }

    private void createRequestsPanel ()
    {
        RequestsPanel requestsPanel = new RequestsPanel ();
        JScrollPane scrollPane = new JScrollPane
                (requestsPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAutoscrolls (true);
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        scrollPane.setBorder (new LineBorder (new Color (16, 22, 30, 208),1));
        add (scrollPane);

        for (int i = 0; i < 5; i++)
        {
            requestsPanel.addNewRequest (RequestType.GET,"Ammm");
            requestsPanel.addNewRequest (RequestType.DELETE,"dfsfg");
            requestsPanel.addNewRequest (RequestType.PATCH,"sdf");
            requestsPanel.addNewRequest (RequestType.PUT,"kfj");
            requestsPanel.addNewRequest (RequestType.POST,"eeee");
        }
    }

    private class buttonHandler implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {

            if (e.getSource () == addRequest)
            {
                // add a new request
            } else if (e.getSource () == searchText)
            {
                // add filter to list
            }
        }
    }
}
