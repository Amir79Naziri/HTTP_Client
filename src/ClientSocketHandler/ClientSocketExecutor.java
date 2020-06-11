package ClientSocketHandler;

import ClientRequest.ClientRequest;
import ControlUnit.Controller;
import Storage.RequestsStorage;

/**
 * this class executes the client Socket
 *
 * @author Amir Naziri
 */
public class ClientSocketExecutor implements Runnable
{
    private RequestsStorage requestsStorage;
    private int port;
    private String host;

    /**
     * creates new ClientSocketExecutor
     * @param requestsStorage requestsStorage
     */
    public ClientSocketExecutor (RequestsStorage requestsStorage)
    {
        this.requestsStorage = requestsStorage;

    }

    /**
     * creates new ClientSocketExecutor
     * @param clientRequest clientRequest
     */
    public ClientSocketExecutor (ClientRequest clientRequest) {
        requestsStorage = new RequestsStorage ();
        requestsStorage.add (clientRequest);

    }

    /**
     * set port
     * @param port port
     */
    public void setPort (int port) {
        this.port = port;
    }

    /**
     * set IP address
     * @param host host
     */
    public void setHost (String host) {
        this.host = host;
    }

    @Override
    public void run () {

        ClientSocket clientSocket = new ClientSocket (port,host,requestsStorage);
        Thread thread1 = new Thread (clientSocket,"clientProxy");
        Thread thread2 = new Thread (new Runnable () {
            @Override
            public void run () {
                while (thread1.isAlive ())
                {
                    try {
                        Thread.sleep (1000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace ();
                    }
                    if (clientSocket.isSuccessfullyFinished ())
                    {
                        Controller.printAllResult (clientSocket.getRequestsStorage ());
                        Controller.updateRequestsStorage (clientSocket.getRequestsStorage ());
                    }
                }
            }
        });
    }
}
