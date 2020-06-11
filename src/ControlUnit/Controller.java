package ControlUnit;

import ClientRequest.ClientRequest;
import ClientSocketHandler.ClientProxy;
import GUI.GUI;
import Jurl.Jurl;
import Storage.RequestsStorage;

import java.util.ArrayList;

/**
 * this class controls relation between GUI , storage unit , jurl
 *
 * @author Amir Naziri
 */

public class Controller {
    private static final StorageUnit storageUnit = new StorageUnit ();
    private static final Jurl jurl = new Jurl ();
    private static final GUI gui = new GUI ();


    /**
     * start program
     */
    public static void startProgram () {
        gui.setBaseFrameVisible ();
        jurl.startProgram ();
    }

    /**
     * add new Client request to list
     *
     * @param clientRequest new clientRequest
     */
    public static void addNewClientRequest (ClientRequest clientRequest) {
        storageUnit.
                addRequest (clientRequest);
    }

    /**
     * remove client request from list
     *
     * @param clientRequest clientRequest
     */
    public static void removeClientRequest (ClientRequest clientRequest) {
        storageUnit.
                removeRequest (clientRequest);
    }

    /**
     * get a client request
     *
     * @param index index
     * @return client request
     */
    public static ClientRequest getClientRequest (int index) {
        return storageUnit.
                getClientRequest (index);
    }

    /**
     * print list of request
     */
    public static void printList () {
        storageUnit.printList ();
    }

    /**
     * save every thing
     */
    public static void saveUpdates () {
        gui.getFirstPanel ().getRequestsPanel ().properRequestsForClosing ();
        storageUnit.save ();
    }

    /**
     * set follow redirection for all client requests
     *
     * @param followRedirect followRedirect
     */
    public static void setFollowRedirectForAllClientRequests (boolean followRedirect) {
        for (ClientRequest clientRequest : storageUnit.getClientRequests ())
            clientRequest.setFollowRedirect (followRedirect);
    }

    /**
     * @return list of client requests
     */
    public static ArrayList<ClientRequest> clientRequests () {
        return storageUnit.
                getClientRequests ();
    }

    /**
     * print all result of given storage
     * @param requestsStorage requestsStorage
     */
    public static synchronized void printAllResult (RequestsStorage requestsStorage)
    {
        if (requestsStorage == null)
            return;
        for (ClientRequest clientRequest : requestsStorage.getClientRequests ())
            clientRequest.printResult ();
    }



    /**
     * update the requestsStorage
     * @param requestsStorage requestsStorage
     */
    public static void updateRequestsStorage (RequestsStorage requestsStorage)
    {
        storageUnit.updateRequestStorage (requestsStorage);
        storageUnit.save ();
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



}
