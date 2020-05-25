package GUI;

import Storage.ResponseStorage;
import javax.swing.*;
import java.awt.*;



public class ResponseCalculator extends SwingWorker<Boolean,Object>
{

    private JLabel statusMessage; // status message
    private JLabel time; // time
    private JLabel size; // size
    private Request request;
    private RawPanel rawPanel;
    private ResultHeaderPanel resultHeaderPanel;


    public ResponseCalculator (Request request, JLabel statusMessage,
                               JLabel time, JLabel size, RawPanel rawPanel,
                               ResultHeaderPanel resultHeaderPanel)
    {
        super();
        this.rawPanel = rawPanel;
        this.resultHeaderPanel = resultHeaderPanel;
        this.statusMessage = statusMessage;
        this.time = time;
        this.size = size;
        this.request = request;

    }

    @Override
    protected Boolean doInBackground ()  {
        request.getClientRequest ().run ();


        return true;
    }



    @Override
    protected void done () {
        ResponseStorage responseStorage
                = request.getClientRequest ().getResponseStorage ();
        if (responseStorage.getResponseCode () == 200)
            statusMessage.setBackground (new Color (52, 174, 22));
        else if (responseStorage.getResponseCode () == 0)
            statusMessage.setBackground (new Color (189, 24, 15));
        else
            statusMessage.setBackground (new Color (165, 123, 0));

        if (responseStorage.getResponseCode () == 0)
            statusMessage.setText (responseStorage.getResponseMessage ());
        else
            statusMessage.setText (responseStorage.getResponseCode ()
                    + " " + responseStorage.getResponseMessage ());



        size.setText (responseStorage.getReadLength ());
        time.setText (responseStorage.getResponseTime () + "ms");
        rawPanel.getTextArea ().setText (responseStorage.getResponseTextRawData ());

        resultHeaderPanel.clear ();

        for (String key : responseStorage.getResponseHeaders ().keySet ())
            for (String value : responseStorage.getResponseHeaders ().get (key))
            {
                if (key != null)
                    resultHeaderPanel.addResultKeyAndValue (key,value);
            }


    }

}
