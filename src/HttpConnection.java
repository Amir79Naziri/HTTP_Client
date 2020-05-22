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


            String contentType = connection.getContentType ().split (";")[0];

            switch (contentType) {
                case "text/html":
                    if (addressOfFileForSaveOutput == null) {
                        addressOfFileForSaveOutput = "./data/RawData/Output_" + new SimpleDateFormat (
                                "yyyy.MM.dd  HH.mm.ss").format (new Date ()) + ".html";
                    }
                    textReader (connection.getInputStream ());
                    break;
                case "image/png":
                    if (addressOfFileForSaveOutput == null) {
                        addressOfFileForSaveOutput = "./data/RawData/Output_" + new SimpleDateFormat (
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
                        addressOfFileForSaveOutput = "./data/RawData/Output_" + new SimpleDateFormat (
                                "yyyy.MM.dd  HH.mm.ss").format (new Date ()) + ".txt";
                    }
                    textReader (connection.getInputStream ());
                    break;
            }

        }catch (IOException e)
        {
            responseStorage.setResponseTextRawData ("Error: URL using bad/illegal format or missing URL");
        }
    }


    private void textReader (InputStream serverInputStream)
    {
        Scanner out = new Scanner (serverInputStream);
        StringBuilder content = new StringBuilder ();
        while (out.hasNext ()) {
            content.append (out.nextLine ()).append ('\n');
        }
        responseStorage.setResponseTextRawData (content.toString ());
        responseStorage.setReadLength (BinaryFilePanel.
                makeSizeReadable (content.toString ().getBytes ().length));
        if (saveRawDataOnFile)
        {
            try (BufferedOutputStream in = new BufferedOutputStream (new FileOutputStream (
                    addressOfFileForSaveOutput))){
                in.write (content.toString ().getBytes ());
                in.flush ();
            } catch (IOException e)
            {
                System.out.println ("some thing went wrong in saving rawData");
            }
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

    private void writeToServer (HttpURLConnection connection, byte[] bytes)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        try (BufferedOutputStream out = new BufferedOutputStream (connection.getOutputStream ()))
        {
            out.write (bytes);
            out.flush ();

        } catch (IOException e) {
            e.printStackTrace ();
            System.out.println ("some thing went wrong in writing to server");
        }
    }

    public void getMethod (HttpURLConnection connection)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        long startTime = System.currentTimeMillis ();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        if (connectToServer (connection))
        {
            // reading
            readFromServer (connection);
        }
        responseStorage.setResponseTime ((System.currentTimeMillis () - startTime));

        disconnectServer (connection);
    }

    public void postMethod (HttpURLConnection connection, byte[] bytes, int messageBodyType)
    {
        if (connection == null)
            throw new NullPointerException ("inValid input");
        long startTime = System.currentTimeMillis ();
        connection.setDoOutput (true);
        connection.setDoInput (true);

        if (messageBodyType == 1)
        {

            if (connectToServer (connection))
            {
                //writing
                 writeToServer (connection, bytes);

                // reading
                readFromServer (connection);
            }
        }
        // TODO : Add JSON and Binary
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


    public ResponseStorage getResponseStorage () {
        return responseStorage;
    }


    public RequestType getRequestType () {
        return requestType;
    }


    private void printResult ()
    {
        responseStorage.printTimeAndReadDetails ();
        System.out.println ();
        System.out.println (url);
        if (showHeadersInResponse)
            responseStorage.printHeaders ();
        responseStorage.printRawResponse ();
    }
}
