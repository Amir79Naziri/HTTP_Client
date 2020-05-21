import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class HttpConnection implements Serializable
{


    private URL url;
    private ResponseStorage responseStorage;
    private RequestType requestType;
    private boolean followRedirect;


    public HttpConnection (String url) throws MalformedURLException
    {
        this.url = new URL (url);
        this.requestType = RequestType.GET;
        followRedirect = false;
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
            return null;
        }
    }

    private boolean connectToServer (HttpURLConnection connection) {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        try {
            connection.connect ();
            return true;
        }
        catch (IOException e)
        {
            System.err.println ("Failed to Connect");
            return false;
        }
    }

    private boolean readFromServer (HttpURLConnection connection) {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        try {
            responseStorage.setResponseCode (connection.getResponseCode ());
            responseStorage.setResponseMessage (connection.getResponseMessage ());

            if (connection.getResponseCode () != HttpURLConnection.HTTP_OK) {

                responseStorage.setResponseRawData
                        ("Error: URL using bad/illegal format or missing URL");
                return false;
            }

            responseStorage.setResponseHeaders (connection.getHeaderFields ());

            Scanner in = new Scanner (connection.getInputStream ());
            StringBuilder content = new StringBuilder ();
            while (in.hasNext ()) {
                content.append (in.nextLine ()).append ('\n');
            }
            in.close ();
            responseStorage.setResponseRawData (content.toString ());

            return true;

        }catch (IOException e)
        {
            System.out.println ("some thing went wrong in reading from server");
            return false;
        }
    }

    private boolean writeToServer (HttpURLConnection connection, byte[] bytes)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        try (BufferedOutputStream out = new BufferedOutputStream (connection.getOutputStream ()))
        {
            out.write (bytes);
            out.flush ();
            return true;
        } catch (IOException e) {
            System.out.println ("some thing went wrong in writing to server");
            return false;
        }
    }

    public void getMethod (HttpURLConnection connection)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        long startTime = System.currentTimeMillis ();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        boolean success = false;
        if (connectToServer (connection))
        {
            // reading
            success = readFromServer (connection);
        }
        responseStorage.setResponseTime ((System.currentTimeMillis () - startTime));
        connection.disconnect ();
    }

    public void postMethod (HttpURLConnection connection, byte[] bytes, int messageBodyType)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        long startTime = System.currentTimeMillis ();
        connection.setDoOutput (true);
        connection.setDoInput (true);
        boolean result1 = false;
        boolean result2 = false;
        if (messageBodyType == 1)
        {
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            if (connectToServer (connection))
            {
                //writing
                result1 = writeToServer (connection, bytes);

                // reading
                result2 = readFromServer (connection);
            }
        }
        // TODO : Add JSON and Binary
        responseStorage.setResponseTime ((System.currentTimeMillis () - startTime));
        connection.disconnect ();
    }





    public void setRequestType (RequestType requestType) {
        this.requestType = requestType;
    }

    public void setUrl (String url) throws MalformedURLException
    {
        this.url = new URL (url);
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
}
