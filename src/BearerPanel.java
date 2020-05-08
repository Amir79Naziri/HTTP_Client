import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.InputMismatchException;

public class BearerPanel extends JPanel
{
    private JTextField tokenText;
    private JTextField prefixText;
    private JCheckBox enabled;


    public BearerPanel ()
    {
        super();
        setLayout (new BorderLayout (15,5));
        addBaseComponents ();
        setBackground (new Color (40, 38, 37, 255));
    }

    private void addBaseComponents ()
    {
        GridBagConstraints constraints = new GridBagConstraints ();
        GridBagLayout layout = new GridBagLayout ();

        Color color = new Color (40, 38, 37, 255);
        JPanel basePanel = new JPanel (layout);
        basePanel.setBackground (color);


        JLabel token = new JLabel ("TOKEN ");
        token.setFont (new Font ("Arial",Font.PLAIN,10));
        token.setForeground (Color.GRAY);
        token.setBackground (color);

        JLabel prefix = new JLabel ("PREFIX ");
        prefix.setFont (new Font ("Arial",Font.PLAIN,10));
        prefix.setForeground (Color.GRAY);
        prefix.setBackground (color);

        ComponentHandler componentHandler = new ComponentHandler ();
        JPanel prefixPanel = new JPanel ();
        prefixText = new JTextField ("");
        KeyAndValue.createTextPanel (color,prefixPanel,prefixText);
        prefixText.setFont (new Font ("Arial",Font.PLAIN,12));
        prefixText.addMouseListener (componentHandler);

        JPanel tokenPanel = new JPanel ();
        tokenText = new JTextField ("");
        KeyAndValue.createTextPanel (color,tokenPanel,tokenText);
        tokenText.setFont (new Font ("Arial",Font.PLAIN,12));
        tokenText.addMouseListener (componentHandler);

        enabled = new JCheckBox ("ENABLED");
        enabled.setSelected (true);
        enabled.setBackground (color);
        enabled.setForeground (Color.GRAY);
        enabled.setFont (new Font ("Arial",Font.PLAIN,10));
        enabled.addItemListener (componentHandler);

        JLabel fake = new JLabel ("");
        fake.setBackground (color);

        constraints.insets = new Insets (7,15,7,15);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 20;
        addComponent (fake,0,0,21,constraints,layout,basePanel);

        addComponent (token,1,0,1,constraints,layout,basePanel);

        constraints.weightx = 0.5;
        addComponent (tokenPanel,1,1,20,constraints,layout,basePanel);

        addComponent (prefixPanel,2,1,20,constraints,layout,basePanel);

        constraints.weightx = 0.0;
        addComponent (prefix,2,0,1,constraints,layout,basePanel);

        addComponent (enabled,3,0,2,constraints,layout,basePanel);
        add(basePanel,BorderLayout.NORTH);

    }

    private void addComponent (JComponent component, int row, int col, int width,
                               GridBagConstraints constraints, GridBagLayout layout,
                               JPanel panel)
    {
        if (constraints == null || layout == null || panel == null)
            throw new InputMismatchException ("inValid input");
        constraints.gridx = col;
        constraints.gridy = row;
        constraints.gridwidth = width;
        constraints.gridheight = 1;
        layout.setConstraints (component,constraints);
        panel.add(component);
    }

    private class ComponentHandler extends MouseAdapter
        implements ItemListener {

        @Override
        public void itemStateChanged (ItemEvent e) {

            if (!enabled.isSelected ()) {
                prefixText.setForeground (Color.DARK_GRAY);
                tokenText.setForeground (Color.DARK_GRAY);


            } else {
                prefixText.setForeground (Color.GRAY);
               tokenText.setForeground (Color.GRAY);

            }
            repaint ();
        }

        @Override
        public void mouseEntered (MouseEvent e) {

            if (e.getComponent () instanceof JTextField && enabled.isSelected ()) {
                JTextField textField = (JTextField) (e.getComponent ());
                textField.setFont
                        (new Font ("Arial", Font.BOLD + Font.ITALIC, 12));
            }
        }

        @Override
        public void mouseExited (MouseEvent e) {
            if (e.getComponent () instanceof JTextField && enabled.isSelected ()) {
                JTextField textField = (JTextField) (e.getComponent ());
                textField.setFont (new Font ("Arial", Font.PLAIN, 12));
            }
        }
    }
}
