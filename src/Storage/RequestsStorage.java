package Storage;

import Client.ClientRequest;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class stores list of client requests of program
 *
 * @author Amir Naziri
 */
public class RequestsStorage implements Serializable
{
    private ArrayList<ClientRequest> clientRequests;

    /**
     * creates a new request storage
     */
    public RequestsStorage ()
    {
        clientRequests = new ArrayList<> ();
    }

    /**
     * add a new client request
     * @param clientRequest new clientRequest
     */
    public void add (ClientRequest clientRequest)
    {
        clientRequests.add (clientRequest);
    }

    /**
     * remove client request from storage
     * @param clientRequest clientRequest
     */
    public void remove (ClientRequest clientRequest)
    {
        clientRequests.remove (clientRequest);
    }

    /**
     * gets a client request
     * @param index index
     * @return clientRequest
     */
    public ClientRequest get (int index)
    {
        return clientRequests.get (index);
    }

    /**
     * size of client requests list
     * @return size
     */
    public int size ()
    {
        return clientRequests.size ();
    }

    /**
     * @return list of Client requests
     */
    public ArrayList<ClientRequest> getClientRequests () {
        return clientRequests;
    }
}
