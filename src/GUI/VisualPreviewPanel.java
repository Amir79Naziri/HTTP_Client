package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * this class represents visual preview panel in third panel
 *
 * @author Amir Naziri
 */
public class VisualPreviewPanel extends JPanel
{

//    private JLabel imageLabel;
    private boolean hasError; // has error to load image
    private Theme theme; // theme

    /**
     * creates a new visual panel
     * @param imageIcon imageIcon
     * @param theme theme
     */
    public VisualPreviewPanel (ImageIcon imageIcon, Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        hasError = false;
        setLayout (new BorderLayout());
        setBackground (theme.getBackGroundColorV4 ());
        addTextArea ();
        if (imageIcon == null)
            hasError = true;
    }

//    public void setImageIcon (ImageIcon imageIcon)
//    {
//        if (imageIcon == null)
//        {
//            hasError = true;
//            return;
//        }
//        imageLabel = new JLabel (imageIcon);
//        hasError = false;
//        repaint ();
//    }
//
//    private void addImagePanel ()
//    {
//        JPanel imagePanel = new JPanel ();
//        imagePanel.setLayout (new BorderLayout ());
//        imagePanel.setBackground (new Color (40, 38, 37, 255));
//        if (!hasError)
//        {
//            imagePanel.add(imageLabel);
//            textArea.setVisible (false);
//        } else
//            textArea.setVisible (true);
//        add(imagePanel);
//    }

    /**
     * adds text message to panel
     */
    private void addTextArea ()
    {
        JTextArea textArea = new JTextArea ("Error: URL using bad/illegal format or missing URL");
        textArea.setEditable (false);
        textArea.setBorder (new EmptyBorder (5,5,5,5));
        textArea.setForeground (theme.getForeGroundColorV2 ());
        textArea.setBackground (theme.getBackGroundColorV4 ());
        add (textArea,BorderLayout.NORTH);
    }
}
