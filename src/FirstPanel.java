import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstPanel extends JPanel
{

    private JButton addRequest;
    private JTextField searchText;
    private RequestsPanel requestsPanel;
    private GUI gui;

    public FirstPanel (GUI gui)
    {
        super();
        this.gui = gui;
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        createHeaderPanel ();
        createFilterPanel ();
        createRequestsPanel (gui);
    }

    private void createHeaderPanel ()
    {
        Color color = new Color (122, 103,218);
        JPanel header = new JPanel (new BorderLayout (3,4));
        header.setPreferredSize (new Dimension (300,55));
        header.setMinimumSize ((new Dimension (300,55)));
        header.setMaximumSize ((new Dimension (850,55)));

        JLabel label = new JLabel ("  HTTP client");
        label.setFont (new Font ("Arial",Font.PLAIN,20));
        label.setForeground (Color.WHITE);

        header.setBackground (color);
        header.add (label,BorderLayout.WEST);
        add(header);
    }

    private void createFilterPanel ()
    {
        ButtonHandler buttonHandler = new ButtonHandler ();
        Color color = new Color (45, 46, 42, 255);
        JPanel filterPanel = new JPanel ();
        filterPanel.setMaximumSize (new Dimension (850,55));
        filterPanel.setMinimumSize (new Dimension (350,55));
        GridBagLayout layout = new GridBagLayout ();
        GridBagConstraints constraints = new GridBagConstraints ();
        filterPanel.setBackground (color);
        filterPanel.setLayout (layout);
        add(filterPanel);

        searchText = new JTextField ("filter");
        searchText.setFont (new Font ("Arial",Font.PLAIN,15));
        searchText.setBackground (color);
        searchText.setForeground (Color.LIGHT_GRAY);
        searchText.setBorder (BorderFactory.createCompoundBorder (
                new LineBorder (Color.GRAY,1,true),
                new EmptyBorder (1,5,1,5)));

        addRequest = new JButton ();
        addRequest.setIcon (new ImageIcon ("./images/addR1.png"));
        addRequest.setRolloverIcon (new ImageIcon ("./images/addR2.png"));
        addRequest.setRolloverEnabled (true);
        addRequest.setBackground (color);
        addRequest.addActionListener (buttonHandler);


        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 100;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        constraints.weightx = 0.5;
        constraints.insets = new Insets (5,5,5,5);
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
        requestsPanel = new RequestsPanel (gui);
        JScrollPane scrollPane = new JScrollPane
                (requestsPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAutoscrolls (true);
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        scrollPane.setBorder (new LineBorder (new Color (16, 22, 30, 208),1));
        add (scrollPane);

    }

    public void addRequest (String name, RequestType type)
    {
        requestsPanel.addNewRequest (type,name);
    }


    private class ButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {

            if (e.getSource () == addRequest)
            {
                JOptionPane.showMessageDialog (gui.getBaseFrame (),
                        new AddRequestDialog (gui),"New Request",
                        JOptionPane.PLAIN_MESSAGE);


            } else if (e.getSource () == searchText)
            {
                // add filter to list
            }
        }
    }
}
