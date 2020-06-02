package GUI;

import Client.ClientRequest;
import Client.RequestType;
import ControlUnit.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class represents a request in firstPanel
 *
 * @author Amir Naziri
 */
public class Request extends JPanel
{
    private JPanel chose; // chose mark
    private final SecondPanel secondPanel; // secondPanel belong to request
    private JPopupMenu popupMenu; // popUpMenu for delete or rename
    private RequestType requestType; // requestType
    private boolean isDeleted; // is Deleted
    private JLabel typeLabel; // type label
    private JLabel nameLabel; // name label
    private ClientRequest clientRequest;


    /**
     * create a new request
     * @param requestType request type
     * @param name name of request
     * @param clientRequest index of request
     * @param gui gui
     * @param theme theme
     */
    public Request (RequestType requestType, String name, ClientRequest clientRequest
            , GUI gui, Theme theme)
    {
        super();
        if (name == null || requestType == null || gui == null || theme == null)
            throw new NullPointerException ("inValid input");
        isDeleted = false;
        this.requestType = requestType;
        this.clientRequest = clientRequest;
        setBackground (theme.getBackGroundColorV2 ());
        setLayout (new FlowLayout (FlowLayout.LEFT));

        chose = new JPanel ();
        chose.setBackground (new Color (122, 103,218));
        chose.setPreferredSize (new Dimension (2,29));
        chose.setVisible (false);

        typeLabel = new JLabel (requestType.getMinimizedName ());
        secondPanel = new SecondPanel (gui,this,theme);
        typeLabel.setFont (new Font ("Arial",Font.PLAIN,10));
        typeLabel.setForeground (requestType.getColor ());
        typeLabel.setPreferredSize (new Dimension (40,29));

        if (name.toCharArray ().length > 15)
            name = name.substring (0,15);
        nameLabel = new JLabel (name + " ");
        nameLabel.setFont (new Font ("Arial",Font.PLAIN,13));
        nameLabel.setForeground (Color.GRAY);
        nameLabel.setBackground (theme.getBackGroundColorV2 ());
        JMenuItem delete = new JMenuItem ("Delete");
        delete.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                setVisible (false);
                delete();
                if (chose.isVisible ())
                {
                    gui.setThirdPanel (new NullPanel (2,theme));
                    gui.setSecondPanel (new NullPanel (1,theme));
                }
                Controller.removeClientRequest (clientRequest);
            }
        });

        JMenuItem rename = new JMenuItem ("Rename");
        rename.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {

                String name =
                        JOptionPane.showInputDialog (gui.getBaseFrame (),"New Name ",
                                nameLabel.getText ());
                if (name.toCharArray ().length > 15)
                    name = name.substring (0,15);
                nameLabel.setText (name);
                clientRequest.setName (name);
            }
        });
        popupMenu = new JPopupMenu ();
        popupMenu.add(rename);
        popupMenu.addSeparator ();
        popupMenu.add (delete);

        add (chose);
        add (typeLabel);
        add (nameLabel);
    }

    /**
     * set a type for request
     * @param requestType request type
     */
    public void setRequestType (RequestType requestType) {
        if (requestType == null)
            throw new NullPointerException ("inValid input");
        this.requestType = requestType;
        typeLabel.setText (requestType.getMinimizedName ());
        typeLabel.setForeground (requestType.getColor ());
        setVisible (false);
        setVisible (true);
    }

    /**
     * is request deleted
     * @return isDeleted
     */
    public boolean isDeleted () {
        return isDeleted;
    }

    /**
     * setIsDelete true
     */
    public void delete () {
        isDeleted = true;
    }

    /**
     * @return popUpMenu
     */
    public JPopupMenu getPopupMenu () {
        return popupMenu;
    }

    /**
     * set chose panel visibility
     * @param visibly visibly
     */
    public void setChoseVisibly (boolean visibly) {
        chose.setVisible (visibly);
    }

    @Override
    public Dimension getMinimumSize () {
        return new Dimension (350,38);
    }

    @Override
    public Dimension getMaximumSize () {
        return new Dimension (1100,38);
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension (350,38);
    }

    /**
     * is requestSelected
     * @return is Selected
     */
    public boolean isSelected ()
    {
        return chose.isVisible ();
    }

    /**
     * @return getSecond panel
     */
    public SecondPanel getSecondPanel () {
        return secondPanel;
    }

    /**
     * @return name Label
     */
    public JLabel getNameLabel () {
        return nameLabel;
    }

    /**
     * gets requestType
     * @return request type
     */
    public RequestType getRequestType () {
        return requestType;
    }


    public ClientRequest getClientRequest () {
        return clientRequest;
    }


}
