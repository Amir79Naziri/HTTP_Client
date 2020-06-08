package ControlUnit;

import Client.ClientRequest;
import GUI.GUI;
import Jurl.Jurl;

import java.util.ArrayList;

/**
 * this class controls relation between GUI , storage unit , jurl
 *
 * @author Amir Naziri
 */

public class Controller
{
    private static final StorageUnit storageUnit = new StorageUnit ();
    private static final Jurl jurl = new Jurl ();
    private static final GUI gui = new GUI();


    /**
     * start program
     */
    public static void startProgram ()
    {
//        gui.setBaseFrameVisible ();
        jurl.startProgram ();
    }

    /**
     * add new Client request to list
     * @param clientRequest new clientRequest
     */
    public static void addNewClientRequest (ClientRequest clientRequest)
    {
        storageUnit.
                addRequest (clientRequest);
    }

    /**
     * remove client request from list
     * @param clientRequest clientRequest
     */
    public static void removeClientRequest (ClientRequest clientRequest)
    {
        storageUnit.
                removeRequest (clientRequest);
    }

    /**
     * get a client request
     * @param index index
     * @return client request
     */
    public static ClientRequest getClientRequest (int index)
    {
        return storageUnit.
                getClientRequest (index);
    }

    /**
     * print list of request
     */
    public static void printList ()
    {
        storageUnit.printList ();
    }

    /**
     * save every thing
     */
    public static void saveUpdates ()
    {
        gui.getFirstPanel ().getRequestsPanel ().properRequestsForClosing ();
        storageUnit.save ();
    }

    /**
     * set follow redirection for all client requests
     * @param followRedirect followRedirect
     */
    public static void setFollowRedirectForAllClientRequests (boolean followRedirect)
    {
        for (ClientRequest clientRequest : storageUnit.getClientRequests ())
            clientRequest.setFollowRedirect (followRedirect);
    }

    /**
     * @return list of client requests
     */
    public static ArrayList<ClientRequest> clientRequests ()
    {
        return storageUnit.
                getClientRequests ();
    }

//    /**
//     * adds a new client request to GUI from jurl in runtime
//     * @param clientRequest clientRequest
//     */
//    public static void addClientRequestToGuiDirectly (ClientRequest clientRequest)
//    {
//        gui.getFirstPanel ().getRequestsPanel ().addRequest (clientRequest.getRequestType (),
//                clientRequest.getName (),clientRequest);
//    }

    /**
     * prints help details
     */
    public static void printHelp () // TODO : move help to controller
    {
        String help = " Usage: jurl url [options...]\n" +
                " do not write <> for your input , this is just for obvious input\n" +
                " -d, --data <\"key1=value1&key2=value2&.....\"> (message body) MultiPart form\n" +
                " -M, --method <method> HTTP method \n" +
                " -H, --headers <\"key1;value1:key2;value2:...\"> Pass custom header(s) to server\n" +
                " -i, --include       Include protocol response headers in the output\n" +
                " -h, --help          This help text\n" +
                " -f                   Follow redirect\n" +
                " -O, --output <name of file> Write response body to file instead of stdout\n" +
                "                     if you don't mention any name it will be output_[CurrentDate]\n" +
                " -S, --save           This will save the request\n" +
                " -j, --json  <\"{data1:details,data2:details,....}\">\n" +
                " --upload <absolute path>   upload file (messageBody) binaryFile\n" +
                " -Q <\"key1=value1&key2=value2&.....\"> add query data\n" +
                " --name <name of request>   change name of request\n" +
                " --close                     close program (only first element)\n" +
                " --dataEncoded <\"key1=value1&key2=value2&.....\"> (message body) form url encoded form\n" +
                " -r, --remove <indexes>         remove requests by their index (only first element)\n" +
                " list                           print list of requests (only first element)\n" +
                " fire <indexes>                 send requests by their index (only first element)\n" +
                " --rename <index> <new name>    rename request by its index to new name (only first element)\n";
        System.out.println (help);
    }


}
