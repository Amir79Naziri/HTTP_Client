package ControlUnit;

import Client.ClientRequest;
import GUI.GUI;
import Jurl.Jurl;
import Storage.StorageUnit;

import java.util.ArrayList;


public class Controller
{
    private static final StorageUnit storageUnit = new StorageUnit ();
    private static final Jurl jurl = new Jurl ();
    private static final GUI gui = new GUI();

    public static void startProgram ()
    {
        gui.setBaseFrameVisible ();
        jurl.startProgram ();
    }


    public static void addNewClientRequest (ClientRequest clientRequest)
    {
        storageUnit.addRequest (clientRequest);
    }

    public static void removeClientRequest (ClientRequest clientRequest)
    {
        storageUnit.removeRequest (clientRequest);
    }

    public static ClientRequest getClientRequest (int index)
    {
        return storageUnit.getClientRequest (index);
    }

    public static void printList ()
    {
        storageUnit.printList ();
    }
    public static void saveUpdates ()
    {
        gui.getFirstPanel ().getRequestsPanel ().properRequestsForClosing ();
        storageUnit.save ();
    }

    public static void changeFollowRedirect (boolean followRedirect)
    {
        for (ClientRequest clientRequest : storageUnit.getClientRequests ())
            clientRequest.setFollowRedirect (followRedirect);
    }

    public static ArrayList<ClientRequest> clientRequests ()
    {
        return storageUnit.getClientRequests ();
    }


    public static void addClientRequestToGuiDirectly (ClientRequest clientRequest)
    {
        gui.getFirstPanel ().getRequestsPanel ().addRequest (clientRequest.getRequestType (),
                clientRequest.getName (),clientRequest);
    }
}
