package ControlUnit;

import Client.ClientRequest;
import Jurl.Jurl;
import Storage.StorageUnit;

public class Controller
{
    private static final StorageUnit storageUnit = new StorageUnit ();
    private static final Jurl jurl = new Jurl (storageUnit);

    public static void startProgram ()
    {
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
        storageUnit.save ();
    }

}
