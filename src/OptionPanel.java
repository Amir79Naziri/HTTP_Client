import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;


public class OptionPanel extends JPanel
{
    private JCheckBox followRedirect;
    private JCheckBox hideInSystemTray;
    private JComboBox<String> themeChoose;
    private GUI gui;


    public OptionPanel (GUI gui)
    {
        super();
        this.gui = gui;
        setLayout (new BorderLayout (5, 5));
        setSize (400,200);
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

        hideInSystemTray = new JCheckBox ("Hide in System Tray");
        hideInSystemTray.setBackground (Color.WHITE);
        hideInSystemTray.addItemListener (componentHandler);

        JLabel label = new JLabel ("Theme :  ");

        String[] themes = {"Dark", "White"};
        themeChoose = new JComboBox<> (themes);
        themeChoose.setBackground (Color.WHITE);


        constraints.insets = new Insets (15,15,0,20);
        constraints.fill = GridBagConstraints.BOTH;
        AddNewRequestPanel.addComponent (followRedirect,0,0,2,layout,constraints,
                basePanel);
        AddNewRequestPanel.addComponent (hideInSystemTray,0,2,2,layout,constraints,
                basePanel);
        AddNewRequestPanel.addComponent (new JSeparator (),1,0,4,layout,constraints,
                basePanel);
        AddNewRequestPanel.addComponent (label,2,0,1,layout,constraints,
                basePanel);
        constraints.ipadx = 40;
        AddNewRequestPanel.addComponent (themeChoose,2,1,2,layout,constraints,
                basePanel);
    }


    private class ComponentHandler implements ActionListener, ItemListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {

        }

        @Override
        public void itemStateChanged (ItemEvent e) {
            if (e.getSource () == hideInSystemTray && hideInSystemTray.isSelected ())
            {
                gui.setShouldHideInSystemTray (true);
            }
            else if (e.getSource () == hideInSystemTray && !(hideInSystemTray.isSelected ()))
            {
                gui.setShouldHideInSystemTray (false);
            }
        }
    }
}
