import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ClientRequest implements Serializable, Runnable
{
    private HttpConnection httpConnection;
    private String name;
    private HashMap<String,ArrayList<String>> customHeaders;
    private HashMap<String,ArrayList<String>> formUrlData;
    private int messageBodyType;


    public ClientRequest (String url) throws MalformedURLException
    {
        this.name = "MyRequest";
        customHeaders = new HashMap<> ();
        formUrlData = new HashMap<> ();
        httpConnection = new HttpConnection (url);
        messageBodyType = 1;
    }


    private void addTo (String key, String value, HashMap<String, ArrayList<String>> list)
    {
        if (key == null || value == null || list == null)
            return;
        if (list.containsKey (key))
            list.get (key).add (value);
        else {
            ArrayList<String> values = new ArrayList<> ();
            values.add (value);
            list.put (key,values);
        }
    }

    private void removeFrom (String key, String value, HashMap<String,ArrayList<String>> list)
    {
        if (key == null || value == null || list == null)
            return;
        if (list.containsKey (key))
        {
            ArrayList<String> values = list.get (key);
            values.remove (value);

            if (values.size () <= 0)
                list.remove (key);
        }
    }

    public void addCustomHeader (String inputHeader)
    {
        if (inputHeader == null)
            return;
        if (inputHeader.toCharArray ()[0] != '\"' ||
                inputHeader.toCharArray ()[inputHeader.length () - 1] != '\"')
            return;

        String inputHeadersV2 = inputHeader.trim ().replaceAll ("\"","");
        String[] headers = inputHeadersV2.split (";");
        for (String header : headers)
        {
            if (!(header.contains (":")))
                continue;
            String[] keyValue = header.split (":",2);
            if (keyValue.length >= 2)
                addTo (keyValue[0],keyValue[1], customHeaders);
        }
    }

    public void removeCustomHeader (String key, String value)
    {
        removeFrom (key,value,customHeaders);
    }


    public void addFormUrlData (String inputFormUrl)
    {
        if (inputFormUrl == null)
            return;
        if (inputFormUrl.toCharArray ()[0] != '\"' ||
                inputFormUrl.toCharArray ()[inputFormUrl.length () - 1] != '\"')
            return;

        String inputFormUrlV2 = inputFormUrl.trim ().replaceAll ("\"","");
        String[] bodies = inputFormUrlV2.split ("&");
        for (String body : bodies)
        {
            if (!(inputFormUrl.contains ("=")))
                continue;
            String[] keyValue = body.split ("=",2);
            if (keyValue.length >= 2)
                addTo (keyValue[0],keyValue[1], formUrlData);
        }
    }

    public void removeFormUrlData (String key, String value)
    {
        removeFrom (key,value,formUrlData);
    }


    public byte[] getFormUrlDataByteForServer ()
    {
        StringBuilder data = new StringBuilder ();

        int counter = 0;
        for (String key : formUrlData.keySet ())
        {
            for (String value : formUrlData.get (key))
            {
                if (counter == 0)
                    data.append (key).append ("=").append (value);
                else
                    data.append ("&").append (key).append ("=").append (value);
                counter++;
            }
        }
        return data.toString ().getBytes (StandardCharsets.UTF_8);
    }


    @Override
    public void run ()
    {
        HttpURLConnection connection;
        if ((connection = httpConnection.connectionInitializer (customHeaders)) != null)
        {
            switch (httpConnection.getRequestType ())
            {
                case GET: httpConnection.getMethod (connection); return;
                case POST: httpConnection.postMethod (connection,getFormUrlDataByteForServer (),
                        messageBodyType);
            }
        }
    }


    @Override
    public String toString () {
        return "name: " + name + " | " +
                "url: " + httpConnection.getUrl ().toString () + " | " +
                "method: " + httpConnection.getRequestType () + " | " +
                "headers=" + customHeaders;
    }


    public void setName (String name) {
        this.name = name;
    }


    public void setMessageBodyType (int messageBodyType) {
        this.messageBodyType = messageBodyType;
    }


    public void setFollowRedirect (boolean followRedirection)
    {
        httpConnection.setFollowRedirect (followRedirection);
    }

    public void setUrl (String url) throws MalformedURLException
    {
        httpConnection.setUrl (url);
    }

    public void setRequestType (String requestType)
    {
        if (requestType == null)
        {
            httpConnection.setRequestType (RequestType.GET);
            return;
        }

        switch (requestType)
        {
            case "POST" : httpConnection.setRequestType (RequestType.POST); break;
            case "DELETE" : httpConnection.setRequestType (RequestType.DELETE); break;
            case "PATCH" : httpConnection.setRequestType (RequestType.PATCH); break;
            case "PUT" : httpConnection.setRequestType (RequestType.PUT); break;
            case "GET" :
            default : httpConnection.setRequestType (RequestType.GET);
        }
    }

    public void setShowHeadersInResult (boolean showHeadersInResult)
    {
        httpConnection.getResponseStorage ().setShowHeadersInResult (showHeadersInResult);
    }
}
