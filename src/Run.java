import GUI.*;
import Jurl.*;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
//
////
        Jurl jurl = new Jurl ();
        jurl.startProgram ();







    }
}
