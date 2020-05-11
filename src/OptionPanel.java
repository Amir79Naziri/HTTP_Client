import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.InputMismatchException;


public class OptionPanel extends JPanel
{
    private transient JCheckBox followRedirect;
    private transient JCheckBox hideInSystemTray;
    private transient JComboBox<String> themeChoose;

    OptionData data;


    public OptionPanel (OptionData optionData)
    {
        super();
        if (optionData == null)
            throw new InputMismatchException ("inValid input");
        setLayout (new BorderLayout (5, 5));
        setSize (400,200);
        data = optionData;
        addBasePanel ();
    }



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
        themeChoose = new JComboBox<> (themes);
        themeChoose.addItemListener (componentHandler);
        themeChoose.setBackground (Color.WHITE);
        if (data.getTheme () == 0)
            themeChoose.setSelectedIndex (0);
        else
            themeChoose.setSelectedIndex (1);



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


    private class ComponentHandler implements ItemListener
    {

        @Override
        public void itemStateChanged (ItemEvent e) {
            if (e.getSource () == hideInSystemTray && hideInSystemTray.isSelected ())
            {
                data.setHideInSystemTray (true);
                System.out.println (data.isHideInSystemTray ());
            }
            else if (e.getSource () == hideInSystemTray && !(hideInSystemTray.isSelected ()))
            {
                data.setHideInSystemTray (false);
                System.out.println (data.isHideInSystemTray ());
            } else if (e.getSource () == followRedirect && followRedirect.isSelected ())
            {
                data.setFollowRedirect (true);
                System.out.println (data.isFollowRedirect ());
            } else if (e.getSource () == followRedirect && !(followRedirect.isSelected ()))
            {
                data.setFollowRedirect (false);
                System.out.println (data.isFollowRedirect ());
            } else if (e.getSource () == themeChoose)
            {
                if (themeChoose.getSelectedIndex () == 0)
                    data.setTheme (0);
                else
                    data.setTheme (1);
                System.out.println (data.getTheme ());
            }

        }
    }
}
