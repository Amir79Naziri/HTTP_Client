package ClientSocketHandler;


import ClientRequest.ClientRequest;
import ControlUnit.Controller;
import Storage.RequestsStorage;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * this class represents ClientSocket
 *
 * @author Amir Naziri
 */
public class ClientSocket implements Runnable
{
    private int port; // port number
    private String host; // IP Address
    private RequestsStorage requestsStorage;
    private boolean successfullyFinished;

    /**
     * client Socket
     * @param clientRequests clientRequests
     */
    public ClientSocket (ArrayList<ClientRequest> clientRequests)
    {
        this.port = -1;
        this.host = null;
        this.requestsStorage = new RequestsStorage ();
        requestsStorage.getClientRequests ().addAll (clientRequests);
        successfullyFinished = false;
    }

    /**
     * client Socket
     * @param clientRequest clientRequest
     */
    public ClientSocket (ClientRequest clientRequest)
    {
        this.port = -1;
        this.host = null;
        requestsStorage = new RequestsStorage ();
        requestsStorage.add (clientRequest);
        successfullyFinished = false;
    }

    /**
     * sets IP Address
     * @param host host
     */
    public void setHost (String host) {
        this.host = host;
    }

    /**
     * sets Port
     * @param port port
     */
    public void setPort (int port) {
        this.port = port;
    }

    /**
     *
     * @return isSuccessfullyFinished
     */
    public boolean isSuccessfullyFinished () {
        return successfullyFinished;
    }

    /**
     *
     * @return requestsStorage
     */
    public RequestsStorage getRequestsStorage () {
        return requestsStorage;
    }

    @Override
    public void run () {
        if (port == -1 && host == null)
        {
            System.err.println ("port number is not valid" + "\nIP is not valid");
            return;
        }
        if (port == -1)
        {
            System.err.println ("port number is not valid");
            return;
        }
        if (host == null)
        {
            System.err.println ("IP is not valid");
            return;
        }
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try (Socket connection = new Socket (host,port))
        {
            out = sendData (connection.getOutputStream ());
            in = receiveData (connection.getInputStream ());
            successfullyFinished = true;
            Controller.printAllResult (requestsStorage);
            Controller.updateRequestsStorage (requestsStorage);
        }
        catch (ConnectException e)
        {
            System.err.println ("Couldn't connect to Server");
        }
        catch (ClassNotFoundException e)
        {
            System.err.println ("Some Thing went Wrong while reading from Client");
        } catch (SocketException e)
        {
            System.err.println ("Server Not Responding");
        } catch (IOException e)
        {
            System.err.println ("Some went Wrong");
        } finally {
            try {
                if (in != null)
                    in.close ();
            }
            catch (SocketException ignore)
            {
            }
            catch (IOException e)
            {
                System.err.println ("Some thing went wrong in closing ServerInputStream");
            }
            try {
                if (out != null)
                    out.close ();
            }
            catch (SocketException ignore)
            {
            }
            catch (IOException e)
            {
                System.err.println ("Some thing went wrong in closing ServerOutputStream");
            }
        }
    }

    /**
     * receive data from server
     * @param serverInputStream serverInputStream
     * @throws IOException IOException
     * @throws ClassNotFoundException couldn't load requestStorage class
     */
    private ObjectInputStream receiveData (InputStream serverInputStream) throws IOException,
            ClassNotFoundException
    {
        ObjectInputStream in = new ObjectInputStream (serverInputStream);
        requestsStorage = (RequestsStorage)in.readObject ();
        System.out.println ("<- data received from Server");
        return in;
    }

    /**
     * send data to request
     * @param serverOutputStream serverOutputStream
     * @throws IOException IOException
     */
    private ObjectOutputStream sendData (OutputStream serverOutputStream) throws IOException
    {
        ObjectOutputStream out = new ObjectOutputStream (serverOutputStream);
        out.writeObject (requestsStorage);
        System.out.println ("-> data sent to Server");
        return out;
    }

}
