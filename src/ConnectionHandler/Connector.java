package ConnectionHandler;

import ClientRequest.ClientRequest;

/**
 * this class connect a client request to server
 */
public class Connector implements Runnable
{
    private ClientRequest clientRequest;

    /**
     * create new connector
     * @param clientRequest clientRequest
     */
    public Connector (ClientRequest clientRequest)
    {
        this.clientRequest = clientRequest;
    }

    /**
     * send request
     */
    @Override
    public void run ()
    {
        HttpConnection httpConnection = new HttpConnection (clientRequest.getResponseStorage ());
        String url = clientRequest.getUrl ();

        while (true)
        {
            if (httpConnection.connectionInitializer
                    (clientRequest.getCustomHeaders (), clientRequest.getQueryDataString (),url,
                            clientRequest.getRequestType ()))
            {
                try {
                    switch (clientRequest.getRequestType ())
                    {
                        case GET:
                            httpConnection.onlyGet (clientRequest.isFollowRedirect (),
                                    clientRequest.isShouldSaveOutputInFile (),
                                    clientRequest.getAddressOfFileForSaveOutput ());
                            break;
                        case POST:
                        case PUT:
                        case DELETE:httpConnection.sendAndGet (clientRequest.getMessageBodyType (),
                                clientRequest.getFormUrlData (),clientRequest.getUploadBinaryFile (),
                                clientRequest.getFormUrlDataEncodedString (),
                                clientRequest.isFollowRedirect (),
                                clientRequest.isShouldSaveOutputInFile (),
                                clientRequest.getAddressOfFileForSaveOutput ());
                    }
                    clientRequest.printResult (url);
                    return;
                } catch (FollowRedirectException e)
                {
                    url = e.getNewUrl ();
                }
            }
            else
                return;
        }


    }
}
