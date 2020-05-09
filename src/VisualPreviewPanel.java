import javax.swing.*;
import java.awt.*;


public class VisualPreviewPanel extends JPanel
{

    private JLabel imageLabel;
    private JTextArea textArea;
    private boolean hasError;

    public VisualPreviewPanel (ImageIcon imageIcon)
    {
        super();
        hasError = false;
        setLayout (new BorderLayout());
        Color color = new Color (40, 38, 37, 255);
        setBackground (color);
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


    private void addTextArea ()
    {
        Color color = new Color (40, 38, 37, 255);
        textArea = new JTextArea ("Error: URL using bad/illegal format or missing URL");
        textArea.setEditable (false);
        textArea.setForeground (Color.LIGHT_GRAY);
        textArea.setBackground (color);
        add (textArea,BorderLayout.NORTH);
    }


}
