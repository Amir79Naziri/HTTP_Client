import javax.swing.*;
import java.awt.*;
import java.util.InputMismatchException;

/**
 * this class made for adding a component to gridBagLayout
 *
 * @author Amir Naziri
 */
public class GridBagAdder
{
    /**
     * add a component to gridBag layout in base frame
     * height is one
     * @param component component
     * @param row row
     * @param col col
     * @param width width
     * @param layout layout
     * @param constraints constrains for layout
     * @param panel base panel
     */
    public static void addComponent (JComponent component, int row, int col, int width,
                                     GridBagLayout layout, GridBagConstraints constraints,
                                     JPanel panel) {
        if (layout == null || constraints  == null)
            throw new InputMismatchException ("inValid input");
        constraints.gridy = row;
        constraints.gridx = col;
        constraints.gridheight = 1;
        constraints.gridwidth = width;

        layout.setConstraints (component, constraints);
        panel.add (component);
    }
}
