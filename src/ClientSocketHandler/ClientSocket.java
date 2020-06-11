package ClientSocketHandler;


import Storage.RequestsStorage;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

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
     * @param port port
     * @param host host
     * @param requestsStorage requestsStorage
     */
    public ClientSocket (int port, String host, RequestsStorage requestsStorage)
    {
        this.port = port;
        this.host = host;
        this.requestsStorage = requestsStorage;
        successfullyFinished = false;
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
        if (port == -1)
        {
            System.err.println ("post number is not valid");
            return;
        }
        if (host == null)
        {
            System.err.println ("IP is not valid");
            return;
        }

        try (Socket connection = new Socket (host,port))
        {
            sendData (connection.getOutputStream ());
            receiveData (connection.getInputStream ());
            successfullyFinished = true;
        } catch (ClassNotFoundException e)
        {
            System.err.println ("Some Thing went Wrong while reading from Client");
        } catch (SocketException e)
        {
            System.err.println ("Server's connection Terminated");
        } catch (IOException e)
        {
            e.printStackTrace ();
        }
    }

    /**
     * receive data from server
     * @param serverInputStream serverInputStream
     * @throws IOException IOException
     * @throws ClassNotFoundException couldn't load requestStorage class
     */
    private void receiveData (InputStream serverInputStream) throws IOException,
            ClassNotFoundException
    {
        try (ObjectInputStream in = new ObjectInputStream (serverInputStream)) {
            requestsStorage = (RequestsStorage)in.readObject ();
            System.out.println ("<- data received from Server");
        }
    }

    /**
     * send data to request
     * @param serverOutputStream serverOutputStream
     * @throws IOException IOException
     */
    private void sendData (OutputStream serverOutputStream) throws IOException
    {
        try (ObjectOutputStream out = new ObjectOutputStream (serverOutputStream)) {
            out.writeObject (requestsStorage);
            System.out.println ("-> data sent to Server");
        }
    }

}
