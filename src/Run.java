import javax.swing.*;
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

//        ExecutorService pool = Executors.newCachedThreadPool ();


//        ClientRequest clientRequest = new ClientRequest ("https://www.p30Download.com",
//                RequestType.GET);
//        clientRequest.addQueryHeaders ("a","g");
//        clientRequest.addQueryHeaders ("sfg","qwe");

//        InputProcessor inputProcessor = new InputProcessor ();
//        inputProcessor.getLine ();

//        StorageUnit storageUnit = new StorageUnit ();

//        storageUnit.addRequest (new ClientRequest ("A",RequestType.GET));
//        storageUnit.addRequest (new ClientRequest ("A",RequestType.GET));

//        System.out.println (storageUnit.size ());
//        storageUnit.printList ();


        InputProcessor inputProcessor = new InputProcessor ();
        inputProcessor.getLine ();
        inputProcessor.print ();





    }
}
