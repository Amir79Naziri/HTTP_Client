package GUI;

import Client.ClientRequest;
import Client.RequestType;
import ControlUnit.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * this class represents requestPanel
 *
 * @author Amir Naziri
 */
public class RequestsPanel extends JPanel
{
    private ArrayList<RequestGui> requestGuis; // list of requestGuis
    private GUI gui; // gui
    private Theme theme; // theme

    /**
     * creates a new requestPanel
     * @param gui gui
     * @param theme theme
     * @param clientRequests clientRequests
     */
    protected RequestsPanel (GUI gui, Theme theme, ArrayList<ClientRequest> clientRequests)
    {
        super();
        if (theme == null || gui == null)
            throw new NullPointerException ("inValid input");
        this.gui = gui;
        this.theme = theme;
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        setBackground (theme.getBackGroundColorV2 ());
        requestGuis = new ArrayList<> ();

        if (clientRequests != null)
            for (ClientRequest clientRequest : clientRequests)
                addRequest (clientRequest.getRequestType (),
                        clientRequest.getName (),clientRequest);
    }

    /**
     * adds new request
     * @param type type of request
     * @param name name of request
     */
    protected void addNewRequest (RequestType type, String name)
    {
        if (type == null || name == null)
            throw new NullPointerException ("inValid input");
        ClientRequest clientRequest = new ClientRequest (gui.getOptionData ().isFollowRedirect (),
                name,type);
        RequestGui requestGui = new RequestGui (type,name,clientRequest,gui,theme);
        Controller.addNewClientRequest (clientRequest);
        MouseHandler mouseHandler = new MouseHandler ();
        requestGui.addMouseListener (mouseHandler);
        requestGuis.add (requestGui);
        add(requestGui);
        requestGui.setVisible (false);
        requestGui.setVisible (true);
    }

    /**
     * add request (for loading)
     * @param type type
     * @param name name
     * @param clientRequest clientRequest
     */
    private void addRequest (RequestType type, String name, ClientRequest clientRequest)
    {
        if (type == null || name == null)
            throw new NullPointerException ("inValid input");

        RequestGui requestGui = new RequestGui (type,name,clientRequest,gui,theme);
        MouseHandler mouseHandler = new MouseHandler ();
        requestGui.addMouseListener (mouseHandler);
        requestGuis.add (requestGui);
        add(requestGui);
        requestGui.setVisible (false);
        requestGui.setVisible (true);
    }

    /**
     * @return list of requestGuis
     */
    protected ArrayList<RequestGui> getRequestGuis () {
        return requestGuis;
    }

    /**
     * this class handles mouse for components in this panel
     */
    private class MouseHandler extends MouseAdapter
    {

        @Override
        public void mouseEntered (MouseEvent e) {

            e.getComponent ().setBackground (theme.getBackGroundColorV3 ());

        }

        @Override
        public void mouseExited (MouseEvent e) {

            e.getComponent ().setBackground (theme.getBackGroundColorV2 ());

        }

        @Override
        public void mouseClicked (MouseEvent e) {

            if (e.getButton () == MouseEvent.BUTTON1)
            {
                for (RequestGui requestGui : requestGuis) {
                    if (requestGui == e.getComponent ()) {
                        requestGui.setChoseVisibly (true);
                        requestGui.getNameLabel ().setFont (
                                new Font ("Arial",Font.BOLD,13));
                        gui.setSecondPanel (requestGui.getSecondPanel ());
                        gui.setThirdPanel (requestGui.getSecondPanel ().getProgramThirdPanel ());

                    } else
                    {
                        requestGui.getNameLabel ().setFont (new Font
                                ("Arial",Font.PLAIN,13));
                        requestGui.setChoseVisibly (false);
                    }
                }
            }
            else if (e.getButton () == MouseEvent.BUTTON3) {
                for (RequestGui requestGui : requestGuis) {
                    if (requestGui == e.getComponent ()) {
                        requestGui.getPopupMenu ().show (requestGui, e.getX (),
                                e.getY ());
                    }
                }
            }
        }
    }

    /**
     * save headers, bodies, ... for closing app
     */
    public void properRequestsForClosing ()
    {
        for (RequestGui requestGui : requestGuis)
            requestGui.getSecondPanel ().initializeForSend (false);
    }
}
