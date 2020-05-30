package Storage;

import Client.ClientRequest;
import GUI.Request;

import java.io.*;
import java.util.ArrayList;

public class StorageUnit
{
    private RequestsStorage requestsStorage;
    private File file;

    public StorageUnit ()
    {
        file = new File ("./data/RequestList/requestsList.ser");
        requestsStorage = new RequestsStorage ();
        load ();
    }


    public void addRequest (ClientRequest clientRequest) // means add in app
    {
        requestsStorage.add (clientRequest);
        save ();
    }

    public void removeRequest (ClientRequest clientRequest)
    {

        requestsStorage.remove (clientRequest);
        save ();
    }

    public ClientRequest getClientRequest (int index)
    {
        //TODO : change in bounds
        try{
            if (index - 1 < 0 || index - 1 >= requestsStorage.size ())
                throw new IndexOutOfBoundsException ("index should be in range 0 to " +
                        (requestsStorage.size () - 1));
        } catch (IndexOutOfBoundsException e)
        {
            System.out.println (e.getMessage ());
            return null;
        }

        return requestsStorage.get (index - 1);
    }

    public void save ()
    {
        try (ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream (file))){

            out.writeObject (requestsStorage);
        } catch (IOException e)
        {
            System.out.println ("some thing was wrong in save");
        }
    }

    private void load ()
    {
        try (ObjectInputStream in = new ObjectInputStream (new FileInputStream (file))){
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

    public int size ()
    {
        return requestsStorage.size ();
    }


    public void printList ()
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

}
