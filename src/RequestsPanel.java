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

    public void addRequest ()
    {

    }

}
