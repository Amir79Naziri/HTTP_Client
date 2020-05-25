import GUI.GUI;

import javax.swing.*;

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
//        Jurl.Jurl jurl = new Jurl.Jurl ();
//        jurl.startProgram ();

//        HttpClient httpClient = new HttpClient ();
//        httpClient.startProgram ();





    }
}
