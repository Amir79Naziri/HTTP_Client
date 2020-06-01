package GUI;

import Client.RequestType;
import ControlUnit.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * this class represents firstPanel in frame
 *
 * @author Amir Naziri
 */
public class FirstPanel extends JPanel
{

    private Theme theme; // theme
    private JButton addRequest; // add requestButton
    private JTextField searchText; // filter text
    private RequestsPanel requestsPanel; // request's panel
    private GUI gui; // gui

    /**
     * creates a new first class
     * @param gui gui
     * @param theme theme
     */
    public FirstPanel (GUI gui, Theme theme)
    {
        super();
        if (gui == null || theme == null)
            throw new NullPointerException ("inValid input");
        this.gui = gui;
        this.theme = theme;
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        createHeaderPanel ();
        createFilterPanel ();
        createRequestsPanel (gui);
    }

    /**
     * creates headerPanel
     */
    private void createHeaderPanel ()
    {
        JPanel header = new JPanel (new BorderLayout (3, 4));
        header.setPreferredSize (new Dimension (300,55));
        header.setMinimumSize ((new Dimension (200,55)));
        header.setMaximumSize ((new Dimension (1100,55)));

        JLabel title = new JLabel ("  HTTP client");
        title.setFont (new Font ("Arial",Font.PLAIN,20));
        title.setForeground (theme.getForeGroundColorV1 ());

        header.setBackground (theme.getBackGroundColorV1 ());
        header.add (title,BorderLayout.WEST);
        add(header);
    }

    /**
     * creates filter panel
     */
    private void createFilterPanel ()
    {
        ComponentHandler componentHandler = new ComponentHandler ();

        JPanel filterPanel = new JPanel ();
        filterPanel.setMaximumSize (new Dimension (1100,55));
        filterPanel.setPreferredSize (new Dimension (350,55));
        filterPanel.setMinimumSize (new Dimension (250,55));
        GridBagLayout layout = new GridBagLayout ();
        GridBagConstraints constraints = new GridBagConstraints ();
        filterPanel.setBackground (theme.getBackGroundColorV2 ());
        filterPanel.setLayout (layout);
        add(filterPanel);

        searchText = new JTextField ("filter");
        searchText.setFont (new Font ("Arial",Font.PLAIN,15));
        searchText.addKeyListener (componentHandler);
        searchText.setBackground (theme.getBackGroundColorV2 ());
        searchText.setForeground (theme.getForeGroundColorV1 ());
        searchText.setBorder (BorderFactory.createCompoundBorder (
                new LineBorder (Color.LIGHT_GRAY,1,true),
                new EmptyBorder (1,5,1,5)));

        addRequest = new JButton ();
        addRequest.setIcon (theme.getAddR1 ());
        addRequest.setRolloverIcon (theme.getAddR2 ());
        addRequest.setRolloverEnabled (true);
        addRequest.setBackground (theme.getBackGroundColorV2 ());
        addRequest.addActionListener (componentHandler);
        addRequest.setFocusable (false);
        addRequest.setToolTipText ("Adds New GUI.Request");


        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        constraints.weightx = 0.5;
        constraints.insets = new Insets (5,5,5,5);
        constraints.gridwidth = 100;
        layout.setConstraints (searchText,constraints);
        filterPanel.add (searchText);

        constraints.gridx = 100;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.0;
        constraints.ipady = -19;
        constraints.ipadx = -32;

        layout.setConstraints (addRequest,constraints);
        filterPanel.add(addRequest);
    }

    /**
     * creates requests panel
     * @param gui gui
     */
    private void createRequestsPanel (GUI gui)
    {
        if (gui == null)
            throw new NullPointerException ("inValid input");
        requestsPanel = new RequestsPanel (gui,theme, Controller.clientRequests ());
        JScrollPane scrollPane = new JScrollPane
                (requestsPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAutoscrolls (true);
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        scrollPane.setBorder (new LineBorder (theme.getBackGroundColorV2 (),1));
        add (scrollPane);

    }

    /**
     * add a new request
     * @param name name of request
     * @param type type of request
     */
    public void addRequest (String name, RequestType type)
    {
        if (name == null || type == null)
            throw new NullPointerException ("inValid input");
        requestsPanel.
                addNewRequest (type,name);
    }

    /**
     * filters list by given string
     * @param filter given String
     */
    private void doFilter (String filter)
    {
        if (filter == null)
            return;
        String l = filter.toLowerCase ().trim ().replaceAll ("\\s+","");

        for (Request request : requestsPanel.getRequests ())
        {
            if (!(request.isDeleted ()))
                request.setVisible (true);
        }
        for (Request request : requestsPanel.getRequests ())
        {
            String a = request.getRequestType ().getMinimizedName ().toLowerCase ().trim ().
                    replaceAll ("\\s+","") +
                    "" +request.getRequestType ().toString ().toLowerCase ().trim ().
                    replaceAll ("\\s+","")
                    + "" +
                    request.getNameLabel ().getText ().toLowerCase ().trim ().
                            replaceAll ("\\s+","");

                    if (!(a.contains (l)))
                        request.setVisible (false);
        }
    }

    /**
     * class for button Handling
     */
    private class ComponentHandler extends KeyAdapter
            implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {

            if (e.getSource () == addRequest)
            {
                AddNewRequestPanel addNewRequestPanel = new AddNewRequestPanel ();
                int res =
                        JOptionPane.showOptionDialog (gui.getBaseFrame (),
                        addNewRequestPanel,"New Request",
                        JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,
                        null,null);
                if (res == 0) {
                    gui.getFirstPanel ().addRequest
                            (addNewRequestPanel.getNameOfNewRequest (),
                                    addNewRequestPanel.getChosenRequestType ()
                            );

                }
            }
        }

        @Override
        public void keyReleased (KeyEvent e) {
            if (e.getKeyCode () != KeyEvent.VK_ENTER)
                doFilter (searchText.getText ());
        }
    }

    public RequestsPanel getRequestsPanel () {
        return requestsPanel;
    }
}
