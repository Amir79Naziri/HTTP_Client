package ClientSocketHandler;

import ClientRequest.ClientRequest;
import ControlUnit.Controller;
import Storage.RequestsStorage;



public class ClientProxyExecutor implements Runnable
{
    private ClientProxy clientProxy;
    private RequestsStorage requestsStorage;
    private int port;
    private String host;

    public ClientProxyExecutor (RequestsStorage requestsStorage)
    {
        this.requestsStorage = requestsStorage;

    }

    public ClientProxyExecutor (ClientRequest clientRequest) {
        requestsStorage = new RequestsStorage ();
        requestsStorage.add (clientRequest);

    }

    public void setPort (int port) {
        this.port = port;
    }

    public void setHost (String host) {
        this.host = host;
    }

    @Override
    public void run () {

        clientProxy = new ClientProxy (port,host,requestsStorage);

        Thread thread1 = new Thread (clientProxy,"clientProxy");
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
                    Controller.printAllResult (clientProxy.getRequestsStorage ());
                    Controller.updateRequestsStorage (clientProxy.getRequestsStorage ());
                }
            }
        });

    }
}
