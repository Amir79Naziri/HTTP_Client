package ClientSocketHandler;


import Storage.RequestsStorage;
import com.sun.jdi.ClassNotLoadedException;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientProxy implements Runnable
{
    private int port;
    private String host;
    private RequestsStorage requestsStorage;
    private boolean successfullyFinished;

    public ClientProxy (int port, String host, RequestsStorage requestsStorage)
    {
        this.port = port;
        this.host = host;
        this.requestsStorage = requestsStorage;
        successfullyFinished = false;
    }

    public boolean isSuccessfullyFinished () {
        return successfullyFinished;
    }

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
        } catch (ClassNotLoadedException e)
        {
            System.err.println (e.getMessage ());
        } catch (SocketException e)
        {
            System.err.println ("Server's connection Terminated");
        } catch (IOException e)
        {
            e.printStackTrace ();
        }
    }


    private void receiveData (InputStream serverInputStream) throws IOException,
            ClassNotLoadedException
    {
        try (ObjectInputStream in = new ObjectInputStream (serverInputStream)) {
            requestsStorage = (RequestsStorage)in.readObject ();
            System.out.println ("<- data received from Server");
        } catch (ClassNotFoundException e)
        {
            throw new ClassNotLoadedException ("Some Thing went Wrong while reading from Client");
        }
    }

    private void sendData (OutputStream serverOutputStream) throws IOException
    {
        try (ObjectOutputStream out = new ObjectOutputStream (serverOutputStream)) {
            out.writeObject (requestsStorage);
            System.out.println ("-> data sent to Server");
        }
    }

}
