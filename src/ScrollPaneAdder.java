import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


/**
 * this class build for adding a scrollPane to panel
 *
 * @author Amir Naziri
 */
public class ScrollPaneAdder
{
    /**
     * adds a scroll pane to panel
     * @param panel panel
     * @return scrollPane which has panel
     */
    public static JScrollPane fetchToJScrollPane (JPanel panel, Theme theme)
    {
        if (panel == null || theme == null)
            throw new NullPointerException ("inValid input");
        JScrollPane scrollPane = new JScrollPane
                (panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder
                (new LineBorder (theme.getBackGroundColorV4 (),1));
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        return scrollPane;
    }
}
