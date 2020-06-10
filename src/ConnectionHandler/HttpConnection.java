package ConnectionHandler;

import Client.RequestType;
import Storage.ResponseStorage;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * this class represents a HttpConnector for connecting to server , read or write on it
 *
 * @author Amir Naziri
 */
public class HttpConnection
{

    private ResponseStorage responseStorage;
    private HttpURLConnection connection;

    /**
     * creates new HttpConnection
     * @param responseStorage responseStorage
     */
    protected HttpConnection (ResponseStorage responseStorage)
    {
        this.
                responseStorage = responseStorage;
    }

    /**
     * initialize connection to input url
     * includes ; setting query, headers, check protocol, ...
     * @param headers headers
     * @param queryData queryData
     * @param url url
     * @param requestType requestType
     * @return was successFull
     */
    protected boolean connectionInitializer
            (HashMap<String, String> headers, String queryData, String url,
             RequestType requestType)
    {

        try {
            URL connectionUrl = new URL (url + "" + queryData);

            if (("http").equals (connectionUrl.getProtocol ()))
                connection = (HttpURLConnection) connectionUrl.openConnection ();
            else if (("https").equals (connectionUrl.getProtocol ()))
                connection = (HttpsURLConnection) connectionUrl.openConnection ();
            else {
                System.err.println ("UNDEFINED PROTOCOL");
                return false;
            }


            connection.setInstanceFollowRedirects (false);

            connection.setConnectTimeout (45000);



            connection.setRequestMethod (requestType.toString ());

            //add headers
            for (String key : headers.keySet ())
                    connection.setRequestProperty (key,headers.get (key));


            System.out.println ("Connection Initialized in : " + connection.getURL () +
                    "\nconnecting......");
            return true;
        }
        catch (MalformedURLException e)
        {
            System.err.println ("Wrong url format");
            return false;
        }
        catch(IOException e) {
            System.err.println ("Failed to start Connecting");
            return false;
        }
    }

    /**
     * connects to server
     * @return was successFull
     */
    private boolean connectToServer () {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        try {
            responseStorage.reset ();
            connection.connect ();
            return true;
        }
        catch (IOException | IllegalArgumentException e)
        {
            System.err.println ("Failed to Connect");
            return false;
        }
    }

