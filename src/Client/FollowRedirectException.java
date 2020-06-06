package Client;

public class FollowRedirectException extends Exception
{
    private String newUrl;


    public FollowRedirectException(String newURl)
    {
        super();
        this.newUrl = newURl;
    }

    public String getNewUrl () {
        return newUrl;
    }
}
