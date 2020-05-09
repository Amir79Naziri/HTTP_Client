import javax.swing.*;
import java.awt.*;
import java.util.InputMismatchException;


public class Request extends JPanel
{
    private JPanel chose;
    private final SecondPanel secondPanel;

    public Request (RequestType type, String name)
    {
        super();

        if (name == null || type == null)
            throw new InputMismatchException ("inValid input");
        secondPanel = new SecondPanel ();
        setLayout (new FlowLayout (FlowLayout.LEFT));
        setBackground (new Color (45, 46, 42, 255));

        chose = new JPanel ();
        chose.setBackground (new Color (122, 103,218));
        chose.setPreferredSize (new Dimension (2,29));
        chose.setVisible (false);

        JLabel typeLabel = new JLabel (type.getName ());
        typeLabel.setFont (new Font ("Arial",Font.PLAIN,10));
        typeLabel.setForeground (type.getColor ());
        typeLabel.setPreferredSize (new Dimension (40,29));


        JLabel nameLabel = new JLabel (name);
        nameLabel.setFont (new Font ("Arial",Font.PLAIN,13));
        nameLabel.setForeground (Color.LIGHT_GRAY);


        add (chose);
        add (typeLabel);
        add (nameLabel);
    }



    public void setChoseVisibly (boolean visibly) {
        chose.setVisible (visibly);
    }

    @Override
    public Dimension getMinimumSize () {
        return new Dimension (300,38);
    }

    @Override
    public Dimension getMaximumSize () {
        return new Dimension (340,38);
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension (340,38);
    }

    public boolean isSelected ()
    {
        return chose.isVisible ();
    }

    public SecondPanel getSecondPanel () {
        return secondPanel;
    }
}
