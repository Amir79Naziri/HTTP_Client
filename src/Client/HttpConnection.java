package Client;

import GUI.BinaryFilePanel;
import Storage.ResponseStorage;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
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

    public HttpConnection (boolean followRedirect, RequestType requestType) throws MalformedURLException
    {
        url = new URL ("https://api.myproduct.com/v1/users");
        this.requestType = requestType;
        this.followRedirect = followRedirect;
        showHeadersInResponse = false;
        responseStorage = new ResponseStorage ();
    }


    public HttpURLConnection connectionInitializer (HashMap<String, String> headers)
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
                    connection.setRequestProperty (key,headers.get (key));

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
                        System.out.println ("you should use --output!");
                    } else {
                        binaryReader (connection.getInputStream ());
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
                        textReader (connection.getInputStream ());
                    }
            }

        }catch (IOException e)
        {
//            textReader (connection.getErrorStream ());
            responseStorage.setResponseTextRawData ("Error:" +
                    " URL using bad/illegal format or missing URL");
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

    private void binaryReader (InputStream serverInputStream) throws IOException
    {
        BufferedInputStream in= new BufferedInputStream (serverInputStream);
        BufferedOutputStream out = new BufferedOutputStream (
                new FileOutputStream (addressOfFileForSaveOutput));
        out.write (in.readAllBytes ());
        out.flush ();
        out.close ();
        in.close ();
        responseStorage.setResponseFileRawData (new File (addressOfFileForSaveOutput));
        responseStorage.setReadLength (BinaryFilePanel.makeSizeReadable (Files.size (
                responseStorage.getResponseFileRawData ().toPath ()
        )));
    }

    private void writeToServer (HttpURLConnection connection, int messageType,
                                HashMap<String,String> body, File file, String boundary)
    {
        try {
            if (messageType == 1)
            {
                writeBinaryFormData (connection.getOutputStream (),boundary,body);
            } else if (messageType == 2)
            {
                writeBinary (connection.getOutputStream (),file);
            }
        } catch (IOException e)
        {
            System.out.println ("Couldn't write on Server");
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

    private void writeBinary (OutputStream serverOutPutStream, File file) throws IOException
    {
        BufferedOutputStream out = new BufferedOutputStream (serverOutPutStream);
        BufferedInputStream in = new BufferedInputStream (new FileInputStream (file));
        out.write (in.readAllBytes ());
        out.flush ();
        out.close ();
        in.close ();
    }

    public void get (HttpURLConnection connection)
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
                            HashMap<String,String> body, File file)
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
            if (file == null || !file.exists ()) {
                {
                    System.out.println ("File is not Valid");
                    return;
                }
            }

            connection.setRequestProperty("Content-Type", "application/octet-stream");
        }

        if (connectToServer (connection))
        {
            //writing
            writeToServer (connection,messageBodyType,body,file,boundary);

            // reading
            readFromServer (connection);
        }


        responseStorage.setResponseTime ((System.currentTimeMillis () - startTime));
        disconnectServer (connection);
    }




    private void disconnectServer (HttpURLConnection connection)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        printResult ();
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
}
