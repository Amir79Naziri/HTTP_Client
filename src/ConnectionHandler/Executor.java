package ConnectionHandler;

import ClientRequest.ClientRequest;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class is a executor which executes list of clientRequest in different Threads.
 *
 * @author Amir Naziri
 */
public class Executor implements Runnable
{

    private ArrayList<ClientRequest> clientRequests; // list of client requests
    private ExecutorService pool; // Executor

    /**
     * creates a new Executor
     * @param clientRequests list of client requests
     */
    public Executor (ArrayList<ClientRequest> clientRequests)
    {
        this.clientRequests = clientRequests;
        pool = Executors.newCachedThreadPool ();
    }

    @Override
    public void run () {
        if (clientRequests == null)
            return;
        for (ClientRequest clientRequest : clientRequests)
        {
            try {
                Thread.sleep (3000);
            } catch (InterruptedException e)
            {
                e.printStackTrace ();
            }
            pool.execute (new Connector (clientRequest));
        }
    }
}
