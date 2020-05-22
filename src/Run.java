import javax.swing.*;
import javax.swing.text.html.HTMLWriter;
import java.net.MalformedURLException;
import java.net.http.HttpClient;
import java.util.ArrayList;

/**
 * this class build for test
 */
public class Run
{
    public static void main (String[] args) {


        try { // "javax.swing.plaf.nimbus.NimbusLookAndFeel"
            UIManager.setLookAndFeel ("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e)
        {
            e.printStackTrace ();
        }
//        GUI gui = new GUI ();
//        gui.setBaseFrameVisible ();


//
        Jurl jurl = new Jurl ();
        jurl.startProgram ();






    }
}
