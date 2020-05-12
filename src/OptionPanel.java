import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * this class build for optionPanel
 *
 * @author Amir Naziri
 */
public class OptionPanel extends JPanel
{
    private  JCheckBox followRedirect; // follow Redirect panel
    private  JCheckBox hideInSystemTray; // hide is System tray
    private  JComboBox<String> themeChoose; // theme choose
    private OptionData data; // option data
    private GUI gui; // gui
    private boolean darkIsDefault; // true if dark theme is the first theme

    /**
     * creates a new optionPanel
     * @param optionData optionData
     * @param gui gui
     */
    public OptionPanel (OptionData optionData, GUI gui)
    {
        super();
        if (gui == null || optionData == null)
            throw new NullPointerException ("inValid input");
        this.gui = gui;
        setLayout (new BorderLayout (5, 5));
        setSize (400,200);
        data = optionData;
        addBasePanel ();
    }

    /**
     * create baseComponent in base Panel
     */
    public void addBasePanel ()
    {
        GridBagConstraints constraints = new GridBagConstraints ();
        GridBagLayout layout = new GridBagLayout ();
        ComponentHandler componentHandler = new ComponentHandler ();
        JPanel basePanel = new JPanel ();
        basePanel.setLayout (layout);
        basePanel.setBorder (new EmptyBorder (5, 5, 5, 5));
        add(basePanel);


        followRedirect = new JCheckBox ("Follow Redirect");
        followRedirect.setBackground (Color.WHITE);
        followRedirect.addItemListener (componentHandler);
        if (data.isFollowRedirect ())
            followRedirect.setSelected (true);

        hideInSystemTray = new JCheckBox ("Hide in System Tray");
        hideInSystemTray.setBackground (Color.WHITE);
        hideInSystemTray.addItemListener (componentHandler);
        if (data.isHideInSystemTray ())
            hideInSystemTray.setSelected (true);


        JLabel label = new JLabel ("Theme :  ");

        String[] themes = {"Dark", "White"};
        String[] themes2 = {"White", "Dark"};
        if (data.getTheme () == Theme.DARK)
        {
            themeChoose = new JComboBox<> (themes);
            darkIsDefault = true;

        }
        else
        {
            themeChoose = new JComboBox<> (themes2);
            darkIsDefault = false;
        }
        themeChoose.addItemListener (componentHandler);
        themeChoose.setBackground (Color.WHITE);

        constraints.insets = new Insets (15,15,0,20);
        constraints.fill = GridBagConstraints.BOTH;
        GridBagAdder.addComponent (followRedirect,0,0,2,layout,constraints,
                basePanel);
        GridBagAdder.addComponent (hideInSystemTray,0,2,2,layout,constraints,
                basePanel);
        GridBagAdder.addComponent (new JSeparator (),1,0,4,layout,constraints,
                basePanel);
        GridBagAdder.addComponent (label,2,0,1,layout,constraints,
                basePanel);
        constraints.ipadx = 40;
        GridBagAdder.addComponent (themeChoose,2,1,2,layout,constraints,
                basePanel);
    }

    /**
     * class for componentHandling
     */
    private class ComponentHandler implements ItemListener
    {
        @Override
        public void itemStateChanged (ItemEvent e) {
            if (e.getSource () == hideInSystemTray && hideInSystemTray.isSelected ())
            {
                data.setHideInSystemTray (true);

            }
            else if (e.getSource () == hideInSystemTray && !(hideInSystemTray.isSelected ()))
            {
                data.setHideInSystemTray (false);

            } else if (e.getSource () == followRedirect && followRedirect.isSelected ())
            {
                data.setFollowRedirect (true);
                System.out.println (data.isFollowRedirect ());
            } else if (e.getSource () == followRedirect && !(followRedirect.isSelected ()))
            {
                data.setFollowRedirect (false);

            } else if (e.getSource () == themeChoose)
            {
                if (e.getStateChange () == ItemEvent.SELECTED)
                {
                    JOptionPane.showMessageDialog (gui.getBaseFrame (),"You should" +
                            " restart program to see result.","Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
                if (darkIsDefault)
                {
                    if (themeChoose.getSelectedIndex () == 0)
                        data.setTheme (Theme.DARK);
                    else
                        data.setTheme (Theme.WHITE);
                } else
                {
                    if (themeChoose.getSelectedIndex () == 0)
                        data.setTheme (Theme.WHITE);
                    else
                        data.setTheme (Theme.DARK);
                }
            }
        }
    }
}
