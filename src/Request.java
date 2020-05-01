import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Request extends JPanel
{
    JPanel chose;
    public Request (RequestType type, String name)
    {
        super();
        setLayout (new FlowLayout (FlowLayout.LEFT));
        setBackground (new Color (46, 53, 53, 255));

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

        nameLabel.setForeground (Color.WHITE);

        add (chose);
        add (typeLabel);
        add (nameLabel);
    }

    @Override
    protected void paintComponent (Graphics g) {

//        Graphics2D g2d = (Graphics2D)g;
//        g2d.setColor (Color.WHITE);
//        g2d.fillArc (getX (), getY (),getHeight (),getHeight (),90,180);
//        g2d.fillRect (getX () + getHeight () / 2,getY (),getWidth () - getHeight (),
//                getHeight ());
//        g2d.fillArc (getWidth () - getHeight () -1 ,getY (),getHeight (),getHeight (),
//                90,-180);
//        g2d.setColor (new Color (46, 53, 53, 255));
        super.paintComponent (g);
    }


    public void setChoseVisibly (boolean visibly) {
        chose.setVisible (visibly);
    }

    @Override
    public Dimension getMinimumSize () {
        return new Dimension (340,40);
    }

    @Override
    public Dimension getMaximumSize () {
        return new Dimension (340,40);
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension (340,40);
    }
}
