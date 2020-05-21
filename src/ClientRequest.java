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
    private HashMap<String,ArrayList<String>> queryData;
    private int messageBodyType;


    public ClientRequest (String url, boolean followRedirect) throws MalformedURLException
    {
        this.name = "MyRequest";
        customHeaders = new HashMap<> ();
        formUrlData = new HashMap<> ();
        queryData = new HashMap<> ();
        httpConnection = new HttpConnection (url,followRedirect);
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

    private void addKeyAndValueType (String input,char f1, String f2, String s, String t)
    {
        if (input == null)
            return;
        if (input.toCharArray ()[0] != f1 ||
                input.toCharArray ()[input.length () - 1] != f1)
            return;

        String inputHeadersV2 = input.trim ().replaceAll (f2,"");
        String[] headers = inputHeadersV2.split (s);
        for (String header : headers)
        {
            if (!(header.contains (t)))
                continue;
            String[] keyValue = header.split (t,2);
            if (keyValue.length >= 2)
                addTo (keyValue[0],keyValue[1], customHeaders);
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
        addKeyAndValueType
                (inputHeader,'\"',"\"",";",":");
    }

    public void removeCustomHeader (String key, String value)
    {
        removeFrom (key,value,customHeaders);
    }


    public void addQuery (String query)
    {
        addKeyAndValueType
                (query,'\"',"\"","&","=");
    }

    public void addJSON (String json)
    {
        if (json == null)
            return;
        if (json.toCharArray ()[0] != '\"' || json.toCharArray ()[1] != '{' ||
                json.toCharArray ()[json.length () - 1] != '\"' ||
                json.toCharArray ()[json.length () - 2] != '}')
            return;

        String inputHeadersV2 = json.trim ().substring (2,json.length () - 2);

        String[] headers = inputHeadersV2.split (",");
        for (String header : headers)
        {
            if (!(header.contains (":")))
                continue;
            String[] keyValue = header.split (":",2);
            if (keyValue.length >= 2)
                addTo (keyValue[0],keyValue[1], customHeaders);
        }
    }

    public void addFormUrlData (String inputFormUrl)
    {
        addKeyAndValueType
                (inputFormUrl,'\"',"\"","&","=");
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

        StringBuilder stringBuilder = new StringBuilder ();
        int counter1 = 0;
        for (String key : customHeaders.keySet ())
        {
            if (counter1 == 0)
                stringBuilder.append (key).append (": ");
            else
                stringBuilder.append ("  ").append (key).append (": ");
            counter1++;
            int counter2 = 0;
            for (String value : customHeaders.get (key))
            {
                if (counter2 == 0)
                    stringBuilder.append (value);
                else
                    stringBuilder.append (",").append (value);
                counter2++;
            }
        }

        return "name: " + name + " | " +
                "url: " + httpConnection.getUrl ().toString () + " | " +
                "method: " + httpConnection.getRequestType () + " | " +
                "headers: " + stringBuilder.toString ();
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

    public void setShowHeadersInResponse (boolean showHeadersInResponse)
    {
        httpConnection.setShowHeadersInResponse (showHeadersInResponse);
    }

    public void setShouldSaveOutputInFile (boolean shouldSaveOutputInFile, String nameOfFile)
    {
        if (nameOfFile == null)
        {
            httpConnection.setSaveRawDataOnFile (shouldSaveOutputInFile);
        } else
        {
            httpConnection.setSaveRawDataOnFile (shouldSaveOutputInFile, nameOfFile);
        }
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
}
