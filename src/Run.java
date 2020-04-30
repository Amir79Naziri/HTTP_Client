import javax.swing.*;

public class Run
{
    public static void main (String[] args) {


        try {
            UIManager.setLookAndFeel ("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e)
        {
            e.printStackTrace ();
        }

        GUI gui = new GUI ();
        gui.setBaseFrameVisible ();
    }
}
