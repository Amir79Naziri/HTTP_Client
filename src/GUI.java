import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.InputMismatchException;

/**
 * this class has the main frame in this program , splitPanes, all panels , ....
 *
 * @author Amir Naziri
 */
public class GUI
{

    private JFrame baseFrame; // baseFrame
    private OptionPanel optionPanel; // optionPanel
    private FirstPanel firstPanel; // firstPanel
    private JPanel secondPanel; // secondPanel
    private JPanel thirdPanel; // third panel
    private JSplitPane splitPane; // splitPane for firstPanel and splitPane2
    private JSplitPane splitPane2; // splitPane for secondPanel and ThirdPanel
    private PopupMenu popupMenu; // popMenu for IconTray
    private JMenuBar bar; // menuBar
    private JMenuItem option; // option menuItem
    private JMenuItem exit; // exit menuItem
    private MenuItem instantlyExit; // instantly Exit menuItem
    private JMenuItem toggleFullScreen; // toggleFullScreen menuItem
    private JMenuItem toggleSideBar; // toggle sideBar menuItem
    private JMenuItem about; // about menuItem
    private JMenuItem addNew; // add new request
    private MenuItem trayAbout; // about menuItem for iconTray
    private MenuItem trayHelp; // help menuItem for iconTray
    private JMenuItem help; // about menuItem for iconTray
    private final SystemTray systemTray = SystemTray.getSystemTray (); // systemTray
    private boolean isFullScreen; // isInFullScreen
    private OptionData optionData; // optionData
    private TrayIcon trayIcon; // trayIcon

    /**
     * creates a new GUI
     */
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
        // theme
        Theme theme = optionData.getTheme ();
        optionPanel = new OptionPanel (optionData, this);
        firstPanel = new FirstPanel (this, theme);
        secondPanel = new NullPanel (1, theme);
        thirdPanel = new NullPanel (2, theme);
        addMenuBar ();
        splitPane2.setRightComponent (thirdPanel);
        splitPane2.setLeftComponent (secondPanel);
        splitPane.setLeftComponent (firstPanel);
        splitPane.setRightComponent (splitPane2);
        baseFrame.add (splitPane);

    }

    /**
     * save optionData
     */
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

    /**
     * load optionData
     */
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

    /**
     * create TrayIcon
     */
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

    /**
     * add popUpMenu
     */
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

    /**
     * add MenuBar
     */
    private void addMenuBar ()
    {
        bar = new JMenuBar ();
        baseFrame.setJMenuBar (bar);
        createApplicationMenu ();
        createViewMenu ();
        createHelpMenu ();
    }

    /**
     * create ApplicationMenu
     */
    private void createApplicationMenu ()
    {
        ComponentHandler componentHandler = new ComponentHandler ();
        JMenu application = new JMenu ("Application");
        application.setMnemonic ('A');
        application.getPopupMenu ().setPreferredSize (new Dimension (170,85));

        addNew = new JMenuItem ("Add NewRequest");
        addNew.setMnemonic ('N');
        addNew.setAccelerator (KeyStroke.getKeyStroke ("control N"));
        addNew.addActionListener (componentHandler);
        option = new JMenuItem ("Option");
        option.setMnemonic ('O');
        option.setAccelerator (KeyStroke.getKeyStroke ("control O"));
        option.addActionListener (componentHandler);

        exit = new JMenuItem ("Exit");
        exit.setMnemonic ('x');
        exit.setAccelerator (KeyStroke.getKeyStroke ("control E"));
        exit.addActionListener (componentHandler);

        application.add (option);
        application.add (addNew);
        application.addSeparator ();
        application.add (exit);
        bar.add (application);
    }

    /**
     * create view menu
     */
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

    /**
     * create help menu
     */
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

    /**
     * @return optionPanel
     */
    public OptionPanel getOptionPanel () {
        return optionPanel;
    }

    /**
     * @return firstPanel
     */
    public FirstPanel getFirstPanel () {
        return firstPanel;
    }

    /**
     * @return baseFrame
     */
    public JFrame getBaseFrame () {
        return baseFrame;
    }

    /**
     * @return splitPane
     */
    public JSplitPane getSplitPane () {
        return splitPane;
    }

    /**
     * set Second Panel
     * @param secondPanel secondPanel
     */
    public void setSecondPanel (JPanel secondPanel) {
        if (secondPanel == null)
            throw new InputMismatchException ("inValid input");
        this.secondPanel = secondPanel;
        secondPanel.setVisible (false);
        secondPanel.setVisible (true);
        splitPane2.setLeftComponent (secondPanel);
    }

    /**
     * sets thirdPanel
     * @param thirdPanel thirdPanel
     */
    public void setThirdPanel (JPanel thirdPanel) {
        if (thirdPanel == null)
            throw new InputMismatchException ("inValid input");
        this.thirdPanel = thirdPanel;
        thirdPanel.setVisible (false);
        thirdPanel.setVisible (true);
        splitPane2.setRightComponent (thirdPanel);
    }

    /**
     * sets Base Frame visible
     */
    public void setBaseFrameVisible ()
    {
        baseFrame.setVisible (true);
    }

    /**
     * this class handles components in this panel
     */
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
            } else if (e.getSource () == addNew)
            {
                AddNewRequestPanel addNewRequestPanel = new AddNewRequestPanel ();
                int res =
                        JOptionPane.showOptionDialog (baseFrame,
                                addNewRequestPanel,"New Request",
                                JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,
                                null,null);
                if (res == 0) {
                    getFirstPanel ().addRequest
                            (addNewRequestPanel.getNameOfNewRequest (),
                                    addNewRequestPanel.getChosenRequestType ()
                            );
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
