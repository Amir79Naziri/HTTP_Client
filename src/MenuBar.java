import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;


public class MenuBar
{
    private JFrame baseFrame;
    private JMenuBar menuBar;
    private JMenuItem option;
    private JMenuItem exit;
    private JMenuItem toggleFullScreen;
    private JMenuItem toggleSideBar;
    private JMenuItem about;
    private JMenuItem help;


    public MenuBar (JFrame baseFrame)
    {
        if (baseFrame == null)
            throw new InputMismatchException ("ERROR : Wrong Input");

        menuBar = new JMenuBar ();
        this.baseFrame = baseFrame;
        baseFrame.setJMenuBar (menuBar);

        createApplicationMenu ();
        createViewMenu ();
        createHelpMenu ();
    }

    private void createApplicationMenu ()
    {
        ItemHandler itemHandler = new ItemHandler ();
        JMenu application = new JMenu ("Application");
        application.setMnemonic ('A');

        option = new JMenuItem ("Option");
        option.setMnemonic ('O');
        option.setAccelerator (KeyStroke.getKeyStroke ("control O"));
        option.addActionListener (itemHandler);

        exit = new JMenuItem ("Exit");
        exit.setMnemonic ('x');
        exit.setAccelerator (KeyStroke.getKeyStroke ("control E"));
        exit.addActionListener (itemHandler);

        application.add (option);
        application.addSeparator ();
        application.add (exit);
        menuBar.add (application);
    }

    private void createViewMenu ()
    {
        ItemHandler itemHandler = new ItemHandler ();
        JMenu view = new JMenu ("View");
        view.getPopupMenu ().setPreferredSize (new Dimension (200,65));
        view.setMnemonic ('V');

        toggleFullScreen = new JMenuItem ("Toggle Full Screen");
        toggleFullScreen.setMnemonic ('T');
        toggleFullScreen.setAccelerator (KeyStroke.getKeyStroke ("F11"));
        toggleFullScreen.addActionListener (itemHandler);

        toggleSideBar = new JMenuItem ("Toggle SideBar");
        toggleSideBar.setMnemonic ('S');
        toggleSideBar.setAccelerator (KeyStroke.getKeyStroke ("F12"));
        toggleSideBar.addActionListener (itemHandler);

        view.add (toggleFullScreen);
        view.add (toggleSideBar);
        menuBar.add (view);
    }

    private void createHelpMenu ()
    {
        ItemHandler itemHandler = new ItemHandler ();
        JMenu helpMenu = new JMenu ("Help");
        helpMenu.setMnemonic ('H');

        help = new JMenuItem ("Help");
        help.setMnemonic ('l');
        help.setAccelerator (KeyStroke.getKeyStroke ("F1"));
        help.addActionListener (itemHandler);

        about = new JMenuItem ("About");
        about.setMnemonic ('b');
        about.setAccelerator (KeyStroke.getKeyStroke ("control A"));
        about.addActionListener (itemHandler);

        helpMenu.add (help);
        helpMenu.addSeparator ();
        helpMenu.add (about);
        menuBar.add (helpMenu);
    }

    private class ItemHandler implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource () == about)
            {

                JOptionPane.showMessageDialog (baseFrame,
                        "Developer Information :\t\t\n" +
                                "Name : Amirreza Naziri\n" +
                                "ID : 9726081\n" +
                                "e-mail : naziriamirreza@gmail.com","About",
                        JOptionPane.PLAIN_MESSAGE);
            } else if (e.getSource () == help)
            {
                JOptionPane.showMessageDialog (baseFrame
                        , "help information", "Help",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
