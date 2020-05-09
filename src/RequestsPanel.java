import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class RequestsPanel extends JPanel
{
    private ArrayList<Request> requests;

    public RequestsPanel ()
    {
        super();
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        setBackground (new Color (45, 46, 42, 255));
        requests = new ArrayList<> ();
    }

    public void addNewRequest (RequestType type, String name)
    {
        Request request = new Request (type,name);
        request.addMouseListener (new MouseHandler ());

        requests.add (request);
        add(request);
        request.setVisible (false);
        request.setVisible (true);


    }

    public ArrayList<Request> getRequests () {
        return requests;
    }


    private class MouseHandler extends MouseAdapter
    {

        @Override
        public void mouseEntered (MouseEvent e) {

            e.getComponent ().setBackground (new Color (60, 62, 63, 255));

        }

        @Override
        public void mouseExited (MouseEvent e) {

            e.getComponent ().setBackground (new Color (45, 46, 42, 255));

        }

        @Override
        public void mouseClicked (MouseEvent e) {

            if (e.getButton () == MouseEvent.BUTTON1) {
                for (Request request : requests) {
                    if (request == e.getComponent ()) {
                        request.requestFocusInWindow ();
                        request.setChoseVisibly (true);
                    } else
                        request.setChoseVisibly (false);
                }
            } else if (e.getButton () == MouseEvent.BUTTON3) {
                for (Request request : requests) {
                    if (request == e.getComponent ()) {
                        request.getPopupMenu ().show (request, e.getX (),
                                e.getY ());
                    }
                }
            }
        }
    }


}
