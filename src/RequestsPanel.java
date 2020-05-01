import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class RequestsPanel extends JPanel
{
    private HashMap<RequestType,JPanel> requests;

    public RequestsPanel ()
    {
        super();
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        setBackground (new Color (46, 53, 53, 255));
        requests = new HashMap<> ();
    }

    public void addNewRequest (RequestType type, String name)
    {
        JPanel request = new Request ();
//        request.setBackground (new Color (46, 53, 53, 255));
//        request.setLayout (new FlowLayout (FlowLayout.LEFT));
//        request.setPreferredSize (new Dimension (340,40));
//        request.setMaximumSize (new Dimension (340,40));
//        request.setMinimumSize (new Dimension (340,40));
//        request.addMouseListener (new MouseHandler ());

        JPanel chose = new JPanel ();
        chose.setBackground (new Color (122, 103,218));
        chose.setPreferredSize (new Dimension (1,35));

        JLabel typeLabel = new JLabel (type.getName ());
        typeLabel.setFont (new Font ("Arial",Font.PLAIN,10));
        typeLabel.setForeground (type.getColor ());
        typeLabel.setPreferredSize (new Dimension (40,29));


        JLabel nameLabel = new JLabel (name);
        nameLabel.setFont (new Font ("Arial",Font.PLAIN,13));

        nameLabel.setForeground (Color.WHITE);

        request.add (chose);
        request.add (typeLabel);
        request.add (nameLabel);

        requests.put (type,request);
        add(request);
    }

    public HashMap<RequestType, JPanel> getRequests () {
        return requests;
    }


//    private class MouseHandler extends MouseAdapter
//    {
//
//        @Override
//        public void mouseEntered (MouseEvent e) {
//
//            e.getComponent ().requestFocusInWindow ();
//            e.getComponent ().setBackground (new Color (71, 80, 87, 255));
//
//        }
//
//        @Override
//        public void mouseExited (MouseEvent e) {
//
//            e.getComponent ().setBackground (new Color (46, 53, 53, 255));
//
//        }
//
//        @Override
//        public void mouseClicked (MouseEvent e) {
//            super.mouseClicked (e);
//        }
//    }


}
