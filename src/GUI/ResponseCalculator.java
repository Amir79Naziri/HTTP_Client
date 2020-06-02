package GUI;
import javax.swing.*;
import java.awt.*;


public class ResponseCalculator extends SwingWorker<Boolean,Object>
{

    private ThirdPanel thirdPanel;



    public ResponseCalculator (ThirdPanel thirdPanel)
    {
        super();
        this.thirdPanel = thirdPanel;
    }

    @Override
    protected Boolean doInBackground ()  {

        Thread thread = new Thread (thirdPanel.getRequest ().getClientRequest ());
        thread.start ();
//        Thread thread = new Thread (new Runnable () {
//            @Override
//            public void run () {
//                for (int i = 0; i < 40; i++)
//                {
//                    try {
//                        Thread.sleep (1000);
//                    } catch (InterruptedException e)
//                    {
//                        System.err.println ("aaa");
//                    }
//                }
//            }
//        });
//        thread.start ();

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
        } catch (InterruptedException ignore)
        {
            thread.interrupt ();
            thirdPanel.getRequest ().getClientRequest ().getResponseStorage ().reset ();
            thirdPanel.getRequest ().getClientRequest ().getResponseStorage ().
                    setResponseMessage ("Canceled");
            setProgress (0);
            thirdPanel.getProgressPanel ().setVisible (false);
            return true;
        }
        return true;
    }



    @Override
    protected void done () {
        thirdPanel.properBack ();
    }


}
