package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.InputMismatchException;

/**
 * this class represents Key and Value in Result Header
 *
 * @author Amir Naziri
 */
public class ResultKeyAndValue extends JPanel
{
    private JTextArea key; // key text Area
    private JTextArea value; // value text Area
    private Color colorThemeForForeGround; // color for foreGround in theme

    /**
     * creates a new instance of class
     * @param key key
     * @param value value
     * @param backGround backGround color
     * @param colorThemeForForeGround  foreGround color
     */
    protected ResultKeyAndValue (String key, String value, Color backGround,
                              Color colorThemeForForeGround)
    {
        super();
        if (key == null || value == null || backGround == null || colorThemeForForeGround == null)
            throw new NullPointerException ("inValid input");
        // constrains
        GridBagConstraints constraints = new GridBagConstraints ();
        // layout
        GridBagLayout layout = new GridBagLayout ();
        this.colorThemeForForeGround = colorThemeForForeGround;
        setBackground (backGround);
        setLayout (layout);
        setBorder (new EmptyBorder (1,5,1,5));

        this.key = new JTextArea (key);
        createTextAria (this.key,backGround);
        this.value = new JTextArea (value);
        createTextAria (this.value,backGround);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        GridBagAdder.addComponent (this.key,0,0,1,layout,constraints,this);
        GridBagAdder.addComponent (this.value,0,1,1,layout,constraints,this);

    }

    /**
     * creates each textAria in input
     * @param textArea textArea
     * @param color color for backGround
     */
    private void createTextAria (JTextArea textArea, Color color)
    {
        if (textArea == null || color == null)
            throw new InputMismatchException ("inValid input");
        textArea.setBorder (new EmptyBorder (0,1,0,1));
        textArea.setBackground (color);
        textArea.setLineWrap (true);
        textArea.setCursor (Cursor.getPredefinedCursor (Cursor.TEXT_CURSOR));
        textArea.setWrapStyleWord (true);
        textArea.setEditable (false);
        textArea.setForeground (colorThemeForForeGround);
        textArea.setFont (new Font ("Arial",Font.PLAIN,13));
    }

    /**
     * @return key TextArea
     */
    protected JTextArea getKey () {
        return key;
    }

    /**
     * @return value TextArea
     */
    protected JTextArea getValue () {
        return value;
    }


}
