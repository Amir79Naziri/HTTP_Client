import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Request extends JPanel
{
    public Request ()
    {
        super();
        setLayout (new FlowLayout (FlowLayout.LEFT));
        setBackground (new Color (46, 53, 53, 255));
        addMouseListener (new MouseHandler ());
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


    private class MouseHandler extends MouseAdapter
    {

        @Override
        public void mouseEntered (MouseEvent e) {

            e.getComponent ().requestFocusInWindow ();
            e.getComponent ().setBackground (new Color (71, 80, 87, 255));

        }

        @Override
        public void mouseExited (MouseEvent e) {

            e.getComponent ().setBackground (new Color (46, 53, 53, 255));

        }

        @Override
        public void mouseClicked (MouseEvent e) {
            super.mouseClicked (e);
        }
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
