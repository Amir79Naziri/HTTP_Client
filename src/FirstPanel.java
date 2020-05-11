import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstPanel extends JPanel
{

    private JPanel header;
    private JLabel title;
    private JPanel filterPanel;
    private Theme theme;



    private JButton addRequest;
    private JTextField searchText;
    private RequestsPanel requestsPanel;
    private GUI gui;

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

    private void createHeaderPanel ()
    {
        header = new JPanel (new BorderLayout (3,4));
        header.setPreferredSize (new Dimension (300,55));
        header.setMinimumSize ((new Dimension (300,55)));
        header.setMaximumSize ((new Dimension (850,55)));

        title = new JLabel ("  HTTP client");
        title.setFont (new Font ("Arial",Font.PLAIN,20));
        title.setForeground (theme.getForeGroundColorV1 ());

        header.setBackground (theme.getBackGroundColorV1 ());
        header.add (title,BorderLayout.WEST);
        add(header);
    }

    private void createFilterPanel ()
    {
        ButtonHandler buttonHandler = new ButtonHandler ();

        filterPanel = new JPanel ();
        filterPanel.setMaximumSize (new Dimension (850,55));
        filterPanel.setMinimumSize (new Dimension (350,55));
        GridBagLayout layout = new GridBagLayout ();
        GridBagConstraints constraints = new GridBagConstraints ();
        filterPanel.setBackground (theme.getBackGroundColorV2 ());
        filterPanel.setLayout (layout);
        add(filterPanel);

        searchText = new JTextField ("filter");
        searchText.addActionListener (new ButtonHandler ());
        searchText.setFont (new Font ("Arial",Font.PLAIN,15));
        searchText.setBackground (theme.getBackGroundColorV2 ());
        searchText.setForeground (theme.getForeGroundColorV1 ());
        searchText.setBorder (BorderFactory.createCompoundBorder (
                new LineBorder (Color.LIGHT_GRAY,1,true),
                new EmptyBorder (1,5,1,5)));

        addRequest = new JButton ();
        addRequest.setIcon (new ImageIcon ("./images/addR1.png"));
        addRequest.setRolloverIcon (new ImageIcon ("./images/addR2.png"));
        addRequest.setRolloverEnabled (true);
        addRequest.setBackground (theme.getBackGroundColorV2 ());
        addRequest.addActionListener (buttonHandler);


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
        constraints.ipady = -15;
        constraints.ipadx = -30;

        layout.setConstraints (addRequest,constraints);
        filterPanel.add(addRequest);
    }

    private void createRequestsPanel (GUI gui)
    {
        if (gui == null)
            throw new NullPointerException ("inValid input");
        requestsPanel = new RequestsPanel (gui,theme);
        JScrollPane scrollPane = new JScrollPane
                (requestsPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAutoscrolls (true);
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        scrollPane.setBorder (new LineBorder (theme.getBackGroundColorV2 (),1));
        add (scrollPane);

    }

    public void addRequest (String name, RequestType type)
    {
        if (name == null || type == null)
            throw new NullPointerException ("inValid input");
        requestsPanel.
                addNewRequest (type,name);
    }

    public void changeTheme ()
    {
        title.setForeground (theme.getForeGroundColorV1 ());
        header.setBackground (theme.getBackGroundColorV1 ());
    }

    private class ButtonHandler implements ActionListener
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
            } else if (e.getSource () == searchText)
            {
                System.out.println (searchText.getText ());
            }
        }
    }
}
