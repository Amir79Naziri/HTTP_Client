package Client;

import GUI.BinaryFilePanel;
import Storage.ResponseStorage;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    private String addressOfFileForSaveOutput;


    public HttpConnection (String url, boolean followRedirect) throws MalformedURLException
    {
        this.url = new URL (url);
        this.requestType = RequestType.GET;
        this.followRedirect = followRedirect;
        showHeadersInResponse = false;
        responseStorage = new ResponseStorage ();
    }

    public HttpConnection (boolean followRedirect, RequestType requestType)
            throws MalformedURLException
    {
        url = new URL ("https://api.myproduct.com/v1/users");
        this.requestType = requestType;
        this.followRedirect = followRedirect;
        showHeadersInResponse = false;
        responseStorage = new ResponseStorage ();
    }


    public HttpURLConnection connectionInitializer
            (HashMap<String, String> headers, String queryData)
    {

        try {
            URL connectionUrl = new URL (getUrl () + "" + queryData);
            HttpURLConnection connection;
            if (("http").equals (connectionUrl.getProtocol ()))
                connection = (HttpURLConnection) connectionUrl.openConnection ();
            else if (("https").equals (connectionUrl.getProtocol ()))
                connection = (HttpsURLConnection) connectionUrl.openConnection ();
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
                    connection.setRequestProperty (key,headers.get (key));


            System.out.println ("Connection Initialized in : " + connection.getURL () +
                    "\nconnecting......");
            return connection;
        }
        catch (MalformedURLException e)
        {
            System.out.println ("Wrong url format");
            return null;
        }
        catch(IOException e) {
            System.err.println ("Failed to start Connecting");
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
            return false;
        }
    }

    private void disconnectServer (HttpURLConnection connection)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        printResult ();
        connection.disconnect ();
    }

    public void onlyGet (HttpURLConnection connection)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        long startTime = System.currentTimeMillis ();
        connection.setDoInput(true);
        if (connectToServer (connection))
        {
            // reading
            readFromServer (connection);
        }
        responseStorage.setResponseTime ((System.currentTimeMillis () - startTime));

        disconnectServer (connection);
    }

    public void sendAndGet (HttpURLConnection connection, int messageBodyType,
                            HashMap<String,String> multipartData,
                            File file, String formUrlEncodedData)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
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
            if (file == null || !file.exists () || !file.isAbsolute ()) {
                {
                    System.out.println ("File is not Valid");
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

        if (connectToServer (connection))
        {
            //writing
            writeToServer (connection,messageBodyType,multipartData,file,boundary,formUrlEncodedData);

            // reading
            readFromServer (connection);
        }


        responseStorage.setResponseTime ((System.currentTimeMillis () - startTime));
        disconnectServer (connection);
    }


    private void readFromServer (HttpURLConnection connection) {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        try {
            responseStorage.setResponseCode (connection.getResponseCode ());
            responseStorage.setResponseMessage (connection.getResponseMessage ());
            responseStorage.setResponseHeaders (connection.getHeaderFields ());





            String contentType = "text/html";
            if (connection.getContentType () != null)
                 contentType = connection.getContentType ().split (";")[0];

            switch (contentType) {
                case "text/html":
                    if (addressOfFileForSaveOutput == null) {
                        addressOfFileForSaveOutput = "./data/RawData/Output_" +
                                new SimpleDateFormat (
                                "yyyy.MM.dd  HH.mm.ss").format (new Date ()) + ".html";
                    }
                    textReader (connection.getInputStream ());
                    break;
                case "image/png":
                    if (addressOfFileForSaveOutput == null) {
                        addressOfFileForSaveOutput = "./data/RawData/Output_" +
                                new SimpleDateFormat (
                                "yyyy.MM.dd  HH.mm.ss").format (new Date ()) + ".png";
                    }
                    if (!saveRawDataOnFile) {
                        {
                            System.out.println ("you should use --output!");
                            binaryReader (connection.getInputStream (),false);
                        }
                    } else {
                        binaryReader (connection.getInputStream (),true);
                    }
                    responseStorage.setResponseTextRawData ("File is Binary !");
                    break;
                case "text/plain":
                    if (addressOfFileForSaveOutput == null) {
                        addressOfFileForSaveOutput = "./data/RawData/Output_" +
                                new SimpleDateFormat (
                                "yyyy.MM.dd  HH.mm.ss").format (new Date ()) + ".txt";
                    }
                    textReader (connection.getInputStream ());
                    break;
                case "application/json":
                    if (addressOfFileForSaveOutput == null) {
                        addressOfFileForSaveOutput = "./data/RawData/Output_" +
                                new SimpleDateFormat (
                                        "yyyy.MM.dd  HH.mm.ss").format (new Date ())
                                + ".js";
                    }
                    textReader (connection.getInputStream ());
            }

        }catch (IOException e)
        {
            try {
                textReader (connection.getErrorStream ());
            } catch (IOException ex)
            {
                responseStorage.setResponseTextRawData ("Error:" +
                        " URL using bad/illegal format or missing URL");
            }
        }
    }

    private void writeToServer (HttpURLConnection connection, int messageType,
                                HashMap<String,String> multipartData, File file, String boundary,
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
            System.out.println ("Couldn't write on Server ");
        }
    }


    private void textReader (InputStream serverInputStream) throws IOException
    {
        Scanner out = new Scanner (serverInputStream);
        StringBuilder content = new StringBuilder ();
        while (out.hasNext ()) {
            content.append (out.nextLine ()).append ('\n');
        }
        out.close ();
        responseStorage.setResponseTextRawData (content.toString ());
        responseStorage.setReadLength (BinaryFilePanel.
                makeSizeReadable (content.toString ().getBytes ().length));
        if (saveRawDataOnFile)
        {
            BufferedOutputStream in = new BufferedOutputStream (new FileOutputStream (
                addressOfFileForSaveOutput));
            in.write (content.toString ().getBytes ());
            in.flush ();
            in.close ();
        }
    }

    private void binaryReader (InputStream serverInputStream,
                               boolean shouldSave) throws IOException
    {
        BufferedInputStream in= new BufferedInputStream (serverInputStream);
        responseStorage.setResponseBinaryRawData (in.readAllBytes ());
        responseStorage.setReadLength (BinaryFilePanel.makeSizeReadable (
                responseStorage.getResponseBinaryRawData ().length));
        in.close ();
        if (shouldSave)
        {
            BufferedOutputStream out = new BufferedOutputStream (
                    new FileOutputStream (addressOfFileForSaveOutput));
            out.write (responseStorage.getResponseBinaryRawData ());
            out.flush ();
            out.close ();
        }
    }

    private void writeBinaryFormData (OutputStream serverOutPutSteam, String boundary,
                              HashMap<String,String> body) throws IOException
    {
        if (body == null || boundary == null)
            throw new IOException("Body or Boundary is Empty");
        BufferedOutputStream out = new BufferedOutputStream (serverOutPutSteam);
        for (String key : body.keySet())
        {
            out.write(("--" + boundary + "\r\n").getBytes());
            if (key.contains("file")) {
                out.write (("" +
                        "" + (new File(body.get(key))).getName() +
                        "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());

                BufferedInputStream tempBufferedInputStream = new BufferedInputStream
                        (new FileInputStream(new File(body.get(key))));
                byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                out.write(filesBytes);
                out.write("\r\n".getBytes());
                tempBufferedInputStream.close ();

            } else {
                out.write(("Content-Disposition: form" +
                        "-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                out.write((body.get(key) + "\r\n").getBytes());
            }
        }
        out.write(("--" + boundary + "--\r\n").getBytes());
        out.flush();
        out.close();

    }

    private void writeBinaryFile (OutputStream serverOutPutStream, File file) throws IOException
    {
        BufferedOutputStream out = new BufferedOutputStream (serverOutPutStream);
        BufferedInputStream in = new BufferedInputStream (new FileInputStream (file));
        out.write (in.readAllBytes ());
        out.flush ();
        out.close ();
        in.close ();
    }

    private void writeBinaryFormDataEncoded (OutputStream serverOutPutStream,
                                             String formUrlEncodedData) throws IOException
    {
        if (formUrlEncodedData == null)
            throw new IOException("Body is Empty");
        BufferedOutputStream out = new BufferedOutputStream (serverOutPutStream);
        out.write (formUrlEncodedData.getBytes (StandardCharsets.UTF_8));
        out.flush ();
        out.close ();
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
        this.addressOfFileForSaveOutput = null;
    }


    public void setSaveRawDataOnFile (boolean saveRawDataOnFile, String nameOfFile) {
        this.saveRawDataOnFile = saveRawDataOnFile;
        this.addressOfFileForSaveOutput = "./data/RawData/" + nameOfFile;
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

    public RequestType getRequestType () {
        return requestType;
    }

    public ResponseStorage getResponseStorage () {
        return responseStorage;
    }

    private synchronized void printResult ()
    {
        responseStorage.printTimeAndReadDetails ();
        System.out.println ();
        System.out.println (url);
        if (showHeadersInResponse)
            responseStorage.printHeaders ();
        responseStorage.printRawResponse ();
    }

    public boolean isSaveRawDataOnFile () {
        return saveRawDataOnFile;
    }
}
