import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * this class represents Bearer panel in body types in second Panel
 *
 * @author Amir Naziri
 */
public class BearerPanel extends JPanel
{
    private JTextField tokenText; // token text
    private JTextField prefixText; // prefix text
    private JCheckBox enabled; // enable button

    /**
     * creates a BearerPanel
     */
    public BearerPanel ()
    {
        super();
        setLayout (new BorderLayout (15,5));
        addBaseComponents ();
        setBackground (new Color (40, 38, 37, 255));
    }

    /**
     * create basePanel which has component init
     */
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
        GridBagAdder.addComponent (fake,0,0,21,layout,constraints,basePanel);

        GridBagAdder.addComponent (token,1,0,1,layout,constraints,basePanel);

        constraints.weightx = 0.5;
        GridBagAdder.addComponent
                (tokenPanel,1,1,20,layout,constraints,basePanel);

        GridBagAdder.addComponent
                (prefixPanel,2,1,20,layout,constraints,basePanel);

        constraints.weightx = 0.0;
        GridBagAdder.addComponent
                (prefix,2,0,1,layout,constraints,basePanel);

        GridBagAdder.addComponent
                (enabled,3,0,2,layout,constraints,basePanel);
        add(basePanel,BorderLayout.NORTH);

    }

    /**
     * this class handles all components in this panel
     */
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
