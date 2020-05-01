import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class RequestsPanel extends JPanel
{
    private HashMap<RequestType,JPanel> requests;

    public RequestsPanel ()
    {
        super();
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        setBackground (new Color (16, 22, 30, 201));
        requests = new HashMap<> ();
    }

    public void addNewRequest (RequestType type, String name)
    {
        Color color = new Color (194, 195, 194, 0);
        JPanel request = new JPanel ();
        request.setBackground (color);
        request.setLayout (new FlowLayout (FlowLayout.LEFT));
        request.setPreferredSize (new Dimension (340,35));
        request.setMaximumSize (new Dimension (340,35));
        request.setMinimumSize (new Dimension (340,35));

        JLabel typeLabel = new JLabel (type.getName ());
        typeLabel.setFont (new Font ("Arial",Font.PLAIN,10));
        typeLabel.setForeground (type.getColor ());
        typeLabel.setPreferredSize (new Dimension (40,29));


        JLabel nameLabel = new JLabel (name);
        nameLabel.setFont (new Font ("Arial",Font.PLAIN,13));

        nameLabel.setForeground (Color.WHITE);

        request.add (typeLabel);
        request.add (nameLabel);

        requests.put (type,request);
        add(request);
    }
}
