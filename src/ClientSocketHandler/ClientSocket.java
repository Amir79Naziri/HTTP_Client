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
    private boolean finished;

    /**
     * client Socket
     * @param clientRequests clientRequests
     * @param host IP
     * @param port port
     */
    public ClientSocket (ArrayList<ClientRequest> clientRequests, String host, int port)
    {
        this.port = port;
        this.host = host;
        this.requestsStorage = new RequestsStorage ();
        requestsStorage.getClientRequests ().addAll (clientRequests);
        finished = false;
    }

    /**
     * client Socket
     * @param clientRequest clientRequest
     * @param port port
     * @param host IP
     */
    public ClientSocket (ClientRequest clientRequest, String host, int port)
    {
        this.port = port;
        this.host = host;
        requestsStorage = new RequestsStorage ();
        requestsStorage.add (clientRequest);
        finished = false;
    }


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
        finished = false;
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
        finished = false;
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
     * @return isFinished
     */
    public boolean isFinished () {
        return finished;
    }



    @Override
    public void run () {
        if (port == -1 && host == null)
        {
            System.err.println ("port number is not valid" + "\nIP is not valid");
            finished = true;
            return;
        }
        if (port == -1)
        {
            System.err.println ("port number is not valid");
            finished = true;
            return;
        }
        if (host == null)
        {
            System.err.println ("IP is not valid");
            finished = true;
            return;
        }
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try (Socket connection = new Socket (host,port))
        {
            out = sendData (connection.getOutputStream ());
            in = receiveData (connection.getInputStream ());
        }
        catch (ConnectException e)
        {
            System.err.println ("Couldn't connect to Server");
            for (ClientRequest clientRequest : requestsStorage.getClientRequests ())
                clientRequest.getResponseStorage ().reset ();
        }
        catch (ClassNotFoundException e)
        {
            System.err.println ("Some Thing went Wrong while reading from Client");
            for (ClientRequest clientRequest : requestsStorage.getClientRequests ())
                clientRequest.getResponseStorage ().reset ();
        } catch (SocketException e)
        {
            System.err.println ("Server Not Responding");
            for (ClientRequest clientRequest : requestsStorage.getClientRequests ())
                clientRequest.getResponseStorage ().reset ();
        } catch (IOException e)
        {
            System.err.println ("Some went Wrong");
            for (ClientRequest clientRequest : requestsStorage.getClientRequests ())
                clientRequest.getResponseStorage ().reset ();
        } finally {
            try {
                if (in != null)
                    in.close ();
            }
            catch (SocketException ignore)
            {
                for (ClientRequest clientRequest : requestsStorage.getClientRequests ())
                    clientRequest.getResponseStorage ().reset ();
            }
            catch (IOException e)
            {
                System.err.println ("Some thing went wrong in closing ServerInputStream");
                for (ClientRequest clientRequest : requestsStorage.getClientRequests ())
                    clientRequest.getResponseStorage ().reset ();
            }
            try {
                if (out != null)
                    out.close ();
            }
            catch (SocketException ignore)
            {
                for (ClientRequest clientRequest : requestsStorage.getClientRequests ())
                    clientRequest.getResponseStorage ().reset ();
            }
            catch (IOException e)
            {
                System.err.println ("Some thing went wrong in closing ServerOutputStream");
                for (ClientRequest clientRequest : requestsStorage.getClientRequests ())
                    clientRequest.getResponseStorage ().reset ();
            }
        }
        Controller.printAllResult (requestsStorage);
        Controller.updateRequestsStorage (requestsStorage);
        finished = true;
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
