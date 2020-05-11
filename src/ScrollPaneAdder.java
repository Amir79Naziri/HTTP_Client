import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.InputMismatchException;

/**
 * this class build for adding a scrollPane to panel
 */
public class ScrollPaneAdder
{
    /**
     * adds a scroll pane to panel
     * @param panel panel
     * @return scrollPane which has panel
     */
    public static JScrollPane fetchToJScrollPane (JPanel panel)
    {
        if (panel == null)
            throw new InputMismatchException ("Input is not valid");
        JScrollPane scrollPane = new JScrollPane
                (panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder
                (new LineBorder (new Color (40, 37, 38, 255),1));
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        return scrollPane;
    }
}
