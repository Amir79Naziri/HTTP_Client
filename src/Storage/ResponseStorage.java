package Storage;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * this class represents a storage which holds responses for owner clientRequest
 *
 * @author Amir Naziri
 */
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

    /**
     * creates a new Response Storage
     */
    public ResponseStorage ()
    {
        valid = false;
        reset ();
    }

    /**
     * reset everyThing
     */
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

    /**
     * set response message
     * @param responseMessage responseMessage
     */
    public void setResponseMessage (String responseMessage) {
        this.responseMessage = responseMessage;
        valid = true;
    }

    /**
     * sets readLength
     * @param readLength readLength
     */
    public void setReadLength (long readLength) {

        this.readLength = makeSizeReadable (readLength);
        valid = true;
    }

    /**
     * sets response code
     * @param responseCode responseCode
     */
    public void setResponseCode (int responseCode) {
        this.responseCode = responseCode;
        valid = true;
    }

    /**
     * sets response headers
     * @param responseHeaders responseHeaders
     */
    public void setResponseHeaders (Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
        valid = true;
    }

    /**
     * sets response text raw data
     * @param responseRawData responseRawData
     */
    public void setResponseTextRawData (String responseRawData) {
        this.responseTextRawData = responseRawData;
        valid = true;
    }

    /**
     * sets response binary raw data
     * @param responseBinaryRawData responseBinaryRawData
     */
    public void setResponseBinaryRawData (byte[] responseBinaryRawData) {
        this.responseBinaryRawData = responseBinaryRawData;
        valid = true;
    }

    /**
     * sets response time
     * @param responseTime responseTime
     */
    public void setResponseTime (long responseTime) {
        this.responseTime = responseTime;
        valid = true;
    }

    /**
     * @return responseTime
     */
    public long getResponseTime () {
        return responseTime;
    }

    /**
     * @return readLength
     */
    public String getReadLength () {
        return readLength;
    }

    /**
     * @return responseCode
     */
    public int getResponseCode () {
        return responseCode;
    }

    /**
     * @return responseMessage
     */
    public String getResponseMessage () {
        return responseMessage;
    }

    /**
     * @return responseTextRawData
     */
    public String getResponseTextRawData () {
        return responseTextRawData;
    }

    /**
     * @return responseBinaryRawData
     */
    public byte[] getResponseBinaryRawData () {
        return responseBinaryRawData;
    }

    /**
     * @return responseHeaders
     */
    public Map<String, List<String>> getResponseHeaders () {
        return responseHeaders;
    }

    /**
     * @return isValid
     */
    public boolean isValid () {
        return valid;
    }

    /**
     * print time and read length
     */
    public void printTimeAndReadDetails ()
    {
        System.out.println ("\nread : " + getReadLength () + "  time : " +
                getResponseTime () +" milliSec" );

    }

    /**
     * print response headers
     */
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

    /**
     * print text raw data
     */
    public void printRawResponse ()
    {
        System.out.println (getResponseTextRawData ());
        System.out.println ();
    }

    /**
     * change the the data size to humanReadable form
     * @param size input size
     * @return humanReadable form
     */
    public static String makeSizeReadable (long size)
    {
        if (size < 1024)
            return size + " B";

        String[] scales = {"k", "M", "G"};
        int i = -1;
        while (size > 1024)
        {
            size /= 1024;
            i++;
        }
        float newSize = (size / 1f);
        return String.format ("%.1f %s",newSize,scales[i]);

    }
}
