package Client;

import Storage.ResponseStorage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

/**
 * this class represents a ClientRequest
 *
 * @author Amir Naziri
 */
public class ClientRequest implements Serializable, Runnable
{
    private URL url;
    private ResponseStorage responseStorage;
    private boolean followRedirect;
    private RequestType requestType;
    private String name;
    private HashMap<String,String> customHeaders;
    private HashMap<String,String> formUrlData; // multiPart
    private HashMap<String,String> formUrlDataEncoded; // urlEncoded
    private HashMap<String,String> queryData;
    private File uploadBinaryFile; // upload file
    private boolean shouldSaveResponseOnFile; // should save result in file
    private String addressOfFileForSaveOutput; // address of file for save result
    private boolean showHeadersInResponse;
    private int messageBodyType; // 1 means multiPart  2 means Binary file
                                 // 3 means urlEncoded


    /**
     * creates a new Client request
     * @param url url
     * @param followRedirect followRedirect
     * @throws MalformedURLException when url is not valid
     */
    public ClientRequest (String url, boolean followRedirect)
            throws MalformedURLException
    {

        this.url = new URL (url);
        this.name = "MyRequest";
        this.followRedirect = followRedirect;
        requestType = RequestType.GET;
        customHeaders = new HashMap<> ();
        formUrlData = new HashMap<> ();
        formUrlDataEncoded = new HashMap<> ();
        queryData = new HashMap<> ();
        responseStorage = new ResponseStorage ();
        messageBodyType = 1;
        shouldSaveResponseOnFile = false;
        addressOfFileForSaveOutput = null;
        showHeadersInResponse = false;
    }

    /**
     * creates a new Client request
     * @param followRedirect followRedirect
     * @param name name of request
     * @param requestType requestType
     */
    public ClientRequest (boolean followRedirect, String name,
                          RequestType requestType)
    {
        this.name = name;
        this.followRedirect = followRedirect;
        customHeaders = new HashMap<> ();
        formUrlData = new HashMap<> ();
        formUrlDataEncoded = new HashMap<> ();
        queryData = new HashMap<> ();
        this.requestType = requestType;
        responseStorage = new ResponseStorage ();
        shouldSaveResponseOnFile = false;
        addressOfFileForSaveOutput = null;
        showHeadersInResponse = false;
        try {
            this.url = new URL ("https://api.myproduct.com/v1/users");
        } catch (MalformedURLException ignore)
        {
        }
        messageBodyType = 1;
    }

