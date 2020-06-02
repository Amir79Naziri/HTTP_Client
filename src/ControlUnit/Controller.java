package ControlUnit;

import Client.ClientRequest;
import GUI.GUI;
import Jurl.Jurl;
import Storage.StorageUnit;

import java.util.ArrayList;


public class Controller
{
    private static final StorageUnit storageUnit = new StorageUnit ();
    private static final Jurl jurl = new Jurl (storageUnit);
    private static final GUI gui = new GUI();

    public static void startProgram ()
    {
        gui.setBaseFrameVisible ();
        jurl.startProgram ();
    }


    public static void addNewRequest (ClientRequest clientRequest)
    {
        storageUnit.addRequest (clientRequest);
    }

    public static void removeRequest (ClientRequest clientRequest)
    {
        storageUnit.removeRequest (clientRequest);
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

}
