import javax.swing.*;
import java.awt.*;
import java.util.InputMismatchException;

public class ThirdPanel extends JPanel
{
    private JLabel statusMessage;
    private JLabel time;
    private JLabel size;

    public ThirdPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        addHeaderStatuses ();
    }

    private void addHeaderStatuses ()
    {
        JPanel headerStatus = new JPanel ();
        headerStatus.setLayout (new BoxLayout (headerStatus,BoxLayout.X_AXIS));
        headerStatus.add (Box.createHorizontalStrut (20));
        headerStatus.setBackground (Color.WHITE);
        headerStatus.setPreferredSize (new Dimension (200,55));

        statusMessage = new JLabel ("200 OK");
        statusMessage.setBackground (new Color (15, 133, 9));
        createLabel (statusMessage,60);


        time = new JLabel ("0 B");
        time.setBackground (Color.GRAY);
        createLabel (time,50);

        size = new JLabel ("0 ms");
        size.setBackground (Color.GRAY);
        createLabel (size,50);

        headerStatus.add (statusMessage);
        headerStatus.add (Box.createHorizontalStrut (15));
        headerStatus.add (time);
        headerStatus.add (Box.createHorizontalStrut (15));
        headerStatus.add (size);

        add(headerStatus,BorderLayout.NORTH);
    }

    private void createLabel (JLabel label, int width) {
        if (label == null)
            throw new InputMismatchException ("inValid input");
        label.setForeground (Color.WHITE);
        label.setOpaque (true);
        label.setPreferredSize (new Dimension (width,25));
        label.setMaximumSize (new Dimension (width,25));
        label.setMinimumSize (new Dimension (width,25));
        label.setHorizontalAlignment (SwingConstants.CENTER);
    }
}
