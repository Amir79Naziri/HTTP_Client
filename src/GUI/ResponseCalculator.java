package GUI;
import ConnectionHandler.Connector;
import javax.swing.*;
import java.awt.*;

/**
 * this class build for connecting process in GUI
 *
 * @author Amir Naziri
 */
public class ResponseCalculator extends SwingWorker<Boolean,Object>
{

    private ThirdPanel thirdPanel;
    private boolean correctExecute; // did program executed correct

    /**
     * creates new response calculator
     * @param thirdPanel thirdPanel
     */
    protected ResponseCalculator (ThirdPanel thirdPanel)
    {
        super();
        this.thirdPanel = thirdPanel;
        correctExecute = false;
    }

    @Override
    protected Boolean doInBackground ()  {

        Thread thread = new Thread (new Connector (thirdPanel.getRequestGui ().getClientRequest ()));
        thread.start ();

        try {
            int i = 2;
            thirdPanel.getStatusMessage ().setText ("Connecting");
            thirdPanel.getStatusMessage ().setBackground (new Color (189, 143, 17));
            while (thread.isAlive ()) {

                setProgress (i);
                Thread.sleep (500);
                i += 1;
            }
            setProgress (100);
            Thread.sleep (1000);
            thirdPanel.getProgressPanel ().setVisible (false);
            correctExecute = true;
            return true;
        } catch (InterruptedException e)
        {
            thread.interrupt ();
            thirdPanel.getRequestGui ().getClientRequest ().getResponseStorage ().reset ();
            thirdPanel.getRequestGui ().getClientRequest ().getResponseStorage ().
                    setResponseMessage ("Canceled");
            setProgress (0);
            thirdPanel.getProgressPanel ().setVisible (false);
            correctExecute = false;
            return false;
        }

    }


    @Override
    protected void done () {
        if (correctExecute)
            thirdPanel.properBack ();
        else
            thirdPanel.statusUnknown ("Cancelled");

        correctExecute = false;
    }

    //TODO : some bug here in cancel and error state
}
