import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class OptionDialog extends DialogPanel
{
    private JCheckBox followRedirect;
    private JCheckBox hideInSystemTray;
    private JComboBox<String> themeChoose;


    public OptionDialog ()
    {
        super();
        setSize (400,200);
    }



    public void addBasePanel ()
    {
        super.addBasePanel ();
        followRedirect = new JCheckBox ("Follow Redirect");
        followRedirect.setBackground (Color.WHITE);

        hideInSystemTray = new JCheckBox ("Hide in System Tray");
        hideInSystemTray.setBackground (Color.WHITE);

        JLabel label = new JLabel ("Theme :  ");

        String[] themes = {"Dark", "White"};
        themeChoose = new JComboBox<> (themes);
        themeChoose.setBackground (Color.WHITE);



        JSeparator separator = new JSeparator ();

        getConstraints ().insets = new Insets (15,15,0,20);
        getConstraints ().fill = GridBagConstraints.BOTH;
        addComponent (followRedirect,0,0,2);
        addComponent (hideInSystemTray,0,2,2);
        addComponent (separator,1,0,4);
        addComponent (label,2,0,1);
        getConstraints ().ipadx = 40;
        addComponent (themeChoose,2,1,2);
    }


    private class ComponentHandler implements ActionListener, ItemListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {

        }

        @Override
        public void itemStateChanged (ItemEvent e) {

        }
    }
}
