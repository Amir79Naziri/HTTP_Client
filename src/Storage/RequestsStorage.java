package Storage;

import Client.ClientRequest;
import java.io.Serializable;
import java.util.ArrayList;

public class RequestsStorage implements Serializable
{
    private ArrayList<ClientRequest> clientRequests;


    public RequestsStorage ()
    {
        clientRequests = new ArrayList<> ();
    }


    public void add (ClientRequest clientRequest)
    {
        clientRequests.add (clientRequest);
    }

    public void remove (ClientRequest clientRequest)
    {
        clientRequests.remove (clientRequest);
    }

    public ClientRequest get (int index)
    {
        return clientRequests.get (index);
    }

    public int size ()
    {
        return clientRequests.size ();
    }

    public ArrayList<ClientRequest> getClientRequests () {
        return clientRequests;
    }
}
