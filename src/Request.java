import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;


public class Request extends JPanel
{
    private JPanel chose;
    private final SecondPanel secondPanel;
    private JPopupMenu popupMenu;
    private String requestName;
    private RequestType requestType;
    private boolean isDeleted;
    private JLabel typeLabel;
    private JLabel nameLabel;

    public Request (RequestType requestType, String name, GUI gui)
    {
        super();

        if (name == null || requestType == null)
            throw new InputMismatchException ("inValid input");
        isDeleted = false;
        this.requestName = name;
        this.requestType = requestType;
        setLayout (new FlowLayout (FlowLayout.LEFT));
        setBackground (new Color (45, 46, 42, 255));

        chose = new JPanel ();
        chose.setBackground (new Color (122, 103,218));
        chose.setPreferredSize (new Dimension (2,29));
        chose.setVisible (false);

        typeLabel = new JLabel (requestType.getName ());
        secondPanel = new SecondPanel (gui,this);
        typeLabel.setFont (new Font ("Arial",Font.PLAIN,10));
        typeLabel.setForeground (requestType.getColor ());
        typeLabel.setPreferredSize (new Dimension (40,29));


        nameLabel = new JLabel (name);
        nameLabel.setFont (new Font ("Arial",Font.PLAIN,13));
        nameLabel.setForeground (Color.LIGHT_GRAY);
        JMenuItem delete = new JMenuItem ("Delete");
        delete.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                setVisible (false);
                delete();
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


    public void setRequestType (RequestType requestType) {
        if (requestType == null)
            throw new InputMismatchException ("inValid input");
        this.requestType = requestType;
        typeLabel.setText (requestType.getName ());
        typeLabel.setForeground (requestType.getColor ());
        setVisible (false);
        setVisible (true);
    }

    public void setRequestName (String requestName) {
        this.requestName = requestName;
        nameLabel.setText (requestName);
        setVisible (false);
        setVisible (true);
    }

    public boolean isDeleted () {
        return isDeleted;
    }

    public void delete () {
        isDeleted = true;
    }

    public JPopupMenu getPopupMenu () {
        return popupMenu;
    }


    public void setChoseVisibly (boolean visibly) {
        chose.setVisible (visibly);
    }

    @Override
    public Dimension getMinimumSize () {
        return new Dimension (350,38);
    }

    @Override
    public Dimension getMaximumSize () {
        return new Dimension (800,38);
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension (350,38);
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
