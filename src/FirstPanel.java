import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstPanel extends JPanel
{
    private JButton filterButton;
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
        header.setMaximumSize (new Dimension (350,55));
        header.setMinimumSize ((new Dimension (350,55)));

        JLabel label = new JLabel ("  HTTP client");
        label.setFont (new Font ("Arial",Font.PLAIN,20));
        label.setForeground (Color.WHITE);

        header.setBackground (color);
        header.add (label,BorderLayout.WEST);
        add(header);
    }

    private void createFilterPanel ()
    {
        Color color = new Color (46, 53, 53, 255);
        JPanel filterPanel = new JPanel ();
        filterPanel.setPreferredSize (new Dimension (350,40));
        filterPanel.setMaximumSize (new Dimension (350,40));
        filterPanel.setMinimumSize ((new Dimension (350,40)));
        filterPanel.setBackground (color);
        filterPanel.setLayout (new FlowLayout (FlowLayout.LEFT));
        add(filterPanel);

        searchText = new JTextField ("filter");
        searchText.setPreferredSize (new Dimension (270,30));
        searchText.setFont (new Font ("Arial",Font.PLAIN,15));
        searchText.setBackground (color);
        searchText.setForeground (Color.WHITE);


        filterButton = new JButton ();
        filterButton.setPreferredSize (new Dimension (30,30));
        filterButton.setIcon (new ImageIcon ("./images/filter.png"));

        addRequest = new JButton ();
        addRequest.setPreferredSize (new Dimension (30,30));
        addRequest.setIcon (new ImageIcon ("./images/addR1.png"));



        filterPanel.add (searchText);
        filterPanel.add (filterButton);
        filterPanel.add (addRequest);
    }

    private void createRequestsPanel ()
    {
        RequestsPanel requestsPanel = new RequestsPanel ();
        JScrollPane scrollPane = new JScrollPane (requestsPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
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
            } else if (e.getSource () == filterButton || e.getSource () == searchText)
            {
                // add filter to list
            }
        }
    }
}
