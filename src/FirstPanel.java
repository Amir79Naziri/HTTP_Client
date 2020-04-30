import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FirstPanel extends JPanel
{
    private JButton filterButton;
    private JButton addRequest;


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
        Color color = new Color (16, 22, 30, 208);
        JPanel filterPanel = new JPanel ();
        filterPanel.setPreferredSize (new Dimension (350,40));
        filterPanel.setMaximumSize (new Dimension (350,40));
        filterPanel.setMinimumSize ((new Dimension (350,40)));
        filterPanel.setBackground (color);
        filterPanel.setLayout (new FlowLayout (FlowLayout.LEFT));
        add(filterPanel);

        JTextField searchText = new JTextField ("filter");
        searchText.setPreferredSize (new Dimension (270,30));
        searchText.setFont (new Font ("Arial",Font.PLAIN,14));


        filterButton = new JButton ();
        filterButton.setPreferredSize (new Dimension (30,30));

        addRequest = new JButton ();
        addRequest.setPreferredSize (new Dimension (30,30));


        filterPanel.add (searchText);
        filterPanel.add (filterButton);
        filterPanel.add (addRequest);
    }

    private void createRequestsPanel ()
    {
        JPanel requestsPanel = new RequestsPanel ();
        JScrollPane scrollPane = new JScrollPane (requestsPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAutoscrolls (true);
        scrollPane.setBorder (new LineBorder (new Color (16, 22, 30, 208),1));
        add (scrollPane);
    }
}
