import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.InputMismatchException;

public class GUI
{

    private JFrame baseFrame;
    private OptionPanel optionPanel;
    private FirstPanel firstPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
    private JSplitPane splitPane;
    private JSplitPane splitPane2;
    private PopupMenu popupMenu;
    private JMenuBar bar;
    private JMenuItem option;
    private JMenuItem exit;
    private MenuItem instantlyExit;
    private JMenuItem toggleFullScreen;
    private JMenuItem toggleSideBar;
    private JMenuItem about;
    private MenuItem trayAbout;
    private MenuItem trayHelp;
    private JMenuItem help;
    private final SystemTray systemTray = SystemTray.getSystemTray ();
    private boolean isFullScreen;
    private OptionData optionData;

    private TrayIcon trayIcon;

    public GUI ()
    {

        addPopUpMenu ();
        createTrayIcon ();
        isFullScreen = false;
        splitPane = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize (3);
        splitPane.setBorder (new LineBorder (Color.DARK_GRAY,1));
        splitPane2 = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT);
        splitPane2.setDividerSize (3);
        splitPane2.setBorder (new LineBorder (Color.DARK_GRAY,1));


        baseFrame = new JFrame ("HTTP client");
        baseFrame.setIconImage (new ImageIcon ("images/baseIcon.png").getImage ());
        baseFrame.setLocation (100,80);
        baseFrame.setMinimumSize (new Dimension (1000,650));
        baseFrame.setSize (1350,670);
        baseFrame.addWindowListener (new ComponentHandler ());
        baseFrame.setLayout (new BorderLayout ());
        optionData = new OptionData ();
        loadOptionData ();
        optionPanel = new OptionPanel (optionData);
        firstPanel = new FirstPanel (this);
        secondPanel = new NullPanel (1);
        thirdPanel = new NullPanel (2);
        addMenuBar ();
        splitPane2.setRightComponent (thirdPanel);
        splitPane2.setLeftComponent (secondPanel);
        splitPane.setLeftComponent (firstPanel);
        splitPane.setRightComponent (splitPane2);
        baseFrame.add (splitPane);

    }

    private void saveOptionData ()
    {
        try(ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream (
                new File ("./data/optionData.ser"))))
        {
            out.writeObject (optionData);
        } catch (IOException e)
        {
            e.printStackTrace ();
        }
    }

    private void loadOptionData ()
    {
        try(ObjectInputStream in = new ObjectInputStream (new FileInputStream (
                new File ("./data/optionData.ser"))))
        {
            optionData = (OptionData)in.readObject ();
        } catch (FileNotFoundException ignore)
        {
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace ();
        }
    }



    private void createTrayIcon ()
    {
        trayIcon = new TrayIcon (Toolkit.getDefaultToolkit ().getImage
                ("images/baseIcon.png"),"HTTP client",popupMenu);
        trayIcon.setImageAutoSize (true);
        trayIcon.addMouseListener (new MouseAdapter () {
            @Override
            public void mousePressed (MouseEvent e) {
                if (e.getButton () == MouseEvent.BUTTON1)
                {
                    baseFrame.setVisible (true);
                    systemTray.remove (trayIcon);
                }
            }
        });
    }

    private void addPopUpMenu ()
    {
        popupMenu = new PopupMenu ();
        ComponentHandler componentHandler = new ComponentHandler ();
        instantlyExit = new MenuItem ("exit");
        instantlyExit.addActionListener (componentHandler);
        trayAbout = new MenuItem ("About");
        trayAbout.addActionListener (componentHandler);
        trayHelp = new MenuItem ("Help");
        trayHelp.addActionListener (componentHandler);
        popupMenu.add (trayHelp);
        popupMenu.add (trayAbout);
        popupMenu.addSeparator ();
        popupMenu.add (instantlyExit);
    }

    private void addMenuBar ()
    {
        bar = new JMenuBar ();
        baseFrame.setJMenuBar (bar);
        createApplicationMenu ();
        createViewMenu ();
        createHelpMenu ();
    }

    private void createApplicationMenu ()
    {
        ComponentHandler componentHandler = new ComponentHandler ();
        JMenu application = new JMenu ("Application");
        application.setMnemonic ('A');
        application.getPopupMenu ().setPreferredSize (new Dimension (170,65));

        option = new JMenuItem ("Option");
        option.setMnemonic ('O');
        option.setAccelerator (KeyStroke.getKeyStroke ("control O"));
        option.addActionListener (componentHandler);

        exit = new JMenuItem ("Exit");
        exit.setMnemonic ('x');
        exit.setAccelerator (KeyStroke.getKeyStroke ("control E"));
        exit.addActionListener (componentHandler);

        application.add (option);
        application.addSeparator ();
        application.add (exit);
        bar.add (application);
    }

    private void createViewMenu ()
    {
        ComponentHandler componentHandler = new ComponentHandler ();
        JMenu view = new JMenu ("View");
        view.getPopupMenu ().setPreferredSize (new Dimension (200,65));
        view.setMnemonic ('V');

        toggleFullScreen = new JMenuItem ("Toggle Full Screen");
        toggleFullScreen.setMnemonic ('T');
        toggleFullScreen.setAccelerator (KeyStroke.getKeyStroke ("F11"));
        toggleFullScreen.addActionListener (componentHandler);

        toggleSideBar = new JMenuItem ("Toggle SideBar");
        toggleSideBar.setMnemonic ('S');
        toggleSideBar.setAccelerator (KeyStroke.getKeyStroke ("F12"));
        toggleSideBar.addActionListener (componentHandler);

        view.add (toggleFullScreen);
        view.add (toggleSideBar);
        bar.add (view);
    }

    private void createHelpMenu ()
    {
        ComponentHandler componentHandler = new ComponentHandler ();
        JMenu helpMenu = new JMenu ("Help");
        helpMenu.setMnemonic ('H');
        helpMenu.getPopupMenu ().setPreferredSize (new Dimension (170,65));

        help = new JMenuItem ("Help");
        help.setMnemonic ('l');
        help.setAccelerator (KeyStroke.getKeyStroke ("F1"));
        help.addActionListener (componentHandler);

        about = new JMenuItem ("About");
        about.setMnemonic ('b');
        about.setAccelerator (KeyStroke.getKeyStroke ("control B"));
        about.addActionListener (componentHandler);

        helpMenu.add (help);
        helpMenu.addSeparator ();
        helpMenu.add (about);
        bar.add (helpMenu);
    }



    public OptionPanel getOptionPanel () {
        return optionPanel;
    }


    public FirstPanel getFirstPanel () {
        return firstPanel;
    }

    public JFrame getBaseFrame () {
        return baseFrame;
    }

    public JSplitPane getSplitPane () {
        return splitPane;
    }


    public void setSecondPanel (JPanel secondPanel) {
        if (secondPanel == null)
            throw new InputMismatchException ("inValid input");
        this.secondPanel = secondPanel;
        secondPanel.setVisible (false);
        secondPanel.setVisible (true);
        splitPane2.setLeftComponent (secondPanel);
    }

    public void setThirdPanel (JPanel thirdPanel) {
        if (thirdPanel == null)
            throw new InputMismatchException ("inValid input");
        this.thirdPanel = thirdPanel;
        thirdPanel.setVisible (false);
        thirdPanel.setVisible (true);
        splitPane2.setRightComponent (thirdPanel);
    }

    public void setBaseFrameVisible ()
    {
        baseFrame.setVisible (true);
    }

    private class ComponentHandler extends WindowAdapter
            implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource () == about || e.getSource () == trayAbout)
            {

                JOptionPane.showMessageDialog (baseFrame,
                        "Developer Information :\t\t\n" +
                                "Name : Amirreza Naziri\n" +
                                "ID : 9726081\n" +
                                "e-mail : naziriamirreza@gmail.com","About",
                        JOptionPane.PLAIN_MESSAGE);
            } else if (e.getSource () == help || e.getSource () == trayHelp)
            {
                JOptionPane.showMessageDialog (baseFrame
                        , "help information", "Help",
                        JOptionPane.PLAIN_MESSAGE);
            } else if (e.getSource () == toggleSideBar)
            {
                if (getFirstPanel ().isVisible ())
                {
                    getFirstPanel ().setVisible (false);
                }
                else
                {
                    getFirstPanel ().setVisible (true);
                    getSplitPane ().setLeftComponent (getFirstPanel ());
                }
            } else if (e.getSource () == option)
            {
                JOptionPane.showMessageDialog (null,
                        getOptionPanel (),"Option",JOptionPane.PLAIN_MESSAGE);
            } else if (e.getSource () == exit)
            {
                saveOptionData ();
                if (optionData.isHideInSystemTray () && SystemTray.isSupported ()) {
                    try {
                        systemTray.add (trayIcon);
                        baseFrame.setVisible (false);
                    } catch (AWTException ex) {
                        ex.printStackTrace ();
                    }

                } else
                    System.exit (0);
            } else if (e.getSource () == instantlyExit)
            {
                saveOptionData ();
                System.exit (0);
            } else if (e.getSource () == toggleFullScreen)
            {
                GraphicsDevice g = GraphicsEnvironment.getLocalGraphicsEnvironment ().
                        getDefaultScreenDevice ();
                if (!isFullScreen)
                {
                    baseFrame.dispose ();
                    baseFrame.setUndecorated (true);

                    g.setFullScreenWindow (baseFrame);
                    isFullScreen = true;
                } else {

                    baseFrame.dispose ();
                    baseFrame.setUndecorated (false);
                    baseFrame.setVisible (true);
                    g.setFullScreenWindow (null);
                    baseFrame.setLocation (100,80);
                    baseFrame.setMinimumSize (new Dimension (1000,650));
                    baseFrame.setSize (1350,670);
                    isFullScreen = false;
                }
            }
        }

        @Override
        public void windowClosing (WindowEvent e) {
            saveOptionData ();
            if (optionData.isHideInSystemTray () && SystemTray.isSupported ()) {
                try {
                    systemTray.add (trayIcon);
                    baseFrame.setVisible (false);
                } catch (AWTException ex) {
                    ex.printStackTrace ();
                }

            } else
                System.exit (0);
        }
    }
}
