import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class AddRequestDialog extends DialogPanel
{

    private JComboBox<String> requestType;
    private JButton create;
    private JTextField textField;
    private RequestType chosenRequestType;


    public AddRequestDialog (GUI gui) {
        super (gui);
        setSize (600, 200);
        addBasePanel ();
    }


    public void addBasePanel () {
        super.addBasePanel ();

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
        requestType.addItemListener (new ItemListener () {
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
        });


        JLabel label = new JLabel ("Name");
        label.setFont (new Font ("Arial",Font.ITALIC,14));

        create = new JButton ("Create");
        create.setBackground (Color.WHITE);
        create.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                repaint ();
                System.out.println (textField.getText ());
                getGui ().getFirstPanel ().addRequest (textField.getText ()
                        ,chosenRequestType);
            }
        });


        JLabel hint = new JLabel ("* Tip: paste Curl command into URL afterwards to" +
                " import it ");
        hint.setFont (new Font ("Arial",Font.ITALIC,9));



        getConstraints ().fill = GridBagConstraints.BOTH;
        getConstraints ().insets = new Insets (10,35,10,35);
        addComponent (label,0,0,1);
        getConstraints ().weightx = 0.5;
        getConstraints ().insets = new Insets (0,35,10,35);
        addComponent (textField,1,0,35);
        getConstraints ().weightx = 0.0;
        addComponent (requestType,1,35,4);
        getConstraints ().insets = new Insets (10,35,10,35);
        addComponent (new JSeparator (SwingConstants.HORIZONTAL),2,0,45);
        addComponent (hint,4,0,35);
        addComponent (create,4,35,4);
    }

}