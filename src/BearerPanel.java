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
    private Theme theme;

    /**
     * creates a BearerPanel
     */
    public BearerPanel (Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        setLayout (new BorderLayout (15,5));
        addBaseComponents ();
        setBackground (theme.getBackGroundColorV4 ());
    }

    /**
     * create basePanel which has component init
     */
    private void addBaseComponents ()
    {
        GridBagConstraints constraints = new GridBagConstraints ();
        GridBagLayout layout = new GridBagLayout ();

        JPanel basePanel = new JPanel (layout);
        basePanel.setBackground (theme.getBackGroundColorV4 ());


        JLabel token = new JLabel ("TOKEN ");
        token.setFont (new Font ("Arial",Font.PLAIN,10));
        token.setForeground (theme.getForeGroundColorV2 ());
        token.setBackground (theme.getBackGroundColorV4 ());

        JLabel prefix = new JLabel ("PREFIX ");
        prefix.setFont (new Font ("Arial",Font.PLAIN,10));
        prefix.setForeground (theme.getForeGroundColorV2 ());
        prefix.setBackground (theme.getBackGroundColorV4 ());
        prefix.setToolTipText ("Prefix to use when sending the Authorization \n\t\t" +
                "header.Default to Bearer");

        ComponentHandler componentHandler = new ComponentHandler ();
        JPanel prefixPanel = new JPanel ();
        prefixText = new JTextField ("");
        KeyAndValue.createTextPanel (prefixPanel,prefixText,theme);
        prefixText.setFont (new Font ("Arial",Font.PLAIN,12));
        prefixText.addMouseListener (componentHandler);

        JPanel tokenPanel = new JPanel ();
        tokenText = new JTextField ("");
        KeyAndValue.createTextPanel (tokenPanel,tokenText,theme);
        tokenText.setFont (new Font ("Arial",Font.PLAIN,12));
        tokenText.addMouseListener (componentHandler);

        enabled = new JCheckBox ("ENABLED");
        enabled.setSelected (true);
        enabled.setBackground (theme.getBackGroundColorV4 ());
        enabled.setForeground (theme.getForeGroundColorV2 ());
        enabled.setFont (new Font ("Arial",Font.PLAIN,10));
        enabled.addItemListener (componentHandler);

        JLabel fake = new JLabel ("");
        fake.setBackground (theme.getBackGroundColorV4 ());

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
                prefixText.setForeground (theme.getForeGroundColorV3 ());
                tokenText.setForeground (theme.getForeGroundColorV3 ());


            } else {
                prefixText.setForeground (theme.getForeGroundColorV2 ());
               tokenText.setForeground (theme.getForeGroundColorV2 ());

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
