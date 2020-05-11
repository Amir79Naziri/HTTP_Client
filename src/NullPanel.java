import javax.swing.*;
import java.awt.*;

/**
 * this class represents a null panel which is a empty panel for when there is n't
 * any Data
 */
public class NullPanel extends JPanel
{
    /**
     * create a null panel
     * @param type 1 for secondPanel
     *             2 for thirdPanel
     */
    public NullPanel (int type)
    {
        super();
        Color color = new Color (40, 37, 38, 255);
        setLayout (new BorderLayout ());
        JPanel upper = new JPanel ();

        upper.setPreferredSize (new Dimension (400,55));
        upper.setMinimumSize (new Dimension (350,55));
        upper.setMaximumSize ((new Dimension (600,55)));
        upper.setBackground (Color.WHITE);
        JPanel center = new JPanel (new BorderLayout ());
        JLabel label = new JLabel ();
        center.setBackground (color);
        label.setHorizontalAlignment (SwingConstants.CENTER);
        if (type == 1)
            label.setText (" Click Or Add New Request ");
        else
            label.setText (" Click Send Button for a Request ");
        label.setOpaque (true);
        label.setFont (new Font ("Arial",Font.PLAIN,15));
        label.setForeground (Color.LIGHT_GRAY);
        label.setBackground (color);
        center.add (label,BorderLayout.CENTER);
        add(upper,BorderLayout.NORTH);
        add(center,BorderLayout.CENTER);
    }
}
