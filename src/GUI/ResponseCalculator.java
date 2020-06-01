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
    private VisualPreviewPanel visualPreviewPanel;


    public ResponseCalculator (Request request, JLabel statusMessage,
                               JLabel time, JLabel size, RawPanel rawPanel,
                               ResultHeaderPanel resultHeaderPanel,
                               VisualPreviewPanel visualPreviewPanel)
    {
        super();
        this.rawPanel = rawPanel;
        this.resultHeaderPanel = resultHeaderPanel;
        this.statusMessage = statusMessage;
        this.time = time;
        this.size = size;
        this.request = request;
        this.visualPreviewPanel = visualPreviewPanel;

    }

    @Override
    protected Boolean doInBackground ()  {
        request.getClientRequest ().run ();

        ResponseStorage responseStorage
                = request.getClientRequest ().getResponseStorage ();
        resultHeaderPanel.clear ();

        for (String key : responseStorage.getResponseHeaders ().keySet ())
            for (String value : responseStorage.getResponseHeaders ().get (key))
            {
                if (key != null) {
                    resultHeaderPanel.addResultKeyAndValue (key, value);

                    if (key.equals ("Content-Type") && value.split (";")[0].
                            equals ("image/png"))
                    {
                        visualPreviewPanel.addImage
                                (responseStorage.getResponseBinaryRawData ());
                    } else
                        visualPreviewPanel.removeImage ();
                }
            }

        return true;
    }



    @Override
    protected void done () {
        ResponseStorage responseStorage
                = request.getClientRequest ().getResponseStorage ();
        String responseCode = responseStorage.getResponseCode () + "";
        if (responseCode.matches ("2(.*)"))
            statusMessage.setBackground (new Color (52, 174, 22));
        else if (responseCode.equals ("0"))
            statusMessage.setBackground (new Color (189, 24, 15));
        else
            statusMessage.setBackground (new Color (133, 94, 8));

        if (responseStorage.getResponseCode () == 0)
            statusMessage.setText (responseStorage.getResponseMessage ());
        else if (responseStorage.getResponseMessage () == null)
            statusMessage.setText (responseStorage.getResponseCode () + "");
        else
            statusMessage.setText (responseStorage.getResponseCode ()
                    + " " + responseStorage.getResponseMessage ());

        size.setText (responseStorage.getReadLength ());
        time.setText (responseStorage.getResponseTime () + "ms");
        rawPanel.getTextArea ().setText (responseStorage.getResponseTextRawData ());

    }

}
