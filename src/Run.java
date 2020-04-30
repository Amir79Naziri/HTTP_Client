import javax.swing.*;

public class Run
{
    public static void main (String[] args) {


        try {
            UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName ());
        } catch (Exception e)
        {
            e.printStackTrace ();
        }

        GUI gui = new GUI ();
        gui.setBaseFrameVisible ();
    }
}
