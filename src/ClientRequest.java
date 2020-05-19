import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ClientRequest implements Runnable , Serializable
{
    private String name;
    private URL url;
    private RequestType requestType; // method of request
    private String responseRawData;
    private Map<String, List<String>> responseHeaders;
    private int responseCode;
    private String responseMessage;
    private HashMap<String,String> customHeaders;
    private StringBuilder queryHeadersString;



    public ClientRequest (String name, RequestType requestType)
    {

        responseRawData = null;
        this.name = name;
        this.requestType = Objects.requireNonNullElse (requestType, RequestType.GET);
        responseMessage = "";
        customHeaders = new HashMap<> ();
        queryHeadersString = new StringBuilder ();
    }


    public void setName (String name) {
        this.name = name;
    }

    public void setRequestType (String requestType) {

        if (requestType == null)
        {
            this.requestType = RequestType.GET;
            return;
        }
        switch (requestType) {
            case "POST":
                this.requestType = RequestType.POST;
                break;
            case "PATCH":
                this.requestType = RequestType.PATCH;
                break;
            case "DELETE":
                this.requestType = RequestType.DELETE;
                break;
            case "PUT":
                this.requestType = RequestType.PUT;
                break;
            case "GET":
            default:
                this.requestType = RequestType.GET;
                break;
        }

    }

    public void setUrl (String url) {
        try {
            this.url = new URL (url + "/");
        } catch (MalformedURLException e)
        {
            e.printStackTrace ();
        }
    }

    public void addHeader (String inputHeader)
    {
        if (inputHeader == null)
            return;
        String[] headers = inputHeader.trim ().split (";");
        for (String header : headers)
        {
            String[] keyValue = header.split (":",2);
            if (keyValue.length >= 2)
                customHeaders.put(keyValue[0],keyValue[1]);
        }
    }

    public void addQueryHeaders (String key, String value)
    {

        if (queryHeadersString.length () == 0)
            queryHeadersString.append ("?").append (key).append ("=").append (value);
        else
            queryHeadersString.append ("&").append (key).append ("=").append (value);
    }

    public void removeQuery (String key, String value)
    {
        String qHeader = "&" + key + "=" + value;
        int start = queryHeadersString.indexOf (qHeader);
    }

    public void removeHeader (String key)
    {
        customHeaders.remove (key);
    }

    @Override
    public void run ()
    {
        HttpURLConnection connection ;
        String address = url.toString ();
        if (queryHeadersString.toString ().length () > 0)
        {
            address = url.toString ();
            address = address + queryHeadersString.toString ();
        }

        System.out.println (address);
        setUrl (address);

        try {

            if (("http").equals (url.getProtocol ()))
                connection = (HttpURLConnection)url.openConnection ();
            else if (("https").equals (url.getProtocol ()))
                connection = (HttpsURLConnection)url.openConnection ();
            else
            {
                System.out.println ("UNDEFINED PROTOCOL");
                return;
            }

            connection.setRequestMethod (requestType.toString ());

            //add custom headers
            for (String key : customHeaders.keySet ())
                connection.setRequestProperty (key,customHeaders.get (key));


            connection.connect ();

            if (connection.getResponseCode () != 200)
            {
                responseMessage = connection.getResponseMessage ();
                responseCode = connection.getResponseCode ();
                return;
            }

            responseHeaders = connection.getHeaderFields ();
            responseCode = connection.getResponseCode ();
            responseMessage = connection.getResponseMessage ();
            System.out.println (connection.getExpiration ());


        } catch (IOException e)
        {
            System.out.println ("Failed to Connect");
            return;
        }


        try(Scanner in =
                new Scanner (connection.getInputStream ()))
        {

            StringBuilder content = new StringBuilder ();
            while (in.hasNext ())
            {
                content.append (in.nextLine ()).append ('\n');
            }

            responseRawData = content.toString ();
            System.out.println (responseCode + " " + responseMessage );
            for (String key : responseHeaders.keySet ())
                System.out.println (key + " " + responseHeaders.get (key));
            System.out.println (responseRawData);


        } catch (IOException e)
        {
            e.printStackTrace ();
        }

    }

    public String getName () {
        return name;
    }

    @Override
    public String toString () {
        return "name: " + name + " | " +
                "url: " + url + " | " +
                "method: " + requestType + " | " +
                "headers=" + customHeaders + " | " +
                "queryHeadersString=" + queryHeadersString;
    }
}
