import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Jurl
{
    private InputProcessor inputProcessor;
    private StorageUnit storageUnit;
    private ExecutorService pool;


    public Jurl ()
    {
        inputProcessor = new InputProcessor ();
        storageUnit = new StorageUnit ();
        pool = Executors.newCachedThreadPool ();
    }


    public void startProgram ()
    {
        while (true) {
            inputProcessor.getLine ();
            ArrayList<ClientRequest> clientRequests = createClientRequest (inputProcessor.getTasks (),
                    inputProcessor.getUrl (), inputProcessor.getInputType ());
            if (clientRequests != null)
                execute (clientRequests);
        }
    }



    private ArrayList<ClientRequest> createClientRequest (TreeMap<ReservedWord,
                                    ArrayList<String>> tasks, String url, int inputType)
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
                        for (String task : tasks.get (reservedWord))
                        {
                            clientRequests.add (storageUnit.getClientRequest (Integer.parseInt (task)));
                        }
                        return clientRequests;
                    case HELP_V2: help (); return null;
                    case LIST: storageUnit.printList (); return null;

                }
            }
        }
        else
        {
            try {
                ClientRequest clientRequest = new ClientRequest (url);

                for (ReservedWord reservedWord : tasks.keySet ())
                {
                    switch (reservedWord)
                    {
                        case JSON_V2:
                            break;
                        case OUTPUT_V2:
                            break;
                        case FOLLOW_REDIRECT: clientRequest.setFollowRedirect (true);
                            break;
                        case NAME: clientRequest.setName (tasks.get (reservedWord).get (0));
                            break;
                        case UPLOAD:
                        case HEADER_V2: clientRequest.addCustomHeader
                                (tasks.get (reservedWord).get (0));
                            break;
                        case METHOD_V2: clientRequest.setRequestType (tasks.get (reservedWord).get (0));
                            break;
                        case FORM_DATA_V2:
                            clientRequest.setMessageBodyType (1);
                            clientRequest.addFormUrlData (tasks.get (reservedWord).get (0));
                            break;
                        case SHOW_HEADER_ARG_V2: clientRequest.setShowHeadersInResult (true);
                    }
                }
                if (tasks.containsKey (ReservedWord.SAVE_V2))
                    storageUnit.addRequest (clientRequest);
                clientRequests.add (clientRequest);

            } catch (MalformedURLException e) {
                e.printStackTrace ();
            }
        }
        return clientRequests;
    }

    private void execute (ArrayList<ClientRequest> clientRequests)
    {
        if (clientRequests == null)
            return;
        for (ClientRequest clientRequest : clientRequests)
        {
            try {
                Thread.sleep (5000);
            } catch (InterruptedException e)
            {
                e.printStackTrace ();
            }
            pool.execute (clientRequest);
        }
    }

    private void help ()
    {
        System.out.println (" Usage: jurl url [options...]");
        System.out.println (" do not write <> for your input , this is just for obvious input");
        System.out.println (" -d, --data <data>   HTTP POST data (message body)");
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
        System.out.println (" --upload <absolute path>   ????");
    }
}
