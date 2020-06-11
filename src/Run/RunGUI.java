package Run;

import ControlUnit.Controller;

import javax.swing.*;

/**
 * this class build for test GUI
 */
public class RunGUI
{
    public static void main (String[] args) {

        try { // "javax.swing.plaf.nimbus.NimbusLookAndFeel"
            UIManager.setLookAndFeel ("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e)
        {
            e.printStackTrace ();
        }

        Controller.startProgramGui ();
    }
}
