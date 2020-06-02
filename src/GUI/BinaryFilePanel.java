package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * this class represents a binary file panel in secondPanel
 *
 * @author Amir Naziri
 */
public class BinaryFilePanel extends JPanel
{
    private JTextField fileAddress; // fileAddress
    private JButton chooseFile; // chooseFileButton
    private JButton resetFile; // reset file
    private Theme theme; // theme
    private Path path;
    /**
     * creates a new binary file panel
     * @param theme theme
     */
    public BinaryFilePanel (Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        setLayout (new BorderLayout ());
        this.theme = theme;
        setBackground (theme.getBackGroundColorV4 ());
        addComponent ();
        path = null;
    }

    /**
     * add components to base Panel
     */
    private void addComponent ()
    {
        GridBagConstraints constraints = new GridBagConstraints ();
        GridBagLayout layout = new GridBagLayout ();
        JPanel basePanel = new JPanel ();
        basePanel.setLayout (layout);
        basePanel.setBorder (new EmptyBorder (10,7,10,7));
        basePanel.setBackground (theme.getBackGroundColorV4 ());
        JLabel label = new JLabel ("SELECTED FILE");
        label.setForeground (theme.getForeGroundColorV2 ());
        label.setFont (new Font ("Arial",Font.PLAIN,10));

        fileAddress = new JTextField ("No file selected");
        fileAddress.setBorder (BorderFactory.createCompoundBorder (
                new LineBorder (Color.GRAY,1,true),
                new EmptyBorder (1,5,1,5)));
        fileAddress.setForeground (theme.getForeGroundColorV2 ());
        fileAddress.setBackground (Color.GRAY);
        fileAddress.setEditable (false);
        ButtonHandler buttonHandler = new ButtonHandler ();
        chooseFile = new JButton ("Choose File");
        chooseFile.setForeground (theme.getForeGroundColorV1 ());
        chooseFile.setBackground (theme.getBackGroundColorV4 ());
        chooseFile.setFocusable (false);
        chooseFile.addActionListener (buttonHandler);

        resetFile = new JButton ("Reset File");
        resetFile.setBackground (theme.getBackGroundColorV4 ());
        resetFile.setForeground (theme.getForeGroundColorV2 ());
        resetFile.setFocusable (false);
        resetFile.addActionListener (buttonHandler);
        resetFile.setEnabled (false);


        constraints.fill = GridBagConstraints.HORIZONTAL;
        GridBagAdder.addComponent (label,0,0,1,layout,constraints,basePanel);
        constraints.weightx = 0.5;
        constraints.ipady = 7;
        GridBagAdder.addComponent (fileAddress,1,0,8,layout,constraints,
                basePanel);
        GridBagAdder.addComponent (new JLabel (),2,0,8,layout,constraints,basePanel);
        constraints.ipady = 0;
        GridBagAdder.addComponent (new JLabel (),3,1,3,layout,constraints,basePanel);
        constraints.weightx = 0.0;
        GridBagAdder.addComponent (resetFile,3,4,1,layout,constraints,basePanel);
        GridBagAdder.addComponent (chooseFile,3,6,1,layout,constraints,basePanel);

        add(basePanel,BorderLayout.NORTH);
    }

    /**
     * use filChooser to choose file
     * @return path of file
     */
    private Path useFileChooser ()
    {
        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileSelectionMode (JFileChooser.FILES_ONLY);
        int res = fileChooser.showOpenDialog (this);

        if (res == JFileChooser.CANCEL_OPTION)
        {
            return path;
        }

        return fileChooser.getSelectedFile ().toPath ();
    }

    /**
     * change the the data size to humanReadable form
     * @param size input size
     * @return humanReadable form
     */
    public static String makeSizeReadable (long size)
    {
        if (size < 1024)
            return size + " B";

        String[] scales = {"k", "M", "G"};
        int i = -1;
        while (size > 1024)
        {
            size /= 1024;
            i++;
        }
        float newSize = (size / 1f);
        return String.format ("%.1f %s",newSize,scales[i]);

    }

    /**
     * @param path  path
     * do task on path
     */
    private void pathHandler (Path path)
    {
        this.path = path;


        if (path != null && Files.exists (path))
        {
            resetFile.setEnabled (true);
            StringBuilder stringBuilder = new StringBuilder ();
            stringBuilder.append (String.format ("%s",path)).append (" ");
            try{
                stringBuilder.append (String.format ("(%s)",makeSizeReadable (Files.size (path))));
            } catch (IOException e)
            {
                e.printStackTrace ();
            }
            fileAddress.setText (stringBuilder.toString ());
        }

    }

    /**
     * class for button handling
     */
    private class ButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource () == chooseFile)
                pathHandler (useFileChooser ());
            else if (e.getSource () == resetFile)
            {
                clearPath ();
            }
        }
    }

    public void clearPath ()
    {
        fileAddress.setText ("No file selected");
        path = null;
        resetFile.setEnabled (false);
    }

    public void setPath (Path path) {
        pathHandler (path);
    }

    public Path getPath () {
        return path;
    }
}
