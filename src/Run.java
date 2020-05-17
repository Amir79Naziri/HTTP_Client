import javax.swing.*;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        ExecutorService pool = Executors.newCachedThreadPool ();


        ClientRequest clientRequest = new ClientRequest ("https://www.p30Download.com",
                RequestType.GET);
        clientRequest.addQueryHeaders ("a","g");
        clientRequest.addQueryHeaders ("sfg","qwe");
        pool.execute (clientRequest);

    }
}
