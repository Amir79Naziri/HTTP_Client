package GUI;


import ClientSocketHandler.ClientSocket;
import ConnectionHandler.Executor;

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
    private int type; // type 1 means normal execute , 2 means server execute
    private ClientSocket clientSocket;

    /**
     * creates new response calculator
     * @param thirdPanel thirdPanel
     * @param type type 1 means normal execute , 2 means server execute
     * @param clientSocket clientSocket , if type 1 it is null
     */
    protected ResponseCalculator (ThirdPanel thirdPanel, int type, ClientSocket clientSocket)
    {
        super();
        this.thirdPanel = thirdPanel;
        correctExecute = false;
        this.type = type;
        this.clientSocket = clientSocket;
    }

    @Override
    protected Boolean doInBackground ()  {

        Executor executor = null;
        Thread thread;


        if (type == 1)
        {
            executor = new Executor (thirdPanel.getRequestGui ().getClientRequest ());
            thread = new Thread (executor);
        } else
        {
            thread = new Thread (clientSocket);
        }

        thread.start ();

        try {
            int i = 2;
            thirdPanel.getStatusMessage ().setText ("Connecting");
            thirdPanel.getStatusMessage ().setBackground (new Color (189, 143, 17));
            if (type == 1)
            {
                assert executor != null;
                while (!executor.getPool ().isTerminated ()) {

                    setProgress (i);
                    Thread.sleep (500);
                    i += 1;
                }
            }
            else
            {
                while (!clientSocket.isFinished ()) {

                    setProgress (i);
                    Thread.sleep (500);
                    i += 1;
                }
            }

            setProgress (100);
            Thread.sleep (1000);
            thirdPanel.getProgressPanel ().setVisible (false);
            correctExecute = true;
            return true;
        } catch (InterruptedException e)
        {
            thread.interrupt ();
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
        else {
            thirdPanel.statusUnknown ("Cancelled");
        }
        correctExecute = false;
    }

}
