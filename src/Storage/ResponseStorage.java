package Storage;

import java.io.*;
import java.util.List;
import java.util.Map;

public class ResponseStorage implements Serializable
{
    private String responseTextRawData;
    private byte[] responseBinaryRawData;
    private int responseCode;
    private String responseMessage;
    private Map<String, List<String>> responseHeaders;
    private String readLength;
    private long responseTime;
    private boolean valid; // if first time : false  OW true

    public ResponseStorage ()
    {
        valid = false;
        reset ();
    }

    public void reset ()
    {
        responseTextRawData = "Error: URL using bad/illegal format or missing URL";
        readLength = "0 B";
        responseTime = 0;
        responseMessage = "ERROR";
        responseCode = 0;
        responseBinaryRawData = null;
        responseHeaders = null;
    }

    public void setResponseMessage (String responseMessage) {
        this.responseMessage = responseMessage;
        valid = true;
    }

    public void setReadLength (String readLength) {
        this.readLength = readLength;
        valid = true;
    }

    public void setResponseCode (int responseCode) {
        this.responseCode = responseCode;
        valid = true;
    }

    public void setResponseHeaders (Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
        valid = true;
    }

    public void setResponseTextRawData (String responseRawData) {
        this.responseTextRawData = responseRawData;
        valid = true;
    }

    public void setResponseBinaryRawData (byte[] responseBinaryRawData) {
        this.responseBinaryRawData = responseBinaryRawData;
        valid = true;
    }

    public void setResponseTime (long responseTime) {
        this.responseTime = responseTime;
        valid = true;
    }


    public long getResponseTime () {
        return responseTime;
    }

    public String getReadLength () {
        return readLength;
    }

    public int getResponseCode () {
        return responseCode;
    }

    public String getResponseMessage () {
        return responseMessage;
    }

    public String getResponseTextRawData () {
        return responseTextRawData;
    }

    public byte[] getResponseBinaryRawData () {
        return responseBinaryRawData;
    }

    public Map<String, List<String>> getResponseHeaders () {
        return responseHeaders;
    }

    public boolean isValid () {
        return valid;
    }

    public void printTimeAndReadDetails ()
    {
        System.out.println ("\nread : " + getReadLength () + "  time : " +
                getResponseTime () +" milliSec" );

    }

    public void printHeaders ()
    {
        if (getResponseHeaders () != null)
        {
            List<String> values = getResponseHeaders ().get (null);
            if (values != null) {
                System.out.println (values.get (0));

                for (String key : getResponseHeaders ().keySet ()) {
                    if (key == null)
                        continue;
                    System.out.print (key + " : ");
                    int counter = 0;
                    for (String value : getResponseHeaders ().get (key)) {
                        if (counter == 0)
                            System.out.print (value);
                        else
                            System.out.print (", " + value);
                        counter++;
                    }
                    System.out.println ();
                }
                System.out.println ();
            }
        }

    }

    public void printRawResponse ()
    {
        System.out.println (getResponseTextRawData ());
    }
}
