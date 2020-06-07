package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * this class represents a null panel which is a empty panel for when there is n't
 * any Data
 *
 * @author Amir Naziri
 */
public class NullPanel extends JPanel
{

    /**
     * create a null panel
     * @param type 1 for secondPanel
     *             2 for thirdPanel
     * @param theme theme
     */
    protected NullPanel (int type, Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        setLayout (new BorderLayout ());
        JPanel upper = new JPanel ();

        upper.setPreferredSize (new Dimension (400,55));
        upper.setMinimumSize (new Dimension (350,55));
        upper.setMaximumSize ((new Dimension (600,55)));
        upper.setBackground (theme.getBackGroundColorV5 ());
        JPanel center = new JPanel (new BorderLayout ());
        JLabel label = new JLabel ();
        center.setBackground (theme.getBackGroundColorV4 ());
        label.setHorizontalAlignment (SwingConstants.CENTER);
        if (type == 1)
            label.setText (" Click Or Add New Request ");
        else
            label.setText (" Click Send Button for a Request ");
        label.setOpaque (true);
        label.setFont (new Font ("Arial",Font.PLAIN,15));
        label.setForeground (theme.getForeGroundColorV2 ());
        label.setBackground (theme.getBackGroundColorV4 ());
        center.add (label,BorderLayout.CENTER);
        add(upper,BorderLayout.NORTH);
        add(center,BorderLayout.CENTER);
    }
}
