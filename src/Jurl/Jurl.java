package Jurl;

import Client.ClientRequest;
import Storage.StorageUnit;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.TreeMap;

public class Jurl
{
    private InputProcessor inputProcessor;
    private StorageUnit storageUnit;



    public Jurl ()
    {
        inputProcessor = new InputProcessor ();
        storageUnit = new StorageUnit ();
    }

    public Jurl (StorageUnit storageUnit)
    {
        this.storageUnit = storageUnit;
        inputProcessor = new InputProcessor ();
    }


    public void startProgram ()
    {
        while (true) {
            inputProcessor.getLine ();
            try {
                ArrayList<ClientRequest> clientRequests = createClientRequest (inputProcessor.getTasks (),
                        inputProcessor.getUrl (), inputProcessor.getInputType ());
                if (clientRequests != null)
                {
                    new Thread (new Executor (clientRequests)).start ();
                }
            } catch (MalformedURLException e)
            {
                e.printStackTrace ();
            }
        }
    }



    private ArrayList<ClientRequest> createClientRequest (TreeMap<ReservedWord,
                                    ArrayList<String>> tasks, String url, int inputType)
            throws MalformedURLException
    {
        ArrayList<ClientRequest> clientRequests = new ArrayList<> ();

        if (inputType == 1)
        {
            for (ReservedWord reservedWord : tasks.keySet ())
            {
                switch (reservedWord)
                {
                    case CLOSE: System.exit (0);
                    case FIRE:
                        for (String arg : tasks.get (reservedWord))
                        {
                            ClientRequest clientRequest =
                                    storageUnit.getClientRequest (Integer.parseInt (arg));
                            if (clientRequest != null)
                                clientRequests.add (clientRequest);
                        }
                        return clientRequests;
                    case HELP_V2: help (); return null;
                    case LIST: storageUnit.printList (); return null;

                }
            }
        }
        else
        {

            ClientRequest clientRequest = new ClientRequest (url,false);

            for (ReservedWord reservedWord : tasks.keySet ())
            {
                switch (reservedWord)
                {
                    case JSON_V2:
                        break;
                    case QUERY: clientRequest.addQuery (tasks.get (reservedWord).get (0));
                        break;
                    case OUTPUT_V2:
                        if (tasks.get (reservedWord).size () == 1)
                            clientRequest.setShouldSaveOutputInFile
                                    (true,tasks.get (reservedWord).get(0));
                        else
                            clientRequest.setShouldSaveOutputInFile
                                    (true,null);

                        break;
                    case FOLLOW_REDIRECT: clientRequest.setFollowRedirect (true);
                        break;
                    case NAME: clientRequest.setName (tasks.get (reservedWord).get (0));
                        break;
                    case UPLOAD:
                        clientRequest.setMessageBodyType (2);
                        clientRequest.addUploadBinaryFile
                                (new File (tasks.get (reservedWord).get (0)));
                        break;
                    case HEADER_V2: clientRequest.addCustomHeader
                            (tasks.get (reservedWord).get (0));
                        break;
                    case METHOD_V2: clientRequest.setRequestType (tasks.get (reservedWord).get (0));
                        break;
                    case FORM_DATA_V2:
                        clientRequest.setMessageBodyType (1);
                        clientRequest.addFormUrlData (tasks.get (reservedWord).get (0));
                        break;
                    case FORM_DATA_ENCODED:
                        clientRequest.setMessageBodyType (3);
                        clientRequest.addFormUrlDataEncoded
                                (tasks.get (reservedWord).get (0));
                        break;
                    case SHOW_HEADER_ARG_V2: clientRequest.setShowHeadersInResponse (true);
                }
            }
            if (tasks.containsKey (ReservedWord.SAVE_V2))
            {

                storageUnit.addRequest (clientRequest);
            }
            clientRequests.add (clientRequest);


        }
        return clientRequests;
    }



    private void help ()
    {
        System.out.println (" Usage: jurl url [options...]");
        System.out.println (" do not write <> for your input , this is just for obvious input");
        System.out.println (" -d, --data <\"key1=value1&key2=value2&.....\">" +
                " (message body) MultiPart form");
        System.out.println (" -M, --method <method> HTTP method ");
        System.out.println (" -H, --headers <\"key1;value1:key2;value2:...\">" +
                " Pass custom header(s) to server");
        System.out.println (" -i, --include       Include protocol response headers in the output");
        System.out.println (" -h, --help          This help text");
        System.out.println (" -f                   Follow redirect");
        System.out.println (" -O, --output <file> Write response body to file instead of stdout\n" +
                "                     if you don't mention any name it will be output_[CurrentDate]");
        System.out.println (" -S, --save           This will save the request");
        System.out.println (" -j, --json  <\"{data1:details,data2:details,....}\">");
        System.out.println (" --upload <absolute path>   upload file");
        System.out.println (" -Q <\"key1=value1&key2=value2&.....\"> add query data");
        System.out.println (" --name <name of request>   change name of request");
        System.out.println (" --close                     close program");
        System.out.println ("--dataEncoded <\"key1=value1&key2=value2&.....\">" +
                " (message body) form url encoded form");
    }
}
