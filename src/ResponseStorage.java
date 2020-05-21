import java.io.Serializable;
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
    private boolean showHeadersInResult;

    public ResponseStorage ()
    {
        responseRawData = "Error: URL using bad/illegal format or missing URL";
        readLength = "0";
        responseTime = 0;
        showHeadersInResult = false;
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

    public void setShowHeadersInResult (boolean showHeadersInResult) {
        this.showHeadersInResult = showHeadersInResult;
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

    public void printResult ()
    {

    }

}
