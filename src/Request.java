import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.InputMismatchException;


public class Request extends JPanel
{
    private JPanel chose;
    private final SecondPanel secondPanel;
    private JPopupMenu popupMenu;
    private String requestName;
    private RequestType requestType;

    public Request (RequestType type, String name, GUI gui)
    {
        super();

        if (name == null || type == null)
            throw new InputMismatchException ("inValid input");
        this.requestName = name;
        this.requestType = type;
        secondPanel = new SecondPanel (gui);
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
        JMenuItem delete = new JMenuItem ("Delete");
        delete.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                setVisible (false);
                if (chose.isVisible ())
                {
                    gui.setThirdPanel (new NullPanel (2));
                    gui.setSecondPanel (new NullPanel (1));
                }

            }
        });
        popupMenu = new JPopupMenu ();
        popupMenu.add (delete);

        add (chose);
        add (typeLabel);
        add (nameLabel);
    }


    public JPopupMenu getPopupMenu () {
        return popupMenu;
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

    public String getRequestName () {
        return requestName;
    }

    public RequestType getRequestType () {
        return requestType;
    }
}