    /**
     * disconnect from server
     */
    private void disconnectServer ()
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        connection.disconnect ();
        connection = null;
    }

    /**
     * send a request which only want to receive from server
     * @param followRedirect followRedirect
     * @param shouldSaveResponseOnFile shouldSaveResponseOnFile
     * @param addressOfFileForSaveOutput addressOfFileForSaveOutput
     * @throws FollowRedirectException need to follow redirect
     */
    protected void onlyGet (boolean followRedirect,
                         boolean shouldSaveResponseOnFile,
                         String addressOfFileForSaveOutput
                         ) throws FollowRedirectException
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        long startTime = System.currentTimeMillis ();

        if (connectToServer ())
        {
            // reading
            readFromServer (followRedirect,
                    shouldSaveResponseOnFile,addressOfFileForSaveOutput);
        }
        responseStorage.setResponseTime ((System.currentTimeMillis () - startTime));

        disconnectServer ();
    }

    /**
     * send a request which has both write to server and read from server
     * @param messageBodyType messageBodyType ; 1 multiPart, 2 binary upload, 3 formUrlEncoded
     * @param multipartData multipartData
     * @param binaryFileUpload binaryFileUpload
     * @param formUrlEncodedData formUrlEncodedData
     * @param followRedirect followRedirect
     * @param shouldSaveResponseOnFile shouldSaveResponseOnFile
     * @param addressOfFileForSaveOutput addressOfFileForSaveOutput
     * @throws FollowRedirectException need to follow redirect
     */
    protected void sendAndGet (int messageBodyType,
                            HashMap<String,String> multipartData,
                            File binaryFileUpload, String formUrlEncodedData,
                            boolean followRedirect,
                            boolean shouldSaveResponseOnFile,
                            String addressOfFileForSaveOutput
                            ) throws FollowRedirectException
    {

        long startTime = System.currentTimeMillis ();
        connection.setDoOutput (true);
        connection.setDoInput (true);
        String boundary = "";
        if (messageBodyType == 1) {
            boundary = System.currentTimeMillis () + "";
            connection.setRequestProperty ("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
        } else if (messageBodyType == 2)
        {
            if (binaryFileUpload == null || !binaryFileUpload.exists () ||
                    !binaryFileUpload.isAbsolute ()) {
                {
                    System.err.println ("File is not Valid");
                    return;
                }
            }
            connection.setRequestProperty("Content-Type", "application/octet-stream");
        } else if (messageBodyType == 3)
        {
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.
                    toString(formUrlEncodedData.getBytes (StandardCharsets.UTF_8).length));
        }

        if (connectToServer ())
        {
            //writing
            writeToServer (messageBodyType,multipartData,binaryFileUpload,boundary,
                    formUrlEncodedData);

            // reading
            readFromServer (followRedirect,
                    shouldSaveResponseOnFile,addressOfFileForSaveOutput);
        }


        responseStorage.setResponseTime ((System.currentTimeMillis () - startTime));
        disconnectServer ();
    }


    /**
     * read from server
     * @param followRedirect followRedirect
     * @param shouldSaveResponseOnFile shouldSaveResponseOnFile
     * @param addressOfFileForSaveOutput addressOfFileForSaveOutput
     * @throws FollowRedirectException need to follow redirect
     */
    private void readFromServer (boolean followRedirect,
                                 boolean shouldSaveResponseOnFile,
                                 String addressOfFileForSaveOutput
                                 ) throws FollowRedirectException
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        try {
            responseStorage.setResponseCode (connection.getResponseCode ());
            responseStorage.setResponseMessage (connection.getResponseMessage ());
            responseStorage.setResponseHeaders (connection.getHeaderFields ());

            if (followRedirect &&
                    (responseStorage.getResponseCode () ==
                            HttpURLConnection.HTTP_SEE_OTHER ||
                            responseStorage.getResponseCode () ==
                                    HttpURLConnection.HTTP_MOVED_TEMP ||
                            responseStorage.getResponseCode () ==
                                    HttpURLConnection.HTTP_MOVED_PERM))
            {
                String newURL = connection.getHeaderField ("Location");
                connection.disconnect ();
                throw new FollowRedirectException (newURL);
            }



            String contentType = "text/html";
            if (connection.getContentType () != null)
                 contentType = connection.getContentType ().split (";")[0];

            switch (contentType) {
                case "text/html":
                    if (shouldSaveResponseOnFile && addressOfFileForSaveOutput == null) {
                        addressOfFileForSaveOutput = "./data/RawData/Output_" +
                                new SimpleDateFormat (
                                "yyyy.MM.dd  HH.mm.ss").format (new Date ()) + ".html";
                    }
                    textReader (connection.getInputStream (),
                            shouldSaveResponseOnFile,addressOfFileForSaveOutput);
                    break;
                case "image/png":
                    if (shouldSaveResponseOnFile && addressOfFileForSaveOutput == null) {
                        addressOfFileForSaveOutput = "./data/RawData/Output_" +
                                new SimpleDateFormat (
                                "yyyy.MM.dd  HH.mm.ss").format (new Date ()) + ".png";
                    }
                    if (!shouldSaveResponseOnFile)
                            System.out.println ("you should use --output!");

                        binaryReader (connection.getInputStream (),
                                shouldSaveResponseOnFile,addressOfFileForSaveOutput);


                    responseStorage.setResponseTextRawData ("File is Binary !");
                    break;
                case "text/plain":
                    if (shouldSaveResponseOnFile && addressOfFileForSaveOutput == null) {
                        addressOfFileForSaveOutput = "./data/RawData/Output_" +
                                new SimpleDateFormat (
                                "yyyy.MM.dd  HH.mm.ss").format (new Date ()) + ".txt";
                    }
                    textReader (connection.getInputStream (),
                            shouldSaveResponseOnFile,addressOfFileForSaveOutput);
                    break;
                case "application/json":
                    if (shouldSaveResponseOnFile && addressOfFileForSaveOutput == null) {
                        addressOfFileForSaveOutput = "./data/RawData/Output_" +
                                new SimpleDateFormat (
                                        "yyyy.MM.dd  HH.mm.ss").format (new Date ())
                                + ".js";
                    }
                    textReader (connection.getInputStream (),
                            shouldSaveResponseOnFile,addressOfFileForSaveOutput);
            }

        }catch (IOException e)
        {
            try {
                textReader (connection.getErrorStream (),
                        shouldSaveResponseOnFile,addressOfFileForSaveOutput);
            } catch (IOException ex)
            {
                responseStorage.setResponseTextRawData ("Error:" +
                        " URL using bad/illegal format or missing URL");
            }
        }
    }

    /**
     * read text / html or plain or Json   from server
     * @param serverInputStream serverInputStream
     * @param shouldSaveResponseOnFile shouldSaveResponseOnFile
     * @param addressOfFileForSaveOutput addressOfFileForSaveOutput
     * @throws IOException IOException
     */
    private void textReader (InputStream serverInputStream,
                             boolean shouldSaveResponseOnFile,
                             String addressOfFileForSaveOutput
                            ) throws IOException
    {
        if (serverInputStream == null)
            throw new IOException ("serverInputStream is null");
        StringBuilder content = new StringBuilder ();
        try (BufferedReader out = new BufferedReader (new InputStreamReader (serverInputStream)))
        {
            String line;
            while ((line = out.readLine ()) != null) {
                content.append (line).append ('\n');
            }
        }

        responseStorage.setResponseTextRawData (content.toString ());
        responseStorage.setReadLength (content.toString ().getBytes ().length);
        if (shouldSaveResponseOnFile)
        {
            try (BufferedOutputStream in = new BufferedOutputStream (new FileOutputStream (
                    addressOfFileForSaveOutput)))
            {
                in.write (content.toString ().getBytes ());
                in.flush ();
            } catch (IOException e)
            {
                System.err.println ("Some thing went Wrong in Save response on File");
            }

        }
    }

    /**
     * read binary from server
     * @param serverInputStream serverInputStream
     * @param shouldSaveResponseOnFile shouldSaveResponseOnFile
     * @param addressOfFileForSaveOutput addressOfFileForSaveOutput
     * @throws IOException IOException
     */
    private void binaryReader (InputStream serverInputStream,
                               boolean shouldSaveResponseOnFile,
                               String addressOfFileForSaveOutput
                              ) throws IOException
    {
        if (serverInputStream == null)
            throw new IOException ("serverInputStream is null");
        try (BufferedInputStream in= new BufferedInputStream (serverInputStream))
        {
            responseStorage.setResponseBinaryRawData (in.readAllBytes ());
            responseStorage.setReadLength (responseStorage.getResponseBinaryRawData ().length);
        }
        if (shouldSaveResponseOnFile)
        {
            try (BufferedOutputStream out = new BufferedOutputStream (
                    new FileOutputStream (addressOfFileForSaveOutput)))
            {
                out.write (responseStorage.getResponseBinaryRawData ());
                out.flush ();
            } catch (IOException e)
            {
                System.err.println ("Some thing went Wrong in Save response on File");
            }
        }
    }

    /**
     * write to server
     * @param messageType messageBodyType ; 1 multiPart, 2 binary upload, 3 formUrlEncoded
     * @param multipartData multipartData
     * @param file binary file
     * @param boundary boundary
     * @param formUrlEncodedData formUrlEncodedData
     */
    private void writeToServer (int messageType,
                                HashMap<String,String> multipartData, File file,
                                String boundary,
                                String formUrlEncodedData)
    {
        try {
            if (messageType == 1)
            {
                writeBinaryFormData (connection.getOutputStream (),boundary,multipartData);
            } else if (messageType == 2)
            {
                writeBinaryFile (connection.getOutputStream (),file);
            } else if (messageType == 3)
            {
                writeBinaryFormDataEncoded (connection.getOutputStream (),formUrlEncodedData);
            }
        } catch (IOException e)
        {
            System.err.println ("Couldn't write on Server ");
        }
    }


    /**
     * write form data (multiPart) to server
     * @param serverOutPutSteam serverOutPutSteam
     * @param boundary boundary
     * @param body body
     * @throws IOException IOException
     */
    private void writeBinaryFormData (OutputStream serverOutPutSteam, String boundary,
                              HashMap<String,String> body) throws IOException
    {
        if (body == null || boundary == null)
            throw new IOException("Body or Boundary is Empty");
        try (BufferedOutputStream out = new BufferedOutputStream (serverOutPutSteam))
        {
            for (String key : body.keySet())
            {
                out.write(("--" + boundary + "\r\n").getBytes());
                if (key.contains("file")) {
                    out.write (("" +
                            "" + (new File(body.get(key))).getName() +
                            "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());

                    try (BufferedInputStream tempBufferedInputStream = new BufferedInputStream
                            (new FileInputStream(new File(body.get(key)))))
                    {
                        byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                        out.write(filesBytes);
                        out.write("\r\n".getBytes());
                    }
                } else {
                    out.write(("Content-Disposition: form" +
                            "-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                    out.write((body.get(key) + "\r\n").getBytes());
                }
            }
            out.write(("--" + boundary + "--\r\n").getBytes());
            out.flush();
        }
    }

    /**
     * write binary file to server
     * @param serverOutPutStream serverOutPutStream
     * @param file file
     * @throws IOException IOException
     */
    private void writeBinaryFile (OutputStream serverOutPutStream, File file)
                                    throws IOException
    {
        try (BufferedOutputStream out = new BufferedOutputStream (serverOutPutStream);
             BufferedInputStream in = new BufferedInputStream (new FileInputStream (file))) {
            out.write (in.readAllBytes ());
            out.flush ();
        }
    }

    /**
     * write form data encoded
     * @param serverOutPutStream serverOutPutStream
     * @param formUrlEncodedData formUrlEncodedData
     * @throws IOException IOException
     */
    private void writeBinaryFormDataEncoded (OutputStream serverOutPutStream,
                                             String formUrlEncodedData) throws IOException
    {
        if (formUrlEncodedData == null)
            throw new IOException("Body is Empty");
        try (BufferedOutputStream out = new BufferedOutputStream (serverOutPutStream))
        {
            out.write (formUrlEncodedData.getBytes (StandardCharsets.UTF_8));
            out.flush ();
        }
    }


}