    /**
     * parse the argument of headers , query, bodies ; if they are all in a String
     * @param map map of headers , query , bodies
     * @param input input String
     * @param s separator for different keyValues from each other in input
     * @param t separator for  key and value in a keyValue in input
     */
    private static void addKeyAndValueType
            (HashMap<String, String> map, String input, String s, String t)
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
                map.put (keyValue[0],keyValue[1]);
        }
    }

    /**
     * add a binaryFile
     * @param uploadBinaryFile binaryFile
     */
    public void addUploadBinaryFile (File uploadBinaryFile) {
        this.uploadBinaryFile =
                uploadBinaryFile;
    }

    /**
     * add custom header
     * @param inputHeader input header
     */
    public void addCustomHeader (String inputHeader)
    {
        addKeyAndValueType
                (customHeaders,inputHeader, ";",":");
    }

    /**
     * add custom header
     * @param key key
     * @param value value
     */
    public void addCustomHeader (String key, String value)
    {
        customHeaders.
                put (key,value);
    }

    /**
     * @return map of custom headers
     */
    public HashMap<String, String> getCustomHeaders () {
        return customHeaders;
    }

    /**
     * add query
     * @param query input query
     */
    public void addQuery (String query)
    {
        addKeyAndValueType
                (queryData,query, "&","=");
    }

    /**
     * add query
     * @param key key
     * @param value value
     */
    public void addQuery (String key,String value)
    {
        queryData.put (key,value);
    }

    /**
     * get Queries in form of a String for appending to Url
     * @return query String
     */
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

    /**
     * @return map of queries
     */
    public HashMap<String,String> getQueryData ()
    {
        return queryData;
    }

    /**
     * add multiPartData (form url data)
     * @param inputFormUrl input multiPartData
     */
    public void addFormUrlData (String inputFormUrl)
    {
        addKeyAndValueType
                (formUrlData,inputFormUrl, "&","=");
    }

    /**
     * add multiPartData (form url data)
     * @param key key
     * @param value value
     */
    public void addFormUrlData (String key, String value)
    {
        formUrlData.
                put (key,value);
    }

    /**
     * @return map multipart (form url data)
     */
    public HashMap<String,String> getFormUrlData ()
    {
        return formUrlData;
    }

    /**
     * add Form Url Data Encoded
     * @param inputFormUrlEncoded input FormUrlEncoded
     */
    public void addFormUrlDataEncoded (String inputFormUrlEncoded)
    {
        addKeyAndValueType
                (formUrlDataEncoded,inputFormUrlEncoded, "&","=");
    }

    /**
     * add Form Url Data Encoded
     * @param key key
     * @param value value
     */
    public void addFormUrlDataEncoded (String key, String value)
    {
        formUrlDataEncoded.
                put (key,value);
    }

    /**
     * @return String of formUrlEncoded for writing on server
     */
    public String getFormUrlDataEncodedString () {
        int counter = 0;
        StringBuilder stringBuilder = new StringBuilder ();
        for (String key : formUrlDataEncoded.keySet ())
        {
            if (counter == 0)
            {
                stringBuilder.append (key).append ("=").append (formUrlDataEncoded.get (key));
            }
            else
            {
                stringBuilder.append ("&").append (key).append ("=").
                        append (formUrlDataEncoded.get (key));
            }
            counter++;
        }
        return stringBuilder.toString ();
    }

    /**
     * @return map of FormUrlDataEncoded
     */
    public HashMap<String, String> getFormUrlDataEncoded ()
    {
        return formUrlDataEncoded;
    }

    /**
     * clear headers map
     */
    public void clearCustomHeaders ()
    {
        customHeaders.clear ();
    }

    /**
     * clear query map
     */
    public void clearQuery ()
    {
        queryData.clear ();
    }

    /**
     * @return path of upload binary file
     */
    public Path getUploadBinaryFilePath () {
        if (uploadBinaryFile != null)
            return uploadBinaryFile.toPath ();
        else
            return null;
    }

    /**
     * clears multipart , formUrlEncoded, and make binary file null
     */
    public void clearBody ()
    {
        formUrlData.clear ();
        formUrlDataEncoded.clear ();
        uploadBinaryFile = null;
    }

    /**
     * send request
     */
    @Override
    public void run ()
    {
        HttpConnection httpConnection = new HttpConnection (responseStorage);
        String url = getUrl ();

        while (true)
        {
            if (httpConnection.connectionInitializer
                    (getCustomHeaders (), getQueryDataString (),url,
                            getRequestType ()))
            {
                try {
                    switch (getRequestType ())
                    {
                        case GET:
                            httpConnection.onlyGet (followRedirect,
                                    shouldSaveResponseOnFile,addressOfFileForSaveOutput);
                            break;
                        case POST:
                        case PUT:
                        case DELETE:httpConnection.sendAndGet (messageBodyType,
                                getFormUrlData (),uploadBinaryFile,
                                getFormUrlDataEncodedString (),followRedirect,
                                shouldSaveResponseOnFile,addressOfFileForSaveOutput);
                    }
                    printResult (url);
                    return;
                } catch (FollowRedirectException e)
                {
                    url = e.getNewUrl ();
                }
            }
            else
                return;
        }


    }

    /**
     * print result
     * @param url url
     */
    private synchronized void printResult (String url)
    {
        responseStorage.printTimeAndReadDetails ();
        System.out.println ();
        System.out.println (url);
        if (showHeadersInResponse)
            responseStorage.printHeaders ();
        responseStorage.printRawResponse ();
    }

    /**
     * @return response storage
     */
    public ResponseStorage getResponseStorage () {
        return responseStorage;
    }

    /**
     * @return messageBody type
     */
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

    /**
     * ready the headers and queries for showing on toString
     * @param map input  headers, queries
     * @return string in special format for showing in toString
     */
    private String readyForShowInToString (HashMap<String,String> map)
    {
        if (map == null)
            throw new NullPointerException ("inValid input");
        StringBuilder stringBuilder = new StringBuilder ();
        int counter = 0;
        for (String key : map.keySet ())
        {
            if (counter == 0)
                stringBuilder.append (key).append (": ");
            else
                stringBuilder.append ("  ").append (key).append (": ");
            counter++;

            stringBuilder.append (map.get (key));
        }
        return stringBuilder.toString ();
    }

    /**
     * set a name for Client request
     * @param name name
     */
    public void setName (String name) {
        this.name = name;
    }

    /**
     * set a messageType this will clear body
     * @param messageBodyType input messageBodyType
     */
    public void setMessageBodyType (int messageBodyType) {
        if (messageBodyType != this.messageBodyType)
        {
            this.messageBodyType = messageBodyType;
            clearBody ();
        }
    }

    /**
     * set followRedirection
     * @param followRedirection followRedirection
     */
    public void setFollowRedirect (boolean followRedirection)
    {
        this.followRedirect =
                followRedirection;
    }

    /**
     * show headers in response
     * @param showHeadersInResponse showHeadersInResponse
     */
    public void setShowHeadersInResponse (boolean showHeadersInResponse)
    {
        this.showHeadersInResponse = showHeadersInResponse;
    }

    /**
     * set should save output in file
     * @param shouldSaveOutputInFile shouldSaveOutputInFile
     * @param nameOfFile nameOfFile (it can be null)
     */
    public void setShouldSaveOutputInFile (boolean shouldSaveOutputInFile,
                                           String nameOfFile)
    {
        if (nameOfFile == null)
        {
            this.shouldSaveResponseOnFile = shouldSaveOutputInFile;
            this.addressOfFileForSaveOutput = null;
        } else
        {
            this.shouldSaveResponseOnFile = shouldSaveOutputInFile;
            this.addressOfFileForSaveOutput = "./data/RawData/" + nameOfFile;
        }
    }

    /**
     * set Url
     * @param url url
     * @throws MalformedURLException when a url is not Valid
     */
    public void setUrl (String url) throws MalformedURLException
    {
        this.url = new URL (url);
    }

    /**
     * @return url
     */
    public String getUrl ()
    {
        return url.toString ();
    }

    /**
     * set a request Type
     * @param requestType requestType
     */
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

    /**
     * @return name of request
     */
    public String getName () {
        return name;
    }

    /**
     * @return request type
     */
    public RequestType getRequestType ()
    {
        return requestType;
    }

    /**
     * @return is Should save output In file
     */
    public boolean isShouldSaveOutputInFile ()
    {
        return shouldSaveResponseOnFile;
    }
}
