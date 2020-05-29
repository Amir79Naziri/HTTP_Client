package Jurl;

import Client.ClientRequest;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executor implements Runnable
{

    ArrayList<ClientRequest> clientRequests;
    ExecutorService pool;

    public Executor (ArrayList<ClientRequest> clientRequests)
    {
        this.clientRequests = clientRequests;
        pool = Executors.newCachedThreadPool ();
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

        try {
            pool.awaitTermination (1, TimeUnit.MINUTES);
        } catch (InterruptedException e)
        {
            e.printStackTrace ();
        }

    }
}