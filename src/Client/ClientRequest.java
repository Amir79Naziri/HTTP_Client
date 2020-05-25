package Client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ClientRequest implements Serializable, Runnable
{
    private HttpConnection httpConnection;
    private String name;
    private HashMap<String,String> customHeaders;
    private HashMap<String,String> formData;
    private HashMap<String,String> queryData;
    private File uploadBinaryFile;
    private int messageBodyType;


    public ClientRequest (String url, boolean followRedirect) throws MalformedURLException
    {
        this.name = "MyRequest";
        customHeaders = new HashMap<> ();
        formData = new HashMap<> ();
        queryData = new HashMap<> ();
        httpConnection = new HttpConnection (url,followRedirect);
        messageBodyType = 1;
    }


    public ClientRequest (boolean followRedirect, String name, RequestType requestType)
    {
        this.name = name;

        customHeaders = new HashMap<> ();
        formData = new HashMap<> ();
        queryData = new HashMap<> ();
        try {
            httpConnection = new HttpConnection (followRedirect,requestType);
        } catch (MalformedURLException ignore)
        {
        }
        messageBodyType = 1;
    }


    private void addKeyAndValueType (HashMap<String,String> list,
                                     String input,char f1, String f2, String s, String t)
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
                list.put (keyValue[0],keyValue[1]);
        }
    }

    public void addUploadBinaryFile (File uploadBinaryFile) {
        this.uploadBinaryFile = uploadBinaryFile;
    }

    public void addCustomHeader (String inputHeader)
    {
        addKeyAndValueType
                (customHeaders,inputHeader,'\"',"\"",";",":");
    }

    public void removeCustomHeader (String key)
    {
        customHeaders.remove (key);
    }

    public HashMap<String,String> getFormData ()
    {
        return formData;
    }


    public void addQuery (String query)
    {
        addKeyAndValueType
                (queryData,query,'\"',"\"","&","=");
    }

    public void removeQuery (String key)
    {
        queryData.remove (key);
    }


    public String getQueryData () {
        int counter = 0;
        StringBuilder stringBuilder = new StringBuilder ();
        for (String key : queryData.keySet ())
        {
            if (counter == 0)
            {
                stringBuilder.append ("?").append (key).append ("=").append (queryData.get (key));
            }
            else
            {
                stringBuilder.append ("&").append (key).append ("=").append (queryData.get (key));
            }
            counter++;
        }
        return stringBuilder.toString ();
    }


    public void addFormUrlData (String inputFormUrl)
    {
        addKeyAndValueType
                (formData,inputFormUrl,'\"',"\"","&","=");
    }

    public void removeFormData (String key)
    {
        formData.remove (key);
    }







    @Override
    public void run ()
    {

        try {
            httpConnection.setUrl (httpConnection.getUrl () + "" + getQueryData ());
        } catch (MalformedURLException ignore) {
            System.out.println ("wrong Url");
        }

        HttpURLConnection connection;
        if ((connection = httpConnection.connectionInitializer (customHeaders)) != null)
        {
            switch (httpConnection.getRequestType ())
            {
                case GET: httpConnection.get (connection); return;
                case POST:
                case PUT:
                case DELETE:httpConnection.sendAndGet (connection,messageBodyType,
                        getFormData (),uploadBinaryFile);
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

            stringBuilder.append (customHeaders.get (key));
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
