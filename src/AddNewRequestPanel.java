import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.InputMismatchException;


public class AddNewRequestPanel extends JPanel
{

    private JComboBox<String> requestType;
    private JButton create;
    private JTextField textField;
    private RequestType chosenRequestType;
    private GUI gui;


    public AddNewRequestPanel (GUI gui) {
        super ();
        this.gui = gui;
        setLayout (new BorderLayout (5, 5));
        setSize (600, 200);
        addBasePanel ();
    }


    public void addBasePanel () {
        ComponentHandler componentHandler = new ComponentHandler ();
        GridBagLayout layout = new GridBagLayout ();
        GridBagConstraints constraints = new GridBagConstraints ();
        JPanel basePanel = new JPanel ();
        basePanel.setBorder (new EmptyBorder (5, 5, 5, 5));
        basePanel.setLayout (layout);
        add(basePanel);

        chosenRequestType = RequestType.GET;
        textField = new JTextField ("My Request");
        textField.setBackground (Color.WHITE);
        textField.setBorder (BorderFactory.createCompoundBorder (new LineBorder (Color.GRAY,
                1,true),
                new EmptyBorder (5,5,5,5)));
        textField.setBackground (Color.WHITE);
        textField.setFont (new Font ("Arial",Font.PLAIN,14));

        String[] type = {"GET", "POST", "PUT", "PATCH", "DELETE"};
        requestType = new JComboBox<> (type);
        requestType.setBackground (Color.WHITE);
        requestType.addItemListener (componentHandler);


        JLabel label = new JLabel ("Name");
        label.setFont (new Font ("Arial",Font.ITALIC,14));

        create = new JButton ("Create");
        create.setBackground (Color.WHITE);
        create.addActionListener (componentHandler);

        JLabel hint = new JLabel ("* Tip: paste Curl command into URL afterwards to" +
                " import it ");
        hint.setFont (new Font ("Arial",Font.ITALIC,9));



        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets (10,35,10,35);
        addComponent (label,0,0,1,layout,constraints,basePanel);
        constraints.weightx = 0.5;
        constraints.insets = new Insets (0,35,10,35);
        addComponent (textField,1,0,35,layout,constraints,basePanel);
        constraints.weightx = 0.0;
        addComponent (requestType,1,35,4,layout,constraints,basePanel);
        constraints.insets = new Insets (10,35,10,35);
        addComponent (new JSeparator (SwingConstants.HORIZONTAL),2,0,45
                ,layout,constraints,basePanel);
        addComponent (hint,4,0,35,layout,constraints,basePanel);
        addComponent (create,4,35,4,layout,constraints,basePanel);
    }

    public static void addComponent (JComponent component, int row, int col, int width,
                              GridBagLayout layout, GridBagConstraints constraints,
                              JPanel panel) {
        if (layout == null || constraints  == null)
            throw new InputMismatchException ("inValid input");
        constraints.gridy = row;
        constraints.gridx = col;
        constraints.gridheight = 1;
        constraints.gridwidth = width;

        layout.setConstraints (component, constraints);
        panel.add (component);
    }



    private class ComponentHandler implements ActionListener, ItemListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {
           if (e.getSource () == create)
           {
               gui.getFirstPanel ().addRequest (textField.getText ()
                       ,chosenRequestType);
           }
        }

        @Override
        public void itemStateChanged (ItemEvent e) {
            switch (requestType.getSelectedIndex ()) {
                case 0:
                    chosenRequestType = RequestType.GET;
                    break;
                case 1:
                    chosenRequestType = RequestType.POST;
                    break;
                case 2:
                    chosenRequestType = RequestType.PUT;
                    break;
                case 3:
                    chosenRequestType = RequestType.PATCH;
                    break;
                case 4:
                    chosenRequestType = RequestType.DELETE;
                    break;
            }
        }
    }
}