package ClientSocketHandler;


import Storage.RequestsStorage;
import com.sun.jdi.ClassNotLoadedException;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Client implements Runnable
{
    private int port;
    private String host;
    private RequestsStorage requestsStorage;

    public Client (String host, int port, RequestsStorage requestsStorage)
    {
        this.host = host;
        this.port = port;
        this.requestsStorage = requestsStorage;
    }

    @Override
    public void run () {
        try (Socket connection = new Socket (host,port))
        {
            sendData (connection.getOutputStream ());
            receiveData (connection.getInputStream ());
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
