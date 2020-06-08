package GUI;

import javax.imageio.ImageIO;
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



    private Theme theme; // theme
    private JLabel imageLabel;
    private JTextArea textArea;
    private JEditorPane editorPane;


    /**
     * creates a new visual panel
     * @param theme theme
     */
    protected VisualPreviewPanel (Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        JPanel panel = new JPanel (new FlowLayout (FlowLayout.CENTER));
        imageLabel = new JLabel ();
        editorPane = new JEditorPane ();
        editorPane.setEditable (false);
        editorPane.setVisible (false);
        panel.add (imageLabel);
        JPanel panel1 = new JPanel (new BorderLayout ());
        panel1.add (editorPane,BorderLayout.CENTER);
        panel.add (ScrollPaneAdder.fetchToJScrollPane (panel1,theme));
        setLayout (new BorderLayout());
        panel.setBackground (theme.getBackGroundColorV4 ());
        setBackground (theme.getBackGroundColorV4 ());
        addTextArea ();
        add(panel,BorderLayout.CENTER);
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

    /**
     * remove image
     */
    protected void removeImage ()
    {
        this.setVisible (false);
        textArea.setVisible (true);
        imageLabel.setIcon (null);
        this.setVisible (true);
    }

    /**
     * add image
     * @param data data
     */
    protected void addImage (byte[] data)  {

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

    /**
     * add editor
     * @param url url
     */
    protected void addEditor (String url) {
        this.setVisible (false);
        textArea.setVisible (false);
        editorPane.setVisible (true);
        this.setVisible (true);
        new Thread (new Runnable () {
            @Override
            public void run () {
                try {
                    editorPane.setPage (url);
                } catch (IOException e) {
                    removeEditor ();
                }
            }
        }).start ();
    }

    /**
     * remove editor
     */
    protected void removeEditor ()
    {
        this.setVisible (false);
        textArea.setVisible (true);
        editorPane.setVisible (false);
        this.setVisible (true);
    }
}
