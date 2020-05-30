package GUI;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * this class represents visual preview panel in third panel
 *
 * @author Amir Naziri
 */
public class VisualPreviewPanel extends JPanel
{

//    private JLabel imageLabel;

    private Theme theme; // theme
    private JLabel imageLabel;
    JTextArea textArea;


    /**
     * creates a new visual panel
     * @param theme theme
     */
    public VisualPreviewPanel (Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        imageLabel = new JLabel ();
        setLayout (new BorderLayout());
        setBackground (theme.getBackGroundColorV4 ());
        addTextArea ();
        add(imageLabel,BorderLayout.CENTER);
    }

    /**
     * adds text message to panel
     */
    private void addTextArea ()
    {
        textArea = new JTextArea ("Error: URL using bad/illegal format or missing URL");
        textArea.setEditable (false);
        textArea.setBorder (new EmptyBorder (5,5,5,5));
        textArea.setForeground (theme.getForeGroundColorV2 ());
        textArea.setBackground (theme.getBackGroundColorV4 ());
        add (textArea,BorderLayout.NORTH);
    }

    public void removeImage ()
    {
        this.setVisible (false);
        textArea.setVisible (true);
        imageLabel.setIcon (null);
        this.setVisible (true);
    }

    public void addImage (byte[] data)  {

        ByteArrayInputStream in = new ByteArrayInputStream (data);
        try{
            BufferedImage image = ImageIO.read(in);
            this.setVisible (false);
            textArea.setVisible (false);
            imageLabel.setIcon (new ImageIcon (image));
            this.setVisible (true);

        } catch (IOException e)
        {
            System.out.println ("some thing went wrong in loading image");

        }
    }
}
