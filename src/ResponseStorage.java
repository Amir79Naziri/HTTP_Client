import java.io.*;
import java.util.List;
import java.util.Map;

public class ResponseStorage implements Serializable
{
    private String responseRawData;
    private int responseCode;
    private String responseMessage;
    private Map<String, List<String>> responseHeaders;
    private String readLength;
    private long responseTime;

    public ResponseStorage ()
    {
        reset ();
    }

    public void reset ()
    {
        responseRawData = "Error: URL using bad/illegal format or missing URL";
        readLength = "0";
        responseTime = 0;
    }

    public void setResponseMessage (String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setReadLength (String readLength) {
        this.readLength = readLength;
    }

    public void setResponseCode (int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseHeaders (Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public void setResponseRawData (String responseRawData) {
        this.responseRawData = responseRawData;
        setReadLength (BinaryFilePanel.
                makeSizeReadable (getResponseRawData ().getBytes ().length));
    }

    public void setResponseTime (long responseTime) {
        this.responseTime = responseTime;
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

    public String getResponseRawData () {
        return responseRawData;
    }

    public Map<String, List<String>> getResponseHeaders () {
        return responseHeaders;
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
        System.out.println (getResponseRawData ());
    }

    public void saveRawData (String fileName)
    {

        try (BufferedOutputStream in = new BufferedOutputStream (new FileOutputStream (
                "./data/RawData/" + fileName))){
            in.write (responseRawData.getBytes ());
            in.flush ();
        } catch (IOException e)
        {
            System.out.println ("some thing went wrong in saving rawData");
        }
    }

}