package ControlUnit;

import ClientRequest.ClientRequest;
import ClientSocketHandler.ClientProxy;
import Storage.RequestsStorage;
import java.io.*;
import java.util.ArrayList;

/**
 * this class represent storage controller and handler
 *
 * @author Amir Naziri
 */
public class StorageUnit
{
    private RequestsStorage requestsStorage;

    /**
     * creates a new storage unit
     */
    protected StorageUnit ()
    {
        requestsStorage = new RequestsStorage ();
        load ();
    }

    /**
     * adds new ClientRequest
     * @param clientRequest clientRequest
     */
    protected void addRequest (ClientRequest clientRequest) // means add in app
    {
        requestsStorage.add (clientRequest);
        save ();
    }

    /**
     * removes Client Request
     * @param clientRequest clientRequest
     */
    protected void removeRequest (ClientRequest clientRequest)
    {

        requestsStorage.remove (clientRequest);
        save ();
    }

    /**
     * get client request
     * @param index index
     * @return client request
     */
    protected ClientRequest getClientRequest (int index)
    {
        try{
            if (requestsStorage.size () == 0)
                throw new IndexOutOfBoundsException ("list is out of request");
            if (index - 1 < 0 || index - 1 >= requestsStorage.size ())
                throw new IndexOutOfBoundsException ("index should be in range 1 to " +
                        (requestsStorage.size ()));
        } catch (IndexOutOfBoundsException e)
        {
            System.out.println (e.getMessage ());
            return null;
        }

        return requestsStorage.get (index - 1);
    }

    /**
     * save
     */
    protected void save ()
    {
        try (ObjectOutputStream out = new ObjectOutputStream (
                new FileOutputStream (
                        new File ("./data/RequestList/requestsList.ser")))){

            out.writeObject (requestsStorage);
        } catch (IOException e)
        {
            System.out.println ("some thing was wrong in save");
        }
    }

    /**
     * load
     */
    private void load ()
    {
        try (ObjectInputStream in = new ObjectInputStream (
                new FileInputStream (
                        new File ("./data/RequestList/requestsList.ser")))){
            Object o = in.readObject ();
            requestsStorage =  (RequestsStorage)o;

        } catch (FileNotFoundException ignore)
        {
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println ("some thing was wrong in load");
        }
    }


    /**
     * @return list of client requests
     */
    protected ArrayList<ClientRequest> getClientRequests ()
    {

        return requestsStorage.getClientRequests ();
    }

    /**
     * print list of request
     */
    protected void printList ()
    {
        if (requestsStorage.getClientRequests ().size () == 0)
            System.out.println ("List is Empty");
        else
        {
            int count = 1;
            for (ClientRequest request : requestsStorage.getClientRequests ())
            {
                System.out.println (count + " . " + request.toString ());
                count++;
            }
        }
    }

    protected void updateRequestStorage (RequestsStorage requestsStorage)
    {
        for (ClientRequest clientRequest : getClientRequests ())
        {
            for (ClientRequest clientRequest1 : requestsStorage.getClientRequests ())
            {
                if (clientRequest.equals (clientRequest1))
                    clientRequest = clientRequest1;
            }
        }
    }

}
