import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class HttpConnection implements Serializable
{


    private URL url;
    private ResponseStorage responseStorage;
    private RequestType requestType;
    private boolean followRedirect;
    private boolean showHeadersInResponse;
    private boolean saveRawDataOnFile;
    private String nameOfFileForSaveOutput;


    public HttpConnection (String url, boolean followRedirect) throws MalformedURLException
    {
        this.url = new URL (url);
        this.requestType = RequestType.GET;
        this.followRedirect = followRedirect;
        showHeadersInResponse = false;
        responseStorage = new ResponseStorage ();
    }


    public HttpURLConnection connectionInitializer (HashMap<String, ArrayList<String>> headers)
    {
        try {
            HttpURLConnection connection;
            if (("http").equals (url.getProtocol ()))
                connection = (HttpURLConnection) url.openConnection ();
            else if (("https").equals (url.getProtocol ()))
                connection = (HttpsURLConnection) url.openConnection ();
            else {
                System.err.println ("UNDEFINED PROTOCOL");
                return null;
            }

            if (followRedirect)
                connection.setInstanceFollowRedirects (true);
            else
                connection.setInstanceFollowRedirects (false);

            connection.setRequestMethod (requestType.toString ());

            //add headers
            for (String key : headers.keySet ())
                for (String value : headers.get (key))
                    connection.setRequestProperty (key,value);

            return connection;
        } catch(IOException e) {
            System.err.println ("Failed to start Connecting");
            responseStorage.printTimeAndReadDetails ();
            return null;
        }
    }

    private boolean connectToServer (HttpURLConnection connection) {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        try {
            responseStorage.reset ();
            connection.connect ();
            return true;
        }
        catch (IOException e)
        {
            System.err.println ("Failed to Connect");
            responseStorage.printTimeAndReadDetails ();
            return false;
        }
    }

    private void readFromServer (HttpURLConnection connection) {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        try {
            responseStorage.setResponseCode (connection.getResponseCode ());
            responseStorage.setResponseMessage (connection.getResponseMessage ());

            responseStorage.setResponseHeaders (connection.getHeaderFields ());


            Scanner in = new Scanner (connection.getInputStream ());
            StringBuilder content = new StringBuilder ();
            while (in.hasNext ()) {
                content.append (in.nextLine ()).append ('\n');
            }
            in.close ();
            responseStorage.setResponseRawData (content.toString ());



        }catch (IOException ignore)
        {
            responseStorage.setResponseRawData ("Error: URL using bad/illegal format or missing URL");
        }
    }

    private void writeToServer (HttpURLConnection connection, byte[] bytes)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        try (BufferedOutputStream out = new BufferedOutputStream (connection.getOutputStream ()))
        {
            out.write (bytes);
            out.flush ();

        } catch (IOException e) {
            e.printStackTrace ();
            System.out.println ("some thing went wrong in writing to server");
        }
    }

    public void getMethod (HttpURLConnection connection)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        long startTime = System.currentTimeMillis ();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        if (connectToServer (connection))
        {
            // reading
            readFromServer (connection);
        }
        responseStorage.setResponseTime ((System.currentTimeMillis () - startTime));

        disconnectServer (connection);
    }

    public void postMethod (HttpURLConnection connection, byte[] bytes, int messageBodyType)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        long startTime = System.currentTimeMillis ();
        connection.setDoOutput (true);
        connection.setDoInput (true);

        if (messageBodyType == 1)
        {
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            if (connectToServer (connection))
            {
                //writing
                 writeToServer (connection, bytes);

                // reading
                readFromServer (connection);
            }
        }
        // TODO : Add JSON and Binary
        responseStorage.setResponseTime ((System.currentTimeMillis () - startTime));
        disconnectServer (connection);
    }

    private void disconnectServer (HttpURLConnection connection)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        printResult ();
        if (saveRawDataOnFile)
            responseStorage.saveRawData (nameOfFileForSaveOutput);
        connection.disconnect ();
    }

    public void setRequestType (RequestType requestType) {
        this.requestType = requestType;
    }


    public void setUrl (String url) throws MalformedURLException
    {
        this.url = new URL (url);
    }

    public void setSaveRawDataOnFile (boolean saveRawDataOnFile) {
        this.saveRawDataOnFile = saveRawDataOnFile;
        this.nameOfFileForSaveOutput = "Output_" + new SimpleDateFormat (
                "yyyy.MM.dd  HH.mm.ss").format (new Date ()) + ".html";
    }

    public void setSaveRawDataOnFile (boolean saveRawDataOnFile, String nameOfFile) {
        this.saveRawDataOnFile = saveRawDataOnFile;
        this.nameOfFileForSaveOutput = nameOfFile;
    }


    public void setShowHeadersInResponse (boolean showHeadersInResponse) {
        this.showHeadersInResponse = showHeadersInResponse;
    }

    public void setFollowRedirect (boolean followRedirect) {
        this.followRedirect = followRedirect;
    }

    public URL getUrl () {
        return url;
    }

    public ResponseStorage getResponseStorage () {
        return responseStorage;
    }

    public RequestType getRequestType () {
        return requestType;
    }

    private void printResult ()
    {
        responseStorage.printTimeAndReadDetails ();
        System.out.println ();
        System.out.println (url);
        if (showHeadersInResponse)
            responseStorage.printHeaders ();
        responseStorage.printRawResponse ();
    }
}
