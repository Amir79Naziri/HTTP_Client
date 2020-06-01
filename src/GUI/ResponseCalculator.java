package GUI;
import javax.swing.*;



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
        thirdPanel.getRequest ().getClientRequest ().run ();


        return true;
    }



    @Override
    protected void done () {
        thirdPanel.properBack ();
    }

}
