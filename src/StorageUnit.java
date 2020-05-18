import java.io.*;

public class StorageUnit implements Serializable
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

    public void removeRequest (int index)
    {
        if (index < 0 || index >= requestsStorage.size ())
            throw new IndexOutOfBoundsException ("index should be in range 0 to " +
                    (requestsStorage.size () - 1));
        requestsStorage.remove (index);
        save ();
    }

    public ClientRequest getClientRequest (int index)
    {
        if (index < 0 || index >= requestsStorage.size ())
            throw new IndexOutOfBoundsException ("index should be in range 0 to " +
                    (requestsStorage.size () - 1));

        return requestsStorage.get (index);
    }

    private void save ()
    {
        try (ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream (file))){

            out.writeObject (requestsStorage);
        } catch (IOException e)
        {
            e.printStackTrace ();
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
        int count = 1;
        for (ClientRequest request : requestsStorage.getClientRequests ())
        {
            System.out.println (count + " . " + request.toString ());
            count++;
        }
    }

}
