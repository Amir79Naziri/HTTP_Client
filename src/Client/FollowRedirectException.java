package Client;

/**
 * this exception occurs when response code is 3** (redirection)
 *
 * @author Amir Naziri
 */
public class FollowRedirectException extends Exception
{
    private String newUrl; // value of Location in response headers
    // (means new url after redirection)

    /**
     * creates a new FollowRedirectException
     * @param newURl new url
     */
    public FollowRedirectException (String newURl)
    {
        super();
        this.newUrl = newURl;
    }

    /**
     * @return new url
     */
    public String getNewUrl () {
        return newUrl;
    }
}
