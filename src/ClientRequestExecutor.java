import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientRequestExecutor implements Runnable
{

    ArrayList<ClientRequest> clientRequests;
    ExecutorService pool;

    public ClientRequestExecutor ()
    {
        clientRequests = new ArrayList<> ();
        pool = Executors.newCachedThreadPool ();
    }

    public void setClientRequests (ArrayList<ClientRequest> clientRequests) {
        this.clientRequests = clientRequests;
    }

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
            pool.execute (clientRequest);
        }

    }
}
