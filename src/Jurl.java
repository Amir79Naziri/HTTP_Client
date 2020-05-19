import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Jurl
{
    private InputProcessor inputProcessor;
    private StorageUnit storageUnit;
    private ClientRequest currentClientRequest;
    private ExecutorService pool;


    public Jurl ()
    {
        inputProcessor = new InputProcessor ();
        storageUnit = new StorageUnit ();
        pool = Executors.newCachedThreadPool ();
    }



    public void startProgram ()
    {
        boolean isFinished = false;
        while (!isFinished)
        {
            currentClientRequest = new ClientRequest ("MyRequest",RequestType.GET);
            inputProcessor.getLine ();
            isFinished = doTasks (inputProcessor.getTasks (),inputProcessor.getUrl ());
        }
    }




    private boolean doTasks (TreeMap<ReservedWord, ArrayList<String>> tasks, String url)
    {
        ArrayList<ClientRequest> clientRequests = new ArrayList<> ();

        for (ReservedWord reservedWord : tasks.keySet ())
        {
            switch (reservedWord)
            {
                case CLOSE: return true;
                case FIRE:
                    for (String task : tasks.get (reservedWord))
                    {
                        clientRequests.add (storageUnit.getClientRequest (Integer.parseInt (task)));
                    }
                    execute (clientRequests);
                    return false;
                case HELP_V2: help (); return false;
                case LIST: storageUnit.printList (); return false;
                case JSON_V2:
                case OUTPUT_V2:
                case FOLLOW_REDIRECT:
                case NAME: currentClientRequest.setName (tasks.get (reservedWord).get (0));
                    break;
                case UPLOAD:
                case HEADER_V2: currentClientRequest.addHeader (tasks.get (reservedWord).get (0));
                    break;
                case METHOD_V2: currentClientRequest.setRequestType (tasks.get (reservedWord).get (0));
                case SAVE_V2: storageUnit.addRequest (currentClientRequest);
                    break;
                case FORM_DATA_V2:
                case SHOW_HEADER_ARG_V2:
            }
        }

        if (url != null)
        {
            currentClientRequest.setUrl (url);
            clientRequests.add (currentClientRequest);
            execute (clientRequests);
        }

        return false;
    }

    private void execute (ArrayList<ClientRequest> clientRequests)
    {
        if (clientRequests == null)
            return;
        for (ClientRequest clientRequest : clientRequests)
            pool.execute (clientRequest);
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
