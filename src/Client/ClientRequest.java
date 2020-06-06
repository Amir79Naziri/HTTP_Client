package Client;

import Storage.ResponseStorage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.nio.file.Path;
import java.util.*;

public class ClientRequest implements Serializable, Runnable
{
    private URL url;
    private boolean followRedirect;
    private RequestType requestType;
    private HttpConnection httpConnection;
    private String name;
    private HashMap<String,String> customHeaders;
    private HashMap<String,String> formData;
    private HashMap<String,String> formDataEncoded;
    private HashMap<String,String> queryData;
    private File uploadBinaryFile;
    private int messageBodyType;



    public ClientRequest (String url, boolean followRedirect) throws MalformedURLException
    {

        this.url = new URL (url);
        this.name = "MyRequest";
        this.followRedirect = followRedirect;
        requestType = RequestType.GET;
        customHeaders = new HashMap<> ();
        formData = new HashMap<> ();
        formDataEncoded = new HashMap<> ();
        queryData = new HashMap<> ();
        httpConnection = new HttpConnection ();
        messageBodyType = 1;
    }

    public ClientRequest (boolean followRedirect, String name, RequestType requestType)
    {
        this.name = name;
        this.followRedirect = followRedirect;
        customHeaders = new HashMap<> ();
        formData = new HashMap<> ();
        formDataEncoded = new HashMap<> ();
        queryData = new HashMap<> ();
        this.requestType = requestType;
        httpConnection = new HttpConnection ();
        try {
            this.url = new URL ("https://api.myproduct.com/v1/users");
        } catch (MalformedURLException ignore)
        {
        }
        messageBodyType = 1;
    }

    private static void addKeyAndValueType
            (HashMap<String, String> list, String input, String s, String t)
    {
        if (input == null)
            return;
        if (input.toCharArray ()[0] != '\"' ||
                input.toCharArray ()[input.length () - 1] != '\"')
            return;

        String inputHeadersV2 = input.trim ().replaceAll ("\"","").
                replaceAll ("\\s+","");
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
        this.uploadBinaryFile =
                uploadBinaryFile;
    }


    public void addCustomHeader (String inputHeader)
    {
        addKeyAndValueType
                (customHeaders,inputHeader, ";",":");
    }

    public void addCustomHeader (String key, String value)
    {
        customHeaders.put (key,value);
    }



    public HashMap<String, String> getCustomHeaders () {
        return customHeaders;
    }


    public void addQuery (String query)
    {
        addKeyAndValueType
                (queryData,query, "&","=");
    }

    public void addQuery (String key,String value)
    {
        queryData.put (key,value);
    }


    public String getQueryDataString () {
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

    public HashMap<String,String> getQueryData ()
    {
        return queryData;
    }

    public void addFormUrlData (String inputFormUrl)
    {
        addKeyAndValueType
                (formData,inputFormUrl, "&","=");
    }

    public void addFormUrlData (String key, String value)
    {
        formData.put (key,value);
    }



    public HashMap<String,String> getFormData ()
    {
        return formData;
    }


    public void addFormUrlDataEncoded (String inputFormUrlEncoded)
    {
        addKeyAndValueType
                (formDataEncoded,inputFormUrlEncoded, "&","=");
    }

    public void addFormUrlDataEncoded (String key, String value)
    {
        formDataEncoded.put (key,value);
    }


    public String getFormDataEncodedString () {
        int counter = 0;
        StringBuilder stringBuilder = new StringBuilder ();
        for (String key : formDataEncoded.keySet ())
        {
            if (counter == 0)
            {
                stringBuilder.append (key).append ("=").append (formDataEncoded.get (key));
            }
            else
            {
                stringBuilder.append ("&").append (key).append ("=").
                        append (formDataEncoded.get (key));
            }
            counter++;
        }
        return stringBuilder.toString ();
    }

    public HashMap<String, String> getFormDataEncoded () {
        return formDataEncoded;
    }

    public void clearCustomHeaders ()
    {
        customHeaders.clear ();
    }

    public void clearQuery ()
    {
        queryData.clear ();
    }

    public Path getUploadBinaryFilePath () {
        if (uploadBinaryFile != null)
            return uploadBinaryFile.toPath ();
        else
            return null;
    }

    public void clearBody ()
    {
        formData.clear ();
        formDataEncoded.clear ();
        uploadBinaryFile = null;
    }

    @Override
    public void run ()
    {

        HttpURLConnection connection;
        String url = getUrl ();
        while (true)
        {
            if ((connection = httpConnection.connectionInitializer
                    (getCustomHeaders (), getQueryDataString (),url,
                            getRequestType ())) != null)
            {
                try {
                    switch (getRequestType ())
                    {
                        case GET:
                            httpConnection.onlyGet (connection,followRedirect); return;
                        case POST:
                        case PUT:
                        case DELETE:httpConnection.sendAndGet (connection,messageBodyType,
                                getFormData (),uploadBinaryFile,
                                getFormDataEncodedString (),followRedirect);
                    }
                    return;
                } catch (FollowRedirectException e)
                {
                    url = e.getNewUrl ();
                }
            }
        }

    }

    public ResponseStorage getResponseStorage () {
        return httpConnection.getResponseStorage ();
    }

    public int getMessageBodyType () {
        return messageBodyType;
    }


    @Override
    public String toString () {

        return "name: " + name + " | " +
                "url: " + url.toString () + " | " +
                "method: " + getRequestType () + " | " +
                "headers: " + readyForShowInToString (customHeaders) + " | " +
                "Query params: " + readyForShowInToString (queryData);
    }

    private String readyForShowInToString (HashMap<String,String> list)
    {
        if (list == null)
            throw new NullPointerException ("inValid input");
        StringBuilder stringBuilder = new StringBuilder ();
        int counter = 0;
        for (String key : list.keySet ())
        {
            if (counter == 0)
                stringBuilder.append (key).append (": ");
            else
                stringBuilder.append ("  ").append (key).append (": ");
            counter++;

            stringBuilder.append (list.get (key));
        }
        return stringBuilder.toString ();
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setMessageBodyType (int messageBodyType) {
        if (messageBodyType != this.messageBodyType)
        {
            this.messageBodyType = messageBodyType;
            clearBody ();
        }
    }
    

    public void setFollowRedirect (boolean followRedirection)
    {
        this.followRedirect =
                followRedirection;
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
        this.url = new URL (url);
    }

    public String getUrl ()
    {
        return url.toString ();
    }

    public void setRequestType (String requestType)
    {
        if (requestType == null)
        {
            this.requestType = RequestType.GET;
            return;
        }

        switch (requestType)
        {
            case "POST" : this.requestType = RequestType.POST; break;
            case "DELETE" : this.requestType = RequestType.DELETE; break;
            case "PATCH" : this.requestType = RequestType.PATCH; break;
            case "PUT" : this.requestType = RequestType.PUT; break;
            case "GET" :
            default : this.requestType = RequestType.GET;
        }
    }

    public String getName () {
        return name;
    }

    public RequestType getRequestType ()
    {
        return requestType;
    }

    public boolean isShouldSaveOutputInFile ()
    {
        return httpConnection.isSaveRawDataOnFile ();
    }
}
